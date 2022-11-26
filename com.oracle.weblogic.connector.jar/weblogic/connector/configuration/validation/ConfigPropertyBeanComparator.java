package weblogic.connector.configuration.validation;

import java.util.Comparator;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.j2ee.descriptor.ConfigPropertyBean;

class ConfigPropertyBeanComparator implements Comparator {
   private PropertyNameNormalizer propertyNameNormalizer;

   public ConfigPropertyBeanComparator(PropertyNameNormalizer propertyNameNormalizer) {
      this.propertyNameNormalizer = propertyNameNormalizer;
   }

   public int compare(ConfigPropertyBean left, ConfigPropertyBean right) {
      String leftName = left.getConfigPropertyName();
      String rightName = right.getConfigPropertyName();
      return this.propertyNameNormalizer.compare(leftName, rightName);
   }
}
