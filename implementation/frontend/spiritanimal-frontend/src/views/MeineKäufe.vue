<template>
  <div>
    <v-app-bar color="white" class="ml-0" flat dense>
      <v-col>
        <div class="title">{{ this.$options.name }}</div>
      </v-col>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-container style="margin-left: 20px">
      <v-expansion-panels>
        <v-expansion-panel v-for="(item,i) in käufe" :key="i">
          <v-expansion-panel-header>
            <v-row>
              <v-col style="max-width: 10%;">
                <v-avatar>
                  <img :src="item.foto" height="70" width="70"/>
                </v-avatar>
              </v-col>
              <v-col style="max-width: 80%;  padding-top: 35px">
                <v-row>
                  {{item.tiername}}
                </v-row>
                <v-row>
                  {{item.kategorie}}
                </v-row>
                <!--                <v-row>-->
                <!--                  {{this.response}}-->
                <!--                </v-row>-->
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row style="padding-top: 5px">
              {{item.beschreibung}}
            </v-row>
            <v-row style="padding-top: 5px">
              Age: {{item.alter}}
            </v-row>
            <v-row style="padding-top: 5px">
              Preis: {{item.preis}}
            </v-row>
            <v-row>
              <v-col>
                <!-- TODO: function when delete button clicked-->
                <v-btn style="margin-left: 60%" @click="starteWiderruf(item.kaufID)">
                  <v-icon>{{iconWiderruf}}</v-icon>
                </v-btn>
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-container>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios"

export default Vue.extend({
  name: "Meine Käufe",
  components: {},
  data: () => ({
    iconWiderruf: "mdi-alert-outline",
    käufe: null,
    requestData: {
      kaufID: ""
    },
    response: ""
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {
    axios
        .get( this.$apiUrl + '/kaeufe?kaeufer',
            {headers: {Authorization: sessionStorage.getItem("accessToken")}}
        )
        .then(result => (this.käufe = result.data))
  },
  watch: {},
  methods: {

    // TODO!

    starteWiderruf(kaufID) {
      this.requestData.kaufID = kaufID;
      axios({ method: "POST", "url": this.$apiUrl + "/widerruf", "data": this.requestData, "headers": { "content-type": "application/json", "Authorization": sessionStorage.getItem("accessToken") } })
          .then(result => {
            this.response = result.data;
            console.log(this.response)
            if (this.response.includes("Kauf vorgemerkt")) {
              window.location.href="/meineKaeufe";
            }
          }, error => {
            console.error(error);
          })
    }
  },
});
</script>

<style lang="scss"></style>