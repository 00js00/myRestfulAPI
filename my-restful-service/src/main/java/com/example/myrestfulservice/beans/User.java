package com.example.myrestfulservice.beans;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value={"password","ssn"})
@ApiModel(description = "user detail information")
public class User {
    private Integer id;
    @Size(min=2, message = "please input more than 2")
    @ApiModelProperty(notes="input user name")
    private String name;

    @Past
    @ApiModelProperty(notes="input user date")
    private Date joinDate;

//    @JsonIgnore
    @ApiModelProperty(notes="input user pwd")
    private String password;

//    @JsonIgnore
    @ApiModelProperty(notes="input user ssn")
    private String ssn;
}
