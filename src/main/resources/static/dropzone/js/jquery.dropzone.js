/**
 * @author 闫枫
 * @date 2018-11-06
 * 
 * dropzone上传组件的二次封装
 * 
 * 使用本文件需先引入jquery文件及dropzone.js文件，否则会有意想不到的事情发生
 * 
 * 用法：
 * 初始化方式：
 * $.hz.dropzone.init("myId", {configCode:"", indexNum: 0, classFieldName:"", idOwner: "", required : true});
 * 表单验证时，校验文件是否上传完成或是否已上传文件方式：
 * $.hz.dropzone.validateAttachment('myId')
 * 
 */
(function($) {
	var DROPZONE_PARAM = "dropzone_param";

	// dropzone上传组件，默认配置
	var dropzone = {
		param : {
			url: com_ue_global_path + "/sys/attachment/saveUploadFile", //必须填写
			method:"post",  //也可用put
			paramName:"filedata", //默认为file
			//maxFiles:10,//一次性上传的文件数量上限
			//maxFilesize: 20, //MB
			//acceptedFiles: ".jpg,.gif,.png", //上传的类型
			addRemoveLinks: true,//默认false。如果设为true，则会给文件添加一个删除链接。 
			parallelUploads: 3,
			dictMaxFilesExceeded: "您最多只能上传{{maxFiles}}个文件！",
			dictResponseError: '文件上传失败!',
			dictInvalidFileType: "你不能上传该类型文件,文件类型只能是{acceptedFiles}",
			dictFallbackMessage:"浏览器不受支持",
			dictDefaultMessage:"<span class='upfile_icon'></span><span style='color:#999999;'>点击或拖放文件到此处上传文件</span>", //修改上传提示文字
			dictFileTooBig:"文件过大上传文件最大支持{maxFilesize}MB",
			init : function() {
				this.on("success", function(file, request){
					file.serviceId = request
					$(file.previewElement).find(".dz-image img").attr("src", com_ue_global_path + "/sys/attachment/showTempPic?attachmentId=" + request);
					// console.log("success--file:" + JSON.stringify(file));
					// console.log("success--file.serviceId:" + file.serviceId);
					// console.log("success--request:" + request);
				});
				this.on("removedfile",function(file){
					//删除文件时触发的方法(删除新上传的文件用)
					if(file.serviceId){
						$.post(com_ue_global_path + "/sys/attachment/ajaxDeleteUploadFile", {
								attachmentId : file.serviceId
							}, 
							function(data) {
								//modals.error(data + "已删除");
							},
							"text"
						);
					}
					// console.log("removedfile--file:" + JSON.stringify(file));
					// console.log("removedfile--file.serviceId:" + file.serviceId);
				});
			}
		}
	}

	$.hz.dropzone = {
		/**
		 * 初始化dropzone上传组件（准备初始化所需各种参数）
		 * @param divId 上传组件初始化所用容器ID
		 * @param param 初始化必要参数（需手动传入的参数）
		 * 		param : { configCode:容许上传文件信息的配置记录code,
		 * 			indexNum: 上传组件顺序,
		 *  		classFieldName:附件绑定的类，及其字段（eg:com.yuntu.imp.modules.sys.entity.User,id）,
		 *  		idOwner: 外键值，可为空当不为空时，自动拉取之前已有附件信息, 
		 *  		required : 为true代表必传，否则非必传
		 *  	}
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		init : function(divId, param){
			var $obj = $("#"+divId);
			if($obj.size() == 0){
				return;
			}
			if(!param){
				param = {};
			}

			// 准备初始化参数
			var configCode = param.configCode || "default";
			var uploadParam = {};
			uploadParam.divId = $.uuid();
			uploadParam.required = param.required || false;
			uploadParam.classFieldName = param.classFieldName;
			uploadParam.indexNum = param.indexNum || 0;

			//依据configCode获取容许上传的文件信息
			$.ajax({
				url:com_ue_global_path + "/sys/attachmentConfig/ajaxGetConfig",
				type:"post",
				dataType:"json",
				async:false,
				data:{
					configCode:configCode
				},
				success:function(result){
					uploadParam.tip = result.allowTypeStr+ "&nbsp;" + result.limitFileExtentionStr+ "&nbsp;" + result.maxCountStr + "&nbsp;"+ ";" + result.maxFileSizeStr+ ";";
					uploadParam.filterArr = result.filterArr;
					uploadParam.maxFileSize = result.maxFileSize;
					uploadParam.maxFileCount = result.maxFileCount;
					uploadParam.limitFileExtention = result.limitFileExtention;
					uploadParam.configId = result.configId;
				}
			});

			//根据idOwner获取已保存的附件
			if(param.idOwner && param.idOwner != ""){
				$.ajax({
					url:com_ue_global_path + "/sys/attachment/ajaxGetAttachment",
					type:"post",
					dataType:"json",
					async:false,
					data:{
						idOwner:param.idOwner,
						configCode:configCode
					},
					success:function(result){
						uploadParam.existAttachments = result;
					}
				});
			}

			// console.log(uploadParam);
			$.hz.dropzone.loadUploadFlash($obj, uploadParam);
		},
		/**
		 * 删除已经提交过的附件
		 * @param objId 上传组件初始化所用容器ID
		 * @param objId 要删除的附件ID值
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		deleteExists : function(objId,attachmentId){
			var $obj = $("#"+objId);
			var divId = $obj.data(DROPZONE_PARAM).divId;
			//删除已有附件
			var oldIds = $("#" + divId + "DelIds").val();
			$("#" + divId + "DelIds").val(oldIds + "," + attachmentId);
			$("#" + attachmentId).remove();

			//设置oldFileArea区域
			var exist = $("#" + divId + "OldFileArea").find(".dz-preview").size();
			if(exist == 0){
				$("#" + divId + "OldFileArea").hide();
			}
		},
		/**
		 * 验证接口（用于表单提交前，验证附件是否已传、是否全部上传完成）
		 * @param objId 上传组件初始化所用容器ID
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		validateAttachment : function(objId){
			var $obj = $("#"+objId);
			var result = true;
			$obj.each(function(){
				var divId = $(this).data(DROPZONE_PARAM).divId;
				$("#" + divId + "HiddenValIpt").val("");
				var flag = $.hz.dropzone.checkRequired($(this));
				if(!flag){
					result = false;
					return false;//退出each循环;
				}
				var this_dropzone = $(this).data(DROPZONE_PARAM).dropzone;

				if(0 < this_dropzone.getQueuedFiles().length || 0 < this_dropzone.getUploadingFiles().length){
					if(window.parent!=window)
						layer.msg("文件正在上传，请上传完成后再提交。", {icon: 0, time: 1000});
					else
						layer.msg("文件正在上传，请上传完成后再提交。", {icon: 0, time: 1000});
					result = false;
					return false;//退出each循环;
				}

				var attIds = "";
				var new_files = this_dropzone.getAcceptedFiles();

				for(var j in new_files){
					var file = new_files[j];
					attIds += file.serviceId + ",";
				}
				$("#" + divId + "HiddenValIpt").val(attIds);
			});
			return result;
		},
		/**
		 * 检查附件是否已传（必传时，若未上传文件则返回false，并弹框提示，其他情况返回true）
		 * @param $obj 上传组件初始化所用容器jQuery对象
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		checkRequired : function ($obj){
			var requiredFlag = $obj.data(DROPZONE_PARAM).required;
			var canCommit = true;
			if(requiredFlag){
				if($.hz.dropzone.getExistFileCount($obj) == 0){
					//没有附件
					canCommit = false;
					if(window.parent!=window)
						layer.msg("请选择文件!", {icon: 0, time: 1000});
					else
						layer.msg("请选择文件!", {icon: 0, time: 1000});
				}
			}
			return canCommit;
		},
		/**
		 * 获取已上传文件总数（包含历史上传数量的和当前新上传还未绑定的附件总数）
		 * @param $obj 上传组件初始化所用容器jQuery对象
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		getExistFileCount : function ($obj){
			var divId = $obj.data(DROPZONE_PARAM).divId;
			var this_dropzone = $obj.data(DROPZONE_PARAM).dropzone;
//		console.log(this_dropzone.getAcceptedFiles());
//		console.log(this_dropzone.getOldFileSize());
			return this_dropzone.getAcceptedFiles().length + this_dropzone.getOldFileSize();
		},
		/**
		 * 初始化dropzone上传组件（拼接页面节点、加载上传组件）
		 * @param $obj 上传组件初始化所用容器jQuery对象
		 * @param param 初始化必要参数
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		loadUploadFlash : function ($obj, param){
			var html = $.nano($.hz.dropzone.getUploadUITemp(),{divId:param.divId,configId:param.configId,classFieldName:param.classFieldName,indexNum:param.indexNum,uploadTip:param.tip});
			$obj.append(html);
			$obj.attr("divId", param.divId);
			$obj.attr("attr_required", param.required);
			var id = $obj.attr("id");
			if(param.existAttachments && param.existAttachments.length>0){
				$("#" + param.divId + "OldFileArea").show();
				var htmlLi = "";
				$(param.existAttachments).each(function(){
					var uf = this,
					htmlLi_this = '<div class="dz-preview dz-processing dz-image-preview dz-success dz-complete" id="{file_id}"><div class="dz-image"><img data-dz-thumbnail="" alt="{file_name}" src="{file_src}"></div><div class="dz-details"><div class="dz-size"><span data-dz-size=""><strong>{file_size}</strong>MB</span></div><div class="dz-filename"><span data-dz-name="">{file_name}</span></div></div><div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress="" style="width: 100%;"></span></div><div class="dz-error-message"><span data-dz-errormessage=""></span></div><div class="dz-success-mark"><svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Check</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage"><path d="M23.5,31.8431458 L17.5852419,25.9283877 C16.0248253,24.3679711 13.4910294,24.366835 11.9289322,25.9289322 C10.3700136,27.4878508 10.3665912,30.0234455 11.9283877,31.5852419 L20.4147581,40.0716123 C20.5133999,40.1702541 20.6159315,40.2626649 20.7218615,40.3488435 C22.2835669,41.8725651 24.794234,41.8626202 26.3461564,40.3106978 L43.3106978,23.3461564 C44.8771021,21.7797521 44.8758057,19.2483887 43.3137085,17.6862915 C41.7547899,16.1273729 39.2176035,16.1255422 37.6538436,17.6893022 L23.5,31.8431458 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" stroke-opacity="0.198794158" stroke="#747474" fill-opacity="0.816519475" fill="#FFFFFF" sketch:type="MSShapeGroup"></path>      </g>    </svg>  </div>  <div class="dz-error-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Error</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <g id="Check-+-Oval-2" sketch:type="MSLayerGroup" stroke="#747474" stroke-opacity="0.198794158" fill="#FFFFFF" fill-opacity="0.816519475">          <path d="M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" sketch:type="MSShapeGroup"></path></g></g></svg></div><a class="dz-remove" href="javascript:;" onclick="$.hz.dropzone.deleteExists(\'{obj_id}\',\'{file_id}\');" data-dz-remove="">X</a></div>';
					htmlLi_this = $.nano(htmlLi_this,{file_id : uf.id, file_name : uf.name, file_size : uf.size, file_src : com_ue_global_path + "/sys/attachment/showPic?downloadToken=" + uf.id + "6210e537ab7425ada8f8cd2430ccb36f", obj_id : id})
					htmlLi += htmlLi_this;
				});
				$("#" + param.divId + "OldFileArea").append(htmlLi).show();
			}
			dropzone.param.maxFiles = param.maxFileCount;
			dropzone.param.maxFilesize = param.maxFileSize;
			dropzone.param.acceptedFiles = param.filterArr == "" ? null : param.filterArr.split(":")[0].replaceAll("*", "").replaceAll(";", ",");
			dropzone.param.dictInvalidFileType = dropzone.param.dictInvalidFileType.replace("{acceptedFiles}", dropzone.param.acceptedFiles);
			dropzone.param.dictFileTooBig = dropzone.param.dictFileTooBig.replace("{maxFilesize}", dropzone.param.maxFilesize);
			var myDropzone = new Dropzone(param.divId + "NewFileArea", dropzone.param);
			param.dropzone = myDropzone;
			$obj.data(DROPZONE_PARAM, param);
			//console.log($obj.data(DROPZONE_PARAM));
		},
		/**
		 * 获取初始化dropzone上传组件时，所需页面节点
		 * @author 闫枫
		 * @date 2018-11-06
		 */
		getUploadUITemp : function (){
			var html = "";
			html += "<input type='hidden' name='attachmentList[{indexNum}]' value='{classFieldName}'/>";
			html += "<input type='hidden' name='{classFieldName}' value='' id='{divId}HiddenValIpt'/>";
			html += "<input type='hidden' name='{classFieldName}DelIds' value='' id='{divId}DelIds'/>";
			html += "<input type='hidden' name='{classFieldName}ConfigId' value='{configId}' id='{divId}ConfigId'/>";
			html += "<div class='uploadStyle'>";
			html += "	<div class='uploadBtn' id='{divId}Btn'>";
			html += "		<span style='font-size:14px; color:#AAAAAA;height:30px;line-height:30px;' id='{divId}tip'><span class='fa fa-exclamation-circle'>&nbsp;{uploadTip}</span>";
			html += "	</div>";
			html += "	<div class='newFileArea dropzone needsclick dz-clickable' id='{divId}NewFileArea'></div>";
			html += "	<div class='oldFileArea dropzone dz-clickable' id='{divId}OldFileArea'>";
			html += "		<span class='ml10 pt5 pl10 clearfix'>已有附件</span>";
			html += "	</div>";
			html += "</div>";
			return html;
		}
	}
})(jQuery)