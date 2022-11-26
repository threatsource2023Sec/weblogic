package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Value;

public interface Val extends Value {
   int NULL_CMP = 2;
   int JOIN_REL = 4;
   int FORCE_OUTER = 8;

   ExpState initialize(Select var1, ExpContext var2, int var3);

   Object toDataStoreValue(Select var1, ExpContext var2, ExpState var3, Object var4);

   void select(Select var1, ExpContext var2, ExpState var3, boolean var4);

   void selectColumns(Select var1, ExpContext var2, ExpState var3, boolean var4);

   void groupBy(Select var1, ExpContext var2, ExpState var3);

   void orderBy(Select var1, ExpContext var2, ExpState var3, boolean var4);

   Object load(ExpContext var1, ExpState var2, Result var3) throws SQLException;

   void calculateValue(Select var1, ExpContext var2, ExpState var3, Val var4, ExpState var5);

   int length(Select var1, ExpContext var2, ExpState var3);

   void appendTo(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4, int var5);

   void appendIsEmpty(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4);

   void appendIsNotEmpty(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4);

   void appendSize(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4);

   void appendIsNull(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4);

   void appendIsNotNull(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4);
}
