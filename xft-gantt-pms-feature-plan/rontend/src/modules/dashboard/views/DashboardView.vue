<template>
  <div class="dashboard-view">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>计划总数</template>
          <div class="stat-value">-</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>进行中任务</template>
          <div class="stat-value">-</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>已完成任务</template>
          <div class="stat-value">-</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>逾期任务</template>
          <div class="stat-value" style="color: #f56c6c">-</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped lang="scss">
.stat-value {
  font-size: 36px;
  font-weight: bold;
  text-align: center;
  color: #409eff;
}
</style>
