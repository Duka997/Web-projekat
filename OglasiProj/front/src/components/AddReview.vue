<template>
  <div class="row">
    <div class="col">
      <div class="row">
        <div class="col">
          <h3>Dodaj recenziju</h3>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <div class="row">
            <div class="col-5">
              <div class="form-group">
                <label>Naslov</label>
                <input type="text" class="form-control" v-model="review.naslov" />
              </div>
              <div class="form-group">
                <label>Sadrzaj</label>
                <textarea class="form-control" rows="3" v-model="review.sadrzaj"></textarea>
              </div>
            </div>
            <div class="col-7">
              <div class="form-group">
                <label>Slika</label>
                <!--<input type="text" class="form-control" v-model="review.slika" />-->
                <!-- -->
                <input style="display: none" type="file" @change="onFileSelected" ref="imageInput" />
                <br />
                <button
                  type="button"
                  class="btn btn-outline-primary"
                  @click="$refs.imageInput.click()"
                >Izaberi sliku</button>
                <!-- -->
              </div>
              <div class="row">
                <div class="col-7 mt-4">
                  <div class="form-check">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      id="tacan"
                      v-model="review.tacan"
                    />
                    <label class="form-check-label" for="tacan">Oglas tacan</label>
                  </div>
                  <div class="form-check mt-2">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      id="dogovor"
                      v-model="review.ispostovanDogovor"
                    />
                    <label class="form-check-label" for="dogovor">Ispostovan dogovor</label>
                  </div>
                </div>
                <div class="col-5">
                  <img :src="review.slika" alt="..." class="img-thumbnail rounded float-right" />
                </div>
              </div>
            </div>
          </div>
          <div class="row mt-3">
            <div class="col">
              <div class="d-flex justify-content-center">
                <button class="btn btn-primary" @click="addReview">Dodaj</button>
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
  props: ["ad", "user"],
  data() {
    return {
      review: {
        oglas: this.ad.naziv,
        recenzent: this.$store.state.user.korisnickoIme,
        naslov: "",
        sadrzaj: "",
        slika: "",
        tacan: true,
        ispostovanDogovor: true,
        aktivan: true
      },
      //
      slectedFile: null
      //
    };
  },
  methods: {
    //
    onFileSelected(event) {
      this.slectedFile = event.target.files[0];
      this.review.slika = URL.createObjectURL(this.slectedFile);
    }, //
    addReview() {
      //
      let data = new FormData();
      let file = this.slectedFile;

      data.append("recenzija", JSON.stringify(this.review));
      data.append("fajl", file);
      data.append("slika", file.name);
      //
      if (this.user === true) {
        axios
          .post(
            "rest/oglas/dodajRecenzijuProdavcu/" +
              this.$store.state.seller.korisnickoIme,
            data
          )
          .then((putanjaSlike) => {
            this.review.slika = putanjaSlike.data;
            this.$store.state.seller.recenzije.push(this.review);
          })
          .catch(error => {
            console.log(error);
          });
      } else {
        axios
          .post("rest/oglas/dodajRecenziju", data)
          .then(putanjaSlike => {
            this.review.slika = putanjaSlike.data;
            this.ad.recenzije.push(this.review);
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }
};
</script>

<style scoped>
img.img-thumbnail {
  height: 8em;
}

h3 {
  color: #4da6ff;
}
</style>
