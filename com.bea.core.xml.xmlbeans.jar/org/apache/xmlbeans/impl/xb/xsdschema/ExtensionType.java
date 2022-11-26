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
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface ExtensionType extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExtensionType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("extensiontypeed4ctype");

   GroupRef getGroup();

   boolean isSetGroup();

   void setGroup(GroupRef var1);

   GroupRef addNewGroup();

   void unsetGroup();

   All getAll();

   boolean isSetAll();

   void setAll(All var1);

   All addNewAll();

   void unsetAll();

   ExplicitGroup getChoice();

   boolean isSetChoice();

   void setChoice(ExplicitGroup var1);

   ExplicitGroup addNewChoice();

   void unsetChoice();

   ExplicitGroup getSequence();

   boolean isSetSequence();

   void setSequence(ExplicitGroup var1);

   ExplicitGroup addNewSequence();

   void unsetSequence();

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

   QName getBase();

   XmlQName xgetBase();

   void setBase(QName var1);

   void xsetBase(XmlQName var1);

   public static final class Factory {
      public static ExtensionType newInstance() {
         return (ExtensionType)XmlBeans.getContextTypeLoader().newInstance(ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType newInstance(XmlOptions options) {
         return (ExtensionType)XmlBeans.getContextTypeLoader().newInstance(ExtensionType.type, options);
      }

      public static ExtensionType parse(String xmlAsString) throws XmlException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExtensionType.type, options);
      }

      public static ExtensionType parse(File file) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((File)file, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(file, ExtensionType.type, options);
      }

      public static ExtensionType parse(URL u) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((URL)u, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(u, ExtensionType.type, options);
      }

      public static ExtensionType parse(InputStream is) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((InputStream)is, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(is, ExtensionType.type, options);
      }

      public static ExtensionType parse(Reader r) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((Reader)r, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(r, ExtensionType.type, options);
      }

      public static ExtensionType parse(XMLStreamReader sr) throws XmlException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(sr, ExtensionType.type, options);
      }

      public static ExtensionType parse(Node node) throws XmlException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((Node)node, ExtensionType.type, (XmlOptions)null);
      }

      public static ExtensionType parse(Node node, XmlOptions options) throws XmlException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(node, ExtensionType.type, options);
      }

      /** @deprecated */
      public static ExtensionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ExtensionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExtensionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExtensionType)XmlBeans.getContextTypeLoader().parse(xis, ExtensionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExtensionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExtensionType.type, options);
      }

      private Factory() {
      }
   }
}
