<template>
  <div class="row">
    <div class="col-2">

    </div>
    <div class="col-6 ml-4">
      <div class="row mb-4">
        <app-ad v-for="ad in ads" :key="ad.naziv" :ad="ad"></app-ad>
      </div>
    </div>
    <div>
      <div style="margin-left: 50%">
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
          >Kategorije
          </a>
          <div class="dropdown-menu pull-right" >
      <app-category
        v-for="category in categories"
        :key="category.naziv"
        class="dropdown-item"
        :categoryName="category.naziv"
        :activeCategory="activeCategory"
        :changeActiveCategory="changeActiveCategory"
      ></app-category>
          </div>
        </li>
      </ul>
      </div>
    </div>

  </div>
</template>

<script>
import Category from "./Category";
import Ad from "./Ad";

export default {
  data() {
    return {
      activeCategory: "Svi Oglasi"
    };
  },
  computed: {
    categories() {
      var categories = [];
      this.$store.state.categories.forEach(category => {
        if (category.aktivan) {
          categories.push(category);
        }
      });
      return categories;
    },
    ads() {
      var ads = [];
      if (this.activeCategory === "Svi Oglasi") {
        this.$store.state.ads.forEach(ad => {
          if (ad.aktivan) {
            if (this.$store.getters.isUserLogged) {
              if (
                !this.$store.state.user.poruceniOglasi.includes(ad.naziv) &&
                !this.$store.state.user.dostavljeniOglasi.includes(ad.naziv)
              ) {
                ads.push(ad);
              }
            } else {
              ads.push(ad);
            }
          }
        });
      } else {
        var activeCategory = this.$store.getters.findCategory(
          this.activeCategory
        );

        this.$store.state.ads.forEach(ad => {
          if (ad.aktivan && activeCategory.oglasi.includes(ad.naziv)) {
            if (this.$store.getters.isUserLogged) {
              if (
                !this.$store.state.user.poruceniOglasi.includes(ad.naziv) &&
                !this.$store.state.user.dostavljeniOglasi.includes(ad.naziv)
              ) {
                ads.push(ad);
              }
            } else {
              ads.push(ad);
            }
          }
        });
      }
      return ads;
    }
  },
  methods: {
    changeActiveCategory(categoryName) {
      this.activeCategory = categoryName;
    }
  },
  components: {
    appCategory: Category,
    appAd: Ad
  }
};
</script>

<style scoped>


.col-9 {
  margin-left: auto;
  margin-right: 1%;
}
</style>
