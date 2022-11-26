package kodo.jdo;

import javax.jdo.listener.AttachCallback;
import javax.jdo.listener.ClearCallback;
import javax.jdo.listener.DeleteCallback;
import javax.jdo.listener.DetachCallback;
import javax.jdo.listener.LoadCallback;
import javax.jdo.listener.StoreCallback;
import kodo.runtime.PostAttachCallback;
import kodo.runtime.PostDetachCallback;
import kodo.runtime.PreAttachCallback;
import kodo.runtime.PreDetachCallback;
import org.apache.openjpa.event.LifecycleCallbacks;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.LifecycleMetaData;

class InstanceCallbacksAdapter implements LifecycleCallbacks {
   private int _events = 0;

   public static boolean install(ClassMetaData meta) {
      Class cls = meta.getDescribedType();
      int events = 0;
      if (LoadCallback.class.isAssignableFrom(cls)) {
         events |= 8;
      }

      if (StoreCallback.class.isAssignableFrom(cls)) {
         events |= 16;
      }

      if (ClearCallback.class.isAssignableFrom(cls)) {
         events |= 64;
      }

      if (DeleteCallback.class.isAssignableFrom(cls)) {
         events |= 256;
      }

      if (DetachCallback.class.isAssignableFrom(cls)) {
         events |= 16384;
         events |= 32768;
      }

      if (AttachCallback.class.isAssignableFrom(cls)) {
         events |= 65536;
         events |= 131072;
      }

      if (PreAttachCallback.class.isAssignableFrom(cls)) {
         events |= 65536;
      }

      if (PostAttachCallback.class.isAssignableFrom(cls)) {
         events |= 131072;
      }

      if (PreDetachCallback.class.isAssignableFrom(cls)) {
         events |= 16384;
      }

      if (PostDetachCallback.class.isAssignableFrom(cls)) {
         events |= 32768;
      }

      if (events == 0) {
         return false;
      } else {
         LifecycleMetaData lm = meta.getLifecycleMetaData();
         lm.setIgnoreSuperclassCallbacks(4 | 2);
         LifecycleCallbacks[] calls = new LifecycleCallbacks[]{new InstanceCallbacksAdapter(events)};
         if ((events & 8) != 0) {
            lm.setDeclaredCallbacks(2, calls, 0);
         }

         if ((events & 16) != 0) {
            lm.setDeclaredCallbacks(3, calls, 0);
         }

         if ((events & 256) != 0) {
            lm.setDeclaredCallbacks(7, calls, 0);
         }

         if ((events & 64) != 0) {
            lm.setDeclaredCallbacks(5, calls, 0);
         }

         if ((events & 16384) != 0) {
            lm.setDeclaredCallbacks(13, calls, 0);
            lm.setDeclaredCallbacks(14, calls, 0);
         }

         if ((events & 65536) != 0) {
            lm.setDeclaredCallbacks(15, calls, 0);
            lm.setDeclaredCallbacks(16, calls, 0);
         }

         return true;
      }
   }

   private InstanceCallbacksAdapter(int events) {
      this._events = events;
   }

   public boolean hasCallback(Object obj, int eventType) {
      return (this._events & 2 << eventType) != 0;
   }

   public void makeCallback(Object obj, Object related, int eventType) {
      switch (eventType) {
         case 2:
            ((LoadCallback)obj).jdoPostLoad();
            break;
         case 3:
            ((StoreCallback)obj).jdoPreStore();
         case 4:
         case 6:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         default:
            break;
         case 5:
            ((ClearCallback)obj).jdoPreClear();
            break;
         case 7:
            ((DeleteCallback)obj).jdoPreDelete();
            break;
         case 13:
            if (obj instanceof PreDetachCallback) {
               ((PreDetachCallback)obj).jdoPreDetach();
            } else {
               ((DetachCallback)obj).jdoPreDetach();
            }
            break;
         case 14:
            if (obj instanceof PostAttachCallback) {
               ((PostDetachCallback)obj).jdoPostDetach(related);
            } else {
               ((DetachCallback)obj).jdoPostDetach(related);
            }
            break;
         case 15:
            if (obj instanceof PreAttachCallback) {
               ((PreAttachCallback)obj).jdoPreAttach();
            } else {
               ((AttachCallback)obj).jdoPreAttach();
            }
            break;
         case 16:
            if (obj instanceof PostAttachCallback) {
               ((PostAttachCallback)obj).jdoPostAttach(related);
            } else {
               ((AttachCallback)obj).jdoPostAttach(related);
            }
      }

   }
}
