<%@ include file="/WEB-INF/template/include.jsp"%>
<!-- <openmrs:require privilege="" otherwise="/login.htm" redirect="module/fdm/fdm.form" /> -->
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localHeader.jsp"%>

<style>
	#openmrsSearchTable_wrapper{
	/* Removes the empty space between the widget and the Create New Patient section if the table is short */
	/* Over ride the value set by datatables */
		min-height: 0px; height: auto !important;
	}
</style>


<style>
	#content1 section1, #content1 article1 { 
		/* border: 2px solid #cdcdcd; */
		-webkit-border-radius: 10px;
		-moz-border-radius: 10px;
		border-radius: 10px;
		display: block; 
		float: left; 
		width: 96%;
		padding-left:15px;
		margin-bottom:10px;
		margin-left:10px;
		-webkit-box-shadow: 0px 4px 4px 6px #cdcdcd;
		-moz-box-shadow: 0px 4px 4px 6px #cdcdcd;
		box-shadow: 0px 4px 4px 6px #cdcdcd;
	}
	
	#content1 { 
		/*width: 200px;*/ 
		height: 75px;
		border: 2px solid;
	}
	
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


<div>
		<div style="width:95%;">
			<section1 id="content1">

				<article1>
					<br />
					Put here
				</article1>
		
		   </section1>
		
		</div>
	
	</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>

