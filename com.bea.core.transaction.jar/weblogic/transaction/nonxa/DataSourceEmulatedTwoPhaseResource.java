package weblogic.transaction.nonxa;

public interface DataSourceEmulatedTwoPhaseResource extends EmulatedTwoPhaseResource {
   boolean isOnePhaseCommit();
}
