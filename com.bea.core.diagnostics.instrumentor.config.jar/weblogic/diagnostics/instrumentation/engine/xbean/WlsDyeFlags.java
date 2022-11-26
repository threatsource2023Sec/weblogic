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

public interface WlsDyeFlags extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsDyeFlags.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsdyeflags32e0type");

   WlsDyeFlag[] getWlsDyeFlagArray();

   WlsDyeFlag getWlsDyeFlagArray(int var1);

   int sizeOfWlsDyeFlagArray();

   void setWlsDyeFlagArray(WlsDyeFlag[] var1);

   void setWlsDyeFlagArray(int var1, WlsDyeFlag var2);

   WlsDyeFlag insertNewWlsDyeFlag(int var1);

   WlsDyeFlag addNewWlsDyeFlag();

   void removeWlsDyeFlag(int var1);

   public static final class Factory {
      public static WlsDyeFlags newInstance() {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().newInstance(WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags newInstance(XmlOptions options) {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().newInstance(WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(String xmlAsString) throws XmlException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(File file) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(file, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(file, WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(URL u) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(u, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(u, WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(InputStream is) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(is, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(is, WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(Reader r) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(r, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(r, WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(XMLStreamReader sr) throws XmlException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(sr, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(sr, WlsDyeFlags.type, options);
      }

      public static WlsDyeFlags parse(Node node) throws XmlException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(node, WlsDyeFlags.type, (XmlOptions)null);
      }

      public static WlsDyeFlags parse(Node node, XmlOptions options) throws XmlException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(node, WlsDyeFlags.type, options);
      }

      /** @deprecated */
      public static WlsDyeFlags parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(xis, WlsDyeFlags.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsDyeFlags parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsDyeFlags)XmlBeans.getContextTypeLoader().parse(xis, WlsDyeFlags.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsDyeFlags.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsDyeFlags.type, options);
      }

      private Factory() {
      }
   }
}
