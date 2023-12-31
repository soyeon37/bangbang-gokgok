package com.ssafy.notice.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.notice.model.dto.NoticeDto;
import com.ssafy.util.PageNavigation;

public interface NoticeService {
	void writeArticle(NoticeDto noticeDto) throws Exception;
	List<NoticeDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	NoticeDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void update(NoticeDto noticeDto) throws Exception;
	void delete(int articleNo)  throws Exception;
}
