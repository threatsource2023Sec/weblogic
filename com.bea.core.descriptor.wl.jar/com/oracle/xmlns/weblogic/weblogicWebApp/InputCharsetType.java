package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface InputCharsetType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InputCharsetType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("inputcharsettypec68atype");

   ResourcePathType getResourcePath();

   void setResourcePath(ResourcePathType var1);

   ResourcePathType addNewResourcePath();

   JavaCharsetNameType getJavaCharsetName();

   void setJavaCharsetName(JavaCharsetNameType var1);

   JavaCharsetNameType addNewJavaCharsetName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InputCharsetType newInstance() {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().newInstance(InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType newInstance(XmlOptions options) {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().newInstance(InputCharsetType.type, options);
      }

      public static InputCharsetType parse(String xmlAsString) throws XmlException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InputCharsetType.type, options);
      }

      public static InputCharsetType parse(File file) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(file, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(file, InputCharsetType.type, options);
      }

      public static InputCharsetType parse(URL u) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(u, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(u, InputCharsetType.type, options);
      }

      public static InputCharsetType parse(InputStream is) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(is, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(is, InputCharsetType.type, options);
      }

      public static InputCharsetType parse(Reader r) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(r, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(r, InputCharsetType.type, options);
      }

      public static InputCharsetType parse(XMLStreamReader sr) throws XmlException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(sr, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(sr, InputCharsetType.type, options);
      }

      public static InputCharsetType parse(Node node) throws XmlException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(node, InputCharsetType.type, (XmlOptions)null);
      }

      public static InputCharsetType parse(Node node, XmlOptions options) throws XmlException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(node, InputCharsetType.type, options);
      }

      /** @deprecated */
      public static InputCharsetType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(xis, InputCharsetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InputCharsetType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InputCharsetType)XmlBeans.getContextTypeLoader().parse(xis, InputCharsetType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InputCharsetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InputCharsetType.type, options);
      }

      private Factory() {
      }
   }
}
