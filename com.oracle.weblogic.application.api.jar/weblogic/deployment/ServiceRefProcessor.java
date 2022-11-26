package weblogic.deployment;

import javax.naming.Context;
import javax.naming.NamingException;

public interface ServiceRefProcessor {
   void bindServiceRef(Context var1, Context var2, String var3) throws NamingException, ServiceRefProcessorException;

   void unbindServiceRef(Context var1) throws NamingException;
}
