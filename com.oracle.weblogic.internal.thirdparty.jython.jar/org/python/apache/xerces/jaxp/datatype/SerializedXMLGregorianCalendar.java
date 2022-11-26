package org.python.apache.xerces.jaxp.datatype;

import java.io.ObjectStreamException;
import java.io.Serializable;

final class SerializedXMLGregorianCalendar implements Serializable {
   private static final long serialVersionUID = -7752272381890705397L;
   private final String lexicalValue;

   public SerializedXMLGregorianCalendar(String var1) {
      this.lexicalValue = var1;
   }

   private Object readResolve() throws ObjectStreamException {
      return (new DatatypeFactoryImpl()).newXMLGregorianCalendar(this.lexicalValue);
   }
}
