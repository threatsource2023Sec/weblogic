package org.apache.xmlbeans.impl.soap;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface SOAPElement extends Node, Element {
   SOAPElement addChildElement(Name var1) throws SOAPException;

   SOAPElement addChildElement(String var1) throws SOAPException;

   SOAPElement addChildElement(String var1, String var2) throws SOAPException;

   SOAPElement addChildElement(String var1, String var2, String var3) throws SOAPException;

   SOAPElement addChildElement(SOAPElement var1) throws SOAPException;

   SOAPElement addTextNode(String var1) throws SOAPException;

   SOAPElement addAttribute(Name var1, String var2) throws SOAPException;

   SOAPElement addNamespaceDeclaration(String var1, String var2) throws SOAPException;

   String getAttributeValue(Name var1);

   Iterator getAllAttributes();

   String getNamespaceURI(String var1);

   Iterator getNamespacePrefixes();

   Name getElementName();

   boolean removeAttribute(Name var1);

   boolean removeNamespaceDeclaration(String var1);

   Iterator getChildElements();

   Iterator getChildElements(Name var1);

   void setEncodingStyle(String var1) throws SOAPException;

   String getEncodingStyle();

   void removeContents();

   Iterator getVisibleNamespacePrefixes();
}
