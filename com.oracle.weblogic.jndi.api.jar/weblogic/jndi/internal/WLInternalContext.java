package weblogic.jndi.internal;

import javax.naming.NamingException;
import weblogic.jndi.WLContext;

public interface WLInternalContext extends WLContext {
   String PARTITION_NAMESPACE_PREFIX = "partition:";
   int PARTITION_NAMESPACE_PREFIX_LEN = 10;
   String DOMAIN_NAMESPACE_PREFIX = "domain:";
   int DOMAIN_NAMESPACE_PREFIX_LEN = 7;
   String SHARABLE_NAMESPACE_PREFIX = "sharable:";
   int SHARABLE_NAMESPACE_PREFIX_LEN = 9;
   String CROSS_PARTITITON_AWARE = "weblogic.jndi.crossPartitionAware";
   String ONLY_GET_CLASSTYPE = "weblogic.jndi.onlyGetClassType";
   String CREATE_NONLISTABLE_NODE = "weblogic.jndi.createNonListableNode";
   String ENABLE_VISIBILITY_CONTROL = "weblogic.jndi.mt.enableVisibilityControl";
   String PARTITION_INFOMATION = "weblogic.jndi.partitionInformation";
   String CREATE_UNDER_SHARABLE = "weblogic.jndi.createUnderSharable";

   void enableLogoutOnClose();

   void disableThreadWarningOnClose();

   boolean isBindable(String var1, Object var2) throws NamingException;

   boolean isBindable(String var1, boolean var2) throws NamingException;
}
