<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="modify_password_div" >
   <form id="modify_password_form">  
       <table class="fr_table" > 
          <tr>  
             <td>原密码：</td>  
             <td>  
                <input type="password" name="old_password" id="old_password" value="" />  
             </td>
          </tr>  
          
          <tr>
             <td>新密码：</td>
             <td>  
                <input type="password" name="new_password" id="new_password" value=""/>  
             </td>
          </tr> 
          
           <tr>
             <td>新密码确认：</td>
             <td>  
                <input type="password" name="new_password_check" value=""/>  
             </td>
          </tr> 
       </table>  
   </form>
	<button id="save" type="button" class="btn btn-warning" style="float:right;width:120px;" onclick="savePassword()">保存</button>
</div>
    
<script type="text/javascript">
	
    $(document).ready(function(){
    	$.validator.addMethod("charAndInt",function(value,element,params){
    		var tel = /\d[A-Za-z]|[A-Za-z]\d/g;
    		return this.optional(element) || (tel.test(value));
    		},"请输入数字与字母的组合");///\d[A-Za-z]|[A-Za-z]\d/g
    	
    	
    	$("#modify_password_form").validate({
		    rules: {
		    	old_password: {
		            required: true,
		            remote: {
                        url: "/Physics/rbac/check_password",
                        type: "post",
                        dataType:"json", 
                        data:{
                        	old_password:function(){return $("#old_password").val();}
                        } 
                    } 
		        },
		        new_password: {
		            required: true,
		            rangelength:[8,30],
		            charAndInt:true
		        },
		        new_password_check: {
		            required: true,
		            rangelength:[8,30],
		            equalTo: "#new_password"
		        }
		    }, 
		    messages: {
		    	old_password: {
		            required: "请输原密码",
		            remote: "原密码错误"
		        },
		        new_password: {
		            required: "请输入新密码",
		            rangelength: jQuery.format("密码长度在{0}到{1}个字符") 
		        },
		        new_password_check:{
		        	required:"请输入新密码确认",
		        	rangelength: jQuery.format("密码长度在{0}到{1}个字符"),
		        	equalTo:"两次输入密码不一致"
		        }
		    }
	  	});
    	
    	$(".fr_table td:nth-child(even)").addClass("fr_left");
        $(".fr_table td:nth-child(odd)").addClass("fr_right");
        
        $( "input[type=submit], a.button , button" ).button();
 
    });  
    
    function savePassword(){
    	$.ajax({
	        type: "POST",
	        url: "/Physics/rbac/savePassword",
	        data: {old_password:$("#old_password").val(), new_password:$("#new_password").val()},
	        dataType: "json",
	        complete: function(result){	
	        	if(result['responseText']){
	        		alert();
	        	}else{
	        		alert();
	        	}
	        }
	    });
    }
</script> 