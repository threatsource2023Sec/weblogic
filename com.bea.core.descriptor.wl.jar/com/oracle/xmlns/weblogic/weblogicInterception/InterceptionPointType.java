package com.oracle.xmlns.weblogic.weblogicInterception;

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

public interface InterceptionPointType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InterceptionPointType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("interceptionpointtype02e3type");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String[] getNameSegmentArray();

   String getNameSegmentArray(int var1);

   XmlString[] xgetNameSegmentArray();

   XmlString xgetNameSegmentArray(int var1);

   int sizeOfNameSegmentArray();

   void setNameSegmentArray(String[] var1);

   void setNameSegmentArray(int var1, String var2);

   void xsetNameSegmentArray(XmlString[] var1);

   void xsetNameSegmentArray(int var1, XmlString var2);

   void insertNameSegment(int var1, String var2);

   void addNameSegment(String var1);

   XmlString insertNewNameSegment(int var1);

   XmlString addNewNameSegment();

   void removeNameSegment(int var1);

   public static final class Factory {
      public static InterceptionPointType newInstance() {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().newInstance(InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType newInstance(XmlOptions options) {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().newInstance(InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(String xmlAsString) throws XmlException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(File file) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(file, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(file, InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(URL u) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(u, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(u, InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(InputStream is) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(is, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(is, InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(Reader r) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(r, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(r, InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(XMLStreamReader sr) throws XmlException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(sr, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(sr, InterceptionPointType.type, options);
      }

      public static InterceptionPointType parse(Node node) throws XmlException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(node, InterceptionPointType.type, (XmlOptions)null);
      }

      public static InterceptionPointType parse(Node node, XmlOptions options) throws XmlException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(node, InterceptionPointType.type, options);
      }

      /** @deprecated */
      public static InterceptionPointType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(xis, InterceptionPointType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InterceptionPointType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InterceptionPointType)XmlBeans.getContextTypeLoader().parse(xis, InterceptionPointType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptionPointType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptionPointType.type, options);
      }

      private Factory() {
      }
   }
}
