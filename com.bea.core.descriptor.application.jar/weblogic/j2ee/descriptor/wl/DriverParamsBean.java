package weblogic.j2ee.descriptor.wl;

public interface DriverParamsBean {
   StatementBean getStatement();

   StatementBean createStatement();

   void destroyStatement(StatementBean var1);

   PreparedStatementBean getPreparedStatement();

   PreparedStatementBean createPreparedStatement();

   void destroyPreparedStatement(PreparedStatementBean var1);

   boolean isRowPrefetchEnabled();

   void setRowPrefetchEnabled(boolean var1);

   int getRowPrefetchSize();

   void setRowPrefetchSize(int var1);

   int getStreamChunkSize();

   void setStreamChunkSize(int var1);
}
