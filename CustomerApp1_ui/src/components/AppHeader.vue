<script setup lang="ts">
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useAuth } from '../stores/auth';

const router = useRouter();
const route = useRoute();
const { isAuthenticated, logout, isAdmin } = useAuth();

const isCreatePage = computed(() => route.name === 'create');
const isMyAppsPage = computed(() => route.name === 'my-apps');
const isGalleryPage = computed(() => route.name === 'gallery');
const isSettingsPage = computed(() => route.name === 'settings');
const isDeploymentServersPage = computed(() => route.name === 'deployment-servers');
	const isStocksPage = computed(() => route.name === 'stocks');
			
const navigateToSignIn = () => {
  router.push({ name: 'signin' });
};

	const navigateToStocks = () => {
  router.push({ name: 'stocks' });
};
			
const handleSignOut = () => {
  logout();
  router.push({ name: 'landing' });
};
</script>

<template>
  <header class="app-header">
    <div class="header-container">
      <div class="logo-section">
        <div class="logo">
          <div class="logo-content" @click="router.push({ name: 'Dashboard' })">
            <div class="title">
              <span>Customerapp1</span>
            </div>
            <div class="subtitle">
              <span>powered by 60secondApps</span>
            </div>
          </div>
        </div>
      </div>
      
      <nav class="nav-section">
      </nav>
      
      <div class="actions-section">
        <template v-if="isAuthenticated">


	          <button @click="navigateToStocks" class="btn-secondary"
            :class="{ active: isStocksPage }"
          >
            Stocks
          </button>
			
          <div class="user-dropdown">
            <div class="user-avatar">
              60
            </div>
            <div class="user-dropdown-menu">
              <button @click="handleSignOut" class="dropdown-item">
                Sign Out
              </button>
            </div>
          </div>
        </template>
        <template v-else>

	          <button @click="navigateToStocks" class="btn-secondary"
            :class="{ active: isStocksPage }"
          >
            Stocks
          </button>
			          <button @click="navigateToSignIn" class="btn-primary">
            Sign In
          </button>
        </template>
      </div>
    </div>
  </header>
</template>

<style scoped>
.app-header {
  background: var(--color-bg-medium);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 300;
  width: 100%;
}

.header-container {
  max-width: 98%;
  margin: 0 auto;
  padding: 0 24px 0 0;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-section {
  flex-shrink: 0;
  min-width: 240px;
  padding-left: 0;
  margin-left: 0;
}

.logo {
  display: flex;
  align-items: center;
  cursor: pointer;
  text-decoration: none;
}

.logo-content {
  cursor: pointer;
  transition: opacity 0.2s ease;
}

.logo-content:hover {
  opacity: 0.8;
}

.title {
  color: var(--color-text-primary);
  text-align: center;
  font-weight: 500;
  font-size: 2.375rem;
  line-height: 1.1;
  font-style: italic;
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0;
}

.subtitle {
  color: var(--color-text-primary);
  text-align: center;
  font-weight: 400;
  font-size: 1rem;
  line-height: 1.5rem;
  font-style: italic;
  margin: 0;
}

.logo-text {
  font-weight: 700;
  color: var(--color-text-primary);
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  cursor: pointer;
  transition: opacity 0.2s ease;
}

.logo-text:hover {
  opacity: 0.8;
}

.nav-section {
  display: flex;
  align-items: center;
  gap: 32px;
  flex: 1;
  justify-content: center;
}

.nav-link {
  color: var(--color-text-secondary);
  text-decoration: none;
  font-weight: 500;
  font-size: 0.95rem;
  transition: color 0.2s ease;
  position: relative;
}

.nav-link:hover {
  color: var(--color-primary);
}

.nav-link::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 0;
  height: 2px;
  background: var(--color-primary);
  transition: width 0.2s ease;
}

.nav-link:hover::after {
  width: 100%;
}

.actions-section {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-shrink: 0;
  min-width: 280px;
  justify-content: flex-end;
}

.btn-secondary {
  background: transparent;
  border: 1px solid var(--color-border-dark);
  color: var(--color-text-primary);
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 500;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-secondary:hover {
  border-color: var(--color-text-secondary);
  background: rgba(255, 255, 255, 0.05);
  color: var(--color-text-primary);
}

.btn-secondary.active {
  border-color: var(--color-primary);
  background: rgba(52, 152, 219, 0.1);
  color: var(--color-primary);
}

.admin-dropdown {
  position: relative;
}

.dropdown-trigger {
  background: transparent;
  border: 1px solid var(--color-border-dark);
  color: var(--color-text-primary);
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 500;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.dropdown-trigger:hover {
  border-color: var(--color-text-secondary);
  background: rgba(255, 255, 255, 0.05);
  color: var(--color-text-primary);
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background: var(--color-bg-light);
  border: 1px solid var(--color-border-medium);
  border-radius: 8px;
  padding: 8px 0;
  min-width: 180px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.3);
  opacity: 0;
  visibility: hidden;
  transform: translateY(-8px);
  transition: all 0.2s ease;
  z-index: 1000;
}

.admin-dropdown:hover .dropdown-menu {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

.dropdown-item {
  width: 100%;
  background: transparent;
  border: none;
  color: var(--color-text-primary);
  padding: 12px 20px;
  text-align: left;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 0.9rem;
  font-weight: 500;
}

.dropdown-item:hover {
  background: rgba(52, 152, 219, 0.1);
  color: var(--color-primary);
}

.dropdown-item.active {
  background: rgba(52, 152, 219, 0.2);
  color: var(--color-primary);
}

.user-dropdown {
  position: relative;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  color: var(--color-text-primary);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 2px 8px rgba(52, 152, 219, 0.3);
}

.user-avatar:hover {
  background: linear-gradient(135deg, var(--color-primary-dark), var(--color-primary));
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.4);
}

.user-dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background: var(--color-bg-light);
  border: 1px solid var(--color-border-medium);
  border-radius: 8px;
  padding: 8px 0;
  min-width: 140px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.3);
  opacity: 0;
  visibility: hidden;
  transform: translateY(-8px);
  transition: all 0.2s ease;
  z-index: 1000;
  margin-top: 8px;
}

.user-dropdown:hover .user-dropdown-menu {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

.user-dropdown .dropdown-item {
  width: 100%;
  background: transparent;
  border: none;
  color: var(--color-text-primary);
  padding: 12px 20px;
  text-align: left;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 0.9rem;
  font-weight: 500;
}

.user-dropdown .dropdown-item:hover {
  background: rgba(52, 152, 219, 0.1);
  color: var(--color-primary);
}

.btn-primary {
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  border: none;
  color: var(--color-text-primary);
  padding: 12px 24px;
  border-radius: 8px;
  font-weight: 600;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 2px 8px rgba(52, 152, 219, 0.3);
}

.btn-primary:hover {
  background: linear-gradient(135deg, var(--color-primary-dark), var(--color-primary));
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.4);
}

@media (max-width: 768px) {
  .header-container {
    padding: 0 16px;
    height: 64px;
  }
  
  .nav-section {
    display: none;
  }
  
  .logo-text {
    font-size: 1.25rem;
  }
  
  .actions-section {
    gap: 12px;
  }
  
  .btn-secondary,
  .btn-primary {
    padding: 8px 16px;
    font-size: 0.85rem;
  }
  
  .admin-dropdown {
    display: none;
  }
}
</style>
