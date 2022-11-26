package weblogic.xml.xpath;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;

public interface XPathStreamObserver {
   void observe(XMLEvent var1);

   void observeAttribute(StartElement var1, Attribute var2);

   void observeNamespace(StartElement var1, Attribute var2);
}
