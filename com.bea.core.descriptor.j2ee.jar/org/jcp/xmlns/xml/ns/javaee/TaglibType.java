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

public interface TaglibType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TaglibType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("taglibtype0440type");

   String getTaglibUri();

   void setTaglibUri(String var1);

   String addNewTaglibUri();

   PathType getTaglibLocation();

   void setTaglibLocation(PathType var1);

   PathType addNewTaglibLocation();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TaglibType newInstance() {
         return (TaglibType)XmlBeans.getContextTypeLoader().newInstance(TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType newInstance(XmlOptions options) {
         return (TaglibType)XmlBeans.getContextTypeLoader().newInstance(TaglibType.type, options);
      }

      public static TaglibType parse(java.lang.String xmlAsString) throws XmlException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TaglibType.type, options);
      }

      public static TaglibType parse(File file) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(file, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(file, TaglibType.type, options);
      }

      public static TaglibType parse(URL u) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(u, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(u, TaglibType.type, options);
      }

      public static TaglibType parse(InputStream is) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(is, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(is, TaglibType.type, options);
      }

      public static TaglibType parse(Reader r) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(r, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(r, TaglibType.type, options);
      }

      public static TaglibType parse(XMLStreamReader sr) throws XmlException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(sr, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(sr, TaglibType.type, options);
      }

      public static TaglibType parse(Node node) throws XmlException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(node, TaglibType.type, (XmlOptions)null);
      }

      public static TaglibType parse(Node node, XmlOptions options) throws XmlException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(node, TaglibType.type, options);
      }

      /** @deprecated */
      public static TaglibType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(xis, TaglibType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TaglibType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TaglibType)XmlBeans.getContextTypeLoader().parse(xis, TaglibType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TaglibType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TaglibType.type, options);
      }

      private Factory() {
      }
   }
}
