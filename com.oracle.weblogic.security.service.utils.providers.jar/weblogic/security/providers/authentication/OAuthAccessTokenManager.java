package weblogic.security.providers.authentication;

import java.io.IOException;
import java.util.List;

public interface OAuthAccessTokenManager {
   String IDCS_MY_SCOPES = "urn:opc:idm:__myscopes__";

   String getAccessToken(String var1, List var2, boolean var3) throws IOException;

   String getAccessTokenHttpHeader(String var1, List var2, boolean var3) throws IOException;

   long getTokenTimeoutWindow();

   long setTokenTimeoutWindow(long var1) throws IllegalArgumentException;

   void clearAccessTokens(String var1);

   void clearAll();
}
