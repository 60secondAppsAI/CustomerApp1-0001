<template>
  <div class="checkout-page">
    <AppHeader />
    
    <main class="checkout-content">
      <div class="checkout-container">
        <div class="checkout-header">
          <h1 class="page-title">Complete Your Purchase</h1>
          <p class="page-subtitle">Choose your plan and start building amazing applications</p>
        </div>

        <!-- Loading State -->
        <div v-if="isLoading" class="loading-state">
          <div class="loading-spinner"></div>
          <p class="loading-text">Processing your request...</p>
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="error-state">
          <div class="error-icon">⚠️</div>
          <h3 class="error-title">Checkout Error</h3>
          <p class="error-message">{{ error }}</p>
          <button @click="retryCheckout" class="retry-button">
            Try Again
          </button>
        </div>

        <!-- Checkout Form -->
        <div v-else class="checkout-form">
          <div class="product-section">
            <div class="product-card">
              <div class="product-image">
                <div class="app-icon">🚀</div>
              </div>
              <div class="product-details">
                <h3 class="product-name">{{ selectedPackage?.name || 'Premium Plan' }}</h3>
                <p class="product-description">
                  Full access to all features and unlimited app creation
                </p>
                <div class="product-features">
                  <div class="feature-item" v-for="feature in getSelectedFeatures()" :key="feature">
                    <span class="feature-icon">✓</span>
                    <span class="feature-text">{{ feature }}</span>
                  </div>
                </div>
              </div>
              <div class="product-pricing">
                <div class="price-display">
                  <span class="currency">$</span>
                  <span class="amount">{{ selectedPackage?.price || '997' }}</span>
                </div>
                <p class="price-frequency">{{ selectedPackage?.frequency || 'one-time' }}</p>
              </div>
            </div>
          </div>

          <div class="payment-section">
            <div class="payment-card">
              <h3 class="section-title">Payment Information</h3>
              
              <form @submit.prevent="handleCheckout" class="payment-form">
                <div class="form-group">
                  <label for="email" class="form-label">Email Address</label>
                  <input
                    id="email"
                    v-model="checkoutForm.email"
                    type="email"
                    class="form-input"
                    placeholder="Enter your email"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="username" class="form-label">Username</label>
                  <input
                    id="username"
                    v-model="checkoutForm.username"
                    type="text"
                    class="form-input"
                    placeholder="Enter your username"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="password" class="form-label">Password</label>
                  <input
                    id="password"
                    v-model="checkoutForm.password"
                    type="password"
                    class="form-input"
                    placeholder="Enter your password"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="githubId" class="form-label">GitHub ID</label>
                  <input
                    id="githubId"
                    v-model="checkoutForm.githubId"
                    type="text"
                    class="form-input"
                    placeholder="Enter your GitHub username"
                  />
                </div>

                <div class="terms-section">
                  <label class="checkbox-label">
                    <input
                      type="checkbox"
                      v-model="checkoutForm.agreeToTerms"
                      class="checkbox-input"
                      required
                    />
                    <span class="checkbox-custom"></span>
                    <span class="checkbox-text">
                      I agree to the <a href="#" class="terms-link">Terms of Service</a> 
                      and <a href="#" class="terms-link">Privacy Policy</a>
                    </span>
                  </label>
                </div>

                <button
                  type="submit"
                  class="checkout-button"
                  :disabled="isProcessing || !checkoutForm.agreeToTerms"
                >
                  <span v-if="isProcessing" class="button-spinner"></span>
                  {{ isProcessing ? 'Processing...' : `Pay $${selectedPackage?.price || '997'}` }}
                </button>
              </form>

              <div class="security-info">
                <div class="security-item">
                  <span class="security-icon">🔒</span>
                  <span class="security-text">Secure 256-bit SSL encryption</span>
                </div>
                <div class="security-item">
                  <span class="security-icon">💳</span>
                  <span class="security-text">Powered by Stripe</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuth } from '../stores/auth';
import AppHeader from './AppHeader.vue';

const route = useRoute();
const router = useRouter();
const { api, user } = useAuth();

const isLoading = ref(false);
const isProcessing = ref(false);
const error = ref('');
const selectedPackage = ref<any>(null);

const checkoutForm = ref({
  username: '',
  password: '',
  email: '',
  githubId: '',
  agreeToTerms: false
});

