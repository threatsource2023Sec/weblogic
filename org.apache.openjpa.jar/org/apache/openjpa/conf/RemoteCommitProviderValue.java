package org.apache.openjpa.conf;

import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.event.RemoteCommitEventManager;
import org.apache.openjpa.event.RemoteCommitProvider;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.util.Options;

public class RemoteCommitProviderValue extends PluginValue {
   private static final String[] ALIASES = new String[]{"sjvm", "org.apache.openjpa.event.SingleJVMRemoteCommitProvider", "jms", "org.apache.openjpa.event.JMSRemoteCommitProvider", "tcp", "org.apache.openjpa.event.TCPRemoteCommitProvider"};
   private Options _opts = null;
   private Boolean _transmitPersIds = null;

   public RemoteCommitProviderValue() {
      super("RemoteCommitProvider", true);
      this.setAliases(ALIASES);
   }

   public void setProperties(String props) {
      super.setProperties(props);
      this._opts = null;
      this._transmitPersIds = null;
   }

   public void setString(String str) {
      super.setString(str);
      this._opts = null;
      this._transmitPersIds = null;
   }

   public RemoteCommitProvider getProvider() {
      return (RemoteCommitProvider)this.get();
   }

   public void setProvider(RemoteCommitProvider provider) {
      this.set(provider);
   }

   public boolean getTransmitPersistedObjectIds() {
      return Boolean.TRUE.equals(this._transmitPersIds);
   }

   public void setTransmitPersistedObjectIds(boolean transmit) {
      this._transmitPersIds = transmit ? Boolean.TRUE : Boolean.FALSE;
   }

   public RemoteCommitProvider instantiateProvider(Configuration conf) {
      return this.instantiateProvider(conf, true);
   }

   public RemoteCommitProvider instantiateProvider(Configuration conf, boolean fatal) {
      return (RemoteCommitProvider)this.instantiate(RemoteCommitProvider.class, conf, fatal);
   }

   public void configureEventManager(RemoteCommitEventManager mgr) {
      this.parseOptions();
      if (this._transmitPersIds != null) {
         mgr.setTransmitPersistedObjectIds(this._transmitPersIds);
      }

   }

   public Object instantiate(Class type, Configuration conf, boolean fatal) {
      Object obj = this.newInstance(this.getClassName(), type, conf, fatal);
      this.parseOptions();
      Configurations.configureInstance(obj, conf, (Properties)this._opts, this.getProperty());
      this.set(obj, true);
      return obj;
   }

   private void parseOptions() {
      if (this._opts == null) {
         this._opts = Configurations.parseProperties(this.getProperties());
         String transmit = StringUtils.trimToNull(this._opts.removeProperty("transmitPersistedObjectIds", "TransmitPersistedObjectIds", (String)null));
         if (transmit != null) {
            this._transmitPersIds = Boolean.valueOf(transmit);
         }

      }
   }
}
