<template>
  <div class="container justify-content-center">
    <h1 class="text-center">
      <strong>Korisnici</strong>
    </h1>
    <div class="row mt-5">
      <div class="col-3 mt-5 card list-group-item mb-2 mr-8"
           style="border-radius: 25px"
           v-for="user in users" :key="user.korisnickoIme">
          <div class="row">
            <div class="col">
              <div class="card-body">
                <h5 class="card-title text-center">{{user.korisnickoIme}}</h5>
                <h6 class="text-center">
                  <small>({{user.ime}} {{user.prezime}})</small>
                </h6>
                <label>Kategorija</label>
                <select
                  class="form-control form-control-sm"
                  v-model="user.uloga"
                  @change="promeniUlogu(user)"
                >
                  <option>Kupac</option>
                  <option>Prodavac</option>
                  <option>Administrator</option>
                </select>

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
      users: []
    };
  },
  methods: {
    promeniUlogu(user) {
      axios
        .post("rest/user/promeniUlogu", user)
        .then(() => {

        })
        .catch(error => {
          console.log(error);
        });
    },

  },
  mounted() {
    axios
      .get("rest/user/allUsers")
      .then(users => {
        this.users = users.data;
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

h3 {
  color: #4da6ff;
}
h1 {
  color: #4da6ff;
}

</style>
