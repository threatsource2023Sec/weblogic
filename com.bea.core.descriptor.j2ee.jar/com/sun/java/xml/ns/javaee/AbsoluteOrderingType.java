package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface AbsoluteOrderingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AbsoluteOrderingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("absoluteorderingtype6dd8type");

   JavaIdentifierType[] getNameArray();

   JavaIdentifierType getNameArray(int var1);

   int sizeOfNameArray();

   void setNameArray(JavaIdentifierType[] var1);

   void setNameArray(int var1, JavaIdentifierType var2);

   JavaIdentifierType insertNewName(int var1);

   JavaIdentifierType addNewName();

   void removeName(int var1);

   OrderingOthersType[] getOthersArray();

   OrderingOthersType getOthersArray(int var1);

   int sizeOfOthersArray();

   void setOthersArray(OrderingOthersType[] var1);

   void setOthersArray(int var1, OrderingOthersType var2);

   OrderingOthersType insertNewOthers(int var1);

   OrderingOthersType addNewOthers();

   void removeOthers(int var1);

   public static final class Factory {
      public static AbsoluteOrderingType newInstance() {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().newInstance(AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType newInstance(XmlOptions options) {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().newInstance(AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(java.lang.String xmlAsString) throws XmlException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(File file) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(file, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(file, AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(URL u) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(u, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(u, AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(InputStream is) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(is, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(is, AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(Reader r) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(r, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(r, AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(XMLStreamReader sr) throws XmlException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(sr, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(sr, AbsoluteOrderingType.type, options);
      }

      public static AbsoluteOrderingType parse(Node node) throws XmlException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(node, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      public static AbsoluteOrderingType parse(Node node, XmlOptions options) throws XmlException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(node, AbsoluteOrderingType.type, options);
      }

      /** @deprecated */
      public static AbsoluteOrderingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(xis, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AbsoluteOrderingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AbsoluteOrderingType)XmlBeans.getContextTypeLoader().parse(xis, AbsoluteOrderingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AbsoluteOrderingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AbsoluteOrderingType.type, options);
      }

      private Factory() {
      }
   }
}
