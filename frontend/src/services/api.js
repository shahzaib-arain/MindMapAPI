import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
   timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
   withCredentials: true
});

// Enhanced request interceptor
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
       console.log('Token attached:', token.substring(0, 10) + '...'); // Log partial token
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Enhanced response interceptor
api.interceptors.response.use(
  response => response,
  error => {
    console.error('API Error:', {
      message: error.message,
      config: error.config,
      response: error.response
    });

    if (error.code === 'ECONNABORTED') {
      alert('Request timeout - server not responding');
    } else if (!error.response) {
      alert('Network error - check backend connection');
    } else if (error.response.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authService = {
  login: (credentials) => api.post('/public/LogIn', credentials),
  signup: (userData) => api.post('/public/SignUp', userData),
};

export const journalService = {
  getEntries: () => api.get('/Journal'),
  getEntryById: (id) => api.get(`/Journal/id/${id}`),
  createEntry: (entry) => api.post('/Journal', {
    ...entry,
    date: new Date(entry.date).toISOString() // Ensure proper date format
  }),
  updateEntry: (id, entry) => api.put(`/Journal/id/${id}`, {
    ...entry,
    date: new Date(entry.date).toISOString()
  }),
  deleteEntry: (id) => api.delete(`/Journal/id/${id}`),
};

// ... other services remain the same

export const userService = {
  getUserProfile: () => api.get('/user'),
  updateUserProfile: (userData) => api.put('/user', userData),
  deleteAccount: () => api.delete('/user'),
  getGreeting: () => api.get('/user/greetings'),
};

export const adminService = {
  getAllUsers: () => api.get('/admin/all-users'),
  createAdmin: (userData) => api.post('/admin/create-admin-user', userData),
  clearCache: () => api.get('/admin/clear-app-cache'),
};

export const sentimentService = {
  getSentiments: () => api.get('/public/sentiments'),
};