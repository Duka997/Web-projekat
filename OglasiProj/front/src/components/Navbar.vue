<template>
  <nav class="navbar navbar-expand-lg sticky-top navbar-light mb-4" style="background-color: #b30000; margin: 1% 1% 1% 1%; border-radius: 15px">
    <div class="container">
      <router-link to="/" class="navbar-brand">Početna</router-link>
      <!--<input type="text" placeholder="Pretraga.." style="border-radius: 25px">-->
      <ul class="navbar-nav mr-0">
        <template v-if="this.$store.getters.isUserLogged">
          <template v-if="this.$store.state.user.uloga === 'Kupac'">
          <li>
            <router-link
              id="navbarItem"
              to="/orderedAds"
              active-class="active"
              class="dropdown-item"

            ><img src="/oglasi/Images/korpa.png" class="img-fluid img-thumbnail" width="40" height="20" alt="Poruceni prozvodi" title="Poruceni oglasi"/></router-link>
          </li>
          <li>
            <router-link
              id="navbarItem"
              to="/deliveredAds"
              active-class="active"
              class="dropdown-item"
            ><img src="/oglasi/Images/dostava.png" class="img-fluid img-thumbnail" width="40" height="40" alt="Dostavljeni prozvodi" title="Dostavljeni oglasi"/></router-link>
          </li>
          <li><router-link
            id="navbarItem"
            to="/favouriteAds"
            active-class="active"
            class="dropdown-item"
          ><img src="/oglasi/Images/srce.png" class="img-fluid img-thumbnail" width="40" height="40" alt="Omiljeni prozvodi" title="Omiljeni oglasi"/></router-link></li>
          </template>
          <template v-else-if="this.$store.state.user.uloga === 'Prodavac'">
            <router-link
              to="/addAd"
              active-class="active"
              class="dropdown-item"
            >
              <img src="/oglasi/Images/plus.png" class="img-fluid img-thumbnail" width="40" height="40" alt="Dodaj oglas" title="Dodaj oglas"/>


            </router-link>
            <router-link to="/userAds" active-class="active" class="dropdown-item">
              <img src="/oglasi/Images/oglasi.png" class="img-fluid img-thumbnail" width="40" height="40" alt="Tvoji oglasi" title="Tvoji oglasi"/>


            </router-link>
            <router-link
              :to="'/userProfile/'+this.$store.state.user.korisnickoIme"
              active-class="active"
              class="dropdown-item"
            ><img src="/oglasi/Images/profil.png" class="img-fluid img-thumbnail" width="40" height="40" alt="Profil" title="Profil"/>
            </router-link>
          </template>

          <template v-else>
            <router-link
              to="/adminCategories"
              active-class="active"
              class="dropdown-item"
            ><img src="/oglasi/Images/dodaj.png" class="img-fluid img-thumbnail" width="40" height="40" alt="Kategorije" title="Kategorije"/>

            </router-link>
            <router-link to="/adminUsers" active-class="active" class="dropdown-item">
              <img src="/oglasi/Images/korisnici.png" class="img-fluid img-thumbnail" width="40" height="20" alt="Korisnici" title="Korisnici"/>

            </router-link>
            <router-link to="/adminAds" active-class="active" class="dropdown-item">
              <img src="/oglasi/Images/adminoglasi.png" class="img-fluid img-thumbnail" width="40" height="20" alt="Oglasi" title="Oglasi"/>
            </router-link>
          </template>

          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              {{ this.$store.state.user.ime }} {{ this.$store.state.user.prezime }}
              <span class="badge badge-info" v-if="unreadMessages!==0">{{unreadMessages}}</span>
            </a>

            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <template v-if="this.$store.state.user.uloga === 'Kupac'">


        </template>
              <template v-else-if="this.$store.state.user.uloga === 'Prodavac'">

              </template>
              <template v-else>

              </template>
              <div class="dropdown-divider"></div>
              <router-link
                to="/sendMessage"
                active-class="active"
                class="dropdown-item"
              >Posalji poruku</router-link>
              <router-link to="/inbox" active-class="active" class="dropdown-item">
                Inbox
                <span class="badge badge-info" v-if="unreadMessages!==0">{{unreadMessages}}</span>
              </router-link>
              <div class="dropdown-divider"></div>
              <button id="logout" class="dropdown-item" @click="logout">Odjavi se</button>
            </div>
          </li>
        </template>
        <template v-else>
          <router-link to="/register" tag="li" class="nav-item">
            <button class="btn btn-outline-light my-0 mr-2">Registruj se</button>
          </router-link>
          <router-link to="/login" tag="li" class="nav-item">
            <button class="btn btn-outline-light my-0">Uloguj se</button>
          </router-link>
        </template>

      </ul>
    </div>
  </nav>
</template>

<script>
import axios from "axios";

export default {

  computed: {
    unreadMessages() {
      var unreadMessagesTemp = 0;

      this.$store.state.user.poruke.forEach(message => {
        if (!message.procitana) {
          unreadMessagesTemp++;
        }
      });

      return unreadMessagesTemp;
    }
  },
  methods: {
    logout() {
      axios
        .post("rest/checkIn/logout")
        .then(() => {
          this.$store.state.user = {};
          this.$router.push("/");
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
#navbarDropdown {
  color: white;
}
#navbarItem {
  color: white;
}

.dropdown-menu .active {
  background-color: #343a40;
  color: white;
  font-weight: bold;
}

.dropdown-menu a:hover {
  background-color: #e0a800;
  color: white;
  font-weight: bold;
}

.dropdown-menu button:hover {
  background-color: rgb(247, 64, 57);
  color: white;
  font-weight: bold;
}

.dropdown-divider + a:hover,
.dropdown-divider + .dropdown-item + a:hover {
  background-color: #118dc7;
  color: white;
  font-weight: bold;
}
</style>
