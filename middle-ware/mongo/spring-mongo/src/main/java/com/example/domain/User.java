package com.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;

/**
 * @author simple
 */
@Document(collation = "user")
@Data
public class User {
    @MongoId
    private String id;
    private String name;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private ZonedDateTime createdTime;
}
