package org.python.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicReference;

public final class LinkedQueueAtomicNode extends AtomicReference {
   private static final long serialVersionUID = 2404266111789071508L;
   private Object value;

   LinkedQueueAtomicNode() {
   }

   LinkedQueueAtomicNode(Object val) {
      this.spValue(val);
   }

   public Object getAndNullValue() {
      Object temp = this.lpValue();
      this.spValue((Object)null);
      return temp;
   }

   public Object lpValue() {
      return this.value;
   }

   public void spValue(Object newValue) {
      this.value = newValue;
   }

   public void soNext(LinkedQueueAtomicNode n) {
      this.lazySet(n);
   }

   public LinkedQueueAtomicNode lvNext() {
      return (LinkedQueueAtomicNode)this.get();
   }
}
