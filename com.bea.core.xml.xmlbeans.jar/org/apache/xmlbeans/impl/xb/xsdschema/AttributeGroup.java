package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AttributeGroup extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AttributeGroup.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("attributegroupe530type");

   Attribute[] getAttributeArray();

   Attribute getAttributeArray(int var1);

   int sizeOfAttributeArray();

   void setAttributeArray(Attribute[] var1);

   void setAttributeArray(int var1, Attribute var2);

   Attribute insertNewAttribute(int var1);

   Attribute addNewAttribute();

   void removeAttribute(int var1);

   AttributeGroupRef[] getAttributeGroupArray();

   AttributeGroupRef getAttributeGroupArray(int var1);

   int sizeOfAttributeGroupArray();

   void setAttributeGroupArray(AttributeGroupRef[] var1);

   void setAttributeGroupArray(int var1, AttributeGroupRef var2);

   AttributeGroupRef insertNewAttributeGroup(int var1);

   AttributeGroupRef addNewAttributeGroup();

   void removeAttributeGroup(int var1);

   Wildcard getAnyAttribute();

   boolean isSetAnyAttribute();

   void setAnyAttribute(Wildcard var1);

   Wildcard addNewAnyAttribute();

   void unsetAnyAttribute();

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   QName getRef();

   XmlQName xgetRef();

   boolean isSetRef();

   void setRef(QName var1);

   void xsetRef(XmlQName var1);

   void unsetRef();

   public static final class Factory {
      /** @deprecated */
      public static AttributeGroup newInstance() {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().newInstance(AttributeGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AttributeGroup newInstance(XmlOptions options) {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().newInstance(AttributeGroup.type, options);
      }

      public static AttributeGroup parse(String xmlAsString) throws XmlException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, AttributeGroup.type, options);
      }

      public static AttributeGroup parse(File file) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((File)file, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(file, AttributeGroup.type, options);
      }

      public static AttributeGroup parse(URL u) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((URL)u, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(u, AttributeGroup.type, options);
      }

      public static AttributeGroup parse(InputStream is) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((InputStream)is, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(is, AttributeGroup.type, options);
      }

      public static AttributeGroup parse(Reader r) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((Reader)r, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(r, AttributeGroup.type, options);
      }

      public static AttributeGroup parse(XMLStreamReader sr) throws XmlException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(sr, AttributeGroup.type, options);
      }

      public static AttributeGroup parse(Node node) throws XmlException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((Node)node, AttributeGroup.type, (XmlOptions)null);
      }

      public static AttributeGroup parse(Node node, XmlOptions options) throws XmlException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(node, AttributeGroup.type, options);
      }

      /** @deprecated */
      public static AttributeGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AttributeGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AttributeGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AttributeGroup)XmlBeans.getContextTypeLoader().parse(xis, AttributeGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AttributeGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AttributeGroup.type, options);
      }

      private Factory() {
      }
   }
}
