package weblogic.jms.backend;

import weblogic.application.ModuleException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSMessageCursorRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.messaging.kernel.runtime.MessageCursorRuntimeImpl;
import weblogic.messaging.runtime.CursorDelegate;

public class JMSMessageCursorRuntimeImpl extends MessageCursorRuntimeImpl implements JMSMessageCursorRuntimeMBean {
   public JMSMessageCursorRuntimeImpl(String name, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(name, parent, registerNow);
   }

   public JMSMessageCursorRuntimeImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public JMSMessageCursorRuntimeImpl(String name, boolean registerNow) throws ManagementException {
      super(name, registerNow);
   }

   public Long sort(String cursorHandle, Long start, String[] fields, Boolean[] ascending) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);

      try {
         return new Long(((JMSMessageCursorDelegate)delegate).sort(start, fields, ascending));
      } catch (ModuleException var7) {
         throw new ManagementException("Error while sorting cursor " + cursorHandle, var7);
      }
   }
}
