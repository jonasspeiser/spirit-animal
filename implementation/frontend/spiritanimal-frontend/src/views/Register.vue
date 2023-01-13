<!--source: https://www.youtube.com/watch?v=Re7FnxBVNoA-->

<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center" dense>
      <v-col cols="12" sm="8" md="4" lg="4">
        <v-card elevation="0">
          <div class="text-center">
            <h1 class="mb-2">Register</h1>
          </div>
          <a href="" name="Spirit Animal" title="Spirit Animal" target="_blank">
            <v-img src="@/assets/logo.png" alt="Spirit Animal Logo" contain height="200"></v-img>
          </a>
          <v-card-text>
            <v-form>
              <v-text-field v-model="input.username" label="Username" name="name" prepend-inner-icon="mdi-account" type="text" class="rounded-0" outlined></v-text-field>
              <v-text-field v-model="input.email" label="E-Mail" name="email" prepend-inner-icon="mdi-email" type="email" class="rounded-0" outlined></v-text-field>
              <v-text-field v-model="input.zahlungsdaten" label="Zahlungsdaten" name="zahlungsdaten" prepend-inner-icon="mdi-credit-card" type="text" class="rounded-0" outlined></v-text-field>
              <v-text-field v-model="input.password" label="Passwort" name="password" prepend-inner-icon="mdi-lock" type="password" class="rounded-0" outlined></v-text-field>
              <v-btn class="rounded-0" color="#000000" v-on:click="sendData()" x-large block dark>Register</v-btn>
              <a>{{this.response}}</a>
              <v-card-actions class="text--secondary">
                <v-spacer></v-spacer>
                Account bereits vorhanden?&nbsp; <router-link style="text-decoration: underline; color: black;" to="/login"> Login</router-link>
              </v-card-actions>
            </v-form>
          </v-card-text>
          <v-card-actions class="ml-6 mr-6 text-center">
            <p>Mit der weiteren Nutzung stimmen Sie unserer <a href="#" class="pl-2" style="color: #000000">Policy</a> und<a href="#" class="pl-2" style="color: #000000">Terms of Use</a> zu.</p>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Vue from "vue";
import axios from "axios"

export default Vue.extend({
  name: "app-register",
  components: {},
  data: () => ({
    input: {},
    response: ""
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {},
  watch: {},
  methods: {
    getData() {

    },
    sendData() {
      console.log(this.input);
      axios({ method: "POST", "url": this.$apiUrl + "/users", "data": this.input, "headers": { "content-type": "application/json" } }).then(result => {
        this.response = result.data;
        window.location.href="/login";
      }, error => {
        console.error(error);
      })
    }
  },
})
</script>

<style lang="css" scoped>
</style>