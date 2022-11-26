package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.XmlError;

public class XmlValueNotSupportedException extends XmlValueOutOfRangeException {
   public XmlValueNotSupportedException() {
   }

   public XmlValueNotSupportedException(String message) {
      super(message);
   }

   public XmlValueNotSupportedException(String code, Object[] args) {
      super(XmlError.formattedMessage(code, args));
   }
}
