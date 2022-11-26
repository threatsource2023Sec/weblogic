package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Label;

public interface ProcessSwitchCallback {
   void processCase(int var1, Label var2) throws Exception;

   void processDefault() throws Exception;
}
