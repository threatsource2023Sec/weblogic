package com.bea.core.repackaged.springframework.cglib.transform.impl;

import com.bea.core.repackaged.springframework.asm.Type;

public interface InterceptFieldFilter {
   boolean acceptRead(Type var1, String var2);

   boolean acceptWrite(Type var1, String var2);
}
