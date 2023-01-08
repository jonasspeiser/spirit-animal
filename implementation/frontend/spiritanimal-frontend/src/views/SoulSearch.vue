<template>
  <div>
    <v-app-bar color="white" class="ml-0" flat dense>
      <v-col>
        <div class="title">TinderView</div>
      </v-col>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-container style="margin-left: 20px">
      <v-row>
        <v-col style="max-width: 90%"></v-col>
        <v-col style="max-width: 10%">
          <v-btn class=" ma-5" fab v-bind="attrs" v-on="on" style="margin-left: 80%" @click="showMenu = !showMenu">
            <v-icon>{{ setting }} </v-icon>
          </v-btn>
          <v-card v-if="showMenu" style="min-width: 800px">
            <v-row>
              <v-subheader>Alter</v-subheader>
              <v-card-text>
                <v-slider
                    v-model="valueAge"
                    step="1"
                    thumb-label
                    ticks
                    min="0"
                    max="10000"
                ></v-slider>
              </v-card-text>
            </v-row>
            <v-row>
              <v-subheader>Preis</v-subheader>
              <v-card-text>
                <v-slider
                    v-model="valuePreis"
                    step="10"
                    thumb-label
                    ticks
                    min="10"
                    max="10000"
                ></v-slider>
              </v-card-text>
            </v-row>
            <v-row>
              <v-checkbox
                  v-for="i in categories"
                  v-model="checkbox"
                  :label="`${i}`"
              ></v-checkbox>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col style="max-width: 20%">
          <v-btn fab dark large color="green" style="margin-top: 323px; margin-left: 125px;">
            <v-icon> {{animalYes}}</v-icon>
          </v-btn>
        </v-col>
        <v-col style="max-width: 60%">
          <v-card
              :loading="loading"
              class="mx-auto my-12"
              max-width="374"
          >
            <template slot="progress">
              <v-progress-linear
                  color="deep-purple"
                  height="10"
                  indeterminate
              ></v-progress-linear>
            </template>
            <v-img
                height="250"
                src="https://www.scinexx.de/wp-content/uploads/b/i/bildsynthese_g.jpg"
            ></v-img>
            <v-card-title style="margin-bottom: 10px">{{animals[0].name}}</v-card-title>
            <v-card-text>
              <v-row align="center" class="mx-0">
              </v-row>
              <div>{{animals[0].description}}</div>
            </v-card-text>
            <v-divider class="mx-4"></v-divider>
            <v-card-title>Informationen</v-card-title>
            <v-card-text>
              <v-row style="padding-left: 10px">
                Tiergattung: {{animals[0].animal}}
              </v-row>
              <v-row style="padding-left: 10px">
                Alter: {{animals[0].age}} Jahre
              </v-row>
              <v-row style="padding-left: 10px">
                Preis: {{animals[0].price}} Euro
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col style="max-width: 20%">
          <v-btn fab dark large color="red" style="margin-top: 323px; margin-right: 125px;">
            <v-icon> {{animalNo}}</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import Vue, { PropType } from "vue";
/*TODO: change path to json + change values on top, based on how it is called*/
import Animals from "@/data/animals.json";

export default Vue.extend({
  name: "TinderView",
  components: {},
  data: () => ({
    showMenu: false,
    valueAge: 0,
    valuePreis: 10,
    setting: "mdi-cog-outline",
    animalYes:  "mdi-checkbox-marked-circle",
    animalNo: "mdi-close-circle-outline",
    animals: Animals.animals,
    categories: ["Hund", "Katze", "Qualle", "Pferd", "Vogel", "..."]
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {},
  watch: {},
  methods: {},
});
</script>

<style lang="scss"></style>