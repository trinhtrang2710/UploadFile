//Viết các hàm javascript sử dụng chung
CommonFunction = {

	 alpha:function(e) {
	    var k;
	    document.all ? k = e.keyCode : k = e.which;
	    return (k!=34 && k!=39 && k!=47 && k!=92 && k!=124);
	},
        replaceSpecialChar:function(str){
		str = CommonFunction.replaceAllSC("\"","",str);
		str = CommonFunction.replaceAllSC("\'","",str);
		str = CommonFunction.replaceAllSC("\\","",str);
		str = CommonFunction.replaceAllSC("\/","",str);
		return str;
	},

	replaceAllSC:function(find, replace, str){
		while( str.indexOf(find) > -1)
	      {
	        str = str.replace(find, replace);
	      }
	      return str;
	},

	formatCurrency : function(ctrl, event) {
	    //Check if arrow keys are pressed - we want to allow navigation around textbox using arrow keys
	    if (event != null)
	    {
	    	if(event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40)
	    		return false;
	    }
	    var val = ctrl.value.replace(/,/g, "");
	    val = '' + (val != '' ? parseInt(val) : '');
	    ctrl.value = "";
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.value = x1 + x2;
	},
	
	formatCurrencyHtml : function(ctrl) {
	    var val = ctrl.innerHTML;
	    val = val.replace(/,/g, "");
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.innerHTML = x1 + x2;
	},

	checkNumeric: function(event) {
		var x = event.which || event.keyCode;
	    return x == 8 || x == 46 || (x >= 48 && x <= 57);
	},
	
	currencyToNum:function(ctrl){
		if(ctrl.value!=""){
			ctrl.value=CommonFunction.replaceAll(",","",ctrl.value);
		}
	},
	replaceAll:function(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
	},
	submitForm: function (){
		$(".currency").each(function(){
			CommonFunction.currencyToNum(this);
		});
		$('#btnSave').click();
		$(".currency").each(function(){
			CommonFunction.formatCurrency(this, null);
		});
	},
        
        selectedItems : function(item) {
		var value = item.value;
		if (value != "0") {
			var ids = $("#hdfIds").val();
			if (item.checked) {
				if (ids == ";") {
					$("#hdfIds").val(ids + value + ";");
				} else {
					if (ids.indexOf(";" + value + ";") < 0)
						$("#hdfIds").val(ids + value + ";");
				}
			} else {
				var re = new RegExp(';' + value + ';', 'g');
				$("#hdfIds").val(ids.replace(re, ";"));
			}
		} else {
			var ids = ";";
			if (item.checked) {
				$("table.table > tbody > tr").each(function() {
					var id = $('td:eq(0) input', this).val();
					if(id != null){
						if (ids == ";")
							ids = ids + id + ";";
						else if (ids.indexOf(";" + id + ";") < 0)
							ids = ids + id + ";";
					}
				});
				$("#hdfIds").val(ids);
			} else {
				$("#hdfIds").val(";");
			}
		}
	},
        deleteItems : function(id) {
		if ($("#hdfIds").val() != ";")
			$("#frmDelete").submit();
		else
			CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle,
					CommonMessenger.MustChoiceBeforeDelete,
					CommonMessenger.errorType);
	},
        // Hiển thị popup thông báo
	// title: tiêu đề thông báo, msg: nội dung thông báo
	// type: error, warning...
	showPopUpMsg : function(title, msg, type) {
		$('#confirm-delete').modal('hide');
		$("#popup-message #myModalLabel").empty();
		if (type.indexOf("error") > -1) {
			$("#popup-message #myModalLabel").append("<i class='fa fa-warning'></i>");
			$("#popup-message .modal-header").addClass("alert-danger");
		} else {
			$("#popup-message .modal-header").removeClass("alert-danger");
		}
		$("#popup-message #myModalLabel").append(title);
		$("#popup-message .modal-body").text(msg);
		$("#popup-message").modal('show');
	},
        goToPageNum : function(p, curPage, maxPage, e) {
		if($("#filter") != null && $("#filter").val() != null && $("#filter").val() != ""){
			var obj = JSON.parse($("#filter").val());
			jQuery.each(obj, function(i, val) {
				console.log(val);
				 $("#" + i).val(val);
                                 
			});
		}
		else
			$("#frmSearch")[0].reset();
		if (e != null) {
			if (e.keyCode == 13 && p > 0 && p != parseInt(curPage)
					&& p <= parseInt(maxPage)) {
				$("#page").val(p);
                                $("#typeAction").val("1");
				$("#frmSearch").submit();
			}
		} else {
			$("#page").val(p);
                        $("#typeAction").val("1");
			$("#frmSearch").submit();
		}
	},

}

