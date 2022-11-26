package org.python.apache.xerces.jaxp.datatype;

import java.io.ObjectStreamException;
import java.io.Serializable;

final class SerializedDuration implements Serializable {
   private static final long serialVersionUID = 3897193592341225793L;
   private final String lexicalValue;

   public SerializedDuration(String var1) {
      this.lexicalValue = var1;
   }

   private Object readResolve() throws ObjectStreamException {
      return (new DatatypeFactoryImpl()).newDuration(this.lexicalValue);
   }
}
