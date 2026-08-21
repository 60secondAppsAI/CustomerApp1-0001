<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from './AppHeader.vue';

const router = useRouter();

const currentPalette = ref(0);
const currentLightPalette = ref(0);
const isDarkMode = ref(true);
const isLoading = ref(false);
const successMessage = ref('');

const darkPalettes = [
  {
    name: 'Default Dark',
    colors: {
      '--color-primary': '#3498db',
      '--color-primary-dark': '#2980b9',
      '--color-bg-dark': '#282c34',
      '--color-bg-medium': '#353b48',
      '--color-bg-light': '#3e4451',
      '--color-text-primary': '#d3d7de',
      '--color-text-secondary': 'rgba(211, 215, 222, 0.8)',
      '--color-text-muted': 'rgba(211, 215, 222, 0.7)',
      '--color-text-placeholder': 'rgba(211, 215, 222, 0.5)',
      '--color-border-light': 'rgba(211, 215, 222, 0.1)',
      '--color-border-medium': 'rgba(211, 215, 222, 0.2)',
      '--color-border-dark': 'rgba(211, 215, 222, 0.3)',
      '--color-success': '#27ae60',
      '--color-warning': '#f39c12',
      '--color-error': '#e74c3c',
      '--color-muted': '#95a5a6'
    }
  },
  {
    name: 'Ocean Blue',
    colors: {
      '--color-primary': '#2196f3',
      '--color-primary-dark': '#1976d2',
      '--color-bg-dark': '#0d1421',
      '--color-bg-medium': '#1a2332',
      '--color-bg-light': '#243447',
      '--color-text-primary': '#e3f2fd',
      '--color-text-secondary': 'rgba(227, 242, 253, 0.8)',
      '--color-text-muted': 'rgba(227, 242, 253, 0.7)',
      '--color-text-placeholder': 'rgba(227, 242, 253, 0.5)',
      '--color-border-light': 'rgba(227, 242, 253, 0.1)',
      '--color-border-medium': 'rgba(227, 242, 253, 0.2)',
      '--color-border-dark': 'rgba(227, 242, 253, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#607d8b'
    }
  },
  {
    name: 'Purple Night',
    colors: {
      '--color-primary': '#9c27b0',
      '--color-primary-dark': '#7b1fa2',
      '--color-bg-dark': '#1a0d21',
      '--color-bg-medium': '#2d1b32',
      '--color-bg-light': '#3f2847',
      '--color-text-primary': '#f3e5f5',
      '--color-text-secondary': 'rgba(243, 229, 245, 0.8)',
      '--color-text-muted': 'rgba(243, 229, 245, 0.7)',
      '--color-text-placeholder': 'rgba(243, 229, 245, 0.5)',
      '--color-border-light': 'rgba(243, 229, 245, 0.1)',
      '--color-border-medium': 'rgba(243, 229, 245, 0.2)',
      '--color-border-dark': 'rgba(243, 229, 245, 0.3)',
      '--color-success': '#66bb6a',
      '--color-warning': '#ffa726',
      '--color-error': '#ef5350',
      '--color-muted': '#9e9e9e'
    }
  },
  {
    name: 'Forest Green',
    colors: {
      '--color-primary': '#4caf50',
      '--color-primary-dark': '#388e3c',
      '--color-bg-dark': '#0d1f0d',
      '--color-bg-medium': '#1b321b',
      '--color-bg-light': '#284728',
      '--color-text-primary': '#e8f5e8',
      '--color-text-secondary': 'rgba(232, 245, 232, 0.8)',
      '--color-text-muted': 'rgba(232, 245, 232, 0.7)',
      '--color-text-placeholder': 'rgba(232, 245, 232, 0.5)',
      '--color-border-light': 'rgba(232, 245, 232, 0.1)',
      '--color-border-medium': 'rgba(232, 245, 232, 0.2)',
      '--color-border-dark': 'rgba(232, 245, 232, 0.3)',
      '--color-success': '#66bb6a',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#78909c'
    }
  },
  {
    name: 'Sunset Orange',
    colors: {
      '--color-primary': '#ff5722',
      '--color-primary-dark': '#d84315',
      '--color-bg-dark': '#211a0d',
      '--color-bg-medium': '#32281b',
      '--color-bg-light': '#473628',
      '--color-text-primary': '#fff3e0',
      '--color-text-secondary': 'rgba(255, 243, 224, 0.8)',
      '--color-text-muted': 'rgba(255, 243, 224, 0.7)',
      '--color-text-placeholder': 'rgba(255, 243, 224, 0.5)',
      '--color-border-light': 'rgba(255, 243, 224, 0.1)',
      '--color-border-medium': 'rgba(255, 243, 224, 0.2)',
      '--color-border-dark': 'rgba(255, 243, 224, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ffc107',
      '--color-error': '#f44336',
      '--color-muted': '#795548'
    }
  },
  {
    name: 'Purple Night 2',
    colors: {
      '--color-primary': '#8bb027',
      '--color-primary-dark': '#7b1fa2',
      '--color-bg-dark': '#1a0d21',
      '--color-bg-medium': '#2d1b32',
      '--color-bg-light': '#3f2847',
      '--color-text-primary': '#f3e5f5',
      '--color-text-secondary': 'rgba(243, 229, 245, 0.8)',
      '--color-text-muted': 'rgba(243, 229, 245, 0.7)',
      '--color-text-placeholder': 'rgba(243, 229, 245, 0.5)',
      '--color-border-light': 'rgba(243, 229, 245, 0.1)',
      '--color-border-medium': 'rgba(243, 229, 245, 0.2)',
      '--color-border-dark': 'rgba(243, 229, 245, 0.3)',
      '--color-success': '#66bb6a',
      '--color-warning': '#ffa726',
      '--color-error': '#ef5350',
      '--color-muted': '#9e9e9e'
    }
  },
  {
    name: 'CyberPunk',
    colors: {
      '--color-primary': '#00ffff',
      '--color-primary-dark': '#00cccc',
      '--color-bg-dark': '#0d0208',
      '--color-bg-medium': '#1a0f1a',
      '--color-bg-light': '#2d1b2d',
      '--color-text-primary': '#00ffff',
      '--color-text-secondary': 'rgba(0, 255, 255, 0.8)',
      '--color-text-muted': 'rgba(0, 255, 255, 0.6)',
      '--color-text-placeholder': 'rgba(0, 255, 255, 0.4)',
      '--color-border-light': 'rgba(0, 255, 255, 0.1)',
      '--color-border-medium': 'rgba(0, 255, 255, 0.2)',
      '--color-border-dark': 'rgba(0, 255, 255, 0.3)',
      '--color-success': '#39ff14',
      '--color-warning': '#ffff00',
      '--color-error': '#ff0080',
      '--color-muted': '#8a2be2'
    }
  },
  {
    name: 'Psychedelic',
    colors: {
      '--color-primary': '#ccff14',
      '--color-primary-dark': '#dc143c',
      '--color-bg-dark': '#2e0249',
      '--color-bg-medium': '#570a57',
      '--color-bg-light': '#7209b7',
      '--color-text-primary': '#00ff7f',
      '--color-text-secondary': 'rgba(0, 255, 127, 0.8)',
      '--color-text-muted': 'rgba(0, 255, 127, 0.7)',
      '--color-text-placeholder': 'rgba(0, 255, 127, 0.5)',
      '--color-border-light': 'rgba(0, 255, 127, 0.1)',
      '--color-border-medium': 'rgba(0, 255, 127, 0.2)',
      '--color-border-dark': 'rgba(0, 255, 127, 0.3)',
      '--color-success': '#00ff00',
      '--color-warning': '#ffa500',
      '--color-error': '#ff4500',
      '--color-muted': '#9370db'
    }
  },
  {
    name: 'Stranger Things',
    colors: {
      '--color-primary': '#7a0000',
      '--color-primary-dark': '#e55a2b',
      '--color-bg-dark': '#0a0a0a',
      '--color-bg-medium': '#1a1a1a',
      '--color-bg-light': '#2a2a2a',
      '--color-text-primary': '#ff6b35',
      '--color-text-secondary': 'rgba(255, 107, 53, 0.8)',
      '--color-text-muted': 'rgba(255, 107, 53, 0.6)',
      '--color-text-placeholder': 'rgba(255, 107, 53, 0.4)',
      '--color-border-light': 'rgba(255, 107, 53, 0.1)',
      '--color-border-medium': 'rgba(255, 107, 53, 0.2)',
      '--color-border-dark': 'rgba(255, 107, 53, 0.3)',
      '--color-success': '#00ff41',
      '--color-warning': '#ffff00',
      '--color-error': '#ff073a',
      '--color-muted': '#666666'
    }
  }
];

