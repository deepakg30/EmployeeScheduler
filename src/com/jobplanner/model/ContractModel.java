package com.jobplanner.model;



public class ContractModel {
	private int id;
	private String description;
	private String shiftType;
	private Boolean singleAssignmentPerDay;
	
	private int singleAssignmentPerDay_weight;
	private int maxNumAssignments;
	private int maxNumAssignments_on;
	private int maxNumAssignments_weight;
	private int minNumAssignments;
	private int minNumAssignments_on;
	private int minNumAssignments_weight;
	private int maxConsecutiveWorkingDays;
	private int minConsecutiveWorkingDays;
	private int maxConsecutiveFreeDays;
	private int minConsecutiveFreeDays;
	private int maxConsecutiveWorkingWeekends;
	private int minConsecutiveWorkingWeekends;
	private int maxWorkingWeekendsInFourWeeks;
	
	private int maxConsecutiveWorkingDays_on;
	private int minConsecutiveWorkingDays_on;
	private int maxConsecutiveFreeDays_on;
	private int minConsecutiveFreeDays_on;
	private int maxConsecutiveWorkingWeekends_on;
	private int minConsecutiveWorkingWeekends_on;
	private int maxWorkingWeekendsInFourWeeks_on;
	
	private int maxConsecutiveWorkingDays_weight;
	private int minConsecutiveWorkingDays_weight;
	private int maxConsecutiveFreeDays_weight;
	private int minConsecutiveFreeDays_weight;
	private int maxConsecutiveWorkingWeekends_weight;
	private int minConsecutiveWorkingWeekends_weight;
	private int maxWorkingWeekendsInFourWeeks_weight;
	
