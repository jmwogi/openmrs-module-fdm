package org.openmrs.module.fdm.model;

import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Person;
import org.openmrs.module.household.model.Household;
import org.openmrs.User;


public class FoodPrescription extends BaseOpenmrsData {
	private Integer id;
	private String uuid;
	private Household household;
	private FoodCombination foodCombination;
	private FoodEncounter foodEncounter;
	private String householdMemberCount;
	private Date prescriptionDate;
	private Person provider;
	private Boolean picked = Boolean.FALSE;
	
	public Integer getId() {
	    return this.id;
    }
	public void setId(Integer id) {
	    this.id = id;
    }
	
	public void setUuid(String uuid) {
	    this.uuid = uuid;
    }
	public String getUuid() {
	    return uuid;
    }
	
	public void setHousehold(Household household) {
	    this.household = household;
    }
	public Household getHousehold() {
	    return household;
    }
	public void setFoodCombination(FoodCombination foodCombination) {
	    this.foodCombination = foodCombination;
    }
	public FoodCombination getfoodCombination() {
	    return foodCombination;
    }
	public void setFoodEncounter(FoodEncounter foodEncounter) {
	    this.foodEncounter = foodEncounter;
    }
	public FoodEncounter getFoodEncounter() {
	    return foodEncounter;
    }
	public void setHouseholdMemberCount(String householdMemberCount) {
	    this.householdMemberCount = householdMemberCount;
    }
	public String getHouseholdMemberCount() {
	    return householdMemberCount;
    }
	public Person getProvider() {
		return provider;
	}
	public void setProvider(Person provider) {
		this.provider = provider;
	}
	public void setPrescriptionDate(Date prescriptionDate) {
	    this.prescriptionDate = prescriptionDate;
    }
	public Date getPrescriptionDate() {
	    return prescriptionDate;
    }
	public void setPicked(Boolean picked) {
		this.picked = picked;
	}
	public Boolean getPicked() {
		return this.picked;
	}
}
