package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Filter;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Sink;

public class NullFilterImpl implements Filter {
   private Kernel kernel;
   private Set sinks;

   public NullFilterImpl(Kernel kernel) {
      this.kernel = kernel;
   }

   public synchronized void subscribe(Sink sink, Expression expression) throws KernelException {
      if (expression != null) {
         throw new KernelException("Only null subscriptions are allowed");
      } else {
         if (this.sinks == null) {
            this.sinks = new LinkedHashSet();
         }

         if (this.sinks.contains(sink)) {
            throw new KernelException("Duplicate subscription");
         } else {
            this.sinks.add(sink);
         }
      }
   }

   public synchronized void resubscribe(Sink sink, Expression expression) throws KernelException {
      if (expression != null) {
         throw new KernelException("Only null subscriptions are allowed");
      } else {
         if (this.sinks == null) {
            this.sinks = new LinkedHashSet();
         }

         this.sinks.add(sink);
      }
   }

   public synchronized void unsubscribe(Sink sink) {
      if (this.sinks != null) {
         this.sinks.remove(sink);
      }

   }

   public synchronized Collection match(MessageElement message) {
      return this.sinks == null ? null : new ArrayList(this.sinks);
   }

   public boolean match(MessageElement message, Expression expression) {
      return expression == null;
   }

   public Expression createExpression(Object object) {
      return null;
   }
}
