package weblogic.diagnostics.accessor.config;

import java.io.File;
import java.util.Map;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.LogAccessorConfiguration;
import weblogic.diagnostics.accessor.parser.DefaultRecordParser;
import weblogic.diagnostics.accessor.parser.LogRecordParser;

public class DefaultLogAccessorConfiguration implements LogAccessorConfiguration {
   private String name;
   private LogRecordParser recordParser;
   private String logFilePath;
   private String logFileRotationDirectory;
   private boolean isModifiable;

   public DefaultLogAccessorConfiguration(String name, String logFilePath) {
      this(name, logFilePath, false);
   }

   public DefaultLogAccessorConfiguration(String name, String logFilePath, boolean isModifiable) {
      this(name, logFilePath, (String)null, isModifiable);
   }

   public DefaultLogAccessorConfiguration(String name, String logFilePath, String logFileRotationDirectory, boolean isModifiable) {
      this(name, new DefaultRecordParser(), logFilePath, logFileRotationDirectory, isModifiable);
   }

   public DefaultLogAccessorConfiguration(String name, LogRecordParser recordParser, String logFilePath, String logFileRotationDirectory, boolean isModifiable) {
      this.name = name;
      this.recordParser = recordParser;
      this.logFilePath = logFilePath;
      this.isModifiable = isModifiable;
      if (logFileRotationDirectory == null) {
         File f = (new File(logFilePath)).getParentFile();
         if (f != null) {
            logFileRotationDirectory = f.getAbsolutePath();
         } else {
            logFileRotationDirectory = ".";
         }
      }

      this.logFileRotationDirectory = logFileRotationDirectory;
   }

   public String getLogFilePath() {
      return this.logFilePath;
   }

   public String getLogFileRotationDirectory() {
      return this.logFileRotationDirectory;
   }

   public LogRecordParser getRecordParser() {
      return this.recordParser;
   }

   public Map getAccessorParameters() {
      return null;
   }

   public ColumnInfo[] getColumns() {
      return this.recordParser.getColumnInfos();
   }

   public String getName() {
      return this.name;
   }

   public boolean isModifiableConfiguration() {
      return this.isModifiable;
   }
}
