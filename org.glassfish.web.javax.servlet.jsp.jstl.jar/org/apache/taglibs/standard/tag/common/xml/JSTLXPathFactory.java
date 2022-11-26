package org.apache.taglibs.standard.tag.common.xml;

import com.oracle.wls.shaded.org.apache.xpath.jaxp.XPathFactoryImpl;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

public class JSTLXPathFactory extends XPathFactoryImpl {
   public XPath newXPath() {
      return new JSTLXPathImpl((XPathVariableResolver)null, (XPathFunctionResolver)null);
   }
}
