package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface SizeParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SizeParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("sizeparamstype5dbbtype");

   int getInitialCapacity();

   XmlInt xgetInitialCapacity();

   boolean isSetInitialCapacity();

   void setInitialCapacity(int var1);

   void xsetInitialCapacity(XmlInt var1);

   void unsetInitialCapacity();

   int getMaxCapacity();

   XmlInt xgetMaxCapacity();

   boolean isSetMaxCapacity();

   void setMaxCapacity(int var1);

   void xsetMaxCapacity(XmlInt var1);

   void unsetMaxCapacity();

   int getCapacityIncrement();

   XmlInt xgetCapacityIncrement();

   boolean isSetCapacityIncrement();

   void setCapacityIncrement(int var1);

   void xsetCapacityIncrement(XmlInt var1);

   void unsetCapacityIncrement();

   TrueFalseType getShrinkingEnabled();

   boolean isSetShrinkingEnabled();

   void setShrinkingEnabled(TrueFalseType var1);

   TrueFalseType addNewShrinkingEnabled();

   void unsetShrinkingEnabled();

   int getShrinkPeriodMinutes();

   XmlInt xgetShrinkPeriodMinutes();

   boolean isSetShrinkPeriodMinutes();

   void setShrinkPeriodMinutes(int var1);

   void xsetShrinkPeriodMinutes(XmlInt var1);

   void unsetShrinkPeriodMinutes();

   int getShrinkFrequencySeconds();

   XmlInt xgetShrinkFrequencySeconds();

   boolean isSetShrinkFrequencySeconds();

   void setShrinkFrequencySeconds(int var1);

   void xsetShrinkFrequencySeconds(XmlInt var1);

   void unsetShrinkFrequencySeconds();

   int getHighestNumWaiters();

   XmlInt xgetHighestNumWaiters();

   boolean isSetHighestNumWaiters();

   void setHighestNumWaiters(int var1);

   void xsetHighestNumWaiters(XmlInt var1);

   void unsetHighestNumWaiters();

   int getHighestNumUnavailable();

   XmlInt xgetHighestNumUnavailable();

   boolean isSetHighestNumUnavailable();

   void setHighestNumUnavailable(int var1);

   void xsetHighestNumUnavailable(XmlInt var1);

   void unsetHighestNumUnavailable();

   public static final class Factory {
      public static SizeParamsType newInstance() {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().newInstance(SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType newInstance(XmlOptions options) {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().newInstance(SizeParamsType.type, options);
      }

      public static SizeParamsType parse(String xmlAsString) throws XmlException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SizeParamsType.type, options);
      }

      public static SizeParamsType parse(File file) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(file, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(file, SizeParamsType.type, options);
      }

      public static SizeParamsType parse(URL u) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(u, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(u, SizeParamsType.type, options);
      }

      public static SizeParamsType parse(InputStream is) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(is, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(is, SizeParamsType.type, options);
      }

      public static SizeParamsType parse(Reader r) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(r, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(r, SizeParamsType.type, options);
      }

      public static SizeParamsType parse(XMLStreamReader sr) throws XmlException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(sr, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(sr, SizeParamsType.type, options);
      }

      public static SizeParamsType parse(Node node) throws XmlException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(node, SizeParamsType.type, (XmlOptions)null);
      }

      public static SizeParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(node, SizeParamsType.type, options);
      }

      /** @deprecated */
      public static SizeParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(xis, SizeParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SizeParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SizeParamsType)XmlBeans.getContextTypeLoader().parse(xis, SizeParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SizeParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SizeParamsType.type, options);
      }

      private Factory() {
      }
   }
}
