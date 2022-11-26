package com.bea.connector.diagnostic;

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

public interface HealthType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HealthType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("healthtype748ftype");

   String getState();

   XmlString xgetState();

   void setState(String var1);

   void xsetState(XmlString var1);

   String[] getReasonArray();

   String getReasonArray(int var1);

   XmlString[] xgetReasonArray();

   XmlString xgetReasonArray(int var1);

   int sizeOfReasonArray();

   void setReasonArray(String[] var1);

   void setReasonArray(int var1, String var2);

   void xsetReasonArray(XmlString[] var1);

   void xsetReasonArray(int var1, XmlString var2);

   void insertReason(int var1, String var2);

   void addReason(String var1);

   XmlString insertNewReason(int var1);

   XmlString addNewReason();

   void removeReason(int var1);

   public static final class Factory {
      public static HealthType newInstance() {
         return (HealthType)XmlBeans.getContextTypeLoader().newInstance(HealthType.type, (XmlOptions)null);
      }

      public static HealthType newInstance(XmlOptions options) {
         return (HealthType)XmlBeans.getContextTypeLoader().newInstance(HealthType.type, options);
      }

      public static HealthType parse(String xmlAsString) throws XmlException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HealthType.type, options);
      }

      public static HealthType parse(File file) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(file, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(file, HealthType.type, options);
      }

      public static HealthType parse(URL u) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(u, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(u, HealthType.type, options);
      }

      public static HealthType parse(InputStream is) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(is, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(is, HealthType.type, options);
      }

      public static HealthType parse(Reader r) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(r, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(r, HealthType.type, options);
      }

      public static HealthType parse(XMLStreamReader sr) throws XmlException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(sr, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(sr, HealthType.type, options);
      }

      public static HealthType parse(Node node) throws XmlException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(node, HealthType.type, (XmlOptions)null);
      }

      public static HealthType parse(Node node, XmlOptions options) throws XmlException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(node, HealthType.type, options);
      }

      /** @deprecated */
      public static HealthType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(xis, HealthType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HealthType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HealthType)XmlBeans.getContextTypeLoader().parse(xis, HealthType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HealthType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HealthType.type, options);
      }

      private Factory() {
      }
   }
}
