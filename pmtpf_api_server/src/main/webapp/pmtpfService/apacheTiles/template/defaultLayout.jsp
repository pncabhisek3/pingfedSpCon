<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<% String rootPath = request.getContextPath(); %>
<title><tiles:getAsString name="title"></tiles:getAsString></title>
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/pagination/pagination.css">
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/semantic.min.css">
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/font-awesome.5.5.3.css">
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/sidebar/sidebar-menu.css">
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/globalCss.css">

<link rel="stylesheet" href="<%=rootPath%>/pmtpfService/css/Fileupload/fine-uploader-new.css">

<!-- easyAutocomplete dependencies -->
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/easyAutocomplete/easy-autocomplete.min.css">
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/easyAutocomplete/easy-autocomplete.themes.min.css">
<link rel="stylesheet" href="<%= rootPath%>/pmtpfService/css/lobibox/lobibox.css">

<script src="<%= rootPath%>/pmtpfService/js/jquery.3.3.1.min.js"></script>

<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/pagination/pagination.js"></script>

<script src="<%= rootPath%>/pmtpfService/js/enter.js"></script>

<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/semantic.js"></script>
<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/ractive1.4.9.js"></script>
<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/ractive-events-keys.min.js"></script>

<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/underscore.1.9.1.js"></script>
<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/backbone.1.3.3.js"></script>

<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/sidebar/sidebar-menu.js"></script>
<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/global_js.js"></script>

<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/lobibox/lobibox.js"></script>

<!-- easyAutocomplete dependencies -->
<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/easyAutocomplete/jquery.easy-autocomplete.min.js"></script>

<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/Fileupload/jquery.fine-uploader.js"></script>

<!-- Prove.js dependencies -->
<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/validation/prove.js"></script>
<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/validation/decorator.js"></script>
<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/validation/validate.js"></script>



<style type="text/css">
.default-header {
	height: 3.3em;
	background: steelblue;/* #1034a6 #F7D969 */
	position: fixed;
	width: 100%;
	z-index: 999;
	-webkit-box-shadow: 0px 1px 12px -2px rgba(0,0,0,0.75);
	-moz-box-shadow: 0px 1px 12px -2px rgba(0,0,0,0.75);
	box-shadow: 0px 1px 12px -2px rgba(0,0,0,0.75);
	
	/*This settings are for logout btn and the header text*/
	display: flex;
    align-items: center;
}

.default-container{
	height: 100%;
	display: flex;
}

.default-menu {
	width: 20em;
	margin-top: 3.3em;
	height: inherit;
	background: #c1c4c5;
}

.default-body {
	width: calc(100% - 20em);
	margin-top: 3.3em;
	height: inherit;
	background: whitesmoke;
}

.selection{
	background: #96999a;
	border-right: 5px solid steelblue;
}
</style>

</head>
<body>
	<div class="default-header">
		<tiles:insertAttribute name="header" />
	</div>

	<div class="default-container">
		<div class="default-menu">
			<tiles:insertAttribute name="menu" />
		</div>

		<div class="default-body">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<script type="text/javascript">
	//This is to set nav selected for it's corresponding page..
	var nav= "<%= request.getAttribute("href")%>";
	$(document).ready(function(){
		$("li.header").removeClass("selection");
		$("."+nav).addClass("selection");
	});
	</script>
</body>
</html>