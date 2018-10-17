var script = function(){
	
	$().ready(function(){
		$('#update-chapter').validate({
			rules:{
				customerId:{
				 required: true
             },
             jobId: {
            	 required: true
             },
             chapterName: {
            	 required: true
             }
			},
			messages: {
				customerId:{
					required: "Please enter a customer id."
				},
				jobId:{
					required: "Please enter a job id."
				},
				chapterName:{
					required: "Please enter a chapter name."
				}
			}
		});
	});
}();