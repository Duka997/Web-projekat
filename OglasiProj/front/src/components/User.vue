<template>
  <div class="container justify-content-center">
    <div class="row">
      <div class="col-sm-4">
        <h1 class="text-center">
          <strong>{{seller.korisnickoIme}}</strong>
        </h1>
        <br>
        <div class="row justify-content-center mt-4">
          <h4>
            <span class="text-danger">Broj prijava: {{seller.brojPrijava.length}}</span>
          </h4>
        </div>
        <div class="row justify-content-center">
          <div class="row mt-2" v-if="isUserAllow && !alredyLiked">
            <div class="col">
              <button class="btn btn-outline-success" @click="like">
                Like
                <span class="text-success">{{seller.like.length}}</span>
              </button>
              &nbsp; &nbsp;
              <button class="btn btn-outline-danger" @click="dislike">
                Dislike
                <span class="text-danger">{{seller.dislike.length}}</span>
              </button>
            </div>
          </div>
          <div class="row mt-2" v-else>
            <div class="col">
              <span class="text-success">Like: {{seller.like.length}}</span>
              &nbsp; &nbsp;
              <span class="text-danger">Dislike: {{seller.dislike.length}}</span>
            </div>
          </div>
        </div>
      </div>
      <hr />
      <div class="row justify-content-center mt-5">
        <div class="col">
          <app-add-review class="mb-4" :ad="ad" :user='true' v-if="isAllowToReview && !alredyReviewed"></app-add-review>
        </div>
      </div>
      <div class="col-sm-8"><div class="row justify-content-center mt-5">
        <div class="col text-center">
          <h3>Recenzije</h3>
          <div class="list-group">
            <app-review
              v-for="(review, index) in reviews"
              :key="review.naslov"
              :review="review"
              :index="index"
              :user='true'
            ></app-review>
          </div>
        </div>
      </div></div>
    </div>




  </div>
</template>

<script>
import AddReview from "./AddReview";
import Review from "./Review";

import axios from "axios";

export default {
  methods: {
    like() {
      axios
        .post("rest/oglas/likeSeller", this.seller.korisnickoIme)
        .then(() => {
          this.seller.like.push(this.$store.state.user.korisnickoIme);
        })
        .catch(error => {
          console.log(error);
        });
    },
    dislike() {
      axios
        .post("rest/oglas/dislikeSeller", this.seller.korisnickoIme)
        .then(() => {
          this.seller.dislike.push(this.$store.state.user.korisnickoIme);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  computed: {

    seller() {
      return this.$store.state.seller;
    },
    isUserAllow() {
      if (this.$store.getters.isUserLogged) {
        return this.$store.state.user.dostavljeniOglasi.includes(this.ad.naziv);
      }
    return false;
    },
    ad() {
      return this.$store.getters.findAd(
        unescape(this.$route.params.nazivOglasa)
      );
    },
    reviews() {
      var reviews = [];

      this.$store.state.seller.recenzije.forEach(review => {
        if (review.aktivan) {
          reviews.push(review);
        }
      });
      return reviews;
    },
    alredyLiked() {
      return (
        this.seller.like.includes(this.$store.state.user.korisnickoIme) ||
        this.seller.dislike.includes(this.$store.state.user.korisnickoIme)
      );
    },
    isAllowToReview() {
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

    alredyReviewed() {
      for (const review of this.$store.state.seller.recenzije) {
        if (
          review.recenzent === this.$store.state.user.korisnickoIme &&
          review.oglas === this.ad.naziv &&
          review.aktivan === true
        ) {
          return true;
        }
      }
      return false;
    },

  },
  components: {
    appAddReview: AddReview,
    appReview: Review
  },
  mounted() {
    if (this.$store.state.user.uloga === "Prodavac") {
      this.$store.state.seller = this.$store.state.user;
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

h3 {
  color: #4da6ff;
}

h3 strong {
  color: #343a40;
}
</style>
