<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글수정</h3></b-alert>
      </b-col>
    </b-row>
    <board-input-item type="modify"></board-input-item>
  </b-container>
</template>

<script>
import BoardInputItem from "./item/BoardInputItem.vue";
export default {
  name: "BoardModify",
  components: { BoardInputItem },
};
</script>

<style scoped></style>
