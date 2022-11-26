package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.ArrayList;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.w3c.dom.Attr;

public class KeywordsUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("lang") && "http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI())) {
         Keywords keywords = (Keywords)samlObject;
         keywords.setXMLLang(attribute.getValue());
      }

   }

   protected void processElementContent(XMLObject samlObject, String elementContent) {
      Keywords keywords = (Keywords)samlObject;
      String[] words = elementContent.split("\\s+");
      ArrayList wordlist = new ArrayList(words.length);
      String[] var6 = words;
      int var7 = words.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String s = var6[var8];
         wordlist.add(s);
      }

      keywords.setKeywords(wordlist);
   }
}
