package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class LibraryComponent extends Component {
   public LibraryComponent(ConfigurationMBeanCustomized c) {
      super(c);
   }

   public void refreshDDsIfNeeded(String[] changedFiles) {
   }
}
