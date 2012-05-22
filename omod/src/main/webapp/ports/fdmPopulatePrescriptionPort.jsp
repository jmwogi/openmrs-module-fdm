<%@ include file="/WEB-INF/template/include.jsp"%>
	
<c:if test="${empty DO_NOT_INCLUDE_JQUERY}">
	<openmrs:htmlInclude file="/scripts/jquery/jquery.min.js" />
	<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
          <openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-timepicker-addon.js" />
	<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js" />
	<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-timepicker-i18n.js" />
	<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
</c:if>
<link href="${pageContext.request.contextPath}/moduleResources/fdm/css/css-table.css" type="text/css" rel="stylesheet" />
<style>
	#cont, #cont2{
		border:1px solid #a1a1a1;
		padding:10px 10px; 
		border-radius:10px;
		-moz-border-radius:10px;
		margin-top:2px; 
	}
	/* #content2 section1, #content2 article1, #content1 section1, #content1 article1 { 
		border:1px solid #a1a1a1;
		padding:10px 10px; 
		/*background:#dddddd;*/
		/* width:300px; */
		/* border-radius:10px;
		-moz-border-radius:10px; */
		
		/* border: 2px solid #cdcdcd; */
		-/* webkit-border-radius: 10px;
		-moz-border-radius: 10px;
		border-radius: 10px;
		display: block;  */
		/* float: left;  */
		/* width: 100%;
		padding-left:15px;
		margin-bottom:10px;
		margin-left:2px; */
		/* -webkit-box-shadow: 0px 4px 4px 6px #cdcdcd;
		-moz-box-shadow: 0px 4px 4px 6px #cdcdcd; */
		/* box-shadow: 0px 4px 4px 6px #cdcdcd; */
	} */
	
	/* #content1 {  */
		/*width: 200px;*/ 
		/* height: 75px;  */
		/* border: 1px solid;
	}
	#content2 {  */
		/*width: 200px;*/ 
		/* height: 75px;  */
		/* border: 1px solid;
	} */
	
	#openmrsSearchTable_wrapper{
	/* Removes the empty space between the widget and the Create New Patient section if the table is short */
	/* Over ride the value set by datatables */
		min-height: 0px; height: auto !important;
	}
	.dataTables_wrapper {
	    clear: both;
	    min-height: 0px;
	    position: relative;
	}
	.evencol {
        border-left: 1px solid #E8E8E9;
        border-right: 1px solid #E8E8E9;
        padding-left: 3px;
    }

    .oddcol {
        padding-left: 3px;
    }
</style>

<script type="text/javascript">
$j(document).ready(function() {
	$j('#householdMembers1').dataTable({
		"sDom": 'T<"clear">lfrtip',
		"bPaginate": false,
	    "bLengthChange": false,
	    "bFilter": false,
	    "bSort": false,
	    "bInfo": true,
	    "bAutoWidth": false
	});
});

function selectProvider(userid,provider){
	var proHouseholdId = null;
	if(provider != null){
		proHouseholdId=provider.personId;
	}
	$j("#userProvider").val(proHouseholdId);
	
}

function getFoodPack(){
	var packageID = document.getElementById("pack").value;
	$j.get("ports/fdmFoodPrescriptionPackPort.form?foodPack=" + packageID,
			function(data){
				$j('#divPack').html(data);
			}
		);
}

function savePrescription(){
	var householdID = ${hid};
	var loci = document.getElementById("site").value;
	var foodpac = document.getElementById("pack").value;
	var prov = document.getElementById("userProvider").value;
	var capDate = document.getElementById("captureDate").value;
	var pop = document.getElementById("popComb").value;
	
	var arrCol = [householdID, loci, foodpac, prov, capDate, pop];
	DWRFoodService.addFoodPrescription( arrCol, returnPrescription);
}
function returnPrescription(data){
	var householdID = ${hid};
	$j.get("ports/fdmEnteredFoodEncounterPort.form?householdID=" + householdID,
			function(dat){
				$j('#cont2').html(dat);
			}
		);
	$j('#postPrescription').slideToggle('fast');
	event.preventDefault();
}

function pullVoidedFC(){
	var valSource = document.getElementById("foodSourceChange").value;
	
	$j.get("ports/fdmFoodAdditionPickPort.form?includedVoided=" + false + "&foodSrc=" + valSource,
			function(dat){
				$j('#idFoodMap').html(dat);
			}
		);
	
}

