$(function (){
    $('#keyword').on('keyup',function () {
        var keyword = $(this).val();
        // console.log(keyword);
        if(keyword !== ''&& keyword!==null && keyword.length !== 0){
            $.ajax({
                type   : 'GET',
                url    : 'http://localhost:8080/mypetstore_war_exploded/productAuto?keyword='+keyword,
                success: function(data){
                    console.log(data);
                    var productListHTML = '';
                    for(var i = 0; i < data.length; i++){
                        productListHTML += '<li class=\"productAutoItem\" data-productId="';
                        productListHTML += data[i].productId;
                        productListHTML +='">';
                        productListHTML += data[i].categoryId;
                        productListHTML += ': ';
                        productListHTML += data[i].name;
                        productListHTML +='</li>';
                    }
                    $('#productAutoList').html(productListHTML)
                    $('#productAutoComplete').show();
                },
                error  :function (errorMsg){
                    console.log(errorMsg);
                }
            });
        }else{
            $('#productAutoComplete').hide();

        }
    });
    // $('.productAutoItem').on('click', function () {
    //
    // })

    // 动态绑定
    $(document).on('click', '.productAutoItem', function () {
        var productId = $(this).data('productid');
        $('#productAutoComplete').hide();
        $('#keyword').val('');
        // console.log(productId);
        window.location.href = 'http://localhost:8080/mypetstore_war_exploded/productForm?productId=' + productId;
    });
    
    // 鼠标移开
    // 区分mouseleave与mouseout
    $('#productAutoComplete').on('mouseleave', function () {
        $(this).hide();
        $('#keyword').val('');
    })
});