package net.shibboleth.utilities.java.support.collection;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class Pair {
   private Object first;
   private Object second;

   public Pair() {
   }

   public Pair(@Nullable Object newFirst, @Nullable Object newSecond) {
      this.first = newFirst;
      this.second = newSecond;
   }

   public Pair(@Nonnull Pair pair) {
      Constraint.isNotNull(pair, "Pair to be copied can not be null");
      this.first = pair.getFirst();
      this.second = pair.getSecond();
   }

   @Nullable
   public Object getFirst() {
      return this.first;
   }

   public void setFirst(@Nullable Object newFirst) {
      this.first = newFirst;
   }

   @Nullable
   public Object getSecond() {
      return this.second;
   }

   public void setSecond(@Nullable Object newSecond) {
      this.second = newSecond;
   }

   public boolean equals(@Nullable Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Pair)) {
         return false;
      } else {
         Pair otherPair = (Pair)o;
         return Objects.equals(this.getFirst(), otherPair.getFirst()) && Objects.equals(this.getSecond(), otherPair.getSecond());
      }
   }

   public int hashCode() {
      return com.google.common.base.Objects.hashCode(new Object[]{this.first, this.second});
   }

   @Nonnull
   public String toString() {
      return MoreObjects.toStringHelper(this).add("first", this.first).add("second", this.second).toString();
   }
}
