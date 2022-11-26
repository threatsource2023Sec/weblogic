package com.octetstring.vde.util;

public class LogRotateTask implements TimedActivityTask {
   private int hour;
   private int minute;
   private int numlogs;
   private boolean srun;

   public LogRotateTask(int hour, int minute, int numlogs) {
      this.hour = hour;
      this.minute = minute;
      this.numlogs = numlogs;
   }

   public void runTask() {
      Logger.getInstance().rotate(this.numlogs);
   }

   public int getHour() {
      return this.hour;
   }

   public int getMinute() {
      return this.minute;
   }

   public boolean hasRun() {
      return this.srun;
   }

   public void setRun(boolean srun) {
      this.srun = srun;
   }
}
