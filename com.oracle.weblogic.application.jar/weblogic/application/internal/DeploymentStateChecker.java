package weblogic.application.internal;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationContext;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.DeploymentWrapper;
import weblogic.application.utils.EarUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.utils.StringUtils;

public class DeploymentStateChecker implements Deployment, DeploymentWrapper {
   public static final int STATE_NEW = 0;
   public static final int STATE_PREPARED = 1;
   public static final int STATE_ADMIN = 2;
   public static final int STATE_ACTIVE = 3;
   public static final int STATE_PENDING_UPDATE = 4;
   protected final Deployment delegate;
   private final List callbacks = new ArrayList();
   private int state = 0;
   private int preUpdateState;
   private int pendingUpdates = 0;
   private final DebugLogger deploymentLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   public DeploymentStateChecker(Deployment delegate) {
      this.delegate = delegate;
   }

   public int getState() {
      return this.state;
   }

   public Deployment getDeployment() {
      return this.delegate;
   }

   public static String state2String(int s) {
      switch (s) {
         case 0:
            return "STATE_NEW";
         case 1:
            return "STATE_PREPARED";
         case 2:
            return "STATE_ADMIN";
         case 3:
            return "STATE_ACTIVE";
         case 4:
            return "STATE_PENDING_UPDATE";
         default:
            throw new AssertionError("unexpected state: " + s);
      }
   }

   private void throwAssertion(String msg) {
      Throwable th = null;
      if (this.deploymentLogger.isDebugEnabled()) {
         th = new DeploymentAssertionError("\n\n\n*********   YOU HAVE ENCOUNTERED A DEPLOYMENT BUG *********\n\n" + msg + "\n\n\n", this.callbacks);
         th.printStackTrace();
      } else {
         th = new DeploymentAssertionError(msg, this.callbacks);
      }

      Loggable l = J2EELogger.logInternalErrorLoggable(th);
      l.log();
   }

   private void assertState(int s1, int s2) {
      if (this.state != s1 && this.state != s2) {
         this.throwAssertion("Unexpected current state for application " + this.getApplicationContext().getApplicationId() + " " + state2String(this.state) + ".  We expected us to be in " + state2String(s1) + " or " + state2String(s2));
      }

   }

   private void illegal(int state, int newState) {
      this.throwAssertion("Unexpected transition: current state for application " + this.getApplicationContext().getApplicationId() + " : " + state2String(state) + " attempt to transition to " + state2String(newState));
   }

   private void up(int newState) {
      if (newState - this.state == 1) {
         if (EarUtils.isDebugOn()) {
            EarUtils.debug("transitioned from " + state2String(this.state) + " to " + state2String(newState) + " " + this.delegate.getApplicationContext().getApplicationId());
         }

         this.state = newState;
      } else {
         this.illegal(this.state, newState);
      }

   }

   private void down(int newState, boolean prodToAdmin) {
      if (this.state - newState == 1) {
         if (EarUtils.isDebugOn()) {
            EarUtils.debug("transition from " + state2String(this.state) + " to " + state2String(newState) + " " + this.delegate.getApplicationContext().getApplicationId());
         }

         this.state = newState;
      } else {
         if (EarUtils.isDebugOn()) {
            EarUtils.debug("Illegal state transition: " + state2String(this.state) + " -> " + state2String(newState));
         }

         if (!prodToAdmin || newState != 2 || this.state != 2) {
            this.illegal(this.state, newState);
         }
      }

   }

   private void save(String methodName) {
      this.callbacks.add(new Exception(methodName));
   }

   public void prepare(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("prepare");
      this.delegate.prepare(deploymentContext);
      this.up(1);
   }

   public void activate(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("activate");
      this.delegate.activate(deploymentContext);
      this.up(2);
   }

