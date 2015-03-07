package com.jobplanner.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

import com.jobplanner.daoimpl.UserDAO;
import com.jobplanner.daoimpl.UserDAOImpl;
import com.jobplanner.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserLoginAction extends ActionSupport implements
		ModelDriven<User>, SessionAware, ServletContextAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private List<User> userList;
	private Map<String, Object> session;
	private ServletContext ctx;
	private Boolean error;
	private String userId;

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
}

	@Override
	public User getModel() {

		return getUser();
	}

	@Override
	public String execute() throws Exception {
		System.out.println(user.getEmail());
		System.out.println(user.getPass());
		if (user.getEmail().equals("")) {
			addActionError("Please Enter user id.");
			error = true;
			 return ERROR;
		} else if (user.getPass().equals("")) {
			addActionError("Please Enter password.");
			error = true;
			 return ERROR;
		} 
		else {
			SessionFactory sf = (SessionFactory) ctx
					.getAttribute("SessionFactory");
			UserDAO userDAO = new UserDAOImpl(sf);
			User userDB = userDAO.getUserDetails(user.getEmail(),
					user.getPass());

			if (userDB == null) {
				return ERROR;
			} else {
				user.setEmail(userDB.getEmail());
				user.setUid(userDB.getUid());
				user.setFirst(userDB.getFirst());
				user.setLast(userDB.getLast());
				 this.session.put("user", user);
				 if (userDB.getActype() == 1){
				
				System.out.println("");
				return "ADMIN";
				 }
				 else if(userDB.getActype()==0){
					
					 System.out.println("");
					 return "USER";
				 }
				 return ERROR;
			}
		}
		

	}
	public String userData(){
		SessionFactory sf = (SessionFactory) ctx
				.getAttribute("SessionFactory");
		UserDAO userDAO = new UserDAOImpl(sf);
		this.user = userDAO.getUserDetails(this.userId);
		this.userList =  new ArrayList<User>();
		this.userList.add(this.user);
		return SUCCESS;
		
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.ctx = arg0;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
