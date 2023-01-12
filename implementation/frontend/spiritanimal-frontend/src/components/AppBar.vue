<template>
  <v-app-bar
      app
      color="primary"
      dark
  >
    <v-btn
        to="/"
        text
    >
      <v-img
          alt="Spirit Animal Logo"
          class="shrink mr-2"
          contain
          src="https://static.vecteezy.com/system/resources/previews/001/990/204/original/love-my-dog-dog-pet-friendship-care-concept-pet-lover-best-friend-human-and-animal-friendship-concept-cartoon-cute-woman-hug-dog-free-vector.jpg"
          transition="scale-transition"
          width="40"
      />

      <span class="mr-2">Spirit Animal</span>
      <!--        <v-icon>mdi-open-in-new</v-icon>-->

      <!--        <v-img-->
      <!--          alt="Vuetify Name"-->
      <!--          class="shrink mt-1 hidden-sm-and-down"-->
      <!--          contain-->
      <!--          min-width="100"-->
      <!--          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"-->
      <!--          width="100"-->
      <!--        />-->
    </v-btn>

    <v-spacer></v-spacer>

    <v-btn
        to="/neuesinserat"
        text
    >
      <v-icon>mdi-folder</v-icon>
      <span class="mr-2">Inserieren</span>
      <!--        <v-icon>mdi-open-in-new</v-icon>-->
    </v-btn>

    <v-btn
        to="/soulsearch"
        text
    >
      <v-icon>mdi-account-multiple</v-icon>
      <span class="mr-2">Soul Search</span>
      <!--        <v-icon>mdi-open-in-new</v-icon>-->
    </v-btn>

    <v-btn
        to="/mydarlings"
        text
    >
      <v-icon>mdi-star</v-icon>
      <span class="mr-2">My Darlings</span>
      <!--        <v-icon>mdi-open-in-new</v-icon>-->
    </v-btn>

    <v-btn
        to="/inserate"
        text
    >
      <v-icon>mdi-dog</v-icon>
      <span class="mr-2">Meine Inserate</span>
      <!--        <v-icon>mdi-open-in-new</v-icon>-->
    </v-btn>

    <v-spacer></v-spacer>

    <v-btn
        to="/profil"
        text
    >
      <span class="mr-2">Mein Profil</span>
      <!--        <v-icon>mdi-open-in-new</v-icon>-->
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  //
}
</script>