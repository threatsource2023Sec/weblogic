package weblogic.jdbc.wrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import weblogic.jdbc.common.internal.DataSourceUtil;
import weblogic.jdbc.common.internal.ProxyDataSourceManager;
import weblogic.jdbc.extensions.DataSourceSwitchingCallback;

public class ProxyDataSource extends AbstractDataSource implements Referenceable {
   private static final long serialVersionUID = 5705424218240300786L;
   private String referenceKey;
   private DataSourceSwitchingCallback callback;
   private String name;
   private String proxySwitchingCallback;
   private String proxySwitchingProperties;

   public void setReferenceKey(String refKey) {
      this.referenceKey = refKey;
   }

   public Reference getReference() throws NamingException {
      Reference reference = new Reference(this.getClass().getName(), ProxyDataSourceManager.class.getCanonicalName(), (String)null);
      reference.add(new StringRefAddr("key", this.referenceKey));
      return reference;
   }

   public Object getDataSource() {
      return this.callback.getDataSource(this.name, this.proxySwitchingProperties);
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setProxySwitchingProperties(String proxySwitchingProperties) {
      this.proxySwitchingProperties = proxySwitchingProperties;
   }

   public String getProxySwitchingProperties() {
      return this.proxySwitchingProperties;
   }

   public void setProxySwitchingCallback(String proxySwitchingCallback) {
      this.proxySwitchingCallback = proxySwitchingCallback;
      if (proxySwitchingCallback == null || proxySwitchingCallback.equals("")) {
         proxySwitchingCallback = "weblogic.jdbc.common.internal.DataSourceSwitchingCallbackImpl";
      }

      try {
         this.callback = (DataSourceSwitchingCallback)DataSourceUtil.loadDriver(proxySwitchingCallback);
      } catch (Exception var3) {
         throw new RuntimeException("Unable to load callback class [" + proxySwitchingCallback + "]", var3);
      }
   }

   public String getProxySwitchingCallback() {
      return this.proxySwitchingCallback;
   }
}
