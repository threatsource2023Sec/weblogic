package weblogic.nodemanager.rest.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.ws.rs.core.StreamingOutput;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.rest.RestInputOutput;
import weblogic.nodemanager.rest.async.AsyncController;
import weblogic.nodemanager.rest.async.AsyncJob;
import weblogic.nodemanager.rest.async.Task;
import weblogic.nodemanager.server.InternalNMCommandHandler;
import weblogic.nodemanager.server.NMServer;

public class NMServerUtils {
   private static final String SPACE = " ";
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();

   public static ResourceResponse getServerStatus(String domainName, String serverName, String serverType) {
      RestInputOutput rio = new RestInputOutput();
      InternalNMCommandHandler handler = new InternalNMCommandHandler(NMServer.getInstance(), rio);
      CommonUtils.addDomainServerCommands(rio, domainName, serverName);
      rio.addCommand(Command.STAT.toString());
      return CommonUtils.runCommands(rio, handler);
   }

   public static StreamingOutput getServerStdOut(String domainName, String serverName, String serverType) {
      RestInputOutput rio = new RestInputOutput();
      InternalNMCommandHandler handler = new InternalNMCommandHandler(NMServer.getInstance(), rio);
      CommonUtils.addDomainServerCommands(rio, domainName, serverName);
      rio.addCommand(Command.GETLOG.toString());
      return new StreamingOutputImpl(handler);
   }

   public static AsyncJob startServerAsync(String domainName, String serverName) {
      return startServer(domainName, serverName, -1L);
   }

   public static AsyncJob startServer(String domainName, final String serverName, long waitTime) {
      Task task = new Task(nmRestText.msgStartingServer(serverName), domainName, CommonUtils.ComponentType.SERVER, serverName, CommonUtils.OperationType.START) {
         public ResourceResponse execute() {
            return NMServerUtils.startServer(this.domainName, serverName, (Properties)null);
         }
      };
      AsyncJob async = null;
      if (waitTime > 0L) {
         async = AsyncController.executeTask(task, waitTime);
      } else {
         async = AsyncController.executeTask(task);
      }

      async.setIntervalToPoll(3000L);
      return async;
   }

   public static AsyncJob killServer(String domainName, final String serverName, long waitTime) {
      Task task = new Task(nmRestText.msgKillingServer(serverName), domainName, CommonUtils.ComponentType.SERVER, serverName, CommonUtils.OperationType.KILL) {
         public ResourceResponse execute() {
            return NMServerUtils.killServer(this.domainName, serverName);
         }
      };
      AsyncJob async = null;
      if (waitTime > 0L) {
         async = AsyncController.executeTask(task, waitTime);
      } else {
         async = AsyncController.executeTask(task);
      }

      return async;
   }

   public static void restartNodeManager(final String domainName) {
      Thread nmThread = new Thread(new Runnable() {
         public void run() {
            RestInputOutput rio = new RestInputOutput();
            InternalNMCommandHandler handler = new InternalNMCommandHandler(NMServer.getInstance(), rio);
            CommonUtils.addDomainCommands(rio, domainName);
            StringBuilder buf = new StringBuilder();
            buf.append(Command.RESTARTNM.toString()).append(" ").append(Boolean.FALSE).append(" ").append(Boolean.FALSE);
            rio.addCommand(buf.toString());
            CommonUtils.runCommands(rio, handler);
         }
      });
      nmThread.start();
   }

   public static void shutdownNodeManager(final String domainName) {
      Thread nmThread = new Thread(new Runnable() {
         public void run() {
            RestInputOutput rio = new RestInputOutput();
            InternalNMCommandHandler handler = new InternalNMCommandHandler(NMServer.getInstance(), rio);
            CommonUtils.addDomainCommands(rio, domainName);
            rio.addCommand(Command.QUIT.toString());
            CommonUtils.runCommands(rio, handler);
         }
      });
      nmThread.start();
   }

   private static ResourceResponse startServer(String domainName, String serverName) {
      return startServer(domainName, serverName, (Properties)null);
   }

   private static ResourceResponse startServer(String domainName, String serverName, Properties props) {
      RestInputOutput rio = new RestInputOutput();
      InternalNMCommandHandler handler = new InternalNMCommandHandler(NMServer.getInstance(), rio);
      CommonUtils.addDomainServerCommands(rio, domainName, serverName);
      if (props == null) {
         rio.addCommand(Command.START.toString());
      } else {
         rio.addCommand(Command.STARTP.toString());
         Iterator var5 = props.keySet().iterator();

         while(var5.hasNext()) {
            Object prop = var5.next();
            rio.addCommand(prop + "=" + props.getProperty((String)prop));
         }
      }

      return CommonUtils.runCommands(rio, handler);
   }

   private static ResourceResponse killServer(String domainName, String serverName) {
      RestInputOutput rio = new RestInputOutput();
      InternalNMCommandHandler handler = new InternalNMCommandHandler(NMServer.getInstance(), rio);
      CommonUtils.addDomainServerCommands(rio, domainName, serverName);
      rio.addCommand(Command.KILL.toString());
      return CommonUtils.runCommands(rio, handler);
   }

   public static AsyncJob getServerJob(String jobId, String domainName, String serverName) {
      return AsyncController.getJob(jobId, domainName, CommonUtils.ComponentType.SERVER, serverName);
   }

   public static List getServerJobs(String domainName, String serverName) {
      return AsyncController.getJobs(domainName, CommonUtils.ComponentType.SERVER, serverName);
   }
}
