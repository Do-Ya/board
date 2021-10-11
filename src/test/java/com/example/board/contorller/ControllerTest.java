package com.example.board.contorller;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ControllerTest {

//    가짜 MVC
//    마치 브라우저에서 URL을 요청한 것처럼 환경을 만들어준다.
    private MockMvc mockMvc;

//    요청을 처리해주는 WebApplicationContext를 불러온다.
    @Autowired
    private WebApplicationContext webApplicationContext;

//    하위의 모든 테스트가 실행 전에 실행되도록 한다.
    @BeforeEach
    public void setUp(){
//        가짜 MVC에 WebApplicationContext를 전달한 후 환경을 생성한다.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testList() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                .andReturn().getModelAndView().getModelMap().toString());
    }

    @Test
    public void testRegister() throws Exception{
        String bno = mockMvc.perform(
                MockMvcRequestBuilders.post("/board/register")
                .param("title", "테스트 새 글 제목")
                .param("content", "테스트 새 글 내용")
                .param("writer", "hds1234")
        ).andReturn().getFlashMap().toString();

        log.info(bno);
    }

    @Test
    public void testRead() throws Exception{
       String board = mockMvc.perform(
                MockMvcRequestBuilders.get("/board/read")
                .param("bno", "41")
        ).andReturn().getModelAndView().getViewName(); /*getModelMap().toString();*/

        log.info(board);

    }

//  /modify 요청을 처리할 수 있는 비지니스 로직 작성
//  수정 성공시 result에 "success"를 담아서 전달한다.
//  단위 테스트로 View에 전달할 파라미터를 조회한다.

    @Test
    public void testModify() throws  Exception{
        String result = mockMvc.perform(
                MockMvcRequestBuilders.post("/board/modify")
                        .param("bno", "41")
                        .param("title", "수정된 글 제목")
                        .param("content", "수정된 글 내용")
        ).andReturn().getModelAndView().getModelMap().toString();

        log.info(result);
    }

    @Test
    public void testRemove() throws Exception{
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove").param("bno", "41"))
                .andReturn().getFlashMap().toString();
        log.info(result);
    }

    @Test
    public void testGoRegister() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/register"))
                .andReturn().getModelAndView().getViewName());
    }

    @Test
    public void testGoModify() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/board/modify").param("bno", "2"))
                .andReturn().getModelAndView().getModelMap().toString();
        log.info(result);
    }
}














