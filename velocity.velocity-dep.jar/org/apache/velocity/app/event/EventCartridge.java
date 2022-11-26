package org.apache.velocity.app.event;

import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalEventContext;

public class EventCartridge implements ReferenceInsertionEventHandler, NullSetEventHandler, MethodExceptionEventHandler {
   private ReferenceInsertionEventHandler rieh = null;
   private NullSetEventHandler nseh = null;
   private MethodExceptionEventHandler meeh = null;

   public boolean addEventHandler(EventHandler ev) {
      if (ev == null) {
         return false;
      } else {
         boolean found = false;
         if (ev instanceof ReferenceInsertionEventHandler) {
            this.rieh = (ReferenceInsertionEventHandler)ev;
            found = true;
         }

         if (ev instanceof NullSetEventHandler) {
            this.nseh = (NullSetEventHandler)ev;
            found = true;
         }

         if (ev instanceof MethodExceptionEventHandler) {
            this.meeh = (MethodExceptionEventHandler)ev;
            found = true;
         }

         return found;
      }
   }

   public boolean removeEventHandler(EventHandler ev) {
      if (ev == null) {
         return false;
      } else {
         boolean found = false;
         if (ev == this.rieh) {
            this.rieh = null;
            found = true;
         }

         if (ev == this.nseh) {
            this.nseh = null;
            found = true;
         }

         if (ev == this.meeh) {
            this.meeh = null;
            found = true;
         }

         return found;
      }
   }

   public Object referenceInsert(String reference, Object value) {
      return this.rieh == null ? value : this.rieh.referenceInsert(reference, value);
   }

   public boolean shouldLogOnNullSet(String lhs, String rhs) {
      return this.nseh == null ? true : this.nseh.shouldLogOnNullSet(lhs, rhs);
   }

   public Object methodException(Class claz, String method, Exception e) throws Exception {
      if (this.meeh == null) {
         throw e;
      } else {
         return this.meeh.methodException(claz, method, e);
      }
   }

   public final boolean attachToContext(Context context) {
      if (context instanceof InternalEventContext) {
         InternalEventContext iec = (InternalEventContext)context;
         iec.attachEventCartridge(this);
         return true;
      } else {
         return false;
      }
   }
}
