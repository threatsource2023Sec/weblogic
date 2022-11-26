package weblogic.messaging.path;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PSEntryCursorRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.messaging.runtime.ArrayCursorRuntimeDelegate;
import weblogic.messaging.runtime.CursorDelegate;

public class PSEntryCursorRuntimeDelegate extends ArrayCursorRuntimeDelegate implements PSEntryCursorRuntimeMBean {
   public PSEntryCursorRuntimeDelegate(String mbeanName, RuntimeMBean parent) throws ManagementException {
      super(mbeanName, parent);
   }

   public Void remove(String cursorHandle, Integer itemHandle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);
      ((PSEntryCursorDelegate)delegate).remove(itemHandle);
      return null;
   }

   public Void update(String cursorHandle, Integer itemHandle, CompositeData newMember) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);
      ((PSEntryCursorDelegate)delegate).update(itemHandle, newMember);
      return null;
   }

   public CompositeData getMember(String cursorHandle, Integer itemHandle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);
      return ((PSEntryCursorDelegate)delegate).getMember(itemHandle);
   }
}
