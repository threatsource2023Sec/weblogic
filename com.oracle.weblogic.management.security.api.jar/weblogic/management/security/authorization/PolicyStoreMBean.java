package weblogic.management.security.authorization;

import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.NotFoundException;
import weblogic.management.utils.PropertiesListerMBean;
import weblogic.management.utils.RemoveException;

public interface PolicyStoreMBean extends PropertiesListerMBean {
   String ACTIVE = "Active";
   String BYREFERENCE = "ByReference";
   String INACTIVE = "Inactive";
   String DOCUMENT = "Document";
   String ID = "ID";
   String VERSION = "Version";
   String STATUS = "Status";

   String listAllPolicies() throws NotFoundException;

   String listAllPoliciesAsString() throws NotFoundException;

   String listAllPolicySets() throws NotFoundException;

   String listAllPolicySetsAsString() throws NotFoundException;

   Policy readPolicy(String var1, String var2) throws NotFoundException;

   String readPolicyAsString(String var1, String var2) throws NotFoundException;

   PolicySet readPolicySet(String var1, String var2) throws NotFoundException;

   String readPolicySetAsString(String var1, String var2) throws NotFoundException;

   void addPolicy(Policy var1) throws CreateException, AlreadyExistsException;

   void addPolicy(String var1) throws CreateException, AlreadyExistsException;

   void addPolicy(Policy var1, String var2) throws CreateException, AlreadyExistsException;

   void addPolicy(String var1, String var2) throws CreateException, AlreadyExistsException;

   void addPolicySet(PolicySet var1) throws CreateException, AlreadyExistsException;

   void addPolicySet(String var1) throws CreateException, AlreadyExistsException;

   void addPolicySet(PolicySet var1, String var2) throws CreateException, AlreadyExistsException;

   void addPolicySet(String var1, String var2) throws CreateException, AlreadyExistsException;

   void modifyPolicy(Policy var1) throws CreateException, NotFoundException;

   void modifyPolicy(String var1) throws CreateException, NotFoundException;

   void modifyPolicy(Policy var1, String var2) throws CreateException, NotFoundException;

   void modifyPolicy(String var1, String var2) throws CreateException, NotFoundException;

   void modifyPolicySet(PolicySet var1) throws CreateException, NotFoundException;

   void modifyPolicySet(String var1) throws CreateException, NotFoundException;

   void modifyPolicySet(PolicySet var1, String var2) throws CreateException, NotFoundException;

   void modifyPolicySet(String var1, String var2) throws CreateException, NotFoundException;

   void modifyPolicyStatus(String var1, String var2, String var3) throws CreateException, NotFoundException;

   String getPolicyStatus(String var1, String var2) throws NotFoundException;

   void modifyPolicySetStatus(String var1, String var2, String var3) throws CreateException, NotFoundException;

   String getPolicySetStatus(String var1, String var2) throws NotFoundException;

   void deletePolicy(String var1, String var2) throws NotFoundException, RemoveException;

   void deletePolicySet(String var1, String var2) throws NotFoundException, RemoveException;
}
