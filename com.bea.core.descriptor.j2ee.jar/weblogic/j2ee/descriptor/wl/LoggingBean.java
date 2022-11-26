package weblogic.j2ee.descriptor.wl;

public interface LoggingBean {
   String TIME_FORMAT = "H:mm";

   String getLogFilename();

   void setLogFilename(String var1);

   boolean isLoggingEnabled();

   void setLoggingEnabled(boolean var1);

   String getRotationType();

   void setRotationType(String var1);

   boolean isNumberOfFilesLimited();

   void setNumberOfFilesLimited(boolean var1);

   int getFileCount();

   void setFileCount(int var1);

   int getFileSizeLimit();

   void setFileSizeLimit(int var1);

   boolean isRotateLogOnStartup();

   void setRotateLogOnStartup(boolean var1);

   String getLogFileRotationDir();

   void setLogFileRotationDir(String var1);

   String getRotationTime();

   void setRotationTime(String var1);

   int getFileTimeSpan();

   void setFileTimeSpan(int var1);

   String getDateFormatPattern();

   void setDateFormatPattern(String var1);

   String getId();

   void setId(String var1);
}
