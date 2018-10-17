

function categoryrefresh()
{
	//alert(document.getElementById("equipmentsave_categoryId").value);
	window.location.href= "/ACS/equipmentadddisplay.action?categoryid="+document.getElementById("equipmentsave_categoryId").value+"&id="+document.getElementById("equipmentsave_equipmentId").value+"&leadid="+document.getElementById("equipmentsave_leadId").value;
}

/**
 * ACS Form Validation 
 */
function formvalidation(){
	var ret = true;
	
	
}

 function fmvalidation()
 {
 	var ret= true;
 	
    /* if($("#equipmentsave_myFile").val()==""){
 		ret =false;
 	} */
 	
 	
 	//Commenting the below code to change the font validation based on lead
    /* convtype=$("#equipmentsave_categoryId option:selected").text();
 	if(convtype.toLowerCase()==="epub"||convtype.toLowerCase()==="epub 3"){
 		if($("#equipmentsave_fileFont").val()==""){
 		    alert('please provide the values for mandatory fields');
 			ret =false;
 		} 
 	}	 */
 	
 	/* var projectType=$("#equipmentsave_leadId option:selected").text();
 	var projectCategory = $("#equipmentsave_projCategory option:selected").text();
 	if(projectType.toLowerCase() != "bits xml" || projectType.toLowerCase() != "netbase" || projectCategory.toLowerCase() != "validation"){
 			if($("#equipmentsave_fileFont").val()==""){
 				alert('ksfhgk')
 				ret =false;
 			}
 		
 	}  */
 	
 	if(projectType.toLowerCase() != "netbase") {
	 	if($("#equipmentsave_equipmentName").val()=="")
	 	{
	 		/* alert('please provide the values for mandatory fields'); */
	 		ret =false;
	 	}
 	}
 	
 	if($("#equipmentsave_fileImage").val() == ""){
 		ret = false;
 	}
 	
 	if($("#equipmentsave_leadId").val()==""){
 		ret =false;
 	}
 	
 	if(projectType.toLowerCase() != "netbase") {
 		if($("#equipmentsave_equipmentName").val()==""){
 			ret =false;
 		}
 	}
 	
 	if(projectType.toLowerCase() == "bits xml" || projectType.toLowerCase() != "netbase") {
 		if($("#equipmentsave_fileMetadata").val()==""){
 			ret =false;
 		}
 	}
 	
 	
 	if(projectType.toLowerCase() == "netbase"){
 		if($("#equipmentsave_fileMetaXlsx").val() == ""){
 			ret = false;
 		}
 	}
 	
 	if(projectType.toLowerCase() == "netbase"){
	 	if($("#equipmentsave_priceBandXml").val() == ""){
				ret = false;
	    }
 	}
 	if(ret === false){
 		alert('please provide the values for mandatory fields');
 		ret = false;
 	}
 	
 	if(projectType.toLowerCase() == "bits xml"){
 		checkXmlInputFile();
 	}
 	
 	return ret;
 }

function outputname()
{
	str = $("#equipmentsave_myFile").val();
	str=str.substring(str.lastIndexOf("\\")+1,str.length);
	str=str.substring(0,str.lastIndexOf("."));
	//alert(str);
	//$("#equipmentsave_equipmentName").val(str.substring(str.lastIndexOf("\\")+1,str.length));
	$("#equipmentsave_equipmentName").val(str);
	
}

function checkInputFile(){
	var flag = true;
	var inputFileName = $("#equipmentsave_myFile").val();
	//Check input file name length
	var inputFileLen=inputFileName.substring(inputFileName.lastIndexOf("\\")+1,inputFileName.length);
	inputFileLen=inputFileLen.substring(0,inputFileLen.lastIndexOf("."));
	alert(inputFileLen);
	var len = inputFileLen.length;
	/* if(len < 13 || len > 13) {
		alert("Input file name should be 13 digit ISBN.");
		$("#equipmentsave_myFile").val("");
		$("#equipmentsave_equipmentName").val("");
		flag = false;
	} */
	
	if(inputFileLen.toString().match(/^\d{13}$/) == null){
		alert("Input file name should be 13 digit ISBN.");
		$("#equipmentsave_myFile").val("");
		$("#equipmentsave_equipmentName").val("");
		flag = false;
	}
	
	return flag;
}

/**
 * Validate BITS Xml File name
 */
function checkXmlInputFile(){
	var flag = true;
	
	//Alert user to choose xml file
	var inputFileName = $("#equipmentsave_myFile").val();
	var inputFileExt = inputFileName.split('.')[inputFileName.split('.').length - 1].toLowerCase();
	if(!(inputFileExt === "xml")){
		alert("Invalid file should be a valid BITS Xml.")
		flag = false;
	}

	//Check input file name length
	var inputFileLen=inputFileName.substring(inputFileName.lastIndexOf("\\")+1,inputFileName.length);
	inputFileLen=inputFileLen.substring(0,inputFileLen.lastIndexOf("."));
	var len = inputFileLen.length;
	if(len.length < 13 || len.length > 13){
		alert("Please check BITS Xml ISBN number.");
		flag = false;
	}
	
	return flag;
}

function outputType() {
		//categoryrefresh();
		//var categoryId = $("#equipmentsave_categoryId").val();
		//alert(categoryId);
			convtype = $("#equipmentsave_categoryId option:selected").text();
			var project = $("#equipmentsave_leadId option:selected").text();
			if(!project.toLowerCase() === "netbase"){
				if (convtype.toLowerCase() === "xml") {
					//$("#equipmentsave_font").hide();	
					$('div#font').hide();
					$('div#AddInfo').hide();
					$('div#Mathml').show();
					//$('#equipmentsave_font').css('display','none');
				} else {
					$('div#font').show();
					$('div#AddInfo').show();
					if (convtype.toLowerCase() === "epub 3")
						$('div#Mathml').show();
					else
						$('div#Mathml').hide();
				}
		   }
		//categoryrefresh();
}

function categoryType(){
	var projCategory = $("#equipmentsave_projCategory option:selected").text();
	//alert(projCategory);
	if(projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#projCategoryFile').show();
	}else{
		$('div#category').show();
		$('div#font').show();
		$('div#AddInfo').show();
		$('div#projCategoryFile').hide();
	}
}

function projectCategoryType() {
	var project = $("#equipmentsave_leadId option:selected").text();
	var projCategory = $("#equipmentsave_projCategory option:selected").text();
	if(project.toLowerCase() === "netbase" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').show();
		$('div#metaXlsx').show();
		$('div#priceBandXml').show();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').hide();
		$('div#outputFile').hide();
		$('div#Mathml').hide();
	}
}
