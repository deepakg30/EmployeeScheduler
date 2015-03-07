package com.jobplanner.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.jobplanner.model.DayOff;
import com.jobplanner.model.User;

public class UserDAOImpl implements UserDAO{
	private SessionFactory sf;
    
    public UserDAOImpl(SessionFactory sf){
        this.sf = sf;
    }


	public User getUserDetails(String email, String password) {
		Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from User where email=:id and pass=:pwd");
        query.setString("id", email); query.setString("pwd", password);
        User user = (User) query.uniqueResult();
        if(user != null){
            System.out.println("User Retrieved from DB::"+user);
        }
        tx.commit();
        session.close();
        return user;
    }


	@Override
	public User getUserDetails(String uid) {
		Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from User where uid=:id");
        query.setString("id", uid); 
        User user = (User) query.uniqueResult();
        if(user != null){
            System.out.println("User Retrieved from DB::"+user);
        }
        tx.commit();
        session.close();
        return user;
	}


	@Override
	public List<DayOff> getUserDayOff(String uid) {
		Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from DayOff where user=:id");
        query.setString("id", uid); 
        List<DayOff> dayoff = query.list();
        if(dayoff != null){
            System.out.println("dayoff Retrieved from DB::"+dayoff);
        }
        tx.commit();
        session.close();
        return dayoff;
	}
	

}
