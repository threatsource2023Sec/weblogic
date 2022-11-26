package weblogic.management.configuration;

import java.io.OutputStream;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface LogFileMBean extends ConfigurationMBean {
   String SIZE_OR_TIME = "bySizeOrTime";
   String SIZE = "bySize";
   String TIME = "byTime";
   String NONE = "none";
   String TIME_FORMAT = "H:mm";
   String DEFAULT_FILE_NAME = "weblogic.log";
   String DEFAULT_FILE_EXTENSION = ".log";
   String FILE_SEP = "/";
   String LOGS_DIR = "logs/";
   int MAX_ROTATED_FILES = 99999;

   String getDateFormatPattern();

   void setDateFormatPattern(String var1);

   String getFileName();

   void setFileName(String var1) throws InvalidAttributeValueException;

   String getRotationType();

   void setRotationType(String var1) throws InvalidAttributeValueException;

   void setNumberOfFilesLimited(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isNumberOfFilesLimited();

   int getFileCount();

   void setFileCount(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getFileTimeSpan();

   String getRotationTime();

   void setRotationTime(String var1) throws InvalidAttributeValueException;

   void setFileTimeSpan(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getFileTimeSpanFactor();

   void setFileTimeSpanFactor(long var1);

   int getFileMinSize();

   void setFileMinSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getRotateLogOnStartup();

   void setRotateLogOnStartup(boolean var1);

   String getLogFileRotationDir();

   void setLogFileRotationDir(String var1);

   /** @deprecated */
   @Deprecated
   String computeLogFilePath();

   String getLogFilePath();

   String getLogRotationDirPath();

   OutputStream getOutputStream();

   void setOutputStream(OutputStream var1);

   int getBufferSizeKB();

   void setBufferSizeKB(int var1);
}
