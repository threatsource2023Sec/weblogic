package kodo.manage;

import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.remote.RemoteJMXAdaptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import javax.management.MBeanServer;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

public class ManagementConfigurationManagementJMX2Remote extends ManagementConfigurationManagement {
   private static final Localizer _loc = Localizer.forPackage(ManagementConfigurationManagementJMX2Remote.class);
   private static final String _adaptorClassName = "com.solarmetric.manage.jmx.remote.jmx2.JMX2RemoteJMXAdaptorImpl";
   private RemoteJMXAdaptor _adaptor;

   public void startConfiguration() {
      super.startConfiguration();

      try {
         Class adaptorClass = Class.forName("com.solarmetric.manage.jmx.remote.jmx2.JMX2RemoteJMXAdaptorImpl");
         this._adaptor = (RemoteJMXAdaptor)adaptorClass.newInstance();
         this.setParameter("setLog", Log.class, ManagementLog.get(this.conf));
      } catch (Exception var2) {
         ManagementLog.get(this.conf).warn(_loc.get("cant-instantiate-exception"), var2);
      }

   }

   public Collection getPlugins() {
      Collection sup = super.getPlugins();
      Collection c = new ArrayList(sup.size() + 1);
      c.addAll(sup);
      c.add(this._adaptor);
      return c;
   }

   public void initManagement(MBeanServer server) throws Exception {
      super.initManagement(server);
      this._adaptor.setMBeanServer(server);
      this._adaptor.init();
   }

   public void closeManagement() {
      this._adaptor.close();
   }

   public void setHost(String host) {
      this.setParameter("setHost", String.class, host);
   }

   public void setNamingImpl(String namingImpl) {
      this.setParameter("setNamingImpl", String.class, namingImpl);
   }

   public void setPort(int port) {
      this.setParameter("setPort", Integer.TYPE, Numbers.valueOf(port));
   }

   public void setServiceURL(String serviceUrl) {
      this.setParameter("setServiceURL", String.class, serviceUrl);
   }

   private void setParameter(String methName, Class cls, Object val) {
      if (this._adaptor != null) {
         try {
            Method meth = this._adaptor.getClass().getMethod(methName, cls);
            meth.invoke(this._adaptor, val);
         } catch (Exception var5) {
            ManagementLog.get(this.conf).warn(_loc.get("param-set-exception", methName, val), var5);
         }

      }
   }
}