const lightPalettes = [
  {
    name: 'Ocean Blue Light',
    colors: {
      '--color-primary': '#2196f3',
      '--color-primary-dark': '#1976d2',
      '--color-bg-dark': '#f0f8ff',
      '--color-bg-medium': '#e3f2fd',
      '--color-bg-light': '#bbdefb',
      '--color-text-primary': '#0d47a1',
      '--color-text-secondary': 'rgba(13, 71, 161, 0.8)',
      '--color-text-muted': 'rgba(13, 71, 161, 0.7)',
      '--color-text-placeholder': 'rgba(13, 71, 161, 0.5)',
      '--color-border-light': 'rgba(13, 71, 161, 0.1)',
      '--color-border-medium': 'rgba(13, 71, 161, 0.2)',
      '--color-border-dark': 'rgba(13, 71, 161, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#607d8b'
    }
  },
  {
    name: 'Purple Night Light',
    colors: {
      '--color-primary': '#9c27b0',
      '--color-primary-dark': '#7b1fa2',
      '--color-bg-dark': '#fce4ec',
      '--color-bg-medium': '#f8bbd9',
      '--color-bg-light': '#f48fb1',
      '--color-text-primary': '#4a148c',
      '--color-text-secondary': 'rgba(74, 20, 140, 0.8)',
      '--color-text-muted': 'rgba(74, 20, 140, 0.7)',
      '--color-text-placeholder': 'rgba(74, 20, 140, 0.5)',
      '--color-border-light': 'rgba(74, 20, 140, 0.1)',
      '--color-border-medium': 'rgba(74, 20, 140, 0.2)',
      '--color-border-dark': 'rgba(74, 20, 140, 0.3)',
      '--color-success': '#66bb6a',
      '--color-warning': '#ffa726',
      '--color-error': '#ef5350',
      '--color-muted': '#9e9e9e'
    }
  },
  {
    name: 'Forest Green Light',
    colors: {
      '--color-primary': '#4caf50',
      '--color-primary-dark': '#388e3c',
      '--color-bg-dark': '#f1f8e9',
      '--color-bg-medium': '#dcedc8',
      '--color-bg-light': '#c5e1a5',
      '--color-text-primary': '#1b5e20',
      '--color-text-secondary': 'rgba(27, 94, 32, 0.8)',
      '--color-text-muted': 'rgba(27, 94, 32, 0.7)',
      '--color-text-placeholder': 'rgba(27, 94, 32, 0.5)',
      '--color-border-light': 'rgba(27, 94, 32, 0.1)',
      '--color-border-medium': 'rgba(27, 94, 32, 0.2)',
      '--color-border-dark': 'rgba(27, 94, 32, 0.3)',
      '--color-success': '#66bb6a',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#78909c'
    }
  },
  {
    name: 'Sunset Orange Light',
    colors: {
      '--color-primary': '#ff5722',
      '--color-primary-dark': '#d84315',
      '--color-bg-dark': '#fff3e0',
      '--color-bg-medium': '#ffe0b2',
      '--color-bg-light': '#ffcc80',
      '--color-text-primary': '#bf360c',
      '--color-text-secondary': 'rgba(191, 54, 12, 0.8)',
      '--color-text-muted': 'rgba(191, 54, 12, 0.7)',
      '--color-text-placeholder': 'rgba(191, 54, 12, 0.5)',
      '--color-border-light': 'rgba(191, 54, 12, 0.1)',
      '--color-border-medium': 'rgba(191, 54, 12, 0.2)',
      '--color-border-dark': 'rgba(191, 54, 12, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ffc107',
      '--color-error': '#f44336',
      '--color-muted': '#795548'
    }
  },
  {
    name: 'Purple Night 2 Light',
    colors: {
      '--color-primary': '#8bb027',
      '--color-primary-dark': '#689f38',
      '--color-bg-dark': '#f9fbe7',
      '--color-bg-medium': '#f0f4c3',
      '--color-bg-light': '#e6ee9c',
      '--color-text-primary': '#33691e',
      '--color-text-secondary': 'rgba(51, 105, 30, 0.8)',
      '--color-text-muted': 'rgba(51, 105, 30, 0.7)',
      '--color-text-placeholder': 'rgba(51, 105, 30, 0.5)',
      '--color-border-light': 'rgba(51, 105, 30, 0.1)',
      '--color-border-medium': 'rgba(51, 105, 30, 0.2)',
      '--color-border-dark': 'rgba(51, 105, 30, 0.3)',
      '--color-success': '#66bb6a',
      '--color-warning': '#ffa726',
      '--color-error': '#ef5350',
      '--color-muted': '#9e9e9e'
    }
  },
  {
    name: 'CyberPunk Light',
    colors: {
      '--color-primary': '#00bcd4',
      '--color-primary-dark': '#0097a7',
      '--color-bg-dark': '#e0f7fa',
      '--color-bg-medium': '#b2ebf2',
      '--color-bg-light': '#80deea',
      '--color-text-primary': '#006064',
      '--color-text-secondary': 'rgba(0, 96, 100, 0.8)',
      '--color-text-muted': 'rgba(0, 96, 100, 0.6)',
      '--color-text-placeholder': 'rgba(0, 96, 100, 0.4)',
      '--color-border-light': 'rgba(0, 96, 100, 0.1)',
      '--color-border-medium': 'rgba(0, 96, 100, 0.2)',
      '--color-border-dark': 'rgba(0, 96, 100, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#607d8b'
    }
  },
  {
    name: 'Psychedelic Light',
    colors: {
      '--color-primary': '#ccff14',
      '--color-primary-dark': '#9ccc65',
      '--color-bg-dark': '#f9fff0',
      '--color-bg-medium': '#f1f8e9',
      '--color-bg-light': '#dcedc8',
      '--color-text-primary': '#1b5e20',
      '--color-text-secondary': 'rgba(27, 94, 32, 0.8)',
      '--color-text-muted': 'rgba(27, 94, 32, 0.7)',
      '--color-text-placeholder': 'rgba(27, 94, 32, 0.5)',
      '--color-border-light': 'rgba(27, 94, 32, 0.1)',
      '--color-border-medium': 'rgba(27, 94, 32, 0.2)',
      '--color-border-dark': 'rgba(27, 94, 32, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#78909c'
    }
  },
  {
    name: 'Stranger Things Light',
    colors: {
      '--color-primary': '#7a0000',
      '--color-primary-dark': '#e55a2b',
      '--color-bg-dark': '#fff8f5',
      '--color-bg-medium': '#ffe8e1',
      '--color-bg-light': '#ffccbc',
      '--color-text-primary': '#3e2723',
      '--color-text-secondary': 'rgba(62, 39, 35, 0.8)',
      '--color-text-muted': 'rgba(62, 39, 35, 0.6)',
      '--color-text-placeholder': 'rgba(62, 39, 35, 0.4)',
      '--color-border-light': 'rgba(62, 39, 35, 0.1)',
      '--color-border-medium': 'rgba(62, 39, 35, 0.2)',
      '--color-border-dark': 'rgba(62, 39, 35, 0.3)',
      '--color-success': '#4caf50',
      '--color-warning': '#ff9800',
      '--color-error': '#f44336',
      '--color-muted': '#8d6e63'
    }
  },
  {
    name: 'Light Mode 2',
    colors: {
      '--color-primary': '#2563eb',
      '--color-primary-dark': '#1d4ed8',
      '--color-bg-dark': '#f5f5f5',
      '--color-bg-medium': '#e8e8e8',
      '--color-bg-light': '#d9d9d9',
      '--color-text-primary': '#2c2c2c',
      '--color-text-secondary': 'rgba(44, 44, 44, 0.8)',
      '--color-text-muted': 'rgba(44, 44, 44, 0.7)',
      '--color-text-placeholder': 'rgba(44, 44, 44, 0.5)',
      '--color-border-light': 'rgba(44, 44, 44, 0.1)',
      '--color-border-medium': 'rgba(44, 44, 44, 0.2)',
      '--color-border-dark': 'rgba(44, 44, 44, 0.3)',
      '--color-success': '#059669',
      '--color-warning': '#d97706',
      '--color-error': '#dc2626',
      '--color-muted': '#64748b'
    }
  },
  {
    name: 'Light Mode',
    colors: {
      '--color-primary': '#2563eb',
      '--color-primary-dark': '#1d4ed8',
      '--color-bg-dark': '#ffffff',
      '--color-bg-medium': '#f8fafc',
      '--color-bg-light': '#f1f5f9',
      '--color-text-primary': '#1e293b',
      '--color-text-secondary': 'rgba(30, 41, 59, 0.8)',
      '--color-text-muted': 'rgba(30, 41, 59, 0.7)',
      '--color-text-placeholder': 'rgba(30, 41, 59, 0.5)',
      '--color-border-light': 'rgba(30, 41, 59, 0.1)',
      '--color-border-medium': 'rgba(30, 41, 59, 0.2)',
      '--color-border-dark': 'rgba(30, 41, 59, 0.3)',
      '--color-success': '#059669',
      '--color-warning': '#d97706',
      '--color-error': '#dc2626',
      '--color-muted': '#64748b'
    }
  }
];

