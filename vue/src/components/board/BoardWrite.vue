<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>
    <board-input-item type="register"></board-input-item>
  </b-container>
</template>

<script>
import BoardInputItem from "./item/BoardInputItem.vue";
export default {
  name: "BoardWrite",
  components: { BoardInputItem },
};
</script>

<style scoped></style>
