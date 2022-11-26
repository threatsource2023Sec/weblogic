package weblogic.cluster;

import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ServiceAdvertiser {
   void offerService(String var1, String var2, Object var3) throws NamingException;

   void createSubcontext(String var1) throws NamingException;

   void replaceService(String var1, String var2, Object var3, Object var4) throws NamingException;

   void retractService(String var1, String var2, Object var3) throws NamingException;
}
