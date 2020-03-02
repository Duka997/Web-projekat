<template>
  <div class="container">

    <div>
      <div>
        <ul class="navbar-nav mr-0">
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >Oglasi
            </a>
            <div class="dropdown-menu pull-right" >
              <button
                class="dropdown-item nav-link"
                :class="{activeCategory: activeFilter === 'Postavljeni oglasi'}"
                @click="activeFilter = 'Postavljeni oglasi'"
              >Postavljeni oglasi</button>
              <button
                class="dropdown-item nav-link"
                :class="{activeCategory: activeFilter === 'Oglasi u procesu'}"
                @click="activeFilter = 'Oglasi u procesu'"
              >Oglasi u procesu</button>
              <button
                class="dropdown-item nav-link"
                :class="{activeCategory: activeFilter === 'Isporuceni oglasi'}"
                @click="activeFilter = 'Isporuceni oglasi'"
              >Isporuceni oglasi</button>
            </div>
          </li>
        </ul>
      </div>
    </div>


    <div class="container justify-content-center mt-4">
      <div class="row mt-2">
        <div class="col">
          <div class="list-group" style="width: 70%">
            <div class="card list-group-item mb-2" style="border-radius: 25px" v-for="(ad, index) in ads" :key="ad.naziv">
              <div class="row">
                <div class="col-3 d-flex align-items-center">
                  <img :src="ad.slika" alt="..." class="img-thumbnail rounded float-left" />
                </div>
                <div class="col-8">
                  <button
                    type="button"
                    class="close"
                    aria-label="Close"
                    @click.stop="deleteAd(ad)"
                    v-if="activeFilter === 'Postavljeni oglasi'"
                  >
                    <span aria-hidden="true">&times;</span>
                  </button>
                  <div class="card-body">
                    <h5 class="card-title">
                      {{ad.naziv}}
                      <small
                        v-if="activeFilter !== 'Postavljeni oglasi'"
                      >({{ byers[index] }})</small>
                    </h5>
                    <p class="card-text">{{ad.opis}}</p>
                    <template v-if="activeFilter === 'Postavljeni oglasi'">
                      <button
                        type="button"
                        class="btn btn-outline-primary btn-sm"
                        data-toggle="modal"
                        data-target="#izmeniOglasModal"
                        @click="activeAd = ad"
                      >Izmeni</button>
                      <button
                        class="btn btn-primary btn-sm"
                        @click="openAd(ad.naziv)"
                      >Otvori</button>
                    </template>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div
      class="modal fade"
      id="izmeniOglasModal"
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
      byers: [],
      activeAd: {},
      activeFilter: "Postavljeni oglasi",
      //
      slectedFile: null
      //
    };
  },
  computed: {
    ads() {
      var ads = [];
      var adsString = [];
      if (this.activeFilter === "Postavljeni oglasi") {
        adsString = this.$store.state.user.objavljeniOglasi;
      } else if (this.activeFilter === "Oglasi u procesu") {
        this.$store.state.user.uRealizacijiOglasi.forEach(adName => {
          adsString.push(adName.split("/")[1]);
          this.byers.push(adName.split("/")[0]);
        });
      } else {
        this.$store.state.user.isporuceniOglasi.forEach(adName => {
          adsString.push(adName.split("/")[1]);
          this.byers.push(adName.split("/")[0]);
        });
      }

      adsString.forEach(adName => {
        var adTemp = this.$store.getters.findAd(adName);
        if (adTemp.aktivan) {
          ads.push(adTemp);
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
    openAd(adNaziv) {
      this.$router.push("/oglas/" + adNaziv);
    },
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
    }
  }
};
</script>

<style scoped>
nav button {
  background-color: #e0a800;
  color: white;
  font-weight: bold;
  margin-left: 0.1em;
}

button:hover {
  background-color: white;
  color: black;
}

button.activeCategory {
  color: #343a40;
  border-width: 2px;
}

button.activeCategory:hover {
  color: white;
}

.container.justify-content-center {
  width: 70%;
  height: auto;
  padding: 2%;
}
</style>
