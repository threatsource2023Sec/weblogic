package weblogic.servlet.spi;

import java.util.Collection;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.protocol.ServerChannel;
import weblogic.servlet.internal.HttpServer;

public interface HttpServerManager {
   boolean isProductionModeEnabled();

   void startWebServers();

   void stopWebServers();

   HttpServer defaultHttpServer();

   String fakeDefaultServerName();

   boolean isDefaultHttpServer(HttpServer var1);

   HttpServer getHttpServer(String var1);

   Collection getHttpServers();

   HttpServer getVirtualHost(String var1, String var2);

   HttpServer getVirtualHost(String var1);

   HttpServer getVirtualHost(ServerChannel var1);

   HttpServer getVirtualTarget(String var1);

   HttpServer findHttpServer(String var1, String var2);

   HttpServer findHttpServer(String var1);

   HttpServer initWebServer(VirtualHostMBean var1) throws DeploymentException;

   HttpServer deployPartitionWebServer(VirtualTargetMBean var1) throws DeploymentException;

   void undeployPartitionWebServer(VirtualTargetMBean var1);

   void registerPartitionWebServer(VirtualTargetMBean var1) throws DeploymentException;
}
