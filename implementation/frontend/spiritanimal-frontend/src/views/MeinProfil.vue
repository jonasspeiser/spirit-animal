<template>
  <v-container>
    <v-row>
      <v-col>
        <v-icon>mdi-jellyfish</v-icon>
        Mein API-Token
        <v-icon>mdi-jellyfish</v-icon>
      </v-col>
      <v-col>
        <code>
          {{token}}
        </code>
      </v-col>
    </v-row>
    <v-row>
      <v-btn
          depressed
          color="primary"
          @click="redirect"
          v-if="username == 'admin'"
      >
        Admin Space
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
import Vue from "vue";

export default Vue.extend({
  name: "MeinProfil",
  components: {},
  data: () => ({
    token: sessionStorage.getItem("accessToken"),
    username: sessionStorage.getItem("username")
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {},
  watch: {},
  methods: {
    redirect(){
      window.location.href='/adminspace'
    }
  },
})
</script>

<style lang="scss"></style>