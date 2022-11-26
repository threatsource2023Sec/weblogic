package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import weblogic.management.scripting.WLSTUtils;

/** @deprecated */
@Deprecated
final class RESTClient implements AutoCloseable {
   static final String GET_LCM_URI = "management/lifecycle/latest";
   private static final String GET_PARTITION_PROVISIONING_URI = "management/lifecycle/latest/provisionerCreateForm";
   private static final String POST_PARTITION_PROVISIONING_URI = "management/lifecycle/latest/provisioner";
   private static final String POST_PARTITION_DEPROVISIONING_URI = "management/lifecycle/latest/deprovisioner";
   private final WLSTUtils utils;
   private final JsonReaderFactory jsonReaderFactory;
   private final JsonWriterFactory jsonWriterFactory;
   final String fRESTBaseURI;
   private final String fUsername;
   private transient String fPassword;

   RESTClient(String restBaseURI, String username, String password) {
      this((WLSTUtils)null, Json.createReaderFactory((Map)null), Json.createWriterFactory((Map)null), restBaseURI, username, password);
   }

   RESTClient(WLSTUtils utils, String restBaseURI, String username, String password) {
      this(utils, Json.createReaderFactory((Map)null), Json.createWriterFactory((Map)null), restBaseURI, username, password);
   }

   RESTClient(WLSTUtils utils, JsonReaderFactory jsonReaderFactory, JsonWriterFactory jsonWriterFactory, String restBaseURI, String username, String password) {
      Objects.requireNonNull(restBaseURI, "restBaseURI == null");
      Objects.requireNonNull(username, "username == null");
      this.utils = utils;
      if (jsonReaderFactory == null) {
         this.jsonReaderFactory = Json.createReaderFactory((Map)null);
      } else {
         this.jsonReaderFactory = jsonReaderFactory;
      }

      if (jsonWriterFactory == null) {
         this.jsonWriterFactory = Json.createWriterFactory((Map)null);
      } else {
         this.jsonWriterFactory = jsonWriterFactory;
      }

      this.fRESTBaseURI = getURI(restBaseURI);
      this.fUsername = username;
      this.fPassword = getEncodedText(password);
   }

   public final void close() {
   }

   final String getBaseURI() {
      return this.fRESTBaseURI;
   }

   final String getUsername() {
      return this.fUsername;
   }

   final String getPassword() {
      return getDecodedText(this.fPassword);
   }

   static final String getURI(String uri) {
      Objects.requireNonNull(uri);
      int i = uri.indexOf("//");
      if (i > 0 && uri.charAt(i - 1) == ':') {
         return "http:" + uri.substring(i);
      } else {
         return i == 0 ? "http:" + uri : "http://" + uri;
      }
   }

   public final Response getLCMRoot() {
      return this.invokeRESTResource("GET", "management/lifecycle/latest");
   }

   public final Response getPartitionProvisioningConfig(String provisioningComponentName) {
      return this.getPartitionProvisioningConfig(provisioningComponentName, (Properties)null);
   }

   public final Response getPartitionProvisioningConfig(String provisioningComponentName, Properties provisioningOperationProperties) {
      return this.invokeRESTResource("GET", "management/lifecycle/latest/provisionerCreateForm");
   }

   public final Response provisionPartition(String partitionName, String componentType, JsonStructure jsonData) {
      return this.invokeRESTResource("POST", "management/lifecycle/latest/provisioner", jsonData);
   }

   public final Response deprovisionPartition(String partitionName, String componentType, JsonStructure jsonData) {
      return this.invokeRESTResource("POST", "management/lifecycle/latest/deprovisioner", jsonData);
   }

   /** @deprecated */
   @Deprecated
   public final String getResponseBody(Response response) {
      Objects.requireNonNull(response);
      Response.Status status = Status.fromStatusCode(response.getStatus());
      if (Status.OK != status && Status.CREATED != status) {
         this.debug("Error while trying to get Response Body.");
         return null;
      } else {
         String responseBody = (String)response.readEntity(String.class);
         this.debug("Got Response Body:\n" + responseBody);
         return responseBody;
      }
   }

   private static final String getEncodedText(String plainText) {
      String returnValue;
      if (plainText == null) {
         returnValue = null;
      } else {
         returnValue = Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
      }

      return returnValue;
   }

   private static final String getDecodedText(String encodedText) {
      String returnValue;
      if (encodedText == null) {
         returnValue = null;
      } else {
         returnValue = new String(Base64.getDecoder().decode(encodedText), StandardCharsets.UTF_8);
      }

      return returnValue;
   }

