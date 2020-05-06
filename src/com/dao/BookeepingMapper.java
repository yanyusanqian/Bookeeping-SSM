package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.domain.Account;
import com.domain.Article;
import com.domain.Reply;
import com.domain.Transferarticle;
import com.domain.Transferreply;
import com.domain.User;

public interface BookeepingMapper {
//	public abstract List<User> selectUserByPhoneNum(@Param("user_phone")String s);
	public abstract int selectUserIdByPhoneNum(@Param("user_phone")String s);
	public abstract void InsertUser(User user);
	public abstract List<User> matchUserByPhoneAndpassword(@Param("user_phone")String userphone, @Param("user_password")String password);
	public abstract int UpdateNikename(@Param("user_phone")String userphone,@Param("user_nikename")String s);
	public abstract int UpdateUserImage(@Param("user_phone")String userphone,@Param("user_image")String s);
	public abstract int InsertArticle(Article article);
	public abstract List<User> matchUserIdByPhone(@Param("user_phone")String phone);
	public abstract List<Transferarticle> getArticleByTime();
	public abstract int InsertReply(Reply reply);
	public abstract List<Transferreply> getReplyById(@Param("article_id")String article_id);
	public abstract int InsertAccount(Account account);
	public abstract void InsertAccountList(List<Account> list);
	public abstract List<Account> getAccountByUserPhone(@Param("user_phone")String userPhone);
	public abstract int deleteAccountByid(@Param("user_id")int user_id,@Param("bill_id")String bill_id);
	public abstract String selectUserImageById(@Param("user_id")int user_id);
}
