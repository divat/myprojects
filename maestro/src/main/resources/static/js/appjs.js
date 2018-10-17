function fnSaveValidation(type)
{
	var errorflag ="0";
	
	if(document.getElementById("generateproject_projectName").value == "")
		{
			$("#generateproject_projectName").css("border-color","red");
			errorflag=1;
		}
	fnFormValidation("1");
	alert(errorflag);
	if(errorflag=="1")
	{
		alert("Mandantory fields are empty!");
		return false;
	}
}

function fnFormValidation(type)
{
	var errorflag = "0";
	

	if(document.getElementById("generateproject_projectName").value == "")
		{
			$("#generateproject_projectName").css("border-color","red");
			errorflag=1;
		}
	
	for (i = 0; i < document.forms[0].elements.length; i++) 
	{
		
		if (document.forms[0].elements[i].name) 
		{
			
					//validation based on types
			var value = document.forms[0].elements[i].value.trim();
				   
			  //if(document.getElementById('divError_'+document.forms[0].elements[i].name) !=null)
			 // document.getElementById('divError_'+document.forms[0].elements[i].name).setAttribute("class", "");
			  /*if(value !="")
			  {	   
				  valid = val(value,document.forms[0].elements[i].getAttribute("validation-type"));
					   
					  try
					  {
						  document.getElementById('divError_'+document.forms[0].elements[i].name).innerHTML='';
					   }catch(e){}
		           		if (valid != "0")
		           		{
		           			document.getElementById('divError_'+document.forms[0].elements[i].name).innerHTML = "<font color='red'>"+valid+"</font>";
		           			document.getElementById('divError_'+document.forms[0].elements[i].name).setAttribute("class", "errordiv");
		        	   	 
		           			return false;
		           		}
				   }*/
			
		           // mandatory checking
		           if (document.forms[0].elements[i].getAttribute("is-mandatory") == "false")
		           {
		        	   if(!fnNotNull(document.forms[0].elements[i].value))
		        		{
		        		   //alert(document.forms[0].elements[i].name.split(',')[0]);
		        			//document.getElementById('divError_'+document.forms[0].elements[i].name).innerHTML = "<font color='red'>field must not be empty</font>";
		        			//document.getElementById('divError_'+document.forms[0].elements[i].name).setAttribute("class", "errordiv");
		        		   $('#'+document.forms[0].elements[i].name.split(',')[0]).css("border-color","red");
		        		   $('#label_'+document.forms[0].elements[i].name.split(',')[0]).html("mandatory field");
		        		   errorflag="1";
		        		}
		           }
		           
		           //data validation
		           if(value !="")
					  {	   
		        	   //alert(value);
		        	   //alert(document.forms[0].elements[i].getAttribute("validation-type"));
						  valid = val(value,document.forms[0].elements[i].getAttribute("validation-type"));
						//alert(valid);	   
							  try
							  {
								  $('#label_'+document.forms[0].elements[i].name.split(',')[0]).html("");
							   }catch(e){}
				           		if (valid != "0")
				           		{
				           			$('#label_'+document.forms[0].elements[i].name.split(',')[0]).html(valid);
				        	   	 
				           			errorflag="1";
				           		}
					  }
		           
		           //rangevalidation
		           if(value !="")
					  {	   
		        	   if(document.forms[0].elements[i].getAttribute("validation-range")!="" && value > 0 && value < 1000)
		        		   {
		        		   		$('#label_'+document.forms[0].elements[i].name.split(',')[0]).html("The value should between 1 to 1000");
			        	   	 
		        		   			errorflag="1";
		        		   }
					  }
		
		}
	}
	
	if(errorflag=="1")
		{
			if(type=="0"){ alert("Mandantory fields are empty!");}
			return false;
		}
}

function fnNotNull(value)
{
	if(value != null && value.trim() != "")
		return true;
	else
		return false;
}
function val(value,type)
{
	str= "0";
	//alert(type);
	switch(type)
	{
		case "numeric":
			//alert('numeric');
			var isInteger_re = /^\s*(\+|-)?\d+\s*$/;
			if(String(value).search (isInteger_re) != -1)
				str = "0";
			else
				str = "numbers only allowed";
			break;
		case "metafield":
            if(value.trim().indexOf(" ")!=-1)
                  str="Space not allowed";
            else
                  str="0";
            break;

		case "alphabetic":
			//alert('alphabetic');
			var exp=new RegExp("[a-zA-Z ]");
			//alert(exp.test(value));
			if(exp.test(value))
				str = "0";
			else
				str ="alphabets only allowed";
			break;
		case "alphabeticwithoutschar":
			// alert('alphabetic');
			//var exp = new RegExp("[a-zA-Z ]");
			//var exp=/[:alpha:]+/;
			//var exp=/^[a-zA-Z][a-zA-Z\\s]+$/;
			var exp = /^[a-zA-Z ]*$/;
			// alert(exp.test(value));
			if (exp.test(value))
				str = "0";
			else
				str = "Alphabets only are allowed";
			break;
		case "alphanumericunderscore":
			var exp = /^([a-z])+([0-9a-z-_]+)$/i;

			if (!exp.test(value))
				str = "Alphanumeric & underscore  only allowed ";
			else
				str = "0";

			break;
		case "alphanumeric1":
			var exp = /^([a-z])+([0-9a-z-_]+)$/i;

			if (!exp.test(value))
				str = "alphabets & numbers only allowed ";
			else
				str = "0";

			break;
		case "alphanumeric":
			//alert('alphanumeric');
			var exp=new RegExp("[A-Za-z0-9]");
			//var exp= /[A-Za-z0-9]$/;
			//alert(exp.test(value) + '-' +value);
			if(!exp.test(value))
				str ="alphabets & numbers only allowed";
			else
				str = "0";
			break;
		case "any":
			//alert('any');
			str = "0";
			break;
		case "decimal":
			//alert('d');
			//var isDecimal_re = /^\s*(\+|-)?((\d+(\.\d+)?)|(\.\d+))\s*$/;
			if(parseFloat(value))
				str = "0";
			else
				str ="decimal numbers only allowed";
			
			break;
		case "email":
			//alert('email');
			//if(value.contains(","))
				//value=value.replace(",",";");
			var regex = new RegExp(",", "g");
			value = value.replace(regex, ';');
			// checks that an input string looks like a valid email address.
			var isEmail_re =/^\s*(?:([A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4})\b\s*)+$/i;
			//var isEmail_re  = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
			//alert(String(value).search(isEmail_re));
			//if(String(value).search(isEmail_re) != -1)
			if(validateMultipleEmailsCommaSeparated(value))
				str = "0";
			else
				str ="not a valid email address";
			break;
		case "webaddress":
			str = "0";
			break;
	}
	
	return str;
}
function validateMultipleEmailsCommaSeparated(value) {
	var result = value.split(";");
	for(var i = 0;i < result.length;i++)
	if(!validateEmail(result[i])) 
	        return false;           
	return true;
	}

function validateEmail(field) {
	var regex=/\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b/i;
	return (regex.test(field)) ? true : false;
	}