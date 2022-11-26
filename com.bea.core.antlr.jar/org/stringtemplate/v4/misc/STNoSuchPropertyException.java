package org.stringtemplate.v4.misc;

import org.stringtemplate.v4.compiler.STException;

public class STNoSuchPropertyException extends STException {
   public Object o;
   public String propertyName;

   public STNoSuchPropertyException(Exception e, Object o, String propertyName) {
      super((String)null, e);
      this.o = o;
      this.propertyName = propertyName;
   }

   public String getMessage() {
      return this.o != null ? "object " + this.o.getClass() + " has no " + this.propertyName + " property" : "no such property: " + this.propertyName;
   }
}
