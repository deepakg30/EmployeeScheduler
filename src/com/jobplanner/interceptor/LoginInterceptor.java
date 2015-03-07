package com.jobplanner.interceptor;

import java.util.Map;

import com.jobplanner.model.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor  extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> sessionAttributes = invocation.getInvocationContext().getSession();
		if(sessionAttributes == null || sessionAttributes.get("user") == null){
			return "login";
		}
		else{
			
			if(!((User)sessionAttributes.get("user")).equals(null)){
				return invocation.invoke();
			}
			else{
				return "login";
			}
		}
		
	}

}
