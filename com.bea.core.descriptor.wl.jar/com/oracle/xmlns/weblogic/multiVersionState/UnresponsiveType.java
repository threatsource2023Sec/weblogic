package com.oracle.xmlns.weblogic.multiVersionState;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface UnresponsiveType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UnresponsiveType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("unresponsivetype844dtype");

   String[] getTargetArray();

   String getTargetArray(int var1);

   XmlString[] xgetTargetArray();

   XmlString xgetTargetArray(int var1);

   int sizeOfTargetArray();

   void setTargetArray(String[] var1);

   void setTargetArray(int var1, String var2);

   void xsetTargetArray(XmlString[] var1);

   void xsetTargetArray(int var1, XmlString var2);

   void insertTarget(int var1, String var2);

   void addTarget(String var1);

   XmlString insertNewTarget(int var1);

   XmlString addNewTarget();

   void removeTarget(int var1);

   public static final class Factory {
      public static UnresponsiveType newInstance() {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().newInstance(UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType newInstance(XmlOptions options) {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().newInstance(UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(String xmlAsString) throws XmlException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(File file) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(file, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(file, UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(URL u) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(u, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(u, UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(InputStream is) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(is, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(is, UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(Reader r) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(r, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(r, UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(XMLStreamReader sr) throws XmlException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(sr, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(sr, UnresponsiveType.type, options);
      }

      public static UnresponsiveType parse(Node node) throws XmlException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(node, UnresponsiveType.type, (XmlOptions)null);
      }

      public static UnresponsiveType parse(Node node, XmlOptions options) throws XmlException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(node, UnresponsiveType.type, options);
      }

      /** @deprecated */
      public static UnresponsiveType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(xis, UnresponsiveType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UnresponsiveType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UnresponsiveType)XmlBeans.getContextTypeLoader().parse(xis, UnresponsiveType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnresponsiveType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnresponsiveType.type, options);
      }

      private Factory() {
      }
   }
}
