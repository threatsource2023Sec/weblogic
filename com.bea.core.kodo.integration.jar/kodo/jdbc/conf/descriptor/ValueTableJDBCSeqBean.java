package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.SequenceBean;

public interface ValueTableJDBCSeqBean extends SequenceBean {
   int getType();

   void setType(int var1);

   int getAllocate();

   void setAllocate(int var1);

   String getTableName();

   void setTableName(String var1);

   String getPrimaryKeyValue();

   void setPrimaryKeyValue(String var1);

   String getTable();

   void setTable(String var1);

   String getPrimaryKeyColumn();

   void setPrimaryKeyColumn(String var1);

   String getSequenceColumn();

   void setSequenceColumn(String var1);

   int getIncrement();

   void setIncrement(int var1);
}
