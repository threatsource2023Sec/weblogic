package com.solarmetric.profile.gui;

import com.solarmetric.ide.Ide;
import com.solarmetric.ide.util.SplashScreen;
import com.solarmetric.profile.ProfilingAgentImpl;
import java.awt.Dimension;
import javax.swing.JComponent;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.util.Localizer;

public abstract class ProfilingIde extends Ide {
   private static final Localizer _loc = Localizer.forPackage(ProfilingIde.class);
   private ProfilingInterfaceImpl _iface = null;
   private Configuration _conf;

   protected ProfilingIde() {
      this._conf = this.newConfiguration();
   }

   protected ProfilingIde(Configuration conf) {
      this._conf = conf;
   }

   public void setInterface(ProfilingInterfaceImpl iface) {
      this._iface = iface;
   }

   public ProfilingInterfaceImpl getInterface() {
      return this._iface;
   }

   public Configuration newConfiguration() {
      return new ConfigurationImpl();
   }

   public Configuration getConfiguration() {
      return this._conf;
   }

   public JComponent newContentContainer() {
      if (this._iface == null) {
         this._iface = ProfilingInterfaceImpl.configureInterface(this.getAgent(), this.isUpdatable(), this.newInterface());
      }

      return this._iface.getComponent();
   }

   public abstract ProfilingInterfaceImpl newInterface();

   public void initialize(JComponent container, SplashScreen status) {
      String msg = _loc.get("loading-status").getMessage();
      status.update(msg, 30);
      this.addToolbar(this._iface.getToolbar());
      status.update(msg, 10);
   }

   public Dimension getDefaultFrameSize() {
      return new Dimension(780, 580);
   }

   public abstract boolean isUpdatable();

   public abstract ProfilingAgentImpl getAgent();
}
