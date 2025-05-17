package com.pcdd.sonovel.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author pcdd
 * Created at 2022/5/23
 */
@Data
@Builder
public class SearchResult {

    /**
     * 书源ID
     */
    private Integer sourceId;

    /**
     * 书源名称
     */
    private String sourceName;

    /**
     * 下载链接
     */
    private String url;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String author;

    /**
     * 章节总数
     */
    private Integer chapterCount;

    /**
     * 最新章节
     */
    private String latestChapter;

    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    /**
     * 简介
     */
    private String intro;

    /**
     * 分类
     */
    private String category;

    /**
     * 状态（连载/完结）
     */
    private String status;

    /**
     * 字数
     */
    private String wordCount;

}