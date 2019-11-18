// Start of _fileuploadActionsTemplate
var _fileuploadActionsTemplate=
`
<span class="fileupload-action-container" style="position: absolute; top: 45px; right: 45px;">
	<button class="ui inverted mini red button delete_button" on-click="Delete_File"> Delete </button>
	<button class="ui inverted mini brown button cancel_button" on-click="Cancel_File"> Clear </button>
	<button class="ui inverted mini violet button select_button" on-click="SELECT_File"> Import </button>
	<button class="ui inverted mini green button upload_button" on-click="UPLOAD_File"> Upload </button>
</span>
`;
// End of _fileuploadActionsTemplate

// Start of _fineUploaderTemplate
var _fineUploaderTemplate=
`
<div id="fine-uploader-gallery" style="position: relative"></div>

<div id="qq-template-gallery" style="display: none;">
	 <div class="qq-uploader-selector qq-uploader qq-gallery" qq-drop-area-text="Drop files here">
	    
	    <!-- progress bar element -->
	    <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
	        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
	    </div>
	    
	    <!-- DnD element -->
	    <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
	        <span class="qq-upload-drop-area-text-selector"></span>
	    </div>
	    
	    <!-- Default upload button whichi is hidden explicitly -->
	    <div class="qq-upload-button-selector qq-upload-button">
	        <div>Upload a file</div>
	    </div>
	    
	    <!-- Processing spinner -->
	    <span class="qq-drop-processing-selector qq-drop-processing">
	        <span>Processing dropped files...</span>
	        <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
	    </span>
	    <ul class="qq-upload-list-selector qq-upload-list" role="region" aria-live="polite" aria-relevant="additions removals">
	        <li>
	            <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
	            <div class="qq-progress-bar-container-selector qq-progress-bar-container">
	                <div style="margin-top: -8px; height: 5px;" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
	            </div>
	            <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
	            <div class="qq-thumbnail-wrapper">
	                <img class="qq-thumbnail-selector" qq-max-size="120" qq-server-scale>
	            </div>
	            <button type="button" class="qq-upload-cancel-selector qq-upload-cancel">X</button>
	            <button type="button" class="qq-upload-retry-selector qq-upload-retry">
	                <span class="qq-btn qq-retry-icon" aria-label="Retry"></span>
	                Retry
	            </button>
	
	            <div class="qq-file-info">
	                <div class="qq-file-name">
	                    <span class="qq-upload-file-selector qq-upload-file"></span>
	                    <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
	                </div>
	                <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
	                <span class="qq-upload-size-selector qq-upload-size"></span>
	                <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">
	                    <span class="qq-btn qq-delete-icon" aria-label="Delete"></span>
	                </button>
	                <button type="button" class="qq-btn qq-upload-pause-selector qq-upload-pause">
	                    <span class="qq-btn qq-pause-icon" aria-label="Pause"></span>
	                </button>
	                <button type="button" class="qq-btn qq-upload-continue-selector qq-upload-continue">
	                    <span class="qq-btn qq-continue-icon" aria-label="Continue"></span>
	                </button>
	            </div>
	        </li>
	    </ul>
	
	    <dialog class="qq-alert-dialog-selector">
	        <div class="qq-dialog-message-selector"></div>
	        <div class="qq-dialog-buttons">
	            <button type="button" class="qq-cancel-button-selector">Close</button>
	        </div>
	    </dialog>
	
	    <dialog class="qq-confirm-dialog-selector">
	        <div class="qq-dialog-message-selector"></div>
	        <div class="qq-dialog-buttons">
	            <button type="button" class="qq-cancel-button-selector">No</button>
	            <button type="button" class="qq-ok-button-selector">Yes</button>
	        </div>
	    </dialog>
	
	    <dialog class="qq-prompt-dialog-selector">
	        <div class="qq-dialog-message-selector"></div>
	        <input type="text">
	        <div class="qq-dialog-buttons">
	            <button type="button" class="qq-cancel-button-selector">Cancel</button>
	            <button type="button" class="qq-ok-button-selector">Ok</button>
	        </div>
	    </dialog>
	</div>
</div>
`;
// End of _fineUploaderTemplate

