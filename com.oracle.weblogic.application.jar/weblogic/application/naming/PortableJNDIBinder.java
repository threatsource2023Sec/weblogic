package weblogic.application.naming;

import javax.naming.NamingException;

public interface PortableJNDIBinder {
   void bind(String var1, Object var2) throws NamingException;

   void unbind(String var1) throws NamingException;

   Object lookup(String var1) throws NamingException;
}
