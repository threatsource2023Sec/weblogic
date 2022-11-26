package org.antlr.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.antlr.tool.Grammar;

public class IntervalSet implements IntSet {
   public static final IntervalSet COMPLETE_SET = of(0, 65535);
   protected List intervals;

   public IntervalSet() {
      this.intervals = new ArrayList(2);
   }

   public IntervalSet(List intervals) {
      this.intervals = intervals;
   }

   public static IntervalSet of(int a) {
      IntervalSet s = new IntervalSet();
      s.add(a);
      return s;
   }

   public static IntervalSet of(int a, int b) {
      IntervalSet s = new IntervalSet();
      s.add(a, b);
      return s;
   }

   public void add(int el) {
      this.add(el, el);
   }

   public void add(int a, int b) {
      this.add(Interval.create(a, b));
   }

   protected void add(Interval addition) {
      if (addition.b >= addition.a) {
         ListIterator iter = this.intervals.listIterator();

         Interval r;
         do {
            if (!iter.hasNext()) {
               this.intervals.add(addition);
               return;
            }

            r = (Interval)iter.next();
            if (addition.equals(r)) {
               return;
            }

            if (addition.adjacent(r) || !addition.disjoint(r)) {
               Interval bigger = addition.union(r);
               iter.set(bigger);

               while(iter.hasNext()) {
                  Interval next = (Interval)iter.next();
                  if (!bigger.adjacent(next) && bigger.disjoint(next)) {
                     break;
                  }

                  iter.remove();
                  iter.previous();
                  iter.set(bigger.union(next));
                  iter.next();
               }

               return;
            }
         } while(!addition.startsBeforeDisjoint(r));

         iter.previous();
         iter.add(addition);
      }
   }

   public void addAll(IntSet set) {
      if (set != null) {
         if (!(set instanceof IntervalSet)) {
            throw new IllegalArgumentException("can't add non IntSet (" + set.getClass().getName() + ") to IntervalSet");
         } else {
            IntervalSet other = (IntervalSet)set;
            int n = other.intervals.size();

            for(int i = 0; i < n; ++i) {
               Interval I = (Interval)other.intervals.get(i);
               this.add(I.a, I.b);
            }

         }
      }
   }

   public IntervalSet complement(int minElement, int maxElement) {
      return this.complement(of(minElement, maxElement));
   }

   public IntervalSet complement(IntSet vocabulary) {
      if (vocabulary == null) {
         return null;
      } else if (!(vocabulary instanceof IntervalSet)) {
         throw new IllegalArgumentException("can't complement with non IntervalSet (" + vocabulary.getClass().getName() + ")");
      } else {
         IntervalSet vocabularyIS = (IntervalSet)vocabulary;
         int maxElement = vocabularyIS.getMaxElement();
         IntervalSet compl = new IntervalSet();
         int n = this.intervals.size();
         if (n == 0) {
            return compl;
         } else {
            Interval first = (Interval)this.intervals.get(0);
            IntervalSet s;
            if (first.a > 0) {
               IntervalSet s = of(0, first.a - 1);
               s = s.and(vocabularyIS);
               compl.addAll(s);
            }

            for(int i = 1; i < n; ++i) {
               Interval previous = (Interval)this.intervals.get(i - 1);
               Interval current = (Interval)this.intervals.get(i);
               IntervalSet s = of(previous.b + 1, current.a - 1);
               IntervalSet a = s.and(vocabularyIS);
               compl.addAll(a);
            }

            Interval last = (Interval)this.intervals.get(n - 1);
            if (last.b < maxElement) {
               s = of(last.b + 1, maxElement);
               IntervalSet a = s.and(vocabularyIS);
               compl.addAll(a);
            }

            return compl;
         }
      }
   }

   public IntervalSet subtract(IntSet other) {
      return this.and(((IntervalSet)other).complement(COMPLETE_SET));
   }

   public IntSet or(IntSet a) {
      IntervalSet o = new IntervalSet();
      o.addAll(this);
      o.addAll(a);
      return o;
   }

   public IntervalSet and(IntSet other) {
      if (other == null) {
         return null;
      } else {
         List myIntervals = this.intervals;
         List theirIntervals = ((IntervalSet)other).intervals;
         IntervalSet intersection = null;
         int mySize = myIntervals.size();
         int theirSize = theirIntervals.size();
         int i = 0;
         int j = 0;

         while(i < mySize && j < theirSize) {
            Interval mine = (Interval)myIntervals.get(i);
            Interval theirs = (Interval)theirIntervals.get(j);
            if (mine.startsBeforeDisjoint(theirs)) {
               ++i;
            } else if (theirs.startsBeforeDisjoint(mine)) {
               ++j;
            } else if (mine.properlyContains(theirs)) {
               if (intersection == null) {
                  intersection = new IntervalSet();
               }

               intersection.add(mine.intersection(theirs));
               ++j;
            } else if (theirs.properlyContains(mine)) {
               if (intersection == null) {
                  intersection = new IntervalSet();
               }

               intersection.add(mine.intersection(theirs));
               ++i;
            } else if (!mine.disjoint(theirs)) {
               if (intersection == null) {
                  intersection = new IntervalSet();
               }

               intersection.add(mine.intersection(theirs));
               if (mine.startsAfterNonDisjoint(theirs)) {
                  ++j;
               } else if (theirs.startsAfterNonDisjoint(mine)) {
                  ++i;
               }
            }
         }

         return intersection == null ? new IntervalSet() : intersection;
      }
   }

