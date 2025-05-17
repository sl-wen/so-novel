package com.pcdd.sonovel.controller;

import cn.hutool.core.util.StrUtil;

import com.pcdd.sonovel.model.SearchResult;
import com.pcdd.sonovel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 小说搜索和下载接口
 *
 * @author pcdd
 * Created at 2024/03/19
 */
@RestController
@RequestMapping("/api/novels")
@RequiredArgsConstructor
public class NovelController {

    private final NovelService novelService;

    /**
     * 根据书名或作者名搜索小说
     *
     * @param keyword 关键词(书名或作者名)
     * @return 搜索结果列表
     */
    @GetMapping("/search")
    public ResponseEntity<List<SearchResult>> search(@RequestParam String keyword) {
        if (StrUtil.isEmpty(keyword)) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<SearchResult> results = novelService.search(keyword);
        return ResponseEntity.ok(results);
    }

    /**
     * 根据小说URL下载小说
     *
     * @param url 小说详情页URL
     * @return 下载结果
     */
    @GetMapping("/download")
    public ResponseEntity<String> download(@RequestParam String url) {
        if (StrUtil.isEmpty(url)) {
            return ResponseEntity.badRequest().body("下载URL不能为空");
        }
        double time = novelService.download(url);
        if (time <= 0) {
            return ResponseEntity.badRequest().body("下载失败，请检查URL是否有效");
        }
        return ResponseEntity.ok("下载完成，耗时" + time + "秒");
    }
}