package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;

public interface MessageCursorRuntimeMBean extends CursorRuntimeMBean {
   CompositeData getMessage(String var1, String var2) throws ManagementException;

   CompositeData getMessage(String var1, Long var2) throws ManagementException;
}
