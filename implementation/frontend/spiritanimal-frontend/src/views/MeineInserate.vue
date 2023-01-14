<template>
  <div>
    <v-app-bar color="white" class="ml-0" flat dense>
      <v-col>
        <div class="title">Meine Inserate</div>
      </v-col>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-container style="margin-left: 20px">
      <v-expansion-panels>
        <v-expansion-panel v-for="(item,i) in animal " :key="i">
          <v-expansion-panel-header>
            <v-row>
              <v-col style="max-width: 10%;">
                <v-avatar>
                  <!-- TODO: change path to images html - https://www.w3schools.com/html/html_filepaths.asp-->
                  <img :src="item.foto" height="70" width="70"/>
                </v-avatar>
              </v-col>
              <v-col style="max-width: 80%;  padding-top: 35px">
                <v-row>
                  {{item.tiername}}
                  <v-spacer></v-spacer>
                  <p v-if="item.premium===true">
                    PREMIUM
                  </p>
                </v-row>
                <v-row>
                  {{item.kategorie}}
                </v-row>
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row style="padding-top: 5px">
              {{item.beschreibung}}
              <v-spacer></v-spacer>
              Status: {{item.status}}
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
                <v-btn style="margin-left: 60%" @click="deleteInserat(item.inseratID)">
                  <v-icon>{{iconDelete}}</v-icon>
                </v-btn>
              </v-col>
              <v-col>
                <!-- TODO: function when buy button clicked-->
                <v-btn style="margin-right: 40%" @click="changeInserat">
                  <v-icon>{{iconChange}}</v-icon>
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
/*TODO: change path to json + change values on top, based on how it is called*/
import Animals from "@/data/animals.json";
import axios from "axios";

export default Vue.extend({
  name: "DashboardView",
  components: {
    // Dashboard
  },
  data: () => ({
    iconDelete: "mdi-delete",
    iconChange: "mdi-pencil",
    animal: null,
  }),
  computed: {},
  beforeCreate() {
    axios({method: "GET", "url": this.$apiUrl + "/inserate/" + sessionStorage.getItem("username"), "headers": { "content-type": "application/json" } }).then(response => {
      this.animal = response.data;})
  },
  created() {},
  async mounted() {},
  watch: {},
  methods: {
    deleteInserat(inseratID){
      axios.delete(this.$apiUrl + "/inserate/" + inseratID, {headers: {Authorization: sessionStorage.getItem("accessToken")}})
      window.location.reload()
    },
    changeInserat(){}
  },
});
</script>

<style lang="scss"></style>