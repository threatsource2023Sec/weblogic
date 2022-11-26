package weblogic.deploy.api.tools.deployer.remote;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.deploy.api.tools.deployer.DeployerException;
import weblogic.deploy.api.tools.deployer.Jsr88Operation;
import weblogic.deploy.api.tools.deployer.Options;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.utils.StackTraceUtils;

public class DeployerBean implements SessionBean {
   public static final String REMOTE_DEPLOYER_NAME = "bea_wls_remote_deployer";
   private static DeployerTextFormatter cat = new DeployerTextFormatter();
   private int failures = -1;
   private String output = "";
   private Jsr88Operation op;
   private ByteArrayOutputStream os;

   public DeployerResponse deploy(Jsr88Operation operation, Options opts) throws Exception {
      this.op = operation;
      this.op.setOptions(opts);
      this.op.options.setRemote(false);
      this.os = new ByteArrayOutputStream();
      this.op.setOut(new PrintStream(this.os));

      DeployerResponse var13;
      try {
         this.connect();
         this.op.prepare();
         this.op.execute();
         this.failures = this.op.report();
         this.output = this.os.toString();
         DeployerResponse var3 = new DeployerResponse(this.failures, this.output);
         return var3;
      } catch (RemoteRuntimeException var10) {
         var13 = new DeployerResponse(1, cat.errorLostConnection());
      } catch (Throwable var11) {
         String m = var11.getMessage();
         if (m == null) {
            m = StackTraceUtils.throwable2StackTrace(var11);
         }

         DeployerResponse var5 = new DeployerResponse(1, m);
         return var5;
      } finally {
         if (this.op != null) {
            this.op.cleanUp();
         }

      }

      return var13;
   }

   private void connect() throws DeployerException {
      try {
         this.op.setDm(SessionHelper.getDeploymentManager((String)null, (String)null));
         if (this.op.options.isUpload()) {
            this.op.getDm().enableFileUploads();
         }

      } catch (DeploymentManagerCreationException var3) {
         DeployerException de = new DeployerException(var3.toString());
         de.initCause(var3);
         throw de;
      }
   }

   public int getFailures() {
      return this.failures;
   }

   public String getOutPut() {
      return this.op.getOut().toString();
   }

   public void ejbActivate() {
   }

   public void ejbRemove() {
   }

   public void ejbPassivate() {
   }

   public void setSessionContext(SessionContext ctx) {
   }

   public void ejbCreate() throws CreateException {
   }
}
