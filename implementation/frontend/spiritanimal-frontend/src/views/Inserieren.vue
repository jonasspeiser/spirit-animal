<template>
  <div>
    <v-app-bar color="white" class="ml-0" flat dense>
      <v-col>
        <div class="title">Inserieren</div>
      </v-col>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-container style="margin-left: 20px">
      <v-card>
        <v-card-title>Tierinformationen</v-card-title>
        <v-card-text>
          <v-row align="center" class="mx-0">
            <v-text-field :counter="20" label="Name" required></v-text-field>
          </v-row>
          <v-row align="center" class="mx-0">
            <v-select
                :items="categories"
                label="Tiergattung"
            ></v-select>
          </v-row>
          <v-row align="center" class="mx-0">
            <v-slider v-model="valueAge" step="1" thumb-label ticks></v-slider>
          </v-row>
          <v-row align="center" class="mx-0">
            Alter: {{valueAge}}
          </v-row>
          <v-row align="center" class="mx-0">
            <v-textarea>
              <template v-slot:label>
                <div>
                  Beschreibung zum Tier
                </div>
              </template>
            </v-textarea>
          </v-row>
          <v-row>
            <v-text-field
                label="Link zum Foto"
            ></v-text-field>
          </v-row>
        </v-card-text>
        <v-divider class="mx-4"></v-divider>
        <v-card-title>Verkaufsinformationen</v-card-title>
        <v-card-text>
          <v-row align="center" class="mx-0">
            <!-- TODO: Ueberlegen ob sinnvoll-->
            <v-slider v-model="valuePrice" step="1" max="10000" thumb-label ticks></v-slider>
          </v-row>
          <v-row align="center" class="mx-0">
            Preis: {{valuePrice}}
          </v-row>
        </v-card-text>
      </v-card>
      <v-row>
        <v-col>
          <v-dialog>
            <template v-slot:activator="{ on, attrs }">
              <v-btn style="background-color: #4ebfe7; color: white; margin-left: 80%; margin-top: 20px" v-bind="attrs"
                     v-on="on">
                Inserieren
              </v-btn>
            </template>
            <v-card>
              <v-card-title class="text-h5">
                Sind Sie sicher, dass sie das Inserat veroeffentlichen wollen?
              </v-card-title>
              <!-- TODO: Text ueberlegen -->
              <v-card-text>Wenn Sie dem Dialog zustimmen, werden die Daten BLABLABLA</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green darken-1"
                    text
                    @click="dialog = false"
                >
                  Ablehnen
                </v-btn>
                <v-btn
                    color="green darken-1"
                    text
                    @click="dialog = false"
                >
                  Zustimmen
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import Vue, { PropType } from "vue";

export default Vue.extend({
  name: "InsertView",
  components: {},
  data: () => ({
    valueAge: 0,
    valuePrice: 0,
    categories: ["Hund", "Katze", "Vogel", "Qualle", "Pferd", "..."]
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