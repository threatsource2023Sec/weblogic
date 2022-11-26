package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.SequenceBean;

public interface ClassTableJDBCSeqBean extends SequenceBean {
   int getType();

   void setType(int var1);

   int getAllocate();

   void setAllocate(int var1);

   String getTableName();

   void setTableName(String var1);

   boolean getIgnoreVirtual();

   void setIgnoreVirtual(boolean var1);

   boolean getIgnoreUnmapped();

   void setIgnoreUnmapped(boolean var1);

   String getTable();

   void setTable(String var1);

   String getPrimaryKeyColumn();

   void setPrimaryKeyColumn(String var1);

   boolean getUseAliases();

   void setUseAliases(boolean var1);

   String getSequenceColumn();

   void setSequenceColumn(String var1);

   int getIncrement();

   void setIncrement(int var1);
}
