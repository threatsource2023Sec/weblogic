package weblogic.jndi;

import java.io.Serializable;
import javax.naming.NamingException;
import weblogic.jndi.internal.NamingNode;

public interface Aggregatable extends Serializable {
   void onBind(NamingNode var1, String var2, Aggregatable var3) throws NamingException;

   void onRebind(NamingNode var1, String var2, Aggregatable var3) throws NamingException;

   boolean onUnbind(NamingNode var1, String var2, Aggregatable var3) throws NamingException;
}
