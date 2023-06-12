<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글수정</h3></b-alert>
      </b-col>
    </b-row>
    <notice-input-item type="modify"></notice-input-item>
  </b-container>
</template>

<script>
import NoticeInputItem from "./item/NoticeInputItem.vue";
export default {
  name: "NoticeModify",
  components: { NoticeInputItem },
};
</script>

<style scoped></style>
