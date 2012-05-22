/**
 * 
 */
package org.openmrs.module.fdm.web.controller.ports;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jmwogi
 *
 */
@Controller
public class FdmPortController {

	private static final Log log = LogFactory.getLog(FdmPortController.class);
	
	@RequestMapping("module/fdm/ports/fdmFoodSourcePort")
    public void prepareFoodSource(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                  @RequestParam(required=false, value="includedRetired") boolean includeRetired){
		FdmService service = Context.getService(FdmService.class);
		List<FoodSource> foodSource = service.getFoodSource(includeRetired);
		map.addAttribute("foodSource", foodSource);
    }
	@RequestMapping("module/fdm/ports/fdmFoodProductPort")
    public void prepareFoodProduct(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                   @RequestParam(required=false, value="includedRetired") boolean includeRetired){
		FdmService service = Context.getService(FdmService.class);
		List<FoodProduct> foodProduct = service.getFoodProduct(includeRetired);
		map.addAttribute("foodProduct", foodProduct);
    }
	
	@RequestMapping("module/fdm/ports/fdmFoodCombinationPort")
    public void prepareFoodCombination(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                   @RequestParam(required=false, value="includedVoided") boolean includeVoided,
                                   @RequestParam(required=false, value="foodSrc") String foodSource){
		FdmService service = Context.getService(FdmService.class);
		FoodSource fs = service.getFoodSource(foodSource);
		List<FoodCombination> foodCombination = service.getFoodCombinationByFoodSource(fs, includeVoided);
		map.addAttribute("foodMapm", foodCombination);
    }
	@RequestMapping("module/fdm/ports/fdmFoodAdditionPickPort")
    public void prepareFoodAdditionPick(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                   @RequestParam(required=false, value="includedVoided") boolean includeVoided,
                                   @RequestParam(required=false, value="foodSrc") String foodSource){
		FdmService service = Context.getService(FdmService.class);
		FoodSource fs = service.getFoodSource(foodSource);
		List<FoodCombination> foodCombination = service.getFoodCombinationByFoodSource(fs, includeVoided);
		map.addAttribute("foodMapm", foodCombination);
    }
	
	@RequestMapping("module/fdm/ports/fdmSelectedPersonHouseholdPort")
    public void prepareSelectedPersonHousehold(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                  @RequestParam(required=true, value="personid") String personID){
		List<HouseholdMembership> hm = new ArrayList<HouseholdMembership>();
		
		Person p = Context.getPersonService().getPerson(Integer.parseInt(personID));
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdMembership> householdMem = service.getAllHouseholdMembershipsByPerson(p);
		
		String definitionCode = Context.getAdministrationService().getGlobalProperty("fdm.householdDefinitionCode");
		for (HouseholdMembership householdMembership : householdMem) {
	        if(householdMembership.getHouseholdMembershipGroups().getHouseholdDef().getHouseholdDefinitionsCode().equals(definitionCode))
	        	hm.add(householdMembership);
        }
		
		map.addAttribute("membership", hm);
    }
	
