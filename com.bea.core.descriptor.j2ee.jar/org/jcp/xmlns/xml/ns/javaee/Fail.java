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

public interface Fail extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Fail.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("faile627type");

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
      public static Fail newInstance() {
         return (Fail)XmlBeans.getContextTypeLoader().newInstance(Fail.type, (XmlOptions)null);
      }

      public static Fail newInstance(XmlOptions options) {
         return (Fail)XmlBeans.getContextTypeLoader().newInstance(Fail.type, options);
      }

      public static Fail parse(java.lang.String xmlAsString) throws XmlException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(xmlAsString, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(xmlAsString, Fail.type, options);
      }

      public static Fail parse(File file) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(file, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(file, Fail.type, options);
      }

      public static Fail parse(URL u) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(u, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(u, Fail.type, options);
      }

      public static Fail parse(InputStream is) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(is, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(is, Fail.type, options);
      }

      public static Fail parse(Reader r) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(r, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(r, Fail.type, options);
      }

      public static Fail parse(XMLStreamReader sr) throws XmlException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(sr, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(sr, Fail.type, options);
      }

      public static Fail parse(Node node) throws XmlException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(node, Fail.type, (XmlOptions)null);
      }

      public static Fail parse(Node node, XmlOptions options) throws XmlException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(node, Fail.type, options);
      }

      /** @deprecated */
      public static Fail parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(xis, Fail.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Fail parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Fail)XmlBeans.getContextTypeLoader().parse(xis, Fail.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Fail.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Fail.type, options);
      }

      private Factory() {
      }
   }
}
