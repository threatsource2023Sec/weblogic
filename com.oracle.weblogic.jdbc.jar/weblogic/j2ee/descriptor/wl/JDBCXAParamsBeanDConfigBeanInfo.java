package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDBCXAParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDBCXAParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("KeepXaConnTillTxComplete", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isKeepXaConnTillTxComplete", "setKeepXaConnTillTxComplete");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NeedTxCtxOnClose", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isNeedTxCtxOnClose", "setNeedTxCtxOnClose");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaEndOnlyOnce", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isXaEndOnlyOnce", "setXaEndOnlyOnce");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NewXaConnForCommit", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isNewXaConnForCommit", "setNewXaConnForCommit");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("KeepLogicalConnOpenOnRelease", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isKeepLogicalConnOpenOnRelease", "setKeepLogicalConnOpenOnRelease");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ResourceHealthMonitoring", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isResourceHealthMonitoring", "setResourceHealthMonitoring");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RecoverOnlyOnce", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isRecoverOnlyOnce", "setRecoverOnlyOnce");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaSetTransactionTimeout", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isXaSetTransactionTimeout", "setXaSetTransactionTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaTransactionTimeout", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "getXaTransactionTimeout", "setXaTransactionTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RollbackLocalTxUponConnClose", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "isRollbackLocalTxUponConnClose", "setRollbackLocalTxUponConnClose");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaRetryDurationSeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "getXaRetryDurationSeconds", "setXaRetryDurationSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XaRetryIntervalSeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanDConfig"), "getXaRetryIntervalSeconds", "setXaRetryIntervalSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for JDBCXAParamsBeanDConfigBeanInfo");
         }
      }
   }
}
