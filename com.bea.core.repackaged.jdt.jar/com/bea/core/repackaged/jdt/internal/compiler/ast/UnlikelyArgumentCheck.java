package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;

public class UnlikelyArgumentCheck {
   public final TypeConstants.DangerousMethod dangerousMethod;
   public final TypeBinding typeToCheck;
   public final TypeBinding expectedType;
   public final TypeBinding typeToReport;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$DangerousMethod;

   private UnlikelyArgumentCheck(TypeConstants.DangerousMethod dangerousMethod, TypeBinding typeToCheck, TypeBinding expectedType, TypeBinding typeToReport) {
      this.dangerousMethod = dangerousMethod;
      this.typeToCheck = typeToCheck;
      this.expectedType = expectedType;
      this.typeToReport = typeToReport;
   }

   public boolean isDangerous(BlockScope currentScope) {
      TypeBinding typeToCheck2 = this.typeToCheck;
      if (typeToCheck2.isBaseType()) {
         typeToCheck2 = currentScope.boxing(typeToCheck2);
      }

      TypeBinding expectedType2 = this.expectedType;
      if (expectedType2.isBaseType()) {
         expectedType2 = currentScope.boxing(expectedType2);
      }

      if (this.dangerousMethod != TypeConstants.DangerousMethod.Equals && currentScope.compilerOptions().reportUnlikelyCollectionMethodArgumentTypeStrict) {
         return !typeToCheck2.isCompatibleWith(expectedType2, currentScope);
      } else {
         if (typeToCheck2.isCapture() || !typeToCheck2.isTypeVariable() || expectedType2.isCapture() || !expectedType2.isTypeVariable()) {
            typeToCheck2 = typeToCheck2.erasure();
            expectedType2 = expectedType2.erasure();
         }

         return !typeToCheck2.isCompatibleWith(expectedType2, currentScope) && !expectedType2.isCompatibleWith(typeToCheck2, currentScope);
      }
   }

   public static UnlikelyArgumentCheck determineCheckForNonStaticSingleArgumentMethod(TypeBinding argumentType, Scope scope, char[] selector, TypeBinding actualReceiverType, TypeBinding[] parameters) {
      if (parameters.length != 1) {
         return null;
      } else {
         int paramTypeId = parameters[0].original().id;
         if (paramTypeId != 1 && paramTypeId != 59) {
            return null;
         } else {
            TypeConstants.DangerousMethod suspect = TypeConstants.DangerousMethod.detectSelector(selector);
            if (suspect == null) {
               return null;
            } else {
               ReferenceBinding listType;
               if (actualReceiverType.hasTypeBit(256) && paramTypeId == 1) {
                  switch (suspect) {
                     case Remove:
                     case Get:
                     case ContainsKey:
                        listType = actualReceiverType.findSuperTypeOriginatingFrom(91, false);
                        if (listType != null && listType.isParameterizedType()) {
                           return new UnlikelyArgumentCheck(suspect, argumentType, ((ParameterizedTypeBinding)listType).typeArguments()[0], listType);
                        }
                     case RemoveAll:
                     case ContainsAll:
                     case RetainAll:
                     default:
                        break;
                     case ContainsValue:
                        listType = actualReceiverType.findSuperTypeOriginatingFrom(91, false);
                        if (listType != null && listType.isParameterizedType()) {
                           return new UnlikelyArgumentCheck(suspect, argumentType, ((ParameterizedTypeBinding)listType).typeArguments()[1], listType);
                        }
                  }
               }

               if (actualReceiverType.hasTypeBit(512)) {
                  if (paramTypeId == 1) {
                     switch (suspect) {
                        case Contains:
                        case Remove:
                           listType = actualReceiverType.findSuperTypeOriginatingFrom(59, false);
                           if (listType != null && listType.isParameterizedType()) {
                              return new UnlikelyArgumentCheck(suspect, argumentType, ((ParameterizedTypeBinding)listType).typeArguments()[0], listType);
                           }
                     }
                  } else if (paramTypeId == 59) {
                     switch (suspect) {
                        case RemoveAll:
                        case ContainsAll:
                        case RetainAll:
                           listType = actualReceiverType.findSuperTypeOriginatingFrom(59, false);
                           ReferenceBinding argumentCollectionType = argumentType.findSuperTypeOriginatingFrom(59, false);
                           if (listType != null && argumentCollectionType != null && argumentCollectionType.isParameterizedTypeWithActualArguments() && listType.isParameterizedTypeWithActualArguments()) {
                              return new UnlikelyArgumentCheck(suspect, ((ParameterizedTypeBinding)argumentCollectionType).typeArguments()[0], ((ParameterizedTypeBinding)listType).typeArguments()[0], listType);
                           }
                     }
                  }

                  if (actualReceiverType.hasTypeBit(1024) && paramTypeId == 1) {
                     switch (suspect) {
                        case IndexOf:
                        case LastIndexOf:
                           listType = actualReceiverType.findSuperTypeOriginatingFrom(92, false);
                           if (listType != null && listType.isParameterizedType()) {
                              return new UnlikelyArgumentCheck(suspect, argumentType, ((ParameterizedTypeBinding)listType).typeArguments()[0], listType);
                           }
                     }
                  }
               }

               return paramTypeId == 1 && suspect == TypeConstants.DangerousMethod.Equals ? new UnlikelyArgumentCheck(suspect, argumentType, actualReceiverType, actualReceiverType) : null;
            }
         }
      }
   }

   public static UnlikelyArgumentCheck determineCheckForStaticTwoArgumentMethod(TypeBinding secondParameter, Scope scope, char[] selector, TypeBinding firstParameter, TypeBinding[] parameters, TypeBinding actualReceiverType) {
      if (parameters.length != 2) {
         return null;
      } else {
         int paramTypeId1 = parameters[0].original().id;
         int paramTypeId2 = parameters[1].original().id;
         if (paramTypeId1 == 1 && paramTypeId2 == 1) {
            TypeConstants.DangerousMethod suspect = TypeConstants.DangerousMethod.detectSelector(selector);
            if (suspect == null) {
               return null;
            } else {
               return actualReceiverType.id == 74 && suspect == TypeConstants.DangerousMethod.Equals ? new UnlikelyArgumentCheck(suspect, secondParameter, firstParameter, firstParameter) : null;
            }
         } else {
            return null;
         }
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$DangerousMethod() {
      int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$DangerousMethod;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[TypeConstants.DangerousMethod.values().length];

         try {
            var0[TypeConstants.DangerousMethod.Contains.ordinal()] = 1;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[TypeConstants.DangerousMethod.ContainsAll.ordinal()] = 4;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[TypeConstants.DangerousMethod.ContainsKey.ordinal()] = 7;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[TypeConstants.DangerousMethod.ContainsValue.ordinal()] = 8;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[TypeConstants.DangerousMethod.Equals.ordinal()] = 11;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[TypeConstants.DangerousMethod.Get.ordinal()] = 6;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[TypeConstants.DangerousMethod.IndexOf.ordinal()] = 9;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[TypeConstants.DangerousMethod.LastIndexOf.ordinal()] = 10;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[TypeConstants.DangerousMethod.Remove.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TypeConstants.DangerousMethod.RemoveAll.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[TypeConstants.DangerousMethod.RetainAll.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$DangerousMethod = var0;
         return var0;
      }
   }
}
