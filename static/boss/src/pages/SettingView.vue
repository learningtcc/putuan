
<template lang="html">

<div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:;" data-toggle="tab" aria-expanded="true"><span class="visible-xs"><i class="fa"></i></span> <span class="hidden-xs">AccessToken</span></a></li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane active" id="accesstoken">
        <div class="text-center">
          <a class="btn btn-default waves-effect waves-light" @click="updateAccessToken()">更新AccessToken</a>
          <br>
          <br>
          <h2>{{accessToken}}</h2>
        </div>
      </div>
    </div>
</div>

</template>

<script>

export default {
    data: function() {
        return {
          accessToken:''
        }
    },
    computed: {},
    ready: function() {
        this.fetchData()
    },
    attached: function() {},
    methods: {
        fetchData() {
            let self = this
            self.$http.get('boss/setting/get').then((response) => {
                self.accessToken = response.data.data.accessToken
            })
        },
        updateAccessToken() {
            let self = this
            self.$http.post('boss/setting/update', {}).then((response) => {
                if(response.data.success){
                    self.fetchData()
                } else{
                    alert('更新失败')
                }
            })
        }
    },
    components: {}
}

</script>
