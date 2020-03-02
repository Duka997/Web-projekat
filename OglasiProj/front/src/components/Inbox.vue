<template>
  <div class="container d-flex justify-content-center">
    <div class="row">
      <div class="col">
        <h1 class="text-center mb-5" style="color: #4da6ff">Inbox</h1>
        <div class="row">
          <div class="col">
            <div
              class="card mb-2"
              :class="{'border-primary': !message.procitana}"
              style="width: 50rem;"
              v-for="(message, index) in messages"
              :key="message"
              @click="readMessage(message, index)"
            >
              <div class="card-header" :class="{'text-primary': !message.procitana}">
                {{message.naslov}} &nbsp;&nbsp;
                <small>
                  ({{message.posiljalac}}
                  <span
                    v-if="message.nazivOglasa!==''"
                  >- {{message.nazivOglasa}}</span>)
                </small>
                <small>{{message.vremePoruke}}</small>
                <button
                  type="button"
                  class="close"
                  data-dismiss="modal"
                  aria-label="Close"
                  @click="deleteMessage(message, index)"
                >
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="card-body">
                <p>{{message.sadrzaj}}</p>

                <button
                  type="button"
                  class="btn btn-outline-info btn-sm ml-5"
                  @click="replayMessage(message)"
                  v-if="message.posiljalac !== 'Oglasi'"
                >Odgovori</button>
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
  computed: {
    messages() {
      var messages = [];
      this.$store.state.user.poruke.forEach(message => {
        if (message.aktivan) {
          messages.unshift(message);
        }
      });

      return messages;
    }
  },
  methods: {
    readMessage(message, index) {
      if (!message.procitana) {
          axios
        .post("rest/message/read", index)
        .then(() => {
          message.procitana = true;
        })
        .catch(error => {
          console.log(error);
        });
      }
    },
    deleteMessage(message, index) {
      axios
        .post("rest/message/delete", index)
        .then(() => {
          message.aktivan = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    replayMessage(message) {
      this.$router.push(
        "/sendMessage/" + message.posiljalac + "/" + message.nazivOglasa
      );
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
