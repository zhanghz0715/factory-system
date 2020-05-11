$(function () {
    getEnterpriseData();
    getJobListData();
});
var point_job = null;

var Page = { size: 200, total: 0, current: 1 };

/**
 * @description: 拉取企业信息
 * @param {type} 
 * @return: 
 */
function getEnterpriseData() {
    js.ajax("post", front_path + "/customer/get/" + js.getUrlRequestParam("customerId"), null, function (data) {
        if (data.success && data.obj)
            initEnterpriseData(data.obj);
    }, false);
}

/**
 * @description: 初始化页面企业基本信息
 * @param {type} 
 * @return: 
 */
function initEnterpriseData(Enterprise) {
    if (!Enterprise)
        return;

    img();
    img_2(Enterprise);

    $("#companyName_h2").html(Enterprise.companyName);
    $("#companyNature_span").html(Enterprise.companyNature);

    var companyUnit_span = "";
    if (Enterprise.companyUnitStart && Enterprise.companyUnitEnd)
        companyUnit_span = Enterprise.companyUnitStart + "—" + Enterprise.companyUnitEnd + "人";
    else if (Enterprise.companyUnitStart)
        companyUnit_span = Enterprise.companyUnitStart + "人以上";
    else if (Enterprise.companyUnitEnd)
        companyUnit_span = Enterprise.companyUnitEnd + "人以下";
    $("#companyUnit_span").html(companyUnit_span);

    $("#companyAddress_p").html("地址：" + (Enterprise.companyAddress ? Enterprise.companyAddress : "未知地址~")).attr("title", Enterprise.companyAddress);
    // $("#head_portrait").attr("src", Enterprise.head_portrait);
    $("#companyContent_dd p").html(Enterprise.companyContent);
    $("#companyName_span").html(Enterprise.companyName);
    $("#companyCreateTime_span").html(js.formatDateTime_date(Enterprise.companyCreateTime));
    $("#companyMoney_span").html(Enterprise.companyMoney);
    $("#companyMoney_span").html(Enterprise.companyMoney);

    if (Enterprise.companyLatlng) {
        var point_array = Enterprise.companyLatlng.split(",");
        var point = new BMap.Point(point_array[1], point_array[0]);
        map.setCenter(point);
        var marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);               // 将标注添加到地图中
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

           initMapMsg(Job);
    }
}

/**
 * @description: 拉取职位分页数据
 * @param {type} 
 * @return: 
 */
function getJobListData() {
    js.ajax("post", front_path + "/job/list", getSelectObj(), function (data) {
        if (data.success) {
            if (data.obj) {
                Page = data.obj;
                initJobListData(data.obj.records);
            }
        } else
            tipsBox(data.msg);
    }, false);
}

/**
 * @description: 初始化查询参数
 * @param {type} 
 * @return: 
 */
function getSelectObj() {
    var formData = new Object();
    formData.size = Page.size;
    formData.current = Page.current;
    formData.customerId = js.getUrlRequestParam("customerId");
    return formData;
}

/**
 * @description: 初始化页面职位数据
 * @param {type} 
 * @return: 
 */
function initJobListData(JobList) {
    if (!JobList)
        return;

    JobList.forEach(Job => {
        var job_one_html = '<li>\
                                <div class="leftDesc" style="cursor: pointer;" job_detail job_id="' + Job.id + '">\
                                    <h2>' + Job.classifyTwo.title + '</h2>\
                                    <p class="desc">\
                                        <span>' + (Job.workAddress ? Job.workAddress.substring(0, 8) : "未知地址~") + '</span>\
                                        <span>' + (Job.welfare || "") + '</span>\
                                        <span>' + (Job.education || "") + '</span>\
                                    </p>\
                                </div>\
                                <div class="right">\
                                    <p>' + Job.wageStart + (Job.wageEnd ? '-' + Job.wageEnd : "以上") + '</p>\
                                    <span>' + js.formatDateTime_YR(Job.createTime) + '</span>\
                                </div>\
                                <div class="clearFloat"></div>\
                            </li>';

        $("#jobList_ul").append(job_one_html);
        initMapMsg(Job);
    });
}

function initMapMsg(Job) {
    if (!Job || !Job.companyLatlng) {
        $("#distance_em").html("未知~");
        $("#time_em").html("未知~");
        return;
    }

    var point_array = Job.companyLatlng.split(",");
    point_job = new BMap.Point(point_array[1], point_array[0]);
    map.setCenter(point_job);
    var marker = new BMap.Marker(point_job);  // 创建标注
    map.addOverlay(marker);               // 将标注添加到地图中
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

    transit_search();
}

function transit_search() {
    if (!get_point_now_successs) {
        $("#distance_em").html("未知~");
        $("#time_em").html("未知~");
        return;
    }

    if (null == point_now) {
        setTimeout(() => {
            transit_search();
        }, 500);
        return;
    }

    transit.search(point_now, point_job);
}

function img() {
    var refresh = {
        "type": '1',
        "customerId": js.getUrlRequestParam("customerId")
    }
    js.ajax("post", front_path + "/img/getPicture", refresh, function (data) {
        if (data.success) {
            if (data.obj) {
                if (data.obj.headPortrait) {
                    $("img[id='head_portrait']").attr("src", base_path+data.obj.headPortrait);
                } else {
                    $("img[id='head_portrait']").attr("src", "/web/static/share/img/qq.png");
                }
            } else {
                $("img[id='head_portrait']").attr("src", "/web/static/share/img/qq.png");
            }
        }
    }, false);
}

function img_2(Customer_) {
    if (!Customer_ && !Customer_.id)
        return;

    var refresh = {
        "type": '2',
        "customerId": Customer_.id
    }

    js.ajax("post", front_path + "/img/getPicture", refresh, function (data) {
        console.log(data);
        if (data.success) {
            if (data.obj) {
                if (data.obj.imgs) {
                    let imgs_array = data.obj.imgs.split(",");
                    for (const i in imgs_array) {
                        if (imgs_array.hasOwnProperty(i)) {
                            const imgs_one =base_path+ imgs_array[i];
                            $("#companyContent_p").append('<img src="' + imgs_one + '" alt="">');
                        }
                    }
                }
            }
        }
    }, false);
}


   

$("#allmap").click(function () {
    console.log(point_now.lat)
    if (null == point_now) {
        return;
    }
    location.href = "http://api.map.baidu.com/direction?origin=latlng:" + point_now.lat + "," + point_now.lng + "|name:我的位置&destination=" + point_job.lat + "," + point_job.lng + "&mode=driving&region=中国&output=html&src=webapp.demo.location"
});