package weblogic.management.security.authorization;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.NotFoundException;

public interface PolicyReaderMBean extends StandardInterface, DescriptorBean {
   boolean policyExists(String var1);

   String getPolicyExpression(String var1) throws NotFoundException;
}
