export interface AppConfig {
  id: string;
  name: string;
  description: string;
  category: string;
  icon: string;
  version: string;
  author: string;
  tags: string[];
  parameters: ConfigParameter[];
  screenshots: string[];
  lastUpdated: string;
}

export interface ConfigParameter {
  id: string;
  name: string;
  type: 'text' | 'number' | 'boolean' | 'select' | 'textarea';
  description: string;
  required: boolean;
  defaultValue?: any;
  options?: string[];
}