// Mock packages data - in a real app, this would come from the API or route params
const packages = ref([
  {
    name: 'starter',
    price: '47',
    frequency: '1-time price',
    features: ['1 user', '1 App Creation', '1 shared repo']
  },
  {
    name: 'premium',
    price: '997',
    frequency: '1-time price',
    features: ['1 user', 'Unlimited App Creations', 'Unlimited Code Download', 'Prompting Iteration']
  },
  {
    name: 'enterprise',
    price: '10000',
    frequency: 'Monthly with LTC',
    features: ['Unlimited team members', 'Unlimited App Creations', 'White Label Deployment']
  }
]);

const getSelectedFeatures = () => {
  return selectedPackage.value?.features || [
    'Unlimited App Creations',
    'Unlimited Code Download',
    'Prompting Iteration',
    'Auto Access to Future Templates'
  ];
};

const handleCheckout = async () => {
  if (!checkoutForm.value.agreeToTerms) {
    error.value = 'Please agree to the terms and conditions';
    return;
  }

  isProcessing.value = true;
  error.value = '';

  try {
    let myUserId;
    
    // Check if user is already authenticated
    if (user.value && user.value.id) {
      // User is already logged in, use their existing ID
      myUserId = user.value.id;
      console.log('User already authenticated, using existing ID:', myUserId);
    } else {
      // User not authenticated, sign them up first
      const signupData = {
        username: checkoutForm.value.username,
        password: checkoutForm.value.password,
        email: checkoutForm.value.email,
        githubId: checkoutForm.value.githubId
      };

      console.log('Signing up user with data:', signupData);

      // Call the API to sign up the user
      const signupResponse = await api.post('/user/signup', signupData);
      
      console.log('Signup response:', signupResponse.data);
      
      const token = signupResponse.data.token;
      myUserId = signupResponse.data.userId;

      if (token) {
        localStorage.setItem('jwt_token', token);
        // Update the API instance to use the new token for subsequent requests
        api.defaults.headers.Authorization = `Bearer ${token}`;
      }
    }

    // Now create checkout session with the new user's ID
    const createCheckoutSessionData = {
      userId: myUserId,
      paymentType: selectedPackage.value?.name || 'premium',
      username: checkoutForm.value.username,
      password: checkoutForm.value.password,
      email: checkoutForm.value.email,
      githubId: checkoutForm.value.githubId,
      packageName: selectedPackage.value?.name || 'premium',
      amount: selectedPackage.value?.price || '997'
    };

    console.log('Creating checkout session with data:', createCheckoutSessionData);

    // Call the API to create a checkout session
    const response = await api.post('/payment/create-checkout-session', createCheckoutSessionData);
    
    console.log('Checkout session response:', response.data);

    // Redirect to Stripe checkout
    if (response.data.sessionUrl) {
      window.location.href = response.data.sessionUrl;
    } else {
      throw new Error('No checkout session URL received');
    }

  } catch (err: any) {
    console.error('Error during checkout:', err);
    if (err.response?.status === 403) {
      error.value = 'Access denied. An account with this email or username may already exist. Please try signing in instead, or use different credentials.';
    } else if (err.response?.status === 409) {
      error.value = 'An account with this email or username already exists. Please try logging in instead.';
    } else if (err.response?.status === 401) {
      error.value = 'Authentication failed. Please check your credentials and try again.';
    } else {
      error.value = err.response?.data?.message || err.message || 'An error occurred. Please try again later.';
    }
  } finally {
    isProcessing.value = false;
  }
};

const retryCheckout = () => {
  error.value = '';
  handleCheckout();
};

const loadPackageFromRoute = () => {
  const packageName = route.query.package as string;
  if (packageName) {
    const pkg = packages.value.find(p => p.name.toLowerCase() === packageName.toLowerCase());
    if (pkg) {
      selectedPackage.value = pkg;
    }
  }
  
  // Default to premium if no package specified
  if (!selectedPackage.value) {
    selectedPackage.value = packages.value.find(p => p.name === 'premium');
  }
};

onMounted(() => {
  loadPackageFromRoute();
  
  // Pre-fill email if user is logged in
  if (user.value?.email) {
    checkoutForm.value.email = user.value.email;
  }
});
</script>

<style scoped>
.checkout-page {
  min-height: 100vh;
  background: var(--color-bg-dark);
  color: var(--color-text-primary);
}

.checkout-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 24px;
}

.checkout-container {
  max-width: 800px;
  margin: 0 auto;
}

