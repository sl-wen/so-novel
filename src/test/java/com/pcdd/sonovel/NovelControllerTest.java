package com.pcdd.sonovel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 小说API测试类
 * @author pcdd
 */
@SpringBootTest
@AutoConfigureMockMvc
public class NovelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试搜索接口
     */
    @Test
    public void testSearch() throws Exception {
        // 测试正常搜索场景
        mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/search")
                .param("keyword", "斗破苍穹")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 测试空关键词场景
        // mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/search")
        //         .param("keyword", "")
        //         .contentType(MediaType.APPLICATION_JSON))
        //         .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * 测试下载接口
     */
    @Test
    public void testDownload() throws Exception {
        // 测试正常下载场景
        mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/download")
                .param("url", "https://example.com/novel/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 测试无效URL场景
        mockMvc.perform(MockMvcRequestBuilders.get("/api/novels/download")
                .param("url", "invalid-url")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}