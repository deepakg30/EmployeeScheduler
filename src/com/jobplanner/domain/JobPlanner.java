package com.jobplanner.domain;

import java.util.Collection;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

public class JobPlanner implements Solution<HardSoftScore>{
	private HardSoftScore score;
	@Override
	public Collection<? extends Object> getProblemFacts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HardSoftScore getScore() {
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	public void setScore(HardSoftScore score) {
		this.score = score;
		
	}

}
