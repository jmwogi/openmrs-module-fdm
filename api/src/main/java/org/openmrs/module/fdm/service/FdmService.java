/**
 * 
 */
package org.openmrs.module.fdm.service;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fdm.model.FoodCombination;
import org.openmrs.module.fdm.model.FoodEncounter;
import org.openmrs.module.fdm.model.FoodPackage;
import org.openmrs.module.fdm.model.FoodPackageCombination;
import org.openmrs.module.fdm.model.FoodPrescription;
import org.openmrs.module.fdm.model.FoodProduct;
import org.openmrs.module.fdm.model.FoodSource;
import org.openmrs.module.household.model.Household;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author jmwogi
 *
 */
@Transactional
public interface FdmService extends OpenmrsService{
	
	public boolean saveFoodProduct(FoodProduct foodProduct);
	public FoodProduct getFoodProduct(int id);
	public FoodProduct getFoodProduct(String uuid);
	public FoodProduct getFoodProduct(FoodProduct foodProduct);
	public boolean purgeFoodProduct(FoodProduct foodProduct, String retireReason);
	public List<FoodProduct> getFoodProduct(boolean retiredIncluded);
	
	public boolean saveFoodSource(FoodSource foodSource);
	public FoodSource getFoodSource(int id);
	public FoodSource getFoodSource(String uuid);
	public FoodSource getFoodSource(FoodSource foodSource);
	public boolean purgeFoodSource(FoodSource foodSource, String retireReason);
	public List<FoodSource> getFoodSource(boolean retiredIncluded);
	
	public boolean saveFoodEncounter(FoodEncounter foodEncounter);
	public FoodEncounter getFoodEncounter(int id);
	public FoodEncounter getFoodEncounter(String uuid);
	public FoodEncounter getFoodEncounter(FoodEncounter foodEncounter);
	public boolean purgeFoodEncounter(FoodEncounter foodEncounter, String voidReason);
	public List<FoodEncounter> getFoodEncounterByHousehold(Household household, boolean voidedIncluded);
	
	public boolean saveFoodCombination(FoodCombination foodCombination);
	public FoodCombination getFoodCombination(int id);
	public FoodCombination getFoodCombination(String uuid);
	public FoodCombination getFoodCombination(FoodCombination foodCombination);
	public boolean purgeFoodCombination(FoodCombination foodCombination, String voidReason);
	public List<FoodCombination> getFoodCombination(boolean voidedIncluded);
	public List<FoodCombination> getFoodCombinationByFoodSource(FoodSource foodSource, boolean voidedIncluded);
	
	public boolean saveFoodPrescription(FoodPrescription foodPrescription);
	public boolean saveFoodPrescription(List<FoodPrescription> foodPrescriptionList);
	public FoodPrescription getFoodPrescription(int id);
	public FoodPrescription getFoodPrescription(String uuid);
	public FoodPrescription getFoodPrescription(FoodPrescription foodPrescription);
	public boolean purgeFoodPrescription(FoodPrescription foodPrescription, String voidReason);
	public List<FoodPrescription> getFoodPrescriptionByEncounter(FoodEncounter foodEncounter, boolean voidedIncluded);
	
	public boolean saveFoodPackage(FoodPackage foodPackage);
	public FoodPackage getFoodPackage(int id);
	public FoodPackage getFoodPackage(String uuid);
	public FoodPackage getFoodPackageByName(String name);
	public FoodPackage getFoodPackage(FoodPackage foodPackage);
	public boolean purgeFoodPackage(FoodPackage foodPackage, String retireReason);
	public List<FoodPackage> getFoodPackage(boolean retiredIncluded);
	
	public boolean saveFoodPackageCombination(FoodPackageCombination foodPackageCombination);
	public FoodPackageCombination getFoodPackageCombination(int id);
	public FoodPackageCombination getFoodPackageCombination(String uuid);
	public FoodPackageCombination getFoodPackageCombination(FoodPackageCombination foodPackageCombination);
	public boolean purgeFoodPackageCombination(FoodPackageCombination foodPackageCombination, String voidReason);
	public List<FoodPackageCombination> getFoodPackageCombination(FoodPackageCombination foodPackageCombination,boolean voidedIncluded);
	public List<FoodPackageCombination> getFoodPackageCombinationByPackage(FoodPackage foodPackage,boolean voidedIncluded);
	
}
