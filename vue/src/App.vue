<template>
  <div id="app">
    <nav-component></nav-component>
    <div class="main-content">
      <router-view></router-view>
    </div>
    <div class="footer">
      <div class="footer-content">
        <p>&copy; 2023 방방곡곡. All Rights Reserved.</p>
        <div class="footer-links">
          <a href="#">About Us</a>
          <a href="#">Contact Us</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavComponent from "./components/NavComponent.vue";
export default {
  name: "app",
  components: { NavComponent },
  data() {
    return {
      message: "",
    };
  },
  created() {},
  methods: {},
};
</script>

<style>
html,
body {
  height: 100%;
  margin: 0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  min-height: 100%;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1 0 auto; /* This will make the main content grow to use all available space */
}

.footer {
  flex-shrink: 0; /* This will prevent the footer from shrinking if the window is smaller than the content */
  width: 100%;
  background-color: #f8f9fa;
  color: #212529;
  text-align: center;
  bottom: 0;
  padding: 30px 0;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.footer p {
  margin: 0;
  font-size: 14px;
}

.footer-links {
  display: flex;
}

.footer-links a {
  margin-left: 15px;
  color: #212529;
  text-decoration: none;
}

.footer-links a:hover {
  text-decoration: underline;
}

/* b-navbar {
  padding: 30px;
}

b-navbar-nav a {
  font-weight: bold;
  color: #2c3e50;
}

b-nav-item a.router-link-exact-active {
  color: #42b983;
} */
@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: 700;
  font-style: normal;
}

* {
  font-family: "GmarketSansMedium";
}
</style>
