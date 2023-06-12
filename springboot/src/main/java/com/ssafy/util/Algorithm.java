package com.ssafy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ssafy.attraction.model.dto.AttractionDto;
import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.notice.model.dto.NoticeDto;
import com.ssafy.recruit.model.dto.RecruitDto;

public class Algorithm {
	public static List<AttractionDto> filterByKeyword(List<AttractionDto> list, String keyword) {
		List<AttractionDto> res = new ArrayList<AttractionDto>();
		for (int i = 0; i < list.size(); i++) {
			AttractionDto attraction = list.get(i);
			if (doKMP(attraction.getTitle(), keyword)) {
				res.add(attraction);
			}
		}
		return res;
	}

	public static List<BoardDto> sortBoardByRegist(List<BoardDto> list) {
		return QuickSort(list, "ASC");
	}

	public static List<BoardDto> sortBoardHit(List<BoardDto> list) {
		return QuickSort(list, "DESC");
	}
	
	public static List<NoticeDto> sortBoardByRegistN(List<NoticeDto> list) {
		return QuickSortN(list, "ASC");
	}
	
	public static List<NoticeDto> sortBoardHitN(List<NoticeDto> list) {
		return QuickSortN(list, "DESC");
	}

	public static List<RecruitDto> sortBoardByRegistR(List<RecruitDto> list){
		return QuickSortR(list, "ASC");
	}
	public static List<RecruitDto> sortBoardHitR(List<RecruitDto> list){
		return QuickSortR(list, "DESC");
	}
	
	
	private static boolean doKMP(String target, String keyword) {
		// TODO KMP로 문자열에 키워드 포함되는지 찾기
		char[] str = target.toCharArray();
		char[] pattern = keyword.toCharArray();
		System.out.println(Arrays.toString(pattern));

		int[] pi = new int[keyword.length()];
		pi[0] = 0;
		int i = 1, j = 0;
		while (i < pattern.length) {
			while (j > 0 && pattern[i] != pattern[j]) {
				j = pi[j - 1];
			}
			if (pattern[i] == pattern[j]) {
				pi[i] = j + 1;
				j++;
			}
			i++;
		}

		i = j = 0;
		while (i < str.length) {
			while (j > 0 && str[i] != pattern[j]) {
				j = pi[j - 1];
			}
			if (str[i] == pattern[j]) {
				j++;
			}
			i++;
			if (j == pattern.length) {
				return true;
			}
		}

		return false;
	}

	private static List<BoardDto> QuickSort(List<BoardDto> list, String option) {
		if (list.size() < 2) {
			return list;
		}

		List<BoardDto> left = new ArrayList<>();
		List<BoardDto> right = new ArrayList<>();
		// 오래된 글 순
		BoardDto pivot = list.get(list.size() / 2);
		list.remove(list.size() / 2);

		switch (option) {
		case "ASC":
			// 오름차순
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getArticleId() <= pivot.getArticleId()) {
					left.add(list.get(i)); // pivot보다 작을 때 left에 저장
				} else {
					right.add(list.get(i)); // pivot보다 클 때 right에 저장
				}
			}
			break;
		case "DESC":
			// 내림차순
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getHit() <= pivot.getHit()) {
					right.add(list.get(i)); // pivot보다 클 때 left에 저장
				} else {
					left.add(list.get(i)); // pivot보다 작을 때 right에 저장
				}
			}
			break;
		}

		left = QuickSort(left, option);
		right = QuickSort(right, option);
		List<BoardDto> sortedList = new ArrayList<>();
		sortedList.addAll(left);
		sortedList.add(pivot);
		sortedList.addAll(right);

		return sortedList;
	}
	
	private static List<NoticeDto> QuickSortN(List<NoticeDto> list, String option) {
		if (list.size() < 2) {
			return list;
		}
		
		List<NoticeDto> left = new ArrayList<>();
		List<NoticeDto> right = new ArrayList<>();
		// 오래된 글 순
		NoticeDto pivot = list.get(list.size() / 2);
		list.remove(list.size() / 2);
		
		switch (option) {
		case "ASC":
			// 오름차순
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getNoticeId() <= pivot.getNoticeId()) {
					left.add(list.get(i)); // pivot보다 작을 때 left에 저장
				} else {
					right.add(list.get(i)); // pivot보다 클 때 right에 저장
				}
			}
			break;
		case "DESC":
			// 내림차순
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getHit() <= pivot.getHit()) {
					right.add(list.get(i)); // pivot보다 클 때 left에 저장
				} else {
					left.add(list.get(i)); // pivot보다 작을 때 right에 저장
				}
			}
			break;
		}
		
		left = QuickSortN(left, option);
		right = QuickSortN(right, option);
		List<NoticeDto> sortedList = new ArrayList<>();
		sortedList.addAll(left);
		sortedList.add(pivot);
		sortedList.addAll(right);
		
		return sortedList;
	}

	private static List<RecruitDto> QuickSortR(List<RecruitDto> list, String option) {
		if (list.size() < 2) {
			return list;
		}
		
		List<RecruitDto> left = new ArrayList<>();
		List<RecruitDto> right = new ArrayList<>();
		// 오래된 글 순
		RecruitDto pivot = list.get(list.size() / 2);
		list.remove(list.size() / 2);
		
		switch (option) {
		case "ASC":
			// 오름차순
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getArticleId() <= pivot.getArticleId()) {
					left.add(list.get(i)); // pivot보다 작을 때 left에 저장
				} else {
					right.add(list.get(i)); // pivot보다 클 때 right에 저장
				}
			}
			break;
		case "DESC":
			// 내림차순
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getHit() <= pivot.getHit()) {
					right.add(list.get(i)); // pivot보다 클 때 left에 저장
				} else {
					left.add(list.get(i)); // pivot보다 작을 때 right에 저장
				}
			}
			break;
		}
		
		left = QuickSortR(left, option);
		right = QuickSortR(right, option);
		List<RecruitDto> sortedList = new ArrayList<>();
		sortedList.addAll(left);
		sortedList.add(pivot);
		sortedList.addAll(right);
		
		return sortedList;
	}
}
