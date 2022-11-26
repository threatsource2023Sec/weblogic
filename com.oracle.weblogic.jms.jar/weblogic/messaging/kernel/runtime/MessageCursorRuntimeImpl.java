package weblogic.messaging.kernel.runtime;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.MessageCursorRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.messaging.runtime.CursorDelegate;
import weblogic.messaging.runtime.CursorRuntimeImpl;

public class MessageCursorRuntimeImpl extends CursorRuntimeImpl implements MessageCursorRuntimeMBean {
   public MessageCursorRuntimeImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public MessageCursorRuntimeImpl(String name, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(name, parent, registerNow);
   }

   public MessageCursorRuntimeImpl(String name, boolean registerNow) throws ManagementException {
      super(name, registerNow);
   }

   public CompositeData getMessage(String cursorHandle, String messageID) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);

      try {
         return ((MessageCursorDelegate)delegate).getMessage(messageID);
      } catch (OpenDataException var5) {
         throw new ManagementException("Failed to get message for message ID " + messageID + " on cursor " + cursorHandle, var5);
      }
   }

   public CompositeData getMessage(String cursorHandle, Long messageHandle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);

      try {
         return ((MessageCursorDelegate)delegate).getMessage(messageHandle);
      } catch (OpenDataException var5) {
         throw new ManagementException("Failed to get message for message handle " + messageHandle + " on cursor " + cursorHandle, var5);
      }
   }
}
