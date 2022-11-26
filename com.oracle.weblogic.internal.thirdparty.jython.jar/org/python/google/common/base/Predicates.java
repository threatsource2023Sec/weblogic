package org.python.google.common.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;

@GwtCompatible(
   emulated = true
)
public final class Predicates {
   private static final Joiner COMMA_JOINER = Joiner.on(',');

   private Predicates() {
   }

   @GwtCompatible(
      serializable = true
   )
   public static Predicate alwaysTrue() {
      return Predicates.ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
   }

   @GwtCompatible(
      serializable = true
   )
   public static Predicate alwaysFalse() {
      return Predicates.ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
   }

   @GwtCompatible(
      serializable = true
   )
   public static Predicate isNull() {
      return Predicates.ObjectPredicate.IS_NULL.withNarrowedType();
   }

   @GwtCompatible(
      serializable = true
   )
   public static Predicate notNull() {
      return Predicates.ObjectPredicate.NOT_NULL.withNarrowedType();
   }

   public static Predicate not(Predicate predicate) {
      return new NotPredicate(predicate);
   }

   public static Predicate and(Iterable components) {
      return new AndPredicate(defensiveCopy(components));
   }

   public static Predicate and(Predicate... components) {
      return new AndPredicate(defensiveCopy((Object[])components));
   }

   public static Predicate and(Predicate first, Predicate second) {
      return new AndPredicate(asList((Predicate)Preconditions.checkNotNull(first), (Predicate)Preconditions.checkNotNull(second)));
   }

   public static Predicate or(Iterable components) {
      return new OrPredicate(defensiveCopy(components));
   }

   public static Predicate or(Predicate... components) {
      return new OrPredicate(defensiveCopy((Object[])components));
   }

   public static Predicate or(Predicate first, Predicate second) {
      return new OrPredicate(asList((Predicate)Preconditions.checkNotNull(first), (Predicate)Preconditions.checkNotNull(second)));
   }

   public static Predicate equalTo(@Nullable Object target) {
      return (Predicate)(target == null ? isNull() : new IsEqualToPredicate(target));
   }

   @GwtIncompatible
   public static Predicate instanceOf(Class clazz) {
      return new InstanceOfPredicate(clazz);
   }

   /** @deprecated */
   @Deprecated
   @GwtIncompatible
   @Beta
   public static Predicate assignableFrom(Class clazz) {
      return subtypeOf(clazz);
   }

   @GwtIncompatible
   @Beta
   public static Predicate subtypeOf(Class clazz) {
      return new SubtypeOfPredicate(clazz);
   }

   public static Predicate in(Collection target) {
      return new InPredicate(target);
   }

   public static Predicate compose(Predicate predicate, Function function) {
      return new CompositionPredicate(predicate, function);
   }

   @GwtIncompatible
   public static Predicate containsPattern(String pattern) {
      return new ContainsPatternFromStringPredicate(pattern);
   }

   @GwtIncompatible("java.util.regex.Pattern")
   public static Predicate contains(Pattern pattern) {
      return new ContainsPatternPredicate(new JdkPattern(pattern));
   }

   private static List asList(Predicate first, Predicate second) {
      return Arrays.asList(first, second);
   }

   private static List defensiveCopy(Object... array) {
      return defensiveCopy((Iterable)Arrays.asList(array));
   }

   static List defensiveCopy(Iterable iterable) {
      ArrayList list = new ArrayList();
      Iterator var2 = iterable.iterator();

      while(var2.hasNext()) {
         Object element = var2.next();
         list.add(Preconditions.checkNotNull(element));
      }

      return list;
   }

   @GwtIncompatible
   private static class ContainsPatternFromStringPredicate extends ContainsPatternPredicate {
      private static final long serialVersionUID = 0L;

      ContainsPatternFromStringPredicate(String string) {
         super(Platform.compilePattern(string));
      }

      public String toString() {
         return "Predicates.containsPattern(" + this.pattern.pattern() + ")";
      }
   }

   @GwtIncompatible
   private static class ContainsPatternPredicate implements Predicate, Serializable {
      final CommonPattern pattern;
      private static final long serialVersionUID = 0L;

      ContainsPatternPredicate(CommonPattern pattern) {
         this.pattern = (CommonPattern)Preconditions.checkNotNull(pattern);
      }

