package weblogic.diagnostics.accessor;

import weblogic.diagnostics.accessor.parser.LogRecordParser;

public interface LogAccessorConfiguration extends AccessorConfiguration {
   LogRecordParser getRecordParser();

   String getLogFilePath();

   String getLogFileRotationDirectory();
}
