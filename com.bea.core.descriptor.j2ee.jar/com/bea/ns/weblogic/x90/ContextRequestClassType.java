package com.bea.ns.weblogic.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ContextRequestClassType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ContextRequestClassType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("contextrequestclasstypeb5a6type");

   XsdStringType getName();

   void setName(XsdStringType var1);

   XsdStringType addNewName();

   ContextCaseType[] getContextCaseArray();

   ContextCaseType getContextCaseArray(int var1);

   int sizeOfContextCaseArray();

   void setContextCaseArray(ContextCaseType[] var1);

   void setContextCaseArray(int var1, ContextCaseType var2);

   ContextCaseType insertNewContextCase(int var1);

   ContextCaseType addNewContextCase();

   void removeContextCase(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ContextRequestClassType newInstance() {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().newInstance(ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType newInstance(XmlOptions options) {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().newInstance(ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(String xmlAsString) throws XmlException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(File file) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(file, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(file, ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(URL u) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(u, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(u, ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(InputStream is) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(is, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(is, ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(Reader r) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(r, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(r, ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(XMLStreamReader sr) throws XmlException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(sr, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(sr, ContextRequestClassType.type, options);
      }

      public static ContextRequestClassType parse(Node node) throws XmlException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(node, ContextRequestClassType.type, (XmlOptions)null);
      }

      public static ContextRequestClassType parse(Node node, XmlOptions options) throws XmlException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(node, ContextRequestClassType.type, options);
      }

      /** @deprecated */
      public static ContextRequestClassType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(xis, ContextRequestClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ContextRequestClassType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ContextRequestClassType)XmlBeans.getContextTypeLoader().parse(xis, ContextRequestClassType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContextRequestClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContextRequestClassType.type, options);
      }

      private Factory() {
      }
   }
}
