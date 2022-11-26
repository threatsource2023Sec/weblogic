package org.apache.xmlbeans.impl.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NameSet {
   public static NameSet EMPTY;
   public static NameSet EVERYTHING;
   private boolean _isFinite;
   private Set _finiteSet;

   private NameSet(boolean isFinite, Set finiteSet) {
      this._isFinite = isFinite;
      this._finiteSet = finiteSet;
   }

   static NameSet newInstance(boolean isFinite, Set finiteSet) {
      if (finiteSet.size() == 0) {
         return isFinite ? EMPTY : EVERYTHING;
      } else {
         Set fs = new HashSet();
         fs.addAll(finiteSet);
         return new NameSet(isFinite, fs);
      }
   }

   private static Set intersectFiniteSets(Set a, Set b) {
      Set intersection = new HashSet();

      while(a.iterator().hasNext()) {
         String name = (String)a.iterator().next();
         if (b.contains(name)) {
            intersection.add(name);
         }
      }

      return intersection;
   }

   public NameSet union(NameSet with) {
      HashSet subst;
      if (this._isFinite) {
         if (with._isFinite) {
            subst = new HashSet();
            subst.addAll(this._finiteSet);
            subst.addAll(with._finiteSet);
            return newInstance(true, subst);
         } else {
            subst = new HashSet();
            subst.addAll(with._finiteSet);
            subst.removeAll(this._finiteSet);
            return newInstance(false, subst);
         }
      } else if (with._isFinite) {
         subst = new HashSet();
         subst.addAll(this._finiteSet);
         subst.removeAll(with._finiteSet);
         return newInstance(false, subst);
      } else {
         return newInstance(false, intersectFiniteSets(this._finiteSet, with._finiteSet));
      }
   }

   public NameSet intersect(NameSet with) {
      HashSet union;
      if (this._isFinite) {
         if (with._isFinite) {
            return newInstance(true, intersectFiniteSets(this._finiteSet, with._finiteSet));
         } else {
            union = new HashSet();
            union.addAll(this._finiteSet);
            union.removeAll(with._finiteSet);
            return newInstance(false, union);
         }
      } else if (with._isFinite) {
         union = new HashSet();
         union.addAll(with._finiteSet);
         union.removeAll(this._finiteSet);
         return newInstance(true, union);
      } else {
         union = new HashSet();
         union.addAll(this._finiteSet);
         union.addAll(with._finiteSet);
         return newInstance(false, union);
      }
   }

   public NameSet substractFrom(NameSet from) {
      return from.substract(this);
   }

   public NameSet substract(NameSet what) {
      HashSet subst;
      if (this._isFinite) {
         if (what._isFinite) {
            subst = new HashSet();
            subst.addAll(this._finiteSet);
            subst.removeAll(what._finiteSet);
            return newInstance(true, subst);
         } else {
            return newInstance(true, intersectFiniteSets(this._finiteSet, what._finiteSet));
         }
      } else if (what._isFinite) {
         subst = new HashSet();
         subst.addAll(this._finiteSet);
         subst.addAll(what._finiteSet);
         return newInstance(false, subst);
      } else {
         subst = new HashSet();
         subst.addAll(what._finiteSet);
         subst.removeAll(this._finiteSet);
         return newInstance(true, subst);
      }
   }

   public NameSet invert() {
      return newInstance(!this._isFinite, this._finiteSet);
   }

   public boolean contains(String name) {
      if (this._isFinite) {
         return this._finiteSet.contains(name);
      } else {
         return !this._finiteSet.contains(name);
      }
   }

   static {
      EMPTY = new NameSet(true, Collections.EMPTY_SET);
      EVERYTHING = new NameSet(false, Collections.EMPTY_SET);
   }
}
