package org.antlr.analysis;

import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;
import org.antlr.tool.Grammar;

public class LookaheadSet {
   public IntervalSet tokenTypeSet;

   public LookaheadSet() {
      this.tokenTypeSet = new IntervalSet();
   }

   public LookaheadSet(IntSet s) {
      this();
      this.tokenTypeSet.addAll(s);
   }

   public LookaheadSet(int atom) {
      this.tokenTypeSet = IntervalSet.of(atom);
   }

   public LookaheadSet(LookaheadSet other) {
      this();
      this.tokenTypeSet.addAll(other.tokenTypeSet);
   }

   public void orInPlace(LookaheadSet other) {
      this.tokenTypeSet.addAll(other.tokenTypeSet);
   }

   public LookaheadSet or(LookaheadSet other) {
      return new LookaheadSet(this.tokenTypeSet.or(other.tokenTypeSet));
   }

   public LookaheadSet subtract(LookaheadSet other) {
      return new LookaheadSet(this.tokenTypeSet.subtract(other.tokenTypeSet));
   }

   public boolean member(int a) {
      return this.tokenTypeSet.member(a);
   }

   public LookaheadSet intersection(LookaheadSet s) {
      IntSet i = this.tokenTypeSet.and(s.tokenTypeSet);
      LookaheadSet intersection = new LookaheadSet(i);
      return intersection;
   }

   public boolean isNil() {
      return this.tokenTypeSet.isNil();
   }

   public void remove(int a) {
      this.tokenTypeSet = this.tokenTypeSet.subtract(IntervalSet.of(a));
   }

   public int hashCode() {
      return this.tokenTypeSet.hashCode();
   }

   public boolean equals(Object other) {
      return this.tokenTypeSet.equals(((LookaheadSet)other).tokenTypeSet);
   }

   public String toString(Grammar g) {
      if (this.tokenTypeSet == null) {
         return "";
      } else {
         String r = this.tokenTypeSet.toString(g);
         return r;
      }
   }

   public String toString() {
      return this.toString((Grammar)null);
   }
}
