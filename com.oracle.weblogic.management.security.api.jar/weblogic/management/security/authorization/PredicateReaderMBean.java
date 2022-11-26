package weblogic.management.security.authorization;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface PredicateReaderMBean extends StandardInterface, DescriptorBean {
   boolean isRegisteredPredicate(String var1);

   String[] getRegisteredPredicates(String var1);
}
