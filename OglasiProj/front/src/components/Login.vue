<template>
  <div class="container d-flex justify-content-center">
    <div class="row">
      <div class="col-xs-12">
        <h2 class="mb-4 text-center">Logovanje</h2>
        <div class="form-group">
          <label>Korisničko ime:</label>
          <input type="text" class="form-control" placeholder="Korisničko ime" v-model="user.korisnickoIme" />
        </div>
        <div class="form-group">
          <label>Šifra:</label>
          <input type="password" class="form-control" placeholder="Šifra" v-model="user.lozinka" />
        </div>
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary" @click="login">Uloguj se</button>
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
      user: {
        korisnickoIme: "",
        lozinka: ""
      }
    };
  },
  methods: {
    login() {
      axios.post("rest/checkIn/login", this.user).then(user => {
        this.$store.state.user = user.data;

        this.$router.push("/");
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
</style>
