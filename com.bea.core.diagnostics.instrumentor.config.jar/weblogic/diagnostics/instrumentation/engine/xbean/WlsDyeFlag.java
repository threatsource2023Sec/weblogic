package weblogic.diagnostics.instrumentation.engine.xbean;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface WlsDyeFlag extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsDyeFlag.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsdyeflag8dd1type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   int getIndex();

   XmlInt xgetIndex();

   void setIndex(int var1);

   void xsetIndex(XmlInt var1);

   public static final class Factory {
      public static WlsDyeFlag newInstance() {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().newInstance(WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag newInstance(XmlOptions options) {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().newInstance(WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(String xmlAsString) throws XmlException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(File file) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(file, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(file, WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(URL u) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(u, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(u, WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(InputStream is) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(is, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(is, WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(Reader r) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(r, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(r, WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(XMLStreamReader sr) throws XmlException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(sr, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(sr, WlsDyeFlag.type, options);
      }

      public static WlsDyeFlag parse(Node node) throws XmlException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(node, WlsDyeFlag.type, (XmlOptions)null);
      }

      public static WlsDyeFlag parse(Node node, XmlOptions options) throws XmlException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(node, WlsDyeFlag.type, options);
      }

      /** @deprecated */
      public static WlsDyeFlag parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(xis, WlsDyeFlag.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsDyeFlag parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsDyeFlag)XmlBeans.getContextTypeLoader().parse(xis, WlsDyeFlag.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsDyeFlag.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsDyeFlag.type, options);
      }

      private Factory() {
      }
   }
}
