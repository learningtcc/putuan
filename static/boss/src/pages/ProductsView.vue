<style lang="css">



</style>

<template lang="html">

<div class="row">
    <div class="col-sm-12">
        <div class="card-box">
            <div class="pad-btm form-inline">
                <div class="row">
                    <div class="col-sm-6 text-xs-center">
                        <div class="form-group">
                            <button  class="btn btn-default m-b-20" @click="addModal()"><i class="fa fa-plus m-r-5"></i> 添加产品</button>
                        </div>
                    </div>

                </div>
            </div>
            <table id="demo-foo-addrow" class="table table-striped m-b-0 table-hover toggle-circle footable-loaded footable default" data-page-size="7">
                <thead>
                    <tr>
                        <th v-for="(key, val) in columns">
                            {{val | capitalize}}
                        </th>
                        <th class="text-center">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="entry in products">
                        <td v-for="(key, val) in columns">
                            {{entry[key]}}
                        </td>
                        <td align="center">
                            <button type="button" class="btn btn-primary waves-effect waves-light" @click="editModal(entry)">修改</button>
                        </td>
                    </tr>
                </tbody>

            </table>

        </div>
    </div>
    <modal :show.sync="showModal" effect="fade">
        <div slot="modal-header" class="modal-header">
            <h4 class="modal-title">{{modaltitle}}</h4>
        </div>
        <div slot="modal-body" class="modal-body">
            <form v-form class="form-horizontal group-border-dashed"  name="myForm" @submit.prevent="postData" id="myForm" novalidate="">

                <div class="form-group">
                    <label class="col-sm-3 control-label">产品ID</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" v-form-ctrl required name="productId" v-model="formdata['productId']"  >
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.productId.$error.required">产品ID不能为空</li>
                        </ul>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">产品名称</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" v-form-ctrl required name="name" v-model="formdata['name']"  >
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.name.$error.required">产品名称不能为空</li>
                        </ul>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">接入方案</label>
                    <div class="col-sm-9">
                      <div class="radio radio-success">
                          <input type="radio" name="accessType" id="radio4" checked value="1" v-form-ctrl required v-model="formdata['accessType']">
                          <label for="radio4">
                              微信硬件云标准接入方案
                          </label>
                      </div>
                      <div class="radio radio-success">
                          <input type="radio" name="accessType" id="radio5" value="2" v-form-ctrl required v-model="formdata['accessType']">
                          <label for="radio5">
                              平台基础接入方案
                          </label>
                      </div>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.accessType.$error.required">接入方案要选择</li>
                      </ul>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">产品描述</label>
                    <div class="col-sm-9">
                        <textarea v-form-ctrl required class="form-control"  name="detail" v-model="formdata['detail']"></textarea>
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.detail.$error.required">产品描述不能为空</li>
                        </ul>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">产品品类</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" v-form-ctrl required name="type" v-model="formdata['type']" >
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.type.$error.required">产品品类不能为空</li>
                        </ul>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">连接类型</label>
                    <div class="col-sm-9" >
                        <div class="checkbox checkbox-success" v-for="connect in connectList">
                            <input id="connect_{{$index}}" type="checkbox" value="{{$index+1}}" name="connectType" v-form-ctrl required  v-model="connectType">
                            <label for="connect_{{$index}}">{{connect}}</label>
                        </div>

                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.connectType.$error.required">连接类型要选择至少一项</li>
                        </ul>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">产品配置方式</label>
                    <div class="col-sm-9">
                        <div class="checkbox checkbox-success">
                            <input id="checkbox7" value="1" type="checkbox" checked name="configureType" v-model="configureType" >
                            <label for="checkbox7">微信配网</label>
                        </div>
                        <div class="checkbox checkbox-success">
                            <input id="checkbox8" value="2" type="checkbox" name="configureType" v-model="configureType">
                            <label for="checkbox8">蓝牙发现</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer text-right">
                    <button type="submit" class="btn btn-default" >保存</button>
                    <button type="button" class="btn btn-success" @click='showModal = false'>取消</button>
                </div>
            </form>
        </div>
        <div slot="modal-footer"></div>
    </modal>
</div>

</template>

<script>
import pagination from '../components/Pagination'
import modal from '../components/Modal'
export default {
    data: function() {
        return {
            showModal: false,
            myForm:{},
            products:[],
            modaltitle: '添加产品',
            keyword: null,
            editing: false,
            editingId:'',
            connectType:[],
            connectList:[
              'Wi-Fi','蓝牙','蜂窝网络','有线连接','网关子设备','其他'
            ],
            configureType:[],
            columns: { //table 表头
                id: 'ID',
                name: '产品名称',
                type: '产品类型',
                detail: '产品描述'
            },
            formdata: {}
        }
    },
    ready: function() {
        this.fetchData()
    },
    methods: {
        connectTypeArray(){
          return _.split(this.formdata['connectType'],'|')
        },
        configureTypeArray(){
          return _.split(this.formdata['configureType'],'|')
        },
        getParams(){
          return {
            productId: this.formdata['productId']?this.formdata['productId']:'',
            name: this.formdata['name']?this.formdata['name']:'',
            accessType: this.formdata['accessType']?this.formdata['accessType']:'',
            detail: this.formdata['detail']?this.formdata['detail']:'',
            type: this.formdata['type']?this.formdata['type']:'',
            connectType: this.connectType?this.connectType.join('|'):'',
            configureType: this.configureType?this.configureType.join('|'):''
          }
        },
        addModal() {
            this.showModal = true
            this.formdata = {}
            this.editing = false
            this.modaltitle = '添加产品'

            this.connectType = []
            this.configureType = []
        },
        editModal(entry){
          this.showModal = true
          this.formdata = entry
          this.editing = true
          this.modaltitle = '修改产品'

          this.connectType = this.connectTypeArray()
          this.configureType = this.configureTypeArray()
        },
        fetchData() {
            let self = this
            this.$http.get('boss/product/list').then((response) => {
                this.products = response.data.data.result
            })
        },
        postData(){
          let params = this.getParams()
          let self = this
          if(this.myForm.$valid){
            if(self.editing){
              self.$http.post('boss/product/update?id='+self.formdata['id'], params).then((response) => {
                  if(response.data.success){
                    self.fetchData()
                    self.showModal = false
                  }else{
                    alert('提交失败')
                  }
              })
            }else{
              this.$http.post('boss/product/add', params).then((response) => {
                if(response.data.success){
                  self.fetchData()
                  self.showModal = false
                }else{
                  alert('提交失败')
                }
              })
            }
          }
        }

    },
    components: {
        modal,
        pagination
    }
}

</script>
