package org.python.google.common.collect;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true
)
final class ByFunctionOrdering extends Ordering implements Serializable {
   final Function function;
   final Ordering ordering;
   private static final long serialVersionUID = 0L;

   ByFunctionOrdering(Function function, Ordering ordering) {
      this.function = (Function)Preconditions.checkNotNull(function);
      this.ordering = (Ordering)Preconditions.checkNotNull(ordering);
   }

   public int compare(Object left, Object right) {
      return this.ordering.compare(this.function.apply(left), this.function.apply(right));
   }

   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ByFunctionOrdering)) {
         return false;
      } else {
         ByFunctionOrdering that = (ByFunctionOrdering)object;
         return this.function.equals(that.function) && this.ordering.equals(that.ordering);
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.function, this.ordering);
   }

   public String toString() {
      return this.ordering + ".onResultOf(" + this.function + ")";
   }
}
