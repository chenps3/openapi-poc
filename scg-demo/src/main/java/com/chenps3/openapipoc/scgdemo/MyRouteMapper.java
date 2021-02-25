package com.chenps3.openapipoc.scgdemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyRouteMapper {

    @Select("select * from my_route")
    List<MyRoute> listAll();
}
