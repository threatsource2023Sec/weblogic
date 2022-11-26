package weblogic.security.jaspic;

import java.util.List;
import java.util.Map;
import javax.security.auth.message.config.AuthConfigFactory;

public interface PersistentRegistrationSource {
   List getPersistedEntries();

   void store(String var1, AuthConfigFactory.RegistrationContext var2, Map var3);

   void delete(AuthConfigFactory.RegistrationContext var1);
}
