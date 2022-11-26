package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import java.util.Date;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ErrorResponseBody;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.model.RestJsonResponseBody;

public class AsyncJobUtils {
   public static void standardizeResponse(InvocationContext ic, AsyncJobHelper helper, JSONObject entity, RestJsonResponseBody rb) throws Exception {
      standardizeResponse(ic, helper, entity);
      if (!entity.getBoolean("completed")) {
         URI abortUri = helper.getAbortUri(ic, entity);
         if (abortUri != null) {
            rb.addResourceLink("abort", abortUri);
         }
      }

   }

   private static void standardizeResponse(InvocationContext ic, AsyncJobHelper helper, JSONObject entity) throws Exception {
      helper.preStandardize(ic, entity);
      String progress = helper.getProgress(ic, entity);
      boolean completed = "success".equals(progress) || "failed".equals(progress);
      entity.put("progress", progress);
      entity.put("completed", completed);
      entity.put("intervalToPoll", helper.getIntervalToPoll(ic, entity));
      addDate(ic, entity, "startTime");
      if (completed) {
         addDate(ic, entity, "endTime");
      } else {
         entity.remove(longName("endTime"));
      }

      if ("failed".equals(progress)) {
         ResponseBody rb = new ResponseBody(ic.request(), QueryUtils.getLinksFilter(ic.request(), ic.query()));
         helper.addFailureMessages(rb, ic, entity);
         ErrorResponseBody erb = new ErrorResponseBody(Status.BAD_REQUEST.getStatusCode(), rb);
         entity.put("error", erb.toJson());
      }

      helper.postStandardize(ic, entity);
   }

   private static void addDate(InvocationContext ic, JSONObject entity, String dateName) throws Exception {
      long time = entity.getLong(longName(dateName));
      Date date = new Date(time);
      Object marshalledDate = DefaultMarshallers.instance().getMarshaller(ic.request(), Date.class).marshal(ic, (ResponseBody)null, (String)null, date);
      entity.put(dateName, marshalledDate);
   }

   private static String longName(String dateName) throws Exception {
      return dateName + "AsLong";
   }
}
