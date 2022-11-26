package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;

public interface PSEntryCursorRuntimeMBean extends CursorRuntimeMBean {
   Void remove(String var1, Integer var2) throws ManagementException;

   Void update(String var1, Integer var2, CompositeData var3) throws ManagementException;

   CompositeData getMember(String var1, Integer var2) throws ManagementException;
}
