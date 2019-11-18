// Start of _primaryActionsTemplate
var _primaryActionsTemplate=
`
<div class="ui large fluid buttons conn-btns">
  <button class="ui grey icon button stndard-conn-btn" on-click="['ENABLE_Connection', 'STANDARD_CONN']">
	<i class="ui linkify icon sp-right-margined"></i>Standard Connection
  </button>
  <div class="or" />
  <button class="ui grey icon button digi-conn-btn" style="margin:0 2px;" on-click="['ENABLE_Connection', 'DIGITALLY_SIGNED_CONN']">
	<i class="ui certificate icon sp-right-margined"></i>Digitally Signed
  </button>
  <div class="or" />
  <button class="ui grey icon button logout-conn-btn" on-click="['ENABLE_Connection', 'LOGOUT_ENABLED_CONN']">
	<i class="ui sign out alternate icon sp-right-margined"></i>Logout Enabled
  </button>
</div>
`;
// End of _primaryActionsTemplate

// Start of _requesterInformationsTemplate
var _requesterInformationsTemplate=
`
<div class="field">
	<div class="ui dividing small header sp-conn-sub_header"> Requester Informations </div>
</div>

<div class="three fields">
	<div class="required field">
		<label>First Name:</label>
		<input type="text" class="ui fluid input validate_me" name="firstName" placeholder="F.Name" value="{{firstName}}"
			v_required='{"message":"Enter your first name"}'>
	</div>
	
	<div class="required field">	
		<label>Last Name:</label>
		<input type="text" class="ui fluid input validate_me" name="lastName" placeholder="L.Name" value="{{lastName}}"
			v_required='{"message":"Enter your last name"}'>
	</div>
	
	<div class="required field">
		<label>Email:</label>
		<input type="email" class="ui fluid input validate_me" name="email" placeholder="Eg: joy@gmail.com" value="{{email}}"
			v_required='{"message":"Enter your email address"}'>
	</div>
	
</div>

<div class="two fields">

	<div class="field required">
	{{#appPortfolio}}
		<label>App Portfolio:</label>
		<div class="ui icon input">
			<input type="text" class="ui fluid input enterable validate_me" name="appPortfolio" v_required='{"message":"Add Application portfolio"}' 
				placeholder="Search App portfolio name" value="{{appPortfolioName}}" data-type="APP" >
			<i class="inverted circular blue search link icon" title="Search Application Portfolio Name" on-click="['VALIDATE_name', 'APP']"></i>
		</div>
	{{/appPortfolio}}
	</div>
	
	<div class="field" app-not-accordion>
		<label style="visibility: hidden;">App Portfolio:</label>
		{{#each selected_apps:idx}}
			<a style="margin-top: 0.5em;" class="ui label" title="Remove" on-click="['REMOVE_Resource', 'APP']">
			  <i class="red remove icon"></i>{{sys_id}}
			</a>
		{{/each}}
	</div>

</div>

<div class="two fields">

	<div class="field required">
		<label>Owner(s):</label>
		<div class="ui icon input">
			<input type="text" class="ui fluid input enterable validate_me" name="owners" placeholder="Search for owner(s)" 
				value="{{owners}}" data-type="OWNER" v_minLength='{"min":3}' v_required='{"message":"Add one or more Owner name(s)"}'>
			<i class="inverted circular blue search link icon" title="Search Owner Name" on-click="['VALIDATE_name', 'OWNER']"></i>
		</div>
	</div>
	
	<div class="field">
		<label>Group(s):</label>
		<div class="ui icon input">
			<input type="text" class="ui fluid input enterable" name="groups" placeholder="Search for group(s)" 
				value="{{groups}}" data-type="GROUP">
			<i class="inverted circular blue search link icon" title="Search Group Name" on-click="['VALIDATE_name', 'GROUP']"></i>
		</div>
	</div>
	
	
</div>

<div class="two fields resource-accordion-content">

	<div class="field">

		<div class="ui accordion owner-accordion">
			<div class="active title">
			    <i class="dropdown green icon"></i> <span style="color: steelblue">Searched Owner(s):<span>
			</div>
			<div class="active content" style="padding: 0em 1em;">
			
				<div class="ui stackable two column grid">
					{{#each selected_owners:idx}}
						<div class="column">
							<a class="ui label" title="REMOVE {{uid}} from owner list" on-click="['REMOVE_Resource', 'OWNER']">
							  <i class="red remove icon"></i>{{uid}}
							</a>
						</div>
					{{/each}}
				</div>
				
			</div>
		</div>
		
	</div>
	
	<div class="field">

		<div class="ui accordion group-accordion">
			<div class="active title">
			    <i class="dropdown green icon"></i> <span style="color: steelblue">Searched Group(s):<span>
			</div>
			<div class="active content" style="padding: 0em 1em;">
				
				<div class="ui stackable two column grid">
					{{#each selected_groups:idx}}
						<div class="column">
							<a class="ui label" title="REMOVE {{cn}} from group list" on-click="['REMOVE_Resource', 'GROUP']">
							  <i class="red remove icon"></i>{{cn}}
							</a>
						</div>
					{{/each}}
				</div>
				
			</div>
		</div>
		
	</div>
	
</div>
`;
// End of _requesterInformationsTemplate

