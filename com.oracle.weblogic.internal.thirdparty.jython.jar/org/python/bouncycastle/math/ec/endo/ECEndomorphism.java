package org.python.bouncycastle.math.ec.endo;

import org.python.bouncycastle.math.ec.ECPointMap;

public interface ECEndomorphism {
   ECPointMap getPointMap();

   boolean hasEfficientPointMap();
}
