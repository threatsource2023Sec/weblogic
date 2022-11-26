package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface PreferApplicationResourcesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PreferApplicationResourcesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("preferapplicationresourcestype0806type");

   String[] getResourceNameArray();

   String getResourceNameArray(int var1);

   XmlString[] xgetResourceNameArray();

   XmlString xgetResourceNameArray(int var1);

   int sizeOfResourceNameArray();

   void setResourceNameArray(String[] var1);

   void setResourceNameArray(int var1, String var2);

   void xsetResourceNameArray(XmlString[] var1);

   void xsetResourceNameArray(int var1, XmlString var2);

   void insertResourceName(int var1, String var2);

   void addResourceName(String var1);

   XmlString insertNewResourceName(int var1);

   XmlString addNewResourceName();

   void removeResourceName(int var1);

   public static final class Factory {
      public static PreferApplicationResourcesType newInstance() {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().newInstance(PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType newInstance(XmlOptions options) {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().newInstance(PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(String xmlAsString) throws XmlException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(File file) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(file, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(file, PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(URL u) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(u, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(u, PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(InputStream is) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(is, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(is, PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(Reader r) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(r, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(r, PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(XMLStreamReader sr) throws XmlException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(sr, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(sr, PreferApplicationResourcesType.type, options);
      }

      public static PreferApplicationResourcesType parse(Node node) throws XmlException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(node, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      public static PreferApplicationResourcesType parse(Node node, XmlOptions options) throws XmlException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(node, PreferApplicationResourcesType.type, options);
      }

      /** @deprecated */
      public static PreferApplicationResourcesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(xis, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PreferApplicationResourcesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PreferApplicationResourcesType)XmlBeans.getContextTypeLoader().parse(xis, PreferApplicationResourcesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PreferApplicationResourcesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PreferApplicationResourcesType.type, options);
      }

      private Factory() {
      }
   }
}
