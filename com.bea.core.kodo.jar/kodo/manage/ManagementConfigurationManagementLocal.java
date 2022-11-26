package kodo.manage;

import com.solarmetric.manage.jmx.gui.JMXGui;
import com.solarmetric.manage.jmx.gui.JMXInterface;
import java.util.ArrayList;
import java.util.Collection;
import javax.management.MBeanServer;

public class ManagementConfigurationManagementLocal extends ManagementConfigurationManagement {
   private JMXInterface _iface = null;

   public void startConfiguration() {
      super.startConfiguration();
      this._iface = new JMXGui(this.conf);
   }

   public Collection getPlugins() {
      Collection sup = super.getPlugins();
      Collection c = new ArrayList(sup.size() + 1);
      c.addAll(sup);
      c.add(this._iface);
      return c;
   }

   public void initManagement(MBeanServer server) throws Exception {
      super.initManagement(server);
      this._iface.setMBeanServer(server);
      (new Thread(this._iface)).start();
   }
}
