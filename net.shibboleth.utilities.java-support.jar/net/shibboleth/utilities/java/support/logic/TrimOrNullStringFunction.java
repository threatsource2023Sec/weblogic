package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@ThreadSafe
public class TrimOrNullStringFunction implements Function {
   public static final TrimOrNullStringFunction INSTANCE = new TrimOrNullStringFunction();

   @Nullable
   @NotEmpty
   public String apply(@Nullable String input) {
      return StringSupport.trimOrNull(input);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         return obj == this ? true : obj instanceof TrimOrNullStringFunction;
      }
   }

   public int hashCode() {
      return 31;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).toString();
   }
}