const palettes = computed(() => isDarkMode.value ? darkPalettes : lightPalettes);
const activePalette = computed(() => isDarkMode.value ? currentPalette.value : currentLightPalette.value);

const colors = ref([
  {
    id: 'primary',
    name: 'Primary Blue',
    variable: '--color-primary',
    value: '#3498db',
    description: 'Main brand color, buttons, links'
  },
  {
    id: 'primary-dark',
    name: 'Primary Blue Dark',
    variable: '--color-primary-dark',
    value: '#2980b9',
    description: 'Hover states, gradients'
  },
  {
    id: 'bg-dark',
    name: 'Background Dark',
    variable: '--color-bg-dark',
    value: '#282c34',
    description: 'Main page background'
  },
  {
    id: 'bg-medium',
    name: 'Background Medium',
    variable: '--color-bg-medium',
    value: '#353b48',
    description: 'Cards, panels, header'
  },
  {
    id: 'bg-light',
    name: 'Background Light',
    variable: '--color-bg-light',
    value: '#3e4451',
    description: 'Input fields, table headers'
  },
  {
    id: 'text-primary',
    name: 'Text Primary',
    variable: '--color-text-primary',
    value: '#d3d7de',
    description: 'Main text color'
  },
  {
    id: 'text-secondary',
    name: 'Text Secondary',
    variable: '--color-text-secondary',
    value: 'rgba(211, 215, 222, 0.8)',
    description: 'Secondary text, descriptions'
  },
  {
    id: 'text-muted',
    name: 'Text Muted',
    variable: '--color-text-muted',
    value: 'rgba(211, 215, 222, 0.7)',
    description: 'Muted text, labels'
  },
  {
    id: 'text-placeholder',
    name: 'Text Placeholder',
    variable: '--color-text-placeholder',
    value: 'rgba(211, 215, 222, 0.5)',
    description: 'Placeholder text'
  },
  {
    id: 'border-light',
    name: 'Border Light',
    variable: '--color-border-light',
    value: 'rgba(211, 215, 222, 0.1)',
    description: 'Card borders, dividers'
  },
  {
    id: 'border-medium',
    name: 'Border Medium',
    variable: '--color-border-medium',
    value: 'rgba(211, 215, 222, 0.2)',
    description: 'Input borders'
  },
  {
    id: 'border-dark',
    name: 'Border Dark',
    variable: '--color-border-dark',
    value: 'rgba(211, 215, 222, 0.3)',
    description: 'Button borders'
  },
  {
    id: 'success',
    name: 'Success Green',
    variable: '--color-success',
    value: '#27ae60',
    description: 'Success states, active indicators'
  },
  {
    id: 'warning',
    name: 'Warning Orange',
    variable: '--color-warning',
    value: '#f39c12',
    description: 'Warning states, pending'
  },
  {
    id: 'error',
    name: 'Error Red',
    variable: '--color-error',
    value: '#e74c3c',
    description: 'Error states, danger actions'
  },
  {
    id: 'muted',
    name: 'Muted Gray',
    variable: '--color-muted',
    value: '#95a5a6',
    description: 'Inactive states'
  }
]);

