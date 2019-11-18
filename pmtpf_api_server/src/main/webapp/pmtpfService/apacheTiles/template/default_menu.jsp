<head>
<title>Menu</title>
</head>
<body>
	<%String rootPath = request.getContextPath(); %>
	<script type="text/javascript">
		var _ciscoApiServer = '<%="//" + request.getServerName() + ":" + request.getServerPort() + rootPath%>'
	</script>
	
	<!-- /////////////////////////////////sidebar menu configuration ////////////////////////////////-->
	<ul class="sidebar-menu noselect">
		<li class="header"><a class="createConnection"
			href="<%=rootPath %>/createConnection">Create Connection</a></li>
		<li class="header"><a class="modifyConnection"
			href="<%=rootPath %>/modifyConnection">Modify Connection</a></li>
	</ul>
	<!-- /////////////////////////////////sidebar menu configuration ////////////////////////////////-->
	
	
	<script type="text/javascript">
		$(document).ready(function(){
			$.sidebarMenu($('.sidebar-menu'));
		});
	</script>
</body>
