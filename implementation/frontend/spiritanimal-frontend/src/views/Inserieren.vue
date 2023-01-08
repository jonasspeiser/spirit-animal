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
            <v-text-field :counter="20" label="Name" v-model="input.tiername" required></v-text-field>
          </v-row>
          <v-row align="center" class="mx-0">
            <v-select
                :items="categories"
                label="Tiergattung"
                v-model="input.kategorie"
            ></v-select>
          </v-row>
          <v-row align="center" class="mx-0">
            <v-text-field label="Alter" v-model="input.alter" required></v-text-field>
          </v-row>
          <v-row align="center" class="mx-0">
            <v-textarea v-model="input.beschreibung">
              <template v-slot:label>
                <div>
                  Beschreibung zum Tier
                </div>
              </template>
            </v-textarea>
          </v-row>
          <v-row>
            <v-text-field
                label="Link zum Foto" v-model="input.foto"
            ></v-text-field>
          </v-row>
        </v-card-text>
        <v-divider class="mx-4"></v-divider>
        <v-card-title>Verkaufsinformationen</v-card-title>
        <v-card-text>
          <v-row align="center" class="mx-0">
            <!-- TODO: Ueberlegen ob sinnvoll-->
            <v-text-field label="Preis" v-model="input.preis" required></v-text-field>
          </v-row>
        </v-card-text>
      </v-card>
      <v-row>
        <v-col>
          <v-dialog>
            <template v-slot:activator="{ on, attrs }">
              <v-btn style="background-color: #4ebfe7; color: white; margin-left: 80%; margin-top: 20px" v-bind="attrs"
                     v-on="on" v-on:click="sendData()">
                Inserieren
              </v-btn>
            </template>
<!--            <v-card>-->
<!--              <v-card-title class="text-h5">-->
<!--                Sind Sie sicher, dass sie das Inserat veroeffentlichen wollen?-->
<!--              </v-card-title>-->
<!--              &lt;!&ndash; TODO: Text ueberlegen &ndash;&gt;-->
<!--              <v-card-text>Wenn Sie dem Dialog zustimmen, werden die Daten BLABLABLA</v-card-text>-->
<!--              <v-card-actions>-->
<!--                <v-spacer></v-spacer>-->
<!--                <v-btn-->
<!--                    color="green darken-1"-->
<!--                    text-->
<!--                    @click="dialog = false"-->
<!--                >-->
<!--                  Ablehnen-->
<!--                </v-btn>-->
<!--                <v-btn-->
<!--                    color="green darken-1"-->
<!--                    text-->
<!--                    @click="dialog = false"-->
<!--                >-->
<!--                  Zustimmen-->
<!--                </v-btn>-->
<!--              </v-card-actions>-->
<!--            </v-card>-->
          </v-dialog>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import Vue, { PropType } from "vue";
import axios from "axios"

export default Vue.extend({
  name: "InsertView",
  components: {},
  data: () => ({
    valueAge: 0,
    valuePrice: 0,
    categories: ["Hund", "Katze", "Vogel", "QUALLEN", "Pferd", "..."],
    input: {
      tiername: "",
      kategorie: "",
      alter: "",
      beschreibung: "",
      preis: ""
    }
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {},
  watch: {},
  methods: {
    sendData() {
      axios({ method: "POST", "url": "http://localhost:8080/api/inserate", "data": this.input, "headers": { "content-type": "application/json" } }).then(result => {
        this.response = result.data;
      }, error => {
        console.error(error);
      })
    }
  },
});
</script>

<style lang="scss"></style>