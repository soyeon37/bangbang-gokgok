<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>
    <recruit-input-item type="register"></recruit-input-item>
  </b-container>
</template>

<script>
import RecruitInputItem from "./item/RecruitInputItem.vue";
export default {
  name: "RecruitWrite",
  components: { RecruitInputItem },
};
</script>

<style scoped></style>
