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
          <v-card-text>
            <p>
              Sed aliquam ultrices mauris. Donec posuere vulputate arcu. Morbi ac felis. Etiam feugiat lorem non metus. Sed a libero.
            </p>

            <p>
              Nam ipsum risus, rutrum vitae, vestibulum eu, molestie vel, lacus. Aenean tellus metus, bibendum sed, posuere ac, mattis non, nunc. Aliquam lobortis. Aliquam lobortis. Suspendisse non nisl sit amet velit hendrerit rutrum.
            </p>

            <p class="mb-0">
              Phasellus dolor. Fusce neque. Fusce fermentum odio nec arcu. Pellentesque libero tortor, tincidunt et, tincidunt eget, semper nec, quam. Phasellus blandit leo ut odio.
            </p>
            <p>
              Text text
            </p>
            <p>
              test test
            </p>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
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
                <v-col>
                  <v-btn
                      style="margin-right: 40%"
                      @click="redirectUser(item.inseratID)"
                  >
                    <v-icon>{{iconChange}}</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <p>
              Fusce a quam. Phasellus nec sem in justo pellentesque facilisis. Nam eget dui. Proin viverra, ligula sit amet ultrices semper, ligula arcu tristique sapien, a accumsan nisi mauris ac eros. In dui magna, posuere eget, vestibulum et, tempor auctor, justo.
            </p>

            <p class="mb-0">
              Cras sagittis. Phasellus nec sem in justo pellentesque facilisis. Proin sapien ipsum, porta a, auctor quis, euismod ut, mi. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nam at tortor in tellus interdum sagittis.
            </p>
          </v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-card>
</template>

<script>
export default {
  name: "AdminSpace.vue",
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
    axios({method: "GET", "url": this.$apiUrl + "/inserate", "headers": { "content-type": "application/json" } }).then(response => {
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
  },
};
</script>

<style lang="scss"></style>