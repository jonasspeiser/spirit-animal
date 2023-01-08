<template>
  <APItest></APItest>
</template>

<script lang="ts">
import Vue from 'vue'
import APItest from '@/components/APItest.vue'

export default Vue.extend({
  name: 'Home',

  components: {
    APItest,
  },
})
</script>