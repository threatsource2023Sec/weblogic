package weblogic.management.deploy.internal.parallel;

import java.util.Collection;
import weblogic.management.DeploymentException;

public class MultiDeploymentException extends DeploymentException {
   private final Collection throwables;

   public MultiDeploymentException(Collection t) {
      super((Throwable)t.iterator().next());
      this.throwables = t;
   }

   public Collection getThrowables() {
      return this.throwables;
   }
}
