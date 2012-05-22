/**
 * 
 */
package org.openmrs.module.fdm.ext;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;


/**
 * @author jmwogi
 *
 */
public class FdmAdminExt extends AdministrationSectionExt {
	
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	public String getTitle() {
		return "fdm.manage.title";
	}
	
	public String getRequiredPrivilege() {
		return "";
	}
	
	public Map<String, String> getLinks() {
		// Using linked hash map to keep order of links
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("module/fdm/fdmConfiguration.form", "Configuration");
		map.put("module/fdm/fdmPrescription.form", "Prescription");
		map.put("module/fdm/fdmStatistic.form", "Statistics");
		return map;
	}
	
}
