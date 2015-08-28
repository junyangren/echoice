(function($) {
	$.ecApp=$.ecApp||{};
	$.ecApp.menuRootId=110;
	$.ecApp.groupRootId=110;
	
	$.fn.selectOptionsUI=function(selVal){
		var options=$('option',this);
		for(var i=0;i<options.length;i++){
			if($(options[i]).val()==selVal){
				$(options[i]).attr('selected','selected');
				break;
			}
		}	
	};
	
	$.ecApp.resizeGridHeight=function(options){
		var defaultOptions={h:45,limit:true,id:'tableListDiv',idArr:[]};
		$.extend(defaultOptions,options);
		var userH=0;
		if(defaultOptions.idArr&&defaultOptions.idArr.length>0){
			$.each(defaultOptions.idArr,function(){
				userH+=$("#"+this).outerHeight();
			});
			userH+=60;
		}else{
			userH=defaultOptions.h;
		}
		//alert(userH);
		//var h=$('body').height();
		var h=$(window).height();
  		h=h-userH;
  		//alert(h);
		if(defaultOptions.limit&&h>=380){
			h=380;
		}
  		$('#'+defaultOptions.id).height(h);
	};
	
 	$.ecApp.setTimeFmt=function(paramJson){
 		if(paramJson.startTime!=''){
 			paramJson.startTime=paramJson.startTime+' 00:00:00';
 		}
 	
 		if(paramJson.endTime!=''){
 			paramJson.endTime=paramJson.endTime+' 23:59:59';
 		}
 	};
 	 	
})(jQuery);

function subForm(dForm){
	var isValid=Validator.Validate(dForm,3);
	if(isValid){
		dForm.submit();
	}			
}