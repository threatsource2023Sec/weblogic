package weblogic.transaction.internal;

public class SCInfo {
   private CoordinatorDescriptor coordinatorDescriptor;
   protected boolean syncRegistered;
   private byte state = 1;
   protected static final byte STATE_ACTIVE = 1;
   protected static final byte STATE_PRE_PREPARING = 2;
   protected static final byte STATE_PRE_PREPARED = 3;
   protected static final byte STATE_PREPARING = 4;
   protected static final byte STATE_PREPARED = 5;
   protected static final byte STATE_COMMITTING = 6;
   protected static final byte STATE_COMMITTED = 7;
   protected static final byte STATE_ROLLING_BACK = 8;
   protected static final byte STATE_ROLLEDBACK = 9;
   protected static final byte STATE_UNKNOWN = 10;
   int vote;

   SCInfo(CoordinatorDescriptor acd) {
      this.coordinatorDescriptor = acd;
   }

   SCInfo(String aSCURL) {
      this.coordinatorDescriptor = CoordinatorDescriptor.getOrCreate(aSCURL);
   }

   final void setState(byte st, int vote) {
      this.setState(st);
      this.vote = vote;
   }

   final void setState(byte st) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug((String)("SC[" + this.getServerID() + "] " + this.getStateAsString() + "-->" + this.getStateAsString(st)), (Throwable)(TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null));
      }

      this.state = st;
   }

   final int getState() {
      return this.state;
   }

   final String getServerName() {
      return this.coordinatorDescriptor.getServerName();
   }

   final CoordinatorDescriptor getCoordinatorDescriptor() {
      return this.coordinatorDescriptor;
   }

   final String getServerID() {
      return this.coordinatorDescriptor.getServerID();
   }

   final String getName() {
      return this.getServerName();
   }

   final String getScUrl() {
      return this.coordinatorDescriptor.getCoordinatorURL();
   }

   final String getScUrl(TransactionImpl tx) {
      return this.coordinatorDescriptor.getCoordinatorURL(tx.isSSLEnabled());
   }

   final String getScServerURL() {
      String coUrl = this.coordinatorDescriptor.getCoordinatorURL();
      CoordinatorDescriptor var10000 = this.coordinatorDescriptor;
      return CoordinatorDescriptor.getServerURL(coUrl);
   }

   public String toString() {
      return "SCInfo[" + this.getServerID() + "]=(state=" + this.getStateAsString() + ")";
   }

   final boolean isSyncRegistered() {
      return this.syncRegistered;
   }

   final void setSyncRegistered(boolean flag) {
      this.syncRegistered = flag;
   }

   final String getStateAsString() {
      return this.getStateAsString(this.getState());
   }

   private final String getStateAsString(int s) {
      switch (s) {
         case 1:
            return "active";
         case 2:
            return "pre-preparing";
         case 3:
            return "pre-prepared";
         case 4:
            return "preparing";
         case 5:
            return "prepared";
         case 6:
            return "committing";
         case 7:
            return "committed";
         case 8:
            return "rolling-back";
         case 9:
            return "rolledback";
         case 10:
            return "unknown";
         default:
            return "***UNMAPPED STATE ***: " + s;
      }
   }
}
