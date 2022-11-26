package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface DistributedDestinationType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DistributedDestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("distributeddestinationtype9804type");

   String getJndiName();

   XmlString xgetJndiName();

   boolean isNilJndiName();

   boolean isSetJndiName();

   void setJndiName(String var1);

   void xsetJndiName(XmlString var1);

   void setNilJndiName();

   void unsetJndiName();

   String getLocalJndiName();

   XmlString xgetLocalJndiName();

   boolean isNilLocalJndiName();

   boolean isSetLocalJndiName();

   void setLocalJndiName(String var1);

   void xsetLocalJndiName(XmlString var1);

   void setNilLocalJndiName();

   void unsetLocalJndiName();

   String getLoadBalancingPolicy();

   XmlString xgetLoadBalancingPolicy();

   boolean isNilLoadBalancingPolicy();

   boolean isSetLoadBalancingPolicy();

   void setLoadBalancingPolicy(String var1);

   void xsetLoadBalancingPolicy(XmlString var1);

   void setNilLoadBalancingPolicy();

   void unsetLoadBalancingPolicy();

   UnitOfOrderRoutingType.Enum getUnitOfOrderRouting();

   UnitOfOrderRoutingType xgetUnitOfOrderRouting();

   boolean isSetUnitOfOrderRouting();

   void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum var1);

   void xsetUnitOfOrderRouting(UnitOfOrderRoutingType var1);

   void unsetUnitOfOrderRouting();

   SafExportPolicy.Enum getSafExportPolicy();

   SafExportPolicy xgetSafExportPolicy();

   boolean isSetSafExportPolicy();

   void setSafExportPolicy(SafExportPolicy.Enum var1);

   void xsetSafExportPolicy(SafExportPolicy var1);

   void unsetSafExportPolicy();

   public static final class Factory {
      public static DistributedDestinationType newInstance() {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType newInstance(XmlOptions options) {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(String xmlAsString) throws XmlException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(File file) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(file, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(file, DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(URL u) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(u, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(u, DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(InputStream is) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(is, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(is, DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(Reader r) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(r, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(r, DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(XMLStreamReader sr) throws XmlException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(sr, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(sr, DistributedDestinationType.type, options);
      }

      public static DistributedDestinationType parse(Node node) throws XmlException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(node, DistributedDestinationType.type, (XmlOptions)null);
      }

      public static DistributedDestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(node, DistributedDestinationType.type, options);
      }

      /** @deprecated */
      public static DistributedDestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xis, DistributedDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DistributedDestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xis, DistributedDestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedDestinationType.type, options);
      }

      private Factory() {
      }
   }

   public interface SafExportPolicy extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafExportPolicy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safexportpolicy08a2elemtype");
      Enum ALL = DistributedDestinationType.SafExportPolicy.Enum.forString("All");
      Enum NONE = DistributedDestinationType.SafExportPolicy.Enum.forString("None");
      int INT_ALL = 1;
      int INT_NONE = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static SafExportPolicy newValue(Object obj) {
            return (SafExportPolicy)DistributedDestinationType.SafExportPolicy.type.newValue(obj);
         }

         public static SafExportPolicy newInstance() {
            return (SafExportPolicy)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationType.SafExportPolicy.type, (XmlOptions)null);
         }

         public static SafExportPolicy newInstance(XmlOptions options) {
            return (SafExportPolicy)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationType.SafExportPolicy.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ALL = 1;
         static final int INT_NONE = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("All", 1), new Enum("None", 2)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
