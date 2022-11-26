package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicEnterpriseBeanBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicEnterpriseBeanBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("EjbName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getEjbName", "setEjbName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EntityDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getEntityDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EntityDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isEntityDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatelessSessionDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getStatelessSessionDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatelessSessionDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isStatelessSessionDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatefulSessionDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getStatefulSessionDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatefulSessionDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isStatefulSessionDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SingletonSessionDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getSingletonSessionDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SingletonSessionDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isSingletonSessionDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageDrivenDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getMessageDrivenDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageDrivenDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isMessageDrivenDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getTransactionDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isTransactionDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IiopSecurityDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getIiopSecurityDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IiopSecurityDescriptorSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isIiopSecurityDescriptorSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ResourceDescriptions", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getResourceDescriptions", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ResourceEnvDescriptions", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getResourceEnvDescriptions", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EjbReferenceDescriptions", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getEjbReferenceDescriptions", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ServiceReferenceDescriptions", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getServiceReferenceDescriptions", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableCallByReference", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isEnableCallByReference", "setEnableCallByReference");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NetworkAccessPoint", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getNetworkAccessPoint", "setNetworkAccessPoint");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ClientsOnSameServer", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isClientsOnSameServer", "setClientsOnSameServer");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RunAsPrincipalName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getRunAsPrincipalName", "setRunAsPrincipalName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("CreateAsPrincipalName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getCreateAsPrincipalName", "setCreateAsPrincipalName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RemoveAsPrincipalName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getRemoveAsPrincipalName", "setRemoveAsPrincipalName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("PassivateAsPrincipalName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getPassivateAsPrincipalName", "setPassivateAsPrincipalName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getJNDIName", "setJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", true);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LocalJNDIName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getLocalJNDIName", "setLocalJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", true);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DispatchPolicy", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getDispatchPolicy", "setDispatchPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RemoteClientTimeout", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getRemoteClientTimeout", "setRemoteClientTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StickToFirstServer", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "isStickToFirstServer", "setStickToFirstServer");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JndiBinding", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getJndiBinding", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicEnterpriseBeanBeanDConfigBeanInfo");
         }
      }
   }
}