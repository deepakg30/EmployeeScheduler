/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jobplanner.solver.drools;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.jobplanner.contract.Contract;
import com.jobplanner.domain.Employee;
import com.jobplanner.model.ContractModel;
import com.jobplanner.model.DayOfWeek;
import com.jobplanner.model.ShiftDate;
import com.jobplanner.model.WeekendDefinition;

public class EmployeeConsecutiveAssignmentEnd implements Comparable<EmployeeConsecutiveAssignmentEnd>, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Employee employee;
    private ShiftDate shiftDate;

    public EmployeeConsecutiveAssignmentEnd(Employee employee, ShiftDate shiftDate) {
        this.employee = employee;
        this.shiftDate = shiftDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ShiftDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(ShiftDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof EmployeeConsecutiveAssignmentEnd) {
            EmployeeConsecutiveAssignmentEnd other = (EmployeeConsecutiveAssignmentEnd) o;
            return new EqualsBuilder()
                    .append(employee, other.employee)
                    .append(shiftDate, other.shiftDate)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(employee)
                .append(shiftDate)
                .toHashCode();
    }

    public int compareTo(EmployeeConsecutiveAssignmentEnd other) {
        return new CompareToBuilder()
                .append(employee, other.employee)
                .append(shiftDate, other.shiftDate)
                .toComparison();
    }

    @Override
    public String toString() {
        return employee + " ... - " + shiftDate;
    }

    public Contract getContract() {
        return employee.getContract();
    }

    public int getShiftDateDayIndex() {
        return shiftDate.getDayIndex();
    }

    public boolean isWeekendAndNotLastDayOfWeekend() {
        WeekendDefinition weekendDefinition = employee.getContract().getWeekendDefinition();
        DayOfWeek dayOfWeek = shiftDate.getDayOfWeek();
        return weekendDefinition.isWeekend(dayOfWeek) && weekendDefinition.getLastDayOfWeekend() != dayOfWeek;
    }

    public int getDistanceToLastDayOfWeekend() {
        WeekendDefinition weekendDefinition = employee.getContract().getWeekendDefinition();
        DayOfWeek dayOfWeek = shiftDate.getDayOfWeek();
        return dayOfWeek.getDistanceToNext(weekendDefinition.getLastDayOfWeekend());
    }

}
