package org.apache.xml.security.utils;

public class JDKXPathFactory extends XPathFactory {
   public XPathAPI newXPathAPI() {
      return new JDKXPathAPI();
   }
}
