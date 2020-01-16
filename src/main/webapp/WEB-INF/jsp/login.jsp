<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>  
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no"/>
<meta name="description" content="SONG_LA">
<title>BANDO</title>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/fonts/font-awesome/css/font-awesome.css">
<link href='http://fonts.googleapis.com/css?family=Lato:400,700,900,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800,600,300' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/modernizr.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.1.11.1.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script type="text/javascript">
	var timer = setTimeout(function() {
		$( "#logo" ).slideToggle(800, function(){
			setTimeout(function() {
				$( "#loginForm" ).slideToggle(1000);
			}, 200);	//顯示表單的時間
		});
	}, 2000);	//隱藏LOGO的時間

	$(document).ready(function() {
		document.body.style.height = window.innerHeight;
		$("#userName").attr('placeholder', 'Your Email');
		$("#name").attr('placeholder', 'Your Nickname');
		$("#formPassword").attr('placeholder', 'Your Password');
		$("#rePassword").attr('placeholder', 'Type Password Again');
		$("#registerForm").css('margin-block-end', '0em');
	});

    $( function() {
    	$( "#user" ).on( "click", function() {
    		console.log("Before Click: " + $( "#ident").val());
    		$( "#ident").val("mem");
    		console.log("After Click: " + $( "#ident").val());
			var classList = $("#user-login").attr('class');
			var classSet = classList.split(" ");
    		$( "#user-login" ).removeClass(classSet[2],1000).addClass( "panel-mem", 1000);
    		$("#identity").html("一般會員");
    	});

    	$( "#deliver" ).on( "click", function() {
    		console.log("Before Click: " + $( "#ident").val());
    		$( "#ident").val("admin");
    		console.log("After Click: " + $( "#ident").val());
    		var classList = $("#user-login").attr('class');
			var classSet = classList.split(" ");
    		$( "#user-login" ).removeClass(classSet[2],1000).addClass( "panel-deliver", 1000);
    		$("#identity").html("管理員");
    	});

		$( "#store" ).on( "click", function() {
			var timer = setTimeout(function() {
				$( "#loginForm" ).slideToggle(800, function(){
					setTimeout(function() {
						$( "#registForm" ).slideToggle(1000);
					}, 200);	//顯示表單的時間
				});
			}, 0);	//隱藏LOGO的時間
		});

		$( "#noreg" ).on( "click", function() {
			var timer = setTimeout(function() {
				$( "#registForm" ).slideToggle(800, function(){
					setTimeout(function() {
						$( "#loginForm" ).slideToggle(1000);
					}, 200);	//顯示表單的時間
				});
			}, 0);	//隱藏LOGO的時間
		});
		
		$( "#send-email").on( "click", function() {
			var email = $("#email-forgot").val();
			
			if(email.length==0){
				var msg = "請輸入你的 E-mail";
				$("#message-to-user").html(msg);
				$("#msgModal").modal();
				setTimeout(function() {
					$("#msgModal").modal('hide');
				},1000);
				return false;
			}
			
			var forgotInfo = {};
			forgotInfo["email"] = $("#email-forgot").val();
			$("#myModal").modal("hide");
			
			$.ajax({
		    	type: 'POST',
		        contentType: "application/json",
		        dataType: 'json',
		        cache: false,
		        async: true,
		        url: "forgot",
		        data: JSON.stringify(forgotInfo),
		        beforeSend: function() {
		  			$("#loadingModal").modal({backdrop: 'static', keyboard: false});
		  		},
		        success: function (result) {
		        	console.log(result);
		        	$("#loadingModal").modal('hide');
		        	var msg = "";
					if (result.status == "succeed"){
		        		msg = "新密碼已寄至 " + $("#email-forgot").val();		        	
					} else if ((result.status == "failed")){
						msg = "查無此帳號，請註冊";
		        	}
					$("#message-to-user").html(msg);
					setTimeout(function() {
						$("#msgModal").modal();
					},100);
					setTimeout(function() {
						$("#msgModal").modal('hide');
					},1000);
		        },
		        error: function (xhr, textStatus, thrownError) {
		            $("#message-to-user").html("系統連線異常");
		            $("#loadingModal").modal('hide');
		            setTimeout(function() {
						$("#msgModal").modal();
					},100);
					setTimeout(function() {
						$("#msgModal").modal('hide');
					},1000);
		        }
		    });
		});

		$("#registerSubmit").on( "click", function() {
	    	console.log("register submit is click");

			if(validateRegisterForm().form()) {	    	
		    	var str = $("#registerForm").serialize();
		  		$.ajax({
		  			type: "post",
		  			data: str,
		  			url: "signUp",
		  			async: true,
		  			dataType: "json",
		  			beforeSend: function() {
		  				$("#loadingModal").modal({backdrop: 'static', keyboard: false});
		  			},
		  			success: function(result) {
		  				$("#loadingModal").modal('hide');
		  				if (result.status == "success"){
		  					$("#message-to-user").html("註冊信已寄出");
		  				} else {
		  					$("#message-to-user").html("註冊資料有誤");
		  				}
		  				setTimeout(function() {
							$("#msgModal").modal();
						},100);
						setTimeout(function() {
							$("#msgModal").modal('hide');
						},2000);
		  				console.log(result);
		  			},
		  			error: function (xhr, textStatus, thrownError) {
				    	$("#loadingModal").modal('hide');
				    	$("#message-to-user").html("系統連線異常");
				    	setTimeout(function() {
							$("#msgModal").modal();
						},100);
						setTimeout(function() {
							$("#msgModal").modal('hide');
						},2000);

				    }
	  			});
		  	}
    	});

    });
    
    function forgot() {
    	console.log("In forgot");
    	$("#myModal").modal();
    }

    function validateRegisterForm() {
    	return $("#registerForm").validate({
    		onBlur: true,
    		onChange: true,
    		rules: {
    			"userName": {
    				required: true,
    				email: true
    			},
    			"name": {
    				required: true,
    				rangelength: [1,12]
    			},
    			"formPassword": {
    				required: true,
    				rangelength: [6,15]
    			},
    			"rePassword": {
    				required: true,
    				rangelength: [6,15],
    				equalTo : "#formPassword"
    			}
    		},
    		messages: {
    			"userName": {
    				required: "請輸入信箱",
    				email: "請輸入正確格式"
    			},
    			"name": {
    				required: "請輸入暱稱",
    				rangelength: "長度請介於{0}~{1}字"
    			},
    			"formPassword": {
    				required: "請輸入密碼",
    				rangelength: "密碼長度請介於{0}~{1}字"
    			},
    			"rePassword": {
    				required: "請確認密碼",
    				rangelength: "密碼長度請介於{0}~{1}字",
    				equalTo: "與密碼不符"
    			}
    		}
    	});
    }
    
    
