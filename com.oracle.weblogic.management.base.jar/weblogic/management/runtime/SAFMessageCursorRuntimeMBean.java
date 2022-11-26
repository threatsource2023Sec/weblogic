package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface SAFMessageCursorRuntimeMBean extends MessageCursorRuntimeMBean {
   Long sort(String var1, Long var2, String[] var3, Boolean[] var4) throws ManagementException;
}
