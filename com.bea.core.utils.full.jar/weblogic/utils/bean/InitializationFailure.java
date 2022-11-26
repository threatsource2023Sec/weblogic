package weblogic.utils.bean;

import java.beans.PropertyDescriptor;

public class InitializationFailure {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private PropertyDescriptor property;
   private Throwable exception;

   public PropertyDescriptor getProperty() {
      return this.property;
   }

   public void setProperty(PropertyDescriptor p) {
      this.property = p;
   }

   public Throwable getException() {
      return this.exception;
   }

   public void setException(Throwable t) {
      this.exception = t;
   }

   public InitializationFailure(PropertyDescriptor prop, Throwable th) {
      this.property = prop;
      this.exception = th;
   }
}
