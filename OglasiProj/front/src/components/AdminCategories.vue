<template>
  <div class="container">
    <h1 class="text-center">
      <strong>Kategorije</strong>
    </h1>
    <div class="row justify-content-center mt-5" id="nova" style="border-radius: 25px">
      <div class="col-4">
        <h3 class="text-center">Dodaj kategoriju</h3>
        <div class="form-group">
          <label>Naziv:</label>
          <input type="text" placeholder="Naziv kategorije" class="form-control" v-model="category.naziv" />
        </div>
        <div class="form-group">
          <label>Opis:</label>
          <textarea class="form-control" placeholder="Opis kategorije" rows="3" v-model="category.opis"></textarea>
        </div>
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary mb-1" @click="addCategory">Dodaj</button>
        </div>
      </div>
    </div>


    <!--  -->
    <h3 class="text-center mb-4 mt-4">Postojeće kategorije</h3>
    <div class="row">
      <div class="col-3 mt-5 card list-group-item mb-2 mr-6" style="border-radius: 25px"
          v-for="category in categories"
          :key="category.naziv"
        >
          <div class="row">
            <div class="col">
              <button type="button" class="close" aria-label="Close" @click="deleteCategory(category)">
                <span aria-hidden="true">&times;</span>
              </button>
              <div class="card-body">
                <h5 class="card-title">{{category.naziv}}</h5>
                <p class="card-text">{{category.opis}}</p>
                <div class="d-flex justify-content-center">
                  <button
                    type="button"
                    class="btn btn-outline-primary btn-sm"
                    data-toggle="modal"
                    data-target="#izmeniKategorijuModal"
                    @click="activeCategory = category"
                  >Izmeni</button>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>

    <!-- -->
    <div
      class="modal fade"
      id="izmeniKategorijuModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Izmeni kategoriju</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="editCategory">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="row justify-content-center">
              <div class="col-5">
                <div class="form-group">
                  <label>Naziv</label>
                  <input type="text" class="form-control" v-model="activeCategory.naziv" disabled/>
                </div>
                <div class="form-group">
                  <label>Opis</label>
                  <textarea class="form-control" rows="3" v-model="activeCategory.opis"></textarea>
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
      category: {
        naziv: "",
        opis: "",
        oglasi: [],
        aktivan: true
      },
      activeCategory: {}
    };
  },
  computed: {
    categories(){
      var categories = [];
      this.$store.state.categories.forEach(category => {
        if (category.aktivan === true) {
          categories.push(category);
        }
      });
      return categories;
    }
  },
  methods: {
    addCategory() {
      axios
        .post("rest/kategorija/dodajKategoriju", this.category)
        .then(category => {
          this.$store.state.categories.push(category.data);
          this.category.naziv = "";
          this.category.opis = "";
        })
        .catch(error => {
          console.log(error);
        });
    },
    deleteCategory(category){
      axios
        .post("rest/kategorija/izbrisiKategoriju", category)
        .then(() => {
          category.aktivan = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    editCategory() {
      axios
        .post("rest/kategorija/izmeniKategoriju", this.activeCategory)
        .then(() => {})
        .catch(error => {
          console.log(error);
        });
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
h1 {
  color: #4da6ff;
}

 #nova{
   border: 2px solid #4da6ff;
 }
</style>
