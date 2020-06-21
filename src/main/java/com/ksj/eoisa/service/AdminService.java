package com.ksj.eoisa.service;

import java.util.List;

import com.ksj.eoisa.dao.AdminDAO;
import com.ksj.eoisa.dto.BoardDTO;
import com.ksj.eoisa.dto.MainDTO;
import com.ksj.eoisa.dto.NoticeBoardDTO;
import com.ksj.eoisa.dto.SignDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private AdminDAO dao;

	public List<SignDTO> getUserAll(String column) {
		return dao.getUserList(column);
	}

	public List<NoticeBoardDTO> getNoticeAll() {

		return dao.getNoticeList();
	}

	public List<BoardDTO> getFreeAll() {
		return dao.getFreeList();
	}

	public List<BoardDTO> getReviewAll() {
		return dao.getReviewList();
	}

	public List<MainDTO> getDealAll() {
		return dao.getDealList();
	}

	public int getUserCount() {
		return dao.getUserCount();
	}

	public int getNoticeCount() {
		return dao.getNoticeCount();
	}

	public int getFreeCount() {
		return dao.getFreeCount();
	}

	public int getReviewCount() {
		return dao.getReviewCount();
	}

	public int getDealCount() {
		return dao.getDealCount();
	}

	public int updateMember(List<SignDTO> list) {
		return dao.updateUser(list);
	}

	public int deleteBoard(List<BoardDTO> list, String type) {
		if(type.equals("review")) {
			return dao.delReview(list);
		} else {
			return dao.delFree(list);
		}
	}

	public int deleteNotice(List<NoticeBoardDTO> list) {
		return dao.delNotice(list);
	}

	public int deleteDeal(List<MainDTO> list) {
		return dao.deleteDeal(list);
	}

	public int deleteSearch(List<BoardDTO> list) {
		return dao.deleteSearch(list);
	}

	// search
	public List<SignDTO> searchMemberlist(String sVal) {
		return dao.searchMember(sVal);
	}

	public List<MainDTO> searchDeallist(String sVal) {
		return dao.searchDeal(sVal);
	}

	public List<BoardDTO> searchBoardlist(String sVal) {
		return dao.searchAllBoard(sVal);
	}
	
}