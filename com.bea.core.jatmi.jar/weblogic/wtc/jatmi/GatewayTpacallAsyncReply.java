package weblogic.wtc.jatmi;

import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import javax.transaction.Transaction;

public interface GatewayTpacallAsyncReply extends TpacallAsyncReply {
   Transaction getTransaction();

   void setTargetSubject(TCAuthenticatedUser var1);
}
