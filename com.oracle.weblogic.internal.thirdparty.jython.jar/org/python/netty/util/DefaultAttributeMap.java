package org.python.netty.util;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultAttributeMap implements AttributeMap {
   private static final AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
   private static final int BUCKET_SIZE = 4;
   private static final int MASK = 3;
   private volatile AtomicReferenceArray attributes;

   public Attribute attr(AttributeKey key) {
      if (key == null) {
         throw new NullPointerException("key");
      } else {
         AtomicReferenceArray attributes = this.attributes;
         if (attributes == null) {
            attributes = new AtomicReferenceArray(4);
            if (!updater.compareAndSet(this, (Object)null, attributes)) {
               attributes = this.attributes;
            }
         }

         int i = index(key);
         DefaultAttribute head = (DefaultAttribute)attributes.get(i);
         if (head == null) {
            head = new DefaultAttribute();
            DefaultAttribute attr = new DefaultAttribute(head, key);
            head.next = attr;
            attr.prev = head;
            if (attributes.compareAndSet(i, (Object)null, head)) {
               return attr;
            }

            head = (DefaultAttribute)attributes.get(i);
         }

         synchronized(head) {
            DefaultAttribute curr = head;

            while(true) {
               DefaultAttribute next = curr.next;
               if (next == null) {
                  DefaultAttribute attr = new DefaultAttribute(head, key);
                  curr.next = attr;
                  attr.prev = curr;
                  return attr;
               }

               if (next.key == key && !next.removed) {
                  return next;
               }

               curr = next;
            }
         }
      }
   }

   public boolean hasAttr(AttributeKey key) {
      if (key == null) {
         throw new NullPointerException("key");
      } else {
         AtomicReferenceArray attributes = this.attributes;
         if (attributes == null) {
            return false;
         } else {
            int i = index(key);
            DefaultAttribute head = (DefaultAttribute)attributes.get(i);
            if (head == null) {
               return false;
            } else {
               synchronized(head) {
                  for(DefaultAttribute curr = head.next; curr != null; curr = curr.next) {
                     if (curr.key == key && !curr.removed) {
                        return true;
                     }
                  }

                  return false;
               }
            }
         }
      }
   }

   private static int index(AttributeKey key) {
      return key.id() & 3;
   }

   private static final class DefaultAttribute extends AtomicReference implements Attribute {
      private static final long serialVersionUID = -2661411462200283011L;
      private final DefaultAttribute head;
      private final AttributeKey key;
      private DefaultAttribute prev;
      private DefaultAttribute next;
      private volatile boolean removed;

      DefaultAttribute(DefaultAttribute head, AttributeKey key) {
         this.head = head;
         this.key = key;
      }

      DefaultAttribute() {
         this.head = this;
         this.key = null;
      }

      public AttributeKey key() {
         return this.key;
      }

      public Object setIfAbsent(Object value) {
         while(true) {
            if (!this.compareAndSet((Object)null, value)) {
               Object old = this.get();
               if (old == null) {
                  continue;
               }

               return old;
            }

            return null;
         }
      }

      public Object getAndRemove() {
         this.removed = true;
         Object oldValue = this.getAndSet((Object)null);
         this.remove0();
         return oldValue;
      }

      public void remove() {
         this.removed = true;
         this.set((Object)null);
         this.remove0();
      }

      private void remove0() {
         synchronized(this.head) {
            if (this.prev != null) {
               this.prev.next = this.next;
               if (this.next != null) {
                  this.next.prev = this.prev;
               }

               this.prev = null;
               this.next = null;
            }
         }
      }
   }
}
