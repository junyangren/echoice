$(document).ready(function(){
	
	$('#tabs_center').tabs({fit:true,
		border:false,
		plain:false
		/**
		tools:[{
			iconCls:'icon-no',
			//text:'关闭',
			plain:true,
			handler: function(){
				var tabArr=$('#tabs_center').tabs('tabs');
				var title='';
				for(var i=tabArr.length-1;i>=0;i--){
					title=tabArr[i].panel('options').title;
					if(tabArr[i].panel('options').closable){
						$('#tabs_center').tabs('close',title);
					}
				}
			}}
		]**/
	});
	
	$('#menu-accordion-id').accordion({fit:true,border:false});
	//初始化业务菜单树	
	var firstInit=true;
	var setting = {
			async: {
				enable: true,
				url:context+"console/opUms.do?action=tree&rootId="+$.ecApp.menuRootId,
				autoParam:["id", "name"]
			},
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag){
						if (!treeNode.isParent) {
							var url=treeNode.extUrl;
							if(url&&url!=''){
								var reg=/^http[s]*:\/\/[A-Za-z0-9]+/;
								if(reg.test(url)){
									var dForm=document.forms[0];
									dForm.action=url;
									dForm.target='_blank';
									dForm.submit();
								}else{
									if(url.indexOf('?')!=-1){
										url+="&";
									}else{
										url+="?";
									}
									v_frameId=treeNode.alias+'-tabFrame';
									url+='_frameTabId='+v_frameId;
									url+='&rnd='+parseInt(Math.random()*1000000);
									url=context+url;
									addFrameTab({title:treeNode.title,href:url,frameId:v_frameId,reload:true});
								}									
							}
						}
				},
				onAsyncSuccess:function(event, treeId, treeNode, msg){
					if(firstInit){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						var nodes=zTree.getNodes();
						for ( var i = 0; i < nodes.length; i++) {
							if(nodes[i].isParent){
								zTree.expandNode(nodes[i], true, false, false);
							}
						}
						firstInit=false;
					}
				}
		}
	};
	$.fn.zTree.init($("#menuTreeId"), setting);
	
	$('#passwordLink').click(function(){
		addFrameTab({url:'password.jsp',title:'密码修改',href:'password.jsp'});
	});
	
	$('#userCenter').click(function(e){
		var p=$(this).position();
		$('#userMM').menu('show', {
			left: p.left,
			top: p.top+20
		});
	});	
});

function addFrameTab(options){
		var frameId=options.frameId;
		var isExist=$('#tabs_center').tabs('exists',options.title);
		if(!isExist){
			frameId=frameId?frameId:'frame_ID'+Math.round(Math.random()*1000000);
			var tbh=$('#tabs_center').innerHeight()-33;
	  		$('#tabs_center').tabs('add',{
				title:options.title,
				content:'<iframe scrolling="auto" frameborder="0" src="'+options.href+'" id="'+frameId+'" style="width:100%;height:'+tbh+'px;"></iframe>',
				closable:true,
				iconCls:options.iconCls
			});		  		
		}else{
			$('#tabs_center').tabs('select',options.title);
			if(options.reload){
				if(frameId){
					var url=options.href;
					$("#"+frameId).attr("src",url);
				}
			}
		}			
}

//刷新对应frame中的数据，主要用于单据保存刷新列表
function reloadFrame(options){
	var dps={searchBtnID:'reloadBtnID',frameId:''};
	$.extend(dps,options);
	if(dps.frameId){
		var obj=$("#"+dps.frameId);
		if(obj.length>0){
			var frameWin=document.getElementById(dps.frameId).contentWindow;
			frameWin.document.getElementById(dps.searchBtnID).click();
		}
	}
	//刷新待办任务
	$('#taskTableId').datagrid('load',{rnd:Math.random()});
}

//关闭当前窗口
function closeFrameTab(options){
	if(options&&options.title){
		$('#tabs_center').tabs('select',options.title);
	}else{
		var tab = $('#tabs_center').tabs('getSelected');
		$('#tabs_center').tabs('close',tab.panel('options').title);
	}
}	