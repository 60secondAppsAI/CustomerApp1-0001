<template>
  <div class="dashboard-page">
    <AppHeader />
    
    <div class="content">
      <div class="dashboard-title">My Dashboard</div>

      <div class="dashboard-main-content">
        <div class="dashboard-content force-expansion">
          <div class="stats-grid">
            <div class="stat-card" v-for="stat in stats" :key="stat.label">
              <div class="stat-value">{{ stat.value }}</div>
              <div>{{ stat.label }}</div>
            </div>
          </div>

          <div class="dashboard-table-container">
            <h2 class="dashboard-table-title">Recent Accounts</h2>
            
            <table class="table">
              <thead>
                <tr>
                  <th v-for="header in accountHeaders" :key="header">{{ header }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="account in accounts" :key="account.name" @click="showRecordDetails(account)">
                  <td>{{ account.name }}</td>
                  <td>{{ account.email }}</td>
                  <td>{{ account.status }}</td>
                  <td>{{ account.createdDate }}</td>
                </tr>
              </tbody>
            </table>
          </div>
              
          <div class="dashboard-table-container">
            <h2 class="dashboard-table-title">Recent Transactions</h2>
            <table class="table">
              <thead>
                <tr>
                  <th v-for="header in transactionHeaders" :key="header">{{ header }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="transaction in transactions" :key="transaction.id" @click="showRecordDetails(transaction)">
                  <td>{{ transaction.id }}</td>
                  <td>{{ transaction.amount }}</td>
                  <td>{{ transaction.date }}</td>
                  <td>{{ transaction.status }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import AppHeader from './AppHeader.vue';

const simValue = ref(43);
const stats = ref<any[]>([]);

const accountHeaders = ['Name', 'Email', 'Status', 'Created Date'];
const accounts = ref([
  {
    name: 'John Smith',
    email: 'john@example.com',
    status: 'Active',
    createdDate: '2023-07-20'
  },
  {
    name: 'Sarah Johnson',
    email: 'sarah@techsolutions.com',
    status: 'Active',
    createdDate: '2023-07-19'
  },
  {
    name: 'Michael Brown',
    email: 'michael@global.com',
    status: 'Pending',
    createdDate: '2023-07-18'
  }
]);

const transactionHeaders = ['ID', 'Amount', 'Date', 'Status'];
const transactions = ref([
  {
    id: 'TXN-001',
    amount: '$1,250.00',
    date: 'Today',
    status: 'Completed'
  },
  {
    id: 'TXN-002',
    amount: '$850.00',
    date: 'Yesterday',
    status: 'Pending'
  }
]);

const updateStats = () => {
  stats.value = [
    { value: '127', label: 'Total Accounts' },
    { value: simValue.value.toString(), label: 'Active Plans' },
    { value: '$1.2M', label: 'Total Revenue' },
    { value: '89%', label: 'Success Rate' }
  ];
};

const simulate = () => {
  setTimeout(() => {
    simValue.value = simValue.value + 1;
    updateStats();
    simulate();
  }, Math.random() * 3000 + 1000);
};

const showRecordDetails = (record: any) => {
  console.log('Show details for:', record);
};

onMounted(() => {
  updateStats();
  simulate();
});
</script>

<style scoped>
.dashboard-page {
  min-height: 100vh;
  background: var(--color-bg-dark);
  color: var(--color-text-primary);
}

.content {
  padding-top: 20px;
}

.dashboard-main-content {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-left: auto;
  margin-right: auto;
}

.dashboard-content {
  padding: 25px;
  width: 100%;
  overflow-x: auto;
  background: var(--color-bg-medium);
  border-radius: 15px;
  margin: 0 20px;
}

.dashboard-table-container {
  background: var(--color-bg-light);
  border-radius: 15px;
  padding: 1.5rem;
  margin-top: 1rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.stat-card {
  background: var(--color-bg-light);
  padding: 1rem;
  border-radius: 15px;
  text-align: center;
  color: var(--color-text-primary);
}

.stat-value {
  font-size: 2rem;
  color: var(--color-primary);
  font-weight: bold;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid var(--color-border-light);
  color: var(--color-text-primary);
}

.table th {
  background: var(--color-bg-medium);
  font-weight: 600;
}

.table tr:hover {
  background-color: var(--color-bg-medium);
  cursor: pointer;
}

.force-expansion {
  width: calc(100vw - 260px);
}

.dashboard-title {
  font-size: 1.75rem;
  font-weight: bold;
  font-style: italic;
  color: var(--color-primary);
  text-align: left;
  padding: 0px 15px;
  margin-left: 20px;
}

.dashboard-table-title {
  color: var(--color-primary);
  font-weight: bold;
  text-align: left;
  margin: 0 0 1rem 0;
}

@media (max-width: 768px) {
  .force-expansion {
    width: calc(100vw - 40px);
  }
  
  .dashboard-content {
    margin: 0 10px;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
}
</style>