</script>
</head>  
  
<body>  

	<div class="fixed_bg bg_img_1">
		<div class="video-container" id="top">
	        <video autoplay loop class="fillWidth">
		       <source src="<%=request.getContextPath()%>/video/Home-And-Away.mp4" type="video/mp4"/>
	        </video>
		    <!-- scroll with content (but position swift) -->
		    <!-- </div> -->
	        <div class="group">
	        	<h1 class=" shadow">Welcome to <strong>BANDON</strong></h1>
	        	<div id="logo" style="display:block">
	        		<img id="bgimg" src="<%=request.getContextPath()%>/video/bg.png">
	        	</div>
				<div id="loginForm" class="container" style="display:none"> 
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div id="user-login" class="panel panel-default panel-mem">
								<h2><strong>請選擇角色</strong></h2><br>
								<span class="span-pic" id="deliver"><i class="fa fa-cog"></i></span>
								<span class="span-pic" id="user"><i class="fa fa-user"></i></span>
								<span class="span-pic" id="store" title="註冊"><i class="fa fa-list"></i></span>
								<br><br>
								<h4>以 <strong id="identity">一般會員</strong> 身分登入</h4>
								<c:if test="${param.error ne null}">
									<span class="error">帳號或密碼錯誤</span>
								</c:if>
								<div class="panel-body">
									<form:form method="post" modelAttribute="mem" servletRelativeAction="/login">
										<input id="ident" type="hidden" name="ident" value="mem">
										<div class="form-group">
											<form:input path="username" class="form-control" required="true" placeholder="Enter Your Email" value="" />
										</div>
										<div class="form-group">
											<form:input type="password" path="password" required="true" class="form-control" placeholder="Enter Your Password" /> 
										</div>
