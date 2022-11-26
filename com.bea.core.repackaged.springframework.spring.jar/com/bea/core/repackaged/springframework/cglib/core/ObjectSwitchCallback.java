package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Label;

public interface ObjectSwitchCallback {
   void processCase(Object var1, Label var2) throws Exception;

   void processDefault() throws Exception;
}
