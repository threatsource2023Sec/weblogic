package kodo.jdbc.profile.gui;

import com.solarmetric.ide.util.IconManager;
import com.solarmetric.profile.gui.ProfilingInterfaceImpl;
import javax.swing.ImageIcon;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.Options;

public class ProfilingViewer extends com.solarmetric.profile.gui.ProfilingViewer {
   private IconManager _iconManager = IconManager.forClass(this.getClass());

   public ProfilingViewer() {
   }

   public ProfilingViewer(JDBCConfiguration conf) {
      super(conf);
   }

   public Configuration newConfiguration() {
      return new JDBCConfigurationImpl();
   }

   public ImageIcon getSplashScreen() {
      return this._iconManager.getIcon("kodologobig.gif");
   }

   public String getName() {
      return "Kodo " + super.getName();
   }

   public ProfilingInterfaceImpl newInterface() {
      return new ProfilingInterface((JDBCConfiguration)this.getConfiguration());
   }

   public static void main(String[] args) throws Exception {
      Options opts = new Options();
      opts.setFromCmdLine(args);
      JDBCConfiguration conf = new JDBCConfigurationImpl();
      Configurations.populateConfiguration(conf, opts);
      loadFileFromArgs(new ProfilingViewer(conf), args);
   }
}
