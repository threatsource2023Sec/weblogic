package weblogic.security.providers.authentication;

import java.security.Principal;
import javax.security.auth.callback.CallbackHandler;

public interface IDCSTenantEnvironment {
   String getTenant(CallbackHandler var1) throws IllegalArgumentException;

   String getFilterValue(CallbackHandler var1) throws IllegalArgumentException;

   TenantNameData getTenantFromEncodedUsername(String var1);

   Principal createUserPrincipal(String var1, String var2, String var3);

   Principal createGroupPrincipal(String var1, String var2, String var3, String var4);

   Principal createAppRolePrincipal(String var1, String var2, String var3, String var4, String var5);

   Principal createClientPrincipal(String var1, String var2, String var3);

   Principal createScopePrincipal(String var1, String var2);

   boolean isWLSStandAloneEnvironment();

   boolean useTenantInPrincipal(String var1);

   void refresh(IDCSConfiguration var1);

   String getUsernameFromClient(String var1, String var2, String var3);
}
