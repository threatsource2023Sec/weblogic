package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleSetOfCharArray;
import java.util.function.Predicate;

public interface IModuleAwareNameEnvironment extends INameEnvironment {
   default NameEnvironmentAnswer findType(char[][] compoundTypeName) {
      return this.findType(compoundTypeName, ModuleBinding.ANY);
   }

   default NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {
      return this.findType(typeName, packageName, ModuleBinding.ANY);
   }

   default boolean isPackage(char[][] parentPackageName, char[] packageName) {
      return this.getModulesDeclaringPackage(parentPackageName, packageName, ModuleBinding.ANY) != null;
   }

   NameEnvironmentAnswer findType(char[][] var1, char[] var2);

   NameEnvironmentAnswer findType(char[] var1, char[][] var2, char[] var3);

   char[][] getModulesDeclaringPackage(char[][] var1, char[] var2, char[] var3);

   default char[][] getUniqueModulesDeclaringPackage(char[][] parentPackageName, char[] name, char[] moduleName) {
      char[][] allNames = this.getModulesDeclaringPackage(parentPackageName, name, moduleName);
      if (allNames != null && allNames.length > 1) {
         SimpleSetOfCharArray set = new SimpleSetOfCharArray(allNames.length);
         char[][] var9 = allNames;
         int var8 = allNames.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            char[] oneName = var9[var7];
            set.add(oneName);
         }

         allNames = new char[set.elementSize][];
         set.asArray(allNames);
      }

      return allNames;
   }

   boolean hasCompilationUnit(char[][] var1, char[] var2, boolean var3);

   IModule getModule(char[] var1);

   char[][] getAllAutomaticModules();

   default void applyModuleUpdates(IUpdatableModule module, IUpdatableModule.UpdateKind kind) {
   }

   public static enum LookupStrategy {
      Named {
         public boolean matchesWithName(Object elem, Predicate isNamed, Predicate nameMatcher) {
            if (!IModuleAwareNameEnvironment.LookupStrategy.$assertionsDisabled && nameMatcher == null) {
               throw new AssertionError("name match needs a nameMatcher");
            } else {
               return isNamed.test(elem) && nameMatcher.test(elem);
            }
         }
      },
      AnyNamed {
         public boolean matchesWithName(Object elem, Predicate isNamed, Predicate nameMatcher) {
            return isNamed.test(elem);
         }
      },
      Any {
         public boolean matchesWithName(Object elem, Predicate isNamed, Predicate nameMatcher) {
            return true;
         }
      },
      Unnamed {
         public boolean matchesWithName(Object elem, Predicate isNamed, Predicate nameMatcher) {
            return !isNamed.test(elem);
         }
      };

      // $FF: synthetic field
      static final boolean $assertionsDisabled = !IModuleAwareNameEnvironment.class.desiredAssertionStatus();
      // $FF: synthetic field
      private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy;

      private LookupStrategy() {
      }

      public abstract boolean matchesWithName(Object var1, Predicate var2, Predicate var3);

      public boolean matches(Object elem, Predicate isNamed) {
         return this.matchesWithName(elem, isNamed, (t) -> {
            return true;
         });
      }

      public static LookupStrategy get(char[] moduleName) {
         if (moduleName == ModuleBinding.ANY) {
            return Any;
         } else if (moduleName == ModuleBinding.ANY_NAMED) {
            return AnyNamed;
         } else {
            return moduleName == ModuleBinding.UNNAMED ? Unnamed : Named;
         }
      }

      public static String getStringName(char[] moduleName) {
         switch (get(moduleName)) {
            case Named:
               return String.valueOf(moduleName);
            default:
               return null;
         }
      }

      // $FF: synthetic method
      LookupStrategy(LookupStrategy var3) {
         this();
      }

      // $FF: synthetic method
      static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy() {
         int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy;
         if (var10000 != null) {
            return var10000;
         } else {
            int[] var0 = new int[values().length];

            try {
               var0[Any.ordinal()] = 3;
            } catch (NoSuchFieldError var4) {
            }

            try {
               var0[AnyNamed.ordinal()] = 2;
            } catch (NoSuchFieldError var3) {
            }

            try {
               var0[Named.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
            }

            try {
               var0[Unnamed.ordinal()] = 4;
            } catch (NoSuchFieldError var1) {
            }

            $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy = var0;
            return var0;
         }
      }
   }
}
