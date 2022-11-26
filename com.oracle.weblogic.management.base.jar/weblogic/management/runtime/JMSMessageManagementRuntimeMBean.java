package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;

public interface JMSMessageManagementRuntimeMBean extends JMSMessageCursorRuntimeMBean {
   Long getMessagesMovedCurrentCount();

   Long getMessagesDeletedCurrentCount();

   String getMessages(String var1, Integer var2) throws ManagementException;

   String getMessages(String var1, Integer var2, Integer var3) throws ManagementException;

   CompositeData getMessage(String var1) throws ManagementException;

   Integer moveMessages(String var1, CompositeData var2) throws ManagementException;

   Integer moveMessages(String var1, CompositeData var2, Integer var3) throws ManagementException;

   Integer deleteMessages(String var1) throws ManagementException;

   Void importMessages(CompositeData[] var1, Boolean var2) throws ManagementException;

   Void importMessages(CompositeData[] var1, Boolean var2, Boolean var3) throws ManagementException;
}
