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
                  <img src="https://www.scinexx.de/wp-content/uploads/b/i/bildsynthese_g.jpg" height="70" width="70"/>
                </v-avatar>
              </v-col>
              <v-col style="max-width: 80%;  padding-top: 35px">
                <v-row>
                  {{item.name}}
                </v-row>
                <v-row>
                  {{item.animal}}
                </v-row>
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row style="padding-top: 5px">
              {{item.description}}
            </v-row>
            <v-row style="padding-top: 5px">
              Age: {{item.age}}
            </v-row>
            <v-row style="padding-top: 5px">
              Preis: {{item.price}}
            </v-row>
            <v-row>
              <v-col>
                <!-- TODO: function when delete button clicked-->
                <v-btn style="margin-left: 60%" @click="deleteDarling">
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

<script lang="ts">
import Vue, { PropType } from "vue";
import axios from "axios"
/*TODO: change path to json + change values on top, based on how it is called*/
import Animals from "@/data/animals.json";

export default Vue.extend({
  name: "DashboardView",
  components: {
    // Dashboard
  },
  data: () => ({
    iconDelete: "mdi-delete",
    iconBuy: "mdi-cart-outline",
    animals: Animals.animals,
    info: null
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {
    axios
        .get('http://localhost:8080/api/inserate/Herbert')
        .then(response => (this.info = response.data))
  },
  watch: {},
  methods: {
    deleteDarling(){

    }
  },
});
</script>

<style lang="scss"></style>