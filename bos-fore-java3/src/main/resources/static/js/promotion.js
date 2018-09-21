$(function() {

	//发送ajax查询所有，将所有的数据，拼凑成一个规定格式，插入到隐藏div中 （hiddenpromotion）
	$.ajax({
		type: "get",
		//url: "data/promotion.json"
		url: "/forePromotion",
		async: false,//不是异步加载,让ajax先执行
		dataType: "json",
		success: function(data) {
			$.each(data, function(i, item) {
				var str = '<div class="result col-md-3">';
				str += '<div class="thumbnail">';
				str += '<img src=' + '"' + item.titleImg + '"' + '>';
				str += '<div class="caption"><p>' + item.title + '</p>';
				str += '<p class="text-right status"><span>' + (item.status==1?"进行中":"已结束") + '</span></p>';
				str += '<p class="text-right grey">' + item.startDate + '-' + item.endDate + '</p>';
				str += '<p class="text-right grey">' + item.activescope + '</p>';
				str += '</div></div></div>';
				$("#hiddenpromotion").append(str);
			});


        }
	});
	
	//不要放在ajax里面,页面还要用到的函数
	var page_every = 4; //每页显示个数

	//获得隐藏div存放列表个数
    var num_entries = $("#hiddenpromotion div.result").length;

    //绘制分页条，$(...).pageination(总条数 , 参数)

    $("#Pagination").pagination(num_entries, {
        num_edge_entries: 0, 			//边缘页数 （两侧显示的首尾分页的条目数）
        num_display_entries: 4, 		//主体页数 （页面上最大显示分页页面个数）
        callback: pageselectCallback,
        items_per_page: page_every, 	//每页显示个数
        prev_text: "前一页",
        next_text: "后一页",
        link_to:"javascript:void(0)",
    });

    //从隐藏div获得制定内容，并添加到需要显示的区域中
	function pageselectCallback(page_index,jq){
		//清空原来的内容
		$("#promotionresult").empty();
		//获得某一页开始索引值，第一页：0 ， 第二页：4 ，第三页：8
		var page_end = page_index * page_every;
		//只循环4次
		for (var i = 0;i<page_every;i++){
			var new_content = $('#hiddenpromotion div.result:eq('+page_end+')').clone();
			//将克隆的数据添加到展示区域
			$("#promotionresult").append(new_content);
			//不断累加，从当前页第1个到第4个
			page_end ++;
		}	
	}
});


/*$(function () {
	var pageCount=0;  // 分页总数量
	
	$.ajax({
		type: "get",
		url: "/customer/promotionListSize.html",
		async: false,//不是异步加载,让ajax先执行
		dataType: "json",
		success: function(data) {
			pageCount = data;
		}
	});
	
	
    // $("#pagination").pagination(pageCount); //简单初始化方法
    $("#promotionresult").pagination(pageCount,    // 分布总数量，必须参数
	   {
	     callback: PageCallback,  // PageCallback() 为翻页调用次函数。
         prev_text: "« 上一页",
         next_text: "下一页 »",
         items_per_page:10,
         num_edge_entries: 2,       // 两侧首尾分页条目数
         num_display_entries: 10,    // 连续分页主体部分分页条目数
         current_page: 0,   // 当前页索引
         link_to: "?page=__id__"  // 分页的js中会自动把"__id__"替换为当前的数。0
	});
});

function PageCallback(page_index,jq)
{
     $.ajax({   
        type: "get",  
        dataType: "text",  
        url: '/customer/queryPromotionByPage.html',      // 提交到一般处理程序请求数据
        data: "page=" + page_index,          // 提交两个参数：pageIndex(页面索引)，pageSize(显示条数)
        success: function(data) {
                   // 后台服务返回数据，重新加载数据
        	alert(data);
        }  
   }); 
}	*/
		

















