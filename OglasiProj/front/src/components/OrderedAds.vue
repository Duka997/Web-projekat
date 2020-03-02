<template>
  <div class="container justify-content-center">
    <h1 class="text-center" style="color: #4da6ff">
      <strong>Poruceni proizvodi</strong>
    </h1>
    <div class="row mt-5" style="width: 70%; margin-left: 20%">
      <div class="col">
        <div class="list-group">
          <div
            id="oglas"
            class="card list-group-item list-group-item-action mb-2"
            style="border-radius: 25px"
            v-for="(ad, index) in orderedAds"
            :key="ad.naziv"
            @click="openAd(ad.naziv)"
          >
            <div class="row">
              <div class="col-3 d-flex align-items-center">
                <img :src="ad.slika" alt="..." class="img-thumbnail rounded float-left" />
              </div>
              <div class="col-8">
                <div class="card-body">
                  <h5 class="card-title">{{ad.naziv}}</h5>
                  <p class="card-text">{{ad.opis}}</p>
                  <button
                    type="button"
                    class="btn btn-outline-primary"
                    @click.stop="dostavljeno(ad.naziv, index)"
                  >Dostavljeno</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  methods: {
    openAd(adNaziv) {
      this.$router.push("/oglas/" + adNaziv);
    },
    dostavljeno(adNaziv, adIndex) {
      axios
        .post("rest/oglas/oglasDostavljen", adNaziv)
        .then(() => {
          this.$store.state.user.poruceniOglasi.splice(adIndex, 1);
          this.$store.state.user.dostavljeniOglasi.push(adNaziv);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  computed: {
    orderedAds() {
      var ads = [];

      var poruceniOglasi = this.$store.state.user.poruceniOglasi;
      poruceniOglasi.forEach(adName => {
        ads.push(this.$store.getters.findAd(adName));
      });
      return ads;
    }
  }
};
</script>

<style scoped>
.container {
  width: 70%;
  height: auto;
  padding: 2%;
}

img.img-thumbnail {
  height: 8em;
}

#oglas:hover{
  border-color: #4da6ff;
  background-color: #b3e6ff;
}
</style>
