package com.minibackoffice.backend.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

/**
 - Postman에서 보내는 JSON Body를 받을 DTO(요청용 상자)
 
 예시 요청(JSON):
 {
    "status": "BLOCKED"
 }

 이 JSON이 들어오면,
 스프링이 자동으로 이 클래스로 바꿔서(매핑해서) 컨트롤러에 전달해줌
 */
public class UserStatusUpdateRequest {

    @NotBlank(message = "status는 필수입니다.")
    private String status; // "ACTIVE" 또는 "BLOCKED"

    public String getStatus() {
        return status;
    }
}
