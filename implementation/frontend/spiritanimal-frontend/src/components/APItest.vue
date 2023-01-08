<template>
  <div id="APItest">
    <h1>APItest</h1>
    <button v-on:click="getData()">Get Response</button>
<!--    <p>{{response.data}}</p>-->
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "APItest",
  data(){
    return {
      message: "success"
    }
  },
  methods: {
    getData() {
      axios({method: "GET", "url": "http://localhost:8080/api/test", "headers": { "content-type": "application/json" } }).then(result => {
        this.response = result.data;})
    }
  }
}

</script>

<style scoped>

</style>