package com.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dao.BookeepingMapper;
import com.domain.Account;
import com.domain.Article;
import com.domain.Reply;
import com.domain.TimeUtil;
import com.domain.Transferarticle;
import com.domain.Transferreply;
import com.domain.User;

@Service
@Scope
public class BookeepingService {
	@Resource
	private BookeepingMapper bookeepingMapper;

	public List<User> getUserByPhoneNum(String userphone) {
		return bookeepingMapper.matchUserIdByPhone(userphone);
	}

	public int getUserIdByPhoneNum(String userphone) {
		return bookeepingMapper.selectUserIdByPhoneNum(userphone);
	}

	public List<User> getUserByPhoneAndpassword(String userphonenum, String password) {
		return bookeepingMapper.matchUserByPhoneAndpassword(userphonenum, password);
	}

	/*public int LoginUser(String userphonenum, String password) throws Exception {
		List<User> userlist = bookeepingMapper.selectUserListByPhoneNum(userphonenum);
		// 用户名不存在
		if (userlist.size() == 0)
			return 0;
		// 密码不对
		List<User> list = bookeepingMapper.matchUserByPhoneAndpassword(userphonenum, password);
		if (list.size() == 0)
			return 1;
		else
			// 找到叻
			return 2;
	}*/

	public boolean RegisterUser(User user) {
		List<User> list = bookeepingMapper.matchUserIdByPhone(user.getUser_phone());
		if (list.size() != 0) {
			return false;
		} else {
			bookeepingMapper.InsertUser(user);
			return true;
		}
	}

	public boolean UpdateUserNikename(String phone, String nikename) {
		return bookeepingMapper.UpdateNikename(phone, nikename) == 1;
	}

	public boolean UpdateUserImage(String phone, String imagePath) {
		return bookeepingMapper.UpdateUserImage(phone, imagePath) == 1;
	}

	public int AddArticle(String phone, String content, String time, String imgs) {
		List<User> u = bookeepingMapper.matchUserIdByPhone(phone);
		if (u.size() != 0) {
			Article article = new Article(u.get(0).getUser_id(), content, imgs, time);
			return bookeepingMapper.InsertArticle(article);
		} else {
			return 0;
		}
	}

	public List<Transferarticle> getArticleByTime() {
		return bookeepingMapper.getArticleByTime();
	}

	public void InsertReply(Reply reply) {
		bookeepingMapper.InsertReply(reply);
	}

	public List<Transferreply> getReplyByid(String article_id) {
		 List<Transferreply> list =  bookeepingMapper.getReplyById(article_id);
		 for(Transferreply r:list) {
			 r.setUser_imgage(bookeepingMapper.selectUserImageById(r.getReply_user_id()));
		 }
		 return list;
	}

	public int AddAccount(Account account, String userPhone) {
		List<User> list = bookeepingMapper.matchUserIdByPhone(userPhone);
		if (list.size() != 0) {
			account.setUser_id(list.get(0).getUser_id());
			bookeepingMapper.InsertAccount(account);
			return account.getBill_id();
		} else {
			return 0;
		}
	}

	public void AddAccountList(List<Account> accountlist, String userPhone) {
		int id = bookeepingMapper.selectUserIdByPhoneNum(userPhone);
		System.out.println("id"+id);
		for (Account a : accountlist) {
			a.setUser_id(id);
		}
		bookeepingMapper.InsertAccountList(accountlist);
	}

	public List<Account> getAccountByUserPhone(String userPhone) {
		return bookeepingMapper.getAccountByUserPhone(userPhone);
	}
	
	public int deleteAccountByBillidandUserid(String user_phone,String bill_id) {
		int user_id = bookeepingMapper.selectUserIdByPhoneNum(user_phone);
		int a = bookeepingMapper.deleteAccountByid(user_id, bill_id);
		if(a>0) {
			return 1;
		}else {
			return 0;
		}
	}

}
