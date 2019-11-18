// Start of _manageConnectionsList
var _manageConnectionsList=
`
<table class="ui very compact small fixed single line striped celled unstackable selectable table con-list-table">
	<thead class="sticky-header">
		<tr>
			<th class="center aligned bluish custom-background"> Entity Id </th>
			<th class="center aligned bluish custom-background"> Entity Name </th>
			<th class="center aligned bluish custom-background"> Application name </th>
			<th class="center aligned bluish custom-background"> Email </th>
			<th class="center aligned bluish custom-background"> 
				<span>Actions</span>
				<span class="search-remove-btn-content">
					<button class="ui circular mini icon yellow button search-clear-icon" style="float: right;"
						title="Clear Searched Items" on-click="CLEAR_SearchedItems">
						<i class="remove icon"></i>
					</button>
				</span>
			 </th>
		</tr>
	</thead>
	<tbody>
	{{#each connections: idx}}
		<tr class="center aligned" id="conlist_row{{idx}}">
			<td title="{{entityId}}">
				{{entityId}}
			</td>
			<td class="collapsing" title="{{entityName}}">
				{{entityName}}
			</td>
			<td title="{{appPortfolio.name}}">
				{{appPortfolio.name}}
			</td>
			<td title="{{email}}">
				{{email}}
			</td>
			<td>
				<button class="ui inverted circular mini icon violet button con-list-edit-btn" 
					title="Modify Connection" on-click="OPEN_ModifyConnectionModal">
					<i class="edit icon"></i>
				</button>
				<button class="ui inverted circular mini icon red button  con-list-delete-btn" title="Delete Connection"  on-click="DELETE_Connection">
					<i class="trash icon"></i>
				</button>
			</td>
		</tr>
	{{/each}}
	</tbody>
</table>
`;
// End of _manageConnectionsList

// Start of _modifyConnectionModal
var _modifyConnectionModal=
`
<div class="ui large modal modify-con-modal">
	<div class="ui header" style="padding: 0.5em; background-color: steelblue; color: white;">	
		<div class="modify-con-header-content">  
			<span class="modify-con-modal_text">Modify Connection Details</span> 
			<button class="ui circular icon yellow button" on-click="CLOSE_ModifyConnectionModal" style=" float: right; margin-top: -5px;">
				<i class="close icon"></i>
			</button>
		</div>
	</div>
	<div class="ui scrolling content modify-con-target">
		
	</div>
</div>
`;
// End of _modifyConnectionModal

// Start of _searchConnectionBarTemplate
var _searchConnectionBarTemplate=
`
<div class="search-con-content">
	<div class="ui icon fluid input">
		<input class="search-con-input" placeholder="Search Connection by EntityId"/>
		<i class="ui search icon"></i>
	</div>
</div>
`;
// End of _searchConnectionBarTemplate

// Start of _loadingPageTemplate
var _loadingPageTemplate=
`
<div class="ui segment loading-content" style="height: 100%; margin: 0;">
  <div class="ui active inverted dimmer">
    <div class="ui large text loader">Fetching Connections ...</div>
  </div>
</div>
`;
// End of _loadingPageTemplate

// Start of placeHolderTemplate
var placeHolderTemplate=
`
<div class="ui placeholder segment placeholder-container" 
	style="position: fixed; width: 16em; top: 50%; right: 50%; bottom: 50%; left: 50%; margin: auto; background-color: #ffb795;">
  <div class="ui icon header">
    <i class="frown outline icon placeholder-icon"></i>
		No Connections Found. Try refreshing the page again.
  </div>
  <div class="ui teal button placeholder-btn" on-click="REDIRECT_CreateConnection">OR Create New Connection</div>
</div>
`
// End of placeHolderTemplate
	
// Start of _deletePopupTemplate
var _deletePopupTemplate=
`

`;
// End of _deletePopupTemplate


// Start of _paginationTemplate
var _paginationTemplate= `
<div class="page_segment" style="display: flex;">
		
	<div class="page_segment2">
		<select class="ui compact selection dropdown page-item-ddn" on-change="SELECT_itemPerPage">
			<option value="5">5</option>
		  	<option value="15">15</option>
		  	<option value="30">30</option>
		  	<option value="50">50</option>
		</select>
	</div>
	
	<div class="page_segment1" id="pagination-container"></div>
</div>
`;
// End of _paginationTemplate

