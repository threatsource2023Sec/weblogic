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

public interface WlsEntryClass extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsEntryClass.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsentryclass3ccdtype");

   String getClassname();

   XmlString xgetClassname();

   void setClassname(String var1);

   void xsetClassname(XmlString var1);

   public static final class Factory {
      public static WlsEntryClass newInstance() {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().newInstance(WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass newInstance(XmlOptions options) {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().newInstance(WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(String xmlAsString) throws XmlException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(File file) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(file, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(file, WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(URL u) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(u, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(u, WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(InputStream is) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(is, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(is, WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(Reader r) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(r, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(r, WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(XMLStreamReader sr) throws XmlException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(sr, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(sr, WlsEntryClass.type, options);
      }

      public static WlsEntryClass parse(Node node) throws XmlException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(node, WlsEntryClass.type, (XmlOptions)null);
      }

      public static WlsEntryClass parse(Node node, XmlOptions options) throws XmlException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(node, WlsEntryClass.type, options);
      }

      /** @deprecated */
      public static WlsEntryClass parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(xis, WlsEntryClass.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsEntryClass parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsEntryClass)XmlBeans.getContextTypeLoader().parse(xis, WlsEntryClass.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsEntryClass.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsEntryClass.type, options);
      }

      private Factory() {
      }
   }
}
