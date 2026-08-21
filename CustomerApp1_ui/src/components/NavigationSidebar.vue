<script setup lang="ts">
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

// Default navigation items (can be extended or overridden)
const defaultNavigationItems = [
  {
    name: 'Dashboard',
    icon: '📊',
    route: 'Dashboard',
    path: '/dashboard'
  },
  // Module navigation items will be added here dynamically
];

// Get module navigation items from configuration or store
const moduleNavigationItems = [
  {
    name: 'Stocks',
    icon: '💳',
    route: 'Stocks',
    path: '/stocks'
  },
  {
    name: 'Traders',
    icon: '💳',
    route: 'Traders',
    path: '/traders'
  },
  {
    name: 'Transactions',
    icon: '💳',
    route: 'Transactions',
    path: '/transactions'
  },
];

// Combine default and module navigation items
const navigationItems = computed(() => [
  ...defaultNavigationItems,
  ...moduleNavigationItems
]);

const isActiveRoute = (routeName: string) => {
  if (!routeName) return false;
  return route.name === routeName || 
         route.name?.toString().startsWith(routeName) ||
         route.path === `/${routeName.toLowerCase()}`;
};

const navigateTo = (path: string) => {
  router.push(path);
};
</script>

<template>
  <nav class="navigation-sidebar">
    <div class="nav-header">
      <h2 class="nav-title">Navigation</h2>
    </div>
    
    <div class="nav-items">
      <button
        v-for="item in navigationItems"
        :key="item.route"
        @click="navigateTo(item.path)"
        :class="['nav-item', { active: isActiveRoute(item.route) }]"
        :title="item.name"
      >
        <span class="nav-icon" aria-hidden="true">{{ item.icon }}</span>
        <span class="nav-label">{{ item.name }}</span>
      </button>
    </div>
  </nav>
</template>

<style scoped>
.navigation-sidebar {
  width: 240px;
  height: 100vh;
  background: var(--color-bg-medium);
  border-right: 1px solid var(--color-border-light);
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 100;
  padding-top: 80px;
  transition: transform 0.3s ease;
}

.nav-header {
  padding: 24px 20px 16px;
  border-bottom: 1px solid var(--color-border-light);
}

.nav-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
  color: var(--color-text-primary);
}

.nav-items {
  flex: 1;
  padding: 16px 12px;
  display: flex;
  flex-direction: column;
  gap: 4px;
  overflow-y: auto;
}

.nav-item {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 10px 12px;
  border-radius: 6px;
  background: transparent;
  border: none;
  color: var(--color-text-secondary);
  cursor: pointer;
  text-align: left;
  transition: all 0.2s ease;
}

.nav-item:hover {
  background: var(--color-bg-light);
  color: var(--color-text-primary);
}

.nav-item.active {
  background: var(--color-primary);
  color: white;
  font-weight: 500;
}

.nav-icon {
  font-size: 1.25rem;
  margin-right: 12px;
  width: 24px;
  text-align: center;
}

.nav-label {
  font-size: 0.9375rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Responsive styles */
@media (max-width: 1024px) {
  .navigation-sidebar {
    transform: translateX(-100%);
  }
  
  .navigation-sidebar.open {
    transform: translateX(0);
  }
}

/* Scrollbar styles */
.nav-items::-webkit-scrollbar {
  width: 6px;
}

.nav-items::-webkit-scrollbar-track {
  background: var(--color-bg-medium);
}

.nav-items::-webkit-scrollbar-thumb {
  background-color: var(--color-border-medium);
  border-radius: 3px;
}

.nav-items::-webkit-scrollbar-thumb:hover {
  background-color: var(--color-border-dark);
}
</style>
