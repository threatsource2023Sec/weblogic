package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.model.RestJsonResponseBody;

public class AsyncOperationHandler {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(AsyncOperationHandler.class);
   private InvocationContext opIc;
   private InvocationContext jobIc;
   private URI jobUri;
   private RestInvoker.ResponseWrapper failedGetJobResponse;
   private JSONObject jobEntity;
   private AsyncOperationHelper helper;
   private long pollTime;
   private long maxTime;
   private int finalStatus;

   public static Response getResponse(InvocationContext opIc, RestJsonResponseBody opRb, AsyncOperationHelper helper) throws Exception {
      JSONArray jobId = opRb.getEntity().getJSONArray("return");
      Object job = PathUtils.findBean(opIc, jobId);
      InvocationContext jobIc = opIc.clone(job);
      AsyncOperationHandler handler = new AsyncOperationHandler(opIc, jobIc, helper);
      return handler.getResponse();
   }

   private AsyncOperationHandler(InvocationContext opIc, InvocationContext jobIc, AsyncOperationHelper helper) throws Exception {
      this.opIc = opIc;
      this.jobIc = jobIc;
      this.helper = helper;
      this.jobUri = PathUtils.getUri(this.jobIc);
   }

   private Response getResponse() throws Exception {
      OverallStatus status = this.overallWait();
      RestJsonResponseBody rb = new RestJsonResponseBody(this.jobIc.request(), QueryUtils.getLinksFilter(this.opIc.request(), this.opIc.query()));
      if (status == AsyncOperationHandler.OverallStatus.GET_JOB_FAILED) {
         String entity = this.failedGetJobResponse.hasEntity() ? this.failedGetJobResponse.getEntity().toString() : null;
         rb.addWarning(MessageUtils.beanFormatter(this.jobIc.request()).msgGETJobFailed(this.jobUri.toString(), this.failedGetJobResponse.getStatus(), entity));
         return Response.ok(rb).build();
      } else {
         this.helper.addMessages(this.opIc, this.jobIc, this.jobEntity, rb);
         if (this.jobIc.bean() != null) {
            QueryUtils.getPropertiesFilter(this.opIc.request(), this.opIc.query()).newScope().trim(this.jobEntity);
            rb.addResourceLink("job", this.jobUri);
            rb.setEntity(this.jobEntity);
         }

         if (status == AsyncOperationHandler.OverallStatus.SUCCEEDED) {
            URI newItemUri = this.helper.getNewItemUri(this.opIc, this.jobIc, this.jobEntity);
            return newItemUri != null ? Response.created(newItemUri).entity(rb).build() : Response.ok(rb).build();
         } else if (status == AsyncOperationHandler.OverallStatus.JOB_FAILED) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(rb).build());
         } else {
            return Response.status(Status.ACCEPTED).entity(rb).header("Location", this.jobUri).build();
         }
      }
   }

   private OverallStatus overallWait() throws Exception {
      this.initMaxTime();
      OverallStatus finalStatus = AsyncOperationHandler.OverallStatus.RUNNING;
      JobStatus jobStatus = this.waitForJob();
      if (jobStatus == AsyncOperationHandler.JobStatus.TIMED_OUT) {
         finalStatus = AsyncOperationHandler.OverallStatus.JOB_TIMED_OUT;
      } else if (jobStatus == AsyncOperationHandler.JobStatus.FAILED) {
         finalStatus = AsyncOperationHandler.OverallStatus.JOB_FAILED;
      } else if (jobStatus == AsyncOperationHandler.JobStatus.GET_JOB_FAILED) {
         finalStatus = AsyncOperationHandler.OverallStatus.GET_JOB_FAILED;
      } else {
         EntityReadyStatus entityReadyStatus = this.waitForEntityReady();
         if (entityReadyStatus == AsyncOperationHandler.EntityReadyStatus.TIMED_OUT) {
            finalStatus = AsyncOperationHandler.OverallStatus.ENTITY_READY_TIMED_OUT;
         } else {
            finalStatus = AsyncOperationHandler.OverallStatus.SUCCEEDED;
         }
      }

      return finalStatus;
   }

   private JobStatus waitForJob() throws Exception {
      if (this.jobIc.bean() == null) {
         return AsyncOperationHandler.JobStatus.SUCCEEDED;
      } else {
         JobStatus jobStatus = AsyncOperationHandler.JobStatus.RUNNING;

         do {
            RestInvoker.ResponseWrapper jobResponse = RestInvoker.get(this.jobIc, URLUtils.reHostToLocalServer(this.jobUri.toString()), RestInvoker.queryParams().put("links", "none"));
            if (jobResponse.getStatus() != Status.OK.getStatusCode()) {
               this.failedGetJobResponse = jobResponse;
               return AsyncOperationHandler.JobStatus.GET_JOB_FAILED;
            }

            this.jobEntity = jobResponse.getJsonEntity();
            String progress = this.jobEntity.getString("progress");
            if (!progress.equals("processing") && !progress.equals("pending")) {
               if (progress.equals("success")) {
                  jobStatus = AsyncOperationHandler.JobStatus.SUCCEEDED;
               } else {
                  jobStatus = AsyncOperationHandler.JobStatus.FAILED;
               }
            } else if (!this.timedOut() && !this.helper.pendingUserAction(this.opIc, this.jobIc, this.jobEntity)) {
               int pollingInterval = this.jobIc.intervalToPollMilliSeconds();
               if (pollingInterval == -1) {
                  pollingInterval = this.jobEntity.getInt("intervalToPoll");
               }

               Thread.sleep((long)pollingInterval);
            } else {
               jobStatus = AsyncOperationHandler.JobStatus.TIMED_OUT;
            }
         } while(jobStatus == AsyncOperationHandler.JobStatus.RUNNING);

         return jobStatus;
      }
   }

   private EntityReadyStatus waitForEntityReady() throws Exception {
      EntityReadyStatus entityReadyStatus = AsyncOperationHandler.EntityReadyStatus.RUNNING;

      while(entityReadyStatus == AsyncOperationHandler.EntityReadyStatus.RUNNING) {
         if (this.helper.entityReady(this.opIc, this.jobIc, this.jobEntity)) {
            entityReadyStatus = AsyncOperationHandler.EntityReadyStatus.SUCCEEDED;
         } else if (this.timedOut()) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("waitForEntityReady sync operation timed out");
            }

            entityReadyStatus = AsyncOperationHandler.EntityReadyStatus.TIMED_OUT;
         } else {
            Thread.sleep(2000L);
         }
      }

      return entityReadyStatus;
   }

   private boolean timedOut() throws Exception {
      if (this.opIc.async()) {
         return true;
      } else if (this.maxTime == 0L) {
         return false;
      } else if (System.currentTimeMillis() < this.maxTime) {
         return false;
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("timedOut timed out because the current time is greater than the max time, max wait = " + this.getMaxWait());
         }

         return true;
      }
   }

   private void initMaxTime() throws Exception {
      int maxWaitMilliSeconds = this.getMaxWait();
      this.maxTime = maxWaitMilliSeconds == 0 ? 0L : System.currentTimeMillis() + (long)maxWaitMilliSeconds;
   }

   private int getMaxWait() throws Exception {
      int maxWait = this.jobIc.syncMaxWaitMilliSeconds();
      if (maxWait == -1) {
         maxWait = this.helper.getDefaultSyncMaxWaitMilliSeconds(this.opIc);
      }

      return maxWait;
   }

   private static enum OverallStatus {
      RUNNING,
      JOB_FAILED,
      JOB_TIMED_OUT,
      GET_JOB_FAILED,
      ENTITY_READY_TIMED_OUT,
      SUCCEEDED;
   }

   private static enum EntityReadyStatus {
      RUNNING,
      TIMED_OUT,
      SUCCEEDED;
   }

   private static enum JobStatus {
      RUNNING,
      TIMED_OUT,
      FAILED,
      SUCCEEDED,
      GET_JOB_FAILED;
   }
}
