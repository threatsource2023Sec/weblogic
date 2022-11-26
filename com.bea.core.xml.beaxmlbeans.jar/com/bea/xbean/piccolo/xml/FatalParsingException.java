package com.bea.xbean.piccolo.xml;

import org.xml.sax.SAXException;

class FatalParsingException extends SAXException {
   FatalParsingException(String msg) {
      super(msg);
   }

   FatalParsingException(String msg, Exception ex) {
      super(msg, ex);
   }
}