      public boolean apply(CharSequence t) {
         return this.pattern.matcher(t).find();
      }

      public int hashCode() {
         return Objects.hashCode(this.pattern.pattern(), this.pattern.flags());
      }

      public boolean equals(@Nullable Object obj) {
         if (!(obj instanceof ContainsPatternPredicate)) {
            return false;
         } else {
            ContainsPatternPredicate that = (ContainsPatternPredicate)obj;
            return Objects.equal(this.pattern.pattern(), that.pattern.pattern()) && this.pattern.flags() == that.pattern.flags();
         }
      }

      public String toString() {
         String patternString = MoreObjects.toStringHelper((Object)this.pattern).add("pattern", this.pattern.pattern()).add("pattern.flags", this.pattern.flags()).toString();
         return "Predicates.contains(" + patternString + ")";
      }
   }

   private static class CompositionPredicate implements Predicate, Serializable {
      final Predicate p;
      final Function f;
      private static final long serialVersionUID = 0L;

      private CompositionPredicate(Predicate p, Function f) {
         this.p = (Predicate)Preconditions.checkNotNull(p);
         this.f = (Function)Preconditions.checkNotNull(f);
      }

      public boolean apply(@Nullable Object a) {
         return this.p.apply(this.f.apply(a));
      }

      public boolean equals(@Nullable Object obj) {
         if (!(obj instanceof CompositionPredicate)) {
            return false;
         } else {
            CompositionPredicate that = (CompositionPredicate)obj;
            return this.f.equals(that.f) && this.p.equals(that.p);
         }
      }

      public int hashCode() {
         return this.f.hashCode() ^ this.p.hashCode();
      }

      public String toString() {
         return this.p + "(" + this.f + ")";
      }

      // $FF: synthetic method
      CompositionPredicate(Predicate x0, Function x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class InPredicate implements Predicate, Serializable {
      private final Collection target;
      private static final long serialVersionUID = 0L;

      private InPredicate(Collection target) {
         this.target = (Collection)Preconditions.checkNotNull(target);
      }

      public boolean apply(@Nullable Object t) {
         try {
            return this.target.contains(t);
         } catch (NullPointerException var3) {
            return false;
         } catch (ClassCastException var4) {
            return false;
         }
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof InPredicate) {
            InPredicate that = (InPredicate)obj;
            return this.target.equals(that.target);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.target.hashCode();
      }

      public String toString() {
         return "Predicates.in(" + this.target + ")";
      }

      // $FF: synthetic method
      InPredicate(Collection x0, Object x1) {
         this(x0);
      }
   }

   @GwtIncompatible
   private static class SubtypeOfPredicate implements Predicate, Serializable {
      private final Class clazz;
      private static final long serialVersionUID = 0L;

      private SubtypeOfPredicate(Class clazz) {
         this.clazz = (Class)Preconditions.checkNotNull(clazz);
      }

      public boolean apply(Class input) {
         return this.clazz.isAssignableFrom(input);
      }

      public int hashCode() {
         return this.clazz.hashCode();
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof SubtypeOfPredicate) {
            SubtypeOfPredicate that = (SubtypeOfPredicate)obj;
            return this.clazz == that.clazz;
         } else {
            return false;
         }
      }

      public String toString() {
         return "Predicates.subtypeOf(" + this.clazz.getName() + ")";
      }

      // $FF: synthetic method
      SubtypeOfPredicate(Class x0, Object x1) {
         this(x0);
      }
   }

   @GwtIncompatible
   private static class InstanceOfPredicate implements Predicate, Serializable {
      private final Class clazz;
      private static final long serialVersionUID = 0L;

      private InstanceOfPredicate(Class clazz) {
         this.clazz = (Class)Preconditions.checkNotNull(clazz);
      }

      public boolean apply(@Nullable Object o) {
         return this.clazz.isInstance(o);
      }

      public int hashCode() {
         return this.clazz.hashCode();
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof InstanceOfPredicate) {
            InstanceOfPredicate that = (InstanceOfPredicate)obj;
            return this.clazz == that.clazz;
         } else {
            return false;
         }
      }

      public String toString() {
         return "Predicates.instanceOf(" + this.clazz.getName() + ")";
      }

