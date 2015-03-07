package com.jobplanner.daoimpl;

import java.util.List;

import com.jobplanner.model.DayOff;
import com.jobplanner.model.User;

public interface UserDAO {
	public User getUserDetails(String email, String password) ;
	public User getUserDetails(String uid) ;
	public List<DayOff> getUserDayOff(String uid) ;
}
