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

public interface WlsEntryClasses extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsEntryClasses.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsentryclassesdd1ftype");

   WlsEntryClass[] getWlsEntryClassArray();

   WlsEntryClass getWlsEntryClassArray(int var1);

   int sizeOfWlsEntryClassArray();

   void setWlsEntryClassArray(WlsEntryClass[] var1);

   void setWlsEntryClassArray(int var1, WlsEntryClass var2);

   WlsEntryClass insertNewWlsEntryClass(int var1);

   WlsEntryClass addNewWlsEntryClass();

   void removeWlsEntryClass(int var1);

   public static final class Factory {
      public static WlsEntryClasses newInstance() {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().newInstance(WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses newInstance(XmlOptions options) {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().newInstance(WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(String xmlAsString) throws XmlException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(File file) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(file, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(file, WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(URL u) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(u, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(u, WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(InputStream is) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(is, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(is, WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(Reader r) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(r, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(r, WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(XMLStreamReader sr) throws XmlException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(sr, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(sr, WlsEntryClasses.type, options);
      }

      public static WlsEntryClasses parse(Node node) throws XmlException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(node, WlsEntryClasses.type, (XmlOptions)null);
      }

      public static WlsEntryClasses parse(Node node, XmlOptions options) throws XmlException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(node, WlsEntryClasses.type, options);
      }

      /** @deprecated */
      public static WlsEntryClasses parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(xis, WlsEntryClasses.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsEntryClasses parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsEntryClasses)XmlBeans.getContextTypeLoader().parse(xis, WlsEntryClasses.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsEntryClasses.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsEntryClasses.type, options);
      }

      private Factory() {
      }
   }
}
