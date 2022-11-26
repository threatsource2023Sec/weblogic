package weblogic.transaction;

import javax.transaction.xa.Xid;

public interface WLXid extends Xid {
   byte[] getTruncatedBranchQualifier(String var1);
}
