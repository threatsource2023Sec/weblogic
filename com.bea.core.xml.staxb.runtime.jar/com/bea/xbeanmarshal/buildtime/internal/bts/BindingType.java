package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.xml.XmlException;
import java.io.Serializable;

public abstract class BindingType implements Serializable {
   private BindingTypeName name;
   private static final long serialVersionUID = 1L;

   protected BindingType() {
   }

   protected BindingType(BindingTypeName btName) {
      this.setName(btName);
   }

   public final BindingTypeName getName() {
      return this.name;
   }

   public final void setName(BindingTypeName name) {
      this.name = name;
   }

   public abstract void accept(BindingTypeVisitor var1) throws XmlException;

   public String toString() {
      return this.getClass().getName() + "[" + this.getName().getJavaName() + "; " + this.getName().getXmlName() + "]";
   }
}
