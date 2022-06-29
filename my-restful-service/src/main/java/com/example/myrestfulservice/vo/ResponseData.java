package com.example.myrestfulservice.vo;

import com.example.myrestfulservice.beans.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
//    사용자한테 어떤 데이터 타입으로 보낼지 정해주는 곳
    private Integer count;
    private List<User> users;

}
