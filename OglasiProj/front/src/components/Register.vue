<template>
  <div class="container d-flex justify-content-center">
    <div class="row">
      <div class="col-xs-12">
        <h2 class="mb-4 text-center">Registracija</h2>
        <div class="form-group">
          <label>Korisničko ime:</label>
          <input type="text" placeholder="Unesite korisničko ime" class="form-control" v-model="user.korisnickoIme" />
        </div>
        <div class="form-group">
          <label>Šifra:</label>
          <input type="password" placeholder="Unesite šifru" class="form-control" v-model="user.lozinka" />
        </div>
        <div class="form-group">
          <label>Ime:</label>
          <input type="text" placeholder="Unesite ime" class="form-control" v-model="user.ime" />
        </div>
        <div class="form-group">
          <label>Prezime:</label>
          <input type="text" placeholder="Unesite prezime" class="form-control" v-model="user.prezime" />
        </div>
        <div class="form-group">
          <label>Telefon:</label>
          <input type="text" placeholder="Unesite broj telefona" class="form-control" v-model="user.kontaktTelefon" />
        </div>
        <div class="form-group">
          <label>Grad:</label>
          <input type="text" placeholder="Unesite grad" class="form-control" v-model="user.grad" />
        </div>
        <div class="form-group">
          <label>Email:</label>
          <input type="email" placeholder="Unesite e-mail" class="form-control" v-model="user.emailAdresa" />
        </div>
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary" @click="register">Registruj se</button>
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
        lozinka: "",
        ime: "",
        prezime: "",
        kontaktTelefon: "",
        grad: "",
        emailAdresa: ""
      }
    };
  },
  methods: {
    register() {
      axios.post("rest/checkIn/register", this.user).then(user => {
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
