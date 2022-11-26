package org.python.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.ForOverride;

@GwtCompatible
public abstract class Equivalence {
   protected Equivalence() {
   }

   public final boolean equivalent(@Nullable Object a, @Nullable Object b) {
      if (a == b) {
         return true;
      } else {
         return a != null && b != null ? this.doEquivalent(a, b) : false;
      }
   }

   @ForOverride
   protected abstract boolean doEquivalent(Object var1, Object var2);

   public final int hash(@Nullable Object t) {
      return t == null ? 0 : this.doHash(t);
   }

   @ForOverride
   protected abstract int doHash(Object var1);

   public final Equivalence onResultOf(Function function) {
      return new FunctionalEquivalence(function, this);
   }

   public final Wrapper wrap(@Nullable Object reference) {
      return new Wrapper(this, reference);
   }

   @GwtCompatible(
      serializable = true
   )
   public final Equivalence pairwise() {
      return new PairwiseEquivalence(this);
   }

   public final Predicate equivalentTo(@Nullable Object target) {
      return new EquivalentToPredicate(this, target);
   }

   public static Equivalence equals() {
      return Equivalence.Equals.INSTANCE;
   }

   public static Equivalence identity() {
      return Equivalence.Identity.INSTANCE;
   }

   static final class Identity extends Equivalence implements Serializable {
      static final Identity INSTANCE = new Identity();
      private static final long serialVersionUID = 1L;

      protected boolean doEquivalent(Object a, Object b) {
         return false;
      }

      protected int doHash(Object o) {
         return System.identityHashCode(o);
      }

      private Object readResolve() {
         return INSTANCE;
      }
   }

   static final class Equals extends Equivalence implements Serializable {
      static final Equals INSTANCE = new Equals();
      private static final long serialVersionUID = 1L;

      protected boolean doEquivalent(Object a, Object b) {
         return a.equals(b);
      }

      protected int doHash(Object o) {
         return o.hashCode();
      }

      private Object readResolve() {
         return INSTANCE;
      }
   }

   private static final class EquivalentToPredicate implements Predicate, Serializable {
      private final Equivalence equivalence;
      @Nullable
      private final Object target;
      private static final long serialVersionUID = 0L;

      EquivalentToPredicate(Equivalence equivalence, @Nullable Object target) {
         this.equivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
         this.target = target;
      }

      public boolean apply(@Nullable Object input) {
         return this.equivalence.equivalent(input, this.target);
      }

      public boolean equals(@Nullable Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof EquivalentToPredicate)) {
            return false;
         } else {
            EquivalentToPredicate that = (EquivalentToPredicate)obj;
            return this.equivalence.equals(that.equivalence) && Objects.equal(this.target, that.target);
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.equivalence, this.target);
      }

      public String toString() {
         return this.equivalence + ".equivalentTo(" + this.target + ")";
      }
   }

   public static final class Wrapper implements Serializable {
      private final Equivalence equivalence;
      @Nullable
      private final Object reference;
      private static final long serialVersionUID = 0L;

      private Wrapper(Equivalence equivalence, @Nullable Object reference) {
         this.equivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
         this.reference = reference;
      }

      @Nullable
      public Object get() {
         return this.reference;
      }

      public boolean equals(@Nullable Object obj) {
         if (obj == this) {
            return true;
         } else {
            if (obj instanceof Wrapper) {
               Wrapper that = (Wrapper)obj;
               if (this.equivalence.equals(that.equivalence)) {
                  Equivalence equivalence = this.equivalence;
                  return equivalence.equivalent(this.reference, that.reference);
               }
            }

            return false;
         }
      }

      public int hashCode() {
         return this.equivalence.hash(this.reference);
      }

      public String toString() {
         return this.equivalence + ".wrap(" + this.reference + ")";
      }

      // $FF: synthetic method
      Wrapper(Equivalence x0, Object x1, Object x2) {
         this(x0, x1);
      }
   }
}
