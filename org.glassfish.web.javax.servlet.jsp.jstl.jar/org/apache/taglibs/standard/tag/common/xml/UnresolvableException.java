package org.apache.taglibs.standard.tag.common.xml;

import javax.xml.xpath.XPathException;

public class UnresolvableException extends XPathException {
   public UnresolvableException(String message) {
      super(message);
   }

   public UnresolvableException(Throwable cause) {
      super(cause);
   }
}
