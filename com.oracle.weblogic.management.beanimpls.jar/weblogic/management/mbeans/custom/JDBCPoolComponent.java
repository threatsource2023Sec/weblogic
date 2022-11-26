package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class JDBCPoolComponent extends Component {
   public JDBCPoolComponent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void refreshDDsIfNeeded(String[] changedFiles) {
   }
}
