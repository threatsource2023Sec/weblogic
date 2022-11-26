package weblogic.jms.extensions;

import weblogic.jndi.WLContext;

public interface WLJMSOBSContext extends WLContext {
   String SECURITY_POLICY = "weblogic.jndi.securityPolicy";
   String OBJECT_BASED = "ObjectBased";
   String OBJECT_BASED_HYBRID = "ObjectBasedHybrid";
}
