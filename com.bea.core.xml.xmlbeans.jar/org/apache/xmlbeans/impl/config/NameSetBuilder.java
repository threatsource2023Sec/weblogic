package org.apache.xmlbeans.impl.config;

import java.util.HashSet;
import java.util.Set;

public class NameSetBuilder {
   private boolean _isFinite = true;
   private Set _finiteSet = new HashSet();

   public void invert() {
      this._isFinite = !this._isFinite;
   }

   public void add(String name) {
      if (this._isFinite) {
         this._finiteSet.add(name);
      } else {
         this._finiteSet.remove(name);
      }

   }

   public NameSet toNameSet() {
      if (this._finiteSet.size() == 0) {
         return this._isFinite ? NameSet.EMPTY : NameSet.EVERYTHING;
      } else {
         return NameSet.newInstance(this._isFinite, this._finiteSet);
      }
   }
}
