package weblogic.connector.external;

public interface LoggingInfo {
   String getLogFilename();

   boolean isLoggingEnabled();

   String getRotationType();

   String getRotationTime();

   boolean isNumberOfFilesLimited();

   int getFileCount();

   int getFileSizeLimit();

   int getFileTimeSpan();

   boolean isRotateLogOnStartup();

   String getLogFileRotationDir();
}
