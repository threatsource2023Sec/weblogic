package weblogic.deploy.api.spi.deploy.internal;

import javax.enterprise.deploy.spi.status.ProgressObject;

final class FailedOperationException extends Throwable {
   private ProgressObject po;

   FailedOperationException(ProgressObject po) {
      super((String)null);
      this.po = po;
   }

   ProgressObject getProgressObject() {
      return this.po;
   }
}
