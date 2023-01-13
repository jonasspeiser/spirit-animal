<template>
  <div>
    <v-app-bar color="white" class="ml-0" flat dense>
      <v-col>
        <div class="title">{{ info }}</div>
      </v-col>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-container style="margin-left: 20px">
      <v-expansion-panels>
        <v-expansion-panel v-for="(item,i) in animals" :key="i">
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
                <v-btn style="margin-left: 60%" @click="deleteDarling(item.inseratID)">
                  <v-icon>{{iconDelete}}</v-icon>
                </v-btn>
              </v-col>
              <v-col>
                <!-- TODO: function when buy button clicked-->
                <v-btn style="margin-right: 40%" @click="">
                  <v-icon>{{iconBuy}}</v-icon>
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
  name: "MyDarlings",
  components: {

  },
  data: () => ({
    iconDelete: "mdi-delete",
    iconBuy: "mdi-cart-outline",
    animals: null,
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {
    axios
        .get( this.$apiUrl + '/soulsearch/mydarlings',
            {headers: {Authorization: sessionStorage.getItem("accessToken")}}
        )
        .then(response => (this.animals = response.data))
  },
  watch: {},
  methods: {
    deleteDarling(inseratID){
      axios
          .delete( this.$apiUrl + '/soulsearch/mydarlings/' + inseratID,
              {headers: {Authorization: sessionStorage.getItem("accessToken")}}
          )
      window.location.reload()
    }
  },
});
</script>

<style lang="scss"></style>