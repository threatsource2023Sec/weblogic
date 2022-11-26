package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.TaskScheduler;
import com.bea.core.repackaged.springframework.scheduling.Trigger;
import com.bea.core.repackaged.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduledTaskRegistrar implements ScheduledTaskHolder, InitializingBean, DisposableBean {
   @Nullable
   private TaskScheduler taskScheduler;
   @Nullable
   private ScheduledExecutorService localExecutor;
   @Nullable
   private List triggerTasks;
   @Nullable
   private List cronTasks;
   @Nullable
   private List fixedRateTasks;
   @Nullable
   private List fixedDelayTasks;
   private final Map unresolvedTasks = new HashMap(16);
   private final Set scheduledTasks = new LinkedHashSet(16);

   public void setTaskScheduler(TaskScheduler taskScheduler) {
      Assert.notNull(taskScheduler, (String)"TaskScheduler must not be null");
      this.taskScheduler = taskScheduler;
   }

   public void setScheduler(@Nullable Object scheduler) {
      if (scheduler == null) {
         this.taskScheduler = null;
      } else if (scheduler instanceof TaskScheduler) {
         this.taskScheduler = (TaskScheduler)scheduler;
      } else {
         if (!(scheduler instanceof ScheduledExecutorService)) {
            throw new IllegalArgumentException("Unsupported scheduler type: " + scheduler.getClass());
         }

         this.taskScheduler = new ConcurrentTaskScheduler((ScheduledExecutorService)scheduler);
      }

   }

   @Nullable
   public TaskScheduler getScheduler() {
      return this.taskScheduler;
   }

   public void setTriggerTasks(Map triggerTasks) {
      this.triggerTasks = new ArrayList();
      triggerTasks.forEach((task, trigger) -> {
         this.addTriggerTask(new TriggerTask(task, trigger));
      });
   }

   public void setTriggerTasksList(List triggerTasks) {
      this.triggerTasks = triggerTasks;
   }

   public List getTriggerTaskList() {
      return this.triggerTasks != null ? Collections.unmodifiableList(this.triggerTasks) : Collections.emptyList();
   }

   public void setCronTasks(Map cronTasks) {
      this.cronTasks = new ArrayList();
      cronTasks.forEach(this::addCronTask);
   }

   public void setCronTasksList(List cronTasks) {
      this.cronTasks = cronTasks;
   }

   public List getCronTaskList() {
      return this.cronTasks != null ? Collections.unmodifiableList(this.cronTasks) : Collections.emptyList();
   }

   public void setFixedRateTasks(Map fixedRateTasks) {
      this.fixedRateTasks = new ArrayList();
      fixedRateTasks.forEach(this::addFixedRateTask);
   }

   public void setFixedRateTasksList(List fixedRateTasks) {
      this.fixedRateTasks = fixedRateTasks;
   }

   public List getFixedRateTaskList() {
      return this.fixedRateTasks != null ? Collections.unmodifiableList(this.fixedRateTasks) : Collections.emptyList();
   }

   public void setFixedDelayTasks(Map fixedDelayTasks) {
      this.fixedDelayTasks = new ArrayList();
      fixedDelayTasks.forEach(this::addFixedDelayTask);
   }

   public void setFixedDelayTasksList(List fixedDelayTasks) {
      this.fixedDelayTasks = fixedDelayTasks;
   }

   public List getFixedDelayTaskList() {
      return this.fixedDelayTasks != null ? Collections.unmodifiableList(this.fixedDelayTasks) : Collections.emptyList();
   }

   public void addTriggerTask(Runnable task, Trigger trigger) {
      this.addTriggerTask(new TriggerTask(task, trigger));
   }

   public void addTriggerTask(TriggerTask task) {
      if (this.triggerTasks == null) {
         this.triggerTasks = new ArrayList();
      }

      this.triggerTasks.add(task);
   }

   public void addCronTask(Runnable task, String expression) {
      this.addCronTask(new CronTask(task, expression));
   }

   public void addCronTask(CronTask task) {
      if (this.cronTasks == null) {
         this.cronTasks = new ArrayList();
      }

      this.cronTasks.add(task);
   }

   public void addFixedRateTask(Runnable task, long interval) {
      this.addFixedRateTask(new IntervalTask(task, interval, 0L));
   }

   public void addFixedRateTask(IntervalTask task) {
      if (this.fixedRateTasks == null) {
         this.fixedRateTasks = new ArrayList();
      }

      this.fixedRateTasks.add(task);
   }

   public void addFixedDelayTask(Runnable task, long delay) {
      this.addFixedDelayTask(new IntervalTask(task, delay, 0L));
   }

   public void addFixedDelayTask(IntervalTask task) {
      if (this.fixedDelayTasks == null) {
         this.fixedDelayTasks = new ArrayList();
      }

      this.fixedDelayTasks.add(task);
   }

   public boolean hasTasks() {
      return !CollectionUtils.isEmpty((Collection)this.triggerTasks) || !CollectionUtils.isEmpty((Collection)this.cronTasks) || !CollectionUtils.isEmpty((Collection)this.fixedRateTasks) || !CollectionUtils.isEmpty((Collection)this.fixedDelayTasks);
   }

   public void afterPropertiesSet() {
      this.scheduleTasks();
   }

   protected void scheduleTasks() {
      if (this.taskScheduler == null) {
         this.localExecutor = Executors.newSingleThreadScheduledExecutor();
         this.taskScheduler = new ConcurrentTaskScheduler(this.localExecutor);
      }

      Iterator var1;
      if (this.triggerTasks != null) {
         var1 = this.triggerTasks.iterator();

         while(var1.hasNext()) {
            TriggerTask task = (TriggerTask)var1.next();
            this.addScheduledTask(this.scheduleTriggerTask(task));
         }
      }

      if (this.cronTasks != null) {
         var1 = this.cronTasks.iterator();

         while(var1.hasNext()) {
            CronTask task = (CronTask)var1.next();
            this.addScheduledTask(this.scheduleCronTask(task));
         }
      }

      IntervalTask task;
      if (this.fixedRateTasks != null) {
         var1 = this.fixedRateTasks.iterator();

         while(var1.hasNext()) {
            task = (IntervalTask)var1.next();
            this.addScheduledTask(this.scheduleFixedRateTask(task));
         }
      }

      if (this.fixedDelayTasks != null) {
         var1 = this.fixedDelayTasks.iterator();

         while(var1.hasNext()) {
            task = (IntervalTask)var1.next();
            this.addScheduledTask(this.scheduleFixedDelayTask(task));
         }
      }

   }

   private void addScheduledTask(@Nullable ScheduledTask task) {
      if (task != null) {
         this.scheduledTasks.add(task);
      }

   }

   @Nullable
   public ScheduledTask scheduleTriggerTask(TriggerTask task) {
      ScheduledTask scheduledTask = (ScheduledTask)this.unresolvedTasks.remove(task);
      boolean newTask = false;
      if (scheduledTask == null) {
         scheduledTask = new ScheduledTask(task);
         newTask = true;
      }

      if (this.taskScheduler != null) {
         scheduledTask.future = this.taskScheduler.schedule(task.getRunnable(), task.getTrigger());
      } else {
         this.addTriggerTask(task);
         this.unresolvedTasks.put(task, scheduledTask);
      }

      return newTask ? scheduledTask : null;
   }

   @Nullable
   public ScheduledTask scheduleCronTask(CronTask task) {
      ScheduledTask scheduledTask = (ScheduledTask)this.unresolvedTasks.remove(task);
      boolean newTask = false;
      if (scheduledTask == null) {
         scheduledTask = new ScheduledTask(task);
         newTask = true;
      }

      if (this.taskScheduler != null) {
         scheduledTask.future = this.taskScheduler.schedule(task.getRunnable(), task.getTrigger());
      } else {
         this.addCronTask(task);
         this.unresolvedTasks.put(task, scheduledTask);
      }

      return newTask ? scheduledTask : null;
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public ScheduledTask scheduleFixedRateTask(IntervalTask task) {
      FixedRateTask taskToUse = task instanceof FixedRateTask ? (FixedRateTask)task : new FixedRateTask(task.getRunnable(), task.getInterval(), task.getInitialDelay());
      return this.scheduleFixedRateTask(taskToUse);
   }

   @Nullable
   public ScheduledTask scheduleFixedRateTask(FixedRateTask task) {
      ScheduledTask scheduledTask = (ScheduledTask)this.unresolvedTasks.remove(task);
      boolean newTask = false;
      if (scheduledTask == null) {
         scheduledTask = new ScheduledTask(task);
         newTask = true;
      }

      if (this.taskScheduler != null) {
         if (task.getInitialDelay() > 0L) {
            Date startTime = new Date(System.currentTimeMillis() + task.getInitialDelay());
            scheduledTask.future = this.taskScheduler.scheduleAtFixedRate(task.getRunnable(), startTime, task.getInterval());
         } else {
            scheduledTask.future = this.taskScheduler.scheduleAtFixedRate(task.getRunnable(), task.getInterval());
         }
      } else {
         this.addFixedRateTask(task);
         this.unresolvedTasks.put(task, scheduledTask);
      }

      return newTask ? scheduledTask : null;
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public ScheduledTask scheduleFixedDelayTask(IntervalTask task) {
      FixedDelayTask taskToUse = task instanceof FixedDelayTask ? (FixedDelayTask)task : new FixedDelayTask(task.getRunnable(), task.getInterval(), task.getInitialDelay());
      return this.scheduleFixedDelayTask(taskToUse);
   }

   @Nullable
   public ScheduledTask scheduleFixedDelayTask(FixedDelayTask task) {
      ScheduledTask scheduledTask = (ScheduledTask)this.unresolvedTasks.remove(task);
      boolean newTask = false;
      if (scheduledTask == null) {
         scheduledTask = new ScheduledTask(task);
         newTask = true;
      }

      if (this.taskScheduler != null) {
         if (task.getInitialDelay() > 0L) {
            Date startTime = new Date(System.currentTimeMillis() + task.getInitialDelay());
            scheduledTask.future = this.taskScheduler.scheduleWithFixedDelay(task.getRunnable(), startTime, task.getInterval());
         } else {
            scheduledTask.future = this.taskScheduler.scheduleWithFixedDelay(task.getRunnable(), task.getInterval());
         }
      } else {
         this.addFixedDelayTask(task);
         this.unresolvedTasks.put(task, scheduledTask);
      }

      return newTask ? scheduledTask : null;
   }

   public Set getScheduledTasks() {
      return Collections.unmodifiableSet(this.scheduledTasks);
   }

   public void destroy() {
      Iterator var1 = this.scheduledTasks.iterator();

      while(var1.hasNext()) {
         ScheduledTask task = (ScheduledTask)var1.next();
         task.cancel();
      }

      if (this.localExecutor != null) {
         this.localExecutor.shutdownNow();
      }

   }
}
