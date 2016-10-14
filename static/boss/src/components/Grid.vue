<template>
<div class="row">
  <div class="table-responsive">
      <table class="table table-hover mails m-0 table table-actions-bar">
          <thead>
              <tr>
                  <th v-for='item in columns'>{{item.label}}</th>
                  <th v-if="editurl">操作</th>
              </tr>
          </thead>
          <tbody id="gridContent">
              <tr v-for='entry in datas' id='row_{{entry.id}}'>
                  <td v-for='item in columns' style="max-width:400px">
                      <div v-if="item.type=='array'">
                        <span>
                          {{joinToString(entry[item.key],item.arryName)}}
                        </span>
                      </div>
                      <span v-if="item.type=='img'">
                        <img v-bind:src="entry[item.key]" style="max-width:100px;background:#dedede" alt="" />
                      </span>
                      <div  v-if="item.type=='html'" style="max-height:80px;overflow:hidden">
                        {{{entry[item.key]}}}
                      </div>
                      <span  v-if="item.type=='select'">
                          {{{item.selections[entry[item.key]]}}}
                      </span>
                      <span v-if="item.type==undefined" style="word-break: break-word;">
                        {{entry[item.key]}}
                      </span>
                  </td>
                  <td v-if="editurl">
                      <slot name="actions_btns">
                        <a href="#" data-toggle="modal" data-target="#{{modalid}}" class="table-action-btn" v-on:click="editPop(entry);" ><i class="md md-edit"></i></a>
                        <a href="#" class="table-action-btn" v-on:click="delData(entry.id)"><i class="md md-close"></i></a>
                      </slot>
                  </td>
              </tr>
          </tbody>
      </table>
  </div>
</div>

</template>

<script type="text/javascript">

  export default {
    props:['columns','data'],
    methods:{
      editPop : function(item){
        let data = item;
        this.setPopData(data);
        this.setModal(this.modaltitle,this.editurl+item.id);
        this.setEditing(true);
      },
      delData : function(_id){
        var self = this;
        alertify.confirm('确定删除？', function(){
          self.$http.get(self.delurl+_id).then( (response) => {
            if(response.data.success){
              document.getElementById("row_"+_id).remove();
              alertify.success('删除成功')
            }else{
              alertify.error(response.data.msg)
            }
          })
        }).set({transition:'fade'});
      }
    }
  }
</script>
