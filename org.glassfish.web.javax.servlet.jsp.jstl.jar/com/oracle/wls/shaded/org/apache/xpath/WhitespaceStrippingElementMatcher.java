package com.oracle.wls.shaded.org.apache.xpath;

import javax.xml.transform.TransformerException;
import org.w3c.dom.Element;

public interface WhitespaceStrippingElementMatcher {
   boolean shouldStripWhiteSpace(XPathContext var1, Element var2) throws TransformerException;

   boolean canStripWhiteSpace();
}
