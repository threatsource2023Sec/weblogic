package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceIdentityAsserterBean extends SettableBean {
   String getClassName();

   void setClassName(String var1);

   CoherenceInitParamBean[] getCoherenceInitParams();

   void setCoherenceInitParams(CoherenceInitParamBean[] var1);

   CoherenceInitParamBean createCoherenceInitParam(String var1);

   CoherenceInitParamBean lookupCoherenceInitParam(String var1);

   void destroyCoherenceInitParam(CoherenceInitParamBean var1);
}
