package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface DataSourceLogFileMBean extends LogFileMBean {
   String DEFAULT_DATASOURCE_FILE_NAME = "datasource.log";

   String getFileName();

   void setFileName(String var1) throws InvalidAttributeValueException;
}
