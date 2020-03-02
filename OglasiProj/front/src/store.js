import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    seller: {
      /*// za sve Korisnike
      korisnickoIme: "dado22",
      lozinka: "123",
      ime: "Damjan",
      prezime: "Pantic",
      uloga: "Prodavac",
      kontaktTelefon: "065/5504-205",
      grad: "Novi Sad",
      emailAdresa: "panticdamjan@gmail.com",
      datumRegistracije: "19/8/2019",
      poruke: [],
      // samo za Kupca
      poruceniOglasi: [],
      dostavljeniOglasi: [],
      omiljeniOglasi: [],
      // samo za Prodavca
      objavljeniOglasi: ["Oglas 1"],
      isporuceniOglasi: [],
      uRealizacijiOglasi: [],
      like: [],
      dislike: [],
      brojPrijava: [],
      recenzije: []*/
    },
    user: {
      /*// za sve Korisnike
      korisnickoIme: "dado",
      lozinka: "123",
      ime: "Damjan",
      prezime: "Pantic",
      uloga: "Kupac",
      kontaktTelefon: "065/5504-205",
      grad: "Novi Sad",
      emailAdresa: "panticdamjan@gmail.com",
      datumRegistracije: "19/8/2019",
      poruke: [],
      // samo za Kupca
      poruceniOglasi: [],
      dostavljeniOglasi: ['Oglas 1'],
      omiljeniOglasi: [],
      // samo za Prodavca
      objavljeniOglasi: [],
      isporuceniOglasi: [],
      uRealizacijiOglasi: [],
      like: [],
      dislike: [],
      brojPrijava: [],
      recenzije: []*/
    },
    categories: [
      /*{ naziv: 'Svi Oglasi', opis: 'Opis za kategoriju svi oglasi', oglasi: [], aktivan: true },
      { naziv: 'Kategorija 1', opis: 'Opis za kategoriju 1', oglasi: ['Oglas 1', 'Oglas 2', 'Oglas 3'], aktivan: true },
      { naziv: 'Kategorija 2', opis: 'Opis za kategoriju 2', oglasi: ['Oglas 4', 'Oglas 5', 'Oglas 6', 'Oglas 7'], aktivan: true },
      { naziv: 'Kategorija 3', opis: 'Opis za kategoriju 3', oglasi: ['Oglas 8', 'Oglas 9'], aktivan: true },
      { naziv: 'Kategorija 4', opis: 'Opis za kategoriju 4', oglasi: [], aktivan: true },*/
    ],
    ads: [
      /*{
        naziv: 'Oglas 1',
        cena: 1001,
        opis: 'Opis za oglas 1',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '01/01/2011',
        datumIsticanja: '01/01/2012',
        aktivan: true,
        recenzije: [],
        grad: 'Novi Sad'
      },
      {
        naziv: 'Oglas 2',
        cena: 1002,
        opis: 'Opis za oglas 2',
        like: [],
        dislike: [],
        slika: 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Vue.js_Logo_2.svg/1920px-Vue.js_Logo_2.svg.png',
        datumPostavljanja: '02/02/2012',
        datumIsticanja: '02/02/2013',
        aktivan: true,
        recenzije: [
          {
            oglas: 'Oglas 2',
            recenzent: 'dado',
            naslov: "Naslov 1",
            sadrzaj: "Ovde sad kao nesto pise",
            slika: "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Vue.js_Logo_2.svg/1920px-Vue.js_Logo_2.svg.png",
            tacan: true,
            ispostovanDogovor: true,
            aktivan: true
          },
          {
            oglas: 'Oglas 2',
            recenzent: 'dado22',
            naslov: "Naslov 2",
            sadrzaj: "Ode pise isto nesto malo duze od predhodnog",
            slika: "",
            tacan: true,
            ispostovanDogovor: true,
            aktivan: true
          }
        ],
        grad: 'Novi Sad'
      },
      {
        naziv: 'Oglas 3',
        cena: 1003,
        opis: 'Opis za oglas 3',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '03/03/2013',
        datumIsticanja: '03/03/2014',
        aktivan: true,
        recenzije: [],
        grad: 'Subotica'
      },
      {
        naziv: 'Oglas 4',
        cena: 1004,
        opis: 'Opis za oglas 4',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '04/04/2014',
        datumIsticanja: '04/04/2015',
        aktivan: true,
        recenzije: [],
        grad: 'Novi Sad'
      },
      {
        naziv: 'Oglas 5',
        cena: 1005,
        opis: 'Opis za oglas 5',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '05/05/2015',
        datumIsticanja: '05/05/2016',
        aktivan: true,
        recenzije: [],
        grad: 'Beograd'
      },
      {
        naziv: 'Oglas 6',
        cena: 1006,
        opis: 'Opis za oglas 6',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '06/06/2015',
        datumIsticanja: '06/06/2017',
        aktivan: true,
        recenzije: [],
        grad: 'Valjevo'
      },
      {
        naziv: 'Oglas 7',
        cena: 1007,
        opis: 'Opis za oglas 7',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '07/07/2017',
        datumIsticanja: '07/07/2018',
        aktivan: true,
        recenzije: [],
        grad: 'Nis'
      },
      {
        naziv: 'Oglas 8',
        cena: 1008,
        opis: 'Opis za oglas 8',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '08/08/2018',
        datumIsticanja: '08/08/2019',
        aktivan: true,
        recenzije: [],
        grad: 'Novi Sad'
      },
      {
        naziv: 'Oglas 9',
        cena: 1009,
        opis: 'Opis za oglas 9',
        like: [],
        dislike: [],
        slika: 'C:\Users\damjanpantic\Desktop\ra7-2016',
        datumPostavljanja: '09/09/2019',
        datumIsticanja: '09/09/2020',
        aktivan: true,
        recenzije: [],
        grad: 'Beograd'
      }*/
    ]
  },
  getters: {
    isUserLogged(state) {
      return Object.keys(state.user).length !== 0
    },
    findCategory: (state) => (categoryName) => {
      var activeCategory = {};

      for (const category of state.categories) {
        if (category.naziv === categoryName) {
          activeCategory = category;
          break;
        }
      }
      return activeCategory;
    },
    findAd: (state) => (adName) => {
      var activeAd = {};

      for (const ad of state.ads) {
        if (ad.naziv === adName) {
          activeAd = ad;
          break;
        }
      }
      return activeAd;
    }
  },
  mutations: {

  },
  actions: {

  }
})
