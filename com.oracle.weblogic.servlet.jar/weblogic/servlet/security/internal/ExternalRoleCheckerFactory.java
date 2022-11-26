package weblogic.servlet.security.internal;

import weblogic.servlet.internal.WebAppModule;

public interface ExternalRoleCheckerFactory {
   ExternalRoleChecker getExternalRoleChecker(WebAppModule var1);
}
