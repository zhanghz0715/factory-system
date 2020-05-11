var js = {
    /**
    * yyyy-MM-dd HH:mm
    * @param {*} timestamp 
    */
    formatDateTime: function (timestamp) {
        if (!timestamp) {
            return "";
        }
        var date = new Date(timestamp);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + '-';
        D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        // s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        // return Y + M + D + h + m + s;
        return Y + M + D + h + m;
    },
    /**
     * yyyy-MM-dd
     * @param {*} timestamp 
     */
    formatDateTime_date: function (timestamp) {
        if (!timestamp) {
            return "";
        }
        var date = new Date(timestamp);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + '-';
        D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
        // h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        // s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        // return Y + M + D + h + m + s;
        return Y + M + D;
    },
    /**
     * MM月dd日
     * @param {*} timestamp 
     */
    formatDateTime_YR: function (timestamp) {
        if (!timestamp) {
            return "";
        }
        var date = new Date(timestamp);
        //Y = date.getFullYear() + '-';
        M = (date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + '月';
        D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '日 ';
        // h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        // s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        // return Y + M + D + h + m + s;
        return M + D;
    },
    /**
     * yyyy
     * @param {*} timestamp 
     */
    formatDateTime_Year: function (timestamp) {
        if (!timestamp) {
            return "";
        }
        var date = new Date(timestamp);
        Y = date.getFullYear();
        //M = (date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + '-';
        //D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
        // h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
        // m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        // s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        // return Y + M + D + h + m + s;
        return Y;
    },
    getIntervalDays: function (timestamp) {
        if (!timestamp) {
            return "";
        }
        var timestamp_now = new Date().getTime(),
            timestamp_sub_number = timestamp_now - timestamp;

        if (timestamp_sub_number > 86400000)//大于一天
            return Math.floor(timestamp_sub_number / 86400000) + "天前";
        else if (timestamp_sub_number > 3600000)//大于1小时
            return Math.floor(timestamp_sub_number / 3600000) + "小时前";
        else if (timestamp_sub_number > 60000)//大于一分钟
            return Math.floor(timestamp_sub_number / 60000) + "分钟前";
        else //一分钟内
            return "刚刚";
    },
    ajax: function (type, url, data, callback, async) {
        showLodding();
        var result = null;
        jQuery.ajaxSetup({ cache: true });
        jQuery.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With', {
                    toString: function () {
                        return 'XMLHttpRequest';
                    }
                });
            },
            // async: async,
            type: type,
            url: url,
            data: data,
            dataType: 'json',
            success: function (data) {
                hideLodding();
                result = data;
                if (callback) {
                    callback.call(this, data);
                }
            },
            error: function (err, err1, err2) {
                if (err.status != '0') {
                    // console.log("ajax请求发生异常，请仔细检查请求url是否正确");
                    tipsBox("网络错误, 请稍后再试!");
                }
                hideLodding();
            }
        });
        return result;
    },
    getUrlRequestParam: function (name) {
        return params[name] ? params[name] : "";
    }
};

//提示信息
function tipsBox(val) {
    var oTipsBox = '<div class="tipsBox" style="padding: 10px; margin: 0 auto; left: 0; text-align: center; right: 0; top: 50%; position: fixed; color:#fff; background: #333; border-radius: 10px; font-size: 14px;width: 50%;display:block;z-index:50000">' + val + '</div>';
    $("body").append(oTipsBox);
    setTimeout(function () {
        $('.tipsBox').remove();
    }, 3000);
}

function showLodding() {
    var lodding_html = $(".loadingBox");
    if (typeof lodding_html[0] == "undefined") {
        lodding_html = '<div class="loadingBox"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span>正在加载</span></div>';
        $("body").append(lodding_html);
    }
    $(".loadingBox").show();
}
function hideLodding() {
    $(".loadingBox").hide();
}

/**
 * @description: 跳转到网易云信IM回合列表页并携带自动登录所需信息
 * @param {type} 
 * @return: 
 */
function goYunxin163Session() {
    if (!Customer.accid_163 || !Customer.tocken_163)
        return;

    localStorage.setItem('uid', Customer.accid_163);
    localStorage.setItem('sdktoken', Customer.tocken_163);
    location.href = '/webdemo/h5/index.html#/session';
}