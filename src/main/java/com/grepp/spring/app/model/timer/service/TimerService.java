package com.grepp.spring.app.model.timer.service;

import com.grepp.spring.app.controller.api.timer.payload.StudyTimeRecordRequest;
import com.grepp.spring.app.model.member.repository.StudyMemberRepository;
import com.grepp.spring.app.model.study.repository.StudyRepository;
import com.grepp.spring.app.model.timer.dto.DailyStudyLogResponse;
import com.grepp.spring.app.model.timer.dto.StudyWeekTimeResponse;
import com.grepp.spring.app.model.timer.dto.TotalStudyTimeResponse;
import com.grepp.spring.app.model.timer.entity.Timer;
import com.grepp.spring.app.model.timer.repository.TimerRepository;
import com.grepp.spring.infra.error.exceptions.EarlierDateException;
import com.grepp.spring.infra.error.exceptions.NotFoundException;
import com.grepp.spring.infra.error.exceptions.StudyDataException;
import com.grepp.spring.infra.response.ResponseCode;
import com.querydsl.core.Tuple;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    @Transactional(readOnly = true)
    public TotalStudyTimeResponse getAllStudyTime(Long memberId) {
        List<Long> studyMemberIds = studyMemberRepository.findAllStudies(memberId);
        if (studyMemberIds == null || studyMemberIds.isEmpty()) {
            log.info("참여 중인 스터디가 없습니다. memberId: {}", memberId);
            return TotalStudyTimeResponse.builder()
                .totalStudyTime(0L)
                .build();
        }

        log.info("조회된 studyMemberIds: {}", studyMemberIds);
        Long totalStudyTime = timerRepository.findTotalStudyTime(studyMemberIds);

        return TotalStudyTimeResponse.builder()
            .totalStudyTime(totalStudyTime != null ? totalStudyTime : 0L)
            .build();
    }

    @Transactional
    public List<DailyStudyLogResponse> findDailyStudyLogs(Long studyId, Long memberId) {
        Long studyMemberId = studyMemberRepository.findStudyMemberId(studyId, memberId)
            .orElseThrow(() -> new NotFoundException("Study Member Not Found"));
        LocalDateTime endOfDay = LocalDateTime.now();
        LocalDateTime startOfDay = endOfDay.toLocalDate().minusDays(6).atStartOfDay();

        List<Tuple> results = timerRepository.findDailyStudyLogs(studyMemberId, studyId, startOfDay, endOfDay);

        return results.stream()
            .map(tuple -> new DailyStudyLogResponse(
                tuple.get(0, Date.class).toLocalDate(),
                tuple.get(1, Long.class)
            ))
            .toList();
    }

    @Transactional(readOnly = true)
    public StudyWeekTimeResponse getStudyTimeForPeriod(Long studyId, Long memberId) {
        Long studyMemberId = studyMemberRepository.findStudyMemberId(studyId, memberId)
            .orElseThrow(() -> new NotFoundException("Study Member Not Found"));

        LocalDateTime endOfDay = LocalDateTime.now();
        LocalDateTime startOfDay = endOfDay.toLocalDate().minusDays(6).atStartOfDay();

        Long totalTime = timerRepository.findTotalStudyLogsInWeek(studyMemberId, studyId, startOfDay, endOfDay);

        return StudyWeekTimeResponse.builder()
            .totalStudyTime(totalTime != null ? totalTime : 0L)
            .build();
    }

    @Transactional
    public void recordStudyTime(Long studyId, Long memberId, StudyTimeRecordRequest req) {

        LocalDate studyStartDate = studyRepository.findStudyStartDate(studyId);
        if (studyStartDate.isAfter(LocalDate.now())) {
            throw new EarlierDateException(ResponseCode.BAD_REQUEST);
        }

        Long studyMemberId = studyMemberRepository.findStudyMemberId(studyId, memberId)
            .orElseThrow(() -> new NotFoundException("Study Member Not Found"));

        Timer timer = Timer.builder()
            .studyId(studyId)
            .studyMemberId(studyMemberId)
            .dailyStudyTime(req.getStudyTime())
            .build();
        timerRepository.save(timer);
    }
}
