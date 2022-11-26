package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface InverseManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InverseManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("inversemanagertypeac5ctype");

   String getAction();

   XmlString xgetAction();

   boolean isNilAction();

   boolean isSetAction();

   void setAction(String var1);

   void xsetAction(XmlString var1);

   void setNilAction();

   void unsetAction();

   boolean getManageLrs();

   XmlBoolean xgetManageLrs();

   boolean isSetManageLrs();

   void setManageLrs(boolean var1);

   void xsetManageLrs(XmlBoolean var1);

   void unsetManageLrs();

   public static final class Factory {
      public static InverseManagerType newInstance() {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().newInstance(InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType newInstance(XmlOptions options) {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().newInstance(InverseManagerType.type, options);
      }

      public static InverseManagerType parse(String xmlAsString) throws XmlException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InverseManagerType.type, options);
      }

      public static InverseManagerType parse(File file) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(file, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(file, InverseManagerType.type, options);
      }

      public static InverseManagerType parse(URL u) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(u, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(u, InverseManagerType.type, options);
      }

      public static InverseManagerType parse(InputStream is) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(is, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(is, InverseManagerType.type, options);
      }

      public static InverseManagerType parse(Reader r) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(r, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(r, InverseManagerType.type, options);
      }

      public static InverseManagerType parse(XMLStreamReader sr) throws XmlException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(sr, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(sr, InverseManagerType.type, options);
      }

      public static InverseManagerType parse(Node node) throws XmlException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(node, InverseManagerType.type, (XmlOptions)null);
      }

      public static InverseManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(node, InverseManagerType.type, options);
      }

      /** @deprecated */
      public static InverseManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(xis, InverseManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InverseManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InverseManagerType)XmlBeans.getContextTypeLoader().parse(xis, InverseManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InverseManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InverseManagerType.type, options);
      }

      private Factory() {
      }
   }
}
