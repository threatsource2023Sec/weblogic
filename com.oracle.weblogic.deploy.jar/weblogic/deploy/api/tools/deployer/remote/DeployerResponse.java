package weblogic.deploy.api.tools.deployer.remote;

import java.io.Serializable;

public class DeployerResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private int failures;
   private String output;

   DeployerResponse(int f, String o) {
      this.failures = f;
      this.output = o;
   }

   public int getFailures() {
      return this.failures;
   }

   public String getOutput() {
      return this.output;
   }
}
