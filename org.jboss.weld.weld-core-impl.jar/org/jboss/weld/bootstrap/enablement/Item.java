package org.jboss.weld.bootstrap.enablement;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.weld.util.Preconditions;

class Item implements Comparable {
   static final int ITEM_PRIORITY_SCALE_POWER = 10;
   private final Class javaClass;
   private final int originalPriority;
   private final AtomicInteger priority;

   Item(Class javaClass, int priority) {
      this(javaClass, priority, priority);
   }

   Item(Class javaClass, int originalPriority, int priority) {
      Preconditions.checkArgumentNotNull(javaClass, "javaClass");
      Preconditions.checkArgumentNotNull(priority, "priority");
      Preconditions.checkArgumentNotNull(originalPriority, "originalPriority");
      this.javaClass = javaClass;
      this.priority = new AtomicInteger(priority);
      this.originalPriority = originalPriority;
   }

   void scalePriority() {
      this.priority.getAndUpdate((p) -> {
         return p * 10;
      });
   }

   Class getJavaClass() {
      return this.javaClass;
   }

   int getPriority() {
      return this.priority.get();
   }

   int getOriginalPriority() {
      return this.originalPriority;
   }

   int getNumberOfScaling() {
      int current = this.priority.get();
      if (current == this.originalPriority) {
         return 0;
      } else {
         int scaling = 0;

         do {
            current /= 10;
            ++scaling;
         } while(current != this.originalPriority);

         return scaling;
      }
   }

   public int compareTo(Item o) {
      int p1 = this.priority.get();
      int p2 = o.priority.get();
      return p1 == p2 ? this.javaClass.getName().compareTo(o.javaClass.getName()) : p1 - p2;
   }

   public int hashCode() {
      return this.javaClass.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof Item) {
         Item that = (Item)obj;
         return Objects.equals(this.javaClass, that.javaClass);
      } else {
         return false;
      }
   }

   public String toString() {
      return "[Class=" + this.javaClass + ", priority=" + this.priority + "]";
   }
}
