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
                            <button id="demo-btn-addrow" class="btn btn-default m-b-20" @click="addModal()"><i class="fa fa-plus m-r-5"></i> 添加设备</button>
                        </div>
                    </div>
                    <div class="col-sm-6 text-xs-center text-right">
                        <div class="form-group">
                            <input id="demo-input-search2" type="text" placeholder="搜索" v-model="keyword" v-on:keyup="fetchData" class="form-control input-sm" autocomplete="off">
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
                    <tr v-for="entry in devices">
                        <td v-for="(key, val) in columns">
                            <span v-if="key=='createTime'">
                              {{convertDate(entry[key])}}
                            </span>
                            <span v-else>
                              {{entry[key]}}
                            </span>

                        </td>
                        <td class="text-center">
                            <button type="button" class="btn btn-primary waves-effect waves-light" @click="editModal(entry)">修改</button>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6" class="footable-visible">
                            <div class="text-center">
                                  <pagination :pages="pages"></pagination>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>

        </div>
    </div>
    <modal :show.sync="showModal" effect="fade" large>
        <div slot="modal-header" class="modal-header">
            <h4 class="modal-title">{{modaltitle}}</h4>
        </div>
        <div slot="modal-body" class="modal-body">
            <form v-form class="form-horizontal group-border-dashed"  name="myForm" @submit.prevent="postData" id="myForm"  novalidate="">
                <div class="form-group"  v-if="!editing">
                    <label class="col-sm-3 control-label">产品</label>
                    <div class="col-sm-9">
                      <select name="productId" class="form-control"  v-form-ctrl required v-model="formdata['productId']">
                          <option v-for="product in products" value="{{product.id}}" >{{product.name}}</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.productId.$error.required">产品不能为空</li>
                      </ul>
                    </div>
                </div>
                <div class="form-group" v-else>
                    <input type="text" name="productId" style="display:none" v-model="formdata['productId']">
                    <input type="text" name="id" style="display:none" v-model="formdata['id']">
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">mac</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="mac"   v-form-ctrl required v-model="formdata['mac']" >
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.mac.$error.required">mac不能为空</li>
                        </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">connect_protocol</label>
                    <div class="col-sm-9" >
                        <div class="checkbox checkbox-success" v-for='connect in connectProtocolList'>
                            <input id="connect_{{$index}}" type="checkbox" value="{{$index}}" name="connectProtocol" v-form-ctrl required v-model="connectProtocol">
                            <label for="connect_{{$index}}">{{connect}}</label>
                        </div>
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.connectProtocol.$error.required">connect_protocol不能为空</li>
                        </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">auth_key</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" required="" name="authKey" v-model="formdata['authKey']" placeholder="auth及通信的加密key，第三方需要将key烧制在设备上（128bit），格式采用16进制串的方式（长度为32字节）" >
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">close_strategy</label>
                    <div class="col-sm-9">
                      <select class="form-control" name="closeStrategy" v-form-ctrl required v-model="formdata['closeStrategy']" >
                          <option value="1" selected>1：退出公众号页面时即断开连接</option>
                          <option value="2">2：退出公众号之后保持连接不断开</option>
                          <option value="3">3：退出公众号之后一直保持连接</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.closeStrategy.$error.required">close_strategy不能为空</li>
                      </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">conn_strategy</label>
                    <div class="col-sm-9">
                        <div class="checkbox checkbox-success">
                            <input id="checkbox5" value="1" type="checkbox" name="connStrategy" v-form-ctrl required  v-model='connStrategy' >
                            <label for="checkbox5">1：在公众号对话页面，不停的尝试连接设备</label>
                        </div>
                        <div class="checkbox checkbox-success">
                            <input id="checkbox6" value="4" type="checkbox" name="connStrategy" v-form-ctrl required  v-model='connStrategy'>
                            <label for="checkbox6"> 4：处于非公众号页面（如主界面等），微信自动连接</label>
                        </div>
                        <div class="checkbox checkbox-success">
                            <input id="checkbox7" value="8" type="checkbox" name="connStrategy" v-form-ctrl required  v-model='connStrategy'>
                            <label for="checkbox7">8：进入微信后即刻开始连接。</label>
                        </div>
                        <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                            <li class="parsley-required" v-if="myForm.connStrategy.$error.required">conn_strategy不能为空</li>
                        </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">crypt_method</label>
                    <div class="col-sm-9">
                      <select class="form-control" name="cryptMethod" v-form-ctrl required  v-model="formdata['cryptMethod']">
                        <option value="0" selected>0：不加密</option>
                        <option value="1">1：AES加密（CBC模式，PKCS7填充方式）</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.cryptMethod.$error.required">conn_strategy不能为空</li>
                      </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">auth_ver</label>
                    <div class="col-sm-9">
                      <select class="form-control" name="authVer" v-form-ctrl required v-model="formdata['authVer']">
                        <option value="0" selected>0：不加密的version</option>
                        <option value="1">1：version 1</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.authVer.$error.required">authVer不能为空</li>
                      </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">manu_mac_pos</label>
                    <div class="col-sm-9">
                      <select name="manuMacPos" class="form-control"  v-form-ctrl required v-model="formdata['manuMacPos']">
                        <option value="-1" selected>-1：在尾部</option>
                        <option value="-2">-2：表示不包含mac地址</option>
                        <option value="0">其他：非法偏移</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.manuMacPos.$error.required">manu_mac_pos不能为空</li>
                      </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">ser_mac_pos</label>
                    <div class="col-sm-9">
                      <select name="serMacPos" class="form-control" v-form-ctrl required v-model="formdata['serMacPos']">
                        <option value="-1" selected>-1：在尾部</option>
                        <option value="-2" >-2：表示不包含mac地址</option>
                        <option value="0">其他：非法偏移</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.serMacPos.$error.required">ser_mac_pos不能为空</li>
                      </ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">ble_simple_protocol</label>
                    <div class="col-sm-9">
                      <select name="bleSimpleProtocol" class="form-control" v-form-ctrl required v-model="formdata['bleSimpleProtocol']">
                        <option value="1" selected>1：计步设备精简协议</option>
                      </select>
                      <ul class="parsley-errors-list filled" v-if="myForm.$submitted">
                          <li class="parsley-required" v-if="myForm.bleSimpleProtocol.$error.required">ble_simple_protocol不能为空</li>
                      </ul>
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
            devices: [],
            myForm:{},
            products:[],
            modaltitle: '添加设备',
            keyword: null,
            editing: false,
            pageNo: 1,
            pages:1,
            pageSize:15,
            connectProtocol:[],
            connectProtocolList:[
              'android classic bluetooth','ios classic bluetooth','ble','wifi'
            ],
            connStrategy:[],
            columns: { //table 表头
                id: 'ID',
                deviceId: '设备ID',
                mac: 'mac地址',
                createTime: '创建时间'
            },
            formdata: {}
        }
    },
    ready: function() {
        this.fetchData()
        this.fetchProduct()
    },
    methods: {
        convertDate(datestr){
          return new Date(datestr).toLocaleString()
        },
        connectProtocolArray(){
          return _.split(this.formdata['connectProtocol'],'|')
        },
        getParams(){
          return {
            deviceId: this.formdata['deviceId']?this.formdata['deviceId']:'',
            productId: this.formdata['productId']?this.formdata['productId']:'',
            mac: this.formdata['mac']?this.formdata['mac']:'',
            connectProtocol:this.connectProtocol?this.connectProtocol.join('|'):'',
            authKey: this.formdata['authKey']?this.formdata['authKey']:'',
            closeStrategy: this.formdata['closeStrategy']?this.formdata['closeStrategy']:'',
            connStrategy: function(){
              var connStrategy = 0;
              $('input[name="connStrategy"]:checked').each(function () {
                  connStrategy |= $(this).val().trim()
              });
              return connStrategy
            },
            authVer: this.formdata['authVer']?this.formdata['authVer']:'',
            manuMacPos: this.formdata['manuMacPos']?this.formdata['manuMacPos']:'',
            serMacPos: this.formdata['serMacPos']?this.formdata['serMacPos']:'',
            cryptMethod: this.formdata['cryptMethod']?this.formdata['cryptMethod']:'',
            bleSimpleProtocol: this.formdata['bleSimpleProtocol']?this.formdata['bleSimpleProtocol']:''
          }
        },
        fetchProduct(){
          this.$http.get('boss/product/list').then((response)=>{
            this.products = response.data.data.result
          })
        },
        checkedStrategy(){
          let self = this
          $('input[name="connStrategy"]').each(function () {
            let a = parseInt(self.formdata['connStrategy'])
            let b = $(this).val()
            if ((a & b) == b) {
              self.connStrategy.push(b)
            }
          });
        },
        addModal() {
            this.showModal = true
            this.formdata = {}
            this.editing = false
            this.modaltitle = '添加设备'

            this.connectProtocol = []
            this.connStrategy = []
        },
        editModal(entry){
          this.showModal = true
          this.formdata = entry
          this.editing = true
          this.modaltitle = '修改设备'
          this.checkedStrategy()
          this.connectProtocol = this.connectProtocolArray()
        },
        setPages(total){
          this.pages = total%this.pageSize==0?(total/this.pageSize):parseInt(total/this.pageSize + 1)
        },
        fetchData() {
            let self = this
            let params = {
                pageNo: this.pageNo,
                keyword: this.keyword,
                pageSize: this.pageSize
            }
            this.$http.post('boss/device/list', params).then((response) => {
                this.devices = response.data.data.result
                this.setPages(response.data.data.totalCount)

            })
        },
        postData(){
          debugger
          let params = this.getParams()
          let self = this
          if(this.myForm.$valid){
            if(self.editing){
              self.$http.post('boss/device/update?id='+self.formdata['id'], params).then((response) => {
                  if(response.data.success){
                    self.fetchData()
                    self.showModal = false
                  }else{
                    alert('提交失败')
                  }
              })
            }else{
              this.$http.post('boss/device/add', params).then((response) => {
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
