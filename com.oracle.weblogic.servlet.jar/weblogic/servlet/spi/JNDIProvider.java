package weblogic.servlet.spi;

import javax.naming.Context;
import javax.naming.NamingException;

public interface JNDIProvider {
   Context lookupInitialContext() throws NamingException;

   void pushContext(Context var1);

   void popContext();

   Context createApplicationContext(String var1) throws NamingException;
}