	private Boolean completeWeekends;
	private Boolean identicalShiftTypesDuringWeekend;
	private Boolean noNightShiftBeforeFreeWeekend;
	private Boolean alternativeSkillCategory;
	private String unwantedPatterns;
	private String weekendDefinition;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShiftType() {
		return shiftType;
	}
	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
	public Boolean getSingleAssignmentPerDay() {
		return singleAssignmentPerDay;
	}
	public void setSingleAssignmentPerDay(Boolean singleAssignmentPerDay) {
		this.singleAssignmentPerDay = singleAssignmentPerDay;
	}
	public int getSingleAssignmentPerDay_weight() {
		return singleAssignmentPerDay_weight;
	}
	public void setSingleAssignmentPerDay_weight(int singleAssignmentPerDay_weight) {
		this.singleAssignmentPerDay_weight = singleAssignmentPerDay_weight;
	}
	public int getMaxNumAssignments() {
		return maxNumAssignments;
	}
	public void setMaxNumAssignments(int maxNumAssignments) {
		this.maxNumAssignments = maxNumAssignments;
	}
	public int getMaxNumAssignments_on() {
		return maxNumAssignments_on;
	}
	public void setMaxNumAssignments_on(int maxNumAssignments_on) {
		this.maxNumAssignments_on = maxNumAssignments_on;
	}
	public int getMaxNumAssignments_weight() {
		return maxNumAssignments_weight;
	}
	public void setMaxNumAssignments_weight(int maxNumAssignments_weight) {
		this.maxNumAssignments_weight = maxNumAssignments_weight;
	}
	public int getMinNumAssignments() {
		return minNumAssignments;
	}
	public void setMinNumAssignments(int minNumAssignments) {
		this.minNumAssignments = minNumAssignments;
	}
	public int getMinNumAssignments_on() {
		return minNumAssignments_on;
	}
	public void setMinNumAssignments_on(int minNumAssignments_on) {
		this.minNumAssignments_on = minNumAssignments_on;
	}
	public int getMinNumAssignments_weight() {
		return minNumAssignments_weight;
	}
	public void setMinNumAssignments_weight(int minNumAssignments_weight) {
		this.minNumAssignments_weight = minNumAssignments_weight;
	}
	public int getMaxConsecutiveWorkingDays() {
		return maxConsecutiveWorkingDays;
	}
	public void setMaxConsecutiveWorkingDays(int maxConsecutiveWorkingDays) {
		this.maxConsecutiveWorkingDays = maxConsecutiveWorkingDays;
	}
	public int getMinConsecutiveWorkingDays() {
		return minConsecutiveWorkingDays;
	}
	public void setMinConsecutiveWorkingDays(int minConsecutiveWorkingDays) {
		this.minConsecutiveWorkingDays = minConsecutiveWorkingDays;
	}
	public int getMaxConsecutiveFreeDays() {
		return maxConsecutiveFreeDays;
	}
	public void setMaxConsecutiveFreeDays(int maxConsecutiveFreeDays) {
		this.maxConsecutiveFreeDays = maxConsecutiveFreeDays;
	}
	public int getMinConsecutiveFreeDays() {
		return minConsecutiveFreeDays;
	}
	public void setMinConsecutiveFreeDays(int minConsecutiveFreeDays) {
		this.minConsecutiveFreeDays = minConsecutiveFreeDays;
	}
	public int getMaxConsecutiveWorkingWeekends() {
		return maxConsecutiveWorkingWeekends;
	}
	public void setMaxConsecutiveWorkingWeekends(int maxConsecutiveWorkingWeekends) {
		this.maxConsecutiveWorkingWeekends = maxConsecutiveWorkingWeekends;
	}
	public int getMinConsecutiveWorkingWeekends() {
		return minConsecutiveWorkingWeekends;
	}
	public void setMinConsecutiveWorkingWeekends(int minConsecutiveWorkingWeekends) {
		this.minConsecutiveWorkingWeekends = minConsecutiveWorkingWeekends;
	}
	public int getMaxWorkingWeekendsInFourWeeks() {
		return maxWorkingWeekendsInFourWeeks;
	}
	public void setMaxWorkingWeekendsInFourWeeks(int maxWorkingWeekendsInFourWeeks) {
		this.maxWorkingWeekendsInFourWeeks = maxWorkingWeekendsInFourWeeks;
	}
	public int getMaxConsecutiveWorkingDays_on() {
		return maxConsecutiveWorkingDays_on;
	}
	public void setMaxConsecutiveWorkingDays_on(int maxConsecutiveWorkingDays_on) {
		this.maxConsecutiveWorkingDays_on = maxConsecutiveWorkingDays_on;
	}
	public int getMinConsecutiveWorkingDays_on() {
		return minConsecutiveWorkingDays_on;
	}
	public void setMinConsecutiveWorkingDays_on(int minConsecutiveWorkingDays_on) {
		this.minConsecutiveWorkingDays_on = minConsecutiveWorkingDays_on;
	}
	public int getMaxConsecutiveFreeDays_on() {
		return maxConsecutiveFreeDays_on;
	}
	public void setMaxConsecutiveFreeDays_on(int maxConsecutiveFreeDays_on) {
		this.maxConsecutiveFreeDays_on = maxConsecutiveFreeDays_on;
	}
	public int getMinConsecutiveFreeDays_on() {
		return minConsecutiveFreeDays_on;
	}
	public void setMinConsecutiveFreeDays_on(int minConsecutiveFreeDays_on) {
		this.minConsecutiveFreeDays_on = minConsecutiveFreeDays_on;
	}
	public int getMaxConsecutiveWorkingWeekends_on() {
		return maxConsecutiveWorkingWeekends_on;
	}
	public void setMaxConsecutiveWorkingWeekends_on(
			int maxConsecutiveWorkingWeekends_on) {
		this.maxConsecutiveWorkingWeekends_on = maxConsecutiveWorkingWeekends_on;
	}
	public int getMinConsecutiveWorkingWeekends_on() {
		return minConsecutiveWorkingWeekends_on;
	}
	public void setMinConsecutiveWorkingWeekends_on(
			int minConsecutiveWorkingWeekends_on) {
		this.minConsecutiveWorkingWeekends_on = minConsecutiveWorkingWeekends_on;
	}
	public int getMaxWorkingWeekendsInFourWeeks_on() {
		return maxWorkingWeekendsInFourWeeks_on;
	}
	public void setMaxWorkingWeekendsInFourWeeks_on(
			int maxWorkingWeekendsInFourWeeks_on) {
		this.maxWorkingWeekendsInFourWeeks_on = maxWorkingWeekendsInFourWeeks_on;
	}
	public int getMaxConsecutiveWorkingDays_weight() {
		return maxConsecutiveWorkingDays_weight;
	}
	public void setMaxConsecutiveWorkingDays_weight(
			int maxConsecutiveWorkingDays_weight) {
		this.maxConsecutiveWorkingDays_weight = maxConsecutiveWorkingDays_weight;
	}
	public int getMinConsecutiveWorkingDays_weight() {
		return minConsecutiveWorkingDays_weight;
	}
	public void setMinConsecutiveWorkingDays_weight(
			int minConsecutiveWorkingDays_weight) {
		this.minConsecutiveWorkingDays_weight = minConsecutiveWorkingDays_weight;
	}
	public int getMaxConsecutiveFreeDays_weight() {
		return maxConsecutiveFreeDays_weight;
	}
	public void setMaxConsecutiveFreeDays_weight(int maxConsecutiveFreeDays_weight) {
		this.maxConsecutiveFreeDays_weight = maxConsecutiveFreeDays_weight;
	}
	public int getMinConsecutiveFreeDays_weight() {
		return minConsecutiveFreeDays_weight;
	}
	public void setMinConsecutiveFreeDays_weight(int minConsecutiveFreeDays_weight) {
		this.minConsecutiveFreeDays_weight = minConsecutiveFreeDays_weight;
	}
	public int getMaxConsecutiveWorkingWeekends_weight() {
		return maxConsecutiveWorkingWeekends_weight;
	}
	public void setMaxConsecutiveWorkingWeekends_weight(
			int maxConsecutiveWorkingWeekends_weight) {
		this.maxConsecutiveWorkingWeekends_weight = maxConsecutiveWorkingWeekends_weight;
	}
	public int getMinConsecutiveWorkingWeekends_weight() {
		return minConsecutiveWorkingWeekends_weight;
	}
	public void setMinConsecutiveWorkingWeekends_weight(
			int minConsecutiveWorkingWeekends_weight) {
		this.minConsecutiveWorkingWeekends_weight = minConsecutiveWorkingWeekends_weight;
	}
	public int getMaxWorkingWeekendsInFourWeeks_weight() {
		return maxWorkingWeekendsInFourWeeks_weight;
	}
	public void setMaxWorkingWeekendsInFourWeeks_weight(
			int maxWorkingWeekendsInFourWeeks_weight) {
		this.maxWorkingWeekendsInFourWeeks_weight = maxWorkingWeekendsInFourWeeks_weight;
	}
	public Boolean getCompleteWeekends() {
		return completeWeekends;
	}
	public void setCompleteWeekends(Boolean completeWeekends) {
		this.completeWeekends = completeWeekends;
	}
	public Boolean getIdenticalShiftTypesDuringWeekend() {
		return identicalShiftTypesDuringWeekend;
	}
	public void setIdenticalShiftTypesDuringWeekend(
			Boolean identicalShiftTypesDuringWeekend) {
		this.identicalShiftTypesDuringWeekend = identicalShiftTypesDuringWeekend;
	}
	public Boolean getNoNightShiftBeforeFreeWeekend() {
		return noNightShiftBeforeFreeWeekend;
	}
	public void setNoNightShiftBeforeFreeWeekend(
			Boolean noNightShiftBeforeFreeWeekend) {
		this.noNightShiftBeforeFreeWeekend = noNightShiftBeforeFreeWeekend;
	}
	public Boolean getAlternativeSkillCategory() {
		return alternativeSkillCategory;
	}
	public void setAlternativeSkillCategory(Boolean alternativeSkillCategory) {
		this.alternativeSkillCategory = alternativeSkillCategory;
	}
	public String getUnwantedPatterns() {
		return unwantedPatterns;
	}
	public void setUnwantedPatterns(String unwantedPatterns) {
		this.unwantedPatterns = unwantedPatterns;
	}
	public String getWeekendDefinition() {
		return weekendDefinition;
	}
	public void setWeekendDefinition(String weekendDefinition) {
		this.weekendDefinition = weekendDefinition;
	}
	
	
	

	
}
