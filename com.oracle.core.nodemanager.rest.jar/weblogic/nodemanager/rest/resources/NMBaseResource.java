package weblogic.nodemanager.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONException;
import org.glassfish.admin.rest.composite.CompositeResource;
import org.glassfish.admin.rest.model.ErrorResponseBody;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import org.glassfish.admin.rest.utils.JsonFilter;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.rest.async.AsyncJob;
import weblogic.nodemanager.rest.async.TaskExecutionException;
import weblogic.nodemanager.rest.async.TaskNotCompleteException;
import weblogic.nodemanager.rest.model.ServerJob;
import weblogic.nodemanager.rest.utils.CommonUtils;
import weblogic.nodemanager.rest.utils.ResourceResponse;
import weblogic.nodemanager.server.NMServer;
import weblogic.nodemanager.util.DomainInfo;
import weblogic.nodemanager.util.ServerInfo;

public class NMBaseResource extends CompositeResource {
   protected static final String INCLUDE_FIELDS = "fields";
   protected static final String EXCLUDE_FIELDS = "excludeFields";
   protected static final String INCLUDE_LINKS = "links";
   protected static final String EXCLUDE_LINKS = "excludeLinks";
   protected static final String CONTENT_DISPOSITION = "Content-Disposition";
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   protected static final String SERVER_NOT_CONFIGURED;
   private static final String EQUAL = "=";
   private static final String COMMA = ",";

   public Response getJobResponse(AsyncJob job) throws Exception {
      URI jobUri = getJobUri(this.getParentUri().toString(), job.getId());
      TaskResponse taskResponse = this.getTaskResponse(job);
      String status = taskResponse.getStatus();
      if ("failed".equals(status)) {
         ResourceResponse res = (ResourceResponse)job.getOutput();
         String errorMessage = (String)res.getResponse();
         ResponseBody erb = (new ResponseBody(true)).addFailure(errorMessage);
         erb.addResourceLink("job", jobUri);
         return this.badRequest(erb);
      } else {
         RestModelResponseBody rb = this.restModelResponseBody(ServerJob.class);
         ServerJob serverJob = this.getJobModel(job, taskResponse);
         rb.addResourceLink("job", jobUri);
         rb.setEntity(serverJob);
         return "succeeded".equals(serverJob.getProgress()) ? this.acted(rb) : this.accepted(rb, jobUri);
      }
   }

   protected ServerJob getJobModel(AsyncJob asyncJob) throws TaskNotCompleteException, TaskExecutionException, JSONException {
      TaskResponse taskResponse = this.getTaskResponse(asyncJob);
      return this.getJobModel(asyncJob, taskResponse);
   }

   private ServerJob getJobModel(AsyncJob asyncJob, TaskResponse taskResponse) throws JSONException {
      ServerJob serverJob = new ServerJob();
      String status = taskResponse.getStatus();
      if ("failed".equals(status)) {
         String errorMessage = taskResponse.getErrorMessage();
         int code = Status.BAD_REQUEST.getStatusCode();
         ResponseBody erb = (new ResponseBody(false)).addFailure(errorMessage);
         ErrorResponseBody errorResponse = new ErrorResponseBody(code, erb);
         serverJob.setError(errorResponse.toJson());
         serverJob.setProgress("failed");
      } else if ("succeeded".equals(status)) {
         serverJob.setProgress("succeeded");
      } else {
         serverJob.setProgress("processing");
      }

      serverJob.setName(asyncJob.getId());
      serverJob.setDescription(asyncJob.getDescription());
      serverJob.setOperation(asyncJob.getOperationType().toString());
      serverJob.setIntervalToPoll(asyncJob.getIntervalToPoll());
      serverJob.setStartTime(CommonUtils.getDateTimeAsString(asyncJob.getStartTime()));
      if ("processing".equals(serverJob.getProgress())) {
         serverJob.setCompleted(false);
         serverJob.setEndTime((String)null);
      } else {
         serverJob.setCompleted(true);
         serverJob.setEndTime(CommonUtils.getDateTimeAsString(asyncJob.getEndTime()));
      }

      serverJob.allFieldsSet();
      return serverJob;
   }

   protected String getContentDisposition(String fileName) {
      return "attachment; filename=" + fileName;
   }

   private TaskResponse getTaskResponse(AsyncJob asyncJob) throws TaskNotCompleteException {
      if (asyncJob.isComplete()) {
         try {
            ResourceResponse res = (ResourceResponse)asyncJob.getOutput();
            return res.isSuccess() ? new TaskResponse("succeeded", (String)null) : new TaskResponse("failed", (String)res.getResponse());
         } catch (TaskExecutionException var3) {
            return new TaskResponse("failed", var3.getMessage());
         }
      } else {
         return new TaskResponse("processing", (String)null);
      }
   }

