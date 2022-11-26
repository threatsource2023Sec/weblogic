package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import javax.lang.model.element.Name;

public class NameImpl implements Name {
   private final String _name;

   private NameImpl() {
      this._name = null;
   }

   public NameImpl(CharSequence cs) {
      this._name = cs.toString();
   }

   public NameImpl(char[] chars) {
      this._name = String.valueOf(chars);
   }

   public boolean contentEquals(CharSequence cs) {
      return this._name.equals(cs.toString());
   }

   public char charAt(int index) {
      return this._name.charAt(index);
   }

   public int length() {
      return this._name.length();
   }

   public CharSequence subSequence(int start, int end) {
      return this._name.subSequence(start, end);
   }

   public String toString() {
      return this._name;
   }

   public int hashCode() {
      return this._name.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         NameImpl other = (NameImpl)obj;
         return this._name.equals(other._name);
      }
   }
}
