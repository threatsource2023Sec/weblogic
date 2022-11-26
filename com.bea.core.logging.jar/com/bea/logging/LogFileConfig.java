package com.bea.logging;

import weblogic.utils.PropertyHelper;

public interface LogFileConfig {
   int MAX_ROTATED_FILES = 99999;
   String TIME_FORMAT = "k:mm";
   long FILESIZE_LIMIT = 1048576L * (long)Math.max(500, PropertyHelper.getInteger("weblogic.log.LogFileMaxSizeLimitMB", 0));
   String BY_SIZE = "bySize";
   String BY_TIME = "byTime";
   String BY_SIZE_OR_TIME = "bySizeOrTime";
   String NONE = "none";

   String getLogFileSeverity();

   void setLogFileSeverity(String var1);

   int getRotationTimeSpan();

   void setRotationTimeSpan(int var1);

   long getRotationTimeSpanFactor();

   void setRotationTimeSpanFactor(long var1);

   String getRotationType();

   void setRotationType(String var1);

   String getBaseLogFileName();

   void setBaseLogFileName(String var1);

   String getLogFileRotationDir();

   void setLogFileRotationDir(String var1);

   boolean isRotateLogOnStartupEnabled();

   void setRotateLogOnStartupEnabled(boolean var1);

   int getRotationSize();

   void setRotationSize(int var1);

   boolean isNumberOfFilesLimited();

   void setNumberOfFilesLimited(boolean var1);

   int getRotatedFileCount();

   void setRotatedFileCount(int var1);

   String getRotationTime();

   void setRotationTime(String var1);

   String getLogFileFilterName();

   void setLogFileFilterName(String var1);
}