   public void adminToProduction(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("adminToProduction");
      this.delegate.adminToProduction(deploymentContext);
      this.up(3);
   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("gracefulProductionToAdmin");
      this.down(2, true);
      this.delegate.gracefulProductionToAdmin(deploymentContext);
   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("forceProductionToAdmin");
      if (this.state != 2) {
         this.down(2, true);
      }

      this.delegate.forceProductionToAdmin(deploymentContext);
   }

   public void deactivate(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("deactivate");
      this.down(1, false);
      this.delegate.deactivate(deploymentContext);
   }

   public void unprepare(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("unprepare");
      this.down(0, false);
      this.delegate.unprepare(deploymentContext);
   }

   public void remove(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("remove");
      if (this.state != 0) {
         this.illegal(this.state, 0);
      }

      this.delegate.remove(deploymentContext);
   }

   public void prepareUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("prepareUpdate uris: " + this.getUrisAsString(deploymentContext));
      this.delegate.prepareUpdate(deploymentContext);
      if (this.state != 2 && this.state != 3) {
         if (this.state == 4) {
            ++this.pendingUpdates;
         } else {
            this.illegal(this.state, 4);
         }
      } else {
         this.preUpdateState = this.state;
         this.pendingUpdates = 1;
         this.state = 4;
      }

   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
      String[] uris = null;
      if (deploymentContext != null) {
         uris = deploymentContext.getUpdatedResourceURIs();
      }

      this.save("rollbackUpdate uris: " + this.getUrisAsString(deploymentContext));
      if (this.state != 4) {
         this.illegal(this.state, 3);
      }

      --this.pendingUpdates;
      if (this.pendingUpdates == 0) {
         this.state = this.preUpdateState;
      }

      this.delegate.rollbackUpdate(deploymentContext);
   }

   public void activateUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("activateUpdate uris: " + this.getUrisAsString(deploymentContext));
      if (this.state != 4) {
         this.illegal(this.state, 3);
      }

      --this.pendingUpdates;
      if (this.pendingUpdates == 0) {
         this.state = this.preUpdateState;
      }

      this.delegate.activateUpdate(deploymentContext);
   }

   public void stop(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("stop");
      this.assertState(2, 3);
      this.delegate.stop(deploymentContext);
   }

   public void start(DeploymentContext deploymentContext) throws DeploymentException {
      this.save("start");
      this.assertState(2, 3);
      this.delegate.start(deploymentContext);
   }

   public void assertUndeployable() throws DeploymentException {
      this.delegate.assertUndeployable();
   }

   public ApplicationContext getApplicationContext() {
      return this.delegate.getApplicationContext();
   }

   private String getUrisAsString(DeploymentContext deploymentContext) {
      String[] uris = null;
      if (deploymentContext != null) {
         uris = deploymentContext.getUpdatedResourceURIs();
      }

      return uris == null ? "" : StringUtils.join(uris, ",");
   }

   private static class DeploymentAssertionError extends AssertionError {
      private final List callbacks;

      DeploymentAssertionError(String msg, List callbacks) {
         super(msg);
         this.callbacks = callbacks;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString());
         StringWriter w = new StringWriter();
         PrintWriter pw = new PrintWriter(w);
         this.printCallbacks(pw);
         pw.flush();
         sb.append(w.toString());
         return sb.toString();
      }

      public void printStackTrace(PrintStream ps) {
         super.printStackTrace(ps);
         this.printStackTrace(new PrintWriter(new OutputStreamWriter(ps)));
      }

      public void printStackTrace(PrintWriter pw) {
         super.printStackTrace(pw);
         this.printCallbacks(pw);
      }

      private void printCallbacks(PrintWriter pw) {
         pw.println("\n\nDumping " + this.callbacks.size() + " callbacks");
         pw.println("----------------------   BEGIN CALLBACK DUMP -------");
         Iterator it = this.callbacks.iterator();

         while(it.hasNext()) {
            pw.println("\n");
            ((Exception)it.next()).printStackTrace(pw);
            pw.println("\n");
         }

         pw.println("----------------------   END CALLBACK DUMP -------");
      }
   }
}
