package com.hyeonsook.spring.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 테스트를 진행할 때, JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴(SpringRunner)
@WebMvcTest(controllers=HelloController.class) // 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있음
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈을 주입 받음.
    private MockMvc mvc; //스프링 MVC 테스트의 시작점, API 테스트를 할 수 있음.

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";
        // MockMvc를 통해 /hello로 http get 요청을 함
        mvc.perform(get("/hello"))
                // mvc.perform의 결과를 검증함. http header의 status를 검증.
                .andExpect(status().isOk())
                // mvc.perform의 결과를 검증함. content를 검증.
                // Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증.
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                // api 테스트할 때 사용될 요청 파라미터를 설정함.
                // 단 값은 String만 허용됨. 다른 데이터를 등록할 때는 문자열로 변경해야 함.
                .param("name", name).param("amount", String.valueOf((amount))))
                    .andExpect(status().isOk())
                    // json 응답값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명을 명시함.
                    .andExpect(jsonPath("$.name", is(name)))
                    .andExpect((jsonPath(("$.amount", is(amount))));
    }
}
