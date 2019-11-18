<%@include file="/pmtpfService/jsp/head.jsp"%>
<head>
<style type="text/css">
	.pmtpf_header-content{
		width: 100%;
		display: inherit;
    	align-items: center;
	}
	
	.pmtpf_header-primary_text{
	    color: white;
	    font-size: 1.6em;
	    font-weight: bold;
    }
    
    .pmtpf_header-signout_btn{
    	position: fixed;
    	right: 0;
    }
    
    .pmtpf_header-logo{
    	width: 1em;
    	min-width: 9em;
    }
    
    .pmtpf_header-login_user{
    	position: fixed;
    	right: 60px; 
    	font-size: 1.2em;
    	font-weight: bold;
    	color: lightgrey;
    }
</style>
</head>
<body>
	<script type="text/javascript">
		<%
			//Getting PMTPF_AUTHUSER from request header set in PMT 
			String loggedInUser= request.getHeader("PMTPF_AUTHUSER");
			loggedInUser = loggedInUser == null ? "User" : loggedInUser;
			
			String members= request.getHeader("PMTPF_MEMBEROF");
		%>
		var member= "<%=members%>";
		console.log("members :",member);
	</script>
	
	<div class="pmtpf_header-content noselect">
		<img src="pmtpfService/image/png/cisco-logo-white.png" class="pmtpf_header-logo" alt="CISCO">
		<span class="pmtpf_header-primary_text">Pingfederate Connection Automation</span>
		
		<span class="pmtpf_header-login_user"><%= loggedInUser %></span>
		<button class="ui yellow inverted circular button icon pmtpf_header-signout_btn" title="Logout">
			<i class="sign out alternate icon"></i>
		</button>
	</div>
	
	<script type="text/javascript">
		//TODO: Display signin userid in the header
		
		// Enable cisco global logout...
		$(".pmtpf_header-signout_btn").click(function(){
			// For dev and stage global logout url..
			document.location.href = "https://cloudsso-test.cisco.com/autho/logout.html?redirectTo=http://<%= serverName +":"+request.getServerPort()+ request.getContextPath() %>/logout";
			// TODO:: for prod logout url...
		})
	</script>
</body>