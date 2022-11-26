package com.bea.common.ldap.exps;

import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

public abstract class Val implements Value {
   private ClassMetaData _meta;
   private Class _type = null;

   public Val(Class type) {
      this._type = type;
   }

   public Val(ClassMetaData meta) {
      this._meta = meta;
      this._type = meta.getDescribedType();
   }

   public Class getType() {
      return this._type;
   }

   public void setImplicitType(Class type) {
      this._type = type;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean isVariable() {
      return false;
   }

   public boolean isXPath() {
      return false;
   }

   public boolean isAggregate() {
      return false;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter(this);
      visitor.exit(this);
   }
}
