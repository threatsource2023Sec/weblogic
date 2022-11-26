package weblogic.management.runtime;

import java.util.SortedSet;
import weblogic.management.ManagementException;

public interface LogRuntimeMBean extends RuntimeMBean {
   void forceLogRotation() throws ManagementException;

   void ensureLogOpened() throws ManagementException;

   void flushLog() throws ManagementException;

   boolean isLogFileStreamOpened();

   String getCurrentLogFile();

   String getLogRotationDir();

   SortedSet getRotatedLogFiles();
}
