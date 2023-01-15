<template>
  <v-card>
    <v-toolbar
        flat
        color="primary"
        dark
    >
      <v-toolbar-title>Admin Space</v-toolbar-title>
    </v-toolbar>
    <v-tabs vertical>
      <v-tab>
        <v-icon left>
          mdi-account
        </v-icon>
        Alle User
      </v-tab>
      <v-tab>
        <v-icon left>
          mdi-dog
        </v-icon>
        Alle Inserate
      </v-tab>
      <v-tab>
        <v-icon left>
          mdi-cart
        </v-icon>
        Alle Käufe
      </v-tab>

      <v-tab-item>
        <v-card flat>
          <v-expansion-panels>
            <v-expansion-panel v-for="(item,i) in user " :key="i">
              <v-expansion-panel-header>
                <v-row>
                  <v-col style="max-width: 80%;  padding-top: 35px">
                    <v-row>
                      {{item.username}}
                    </v-row>
                  </v-col>
                </v-row>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-row style="padding-top: 5px">
                  E-Mail: {{item.email}}
                </v-row>
                <v-row style="padding-top: 5px">
                  Adresse: {{item.addresse}}
                </v-row>
                <v-row style="padding-top: 5px">
                  Zahlungsdaten: {{item.zahlungsdaten}}
                </v-row>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-expansion-panels>
            <v-expansion-panel v-for="(item,i) in animal " :key="i">
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
                    <v-btn style="margin-left: 60%" @click="deleteInserat(item.inseratID)">
                      <v-icon>{{iconDelete}}</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-expansion-panels>
            <v-expansion-panel v-for="(item,i) in purchase " :key="i">
              <v-expansion-panel-header>
                <v-row>
                  <v-col style="max-width: 80%;  padding-top: 35px">
                    <v-row>
                      Kauf-ID: {{item.kaufID}}
                    </v-row>
                  </v-col>
                </v-row>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-row style="padding-top: 5px">
                  Käufer: {{item.käuferUsername}}
                </v-row>
                <v-row style="padding-top: 5px">
                  Verkäufer: {{item.anbieterUsername}}
                </v-row>
                <v-row style="padding-top: 5px">
                  Kaufstatus: {{item.status}}
                </v-row>
                <v-row style="padding-top: 5px">
                  Datum: {{item.kaufdatum}}
                </v-row>
                <v-row style="padding-top: 5px">
                  Inserat: {{item.inserat}}
                </v-row>
                <v-row>
                  <v-col>
                    <v-btn style="margin-left: 60%" @click="deleteKauf(item.kaufID)">
                      <v-icon>{{iconDelete}}</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-card>
</template>

<script>
import axios from "axios"
import Vue from "vue"
export default Vue.extend({
  name: "AdminSpace.vue",
  components: {
    // Dashboard
  },
  data: () => ({
    iconDelete: "mdi-delete",
    iconChange: "mdi-pencil",
    animal: null,
    purchase: null,
    user: null
  }),
  computed: {},
  beforeCreate() {
    const headers = {
      "Content-Type": "application/json",
      "Authorization": sessionStorage.getItem("accessToken")
    }
    axios.get(this.$apiUrl + '/inserate', { headers }).then(response => {
      this.animal = response.data;})
    axios.get(this.$apiUrl + '/users', { headers }).then(response => {
      this.user = response.data;})
    axios.get(this.$apiUrl + '/kaeufe', { headers }).then(response => {
      this.purchase = response.data
    })
  },
  created() {},
  async mounted() {},
  watch: {},
  methods: {
    deleteInserat(inseratID){
      axios.delete(this.$apiUrl + "/inserate/" + inseratID, {headers: {Authorization: sessionStorage.getItem("accessToken")}})
      window.location.reload()
    },
    deleteKauf(kaufID){
      axios.delete(this.$apiUrl + "/kaeufe/" + kaufID, {headers: {Authorization: sessionStorage.getItem("accessToken")}})
      window.location.reload()
    }
  },
});
</script>

<style lang="scss"></style>