var _template=
`
<div class="con-list-container">
	<div class="con-list-header" style="display: flex; position: relative;">
		<span class="mod-con-list" style="margin-top: 0.5em; font-weight: bold;">Modify SP Connections</span>
		<span class="search-con"> `+_searchConnectionBarTemplate+` </span>
	</div>
	<div class="con-list-content">
		`+_manageConnectionsList+`
		
		`+_paginationTemplate+`
	</div>
</div>


`+_loadingPageTemplate+`

`+_modifyConnectionModal+`

`+placeHolderTemplate+`

`+_deletePopupTemplate+`

`;

var ManageSpComponent = Ractive.extend({
	template: _template,
	toRemoveSearchedItems: false,
	templateStyler(){
		this.targetEL= $(this.target);
		
		this.LOADING_DIMMER= this.targetEL.find(".loading-content");
		
		this.CONLIST_CONTAINER= this.targetEL.find(".con-list-content");
		this.CONLIST_CONTAINER.hide();
		this.PLACEHOLDER_CONTAINER= this.targetEL.find(".placeholder-container");
		this.PLACEHOLDER_CONTAINER.hide();
		this.SEARCH_BAR_CONTAINER= this.targetEL.find('.search-con');
		this.SEARCH_BAR_CONTAINER.hide();
		
		// Container styling...
		this.CONLIST_CONTAINER.css({"margin":"5px","height":"92%","overflow-y":"auto"});
		
		// Table styling..
		var CON_LIST_TABLE= this.targetEL.find(".con-list-table");
		CON_LIST_TABLE.find("th.custom-background").css({"background":"#9b9d9e","font-size":"1.2em", "padding":"0.5em", "color":"white"});
		
		$('.modify-con-target').css({"padding":"0.2em"});
		if(this.targetEL.find("div.sp-celled").length > 0)
			this.targetEL.find("div.sp-celled").css({"display":"table-cell"});
		
		this.targetEL.find(".mod-con-list").css({"color":"white","font-size":"1.2em"});
		this.targetEL.find(".con-list-header").css({"background":"coral","padding":"0.3em"});
		
		// By default "ClearSearchResultBtn" will be hidden;
		this.ClearSearchResultBtn= this.targetEL.find(".search-clear-icon");
		this.ClearSearchResultBtn.hide();
		
		// pagination container...
		this.targetEL.find(".page-item-ddn").css({
			"min-width": "5em",
	    	"border": "1px solid #2224266e",
	    	"margin-right":"0.5em"
		});
		
	},
	
	initPagination: function(){
		var ractive= this;
		var paginator= this.targetEL.find('#pagination-container').pagination({
			
			//DISPLAY Control..
			items: ractive.pageOpt.items || 100,
//		    itemsOnPage: ractive.pageOpt.itemsOnPage || 10,
		    cssStyle: 'light-theme',
		    pages: ractive.pageOpt.pages|| 1,
		    edges: ractive.pageOpt.edges || 5,
		    currentPage: ractive.pageOpt.currentPage || 1,
		    
		    
		    onPageClick: ractive.pageOpt.onPageClick || function(pageNum, event){
		    	ractive.onPageChange= true;
		    	ractive.pageOpt.currentPage= pageNum;
		    	var countURL= _pmtpfServer + "/connections.json?pagable=false";
				var dataURL= _pmtpfServer + "/connections.json?pagable=true&pageNum=" + pageNum + "&itemPerPage=" + ractive.itemPerPage;
				ractive.notPaginated= true;
				ractive.paginationFetch(countURL, dataURL);
				
		    },
		    
		    onInit: ractive.pageOpt.onInit || function(){
		    	if(ractive.onPageChange)
		    		return false;
		    	ractive.onPageChange= false;
		    	var countURL= _pmtpfServer + "/connections.json?pagable=false";
				var dataURL= _pmtpfServer + "/connections.json?pagable=true&itemPerPage=" + ractive.itemPerPage;
				if(ractive.notPaginated){
					ractive.paginationFetch(countURL, dataURL);
					ractive.notPaginated= false;
				}
		    }
		});
		console.log("Init pagination...");
		return paginator;
	},
	
	onPageChange: false,
	notPaginated: true,
	paginationFetch(countURL, dataURL){
		var ractive= this;
		this.fetchContents(countURL,
				function(model, response, options){
					
					if(response.data && response.data.hasOwnProperty("PAGINATION_COUNT")){
						// Then show the page count... 
						ractive.itemPerPage= parseInt(ractive.itemPerPage);
						ractive.pageOpt.pages= Math.ceil(response.data.PAGINATION_COUNT/ractive.itemPerPage);
						// Send one more fetch request for data
						ractive.fetchContents(dataURL, 
								function(model, response, options){
									var connections= response.data;
									if(!  connections || connections.length == 0)
										ractive.PLACEHOLDER_CONTAINER.show();
									else{
										ractive.set("connections", connections);
										ractive.enableSearchBar(ractive.SEARCH_BAR_CONTAINER, true);
										ractive.CONLIST_CONTAINER.show();
										ractive.notPaginated= false;
										ractive.initPagination();
									}
							
							}, function(model, response, options){
								console.log("Fetch error for pagination...",response);
								ractive.LOADING_DIMMER.hide();
								ractive.PLACEHOLDER_CONTAINER.show();
								ractive.enableLobiboxNotification(response);
						});
						
					} else {
						var connections= response.data;
						if(!  connections || connections.length == 0)
							ractive.PLACEHOLDER_CONTAINER.show();
						else{
							ractive.set("connections", connections);
							ractive.enableSearchBar(ractive.SEARCH_BAR_CONTAINER, true);
							ractive.CONLIST_CONTAINER.show();
						}
					}
					ractive.LOADING_DIMMER.hide();
					// Search bar configuration inside Fetch success function...
				}, function(model, response, options){
					console.log("FETCH un-successful", response);
					ractive.LOADING_DIMMER.hide();
					ractive.PLACEHOLDER_CONTAINER.show();
					ractive.enableLobiboxNotification(response);
		});
		
	},
	
	clearPushConnections: function(_conn){
		this.set("connections", []);
		this.push("connections", _conn);
		this.set("connection", _conn);
	},
	
	fetchContents: function(url, successFn, errorFn){
		var Connection = Backbone.Model.extend();
		var Connections = Backbone.Collection.extend({
			model: Connection,
			url: url
		});
		var _connInstance = new Connections();
		_connInstance.fetch({
			success: function(model, response, options){
				if(successFn)
					successFn(model, response, options);
			}, 
			error: function(model, response, options){
				if(errorFn)
					errorFn(model, response, options);
			}
		})
	},
	
	deleteConnection: function(url, id, rowIndex){
		var ractive= this;
		var ConnectionModel = Backbone.Model.extend({
			urlRoot: url,
			idAttribute: "_id"
		});
		var conInstance= new ConnectionModel({
			_id: id
		});
		conInstance.destroy({
			success: function(model, response, options){
				ractive.splice("connections", rowIndex, 1);
				if(ractive.get("connections").length == 0) {
					ractive.CONLIST_CONTAINER.hide();
					ractive.PLACEHOLDER_CONTAINER.show(300);
				}
			}, 
			error: function(model, response, options){
				console.log("Delete Unsuccessful:", response);
				ractive.enableLobiboxNotification(response);
			}
		});
	},
	
	enableSearchBar: function(SEARCH_BAR_CONTAINER, applyCustomCss){
		var ractive= this;
		initSearchBar(SEARCH_BAR_CONTAINER, {
			data: ractive.get("connections"),
			getValue: "entityId",
			list: {
				maxNumberOfElements: 15,
				match: { 
					enabled: true
				},
				sort: {
					enabled: true
				},
				onClickEvent: function() {
					ractive.clearPushConnections(SEARCH_BAR_CONTAINER.find('.search-con-input').getSelectedItemData());
					ractive.toRemoveSearchedItems= true;
					ractive.ClearSearchResultBtn.show(500);
				}
			},
		}, applyCustomCss);
		SEARCH_BAR_CONTAINER.show();
	},
	
	onConnectionModified: function(ractive, rowIndex, modifiedConn){
		// Remove and replace the old conn row by modified one...
		ractive.splice("connections", rowIndex, 1);
		ractive.splice("connections", rowIndex, 0, modifiedConn);
		
		setTimeout(function(){
			$(".modify-con-modal").modal("hide");
			window.location.reload();
		}, 2000);
	},
	
	enableLobiboxNotification: function(response){
		var errResp= response.responseJSON;
		var errorObj= JSON.parse(errResp ? errResp.data : "");
		var errorList= errorObj ? errorObj.validationErrors : "";
		var errAppender="";
		for ( var i=0; i < errorList.length; i++) 
			errAppender += i+1 + ". " + errorList[i].message +".\n";
		
		var lobibox= Lobibox.notify('error',{
			title: errResp ?  (errResp.error ? errResp.error+" | Status:"+response.status : errResp.statusMsg) : "",
			delay: 17000,
			closeOnClick:false,
			icon: false,
			position: 'top right',
			//(errResp.data ? JSON.stringify(JSON.parse(errResp.data), null, 10) : "") 
			msg: errAppender ? errAppender : response.responseText
		});
		lobibox.$el.click(function(){
			$(this).css({"width":"40%"});
			$(this).find(".lobibox-notify-body").css({"overflow":"auto", "height": "25em"});
			$(this).find(".lobibox-notify-msg").css({"max-height":"99%", "word-break":"break-word", "margin-top":"0.3em"});
		});
	},
	
	
	
	on:{
		init: function(){
			this.set("connections", []);
			this.set("connection", {});
			this.pageOpt= {};
		},
		
		render: function(){
			this.templateStyler();
			var listContent= this.targetEL.find(".con-list-content");
			stickyHeader(listContent,listContent.find("sticky-header"));
			
			this.itemPerPage= this.targetEL.find(".page-item-ddn").dropdown("get value");
			
			var ractive= this;
			// Fetching list of connections from server...?pagable=false
			var countURL= _pmtpfServer + "/connections.json?pagable=false";
			var dataURL= _pmtpfServer + "/connections.json?pagable=true&itemPerPage=" + this.itemPerPage;
			this.paginationFetch(countURL, dataURL);
			
			this.ADD_SP_CONNECTION_WIDGET = new SpConnectionComponent({target: ""});
		},
		
		OPEN_ModifyConnectionModal: function(ctx){
			var ractive= this;
			var rowIndex= ctx.get("idx");
			var entityId= this.get("connections")[rowIndex].entityId;
			
			this.fetchContents(_pmtpfServer+"/connections.json/"+entityId, function(model, response, options){
				// 1. addConnectionComponentn settings...
				if(ractive.ADD_SP_CONNECTION_WIDGET.target)
					ractive.ADD_SP_CONNECTION_WIDGET.unrender();
				// Tell addConnectionComponent that it's an edit mode...
				ractive.ADD_SP_CONNECTION_WIDGET.setIsEditMode(true);
				ractive.ADD_SP_CONNECTION_WIDGET.setConnectionObject(response.data);
				ractive.ADD_SP_CONNECTION_WIDGET.render(".modify-con-target");
				ractive.ADD_SP_CONNECTION_WIDGET.onConnectionAdded(ractive.onConnectionModified, ractive, rowIndex);
				
				// 2. Show modal at the end...
				$(".modify-con-modal").modal({autofocus: false, closable: false}).modal("show");
				
			}, function(model, response, options){
				console.log("FETCH un-successful", response);
			})
			
		},
		
		DELETE_Connection: function(ctx){
			// TODO: To send delete request to the server..
			var rowIndex= ctx.get("idx");
			var connId =this.get("connections")[rowIndex].entityId;
			this.deleteConnection(_pmtpfServer+"/connections.json", connId, rowIndex);
		},
		
		CLOSE_ModifyConnectionModal: function(ctx){
			$(".modify-con-modal").modal("hide");
		},
		
		CLEAR_SearchedItems: function(ctx){
			if(this.toRemoveSearchedItems) {
				this.ClearSearchResultBtn.hide(500);
				this.targetEL.find(".search-con-input").val(null);
				this.toRemoveSearchedItems= false;
				var ractive= this;
				
				this.fetchContents(_pmtpfServer+"/connections.json", function(model, response, options){
					ractive.set("connections", response.data);
					ractive.enableSearchBar(ractive.SEARCH_BAR_CONTAINER, true);
				}, function(model, response, options){
					console.log("FETCH un-successful", response);
				});
			}
		},
		
		REDIRECT_CreateConnection: function(ctx){
			window.location= _pmtpfServer+"/connection";
		},
		SELECT_itemPerPage: function(){
			this.itemPerPage= this.targetEL.find(".page-item-ddn").dropdown("get value");
			this.pageOpt.currentPage= 1;
			var countURL= _pmtpfServer + "/connections.json?pagable=false";
			var dataURL= _pmtpfServer + "/connections.json?pagable=true&itemPerPage=" + this.itemPerPage;
			this.paginationFetch(countURL, dataURL);
		}
	}
});

Ractive.components.GlobalManageSpComponent = ManageSpComponent;