package org.python.google.common.base;

import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
final class Absent extends Optional {
   static final Absent INSTANCE = new Absent();
   private static final long serialVersionUID = 0L;

   static Optional withType() {
      return INSTANCE;
   }

   private Absent() {
   }

   public boolean isPresent() {
      return false;
   }

   public Object get() {
      throw new IllegalStateException("Optional.get() cannot be called on an absent value");
   }

   public Object or(Object defaultValue) {
      return Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
   }

   public Optional or(Optional secondChoice) {
      return (Optional)Preconditions.checkNotNull(secondChoice);
   }

   public Object or(Supplier supplier) {
      return Preconditions.checkNotNull(supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
   }

   @Nullable
   public Object orNull() {
      return null;
   }

   public Set asSet() {
      return Collections.emptySet();
   }

   public Optional transform(Function function) {
      Preconditions.checkNotNull(function);
      return Optional.absent();
   }

   public boolean equals(@Nullable Object object) {
      return object == this;
   }

   public int hashCode() {
      return 2040732332;
   }

   public String toString() {
      return "Optional.absent()";
   }

   private Object readResolve() {
      return INSTANCE;
   }
}
