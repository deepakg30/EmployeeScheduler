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
import com.jobplanner.model.DayOff;
import com.jobplanner.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction  extends ActionSupport implements
ModelDriven<User>, SessionAware, ServletContextAware{
	private User user;
	private List<User> userList;
	private List<DayOff> dayOffList;
	private Map<String, Object> session;
	private ServletContext ctx;
	private Boolean error;
	private String userId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public ServletContext getCtx() {
		return ctx;
	}

	public void setCtx(ServletContext ctx) {
		this.ctx = ctx;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.ctx = arg0;
		
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
		
	}

	@Override
	public User getModel() {
		return getUser();
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
	
	public String dayOffList(){
		SessionFactory sf = (SessionFactory) ctx
				.getAttribute("SessionFactory");
		UserDAO userDAO = new UserDAOImpl(sf);
		this.user = userDAO.getUserDetails(this.userId);
		this.userList =  new ArrayList<User>();
		this.userList.add(this.user);
		return SUCCESS;
	}
	
	
	public String schedule(){
		return SUCCESS;
	}

	public List<DayOff> getDayOffList() {
		return dayOffList;
	}

	public void setDayOffList(List<DayOff> dayOffList) {
		this.dayOffList = dayOffList;
	}
}
