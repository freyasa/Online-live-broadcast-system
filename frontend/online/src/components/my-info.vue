<script lang="ts" setup>
import {login} from "../global/global";
import User from "../global/vo/User";
import {onMounted, ref} from "vue";
import {genFileId} from 'element-plus'
import type {UploadInstance, UploadProps, UploadRawFile} from 'element-plus'
import axios from "axios";
import {defaultAvatar} from "../global/static/base64Template";
import {ElMessage} from 'element-plus'


const upload = ref<UploadInstance>()
let showPasswordError = ref(false);
let modifyUser = ref(new User());
let modifyPassword = ref({
  oldPassword: '',
  newPassword: '',
  checkPassword: ''
})
let upFile = ref();

// for (let i in modifyUser) {
//   modifyUser.value[i] = login.user[i];
//   // console.log(modifyUser[i])
// }

const preModifyInfo = () => {
  const displayElement = document.getElementsByClassName('display')
  const modifyElement = document.getElementsByClassName('modify')
  for (let i = 0; i < displayElement.length; i++) {
    displayElement[i].style.display = 'none';
  }

  for (let i = 0; i < modifyElement.length; i++) {
    modifyElement[i].style.display = 'block'
  }
}


const getBase64 = (file) => {
  return new Promise(function (resolve, reject) {
    let reader = new FileReader();
    let imgResult = "";
    reader.readAsDataURL(file);
    reader.onload = function () {
      imgResult = reader.result;
    };
    reader.onerror = function (error) {
      reject(error);
    };
    reader.onloadend = function () {
      resolve(imgResult);
    };
  });
}

const getFile = (file) => {
  // if(file.success){
  console.log(file)
  const isJPG = file.raw.type === 'image/jpeg';
  const isPNG = file.raw.type === 'image/png';
  const isLt5M = file.raw.size / 1024 / 1024 < 5;
  if (!isJPG && !isPNG) {
    console.log('上传图片只能是JPG或者PNG格式!');
  }
  if (!isLt5M) {
    console.log('上传图片大小不能超过 5MB!');
  }
  if ((isJPG || isPNG) && isLt5M) {
    getBase64(file.raw).then(res => {
      // modifyUser.value.userAvatar = res;
    });
  }
}


const cancelModifyInfo = () => {
  const displayElement = document.getElementsByClassName('display')
  const modifyElement = document.getElementsByClassName('modify')
  for (let i = 0; i < displayElement.length; i++) {
    displayElement[i].style.display = 'block';
  }

  for (let i = 0; i < modifyElement.length; i++) {
    modifyElement[i].style.display = 'none';
  }
}

const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}

const affirmModifyAvatar = (options) => {
  // upload.value!.submit()
  console.log(options)
  console.log(upload.value)
}

const handleChange = (file, fileList) => {
  console.log(file, fileList)
  upFile.value = file.raw
}

const affirmModifyInfo = () => {
  // upload.value!.submit()
  // console.log(upload.value)

  let fd = new FormData();
  fd.append("uuid", login.user.uuid);       //附件类型
  fd.append("username", modifyUser.value.userName);       //附件类型
  fd.append("usersex", parseInt(String(modifyUser.value.userSex)));       //附件类型
  fd.append("userage", modifyUser.value.userAge);       //附件类型
  fd.append("photo", upFile.value);       //附件类型
  fd.append("useremail", modifyUser.value.userEmail);       //附件类型
  fd.append("userSignature", modifyUser.value.userSignature);       //附件类型

  console.log(fd)
  console.log(upFile.value)

  let config = {
    headers: {
      'Content-Type': 'multipart/form-data',
      'authorization': login.user.token,
    }
  }

  axios.post('http://8.140.143.119:8080/dev/user/updateBaseInfo', fd, config).then(data => {
    console.log(data.data)
    if (data.data.code === 200) {
      ElMessage({
        message: '更新成功',
        type: 'success',
      });
    }
  }).catch(data => {
    console.log(data)
  })


  // console.log(modifyUser.value)
  // axios
  //     .post("http://8.140.143.119:8080/dev/user/updateBaseInfo", {
  //           uuid: login.user.uuid,
  //           username: modifyUser.value.userName,
  //           usersex: parseInt(modifyUser.value.userSex),
  //           userage: modifyUser.value.userAge,
  //           useravatar: modifyUser.value.userAvatar,
  //           useremail: modifyUser.value.userEmail,
  //           userSignature: modifyUser.value.userSignature
  //         },
  //         {
  //           headers: {
  //             authorization: login.user.token,
  //           }
  //         })
  //     .then((data) => {
  //       console.log(data.data);
  //       login.user.userSex = modifyUser.value.userSex;
  //       login.user.userAge = modifyUser.value.userAge;
  //       login.user.userAvatar = modifyUser.value.userAvatar;
  //       login.user.userEmail = modifyUser.value.userEmail;
  //       login.user.userSignature = modifyUser.value.userSignature;
  //       ElMessage({
  //         message: '更新成功',
  //         type: 'success',
  //       });
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //       ElMessage.error('更新失败');
  //     })
}

