package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.weaver.IHasPosition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;

public class SimpleScope implements IScope {
   private static final String[] NoStrings = new String[0];
   private static final String[] javaLangPrefixArray = new String[]{"java.lang."};
   private String[] importedPrefixes;
   private String[] importedNames;
   private World world;
   private ResolvedType enclosingType;
   protected FormalBinding[] bindings;

   public SimpleScope(World world, FormalBinding[] bindings) {
      this.importedPrefixes = javaLangPrefixArray;
      this.importedNames = NoStrings;
      this.world = world;
      this.bindings = bindings;
   }

   public UnresolvedType lookupType(String name, IHasPosition location) {
      int len;
      for(len = 0; len < this.importedNames.length; ++len) {
         String importedName = this.importedNames[len];
         if (importedName.endsWith(name)) {
            return this.world.resolve(importedName);
         }
      }

      if (name.length() < 8 && Character.isLowerCase(name.charAt(0))) {
         len = name.length();
         if (len == 3) {
            if (name.equals("int")) {
               return UnresolvedType.INT;
            }
         } else if (len == 4) {
            if (name.equals("void")) {
               return UnresolvedType.VOID;
            }

            if (name.equals("byte")) {
               return UnresolvedType.BYTE;
            }

            if (name.equals("char")) {
               return UnresolvedType.CHAR;
            }

            if (name.equals("long")) {
               return UnresolvedType.LONG;
            }
         } else if (len == 5) {
            if (name.equals("float")) {
               return UnresolvedType.FLOAT;
            }

            if (name.equals("short")) {
               return UnresolvedType.SHORT;
            }
         } else if (len == 6) {
            if (name.equals("double")) {
               return UnresolvedType.DOUBLE;
            }
         } else if (len == 7 && name.equals("boolean")) {
            return UnresolvedType.BOOLEAN;
         }
      }

      if (name.indexOf(46) != -1) {
         return this.world.resolve(UnresolvedType.forName(name), true);
      } else {
         String[] arr$ = this.importedPrefixes;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String importedPrefix = arr$[i$];
            ResolvedType tryType = this.world.resolve(UnresolvedType.forName(importedPrefix + name), true);
            if (!tryType.isMissing()) {
               return tryType;
            }
         }

         return this.world.resolve(UnresolvedType.forName(name), true);
      }
   }

   public IMessageHandler getMessageHandler() {
      return this.world.getMessageHandler();
   }

   public FormalBinding lookupFormal(String name) {
      int i = 0;

      for(int len = this.bindings.length; i < len; ++i) {
         if (this.bindings[i].getName().equals(name)) {
            return this.bindings[i];
         }
      }

      return null;
   }

   public FormalBinding getFormal(int i) {
      return this.bindings[i];
   }

   public int getFormalCount() {
      return this.bindings.length;
   }

   public String[] getImportedNames() {
      return this.importedNames;
   }

   public String[] getImportedPrefixes() {
      return this.importedPrefixes;
   }

   public void setImportedNames(String[] importedNames) {
      this.importedNames = importedNames;
   }

   public void setImportedPrefixes(String[] importedPrefixes) {
      this.importedPrefixes = importedPrefixes;
   }

   public static FormalBinding[] makeFormalBindings(UnresolvedType[] types, String[] names) {
      int len = types.length;
      FormalBinding[] bindings = new FormalBinding[len];

      for(int i = 0; i < len; ++i) {
         bindings[i] = new FormalBinding(types[i], names[i], i);
      }

      return bindings;
   }

   public ISourceLocation makeSourceLocation(IHasPosition location) {
      return new SourceLocation(ISourceLocation.NO_FILE, 0);
   }

   public void message(IMessage.Kind kind, IHasPosition location1, IHasPosition location2, String message) {
      this.message(kind, location1, message);
      this.message(kind, location2, message);
   }

   public void message(IMessage.Kind kind, IHasPosition location, String message) {
      this.getMessageHandler().handleMessage(new Message(message, kind, (Throwable)null, this.makeSourceLocation(location)));
   }

   public void message(IMessage aMessage) {
      this.getMessageHandler().handleMessage(aMessage);
   }

   public World getWorld() {
      return this.world;
   }

   public ResolvedType getEnclosingType() {
      return this.enclosingType;
   }
}
