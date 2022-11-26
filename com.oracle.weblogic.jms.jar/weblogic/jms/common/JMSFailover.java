package weblogic.jms.common;

import java.rmi.RemoteException;
import javax.naming.NameNotFoundException;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.utils.NestedThrowable;

public final class JMSFailover {
   private final DistributedDestinationImpl[] dests;
   private final Object firstFailure;
   private final int size;
   private int current;

   public JMSFailover(DistributedDestinationImpl[] dests, Object firstFailure) {
      this.dests = dests;
      this.size = dests.length;
      this.current = 0;
      this.firstFailure = firstFailure;
   }

   public static boolean isRecoverableFailure(Throwable t) {
      Throwable start = t;

      while(t != null) {
         if (t instanceof RuntimeException || t instanceof Error) {
            return false;
         }

         if (t instanceof RemoteException) {
            return RemoteHelper.isRecoverablePreInvokeFailure((RemoteException)t);
         }

         if (t.getCause() != null) {
            t = t.getCause();
         } else {
            if (!(t instanceof NestedThrowable)) {
               if (!(t instanceof javax.jms.JMSException) && !(t instanceof NameNotFoundException)) {
                  String exText = t.toString();
                  if (!exText.contains("Destination not found")) {
                     return false;
                  }
               }

               if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                  JMSDebug.JMSMessagePath.debug("failover permitted for ", start);
               } else if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("failover permitted for ", start);
               }

               return true;
            }

            t = ((NestedThrowable)t).getNested();
         }
      }

      return false;
   }

   public DistributedDestinationImpl failover(DistributedDestinationImpl failedDest, Throwable t) {
      if (!isRecoverableFailure(t)) {
         return null;
      } else if (this.current < this.size) {
         return this.dests[this.current] == this.firstFailure && ++this.current >= this.size ? null : this.dests[this.current++];
      } else {
         return null;
      }
   }
}
