<template>
  <div id="login">
    <h1>Login</h1>
    <input type="text" name="username" v-model="input.username" placeholder="Username" />
    <input type="password" name="password" v-model="input.password" placeholder="Password" />
    <button type="button" v-on:click="login()">Login</button>
    <button v-on:click="sendData()">Send</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'Login',
  data() {
    return {
      input: {
        username: "",
        password: ""
      },
      response: ""
    }
  },
  // mounted() {
  //   axios({ method: "POST", "url": "https://localhost:8080/api/users", "data": this.input, "headers": { "content-type": "application/json" } }).then(result => {
  //     this.response = result.data;
  //   }, error => {
  //     console.error(error);
  //   })
  // },
  methods: {
    login() {
      if(this.input.username != "" && this.input.password != "") {
        if(this.input.username == this.$parent.mockAccount.username && this.input.password == this.$parent.mockAccount.password) {
          this.$emit("authenticated", true);
          this.$router.replace({ name: "secure" });
        } else {
          console.log("The username and / or password is incorrect");
        }
      } else {
        console.log("A username and password must be present");
      }
    },
    sendData() {
      axios({ method: "POST", "url": "https://localhost:8080/api/users", "data": this.input, "headers": { "content-type": "application/json" } }).then(result => {
        this.response = result.data;
      }, error => {
        console.error(error);
      })
    }
  }
}
</script>

<style scoped>
#login {
  width: 500px;
  border: 1px solid #CCCCCC;
  background-color: aliceblue;
  margin: auto;
  margin-top: 200px;
  padding: 20px;
}
</style>