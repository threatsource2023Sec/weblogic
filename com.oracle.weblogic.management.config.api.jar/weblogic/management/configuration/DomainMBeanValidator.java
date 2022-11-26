package weblogic.management.configuration;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DomainMBeanValidator {
   void validate(DomainMBean var1);
}