</script>
<div class="boxHeader">Prescription Section:</div>
<div id="showprescription">
	<div>
		<div style="width:65%;float: left;">
			<div id="cont">
				<br />
				<b>Household:</b> &nbsp;&nbsp;&nbsp;<i> ${hidentifier}</i>
				&nbsp;&nbsp;&nbsp;&nbsp;<b>Last prescription Date:</b>&nbsp;<i>${fn:substring(lastEncounterDate,0,10)}</i><br />
				<br /><br />
				<b>Active Members</b>
				<br />
				
				<table border="0" id="householdMembers1" cellpadding="0" cellspacing="5">
					<thead>
				  		<tr>
				  			<th class="tbClass">&nbsp;</th>
				  			<th class="tbClass">Names</th>
				  			<th class="tbClass">Gender</th>
				  			<th class="tbClass">Birth Date</th>
				  			<th class="tbClass">Start Date</th>
				  		</tr>
					 </thead>
					 <tbody>	
				  		<c:forEach var="householdMembers" items="${members}">
					  		
					  		<tr>
					  			<td class="tdClass">
					  				 <c:choose>
	                                <c:when test="${householdMembers.householdMembershipHeadship }">
	                                    <img src="${pageContext.request.contextPath}/moduleResources/fdm/images/tick.png" alt="[HEAD/INDEX]" />
	                                </c:when>
	                                <c:otherwise>
	                                </c:otherwise>
		                        </c:choose>
					  			</td>
					  			<td class="tdClass">
					  			<c:choose>
									<c:when test="${not empty householdMembers.householdMembershipMember.givenName }">
										${householdMembers.householdMembershipMember.givenName} &nbsp;
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty householdMembers.householdMembershipMember.middleName }">
										${householdMembers.householdMembershipMember.middleName} &nbsp;
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty householdMembers.householdMembershipMember.familyName }">
										${householdMembers.householdMembershipMember.familyName} &nbsp;
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
					  			</td>
					   			<td class="tdClass" align="center">${householdMembers.householdMembershipMember.gender}</td>
					  			<td class="tdClass" align="center">${fn:substring(householdMembers.householdMembershipMember.birthdate,0,10)}</td>
					  			<td class="tdClass" align="center">${fn:substring(householdMembers.startDate,0,10)}</td>
					  		</tr>
				  		</c:forEach>
					 </tbody> 		
					</table>
					<br />
					<script type="text/javascript">
						$j(document).ready(function() {
							$j('.togglePostPrescription').click(function(event) {
								$j('#postPrescription').slideToggle('fast');
								event.preventDefault();
							});
						});
					</script>
					<a class="togglePostPrescription" href="#">New Presciption</a><br />
					<br /><br />
					<div id="postPrescription" style="border: 1px white solid; display: none; background-color: #EEEEEE; padding:10px 10px; border-radius:10px; -moz-border-radius:10px;">
						Distribution Site: <select name="site" id="site">
									<c:forEach var="fs" items="${foodSites}" varStatus="ind">
										<option id="${ind.index + 1 }" value="${fs.locationId}" >${fs.name}</option>
									</c:forEach>
								</select>
								
						&nbsp;&nbsp;&nbsp;&nbsp;
						Food Package: <select name="pack" id="pack" onchange="javascript:getFoodPack()">
									<c:forEach var="fpack" items="${allFoodPackage}" varStatus="ind">
										<option id="${ind.index + 1 }" value="${fpack.id}" >${fpack.name}</option>
									</c:forEach>
								</select>
						
						<br />
						<div id="divPack">
							<table border="0" class="lineTable">
								<thead>
									<tr>
							            <th scope="col" rowspan="2">&nbsp;</th>
							            <th scope="col" colspan="3">Products:</th>
							        </tr>
									<tr>
										<th>Product</th>
										<th>Food Source</th>
										<th>Available</th>
									</tr>
								</thead>
								<c:forEach var="foodpack" items="${foodPackCombination}" varStatus="ind">
								<tr>
									<th>${ind.index + 1}</th>
									<td class="highlight">${foodpack.foodCombination.foodProduct.name}</td>
									<td class="highlight">
										${foodpack.foodCombination.foodSource.code}
									</td>
									<td class="highlight">
										<c:choose>
											<c:when test="${foodpack.foodCombination.dispensable}">
												<input type="checkbox" id="chkDispensable" checked="checked" disabled="disabled" />
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="chkDispensable" disabled="disabled" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:forEach>
								
							</table>
						</div>
							<br />
							<script type="text/javascript">
								$j(document).ready(function() {
									$j('.toggleAddFoodMap').click(function(event) {
										$j('#idAdditionalCombination').slideToggle('fast');
										event.preventDefault();
									});
								});
								
								function populateNewComb(pas){
									var pop = document.getElementById("popComb").value;
									var chkSel = document.getElementById("idSel" + pas);
									if (chkSel.checked == 1){
										if(pop == ""){
											$j("#popComb").val(pas);
										}else
											$j("#popComb").val(pop + "," + pas);
									}else{
										var strOut = "";
										var noSeleted = pop.split(",");
										for(var x=0; x<(noSeleted.length); x++){
									            if(noSeleted[x]!=pas){
									                if(strOut=="")
									                	strOut = noSeleted[x];
									                else
									                	strOut = strOut + "," + noSeleted[x];
									            }
										}
										//$j("#popComb").val("");
										$j("#popComb").val(strOut);
									}
								}
							</script>
							
							<a href="#" class="toggleAddFoodMap">Additional pick</a>
							<div id="idAdditionalCombination" style="border: 1px white solid; display: none; background-color: #EFEFEF; padding:10px 10px; border-radius:10px; -moz-border-radius:10px;">
								Source: <select name="foodSourceChange" id="foodSourceChange" varStatus="state" onchange="javascript:pullVoidedFC()">
									<c:forEach var="sourceChange" items="${allFoodsSource}" varStatus="ind">
										<option id="${state.index + 1 }" value="${sourceChange.uuid}" >${sourceChange.code}</option>
									</c:forEach>
								</select>
								
								<input type="hidden" id="popComb" />
								<br />
								<div id="idFoodMap">
									
									<table id="tblFoodMap"  class="lineTable">
										<thead>    
									    	<tr>
									            <th scope="col" rowspan="2">&nbsp;</th>
									            <th scope="col" colspan="4">Food Source-To-Product mapping</th>
									        </tr>
									        <tr>
									            <th scope="col">Food Product</th>
									            <th scope="col">Available</th>
									            <th scope="col">Choose</th>
									        </tr>        
									    </thead>
									    <tbody>
									    	<c:forEach var="foodSourceMap" items="${foodMapm}" varStatus="ind">
												<form method="POST" name="${foodSourceMap.id}">
													<tr valign="top">
														<th>${ind.index + 1}</th>
														<td class="highlight">
															${foodSourceMap.foodProduct.name} 
														
														</td>
														<td class="highlight">
															<c:choose>
																<c:when test="${foodSourceMap.dispensable}">
																	<input type="checkbox" id="chkDispensable" checked="checked" readonly="readonly" />
																</c:when>
																<c:otherwise>
																	<input type="checkbox" id="chkDispensable" readonly="readonly" />
																</c:otherwise>
															</c:choose>
														</td>
														<td class="highlight">
															<input type="checkbox" id="idSel${foodSourceMap.id}" onchange="javascript:populateNewComb('${foodSourceMap.id}')" value="${foodSourceMap.id}"/>
														</td>
													</tr>
												</form>
											</c:forEach>
									    </tbody>
									</table>
								</div>
							</div>
						<table>
							<tr>
								<td>Provider</td>
								<td><openmrs_tag:userField formFieldName="userProviders" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectProvider" /></td>
								<td><input type="hidden" id="userProvider" size="10" />
							</tr>
							<tr>
								<td>Capture Date:</td><td><input type="text" name="captureDate" id="captureDate" onClick="showCalendar(this)" /></td>
							</tr> 
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><input type="button" value="Prescribe" onclick="javascript:savePrescription()"/></td>
							</tr>
						</table>
					</div>
					<div id="popPrescription" style="background-color: #EEEEEE; padding:10px 10px; border-radius:10px; -moz-border-radius:10px;"></div>
					<script type="text/javascript">
					$j(document).ready(function() {	
						$j('#popPrescription').hide();
					}
					</script>
				</div>
		</div>
		<div style="width:35%; float: right;">
			<div id="cont2" style="background-color: #EEEEEE; padding:10px 10px; border-radius:10px; -moz-border-radius:10px;">
					<table border="0" class="lineTable">
						<thead>
							<tr>
					            <th scope="col" colspan="3">Previous Prescriptions:</th>
					        </tr>
							<tr>
								<th></th>
								<th>Date</th>
								<th>Provider</th>
							</tr>
						</thead>
						<c:forEach var="foodEnc" items="${foodEncounters}" varStatus="ind">
						<tr>
							<th>${ind.index + 1}</th>
							<td class="highlight"><a href="#" onClick="javascript:popPrescrip('${foodEnc.id}')">${fn:substring(foodEnc.encounterDatetime,0,10)}&nbsp;(${foodEnc.location.name})</a></td>
							<td class="highlight">
								<openmrs:format person="${foodEnc.provider}"/>
							</td>
						</tr>
						</c:forEach>
					</table>
			</div>
			<script type="text/javascript">
				function popPrescrip(id){
					$j.get("ports/fdmSavedPrescriptionPort.form?encounter=" + id,
							function(dat){
								$j('#popPrescription').html(dat);
								$j('#popPrescription').show();
							}
						);
				}
			</script>
		</div>
		<table><tr><td>&nbsp;</td></tr></table>
	</div>
	
</div>