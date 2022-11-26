package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WelcomeFileListType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WelcomeFileListType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("welcomefilelisttyped21ftype");

   java.lang.String[] getWelcomeFileArray();

   java.lang.String getWelcomeFileArray(int var1);

   XmlString[] xgetWelcomeFileArray();

   XmlString xgetWelcomeFileArray(int var1);

   int sizeOfWelcomeFileArray();

   void setWelcomeFileArray(java.lang.String[] var1);

   void setWelcomeFileArray(int var1, java.lang.String var2);

   void xsetWelcomeFileArray(XmlString[] var1);

   void xsetWelcomeFileArray(int var1, XmlString var2);

   void insertWelcomeFile(int var1, java.lang.String var2);

   void addWelcomeFile(java.lang.String var1);

   XmlString insertNewWelcomeFile(int var1);

   XmlString addNewWelcomeFile();

   void removeWelcomeFile(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WelcomeFileListType newInstance() {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().newInstance(WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType newInstance(XmlOptions options) {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().newInstance(WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(java.lang.String xmlAsString) throws XmlException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(File file) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(file, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(file, WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(URL u) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(u, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(u, WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(InputStream is) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(is, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(is, WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(Reader r) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(r, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(r, WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(XMLStreamReader sr) throws XmlException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(sr, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(sr, WelcomeFileListType.type, options);
      }

      public static WelcomeFileListType parse(Node node) throws XmlException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(node, WelcomeFileListType.type, (XmlOptions)null);
      }

      public static WelcomeFileListType parse(Node node, XmlOptions options) throws XmlException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(node, WelcomeFileListType.type, options);
      }

      /** @deprecated */
      public static WelcomeFileListType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(xis, WelcomeFileListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WelcomeFileListType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WelcomeFileListType)XmlBeans.getContextTypeLoader().parse(xis, WelcomeFileListType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WelcomeFileListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WelcomeFileListType.type, options);
      }

      private Factory() {
      }
   }
}
