package weblogic.transaction;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface TxHelperService {
   String getTransactionId();
}
