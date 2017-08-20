//iframe自适应
$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

var vm = new Vue({
    el: '#app',
    data: {
        user: {},
        menuList: {},
        main: "main.html",
        password: '',
        newPassword: '',
        navTitle: '欢迎页'
    },
    methods: {
        getMenuList: function () {
            $.getJSON(baseURL + 'menu/user',function (result) {
                vm.menuList=result.menuList;
                window.permissions=result.permissions;
            });
        },
        getUser: function() {
            $.getJSON(baseURL+ 'sys/user/info',function(result){
                vm.user=result.user;
            })
        },
        logout:function() {
            localStorage.removeItem('token');
            location.href = baseURL + 'login.html';
        }
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    }
});