// Start of _connectionInformationTemplate
var _connectionInformationTemplate=
`
<div class="field">
	<div class="ui dividing small header sp-conn-sub_header"> Connection Informations </div>
</div>

<div class="three fields">
	<div class="required field">
		<label>Entity Id:</label>
		<input type="text" class="ui fluid input validate_me" name="entityId" v_required='{"message":"Enter Valid Entity Id"}'
			placeholder="Valid Id" value="{{entityId}}">
	</div>
	
	<div class="required field">	
		<label>Connection Name:</label>
		<input type="text" class="ui fluid input validate_me" name="entityName" v_required='{"message":"Enter Valid Entity Name"}'
			placeholder="Conn. Name" value="{{entityName}}">
	</div>
	
	<div class="required field">
		<label>Assertion URL:</label>
		<input type="url" class="ui fluid input validate_me" name="acsUrl" v_required='{"message":"Enter Valid Assertion URL"}'
			placeholder="Enter Consumer URL" value="{{acsUri}}">
	</div>
</div>

<div class="two fields">
	<div class="field">
		<label style="visibility: hidden;">SAML Assertions signed:</label>
	    <div class="ui toggle checkbox">
		  <input type="checkbox" checked="{{signAssertion}}">
		  <label class="sp-chkbox-adjucent-label">SAML Assertions Signed?</label>
		</div>
	</div>
	
	<div class="field sp-auth-req-chkbox">
		<label style="visibility: hidden;">Signed Authentication Request:</label>
	    <div class="ui toggle checkbox">
		  <input type="checkbox" checked="{{signAuthReq}}">
		  <label class="sp-chkbox-adjucent-label">Signed Authentication Request?</label>
		</div>
	</div>
</div>

<div class="three fields">
	<div class="required field">
		<label>SAML Subject Value:</label>
		<select class="ui fluid dropdown sp-subject-value-ddn validate_me" v_required='{"message":"Select appropriate SAML Subject value"}'
			name="sp-subject-value-ddn" value={{subject.SAML_SUBJECT}}>
			<option value="">Choose Subject Value</option>
			<option value="email">Email</option>
			<option value="userId">UserId</option>
		</select>
	</div>
	
	<div class="required field">
		<label>SAML Subject Format:</label>
		<select class="ui fluid dropdown sp-subject-format-ddn validate_me" name="sp-subject-format-ddn" value={{subject.nameID_Format}}
			v_required='{"message":"Select appropriate SAML subject format"}'>
			<option value="">Choose Subject Format</option>
			<option title="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified" value="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified">	
				SAML:1.1:nameid-format:unspecified
			</option>
			<option title="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress" value="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress">
				SAML:1.1:nameid-format:emailAddress
			</option>
			<option title="urn:oasis:names:tc:SAML:2.0:attrname-format:basic" value="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
				SAML:2.0:attrname-format:basic
			</option>
		</select>
	</div>
	
	<div class="field">
	<label>Attributes:</label>
	<select class="ui search selection fluid dropdown sp-attribute-ddn"	name="sp-attribute-ddn" on-change="HANDLE_Attribute">
		<option value="">Select Required Attributes</option>
		<option value="givenname">First Name</option>
		<option value="sn">Last Name</option>
		<option value="uid">User Id</option>
		<option value="mail">Email Address</option>
		<option value="jobRole">Job Role</option>
		<option value="accesslevel">User Level</option>
		<option value="street">Address</option>
		<option value="co">Country</option>
		<option value="postalCode">Postal Code</option>
		<option value="l">City</option>
		<option value="st">State</option>
		<option value="memberOf">Group Membership</option>
		<option value="departmentNumber">Department Number</option>
	</select>
</div>
</div>

<div class="attributes_container field">
	<div class="field">
		{{#attributes: key,idx}}
			<div class="inline fields attribute-row">
				<div class="seven wide field">
					<span style="margin: auto; font-weight: bold; color: steelblue;" class="attribute_name" id="attribute_name_{{key}}">{{@this.getAttrText(key)}}</span>
				</div>
				<div class="seven wide field required">
					<div class="ui input">
						<input type="text" name="attrbute_value" value="{{attributes[key]}}">
					</div>
				</div>
				<div class="two wide field">
					<button class="ui red mini icon circular mini button" style="margin: 0.5em 0.5em; float: right;"
						data_key="{{key}}" id="sp-attribute-actions{{idx}}" title="Delete Attribute" on-click="DELETE_Attribute">
						<i class="ui trash icon" />
					</button>
				</div>
			</div>
		{{/attributes}}
	</div>
</div>

<div class="field spSLOServiceEndpoint">
	<label>SP SLOServiceEndpoint:</label>
	<input type="text" class="ui fluid input" name="spSLOServiceEndpoint" placeholder="SLO service Endpoint url" value="{{spSLOServiceEndpoint}}">
</div>

<div class="field sp-digisigned-text">
	 <div class="field required">
    	<label>SP X509 Certificate:</label>
	    <!--<textarea rows="2">{{spSLOcert}}</textarea>-->
	    <div class="fileUploader_target">
	    	Fileupload component go here...
	    </div>
	 </div>
</div>
`;
// End of _connectionInformationTemplate

// Start of _formActionsTemplate
var _formActionsTemplate=
`
<div class="field">
	<button class="ui reset grey button sp-con-reset_btn" on-click="RESET_Section">Reset</button>
	<button class="ui primary submit button sp-con-submit_btn" on-click="CREATE_Connection">Create Connection</button>
	<span style="font-weight: bold; font-size: 0.8em;" class="form-root-optional-txt"></span>
</div>
`;
// End of _formActionsTemplate

