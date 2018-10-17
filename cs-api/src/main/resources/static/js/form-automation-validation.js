var script = function(){
	
	$().ready(function(){
		$('#automationInputs').validate({
			rules:{
				automationName:{
				 required: true
             },
             manualMetrics: {
            	 required: true
             },
             manualPages: {
            	 required: true
             },
             automationMetrics: {
            	 required: true
             },
             automationPages: {
            	 required: true
             },
             toolDescription: {
            	 required: true
             },
			},
			messages: {
				automationName:{
					required: "Please enter a automation name."
				},
				manualMetrics:{
					required: "Please enter a manual metrics."
				},
				manualPages:{
					required: "Please enter a manual pages."
				},
				automationMetrics:{
					required: "Please enter a automation metrics."
				},
				automationPages:{
					required: "Please enter a automation pages."
				},
				toolDescription:{
					required: "Please enter the automation description."
				}
			}
		});
	});
}();