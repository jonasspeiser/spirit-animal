<template>
<v-row>
  <v-col>Mein API-Token</v-col>
  <v-col>
    <code>
      {{token}}
    </code>
  </v-col>
</v-row>
</template>

<script>
import Vue from "vue";

export default Vue.extend({
  name: "MeinProfil",
  components: {},
  data: () => ({
    token: sessionStorage.getItem("accessToken")
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {},
  watch: {},
  methods: {},
})
</script>

<style scoped>

</style>