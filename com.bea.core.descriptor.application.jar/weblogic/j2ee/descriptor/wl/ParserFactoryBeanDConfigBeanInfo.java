package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ParserFactoryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ParserFactoryBeanDConfig.class);
   static PropertyDescriptor[] pds = null;

   public BeanDescriptor getBeanDescriptor() {
      return this.bd;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      if (pds != null) {
         return pds;
      } else {
         List plist = new ArrayList();

         try {
            PropertyDescriptor pd = new PropertyDescriptor("SaxparserFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getSaxparserFactory", "setSaxparserFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DocumentBuilderFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getDocumentBuilderFactory", "setDocumentBuilderFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransformerFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getTransformerFactory", "setTransformerFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XpathFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getXpathFactory", "setXpathFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SchemaFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getSchemaFactory", "setSchemaFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XMLInputFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getXMLInputFactory", "setXMLInputFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XMLOutputFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getXMLOutputFactory", "setXMLOutputFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XMLEventFactory", Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanDConfig"), "getXMLEventFactory", "setXMLEventFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for ParserFactoryBeanDConfigBeanInfo");
         }
      }
   }
}
