package com.bea.common.ldap.exps;

import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.meta.FieldMetaData;

public class Variable extends Val implements Path {
   private final String _name;
   private Class _cast = null;
   private boolean _isBound = false;
   private ExpPath bound;

   public Variable(String name, Class type, boolean isBound) {
      super(type);
      this._name = name;
      this._isBound = isBound;
   }

   public String getName() {
      return this._name;
   }

   public boolean isBound() {
      return this._isBound;
   }

   public ExpPath getBound() {
      return this.bound;
   }

   public void setBound(ExpPath bound) {
      this.bound = bound;
   }

   public boolean isVariable() {
      return true;
   }

   public Class getType() {
      return this._cast != null ? this._cast : super.getType();
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public boolean hasVariable(Variable var) {
      return this == var;
   }

   public void get(FieldMetaData field, boolean nullTraversal) {
      this.bound.get(field, nullTraversal);
   }

   public FieldMetaData last() {
      return this.bound.last();
   }
}
