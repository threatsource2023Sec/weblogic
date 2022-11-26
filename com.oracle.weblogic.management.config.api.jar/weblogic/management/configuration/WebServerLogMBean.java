package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface WebServerLogMBean extends LogFileMBean {
   String DEFAULT_ACCESS_LOG_FILE_NAME = "access.log";
   String COMMON_LOG_FORMAT = "common";
   String EXTENDED_LOG_FORMAT = "extended";
   String DATE_COLUMN_NAME = "time";
   String TIME_COLUMN_NAME = "time";

   String getFileName();

   void setFileName(String var1) throws InvalidAttributeValueException;

   void setLoggingEnabled(boolean var1);

   boolean isLoggingEnabled();

   String getELFFields();

   void setELFFields(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getLogFileFormat();

   void setLogFileFormat(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isLogTimeInGMT();

   void setLogTimeInGMT(boolean var1);

   boolean isLogMilliSeconds();

   void setLogMilliSeconds(boolean var1);
}
