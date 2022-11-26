package weblogic.wtc.gwt;

import com.bea.core.jatmi.intf.TCTask;
import weblogic.wtc.jatmi.TXidERFactory;
import weblogic.wtc.jatmi.TuxXidRply;

class TxidHandlerFactory implements TXidERFactory {
   public TxidHandlerFactory() {
   }

   public TCTask getWork(TuxXidRply xidRplyObj) {
      return new OatmialUnknownXidHandler(xidRplyObj);
   }
}
