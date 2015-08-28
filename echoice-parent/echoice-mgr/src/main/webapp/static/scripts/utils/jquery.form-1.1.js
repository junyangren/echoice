jQuery.fn.extend({   
    fillForm:function(options){   
        var settings = jQuery.extend({   
            prefix:'' //表单项name前缀   
            ,data:{} //数据默认值   
        },options);   
	    this.each(function(){
	        var that =jQuery(this);   
	        if(jQuery.inArray(that.prop('tagName').toLowerCase(),['input','select','radio','textarea'])>-1){   
	            that.val(settings.data[that.prop('name')]);   
	        }else{   
	            for(var item in settings.data){   
	                try{   
	                    that.find('[name=\''+settings.prefix+item+'\']').val(settings.data[item]);   
	                }catch(e){   
	                    if(window.console){   
	                        console.error('未能获取元素:'+settings.prefix+item);   
	                    }
	                }
	            }
	        }
	    });   
    }   
    ,getForm:function(options){   
        var settings = jQuery.extend({   
            prefix:'' //表单项name前缀   
            ,data:{} //数据默认值   
        },options);   
        var ret =[];   
        var forms = this;   
        if(this.prop('tagName').toLowerCase()!=='form'){   
            forms = this.find('form');   
        }   
        forms.each(function(){   
            var that =jQuery(this);   
            var o ={};   
            that.find('input,select,radio,textarea').each(function(){   
                var el = jQuery(this);   
                var fieldFullName = el.prop('name');
                var fieldType=el.prop('type');
                
                var fieldName =fieldFullName.replace(settings.prefix,'');   
                if(settings.data[fieldName]){   
                    o[fieldName] =settings.data[fieldName];   
                    return;
                }   
                if(settings.prefix && fieldFullName.indexOf(settings.prefix)<0 ){   
                    //不符合前缀或已设置默认值   
                    return;
                }
                if(fieldType=='radio'){
                	if(el.prop('checked')){
                		o[fieldName] = el.val();
                	}
                }else{
                	o[fieldName] = el.val();
                }
                   
            });   
            ret.push(o);   
        });   
        if(ret.length === 1)return ret[0];   
        return ret;   
    }   
});  