<%@ include file="/WEB-INF/template/include.jsp"%>
<span style="float: right;"><a href="#" onclick="javascript:closeThis()" >[x]</a> </span>
<table id="tblFoodPack"  class="lineTable">
	<thead>    
    	<tr>
            <th scope="col" rowspan="2">&nbsp;</th>
            <th scope="col" colspan="3">Prescription [${foodPrescDetails}] 
            	<span style="float: right;">
            		&nbsp; 
            		<input type="checkbox" id="chkAdjust" /><label for="chkAdjust">Adjust</label></span>
            </th>
        </tr>
        <tr>
            <th scope="col">Source-to-Product</th>
            <th scope="col"><spring:message code="general.createdBy"/></th>
            <th scope="col">Pick</th>
        </tr>        
    </thead>
    <tbody>
    	<c:forEach var="foodPack" items="${foodPresc}" varStatus="ind">
	    	<tr valign="top">
				<th>${ind.index + 1}</th>
				<td class="highlight">
					${foodPack.foodCombination.foodSource.code} - ${foodPack.foodCombination.foodProduct.name}
				</td>
				<td class="highlight">
					<openmrs:format user="${foodPack.creator}"/><br />
					<openmrs:formatDate date="${foodPack.dateCreated}"/><br />
				</td>
				<td class="highlight">
					<input type="checkbox" id="chkPickedPresc${foodPack.id}" onchange="javascript:populatePick('${foodPack.id}')"/>
				</td>
			</tr>
		</c:forEach>
    </tbody>
    <tfoot>
    	<tr>
    		<td colspan="5"><input type="checkbox" id="chkPicked" value="${prescUuid}"/><label for="chkPicked">Picked All</label> 
    		<span style="float: right;">[<a href="#" id="lnkSavePick">Confirm Individual Pick-up</a>]</span></td>
    	</tr>
    </tfoot>
   </table>
<input type="hidden" id="hdnConfirmedPick"/>
<script type="text/javascript">
    function closeThis(){
        $j('#popPrescription').hide();
    }
    
    function populatePick(pas){
		var pop = document.getElementById("hdnConfirmedPick").value;
		var chkSel = document.getElementById("chkPickedPresc" + pas);
		if (chkSel.checked == 1){
			if(pop == ""){
				$j("#hdnConfirmedPick").val(pas);
			}else
				$j("#hdnConfirmedPick").val(pop + "," + pas);
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
			$j("#hdnConfirmedPick").val(strOut);
		}
	}

</script>