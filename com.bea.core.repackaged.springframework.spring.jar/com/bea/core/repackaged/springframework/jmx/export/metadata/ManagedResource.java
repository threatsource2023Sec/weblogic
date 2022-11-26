package com.bea.core.repackaged.springframework.jmx.export.metadata;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class ManagedResource extends AbstractJmxAttribute {
   @Nullable
   private String objectName;
   private boolean log = false;
   @Nullable
   private String logFile;
   @Nullable
   private String persistPolicy;
   private int persistPeriod = -1;
   @Nullable
   private String persistName;
   @Nullable
   private String persistLocation;

   public void setObjectName(@Nullable String objectName) {
      this.objectName = objectName;
   }

   @Nullable
   public String getObjectName() {
      return this.objectName;
   }

   public void setLog(boolean log) {
      this.log = log;
   }

   public boolean isLog() {
      return this.log;
   }

   public void setLogFile(@Nullable String logFile) {
      this.logFile = logFile;
   }

   @Nullable
   public String getLogFile() {
      return this.logFile;
   }

   public void setPersistPolicy(@Nullable String persistPolicy) {
      this.persistPolicy = persistPolicy;
   }

   @Nullable
   public String getPersistPolicy() {
      return this.persistPolicy;
   }

   public void setPersistPeriod(int persistPeriod) {
      this.persistPeriod = persistPeriod;
   }

   public int getPersistPeriod() {
      return this.persistPeriod;
   }

   public void setPersistName(@Nullable String persistName) {
      this.persistName = persistName;
   }

   @Nullable
   public String getPersistName() {
      return this.persistName;
   }

   public void setPersistLocation(@Nullable String persistLocation) {
      this.persistLocation = persistLocation;
   }

   @Nullable
   public String getPersistLocation() {
      return this.persistLocation;
   }
}