	@RequestMapping("module/fdm/ports/fdmPopulatePrescriptionPort")
    public void preparePopulatePrescription(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                            @RequestParam(required=true, value="hhmid") String householdMembershipID){
		FdmService service = Context.getService(FdmService.class);
		LocationService locationService = Context.getService(LocationService.class);
		
		HouseholdService householdService = Context.getService(HouseholdService.class);
		List<FoodPackage> allFoodPackage = service.getFoodPackage(false);
		String householdIdentifier = householdService.getHouseholdMembership(Integer.parseInt(householdMembershipID)).
			getHouseholdMembershipGroups().getHouseholdIdentifier();
		int householdId = householdService.getHouseholdMembership(Integer.parseInt(householdMembershipID)).getHouseholdMembershipGroups().getId();
		List<HouseholdMembership> hmem = householdService.getAllHouseholdMembershipsByGroup(householdService.getHouseholdMembership(Integer.parseInt(householdMembershipID)).
			getHouseholdMembershipGroups());
		List<Location> location = locationService.getAllLocations();
		
    	List<FoodSource> allFoodsSource=service.getFoodSource(false);
    	FoodPackage fp = allFoodPackage.get(0);
    	List<FoodPackageCombination> fpc = service.getFoodPackageCombinationByPackage(fp,false);
    	List<FoodEncounter> fEnc = service.getFoodEncounterByHousehold(householdService.getHouseholdMembership(Integer.parseInt(householdMembershipID)).getHouseholdMembershipGroups(), false);
    	String lastEncDate = "";
    	if(fEnc.size() != 0)
    		lastEncDate = fEnc.get(0).getEncounterDatetime().toString();
		map.addAttribute("foodPackCombination",fpc );
		map.addAttribute("allFoodsSource",allFoodsSource );
		map.addAttribute("members", hmem);
		map.addAttribute("foodSites", location);
		map.addAttribute("hidentifier", householdIdentifier);
		map.addAttribute("hid", householdId);
		map.addAttribute("allFoodPackage",allFoodPackage );
		map.addAttribute("foodEncounters",fEnc );
		map.addAttribute("lastEncounterDate",lastEncDate );
		
		List<FoodSource> foodSource = allFoodsSource;
		FoodSource fs = new FoodSource();
		if(foodSource.size() != 0)
			fs = foodSource.get(0);
		List<FoodProduct> foodProduct = service.getFoodProduct(false);
		List<FoodCombination> foodCombination = service.getFoodCombinationByFoodSource(fs, false);
		map.addAttribute("foodMapm", foodCombination);
		
	}
	
	@RequestMapping("module/fdm/ports/fdmFoodComPort")
    public void prepareFoodCom(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                   @RequestParam(required=false, value="includedVoided") boolean includeVoided,
                                   @RequestParam(required=false, value="foodSrc") String foodSource){
		FdmService service = Context.getService(FdmService.class);
		FoodSource fs = service.getFoodSource(foodSource);
		List<FoodCombination> foodCombination = service.getFoodCombinationByFoodSource(fs, includeVoided);
		map.addAttribute("foodMapCom", foodCombination);
    }
	
	@RequestMapping("module/fdm/ports/fdmFoodPackagePort")
    public void prepareFoodPackage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                  @RequestParam(required=false, value="includedRetired") boolean includeRetired){
		FdmService service = Context.getService(FdmService.class);
		List<FoodPackage> foodPackage = service.getFoodPackage(includeRetired);
		map.addAttribute("foodPackage", foodPackage);
    }
	
	@RequestMapping("module/fdm/ports/fdmFoodPrescriptionPackPort")
    public void prepareFoodPrescriptionPack(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                  @RequestParam(required=false, value="foodPack") String foodPack){
		FdmService service = Context.getService(FdmService.class);
		FoodPackage fp = service.getFoodPackage(Integer.parseInt(foodPack));
		List<FoodPackageCombination> fpc = service.getFoodPackageCombinationByPackage(fp,false);
    	map.addAttribute("foodPackCombination",fpc );
    }
	
	@RequestMapping("module/fdm/ports/fdmSavedPrescriptionPort")
    public void prepareSavedPrescriptionPort(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                  @RequestParam(required=false, value="encounter") String foodEncounterID){
		FdmService service = Context.getService(FdmService.class);
		FoodEncounter fe = service.getFoodEncounter(Integer.parseInt(foodEncounterID));
		List<FoodPrescription> fp = service.getFoodPrescriptionByEncounter(fe, false);
		map.addAttribute("foodPresc",fp );
        map.addAttribute("foodPrescDetails",fe.getLocation() + "-" + fe.getEncounterDatetime().toString().substring(0,9));
        map.addAttribute("prescUuid",fe.getUuid());
    }
	
	@RequestMapping("module/fdm/ports/fdmEnteredFoodEncounterPort")
    public void prepareEnteredFoodEncounterPort(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                                  @RequestParam(required=false, value="householdID") String hid){
		FdmService service = Context.getService(FdmService.class);
		HouseholdService householdService = Context.getService(HouseholdService.class);
		Household hh = householdService.getHouseholdGroup(Integer.parseInt(hid));
		List<FoodEncounter> fEnc = service.getFoodEncounterByHousehold(hh, false);
		
		map.addAttribute("foodEncounters",fEnc );
    }
	
}