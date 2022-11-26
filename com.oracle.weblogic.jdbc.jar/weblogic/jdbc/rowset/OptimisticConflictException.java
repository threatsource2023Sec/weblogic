package weblogic.jdbc.rowset;

import javax.sql.rowset.spi.SyncProviderException;
import javax.sql.rowset.spi.SyncResolver;

/** @deprecated */
@Deprecated
public class OptimisticConflictException extends SyncProviderException {
   private static final long serialVersionUID = 7684785426862655886L;

   public OptimisticConflictException() {
   }

   public OptimisticConflictException(String reason) {
      super(reason);
   }

   public OptimisticConflictException(SyncResolver sr) {
      super(sr);
   }

   public OptimisticConflictException(String reason, SyncResolver sr) {
      super(reason);
      this.setSyncResolver(sr);
   }

   public OptimisticConflictException(String reason, String sqlState) {
      super(reason);
   }

   public OptimisticConflictException(String reason, String sqlState, int vendorCode) {
      super(reason);
   }
}
