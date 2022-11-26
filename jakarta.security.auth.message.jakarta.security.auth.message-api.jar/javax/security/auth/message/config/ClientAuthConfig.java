package javax.security.auth.message.config;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.message.AuthException;

public interface ClientAuthConfig extends AuthConfig {
   ClientAuthContext getAuthContext(String var1, Subject var2, Map var3) throws AuthException;
}
