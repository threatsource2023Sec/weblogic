package org.python.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;

@GwtCompatible(
   serializable = true,
   emulated = true
)
abstract class ImmutableAsList extends ImmutableList {
   abstract ImmutableCollection delegateCollection();

   public boolean contains(Object target) {
      return this.delegateCollection().contains(target);
   }

   public int size() {
      return this.delegateCollection().size();
   }

   public boolean isEmpty() {
      return this.delegateCollection().isEmpty();
   }

   boolean isPartialView() {
      return this.delegateCollection().isPartialView();
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   @GwtIncompatible
   Object writeReplace() {
      return new SerializedForm(this.delegateCollection());
   }

   @GwtIncompatible
   static class SerializedForm implements Serializable {
      final ImmutableCollection collection;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableCollection collection) {
         this.collection = collection;
      }

      Object readResolve() {
         return this.collection.asList();
      }
   }
}