<!-- 										<div class="checkbox"> -->
<!-- 											<input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me" />記住我 -->
<!-- 										</div> -->
										<div class="form-group">
											<input type="submit" value="登入" class="btn btn-success btn-lg btn-block">
										</div>
									</form:form>
									<div class="forget">
										<a onclick="forgot()">忘記密碼</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="registForm" class="container" style="display:none">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div id="user-reg" class="panel panel-default panel-store">
								<h4 style="color: white;text-align: center"><strong>註冊會員</strong></h4>
								<div class="panel-body">
									<form:form method="post" modelAttribute="registerForm" servletRelativeAction="#">
										<div class="form-group" style="margin-bottom: 5px">
											<form:input type="email" path="userName" class="form-control" />
											<div class="row">
												<div class="col-sm-2">
													<span>&nbsp;</span>
												</div>
												<div class="col-sm-10">
													<label id="userName-error" class="error" for="userName"><span>&nbsp;</span></label>
													<form:errors class="errorMsg" path="userName" />
												</div>
											</div>
			  							</div>
			  							<div class="form-group" style="margin-bottom: 5px">
											<form:input type="text" path="name" class="form-control" />
											<div class="row">
												<div class="col-sm-2">
													<span>&nbsp;</span>
												</div>
												<div class="col-sm-10">
													<label id="name-error" class="error" for="name"><span>&nbsp;</span></label>
													<form:errors class="errorMsg" path="name" />
												</div>
											</div>
			  							</div>
										<div class="form-group" style="margin-bottom: 5px">
											<form:input type="password" path="formPassword" class="form-control" />
											<div class="row">
												<div class="col-sm-2">
													<span>&nbsp;</span>
												</div>
												<div class="col-sm-10">
													<label id="formPassword-error" class="error" for="formPassword"><span>&nbsp;</span></label>
													<form:errors class="errorMsg" path="formPassword" />
												</div>
											</div>
										</div>
										<div class="form-group" style="margin-bottom: 5px">
											<form:input type="password" path="rePassword" class="form-control" />
											<div class="row">
												<div class="col-sm-2">
													<span>&nbsp;</span>
												</div>
												<div class="col-sm-10">
													<label id="rePassword-error" class="error" for="rePassword"><span>&nbsp;</span></label>
													<form:errors class="errorMsg" path="rePassword" />
												</div>
											</div>
										</div>
			  						</form:form>
		  							<button type="button" id="registerSubmit" class="btn btn-success btn-lg btn-block">註冊</button>
		  							<button type="button" id="noreg" class="btn btn-primary btn-block">取消</button>
		  						</div>
	  						</div>
						</div>
					</div>
				</div> 
	        	<h1 class=" shadow">The Best Way to Color Your Life</h1>
			</div>
	    <!-- change Position -->
	    </div>
    </div>
	
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
		
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">What is Your Email Address ?</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="email-forgot">Email address:</label>
						<input type="email" class="form-control" id="email-forgot">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" id="send-email">Send</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" >Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Message Modal -->
	<div id="msgModal" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body">
					<h4 id="message-to-user" style="text-align: center"></h4>
				</div>
			</div>
		</div>
	</div>
	<!-- Message Modal -->
	<div id="loadingModal" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width:20%">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body">
					<div class=row>
						<div class="col-sm-offset-1 col-sm-4" style="display: inline-block;padding-right: 5px">
							<img style="width: 40px;" src="<%=request.getContextPath()%>/video/spinner.gif">
						</div>
						<div class="col-sm-7" style="display: flex; align-items: center;padding-left: 5px">
							<h4><strong>系統發信中</strong></h4>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>  
</html>  