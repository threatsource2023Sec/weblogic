package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;

interface Exp extends Expression {
   ExpState initialize(Select var1, ExpContext var2, Map var3);

   void appendTo(Select var1, ExpContext var2, ExpState var3, SQLBuffer var4);

   void selectColumns(Select var1, ExpContext var2, ExpState var3, boolean var4);
}
