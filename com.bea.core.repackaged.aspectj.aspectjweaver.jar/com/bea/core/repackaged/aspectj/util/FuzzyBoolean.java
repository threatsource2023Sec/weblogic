package com.bea.core.repackaged.aspectj.util;

public abstract class FuzzyBoolean {
   public static final FuzzyBoolean YES = new YesFuzzyBoolean();
   public static final FuzzyBoolean NO = new NoFuzzyBoolean();
   public static final FuzzyBoolean MAYBE = new MaybeFuzzyBoolean();
   public static final FuzzyBoolean NEVER = new NeverFuzzyBoolean();

   public abstract boolean alwaysTrue();

   public abstract boolean alwaysFalse();

   public abstract boolean maybeTrue();

   public abstract boolean maybeFalse();

   public abstract FuzzyBoolean and(FuzzyBoolean var1);

   public abstract FuzzyBoolean or(FuzzyBoolean var1);

   public abstract FuzzyBoolean not();

   public static final FuzzyBoolean fromBoolean(boolean b) {
      return b ? YES : NO;
   }

   private static class MaybeFuzzyBoolean extends FuzzyBoolean {
      private MaybeFuzzyBoolean() {
      }

      public boolean alwaysFalse() {
         return false;
      }

      public boolean alwaysTrue() {
         return false;
      }

      public boolean maybeFalse() {
         return true;
      }

      public boolean maybeTrue() {
         return true;
      }

      public FuzzyBoolean and(FuzzyBoolean other) {
         return (FuzzyBoolean)(other.alwaysFalse() ? other : this);
      }

      public FuzzyBoolean not() {
         return this;
      }

      public FuzzyBoolean or(FuzzyBoolean other) {
         return (FuzzyBoolean)(other.alwaysTrue() ? other : this);
      }

      public String toString() {
         return "MAYBE";
      }

      // $FF: synthetic method
      MaybeFuzzyBoolean(Object x0) {
         this();
      }
   }

   private static class NeverFuzzyBoolean extends FuzzyBoolean {
      private NeverFuzzyBoolean() {
      }

      public boolean alwaysFalse() {
         return true;
      }

      public boolean alwaysTrue() {
         return false;
      }

      public boolean maybeFalse() {
         return true;
      }

      public boolean maybeTrue() {
         return false;
      }

      public FuzzyBoolean and(FuzzyBoolean other) {
         return this;
      }

      public FuzzyBoolean not() {
         return this;
      }

      public FuzzyBoolean or(FuzzyBoolean other) {
         return this;
      }

      public String toString() {
         return "NEVER";
      }

      // $FF: synthetic method
      NeverFuzzyBoolean(Object x0) {
         this();
      }
   }

   private static class NoFuzzyBoolean extends FuzzyBoolean {
      private NoFuzzyBoolean() {
      }

      public boolean alwaysFalse() {
         return true;
      }

      public boolean alwaysTrue() {
         return false;
      }

      public boolean maybeFalse() {
         return true;
      }

      public boolean maybeTrue() {
         return false;
      }

      public FuzzyBoolean and(FuzzyBoolean other) {
         return this;
      }

      public FuzzyBoolean not() {
         return FuzzyBoolean.YES;
      }

      public FuzzyBoolean or(FuzzyBoolean other) {
         return other;
      }

      public String toString() {
         return "NO";
      }

      // $FF: synthetic method
      NoFuzzyBoolean(Object x0) {
         this();
      }
   }

   private static class YesFuzzyBoolean extends FuzzyBoolean {
      private YesFuzzyBoolean() {
      }

      public boolean alwaysFalse() {
         return false;
      }

      public boolean alwaysTrue() {
         return true;
      }

      public boolean maybeFalse() {
         return false;
      }

      public boolean maybeTrue() {
         return true;
      }

      public FuzzyBoolean and(FuzzyBoolean other) {
         return other;
      }

      public FuzzyBoolean not() {
         return FuzzyBoolean.NO;
      }

      public FuzzyBoolean or(FuzzyBoolean other) {
         return this;
      }

      public String toString() {
         return "YES";
      }

      // $FF: synthetic method
      YesFuzzyBoolean(Object x0) {
         this();
      }
   }
}
