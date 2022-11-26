package weblogic.deploy.api.tools.deployer;

import java.net.URI;
import java.net.URISyntaxException;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.deploy.utils.MBeanHomeTool;
import weblogic.rmi.extensions.RemoteRuntimeException;

public class Deployer extends MBeanHomeTool {
   private final DeployerTextFormatter cat;
   private Options options;
   private String[] myargs;
   private static final String JNDI_NAME = "weblogic.remote.Deployer";

   public Deployer(String[] args) {
      super(args);
      this.myargs = args;
      this.cat = new DeployerTextFormatter();
   }

   public void prepare() {
      super.prepare();
      this.options = new Options(this.opts);
      this.setRequireExtraArgs(false);
   }

   public void runBody() throws Exception {
      int failures = -1;
      this.options.extractOptions();
      System.out.println(this.cat.infoOptions(this.removePassword(this.myargs)));
      this.checkForMultipleOps();
      if (this.options.examples) {
         this.showDetailedMessage();
      } else {
         this.setShowStackTrace(this.options.debug);
         Operation op = null;

         try {
            op = this.newOperation();
            op.validate();
            failures = this.perform(op, failures);
         } catch (Throwable var8) {
            if (var8 instanceof RemoteRuntimeException) {
               throw new DeployerException(this.cat.errorLostConnection());
            }

            if ((!(var8 instanceof RuntimeException) || var8 instanceof IllegalArgumentException) && var8 instanceof Exception) {
               failures = this.handleExpectedException((Exception)var8);
               throw (Exception)var8;
            }

            Exception t1 = this.handleUnexpectedException(var8);
            failures = 1;
            throw t1;
         } finally {
            if (op != null) {
               op.cleanUp();
            }

            this.reset();
            if (!this.options.noexit && failures != -1) {
               System.exit(failures);
            }

         }

      }
   }

   private Exception handleUnexpectedException(Throwable t) throws Exception {
      t.printStackTrace();
      Object t1;
      if (t instanceof Exception) {
         t1 = (Exception)t;
      } else {
         t1 = new DeployerException(t.getMessage());
      }

      if (!this.options.noexit) {
         System.out.println(((Exception)t1).getMessage());
      }

      return (Exception)t1;
   }

   private int handleExpectedException(Exception t) {
      if (this.options.debug) {
         t.printStackTrace();
      } else if (!this.options.noexit) {
         if (t.getMessage() != null) {
            System.out.println(t.getMessage());
         } else {
            System.out.println(t.toString());
         }
      }

      return 1;
   }

   private int perform(Operation op, int failures) throws Exception {
      op.connect();
      op.prepare();
      op.execute();
      failures = op.report();
      return failures;
   }

   private String getUriAsString(URI uri) throws URISyntaxException {
      String s = uri.getScheme();
      String h = uri.getHost();
      int p = uri.getPort();
      return (new URI(s, (String)null, h, p, (String)null, (String)null, (String)null)).toString();
   }

   private String removePassword(String[] s) {
      if (s == null) {
         return "";
      } else {
         boolean pwd = false;
         String news = "";

         for(int i = 0; i < s.length; ++i) {
            String s1 = s[i];
            if (s1.equals("-password")) {
               pwd = true;
            } else if (!pwd) {
               news = news + " " + s1;
            } else {
               pwd = false;
            }
         }

         return news;
      }
   }

   private void checkForMultipleOps() throws IllegalArgumentException {
      int ops = 0;
      StringBuffer sb = new StringBuffer();
      if (this.options.distributeOp) {
         ++ops;
         sb.append("distribute ");
      }

      if (this.options.startOp) {
         ++ops;
         sb.append("start ");
      }

      if (this.options.stopOp) {
         ++ops;
         sb.append("stop ");
      }

      if (this.options.redeployOp) {
         ++ops;
         sb.append("redeploy ");
      }

      if (this.options.undeployOp) {
         ++ops;
         sb.append("undeploy ");
      }

      if (this.options.deployOp) {
         ++ops;
         sb.append("deploy ");
      }

      if (this.options.updateOp) {
         ++ops;
         sb.append("update ");
      }

      if (this.options.cancelOp) {
         ++ops;
         sb.append("cancel ");
      }

      if (this.options.listOp) {
         ++ops;
         sb.append("list ");
      }

      if (this.options.purgetasksOp) {
         ++ops;
         sb.append("purgetasks ");
      }

      if (this.options.listtaskOp) {
         ++ops;
         sb.append("listtask ");
      }

      if (this.options.listappOp) {
         ++ops;
         sb.append("listapps ");
      }

      if (this.options.activateOp) {
         ++ops;
         sb.append("activate ");
      }

      if (this.options.deactivateOp) {
         ++ops;
         sb.append("deactivate ");
      }

      if (this.options.unprepareOp) {
         ++ops;
         sb.append("unprepare ");
      }

      if (this.options.removeOp) {
         ++ops;
         sb.append("remove ");
      }

      if (this.options.extendLoaderOp) {
         ++ops;
         sb.append("extendloader ");
      }

      if (ops > 1) {
         throw new IllegalArgumentException(this.cat.errorMultipleActions(sb.toString()));
      }
   }

