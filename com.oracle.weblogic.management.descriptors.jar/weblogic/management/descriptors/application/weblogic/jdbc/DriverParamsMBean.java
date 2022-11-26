package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface DriverParamsMBean extends XMLElementMBean {
   StatementMBean getStatement();

   void setStatement(StatementMBean var1);

   PreparedStatementMBean getPreparedStatement();

   void setPreparedStatement(PreparedStatementMBean var1);

   boolean isRowPrefetchEnabled();

   void setRowPrefetchEnabled(boolean var1);

   int getRowPrefetchSize();

   void setRowPrefetchSize(int var1);

   int getStreamChunkSize();

   void setStreamChunkSize(int var1);

   boolean getEnableTwoPhaseCommit();

   void setEnableTwoPhaseCommit(boolean var1);
}
