package com.pcdd.sonovel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.pcdd.sonovel.core.Crawler;
import com.pcdd.sonovel.core.Source;
import com.pcdd.sonovel.model.*;
import com.pcdd.sonovel.parse.BookParser;
import com.pcdd.sonovel.parse.TocParser;
import com.pcdd.sonovel.service.NovelService;
import com.pcdd.sonovel.util.JsoupUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 小说服务实现类
 *
 * @author pcdd
 * Created at 2024/03/19
 */
@Service
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService {

    private final AppConfig appConfig;

    @Override
    public List<SearchResult> search(String keyword) {
        if (StrUtil.isEmpty(keyword)) {
            return Collections.emptyList();
        }
        return new Crawler(appConfig).search(keyword);
    }

    @Override
    public double download(String url) {
        if (StrUtil.isEmpty(url)) {
            return 0.0;
        }

        // 获取书源规则
        Rule rule = new Source(appConfig).rule;
        // 处理特殊网站链接转换
        String bookUrl = JsoupUtils.invokeJs(rule.getBook().getUrl(), url);
        
        // 解析书籍信息
        Book book = new BookParser(appConfig).parse(bookUrl);
        SearchResult sr = SearchResult.builder()
                .url(book.getUrl())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .latestChapter(book.getLatestChapter())
                .lastUpdateTime(book.getLastUpdateTime())
                .build();

        // 解析目录
        TocParser tocParser = new TocParser(appConfig);
        List<Chapter> toc = tocParser.parse(sr.getUrl());

        if (CollUtil.isEmpty(toc)) {
            return 0.0;
        }

        // 开始下载
        return new Crawler(appConfig).crawl(sr.getUrl(), toc);
    }
}