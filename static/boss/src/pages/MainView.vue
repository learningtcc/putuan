<template lang="html">

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
import Topbar from '../components/Topbar'
import Sidebar from '../components/Sidebar'
export default {
  data: function () {
    return {
    }
  },
  computed: {},
  ready: function () {},
  attached: function () {},
  methods: {},
  components: {
    Topbar,
    Sidebar
  }
}
</script>

<style lang="css">
</style>
