<style>

.pagination {
    margin-bottom: 5px;
}

</style>

<template>

<div class="page-bar">
    <ul class="pagination pagination-split m-t-30">
        <li v-if="showFirst" class="footable-page"><a v-on:click="cur--">上一页</a></li>
        <li v-for="index in indexs"  class="footable-page" v-bind:class="{ 'active': cur == index}">
            <a v-on:click="btnClick(index)">{{ index }}</a>
        </li>
        <li v-if="showLast" class="footable-page"><a v-on:click="cur++">下一页</a></li>
    </ul>
</div>

</template>

<script>

module.exports = {
    props: ['pages'],
    data() {
        return {
            cur: 1
        }
    },
    computed: {
        indexs: function() {
            var left = 1
            var right = this.pages
            var ar = []
            if (this.pages >= 11) {
                if (this.cur > 5 && this.cur < this.pages - 4) {
                    left = this.cur - 5
                    right = this.cur + 4
                } else {
                    if (this.cur <= 5) {
                        left = 1
                        right = 10
                    } else {
                        right = this.pages
                        left = this.pages - 9
                    }
                }
            }
            while (left <= right) {
                ar.push(left)
                left++
            }
            return ar
        },
        showLast: function() {
            if (this.cur == this.pages) {
                return false
            }
            return true
        },
        showFirst: function() {
            if (this.cur == 1) {
                return false
            }
            return true
        }
    },
    watch: {
      cur: function(oldValue, newValue){
        this.$parent.pageNo = this.cur
        this.$parent.fetchData()
      }
    },
    methods: {
      btnClick: function(index){//页码点击事件
          if(index != this.cur){
              this.cur = index
          }
      }
    }
};

</script>
