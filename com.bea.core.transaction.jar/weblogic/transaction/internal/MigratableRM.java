package weblogic.transaction.internal;

import javax.transaction.xa.XAResource;

public interface MigratableRM extends XAResource {
   MigratableRM createForMigration(String var1, Object var2) throws Exception;
}
