package org.python.google.common.hash;

import java.io.Serializable;
import org.python.google.common.annotations.Beta;

@Beta
public interface Funnel extends Serializable {
   void funnel(Object var1, PrimitiveSink var2);
}
