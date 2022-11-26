package com.oracle.xmlns.weblogic.weblogicWebservices;

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

public interface ClassLoadingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClassLoadingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("classloadingtypeff32type");

   ShareableType[] getShareableArray();

   ShareableType getShareableArray(int var1);

   int sizeOfShareableArray();

   void setShareableArray(ShareableType[] var1);

   void setShareableArray(int var1, ShareableType var2);

   ShareableType insertNewShareable(int var1);

   ShareableType addNewShareable();

   void removeShareable(int var1);

   public static final class Factory {
      public static ClassLoadingType newInstance() {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().newInstance(ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType newInstance(XmlOptions options) {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().newInstance(ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(String xmlAsString) throws XmlException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(File file) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(file, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(file, ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(URL u) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(u, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(u, ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(InputStream is) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(is, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(is, ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(Reader r) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(r, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(r, ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(XMLStreamReader sr) throws XmlException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(sr, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(sr, ClassLoadingType.type, options);
      }

      public static ClassLoadingType parse(Node node) throws XmlException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(node, ClassLoadingType.type, (XmlOptions)null);
      }

      public static ClassLoadingType parse(Node node, XmlOptions options) throws XmlException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(node, ClassLoadingType.type, options);
      }

      /** @deprecated */
      public static ClassLoadingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(xis, ClassLoadingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClassLoadingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClassLoadingType)XmlBeans.getContextTypeLoader().parse(xis, ClassLoadingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassLoadingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassLoadingType.type, options);
      }

      private Factory() {
      }
   }
}