   private final Response invokeRESTResource(String httpMethod, String restResourceURI) {
      return this.invokeRESTResource(httpMethod, restResourceURI, (JsonStructure)null);
   }

   private final Response invokeRESTResource(String httpMethod, String restResourceURI, JsonStructure jsonData) {
      Objects.requireNonNull(httpMethod);
      Objects.requireNonNull(restResourceURI);
      Response response = null;
      javax.ws.rs.client.Client client = ClientBuilder.newClient();

      assert client != null;

      try {
         HttpAuthenticationFeature httpAuthFeature = HttpAuthenticationFeature.basicBuilder().build();
         client.register(httpAuthFeature);
         this.debug("\tCreating Target");
         WebTarget target = client.target(this.getBaseURI());
         this.debug("\tNavigating to Resource Path");
         target = target.path(restResourceURI);
         this.debug("\tAdding Params");
         target = addQueryParams(target);
         this.debug("\t[" + httpMethod + "] " + target.getUri());
         this.debug("\tCreating Builder");
         Invocation.Builder invocationBuilder = target.request(new String[]{"application/json"});
         this.debug("\tAdding Properties");
         invocationBuilder = this.addProperties(invocationBuilder);
         this.debug("\tAdding Headers");
         invocationBuilder = addHeaders(invocationBuilder);
         response = this.invokeTarget(httpMethod, target, invocationBuilder, jsonData);

         assert response != null : "invokeTarget() == null";

         int statusCode = response.getStatus();
         this.debug("HTTP Response Status: " + statusCode);
         Response.Status responseStatus = Status.fromStatusCode(statusCode);

         assert responseStatus != null;

         if (Status.OK == responseStatus || Status.CREATED == responseStatus) {
            this.debug("Request was processed successfully.");
         }
      } finally {
         if (response != null) {
            try {
               response.close();
            } catch (ProcessingException var19) {
            }
         }

         try {
            client.close();
         } catch (IllegalStateException var18) {
         }

      }

      return response;
   }

   private static final WebTarget addQueryParams(WebTarget target) {
      Objects.requireNonNull(target);
      return target;
   }

   private Invocation.Builder addProperties(Invocation.Builder requestBuilder) {
      Objects.requireNonNull(requestBuilder);
      requestBuilder = requestBuilder.property("jersey.config.client.http.auth.basic.username", this.fUsername);
      requestBuilder = requestBuilder.property("jersey.config.client.http.auth.basic.password", getDecodedText(this.fPassword));
      return requestBuilder;
   }

   private static final Invocation.Builder addHeaders(Invocation.Builder requestBuilder) {
      Objects.requireNonNull(requestBuilder);
      requestBuilder = requestBuilder.header("X-Requested-By", "WLST-CLI");
      requestBuilder = requestBuilder.header("Accept", "application/json");
      requestBuilder = requestBuilder.header("Content-Type", "application/json");
      return requestBuilder;
   }

   private final Response invokeTarget(String httpMethod, WebTarget target, Invocation.Builder requestBuilder, JsonStructure jsonStructure) {
      Objects.requireNonNull(httpMethod);
      Objects.requireNonNull(requestBuilder);
      this.debug("Invoking Target");
      Response response;
      switch (httpMethod) {
         case "GET":
            response = requestBuilder.get();
            break;
         case "POST":
            String jsonData;
            if (jsonStructure == null) {
               jsonData = null;
            } else {
               StringWriter writer = new StringWriter();
               JsonWriter jsonWriter = this.jsonWriterFactory.createWriter(writer);
               Throwable var11 = null;

               try {
                  assert jsonWriter != null;

                  jsonWriter.write(jsonStructure);
               } catch (Throwable var20) {
                  var11 = var20;
                  throw var20;
               } finally {
                  if (jsonWriter != null) {
                     if (var11 != null) {
                        try {
                           jsonWriter.close();
                        } catch (Throwable var19) {
                           var11.addSuppressed(var19);
                        }
                     } else {
                        jsonWriter.close();
                     }
                  }

               }

               jsonData = writer.toString();
            }

            if (jsonData == null) {
               throw new IllegalArgumentException("JSON data to POST is <null>.");
            }

            response = requestBuilder.post(Entity.json(jsonData));
            break;
         case "DELETE":
            response = requestBuilder.delete();
            break;
         default:
            response = null;
            throw new IllegalArgumentException("Unknown http method: " + httpMethod);
      }

      this.debug("[Done] Invoking Target");
      return response;
   }

   private final void debug(String message) {
      if (this.utils != null) {
         this.utils.printDebug("[DEBUG]::[RESTClient]::" + message);
      }

   }
}
