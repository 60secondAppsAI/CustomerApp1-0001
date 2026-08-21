<script setup lang="ts">
import http from "../http-common";
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import AppHeader from './AppHeader.vue';

const route = useRoute();

const API_BASE_URL = http.defaults.baseURL;

const stockId = computed(() => route.params.id as string);
const stockData = ref<any>(null);
const isLoading = ref(true);
const error = ref('');
const activeTab = ref('Details');

const loadStockDetails = async () => {
  isLoading.value = true;
  error.value = '';

  try {
    const response = await fetch(`${API_BASE_URL}/stock/${stockId.value}`);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    stockData.value = await response.json();
  } catch (err: any) {
    console.error('Failed to load stock details:', err);
    error.value = err.message || 'Failed to load stock details';
  } finally {
    isLoading.value = false;
  }
};


const getStockIcon = (stock: any) => {
  const icons = ['👤', '👨', '👩', '🧑', '👱', '🧔', '👨‍💼', '👩‍💼', '🧑‍💻', '👨‍🔬'];
  return icons[stock.stockId % icons.length];
};

onMounted(() => {
  loadStockDetails();
});
</script>

<template>
  <div class="stock-detail-page">
    <AppHeader />

    <main class="detail-content">
      <!-- Loading State -->
      <div v-if="isLoading" class="loading-state">
        <div class="loading-spinner"></div>
        <p class="loading-text">Loading stock details...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="error-state">
        <div class="error-icon">⚠️</div>
        <h3 class="error-title">Failed to Load Stock</h3>
        <p class="error-message">{{ error }}</p>
        <button @click="loadStockDetails" class="retry-button">
          Try Again
        </button>
      </div>

      <!-- Stock Details -->
      <div v-else-if="stockData" class="stock-details">
        <div class="stock-header-section">
          <div class="stock-icon-large">{{ getStockIcon(stockData) }}</div>
          <div class="stock-header-info">
            <h1 class="stock-title">{{ stockData.name || 'Unknown Stock' }}</h1>
            <p class="stock-subtitle">Stock ID: {{ stockData.stockId }}</p>
          </div>
        </div>

        <!-- Tabs Section -->
        <div class="tabs-section">
          <div class="tabs-container">
            <div class="tabs-nav">
              <button 
                v-for="tab in ['Details', 'Contact', 'Activity']"
                :key="tab"
                @click="activeTab = tab"
                :class="['tab-button', { active: activeTab === tab }]"
              >
                {{ tab }}
              </button>
            </div>
            
            <div class="tab-content">
              <!-- Details Tab -->
              <div v-if="activeTab === 'Details'" class="tab-panel">
                <div class="details-section">
                  <h2 class="section-title">Stock Information</h2>
                  <div class="info-grid">
                    <div class="info-item">
                      <span class="info-label">Stock ID</span>
                      <span class="info-value">{{ stockData.stockId }}</span>
                    </div>

                    <div class="info-item">
                      <span class="info-label">StockId</span>
                      <span class="info-value">{{ stockData.stockId || 'N/A' }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">TickerSymbol</span>
                      <span class="info-value">{{ stockData.tickerSymbol || 'N/A' }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">CompanyName</span>
                      <span class="info-value">{{ stockData.companyName || 'N/A' }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">MarketValue</span>
                      <span class="info-value">{{ stockData.marketValue || 'N/A' }}</span>
                    </div>

                  </div>
                </div>
              </div>

              <!-- Activity Tab -->
              <div v-else-if="activeTab === 'Activity'" class="tab-panel">
                <div class="empty-tab-state">
                  <div class="empty-icon">📊</div>
                  <h3>Activity Log</h3>
                  <p>Stock activity and history will be available here.</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.stock-detail-page {
  min-height: 100vh;
  background: var(--color-bg-dark);
  color: var(--color-text-primary);
}

.detail-header {
  background: var(--color-bg-medium);
  border-bottom: 1px solid var(--color-border-light);
  padding: 20px 0;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.back-button {
  background: var(--color-bg-light);
  border: 1px solid var(--color-border-medium);
  color: var(--color-text-primary);
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.95rem;
  transition: all 0.2s ease;
}

.back-button:hover {
  background: var(--color-bg-light);
  border-color: var(--color-primary);
}

.detail-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 24px;
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

.stock-header-section {
  display: flex;
  align-items: flex-start;
  gap: 32px;
  margin-bottom: 48px;
  padding: 32px;
  background: var(--color-bg-medium);
  border-radius: 16px;
  border: 1px solid var(--color-border-light);
}

.stock-icon-large {
  font-size: 4rem;
  flex-shrink: 0;
}

.stock-header-info {
  flex: 1;
}

.stock-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0 0 8px 0;
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.stock-subtitle {
  font-size: 1.125rem;
  color: var(--color-text-secondary);
  margin: 0;
  line-height: 1.6;
}

/* Tabs Styles */
.tabs-section {
  max-width: 1200px;
  margin: 0 auto 40px;
  padding: 0 24px;
}

.tabs-container {
  background: var(--color-bg-medium);
  border-radius: 16px;
  border: 1px solid var(--color-border-light);
  overflow: hidden;
}

.tabs-nav {
  display: flex;
  background: var(--color-bg-light);
  border-bottom: 1px solid var(--color-border-light);
  overflow-x: auto;
}

.tab-button {
  background: transparent;
  border: none;
  color: var(--color-text-secondary);
  padding: 16px 24px;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border-bottom: 3px solid transparent;
  white-space: nowrap;
  flex-shrink: 0;
}

.tab-button:hover {
  color: var(--color-text-primary);
  background: rgba(52, 152, 219, 0.1);
}

.tab-button.active {
  color: var(--color-primary);
  border-bottom-color: var(--color-primary);
  background: rgba(52, 152, 219, 0.1);
}

.tab-content {
  min-height: 400px;
}

.tab-panel {
  padding: 32px;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.tab-content-section {
  background: var(--color-bg-light);
  border-radius: 12px;
  padding: 24px;
  border: 1px solid var(--color-border-light);
}

.empty-tab-state {
  text-align: center;
  padding: 60px 20px;
  color: var(--color-text-secondary);
}

.empty-tab-state .empty-icon {
  font-size: 3rem;
  margin-bottom: 16px;
}

.empty-tab-state h3 {
  font-size: 1.25rem;
  margin: 0 0 8px 0;
  color: var(--color-text-primary);
}

.empty-tab-state p {
  margin: 0;
  font-size: 0.95rem;
}

.details-section {
  background: var(--color-bg-medium);
  border-radius: 16px;
  padding: 32px;
  border: 1px solid var(--color-border-light);
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 24px 0;
  color: var(--color-text-primary);
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 16px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid var(--color-border-light);
}

.info-label {
  font-weight: 500;
  color: var(--color-text-secondary);
}

.info-value {
  font-weight: 600;
  color: var(--color-text-primary);
  text-align: right;
}

.contact-details {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.contact-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: var(--color-bg-medium);
  border-radius: 12px;
  border: 1px solid var(--color-border-light);
}

.contact-icon {
  font-size: 2rem;
  flex-shrink: 0;
}

.contact-info h3 {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0 0 4px 0;
  color: var(--color-text-primary);
}

.contact-info p {
  margin: 0;
  color: var(--color-text-secondary);
  font-size: 0.95rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@media (max-width: 768px) {
  .stock-header-section {
    flex-direction: column;
    gap: 20px;
    text-align: center;
  }
  
  .stock-title {
    font-size: 2rem;
  }
  
  .info-grid {
    grid-template-columns: 1fr;
  }
  
  .info-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
  }
  
  .info-value {
    text-align: left;
  }
  
  .contact-item {
    flex-direction: column;
    text-align: center;
    gap: 12px;
  }
}
</style>
