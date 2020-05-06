package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.domain.Account;
import com.domain.Reply;
import com.domain.Transferarticle;
import com.domain.Transferreply;
import com.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.BookeepingService;

@Controller
public class BookeepingController extends HttpServlet {
	@Resource
	private BookeepingService bookeepingService;

	@RequestMapping("/RegisterUser")
	public void RegisterUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			User user = new User();
			user.setUser_phone(request.getParameter("user_phone"));
			user.setUser_password(getMD5(request.getParameter("user_password")));
			user.setUser_nikename(request.getParameter("user_nikename"));
			boolean flag = bookeepingService.RegisterUser(user);
			if (flag) {
				PrintWriter out = response.getWriter();
				out.write("1");
				response.flushBuffer();
				out.close();
			} else {
				PrintWriter out = response.getWriter();
				out.write("0");
				response.flushBuffer();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/LoginUser")
	public void LoginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			System.out.println("here");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String userphone = request.getParameter("user_phone").trim();
			String password = getMD5(request.getParameter("user_password").trim());
			System.out.println(userphone);
			
			List<User> userlist = bookeepingService.getUserByPhoneNum(userphone);
			if(userlist.size()==0) {
				PrintWriter out = response.getWriter();
				out.write("0");
				response.flushBuffer();
				out.close();
			}else {
				List<User> list = bookeepingService.getUserByPhoneAndpassword(userphone, password);
				if(list.size()==0) {
					PrintWriter out = response.getWriter();
					out.write("1");
					response.flushBuffer();
					out.close();
				}else {
					Gson gson = new Gson();
					String json = gson.toJson(list);
					PrintWriter out = response.getWriter();
					System.out.println("LOGIN:"+json);
					out.write(json);
					response.flushBuffer();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.write("0");
			response.flushBuffer();
			out.close();
		}
	}

	@RequestMapping("/UpdateUserNikename")
	public void UpdateUserNikename(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String user_nikename = request.getParameter("user_nikename");
			String user_phone = request.getParameter("user_phone");
			if (bookeepingService.UpdateUserNikename(user_phone, user_nikename)) {
				PrintWriter out = response.getWriter();
				out.write("1");
				response.flushBuffer();
				out.close();
			} else {
				PrintWriter out = response.getWriter();
				out.write("0");
				response.flushBuffer();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/ChangeUserImage")
	public void UpdateUserImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "img", required = false) List<MultipartFile> listFile) throws IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String user_phone = request.getParameter("user_phone");
			String img = "";

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);

			if (isMultipart) {
				String filePath;
				// 获取Tomcat服务器所在的路径
				String tomcat_path = System.getProperty("catalina.home");

				System.out.println(tomcat_path);
				// 获取Tomcat服务器所在路径的最后一个文件目录
				String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\") + 1, tomcat_path.length());
				System.out.println(bin_path);
				// 若最后一个文件目录为bin目录，则服务器为手动启动
				if (("bin").equals(bin_path)) {// 手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
					// 获取保存上传图片的文件路径
					filePath = tomcat_path.substring(0, System.getProperty("user.dir").lastIndexOf("\\")) + "\\webapps"
							+ "\\pic_file\\";
				} else {// 服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
					filePath = tomcat_path + "\\webapps" + "\\pic_file\\";
				}
				System.out.println(filePath);

				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}

				if (listFile.size() != 0) {
					img = makeFileName(listFile.get(0).getOriginalFilename().toString().trim());
					System.out.println("更改头像_img:" + img);
					listFile.get(0).transferTo(new File(filePath, img.substring(img.lastIndexOf("/") + 1)));
					
					if(bookeepingService.UpdateUserImage(user_phone, img)) {
						PrintWriter out = response.getWriter();
						out.write(img);
						response.flushBuffer();
						out.close();
					}else {
						PrintWriter out = response.getWriter();
						out.write("o");
						response.flushBuffer();
						out.close();
					}
				}else {
					PrintWriter out = response.getWriter();
					out.write("o");
					response.flushBuffer();
					out.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/AddArticle")
	public void AddArticle(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "img", required = false) List<MultipartFile> listFile) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user_phone = request.getParameter("user_phone");
		String content = request.getParameter("content");
		String time = request.getParameter("time");
		String imgs = "";

