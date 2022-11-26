package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

class CodeUtils implements InstrumentationEngineConstants {
   static void generateCatchAll(MethodVisitor codeVisitor, int exceptionIndex, Type retType, Label targetLabel, Label finallyLabel) {
      codeVisitor.visitLabel(targetLabel);
      codeVisitor.visitVarInsn(58, exceptionIndex);
      if (finallyLabel != null) {
         int popOpcode = pushDummyReturnValue(codeVisitor, retType);
         codeVisitor.visitJumpInsn(168, finallyLabel);
         if (popOpcode > 0) {
            codeVisitor.visitInsn(popOpcode);
         }
      }

      codeVisitor.visitVarInsn(25, exceptionIndex);
      codeVisitor.visitInsn(191);
   }

   static int pushDummyReturnValue(MethodVisitor codeVisitor, Type retType) {
      switch (retType.getSort()) {
         case 0:
            return 0;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            codeVisitor.visitInsn(3);
            break;
         case 6:
            codeVisitor.visitInsn(11);
            break;
         case 7:
            codeVisitor.visitInsn(9);
            return 88;
         case 8:
            codeVisitor.visitInsn(14);
            return 88;
         case 9:
         case 10:
            codeVisitor.visitInsn(1);
      }

      return 87;
   }

   static int getPOPInstruction(Type type) {
      int code = 87;
      switch (type.getSort()) {
         case 0:
            code = 0;
            break;
         case 7:
         case 8:
            code = 88;
      }

      return code;
   }

   static int getDUPInstruction(Type type) {
      int code = 89;
      switch (type.getSort()) {
         case 0:
            code = 0;
            break;
         case 7:
         case 8:
            code = 92;
      }

      return code;
   }

   static void pushInt(MethodVisitor cv, int val) {
      if (val <= 127) {
         cv.visitIntInsn(16, val);
      } else {
         cv.visitIntInsn(17, val);
      }

   }

   static void pushArgs(MethodVisitor codeVisitor, Type[] argTypes) {
      int varIndex = 0;

      for(int i = 0; i < argTypes.length; ++i) {
         int opcode = argTypes[i].getOpcode(21);
         codeVisitor.visitVarInsn(opcode, varIndex);
         varIndex += argTypes[i].getSize();
      }

   }

   static void objectifyArg(MethodVisitor codeVisitor, Type aType, String supportClassName) {
      String methodName = "convertToObject";
      String methodDesc = null;
      switch (aType.getSort()) {
         case 1:
            methodDesc = "(Z)Ljava/lang/Object;";
            break;
         case 2:
            methodDesc = "(C)Ljava/lang/Object;";
            break;
         case 3:
            methodDesc = "(B)Ljava/lang/Object;";
            break;
         case 4:
            methodDesc = "(S)Ljava/lang/Object;";
            break;
         case 5:
            methodDesc = "(I)Ljava/lang/Object;";
            break;
         case 6:
            methodDesc = "(F)Ljava/lang/Object;";
            break;
         case 7:
            methodDesc = "(J)Ljava/lang/Object;";
            break;
         case 8:
            methodDesc = "(D)Ljava/lang/Object;";
            break;
         default:
            return;
      }

      codeVisitor.visitMethodInsn(184, supportClassName, methodName, methodDesc);
   }

   static void deObjectifyArg(MethodVisitor codeVisitor, Type aType, String supportClassName) {
      String methodName = "convertFromObject";
      String methodDesc = null;
      String castTo = "java/lang/Object";
      switch (aType.getSort()) {
         case 1:
            methodDesc = "(Ljava/lang/Boolean;)Z";
            castTo = "java/lang/Boolean";
            break;
         case 2:
            methodDesc = "(Ljava/lang/Character;)C";
            castTo = "java/lang/Character";
            break;
         case 3:
            methodDesc = "(Ljava/lang/Byte;)B";
            castTo = "java/lang/Byte";
            break;
         case 4:
            methodDesc = "(Ljava/lang/Short;)S";
            castTo = "java/lang/Short";
            break;
         case 5:
            methodDesc = "(Ljava/lang/Integer;)I";
            castTo = "java/lang/Integer";
            break;
         case 6:
            methodDesc = "(Ljava/lang/Float;)F";
            castTo = "java/lang/Float";
            break;
         case 7:
            methodDesc = "(Ljava/lang/Long;)J";
            castTo = "java/lang/Long";
            break;
         case 8:
            methodDesc = "(Ljava/lang/Double;)D";
            castTo = "java/lang/Double";
            break;
         case 9:
            codeVisitor.visitTypeInsn(192, aType.getDescriptor());
            return;
         default:
            codeVisitor.visitTypeInsn(192, aType.getClassName().replace('.', '/'));
            return;
      }

      codeVisitor.visitTypeInsn(192, castTo);
      codeVisitor.visitMethodInsn(184, supportClassName, methodName, methodDesc);
   }