   protected long findMaxWaitTime(String prefer) throws Exception {
      if (prefer == null) {
         return 300L;
      } else {
         String[] values = prefer.split(",");
         int waitTime = 0;
         String[] var4 = values;
         int var5 = values.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String value = var4[var6];
            value = value.trim();
            if (value.equals("respond-asyc")) {
               waitTime = -1;
            } else if (value.startsWith("wait=")) {
               String[] wa = value.split("=");
               if (wa.length > 1) {
                  String timeStr = wa[1].trim();
                  return Long.parseLong(timeStr);
               }
            }
         }

         return (long)waitTime;
      }
   }

   protected static URI getJobUri(String parentUri, String jobId) throws URISyntaxException {
      URI jobUri = (new URI(parentUri + "/")).resolve("jobs/").resolve(jobId);
      return jobUri;
   }

   protected RestJsonResponseBody restJsonResponseBody() {
      return this.restJsonResponseBody((JsonFilter)null);
   }

   protected RestJsonResponseBody restJsonResponseBody(JsonFilter linksFilter) {
      return new RestJsonResponseBody(this.getRequest(), linksFilter);
   }

   protected boolean isServerConfigured(String domainName, String serverName) {
      try {
         DomainInfo domainInfo = NMServer.getInstance().getDomainInfo(domainName);
         return domainInfo.isServerConfigured(serverName, "WebLogic");
      } catch (Exception var4) {
         if (NMServer.nmLog.isLoggable(Level.FINE)) {
            NMServer.nmLog.log(Level.FINE, "Error getting details for server " + serverName, var4);
         }

         throw this.badRequest(CommonUtils.GENERIC_ERROR_MESSAGE);
      }
   }

   protected ServerInfo getServerInfo(String domainName, String serverName) {
      try {
         DomainInfo domainInfo = NMServer.getInstance().getDomainInfo(domainName);
         return domainInfo.getServerInfo(serverName, "WebLogic");
      } catch (Exception var4) {
         if (NMServer.nmLog.isLoggable(Level.FINE)) {
            NMServer.nmLog.log(Level.FINE, "Error getting details for server " + serverName, var4);
         }

         throw this.badRequest(CommonUtils.GENERIC_ERROR_MESSAGE);
      }
   }

   protected void verifyOptions() throws Exception {
      if (!this.supportsOptions()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyGet() throws Exception {
      if (!this.supportsGet()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyPut() throws Exception {
      if (!this.supportsPut()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyPost() throws Exception {
      if (!this.supportsPost()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyDelete() throws Exception {
      if (!this.supportsDelete()) {
         this.throwMethodNotAllowed();
      }

   }

   protected boolean supportsOptions() throws Exception {
      return true;
   }

   protected boolean supportsGet() throws Exception {
      return true;
   }

   protected boolean supportsPut() throws Exception {
      return false;
   }

   protected boolean supportsPost() throws Exception {
      return false;
   }

   protected boolean supportsDelete() throws Exception {
      return false;
   }

   protected boolean supportsAsyncOptions() throws Exception {
      return false;
   }

   protected boolean supportsAsyncGet() throws Exception {
      return false;
   }

   protected boolean supportsAsyncPut() throws Exception {
      return false;
   }

   protected boolean supportsAsyncPost() throws Exception {
      return false;
   }

   protected boolean supportsAsyncDelete() throws Exception {
      return false;
   }

   protected void throwMethodNotAllowed() throws Exception {
      throw this.methodNotAllowed(this.allowedMethods());
   }

   protected Response options() throws Exception {
      this.verifyOptions();
      return this._options();
   }

   protected Response get() throws Exception {
      this.verifyGet();
      return this._get();
   }

   protected Response put() throws Exception {
      this.verifyPut();
      return this._put();
   }

   protected Response post() throws Exception {
      this.verifyPost();
      return this._post();
   }

   protected Response delete() throws Exception {
      this.verifyDelete();
      return this._delete();
   }

   protected Response _options() throws Exception {
      return Response.ok().header("Allow", this.allowedMethods()).build();
   }

   protected Response _get() throws Exception {
      throw new AssertionError("Generic GET not supported");
   }

   protected Response _put() throws Exception {
      throw new AssertionError("Generic GET not supported");
   }

   protected Response _post() throws Exception {
      throw new AssertionError("Generic GET not supported");
   }

   protected Response _delete() throws Exception {
      throw new AssertionError("Generic DELETE not supported");
   }

   private String allowedMethods() throws Exception {
      List methods = new ArrayList();
      if (this.supportsOptions()) {
         methods.add("OPTIONS");
      }

      if (this.supportsGet()) {
         methods.add("GET");
      }

      if (this.supportsPut()) {
         methods.add("PUT");
      }

      if (this.supportsPost()) {
         methods.add("POST");
      }

      if (this.supportsDelete()) {
         methods.add("DELETE");
      }

      StringBuilder sb = new StringBuilder();
      boolean first = true;

      for(Iterator var4 = methods.iterator(); var4.hasNext(); first = false) {
         String method = (String)var4.next();
         if (!first) {
            sb.append(",");
         }

         sb.append(method);
      }

      return sb.toString();
   }

   static {
      SERVER_NOT_CONFIGURED = nmRestText.msgServerNotConfigured();
   }

   private static class TaskResponse {
      private String status;
      private String errorMessage;

      public String getStatus() {
         return this.status;
      }

      public String getErrorMessage() {
         return this.errorMessage;
      }

      public TaskResponse(String status, String errorMessage) {
         this.status = status;
         this.errorMessage = errorMessage;
      }
   }
}
