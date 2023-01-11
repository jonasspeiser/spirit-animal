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
              <v-text-field label="Username" name="name" prepend-inner-icon="mdi-account" type="text" class="rounded-0" outlined></v-text-field>
              <v-text-field label="E-Mail" name="email" prepend-inner-icon="mdi-email" type="email" class="rounded-0" outlined></v-text-field>
              <v-text-field label="Zahlungsdaten" name="password" prepend-inner-icon="mdi-credit-card" type="password" class="rounded-0" outlined></v-text-field>
              <v-text-field label="Passwort" name="password" prepend-inner-icon="mdi-lock" type="password" class="rounded-0" outlined></v-text-field>
              <v-btn class="rounded-0" color="#000000" x-large block dark>Register</v-btn>
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
export default {
  name: "app-register"
}
</script>

<style lang="css" scoped>
</style>