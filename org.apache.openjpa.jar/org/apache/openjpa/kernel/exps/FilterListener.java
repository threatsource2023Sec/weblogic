package org.apache.openjpa.kernel.exps;

import java.io.Serializable;
import org.apache.openjpa.kernel.StoreContext;

public interface FilterListener extends Serializable {
   String getTag();

   boolean expectsArguments();

   boolean expectsTarget();

   Object evaluate(Object var1, Class var2, Object[] var3, Class[] var4, Object var5, StoreContext var6);

   Class getType(Class var1, Class[] var2);
}
