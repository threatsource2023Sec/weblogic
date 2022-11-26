package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.ComponentProvider;
import com.solarmetric.ide.ui.ToolbarProvider;
import com.solarmetric.manage.jmx.NotificationDispatchListener;
import com.solarmetric.profile.ProfilingAgentImpl;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public abstract class ProfilingMBeanViewer implements ComponentProvider, ToolbarProvider, Configurable {
   private ProfilingAgentImpl _agent;
   private ProfilingInterfaceImpl _interface;

   protected ProfilingMBeanViewer() {
   }

   public ProfilingMBeanViewer(MBeanServer server, ObjectInstance instance, MBeanInfo info, NotificationDispatchListener dispatcher, Configuration conf) {
      this._agent = this.newAgentJMX(server, instance, info, dispatcher, conf);
      this._interface = this.newInterface(this._agent, conf);
   }

   protected ProfilingAgentImpl newAgentJMX(MBeanServer server, ObjectInstance instance, MBeanInfo info, NotificationDispatchListener dispatcher, Configuration conf) {
      return new ProfilingAgentJMX(server, instance, info, dispatcher, conf);
   }

   public abstract ProfilingInterfaceImpl newInterface(ProfilingAgentImpl var1, Configuration var2);

   public JComponent getComponent() {
      return this._interface.getComponent();
   }

   public JToolBar getToolbar() {
      return this._interface.getToolbar();
   }

   public void setConfiguration(Configuration conf) {
      this._interface.setConfiguration(conf);
   }

   public void startConfiguration() {
      this._interface.startConfiguration();
   }

   public void endConfiguration() {
      this._interface.endConfiguration();
   }
}
