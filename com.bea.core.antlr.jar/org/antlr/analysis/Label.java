package org.antlr.analysis;

import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;
import org.antlr.tool.Grammar;

public class Label implements Comparable, Cloneable {
   public static final int INVALID = -7;
   public static final int ACTION = -6;
   public static final int EPSILON = -5;
   public static final String EPSILON_STR = "<EPSILON>";
   public static final int SEMPRED = -4;
   public static final int SET = -3;
   public static final int EOT = -2;
   public static final int EOF = -1;
   public static final int NUM_FAUX_LABELS = 7;
   public static final int MIN_ATOM_VALUE = -2;
   public static final int MIN_CHAR_VALUE = 0;
   public static final int MAX_CHAR_VALUE = 65535;
   public static final int EOR_TOKEN_TYPE = 1;
   public static final int DOWN = 2;
   public static final int UP = 3;
   public static final int MIN_TOKEN_TYPE = 4;
   protected int label;
   protected IntSet labelSet;

   public Label(int label) {
      this.label = label;
   }

   public Label(IntSet labelSet) {
      if (labelSet == null) {
         this.label = -3;
         this.labelSet = IntervalSet.of(-7);
      } else {
         int singleAtom = labelSet.getSingleElement();
         if (singleAtom != -7) {
            this.label = singleAtom;
         } else {
            this.label = -3;
            this.labelSet = labelSet;
         }
      }
   }

   public Object clone() {
      try {
         Label l = (Label)super.clone();
         l.label = this.label;
         l.labelSet = new IntervalSet();
         l.labelSet.addAll(this.labelSet);
         return l;
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }
   }

   public void add(Label a) {
      if (this.isAtom()) {
         this.labelSet = IntervalSet.of(this.label);
         this.label = -3;
         if (a.isAtom()) {
            this.labelSet.add(a.getAtom());
         } else {
            if (!a.isSet()) {
               throw new IllegalStateException("can't add element to Label of type " + this.label);
            }

            this.labelSet.addAll(a.getSet());
         }

      } else if (this.isSet()) {
         if (a.isAtom()) {
            this.labelSet.add(a.getAtom());
         } else {
            if (!a.isSet()) {
               throw new IllegalStateException("can't add element to Label of type " + this.label);
            }

            this.labelSet.addAll(a.getSet());
         }

      } else {
         throw new IllegalStateException("can't add element to Label of type " + this.label);
      }
   }

   public boolean isAtom() {
      return this.label >= -2;
   }

   public boolean isEpsilon() {
      return this.label == -5;
   }

   public boolean isSemanticPredicate() {
      return false;
   }

   public boolean isAction() {
      return false;
   }

   public boolean isSet() {
      return this.label == -3;
   }

   public int getAtom() {
      return this.isAtom() ? this.label : -7;
   }

   public IntSet getSet() {
      return (IntSet)(this.label != -3 ? IntervalSet.of(this.label) : this.labelSet);
   }

   public void setSet(IntSet set) {
      this.label = -3;
      this.labelSet = set;
   }

   public SemanticContext getSemanticContext() {
      return null;
   }

   public boolean matches(int atom) {
      if (this.label == atom) {
         return true;
      } else {
         return this.isSet() ? this.labelSet.member(atom) : false;
      }
   }

   public boolean matches(IntSet set) {
      if (this.isAtom()) {
         return set.member(this.getAtom());
      } else if (this.isSet()) {
         return !this.getSet().and(set).isNil();
      } else {
         return false;
      }
   }

   public boolean matches(Label other) {
      if (other.isSet()) {
         return this.matches(other.getSet());
      } else {
         return other.isAtom() ? this.matches(other.getAtom()) : false;
      }
   }

   public int hashCode() {
      return this.label == -3 ? this.labelSet.hashCode() : this.label;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (this == o) {
         return true;
      } else if (this.label != ((Label)o).label) {
         return false;
      } else {
         return this.label == -3 ? this.labelSet.equals(((Label)o).labelSet) : true;
      }
   }

   public int compareTo(Label o) {
      return this.label - o.label;
   }

   public String toString() {
      switch (this.label) {
         case -3:
            return this.labelSet.toString();
         default:
            return String.valueOf(this.label);
      }
   }

   public String toString(Grammar g) {
      switch (this.label) {
         case -3:
            return this.labelSet.toString(g);
         default:
            return g.getTokenDisplayName(this.label);
      }
   }

   public static boolean intersect(Label label, Label edgeLabel) {
      boolean hasIntersection = false;
      boolean labelIsSet = label.isSet();
      boolean edgeIsSet = edgeLabel.isSet();
      if (!labelIsSet && !edgeIsSet && edgeLabel.label == label.label) {
         hasIntersection = true;
      } else if (labelIsSet && edgeIsSet && !edgeLabel.getSet().and(label.getSet()).isNil()) {
         hasIntersection = true;
      } else if (labelIsSet && !edgeIsSet && label.getSet().member(edgeLabel.label)) {
         hasIntersection = true;
      } else if (!labelIsSet && edgeIsSet && edgeLabel.getSet().member(label.label)) {
         hasIntersection = true;
      }

      return hasIntersection;
   }
}
