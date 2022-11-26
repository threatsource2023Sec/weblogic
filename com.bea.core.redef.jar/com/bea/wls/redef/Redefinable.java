package com.bea.wls.redef;

import weblogic.utils.annotation.BeaSynthetic;

public interface Redefinable {
   @BeaSynthetic
   Object beaInvoke(Object[] var1, int var2);
}
