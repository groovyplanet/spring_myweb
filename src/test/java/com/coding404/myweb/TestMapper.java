package com.coding404.myweb;

import com.coding404.myweb.command.MemoVO;
import com.coding404.myweb.command.UsersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TestMapper {

    //EX_MEMO테이블 N
    //EX_USERS테이블 1
    //N:1조인 형식
    public ArrayList<MemoVO> joinOne();

    //1:N조인 형식
    public UsersVO joinTwo();


}
