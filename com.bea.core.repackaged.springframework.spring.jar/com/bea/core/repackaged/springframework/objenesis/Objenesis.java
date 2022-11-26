package com.bea.core.repackaged.springframework.objenesis;

import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;

public interface Objenesis {
   Object newInstance(Class var1);

   ObjectInstantiator getInstantiatorOf(Class var1);
}