.checkout-header {
  text-align: center;
  margin-bottom: 48px;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0 0 16px 0;
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.page-subtitle {
  font-size: 1.125rem;
  color: var(--color-text-secondary);
  margin: 0;
}

.loading-state,
.error-state {
  text-align: center;
  padding: 80px 24px;
}

.loading-spinner {
  width: 48px;
  height: 48px;
  border: 4px solid var(--color-border-medium);
  border-top: 4px solid var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 24px;
}

.loading-text {
  color: var(--color-text-secondary);
  font-size: 1.1rem;
  margin: 0;
}

.error-icon {
  font-size: 4rem;
  margin-bottom: 24px;
}

.error-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 12px 0;
  color: var(--color-text-primary);
}

.error-message {
  color: var(--color-text-secondary);
  margin: 0 0 32px 0;
  font-size: 1rem;
}

.retry-button {
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  border: none;
  color: var(--color-text-primary);
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.retry-button:hover {
  background: linear-gradient(135deg, var(--color-primary-dark), var(--color-primary));
  transform: translateY(-1px);
}

.checkout-form {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.product-section,
.payment-section {
  width: 100%;
}

.product-card,
.payment-card {
  background: var(--color-bg-medium);
  border: 1px solid var(--color-border-light);
  border-radius: 16px;
  padding: 32px;
}

.product-card {
  display: flex;
  align-items: center;
  gap: 24px;
}

.product-image {
  flex-shrink: 0;
}

.app-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
}

.product-details {
  flex: 1;
}

.product-name {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: var(--color-text-primary);
  text-transform: capitalize;
}

.product-description {
  color: var(--color-text-secondary);
  margin: 0 0 16px 0;
  font-size: 1rem;
}

.product-features {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.feature-icon {
  color: var(--color-success);
  font-weight: bold;
  font-size: 0.9rem;
}

.feature-text {
  color: var(--color-text-secondary);
  font-size: 0.9rem;
}

.product-pricing {
  text-align: right;
  flex-shrink: 0;
}

.price-display {
  display: flex;
  align-items: baseline;
  justify-content: flex-end;
  gap: 4px;
}

.currency {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--color-primary);
}

.amount {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--color-primary);
}

.price-frequency {
  color: var(--color-text-muted);
  margin: 4px 0 0 0;
  font-size: 0.9rem;
}

.section-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin: 0 0 24px 0;
  color: var(--color-text-primary);
}

.payment-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.form-label {
  font-weight: 500;
  color: var(--color-text-primary);
  font-size: 0.9rem;
}

.form-input,
.form-select {
  padding: 12px 16px;
  background: var(--color-bg-light);
  border: 1px solid var(--color-border-medium);
  border-radius: 8px;
  color: var(--color-text-primary);
  font-size: 1rem;
  transition: all 0.2s ease;
}

.form-input:focus,
.form-select:focus {
  outline: none;
  border-color: var(--color-primary);
  background: var(--color-bg-light);
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.form-input::placeholder {
  color: var(--color-text-placeholder);
}

.terms-section {
  margin: 8px 0;
}

.checkbox-label {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  cursor: pointer;
  font-size: 0.9rem;
  line-height: 1.5;
}

.checkbox-input {
  display: none;
}

.checkbox-custom {
  width: 20px;
  height: 20px;
  border: 2px solid var(--color-border-medium);
  border-radius: 4px;
  background: var(--color-bg-light);
  flex-shrink: 0;
  position: relative;
  transition: all 0.2s ease;
  margin-top: 2px;
}

.checkbox-input:checked + .checkbox-custom {
  background: var(--color-primary);
  border-color: var(--color-primary);
}

.checkbox-input:checked + .checkbox-custom::after {
  content: '✓';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 12px;
  font-weight: bold;
}

.checkbox-text {
  color: var(--color-text-secondary);
}

.terms-link {
  color: var(--color-primary);
  text-decoration: none;
  transition: color 0.2s ease;
}

.terms-link:hover {
  color: var(--color-primary-dark);
  text-decoration: underline;
}

.checkout-button {
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  border: none;
  color: var(--color-text-primary);
  padding: 16px 32px;
  font-size: 1.1rem;
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

.checkout-button:hover:not(:disabled) {
  background: linear-gradient(135deg, var(--color-primary-dark), var(--color-primary));
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.4);
}

.checkout-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.button-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top: 2px solid white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.security-info {
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid var(--color-border-light);
  display: flex;
  justify-content: center;
  gap: 32px;
}

.security-item {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--color-text-muted);
  font-size: 0.9rem;
}

.security-icon {
  font-size: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@media (max-width: 768px) {
  .page-title {
    font-size: 2rem;
  }
  
  .product-card {
    flex-direction: column;
    text-align: center;
    gap: 20px;
  }
  
  .product-pricing {
    text-align: center;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .security-info {
    flex-direction: column;
    gap: 16px;
    text-align: center;
  }
}
</style>