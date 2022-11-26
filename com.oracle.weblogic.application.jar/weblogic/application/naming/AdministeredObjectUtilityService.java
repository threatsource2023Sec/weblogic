package weblogic.application.naming;

import javax.naming.Reference;
import javax.resource.ResourceException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;

@Contract
public interface AdministeredObjectUtilityService {
   Reference createConnectionFactory(ConnectionFactoryResourceBean var1, String var2, String var3, String var4) throws ResourceException;

   Object revokeConnectionFactory(String var1, String var2, String var3, String var4, String var5) throws ResourceException;

   Reference createAdministeredObject(AdministeredObjectBean var1, String var2, String var3, String var4) throws ResourceException;

   Object revokeAdministeredObject(String var1, String var2, String var3, String var4, String var5) throws ResourceException;

   void destroyAdministeredObject(Object var1, String var2, String var3, String var4, String var5, String var6) throws ResourceException;

   void destroyConnectionFactory(Object var1, String var2, String var3, String var4, String var5, String var6) throws ResourceException;
}
