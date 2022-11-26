package kodo.profile.gui;

import com.solarmetric.manage.jmx.NotificationDispatchListener;
import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.gui.ProfilingInterfaceImpl;
import com.solarmetric.profile.gui.ProfilingMBeanViewer;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import kodo.manage.Management;
import kodo.profile.KodoProfilingAgentJMX;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configuration;

public class KodoProfilingMBeanViewer extends ProfilingMBeanViewer {
   public KodoProfilingMBeanViewer(MBeanServer server, ObjectInstance instance, MBeanInfo info, NotificationDispatchListener dispatcher, Configuration conf) {
      super(server, instance, info, dispatcher, conf);
   }

   protected ProfilingAgentImpl newAgentJMX(MBeanServer server, ObjectInstance instance, MBeanInfo info, NotificationDispatchListener dispatcher, Configuration conf) {
      return new KodoProfilingAgentJMX(server, instance, info, dispatcher, (OpenJPAConfiguration)conf);
   }

   public ProfilingInterfaceImpl newInterface(ProfilingAgentImpl agent, Configuration conf) {
      return ProfilingInterfaceImpl.configureInterface(agent, true, (ProfilingInterfaceImpl)Management.getInstance((OpenJPAConfiguration)conf).newVisualProfilingInterface());
   }
}
