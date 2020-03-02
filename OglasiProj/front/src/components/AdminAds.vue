<template>
  <div class="container mt-4">
    <h1 class="text-center">Oglasi</h1>
    <div class="row mt-5">
      <div class="col-4 mt-5 card list-group-item mb-2 mr-8"
           style="border-radius: 25px"
           v-for="ad in ads"
           :key="ad.naziv">
            <div class="row">
              <div class="col-3 d-flex align-items-center">
                <img :src="ad.slika" alt="..." class="img-thumbnail rounded float-left" />
              </div>
              <div class="col-8">
                <button type="button" class="close" aria-label="Close" @click.stop="deleteAd(ad)">
                  <span aria-hidden="true">&times;</span>
                </button>
                <div class="card-body">
                  <h5 class="card-title">{{ad.naziv}}</h5>
                  <p class="card-text">{{ad.opis}}</p>
                  <button
                    type="button"
                    class="btn btn-outline-primary btn-sm"
                    data-toggle="modal"
                    data-target="#izmeniOglasAdminModal"
                    @click="izmeniClick(ad)"
                  >Izmeni</button>
                </div>
              </div>
            </div>
          </div>
    </div>

    <!-- Modal -->
    <div
      class="modal fade"
      id="izmeniOglasAdminModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Izmeni oglas</h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
              @click="editAd"
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
                      <label>Naziv</label>
                      <input type="text" class="form-control" v-model="activeAd.naziv" disabled />
                    </div>
                    <div class="form-group">
                      <label>Opis</label>
                      <textarea class="form-control" rows="3" v-model="activeAd.opis"></textarea>
                    </div>
                  </div>
                  <div class="col-7">
                    <div class="form-group">
                      <label>Cena</label>
                      <input type="number" class="form-control" v-model="activeAd.cena" />
                    </div>
                    <div class="form-group">
                      <label>Grad</label>
                      <input type="text" class="form-control" v-model="activeAd.grad" />
                    </div>
                    <div class="form-group">
                      <label>Slika</label>
                      <!--<input type="text" class="form-control" v-model="activeAd.slika" />-->
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
                      <div class="col-5">
                        <img
                          :src="activeAd.slika"
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
  data() {
    return {
      activeAd: {},
      //
      slectedFile: null
      //
    };
  },
  computed: {
    ads() {
      var ads = [];

      this.$store.state.ads.forEach(ad => {
        if (ad.aktivan) {
          ads.push(ad);
        }
      });

      return ads;
    }
  },
  methods: {
    //
    onFileSelected(event) {
      this.slectedFile = event.target.files[0];
      this.activeAd.slika = URL.createObjectURL(this.slectedFile);
    }, //
    deleteAd(ad) {
      axios
        .post("rest/oglas/deleteOglas", ad)
        .then(() => {
          ad.aktivan = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    editAd() {
      //
      let data = new FormData();
      let file = this.slectedFile;

      data.append("oglas", JSON.stringify(this.activeAd));
      data.append("fajl", file);
      data.append("slika", file.name);
      //
      axios
        .post("rest/oglas/editOglas", data)
        .then(() => {})
        .catch(error => {
          console.log(error);
        });
    },
    izmeniClick(ad) {
      this.activeAd = ad;
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
h1 {
  color: #4da6ff;
}

</style>
