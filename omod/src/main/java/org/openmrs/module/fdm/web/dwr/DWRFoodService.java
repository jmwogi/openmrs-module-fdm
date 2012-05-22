package org.openmrs.module.fdm.web.dwr;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.fdm.model.*;
import org.openmrs.module.fdm.service.FdmService;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.service.HouseholdService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DWRFoodService {
	
	private static final Log log = LogFactory.getLog(DWRFoodService.class);
	
	public boolean addEditFoodSource(String [] passedArr){
		FdmService service = Context.getService(FdmService.class);
		
		if(passedArr[0].equals("1")){
			FoodSource fs = new FoodSource(passedArr[2], passedArr[3], passedArr[4]);
			service.saveFoodSource(fs);
		}else if(passedArr[0].equals("2")){
			FoodSource fs = service.getFoodSource(Integer.parseInt(passedArr[1]));
			fs.setCode(passedArr[2]);
			fs.setName(passedArr[3]);
			fs.setDescription(passedArr[4]);
			service.saveFoodSource(fs);
		}else if(passedArr[0].equals("3")){
			FoodSource fs = service.getFoodSource(Integer.parseInt(passedArr[1]));
			return service.purgeFoodSource(fs,passedArr[4]);
		}
		return false;
	}
	
	public boolean addEditFoodProduct(String [] passedArr){
		FdmService service = Context.getService(FdmService.class);
		
		if(passedArr[0].equals("1")){
			FoodProduct fp = new FoodProduct(passedArr[2]);
			service.saveFoodProduct(fp);
		}else if(passedArr[0].equals("2")){
			FoodProduct fp = service.getFoodProduct(Integer.parseInt(passedArr[1]));
			fp.setName(passedArr[3]);
			service.saveFoodProduct(fp);
		}else if(passedArr[0].equals("3")){
			FoodProduct fp = service.getFoodProduct(Integer.parseInt(passedArr[1]));
			return service.purgeFoodProduct(fp,passedArr[3]);
		}
		return false;
	}
	/**
	 * Get Households by personID
	 * @Param personID
	 */
	@SuppressWarnings("null")
    public List<HouseholdMembership> getHouseholdsByPersonID(String personID){
		List<HouseholdMembership> hm = new ArrayList<HouseholdMembership>();
		
		Person p = Context.getPersonService().getPerson(Integer.parseInt(personID));
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdMembership> householdMem = service.getAllHouseholdMembershipsByPerson(p);
		
		String definitionCode = Context.getAdministrationService().getGlobalProperty("fdm.householdDefinitionCode");
		String []def = null;
		if(definitionCode.contains(","))
			def = definitionCode.split(",");
		else
			def[0] = definitionCode;
		for (HouseholdMembership householdMembership : householdMem) {
	        for (int i = 0; i < def.length; i++) {
	        	if(householdMembership.getHouseholdMembershipGroups().getHouseholdDef().getHouseholdDefinitionsCode().equals(def[i]))
	        		hm.add(householdMembership);
            }
        }
		return hm;
	}
	
	public boolean addEditFoodCombination(String [] passedArr){
		FdmService service = Context.getService(FdmService.class);
		
		if(passedArr[0].equals("1")){
			FoodSource fs = service.getFoodSource(Integer.parseInt(passedArr[2]));
			FoodProduct fp = service.getFoodProduct(Integer.parseInt(passedArr[3]));
			FoodCombination fc = new FoodCombination(fs, fp);
			
			List<FoodCombination> comb = service.getFoodCombinationByFoodSource(fs, false);

			for (FoodCombination foodCombination : comb) {
	            if(foodCombination.getFoodProduct().equals(fp))
	            	return false;
            }
			fc.setDispensable(true);
			return service.saveFoodCombination(fc);
		}else if(passedArr[0].equals("2")){
			FoodCombination fc = service.getFoodCombination(Integer.parseInt(passedArr[1]));
			if(fc.isDispensable())
				fc.setDispensable(false);
			else
				fc.setDispensable(true);
			return service.saveFoodCombination(fc);
		}else if(passedArr[0].equals("3")){
			FoodCombination fc = service.getFoodCombination(Integer.parseInt(passedArr[1]));
			return service.purgeFoodCombination(fc,passedArr[3]);
		}
		return false;
	}
	
	public void savePrescription(String household,String foodProduct,String foodSource,String foodEncounter,String provide){
		
	}
	
	public boolean populatePackage(String []pack){
		FdmService service = Context.getService(FdmService.class);
		
		FoodPackage fp = new FoodPackage();
		
		List<String> passedCombination = new ArrayList<String>();
		String [] pop = null;
		if (pack[0].contains(",")){
			pop = pack[0].split(",");
			for (int i = 0; i < pop.length; i++)
				passedCombination.add(pop[i]);
		}else
			passedCombination.add(pack[0]);
		String packageName = pack[1];
		fp.setName(packageName);
		
		boolean isPackageSaved = service.saveFoodPackage(fp);
		
		FoodPackage fpack = service.getFoodPackageByName(packageName);
		
		boolean nini = true;
		if(isPackageSaved){
			for (int i = 0; i < passedCombination.size(); i++) {
				FoodCombination fc = service.getFoodCombination(Integer.parseInt(passedCombination.get(i)));
				log.info("");
		        FoodPackageCombination foodPackageCombination = new FoodPackageCombination(fc,fpack);
		        nini = service.saveFoodPackageCombination(foodPackageCombination);
		        if (!nini)
		        	break;
	        }
			return nini;
		}else
			return false;
	}
	public boolean addFoodPrescription(String [] arrData) throws ParseException{
		FdmService service = Context.getService(FdmService.class);
		
		Household household = Context.getService(HouseholdService.class).getHouseholdGroup(Integer.parseInt(arrData[0]));
		Location location = Context.getService(LocationService.class).getLocation(Integer.parseInt(arrData[1]));
		FoodPackage foodPackage = service.getFoodPackage(Integer.parseInt(arrData[2]));
		Person provider = Context.getPersonService().getPerson(Integer.parseInt(arrData[3]));
		String additionalFoodPresc = arrData[5];
		
		List<String> passedCombination = new ArrayList<String>();
		if(StringUtils.isNotEmpty(additionalFoodPresc)){
			String [] pop = null;
			if (additionalFoodPresc.contains(",")){
				pop = additionalFoodPresc.split(",");
				for (int i = 0; i < pop.length; i++)
					passedCombination.add(pop[i]);
			}else
				passedCombination.add(additionalFoodPresc);
		}
		
		FoodEncounter foodEncounter = new FoodEncounter();
		foodEncounter.setHousehold(household);
		foodEncounter.setLocation(location);
		foodEncounter.setProvider(provider);
		if(StringUtils.isNotEmpty(arrData[4]))
			foodEncounter.setEncounterDatetime(dateFormatHelper(arrData[4]));
		else
			foodEncounter.setEncounterDatetime(new Date());
		service.saveFoodEncounter(foodEncounter);
		
		List<FoodCombination> foodCombination = new ArrayList<FoodCombination>();
		List<FoodPackageCombination> foodPackageCombination = service.getFoodPackageCombinationByPackage(foodPackage, false);
		for (int i = 0; i < foodPackageCombination.size(); i++) {
			foodCombination.add(foodPackageCombination.get(i).getFoodCombination());
        }
		//TODO: additional foodCombination
		if(passedCombination!=null){
			for (int i = 0; i < passedCombination.size(); i++) {
				FoodCombination fc = service.getFoodCombination(Integer.parseInt(passedCombination.get(i)));
				foodCombination.add(fc);
	        }
		}
		
		String memberID = "";
		List<HouseholdMembership> hmem = Context.getService(HouseholdService.class).getAllHouseholdMembershipsByGroup(household);
		for (HouseholdMembership householdMembership : hmem) {
			memberID += householdMembership.getHouseholdMembershipMember().getPersonId() + ",";
        }
		List<FoodPrescription> foodPrescriptionList = new ArrayList<FoodPrescription>();
		for (int i = 0; i < foodCombination.size(); i++) {
			FoodPrescription foodPrescription = new FoodPrescription();
			foodPrescription.setFoodCombination(foodCombination.get(i));
			foodPrescription.setFoodEncounter(foodEncounter);
			foodPrescription.setHousehold(household);
			foodPrescription.setHouseholdMemberCount(memberID);
			foodPrescription.setProvider(provider);
			if(StringUtils.isNotEmpty(arrData[4]))
				foodPrescription.setPrescriptionDate(dateFormatHelper(arrData[4]));
			else
				foodPrescription.setPrescriptionDate(new Date());
			
			foodPrescriptionList.add(foodPrescription);
        }
		return service.saveFoodPrescription(foodPrescriptionList);
	}
	
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	private static Date dateFormatHelper(String strvalue) throws ParseException {
		if (strvalue == null || strvalue.length() == 0)
			return new Date();
		else
			return dateFormat.parse(strvalue);
	}
}
