package com.bwei.ydhl.bean;

import java.util.List;

/**
 * Created by muhanxi on 17/4/19.
 */

public class GroupBean {


    /**
     * ret_code : 200
     * ret_msg : ok
     * list : [{"date":"5小时前","id":1,"pic":"http://p2.pstatp.com/list/190x124/1a6d000cc4ae22565fb3","title":" \n军改后军级阵容首次曝光，六位将军特别亮相","type":1},{"date":"2小时前","id":2,"pic":"http://p9.pstatp.com/list/190x124/1bf4000e8cea4899c0fa","title":"明星和岳父的合影曝光：吴奇隆刘强东真尴尬，王祖蓝真心不容易~","type":1},{"date":"2小时前","id":4,"pic":"http://p3.pstatp.com/list/190x124/1b7c00029c53d18dcb15","title":"细数电视剧《人民的名义》十大穿帮镜头，最后一个亮了","type":1},{"date":"3小时前","id":5,"pic":"http://p3.pstatp.com/list/190x124/1a6d000ccf3534b1a347","title":" \n中国女乒惨败，孔令辉向日本低头认输，却遭刘国梁一语打脸","type":1},{"date":"4小时前","id":6,"pic":"http://p3.pstatp.com/list/190x124/1c19000062675e53b27e|http://p3.pstatp.com/list/190x124/1aa4000a2b8a788b321f|http://p3.pstatp.com/list/190x124/1c19000062709c653bce","title":" \n北京的你再忙也要加一下这个投资微信！不然后悔！","type":2},{"date":"7小时前","id":8,"pic":"http://p3.pstatp.com/list/190x124/1bf50001f9fbb774fed9|http://p2.pstatp.com/list/190x124/1a6c001f60fb4971a495|http://p3.pstatp.com/list/190x124/1bf40001fa4bf7971585","title":"镜头中的中朝边境，两人背对背却在不同的国家","type":2},{"date":"1分钟前","id":11,"pic":"http://p3.pstatp.com/list/190x124/1bf5000efb7d5e2a8611","title":" \n郭富城结婚太抠门？婚礼账单超惊人，光是发记者红包就不少钱！","type":1},{"date":"5分钟前","id":13,"pic":"http://p9.pstatp.com/list/190x124/1aa4000918d4b343e8c5|http://p3.pstatp.com/list/190x124/19480014fbcc140e6640|http://p3.pstatp.com/list/190x124/1aa300090f598b8fbd14","title":"【莱克】魔洁大吸力无线吸尘器，多功能一键切换","type":2},{"date":"10分钟前","id":16,"pic":"http://p3.pstatp.com/list/190x124/1cbc0003cf55a3003467|http://p3.pstatp.com/list/190x124/1cbc0003cf53e713d90e|http://p3.pstatp.com/list/190x124/1bc30008ed9744c2c34b","title":"90后女孩辞职做代孕赚20万 供两个妹妹上大学","type":2},{"date":"2小时前","id":19,"pic":"http://p9.pstatp.com/list/190x124/ef7000c2947b313fe05","title":" \n为什么越来越多的人会选择关闭朋友圈？","type":1}]
     */

    private int ret_code;
    private String ret_msg;
    private List<ListBean> list;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * date : 5小时前
         * id : 1
         * pic : http://p2.pstatp.com/list/190x124/1a6d000cc4ae22565fb3
         * title :
         军改后军级阵容首次曝光，六位将军特别亮相
         * type : 1
         */

        private String date;
        private int id;
        private String pic;
        private String title;
        private int type;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
