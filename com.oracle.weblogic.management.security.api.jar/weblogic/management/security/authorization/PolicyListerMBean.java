package weblogic.management.security.authorization;

import java.util.Properties;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.management.utils.PropertiesListerMBean;

public interface PolicyListerMBean extends PropertiesListerMBean {
   String listAllPolicies(int var1) throws NotFoundException;

   String listPoliciesByResourceType(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listPoliciesByApplication(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listPoliciesByComponent(String var1, String var2, String var3, int var4) throws NotFoundException, InvalidParameterException;

   String listChildPolicies(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listRepeatingActionsPolicies(String var1, int var2) throws NotFoundException, InvalidParameterException;

   Properties getPolicy(String var1) throws InvalidParameterException;
}
