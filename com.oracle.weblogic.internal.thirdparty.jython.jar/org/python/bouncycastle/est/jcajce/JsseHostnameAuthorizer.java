package org.python.bouncycastle.est.jcajce;

import java.io.IOException;
import javax.net.ssl.SSLSession;

public interface JsseHostnameAuthorizer {
   boolean verified(String var1, SSLSession var2) throws IOException;
}
