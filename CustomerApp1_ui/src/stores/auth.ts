import { ref, computed } from 'vue';
import axios from 'axios';
import http from "../http-common";

// Auth state
const token = ref<string | null>(localStorage.getItem('jwt_token'));
const user = ref<any>(null);
const userRoles = ref<number[]>([]);

const API_BASE_URL = http.defaults.baseURL;

// Auth functions at module level
const logout = () => {
  token.value = null;
  user.value = null;
  userRoles.value = [];
  localStorage.removeItem('jwt_token');
};

// Axios instance with interceptors
const api = axios.create({
  baseURL: API_BASE_URL,
});

// Request interceptor to add token
api.interceptors.request.use((config) => {
  if (token.value) {
    config.headers.Authorization = `Bearer ${token.value}`;
  }
  return config;
});

// Response interceptor to handle token expiration
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Only auto-logout on 401 (unauthorized), not 403 (forbidden)
    // 403 might be a permission issue, not an authentication issue
    if (error.response?.status === 401) {
      logout();
    }
    return Promise.reject(error);
  }
);

// Auth functions
export const useAuth = () => {
  const isAuthenticated = computed(() => !!token.value);

  const signin = async (username: string, password: string) => {
    try {
      const response = await api.post('/user/signin', {
        username,
        password
      });

      if (response.data.token) {
        token.value = response.data.token;
        user.value = response.data.user || { username };
        userRoles.value = response.data.roleIds || [];
        localStorage.setItem('jwt_token', response.data.token);
        return { success: true };
      } else {
        throw new Error('No token received');
      }
    } catch (error: any) {
      console.error('Sign in error:', error);
      return {
        success: false,
        error: error.response?.data?.message || error.message || 'Sign in failed'
      };
    }
  };

  const getMyApps = async (params = {}) => {
    try {
      const defaultParams = {
        sortBy: 'applicationId',
        sortOrder: 'desc',
        searchQuery: '',
        page: 0,
        size: 20,
        ...params
      };

      const response = await api.get('/application/myApps', { params: defaultParams });
      return { success: true, data: response.data };
    } catch (error: any) {
      console.error('Get my apps error:', error);
      return {
        success: false,
        error: error.response?.data?.message || error.message || 'Failed to fetch apps'
      };
    }
  };

  return {
    token: computed(() => token.value),
    user: computed(() => user.value),
    userRoles: computed(() => userRoles.value),
    isAdmin: computed(() => userRoles.value.includes(1)),
    isAuthenticated,
    signin,
    logout,
    getMyApps,
    api
  };
};