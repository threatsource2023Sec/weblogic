package kodo.profile.gui;

import com.solarmetric.ide.util.IconManager;
import com.solarmetric.profile.gui.ProfilingInterfaceImpl;
import javax.swing.ImageIcon;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAVersion;

public class ProfilingInterface extends ProfilingInterfaceImpl {
   public ProfilingInterface() {
   }

   public ProfilingInterface(OpenJPAConfiguration conf) {
      super(conf);
   }

   public ProfilingInterfaceImpl.Ide newIde() {
      return new KodoProfilingIde();
   }

   public String getVersionString() {
      return (new OpenJPAVersion()).toString();
   }

   public String[] getInitialCategoryNames() {
      return new String[0];
   }

   protected class KodoProfilingIde extends ProfilingInterfaceImpl.Ide {
      private IconManager _iconManager = IconManager.forClass(this.getClass());

      protected KodoProfilingIde() {
         super();
      }

      public ImageIcon getSplashScreen() {
         return this._iconManager.getIcon("kodologobig.gif");
      }

      public String getName() {
         return "Kodo " + super.getName();
      }
   }
}
