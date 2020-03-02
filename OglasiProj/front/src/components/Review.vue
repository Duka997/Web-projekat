<template>
  <div class="list-group-item mt-3">
    <div class="row">
      <div class="col-8">
        <h6>
          {{review.naslov}} &nbsp;
          <small>({{review.recenzent}})</small>
        </h6>
      </div>
      <div class="col-4" v-if="review.recenzent === this.$store.state.user.korisnickoIme">
        <button
          type="button"
          class="btn btn-outline-primary btn-sm"
          data-toggle="modal"
          data-target="#izmeniRecenzijuModal"
        >Izmeni</button>
        <button type="button" class="close" aria-label="Close" @click="deleteReview">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </div>
    <div class="row">
      <div class="col-7">
        <p>{{review.sadrzaj}}</p>
      </div>
      <div class="col-5">
        <img :src="review.slika" alt="..." class="img-thumbnail rounded float-right" />
      </div>
    </div>
    <span>Oglas {{(review.tacan)? 'je': 'nije'}} tacan</span>
    <span class="ml-5">Dogovor {{(review.ispostovanDogovor)? 'je': 'nije'}} ispostovan</span>

    <div
      class="modal fade"
      id="izmeniRecenzijuModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Izmena recenzije</h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
              @click="editReview"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col">
                <div class="row">
                  <div class="col-5">
                    <div class="form-group">
                      <label>Naslov</label>
                      <input type="text" class="form-control" v-model="review.naslov" disabled />
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
                      <input
                        style="display: none"
                        type="file"
                        @change="onFileSelected"
                        ref="imageInput"
                      />
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
                        <img
                          :src="review.slika"
                          alt="..."
                          class="img-thumbnail rounded float-right"
                        />
                      </div>
                    </div>
                  </div>
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
  props: ["review", "index", "user"],
  data(){
    return {
      //
      slectedFile: null
      //
    }
  },
  methods: {
    //
    onFileSelected(event) {
      this.slectedFile = event.target.files[0];
      this.review.slika = URL.createObjectURL(this.slectedFile);
    }, //
    deleteReview() {
      if (this.user === true) {
        axios
          .post(
            "rest/oglas/izbrisiRecenzijuProdavcu/" +
              this.$store.state.seller.korisnickoIme,
            this.review
          )
          .then(() => {
            this.review.aktivan = false;
          })
          .catch(error => {
            console.log(error);
          });
      } else {
        axios
          .post("rest/oglas/izbrisiRecenziju", this.review)
          .then(() => {
            this.review.aktivan = false;
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    editReview() {
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
            "rest/oglas/izmeniRecenzijuProdavcu/" +
              this.$store.state.seller.korisnickoIme,
            data
          )
          .then(() => {})
          .catch(error => {
            console.log(error);
          });
      } else {
        axios
          .post("rest/oglas/izmeniRecenziju", data)
          .then(() => {})
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
</style>
