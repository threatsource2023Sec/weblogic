package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public class IllegalJavaValueException extends IllegalArgumentException {
   private final Object javaValue;
   private final QName type;

   public IllegalJavaValueException(String msg, Object javaValue, QName type) {
      super(msg);
      this.javaValue = javaValue;
      this.type = type;
   }

   public IllegalJavaValueException(String msg, Object javaValue, QName type, Throwable cause) {
      this(msg, javaValue, type);
      this.initCause(cause);
   }

   public Object getJavaValue() {
      return this.javaValue;
   }

   public QName getType() {
      return this.type;
   }
}