// Start of _fineUploaderTemplate
var _fineuploaderTemplate=
`
<div class="ui segment">
	
	`+_fineUploaderTemplate+`
	
	`+ _fileuploadActionsTemplate +`
	
</div>
`;
// End of _fineuploaderTemplate


var _template=
`
	`+_fineuploaderTemplate+`
	
`;

var Fileuploader = Ractive.extend({
	
	template: _template,
	
	// Notify parent component as soon as file is uploaded or not uploaded..
	onCompleteCallbackFn: null,
	onCompleteCallbackFnArgs: null,
	onCompleteCallback: function(fn){
	
		this.onCompleteCallbackFnArgs= [];
		this.onCompleteCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onCompleteCallbackFnArgs[this.onCompleteCallbackFnArgs.length] = arguments[i];
		}
	},
	
	onErrorCallbackFn: null,
	onErrorCallbackFnArgs: null,
	onErrorCallback: function(fn){
	
		this.onErrorCallbackFnArgs= [];
		this.onErrorCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onErrorCallbackFnArgs[this.onErrorCallbackFnArgs.length] = arguments[i];
		}
	},
	
	onProgressCallbackFn: null,
	onProgressCallbackFnArgs: null,
	onProgressCallback: function(fn){
	
		this.onProgressCallbackFnArgs= [];
		this.onProgressCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onProgressCallbackFnArgs[this.onProgressCallbackFnArgs.length] = arguments[i];
		}
	},
	
	onTotalProgressCallbackFn: null,
	onTotalProgressCallbackFnArgs: null,
	onTotalProgressCallback: function(fn){
	
		this.onTotalProgressCallbackFnArgs= [];
		this.onTotalProgressCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onTotalProgressCallbackFnArgs[this.onTotalProgressCallbackFnArgs.length] = arguments[i];
		}
	},
	
	onSubmitCallbackFn: null,
	onSubmitCallbackFnArgs: null,
	onSubmitCallback: function(fn){
	
		this.onSubmitCallbackFnArgs= [];
		this.onSubmitCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onSubmitCallbackFnArgs[this.onSubmitCallbackFnArgs.length] = arguments[i];
		}
	},
	
	onSubmittedCallbackFn: null,
	onSubmittedCallbackFnArgs: null,
	onSubmittedCallback: function(fn){
	
		this.onSubmittedCallbackFnArgs= [];
		this.onSubmittedCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onSubmittedCallbackFnArgs[this.onSubmittedCallbackFnArgs.length] = arguments[i];
		}
	},
	onUploadCallbackFn: null,
	onUploadCallbackFnArgs: null,
	onUploadCallback: function(fn){
	
		this.onUploadCallbackFnArgs= [];
		this.onUploadCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onUploadCallbackFnArgs[this.onUploadCallbackFnArgs.length] = arguments[i];
		}
	},
	onSubmittedCallbackFn: null,
	onSubmittedCallbackFnArgs: null,
	onSubmittedCallback: function(fn){
	
		this.onSubmittedCallbackFnArgs= [];
		this.onSubmittedCallbackFn = fn;
		if(arguments.length > 1){
			for (var i = 1; i < arguments.length; i++)
			this.onSubmittedCallbackFnArgs[this.onSubmittedCallbackFnArgs.length] = arguments[i];
		}
	},
	
	
	/** File upload mechanism */
	initFileupload: function(opt){
		 var ractive= this;
		 var Fineuploader= this.targetEL.find('#fine-uploader-gallery').fineUploader({
	            template: 'qq-template-gallery',
	            
	            // 1. Core options
	            debug: opt ? opt.debug : true,
	            autoUpload: opt ? opt.autoUpload :  false,
	            multiple: opt ? opt.multiple : false,
	            defaultName: opt ? opt.defaultName :"File1",
	            
	            request: {
	            	paramsInBody: opt ? opt.paramsInBody : false,
	            	requireSuccessJson: opt ? opt.requireSuccessJson : false,
	                endpoint: 'set/url/through/Fileuploader component'
	            },
	            
	            // 2. Image/Thumbnail details..
	            thumbnails: {
	                placeholders: {
	                    waitingPath: 'pmtpfService/css/Fileupload/loading.gif',
	                    notAvailablePath: 'pmtpfService/image/png/cert.png'
	                },
	            },
	            
	            // 3. Validation options
	            validation: {
	                allowedExtensions: opt ? opt.allowedExtensions :['jpeg', 'jpg', 'gif', 'png'],
	                /* itemLimit: opt ? opt.itemLimit : 100, */
	                stopOnFirstInvalidFile: opt ? opt.stopOnFirstInvalidFile : true
	            },
	            
	        }).on('complete', function (event, id, name, response) {
//	        	console.log("complete: ",event, id, name, response);
	        	// Capture fileId as soon as file is uploaded..
	        	if(response && response.data){
	        		ractive.uploadedFileId= response.data.inputFileId;
	        		ractive.DELETE_BTN.show();
	        	}
	        	
	        	if(ractive.onCompleteCallbackFn){
	        		// 3.1. Reset callback args to avoid data appending..
					var firstEl= ractive.onCompleteCallbackFnArgs[0];
					ractive.onCompleteCallbackFnArgs = [];
					ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = firstEl;
					
	        		ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = "POST";
            		ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = name;
        			ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = response;
        			
        			ractive.onCompleteCallbackFn.apply(null,ractive.onCompleteCallbackFnArgs);
            	}
	        	
	        }).on('error', function (event, id, name, response) {
	        	console.log("error: ",event, id, name, response);
	        	if(ractive.onErrorCallbackFn){
            		ractive.onErrorCallbackFnArgs[ractive.onErrorCallbackFnArgs.length] = name;
        			ractive.onErrorCallbackFnArgs[ractive.onErrorCallbackFnArgs.length] = response;
        			
        			ractive.onErrorCallbackFn.apply(null,ractive.onErrorCallbackFnArgs);
            	}
	        	
	        }).on('progress', function (event, id, name) {
//	        	console.log("progress: ",event, id, name);
            	if(ractive.onProgressCallbackFn){
		    		ractive.onProgressCallbackFnArgs[ractive.onProgressCallbackFnArgs.length] = event;
		    		ractive.onProgressCallbackFnArgs[ractive.onProgressCallbackFnArgs.length] = id;
		    		ractive.onProgressCallbackFnArgs[ractive.onProgressCallbackFnArgs.length] = name;
					
					ractive.onProgressCallbackFn.apply(null,ractive.onProgressCallbackFnArgs);
		    	}
	        }).on('totalProgress', function (event, totalUploadedBytes, totalBytes) {
//	        	console.log("onTotalProgress: ",event, totalUploadedBytes, totalBytes);
	        	ractive.targetEL.find(".qq-progress-bar-container-selector").show();
            	if(ractive.onTotalProgressCallbackFn){
		    		ractive.onTotalProgressCallbackFnArgs[ractive.onTotalProgressCallbackFnArgs.length] = totalUploadedBytes;
		    		ractive.onTotalProgressCallbackFnArgs[ractive.onTotalProgressCallbackFnArgs.length] = totalBytes;
					
					ractive.onTotalProgressCallbackFn.apply(null,ractive.onProgressCallbackFnArgs);
		    	}
            	
	        }).on('submit', function (event, id, name) {
//	        	console.log("submit: ",event, id, name);
	        	ractive.targetEL.find(".qq-total-progress-bar-container-selector").hide();
	        	if(ractive.onSubmitCallbackFn){
	        		ractive.onProgressCallbackFnArgs[ractive.onProgressCallbackFnArgs.length] = event;
		    		ractive.onProgressCallbackFnArgs[ractive.onProgressCallbackFnArgs.length] = id;
		    		ractive.onProgressCallbackFnArgs[ractive.onProgressCallbackFnArgs.length] = name;
            		
        			ractive.onSubmitCallbackFn.apply(null,ractive.onSubmitCallbackFnArgs);
            	}
	        	
	        }).on('submitted', function (event, id, name) {
//	        	console.log("submitted: ",event, id, name);
	        	ractive.targetEL.find(".qq-upload-cancel").hide();
	        	
            	if(ractive.onSubmittedCallbackFn){
	        		ractive.onSubmittedCallbackFnArgs[ractive.onSubmittedCallbackFnArgs.length] = id;
	        		ractive.onSubmittedCallbackFnArgs[ractive.onSubmittedCallbackFnArgs.length] = name;
	        		
	    			ractive.onSubmittedCallbackFn.apply(null,ractive.onSubmittedCallbackFnArgs);
	        	}
	        }).on('upload', function (event, id, name, reason) {
//	        	console.log("upload: ",event, id, name, reason);
            	if(ractive.onUploadCallbackFn){
	        		ractive.onUploadCallbackFnArgs[ractive.onUploadCallbackFnArgs.length] = id;
	        		ractive.onUploadCallbackFnArgs[ractive.onUploadCallbackFnArgs.length] = name;
	        		
	    			ractive.onUploadCallbackFn.apply(null,ractive.onUploadCallbackFnArgs);
	        	}
	        });
		 
		 // Making some default jquery settings to fineupload element.
		 ractive.DEFAULT_UPLOD_BTN= ractive.targetEL.find("input[name=qqfile]");
		 if(ractive.DEFAULT_UPLOD_BTN && ractive.DEFAULT_UPLOD_BTN.length == 1)
			 ractive.targetEL.find("div.qq-upload-button").hide();
			
		 return Fineuploader;
	},
	
	deleteFile: function(url, id, successFn, deleteFn){
		var ractive= this;
		var ConnectionModel = Backbone.Model.extend({
			urlRoot: url,
			idAttribute: "_id"
		});
		var conInstance= new ConnectionModel({
			_id: id
		});
		conInstance.destroy({
			headers: {
				AUTHUSER: "abhisekm"
			},
			success: function(model, response, options){
				if(successFn)
					successFn(model, response, options);
			}, 
			error: function(model, response, options){
				console.log("Delete Unsuccessful:", response);
				if(deleteFn)
					deleteFn(model, response, options);
			}
		});
	},
	
	on:{
		init:function(){
			this.set("fileuploadOptions", {});
			this.set("uploadUrl",null);
			this.set("deleteUrl",null);
			//TODO: this.set("downloadFileUrl", "Add file download url...");
		},
		
		render: function(){
			this.targetEL=$(this.target);
			this.DELETE_BTN= this.targetEL.find(".delete_button");
			this.DELETE_BTN.hide();
			this.uploader= this.initFileupload(this.get("fileuploadOptions"));
			console.log("File uploader is initialized..");
			this.DELETE_URL= _pmtpfServer+"/document/document.json/";
			
		},
		SELECT_File: function(ctx){
			this.DEFAULT_UPLOD_BTN.click();
		},
		
		UPLOAD_File: function(ctx){
			var url= this.get("uploadUrl") ? this.get("uploadUrl") : (_pmtpfServer + "/document/document.json");
			this.uploader.fineUploader('setEndpoint', this.get("uploadUrl"));
			this.uploader.fineUploader('uploadStoredFiles');
		},
		Cancel_File: function(ctx){
			if(this.targetEL)
				this.unrender();
			this.render(this.targetEL);
		},
		Delete_File: function(ctx){
			var URL= this.get("deleteUrl") ? this.get("deleteUrl"): this.DELETE_URL;
			var ractive= this;
			this.deleteFile(URL, this.uploadedFileId, 
				function(model, response, options){
					console.log("File DELETED:",model, response, options);
					// 1. Hide delete button.
					ractive.DELETE_BTN.hide();
					
					// 2. Re-render the fileUpload component..
					if(ractive.targetEL)
						ractive.unrender();
					ractive.render(ractive.targetEL);
					
					// 3. Trigger 'onCompleteCallbackFn' function..
					var fileObj= response.data;
					if(ractive.onCompleteCallbackFn){
						// 3.1. Reset callback args to avoid data appending..
						var firstEl= ractive.onCompleteCallbackFnArgs[0];
						ractive.onCompleteCallbackFnArgs = [];
						
						// 3.2. Now add new args and delegate the callback function..
						ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length]= firstEl;
						ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = "DELETE";
	            		ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = !(fileObj) ? fileObj : fileObj.inputFileName;
	        			ractive.onCompleteCallbackFnArgs[ractive.onCompleteCallbackFnArgs.length] = fileObj;
	        			ractive.onCompleteCallbackFn.apply(null,ractive.onCompleteCallbackFnArgs);
	            	}
			},  function(model, response, options){
				console.log("File NOT-DELETED:",model, response, options);
			});
		}
	}
});

Ractive.components.GlobalFileuploader= Fileuploader;