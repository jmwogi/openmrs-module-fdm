<%@ include file="/WEB-INF/template/include.jsp"%>

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