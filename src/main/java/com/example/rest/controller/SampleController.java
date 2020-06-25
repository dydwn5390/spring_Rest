package com.example.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.rest.domain.SampleVO;
import com.example.rest.domain.Ticket;
import lombok.extern.log4j.Log4j;
import org.apache.tools.ant.taskdefs.Get;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

    //1. 단순 문자열 반환
    @GetMapping(value = "/getText", produces="text/plains; charset=UTF-8")
    public String getText(){

        log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
        return "안녕하세요";
    }

    //2. 객체 반환 -> JSON이나 XML 이용
    //JSON 방식과 XML 방식의 데이터를 생성할 수 있도록 작성됨
    @GetMapping(value="/getSample", produces={MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SampleVO getSample(){
        return new SampleVO(112,"스타","로드");
    }

    //produces 생략해도 위와 결과 동일
    @GetMapping(value = "/getSample2")
    public SampleVO getSample2(){
        return new SampleVO(113,"로켓","라쿤");
    }

    //3. 컬렉션 타입의 객체 반환
    // 여러 데이터를 한 번에 전송하기 위해서 배열이나 리스트, 맵 타입의 객체들을 전송
    @GetMapping(value="/getList")
    public List<SampleVO> getList(){
        // 자바 스트림 -> .collect : 결과 리턴, .forEach : 바로 출력
        return IntStream.range(1,10).mapToObj(i -> new SampleVO(i, i+"First", i+"Last")).collect(Collectors.toList());
    }

    //맵의 경우 '키'와' 값' 을 가지는 하나의 객체로 간주
    @GetMapping(value="/getMap")
    public Map<String, SampleVO> getMap(){
        Map<String, SampleVO> map = new HashMap<>();
        map.put("First",new SampleVO(111,"그루트","주니어"));
        return map;
    }

    //4.ResponseEntity 타입
    //REST 방식으로 호출하는 경우는 화면 자체가 아니라 데이터 자체를 전송하는 방식으로 처리되기 때문에 데이터를 요청한 쪽에서는 정상적인 데이터인지 비정상적인 데이터인지를 구분할 수 있는 방법을 제공해야 함
    //ResponseEntity는 데이터와 함께 HTTP 헤더의 상태 메세지 등을 같이 전달하는 용도로 사용
    //HTTP의 상태 코드와 에러 메세지 등을 함께 데이터와 전달할 수 있기 때문에 받는 입장에서는 확실히 결과를 알 수 있음.

    @GetMapping(value="/check", params={"height","weight"})
    public ResponseEntity<SampleVO> check(Double height, Double weight){
        //string으로 변경해주기 위해 ""+
        SampleVO vo = new SampleVO(0, ""+height, ""+weight);

        ResponseEntity<SampleVO> result = null;


        if(height < 150){
            //height 값이 150보다 작다면 502 상태 코드와 vo의 데이터 전송
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        }
        else{
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }

        return result;
    }

    //5. @PathVariavle : url 경로 일부를 파라미터로 추출할 때 사용
    @GetMapping("/product/{cat}/{pid}")
    public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid){
        return new String[] {"category: " + cat, "productid: " + pid};
    }

    //6. @RequestBody
    //요청한 내용(body)를 처리하기 때문에 post 사용
    //JSON으로 전달되는 데이터를 받아서 Ticket 타입으로 변환
    @PostMapping("/ticket")
    public Ticket convert(@RequestBody Ticket ticket){
        log.info("convert......ticket" + ticket);
        return ticket;
    }

}
