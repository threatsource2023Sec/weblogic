package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.weaver.IHasPosition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedTypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.World;

public class ScopeWithTypeVariables implements IScope {
   private IScope delegateScope;
   private String[] typeVariableNames;
   private UnresolvedTypeVariableReferenceType[] typeVarTypeXs;

   public ScopeWithTypeVariables(String[] typeVarNames, IScope delegate) {
      this.delegateScope = delegate;
      this.typeVariableNames = typeVarNames;
      this.typeVarTypeXs = new UnresolvedTypeVariableReferenceType[typeVarNames.length];
   }

   public UnresolvedType lookupType(String name, IHasPosition location) {
      for(int i = 0; i < this.typeVariableNames.length; ++i) {
         if (this.typeVariableNames[i].equals(name)) {
            if (this.typeVarTypeXs[i] == null) {
               this.typeVarTypeXs[i] = new UnresolvedTypeVariableReferenceType(new TypeVariable(name));
            }

            return this.typeVarTypeXs[i];
         }
      }

      return this.delegateScope.lookupType(name, location);
   }

   public World getWorld() {
      return this.delegateScope.getWorld();
   }

   public ResolvedType getEnclosingType() {
      return this.delegateScope.getEnclosingType();
   }

   public IMessageHandler getMessageHandler() {
      return this.delegateScope.getMessageHandler();
   }

   public FormalBinding lookupFormal(String name) {
      return this.delegateScope.lookupFormal(name);
   }

   public FormalBinding getFormal(int i) {
      return this.delegateScope.getFormal(i);
   }

   public int getFormalCount() {
      return this.delegateScope.getFormalCount();
   }

   public String[] getImportedPrefixes() {
      return this.delegateScope.getImportedPrefixes();
   }

   public String[] getImportedNames() {
      return this.delegateScope.getImportedNames();
   }

   public void message(IMessage.Kind kind, IHasPosition location, String message) {
      this.delegateScope.message(kind, location, message);
   }

   public void message(IMessage.Kind kind, IHasPosition location1, IHasPosition location2, String message) {
      this.delegateScope.message(kind, location1, location2, message);
   }

   public void message(IMessage aMessage) {
      this.delegateScope.message(aMessage);
   }
}
