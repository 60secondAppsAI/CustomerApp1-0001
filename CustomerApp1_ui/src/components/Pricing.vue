<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from './AppHeader.vue';
import pricingData from '../data/data.json';

const router = useRouter();

const activePackageType = ref('monthly');
const packages = ref<any[]>([]);
const isLoading = ref(true);
const error = ref('');

const loadPricingData = () => {
  try {
    packages.value = pricingData.packages;
    isLoading.value = false;
  } catch (err: any) {
    console.error('Failed to load pricing data:', err);
    error.value = 'Failed to load pricing data';
    isLoading.value = false;
  }
};

const handleBuy = (card: any) => {
  // Navigate to checkout with the selected package
  router.push({ 
    name: 'checkout', 
    query: { 
      package: card.name,
      type: activePackageType.value
    }
  });
};

onMounted(() => {
  loadPricingData();
});
</script>

<template>
  <div class="pricing-page">
    <AppHeader />
    
    <div class="content">
      <div class="topbar">
        <h2 class="title">Choose Your Plan</h2>
        <p class="label">
          Scale your business with our flexible pricing plans designed to grow with you.
        </p>
        
        <div class="package-types">
          <div 
            class="package-type"
            :class="{ active: activePackageType === 'monthly' }"
            @click="activePackageType = 'monthly'"
          >
            Monthly
          </div>
          <div 
            class="package-type"
            :class="{ active: activePackageType === 'annual' }"
            @click="activePackageType = 'annual'"
          >
            Annual (Save 20%)
          </div>
        </div>
      </div>
      
      <div v-if="isLoading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>Loading pricing information...</p>
      </div>
      
      <div v-else-if="error" class="error-state">
        <div class="error-icon">⚠️</div>
        <h3>Failed to Load Pricing</h3>
        <p>{{ error }}</p>
        <button @click="loadPricingData" class="retry-button">Try Again</button>
      </div>
      
      <div v-else class="pricing-table-wrapper">
        <div class="card" v-for="(card, index) in packages" :key="index">
          <p class="card-title">{{ card.name.charAt(0).toUpperCase() + card.name.slice(1) }}</p>
          <p class="card-price">${{ activePackageType === "annual" ? Math.round(card.price * 10) : card.price }}</p>
          <p class="card-frequency">{{ activePackageType === "annual" ? "per year" : card.frequency }}</p>
          <div class="packages-list">
            <div class="package" v-for="(pkg, i) in card.package" :key="i">
              <div class="package-icon">
                <div v-if="pkg.access" class="access-indicator">
                  <svg viewBox="0 0 20 20" class="check-icon">
                    <path d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"/>
                  </svg>
                </div>
                <div v-else class="no-access-indicator">
<svg viewBox="0 0 20 20" class="minus-icon">
  <path d="M4 9h12v2H4z"/>
</svg>
                </div>
              </div>
              <span class="package-label">{{ pkg.name }}</span>
            </div>
          </div>
          <button
            class="buy-btn"
            @click="handleBuy(card)"
          >
            Get Started
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.pricing-page {
  min-height: 100vh;
  background: var(--color-bg-dark);
  color: var(--color-text-primary);
}

.content {
  position: relative;
}

.topbar {
  height: 400px;
  background: var(--color-bg-dark);
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 0 24px;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0 0 16px 0;
  color: var(--color-text-primary);
}

.label {
  font-size: 1.25rem;
  margin: 0 0 48px 0;
  color: var(--color-text-primary);
  max-width: 600px;
}

.package-types {
  width: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  color: #000;
  gap: 20px;
  padding: 12px 0;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.package-type {
  cursor: pointer;
  font-size: 1rem;
  font-weight: 500;
  letter-spacing: 0.04rem;
  text-transform: capitalize;
  padding: 8px 16px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.package-type:hover {
  background: rgba(52, 152, 219, 0.1);
  color: var(--color-primary);
}

.package-type.active {
  background: var(--color-primary);
  color: #fff;
  font-weight: 600;
}

.pricing-table-wrapper {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: -200px;
  flex-wrap: wrap;
  padding: 0 24px;
  position: relative;
  z-index: 10;
}

.card {
  min-width: 350px;
  max-width: 400px;
  color: #444;
  text-align: center;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  padding: 32px 24px;
  background: var(--color-text-primary);
  transition: transform 0.3s ease;
  position: relative;
}

.card:hover {
  transform: translateY(-8px);
}

.card-title {
  font-size: 0.75rem;
  background: #e9ecef;
  width: max-content;
  margin: 0 auto 24px;
  padding: 8px 16px;
  text-transform: uppercase;
  border-radius: 20px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.card-price {
  font-size: 3rem;
  font-weight: 700;
  margin: 0;
  color: var(--color-bg-dark);
}

.card-frequency {
  font-size: 1rem;
  font-weight: 500;
  margin: 8px 0 32px 0;
  color: var(--color-bg-dark);
}

.packages-list {
  margin-bottom: 32px;
  text-align: left;
}

.package {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  padding: 0 8px;
}

.package-icon {
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.access-indicator {
  width: 20px;
  height: 20px;
  background: linear-gradient(135deg, #10b981, #059669);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(16, 185, 129, 0.3);
}

.no-access-indicator {
  width: 20px;
  height: 20px;
  background: linear-gradient(135deg, #d1d5db, #9ca3af);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(239, 68, 68, 0.3);
}

.check-icon {
  width: 12px;
  height: 12px;
  fill: white;
}

.minus-icon {
  margin-top: 3px;
  margin-left: -1px;
  width: 10px;
  height: 10px;
  fill: #282c34;
  border-radius: 9px;
}

.package-label {
  margin-left: 12px;
  font-size: 0.95rem;
  color: var(--color-bg-dark);
  line-height: 1.4;
}

.buy-btn {
  width: 100%;
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  border: none;
  color: #fff;
  padding: 16px 24px;
  font-size: 1rem;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.buy-btn:hover {
  background: linear-gradient(135deg, var(--color-primary-dark), var(--color-primary));
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(52, 152, 219, 0.4);
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

.error-icon {
  font-size: 4rem;
  margin-bottom: 24px;
}

.error-state h3 {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 12px 0;
  color: var(--color-text-primary);
}

.error-state p {
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

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }
  
  .label {
    font-size: 1.125rem;
  }
  
  .pricing-table-wrapper {
    flex-direction: column;
    align-items: center;
    gap: 1.5rem;
    margin-top: -150px;
  }
  
  .card {
    min-width: 300px;
    max-width: 350px;
  }
  .package-types {
    width: 250px;
  }
}
</style>