package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleSetOfCharArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public interface IUpdatableModule {
   char[] name();

   void addReads(char[] var1);

   void addExports(char[] var1, char[][] var2);

   void setMainClassName(char[] var1);

   void setPackageNames(SimpleSetOfCharArray var1);

   public static class AddExports implements Consumer {
      char[] name;
      char[][] targets;

      public AddExports(char[] pkgName, char[][] targets) {
         this.name = pkgName;
         this.targets = targets;
      }

      public void accept(IUpdatableModule t) {
         t.addExports(this.name, this.targets);
      }

      public char[] getName() {
         return this.name;
      }

      public char[][] getTargetModules() {
         return this.targets;
      }

      public UpdateKind getKind() {
         return IUpdatableModule.UpdateKind.PACKAGE;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof AddExports)) {
            return false;
         } else {
            AddExports pu = (AddExports)other;
            if (!CharOperation.equals(this.name, pu.name)) {
               return false;
            } else {
               return CharOperation.equals(this.targets, pu.targets);
            }
         }
      }

      public int hashCode() {
         int hash = CharOperation.hashCode(this.name);
         if (this.targets != null) {
            for(int i = 0; i < this.targets.length; ++i) {
               hash += 17 * CharOperation.hashCode(this.targets[i]);
            }
         }

         return hash;
      }
   }

   public static class AddReads implements Consumer {
      char[] targetModule;

      public AddReads(char[] target) {
         this.targetModule = target;
      }

      public void accept(IUpdatableModule t) {
         t.addReads(this.targetModule);
      }

      public char[] getTarget() {
         return this.targetModule;
      }

      public UpdateKind getKind() {
         return IUpdatableModule.UpdateKind.MODULE;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof AddReads)) {
            return false;
         } else {
            AddReads mu = (AddReads)other;
            return CharOperation.equals(this.targetModule, mu.targetModule);
         }
      }

      public int hashCode() {
         return CharOperation.hashCode(this.targetModule);
      }
   }

   public static enum UpdateKind {
      MODULE,
      PACKAGE;
   }

   public static class UpdatesByKind {
      List moduleUpdates = Collections.emptyList();
      List packageUpdates = Collections.emptyList();
      // $FF: synthetic field
      private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IUpdatableModule$UpdateKind;

      public List getList(UpdateKind kind, boolean create) {
         switch (kind) {
            case MODULE:
               if (this.moduleUpdates == Collections.EMPTY_LIST && create) {
                  this.moduleUpdates = new ArrayList();
               }

               return this.moduleUpdates;
            case PACKAGE:
               if (this.packageUpdates == Collections.EMPTY_LIST && create) {
                  this.packageUpdates = new ArrayList();
               }

               return this.packageUpdates;
            default:
               throw new IllegalArgumentException("Unknown enum value " + kind);
         }
      }

      // $FF: synthetic method
      static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IUpdatableModule$UpdateKind() {
         int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IUpdatableModule$UpdateKind;
         if (var10000 != null) {
            return var10000;
         } else {
            int[] var0 = new int[IUpdatableModule.UpdateKind.values().length];

            try {
               var0[IUpdatableModule.UpdateKind.MODULE.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
            }

            try {
               var0[IUpdatableModule.UpdateKind.PACKAGE.ordinal()] = 2;
            } catch (NoSuchFieldError var1) {
            }

            $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IUpdatableModule$UpdateKind = var0;
            return var0;
         }
      }
   }
}
