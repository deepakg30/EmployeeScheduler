package com.jobplanner.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import com.jobplanner.daoimpl.EmployeeRosterDAO;
import com.jobplanner.daoimpl.EmployeeRosterDAOImpl;
import com.jobplanner.domain.EmployeeRoster;
import com.jobplanner.domain.ShiftAssignment;
import com.jobplanner.model.Schedule;
import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport implements
 SessionAware, ServletContextAware {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private ServletContext ctx;
	 public static final String SOLVER_CONFIG
     = "/emplyeeRosteringSolverConfig.xml";
	 private String division;
	 private String startDate;
	 private String endDate;
	 private List<Schedule> schedule ;
	 

@Override
public void setServletContext(ServletContext arg0) {
	// TODO Auto-generated method stub
	this.ctx = arg0;
	
}

@Override
public void setSession(Map<String, Object> arg0) {
	// TODO Auto-generated method stub
	this.session = arg0;
}
public String execute() throws Exception {
	
	 SolverFactory solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
	 Solver s = solverFactory.buildSolver();
	 SessionFactory sf = (SessionFactory) ctx
				.getAttribute("SessionFactory");
	String[] split1 = this.startDate.split("/");
	String m = split1[0];
	String d =split1[1];
	String y = split1[2];
	String[] split2 = this.endDate.split("/");
	String m1 = split2[0];
	String d1 =split2[1];
	String y1 = split2[2];
	String start = y+"-"+m+"-"+d;
	String end = y1+"-"+m1+"-"+d1;
	
	
	EmployeeRosterDAO  employDao = new EmployeeRosterDAOImpl(sf,this.division, start, end);
	
	EmployeeRoster emp = employDao.getEmployeeRoster();
	s.solve(emp);
	EmployeeRoster emp2 = (EmployeeRoster) s.getBestSolution();
	System.out.println("Final: "+emp2.getShiftAssignmentList().toString());
	HashMap<String,Schedule> index = new HashMap<String,Schedule>();
	for(ShiftAssignment sa :emp2.getShiftAssignmentList()){
		if(index.containsKey(sa.getShift().getShiftDate().getDateString())){
			if(sa.getShift().getShiftType().getCode().equals("E")){
				index.get(sa.getShift().getShiftDate().getDateString()).getE().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("L")){
				index.get(sa.getShift().getShiftDate().getDateString()).getL().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("D")){
				index.get(sa.getShift().getShiftDate().getDateString()).getD().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("N")){
				index.get(sa.getShift().getShiftDate().getDateString()).getN().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("DH")){
				index.get(sa.getShift().getShiftDate().getDateString()).getDh().add(sa.getEmployee().getName());
			}
		}
		else{
			Schedule sch = new Schedule();
			sch.setDate(sa.getShift().getShiftDate().getDateString());
			if(sa.getShift().getShiftType().getCode().equals("E")){
				sch.getE().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("L")){
				sch.getL().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("D")){
				sch.getD().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("N")){
				sch.getN().add(sa.getEmployee().getName());
			}else if(sa.getShift().getShiftType().getCode().equals("DH")){
				sch.getDh().add(sa.getEmployee().getName());
			}
			index.put(sa.getShift().getShiftDate().getDateString(), sch);
			
		}
	}
	this.schedule = new ArrayList<Schedule>();
	Iterator it = index.entrySet().iterator();
	
	 while (it.hasNext()) {
		 Map.Entry pairs = (Map.Entry)it.next();
		 this.schedule.add((Schedule) pairs.getValue());
		 it.remove();
		 
	 }
	 System.out.println("Strat:"+ start);
		System.out.println("End:"+ start);
		System.out.println("List:"+this.schedule);
	return SUCCESS;
	
}

public String getDivision() {
	return division;
}

public void setDivision(String division) {
	this.division = division;
}

public String getStartDate() {
	return startDate;
}

public void setStartDate(String startDate) {
	this.startDate = startDate;
}

public String getEndDate() {
	return endDate;
}

public void setEndDate(String endDate) {
	this.endDate = endDate;
}

public List<Schedule> getSchedule() {
	return schedule;
}

public void setSchedule(List<Schedule> schedule) {
	this.schedule = schedule;
}

}