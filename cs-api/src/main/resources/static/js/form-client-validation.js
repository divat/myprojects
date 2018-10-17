var script = function(){
	
	$().ready(function(){
		$('#clientInputs').validate({
			rules:{
             clientName: {
            	 required: true
             }
			},
			messages: {
				clientName:{
					required: "Please enter a client name."
				}
			}
		});
	});
}();