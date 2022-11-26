package com.bea.xbean.values;

import com.bea.xml.XmlError;

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
