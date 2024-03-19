Page({

    /**
     * 页面的初始数据
     */
    data: {
        list: [],
        s: ["用户", '管理员'],
        show: false,
        user: {}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        var that = this;
        that.getAlls()
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    },
    change(event) {
        this.setData({
            'show': !this.data.show,
            'user': event.currentTarget.dataset.id
        })
    },
    delete(event) {
        console.log(event.currentTarget.dataset.id);
        var user = event.currentTarget.dataset.id;
        var that = this;
        wx.showModal({
            title: '是否删除',
            content: '',
            complete: (res) => {
                if (res.cancel) {}
                if (res.confirm) {
                    wx.request({
                        url: 'http://localhost:8082/user/DeleteUser',
                        data: {
                            id: user.id,
                            name: user.name
                        },
                        method: "POST",
                        success(res) {
                            // that.setData({

                            // })
                            if(res.data.status == 500){
                                return;
                            }
                            that.getAlls()
                        }
                    })
                }
            }
        })

    },
    update(event) {
        var user = event.currentTarget.dataset.id;
        var that = this;
        wx.showModal({
            title: '',
            content: '',
            editable: true,
            placeholderText: '更换命名',
            complete: (res) => {
                if (res.cancel) {

                }

                if (res.confirm) {
                    if (res.content == '') return;
                    user.name = res.content;
                    wx.request({
                        url: 'http://localhost:8082/user/UpdateUser',
                        data: {
                            id: user.id,
                            userName: user.name
                        },
                        method: "POST",
                        success(res) {
                            that.getAlls()
                        }
                    })
                }
            }
        })

    },
    getqx(event) {
        var id = event.currentTarget.dataset.id;
        console.log(id)

        var that = this;
        var user = that.data.user;
        wx.request({
            url: 'http://localhost:8082/user/UpdateUser',
            data: {
                id: user.id,
                type: that.data.s[id]
            },
            method: "POST",
            success(res) {
                that.setData({
                    'show': !that.data.show
                })
                that.getAlls()
            }
        })
    },
    getAlls() {
        var that = this;
        wx.request({
            url: 'http://localhost:8082/user/GetAllsUser',
            method: 'GET',
            success(res) {
                that.setData({
                    'list': res.data.data
                })
            }
        })
    }
})