      // $FF: synthetic method
      InstanceOfPredicate(Class x0, Object x1) {
         this(x0);
      }
   }

   private static class IsEqualToPredicate implements Predicate, Serializable {
      private final Object target;
      private static final long serialVersionUID = 0L;

      private IsEqualToPredicate(Object target) {
         this.target = target;
      }

      public boolean apply(Object t) {
         return this.target.equals(t);
      }

      public int hashCode() {
         return this.target.hashCode();
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof IsEqualToPredicate) {
            IsEqualToPredicate that = (IsEqualToPredicate)obj;
            return this.target.equals(that.target);
         } else {
            return false;
         }
      }

      public String toString() {
         return "Predicates.equalTo(" + this.target + ")";
      }

      // $FF: synthetic method
      IsEqualToPredicate(Object x0, Object x1) {
         this(x0);
      }
   }

   private static class OrPredicate implements Predicate, Serializable {
      private final List components;
      private static final long serialVersionUID = 0L;

      private OrPredicate(List components) {
         this.components = components;
      }

      public boolean apply(@Nullable Object t) {
         for(int i = 0; i < this.components.size(); ++i) {
            if (((Predicate)this.components.get(i)).apply(t)) {
               return true;
            }
         }

         return false;
      }

      public int hashCode() {
         return this.components.hashCode() + 87855567;
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof OrPredicate) {
            OrPredicate that = (OrPredicate)obj;
            return this.components.equals(that.components);
         } else {
            return false;
         }
      }

      public String toString() {
         return "Predicates.or(" + Predicates.COMMA_JOINER.join((Iterable)this.components) + ")";
      }

      // $FF: synthetic method
      OrPredicate(List x0, Object x1) {
         this(x0);
      }
   }

   private static class AndPredicate implements Predicate, Serializable {
      private final List components;
      private static final long serialVersionUID = 0L;

      private AndPredicate(List components) {
         this.components = components;
      }

      public boolean apply(@Nullable Object t) {
         for(int i = 0; i < this.components.size(); ++i) {
            if (!((Predicate)this.components.get(i)).apply(t)) {
               return false;
            }
         }

         return true;
      }

      public int hashCode() {
         return this.components.hashCode() + 306654252;
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof AndPredicate) {
            AndPredicate that = (AndPredicate)obj;
            return this.components.equals(that.components);
         } else {
            return false;
         }
      }

      public String toString() {
         return "Predicates.and(" + Predicates.COMMA_JOINER.join((Iterable)this.components) + ")";
      }

      // $FF: synthetic method
      AndPredicate(List x0, Object x1) {
         this(x0);
      }
   }

   private static class NotPredicate implements Predicate, Serializable {
      final Predicate predicate;
      private static final long serialVersionUID = 0L;

      NotPredicate(Predicate predicate) {
         this.predicate = (Predicate)Preconditions.checkNotNull(predicate);
      }

      public boolean apply(@Nullable Object t) {
         return !this.predicate.apply(t);
      }

      public int hashCode() {
         return ~this.predicate.hashCode();
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof NotPredicate) {
            NotPredicate that = (NotPredicate)obj;
            return this.predicate.equals(that.predicate);
         } else {
            return false;
         }
      }

      public String toString() {
         return "Predicates.not(" + this.predicate + ")";
      }
   }

   static enum ObjectPredicate implements Predicate {
      ALWAYS_TRUE {
         public boolean apply(@Nullable Object o) {
            return true;
         }

         public String toString() {
            return "Predicates.alwaysTrue()";
         }
      },
      ALWAYS_FALSE {
         public boolean apply(@Nullable Object o) {
            return false;
         }

         public String toString() {
            return "Predicates.alwaysFalse()";
         }
      },
      IS_NULL {
         public boolean apply(@Nullable Object o) {
            return o == null;
         }

         public String toString() {
            return "Predicates.isNull()";
         }
      },
      NOT_NULL {
         public boolean apply(@Nullable Object o) {
            return o != null;
         }

         public String toString() {
            return "Predicates.notNull()";
         }
      };

      private ObjectPredicate() {
      }

      Predicate withNarrowedType() {
         return this;
      }

      // $FF: synthetic method
      ObjectPredicate(Object x2) {
         this();
      }
   }
}
