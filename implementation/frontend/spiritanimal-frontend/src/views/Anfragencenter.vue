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
                  <img :src="item.inserat.foto" height="70" width="70"/>
                </v-avatar>
              </v-col>
              <v-col style="max-width: 80%;  padding-top: 35px">
                <v-row>
                  {{item.inserat.tiername}}
                </v-row>
                <v-row>
                  {{item.inserat.kategorie}}
                </v-row>
                <!--                <v-row>-->
                <!--                  {{this.response}}-->
                <!--                </v-row>-->
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row style="padding-top: 5px">
              {{item.inserat.beschreibung}}
              <v-spacer></v-spacer>
              Status:&nbsp;
              <span v-if="item.status==='BEZAHLT'">
                ANFRAGE - BITTE UM BESTÄTIGUNG
              </span>
              <span v-else>
                {{item.status}}
              </span>
            </v-row>
            <v-row style="padding-top: 5px">
              Age: {{item.inserat.alter}}
              <v-spacer></v-spacer>
              Interessent: {{item.käuferUsername}}
            </v-row>
            <v-row style="padding-top: 5px">
              Preis: {{item.inserat.preis}}
              <v-spacer></v-spacer>
              Kaufangebot vom: {{item.kaufdatum}}
            </v-row>

            <v-row v-if="item.status==='BEZAHLT'">
              <v-col>
                <v-tooltip bottom>
                  <template #activator="{ on:tooltip, attrs }">
                    <v-btn @click="akzeptieren(item.kaufID)" style="margin-left: 60%"  v-bind="attrs" v-on="{ ...tooltip}">
                      <v-icon>{{ iconAkzeptieren }}</v-icon>
                    </v-btn>
                  </template>
                  <span>Akzeptieren</span>
                </v-tooltip>
              </v-col>
              <v-col>
                <v-tooltip bottom>
                  <template #activator="{ on:tooltip, attrs }">
                    <v-btn @click="ablehnen(item.kaufID)" style="margin-left: 10%"  v-bind="attrs" v-on="{ ...tooltip}">
                      <v-icon>{{ iconAblehnen }}</v-icon>
                    </v-btn>
                  </template>
                  <span>Ablehnen</span>
                </v-tooltip>
              </v-col>
            </v-row>

            <v-row v-if="item.status==='WIDERRUF_EINGELEITET'">
              <v-col>
                <v-dialog
                    transition="dialog-bottom-transition"
                    max-width="600"
                >
                  <template #activator="{ on:dialog, attrs }">

                    <v-tooltip bottom>
                      <template #activator="{ on:tooltip, attrs }">
                        <v-btn style="margin-left: 50%"  v-bind="attrs" v-on="{ ...tooltip, ...dialog }">
                          <v-icon>{{ iconTierAngekommen }}</v-icon>
                        </v-btn>
                      </template>
                      <span>Tier zurückerhalten</span>
                    </v-tooltip>

                  </template>
                  <template v-slot:default="dialog">
                    <v-card>
                      <v-toolbar
                          color="primary"
                          dark
                      >Widerruf schließen</v-toolbar>
                      <v-card-text>
                        <div class="text-h6 pa-12">Bitte bestätigen Sie, dass sie das Tier vom Käufer zurück erhalten haben.</div>
                      </v-card-text>
                      <v-card-actions class="justify-end">

                        <v-btn
                            text
                            @click="schließeWiderruf(item.kaufID), dialog.value = false"
                        >Bestätigen</v-btn>

                      </v-card-actions>
                    </v-card>
                  </template>
                </v-dialog>

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
  name: "Anfragencenter",
  components: {},
  data: () => ({
    iconAkzeptieren: "mdi-check",
    iconAblehnen: "mdi-close",
    iconTierAngekommen: "mdi-horse-variant-fast",
    käufe: null,
    requestData: {
      kaufID: "",
      tierBeiKäufer: null
    },
    response: ""
  }),
  computed: {},
  beforeCreate() {},
  created() {},
  async mounted() {
    axios
        .get( this.$apiUrl + '/kaeufe?anbieter',
            {headers: {Authorization: sessionStorage.getItem("accessToken")}}
        )
        .then(result => (this.käufe = result.data))
  },
  watch: {},
  methods: {
    akzeptieren(kaufID) {
      this.requestData.kaufID = kaufID;
      axios({ method: "POST", "url": this.$apiUrl + "/kaeufe/akzeptieren", "data": this.requestData, "headers": { "content-type": "application/json", "Authorization": sessionStorage.getItem("accessToken") } })
          .then(result => {
            this.response = result.data;
            console.log(this.response)
            if (this.response.includes("Kauf abgeschlossen")) {
              window.location.reload();
            }
          }, error => {
            console.error(error);
          })
    },

    ablehnen(kaufID) {
      this.requestData.kaufID = kaufID;
      axios({ method: "POST", "url": this.$apiUrl + "/kaeufe/ablehnen", "data": this.requestData, "headers": { "content-type": "application/json", "Authorization": sessionStorage.getItem("accessToken") } })
          .then(result => {
            this.response = result.data;
            console.log(this.response)
            if (this.response.includes("Kauf abgebrochen")) {
              window.location.reload();
            }
          }, error => {
            console.error(error);
          })
    },

    schließeWiderruf(kaufID) {
      this.requestData.kaufID = kaufID;
      axios({ method: "POST", "url": this.$apiUrl + "/kaeufe/widerruf/schliessen", "data": this.requestData, "headers": { "content-type": "application/json", "Authorization": sessionStorage.getItem("accessToken") } })
          .then(result => {
            this.response = result.data;
            console.log(this.response)
            if (this.response.includes("Widerruf abgeschlossen")) {
              window.location.reload();
            }
          }, error => {
            console.error(error);
          })
    }
  },
});
</script>

<style lang="scss"></style>