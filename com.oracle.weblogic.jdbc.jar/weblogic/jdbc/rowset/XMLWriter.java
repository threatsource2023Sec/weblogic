package weblogic.jdbc.rowset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.utils.AssertionError;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;

public final class XMLWriter {
   private final XMLOutputStream xos;
   private final String defaultNamespaceUri;

   public XMLWriter(XMLOutputStream xos) {
      this(xos, (String)null);
   }

   public XMLWriter(XMLOutputStream xos, String defaultNamespaceUri) {
      this.xos = xos;
      this.defaultNamespaceUri = defaultNamespaceUri;
   }

   private XMLName createName(String name) {
      return ElementFactory.createXMLName(this.defaultNamespaceUri, name);
   }

   public void writeStartElement(XMLName name) throws IOException {
      this.xos.add(ElementFactory.createStartElement(name));
   }

   public void writeStartElement(String name) throws IOException {
      this.writeStartElement(this.createName(name));
   }

   public void writeStartElement(XMLName name, Iterator attributeIterator) throws IOException {
      this.xos.add(ElementFactory.createStartElement(name, ElementFactory.createAttributeIterator(attributeIterator)));
   }

   public void writeStartElement(String name, Iterator attributeIterator) throws IOException {
      this.writeStartElement(this.createName(name), attributeIterator);
   }

   public void writeStartElement(XMLName name, Iterator attributeIterator, Iterator namespaceIterator) throws IOException {
      this.xos.add(ElementFactory.createStartElement(name, ElementFactory.createAttributeIterator(attributeIterator), ElementFactory.createAttributeIterator(namespaceIterator)));
   }

   public void writeStartElement(String name, Iterator attributeIterator, Iterator namespaceIterator) throws IOException {
      this.writeStartElement(this.createName(name), attributeIterator, namespaceIterator);
   }

   public void writeStartElement(XMLName name, String attrName, String attrValue) throws IOException {
      List l = new ArrayList();
      l.add(ElementFactory.createAttribute(attrName, attrValue));
      this.writeStartElement(name, l.iterator());
   }

   public void writeStartElement(XMLName name, XMLName attrName, String attrValue) throws IOException {
      List l = new ArrayList();
      l.add(ElementFactory.createAttribute(attrName, attrValue));
      this.writeStartElement(name, l.iterator());
   }

   public void writeStartElement(String name, String attrName, String attrValue) throws IOException {
      this.writeStartElement(this.createName(name), attrName, attrValue);
   }

   public void writeStartElement(String name, XMLName attrName, String attrValue) throws IOException {
      this.writeStartElement(this.createName(name), attrName, attrValue);
   }

   public void writeStartElement(String name, XMLName name1, String val1, XMLName name2, String val2) throws IOException {
      List l = new ArrayList();
      l.add(ElementFactory.createAttribute(name1, val1));
      l.add(ElementFactory.createAttribute(name2, val2));
      this.writeStartElement(name, l.iterator());
   }

   public void writeStartElement(XMLName name, String name1, String val1, String name2, String val2) throws IOException {
      List l = new ArrayList();
      l.add(ElementFactory.createAttribute(name1, val1));
      l.add(ElementFactory.createAttribute(name2, val2));
      this.writeStartElement(name, l.iterator());
   }

   public void writeStartElement(XMLName name, Map attrMap) throws IOException {
      if (attrMap.isEmpty()) {
         this.writeStartElement(name);
      } else {
         List attrList = new ArrayList();
         Iterator it = attrMap.keySet().iterator();

         while(it.hasNext()) {
            Object n = it.next();
            String v = (String)attrMap.get(n);
            if (n instanceof String) {
               attrList.add(ElementFactory.createAttribute((String)n, v));
            } else {
               if (!(n instanceof XMLName)) {
                  throw new AssertionError("Unexpected Name type: " + n.getClass().getName());
               }

               attrList.add(ElementFactory.createAttribute((XMLName)n, v));
            }
         }

         this.xos.add(ElementFactory.createStartElement(name, ElementFactory.createAttributeIterator(attrList.iterator())));
      }

   }

   public void writeCharacterData(String value) throws IOException {
      this.xos.add(ElementFactory.createCharacterData(value));
   }

   public void writeEndElement(XMLName name) throws IOException {
      this.xos.add(ElementFactory.createEndElement(name));
   }

   public void writeEndElement(String name) throws IOException {
      this.writeEndElement(this.createName(name));
   }

   public void writeSimpleElements(String name, String value) throws IOException {
      this.writeStartElement(name);
      this.writeCharacterData(value);
      this.writeEndElement(name);
   }

   public void writeSimpleElements(String name, boolean value) throws IOException {
      this.writeSimpleElements(name, (new Boolean(value)).toString());
   }

   public void writeSimpleElements(String name, int value) throws IOException {
      this.writeSimpleElements(name, (new Integer(value)).toString());
   }
}
