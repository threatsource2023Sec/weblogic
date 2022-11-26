package com.oracle.xmlns.weblogic.weblogicApplicationClient;

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

public interface ShareableType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ShareableType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("shareabletype7656type");

   String[] getIncludeArray();

   String getIncludeArray(int var1);

   XmlString[] xgetIncludeArray();

   XmlString xgetIncludeArray(int var1);

   int sizeOfIncludeArray();

   void setIncludeArray(String[] var1);

   void setIncludeArray(int var1, String var2);

   void xsetIncludeArray(XmlString[] var1);

   void xsetIncludeArray(int var1, XmlString var2);

   void insertInclude(int var1, String var2);

   void addInclude(String var1);

   XmlString insertNewInclude(int var1);

   XmlString addNewInclude();

   void removeInclude(int var1);

   String[] getExcludeArray();

   String getExcludeArray(int var1);

   XmlString[] xgetExcludeArray();

   XmlString xgetExcludeArray(int var1);

   int sizeOfExcludeArray();

   void setExcludeArray(String[] var1);

   void setExcludeArray(int var1, String var2);

   void xsetExcludeArray(XmlString[] var1);

   void xsetExcludeArray(int var1, XmlString var2);

   void insertExclude(int var1, String var2);

   void addExclude(String var1);

   XmlString insertNewExclude(int var1);

   XmlString addNewExclude();

   void removeExclude(int var1);

   String getDir();

   XmlString xgetDir();

   boolean isSetDir();

   void setDir(String var1);

   void xsetDir(XmlString var1);

   void unsetDir();

   public static final class Factory {
      public static ShareableType newInstance() {
         return (ShareableType)XmlBeans.getContextTypeLoader().newInstance(ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType newInstance(XmlOptions options) {
         return (ShareableType)XmlBeans.getContextTypeLoader().newInstance(ShareableType.type, options);
      }

      public static ShareableType parse(String xmlAsString) throws XmlException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ShareableType.type, options);
      }

      public static ShareableType parse(File file) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(file, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(file, ShareableType.type, options);
      }

      public static ShareableType parse(URL u) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(u, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(u, ShareableType.type, options);
      }

      public static ShareableType parse(InputStream is) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(is, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(is, ShareableType.type, options);
      }

      public static ShareableType parse(Reader r) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(r, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(r, ShareableType.type, options);
      }

      public static ShareableType parse(XMLStreamReader sr) throws XmlException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(sr, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(sr, ShareableType.type, options);
      }

      public static ShareableType parse(Node node) throws XmlException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(node, ShareableType.type, (XmlOptions)null);
      }

      public static ShareableType parse(Node node, XmlOptions options) throws XmlException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(node, ShareableType.type, options);
      }

      /** @deprecated */
      public static ShareableType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(xis, ShareableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ShareableType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ShareableType)XmlBeans.getContextTypeLoader().parse(xis, ShareableType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ShareableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ShareableType.type, options);
      }

      private Factory() {
      }
   }
}
