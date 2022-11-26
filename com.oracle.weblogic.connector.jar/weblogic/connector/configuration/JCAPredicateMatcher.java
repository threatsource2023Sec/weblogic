package weblogic.connector.configuration;

import weblogic.application.descriptor.PredicateMatcher;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.PropertyNameNormalizerFactory;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;

public class JCAPredicateMatcher implements PredicateMatcher {
   PropertyNameNormalizer normalizer;

   public JCAPredicateMatcher(String version) {
      this.normalizer = PropertyNameNormalizerFactory.getPropertyNameNormalizer(version);
   }

   public boolean match(DescriptorBean currBean, Object getValue, Object literalValue) {
      return currBean instanceof ConfigPropertyBean ? this.normalizer.normalize((String)literalValue).equals(this.normalizer.normalize((String)getValue)) : literalValue.equals(getValue);
   }
}