		System.out.println("HERE");
		System.out.println("user_phone:" + user_phone);

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {

			String filePath;

			// 获取Tomcat服务器所在的路径
			String tomcat_path = System.getProperty("catalina.home");

			System.out.println(tomcat_path);
			// 获取Tomcat服务器所在路径的最后一个文件目录
			String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\") + 1, tomcat_path.length());
			System.out.println(bin_path);
			// 若最后一个文件目录为bin目录，则服务器为手动启动
			if (("bin").equals(bin_path)) {// 手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
				// 获取保存上传图片的文件路径
				filePath = tomcat_path.substring(0, System.getProperty("user.dir").lastIndexOf("\\")) + "\\webapps"
						+ "\\pic_file\\";
			} else {// 服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
				filePath = tomcat_path + "\\webapps" + "\\pic_file\\";
			}
			System.out.println(filePath);

			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			if (listFile.size() != 0) {
				for (int i = 0; i < listFile.size(); i++) {
					if (!listFile.get(i).isEmpty()) {
						String img = makeFileName(listFile.get(i).getOriginalFilename().toString().trim());
						imgs += img + "##";
						System.out.println("img:" + img);
						System.out.println("=======" + listFile.get(i).getOriginalFilename().toString().trim());

						listFile.get(i).transferTo(new File(filePath, img.substring(img.lastIndexOf("/") + 1)));
					}
				}
			}
			int a = bookeepingService.AddArticle(user_phone, content, time, imgs);
			System.out.println("SERVICE:" + a);
			if (a != 0) {
				PrintWriter out = response.getWriter();
				out.write("1");
				response.flushBuffer();
				out.close();
			} else {
				PrintWriter out = response.getWriter();
				out.write("0");
				response.flushBuffer();
				out.close();
			}
		}
	}

	private String makeFileName(String filename) { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}

	@RequestMapping("/getAllArticle")
	public void AddArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		List<Transferarticle> list = bookeepingService.getArticleByTime();
		if (list.size() != 0) {
			Gson g = new Gson();
			String json = g.toJson(list);
			System.out.println("LIST:" + json);

			PrintWriter out = response.getWriter();
			out.write(json);
			response.flushBuffer();
			out.close();
		} else {
			System.out.println("失败！");
			PrintWriter out = response.getWriter();
			out.write("0");
			response.flushBuffer();
			out.close();
		}
	}

	@RequestMapping("/AddReply")
	public void AddReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String article_id = request.getParameter("article_id");
		String user_phone = request.getParameter("user_phone");
		String reply_to_user_id = request.getParameter("reply_to_user_id");
		String reply_content = request.getParameter("reply_content");

		int reply_user_id = 0;

		if (user_phone != null) {
			reply_user_id = bookeepingService.getUserIdByPhoneNum(user_phone);
		}

		Reply reply = new Reply(Integer.parseInt(article_id), reply_user_id, Integer.parseInt(reply_to_user_id),
				reply_content);
		bookeepingService.InsertReply(reply);

		List<Transferreply> replylist = bookeepingService.getReplyByid(article_id);
		if (replylist.size() != 0) {
			Gson g = new Gson();
			String json = g.toJson(replylist);
			System.out.println("LIST:" + json);

			PrintWriter out = response.getWriter();
			out.write(json);
			response.flushBuffer();
			out.close();
		} else {
			System.out.println("没有评论或网络错误");
			PrintWriter out = response.getWriter();
			out.write("0");
			response.flushBuffer();
			out.close();
		}

	}

	@RequestMapping("/AddAccount")
	public void AddAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user_phone = request.getParameter("user_phone");
		String bill_count = request.getParameter("bill_count");
		String bill_inexType = request.getParameter("bill_inexType");
		String bill_detailType = request.getParameter("bill_detailType");
		String bill_imgRes = request.getParameter("bill_imgRes");
		String bill_time = request.getParameter("bill_time");
		String bill_note = request.getParameter("bill_note");
		System.out.println("UserPhone" + user_phone);
		
		Account account = new Account(Float.parseFloat(bill_count),bill_inexType,bill_detailType,bill_imgRes,bill_time,bill_note);
		int a = bookeepingService.AddAccount(account,user_phone);
		System.out.println("A2:"+a);
		if(a>0) {
			PrintWriter out = response.getWriter();
			out.write(a+"");
			response.flushBuffer();
			out.close();
		}else {
			PrintWriter out = response.getWriter();
			out.write("0");
			response.flushBuffer();
			out.close();
		}
	}
	
	@RequestMapping("/getAccount")
	public void getAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user_phone = request.getParameter("user_phone");
		
		List<Account> accountList = bookeepingService.getAccountByUserPhone(user_phone);
		if (accountList.size() != 0) {
			Gson g = new Gson();
			String json = g.toJson(accountList);
			System.out.println("LIST:" + json);
			
			PrintWriter out = response.getWriter();
			out.write(json);
			response.flushBuffer();
			out.close();
		} else {
			PrintWriter out = response.getWriter();
			out.write("0");
			response.flushBuffer();
			out.close();
		}
	}
	
	@RequestMapping("/syncAccount")
	public void syncAccount(@RequestBody String param,HttpServletResponse response) throws IOException {
		System.out.println("param:"+param);
		
		try {
			JSONObject jsonObject = new JSONObject(param);
			String user_phone = jsonObject.getString("user_phone");
			String account = jsonObject.getString("account");
			
			Gson gson = new Gson();
	        if (account!=null&&!account.equals("")) {
	            java.lang.reflect.Type type = new TypeToken<List<Account>>() {
	            }.getType();
	            List<Account> list = gson.fromJson(account, type);
	            for(int i =0;i<list.size();i++) {
	            	System.out.println("11:"+list.get(i).getBill_count());
	            }
	            
	            bookeepingService.AddAccountList(list, user_phone);
	            
	            List<Account> accountList = bookeepingService.getAccountByUserPhone(user_phone);
	            if (accountList.size() != 0) {
	    			Gson g = new Gson();
	    			String json = g.toJson(accountList);
	    			System.out.println("LIST account:" + json);
	    			
	    			PrintWriter out = response.getWriter();
	    			out.write(json);
	    			response.flushBuffer();
	    			out.close();
	    		} else {
	    			PrintWriter out = response.getWriter();
	    			out.write("0");
	    			response.flushBuffer();
	    			out.close();
	    		}
	        }else {
	        	PrintWriter out = response.getWriter();
    			out.write("1");
    			response.flushBuffer();
    			out.close();
	        }
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteAccount")
	public void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user_phone = request.getParameter("user_phone");
		String bill_id = request.getParameter("bill_id");
		int a = bookeepingService.deleteAccountByBillidandUserid(user_phone, bill_id);
		PrintWriter out = response.getWriter();
		out.write(a+"");
		response.flushBuffer();
		out.close();
	}
	
	@RequestMapping("/getReply")
	public void getAllArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String article_id = request.getParameter("article_id");
		System.out.println("article_id" + article_id);
		List<Transferreply> replylist = bookeepingService.getReplyByid(article_id);
		if (replylist.size() != 0) {
			Gson g = new Gson();
			String json = g.toJson(replylist);
			System.out.println("LIST:" + json);

			PrintWriter out = response.getWriter();
			out.write(json);
			response.flushBuffer();
			out.close();
		} else {
			System.out.println("失败！");
			PrintWriter out = response.getWriter();
			out.write("0");
			response.flushBuffer();
			out.close();
		}

	}
	

	public static String getMD5(String message) {
		String md5str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte input[] = message.getBytes();
			byte buff[] = md.digest(input);
			md5str = bytesToHex(buff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str;
	}

	public static String bytesToHex(byte bytes[]) {
		StringBuffer md5str = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int digital = bytes[i];
			if (digital < 0)
				digital += 256;
			if (digital < 16)
				md5str.append("0");
			md5str.append(Integer.toHexString(digital));
		}

		return md5str.toString().toUpperCase();
	}
}
