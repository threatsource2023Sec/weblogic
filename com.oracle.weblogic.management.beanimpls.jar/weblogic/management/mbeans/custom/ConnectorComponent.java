package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.utils.Debug;

public final class ConnectorComponent extends Component {
   private static final long serialVersionUID = -6688751447109089056L;

   public ConnectorComponent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void refreshDDsIfNeeded(String[] changedFiles) {
      if (!this.isConfig()) {
         if ((changedFiles == null || this.containsDD(changedFiles, "ra.xml") || this.containsDD(changedFiles, "weblogic-ra.xml")) && DEBUG) {
            Debug.say("Resetting Editor and Descriptor Tree");
         }

      }
   }
}
