package com.example.myrestfulservice.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    @Size(min=2, message = "please input more than 2")
    private String name;
    @Past
    private Date joinDate;
}
