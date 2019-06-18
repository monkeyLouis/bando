function setHtml(parent, pwd, name, auth){
	$(parent).children('td[name="memPwd"]').html(pwd);
	$(parent).children('td[name="memName"]').html(name);
	$(parent).children('td[name="memAuth"]').html(auth);
}
	
$(".btn-edit").click(function(){
	var btnStatus = $(this).html();
	var parent = $(this).parents(".data");
	
	if (btnStatus == "Edit") {
	
		$(this).toggleClass('btn-danger btn-success');
		$(this).html("Save");
		$(".btn").prop("disabled", true);
		$(this).prop("disabled", false);
		oriId = $(parent).children('td[name="memId"]').html();
		oriPwd = $(parent).children('td[name="memPwd"]').html();
		oriName = $(parent).children('td[name="memName"]').html();
		oriAuth = $(parent).children('td[name="memAuth"]').html();
				
		$(parent).children('td[name="memPwd"]').html("<input value='"+ oriPwd + "'>");
		$(parent).children('td[name="memName"]').html("<input value='"+ oriName + "'>");
		$(parent).children('td[name="memAuth"]').html("<input value='"+ oriAuth + "'>");
		
	} else if(btnStatus == "Save"){
		var activeBtn = this;
		var newPwd = $(parent).find('td[name="memPwd"]').find("input").val();
		var newName = $(parent).find('td[name="memName"]').find("input").val();
		var newAuth = $(parent).find('td[name="memAuth"]').find("input").val();
				
		if(oriPwd == newPwd && oriName == newName && oriAuth == newAuth) {
			setHtml(parent, newPwd, newName, newAuth);
			$(activeBtn).toggleClass('btn-danger btn-success');
			$(activeBtn).html("Edit");
			$(".btn").prop("disabled", false);
			alert("All Same !!");
			return false;
		}
				
		if(newPwd.length==0){
			alert("Pwd NOT NULL!!");
			$(parent).find('td[name="memPwd"]').find("input").focus();
			return false;
		}
				
		if(newName.length==0){
			alert("Name NOT NULL!!");
			$(parent).find('td[name="memName"]').find("input").focus();
			return false;
		}
			
		if(newAuth.length==0){
			alert("Auth NOT NULL!!");
			$(parent).find('td[name="memAuth"]').find("input").focus();
			return false;
		}
				
		var member = {};
		member["memId"] = oriId;
		member["memPwd"] = newPwd;
		member["memName"] = newName;
		member["memAuth"] = newAuth;
		
	    $.ajax({
	        type: 'POST',
	        contentType: "application/json",
	        dataType: 'json',
	        cache: false,
	        url: /*[[@{/member/update}]]*/,
	        data: JSON.stringify(member),
	        success: function (result) {
				$(activeBtn).toggleClass('btn-danger btn-success');
				$(activeBtn).html("Edit");
				$(".btn").prop("disabled", false);
				setHtml(parent, result.memPwd, result.memName, result.memAuth);
	        	alert("Id: " + result.memId + "\nPwd: " + result.memPwd + "\nName: " + result.memName + "\nAuth: " + result.memAuth + "\nData Saved !!");
	        },
	        error: function (xhr, textStatus, thrownError) {
	            alert(textStatus);
	        }
	    });
				
	}			
			
});
		
// 		for sign up page
		$(".btn-signUp").click(function(){
			
			var id = $("input[name='id']").val();
			var pwd = $("input[name='pwd']").val();
			var name = $("input[name='name']").val();
			var auth = $("input[name='auth']").val();
			
			if(id.length==0){
				alert("Input your Account !!");
				$("input[name='id']").focus();
				return false;
			}
			
			if(pwd.length==0){
				alert("Input your Pwd !!");
				$("input[name='pwd']").focus();
				return false;
			}
			
			if(name.length==0){
				alert("Input your Name !!");
				$("input[name='name']").focus();
				return false;
			}
			
			if(auth.length==0){
				alert("Input your Auth !!");
				$("input[name='auth']").focus();
				return false;
			}
			
			var member = {};
			member["memId"] = id;
			member["memPwd"] = pwd;
			member["memName"] = name;
			member["memAuth"] = auth;
			
			$.ajax({
		        type: 'POST',
		        contentType: "application/json",
		        dataType: 'json',
		        cache: false,
		        url: /*[[@{/member/update}]]*/,
		        data: JSON.stringify(member),
		        success: function (result) {
		        	alert("Id: " + result.memId + "\nPwd: " + result.memPwd + "\nName: " + result.memName + "\nAuth: " + result.memAuth + "\nMember Signed Up !!");
		        	window.location = /*[[@{/showAll}]]*/;
		        },
		        error: function (xhr, textStatus, thrownError) {
		            alert(textStatus);
		        }
		    });
			
		});