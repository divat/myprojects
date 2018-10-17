/**
 * Add ACS - Validation & Configuration
 */

$('#projCatId').change(function() {
	 var data = document.getElementById("projCatId").value;
	
	 $("#projectSelect").get(0).options.length = 0;
     $("#projectSelect").get(0).options[0] = new Option("Loading projects", "-1"); 
	 
    $.ajax({
        type:"GET",
        url : "loadproject?projCatId="+document.getElementById("projCatId").value,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success : function(data, status) {
        	$("#projectSelect").get(0).options.length = 0;
            $("#projectSelect").get(0).options[0] = new Option("Choose a project", "-1");	
        	//var returnedData = JSON.stringify(data.leadsList);
        	$.each(data.leadsList,function(i,obj){
        		$("#projectSelect").get(0).options[$("#projectSelect").get(0).options.length] = new Option(obj.leadName, obj.leadId);
			});  
        },
        error: function() {
            alert('Failed to load the project.');
        }
    });
});

$('#projectSelect').change(function() {
  $("#categoryId").get(0).options.length = 0;
  $("#categoryId").get(0).options[0] = new Option("Loading category", "-1"); 
	 
   $.ajax({
       type:"GET",
       url : "loadcategory?projectId="+document.getElementById("projectSelect").value,
       contentType: "application/json; charset=utf-8",
       dataType: "json",
       success : function(data, status) {
    	   $("#categoryId").get(0).options.length = 0;
    	   $("#categoryId").get(0).options[0] = new Option("Choose a category", "-1");	
       	   var returnedData = JSON.stringify(data.outputCategoryList);
	       $.each(data.outputCategoryList,function(i,obj){
	    	   $("#categoryId").get(0).options[$("#categoryId").get(0).options.length] = new Option(obj.categoryName, obj.categoryId);
	       });  
       },
       error: function() {
           alert('Failed to load the category.');
       }
   });
});

$('div#mathJax').hide();
$('div#chaLink').hide();
function categoryrefresh(){
	window.location.href= "/ACS/equipmentadddisplay.action?categoryid="+document.getElementById("equipmentsave_categoryId").value+"&id="+document.getElementById("equipmentsave_equipmentId").value+"&leadid="+document.getElementById("equipmentsave_leadId").value;
}

/**
 * 
 * @returns {Boolean}
 */
