package org.python.google.common.collect;

import java.io.Serializable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
final class UsingToStringOrdering extends Ordering implements Serializable {
   static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
   private static final long serialVersionUID = 0L;

   public int compare(Object left, Object right) {
      return left.toString().compareTo(right.toString());
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public String toString() {
      return "Ordering.usingToString()";
   }

   private UsingToStringOrdering() {
   }
}
