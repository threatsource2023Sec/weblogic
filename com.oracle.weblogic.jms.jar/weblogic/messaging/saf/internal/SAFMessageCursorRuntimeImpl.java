package weblogic.messaging.saf.internal;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SAFMessageCursorRuntimeMBean;
import weblogic.messaging.kernel.runtime.MessageCursorRuntimeImpl;
import weblogic.messaging.runtime.CursorDelegate;

public class SAFMessageCursorRuntimeImpl extends MessageCursorRuntimeImpl implements SAFMessageCursorRuntimeMBean {
   static final long serialVersionUID = 7182873948110830340L;

   public SAFMessageCursorRuntimeImpl(String name, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(name, parent, registerNow);
   }

   public Long sort(String cursorHandle, Long start, String[] fields, Boolean[] ascending) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);
      return new Long(((SAFMessageCursorDelegate)delegate).sort(start, fields, ascending));
   }
}
