package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class XAParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(XAParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("DebugLevel", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "getDebugLevel", "setDebugLevel");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("KeepConnUntilTxCompleteEnabled", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isKeepConnUntilTxCompleteEnabled", "setKeepConnUntilTxCompleteEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EndOnlyOnceEnabled", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isEndOnlyOnceEnabled", "setEndOnlyOnceEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RecoverOnlyOnceEnabled", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isRecoverOnlyOnceEnabled", "setRecoverOnlyOnceEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TxContextOnCloseNeeded", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isTxContextOnCloseNeeded", "setTxContextOnCloseNeeded");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NewConnForCommitEnabled", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isNewConnForCommitEnabled", "setNewConnForCommitEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PreparedStatementCacheSize", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "getPreparedStatementCacheSize", "setPreparedStatementCacheSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("KeepLogicalConnOpenOnRelease", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isKeepLogicalConnOpenOnRelease", "setKeepLogicalConnOpenOnRelease");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LocalTransactionSupported", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isLocalTransactionSupported", "setLocalTransactionSupported");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ResourceHealthMonitoringEnabled", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isResourceHealthMonitoringEnabled", "setResourceHealthMonitoringEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaSetTransactionTimeout", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isXaSetTransactionTimeout", "setXaSetTransactionTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaTransactionTimeout", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "getXaTransactionTimeout", "setXaTransactionTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RollbackLocaltxUponConnclose", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "isRollbackLocaltxUponConnclose", "setRollbackLocaltxUponConnclose");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaRetryDurationSeconds", Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanDConfig"), "getXaRetryDurationSeconds", "setXaRetryDurationSeconds");
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
            throw new AssertionError("Failed to create PropertyDescriptors for XAParamsBeanDConfigBeanInfo");
         }
      }
   }
}
