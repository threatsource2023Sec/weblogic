package weblogic.connector.configuration.validation.wl;

import java.util.Comparator;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;

class ConfigPropertyBeanComparator implements Comparator {
   private PropertyNameNormalizer propertyNameNormalizer;

   public ConfigPropertyBeanComparator(PropertyNameNormalizer propertyNameNormalizer) {
      this.propertyNameNormalizer = propertyNameNormalizer;
   }

   public int compare(ConfigPropertyBean left, ConfigPropertyBean right) {
      String leftName = left.getName();
      String rightName = right.getName();
      return this.propertyNameNormalizer.compare(leftName, rightName);
   }
}
