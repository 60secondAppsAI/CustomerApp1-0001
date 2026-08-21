import { AppConfig } from '../types/App';

export const mockApps: AppConfig[] = [
  {
    id: 'todo-app',
    name: 'Todo List Manager',
    description: 'A powerful task management application with team collaboration features',
    category: 'Productivity',
    icon: '📝',
    version: '2.1.0',
    author: 'TaskFlow Inc.',
    tags: ['productivity', 'collaboration', 'tasks'],
    lastUpdated: '2024-01-15',
    screenshots: [
      'https://images.pexels.com/photos/5428836/pexels-photo-5428836.jpeg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/3184360/pexels-photo-3184360.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    parameters: [
      {
        id: 'app-name',
        name: 'Application Name',
        type: 'text',
        description: 'The display name for your todo application',
        required: true,
        defaultValue: 'My Todo App'
      },
      {
        id: 'max-tasks',
        name: 'Maximum Tasks',
        type: 'number',
        description: 'Maximum number of tasks allowed per user',
        required: false,
        defaultValue: 100
      },
      {
        id: 'enable-notifications',
        name: 'Enable Notifications',
        type: 'boolean',
        description: 'Allow push notifications for task reminders',
        required: false,
        defaultValue: true
      },
      {
        id: 'theme',
        name: 'Default Theme',
        type: 'select',
        description: 'Choose the default theme for your application',
        required: true,
        defaultValue: 'light',
        options: ['light', 'dark', 'auto']
      }
    ]
  },
  {
    id: 'ecommerce-shop',
    name: 'E-commerce Store',
    description: 'Complete online store solution with payment integration and inventory management',
    category: 'E-commerce',
    icon: '🛒',
    version: '3.0.2',
    author: 'ShopBuilder Pro',
    tags: ['ecommerce', 'sales', 'inventory', 'payments'],
    lastUpdated: '2024-01-12',
    screenshots: [
      'https://images.pexels.com/photos/34577/pexels-photo.jpg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/908292/pexels-photo-908292.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    parameters: [
      {
        id: 'store-name',
        name: 'Store Name',
        type: 'text',
        description: 'The name of your online store',
        required: true,
        defaultValue: 'My Store'
      },
      {
        id: 'currency',
        name: 'Default Currency',
        type: 'select',
        description: 'Primary currency for your store',
        required: true,
        defaultValue: 'USD',
        options: ['USD', 'EUR', 'GBP', 'JPY', 'CAD']
      },
      {
        id: 'inventory-tracking',
        name: 'Enable Inventory Tracking',
        type: 'boolean',
        description: 'Track product quantities and stock levels',
        required: false,
        defaultValue: true
      },
      {
        id: 'description',
        name: 'Store Description',
        type: 'textarea',
        description: 'Brief description of your store',
        required: false,
        defaultValue: ''
      }
    ]
  },
  {
    id: 'blog-cms',
    name: 'Blog CMS',
    description: 'Modern content management system with SEO optimization and social sharing',
    category: 'Content Management',
    icon: '📚',
    version: '1.8.1',
    author: 'ContentCraft',
    tags: ['blog', 'cms', 'content', 'seo'],
    lastUpdated: '2024-01-10',
    screenshots: [
      'https://images.pexels.com/photos/265667/pexels-photo-265667.jpeg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/296282/pexels-photo-296282.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    parameters: [
      {
        id: 'blog-title',
        name: 'Blog Title',
        type: 'text',
        description: 'Main title for your blog',
        required: true,
        defaultValue: 'My Blog'
      },
      {
        id: 'posts-per-page',
        name: 'Posts Per Page',
        type: 'number',
        description: 'Number of posts to display per page',
        required: false,
        defaultValue: 10
      },
      {
        id: 'enable-comments',
        name: 'Enable Comments',
        type: 'boolean',
        description: 'Allow readers to comment on posts',
        required: false,
        defaultValue: true
      },
      {
        id: 'author-bio',
        name: 'Author Bio',
        type: 'textarea',
        description: 'Brief biography of the main author',
        required: false,
        defaultValue: ''
      }
    ]
  },
  {
    id: 'crm-system',
    name: 'Customer CRM',
    description: 'Comprehensive customer relationship management with sales pipeline tracking',
    category: 'Business',
    icon: '👥',
    version: '4.2.1',
    author: 'BizTools Ltd.',
    tags: ['crm', 'sales', 'customers', 'pipeline'],
    lastUpdated: '2024-01-08',
    screenshots: [
      'https://images.pexels.com/photos/3184465/pexels-photo-3184465.jpeg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/3184302/pexels-photo-3184302.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    parameters: [
      {
        id: 'company-name',
        name: 'Company Name',
        type: 'text',
        description: 'Your company name',
        required: true,
        defaultValue: ''
      },
      {
        id: 'pipeline-stages',
        name: 'Pipeline Stages',
        type: 'number',
        description: 'Number of stages in your sales pipeline',
        required: false,
        defaultValue: 5
      },
      {
        id: 'auto-assign',
        name: 'Auto-assign Leads',
        type: 'boolean',
        description: 'Automatically assign new leads to sales reps',
        required: false,
        defaultValue: false
      },
      {
        id: 'industry',
        name: 'Industry',
        type: 'select',
        description: 'Your primary industry',
        required: true,
        defaultValue: 'technology',
        options: ['technology', 'healthcare', 'finance', 'retail', 'manufacturing', 'other']
      }
    ]
  },
  {
    id: 'dashboard-analytics',
    name: 'Analytics Dashboard',
    description: 'Real-time data visualization and reporting dashboard with custom widgets',
    category: 'Analytics',
    icon: '📊',
    version: '2.5.0',
    author: 'DataViz Pro',
    tags: ['analytics', 'dashboard', 'reporting', 'visualization'],
    lastUpdated: '2024-01-14',
    screenshots: [
      'https://images.pexels.com/photos/590016/pexels-photo-590016.jpg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/669610/pexels-photo-669610.jpg?auto=compress&cs=tinysrgb&w=800'
    ],
    parameters: [
      {
        id: 'dashboard-name',
        name: 'Dashboard Name',
        type: 'text',
        description: 'Name for your analytics dashboard',
        required: true,
        defaultValue: 'Analytics Dashboard'
      },
      {
        id: 'refresh-interval',
        name: 'Refresh Interval (seconds)',
        type: 'number',
        description: 'How often to refresh data automatically',
        required: false,
        defaultValue: 30
      },
      {
        id: 'enable-alerts',
        name: 'Enable Alerts',
        type: 'boolean',
        description: 'Send alerts when metrics exceed thresholds',
        required: false,
        defaultValue: true
      },
      {
        id: 'data-retention',
        name: 'Data Retention Period',
        type: 'select',
        description: 'How long to keep historical data',
        required: true,
        defaultValue: '90-days',
        options: ['30-days', '90-days', '1-year', '2-years', 'indefinite']
      }
    ]
  },
  {
    id: 'project-manager',
    name: 'Project Manager',
    description: 'Agile project management with Kanban boards, time tracking, and team collaboration',
    category: 'Project Management',
    icon: '📋',
    version: '3.1.4',
    author: 'TeamFlow Solutions',
    tags: ['project', 'agile', 'kanban', 'collaboration'],
    lastUpdated: '2024-01-11',
    screenshots: [
      'https://images.pexels.com/photos/3184418/pexels-photo-3184418.jpeg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/7688336/pexels-photo-7688336.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    parameters: [
      {
        id: 'project-name',
        name: 'Default Project Name',
        type: 'text',
        description: 'Name for your initial project',
        required: true,
        defaultValue: 'New Project'
      },
      {
        id: 'team-size',
        name: 'Expected Team Size',
        type: 'number',
        description: 'Number of team members expected',
        required: false,
        defaultValue: 5
      },
      {
        id: 'time-tracking',
        name: 'Enable Time Tracking',
        type: 'boolean',
        description: 'Track time spent on tasks',
        required: false,
        defaultValue: true
      },
      {
        id: 'methodology',
        name: 'Project Methodology',
        type: 'select',
        description: 'Choose your preferred project methodology',
        required: true,
        defaultValue: 'agile',
        options: ['agile', 'waterfall', 'kanban', 'scrum', 'hybrid']
      }
    ]
  }
];