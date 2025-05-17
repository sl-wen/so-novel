package com.pcdd.sonovel.service;

import com.pcdd.sonovel.model.SearchResult;

import java.util.List;

/**
 * 小说服务接口
 *
 * @author pcdd
 * Created at 2024/03/19
 */
public interface NovelService {

    /**
     * 搜索小说
     *
     * @param keyword 关键词(书名或作者名)
     * @return 搜索结果列表
     */
    List<SearchResult> search(String keyword);

    /**
     * 下载小说
     *
     * @param url 小说详情页URL
     * @return 下载耗时(秒)
     */
    double download(String url);
}