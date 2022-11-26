package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class ForwardingObject {
   protected ForwardingObject() {
   }

   protected abstract Object delegate();

   public String toString() {
      return this.delegate().toString();
   }
}
