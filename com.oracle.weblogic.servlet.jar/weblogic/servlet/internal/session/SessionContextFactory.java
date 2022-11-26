package weblogic.servlet.internal.session;

import weblogic.management.DeploymentException;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.Replicator;
import weblogic.servlet.internal.WebAppServletContext;

public interface SessionContextFactory {
   SessionContext createSessionContext(WebAppServletContext var1) throws DeploymentException;

   Replicator createReplicator(HttpServer var1);
}
