package org.python.icu.impl;

public class Pair {
   public final Object first;
   public final Object second;

   protected Pair(Object first, Object second) {
      this.first = first;
      this.second = second;
   }

   public static Pair of(Object first, Object second) {
      if (first != null && second != null) {
         return new Pair(first, second);
      } else {
         throw new IllegalArgumentException("Pair.of requires non null values.");
      }
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         Pair rhs = (Pair)other;
         return this.first.equals(rhs.first) && this.second.equals(rhs.second);
      }
   }

   public int hashCode() {
      return this.first.hashCode() * 37 + this.second.hashCode();
   }
}