// Start of _ResourceResulModalTemplate
var _ResourceResulModalTemplate=
`
<div class="ui small modal resource-modal">
	
	<div class="ui header" style="background-color: steelblue; color: white;">	
		<div class="resource-modal-header-content">  
			<span class="resource-modal_text"> Searched results on Owner(s)/Group(s)</span> 
			<button class="ui circular icon yellow button" on-click="CLOSE_ResourceModal" style=" float: right; margin-top: -9px;">
				<i class="close large icon"></i>
			</button>
		</div>
	</div>
	
	<div class="ui scrolling content resource-modal-target">
		<div class="ui unstackable two column grid">
				
			{{#each resources:idx}}
				<!-- Userid selection -->
				<div class="column" {{#if resource_type !="USERID"}}style="display: none;"{{/if}}>
				  	<input type="radio" class="resource-chebox" idx="resource-chkbox{{idx}}" on-click="HANDLE_ResourceClick" value={{uid}}>
				  	<label class="resource-value-text" style="cursor:default;">{{uid}} {{#if null != givenName}}
				  		<span style="color: orange; font-weight: bold; font-size: 1.1em;">({{givenName}})</span>{{/if}}
				  	</label>
				</div>
				
				<!-- Owner(s) selection -->
				<div class="column" {{#if resource_type !="OWNER"}}style="display: none;"{{/if}}>
					<div class="ui checkbox">
					  	<input type="checkbox" class="resource-chebox" idx="resource-chkbox{{idx}}"	on-click="HANDLE_ResourceClick" value={{uid}}>
					  	<label class="resource-value-text" style="cursor:default;">{{uid}} {{#if null != givenName}}
					  		<span style="color: orange; font-weight: bold; font-size: 1.1em;">({{givenName}})</span>{{/if}}
					  	</label>
				  	</div>
				</div>
				
				<!-- Group(s) selection -->
				<div class="column" {{#if resource_type !="GROUP"}}style="display: none;"{{/if}}>
					 <div class="ui checkbox">
					 	<input type="checkbox" class="resource-chebox" idx="resource-chkbox{{idx}}" on-click="HANDLE_ResourceClick" value={{cn}}>
					  	<label class="resource-value-text" style="cursor:default;">{{cn}}</label>
					 </div>
				</div>
				
				<!-- AppPortfolio selection -->
				<div class="column" {{#if resource_type !="APP"}}style="display: none;"{{/if}}>
				 	<input type="radio" class="resource-chebox" idx="resource-chkbox{{idx}}" name="appPortfolio" 
				 		on-click="HANDLE_ResourceClick" value={{sys_id}}>
				  	<label class="resource-value-text" style="cursor:default;">{{name}}</label>
				</div>
			{{/each}}
			
		</div>
	</div>
	
</div>
`;
// End of _ResourceResulModalTemplate

// Start of loadingPageTemplate
var loadingPageTemplate=
`
<div class="ui segment add-conn-loading-content" style="height: 100%; margin: 0;">
  <div class="ui active inverted dimmer">
    <div class="ui large text loader" style="font-size: 3em;"></div>
  </div>
</div>
`;
// End of loadingPageTemplate

var _template=
`
<div class="sp-add-conn-header"><span class="sp-add-conn-text">Service Provider(SP) Connection</span></div>
<div class="ui grid sp-celled">
	<div class="column">
	
		<div class="ui segment" style="border: none;">
			<div class="ui large form form-root">
				{{#connection}}
				<div class="field">
					`+_primaryActionsTemplate+`
				</div>
				
				<div class="field">
					`+_requesterInformationsTemplate+`
				</div>
				
				<div class="field">
					`+_connectionInformationTemplate+`
				</div>
				
				<div class="field">
					`+_formActionsTemplate+`
				</div>
				{{/connection}}
			</div>
	    </div>
	
	</div>
</div>

`+_ResourceResulModalTemplate+`

`+loadingPageTemplate+`

`;

