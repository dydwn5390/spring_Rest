package com.example.rest.controller;


import com.example.rest.domain.Ticket;
import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/applicationContext.xml","file:src/main/webapp/WEB-INF/spring/dispatcher-servlet.xml"})
@Log4j
public class SampleControllerTests {

    @Setter(onMethod_={@Autowired})
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    //SampleController의 convert() 메서드를 테스트하기 위해 작성
    public void testConvert() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setTno(123);
        ticket.setOwner("admin");
        ticket.setGrade("AAA");

        //GSON: JSON 구조를 띄는 직렬화된 데이터를 JAVA의 객체로 역 직렬화, 직렬화 해주는 자바 라이브러리. 즉 JSON obj -> JAVA obj 또는 그 반대의 행위를 돕는 라이브러리 (마샬링)
        //아래 코드는 Ticket 객체를 json 타입으로 변환
        String jsonStr = new Gson().toJson(ticket);

        log.info(jsonStr);

        //contentType() : 전달하는 데이터가 어떤 형식인지 알려줌
        mockMvc.perform(post("/sample/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().is(200));
    }
}
