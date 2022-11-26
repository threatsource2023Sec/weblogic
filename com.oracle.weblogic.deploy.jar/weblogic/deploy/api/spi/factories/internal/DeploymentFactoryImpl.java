package weblogic.deploy.api.spi.factories.internal;

import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.deploy.WebLogicDeploymentManagerImpl;
import weblogic.deploy.api.spi.factories.WebLogicDeploymentFactory;
import weblogic.utils.net.InetAddressHelper;

public class DeploymentFactoryImpl implements WebLogicDeploymentFactory {
   private static final String JMX_DOMAIN_ACCESS_CLASS = "weblogic.deploy.api.spi.deploy.internal.ServerConnectionImpl";
   private static final String SERVER_CONNECTION_CLASS = "weblogic.deploy.api.spi.deploy.internal.ServerConnectionImpl";
   private static final String DEPLOYMENT_MANAGER_VERSION = "1.0";
   private String host;
   private int port;
   private static final boolean debug = Debug.isDebug("factory");
   private static final String[] uris = new String[]{"deployer:WebLogic", "remote:deployer:WebLogic", "authenticated:deployer:WebLogic"};
   private String deployerUri;
   private URI connectUri;

   public DeploymentManager getDisconnectedDeploymentManager(String uri) throws DeploymentManagerCreationException {
      try {
         ConfigHelper.checkParam("uri", uri);
         if (debug) {
            Debug.say("Getting disconnected DM with uri: " + uri);
         }

         this.validateUri(uri);
         DeploymentManager dm = new WebLogicDeploymentManagerImpl(this.deployerUri);
         return dm;
      } catch (IllegalArgumentException var3) {
         throw new DeploymentManagerCreationException(var3.getMessage());
      }
   }

   public DeploymentManager getDeploymentManager(String uri, String username, String password) throws DeploymentManagerCreationException {
      try {
         ConfigHelper.checkParam("uri", uri);
         if (debug) {
            Debug.say("Getting connected DM with uri: " + uri + ", user: " + username);
         }

         this.validateUri(uri);
         if ((this.host == null || this.port == -1) && !uri.toString().startsWith("authenticated:deployer:WebLogic")) {
            throw new DeploymentManagerCreationException(SPIDeployerLogger.getInvalidServerAuth(uri));
         } else {
            return new WebLogicDeploymentManagerImpl("weblogic.deploy.api.spi.deploy.internal.ServerConnectionImpl", this.deployerUri, this.connectUri, username, password);
         }
      } catch (IllegalArgumentException var6) {
         if (debug) {
            var6.printStackTrace();
         }

         DeploymentManagerCreationException dmce = new DeploymentManagerCreationException(var6.getMessage());
         dmce.initCause(var6);
         throw dmce;
      } catch (DeploymentManagerCreationException var7) {
         if (debug) {
            var7.printStackTrace();
         }

         throw var7;
      }
   }

   public String getDisplayName() {
      return SPIDeployerLogger.getDisplayName();
   }

   public String getProductVersion() {
      return "1.0";
   }

   public boolean handlesURI(String uri) {
      try {
         this.validateUri(uri);
         return true;
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   public String[] getUris() {
      return uris;
   }

   public String createUri(String deployerUri, String host, String port) {
      return this.createUri("t3", deployerUri, host, port);
   }

   public String createUri(String protocol, String deployerUri, String host, String port) {
      return this.createUri(protocol, deployerUri, host, port, (String)null);
   }

   public String createUri(String protocol, String deployerUri, String host, String port, String path) {
      String u = null;

      try {
         u = protocol == null ? "t3://" : protocol + "://";
         u = deployerUri == null ? "deployer:WebLogic:" + u : deployerUri + ":" + u;
         if (debug) {
            Debug.say("createUri host:  " + host);
         }

         String resultHost = null;
         if (host == null) {
            resultHost = "localhost";
         } else {
            resultHost = InetAddressHelper.convertHostIfIPV6(host);
         }

         if (debug) {
            Debug.say("createUri result host:  " + resultHost);
         }

         u = u + resultHost;
         u = port == null ? u + ":" + "7001" : u + ":" + port;
         if (path != null) {
            u = u + path;
            if (debug) {
               Debug.say("createUri path:  " + u);
            }
         }

         this.validateUri(u);
      } catch (IllegalArgumentException var8) {
         if (debug) {
            Debug.say(var8.getMessage());
         }

         return null;
      }

      if (debug) {
         Debug.say("createUri result: " + u);
      }

      return u;
   }

   private void validateUri(String uri) throws IllegalArgumentException {
      if (uri.equals("deployer:WebLogic")) {
         this.deployerUri = "deployer:WebLogic";
      } else if (uri.equals("remote:deployer:WebLogic")) {
         this.deployerUri = "remote:deployer:WebLogic";
      } else if (uri.equals("authenticated:deployer:WebLogic")) {
         this.deployerUri = "authenticated:deployer:WebLogic";
      } else {
         if (uri.startsWith("deployer:WebLogic")) {
            this.deployerUri = "deployer:WebLogic";
         } else if (uri.startsWith("remote:deployer:WebLogic")) {
            this.deployerUri = "remote:deployer:WebLogic";
         } else {
            if (!uri.startsWith("authenticated:deployer:WebLogic")) {
               throw new IllegalArgumentException(SPIDeployerLogger.getInvalidURI(uri));
            }

            this.deployerUri = "authenticated:deployer:WebLogic";
         }

         uri = uri.substring(this.deployerUri.length());
         if (uri.charAt(0) == ':') {
            try {
               if (uri.indexOf("://") != -1) {
                  this.connectUri = new URI(uri.substring(1));
               } else {
                  this.connectUri = new URI("t3://" + uri.substring(1));
               }

               this.host = this.connectUri.getHost();
               this.port = this.connectUri.getPort();
            } catch (URISyntaxException var3) {
               throw new IllegalArgumentException(SPIDeployerLogger.getInvalidServerAuth(uri));
            }
         } else {
            throw new IllegalArgumentException(SPIDeployerLogger.getInvalidServerAuth(uri));
         }
      }
   }
}
