package weblogic.security.jacc;

import javax.security.jacc.PolicyContextException;

public interface PolicyContextHandlerData {
   Object getContext(String var1) throws PolicyContextException;
}
