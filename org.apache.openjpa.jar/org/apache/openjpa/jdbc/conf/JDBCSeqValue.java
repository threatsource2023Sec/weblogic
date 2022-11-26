package org.apache.openjpa.jdbc.conf;

import org.apache.openjpa.conf.SeqValue;
import org.apache.openjpa.jdbc.kernel.ClassTableJDBCSeq;
import org.apache.openjpa.jdbc.kernel.NativeJDBCSeq;
import org.apache.openjpa.jdbc.kernel.TableJDBCSeq;
import org.apache.openjpa.jdbc.kernel.ValueTableJDBCSeq;
import org.apache.openjpa.kernel.TimeSeededSeq;

public class JDBCSeqValue extends SeqValue {
   static final String[] ALIASES = new String[]{"table", TableJDBCSeq.class.getName(), "value-table", ValueTableJDBCSeq.class.getName(), "class-table", ClassTableJDBCSeq.class.getName(), "native", NativeJDBCSeq.class.getName(), "time", TimeSeededSeq.class.getName(), "db", TableJDBCSeq.class.getName(), "db-class", ClassTableJDBCSeq.class.getName(), "sjvm", TimeSeededSeq.class.getName()};

   public JDBCSeqValue(String prop) {
      super(prop);
      this.setAliases(ALIASES);
      this.setDefault(ALIASES[0]);
      this.setClassName(ALIASES[1]);
   }
}
