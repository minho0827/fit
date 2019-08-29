package com.intellisyscorp.fitzme_android.models;


import lombok.Data;

@Data
public class UserDetailVO {
    private int id;                                         //ID
    private String createdAt;                               //생성일시
    private String updatedAt;                               //수정일시
    private String uuid;                                    //고유 번호 (Token 갱신용)
    private int user;                                       //해당 상세정보가 어떤 유저의 정보인지 나타냅니다.
    private String gender;                                  //성별
    private String agegroup;                                //유저 연령대
    private String accessToekn;                              //sns token
    private String refreshToken;                              //sns token

    private String userFirebaseToken;                       // 유저 FCM Token
    private String userNotiChannel;                         // 유저 알람 채널 여부

}


