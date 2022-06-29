package com.example.myrestfulservice.beans;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password","ssn"})
@ApiModel(description = "사용자 상세 정보")
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue
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

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(Integer id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
