package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Integer>{
    //@Query("select m from Message m where m.posted_by=?1")
    //List<Message> findAllByPosted_by(Integer posted_by);

    @Query("SELECT m FROM Message m WHERE m.posted_by = :postedBy")
    List<Message> findAllByPosted_by(@Param("postedBy") Integer posted_by);

}