var RACTIVE= null;
// Ractive Component..
var SpConnectionComponent = Ractive.extend({
	template: _template,
	isOwnerSearched: false,
	isGroupSearched: false,
	isAppSearched: false,
	
	_defaultAttributes: {
		givenname:"First Name",
		sn:"Last Name",
		uid:"User Id",
		mail:"Email Address",
		jobRole:"Job Role",
		accesslevel:"User Level",
		street: "Address",
		co:"Country",
		postalCode:"Postal Code",
		l:"City",
		st:"State",
		memberOf:"Group Membership",
		departmentNumber:"Department Number"
	},
	
	isEditMode: false,
	setIsEditMode: function(_isEditMode){
		this.isEditMode = _isEditMode;
	}, 
	
	setConnectionObject: function(_con){
		this.set("connection", _con);
	},
	
	getAttrText: function(key){
		return (!key) ? "--" : this._defaultAttributes[key];
	},
	
	onConnectionAddedCallback: null,
	onConnectionAddedCallbackArgs: null,
	onConnectionAdded: function(fn){
		this.onConnectionAddedCallbackArgs= [];
		this.onConnectionAddedCallback = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++) 
				this.onConnectionAddedCallbackArgs[this.onConnectionAddedCallbackArgs.length] = arguments[i];
		}
	},
	
	stylizeAttributeRow(){
		this.targetEL.find(".attribute-row:even").css({"background": "beige", "padding": "6px", "border-radius": "5px","margin-bottom": "0"});
		this.targetEL.find(".attribute-row:odd").css({"background": "white", "padding": "6px","margin-bottom": "0"});
	},
	
	enableConnectionType: function(ACCEPT_COLOR, conType){
		if(!ACCEPT_COLOR && typeof ACCEPT_COLOR !== typeof "")
			return;
		if(!ACCEPT_COLOR)
			ACCEPT_COLOR= "green";
		
		var DEF_UNSELECTED_COLOR= "grey";
		var STNDARD_CONN_BTN= this.targetEL.find(".stndard-conn-btn");
		var DIGI_CONN_BTN= this.targetEL.find(".digi-conn-btn");
		var LOGOUT_CONN_BTN= this.targetEL.find(".logout-conn-btn");
		var CONN_BTNS= this.targetEL.find(".conn-btns");
		
		var BTNS= CONN_BTNS.find("button");
		BTNS.addClass(DEF_UNSELECTED_COLOR);
		BTNS.removeClass("green orange yellow teal olive violet brown purple pink red");
		
		if(conType === "STANDARD_CONN"){
			STNDARD_CONN_BTN.removeClass(DEF_UNSELECTED_COLOR);
			STNDARD_CONN_BTN.addClass(ACCEPT_COLOR);
			this.set("connection.connectionType", "basic");
			
		} else if (conType === "DIGITALLY_SIGNED_CONN"){
			DIGI_CONN_BTN.removeClass(DEF_UNSELECTED_COLOR);
			DIGI_CONN_BTN.addClass(ACCEPT_COLOR);
			this.set("connection.connectionType", "spslo");
			this.SLOServiceEndpointEL.show();
			
		} else if (conType === "LOGOUT_ENABLED_CONN"){
			LOGOUT_CONN_BTN.removeClass(DEF_UNSELECTED_COLOR);
			LOGOUT_CONN_BTN.addClass(ACCEPT_COLOR);
			this.set("connection.connectionType", "spslo");
			this.SLOServiceEndpointEL.show();
			
		} else {
			return false;
		}
		this.set("connection.connectionMode", conType);
	},
	
	bindEnterKeys: function(){
		var ractive= this;
		 $("input.enterable").each(function(idx, input){
			$(input).onEnter( function(e) {
				 var dataType= $(this).attr("data-type");
		        ractive.fire("VALIDATE_name", { node: e.currentTarget } , dataType);                
		     }); 
		 });
	},
	
	templateStyler: function(){
		this.bindEnterKeys();
		$("body").css("overflow","hidden");
		this.targetEL.css({"overflow-y":"auto","height":"94%","background":"whitesmoke"});
		this.targetEL.find('.sp-celled').css({"display":"contents"});
		
		this.SLOServiceEndpointEL= this.targetEL.find(".spSLOServiceEndpoint");
		this.SLOServiceEndpointEL.hide();
		
		this.LOADER= this.targetEL.find(".add-conn-loading-content");
		this.LOADER.css({"position": "fixed", "top": "0", "left": "0", "right": "0", "bottom": "0", "opacity": "0.8", "z-index": "3"});
	    
		this.LOADER.hide();
		
		// Styling icons...
		this.targetEL.find(".sp-right-margined").attr("style","margin-right: 10px !important;");
		
		// By-default 'DIGI_CONN_TEXTAREA' should be hidden...
		this.DIGI_CONN_TEXTAREA= this.targetEL.find("div.sp-digisigned-text");
		this.DIGI_CONN_TEXTAREA.hide();
		
		// By-default 'AUTH_REQ_TOGGLE_BTN' toggle btn should be hidden...
		this.AUTH_REQ_TOGGLE_BTN= this.targetEL.find(".sp-auth-req-chkbox");
		this.AUTH_REQ_TOGGLE_BTN.hide();
		
		this.targetEL.find(".sp-chkbox-adjucent-label").css({"font-weight":"bold"});
		
		var submitBtn= this.targetEL.find(".sp-con-submit_btn");
		
		this.targetEL.find(".sp-conn-sub_header").css({
			"background":"aliceblue"
			,"padding":"0.5em"
			,"border-radius":"0.2em"
			,"background":"#afafaf"
			,"border":"none"
			,"color":"white"
		});
		
		this.targetEL.find(".sp-add-conn-header").css({"background":"coral","padding":"0.5em","font-weight":"bold"});
		this.targetEL.find(".sp-add-conn-text").css({"color":"white","font-size":"1.2em"});
		
		if(this.isEditMode) {
			this.targetEL.find(".sp-con-reset_btn").hide();
			$(submitBtn).text("Update Connection");
		}
		
		// Init accordions
		this.GROUP_ACCORDION= this.targetEL.find('.group-accordion');
		this.GROUP_ACCORDION.accordion();
		this.GROUP_ACCORDION.hide();
		
		this.OWNER_ACCORDION= this.targetEL.find('.owner-accordion');
		this.OWNER_ACCORDION.accordion();
		this.OWNER_ACCORDION.hide();
		
		this.APP_NOT_ACCORDION= this.targetEL.find('.app-not-accordion');
		this.APP_NOT_ACCORDION.hide();
		
		initSemanticDropdowns();
	},
	
	// Backbone api functions....
	createConnection: function(URL, data){
		var ractive = this;
		var Connection = Backbone.Model.extend({
			urlRoot:URL,
		});
		var conn = new Connection();
		conn.save(data, {
			success: function(model, response, options){
				ractive.formMsgOpts.status= true;
				showResult(ractive.formMsgOpts);
				// This is to tell any dependant containers that POST operation
				// has done successfully...
				if(ractive.onConnectionAddedCallback){
					ractive.onConnectionAddedCallbackArgs[ractive.onConnectionAddedCallbackArgs.length] = response.data;
					ractive.onConnectionAddedCallback.apply(null,ractive.onConnectionAddedCallbackArgs);
				}
				setTimeout(function(){
					location.reload();
				}, 3000);
				
			},
			error: function(model, response, options){
				console.log("POST unsuccessful", response);
				ractive.formMsgOpts.status= false;
				showResult(ractive.formMsgOpts);
				ractive.enableLobiboxNotification(response);
			}
		});
	},
	
	modifyConnection: function(URL, data, id){
		var ractive= this;
		var Connection = Backbone.Model.extend({
			urlRoot:URL,
			idAttribute: '_id'
		});
		var conn = new Connection({
			_id: id
		});
		conn.save(data, {
			success: function(model, response, options){
				ractive.formMsgOpts.status= true;
				showResult(ractive.formMsgOpts);
				// This is to tell any dependant containers that PUT operation
				// has done successfully...
				if(ractive.onConnectionAddedCallback){
					ractive.onConnectionAddedCallbackArgs[ractive.onConnectionAddedCallbackArgs.length] = response.data;
					ractive.onConnectionAddedCallback.apply(null,ractive.onConnectionAddedCallbackArgs);
				}
			},
			error: function(model, response, options){
				console.log("PUT unsuccessful", response);
				ractive.formMsgOpts.status= false;
				showResult(ractive.formMsgOpts);
				ractive.enableLobiboxNotification(response);
			}
		});
	},
	
	fetchContents: function(url, successFn, errorFn){
		var Connection = Backbone.Model.extend();
		var Connections = Backbone.Collection.extend({
			model: Connection,
			url: url,
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
	// End of backbone api functions...
	
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
			// (errResp.data ? JSON.stringify(JSON.parse(errResp.data), null,
			// 10) : "")
			msg: errAppender ? errAppender : response.responseText
		});
		lobibox.$el.click(function(){
			$(this).css({"width":"40%"});
			$(this).find(".lobibox-notify-body").css({"overflow":"auto", "height": "25em"});
			$(this).find(".lobibox-notify-msg").css({"max-height":"99%", "word-break":"break-word", "margin-top":"0.3em"});
		});
	},
	
	spliceByElement: function(keypath, chkboxContainer, keyType, chkboxVal){
		if(!keypath || !chkboxVal)
			return false;
		var resArray= this.get(keypath);
		if(!resArray || !Array.isArray(resArray))
			return false;
		var ractive = this;
		$(chkboxContainer).find("input[value='"+chkboxVal+"']").each(function(idx, chkbox){
			$(chkbox).prop("checked", false);
			for ( var idx in resArray) {
				var val= resArray[idx][keyType];
				if(val === chkboxVal)
					ractive.splice(keypath, idx, 1);
			}
		});
		this.update(keypath);
	},
	
	updateCheckboxes: function(keypath, chkboxContainer, key){
		if(!keypath)
			return false;
		var resArray= this.get(keypath);
		if(!resArray || !Array.isArray(resArray))
			return false;
		
		// Always uncheck all checkboxes before checked..
		$(chkboxContainer).find(".resource-chebox").each(function(idx, el){ $(el).prop("checked", false); });
		for ( var idx in resArray) {
			$(chkboxContainer).find("input[value='"+ resArray[idx][key] +"']").each(function(idx, chkbox){
				$(chkbox).prop("checked", true);
			});
		}
		this.update();
	},
	
	pushUniqueElement: function(keypath, element){
		if(!this.get(keypath) && !Array.isArray(this.get(keypath)))
			this.set(keypath, []);
		if((this.get(keypath).indexOf(element) == -1) || !this.get(keypath).includes(element))
			this.push(keypath, element);
	},
	
	manageElementsByResource: function(){
		// Show resource accordions if resouece is available.
		if(this.get("selected_owners") && this.get("selected_owners").length > 0)
			this.OWNER_ACCORDION.show();
		if(this.get("selected_groups") && this.get("selected_groups").length > 0)
			this.GROUP_ACCORDION.show();
		if(this.get("selected_apps") && this.get("selected_apps").length > 0)
			this.APP_NOT_ACCORDION.show();
		
		// Clear input boxes..
		this.targetEL.find("input[name='owners']").val(null);
		this.targetEL.find("input[name='groups']").val(null);
		this.targetEL.find("input[name='appPortfolio']").val(null);
	},
	
	_triggerOnFileUploaded: function(ractive, uploadType, fileName, uploadResp){
		var conn= ractive.get("connection");
		if(!conn)
			throw "No connection object found on _triggerOnFileUploaded callback.";
		var oldCertObj= conn.spSloCert;
		if(uploadType == "DELETE")
			ractive.set("connection.spSloCert", null);
		else {
			// This condition will success only in ADD conn. mode..
			if(! oldCertObj || Object.keys(oldCertObj).length == 0 && uploadType == "POST")
				ractive.set("connection.spSloCert", uploadResp.data);
			else{
				// This condition will success only in EDIT conn. mode.
				ractive.set("connection.spSloCert", uploadResp.data);
				ractive.set("connection.spSloCert.staleInputFileId", oldCertObj.inputFileId);
			}
		}
	},
	
	element2ignore: function (resource_keypath, $el, ignoreClass, ractive){
		var array= ractive.get(resource_keypath);
		if(array && array.length > 0)  {
			if($el && $el.length > 0){
				$el.addClass(ignoreClass);	
				$el.addClass("bypass_me");
			}
		}
	},
	
	toUpdateChkboxes: false,
	_resourceType: null,
	on:{
		init: function(){
			RACTIVE= this;
			this.set("connection", null);
			this.set("resources", []);
			this.set("selected_owners", []);
			this.set("selected_groups", []);
			this.set("selected_apps",[]);
			this.set("selected_userid",[]);
			this.set("resource_type", null);
		},
		
		render: function(){
			this.targetEL = $(this.target);
			this.targetEL.css("position","relative");
			this.FORM_ROOT= this.targetEL.find(".form-root");
			
			this.OWNER_INPUT=$("input[data-type=OWNER]");
			this.GROUP_INPUT=$("input[data-type=GROUP]");
			this.APP_INPUT= $("input[data-type=APP]");
			
			// Intantiate fileUploader. Target is set leter, on demand..
			this.fileUploaderWidget= new Fileuploader({ target: "" });
			
			// Custom fileupload settings.
			this.fileUploaderWidget.set("fileuploadOptions",{
				auto: false,
				allowedExtensions: ["cer","der","crt"]
			});
			// File upload api endpoint
			this.fileUploaderWidget.set("uploadUrl",_pmtpfServer+"/document/document.json");
			
			// Fileupload render target on UI.
			this.fileUploaderWidget.onCompleteCallback(this._triggerOnFileUploaded, this);
			
			var connectionMode= "STANDARD_CONN";
			this.formMsgOpts= {
				status: null,
				successStatus: "Success",
				failureStatus: "Failed",
			};
			if(!this.isEditMode && !(this.get("connection")) || Object.keys(this.get("connection")).length == 0){
				// Add mode...
				this.set("connection", connectionObject);
				this.set("connection.attributes", {});
				this.set("connection.connectionMode", connectionMode);
				
				this.formMsgOpts.successStatus= "New Connection Created";
				this.formMsgOpts.failureStatus= "Creation Failed";
			} else {
				// Edit mode...
				this.targetEL.find("input[name='entityId']").prop("disabled", true);
				this.formMsgOpts.successStatus= "Connection Updated";
				this.formMsgOpts.failureStatus= "Updation Failed";

				connectionMode= this.get("connection.connectionMode");
				this.set("selected_owners", this.get("connection.owners"));
				this.set("selected_groups", this.get("connection.groups"));
				this.set("selected_apps",[]);
				this.push("selected_apps", this.get("connection.appPortfolio"));
				
				// Update the corresponding checkboxes
				if(this.get("selected_owners") && this.get("selected_owners").length > 0)
					this.updateCheckboxes("selected_owners", $(".resource-modal"), "uid");
				if(this.get("selected_groups") && this.get("selected_groups").length > 0)
					this.updateCheckboxes("selected_groups", $(".resource-modal"), "cn");
				if(this.get("selected_apps") && this.get("selected_apps").length > 0)
					this.updateCheckboxes("selected_apps", $(".resource-modal"), "sys_id");
				
				this.toUpdateChkboxes= true;
				this.stylizeAttributeRow();
				
				this.element2ignore("selected_owners", this.OWNER_INPUT, "ignore_me", this);
//				this.element2ignore("selected_groups", this.GROUP_INPUT, "ignore_me", this);
				this.element2ignore("selected_apps", this.APP_INPUT, "ignore_me", this);
			}
				
			this.templateStyler();
			this.manageElementsByResource();
			
			initFormMsgDisplay(this.targetEL.find(".form-root"), this.targetEL.find(".sp-con-submit_btn"), this.targetEL.find(".form-root-optional-txt"));
			this.fire("ENABLE_Connection", null, connectionMode);
		
		},
		
		HANDLE_Attribute: function(ctx){
			var ATTR_DROPDOWN = $(ctx.node);
			var attrObj = this.get("connection.attributes");
			var ATTR_VAL = ATTR_DROPDOWN.dropdown('get value');
			attrObj[ATTR_VAL] = null;
			this.update();
			this.targetEL.find("#attribute_name_"+ATTR_VAL).text(ATTR_DROPDOWN.dropdown('get text'));
			
			this.stylizeAttributeRow();
		},
		
		ENABLE_Connection:function(ctx, CONN_TYPE){
			this.DIGI_CONN_TEXTAREA.hide();
			this.AUTH_REQ_TOGGLE_BTN.hide();
			this.SLOServiceEndpointEL.hide();
			var renderFileUploader= false;
			if (CONN_TYPE === "DIGITALLY_SIGNED_CONN"){
				this.DIGI_CONN_TEXTAREA.show();
				this.SLOServiceEndpointEL.show();
				// Now fileupload target is set on demand.
				renderFileUploader= true;
			} 
			if (CONN_TYPE === "LOGOUT_ENABLED_CONN"){
				this.DIGI_CONN_TEXTAREA.show();
				this.AUTH_REQ_TOGGLE_BTN.show();
				this.SLOServiceEndpointEL.show();
				renderFileUploader= true;
			}
			
			if(renderFileUploader){
				if(this.fileUploaderWidget.target)
					this.fileUploaderWidget.unrender();
				this.fileUploaderWidget.render(this.targetEL.find(".fileUploader_target"));
			}
			this.enableConnectionType("yellow", CONN_TYPE);
			destroyValidation();
			initProveValidation(this.FORM_ROOT);
		},
		
		CREATE_Connection: function(ctx){
			var submitBtn= this.targetEL.find(".sp-con-submit_btn");
			submitBtn.removeClass("loading");
			submitBtn.addClass("loading");
			
			var appPortfolio= this.get("selected_apps")[0];
			
			if(appPortfolio){
				this.set("connection.appPortfolio.name", appPortfolio.name);
				this.set("connection.appPortfolio.sys_id", appPortfolio.sys_id);
			} else {
				$("input[name=appPortfolio]").val(null);
			}
			
			var data = this.get("connection");

			// Special treatment for groups and owners...
			data.groups= this.get("selected_groups");
			data.owners= this.get("selected_owners");
			if(data.groups){
				if(!Array.isArray(data.groups))
					data.groups= data.groups.split(',');
			}else {
				data.groups=null;
				$("input[name=groups]").val(null);
			}
				
			if(data.owners){
				if(!Array.isArray(data.owners))
					data.owners= data.owners.split(',');
			} else {
				data.owners=null;
				$("input[name=owners]").val(null);
			}
			if(data.owners.length == 0){
				data.owners= null;
				$("input[name=owners]").val(null);
			}
			if(data.groups.length == 0){
				data.groups= null;
				$("input[name=groups]").val(null);
			}
				
			// Validate spSLOcert ...
			if(data.connectionMode !== "STANDARD_CONN"){
				if(! data.spSloCert || Object.keys(data.spSloCert).length == 0  || !data.spSloCert.inputFileId){
					alert("SP X509 Certificate is mandatory for Digitally signed and Logout Enabled type connection.");
					submitBtn.removeClass("loading");
					return false;
				}
			}
			
			// Proceed to 'submit' if form is validated...
			if(isContainerValid(this.FORM_ROOT)){
				if(this.isEditMode === true){
					// Handling EDIT MODE operations..
					this.modifyConnection(_pmtpfServer+"/connections.json", data, data.entityId);
				} else{
					// Handling ADD MODE operations..
					this.createConnection(_pmtpfServer+"/connections.json", data);
				}
			} else {
				submitBtn.removeClass("loading");
				this.formMsgOpts.status= false;
				this.formMsgOpts.optionalErrorText="Fill out all mandatory * fields";
				showResult(this.formMsgOpts);
				return false;
			}
		},
		
		RESET_Section: function(ctx){
			// Refresh the create connection page... [NOT CONFIRMED]
			if(!this.isEditMode)
				window.location.reload();
		},
		
		DELETE_Attribute: function(ctx){
			delete this.get("connection.attributes")[$(ctx.node).attr("data_key")];
			this.update('connection.attributes');
		},
		
		VALIDATE_name: function(ctx, type){
			// Always capture the input element.
			var $input = $(ctx.node).prev("input").length == 0 ? $(ctx.node) : $(ctx.node).prev("input");
			// 'blur()' to avoide auto suggestion pop-ups below text boxes while
			// modal opening..
			$input.blur();
			
			var RESOURCE_MODAL= $(".resource-modal");
			var MODAL_HEADER_TEXT_EL= RESOURCE_MODAL.find(".resource-modal_text");
			
			this.targetEL.css("overflow-y","hidden");
			this.LOADER.show();
			this._resourceType= type;
			this.set("resource_type", type);
			
			// Based on type set _resourceType value for further use.
			
			var ractive= this;
			var ldapurl= _pmtpfServer+"/validateResource.json/" + $input.val() +"/"+ this._resourceType + "?retriveBy=";
			if(type == "OWNER"){
				ldapurl += "uid&retriveProps=uid,givenName";
				MODAL_HEADER_TEXT_EL.text("Searched result on Owner(s)");
				this.OWNER_INPUT= $("input[data-type="+ type +"]");
			}
			if(type == "GROUP"){
				ldapurl += "cn";
				MODAL_HEADER_TEXT_EL.text("Searched result on Group(s)");
				this.GROUP_INPUT= $("input[data-type="+ type +"]");
			}
			if(type == "APP"){
				ldapurl += "&retriveProps=sys_id,name";
				MODAL_HEADER_TEXT_EL.text("Searched result on App Portfolio");
				this.APP_INPUT= $("input[data-type="+ type +"]");
			}
			
			// Send GET-Api request to fetch available resources..
			this.fetchContents(ldapurl, 
				function(model, response, options){
					var resources= null;
					if(!response)
						throw "Invalid FETCH request for owner(s)/group(s)/appPortfolio(s)";
					var resources= response.data;
					// Show Resource modal if any resource is found.
					if(resources && resources.length>0) {
						if(ractive._resourceType === "APP")
							ractive.set("resources",JSON.parse(resources));
						else
							ractive.set("resources",resources);
						ractive.LOADER.hide();
						ractive.targetEL.css("overflow-y","auto");
						
						// select chkboxes besed on picked value.
						if(ractive.toUpdateChkboxes){
							if(ractive._resourceType === "OWNER")
								ractive.updateCheckboxes("selected_owners", RESOURCE_MODAL, "uid");
							if(ractive._resourceType === "GROUP")
								ractive.updateCheckboxes("selected_groups", RESOURCE_MODAL, "cn");
							if(ractive._resourceType === "APP")
								ractive.updateCheckboxes("selected_apps", RESOURCE_MODAL, "sys_id");
						}
						
						// Show resource modal
						RESOURCE_MODAL.modal({autofocus: false, inverted: true, closable: false, allowMultiple: true}).modal("show");
						
						// Enable hover action on checkboxs...
						RESOURCE_MODAL.find(".resource-value-text").hover(function(){
							$(this).css({"color":"steelblue"});
						},function(){
							$(this).css({"color":"black"});
						});
						
					} else {
						ractive.set("resourcces", []);
						ractive.LOADER.hide();
						alert("No such Owner/Group found.");
						return false;
						// TODO: Handle empty resource warning msg
					}
			}, function(model, response, options){
				console.log("Fetch unsuccess:", response);
				ractive.LOADER.hide();
				ractive.enableLobiboxNotification(response);
			});
			
		},
		
		CLOSE_ResourceModal: function(){
			this.manageElementsByResource();
			$(".resource-modal").modal("hide");
		},
		
		HANDLE_ResourceClick: function(ctx){
			var INDEX= ctx.get("idx");
			RESOURCE_OBJ= this.get("resources")[INDEX];
			var CHKBOX= $(ctx.node);
			
			elementNOT2ignore(this.OWNER_INPUT, "ignore_me");
//			elementNOT2ignore(this.GROUP_INPUT, "ignore_me");
			elementNOT2ignore(this.APP_INPUT, "ignore_me");
			
			
			if(!CHKBOX.prop("checked")){
				// Remove element once unchecked.
				if(this._resourceType === "OWNER"){
					this.spliceByElement("selected_owners", $(".resource-modal"), "uid", CHKBOX.val());
					if(null == this.get("selected_owners") || this.get("selected_owners").length == 0)
						this.OWNER_ACCORDION.hide(300);
				}
				if(this._resourceType === "GROUP"){
					this.spliceByElement("selected_groups", $(".resource-modal"), "cn", CHKBOX.val());
					if(null == this.get("selected_groups") || this.get("selected_groups").length == 0)
						this.GROUP_ACCORDION.hide(300);
				}
				if(this._resourceType === "APP"){
					this.spliceByElement("selected_apps", $(".resource-modal"), "sys_id", CHKBOX.val());
					if(null == this.get("selected_apps") || this.get("selected_apps").length == 0)
						this.APP_NOT_ACCORDION.hide(300);
				}
				
				CHKBOX.prop("checked", false);
			} else {
				// Push element once checked.
				if(this._resourceType === "OWNER")
					this.pushUniqueElement("selected_owners", RESOURCE_OBJ);
				if(this._resourceType === "GROUP")
					this.pushUniqueElement("selected_groups", RESOURCE_OBJ);
				if(this._resourceType === "APP"){
					this.set("selected_apps", []);
					this.push("selected_apps", RESOURCE_OBJ);
				}
				CHKBOX.prop("checked", true);
			}
			
			this.toUpdateChkboxes= true;
			
			this.element2ignore("selected_owners", this.OWNER_INPUT, "ignore_me", this);
//			this.element2ignore("selected_groups", this.GROUP_INPUT, "ignore_me", this);
			this.element2ignore("selected_apps", this.APP_INPUT, "ignore_me", this);
			
			// private functions...
			function elementNOT2ignore($el, ignoreClass){
				if($el && $el.length > 0){
					$el.removeClass(ignoreClass);	
					$el.addClass("validate_me");
				}
			};
			
		},
		
		REMOVE_Resource: function(ctx, type){
			// On unchecked remove Resource based on types
			var value2erase= $(ctx.node).text();
			var index= ctx.get("idx");
			
			// Remove elements by index from owners/groups..
			if(type === "OWNER"){
				this.splice("selected_owners", index, 1);
				modifyElementsAndResourcesOnUNCHECKED("selected_owners", this.OWNER_ACCORDION, this.OWNER_INPUT, this);
			}
			if(type === "GROUP"){
				this.splice("selected_groups", index, 1);
				modifyElementsAndResourcesOnUNCHECKED("selected_groups", this.GROUP_ACCORDION, null, this);
			}
			if(type === "APP"){
				this.splice("selected_apps", index, 1);
				modifyElementsAndResourcesOnUNCHECKED("selected_apps", this.APP_NOT_ACCORDION, this.APP_INPUT, this);
			}
			
			// Private function...
			function modifyElementsAndResourcesOnUNCHECKED(resource_keypath, ACCORDION_EL, RES_INPUT, ractive){
				var resArray= ractive.get(resource_keypath);
				if(resArray.length == 0){
					ACCORDION_EL.hide(300);
					$(".resource-modal").find(".resource-chebox").removeClass("chkbox-checked");
					if(!RES_INPUT || RES_INPUT.length > 0){
						RES_INPUT.addClass("validate_me");
						RES_INPUT.removeClass("ignore_me");
						RES_INPUT.removeClass("bypass_me");
					}
				}
			};
			
			this.toUpdateChkboxes=true; 
		},
		
	}
}); 

Ractive.components.GlobalSpConnectionComponent = SpConnectionComponent;