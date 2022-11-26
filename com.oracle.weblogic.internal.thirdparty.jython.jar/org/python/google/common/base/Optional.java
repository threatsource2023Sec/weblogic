package org.python.google.common.base;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
public abstract class Optional implements Serializable {
   private static final long serialVersionUID = 0L;

   public static Optional absent() {
      return Absent.withType();
   }

   public static Optional of(Object reference) {
      return new Present(Preconditions.checkNotNull(reference));
   }

   public static Optional fromNullable(@Nullable Object nullableReference) {
      return (Optional)(nullableReference == null ? absent() : new Present(nullableReference));
   }

   Optional() {
   }

   public abstract boolean isPresent();

   public abstract Object get();

   public abstract Object or(Object var1);

   public abstract Optional or(Optional var1);

   @Beta
   public abstract Object or(Supplier var1);

   @Nullable
   public abstract Object orNull();

   public abstract Set asSet();

   public abstract Optional transform(Function var1);

   public abstract boolean equals(@Nullable Object var1);

   public abstract int hashCode();

   public abstract String toString();

   @Beta
   public static Iterable presentInstances(final Iterable optionals) {
      Preconditions.checkNotNull(optionals);
      return new Iterable() {
         public Iterator iterator() {
            return new AbstractIterator() {
               private final Iterator iterator = (Iterator)Preconditions.checkNotNull(optionals.iterator());

               protected Object computeNext() {
                  while(true) {
                     if (this.iterator.hasNext()) {
                        Optional optional = (Optional)this.iterator.next();
                        if (!optional.isPresent()) {
                           continue;
                        }

                        return optional.get();
                     }

                     return this.endOfData();
                  }
               }
            };
         }
      };
   }
}
