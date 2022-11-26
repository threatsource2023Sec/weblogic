package org.apache.openjpa.kernel.exps;

import java.io.Serializable;
import java.util.Collection;
import org.apache.openjpa.kernel.StoreContext;

public interface AggregateListener extends Serializable {
   String getTag();

   boolean expectsArguments();

   Object evaluate(Collection var1, Class[] var2, Collection var3, StoreContext var4);

   Class getType(Class[] var1);
}
