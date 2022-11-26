package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface KodoCompatibilityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoCompatibilityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodocompatibilitytype9f28type");

   boolean getCopyObjectIds();

   XmlBoolean xgetCopyObjectIds();

   boolean isSetCopyObjectIds();

   void setCopyObjectIds(boolean var1);

   void xsetCopyObjectIds(XmlBoolean var1);

   void unsetCopyObjectIds();

   boolean getCloseOnManagedCommit();

   XmlBoolean xgetCloseOnManagedCommit();

   boolean isSetCloseOnManagedCommit();

   void setCloseOnManagedCommit(boolean var1);

   void xsetCloseOnManagedCommit(XmlBoolean var1);

   void unsetCloseOnManagedCommit();

   boolean getValidateTrueChecksStore();

   XmlBoolean xgetValidateTrueChecksStore();

   boolean isSetValidateTrueChecksStore();

   void setValidateTrueChecksStore(boolean var1);

   void xsetValidateTrueChecksStore(XmlBoolean var1);

   void unsetValidateTrueChecksStore();

   boolean getValidateFalseReturnsHollow();

   XmlBoolean xgetValidateFalseReturnsHollow();

   boolean isSetValidateFalseReturnsHollow();

   void setValidateFalseReturnsHollow(boolean var1);

   void xsetValidateFalseReturnsHollow(XmlBoolean var1);

   void unsetValidateFalseReturnsHollow();

   boolean getStrictIdentityValues();

   XmlBoolean xgetStrictIdentityValues();

   boolean isSetStrictIdentityValues();

   void setStrictIdentityValues(boolean var1);

   void xsetStrictIdentityValues(XmlBoolean var1);

   void unsetStrictIdentityValues();

   boolean getQuotedNumbersInQueries();

   XmlBoolean xgetQuotedNumbersInQueries();

   boolean isSetQuotedNumbersInQueries();

   void setQuotedNumbersInQueries(boolean var1);

   void xsetQuotedNumbersInQueries(XmlBoolean var1);

   void unsetQuotedNumbersInQueries();

   public static final class Factory {
      public static KodoCompatibilityType newInstance() {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType newInstance(XmlOptions options) {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(String xmlAsString) throws XmlException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(File file) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(URL u) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(InputStream is) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(Reader r) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(XMLStreamReader sr) throws XmlException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, KodoCompatibilityType.type, options);
      }

      public static KodoCompatibilityType parse(Node node) throws XmlException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, KodoCompatibilityType.type, (XmlOptions)null);
      }

      public static KodoCompatibilityType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, KodoCompatibilityType.type, options);
      }

      /** @deprecated */
      public static KodoCompatibilityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, KodoCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoCompatibilityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, KodoCompatibilityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoCompatibilityType.type, options);
      }

      private Factory() {
      }
   }
}
