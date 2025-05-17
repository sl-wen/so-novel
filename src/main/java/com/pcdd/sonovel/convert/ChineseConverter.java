package com.pcdd.sonovel.convert;

import com.hankcs.hanlp.HanLP;
import com.pcdd.sonovel.model.Book;
import com.pcdd.sonovel.model.Chapter;
import com.pcdd.sonovel.model.SearchResult;
import com.pcdd.sonovel.util.LangType;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

/**
 * @author pcdd
 * Created at 2024/12/31
 */
@UtilityClass
public class ChineseConverter {

    public <T> T convert(T obj, String sourceLang, String targetLang) {
        if (obj == null || targetLang.equals(sourceLang)) {
            return obj;
        }

        Function<String, String> convertFunc = getConversionFunction(sourceLang, targetLang);
        if (convertFunc == null) {
            return obj;
        }

        return applyConversion(obj, convertFunc);
    }

    private Function<String, String> getConversionFunction(String sourceLang, String targetLang) {
        return switch (sourceLang + ">" + targetLang) {
            case LangType.ZH_HANT + ">" + LangType.ZH_CN -> HanLP::t2s;
            case LangType.ZH_CN + ">" + LangType.ZH_HANT -> HanLP::s2t;
            case LangType.ZH_CN + ">" + LangType.ZH_TW -> HanLP::s2tw;
            case LangType.ZH_HANT + ">" + LangType.ZH_TW -> HanLP::t2tw;
            default -> null;
        };
    }

    private <T> T applyConversion(T obj, Function<String, String> convertFunc) {
        if (obj instanceof Book book) {
            Book convertedBook = new Book();
            convertedBook.setBookName(convertIfNotNull(book.getBookName(), convertFunc));
            convertedBook.setAuthor(convertIfNotNull(book.getAuthor(), convertFunc));
            convertedBook.setIntro(convertIfNotNull(book.getIntro(), convertFunc));
            convertedBook.setCategory(convertIfNotNull(book.getCategory(), convertFunc));
            convertedBook.setLatestChapter(convertIfNotNull(book.getLatestChapter(), convertFunc));
            convertedBook.setLastUpdateTime(convertIfNotNull(book.getLastUpdateTime(), convertFunc));
            convertedBook.setStatus(convertIfNotNull(book.getStatus(), convertFunc));
            convertedBook.setUrl(book.getUrl());
            @SuppressWarnings("unchecked")
            T result = (T) convertedBook;
            return result;
        }

        if (obj instanceof Chapter chapter) {
            Chapter convertedChapter = Chapter.builder()
                    .title(convertIfNotNull(chapter.getTitle(), convertFunc))
                    .content(convertIfNotNull(chapter.getContent(), convertFunc))
                    .url(chapter.getUrl())
                    .build();
            @SuppressWarnings("unchecked")
            T result = (T) convertedChapter;
            return result;
        }

        if (obj instanceof SearchResult sr) {
            SearchResult convertedSr = SearchResult.builder()
                    .bookName(convertIfNotNull(sr.getBookName(), convertFunc))
                    .author(convertIfNotNull(sr.getAuthor(), convertFunc))
                    .intro(convertIfNotNull(sr.getIntro(), convertFunc))
                    .category(convertIfNotNull(sr.getCategory(), convertFunc))
                    .latestChapter(convertIfNotNull(sr.getLatestChapter(), convertFunc))
                    .lastUpdateTime(convertIfNotNull(sr.getLastUpdateTime(), convertFunc))
                    .wordCount(convertIfNotNull(sr.getWordCount(), convertFunc))
                    .status(convertIfNotNull(sr.getStatus(), convertFunc))
                    .url(sr.getUrl())
                    .build();
            @SuppressWarnings("unchecked")
            T result = (T) convertedSr;
            return result;
        }

        return obj;
    }

    private String convertIfNotNull(String value, Function<String, String> convertFunc) {
        return value != null ? convertFunc.apply(value) : null;
    }

}