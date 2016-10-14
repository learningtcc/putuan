<template>
  <div class="fixed-left">
      <!-- Begin page -->
      <div id="wrapper">
        <Topbar></Topbar>
        <Sidebar></Sidebar>
        <div class="content-page">
            <!-- Start content -->
            <div class="content">
                <div class="container">
                  <router-view></router-view>
                </div>
            </div>
        </div>
      </div>
  </div>
</template>

<script>
import store from './vuex/store'
import _ from 'lodash'
import alertify from 'alertifyjs'
import validate from 'jquery-validation'
import Topbar from './components/Topbar'
import Sidebar from './components/Sidebar'

export default {
  store:store,
  components:{
    Topbar,
    Sidebar
  }
}
</script>


<style lang="less" rel="stylesheet/less">
  @import './assets/css/bootstrap.min.css';
  @import './assets/less/core.less';
  @import '../node_modules/alertifyjs/build/css/alertify.min.css';
  @import './assets/less/components.less';
  @import './assets/less/icons.less';
  @import './assets/less/pages.less';
  @import './assets/less/responsive.less';
  .alertify-notifier .ajs-message.ajs-error{
    color:#fff
  }
  
</style>
