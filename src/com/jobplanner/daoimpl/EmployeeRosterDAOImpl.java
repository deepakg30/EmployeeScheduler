package com.jobplanner.daoimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.jobplanner.model.DayOfWeek;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.jobplanner.domain.Employee;
import com.jobplanner.domain.EmployeeRoster;
import com.jobplanner.domain.EmployeeRosterParametrization;
import com.jobplanner.domain.ShiftAssignment;
import com.jobplanner.domain.ShiftTypeSkillRequirement;
import com.jobplanner.domain.Skill;
import com.jobplanner.domain.SkillProficiency;
import com.jobplanner.model.Constraint;
import com.jobplanner.model.ContractModel;
import com.jobplanner.model.Cover;
import com.jobplanner.model.DayOff;
import com.jobplanner.model.DayOn;
import com.jobplanner.model.PatternEntries;
import com.jobplanner.model.Patterns;
import com.jobplanner.model.Role;
import com.jobplanner.model.Shift;
import com.jobplanner.model.ShiftDate;
import com.jobplanner.model.ShiftOff;
import com.jobplanner.model.ShiftOn;
import com.jobplanner.model.ShiftType;
import com.jobplanner.model.ShiftTypes;
import com.jobplanner.model.Status;
import com.jobplanner.model.User;
import com.jobplanner.pattern.FreeBefore2DaysWithAWorkDayPattern;
import com.jobplanner.pattern.Pattern;
import com.jobplanner.pattern.ShiftType2DaysPattern;
import com.jobplanner.pattern.ShiftType3DaysPattern;
import com.jobplanner.pattern.WorkBeforeFreeSequencePattern;
import com.jobplanner.request.DayOffRequest;
import com.jobplanner.request.DayOnRequest;
import com.jobplanner.request.ShiftOffRequest;
import com.jobplanner.request.ShiftOnRequest;
import com.jobplanner.contract.BooleanContractLine;
import com.jobplanner.contract.Contract;
import com.jobplanner.contract.ContractLine;
import com.jobplanner.contract.ContractLineType;
import com.jobplanner.contract.MinMaxContractLine;
import com.jobplanner.contract.PatternContractLine;
import com.jobplanner.model.WeekendDefinition;

public class EmployeeRosterDAOImpl implements EmployeeRosterDAO{
private SessionFactory sf;
private EmployeeRoster 	employeeRoster;
protected Map<String, ShiftDate> shiftDateMap;
protected Map<String, Pattern> patternMap;
protected Map<String, ShiftType> shiftTypeMap;
protected Map<String, Contract> contractMap;
protected Map<String, Employee> employeeMap;
protected Map<String, Skill> skillMap;
protected String department;
protected Map<List<Object>, List<Shift>> dayOfWeekAndShiftTypeToShiftListMap;
protected Map<List<String>, Shift> dateAndShiftTypeToShiftMap;
protected String startdate;
protected String enddate;
	public EmployeeRosterDAOImpl(SessionFactory sf,String dept,String sd, String ed) {
		super();
		this.sf = sf;
		this.employeeRoster = new EmployeeRoster();
		this.department = dept;
		this.startdate = sd;
		this.enddate = ed;
	}
	@Override
	public EmployeeRoster getEmployeeRoster() {
		this.employeeRoster.setId(0l);
		generateShiftDateList(this.startdate,this.enddate);
		generateNurseRosterInfo();
        readSkillList();
        readShiftTypeList();
        generateShiftList();
        readPatternList();
        readContractList();
        readEmployeeList();
        readRequiredEmployeeSizes();
        readDayOffRequestList();
        readDayOnRequestList();
        readShiftOffRequestList();
        readShiftOnRequestList();
        createShiftAssignmentList();

    
		return this.employeeRoster;
	}
	
	 private void generateShiftList(){
         List<ShiftType> shiftTypeList = this.employeeRoster.getShiftTypeList();
         int shiftListSize = shiftDateMap.size() * shiftTypeList.size();
         List<Shift> shiftList = new ArrayList<Shift>(shiftListSize);
         dateAndShiftTypeToShiftMap = new HashMap<List<String>, Shift>(shiftListSize);
         dayOfWeekAndShiftTypeToShiftListMap = new HashMap<List<Object>, List<Shift>>(7 * shiftTypeList.size());
         long id = 0L;
         int index = 0;
         for (ShiftDate shiftDate : this.employeeRoster.getShiftDateList()) {
             for (ShiftType shiftType : shiftTypeList) {
                 Shift shift = new Shift();
                 shift.setId(id);
                 shift.setShiftDate(shiftDate);
                 shiftDate.getShiftList().add(shift);
                 shift.setShiftType(shiftType);
                 shift.setIndex(index);
                 shift.setRequiredEmployeeSize(0); // Filled in later
                 shiftList.add(shift);
                 dateAndShiftTypeToShiftMap.put(Arrays.asList(shiftDate.getDateString(), shiftType.getCode()), shift);
                 addShiftToDayOfWeekAndShiftTypeToShiftListMap(shiftDate, shiftType, shift);
                 id++;
                 index++;
             }
         }
         this.employeeRoster.setShiftList(shiftList);
     }
	 
