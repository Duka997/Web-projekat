<template>
  <div class="card mb-3" style="max-width: 700px; border-radius: 25px " @click="openAd">
    <div class="row no-gutters">
      <div class="col-md-4" style="margin-top: 2%">
      <img :src="ad.slika" class="card-img" alt="Slika" />
      </div>
      <div class="col-md-8">
      <div class="card-body">
        <h5 class="card-title">
          <strong>{{ ad.naziv }}</strong>
        </h5>
        <p class="card-text">{{ ad.opis }}</p>
        <p>
          <strong>Cena: {{ad.cena}}</strong>
        </p>
        <p>
          <span class="text-success">Like: {{ad.like.length}}</span>
          &nbsp;
          <span class="text-danger">Dislike: {{ad.dislike.length}}</span>
        </p>
        <template v-if="this.$store.getters.isUserLogged && this.$store.state.user.uloga === 'Kupac'">
          <button class="Korisnik btn btn-outline-primary btn-sm" @click.stop="order">Poruči</button>
          <button class="Korisnik btn btn-primary btn-sm" v-if="!alredyFavourite" @click.stop="addToFavourite">Omiljeni</button>
        </template>
      </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["ad"],
  methods: {
    openAd() {
      this.$router.push("/oglas/" + this.ad.naziv);
    },
    order() {
      axios
        .post("rest/oglas/oglasPorucen", this.ad.naziv)
        .then(() => {
          this.$store.state.user.poruceniOglasi.push(this.ad.naziv);
        })
        .catch(error => {
          console.log(error);
        });
    },
    addToFavourite(){
      axios
        .post("rest/oglas/dodajOmiljeniOglas", this.ad.naziv)
        .then(() => {
          this.$store.state.user.omiljeniOglasi.push(this.ad.naziv);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  computed: {
    alredyFavourite(){
      return this.$store.state.user.omiljeniOglasi.includes(this.ad.naziv)
    }
  }
};
</script>

<style scoped>

div.card {
  height: 50%;
  width: 100%;
}

div.card img {
  float: left;
  width: 80%;
  height: 10em;
}

div.card:hover {
  border-color: #4da6ff;
  border-width: 2px;
}
</style>
