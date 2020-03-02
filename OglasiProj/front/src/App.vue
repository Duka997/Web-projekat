<template>
  <div>
    <app-navbar></app-navbar>
    <router-view></router-view>
  </div>
</template>

<script>
import axios from "axios";

import Navbar from "./components/Navbar";

export default {
  components: {
    appNavbar: Navbar
  },
  mounted() {
    axios
      .get("rest/kategorija/allKategorije")
      .then(categories => {
        var sortedCategories = categories.data;
        var svi_oglasi = {};
        for (const categorie of sortedCategories) {
          if (categorie.naziv !== "Svi Oglasi") {
            this.$store.state.categories.push(categorie);
          }else{
            svi_oglasi = categorie;
          }
        }
        this.$store.state.categories.unshift(svi_oglasi);
      })
      .catch(error => {
        console.log(error);
      });

    axios
      .get("rest/oglas/allOglasi")
      .then(ads => {
        this.$store.state.ads = ads.data;
      })
      .catch(error => {
        console.log(error);
      });

    axios
      .get("rest/checkIn/currentUser")
      .then(user => {
        this.$store.state.user = user.data;
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>

<style scoped>
</style>