	 private void addShiftToDayOfWeekAndShiftTypeToShiftListMap(ShiftDate shiftDate, ShiftType shiftType,
             Shift shift) {
         List<Object> key = Arrays.<Object>asList(shiftDate.getDayOfWeek(), shiftType);
         List<Shift> dayOfWeekAndShiftTypeToShiftList = dayOfWeekAndShiftTypeToShiftListMap.get(key);
         if (dayOfWeekAndShiftTypeToShiftList == null) {
             dayOfWeekAndShiftTypeToShiftList = new ArrayList<Shift>((shiftDateMap.size() + 6) / 7);
             dayOfWeekAndShiftTypeToShiftListMap.put(key, dayOfWeekAndShiftTypeToShiftList);
         }
         dayOfWeekAndShiftTypeToShiftList.add(shift);
     }
	   private void generateShiftDateList(String startDateElement, String endDateElement) {
           // Mimic JSR-310 LocalDate
           TimeZone LOCAL_TIMEZONE = TimeZone.getTimeZone("GMT");
           Calendar calendar = Calendar.getInstance();
           calendar.setTimeZone(LOCAL_TIMEZONE);
           calendar.clear();
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           dateFormat.setCalendar(calendar);
           Date startDate;
           try {
               startDate = dateFormat.parse(startDateElement);
           } catch (ParseException e) {
               throw new IllegalArgumentException("Invalid startDate (" + startDateElement + ").", e);
           }
           calendar.setTime(startDate);
           int startDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
           int startYear = calendar.get(Calendar.YEAR);
           Date endDate;
           try {
               endDate = dateFormat.parse(endDateElement);
           } catch (ParseException e) {
               throw new IllegalArgumentException("Invalid endDate (" + endDateElement+ ").", e);
           }
           calendar.setTime(endDate);
           int endDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
           int endYear = calendar.get(Calendar.YEAR);
           int maxDayIndex = endDayOfYear - startDayOfYear;
           if (startYear > endYear) {
               throw new IllegalStateException("The startYear (" + startYear
                       + " must be before endYear (" + endYear + ").");
           }
           if (startYear < endYear) {
               int tmpYear = startYear;
               calendar.setTime(startDate);
               while (tmpYear < endYear) {
                   maxDayIndex += calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
                   calendar.add(Calendar.YEAR, 1);
                   tmpYear++;
               }
           }
           int shiftDateSize = maxDayIndex + 1;
           List<ShiftDate> shiftDateList = new ArrayList<ShiftDate>(shiftDateSize);
           shiftDateMap = new HashMap<String, ShiftDate>(shiftDateSize);
           long id = 0L;
           int dayIndex = 0;
           calendar.setTime(startDate);
           for (int i = 0; i < shiftDateSize; i++) {
               ShiftDate shiftDate = new ShiftDate();
               shiftDate.setId(id);
               shiftDate.setDayIndex(dayIndex);
               String dateString = dateFormat.format(calendar.getTime());
               shiftDate.setDateString(dateString);
               shiftDate.setDayOfWeek(DayOfWeek.valueOfCalendar(calendar.get(Calendar.DAY_OF_WEEK)));
               shiftDate.setShiftList(new ArrayList<Shift>());
               shiftDateList.add(shiftDate);
               shiftDateMap.put(dateString, shiftDate);
               id++;
               dayIndex++;
               calendar.add(Calendar.DAY_OF_YEAR, 1);
           }
           this.employeeRoster.setShiftDateList(shiftDateList);
       }
	   private void generateNurseRosterInfo() {
           List<ShiftDate> shiftDateList = this.employeeRoster.getShiftDateList();
           EmployeeRosterParametrization employeeRosterParametrization = new EmployeeRosterParametrization();
           employeeRosterParametrization.setFirstShiftDate(shiftDateList.get(0));
           employeeRosterParametrization.setLastShiftDate(shiftDateList.get(shiftDateList.size() - 1));
           employeeRosterParametrization.setPlanningWindowStart(shiftDateList.get(0));
           this.employeeRoster.setEmployeeRosterParametrization(employeeRosterParametrization);
       }
	   
	   
	   
	   
	   private void readPatternList() {
           List<Pattern> patternList;
           Session session = sf.openSession();
		    Transaction tx1 = session.beginTransaction();
		    Query query = session.createQuery("from Patterns");
		    List<Patterns> patterns = query.list();
		    tx1.commit();
		    session.close();
           if (patterns.size() == 0) {
               patternList = Collections.emptyList();
           } else {
          
               patternList = new ArrayList<Pattern>(patterns.size());
               patternMap = new HashMap<String, Pattern>(patterns.size());
               long id = 0L;
               long patternEntryId = 0L;
               for (Patterns element : patterns) {
                  
                   String code = Integer.toString(element.getId());
                   int weight = element.getWeight();

                   List<PatternEntries> patternEntry;
                   Session session1 = sf.openSession();
       		    Transaction tx = session1.beginTransaction();
       		    Query query1 = session1.createQuery("from PatternEntries where id= :id");
       		  query1.setInteger("id", element.getId());
       		    patternEntry = query1.list();
       		    tx.commit();
       		    session1.close();
                   
                   if (patternEntry.size() < 2) {
                       throw new IllegalArgumentException("The size of PatternEntries ("
                               + patternEntry.size() + ") of pattern (" + code + ") should be at least 2.");
                   }
                   Pattern pattern;
                   if (patternEntry.get(0).getShiftType().equals("None")) {
                       pattern = new FreeBefore2DaysWithAWorkDayPattern();
                       if (patternEntry.size() != 3) {
                           throw new IllegalStateException("boe");
                       }
                   } else if (patternEntry.get(0).getShiftType().equals("None")) {
                       pattern = new WorkBeforeFreeSequencePattern();
                       // TODO support this too (not needed for competition)
                       throw new UnsupportedOperationException("The pattern (" + code + ") is not supported."
                               + " None of the test data exhibits such a pattern.");
                   } else {
                       switch (patternEntry.size()) {
                           case 2:
                               pattern = new ShiftType2DaysPattern();
                               break;
                           case 3:
                               pattern = new ShiftType3DaysPattern();
                               break;
                           default:
                               throw new IllegalArgumentException("A size of PatternEntries ("
                                       + patternEntry.size() + ") of pattern (" + code
                                       + ") above 3 is not supported.");
                       }
                   }
                 //  pattern.setId(id);
                   pattern.setCode(code);
                   pattern.setWeight(weight);
                   int patternEntryIndex = 0;
                   DayOfWeek firstDayOfweek = null;
                   for (PatternEntries patternEntryElement : patternEntry) {
                       
                       String shiftTypeElement = patternEntryElement.getShiftType();
                       boolean shiftTypeIsNone;
                       ShiftType shiftType;
                       if (shiftTypeElement.equals("Any")) {
                           shiftTypeIsNone = false;
                           shiftType = null;
                       } else if (shiftTypeElement.equals("None")) {
                           shiftTypeIsNone = true;
                           shiftType = null;
                       } else {
                           shiftTypeIsNone = false;
                           shiftType = shiftTypeMap.get(shiftTypeElement);
                           if (shiftType == null) {
                               throw new IllegalArgumentException("The shiftType (" + shiftTypeElement
                                       + ") of pattern (" + pattern.getCode() + ") does not exist.");
                           }
                       }
                       String dayElement = patternEntryElement.getDay();
                       DayOfWeek dayOfWeek;
                       if (dayElement.equals("Any")) {
                           dayOfWeek = null;
                       } else {
                           dayOfWeek = DayOfWeek.valueOfCode(dayElement);
                           if (dayOfWeek == null) {
                               throw new IllegalArgumentException("The dayOfWeek (" + dayElement
                                       + ") of pattern (" + pattern.getCode() + ") does not exist.");
                           }
                       }
                       if (patternEntryIndex == 0) {
                           firstDayOfweek = dayOfWeek;
                       } else {
                           if (firstDayOfweek != null) {
                               if (firstDayOfweek.getDistanceToNext(dayOfWeek) != patternEntryIndex) {
                                   throw new IllegalArgumentException("On patternEntryIndex (" + patternEntryIndex
                                           + ") of pattern (" + pattern.getCode()
                                           + ") the dayOfWeek (" + dayOfWeek
                                           + ") is not valid with previous entries.");
                               }
                           } else {
                               if (dayOfWeek != null) {
                                   throw new IllegalArgumentException("On patternEntryIndex (" + patternEntryIndex
                                           + ") of pattern (" + pattern.getCode()
                                           + ") the dayOfWeek should be (Any), in line with previous entries.");
                               }
                           }
                       }
                       if (pattern instanceof FreeBefore2DaysWithAWorkDayPattern) {
                           FreeBefore2DaysWithAWorkDayPattern castedPattern = (FreeBefore2DaysWithAWorkDayPattern) pattern;
                           if (patternEntryIndex == 0) {
                               if (dayOfWeek == null) {
                                   // TODO Support an any dayOfWeek too (not needed for competition)
                                   throw new UnsupportedOperationException("On patternEntryIndex (" + patternEntryIndex
                                           + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                           + ") the dayOfWeek should not be (Any)."
                                           + "\n None of the test data exhibits such a pattern.");
                               }
                               castedPattern.setFreeDayOfWeek(dayOfWeek);
                           }
                           if (patternEntryIndex == 1) {
                               if (shiftType != null) {
                                   // TODO Support a specific shiftType too (not needed for competition)
                                   throw new UnsupportedOperationException("On patternEntryIndex (" + patternEntryIndex
                                           + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                           + ") the shiftType should be (Any)."
                                           + "\n None of the test data exhibits such a pattern.");
                               }
                               // castedPattern.setWorkShiftType(shiftType);
                               // castedPattern.setWorkDayLength(patternEntryElementList.size() - 1);
                           }
                           // if (patternEntryIndex > 1 && shiftType != castedPattern.getWorkShiftType()) {
                           //     throw new IllegalArgumentException("On patternEntryIndex (" + patternEntryIndex
                           //             + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                           //             + ") the shiftType (" + shiftType + ") should be ("
                           //             + castedPattern.getWorkShiftType() + ").");
                           // }
                           if (patternEntryIndex != 0 && shiftTypeIsNone) {
                               throw new IllegalArgumentException("On patternEntryIndex (" + patternEntryIndex
                                       + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                       + ") the shiftType can not be (None).");
                           }
                       } else if (pattern instanceof WorkBeforeFreeSequencePattern) {
                           WorkBeforeFreeSequencePattern castedPattern = (WorkBeforeFreeSequencePattern) pattern;
                           if (patternEntryIndex == 0) {
                               castedPattern.setWorkDayOfWeek(dayOfWeek);
                               castedPattern.setWorkShiftType(shiftType);
                               castedPattern.setFreeDayLength(patternEntry.size() - 1);
                           }
                           if (patternEntryIndex != 0 && !shiftTypeIsNone) {
                               throw new IllegalArgumentException("On patternEntryIndex (" + patternEntryIndex
                                       + ") of WorkBeforeFreeSequence pattern (" + pattern.getCode()
                                       + ") the shiftType should be (None).");
                           }
                       } else if (pattern instanceof ShiftType2DaysPattern) {
                           ShiftType2DaysPattern castedPattern = (ShiftType2DaysPattern) pattern;
                           if (patternEntryIndex == 0) {
                               if (dayOfWeek != null) {
                                   // TODO Support a specific dayOfWeek too (not needed for competition)
                                   throw new UnsupportedOperationException("On patternEntryIndex (" + patternEntryIndex
                                           + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                           + ") the dayOfWeek should be (Any)."
                                           + "\n None of the test data exhibits such a pattern.");
                               }
                               // castedPattern.setStartDayOfWeek(dayOfWeek);
                           }
                           if (shiftType == null) {
                               // TODO Support any shiftType too (not needed for competition)
                               throw new UnsupportedOperationException("On patternEntryIndex (" + patternEntryIndex
                                       + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                       + ") the shiftType should not be (Any)."
                                       + "\n None of the test data exhibits such a pattern.");
                           }
                           switch (patternEntryIndex) {
                               case 0:
                                   castedPattern.setDayIndex0ShiftType(shiftType);
                                   break;
                               case 1:
                                   castedPattern.setDayIndex1ShiftType(shiftType);
                                   break;
                               default:
                                   throw new IllegalArgumentException("The patternEntryIndex ("
                                           + patternEntryIndex + ") is not supported.");
                           }
                       } else if (pattern instanceof ShiftType3DaysPattern) {
                           ShiftType3DaysPattern castedPattern = (ShiftType3DaysPattern) pattern;
                           if (patternEntryIndex == 0) {
                               if (dayOfWeek != null) {
                                   // TODO Support a specific dayOfWeek too (not needed for competition)
                                   throw new UnsupportedOperationException("On patternEntryIndex (" + patternEntryIndex
                                           + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                           + ") the dayOfWeek should be (Any)."
                                           + "\n None of the test data exhibits such a pattern.");
                               }
                               // castedPattern.setStartDayOfWeek(dayOfWeek);
                           }
                           if (shiftType == null) {
                               // TODO Support any shiftType too
                               throw new UnsupportedOperationException("On patternEntryIndex (" + patternEntryIndex
                                       + ") of FreeBeforeWorkSequence pattern (" + pattern.getCode()
                                       + ") the shiftType should not be (Any)."
                                       + "\n None of the test data exhibits such a pattern.");
                           }
                           switch (patternEntryIndex) {
                               case 0:
                                   castedPattern.setDayIndex0ShiftType(shiftType);
                                   break;
                               case 1:
                                   castedPattern.setDayIndex1ShiftType(shiftType);
                                   break;
                               case 2:
                                   castedPattern.setDayIndex2ShiftType(shiftType);
                                   break;
                               default:
                                   throw new IllegalArgumentException("The patternEntryIndex ("
                                           + patternEntryIndex + ") is not supported.");
                           }
                       } else {
                           throw new IllegalStateException("Unsupported patternClass (" + pattern.getClass() + ").");
                       }
                       patternEntryIndex++;
                   }
                   patternList.add(pattern);
                   if (patternMap.containsKey(pattern.getCode())) {
                       throw new IllegalArgumentException("There are 2 patterns with the same code ("
                               + pattern.getCode() + ").");
                   }
                   patternMap.put(pattern.getCode(), pattern);
                   id++;
               }
           }
           this.employeeRoster.setPatternList(patternList);
       }

