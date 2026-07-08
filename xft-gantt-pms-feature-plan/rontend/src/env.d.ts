/// <reference types="vite/client" />

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string
  readonly VITE_APP_TITLE: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}

declare module 'element-plus' {
  import type { Plugin, Component } from 'vue'

  export const ElMessage: {
    success(msg: string): void
    warning(msg: string): void
    error(msg: string): void
    info(msg: string): void
  }
  export const ElMessageBox: any
  export const ElNotification: any

  export interface FormInstance {
    validate(): Promise<boolean>
    resetFields(): void
    clearValidate(): void
  }

  export type FormRules = Record<string, any[]>

  const ElementPlus: Plugin
  export default ElementPlus
}

declare module 'element-plus/dist/locale/zh-cn.mjs' {
  const zhCn: Record<string, any>
  export default zhCn
}

declare module '@element-plus/icons-vue' {
  import type { Component } from 'vue'
  const icons: Record<string, Component>
  export default icons
}
