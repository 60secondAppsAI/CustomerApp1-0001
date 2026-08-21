<script setup lang="ts">
import { RouterView } from 'vue-router';
import NavigationSidebar from './components/NavigationSidebar.vue';
</script>

<template>
  <div id="app">
    <div class="app-layout">
      <NavigationSidebar />
      <div class="main-content">
        <RouterView />
      </div>
    </div>
  </div>
</template>

<style>
:root {
  --color-primary: #3498db;
  --color-primary-dark: #2980b9;
  --color-bg-dark: #282c34;
  --color-bg-medium: #353b48;
  --color-bg-light: #3e4451;
  --color-text-primary: #d3d7de;
  --color-text-secondary: rgba(211, 215, 222, 0.8);
  --color-text-muted: rgba(211, 215, 222, 0.7);
  --color-text-placeholder: rgba(211, 215, 222, 0.5);
  --color-border-light: rgba(211, 215, 222, 0.1);
  --color-border-medium: rgba(211, 215, 222, 0.2);
  --color-border-dark: rgba(211, 215, 222, 0.3);
  --color-success: #27ae60;
  --color-warning: #f39c12;
  --color-error: #e74c3c;
  --color-muted: #95a5a6;
}

* {
  box-sizing: border-box;
}

#app {
  font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  line-height: 1.5;
}

body {
  margin: 0;
  padding: 0;
  background: var(--color-bg-dark);
}

/* Custom scrollbar styles */
::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-track {
  background: var(--color-bg-medium);
}

::-webkit-scrollbar-thumb {
  background: var(--color-primary);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: var(--color-primary-dark);
}

.app-layout {
  display: flex;
  min-height: 100vh;
}

.main-content {
  flex: 1;
  margin-left: 240px;
  min-height: 100vh;
  padding-top: 80px;
}

@media (max-width: 768px) {
  .main-content {
    margin-left: 0;
  }
}
</style>