function fmvalidation()
{
	
 	var ret= true;  
 	var parent = document.getElementById("bookXls");
 	var child = parent.children[1].value;
 	
 	var projCategory =$("#equipmentsave_projCategory option:selected").text();
    var convtype=$("#equipmentsave_leadId option:selected").text();
    var category = $("#equipmentsave_categoryId option:selected").text();
    var clientId = $("#equipmentsave_clientId option:selected").text();
    var inputtxt = document.getElementById("equipmentsave_adobeEIsbn").value;
    var mobiInputTxt = document.getElementById("equipmentsave_mobiIsbn").value;
    var issueType = $("#issueType option:selected").text();
    
    var project = $("#projectSelect option:selected").text();
    
    if(project == "Choose a project"){
    	alert("Please select a project.")
    	ret = false;
    }
    
    if($("#equipmentsave_adobeEIsbn").val() != ""){
    	var numbers = /^[0-9]+$/;  
        if(inputtxt.match(numbers)){  
           if(inputtxt.length < 13){
        	   alert("Adobe eISBN should be 13 digit.");
                return false;
           }else{
        	   return true;   
           }
        	  
        }else {  
        	alert('Please enter valid Adobe eISBN.');  
        	return false;  
        }  
    }
    
    if($("#equipmentsave_mobiIsbn").val() != ""){
    	var numbers = /^[0-9]+$/;  
        if(mobiInputTxt.match(numbers)){  
           if(mobiInputTxt.length < 13){
        	   alert("Mobi ISBN should be 13 digit.");
                return false;
           }else{
        	   return true;   
           }
        	  
        }else {  
        	alert('Please enter valid Mobi ISBN.');  
        	return false;  
        }  
    }
    
    if (clientId === ""){
    	ret = false;
    }
    
    if(projCategory.toLowerCase() === 'validation' && convtype.toLowerCase() === 'imf'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_myFile").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'validation' && convtype.toLowerCase() === 'zinio'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_myFile").val() === "") || (issueType === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'validation' && convtype.toLowerCase() === 'tandf bits-xml'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_myFile").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'jbl alt-text' && category.toLowerCase() === 'xlsx'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "")){
			ret =false;
	 	}
    }
 
    
    if(projCategory.toLowerCase() === 'validation' && convtype.toLowerCase() === 'content qa'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_myFile").val() === "") || ($("#equipmentsave_fileMetaXml").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'mobi' && category.toLowerCase() === 'epub/mobi'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "")
    			|| ($("#equipmentsave_mobiIsbn").val() === "")){
			ret =false;
	 	}
    }
 
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'nlm xml' && category.toLowerCase() === 'cm xml'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'cm xml' && category.toLowerCase() === 'netbase xml'){
    	if(($("#equipmentsave_leadId").val() === "") || ($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "") || ($("#equipmentsave_fileMetaXlsx").val() === "")
    			|| ($("#equipmentsave_adobeEIsbn").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'mathml enrichment'){
    	if(($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'mathml enrichment'){
    	if(($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === 'conversion' && convtype.toLowerCase() === 'vst xml'){
    	if(($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "") 
				 || ($("equipmentsave_fileCss").val === "")){
			ret =false;
	 	}
    }
    
    if(projCategory.toLowerCase() === "validation" && convtype.toLowerCase() === "pxe qa"){
		if($("#equipmentsave_myFile").val() === ""){
			ret =false;
	 	}
	}
    
	if(projCategory.toLowerCase() === "validation" && convtype.toLowerCase() === "teton"){
		if($("#equipmentsave_myFile").val() === ""){
			ret =false;
	 	}
	}
	
	/**
	 * Updation on 06.07.2017
	 */
	
	if(projCategory.toLowerCase() === "validation" && convtype.toLowerCase() === "CssVsHtml"){
		if($("#equipmentsave_myFile").val() === ""){
			ret =false;
	 	}
	}
	
	if(projCategory.toLowerCase() === "conversion" && convtype.toLowerCase() === "RefLink"){
		if($("#equipmentsave_myFile").val() === ""){
			ret =false;
	 	}
	}
	
	//Updation end 06.07.2017
	
	if(projCategory.toLowerCase() === "validation" && convtype.toLowerCase() === "sands"){
		if(($("#equipmentsave_myFile").val() === "") || ($("#equipmentsave_fileXls").val() === "") || ($("#equipmentsave_equipmentName").val() === "")){
			ret =false;
	 	}
	}
	if(projCategory.toLowerCase() === "validation" && convtype.toLowerCase() === "cm shorttags"){
		if(($("#equipmentsave_fileXls").val() === "") || ($("#equipmentsave_myFile").val() === "") || ($("#equipmentsave_equipmentName").val() === "")){
			ret =false;
	 	}
	}
	if(projCategory.toLowerCase() == "conversion" && convtype.toLowerCase() == "cm shorttags"){
		if(($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "") || ($("#equipmentsave_equipmentName").val() === "")){
			ret =false;
	 	}
	}
	if(projCategory.toLowerCase() === "conversion" && convtype.toLowerCase() === "cm xml" && category.toLowerCase() === 'epub 3'){
		if(($("#equipmentsave_categoryId").val() === "") || ($("#equipmentsave_myFile").val() === "") 
				|| ($("#equipmentsave_fileImage").val() === "") || ($("#equipmentsave_fileMetadata").val() === "")){
			ret =false;
	 	}
	}
	if(projCategory.toLowerCase() === "conversion" && convtype.toLowerCase() === "netbase"){
		if( (child === "") || ($("#equipmentsave_filePriceBands").val() === "") || ($("#equipmentsave_fileMetaXlsx").val() === "")){
			ret =false;
	 	}
	}
	if(projCategory.toLowerCase() === "validation" && convtype.toLowerCase() === "netbase"){
		if((child === "") || ($("#equipmentsave_fileXls").val() === "") || ($("#equipmentsave_filePriceBands").val() === "") 
				|| ($("#equipmentsave_fileMetaXlsx").val() === "")){
			ret =false;
	 	}
	}
 	//Commenting the below code to change the font validation based on lead
    
 	if (ret == false){
 		alert("Mandory field(s) should be entered!!!");
 	}
 	
 	return ret;
 } 
 
 
/**
 * Fetch the output name
 */
function outputname() {
	str = $("#equipmentsave_myFile").val();
	str=str.substring(str.lastIndexOf("\\")+1,str.length);
	str=str.substring(0,str.lastIndexOf("."));
	//alert(str);
	//$("#equipmentsave_equipmentName").val(str.substring(str.lastIndexOf("\\")+1,str.length));
	$("#equipmentsave_equipmentName").val(str);
	
}

/**
 * Check whether the given input file is based
 * on valid ISBN
 * @param text
 * @returns {Boolean}
 */
function checkInputFile(text){
	
	var flag = true;
	var inputFileName = text.value;
	var convtype=$("#equipmentsave_leadId option:selected").text();
	
	if(!(convtype === "Zinio" || convtype === "TandF Page Label" || convtype === "EPUB 2")){
	   //Check input file name length
	   var inputFileLen=inputFileName.substring(inputFileName.lastIndexOf("\\")+1,inputFileName.length);
	   inputFileLen=inputFileLen.substring(0,inputFileLen.lastIndexOf("."));
	   var len = inputFileLen.length;
			
	   if (convtype.toLowerCase() === "reflink"){}
	   else if (convtype.toLowerCase() === "cssvshtml"){}
	   else if (convtype.toLowerCase() === "imf"){}
	   else{
		   if(inputFileLen.toString().match(/^\d{13}$/) == null){
			   alert("Input file name should be 13 digit ISBN.");
			   text.value = "";
			   $("#equipmentsave_equipmentName").val("");
			   flag = false;
		   }
	   }
	   return flag;
	}
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
		alert("Invalid file should be a valid xml file.")
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

/**
 * Display the input fields
 * based on output type chosen
 */
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

/**
 * Display the input fields
 * based on category type chosen
 */
function categoryType(){
	var projCategory = $("#equipmentsave_projCategory option:selected").text();
	var convtype=$("#equipmentsave_leadId option:selected").text();
	var category = $("#equipmentsave_categoryId option:selected").text();
	
	if(convtype.toLowerCase() === "choose a lead..."){
		alert("Please choose the project.")
	}else{
		if(projCategory.toLowerCase() === "validation"){
			$('div#category').hide();
			$('div#font').hide();
			$('div#AddInfo').hide();
			$('div#projCategoryFile').show();
			$('div#adobeEIsbn').hide();
		}
	}
}

/**
 * Display the input fields
 * based on proj category type chosen
 */
function projectCategoryType() {
	var project = $("#equipmentsave_leadId option:selected").text();
	var projCategory = $("#equipmentsave_projCategory option:selected").text();
	var category = $("#equipmentsave_categoryId option:selected").text();
	
	if(project.toLowerCase() === "zinio"){
		$('div#issuetype').show();
	}
	
	if(project.toLowerCase() !== "zinio"){
		$('div#issuetype').hide();
	}
	
	if(project.toLowerCase() === "epub 2" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	
	if(project.toLowerCase() === "tandf page label" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	
	if(project.toLowerCase() === "imf" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		
	} 
	
	if(project.toLowerCase() === "zinio" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		
	} 
	
	if(project.toLowerCase() === "tandf bits-xml" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	} 
	
	if(project.toLowerCase() === "jbl alt-text" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	} 
	
	if(project.toLowerCase() === "pxe qa" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	} 
	if(project.toLowerCase() === "sands" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').show();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	} 
	if(project.toLowerCase() === "teton" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').hide();
		$('div#Mathml').hide();
		$('div#outputFile').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	} 
	
	/**
	 * Updation on 06.07.2017
	 */
	
	if((project.toLowerCase() === "cssvshtml" && ((projCategory.toLowerCase() === "validation") || (projCategory.toLowerCase() === "conversion"))) || 
			(project.toLowerCase() === "reflink" && projCategory.toLowerCase() === "conversion")){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').hide();
		$('div#Mathml').hide();
		$('div#outputFile').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	} 
	
	if (project.toLowerCase() === "reflink" && projCategory.toLowerCase() === "conversion"){
		$('div#chaLink').show();
	}
	
	if (project.toLowerCase() === "reflink" && projCategory.toLowerCase() !== "conversion"){
		$('div#chaLink').hide();
		alert("Please choose Conversion");
	}
	
	if (project.toLowerCase() === "cssvshtml" && projCategory.toLowerCase() !== "validation"){
		$('div#chaLink').hide();
		alert("Please choose Validation Process");
	}
	
	
	//Updation end 06.07.2017
	
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
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	if(project.toLowerCase() === "cm shorttags" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	if(project.toLowerCase() === "vst xml" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').show();
		$('div#AddInfo').show();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	if(project.toLowerCase() === "mathml enrichment" && projCategory.toLowerCase() === "conversion"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	
	if(project.toLowerCase() === "cm xml" && projCategory.toLowerCase() === "conversion" && category.toLowerCase() === "epub 3"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').show();
		$('div#AddInfo').show();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issuetype').hide();*/
	}
	
	if(project.toLowerCase() === "cm xml" && projCategory.toLowerCase() === "conversion" && category.toLowerCase() === "netbase xml"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').show();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').show();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issueType').hide();*/
	}
	
	if(project.toLowerCase() === "nlm xml" && projCategory.toLowerCase() === "conversion" && category.toLowerCase() === "cm xml"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').hide();
		/*$('div#issueType').hide();*/
	}
	
	if(project.toLowerCase() === "mobi" && projCategory.toLowerCase() === "conversion" && category.toLowerCase() === "epub/mobi"){
		$('div#category').show();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').hide();
		$('div#mobiIsbn').show();
		/*$('div#issueType').hide();*/
	}
	
	if(project.toLowerCase() === "content qa" && projCategory.toLowerCase() === "validation"){
		$('div#category').hide();
		$('div#bookXls').hide();
		$('div#metaXlsx').hide();
		$('div#priceBandXml').hide();
		$('div#font').hide();
		$('div#AddInfo').hide();
		$('div#inputFile').show();
		$('div#outputFile').show();
		$('div#Mathml').hide();
		$('div#projCategoryFile').hide();
		$('div#adobeEIsbn').hide();
		$('div#epubMeta').show();
		$('div#mobiIsbn').hide();
		/*$('div#issueType').hide();*/
	}
	
	
	if(category.toLowerCase() === "epub 3"){
		$('div#mathJax').show();
	}
	if(category.toLowerCase() !== "epub 3"){
		$('div#mathJax').hide();
	}
	if (project.toLowerCase() === "tandf page label" && category.toLowerCase() === "epub 3"){
		$('div#mathJax').hide();
	}
	
}

/**
 * Validate the entered value is number
 * @param evt
 * @returns {Boolean}
 */
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}