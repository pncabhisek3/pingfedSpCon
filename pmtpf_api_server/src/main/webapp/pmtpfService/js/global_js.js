// Standard operations to perform once document is ready...
$(document).ready(function() {
	initSemanticDropdowns();
});

//initializing dropdowns..
function initSemanticDropdowns(dropdowns, options) {
	var $ddn = null;
	if ($(dropdowns).length == 0)
		$ddn = $(".ui.dropdown");
	if (!options)
		$ddn.dropdown();
	if (options && Object.keys(options).length > 0)
		$ddn.dropdown(options);
};

// Sticky table header
function stickyHeader(scrollableEl, theadEl){
	if(!scrollableEl || $(scrollableEl).length ==0)
		throw "No scrollable container or element found.";
	if(!theadEl || $(theadEl).length ==0)
		theadEl= "thead";
	$(scrollableEl).on("scroll", function(){
	   var translate = "translate(0,"+this.scrollTop+"px)";
	   theadEl= $(scrollableEl).find(theadEl);
	   theadEl.attr("style","z-index:5 !important");
	   theadEl.css({"transform":translate});
	});
};

// Initialize Search bar...
function initSearchBar(searchBarContainer, options, applyCustomCss){
	if(!searchBarContainer || $(searchBarContainer).length == 0)
		throw "Search input box not found";
	if(!options || Object.keys(options).length == 0)
		$(searchBarContainer).easyAutocomplete();
	else{
		$(searchBarContainer).find(".search-con-input").easyAutocomplete({
			data: options.data || null,
			getValue: options.getValue || null,
			list: {
				maxNumberOfElements: 15,
				match: { 
					enabled: options.list.match.enabled || true
				},
				sort: {
					enabled: options.list.sort.enabled || true
				},
				onClickEvent: options.list.onClickEvent || function() {
					console.log("onClickEvent fired...");
				},
			}
		});
	}
	if(applyCustomCss) {
		$(searchBarContainer).css({"position":"absolute","right":"0","width":"400"});
		$(searchBarContainer).find(".search-con-input").css({"width":"400"});
		$(searchBarContainer).find(".easy-autocomplete").css({"width":"100% !important"});
		$(searchBarContainer).find(".easy-autocomplete-container").attr("style","width:400;");
	}
};

/*
 * param1: Provide any parent container selector("id, class") || default is "form-root"
 * param2: provide submit button selector ("id, class") || default is "form-root-submit-btn"
 * 
 * options: {
 *	// Parent of root container.
 * 	root: "form-root",
 * 
 *	// Submit button in the form container.
 * 	submitButton: "form-root-submit-btn",
 * 
 *	// Duration to show the status integer value.
 *  duration: 3000,
 *  
 *  // Is successFul of failed boolean value.
 *  status: true,
 *  
 *  // Success status message.
 *  successStatus: "Success",
 *  
 *  // failureStatus message.
 *  failureStatus: "Failed",
 *  
 *  // Show loading on button before Api response result.
 *  loadable: false, 
 * };
 * ------------------------------------------------------------------
 * Invoke this function on component render to enable formMsgDisplay.
 */
var formOptions= null;
function initFormMsgDisplay(formContainer, submitButton, opionalTxtEL){
	if(!formContainer)
		throw "No form msg options found.";
		
	// Checking root...
	var $root= $(formContainer)
	if($root.length == 0)
		$root= $(".form-root");
	if(null == $root || $root.length == 0)
		$root= $("#form-root");
	if(null == $root || $root.length == 0)
		throw "No parent form container found. Or use 'form-root' as class or id attribute.";
	
	// Checking submit btn...
	var $submitBtn= $root.find(submitButton);
	if(null == $submitBtn || $submitBtn.length == 0)
		$submitBtn= $root.find(".form-root-submit-btn");
	if(null == $submitBtn || $submitBtn.length == 0)
		$submitBtn= $root.find("#form-root-submit-btn");
	if(null == $submitBtn || $submitBtn.length == 0)
		throw "No submit button found in form container..",elements.root;
	
	// OptionalTextEL...
	var $opionalTxtEL= $root.find(opionalTxtEL);
	if(null == $opionalTxtEL || $opionalTxtEL.length == 0)
		$opionalTxtEL= $root.find(".form-root-optional-txt");
	if(null == $opionalTxtEL || $opionalTxtEL.length == 0)
		$opionalTxtEL= $root.find("#form-root-optional-txt");
	if(null == $opionalTxtEL || $opionalTxtEL.length == 0)
		throw "No submit button found in form container..",elements.root;
	
	formOptions = {
		root: $root,
		submitButton: $submitBtn,
		optionalTxtEL: $opionalTxtEL
	};
};

// Invoke "showResult()" anywhere to show formMsg result. 
function showResult(options){
	if(null == formOptions || Object.keys(formOptions).length == 0)
		throw "Form msg display must be initialized. Invoke 'initFormMsgDisplay()' on component render.";
	var defaultOptions = {
		duration: options.duration || 5000,
		status: (options.status === true || options.status=== false) ? options.status : null,
		successStatus: options.successStatus || "Success",
		failureStatus: options.failureStatus || "Failed",
		optionalSuccessText: options.optionalSuccessText || "",
		optionalErrorText: options.optionalErrorText || ""
	};
	var submitBtn= formOptions.submitButton;
	var submitBtnDefaultClass= submitBtn.attr("class");
	var submitBtnDefaultText= submitBtn.text();
	var _optTextEL= formOptions.optionalTxtEL;
	var optFormText= _optTextEL.text();
	
	if(defaultOptions.status === true){
		submitBtn.removeClass("primary blue grey negative loading");
		submitBtn.text(defaultOptions.successStatus);
		submitBtn.addClass("positive");
		_optTextEL.text(defaultOptions.optionalSuccessText);
		_optTextEL.css("color","green");
	} 
	if(defaultOptions.status === false){
		submitBtn.removeClass("primary blue grey positive loading");
		submitBtn.text(defaultOptions.failureStatus);
		submitBtn.addClass("negative");
		_optTextEL.text(defaultOptions.optionalErrorText);
		_optTextEL.css("color","red");
	}
	setTimeout(function(){ 
		submitBtn.addClass(submitBtnDefaultClass);
		submitBtn.removeClass("loading positive negative grey");
		if(submitBtnDefaultText)
			submitBtn.text(submitBtnDefaultText);
		_optTextEL.text(null);
		_optTextEL.css("color","");
		
	}, defaultOptions.duration);
};

function syntaxHighlight(json) {
    if (typeof json != 'string') {
         json = JSON.stringify(json, undefined, 10);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}

function bineEnterKey(elements){
	if(!elements || $(elements).length==0)
		throw new "Elements not found to bind enter key".
	$.each($(elements), function(idx, el){
		$(el).bind("enterKey",function(e){
		   //do stuff here
		});
	});
	$('textarea').bind("enterKey",function(e){
		   //do stuff here
		});
		$('textarea').keyup(function(e){
		    if(e.keyCode == 13)
		    {
		        $(this).trigger("enterKey");
		    }
		});
}

