<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuth } from '../stores/auth';
import AppHeader from './AppHeader.vue';

const router = useRouter();
const { signin } = useAuth();

const username = ref('');
const password = ref('');
const isLoading = ref(false);
const error = ref('');

const handleSignIn = async () => {
  if (!username.value || !password.value) {
    error.value = 'Please enter both username and password';
    return;
  }

  isLoading.value = true;
  error.value = '';

  const result = await signin(username.value, password.value);

  if (result.success) {
    router.push({ name: 'my-apps' });
  } else {
    error.value = result.error || 'Sign in failed';
  }

  isLoading.value = false;
};

const handleKeyPress = (event: KeyboardEvent) => {
  if (event.key === 'Enter') {
    handleSignIn();
  }
};
</script>

<template>
  <div class="sign-in-page">
    <AppHeader />
    
    <main class="sign-in-main">
      <div class="sign-in-container">
        <div class="sign-in-card">
          <div class="sign-in-header">
            <div class="sign-in-icon">🔐</div>
            <h1 class="sign-in-title">Welcome Back</h1>
            <p class="sign-in-subtitle">Sign in to access your applications</p>
          </div>

          <form @submit.prevent="handleSignIn" class="sign-in-form">
            <div class="form-group">
              <label for="username" class="form-label">Username</label>
              <input
                id="username"
                v-model="username"
                type="text"
                class="form-input"
                placeholder="Enter your username"
                :disabled="isLoading"
                @keypress="handleKeyPress"
                required
              />
            </div>

            <div class="form-group">
              <label for="password" class="form-label">Password</label>
              <input
                id="password"
                v-model="password"
                type="password"
                class="form-input"
                placeholder="Enter your password"
                :disabled="isLoading"
                @keypress="handleKeyPress"
                required
              />
            </div>

            <div v-if="error" class="error-message">
              {{ error }}
            </div>

            <button
              type="submit"
              class="sign-in-button"
              :disabled="isLoading"
            >
              <span v-if="isLoading" class="loading-spinner"></span>
              {{ isLoading ? 'Signing In...' : 'Sign In' }}
            </button>
          </form>

          <div class="sign-in-footer">
            <p class="footer-text">
              Don't have an account? 
              <a @click="router.push({ name: 'pricing' })" class="footer-link">Signup</a>
            </p>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.sign-in-page {
  min-height: 100vh;
  background: var(--color-bg-dark);
  color: var(--color-text-primary);
}

.sign-in-main {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 72px);
  padding: 24px;
}

.sign-in-container {
  width: 100%;
  max-width: 420px;
}

.sign-in-card {
  background: var(--color-bg-medium);
  border: 1px solid var(--color-border-light);
  border-radius: 16px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.sign-in-header {
  text-align: center;
  margin-bottom: 32px;
}

.sign-in-icon {
  font-size: 3rem;
  margin-bottom: 16px;
}

.sign-in-title {
  font-size: 1.75rem;
  font-weight: 700;
  margin: 0 0 8px 0;
  color: var(--color-text-primary);
}

.sign-in-subtitle {
  color: var(--color-text-secondary);
  margin: 0;
  font-size: 0.95rem;
}

.sign-in-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-label {
  font-weight: 600;
  color: var(--color-text-primary);
  font-size: 0.9rem;
}

.form-input {
  padding: 14px 16px;
  background: var(--color-bg-light);
  border: 1px solid var(--color-border-medium);
  border-radius: 8px;
  color: var(--color-text-primary);
  font-size: 1rem;
  transition: all 0.2s ease;
}

.form-input:focus {
  outline: none;
  border-color: var(--color-primary);
  background: var(--color-bg-light);
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.form-input:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.form-input::placeholder {
  color: var(--color-text-placeholder);
}

.error-message {
  background: rgba(231, 76, 60, 0.1);
  border: 1px solid rgba(231, 76, 60, 0.3);
  color: var(--color-error);
  padding: 12px 16px;
  border-radius: 8px;
  font-size: 0.9rem;
  text-align: center;
}

.sign-in-button {
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  border: none;
  color: var(--color-text-primary);
  padding: 16px 24px;
  font-size: 1rem;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin-top: 8px;
}

.sign-in-button:hover:not(:disabled) {
  background: linear-gradient(135deg, var(--color-primary-dark), var(--color-primary));
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.4);
}

.sign-in-button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
  transform: none;
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid var(--color-border-dark);
  border-top: 2px solid var(--color-text-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.sign-in-footer {
  margin-top: 24px;
  text-align: center;
}

.footer-text {
  color: var(--color-text-muted);
  margin: 0;
  font-size: 0.9rem;
}

.footer-link {
  color: var(--color-primary);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s ease;
}

.footer-link:hover {
  color: var(--color-primary-dark);
}

@media (max-width: 480px) {
  .sign-in-card {
    padding: 32px 24px;
  }
  
  .sign-in-title {
    font-size: 1.5rem;
  }
}
</style>