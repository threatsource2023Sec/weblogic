package weblogic.management.deploy;

import org.jvnet.hk2.annotations.Contract;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;

@Contract
public interface TargetHelperInterface {
   TargetMBean[] lookupTargetMBeans(DomainMBean var1, String[] var2, String var3) throws InvalidTargetException;

   TargetMBean[] lookupTargetMBeans(DomainMBean var1, String[] var2) throws InvalidTargetException;

   int getTypeForTarget(String var1);
}
