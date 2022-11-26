package com.bea.core.repackaged.springframework.beans.factory.parsing;

import java.util.EventListener;

public interface ReaderEventListener extends EventListener {
   void defaultsRegistered(DefaultsDefinition var1);

   void componentRegistered(ComponentDefinition var1);

   void aliasRegistered(AliasDefinition var1);

   void importProcessed(ImportDefinition var1);
}
