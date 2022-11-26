package weblogic.wtc.wls;

import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.intf.TCResourceService;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;
import weblogic.management.MBeanHome;
import weblogic.management.configuration.DomainMBean;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TxHelper;
import weblogic.transaction.XIDFactory;
import weblogic.transaction.internal.ServerTransactionManagerImpl;
import weblogic.wtc.gwt.OatmialServices;

public class WlsResourceService implements TCResourceService {
   private int wlsFormatID = XIDFactory.getFormatId();
   private MBeanHome home;
   private static int bypass_tight_couple_check = -1;

   public Xid createWLSXid(int formatID, byte[] gtrid, byte[] bqual) {
      return TxHelper.createXid(formatID, gtrid, bqual);
   }

   public int getWLSFormatID() {
      return this.wlsFormatID;
   }

   public boolean isTightlyCoupledTransactionsEnabled() {
      if (bypass_tight_couple_check == -1) {
         String str;
         if ((str = System.getProperty("weblogic.wtc.bypass_tight_couple_check")) != null) {
            bypass_tight_couple_check = Integer.parseInt(str);
            if (bypass_tight_couple_check != 1) {
               bypass_tight_couple_check = 0;
            }
         } else {
            bypass_tight_couple_check = 0;
         }
      }

      if (bypass_tight_couple_check == 1) {
         return false;
      } else {
         ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
         if (tm instanceof ServerTransactionManagerImpl) {
            ServerTransactionManagerImpl stm = (ServerTransactionManagerImpl)tm;
            return stm.isTightlyCoupledTransactionsEnabled();
         } else {
            return false;
         }
      }
   }

   public boolean getParallelXAEnabled() {
      if (this.home == null) {
         OatmialServices tos = ConfigHelper.getTuxedoServices();
         if (tos == null) {
            return false;
         }

         Context nameService = tos.getNameService();
         if (nameService == null) {
            return false;
         }

         try {
            this.home = (MBeanHome)nameService.lookup("weblogic.management.home.localhome");
         } catch (NamingException var4) {
         }

         if (this.home == null) {
            return false;
         }
      }

      DomainMBean domain = this.home.getActiveDomain();
      return domain == null ? false : domain.getJTA().getParallelXAEnabled();
   }

   public Serializable getTransactionProperty(Transaction tran, String name) {
      return tran instanceof weblogic.transaction.Transaction ? ((weblogic.transaction.Transaction)tran).getProperty(name) : null;
   }
}
