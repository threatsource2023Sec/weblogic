package org.apache.xml.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface Reference {
   String getType();

   String getURI();

   void setURI(String var1);

   Iterator getElementRetrievalInformation();

   void addElementRetrievalInformation(Element var1);

   void removeElementRetrievalInformation(Element var1);
}
