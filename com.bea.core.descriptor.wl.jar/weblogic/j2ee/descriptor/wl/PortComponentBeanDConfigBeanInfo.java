package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class PortComponentBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(PortComponentBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("PortComponentName", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getPortComponentName", "setPortComponentName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ServiceEndpointAddress", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getServiceEndpointAddress", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AuthConstraint", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getAuthConstraint", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoginConfig", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getLoginConfig", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransportGuarantee", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getTransportGuarantee", "setTransportGuarantee");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DeploymentListenerList", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getDeploymentListenerList", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Wsdl", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getWsdl", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionTimeout", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getTransactionTimeout", "setTransactionTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CallbackProtocol", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getCallbackProtocol", "setCallbackProtocol");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StreamAttachments", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getStreamAttachments", "setStreamAttachments");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ValidateRequest", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "isValidateRequest", "setValidateRequest");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("HttpFlushResponse", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "isHttpFlushResponse", "setHttpFlushResponse");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("HttpResponseBuffersize", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getHttpResponseBuffersize", "setHttpResponseBuffersize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ReliabilityConfig", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getReliabilityConfig", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("PersistenceConfig", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getPersistenceConfig", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BufferingConfig", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getBufferingConfig", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("WSATConfig", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getWSATConfig", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Operations", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getOperations", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SoapjmsServiceEndpointAddress", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getSoapjmsServiceEndpointAddress", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("FastInfoset", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "isFastInfoset", "setFastInfoset");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoggingLevel", Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanDConfig"), "getLoggingLevel", "setLoggingLevel");
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
            throw new AssertionError("Failed to create PropertyDescriptors for PortComponentBeanDConfigBeanInfo");
         }
      }
   }
}