   public boolean member(int el) {
      int n = this.intervals.size();

      for(int i = 0; i < n; ++i) {
         Interval I = (Interval)this.intervals.get(i);
         int a = I.a;
         int b = I.b;
         if (el < a) {
            break;
         }

         if (el >= a && el <= b) {
            return true;
         }
      }

      return false;
   }

   public boolean isNil() {
      return this.intervals == null || this.intervals.isEmpty();
   }

   public int getSingleElement() {
      if (this.intervals != null && this.intervals.size() == 1) {
         Interval I = (Interval)this.intervals.get(0);
         if (I.a == I.b) {
            return I.a;
         }
      }

      return -7;
   }

   public int getMaxElement() {
      if (this.isNil()) {
         return -7;
      } else {
         Interval last = (Interval)this.intervals.get(this.intervals.size() - 1);
         return last.b;
      }
   }

   public int getMinElement() {
      if (this.isNil()) {
         return -7;
      } else {
         int n = this.intervals.size();

         for(int i = 0; i < n; ++i) {
            Interval I = (Interval)this.intervals.get(i);
            int a = I.a;
            int b = I.b;

            for(int v = a; v <= b; ++v) {
               if (v >= 0) {
                  return v;
               }
            }
         }

         return -7;
      }
   }

   public List getIntervals() {
      return this.intervals;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof IntervalSet)) {
         return false;
      } else {
         IntervalSet other = (IntervalSet)obj;
         return this.intervals.equals(other.intervals);
      }
   }

   public String toString() {
      return this.toString((Grammar)null);
   }

   public String toString(Grammar g) {
      StringBuilder buf = new StringBuilder();
      if (this.intervals != null && !this.intervals.isEmpty()) {
         if (this.intervals.size() > 1) {
            buf.append("{");
         }

         Iterator iter = this.intervals.iterator();

         while(iter.hasNext()) {
            Interval I = (Interval)iter.next();
            int a = I.a;
            int b = I.b;
            if (a == b) {
               if (g != null) {
                  buf.append(g.getTokenDisplayName(a));
               } else {
                  buf.append(a);
               }
            } else if (g != null) {
               buf.append(g.getTokenDisplayName(a)).append("..").append(g.getTokenDisplayName(b));
            } else {
               buf.append(a).append("..").append(b);
            }

            if (iter.hasNext()) {
               buf.append(", ");
            }
         }

         if (this.intervals.size() > 1) {
            buf.append("}");
         }

         return buf.toString();
      } else {
         return "{}";
      }
   }

   public int size() {
      int n = 0;
      int numIntervals = this.intervals.size();
      if (numIntervals == 1) {
         Interval firstInterval = (Interval)this.intervals.get(0);
         return firstInterval.b - firstInterval.a + 1;
      } else {
         for(int i = 0; i < numIntervals; ++i) {
            Interval I = (Interval)this.intervals.get(i);
            n += I.b - I.a + 1;
         }

         return n;
      }
   }

   public List toList() {
      List values = new ArrayList();
      int n = this.intervals.size();

      for(int i = 0; i < n; ++i) {
         Interval I = (Interval)this.intervals.get(i);
         int a = I.a;
         int b = I.b;

         for(int v = a; v <= b; ++v) {
            values.add(Utils.integer(v));
         }
      }

      return values;
   }

   public int get(int i) {
      int n = this.intervals.size();
      int index = 0;

      for(int j = 0; j < n; ++j) {
         Interval I = (Interval)this.intervals.get(j);
         int a = I.a;
         int b = I.b;

         for(int v = a; v <= b; ++v) {
            if (index == i) {
               return v;
            }

            ++index;
         }
      }

      return -1;
   }

   public int[] toArray() {
      int[] values = new int[this.size()];
      int n = this.intervals.size();
      int j = 0;

      for(int i = 0; i < n; ++i) {
         Interval I = (Interval)this.intervals.get(i);
         int a = I.a;
         int b = I.b;

         for(int v = a; v <= b; ++v) {
            values[j] = v;
            ++j;
         }
      }

      return values;
   }

   public org.antlr.runtime.BitSet toRuntimeBitSet() {
      org.antlr.runtime.BitSet s = new org.antlr.runtime.BitSet(this.getMaxElement() + 1);
      int n = this.intervals.size();

      for(int i = 0; i < n; ++i) {
         Interval I = (Interval)this.intervals.get(i);
         int a = I.a;
         int b = I.b;

         for(int v = a; v <= b; ++v) {
            s.add(v);
         }
      }

      return s;
   }

   public void remove(int el) {
      throw new NoSuchMethodError("IntervalSet.remove() unimplemented");
   }
}
