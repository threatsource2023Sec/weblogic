package weblogic.wtc.jatmi;

import java.io.Serializable;

public interface TpacallAsyncReply extends Serializable {
   void success(ApplicationToMonitorInterface var1, CallDescriptor var2, Reply var3);

   void failure(ApplicationToMonitorInterface var1, CallDescriptor var2, TPException var3);
}