   static void captureArguments(MethodVisitor codeVisitor, boolean argsOnStack, RegisterFile registers, String supportClassName, Type[] argTypes, boolean isNewCall, boolean captureArgs, boolean captureThisOnly) {
      if (!registers.getLocalHolderRequired()) {
         throw new RuntimeException("Should not be called if generating code using locals");
      } else if (argTypes != null && argTypes.length != 0) {
         if (argsOnStack) {
            captureThisOnly = false;
         }

         if (captureThisOnly && !captureArgs) {
            Type[] typeArr = new Type[]{argTypes[0]};
            argTypes = typeArr;
         }

         int argCnt = argTypes.length;
         int holderIndex = true;
         int argsIndex = true;
         int scratchIndex = true;
         int holderIndex = registers.getLocalHolderRegister();
         Label l1 = new Label();
         codeVisitor.visitVarInsn(25, holderIndex);
         codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "argsCapture", "Z");
         codeVisitor.visitJumpInsn(153, l1);
         codeVisitor.visitVarInsn(25, holderIndex);
         if (argCnt <= 127) {
            codeVisitor.visitIntInsn(16, argCnt);
         } else {
            codeVisitor.visitIntInsn(17, argCnt);
         }

         codeVisitor.visitTypeInsn(189, "java/lang/Object");
         codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");
         int index;
         if (argsOnStack) {
            for(index = argCnt - 1; index >= 0; --index) {
               if (index == 0 && isNewCall) {
                  codeVisitor.visitVarInsn(25, holderIndex);
                  codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");
                  codeVisitor.visitIntInsn(16, index);
                  codeVisitor.visitInsn(1);
                  codeVisitor.visitInsn(83);
               } else {
                  objectifyArg(codeVisitor, argTypes[index], supportClassName);
                  codeVisitor.visitVarInsn(25, holderIndex);
                  codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");
                  codeVisitor.visitInsn(95);
                  pushInt(codeVisitor, index);
                  codeVisitor.visitInsn(95);
                  codeVisitor.visitInsn(83);
               }
            }

            for(index = isNewCall ? 1 : 0; index < argCnt; ++index) {
               codeVisitor.visitVarInsn(25, holderIndex);
               codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");
               pushInt(codeVisitor, index);
               codeVisitor.visitInsn(50);
               deObjectifyArg(codeVisitor, argTypes[index], supportClassName);
            }
         } else {
            index = 0;
            codeVisitor.visitVarInsn(25, holderIndex);
            codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");

            for(int i = 0; i < argCnt; ++i) {
               if (argCnt > 1) {
                  codeVisitor.visitInsn(89);
               }

               Type aType = argTypes[i];
               pushInt(codeVisitor, i);
               codeVisitor.visitVarInsn(aType.getOpcode(21), index);
               objectifyArg(codeVisitor, aType, supportClassName);
               codeVisitor.visitInsn(83);
               index += aType.getSize();
            }

            if (argCnt > 1) {
               codeVisitor.visitInsn(87);
            }

            if (captureThisOnly) {
               codeVisitor.visitVarInsn(25, holderIndex);
               codeVisitor.visitInsn(89);
               codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");
               codeVisitor.visitInsn(3);
               codeVisitor.visitInsn(50);
               codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "thisArg", "Ljava/lang/Object;");
            }
         }

         codeVisitor.visitLabel(l1);
      }
   }

   static void captureSensitiveArguments(MethodVisitor codeVisitor, RegisterFile registers, String supportClassName, int length) {
      if (!registers.getLocalHolderRequired()) {
         throw new RuntimeException("Should not be called if generating code using locals");
      } else if (length > 0) {
         int holderIndex = registers.getLocalHolderRegister();
         Label l1 = new Label();
         codeVisitor.visitVarInsn(25, holderIndex);
         codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "argsCapture", "Z");
         codeVisitor.visitJumpInsn(153, l1);
         codeVisitor.visitVarInsn(25, registers.getLocalHolderRegister());
         pushInt(codeVisitor, length);
         codeVisitor.visitMethodInsn(184, supportClassName, "toSensitive", "(I)[Ljava/lang/Object;");
         codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "args", "[Ljava/lang/Object;");
         codeVisitor.visitLabel(l1);
      }
   }

   static String getJPMonitorsFieldName(String jpName) {
      return jpName == null ? null : jpName.replace("_WLDF$INST_JPFLD_", "_WLDF$INST_JPFLD_JPMONS_");
   }
}