const getCurrentUser = () => {
  let token = login.user.token;
  axios
      .get("http://8.140.143.119:8080/dev/user/getCurrentUser",
          {
            headers: {
              authorization: token,
            }
          })
      .then((data) => {
        // console.log("data.data");
        // console.log(data.data);
        login.user.uuid = data.data.data.uuid;
        login.user.userAccount = data.data.data.useraccount;
        login.user.userName = data.data.data.username;
        login.user.userSex = data.data.data.usersex;
        login.user.userAge = data.data.data.userage;
        login.user.userAvatar = data.data.data.useravatar;
        login.user.userEmail = data.data.data.useremail;
        login.user.userSignature = data.data.data.userSignature;

        modifyUser.value.uuid = data.data.data.uuid;
        modifyUser.value.userAccount = data.data.data.useraccount;
        modifyUser.value.userName = data.data.data.username;
        modifyUser.value.userSex = data.data.data.usersex;
        modifyUser.value.userAge = data.data.data.userage;
        modifyUser.value.userAvatar = data.data.data.useravatar;
        modifyUser.value.userEmail = data.data.data.useremail;
        modifyUser.value.userSignature = data.data.data.userSignature;
      })
      .catch((err) => {
        console.log(err);
      })
}

const affirmModifyPwd = () => {
  // let modifyPassword = ref({
  //   oldPassword: '',
  //   newPassword: '',
  //   checkPassword: ''
  // })
  if (modifyPassword.value.newPassword === modifyPassword.value.checkPassword) {
    axios
        .post("http://8.140.143.119:8080/dev/user/updatePsw", {
              oldPassword: modifyPassword.value.oldPassword,
              newPassword: modifyPassword.value.newPassword,
              checkPassword: modifyPassword.value.checkPassword
            },
            {
              headers: {
                authorization: login.user.token,
              }
            })
        .then((data) => {
          console.log(data.data);
          if (data.data.code === 202) {
            ElMessage.error('新密码过于简单');
          } else if (data.data.code === 200) {
            ElMessage({
              message: '更新成功',
              type: 'success',
            });
          } else {
            ElMessage.error('请重新登录');
            login.loginState = false;
            login.user = {};
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error('更新失败');
        })
  } else {
    showPasswordError.value = true;
  }
}

onMounted(() => {
  getCurrentUser();
})

</script>

<template>
  <!--  <div style="height: 1080px;-->
  <!--              background-image: linear-gradient(to bottom, transparent 70%, white), url('http://8.140.143.119:8000/images/2024/02/01/live_background.webp');">-->

  <!--  </div>-->

  <!--  <div style="font-size: 30px; text-align: left; margin-left: 20px">我的信息</div>-->
  <div style="background-color: #f0f0f0; height: calc(100vh - 60px)">
    <div style="width: 70%; background-image: url('https://img2.imgtp.com/2024/02/06/3Et6amP8.webp');
              margin-left: 15%; height: 180px; text-align: left; border-radius: 2px">
      <div style="display: inline-flex; margin-left: 75px; margin-top: 100px">
        <el-avatar :size="64" style=""
                   :src="modifyUser.userAvatar"
        />
        <div style="color: white; font-weight: 600; line-height: 64px; margin-left: 20px; font-size: 20px">
          {{ login.user.username }}
        </div>
      </div>
    </div>

    <div
        style="text-align: left; width: 70%; margin-left: 15%; margin-top: 10px; background-color: white; border-radius: 2px">
      <br/>
      <div style="font-weight: 500; margin-left: 30px; font-size: 25px">个人设置</div>

      <div style="margin-left: 100px; margin-top: 20px">

        <div class="display">
          <span class="attribute">uuid</span>:
          <span class="value"> {{ login.user.uuid }}</span>
        </div>

        <div class="display">
          <span class="attribute">用户名</span>:
          <span class="value"> {{ login.user.userName }}</span>
        </div>

        <div class="display">
          <span class="attribute">性别</span>:
          <span class="value"> {{ login.user.userSex === 1 ? '男' : '女' }}</span>
        </div>

        <div class="display">
          <span class="attribute">年龄</span>:
          <span class="value"> {{ login.user.userAge }}</span>
        </div>

        <div class="display">
          <span class="attribute">邮箱</span>:
          <span class="value"> {{ login.user.userEmail }}</span>
        </div>

        <div class="display">
          <span class="attribute">个性签名</span>:
          <span class="value"> {{ login.user.userSignature }}</span>
        </div>

        <div>
          <div class="modify">

            <div class="modify">
              <span class="attribute">头像</span>:
              <span class="value">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                    :limit="1"
                    :on-change="handleChange"
                    :on-exceed="handleChange"
                    :http-request="affirmModifyAvatar"
                    :auto-upload="false"
                >
                  <template #trigger>
                    <el-button type="primary">select file</el-button>
                  </template>
                  <!--                  <el-button class="ml-3" type="success" @click="affirmModifyAvatar">-->
                  <!--                    upload to server-->
                  <!--                  </el-button>-->
                  <template #tip>
                    <div class="el-upload__tip text-red">
                      limit 1 file, new file will cover the old file
                    </div>
                  </template>
                </el-upload>
              </span>
            </div>

            <div class="modify">
              <span class="attribute">性别</span>:
              <el-radio-group v-model="modifyUser.userSex">å
                <el-radio label="1">男</el-radio>
                <el-radio label="2">女</el-radio>
              </el-radio-group>
            </div>

            <span class="attribute">年龄</span>:
            <el-input-number v-model="modifyUser.userAge" class="value input" :step="1"/>
            <!--          <el-input v-model="modifyUser.userAge" class="value input"></el-input>-->
          </div>

          <div class="modify">
            <span class="attribute">邮箱</span>:
            <el-input v-model="modifyUser.userEmail" class="value input"></el-input>
          </div>

          <div class="modify">
            <span class="attribute">个性签名</span>:
            <el-input v-model="modifyUser.userSignature" class="value input"></el-input>
          </div>

        </div>

        <div style="margin-left: 100px; margin-top: 20px" class="display">
          <el-button type="primary" @click="preModifyInfo">修改信息</el-button>
        </div>
        <div style="margin-left: 100px; margin-top: 20px" class="modify">
          <el-button type="success" @click="affirmModifyInfo">确认修改</el-button>
          <el-button type="danger" @click="cancelModifyInfo">取消</el-button>
        </div>
        <br/>
      </div>
      <hr/>
      <div>
        <div style="margin-left: 100px; margin-top: 20px" class="modify">

          <el-alert v-show="showPasswordError" title="旧密码错误或两次输入的密码不一致" type="error" show-icon/>

          <div class="modify">
            <br/>
            <span class="attribute">旧密码</span>:
            <el-input v-model="modifyPassword.oldPassword" class="value input" show-password></el-input>
            <!--          <el-input v-model="modifyUser.userAge" class="value input"></el-input>-->
          </div>

          <div class="modify">
            <span class="attribute">新密码</span>:
            <el-input v-model="modifyPassword.newPassword" class="value input" show-password></el-input>
          </div>

          <div class="modify">
            <span class="attribute">确认密码</span>:
            <el-input v-model="modifyPassword.checkPassword" class="value input" show-password></el-input>
          </div>
          <div style="margin-left: 100px; margin-top: 20px" class="modify">
            <el-button type="success" @click="affirmModifyPwd">确认修改</el-button>
            <el-button type="danger" @click="cancelModifyInfo">取消</el-button>
          </div>
          <br/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.display {
  line-height: 2.5;
}

.modify {
  line-height: 2.5;
  display: none;
}

.input {
  width: 20%;
}

.attribute {

}

.value {

}
</style>