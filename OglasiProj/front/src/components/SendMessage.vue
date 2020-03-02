<template>
  <div class="container d-flex justify-content-center">
    <div class="col">
      <div class="row mb-4">
        <div class="col" style="color: #4da6ff">
          <h1 class="text-center" >Pošalji poruku</h1>
        </div>
      </div>
      <div class="row justify-content-center" style="width: 70%; margin-left: 20%">
        <div class="col-8">
          <div class="form-group">
            <label>Kome šaljete:</label>
            <select class="form-control" v-model="sendTo" @change="getAds()">
              <option v-for="user in users" :key="user.korisnickoIme" :value="user.korisnickoIme">
                {{user.korisnickoIme}}
                <small>({{user.uloga}})</small>
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>Naziv oglas:</label>
            <select class="form-control" v-model="message.nazivOglasa">
              <option v-for="ad in adsName" :key="ad">{{ad}}</option>
            </select>
          </div>
          <div class="form-group">
            <label>Naslov:</label>
            <input type="text" class="form-control" v-model="message.naslov" />
          </div>
          <div class="form-group">
            <label>Sadržaj poruke:</label>
            <textarea type="text" class="form-control" v-model="message.sadrzaj" rows="5" />
          </div>
          <div class="d-flex justify-content-center">
            <button class="btn btn-info" @click="posaljiPoruku">Pošalji</button>
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
      message: {
        nazivOglasa: "",
        posiljalac: this.$store.state.user.korisnickoIme,
        naslov: "",
        sadrzaj: ""
      },
      sendTo: "",
      users: [],
      adsName: []
    };
  },
  methods: {
    getAds() {
      axios
        .get("rest/message/usersAds/" + this.sendTo)
        .then(ads => {
          this.adsName = ads.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    posaljiPoruku() {
      axios
        .post("rest/message/sendMessage/" + this.sendTo, this.message)
        .then(() => {
          this.message.nazivOglasa = "";
          this.message.naslov = "";
          this.message.sadrzaj = "";
          this.sendTo = "";
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  mounted() {
    if (this.$route.params.korisnickoIme === undefined) {
      axios
        .get("rest/message/users")
        .then(users => {
          this.users = users.data;
        })
        .catch(error => {
          console.log(error);
        });
    } else {
      axios
        .get("rest/message/user/" + this.$route.params.korisnickoIme)
        .then(user => {
          this.sendTo = user.data.korisnickoIme;
          this.users = [];
          this.users.push(user.data);
        })
        .catch(error => {
          console.log(error);
        });
      if (this.$route.params.oglas !== undefined) {
        this.message.nazivOglasa = this.$route.params.oglas;
        this.adsName.push(this.$route.params.oglas);
      }
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
</style>
