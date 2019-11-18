/**
 * 'customValidation.js' relies on prove.js and decorator.js
 * --------------------------------------------------------- 
 *  abhisekm
 */

var _defaultValidationMsgs = {
	"v_required" 	: "Required: please provide a value",
	"v_length" 		: "Length must be within: {0} and {1}",
	"v_minLength" 	: "Less than minimum length",
	"v_maxLength" 	: "More than maximum length"
};

// PROVE validator configuration
function initProveValidation(parentElement) {
	console.log("Initializing validation..");
	var targetContainer = $(parentElement).find('.validate_me');
	if (!targetContainer || targetContainer.length == 0)
		throw "Add 'validate_me' to those fields require validation inside container.";
	var validatorObj = {
		fields : {},
		submit : {}
	};
	var formFields = {};
	targetContainer.each(function(index, el) {
		var $el = $(el);
		var validators = {};
		var fieldName = null;

		// 1. Create each field object by element name attribute..
		fieldName = $el.attr('name');

		// 1.1. Handle dropdowns(Special treatment)..
		if (!fieldName && $el.hasClass('dropdown') && !$el.is('select')) {
			fieldName = $el.find('select').attr('name');
			$el = $el.find('select');
		}

		// 2. Handle Validation msg for element..
		$.each(_defaultValidationMsgs, function(vRule, defaultMsg) {
			formFields[fieldName] = {};
			var _vRuleValue = {};
			if (!$el.attr(vRule))
				return false;
			if ($el.attr(vRule))
				// 2.1. each validable elements must contain 'vRule' attribute
				_vRuleValue = JSON.parse($el.attr(vRule));
			switch (vRule) {
			
			// 2.1.1. Eg: <input v_required='{"message":""}'>
			case "v_required":
				validators.proveRequired = {
					message : _vRuleValue['message'] || defaultMsg
				}
				break;
				
			// 2.1.2. Eg: <input v_maxLength='{"max":""}'>
			case "v_maxLength":
				validators.proveLength = {
					message : _vRuleValue['message'] || defaultMsg,
					max : _vRuleValue['value']
				}
				break;
				
			// 2.1.3. Eg: <input v_minLength='{"min":""}'>
			case "v_minLength":
				validators.proveLength = {
					message : _vRuleValue['message'] || defaultMsg,
					min : _vRuleValue['value']
				}
				break;
		
			// 2.1.4. Eg: <input v_length='{"min":"", "max":""}'>
			case "v_length":
				validators.proveLength = {
					message : (_vRuleValue['message'] || defaultMsg).format(
							_vRuleValue.min, _vRuleValue.max)
				}
				if (_vRuleValue.min)
					validators.proveLength.min = _vRuleValue['min'];
				if (_vRuleValue.max)
					validators.proveLength.max = _vRuleValue['max'];
				break;
			}
		});

		// 3. Handle events for dropdown, input elements, textareas..
		if (!$el.hasClass('dropdown') && $el.is('input, textarea'))
			formFields[fieldName]["trigger"] = "blur";
		else
			formFields[fieldName]["trigger"] = "change";
		formFields[fieldName]["validators"] = validators;
	});
	
	// 4. Finalize validator object
	validatorObj['fields'] = formFields;
	
	// 5. Invoke 'initDecorativeValidation' to enable custom decorator with error.
	initDecorativeValidation(parentElement, validatorObj);
};


// PROVE validator and decorator invokation via parent container
var form= null;
function initDecorativeValidation(parentElement, validatorObj) {
	form = $(parentElement).prove(validatorObj);
	form.decorate('myCustomDecorator');
};

function destroyValidation(){
	if(form)
		form.data('prove').destroy();
}

// PROVE 'custom-decorator' configuration based on 'field trigger(s)'
$.fn.myCustomDecorator = function(options) {
	
	// Return from here if status is not validated...
	if (options.status != 'validated')
		return;
	var targetField = $(this);
	var closestParent = targetField.closest('div');
	var fieldTypeParent = $(closestParent).parent('.field');
	
	// Decorate input, textarea, radio, checkbox...
	if (targetField.is('input, textarea, radio, checkbox')) {
		// 'success', 'danger', 'warning', 'reset'
		switch (options.validation) { 
			case "danger":
				
				if(toIgnore(targetField, closestParent, fieldTypeParent))
					return;
				closestParent.addClass('error');
				if (fieldTypeParent.length > 0)
					fieldTypeParent.addClass('error');
				targetField.attr('title', options.message);
				
				// TODO: Decoration to add for radio, checkbox...
				break;
			case "success":
			case "reset":
				
				if (fieldTypeParent.length > 0)
					fieldTypeParent.removeClass('error');
			
				closestParent.removeClass('error');
				targetField.removeAttr('title');
				
				break;
		}
	}
	
	// Decorate dropdown...
	if (targetField.is('.ui.dropdown') || targetField.is('select')) {
		switch (options.validation) {
			
			case "danger":
				
				if(toIgnore(targetField, closestParent, fieldTypeParent))
					return;
				if (fieldTypeParent.length > 0)
					fieldTypeParent.addClass('error');
//				if (closestParent.length > 0)
//					closestParent.addClass('error');
				targetField.attr('title', options.message);
				
				break;
				
			case "success":
			case "reset":
				
//				if (closestParent.length > 0)
//					if (closestParent.hasClass('error'))
//						closestParent.removeClass('error');
				if (fieldTypeParent.length > 0)
					if (fieldTypeParent.hasClass('error'))
						fieldTypeParent.removeClass('error');
				targetField.removeAttr('title');
				
				break;
		}
	}

};

function toIgnore(targetField, closestParent, fieldTypeParent){
	var toIgnore= false;
	if (targetField.hasClass('ignore_me')
			|| closestParent.hasClass('ignore_me')
			|| fieldTypeParent.hasClass('ignore_me')) {
		toIgnore= true;
	}
	
	if (targetField.hasClass('ignore_once')
			|| closestParent.hasClass('ignore_once')
			|| fieldTypeParent.hasClass('ignore_once')) {
		
		targetField.removeClass('ignore_once');
		closestParent.removeClass('ignore_once');
		fieldTypeParent.removeClass('ignore_once');
		
		toIgnore= true;
	}
	return toIgnore;
};


// Invoke this function that returns boolean value after container validation.
function isContainerValid(container) {
	
	//Private function to check elements to bypass authentication.
	function bypassElement(){
		var _isValid= true;
		var targetContainer = $(container).find('.validate_me');
		if (!targetContainer || targetContainer.length == 0)
			return true;
		targetContainer.each(function(index, el) {
			if(!$(el).hasClass("dropdown")){
				if(!$(el).val() && !$(el).hasClass("bypass_me")){
					return _isValid= false;
				}
			} else {
				var ddnval= $(el).find("option:selected").val();
				if(!ddnval)
					return _isValid= false;
			}
		});
		return _isValid;
	};
	
	var dfd = container.validate();
	
	dfd.done(function(isValid) { 
		// If validation failed, then check bypass authentication.
		if(!isValid)
			isValid= bypassElement();
		dfd['isValid'] = isValid; 
	});
	return dfd.isValid;
//	container.validate().done(function(isValid) { return isValid; });
};