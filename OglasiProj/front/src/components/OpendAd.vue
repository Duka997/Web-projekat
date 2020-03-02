<template>
  <div class="container justify-content-center">


    <div class="row">
      <div class="col">
        <h1 class="text-left">
          <strong>{{ ad.naziv }}</strong>
        </h1>
        <h5 class="text" @click="openAutor">{{ this.$store.state.seller.korisnickoIme }}</h5>
        <div class="row justify-content-center mt-2" v-if="isUserAllowToReport && !alredyReported">
          <button type="button" class="btn btn-danger btn-sm" @click="reportSeller">
            Prijavi korisnika
            <span
              class="text-danger"
            >{{this.$store.state.seller.brojPrijava.length}}</span>
          </button>
        </div>
        <div class="row justify-content ml-1 mt-2" v-else>
          <h6>
            <span class="text-danger">Broj prijava: {{this.$store.state.seller.brojPrijava.length}}</span>
          </h6>
        </div>
      </div>
      <div class="col">
        <h3>
          Cena:
          <strong>{{ad.cena}} Din</strong>
        </h3>
      </div>
      <div class="col">
        <div class="row">
          <div class="col">
            <div class="row">
              <h6>
                Datum postavljanja:
                <br />
                <strong>{{ad.datumPostavljanja}}</strong>
              </h6>
            </div>
            <div class="row">
              <h6>
                Datum isticanja:
                <br />
                <strong>{{ad.datumIsticanja}}</strong>
              </h6>
            </div>
            <div class="row">
              <h6>
                Grad:
                <strong>{{ad.grad}}</strong>
              </h6>
            </div>
          </div>
        </div>
      </div>
    </div>



    <div class="row mt-5">
      <div class="col-4">
        <img :src="ad.slika" alt="..." />
      </div>
      <div class="col-7 mt-5">
        <h3>Opis</h3>
        <p class="mt-3">{{ad.opis}}</p>
      </div>
    </div>
    <div class="row mt-5">
      <div class="col-4">
        <div
          class="row ml-1 mt-2"
          v-if="this.$store.getters.isUserLogged && isAdOrderd && this.$store.state.user.uloga === 'Kupac'"
        >
          <button class="Korisnik btn btn-outline-primary btn-sm mr-4" @click="order">Poruci</button>
          <button
            class="Korisnik btn btn-primary btn-sm"
            v-if="!alredyFavourite"
            @click="addToFavourite"
          >Omiljeni</button>
        </div>


        <div class="row mt-4 ml-1" v-if="isUserAllow && !alredyLiked">
          <button class="btn btn-outline-success" @click="like">
            Like
            <span class="text-success">{{ad.like.length}}</span>
          </button>
          &nbsp; &nbsp;
          <button class="btn btn-outline-danger" @click="dislike">
            Dislike
            <span class="text-danger">{{ad.dislike.length}}</span>
          </button>
        </div>
        <div class="row mt-3 ml-1" v-else>
          <span class="text-success">Like: {{ad.like.length}}</span>
          &nbsp; &nbsp;
          <span class="text-danger">Dislike: {{ad.dislike.length}}</span>
        </div>
      </div>
      <div class="col-7 ml-3 justify-content-center">
        <app-add-review class="mb-4" :ad="ad" :user='false' v-if="isUserAllow && !alredyReviewed"></app-add-review>
        <div class="row">
          <div class="col">
            <h3>Recenzije</h3>
            <div class="list-group">
              <app-review
                v-for="(review, index) in reviews"
                :key="review.naslov"
                :review="review"
                :index="index"
                :user='false'
              ></app-review>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AddReview from "./AddReview";
import Review from "./Review";

import axios from "axios";

export default {
  methods: {
    reportSeller() {
      axios
        .post(
          "rest/oglas/reportSeller",
          this.$store.state.seller.korisnickoIme + "/" + this.ad.naziv
        )
        .then(() => {
          this.$store.state.seller.brojPrijava.push(
            this.$store.state.user.korisnickoIme + "/" + this.ad.naziv
          );
        })
        .catch(error => {
          console.log(error);
        });
    },
    openAutor() {
      this.$router.push(
        "/userProfile/" + this.$store.state.seller.korisnickoIme +'/'+this.ad.naziv
      );
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
    addToFavourite() {
      axios
        .post("rest/oglas/dodajOmiljeniOglas", this.ad.naziv)
        .then(() => {
          this.$store.state.user.omiljeniOglasi.push(this.ad.naziv);
        })
        .catch(error => {
          console.log(error);
        });
    },
    like() {
      axios
        .post("rest/oglas/likeOglas", this.ad.naziv)
        .then(() => {
          this.ad.like.push(this.$store.state.user.korisnickoIme);
        })
        .catch(error => {
          console.log(error);
        });
    },
    dislike() {
      axios
        .post("rest/oglas/dislikeOglas", this.ad.naziv)
        .then(() => {
          this.ad.dislike.push(this.$store.state.user.korisnickoIme);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  computed: {
    ad() {
      return this.$store.getters.findAd(unescape(this.$route.params.naziv));
    },
    reviews() {
      var reviews = [];

      this.ad.recenzije.forEach(review => {
        if (review.aktivan) {
          reviews.push(review);
        }
      });
      return reviews;
    },
    isUserAllow() {
      if (this.$store.getters.isUserLogged) {
        return this.$store.state.user.dostavljeniOglasi.includes(this.ad.naziv);
      }
      return false;
    },
    isUserAllowToReport() {
      if (this.$store.getters.isUserLogged) {
        if (this.$store.state.user.uloga === "Kupac") {
          if (
            this.$store.state.user.dostavljeniOglasi.includes(this.ad.naziv)
          ) {
            for (const oglas of this.$store.state.user.dostavljeniOglasi) {
              if (this.$store.state.seller.objavljeniOglasi.includes(oglas)) {
                return true;
              }
            }
          }
        }
      }
      return false;
    },
    alredyReported() {
      return this.$store.state.seller.brojPrijava.includes(
        this.$store.state.user.korisnickoIme + "/" + this.ad.naziv
      );
    },
    alredyReviewed() {
      for (const review of this.ad.recenzije) {
        if (
          review.recenzent === this.$store.state.user.korisnickoIme &&
          review.aktivan === true
        ) {
          return true;
        }
      }
      return false;
    },
    isAdOrderd() {
      if (this.$store.getters.isUserLogged) {
        if (
          this.$store.state.user.dostavljeniOglasi.includes(this.ad.naziv) ||
          this.$store.state.user.poruceniOglasi.includes(this.ad.naziv)
        ) {
          return false;
        } else {
          return true;
        }
      }

      return false;
    },
    alredyFavourite() {
      return this.$store.state.user.omiljeniOglasi.includes(this.ad.naziv);
    },
    alredyLiked() {
      return (
        this.ad.like.includes(this.$store.state.user.korisnickoIme) ||
        this.ad.dislike.includes(this.$store.state.user.korisnickoIme)
      );
    }
  },
  components: {
    appAddReview: AddReview,
    appReview: Review
  },
  mounted() {
    axios
      .post("rest/oglas/getSeller", "" + this.ad.naziv)
      .then(seller => {
        this.$store.state.seller = seller.data;
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>

<style scoped>
.container {
  width: 70%;
  height: auto;
  padding: 2%;
}

.col-4 img {
  height: 15em;
  width: 15em;
  border: 2px solid #343a40;
  padding: 1em;
}

h3 {
  color: #4da6ff;
}

h3 strong {
  color: #343a40;
}

h5 {
  color: #4da6ff;
  cursor: pointer;
}
</style>
