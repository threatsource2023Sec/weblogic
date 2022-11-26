package weblogic.diagnostics.instrumentation.engine.xbean;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WlsPackages extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsPackages.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlspackagesfe0dtype");

   WlsPackage[] getWlsPackageArray();

   WlsPackage getWlsPackageArray(int var1);

   int sizeOfWlsPackageArray();

   void setWlsPackageArray(WlsPackage[] var1);

   void setWlsPackageArray(int var1, WlsPackage var2);

   WlsPackage insertNewWlsPackage(int var1);

   WlsPackage addNewWlsPackage();

   void removeWlsPackage(int var1);

   public static final class Factory {
      public static WlsPackages newInstance() {
         return (WlsPackages)XmlBeans.getContextTypeLoader().newInstance(WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages newInstance(XmlOptions options) {
         return (WlsPackages)XmlBeans.getContextTypeLoader().newInstance(WlsPackages.type, options);
      }

      public static WlsPackages parse(String xmlAsString) throws XmlException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsPackages.type, options);
      }

      public static WlsPackages parse(File file) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(file, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(file, WlsPackages.type, options);
      }

      public static WlsPackages parse(URL u) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(u, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(u, WlsPackages.type, options);
      }

      public static WlsPackages parse(InputStream is) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(is, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(is, WlsPackages.type, options);
      }

      public static WlsPackages parse(Reader r) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(r, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(r, WlsPackages.type, options);
      }

      public static WlsPackages parse(XMLStreamReader sr) throws XmlException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(sr, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(sr, WlsPackages.type, options);
      }

      public static WlsPackages parse(Node node) throws XmlException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(node, WlsPackages.type, (XmlOptions)null);
      }

      public static WlsPackages parse(Node node, XmlOptions options) throws XmlException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(node, WlsPackages.type, options);
      }

      /** @deprecated */
      public static WlsPackages parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(xis, WlsPackages.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsPackages parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsPackages)XmlBeans.getContextTypeLoader().parse(xis, WlsPackages.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsPackages.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsPackages.type, options);
      }

      private Factory() {
      }
   }
}
