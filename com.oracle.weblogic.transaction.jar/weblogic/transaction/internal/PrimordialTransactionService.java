package weblogic.transaction.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.transaction.TransactionHelper;

@Service
@Named
@RunLevel(10)
public class PrimordialTransactionService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      TransactionHelper.setTransactionHelper(new TransactionHelperImpl());
   }
}