const updateColor = (colorItem: any, newValue: string) => {
  colorItem.value = newValue;
  document.documentElement.style.setProperty(colorItem.variable, newValue);
};

const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value;
  applyCurrentPalette();
};

const applyPalette = (paletteIndex: number) => {
  if (isDarkMode.value) {
    currentPalette.value = paletteIndex;
  } else {
    currentLightPalette.value = paletteIndex;
  }
  applyCurrentPalette();
};

const applyCurrentPalette = () => {
  const paletteArray = isDarkMode.value ? darkPalettes : lightPalettes;
  const paletteIndex = isDarkMode.value ? currentPalette.value : currentLightPalette.value;
  const palette = paletteArray[paletteIndex];
  
  colors.value.forEach(color => {
    const newValue = palette.colors[color.variable as keyof typeof palette.colors];
    if (newValue) {
      color.value = newValue;
      document.documentElement.style.setProperty(color.variable, newValue);
    }
  });
  showSuccessMessage(`Applied ${palette.name} palette`);
};

const saveSettings = () => {
  isLoading.value = true;
  
  // Save to localStorage
  const colorSettings = colors.value.reduce((acc, color) => {
    acc[color.variable] = color.value;
    return acc;
  }, {} as Record<string, string>);
  
  localStorage.setItem('app-color-settings', JSON.stringify(colorSettings));
  localStorage.setItem('app-current-palette', currentPalette.value.toString());
  localStorage.setItem('app-current-light-palette', currentLightPalette.value.toString());
  localStorage.setItem('app-is-dark-mode', isDarkMode.value.toString());
  
  setTimeout(() => {
    isLoading.value = false;
    showSuccessMessage('Settings saved successfully');
  }, 500);
};

