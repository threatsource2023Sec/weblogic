package org.apache.openjpa.kernel.exps;

import java.io.Serializable;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.meta.ClassMetaData;

public interface ExpressionParser extends Serializable {
   String getLanguage();

   Object parse(String var1, ExpressionStoreQuery var2);

   void populate(Object var1, ExpressionStoreQuery var2);

   QueryExpressions eval(Object var1, ExpressionStoreQuery var2, ExpressionFactory var3, ClassMetaData var4);

   Value[] eval(String[] var1, ExpressionStoreQuery var2, ExpressionFactory var3, ClassMetaData var4);
}
