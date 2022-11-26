package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface JspConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JspConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("jspconfigtype8343type");

   TaglibType[] getTaglibArray();

   TaglibType getTaglibArray(int var1);

   int sizeOfTaglibArray();

   void setTaglibArray(TaglibType[] var1);

   void setTaglibArray(int var1, TaglibType var2);

   TaglibType insertNewTaglib(int var1);

   TaglibType addNewTaglib();

   void removeTaglib(int var1);

   JspPropertyGroupType[] getJspPropertyGroupArray();

   JspPropertyGroupType getJspPropertyGroupArray(int var1);

   int sizeOfJspPropertyGroupArray();

   void setJspPropertyGroupArray(JspPropertyGroupType[] var1);

   void setJspPropertyGroupArray(int var1, JspPropertyGroupType var2);

   JspPropertyGroupType insertNewJspPropertyGroup(int var1);

   JspPropertyGroupType addNewJspPropertyGroup();

   void removeJspPropertyGroup(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JspConfigType newInstance() {
         return (JspConfigType)XmlBeans.getContextTypeLoader().newInstance(JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType newInstance(XmlOptions options) {
         return (JspConfigType)XmlBeans.getContextTypeLoader().newInstance(JspConfigType.type, options);
      }

      public static JspConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspConfigType.type, options);
      }

      public static JspConfigType parse(File file) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(file, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(file, JspConfigType.type, options);
      }

      public static JspConfigType parse(URL u) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(u, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(u, JspConfigType.type, options);
      }

      public static JspConfigType parse(InputStream is) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(is, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(is, JspConfigType.type, options);
      }

      public static JspConfigType parse(Reader r) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(r, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(r, JspConfigType.type, options);
      }

      public static JspConfigType parse(XMLStreamReader sr) throws XmlException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(sr, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(sr, JspConfigType.type, options);
      }

      public static JspConfigType parse(Node node) throws XmlException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(node, JspConfigType.type, (XmlOptions)null);
      }

      public static JspConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(node, JspConfigType.type, options);
      }

      /** @deprecated */
      public static JspConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(xis, JspConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JspConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JspConfigType)XmlBeans.getContextTypeLoader().parse(xis, JspConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspConfigType.type, options);
      }

      private Factory() {
      }
   }
}