const loadSettings = () => {
  const saved = localStorage.getItem('app-color-settings');
  const savedPalette = localStorage.getItem('app-current-palette');
  const savedLightPalette = localStorage.getItem('app-current-light-palette');
  const savedDarkMode = localStorage.getItem('app-is-dark-mode');
  
  if (saved) {
    try {
      const colorSettings = JSON.parse(saved);
      colors.value.forEach(color => {
        if (colorSettings[color.variable]) {
          color.value = colorSettings[color.variable];
          document.documentElement.style.setProperty(color.variable, color.value);
        }
      });
    } catch (error) {
      console.error('Failed to load color settings:', error);
    }
  }
  
  if (savedPalette) {
    currentPalette.value = parseInt(savedPalette);
  }
  
  if (savedLightPalette) {
    currentLightPalette.value = parseInt(savedLightPalette);
  }
  
  if (savedDarkMode) {
    isDarkMode.value = savedDarkMode === 'true';
  }
  
  // Apply the current palette after loading
  applyCurrentPalette();
};

const showSuccessMessage = (message: string) => {
  successMessage.value = message;
  setTimeout(() => {
    successMessage.value = '';
  }, 3000);
};

onMounted(() => {
  loadSettings();
});
</script>

<template>
  <div class="settings-page">
    <AppHeader />
    
    <header class="settings-header">
      <div class="header-row">
        <div class="header-content">
          <div class="header-info">
            <h1 class="page-title">Settings</h1>
            <p class="page-subtitle">Customize your application appearance</p>
          </div>
        </div>
        
        <div class="header-actions">
          <button @click="isDarkMode = true; currentPalette = 0; applyCurrentPalette()" class="reset-button">
            Reset to Defaults
          </button>
          <button @click="saveSettings" :disabled="isLoading" class="save-button">
            <span v-if="isLoading" class="loading-spinner"></span>
            {{ isLoading ? 'Saving...' : 'Save Settings' }}
          </button>
        </div>
      </div>
    </header>

    <main class="settings-content">
 
      <div class="settings-section">
        <h2 class="section-title">Theme</h2>
        <p class="section-description">
          Switch between dark and light themes and choose your preferred palette.
        </p>
        
        <div class="theme-toggle">
          <div class="toggle-container">
            <span :class="['toggle-label', { active: isDarkMode }]">Dark Mode</span>
            <div class="toggle-switch" @click="toggleDarkMode">
              <div :class="['toggle-slider', { active: !isDarkMode }]"></div>
            </div>
            <span :class="['toggle-label', { active: !isDarkMode }]">Light Mode</span>
          </div>
        </div>

        
        <div class="palette-selector">
          <div class="palette-slider-container">
            <input
              type="range"
              :min="0"
              :max="palettes.length - 1"
              :value="activePalette"
              @input="applyPalette(parseInt(($event.target as HTMLInputElement).value))"
              class="palette-slider"
            />
            <div class="palette-labels">
              <span
                v-for="(palette, index) in palettes"
                :key="index"
                :class="['palette-label', { active: activePalette == index }]"
              >
                {{ palette.name }}
              </span>
            </div>
          </div>
        </div>

        <div v-if="successMessage" class="success-message">
          {{ successMessage }}
        </div>

      </div>

      <div class="settings-section">
        <h2 class="section-title">Individual Colors</h2>
        <p class="section-description">
          Fine-tune individual colors to create your perfect theme.
        </p>
        
        <div class="colors-list">
          <div v-for="color in colors" :key="color.variable" class="color-row">
            <div class="color-preview">
              <div 
                class="color-swatch"
                :style="{ backgroundColor: color.value }"
              ></div>
              <input
                type="color"
                :value="color.value"
                @input="updateColor(color, ($event.target as HTMLInputElement).value)"
                class="color-picker"
              />
            </div>
            <div class="color-info">
              <h3 class="color-name">{{ color.name }}</h3>
              <p class="color-description">{{ color.description }}</p>
            </div>
            <div class="color-details">
              <span class="color-variable">{{ color.variable }}</span>
              <span class="color-value">{{ color.value }}</span>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.settings-page {
  min-height: 100vh;
  background: var(--color-bg-dark, #282c34);
  color: var(--color-text-primary, #d3d7de);
}

.settings-header {
  padding: 20px;
}

.header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 1500px;
  margin: 0 auto;
  gap: 20px;
}

.header-content {
  flex: 1;
  min-width: 0;
}

.header-info {
  width: 100%;
}

.page-title {
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0 0 4px 0;
  margin-top: 12px;
  background: linear-gradient(135deg, var(--color-primary, #3498db), var(--color-primary-dark, #2980b9));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.page-subtitle {
  color: var(--color-text-secondary, rgba(211, 215, 222, 0.8));
  margin: 0;
  font-size: 1rem;
}

.header-actions {
  display: flex;
  gap: 12px;
  align-items: center;
}

.reset-button {
  background: transparent;
  border: 1px solid var(--color-border-dark, rgba(211, 215, 222, 0.3));
  color: var(--color-text-primary, #d3d7de);
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 0.9rem;
}

.reset-button:hover {
  border-color: var(--color-error, #e74c3c);
  background: rgba(231, 76, 60, 0.1);
  color: var(--color-error, #e74c3c);
}

.save-button {
  background: linear-gradient(135deg, var(--color-primary, #3498db), var(--color-primary-dark, #2980b9));
  border: none;
  color: var(--color-text-primary, #d3d7de);
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 8px;
}

.save-button:hover:not(:disabled) {
  background: linear-gradient(135deg, var(--color-primary-dark, #2980b9), var(--color-primary, #3498db));
  transform: translateY(-1px);
}

.save-button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
  transform: none;
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(211, 215, 222, 0.3);
  border-top: 2px solid var(--color-text-primary, #d3d7de);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.settings-content {
  max-width: 1500px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.success-message {
  background: rgba(39, 174, 96, 0.1);
  border: 1px solid rgba(39, 174, 96, 0.3);
  color: var(--color-success, #27ae60);
  padding: 12px 20px;
  border-radius: 8px;
  text-align: center;
  font-weight: 500;
}

.settings-section {
  background: var(--color-bg-medium, #353b48);
  border: 1px solid var(--color-border-light, rgba(211, 215, 222, 0.1));
  border-radius: 16px;
  padding: 32px;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: var(--color-text-primary, #d3d7de);
}

.section-description {
  color: var(--color-text-secondary, rgba(211, 215, 222, 0.8));
  margin: 0 0 32px 0;
  font-size: 1rem;
  line-height: 1.5;
}

.palette-selector {
  margin-bottom: 0;
  margin-top: 20px;
}

.palette-slider-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.palette-slider {
  width: 100%;
  height: 8px;
  border-radius: 4px;
  background: var(--color-bg-light, #3e4451);
  outline: none;
  cursor: pointer;
  -webkit-appearance: none;
}

.palette-slider::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--color-primary, #3498db), var(--color-primary-dark, #2980b9));
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
  transition: all 0.2s ease;
}

.palette-slider::-webkit-slider-thumb:hover {
  transform: scale(1.1);
}

.palette-slider::-moz-range-thumb {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--color-primary, #3498db), var(--color-primary-dark, #2980b9));
  cursor: pointer;
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

.palette-labels {
  display: flex;
  justify-content: space-between;
  padding: 0 12px;
}

.palette-label {
  font-size: 0.9rem;
  color: var(--color-text-muted, rgba(211, 215, 222, 0.7));
  font-weight: 500;
  transition: all 0.2s ease;
  cursor: pointer;
}

.palette-label.active {
  color: var(--color-primary, #3498db);
  font-weight: 600;
}

.theme-toggle {
  margin-bottom: 0;
}

.toggle-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
}

.toggle-label {
  font-size: 1rem;
  font-weight: 500;
  color: var(--color-text-muted, rgba(211, 215, 222, 0.7));
  transition: color 0.2s ease;
}

.toggle-label.active {
  color: var(--color-primary, #3498db);
  font-weight: 600;
}

.toggle-switch {
  position: relative;
  width: 60px;
  height: 30px;
  background: var(--color-bg-light, #3e4451);
  border-radius: 15px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  border: 2px solid var(--color-border-medium, rgba(211, 215, 222, 0.2));
}

.toggle-switch:hover {
  border-color: var(--color-primary, #3498db);
}

.toggle-slider {
  position: absolute;
  top: 2px;
  left: 2px;
  width: 22px;
  height: 22px;
  background: linear-gradient(135deg, var(--color-primary, #3498db), var(--color-primary-dark, #2980b9));
  border-radius: 50%;
  transition: transform 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.toggle-slider.active {
  transform: translateX(30px);
}

.colors-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.color-row {
  display: grid;
  grid-template-columns: auto 1fr auto;
  gap: 20px;
  align-items: center;
  padding: 16px;
  background: var(--color-bg-light, #3e4451);
  border: 1px solid var(--color-border-light, rgba(211, 215, 222, 0.1));
  border-radius: 12px;
  transition: all 0.2s ease;
}

.color-row:hover {
  border-color: var(--color-border-medium, rgba(211, 215, 222, 0.2));
  transform: translateY(-1px);
}

.color-preview {
  position: relative;
  width: 48px;
  height: 48px;
}

.color-swatch {
  width: 48px;
  height: 48px;
  border-radius: 8px;
  border: 2px solid var(--color-border-medium, rgba(211, 215, 222, 0.2));
  cursor: pointer;
  transition: transform 0.2s ease;
  position: absolute;
  top: 0;
  left: 0;
}

.color-swatch:hover {
  transform: scale(1.05);
}

.color-picker {
  width: 48px;
  height: 48px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  background: transparent;
  opacity: 0;
  position: absolute;
  top: 0;
  left: 0;
}

.color-info {
  flex: 1;
  min-width: 0;
}

.color-name {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0 0 4px 0;
  color: var(--color-text-primary, #d3d7de);
}

.color-description {
  color: var(--color-text-secondary, rgba(211, 215, 222, 0.8));
  margin: 0;
  font-size: 0.9rem;
  line-height: 1.4;
}

.color-details {
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-end;
  min-width: 200px;
}

.color-variable {
  font-family: monospace;
  font-size: 0.8rem;
  color: var(--color-primary, #3498db);
  background: rgba(52, 152, 219, 0.1);
  padding: 2px 6px;
  border-radius: 4px;
}

.color-value {
  font-family: monospace;
  font-size: 0.8rem;
  color: var(--color-text-muted, rgba(211, 215, 222, 0.7));
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@media (max-width: 768px) {
  .header-row {
    flex-direction: column;
    gap: 16px;
    align-items: stretch;
  }
  
  .header-actions {
    justify-content: center;
  }
  
  .color-row {
    grid-template-columns: auto 1fr;
    gap: 16px;
  }
  
  .color-details {
    grid-column: 1 / -1;
    align-items: flex-start;
    margin-top: 8px;
  }
  
  .page-title {
    font-size: 1.75rem;
  }
  
  .palette-labels {
    flex-direction: column;
    gap: 8px;
    text-align: center;
  }
}
</style>