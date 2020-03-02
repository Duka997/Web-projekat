<template>
  <button
    class="list-group-item list-group-item-action"
    @click="changeActiveCategory(categoryName)"
    :class="{activeCategory: activeCategory === categoryName}"
  >{{categoryName}}</button>
</template>

<script>
export default {
  props: ["categoryName", "activeCategory", "changeActiveCategory"]
};
</script>

<style scoped>
button {
  background-color: #4da6ff;
  color: white;
  font-weight: bold;
}

button:hover {
  background-color: #343a40;
  color: white;
}

button.activeCategory {
  color: #343a40;
  border-color: #343a40;
  border-width: 2px;
  margin-bottom: 5px;
  margin-top: 5px;
}

button.activeCategory:hover {
  color: white;
}
</style>
