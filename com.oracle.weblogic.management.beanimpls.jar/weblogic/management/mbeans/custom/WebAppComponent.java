package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.utils.Debug;

public class WebAppComponent extends Component {
   private static final long serialVersionUID = 5422191349969975414L;

   public WebAppComponent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void refreshDDsIfNeeded(String[] changedFiles) {
      if (!this.isConfig()) {
         if ((changedFiles == null || this.containsDD(changedFiles, "weblogic.xml") || this.containsDD(changedFiles, "web.xml")) && DEBUG) {
            Debug.say("Resetting Editor and Descriptor Tree");
         }

      }
   }
}
