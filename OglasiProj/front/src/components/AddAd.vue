<template>
  <div class="container d-flex justify-content-center">
    <div class="row">
      <div class="col-xs-12">
        <h2 class="mb-4 text-center">Dodaj oglas</h2>
        <div class="form-group">
          <label>Naziv</label>
          <input type="text" class="form-control" placeholder="Unesite naziv" v-model="ad.naziv" />
        </div>
        <div class="form-group">
          <label>Kategorija</label>
          <select class="form-control" v-model="kategorija">
            <option v-for="kategorija in categories" :key="kategorija.naziv">{{kategorija.naziv}}</option>
          </select>
        </div>
        <div class="form-group">
          <label>
            Cena
            <small>(Din)</small>
          </label>
          <input type="number" class="form-control" v-model="ad.cena" />
        </div>
        <div class="form-group">
          <label>Opis</label>
          <textarea class="form-control" placeholder="Unesite opis" rows="3" v-model="ad.opis"></textarea>
        </div>
        <div class="form-group">
          <label>Grad</label>
          <input type="text" placeholder="Unesite grad" class="form-control" v-model="ad.grad" />
        </div>
        <div class="form-group">
          <label>Slika</label>

          <input style="display: none" type="file" @change="onFileSelected" ref="imageInput" />
          <br />
          <button
            type="button"
            class="btn btn-primary"
            @click="$refs.imageInput.click()"
          >Izaberi sliku</button>
          <!-- -->
        </div>
        <div class="form-group text-center">
          <img :src="ad.slika" alt="..." class="img-thumbnail rounded" />
        </div>
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary" @click="addAd">Dodaj</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      ad: {
        naziv: "",
        cena: 0,
        opis: "",
        slika: "",
        grad: ""
      },
      kategorija: "",
      //
      slectedFile: null
      //
    };
  },
  computed: {
    categories() {
      var kategorije = [];
      this.$store.state.categories.forEach(category => {
        if (category.naziv !== "Svi Oglasi") {
          kategorije.push(category);
        }
      });

      return kategorije;
    }
  },
  methods: {
    //
    onFileSelected(event) {
      this.slectedFile = event.target.files[0];
      this.ad.slika = URL.createObjectURL(this.slectedFile);
    }, //
    addAd() {
      //
      let data = new FormData();
      let file = this.slectedFile;

      data.append("oglas", JSON.stringify(this.ad));
      data.append("fajl", file);
      data.append("slika", file.name);
      //
      axios
        .post("rest/oglas/addOglas/" + this.kategorija, data)
        .then(ad => {
          this.$store.state.ads.push(ad.data);
          this.$store.getters
            .findCategory(this.kategorija)
            .oglasi.push(ad.data.naziv);
          this.$store.state.user.objavljeniOglasi.push(ad.data.naziv);
          this.$router.push("/");
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
.container {
  width: 30%;
  height: auto;
  padding: 2%;
}

img.img-thumbnail {
  height: 8em;
}
</style>
