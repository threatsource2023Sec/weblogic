package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.ObjectFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface Scope {
   Object get(String var1, ObjectFactory var2);

   @Nullable
   Object remove(String var1);

   void registerDestructionCallback(String var1, Runnable var2);

   @Nullable
   Object resolveContextualObject(String var1);

   @Nullable
   String getConversationId();
}
