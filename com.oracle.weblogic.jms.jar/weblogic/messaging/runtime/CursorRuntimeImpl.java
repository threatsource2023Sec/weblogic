package weblogic.messaging.runtime;

import java.util.HashMap;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.CursorRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public abstract class CursorRuntimeImpl extends RuntimeMBeanDelegate implements CursorRuntimeMBean {
   private transient long counter;
   private transient HashMap cursors = new HashMap();

   public CursorRuntimeImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public CursorRuntimeImpl(String name, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(name, parent, registerNow);
   }

   public CursorRuntimeImpl(String name, boolean registerNow) throws ManagementException {
      super(name, registerNow);
   }

   public Long getCursorStartPosition(String handle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(handle);
      return delegate.getCursorStartPosition();
   }

   public Long getCursorEndPosition(String handle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(handle);
      return delegate.getCursorEndPosition();
   }

   public CompositeData[] getItems(String cursorHandle, Long start, Integer count) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);

      try {
         return delegate.getItems(start, count);
      } catch (OpenDataException var6) {
         throw new ManagementException("Failed to get " + count + " items at " + start + " on cursor " + cursorHandle + ": " + var6.toString(), var6);
      }
   }

   public CompositeData[] getNext(String cursorHandle, Integer count) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);

      try {
         return delegate.getNext(count);
      } catch (OpenDataException var5) {
         throw new ManagementException("Failed to get next " + count + " items  on cursor " + cursorHandle, var5);
      }
   }

   public CompositeData[] getPrevious(String cursorHandle, Integer count) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(cursorHandle);

      try {
         return delegate.getPrevious(count);
      } catch (OpenDataException var5) {
         throw new ManagementException("Failed to get previous " + count + " items  on cursor " + cursorHandle, var5);
      }
   }

   public Long getCursorSize(String handle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(handle);
      return delegate.getCursorSize();
   }

   public Void closeCursor(String handle) throws ManagementException {
      CursorDelegate delegate = this.getCursorDelegate(handle);
      delegate.close();
      this.removeCursorDelegate(handle);
      return null;
   }

   public synchronized String addCursorDelegate(CursorDelegate delegate) {
      String newHandle = this.name + ":" + this.counter++;
      delegate.setHandle(newHandle);
      this.cursors.put(newHandle, delegate);
      return newHandle;
   }

   protected synchronized CursorDelegate getCursorDelegate(String handle) throws ManagementException {
      CursorDelegate delegate = (CursorDelegate)this.cursors.get(handle);
      if (delegate == null) {
         throw new ManagementException("no cursor matching handle " + handle);
      } else {
         return delegate;
      }
   }

   public synchronized CursorDelegate removeCursorDelegate(String handle) {
      return (CursorDelegate)this.cursors.remove(handle);
   }
}
