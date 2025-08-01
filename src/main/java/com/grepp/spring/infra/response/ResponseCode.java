package com.grepp.spring.infra.response;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    SUCCESS("0000", HttpStatus.OK, "정상적으로 완료되었습니다."),
    ITEM_PURCHASE_SUCCESS("0002", HttpStatus.CREATED, "성공적으로 구매하였습니다."),
    BAD_REQUEST("4000", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_FILENAME("4001", HttpStatus.BAD_REQUEST, "사용 할 수 없는 파일 이름입니다."),
    FAIL_SEARCH_STUDY("4002", HttpStatus.NOT_FOUND, "스터디 검색에 실패했습니다."),
    SAME_STATE("4003", HttpStatus.BAD_REQUEST , "현재 상태와 동일한 상태입니다."),
    FAIL_GET_STUDY_INFO("4004", HttpStatus.NOT_FOUND,"스터디 상세 정보를 조회하지 못했습니다."),
    UNAUTHORIZED("4010", HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    BAD_CREDENTIAL("4011", HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다."),
    NOT_EXIST_PRE_AUTH_CREDENTIAL("4012", HttpStatus.OK, "사전 인증 정보가 요청에서 발견되지 않았습니다."),
    SOCIAL_LOGIN_CONFLICT("4013", HttpStatus.UNAUTHORIZED, "이미 동일한 이메일의 로컬 계정이 존재합니다."),
    INCORRECT_PASSWORD("4015", HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
    SAME_PASSWORD_NOT_ALLOWED("4016", HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 달라야 합니다."),
    NOT_FOUND("4040", HttpStatus.NOT_FOUND, "NOT FOUND"),
    ALREADY_EXIST("4090", HttpStatus.CONFLICT, "해당 데이터는 이미 존재합니다."),
    POINT_NOT_ENOUGH("4091",HttpStatus.CONFLICT,"포인트가 부족합니다."),
    ALREADY_ATTENDED("4092",  HttpStatus.CONFLICT, "이미 출석했습니다."),
    INTERNAL_SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "서버에러 입니다."),
    MAIL_SEND_FAIL("5001", HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패하였습니다."),
    SECURITY_INCIDENT("6000", HttpStatus.OK, "비정상적인 로그인 시도가 감지되었습니다."),
    INVALID_QUIZ("8000", HttpStatus.NOT_FOUND, "유효하지 않은 퀴즈 데이터입니다."),
    QUIZ_ALREADY_EXISTS("8001", HttpStatus.CONFLICT, "이미 해당 주차의 퀴즈가 존재합니다."),
    INVALID_QUIZ_GRADE_REQUEST("8002", HttpStatus.BAD_REQUEST, "퀴즈 채점 요청이 올바르지 않습니다."),
    QUIZ_RESULT_ALREADY_SUBMITTED("8003", HttpStatus.CONFLICT, "이미 퀴즈 결과가 제출되었습니다."),
    STUDY_GOAL_NOT_FOUND("8005", HttpStatus.NOT_FOUND, "해당 주차의 스터디 목표가 존재하지 않습니다."),
    QUIZ_GENERATION_FAILED("8006", HttpStatus.INTERNAL_SERVER_ERROR, "퀴즈 생성 중 오류가 발생했습니다."),
    STUDY_MEMBER_NOT_FOUND("8007", HttpStatus.NOT_FOUND, "해당 스터디에 존재하지 않는 스터디 멤버입니다."),
    ALARM_RESULT_STATUS_REQUIRED("9000", HttpStatus.BAD_REQUEST, "RESULT 타입 알람은 resultStatus가 필요합니다."),
    ALARM_RESULT_STATUS_NOT_ALLOWED("9001", HttpStatus.BAD_REQUEST, "APPLY 타입 알람은 resultStatus를 포함할 수 없습니다."),
    ALARM_ACCESS_DENIED("9002", HttpStatus.FORBIDDEN, "본인의 알림만 읽음 처리할 수 있습니다."),
    ALARM_SENDER_EQUALS_RECEIVER("9003", HttpStatus.BAD_REQUEST, "자기 자신에게 알림을 보낼 수 없습니다."),
    ALARM_SENDER_UNAUTHORIZED("9004", HttpStatus.UNAUTHORIZED, "알림 발신자는 본인이어야 합니다."),
    ALARM_STUDY_ID_REQUIRED("9005", HttpStatus.BAD_REQUEST, "해당 타입의 알림에는 studyId가 필요합니다.")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    ResponseCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }

}