var CommonMessenger = {
	errorType : 'error',
	ErrorTitle : ' Thông báo lỗi',
	Info : 'Thông báo',
	Warning : 'Cảnh báo',
	Success : 'Cập nhật thành công!',
	Delete : 'Bạn có chắc chắn xóa dữ liệu này không?',
	Error : 'Đã có lỗi trong quá trình xử lý!',
	SureToDelete : 'Bạn có chắc chắn muốn xóa dữ liệu này không?',
	NothingToDownload : 'Dữ liệu này không có tập tin đính kèm!',
	ConfirmWrongPassword : 'Nhắc lại mật khẩu mới chưa đúng!',
	OnlyChoiceToView : 'Bạn chỉ được chọn một giá trị để xem chi tiết!',
	OnlyChoiceToEdit : 'Bạn chỉ được chọn một giá trị để thực hiện thay đổi nội dung!',
	OnlyChoiceToAction : 'Bạn chỉ được chọn một giá trị để {0}!',
	MustFillAll : 'Bạn phải nhập đầy đủ thông tin!',
	MustChoiceBeforeAction : 'Bạn phải chọn dữ liệu cần {0} trước!',
	MustChoiceBeforeAction2 : 'Bạn phải chọn {0} cần {1} trước!',
	MustChoiceBeforeView : 'Bạn phải chọn dữ liệu cần xem trước!',
	MustChoiceBeforePhanCong : 'Bạn phải chọn hồ sơ để phân công rà soát!',
	MustChoiceBeforeEdit : 'Bạn phải chọn dữ liệu cần sửa trước!',
	MustChoiceBeforeTrinh : 'Bạn phải chọn hồ sơ trước khi trình lãnh đạo!',
	MustChoiceBeforeDelete : 'Bạn phải chọn dữ liệu cần xóa trước!',
	MustChoiceBeforePrint : 'Bạn phải chọn dữ liệu cần in trước!',
	MustChoiceBeforeSendMail : 'Bạn phải chọn dữ liệu cần phải gửi email trước!',
	MustChoiceBeforeDownload : 'Bạn phải chọn tài liệu đính kèm cần tải xuống trước!',
	MustChoiceDeXuatBeforeAttach : 'Bạn phải chọn đề xuất cần đính kèm tài liệu trước!',
	MustChoiceDeTaiDuAnBeforeAttach : 'Bạn phải chọn đề tài - dự án cần đính kèm tài liệu trước!',
	MustChoiceBeforeAddAttach : 'Bạn phải chọn {0} cần đính kèm tài liệu trước!',
	MustChoiceBeforeEditAttach : 'Bạn phải chọn tài liệu {0} cần sửa thông tin trước!',
	MustChoiceBeforeDeleteAttach : 'Bạn phải chọn tài liệu {0} cần xóa trước!',
	MustChoiceBeforeUpdate : 'Bạn phải chọn dữ liệu cần cập nhật thông tin trước!',
	MustChoiceBeforeSecurity : 'Bạn phải chọn ứng dụng cần phân quyền trước!',
	MustChoiceBeforeAddUserOrGroup : 'Bạn phải chọn nhóm quyền cần thêm nhóm/người dùng trước!',
	MustChoiceBeforeDanhGia : 'Bạn phải chọn tài liệu {0} cần đánh giá trước!',
	OnlyChoiceOneRecode : 'Bạn chỉ được chọn một bản ghi ',

	successNotice : function(title, msg) {
		window.Ext.net.Notification.show({
			// iconCls: 'icon-information',
			// icon: windown.Ext.Net.Icon.Information,
			icon : window.Ext.Msg.INFO,
			hideDelay : 3000,
			autoHide : true,
			closeVisible : true,
			html : msg,
			title : CommonMessenger.Info,
			alignToCfg : {
				offset : [ 0, 20 ],
				position : 'tr-tr'
			},
			showFx : {
				args : [ 't', {} ],
				fxName : 'slideIn'
			},
			hideFx : {
				args : [ 't', {} ],
				fxName : 'ghost'
			}
		});
	},
	successHandler : function(form, action) {
		if (action.result && action.result.msg) {
		}
		CommonFunction.CloseWindow();
		// successNotice('Thông báo', 'Lưu dữ liệu thành công');
	},

	failureHandler : function(form, action) {
		var msg = '';

		if (action.failureType == 'client'
				|| (action.result && action.result.errors && action.result.errors.length > 0)) {
			msg = "Không nhập đủ thông tin";
		} else if (action.result && action.result.msg) {
			msg = action.result.msg;
		} else if (action.response) {
			msg = action.response.responseText;
		}
		CommonMessenger.showErrorMessage('', msg);
	},

	showInfoMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.Info,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.INFO
		});
	},

	showErrorMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.ErrorTitle,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.ERROR
		});
	},

	showWarningMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.Warning,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.WARNING
		});
	},

	showConfirmMessage : function(title, msg, func) {
		Ext.Msg.show({
			title : CommonMessenger.Info,
			msg : msg,
			buttons : Ext.MessageBox.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : func,
			width : 350
		});
	},

	showWindowMessage : function(title, msg, func) {
		window.Ext.Msg.show({
			title : title,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.INFO
		});
	},
};


$(document).ready(function(){
	$(".currency").each(function(){
		CommonFunction.formatCurrency(this, null);
	});
	$(".currencyHtml").each(function(){
		CommonFunction.formatCurrencyHtml(this);
	});
	$('.currency')
    .keyup(function(event){
    	CommonFunction.formatCurrency(this, event);
    })
    .keypress(function(event){
    	return CommonFunction.checkNumeric(event);
    })
    /*.blur(function(){
    	CommonFunction.checkRound(this);
    })*/
    .change(function(event){
    	CommonFunction.formatCurrency(this, event);
    });
});
