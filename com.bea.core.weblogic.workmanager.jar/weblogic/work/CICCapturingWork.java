package weblogic.work;

import weblogic.invocation.ComponentInvocationContext;

public interface CICCapturingWork {
   ComponentInvocationContext getSubmittingThreadCIC();
}
