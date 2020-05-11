/**
 * @description: 职位申请
 * @param {type} 
 * @return: 
 */
function askJob(jobId, this_) {
    js.ajax("post", front_path + "/jobAsk/save", getSubmitObj(jobId), function (data) {
        if (data.success) {
            tipsBox("投递成功！");
            this_.addClass("delivered").attr("isAsk", "yes").html("已投递");
        } else
            tipsBox(data.msg);
    }, false);
}

/**
 * @description: 组装申请职位接口提交的数据
 * @param {type} 
 * @return: 
 */
function getSubmitObj(jobId) {
    var formData = new Object();
    formData.jobId = jobId;
    formData.hunterCustomerId = Customer.id;
    return formData;
}

/**
 * 立即投递按钮的监听
 */
$(document).on("click", "a[job_ask][isAsk=no]", function () {

    if ("1" == Customer.type) {
        return;
    }

    askJob($(this).attr("job_id"), $(this));
});

/**
 * 跳转职位详情的监听
 */
/*
$(document).on("click", "div[job_detail]", function () {
    location.href = "/job_details/jobDetails.html?jobId=" + $(this).attr("job_id");
});*/
