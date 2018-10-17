/**
 *  Tinymce editor source 
 */

(function(){
  $(document).keyup(function(e){
    if(e.keyCode === 27){
      $("input.searchInput").hide();
      tinyMCE.activeEditor.focus();
    }
  });
})();
	
/* alert(document.getElementById('hdnid').value); */	
jQuery.ajaxSetup({async:false});
var css_class_names = [];
(function(){
  $.get(document.getElementById('hdncss').value, function (data, success) {
        //call process to show the result
        if (success == "success"){
            var data_replace = data.replace(/\n/gi, '');

            data_replace = data_replace.replace(/\/\*(.*?)\*\//gi, '');
            data_replace = data_replace.replace(/\}/g, "}\n");
            split_value = data_replace.split('\n');
            //query = "[";
            for (i = 0; i < split_value.length; i++){
                split_value_class = split_value[i].split('{');
                split_value_class[0] = split_value_class[0].replace("{", "");
                split_value_class[0] = split_value_class[0].replace("@font-face", "");
                css_class_names.push(split_value_class[0]);
            }
        }
        else if (success == "error"){
          alert("File Not Loaded");
        }

     }, 'text');

})();

var css_class_names1 = document.getElementById('font_information_list').value.split('\n');
css_class_names = css_class_names.concat(css_class_names1);

	/* tinymce.PluginManager.add('removespaces', function(editor, url) {
		function removeSpaces(){
			var content = tinymce.get('editor').getContent({format: 'text'});
			content=$.trim(content);
			tinymce.editors[0].setContent(content);
		}
		editor.addShortcut('alt+shift+r', 'removespaces', removeSpaces);
	}); */
	
    //Add a plugin to apply indentation
	tinymce.PluginManager.add('indent', function(editor, url) {
		function indent(){
			var selectedContent = editor.selection.getContent({format : 'html'});
			var checking_node = editor.dom.getAttrib(editor.selection.getNode(), 'class');
            if (checking_node == 'noindent'){
            	var indentClass = editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','indent');
            	editor.dom.setAttrib(indentClass, 'class', 'indent');
		    }
           
		}
		editor.addShortcut('alt+i','indent', indent);
	});
	
    //Add a plugin to apply no-indentation
	tinymce.PluginManager.add('noindent', function(editor, url) {
		function indent(){
			var selectedContent = editor.selection.getContent({format : 'html'});
			//var checking_node_name = editor.dom.getNode().nodeName;
			var checking_node = editor.dom.getAttrib(editor.selection.getNode(), 'class');
			
            if (checking_node == 'indent'){
            	var noindentClass = editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','noindent');
            	editor.dom.setAttrib(noindentClass, 'class', 'noindent');
		    }
           
		}
		editor.addShortcut('alt+n','noindent', indent);
	});
    
    //Add a plugin to apply figure group class
	tinymce.PluginManager.add('figgroup', function(editor, url){
		
		function figureGroup(){
			var selectedContent = editor.selection.getContent({format : 'html'});
			var selectedNode =  editor.selection.getNode(selectedContent, 'class');
			
			var figGroup = editor.dom.getAttrib(selectedNode, 'class');
			if(figgroup !== null && figgroup != undefined ){
				var fgClass = editor.dom.getParent(editor.selection.getNode(), 'div').setAttribute('class','figgroup');
				editor.dom.setAttrib(fgClass, 'class', 'figgroup');
				editor.selection.setContent(selectedContent);	
			}
			
		}
	
		editor.addShortcut('alt+g','figgroup', figureGroup);
	});

	//Add a plugin to convert uppercase for tinyemc
	tinymce.PluginManager.add('tolower', function(editor, url) {
		
		function changelowercase() {
			//editor.insertContent(tinymce.activeEditor.selection.getContent({format: 'text'}).toUpperCase());
			editor.insertContent(tinymce.activeEditor.selection.getContent({
				format : 'text'
			}).toLowerCase());
		}
		//editor.addCommand("mcetolower", changelowercase);

		editor.addButton('tolower', {
			image : 'images/lowercase.png',
			tooltip : 'tolower case',
			//shortcut: 'Meta+Alt+L',
			onclick : changelowercase
		});

		editor.addMenuItem('tolower', {	
			image : 'images/lowercase.png',
			text : 'Lower Case',
			context : 'format',
			//shortcut: 'Meta+Alt+L',
			onclick : changelowercase
		});
		editor.addShortcut('alt+l','lowercase', changelowercase);
	});
	//Add a plugin to convert lowercase for tinyemc
	tinymce.PluginManager.add('toUpper', function(editor, url) {
		// Add a button that opens a window
		function changelowercase() {
			//editor.insertContent(tinymce.activeEditor.selection.getContent({format: 'text'}).toUpperCase());
			editor.insertContent(tinymce.activeEditor.selection.getContent({
				format : 'text'
			}).toUpperCase());
		}

		editor.addButton('toUpper', {
			image : 'images/uppercase.png',
			tooltip : 'toupper case',
			//shortcut: 'Meta+Alt+U',
			onclick : changelowercase
		});

		editor.addMenuItem('toUpper', {
			image : 'images/uppercase.png',
			text : 'Upper Case',
			//shortcut: 'Meta+Alt+U',
			context : 'format',
			onclick : changelowercase
		});
		editor.addShortcut('alt+u','Uppercase', changelowercase);
	});
	//Add a plugin to merge multiple para to single para for tinyemc
	tinymce.PluginManager.add('merge', function(editor, url) {
		   
	  function mergeContent(){
	    var selectedContent = editor.selection.getContent();
	    //console.log('merge ~ selected content ~ '+selectedContent);
	    //selectedContent = selectedContent.replace(/(<\/?p>)|(<\/?h[1-6]>)/g, '');
	    selectedContent = selectedContent.replace(/(<p.*?>)|(<\/p>)|(<\/?h[1-6]>)/g, '');
	    editor.selection.setContent("<p>" + selectedContent + "</p>");
	  }
	   //editor.addCommand("mcetolower", changelowercase);
	   

	   editor.addButton('merge', {
	    //icon: 'code',
	    image:'images/merge.png',
	    tooltip: 'merge paragraph',
	    //shortcut: 'Meta+Alt+U',
	    onclick: mergeContent
	  });

	  editor.addMenuItem('merge', {
	    //icon: 'code',
	    image:'images/merge.png',
	    text: 'merge paragraph',
	    //shortcut: 'Meta+Alt+U',
	    context: 'format',
	    onclick: mergeContent
	  });
	 editor.addShortcut('alt+m','merge paragraph',mergeContent);
	});
	//Add a plugin to insert <br /> in tinyemc
	tinymce.PluginManager.add('br', function(editor) {
		  editor.addCommand('InsertBreakRule', function() {
		    editor.execCommand('mceInsertContent', false, '<br />');
		  });

		  editor.addButton('br', {
		    image: 'images/br.png',
		    tooltip: 'Break line',
		    cmd: 'InsertBreakRule'
		  });

		  editor.addMenuItem('br', {
		    icimageon: 'images/br.png',
		    text: 'Break line',
		    cmd: 'InsertBreakRule',
		    context: 'insert'
		  });
		editor.addShortcut('alt+b','br','InsertBreakRule');
		});

	//p class ref
	//Add a plugin to insert <p /> in tinyemc
	tinymce.PluginManager.add('pclassh', function(editor, url) 
	{		   
		function classtilde()
	    {	    		    		    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {	
	    		var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="p"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="p">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='p'>" + selectedContent.trim() + "</p>";	
				}
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','p');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{	    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','p');
	        }	    	
	    }
	    
	    function classh1()
	    {    		    		    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   			    		
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="h1"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="h1"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="h1">');					    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    
			    	return_text="<p class='h1'>" + selectedContent.trim() + "</p>";	
				}				
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','h1');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','h1');
	        }	    	
	    }
	    
	    function classh2()
	    {	    
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {    		
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="h2"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="h2"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="h2">');				    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='h2'>" + selectedContent.trim() + "</p>";	
				}				
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','h2');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','h2');
	        }
	    }
	    
	    function classh3()
	    {	    
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {    				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="h3"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="h3"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="h3">');				    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='h3'>" + selectedContent.trim() + "</p>";	
				}				
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','h3');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','h3');
	        }
	    }
	    
	    function classh4()
	    {
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {    				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="h4"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="h4"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="h4">');			    				    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='h4'>" + selectedContent.trim() + "</p>";	
				}								
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','h4');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','h4');
	        }
	    }  

	    function classh5()
	    {	    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {    				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="deck"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="deck"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="deck">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='deck'>" + selectedContent.trim() + "</p>";	
				}				
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','deck');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','deck');
	        }
	    }
	    
	    function classh6()
	    {	    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="byline"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="byline"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="byline">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='byline'>" + selectedContent.trim() + "</p>";	
				}							
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','byline');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','byline');
	        }
	    }   
	    
	    function pullQuote()
	    {	    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {    				
	    		//alert("pullqutetext1:"+selectedContent);
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
					//alert("pullqutetext:"+selectedContent);
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="pullQuote"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="pullQuote">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='pullQuote'>" + selectedContent.trim() + "</p>";	
				}				
				
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','pullQuote');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','pullQuote');
	        }
	    }    
	    function captionTag()
	    {	    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="caption"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="caption"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="caption">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='caption'>" + selectedContent.trim() + "</p>";	
				}		
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','caption');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','caption');
	        }
	    }  
	    function creditTag()
	    {	    	
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="credit"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="credit"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="credit">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='credit'>" + selectedContent.trim() + "</p>";	
				}					
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','credit');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','credit');
	        }
	    }    
	    function sidebarTag()
	    {	 
		var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   				    	
		    	var return_text;  					    			    			    	
			    return_text="<aside>" + selectedContent.trim() + "</aside>";			
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }   	
	    	/*var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="sidebar"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="sidebar"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="sidebar">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='sidebar'>" + selectedContent.trim() + "</p>";	
				}		
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','sidebar');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','sidebar');
	        }	*/    	
	    }       

	    function orderListTag()
	    {	    		    
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {   				    	
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="orderList"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="orderList"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="orderList">');			    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    
			    	return_text="<p class='orderList'>" + selectedContent.trim() + "</p>";	
				}		
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','orderList');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','orderList');
	        }	
	    }      
	    function unorderListTag()
	    {	    		
	    	var selectedContent = editor.selection.getContent({format: 'html'});	    	
	    	if(selectedContent && selectedContent.length > 0) 
		    {    				
		    	var return_text;    		
			    if(selectedContent.indexOf('<p')>-1)
				{
			    	//selectedContent = selectedContent.replace(/(class="(.*?)")/g, 'class="unorderedList"');
			    	selectedContent = selectedContent.replace(/(<p class="(.*?)")/g, '<p class="unorderedList"');
			    	selectedContent = selectedContent.replace(/(<p>)/g, '<p class="unorderedList">');				    	
			    	return_text=selectedContent.trim();		
				}
			    else
				{			    	
			    	return_text="<p class='unorderedList'>" + selectedContent.trim() + "</p>";	
				}		
		    	editor.execCommand('mceInsertContent', 0, return_text);		      
		    }
		    else if(editor.selection.getNode().nodeName.trim() == "P") 
		    {
		    	editor.selection.getNode().removeAttribute('class');
		    	editor.selection.getNode().setAttribute('class','unorderedList');
		    }
		    else if((editor.selection.getNode().nodeName.trim() == "STRONG") || (editor.selection.getNode().nodeName.trim() == "EM"))
			{		    			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');			    			    
			    editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class','unorderedList');
	        }    	
	    }     
	 //28Sept2016
	     function textblockopen()
	     {
	    	//editor.execCommand('mceInsertContent', 0, '<p class="textblock">[Textblock-St]</p>');
                editor.execCommand('mceInsertContent', 0, '<p class="textblock">[TB-]</p>');
	     }
	     function textblockclose()
	     {
	    	editor.execCommand('mceInsertContent', 0, '<p class="textblock">[Textblock-En]</p>');
	     }	

            function removeclass()
	    {
	    	//editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class'); 
	            if(editor.dom.getParent(editor.selection.getNode(), 'P')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');    
		    }
		    else if(editor.dom.getParent(editor.selection.getNode(), 'h1')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'h1').removeAttribute('class');    
		    }
		    else if(editor.dom.getParent(editor.selection.getNode(), 'h2')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'h2').removeAttribute('class');    
		    } 
		    else if(editor.dom.getParent(editor.selection.getNode(), 'h3')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'h3').removeAttribute('class');    
		    } 
		    else if(editor.dom.getParent(editor.selection.getNode(), 'h4')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'h4').removeAttribute('class');    
		    }
		    else if(editor.dom.getParent(editor.selection.getNode(), 'h5')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'h5').removeAttribute('class');    
		    }
		    else if(editor.dom.getParent(editor.selection.getNode(), 'h6')!=null)
		    {
		    	editor.dom.getParent(editor.selection.getNode(), 'h6').removeAttribute('class');    
		    }     
	    }

                function removeAside()
		{
		    if(editor.dom.getParent(editor.selection.getNode(), 'aside')!=null)
		    {		    			    
			var selectedContent = editor.dom.getParent(editor.selection.getNode(), 'aside').innerHTML;				
			editor.dom.setOuterHTML(editor.dom.getParent(editor.selection.getNode(), 'aside'), selectedContent);				
		    }
		}
	    
                function showregex()
		{
        	// Open window
        	editor.windowManager.open({
        	    title: 'Replace(REGEX)',
        	        body: [
        	            {type: 'textbox', name: 'Find', label: 'Find'},
        	            {type: 'textbox', name: 'Replace', label: 'Replace'}
        	        ],
        	        onsubmit: function(e) {
        	            // Insert content when the window form is submitted
        	            var findtext=e.data.Find;
        	            var replacetext=e.data.Replace;

        	            //alert("findtext:"+findtext);
        	            //alert("replacetext:"+replacetext);        	            
        	            var contenttiny=editor.getContent();        	                    	            
        	            var f1 = new RegExp(findtext,'gi');    	                    	                    	            
        	            contenttiny = contenttiny.replace(f1, replacetext);
        	            editor.setContent(contenttiny);
        	        }
        	});
		}
	   //end 
	   
	   editor.addShortcut('alt+p','pclasstilde', classtilde);	   
	   editor.addShortcut('alt+1','pclassh',classh1);
	   editor.addShortcut('alt+2','pclassh1',classh2);
	   editor.addShortcut('alt+3','pclassh2',classh3);
	   editor.addShortcut('alt+4','pclassh3',classh4);	 
	   for (var i = 1; i <= 6; i++) 
	   {
		   editor.addShortcut('ctrl+shift+' + i, '', ['FormatBlock', false, 'h' + i]);
	   }
	   editor.addShortcut('alt+d','pclassh4',classh5);
	   editor.addShortcut('alt+b','pclassh5',classh6);	   
	   editor.addShortcut('alt+q','pullQuote',pullQuote);
	   editor.addShortcut('alt+t','captionTag',captionTag);
	   editor.addShortcut('alt+c','creditTag',creditTag);
	   editor.addShortcut('alt+s','sidebarTag',sidebarTag);	    //need to change and clear formatting shortcut 
	   editor.addShortcut('alt+a','orderList',orderListTag);
	   editor.addShortcut('alt+x','unorderList',unorderListTag);
	   editor.addShortcut('alt+r','removeclass',removeclass); 
	   editor.addShortcut('alt+f',"insert1",textblockopen);
	   //editor.addShortcut('alt+e',"insert2",textblockclose);  
           editor.addShortcut('ctrl+alt+r','removeaside',removeAside);
            editor.addShortcut('ctrl+d','showregex',showregex);	 	  	     		   
	});

	
	function shortcuts(editor) 
	{		
		
		/* editor.on('keyDown', function(e) {
			console.debug('Key up event: ' + e.keyCode);
	          if (e.keyCode == 9 && !e.ctrlKey){ // tab pressed
	        	//alert('test');
	        	//editor.execCommand('mceInsertRawHTML', false, '\x09'); // inserts tab
	        	var content = editor.selection.getNode();
	        	
	        	//alert(content);
	            e.preventDefault();
	            e.stopPropagation();
	            return false;
	          }
        }); */

        //Need to update server
        editor.on('change', function(e) {
            var editor = tinymce.get('editor');
            var content = editor.getContent();
            localStorage.setItem('ope3', content);
        });
        editor.addButton('CMTool', {
            type: 'menubutton',
            text: 'Retrieve Backup',
            icon: false,
            menu: [{
              text: 'Restore',
              onclick: function() 
              {                              
                  var content = editor.getContent();
                  content=localStorage.getItem('ope3');
                  editor.setContent(content);
              }
            },
            {
                text: 'Backup',
                onclick: function() 
                {                                
                    var content = editor.getContent();
                    localStorage.setItem('ope3', content);                                
                }
              }

            ]
          });
          //end 
 	    
//Zinio Guide

	editor.addMenuItem('example', {
		text: 'Zinio Guide',
		context: 'tools',
		onclick: function() {
			// Open window with a specific url
			editor.windowManager.open({
				title: 'Zinio PRISM-XML Article Guidelines',
//				url: 'http://www.tinymce.com',
				url: 'codeMantra_Prism_XML_Article_Guidelines_2015_05_29.pdf',

				width: 500,
				height: 550,
				buttons: [{
					text: 'Close',
					onclick: 'close'
				}]
			});
		}
	});

	    editor.addButton('shortcutbtn', {
	      type: 'menubutton',
	      text: 'Shortcuts Help',
	      icon: false,
	      menu: [    
	   	  {
		        text: 'H1',
		        shortcut: 'ctrl+shift+1'	        
	         },
	         {
		        text: 'H2',
		        shortcut: 'ctrl+shift+2'	        
	         },
	         {
		        text: 'H3',
		        shortcut: 'ctrl+shift+3'	        
	         },
	         {
		        text: 'H4',
		        shortcut: 'ctrl+shift+4'	        
	         },
	         {
		        text: 'H5',
		        shortcut: 'ctrl+shift+5'	        
	          },
	          {
		        text: 'H6',
		        shortcut: 'ctrl+shift+6'	        
	          }, 
	          /* {
		        text: 'p.p',
		        shortcut: 'alt+p'	        
	          }, 
	          {
		        text: 'p.h1',
		        shortcut: 'alt+1'
	          }, 
	          {
		        text: 'p.h2',
		        shortcut: 'alt+2'
		      }, 
	          {
		        text: 'p.h3',
		        shortcut: 'alt+3'
		  	  }, 
	       {
		        text: 'p.h4',
		        shortcut: 'alt+4'
		   }, 
		   {
			    text: 'p.deck',
			    shortcut: 'alt+d'
		   }, 
		   {
			    text: 'p.byline',
			    shortcut: 'alt+b'
		   }, 
		   {
			    text: 'p.pullQuote',
			    shortcut: 'alt+q'
		   },  */
		   {
			    text: 'p.caption',
			    shortcut: 'alt+t'
		   }, 
		   {
			    text: 'p.credit',
			    shortcut: 'alt+c'
		   }, 
		   {
			    text: 'aside',
			    shortcut: 'alt+s'
		   }, 
		   /* {
			    text: 'p.orderList',
			    shortcut: 'alt+a'
		   }, 
		   {
			    text: 'p.unorderedList',
			    shortcut: 'alt+x'
		   }, 
		   {
			    text: 'TextBlock Open',
			    shortcut: 'alt+f'
		   }, 
		   {
			    text: 'Remove Style P',
			    shortcut: 'alt+r'
		   }, ///need to update in server */
		   {
			    text: 'lowercase',
			    shortcut: 'alt+l'
		   }, 
		   {
			    text: 'UPPERCASE',
			    shortcut: 'alt+u'
		   }, 
		   {
			    text: 'Clear Formatting',
			    shortcut: 'alt+k'
		   }, 
		   {
			    text: 'Smallcaps',
			    shortcut: 'ctrl+shift+k'
		   }, 
		   {
			    text: 'Strikethrough',
			    shortcut: 'ctrl+alt+s'
		   }, 
		   {
			    text: 'Superscript',
			    shortcut: 'ctrl+alt+6'
		   }, 
		   {
			    text: 'Subscript',
			    shortcut: 'ctrl+alt+9'
		   }, 
		   {
			    text: 'Paragraph',
			    shortcut: 'alt+9'
		   }, 
		   {
			    text: 'TextBlock',
			    shortcut: 'ctrl+alt+q'
		   }, 
		   /* {
			    text: 'Remove Aside',
			    shortcut: 'ctrl+alt+r'
		   }, */
		   {
			   text: 'Indent',
			   shortcut:'alt+i'
		   },
		   {
			   text: 'Noindent',
			   shortcut: 'alt+n'
		   },
		   {
			   text: 'Insert/Edit image',
			   shortcut: 'alt+e'
		   },
		   {
			   text: 'Figure Group',
			   shortcut: 'alt+g'
		   }]
	    });
	    
	    
	    editor.on('keyup', function (e) { 
            if(e.keyCode == 17){
              if (!$("div#dynamic_input").find("input.searchInput").length) {
                var inputHTML = "<div class='bcolor'><input name='search' value='' class='searchInput'/></div>";
                $(inputHTML).appendTo("div#dynamic_input");
                $("input.searchInput:last").focus();
              }
              $("input.searchInput").autocomplete({
                source: css_class_names,
                change: function(){
                    $("input.searchInput").remove();
                    return  false;
                },
                close: function(e,ui) {  
                	if (this.value.startsWith('@')){
                        var splitting_value = this.value.replace('@', '');
                        var element = editor.dom.getRoot();
                        var child = element.firstChild;
                        var checking_node = editor.dom.getAttrib(editor.selection.getNode(), 'class');
                        var checking_node_name = editor.selection.getNode().nodeName;
                        
                        if (editor.selection.getContent({format: 'html'}).length > 0){
                          editor.selection.setContent('<span class=\"' + splitting_value + '\">'+editor.selection.getContent({format: 'html'})+ '</span>');
                        }else{
                          if (/(\s)+/.exec(checking_node)){
                            while(child){
                              if (child.nodeName === 'H1' || child.nodeName === 'H2' || child.nodeName === 'H3' || child.nodeName === 'H4' || child.nodeName === 'H5' || child.nodeName === 'H6'){
                                alert(child.nodeName);
                                var get_att_value = editor.dom.getAttrib(child, 'class');
                                get_att_value = get_att_value.split(" ");
                                if (checking_node_name === child.nodeName){
                                 editor.dom.setAttrib(child, 'class', get_att_value[0] + ' ' + splitting_value);
                                }
                              }
                                child = child.nextSibling;
                            }
                          }else{
                            while(child){
                              if (child.nodeName === 'H1' || child.nodeName === 'H2' || child.nodeName === 'H3' || child.nodeName === 'H4' || child.nodeName === 'H5' || child.nodeName === 'H6'){
                                var get_att_value = editor.dom.getAttrib(child, 'class');
                                if (checking_node_name === child.nodeName){
                                 editor.dom.setAttrib(child, 'class', get_att_value + ' ' +splitting_value);
                                }
                              }
                              child = child.nextSibling;
                            }
                          }
                        }
                        tinyMCE.activeEditor.focus();
                      }else{
                      	var splitting_value = this.value.split("\.");
                        if (editor.selection.getContent({format: 'html'}).length > 0){
                          if (this.value.startsWith("div")){
                            var new_edi = editor.selection.getContent({format: 'html'});
                            new_edi = new_edi.replace(/\<div class=\"(.*?)\"\>/gi, '');
                            new_edi = new_edi.replace(/\<\/div\>/gi, '');
                            
                            editor.selection.setContent('<div class=\"'+splitting_value[1] +'\">' + new_edi + '</div>');
                          }else if (this.value.startsWith("h")){
                            alert("Please Don't Select Multiple Nodes");
                          }else if (this.value.startsWith("span")){
                        	  editor.selection.setContent('<span class=\"'+ splitting_value[1] +'\">'+editor.selection.getContent({format: 'html'}) + '</span>');
                          }else{
                            var new_edi = editor.selection.getContent({format: 'html'});
                            if (new_edi.indexOf('<p class') == -1){
                                new_edi = new_edi.replace(/<p>/gi, ('<p class=\"'+splitting_value[1] +'\">'));
                            }
                            else{
                              new_edi = new_edi.replace(/<p class=\"(.*?)\">/gi, ('<p class=\"'+splitting_value[1]
                              )+'\">');
                            }
                            editor.selection.setContent(new_edi);
                          }
                        }
                        else{
                          if (this.value.startsWith("h")){
                        	  alert(this.value);
                              editor.selection.getNode().removeAttribute('class'); 
                              editor.selection.getNode().setAttribute('class', splitting_value[1]);
                              tinyMCE.activeEditor.focus();
                              editor.dom.rename(editor.selection.getNode(), splitting_value[0]).toLowerCase();
                          }else{
                        	  
                            if (editor.selection.getNode().nodeName.trim() == 'P'){
                              editor.selection.getNode().removeAttribute('class');
                              editor.selection.getNode().setAttribute('class', splitting_value[1]);
                            }else if (editor.dom.getParent(editor.selection.getNode(), 'P')){
                                editor.dom.getParent(editor.selection.getNode(), 'P').removeAttribute('class');
                                editor.dom.getParent(editor.selection.getNode(), 'P').setAttribute('class', splitting_value[1]);
                            }
                          }//inner else end
                        }//main else end
                      }
                    if (e.which === 13){
                    	tinyMCE.activeEditor.focus();
                    }
                  }
              });
            }
          });
	 }
	//end of p class ref

	tinymce.PluginManager.add('enref', function(editor, url) {
		   
	    function enref(){
	    var selectedContent = editor.selection.getContent({format: 'html'});
	    console.log('enref ~ selected content ~ '+selectedContent);
	    //selectedContent = selectedContent.replace(/(<\/?p>)|(<\/?h[1-6]>)/g, '');
	    //selectedContent = selectedContent.replace(/(<p.*?>)|(<\/p>)|(<\/?h[1-6]>)/g, '');
	    editor.selection.setContent("<enref>" + selectedContent + "</enref>");
	  }
	   //editor.addCommand("mcetolower", changelowercase);

	   editor.addButton('enref', {
	    //icon: 'code',
	    image:'images/enref.png',
	    tooltip: 'enref',
	    //shortcut: 'Meta+Alt+U',
	    onclick: enref
	  });

	});
	
	tinymce.PluginManager.add('smallcaps', function(editor, url) {
		   
	    function smallcaps(){
	    var selectedContent = editor.selection.getContent({format: 'html'});
	    console.log('smallcaps ~ selected content ~ '+selectedContent);
	    //selectedContent = selectedContent.replace(/(<\/?p>)|(<\/?h[1-6]>)/g, '');
	    //selectedContent = selectedContent.replace(/(<p.*?>)|(<\/p>)|(<\/?h[1-6]>)/g, '');
	    editor.selection.setContent("<small>" + selectedContent + "</small>");
	  }
	   //editor.addCommand("mcetolower", changelowercase);

	   editor.addButton('smallcaps', {
	    //icon: 'code',
	    image:'images/smallcaps.png',
	    tooltip: 'smallcaps',
	    //shortcut: 'Meta+Alt+U',
	    onclick: smallcaps
	  });
	editor.addShortcut('alt+k','Clear formatting', 'RemoveFormat');
	editor.addShortcut('ctrl+shift+k','smallcaps',smallcaps);
	editor.addShortcut('ctrl+alt+s','Strikethrough', 'Strikethrough');
	editor.addShortcut('ctrl+alt+6','Superscript', 'Superscript');
	editor.addShortcut('ctrl+alt+9','Subscript', 'Subscript');
	editor.addShortcut('alt+9', '', ['FormatBlock', false, 'p']);
        //22sept2016 vanarajan
	/*editor.addShortcut('alt+1', '', ['FormatBlock', false, 'h1']);
	editor.addShortcut('alt+2', '', ['FormatBlock', false, 'h2']);
	editor.addShortcut('alt+3', '', ['FormatBlock', false, 'h3']);
	editor.addShortcut('alt+4', '', ['FormatBlock', false, 'h4']);
	editor.addShortcut('alt+5', '', ['FormatBlock', false, 'h5']);
	editor.addShortcut('alt+6', '', ['FormatBlock', false, 'h6']);*/
	});
	tinymce.PluginManager.add('textblock', function(editor, url) {
		   
	    function textblock(){
	    var selectedContent = editor.selection.getContent();
	    console.log('textblock ~ selected content ~ '+selectedContent);
	    editor.selection.setContent("<div class='textblock'>" + selectedContent + "</div>");
	  }
	   //editor.addCommand("mcetolower", changelowercase);

	   editor.addButton('textblock', {
	    //icon: 'code',
	    image:'images/merge.png',
	    tooltip: 'Text Block',
	    //shortcut: 'Meta+Alt+U',
	    onclick: textblock
	  });

	  editor.addMenuItem('textblock', {
	    //icon: 'code',
	    image:'images/merge.png',
	    text: 'textblock',
	    //shortcut: 'Meta+Alt+U',
	    context: 'format',
	    onclick: textblock
	  });

	  //shortcut for merger para
	  editor.addShortcut('ctrl+Alt+q','textblock',textblock);
	  //editor.addShortcut('alt+m','merge paragraph',textblock);
	});
	tinyMCE.PluginManager.add('stylebuttons', function(editor, url) {
		  ['pre', 'p', 'code', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6'].forEach(function(name){
		   editor.addButton("style-" + name, {
		       tooltip: "Toggle " + name,
		         text: name.toUpperCase(),
		         onClick: function() { editor.execCommand('mceToggleFormat', false, name); },
		         onPostRender: function() {
		             var self = this, setup = function() {
		                 editor.formatter.formatChanged(name, function(state) {
		                     self.active(state);
		                 });
		             };
		             editor.formatter ? setup() : editor.on('init', setup);
		         }
		     })
		  });
		   //editor.addShortcut('alt+p', '', ['FormatBlock', false, 'orderedlist']);
		   // BlockFormat shortcuts keys
		   for (var i = 1; i <= 6; i++) {
		    editor.addShortcut('ctrl+shift+' + i, '', ['FormatBlock', false, 'h' + i]);
		   }
		});
	
	tinymce.PluginManager.add('ShortcutCBS', function(editor, url) {
	       
	      function block_quote_textblock(){
	      var selectedContent = editor.selection.getContent({format: 'html'});
	      selectedContent = selectedContent.replace(/<p(.*?)>/g, '<p>');
	      editor.selection.setContent("<div class=\"blockquote\">" + selectedContent + "</div>");
	    }
	    function caption_textblock(){
	      var selectedContent = editor.selection.getContent({format: 'html'});
	      selectedContent = selectedContent.replace(/<p(.*?)>/g, '<p>');
	      editor.selection.setContent("<div class=\"caption\">" + selectedContent + "</div>");
	    }
	    function sidebar_textblock(){
	      var selectedContent = editor.selection.getContent({format: 'html'});
	      selectedContent = selectedContent.replace(/<p(.*?)>/g, '<p>');
	      editor.selection.setContent("<div class=\"sidebar\">" + selectedContent + "</div>");
	    }
	    editor.addShortcut('ctrl+Alt+e','',block_quote_textblock);
	    editor.addShortcut('ctrl+Alt+c','',caption_textblock);
	    editor.addShortcut('ctrl+Alt+b','',sidebar_textblock);
	  });
	
	//alert(document.getElementById('hdncss').value);
	//document.getElementById('hdnimg').value = eval(document.getElementById('hdnimg').value);
	
	tinymce.init({
		selector : '#editor',
		//image_prepend_url:'workingfolder/'+document.getElementById('hdnid').value+'/images',
		/*image_list: function(success) {
		success([document.getElementById('hdnimg').value]);},*/
		image_list: eval(document.getElementById('hdnimg').value),
		plugins : 'searchreplace,fullscreen,link,code,image, media,preview,importcss,replaceclass,tolower,toUpper,charmap,autolink,merge,hr,br,enref,smallcaps,stylebuttons,anchor,textblock,pclassh,table,textcolor,directionality,print,spellchecker,ShortcutCBS,indent,noindent,figgroup,tiny_mce_wiris',
		toolbar : ['undo redo | bold italic tiny_mce_wiris_formulaEditor superscript subscript underline strikethrough tolower toUpper | link unlink | image media table | fullscreen preview code | removeformat | forecolor backcolor | ltr rtl print |',
				   'spellchecker | searchreplace replaceclass | charmap | merge br style-p enref smallcaps anchor textblock | styleselect fontselect fontsizeselect | shortcutbtn CMTool'],
		entity_encoding : "numeric",
		//content_css : ['./projectcss/Default/zinio.css'],
		setup: shortcuts,
		content_css : [document.getElementById('hdncss').value],
		importcss_append : true,
		custom_elements :'~enref',
	});
	/*$("#buttonId").click(function(){
		var content = tinymce.get('editor').getContent({format: 'text'});
		content=$.trim(content);
	});*/
	
	
	