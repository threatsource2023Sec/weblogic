package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;

public interface CursorRuntimeMBean extends RuntimeMBean {
   Long getCursorStartPosition(String var1) throws ManagementException;

   Long getCursorEndPosition(String var1) throws ManagementException;

   CompositeData[] getItems(String var1, Long var2, Integer var3) throws ManagementException;

   CompositeData[] getNext(String var1, Integer var2) throws ManagementException;

   CompositeData[] getPrevious(String var1, Integer var2) throws ManagementException;

   Long getCursorSize(String var1) throws ManagementException;

   Void closeCursor(String var1) throws ManagementException;
}
