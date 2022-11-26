package org.apache.xmlbeans.xml.stream;

public interface AttributeIterator {
   Attribute next();

   boolean hasNext();

   Attribute peek();

   void skip();
}
