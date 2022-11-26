package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.weaver.IHasPosition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;

public interface IScope {
   UnresolvedType lookupType(String var1, IHasPosition var2);

   World getWorld();

   ResolvedType getEnclosingType();

   IMessageHandler getMessageHandler();

   FormalBinding lookupFormal(String var1);

   FormalBinding getFormal(int var1);

   int getFormalCount();

   String[] getImportedPrefixes();

   String[] getImportedNames();

   void message(IMessage.Kind var1, IHasPosition var2, String var3);

   void message(IMessage.Kind var1, IHasPosition var2, IHasPosition var3, String var4);

   void message(IMessage var1);
}
