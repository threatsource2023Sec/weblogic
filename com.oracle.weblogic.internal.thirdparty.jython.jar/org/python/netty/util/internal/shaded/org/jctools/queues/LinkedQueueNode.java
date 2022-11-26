package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

final class LinkedQueueNode {
   private static final long NEXT_OFFSET;
   private Object value;
   private volatile LinkedQueueNode next;

   LinkedQueueNode() {
      this((Object)null);
   }

   LinkedQueueNode(Object val) {
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

   public void soNext(LinkedQueueNode n) {
      UnsafeAccess.UNSAFE.putOrderedObject(this, NEXT_OFFSET, n);
   }

   public LinkedQueueNode lvNext() {
      return this.next;
   }

   static {
      try {
         NEXT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(LinkedQueueNode.class.getDeclaredField("next"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
