package weblogic.management.runtime;

import javax.management.openmbean.TabularData;

public interface WTCStatisticsRuntimeMBean extends RuntimeMBean {
   long getInboundMessageTotalCount(String var1, String var2);

   long getInboundMessageTotalCount(String var1, String var2, boolean var3);

   long getInboundSuccessReqTotalCount(String var1, String var2, boolean var3);

   long getInboundFailReqTotalCount(String var1, String var2, boolean var3);

   long getOutboundMessageTotalCount(String var1, String var2);

   long getOutboundMessageTotalCount(String var1, String var2, String var3);

   long getOutboundSuccessReqTotalCount(String var1, String var2, String var3);

   long getOutboundFailReqTotalCount(String var1, String var2, String var3);

   long getInboundNWMessageTotalSize(String var1, String var2);

   long getInboundNWMessageTotalSize(String var1, String var2, boolean var3);

   long getOutboundNWMessageTotalSize(String var1, String var2);

   long getOutboundNWMessageTotalSize(String var1, String var2, String var3);

   long getOutstandingNWReqCount(String var1, String var2);

   long getOutstandingNWReqCount(String var1, String var2, String var3);

   long getInTransactionCommittedTotalCount(String var1, String var2);

   long getInTransactionRolledBackTotalCount(String var1, String var2);

   long getOutTransactionCommittedTotalCount(String var1, String var2);

   long getOutTransactionRolledBackTotalCount(String var1, String var2);

   TabularData getConnectionStatistics();

   TabularData getImportedServiceStatistics();

   TabularData getExportedServiceStatistics();
}
