package weblogic.corba.idl.poa;

import org.omg.CORBA_2_3.portable.ObjectImpl;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAManagerPackage.State;
import weblogic.kernel.KernelStatus;
import weblogic.work.ShutdownCallback;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;
import weblogic.work.WorkManagerService;
import weblogic.work.WorkManagerServiceImpl;

class POAManagerImpl extends ObjectImpl implements POAManager {
   private State state;
   private final WorkManagerService wm;

   public String[] _ids() {
      return new String[]{POAHelper.id()};
   }

   public POAManagerImpl(String name) {
      this.state = State.HOLDING;
      if (KernelStatus.isServer()) {
         this.wm = WorkManagerServiceImpl.createService(name, "POA", "POA");
      } else {
         this.wm = null;
      }

   }

   public WorkManager getWorkManager() {
      return KernelStatus.isServer() ? this.wm.getDelegate() : WorkManagerFactory.getInstance().getDefault();
   }

   public synchronized void activate() throws AdapterInactive {
      if (this.state.equals(State.INACTIVE)) {
         throw new AdapterInactive("activate()");
      } else {
         if (this.wm != null) {
            this.wm.start();
         }

         this.state = State.ACTIVE;
      }
   }

   public synchronized void discard_requests(boolean wait_for_completion) throws AdapterInactive {
      if (this.state.equals(State.INACTIVE)) {
         throw new AdapterInactive("discard_requests()");
      } else {
         this.state = State.DISCARDING;
      }
   }

   public synchronized void hold_requests(boolean wait_for_completion) throws AdapterInactive {
      if (this.state.equals(State.INACTIVE)) {
         throw new AdapterInactive("hold_requests()");
      } else {
         this.state = State.HOLDING;
      }
   }

   public synchronized void deactivate(boolean etherealize_objects, boolean wait_for_completion) throws AdapterInactive {
      if (this.state.equals(State.INACTIVE)) {
         throw new AdapterInactive("deactivate()");
      } else {
         if (this.wm != null) {
            this.wm.shutdown(new ShutdownCallback() {
               public void completed() {
                  synchronized(POAManagerImpl.this) {
                     POAManagerImpl.this.state = State.INACTIVE;
                     POAManagerImpl.this.notify();
                  }
               }
            });
         }

         if (wait_for_completion && this.wm != null) {
            while(this.state != State.INACTIVE) {
               try {
                  this.wait();
               } catch (InterruptedException var4) {
               }
            }
         } else {
            this.state = State.INACTIVE;
         }

      }
   }

   public State get_state() {
      return this.state;
   }
}
