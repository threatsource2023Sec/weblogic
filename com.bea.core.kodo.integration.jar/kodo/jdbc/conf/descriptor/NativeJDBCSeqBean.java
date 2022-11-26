package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.SequenceBean;

public interface NativeJDBCSeqBean extends SequenceBean {
   int getType();

   void setType(int var1);

   int getAllocate();

   void setAllocate(int var1);

   String getTableName();

   void setTableName(String var1);

   int getInitialValue();

   void setInitialValue(int var1);

   String getSequence();

   void setSequence(String var1);

   String getSequenceName();

   void setSequenceName(String var1);

   String getFormat();

   void setFormat(String var1);

   int getIncrement();

   void setIncrement(int var1);
}
