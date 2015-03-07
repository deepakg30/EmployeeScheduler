package com.jobplanner.domain;
import java.io.Serializable;
import java.util.Map;

import com.jobplanner.contract.Contract;
import com.jobplanner.model.Shift;
import com.jobplanner.model.ShiftDate;
import com.jobplanner.request.DayOffRequest;
import com.jobplanner.request.DayOnRequest;
import com.jobplanner.request.ShiftOffRequest;
import com.jobplanner.request.ShiftOnRequest;



public class Employee implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String code;
    private String name;
    private Contract contract;

   private Map<ShiftDate, DayOffRequest> dayOffRequestMap;
    private Map<ShiftDate, DayOnRequest> dayOnRequestMap;
   private Map<Shift, ShiftOffRequest> shiftOffRequestMap;
   private Map<Shift, ShiftOnRequest> shiftOnRequestMap;

    

    public String getName() {
        return name;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String id) {
		this.code = id;
	}

	public void setName(String name) {
        this.name = name;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }



    public Map<ShiftDate, DayOffRequest> getDayOffRequestMap() {
        return dayOffRequestMap;
    }

    public void setDayOffRequestMap(Map<ShiftDate, DayOffRequest> dayOffRequestMap) {
        this.dayOffRequestMap = dayOffRequestMap;
    }

    public Map<ShiftDate, DayOnRequest> getDayOnRequestMap() {
        return dayOnRequestMap;
    }

    public void setDayOnRequestMap(Map<ShiftDate, DayOnRequest> dayOnRequestMap) {
        this.dayOnRequestMap = dayOnRequestMap;
    }

    public Map<Shift, ShiftOffRequest> getShiftOffRequestMap() {
        return shiftOffRequestMap;
    }

    public void setShiftOffRequestMap(Map<Shift, ShiftOffRequest> shiftOffRequestMap) {
        this.shiftOffRequestMap = shiftOffRequestMap;
    }

    public Map<Shift, ShiftOnRequest> getShiftOnRequestMap() {
        return shiftOnRequestMap;
    }

    public void setShiftOnRequestMap(Map<Shift, ShiftOnRequest> shiftOnRequestMap) {
        this.shiftOnRequestMap = shiftOnRequestMap;
    }

    public String getLabel() {
        return "Employee " + name;
    }

    @Override
    public String toString() {
        return code + "(" + name + ")";
    }
    public int getWeekendLength() {
        return getContract().getWeekendLength();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
