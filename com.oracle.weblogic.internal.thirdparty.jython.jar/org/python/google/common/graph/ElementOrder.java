package org.python.google.common.graph;

import java.util.Comparator;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.Ordering;
import org.python.google.errorprone.annotations.Immutable;

@Immutable
@Beta
public final class ElementOrder {
   private final Type type;
   @Nullable
   private final Comparator comparator;

   private ElementOrder(Type type, @Nullable Comparator comparator) {
      this.type = (Type)Preconditions.checkNotNull(type);
      this.comparator = comparator;
      Preconditions.checkState(type == ElementOrder.Type.SORTED == (comparator != null));
   }

   public static ElementOrder unordered() {
      return new ElementOrder(ElementOrder.Type.UNORDERED, (Comparator)null);
   }

   public static ElementOrder insertion() {
      return new ElementOrder(ElementOrder.Type.INSERTION, (Comparator)null);
   }

   public static ElementOrder natural() {
      return new ElementOrder(ElementOrder.Type.SORTED, Ordering.natural());
   }

   public static ElementOrder sorted(Comparator comparator) {
      return new ElementOrder(ElementOrder.Type.SORTED, comparator);
   }

   public Type type() {
      return this.type;
   }

   public Comparator comparator() {
      if (this.comparator != null) {
         return this.comparator;
      } else {
         throw new UnsupportedOperationException("This ordering does not define a comparator.");
      }
   }

   public boolean equals(@Nullable Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof ElementOrder)) {
         return false;
      } else {
         ElementOrder other = (ElementOrder)obj;
         return this.type == other.type && Objects.equal(this.comparator, other.comparator);
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.type, this.comparator);
   }

   public String toString() {
      MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper((Object)this).add("type", this.type);
      if (this.comparator != null) {
         helper.add("comparator", this.comparator);
      }

      return helper.toString();
   }

   Map createMap(int expectedSize) {
      switch (this.type) {
         case UNORDERED:
            return Maps.newHashMapWithExpectedSize(expectedSize);
         case INSERTION:
            return Maps.newLinkedHashMapWithExpectedSize(expectedSize);
         case SORTED:
            return Maps.newTreeMap(this.comparator());
         default:
            throw new AssertionError();
      }
   }

   ElementOrder cast() {
      return this;
   }

   public static enum Type {
      UNORDERED,
      INSERTION,
      SORTED;
   }
}
