package org.jcp.xmlns.xml.ns.javaee;

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

public interface End extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(End.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("endf9bctype");

   java.lang.String getOn();

   XmlString xgetOn();

   void setOn(java.lang.String var1);

   void xsetOn(XmlString var1);

   java.lang.String getExitStatus();

   XmlString xgetExitStatus();

   boolean isSetExitStatus();

   void setExitStatus(java.lang.String var1);

   void xsetExitStatus(XmlString var1);

   void unsetExitStatus();

   public static final class Factory {
      public static End newInstance() {
         return (End)XmlBeans.getContextTypeLoader().newInstance(End.type, (XmlOptions)null);
      }

      public static End newInstance(XmlOptions options) {
         return (End)XmlBeans.getContextTypeLoader().newInstance(End.type, options);
      }

      public static End parse(java.lang.String xmlAsString) throws XmlException {
         return (End)XmlBeans.getContextTypeLoader().parse(xmlAsString, End.type, (XmlOptions)null);
      }

      public static End parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (End)XmlBeans.getContextTypeLoader().parse(xmlAsString, End.type, options);
      }

      public static End parse(File file) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(file, End.type, (XmlOptions)null);
      }

      public static End parse(File file, XmlOptions options) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(file, End.type, options);
      }

      public static End parse(URL u) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(u, End.type, (XmlOptions)null);
      }

      public static End parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(u, End.type, options);
      }

      public static End parse(InputStream is) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(is, End.type, (XmlOptions)null);
      }

      public static End parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(is, End.type, options);
      }

      public static End parse(Reader r) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(r, End.type, (XmlOptions)null);
      }

      public static End parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (End)XmlBeans.getContextTypeLoader().parse(r, End.type, options);
      }

      public static End parse(XMLStreamReader sr) throws XmlException {
         return (End)XmlBeans.getContextTypeLoader().parse(sr, End.type, (XmlOptions)null);
      }

      public static End parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (End)XmlBeans.getContextTypeLoader().parse(sr, End.type, options);
      }

      public static End parse(Node node) throws XmlException {
         return (End)XmlBeans.getContextTypeLoader().parse(node, End.type, (XmlOptions)null);
      }

      public static End parse(Node node, XmlOptions options) throws XmlException {
         return (End)XmlBeans.getContextTypeLoader().parse(node, End.type, options);
      }

      /** @deprecated */
      public static End parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (End)XmlBeans.getContextTypeLoader().parse(xis, End.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static End parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (End)XmlBeans.getContextTypeLoader().parse(xis, End.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, End.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, End.type, options);
      }

      private Factory() {
      }
   }
}
