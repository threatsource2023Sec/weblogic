package weblogic.ejb.spi;

import weblogic.security.UsernameAndPassword;

public interface SecurityPlugin {
   UsernameAndPassword getCredentials(String var1);
}
