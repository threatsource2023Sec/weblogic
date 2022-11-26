package com.oracle.weblogic.lifecycle.provisioning.wlst;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public final class Client implements AutoCloseable {
   private final javax.ws.rs.client.Client client;
   private final WebTarget baseTarget;
   private final Logger logger;

   public Client(URI hostAndPortInformation, String user, String password) throws URISyntaxException {
      String cn = this.getClass().getName();
      String mn = "<init>";
      this.logger = Logger.getLogger(cn);

      assert this.logger != null;

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "<init>", new Object[]{hostAndPortInformation, user, password});
      }

      Objects.requireNonNull(hostAndPortInformation, "hostAndPortInformation == null");
      int port = hostAndPortInformation.getPort();
      if (port < 0) {
         port = 80;
      }

      URI uri = new URI("http", hostAndPortInformation.getUserInfo(), hostAndPortInformation.getHost(), port, "/management/lifecycle/latest", (String)null, (String)null);
      Feature httpAuthenticationFeature = createAuthenticationFeature(uri, user, password);

      assert httpAuthenticationFeature != null;

      javax.ws.rs.client.Client client = ClientBuilder.newClient();

      assert client != null;

      client.register(httpAuthenticationFeature);
      this.client = client;
      this.baseTarget = client.target(uri);
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "<init>");
      }

   }

   public Client(String host, int port, String user, String password) throws URISyntaxException {
      this(new URI("http", (String)null, host, port < 0 ? 80 : port, "/management/lifecycle/latest", (String)null, (String)null), user, password);
   }

   public final URI connect() throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "connect";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "connect");
      }

      this.options(this.baseTarget);
      URI returnValue = this.baseTarget.getUri();
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "connect", returnValue);
      }

      return returnValue;
   }

   public final void close() {
      this.close0(true);
   }

   final void close0(boolean log) {
      if (log && this.logger.isLoggable(Level.FINER)) {
         this.logger.logp(Level.FINER, this.getClass().getName(), "close", "ENTRY");
      }

      this.client.close();
      if (log && this.logger.isLoggable(Level.FINER)) {
         this.logger.logp(Level.FINER, this.getClass().getName(), "close", "RETURN");
      }

   }

   public final JsonObject getConfigurableAttributes() throws ProvisioningException {
      return this.getConfigurableAttributes((String)null, (Map)null);
   }

   public final JsonObject getConfigurableAttributes(Map provisioningOperationProperties) throws ProvisioningException {
      return this.getConfigurableAttributes((String)null, provisioningOperationProperties);
   }

   public final JsonObject getConfigurableAttributes(String provisioningComponentName, Map provisioningOperationProperties) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "getConfigurableAttributes";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "getConfigurableAttributes", new Object[]{provisioningComponentName, provisioningOperationProperties});
      }

      JsonObject returnValue = this.get("provisionerCreateForm");
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "getConfigurableAttributes", returnValue);
      }

      return returnValue;
   }

   public final void provisionPartition(JsonObject json) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "provisionPartition";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "provisionPartition", json);
      }

      this.post("provisioner", json);
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "provisionPartition");
      }

   }

   public final void deprovisionPartition(JsonObject json) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "deprovisionPartition";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "deprovisionPartition", json);
      }

      this.post("deprovisioner", json);
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "deprovisionPartition");
      }

   }

   private final SyncInvoker getSyncInvoker(WebTarget target) {
      if (target == null) {
         target = this.baseTarget;
      }

      assert target != null;

      Invocation.Builder builder = target.request();
      builder = builder.accept(new MediaType[]{MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())});
      builder = builder.header("X-Requested-By", "WLST-CLI");
      return builder;
   }

   private final SyncInvoker getSyncInvoker(String path) {
      SyncInvoker returnValue;
      if (path == null) {
         returnValue = this.getSyncInvoker(this.baseTarget);
      } else {
         returnValue = this.getSyncInvoker(this.baseTarget.path(path));
      }

      return returnValue;
   }

   private final String options(WebTarget target) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "options";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "options", target);
      }

      String returnValue = null;

      try {
         SyncInvoker invoker = this.getSyncInvoker(target);

         assert invoker != null;

         returnValue = (String)invoker.options(String.class);
      } catch (WebApplicationException | ProcessingException var6) {
         throw (ProvisioningException)toProvisioningException(var6).fillInStackTrace();
      }

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "options", returnValue);
      }

      return returnValue;
   }

   private final JsonObject get(String path) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "get";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "get", path);
      }

      JsonObject returnValue = null;

      try {
         SyncInvoker invoker = this.getSyncInvoker(path);

         assert invoker != null;

         returnValue = (JsonObject)invoker.get(JsonObject.class);
      } catch (WebApplicationException | ProcessingException var6) {
         throw (ProvisioningException)toProvisioningException(var6).fillInStackTrace();
      }

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "get", returnValue);
      }

      return returnValue;
   }

   private final void post(String path, JsonObject json) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "post";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "post", new Object[]{path, json});
      }

      Objects.requireNonNull(json, "json == null");

      try {
         SyncInvoker invoker = this.getSyncInvoker(path);

         assert invoker != null;

         invoker.post(Entity.json(json), String.class);
      } catch (WebApplicationException | ProcessingException var6) {
         throw (ProvisioningException)toProvisioningException(var6).fillInStackTrace();
      }

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "post");
      }

   }

   static final Feature createAuthenticationFeature(URI userInfoInformation, String username, String password) {
      String user = null;
      String pw = null;
      if (userInfoInformation != null) {
         String userInfo = userInfoInformation.getUserInfo();
         if (userInfo != null) {
            int userInfoLength = userInfo.length();
            if (userInfoLength > 0) {
               int colonIndex = userInfo.indexOf(58);
               if (colonIndex < 0) {
                  user = userInfo;
               } else if (userInfoLength > 1) {
                  if (colonIndex == 0) {
                     pw = userInfo.substring(1);
                  } else {
                     user = userInfo.substring(0, colonIndex);
                     if (colonIndex + 1 != userInfo.length()) {
                        pw = userInfo.substring(colonIndex + 1);
                     }
                  }
               }
            }
         }
      }

      if (username != null) {
         user = username;
      }

      if (password != null) {
         pw = password;
      }

      Feature httpAuthenticationFeature = HttpAuthenticationFeature.basic(user, pw);

      assert httpAuthenticationFeature != null;

      return httpAuthenticationFeature;
   }

   private static final ProvisioningException toProvisioningException(Throwable e) {
      ProvisioningException provisioningException;
      if (e == null) {
         provisioningException = new ProvisioningException();
      } else if (e instanceof ProvisioningException) {
         provisioningException = (ProvisioningException)e;
      } else if (e instanceof ResponseProcessingException) {
         provisioningException = toProvisioningException(e, ((ResponseProcessingException)e).getResponse());
      } else if (e instanceof WebApplicationException) {
         provisioningException = toProvisioningException(e, ((WebApplicationException)e).getResponse());
      } else {
         provisioningException = new ProvisioningException(e.getMessage(), e);
      }

      return provisioningException;
   }

   private static final ProvisioningException toProvisioningException(Throwable cause, Response response) {
      ProvisioningException returnValue = null;
      String message = null;
      if (cause != null) {
         message = cause.getMessage();
      }

      if (response != null && MediaType.APPLICATION_JSON_TYPE.isCompatible(response.getMediaType())) {
         try {
            if (response.bufferEntity()) {
               assert response.hasEntity() : "!response.hasEntity()";

               JsonObject payload = (JsonObject)response.readEntity(JsonObject.class);
               if (payload != null) {
                  JsonValue detailValue = (JsonValue)payload.get("detail");
                  if (detailValue != null && detailValue instanceof JsonString) {
                     message = ((JsonString)detailValue).getString();
                  }
               }
            }

            assert returnValue == null : "returnValue != null: " + returnValue;

            returnValue = new ProvisioningException(message, cause);
         } catch (ProcessingException | IllegalStateException var14) {
            assert returnValue == null : "returnValue != null: " + returnValue;

            returnValue = new ProvisioningException(message, cause);
            returnValue.addSuppressed(var14);
         } finally {
            try {
               response.close();
            } catch (ProcessingException var13) {
               if (returnValue == null) {
                  returnValue = new ProvisioningException(message, cause);
               }

               returnValue.addSuppressed(var13);
            }

         }
      } else {
         returnValue = new ProvisioningException(message, cause);
      }

      assert returnValue != null : "returnValue == null";

      return returnValue;
   }
}
