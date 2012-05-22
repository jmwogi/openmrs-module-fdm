package org.openmrs.module.fdm.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.fdm.model.FoodCombination;
import org.openmrs.module.fdm.model.FoodPackage;
import org.openmrs.module.fdm.model.FoodSource;
import org.openmrs.module.fdm.model.FoodProduct;
import org.openmrs.module.fdm.service.FdmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FdmController {
	private static final Log log = LogFactory.getLog(FdmController.class);
	
    @RequestMapping("module/fdm/fdmPrescription")
    public void asPageLoads(ModelMap map) {
    	
    	FdmService service = Context.getService(FdmService.class);
    	
    	List<FoodProduct> allFoodsProduct = service.getFoodProduct(false);
    	List<FoodSource> allFoodsSource = service.getFoodSource(false);
    	
		map.addAttribute("allFoodsProduct",allFoodsProduct );
		map.addAttribute("allFoodsSource",allFoodsSource );
    }
    
    @RequestMapping("/module/fdm/fdmConfiguration")
	public String showConfigurationForm(ModelMap map) throws Exception {
    	FdmService service = Context.getService(FdmService.class);
		List<FoodSource> foodSource = service.getFoodSource(false);
		FoodSource fs = new FoodSource();
		if(foodSource.size() != 0)
			fs = foodSource.get(0);
		List<FoodProduct> foodProduct = service.getFoodProduct(false);
		List<FoodCombination> foodCombination = service.getFoodCombinationByFoodSource(fs, false);
		List<FoodProduct> allFoodsProduct=service.getFoodProduct(false);
    	List<FoodSource> allFoodsSource=service.getFoodSource(false);
    	List<FoodPackage> allFoodPackage=service.getFoodPackage(false);
    	
		map.addAttribute("foodSource", foodSource);
		map.addAttribute("foodProduct", foodProduct);
		map.addAttribute("foodMapm", foodCombination);
		map.addAttribute("allFoodProduct",allFoodsProduct );
		map.addAttribute("allFoodSource",allFoodsSource );
		map.addAttribute("foodMapCom", foodCombination);
		map.addAttribute("foodPackage", allFoodPackage);
		
    	return "/module/fdm/fdmConfiguration";
	}
    
    @RequestMapping("/module/fdm/fdmStatistic")
	public void showStatisticForm(ModelMap map) throws Exception {
    	
	}
	
	
}