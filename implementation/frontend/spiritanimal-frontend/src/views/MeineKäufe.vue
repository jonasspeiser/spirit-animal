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
              Status: {{item.status}}
            </v-row>
            <v-row style="padding-top: 5px">
              Age: {{item.inserat.alter}}
              <v-spacer></v-spacer>
              Anbieter: {{item.anbieterUsername}}
            </v-row>
            <v-row style="padding-top: 5px">
              Preis: {{item.inserat.preis}}
              <v-spacer></v-spacer>
              Gekauft am: {{item.kaufdatum}}
            </v-row>
            <v-row>
              <v-col>
                <v-dialog
                    transition="dialog-bottom-transition"
                    max-width="600"
                >
                  <template #activator="{ on:dialog, attrs }">

                    <v-tooltip bottom>
                      <template #activator="{ on:tooltip, attrs }">
                        <v-btn style="margin-left: 60%"  v-bind="attrs" v-on="{ ...tooltip, ...dialog }">
                          <v-icon>{{iconWiderruf}}</v-icon>
                        </v-btn>
                      </template>
                      <span>Beanstanden</span>
                    </v-tooltip>

                  </template>
                  <template v-slot:default="dialog">
                    <v-card>
                      <v-toolbar
                          color="primary"
                          dark
                      >Widerruf starten</v-toolbar>
                      <v-card-text>
                        <div class="text-h6 pa-12">Ist das Tier bereits bei Ihnen angekommen?</div>
                        <v-radio-group
                            v-model="requestData.tierBeiKäufer"
                            column
                        >
                          <v-radio
                              label="Ja"
                              v-bind:value="true"
                          ></v-radio>
                          <v-radio
                              label="Nein"
                              v-bind:value="false"
                          ></v-radio>
                        </v-radio-group>
                      </v-card-text>
                      <v-card-actions class="justify-end">

                        <v-btn
                            text
                            @click="starteWiderruf(item.kaufID), dialog.value = false"
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
  name: "Meine Käufe",
  components: {},
  data: () => ({
    iconWiderruf: "mdi-alert-outline",
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
      axios({ method: "POST", "url": this.$apiUrl + "/kaeufe/widerruf", "data": this.requestData, "headers": { "content-type": "application/json", "Authorization": sessionStorage.getItem("accessToken") } })
          .then(result => {
            this.response = result.data;
            console.log(this.response)
            if (this.response.includes("Widerruf eingeleitet")) {
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