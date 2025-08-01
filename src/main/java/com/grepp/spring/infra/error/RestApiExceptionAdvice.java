package com.grepp.spring.infra.error;

import com.grepp.spring.infra.error.exceptions.AlreadyCheckedAttendanceException;
import com.grepp.spring.infra.error.exceptions.AlreadyExistException;
import com.grepp.spring.infra.error.exceptions.CommonException;
import com.grepp.spring.infra.error.exceptions.EarlierDateException;
import com.grepp.spring.infra.error.exceptions.InsufficientRewardPointsException;
import com.grepp.spring.infra.error.exceptions.LateApplyException;
import com.grepp.spring.infra.error.exceptions.MailSendFailureException;
import com.grepp.spring.infra.error.exceptions.NotFoundException;
import com.grepp.spring.infra.error.exceptions.NullStateException;
import com.grepp.spring.infra.error.exceptions.OutOfMinimumPageException;
import com.grepp.spring.infra.error.exceptions.OutOfMinimumPageSizeException;
import com.grepp.spring.infra.error.exceptions.PasswordValidationException;
import com.grepp.spring.infra.error.exceptions.Quiz.InvalidQuizException;
import com.grepp.spring.infra.error.exceptions.Quiz.InvalidQuizGradeRequestException;
import com.grepp.spring.infra.error.exceptions.Quiz.QuizAlreadyExistsException;
import com.grepp.spring.infra.error.exceptions.Quiz.QuizGenerationFailedException;
import com.grepp.spring.infra.error.exceptions.Quiz.QuizResultAlreadySubmittedException;
import com.grepp.spring.infra.error.exceptions.Quiz.QuizSetNotFoundException;
import com.grepp.spring.infra.error.exceptions.Quiz.StudyGoalNotFoundException;
import com.grepp.spring.infra.error.exceptions.Quiz.StudyMemberNotFoundException;
import com.grepp.spring.infra.error.exceptions.Quiz.StudyNotFoundException;
import com.grepp.spring.infra.error.exceptions.StudyDataException;
import com.grepp.spring.infra.error.exceptions.alarm.AlarmValidationException;
import com.grepp.spring.infra.response.CommonResponse;
import com.grepp.spring.infra.response.ResponseCode;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.grepp.spring.app.controller.api")
@Slf4j
public class RestApiExceptionAdvice {
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResponse<String>>
    methodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                   .badRequest()
                   .body(CommonResponse.error(ResponseCode.BAD_REQUEST, ex.getMessage()));
    }
    
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse<String>> restApiExceptionHandler(CommonException ex) {
        return ResponseEntity
                   .status(ex.code().status())
                   .body(CommonResponse.error(ex.code()));
    }
    
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<CommonResponse<String>>  authorizationDeniedHandler(AuthorizationDeniedException ex, Model model){
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                   .status(HttpStatus.UNAUTHORIZED)
                   .body(CommonResponse.error(ResponseCode.UNAUTHORIZED));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleIllegalArgumentException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(CommonResponse.error(ResponseCode.UNAUTHORIZED));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleAlreadyExistException(AlreadyExistException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(CommonResponse.error(ResponseCode.ALREADY_EXIST));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(CommonResponse.error(ResponseCode.NOT_FOUND));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleNullPointerException(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CommonResponse.error(ResponseCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST));
    }

    @ExceptionHandler(MailSendFailureException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleMailSendFailureException(MailSendFailureException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(500)
            .body(CommonResponse.error(ResponseCode.MAIL_SEND_FAIL));
    }

    @ExceptionHandler(InsufficientRewardPointsException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleInsufficientPoints(InsufficientRewardPointsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(CommonResponse.error(ResponseCode.POINT_NOT_ENOUGH));
    }

    @ExceptionHandler(AlreadyCheckedAttendanceException.class)
    public ResponseEntity<CommonResponse<String>> handleAlreadyCheckedAttendance(AlreadyCheckedAttendanceException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(CommonResponse.error(ResponseCode.ALREADY_EXIST.code(), ex.getMessage()));
    }

    @ExceptionHandler(OutOfMinimumPageSizeException.class)
    public ResponseEntity<CommonResponse<String>> handleOutOfMinimumPageSize(OutOfMinimumPageSizeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST.code(), ex.getMessage()));
    }

    @ExceptionHandler(OutOfMinimumPageException.class)
    public ResponseEntity<CommonResponse<String>> handleOutOfMinimumPage(OutOfMinimumPageException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST.code(), ex.getMessage()));
    }

    @ExceptionHandler(StudyDataException.class)
    public ResponseEntity<CommonResponse<String>> handleStudyDataException(StudyDataException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(ex.code().status())
            .body(CommonResponse.error(ex.code().code(), ex.getMessage()));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .badRequest()
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST.code(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST.code(), ex.getMessage()));
    }

    @ExceptionHandler(StudyNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleStudyNotFoundException(StudyNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.FAIL_SEARCH_STUDY.status())
                .body(CommonResponse.error(ResponseCode.FAIL_SEARCH_STUDY.code(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidQuizException.class)
    public ResponseEntity<CommonResponse<String>> handleInvalidQuizException(InvalidQuizException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.INVALID_QUIZ.status())
                .body(CommonResponse.error(ResponseCode.INVALID_QUIZ.code(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidQuizGradeRequestException.class)
    public ResponseEntity<CommonResponse<String>> handleInvalidQuizGradeRequest(InvalidQuizGradeRequestException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.INVALID_QUIZ_GRADE_REQUEST.status())
                .body(CommonResponse.error(ResponseCode.INVALID_QUIZ_GRADE_REQUEST.code(), ex.getMessage()));
    }

    @ExceptionHandler(QuizSetNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleQuizSetNotFound(QuizSetNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.FAIL_SEARCH_STUDY.status())
                .body(CommonResponse.error(ResponseCode.FAIL_SEARCH_STUDY.code(), ex.getMessage()));
    }

    @ExceptionHandler(QuizResultAlreadySubmittedException.class)
    public ResponseEntity<CommonResponse<String>> handleQuizResultAlreadySubmitted(QuizResultAlreadySubmittedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.QUIZ_RESULT_ALREADY_SUBMITTED.status())
                .body(CommonResponse.error(ResponseCode.QUIZ_RESULT_ALREADY_SUBMITTED.code(), ex.getMessage()));
    }

    @ExceptionHandler(QuizAlreadyExistsException.class)
    public ResponseEntity<CommonResponse<String>> handleQuizAlreadyExistsException(QuizAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.QUIZ_ALREADY_EXISTS.status())
                .body(CommonResponse.error(ResponseCode.QUIZ_ALREADY_EXISTS.code(), ex.getMessage()));
    }

    @ExceptionHandler(StudyGoalNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleStudyGoalNotFound(StudyGoalNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.STUDY_GOAL_NOT_FOUND.status())
                .body(CommonResponse.error(ResponseCode.STUDY_GOAL_NOT_FOUND.code(), ex.getMessage()));
    }

    @ExceptionHandler(QuizGenerationFailedException.class)
    public ResponseEntity<CommonResponse<String>> handleQuizGenerationFailed(QuizGenerationFailedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.QUIZ_GENERATION_FAILED.status())
                .body(CommonResponse.error(ResponseCode.QUIZ_GENERATION_FAILED.code(), ex.getMessage()));
    }

    @ExceptionHandler(StudyMemberNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleStudyMemberNotFound(StudyMemberNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ResponseCode.STUDY_MEMBER_NOT_FOUND.status())
                .body(CommonResponse.error(ResponseCode.STUDY_MEMBER_NOT_FOUND.code(), ex.getMessage()));
    }

    @ExceptionHandler(NullStateException.class)
    public ResponseEntity<CommonResponse<String>> handleNullStateException(NullStateException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(CommonResponse.error(ResponseCode.NOT_FOUND));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CommonResponse<String>> handleNoSuchElementException(NoSuchElementException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST.code(), ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(CommonResponse.error(ResponseCode.NOT_FOUND));
    }

    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<CommonResponse<Void>> handlePasswordValidation(PasswordValidationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(ex.getCode().status())
            .body(CommonResponse.error(ex.getCode()));
    }

    @ExceptionHandler(AlarmValidationException.class)
    public ResponseEntity<CommonResponse<Void>> handleAlarmValidation(AlarmValidationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(ex.getCode().status())
            .body(CommonResponse.error(ex.getCode()));
    }

    @ExceptionHandler(EarlierDateException.class)
    public ResponseEntity<CommonResponse<Void>> handlerEarlierDateException(EarlierDateException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST));
    }

    @ExceptionHandler(LateApplyException.class)
    public ResponseEntity<CommonResponse<Void>> handlerLateApplyException(LateApplyException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(ResponseCode.BAD_REQUEST));
    }
}

