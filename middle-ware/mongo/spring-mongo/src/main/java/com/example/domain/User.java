package com.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

/**
 * @author simple
 */
@Document(collection = "user")
@ToString
@Data
public class User {
    @MongoId
    private String id;
    private String name;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private LocalDate createdTime;
}
