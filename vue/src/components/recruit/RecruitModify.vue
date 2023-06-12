<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글수정</h3></b-alert>
      </b-col>
    </b-row>
    <recruit-input-item type="modify"></recruit-input-item>
  </b-container>
</template>

<script>
import RecruitInputItem from "./item/RecruitInputItem.vue";
export default {
  name: "RecruitModify",
  components: { RecruitInputItem },
};
</script>

<style scoped></style>
