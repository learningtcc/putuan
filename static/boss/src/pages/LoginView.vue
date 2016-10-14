<style lang="css" scoped>
</style>

<template lang="html">
<div class="account-pages">
</div>
<div class="clearfix"></div>
<div class="wrapper-page">
    <div class=" card-box">
        <div class="panel-heading">
            <h3 class="text-center"> 登录管理系统 </h3>
        </div>
        <div class="panel-body">
          <form v-form class="form-horizontal" id="loginForm" name="loginform" @submit.prevent="onSubmit" role="form">
            <div class="form-group ">
                <div class="col-xs-12">
                    <input class="form-control" type="text" name="username" v-model="username" v-form-ctrl required  placeholder="用户名">
                    <div class="errors" v-if="loginform.$submitted">
                        <p class="text-center" v-if="loginform.username.$error.required">请输入用户名</p>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12">
                    <input class="form-control" type="password" name="password" v-model="password" v-form-ctrl  required  placeholder="密码">
                    <div class="errors" v-if="loginform.$submitted">
                        <p class="text-center" v-if="loginform.password.$error.required">请输入密码</p>
                    </div>
                </div>
            </div>
            <div class="form-group text-center">
                <div class="col-xs-12">
                    <button class="btn btn-primary btn-block text-uppercase waves-effect waves-light"  type="submit">登 录</button>
                </div>
            </div>
          </form>
        </div>
    </div>

</div>
</template>

<script>
export default {
  data(){
    return {
      loginform:{},
      username:'',
      password:''
    }
  },
  methods:{
    onSubmit: function() {
        let self = this
        if(this.loginform.$valid==true){
          self.$http.post('tologin',{userName:self.username,password:self.password}).then((response) => {
            if(response.data.success){
              self.$route.go('/')
            }else{
              if(response.data.msg){
                alertify.error(response.data.msg)
              }else{
                alertify.error('登录失败')
              }
            }
          })
        }
    }
  }
}

</script>

<style lang="css">
  .errors p{
      color:red;
      font-size: 12px;
  }
</style>
