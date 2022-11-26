package weblogic.transaction;

public interface XAResource extends javax.transaction.xa.XAResource {
   int TMCLUSTERSCAN = 1001001;
   int TMCLUSTERSCANPASSTHROUGH = 1001002;
   int DETERMINERXIDSCAN = 1001003;

   int getDelistFlag();

   boolean detectedUnavailable();
}
