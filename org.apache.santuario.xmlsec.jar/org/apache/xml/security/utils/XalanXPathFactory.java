package org.apache.xml.security.utils;

public class XalanXPathFactory extends XPathFactory {
   public XPathAPI newXPathAPI() {
      return new XalanXPathAPI();
   }
}
