package weblogic.diagnostics.instrumentation.engine.xbean;

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

public interface WlsPackage extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsPackage.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlspackage2904type");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getPackage();

   XmlString xgetPackage();

   void setPackage(String var1);

   void xsetPackage(XmlString var1);

   public static final class Factory {
      public static WlsPackage newInstance() {
         return (WlsPackage)XmlBeans.getContextTypeLoader().newInstance(WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage newInstance(XmlOptions options) {
         return (WlsPackage)XmlBeans.getContextTypeLoader().newInstance(WlsPackage.type, options);
      }

      public static WlsPackage parse(String xmlAsString) throws XmlException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsPackage.type, options);
      }

      public static WlsPackage parse(File file) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(file, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(file, WlsPackage.type, options);
      }

      public static WlsPackage parse(URL u) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(u, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(u, WlsPackage.type, options);
      }

      public static WlsPackage parse(InputStream is) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(is, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(is, WlsPackage.type, options);
      }

      public static WlsPackage parse(Reader r) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(r, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(r, WlsPackage.type, options);
      }

      public static WlsPackage parse(XMLStreamReader sr) throws XmlException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(sr, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(sr, WlsPackage.type, options);
      }

      public static WlsPackage parse(Node node) throws XmlException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(node, WlsPackage.type, (XmlOptions)null);
      }

      public static WlsPackage parse(Node node, XmlOptions options) throws XmlException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(node, WlsPackage.type, options);
      }

      /** @deprecated */
      public static WlsPackage parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(xis, WlsPackage.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsPackage parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsPackage)XmlBeans.getContextTypeLoader().parse(xis, WlsPackage.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsPackage.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsPackage.type, options);
      }

      private Factory() {
      }
   }
}