	   private void readContractList(){
           int contractLineTypeListSize = ContractLineType.values().length;
           Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from ContractModel");
	        List<ContractModel> contractElementList = query.list();
		    tx.commit();
		    session.close();

           List<Contract> contractList = new ArrayList<Contract>(contractElementList.size());
           contractMap = new HashMap<String, Contract>(contractElementList.size());
           long id = 0L;
           List<ContractLine> contractLineList = new ArrayList<ContractLine>(
                   contractElementList.size() * contractLineTypeListSize);
           long contractLineId = 0L;
           List<PatternContractLine> patternContractLineList = new ArrayList<PatternContractLine>(
                   contractElementList.size() * 3);
           long patternContractLineId = 0L;
           for (ContractModel element : contractElementList) {
              
               Contract contract = new Contract();
               contract.setId(id);
               contract.setCode(Integer.toString(element.getId()));
               contract.setDescription(element.getDescription());

               List<ContractLine> contractLineListOfContract = new ArrayList<ContractLine>(contractLineTypeListSize);
               contractLineId = readBooleanContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getSingleAssignmentPerDay(),element.getSingleAssignmentPerDay_weight(),
                       ContractLineType.SINGLE_ASSIGNMENT_PER_DAY);
               contractLineId = readMinMaxContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getMinNumAssignments(),element.getMinNumAssignments_on(),element.getMinNumAssignments_weight(),
                       element.getMaxNumAssignments(),element.getMaxNumAssignments_on(),element.getMaxNumAssignments_weight(),
                       ContractLineType.TOTAL_ASSIGNMENTS);
               contractLineId = readMinMaxContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getMinConsecutiveWorkingDays(),element.getMinConsecutiveWorkingDays_on(),element.getMinConsecutiveWorkingDays_weight(),
                       element.getMaxConsecutiveWorkingDays(),element.getMaxConsecutiveWorkingDays_on(),element.getMaxConsecutiveWorkingDays_weight(),
                       ContractLineType.CONSECUTIVE_WORKING_DAYS);
               contractLineId = readMinMaxContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getMinConsecutiveFreeDays(),element.getMinConsecutiveFreeDays_on(),element.getMinConsecutiveFreeDays_weight(),
                       element.getMaxConsecutiveFreeDays(),element.getMaxConsecutiveFreeDays_on(),element.getMaxConsecutiveFreeDays_weight(),
                       ContractLineType.CONSECUTIVE_FREE_DAYS);
               contractLineId = readMinMaxContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getMinConsecutiveWorkingWeekends(),element.getMinConsecutiveWorkingWeekends_on(),element.getMinConsecutiveWorkingWeekends_weight(),
                       element.getMaxConsecutiveWorkingWeekends(),element.getMaxConsecutiveWorkingWeekends_on(),element.getMaxConsecutiveWorkingWeekends_weight(),
                       ContractLineType.CONSECUTIVE_WORKING_WEEKENDS);
               contractLineId = readMinMaxContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, 0,0,0,
                       element.getMaxConsecutiveWorkingWeekends(),element.getMaxConsecutiveWorkingWeekends_on(),element.getMaxConsecutiveWorkingWeekends_weight(),
                       ContractLineType.TOTAL_WORKING_WEEKENDS_IN_FOUR_WEEKS);
               WeekendDefinition weekendDefinition = WeekendDefinition.valueOfCode(element.getWeekendDefinition());
               contract.setWeekendDefinition(weekendDefinition);
               contractLineId = readBooleanContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getCompleteWeekends(),1,
                       ContractLineType.COMPLETE_WEEKENDS);
               contractLineId = readBooleanContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getIdenticalShiftTypesDuringWeekend(),1,
                       ContractLineType.IDENTICAL_SHIFT_TYPES_DURING_WEEKEND);
               contractLineId = readBooleanContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getNoNightShiftBeforeFreeWeekend(),1,
                       ContractLineType.NO_NIGHT_SHIFT_BEFORE_FREE_WEEKEND);
               contractLineId = readBooleanContractLine(contract, contractLineList, contractLineListOfContract,
                       contractLineId, element.getAlternativeSkillCategory(),1,
                       ContractLineType.ALTERNATIVE_SKILL_CATEGORY);
               contract.setContractLineList(contractLineListOfContract);

               String unwantedpattern = element.getUnwantedPatterns();
               String [] unwantedPatternElementList = unwantedpattern.split(",");
               for (String patternElement : unwantedPatternElementList) {
                  
                   Pattern pattern = patternMap.get(patternElement);
                   if (pattern == null) {
                       throw new IllegalArgumentException("The pattern (" + patternElement
                               + ") of contract (" + contract.getCode() + ") does not exist.");
                   }
                   PatternContractLine patternContractLine = new PatternContractLine();
                   patternContractLine.setId(patternContractLineId);
                   patternContractLine.setContract(contract);
                   patternContractLine.setPattern(pattern);
                   patternContractLineList.add(patternContractLine);
                   patternContractLineId++;
               }

               contractList.add(contract);
               System.out.println(" Teja Contract Added: "+contract);
               if (contractMap.containsKey(contract.getCode())) {
                   throw new IllegalArgumentException("There are 2 contracts with the same code ("
                           + contract.getCode() + ").");
               }
               contractMap.put(contract.getCode(), contract);
               id++;
           }
          this.employeeRoster.setContractList(contractList);
          this.employeeRoster.setContractLineList(contractLineList);
          this.employeeRoster.setPatternContractLineList(patternContractLineList);
       }
	   
	   private long readBooleanContractLine(Contract contract, List<ContractLine> contractLineList,
               List<ContractLine> contractLineListOfContract, long contractLineId, Boolean element,int w,
               ContractLineType contractLineType) {
           boolean enabled = element;
           int weight;
           if (enabled) {
               weight = w;
               if (weight < 0) {
                   throw new IllegalArgumentException("The weight (" + weight
                           + ") of contract (" + contract.getCode() + ") and contractLineType (" + contractLineType
                           + ") should be 0 or at least 1.");
               } else if (weight == 0) {
                   // If the weight is zero, the constraint should not be considered.
                   enabled = false;
                
               }
           } else {
               weight = 0;
           }
           if (enabled) {
               BooleanContractLine contractLine = new BooleanContractLine();
              // contractLine.setId(contractLineId);
               contractLine.setContract(contract);
               contractLine.setContractLineType(contractLineType);
               contractLine.setEnabled(enabled);
               contractLine.setWeight(weight);
               contractLineList.add(contractLine);
               contractLineListOfContract.add(contractLine);
               contractLineId++;
           }
           return contractLineId;
       }
	   
	   
	   
	   private long readMinMaxContractLine(Contract contract, List<ContractLine> contractLineList,
               List<ContractLine> contractLineListOfContract, long contractLineId,
               int minElement, int maxElement,
               int i, int j, int k, int l, ContractLineType contractLineType){
           boolean minimumEnabled = i == 0 ? false : true;
           int minimumWeight;
           if (minimumEnabled) {
               minimumWeight = j;
               if (minimumWeight < 0) {
                   throw new IllegalArgumentException("The minimumWeight (" + minimumWeight
                           + ") of contract (" + contract.getCode() + ") and contractLineType (" + contractLineType
                           + ") should be 0 or at least 1.");
               } else if (minimumWeight == 0) {
                   // If the weight is zero, the constraint should not be considered.
                   minimumEnabled = false;
                   System.out.println("In contract ({}), the contractLineType ({}) minimum is enabled with weight 0."+","+
                           contract.getCode()+","+contractLineType);
               }
           } else {
               minimumWeight = 0;
           }
           boolean maximumEnabled = k == 0 ? false : true;
           int maximumWeight;
           if (maximumEnabled) {
               maximumWeight = l;
               if (maximumWeight < 0) {
                   throw new IllegalArgumentException("The maximumWeight (" + maximumWeight
                           + ") of contract (" + contract.getCode() + ") and contractLineType (" + contractLineType
                           + ") should be 0 or at least 1.");
               } else if (maximumWeight == 0) {
                   // If the weight is zero, the constraint should not be considered.
                   maximumEnabled = false;
                   System.out.println("In contract ({}), the contractLineType ({}) maximum is enabled with weight 0."+","+
                           contract.getCode()+","+ contractLineType);
               }
           } else {
               maximumWeight = 0;
           }
           if (minimumEnabled || maximumEnabled) {
               MinMaxContractLine contractLine = new MinMaxContractLine();
              // contractLine.setId(contractLineId);
               contractLine.setContract(contract);
               contractLine.setContractLineType(contractLineType);
               contractLine.setMinimumEnabled(minimumEnabled);
               if (minimumEnabled) {
                   int minimumValue = minElement;
                   if (minimumValue < 1) {
                       throw new IllegalArgumentException("The minimumValue (" + minimumValue
                               + ") of contract (" + contract.getCode() + ") and contractLineType ("
                               + contractLineType + ") should be at least 1.");
                   }
                   contractLine.setMinimumValue(minimumValue);
                   contractLine.setMinimumWeight(minimumWeight);
               }
               contractLine.setMaximumEnabled(maximumEnabled);
               if (maximumEnabled) {
                   int maximumValue = maxElement;
                   if (maximumValue < 0) {
                       throw new IllegalArgumentException("The maximumValue (" + maximumValue
                               + ") of contract (" + contract.getCode() + ") and contractLineType ("
                               + contractLineType + ") should be at least 0.");
                   }
                   contractLine.setMaximumValue(maximumValue);
                   contractLine.setMaximumWeight(maximumWeight);
               }
               contractLineList.add(contractLine);
               contractLineListOfContract.add(contractLine);
               contractLineId++;
           }
           return contractLineId;
       }
	   
	   
	   private void readEmployeeList() {
		   Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("select user from User user, Status status where user.uid = status.uid and status.division=:division");
		    query.setString("division", this.department);
		    List<User> employeeElementList  = query.list();
		    tx.commit();
		    session.close();
		  
           List<Employee> employeeList = new ArrayList<Employee>(employeeElementList.size());
           employeeMap = new HashMap<String, Employee>(employeeElementList.size());
           long id = 0L;
           List<SkillProficiency> skillProficiencyList
                   = new ArrayList<SkillProficiency>(employeeElementList.size() * 2);
           long skillProficiencyId = 0L;
           for (User element : employeeElementList) {
              
               Employee employee = new Employee();
               employee.setId(id);
               employee.setCode(element.getUid());
               employee.setName(element.getFirst());
               System.out.println("element"+element.getContract());
              
               Contract contract = contractMap.get(Integer.toString(element.getContract()));
               if (contract == null) {
                   throw new IllegalArgumentException("The contract (" + element.getContract()
                           + ") of employee (" + employee.getCode() + ") does not exist.");
               }
               employee.setContract(contract);
               int estimatedRequestSize = (shiftDateMap.size() / employeeElementList.size()) + 1;
               employee.setDayOffRequestMap(new HashMap<ShiftDate, DayOffRequest>(estimatedRequestSize));
               employee.setDayOnRequestMap(new HashMap<ShiftDate, DayOnRequest>(estimatedRequestSize));
               employee.setShiftOffRequestMap(new HashMap<Shift, ShiftOffRequest>(estimatedRequestSize));
               employee.setShiftOnRequestMap(new HashMap<Shift, ShiftOnRequest>(estimatedRequestSize));
               
               Session session1 = sf.openSession();
   		    Transaction tx1 = session1.beginTransaction();
   		    Query query1 = session1.createQuery("from Status where uid =:uid");
   		    query1.setString("uid", employee.getCode());
   		    List<Status> skillElementList  = query1.list();
   		    tx1.commit();
   		    session1.close();
   		  for (Status skillElement : skillElementList) {
                      
                       Skill skill = skillMap.get(skillElement.getRole());
                       if (skill == null) {
                           throw new IllegalArgumentException("The skill (" + skillElement.getRole()
                                   + ") of employee (" + employee.getCode() + ") does not exist.");
                       }
                       SkillProficiency skillProficiency = new SkillProficiency();
                       skillProficiency.setId(skillProficiencyId);
                       skillProficiency.setEmployee(employee);
                       skillProficiency.setSkill(skill);
                       skillProficiencyList.add(skillProficiency);
                       skillProficiencyId++;
                   }
               

               employeeList.add(employee);
               if (employeeMap.containsKey(employee.getCode())) {
                   throw new IllegalArgumentException("There are 2 employees with the same code ("
                           + employee.getCode() + ").");
               }
               employeeMap.put(employee.getCode(), employee);
               id++;
           }
           this.employeeRoster.setEmployeeList(employeeList);
           this.employeeRoster.setSkillProficiencyList(skillProficiencyList);
       }
	   
	   private void readRequiredEmployeeSizes() {
		   Session session1 = sf.openSession();
  		    Transaction tx1 = session1.beginTransaction();
  		    Query query1 = session1.createQuery("from Cover where division=:division");
  		    query1.setString("division", this.department);
  		   
           List<Cover> coverRequirementElementList =  query1.list();
           
           for (Cover element : coverRequirementElementList) {
              
                   String dayOfWeekElement = element.getDayofweek();
                   DayOfWeek dayOfWeek = DayOfWeek.valueOfCode(dayOfWeekElement);
                   if (dayOfWeek == null) {
                       throw new IllegalArgumentException("The dayOfWeek (" + dayOfWeekElement
                               + ") of an entity DayOfWeekCover does not exist.");
                   }

                  
                   String[] shiftlist = element.getShift().split(",");
                   String[] preferlist = element.getPreffered().split(",");
                   for (int i =0;i<shiftlist.length;i++) {
                      
                       ShiftType shiftType = shiftTypeMap.get(shiftlist[i]);
                       if (shiftType == null) {
                           if (shiftlist[i].equals("Any")) {
                               throw new IllegalStateException("The shiftType Any is not supported on DayOfWeekCover.");
                           } else if (shiftlist[i].equals("None")) {
                               throw new IllegalStateException("The shiftType None is not supported on DayOfWeekCover.");
                           } else {
                               throw new IllegalArgumentException("The shiftType (" + shiftlist[i]
                                       + ") of an entity DayOfWeekCover does not exist.");
                           }
                       }
                       List<Object> key = Arrays.<Object>asList(dayOfWeek, shiftType);
                       List<Shift> shiftList = dayOfWeekAndShiftTypeToShiftListMap.get(key);
                       if (shiftList == null) {
                           throw new IllegalArgumentException("The dayOfWeek (" + dayOfWeekElement
                                   + ") with the shiftType (" + shiftlist[i]
                                   + ") of an entity DayOfWeekCover does not have any shifts.");
                       }
                       int requiredEmployeeSize = Integer.parseInt(preferlist[i]);
                       for (Shift shift : shiftList) {
                           shift.setRequiredEmployeeSize(shift.getRequiredEmployeeSize() + requiredEmployeeSize);
                       }
                   }
             
           }
       }

	   
	   
	   private void readSkillList()  {
		   Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from Role");
	        List<Role> skillsElement = query.list();
		    tx.commit();
		    session.close();
           List<Skill> skillList;
           if (skillsElement == null) {
               skillList = Collections.emptyList();
           } else {
               
               skillList = new ArrayList<Skill>(skillsElement.size());
               skillMap = new HashMap<String, Skill>(skillsElement.size());
               long id = 0L;
               for (Role element : skillsElement) {
                  
                   Skill skill = new Skill();
                   skill.setId(id);
                   skill.setCode(element.getName());
                   skillList.add(skill);
                   if (skillMap.containsKey(skill.getCode())) {
                       throw new IllegalArgumentException("There are 2 skills with the same code ("
                               + skill.getCode() + ").");
                   }
                   skillMap.put(skill.getCode(), skill);
                   id++;
               }
           }
           this.employeeRoster.setSkillList(skillList);
       }
	   
	   private void readShiftTypeList()  {
		   Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from ShiftTypes");
	        List<ShiftTypes> shiftTypeElementList = query.list();
		    tx.commit();
		    session.close();
         
           List<ShiftType> shiftTypeList = new ArrayList<ShiftType>(shiftTypeElementList.size());
           shiftTypeMap = new HashMap<String, ShiftType>(shiftTypeElementList.size());
           long id = 0L;
           int index = 0;
           List<ShiftTypeSkillRequirement> shiftTypeSkillRequirementList
                   = new ArrayList<ShiftTypeSkillRequirement>(shiftTypeElementList.size() * 2);
           long shiftTypeSkillRequirementId = 0L;
           for (ShiftTypes element : shiftTypeElementList) {
              
               ShiftType shiftType = new ShiftType();
               shiftType.setId(id);
               shiftType.setCode(element.getUid());
               shiftType.setIndex(index);
               String startTimeString = element.getStarttime();
               shiftType.setStartTimeString(startTimeString);
               String endTimeString = element.getEndtime();
               shiftType.setEndTimeString(endTimeString);
               shiftType.setNight(startTimeString.compareTo(endTimeString) > 0);
               shiftType.setDescription(element.getDescription());

            
                   String[] skillElementList = element.getRole().split(",");
                   for (String skillElement : skillElementList) {
                       
                       ShiftTypeSkillRequirement shiftTypeSkillRequirement = new ShiftTypeSkillRequirement();
                       shiftTypeSkillRequirement.setId(shiftTypeSkillRequirementId);
                       shiftTypeSkillRequirement.setShiftType(shiftType);
                       Skill skill = skillMap.get(skillElement);
                       if (skill == null) {
                           throw new IllegalArgumentException("The skill (" + skillElement
                                   + ") of shiftType (" + shiftType.getCode() + ") does not exist.");
                       }
                       shiftTypeSkillRequirement.setSkill(skill);
                       shiftTypeSkillRequirementList.add(shiftTypeSkillRequirement);
                       shiftTypeSkillRequirementId++;
                   }
               

               shiftTypeList.add(shiftType);
               if (shiftTypeMap.containsKey(shiftType.getCode())) {
                   throw new IllegalArgumentException("There are 2 shiftTypes with the same code ("
                           + shiftType.getCode() + ").");
               }
               shiftTypeMap.put(shiftType.getCode(), shiftType);
               id++;
               index++;
           }
           this.employeeRoster.setShiftTypeList(shiftTypeList);
           this.employeeRoster.setShiftTypeSkillRequirementList(shiftTypeSkillRequirementList);
       }

	   
	   private void readDayOffRequestList() {
           List<DayOffRequest> dayOffRequestList;
           Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from DayOff");
	        List<DayOff> dayOffRequestsElement = query.list();
		    tx.commit();
		    session.close();
           if (dayOffRequestsElement == null) {
               dayOffRequestList = Collections.emptyList();
           } else {
               dayOffRequestList = new ArrayList<DayOffRequest>(dayOffRequestsElement.size());
               long id = 0L;
               for (DayOff element : dayOffRequestsElement) {
                   
                   DayOffRequest dayOffRequest = new DayOffRequest();
                   dayOffRequest.setId(id);

                 
                   Employee employee = employeeMap.get(element.getUser());
                   if (employee == null) {
                       throw new IllegalArgumentException("The shiftDate (" +element.getUser()
                               + ") of dayOffRequest (" + dayOffRequest + ") does not exist.");
                   }
                   dayOffRequest.setEmployee(employee);
                   ShiftDate shiftDate = shiftDateMap.get(element.getDate());
                   if (shiftDate == null) {
                       throw new IllegalArgumentException("The date (" + element.getDate()
                               + ") of dayOffRequest (" + dayOffRequest + ") does not exist.");
                   }
                   dayOffRequest.setShiftDate(shiftDate);

                   dayOffRequest.setWeight(1);

                   dayOffRequestList.add(dayOffRequest);
                   employee.getDayOffRequestMap().put(shiftDate, dayOffRequest);
                   id++;
               }
           }
           this.employeeRoster.setDayOffRequestList(dayOffRequestList);
       }
	   
	   private void readDayOnRequestList()  {
		   Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from DayOn");
		    List<DayOn> dayOnRequestList = query.list();
		    tx.commit();
		    session.close();
		    List<DayOnRequest> dayOnRequestListAll;
          
           if (dayOnRequestList == null) {
        	   dayOnRequestListAll = Collections.emptyList();
           } else {
        	   long id = 0L;
        	   dayOnRequestListAll =  new ArrayList<DayOnRequest>(dayOnRequestList.size());
               for (DayOn element : dayOnRequestList) {
                   
                   DayOnRequest dayOnRequest = new DayOnRequest();
                   dayOnRequest.setId(id);

                   
                   Employee employee = employeeMap.get(element.getUser());
                   if (employee == null) {
                       throw new IllegalArgumentException("The shiftDate (" + element.getUser()
                               + ") of dayOnRequest (" + dayOnRequest + ") does not exist.");
                   }
                   dayOnRequest.setEmployee(employee);

               
                   ShiftDate shiftDate = shiftDateMap.get(element.getDate());
                   if (shiftDate == null) {
                       throw new IllegalArgumentException("The date (" + element.getDate()
                               + ") of dayOnRequest (" + dayOnRequest + ") does not exist.");
                   }
                   dayOnRequest.setShiftDate(shiftDate);

                   dayOnRequest.setWeight(1);

                   dayOnRequestListAll.add(dayOnRequest);
                   employee.getDayOnRequestMap().put(shiftDate, dayOnRequest);
                   id++;
               }
           }
           this.employeeRoster.setDayOnRequestList(dayOnRequestListAll);
       }

	   
	   private void readShiftOffRequestList() {
           List<ShiftOffRequest> shiftOffRequestList;
           Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from ShiftOff");
		    List<ShiftOff> shiftOffRequestsElement = query.list();
		    tx.commit();
		    session.close();
           if (shiftOffRequestsElement == null) {
               shiftOffRequestList = Collections.emptyList();
           } else {
               
               shiftOffRequestList = new ArrayList<ShiftOffRequest>(shiftOffRequestsElement.size());
               long id = 0L;
               for (ShiftOff element : shiftOffRequestsElement) {
                 
                   ShiftOffRequest shiftOffRequest = new ShiftOffRequest();
                   shiftOffRequest.setId(id);

                  
                   Employee employee = employeeMap.get(element.getUser());
                   if (employee == null) {
                       throw new IllegalArgumentException("The shift (" + element.getUser()
                               + ") of shiftOffRequest (" + shiftOffRequest + ") does not exist.");
                   }
                   shiftOffRequest.setEmployee(employee);

                
                   Shift shift = dateAndShiftTypeToShiftMap.get(Arrays.asList(element.getDate(), element.getShift()));
                   if (shift == null) {
                       throw new IllegalArgumentException("The date (" + element.getDate()
                               + ") or the shiftType (" + element.getShift()
                               + ") of shiftOffRequest (" + shiftOffRequest + ") does not exist.");
                   }
                   shiftOffRequest.setShift(shift);

                   shiftOffRequest.setWeight(1);

                   shiftOffRequestList.add(shiftOffRequest);
                   employee.getShiftOffRequestMap().put(shift, shiftOffRequest);
                   id++;
               }
           }
           this.employeeRoster.setShiftOffRequestList(shiftOffRequestList);
       }
	   

       private void readShiftOnRequestList() {
           List<ShiftOnRequest> shiftOnRequestList;
           Session session = sf.openSession();
		    Transaction tx = session.beginTransaction();
		    Query query = session.createQuery("from ShiftOn");
		    List<ShiftOn> shiftOnRequestsElement = query.list();
		    tx.commit();
		    session.close();
           if (shiftOnRequestsElement == null) {
               shiftOnRequestList = Collections.emptyList();
           } else {
              
               shiftOnRequestList = new ArrayList<ShiftOnRequest>(shiftOnRequestsElement.size());
               long id = 0L;
               for (ShiftOn element : shiftOnRequestsElement) {
                   
                   ShiftOnRequest shiftOnRequest = new ShiftOnRequest();
                   shiftOnRequest.setId(id);

                  
                   Employee employee = employeeMap.get(element.getUser());
                   if (employee == null) {
                       throw new IllegalArgumentException("The shift (" + element.getUser()
                               + ") of shiftOnRequest (" + shiftOnRequest + ") does not exist.");
                   }
                   shiftOnRequest.setEmployee(employee);

                
                   Shift shift = dateAndShiftTypeToShiftMap.get(Arrays.asList(element.getDate(), element.getShift()));
                   if (shift == null) {
                       throw new IllegalArgumentException("The date (" + element.getDate()
                               + ") or the shiftType (" + element.getShift()
                               + ") of shiftOnRequest (" + shiftOnRequest + ") does not exist.");
                   }
                   shiftOnRequest.setShift(shift);

                   shiftOnRequest.setWeight(1);

                   shiftOnRequestList.add(shiftOnRequest);
                   employee.getShiftOnRequestMap().put(shift, shiftOnRequest);
                   id++;
               }
           }
           this.employeeRoster.setShiftOnRequestList(shiftOnRequestList);
       }

       
       private void createShiftAssignmentList() {
           List<Shift> shiftList = this.employeeRoster.getShiftList();
           List<ShiftAssignment> shiftAssignmentList = new ArrayList<ShiftAssignment>(shiftList.size());
           long id = 0L;
           for (Shift shift : shiftList) {
               for (int i = 0; i < shift.getRequiredEmployeeSize(); i++) {
                   ShiftAssignment shiftAssignment = new ShiftAssignment();
                   shiftAssignment.setId(id);
                   id++;
                   shiftAssignment.setShift(shift);
                   shiftAssignment.setIndexInShift(i);
                   // Notice that we leave the PlanningVariable properties on null
                   shiftAssignmentList.add(shiftAssignment);
               }
           }
           this.employeeRoster.setShiftAssignmentList(shiftAssignmentList);
       }

}