   private void showDetailedMessage() {
      if (this.options.distributeOp) {
         System.out.println(this.cat.usageAdDistribute());
      } else if (this.options.startOp) {
         System.out.println(this.cat.usageAdStart());
      } else if (this.options.stopOp) {
         System.out.println(this.cat.usageAdStop());
      } else if (this.options.deployOp) {
         System.out.println(this.cat.usageAdDeploy());
      } else if (this.options.redeployOp) {
         System.out.println(this.cat.usageAdRedeploy());
      } else if (this.options.undeployOp) {
         System.out.println(this.cat.usageAdUndeploy());
      } else if (this.options.updateOp) {
         System.out.println(this.cat.usageAdUpdate());
      } else if (this.options.cancelOp) {
         System.out.println(this.cat.usageAdCancel());
      } else if (this.options.listOp) {
         System.out.println(this.cat.usageAdList());
      } else if (this.options.purgetasksOp) {
         System.out.println(this.cat.usageAdPurgeTasks());
      } else if (this.options.listtaskOp) {
         System.out.println(this.cat.usageAdListtask());
      } else if (this.options.listappOp) {
         System.out.println(this.cat.usageAdListapps());
      } else if (this.options.activateOp) {
         System.out.println(this.cat.usageAdDeploy());
      } else if (this.options.deactivateOp) {
         System.out.println(this.cat.usageAdStop());
      } else if (this.options.unprepareOp) {
         System.out.println(this.cat.usageAdStop());
      } else if (this.options.removeOp) {
         System.out.println(this.cat.usageAdUndeploy());
      } else if (this.options.extendLoaderOp) {
         System.out.println(this.cat.usageAdExtendLoader());
      } else {
         System.out.println(this.cat.showExamples());
      }

   }

   private Operation newOperation() throws IllegalArgumentException {
      if (this.options.distributeOp) {
         return new DistributeOperation(this, this.options);
      } else if (this.options.startOp) {
         return new StartOperation(this, this.options);
      } else if (this.options.stopOp) {
         return new StopOperation(this, this.options);
      } else if (this.options.redeployOp) {
         return new RedeployOperation(this, this.options);
      } else if (this.options.undeployOp) {
         return new UndeployOperation(this, this.options);
      } else if (this.options.deployOp) {
         return new DeployOperation(this, this.options);
      } else if (this.options.updateOp) {
         return new UpdateOperation(this, this.options);
      } else if (this.options.cancelOp) {
         return new CancelOperation(this, this.options);
      } else if (this.options.purgetasksOp) {
         return new PurgeTasksOperation(this, this.options);
      } else if (this.options.listOp) {
         return new ListTaskOperation(this, this.options);
      } else if (this.options.listtaskOp) {
         return new ListTaskOperation(this, this.options);
      } else if (this.options.listappOp) {
         return new ListappsOperation(this, this.options);
      } else if (this.options.activateOp) {
         return new ActivateOperation(this, this.options);
      } else if (this.options.deactivateOp) {
         return new DeactivateOperation(this, this.options);
      } else if (this.options.unprepareOp) {
         return new UnprepareOperation(this, this.options);
      } else if (this.options.removeOp) {
         return new RemoveOperation(this, this.options);
      } else if (this.options.extendLoaderOp) {
         return new ExtendLoaderOperation(this, this.options);
      } else {
         throw new IllegalArgumentException(this.cat.errorMissingAction());
      }
   }

   private void debug(String msg) {
      if (this.options.debug) {
         System.out.println(msg);
      }

   }

   private void inform(String msg) {
      if (this.options.verbose) {
         System.out.println(msg);
      }

   }
}
