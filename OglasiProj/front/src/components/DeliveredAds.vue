<template>
  <div class="container justify-content-center">
    <h1 class="text-center" style="color: #4da6ff">
      <strong>Dostavljeni proizvodi</strong>
    </h1>
    <div class="row mt-5" style="width: 70%; margin-left: 20%">
      <div class="col">
        <div class="list-group">
          <div
            id="oglas"
            style="border-radius: 25px"
            class="card list-group-item list-group-item-action mb-2"
            v-for="ad in deliveredAds"
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
export default {
  methods: {
    openAd(adNaziv) {
      this.$router.push("/oglas/" + adNaziv);
    }
  },
  computed: {
    deliveredAds() {
      var ads = [];

      var dostavljeniOglasi = this.$store.state.user.dostavljeniOglasi;
      dostavljeniOglasi.forEach(adName => {
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
