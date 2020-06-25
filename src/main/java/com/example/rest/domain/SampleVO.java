package com.example.rest.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Data
//모든 속성을 사용하는 생성자를 만들어줌
@AllArgsConstructor
//비어있는 생성자를 만들어줌
@NoArgsConstructor
public class SampleVO {

    private Integer mno;
    private String firstName;
    private String lastName;

}
