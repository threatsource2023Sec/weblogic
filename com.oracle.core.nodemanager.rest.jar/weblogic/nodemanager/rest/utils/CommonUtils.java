package weblogic.nodemanager.rest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.admin.rest.composite.Property;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.common.NMProtocol;
import weblogic.nodemanager.rest.RestInputOutput;
import weblogic.nodemanager.server.InternalNMCommandHandler;

public class CommonUtils {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   public static final String GENERIC_ERROR_MESSAGE;
   public static final int ERROR_MSG_SUBSTRING_INDEX;
   public static final int OK_MSG_SUBSTRING_INDEX;
   public static final String VERSION = "version";
   public static final String DOMAIN_PATH_PARAM = "domain-name";
   public static final String AUTH_DOMAIN_PATH_PARAM = "auth-domain-name";
   public static final String SERVER_PATH_PARAM = "server-name";
   public static final String JOB_PARAM = "job";
   public static final String JOBS_CONTEXT = "jobs";
   public static final String BOUNDRY_PARAM = "boundary";
   public static final String PREFER_PARAM = "Prefer";
   public static final String PREFER_ASYNC = "respond-asyc";
   public static final String PREFER_WAIT = "wait=";
   public static final long PREFER_WAIT_TIME_DEFAULT = 300L;
   public static final String RESPONSE_ITEMS_NAME = "items.name";
   public static final String DATE_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
   public static final int SERVER_FILTER_PRIORITY = 6000;
   public static final String STD_OUT_FILE_EXTENSTION = ".out";
   public static final String LOG_FILE_EXTENSTION = ".log";
   private static final String START_OPERATION = "start";
   private static final String KILL_OPERATION = "kill";
   private static final String RESTART_OPERATION = "restart";

   public static void addDomainCommands(RestInputOutput rio, String domainName) {
      rio.setNMProtocolVersion(NMProtocol.getLatestVersion());
      rio.addCommand(Command.DOMAIN + " " + domainName);
   }

   public static void addDomainServerCommands(RestInputOutput rio, String domainName, String serverName) {
      addDomainCommands(rio, domainName);
      rio.addCommand(Command.SERVER + " " + serverName);
   }

   public static WebApplicationException createWebException(Response.Status httpCode, String message) {
      return new WebApplicationException(new Throwable(), Response.status(httpCode).entity(message).build());
   }

   public static ResourceResponse getResponseOnError(String res) {
      if (res == null) {
         return new ResourceResponse(Status.INTERNAL_SERVER_ERROR, nmRestText.msgUnknownErr(), false);
      } else {
         return res.startsWith("-ERR ") ? new ResourceResponse(Status.INTERNAL_SERVER_ERROR, res.substring(ERROR_MSG_SUBSTRING_INDEX), false) : null;
      }
   }

   public static ResourceResponse getSuccessResponse(String messsage) {
      return new ResourceResponse(Status.OK, messsage, true);
   }

   public static ResourceResponse getSuccessResponse(Response.Status httpSuccessCode, String messsage) {
      return new ResourceResponse(httpSuccessCode, messsage, true);
   }

   public static ResourceResponse getFinalResponse(String message) {
      ResourceResponse hRes = null;
      hRes = getResponseOnError(message);
      return hRes != null ? hRes : getSuccessResponse(message.substring(OK_MSG_SUBSTRING_INDEX));
   }

   public static ResourceResponse runCommands(RestInputOutput rio, InternalNMCommandHandler handler) {
      handler.processRequests();
      ResourceResponse res = null;
      String finalMessage = rio.getLatestResponse();
      res = getResponseOnError(finalMessage);
      if (res == null) {
         res = getFinalResponse(finalMessage);
      }

      return res;
   }

   public static Properties toProperties(List listProp) {
      Properties props = new Properties();
      Iterator var2 = listProp.iterator();

      while(var2.hasNext()) {
         Property prop = (Property)var2.next();
         props.setProperty(prop.getName(), prop.getValue());
      }

      return props;
   }

   public static String getDateTimeAsString(long timeinMillis) {
      return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")).format(new Date(timeinMillis));
   }

   static {
      GENERIC_ERROR_MESSAGE = nmRestText.msgNMReadConfigErr();
      ERROR_MSG_SUBSTRING_INDEX = "-ERR ".length();
      OK_MSG_SUBSTRING_INDEX = "+OK ".length();
   }

   public static class ServerJobProgress {
      public static final String SUCCEEDED = "succeeded";
      public static final String FAILED = "failed";
      public static final String PROCESSING = "processing";
   }

   public static class ServerOperationType {
      public static final String START = "start";
      public static final String KILL = "kill";
   }

   public static enum ComponentType {
      SERVER,
      NODEMANAGER;
   }

   public static enum OperationType {
      START {
         public String toString() {
            return "start";
         }
      },
      KILL {
         public String toString() {
            return "kill";
         }
      },
      RESTART {
         public String toString() {
            return "restart";
         }
      };

      private OperationType() {
      }

      // $FF: synthetic method
      OperationType(Object x2) {
         this();
      }
   }
}
