package weblogic.logging;

import com.bea.logging.BaseLogEntry;

public interface LogEntry extends BaseLogEntry {
   ThrowableInfo getThrowableInfo();

   void setThrowableInfo(ThrowableInfo var1);
}
