package org.apache.velocity.runtime.compiler;

import java.io.IOException;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

public class Compiler implements InstructionConstants {
   public static void main(String[] args) {
      String template = args[0].substring(0, args[0].indexOf("."));
      ClassGen cg = new ClassGen(template, "java.lang.Object", "<generated>", 33, (String[])null);
      ConstantPoolGen cp = cg.getConstantPool();
      InstructionList il = new InstructionList();
      MethodGen mg = new MethodGen(9, Type.VOID, new Type[]{new ArrayType(Type.STRING, 1)}, new String[]{"argv"}, "main", template, il, cp);
      int br_index = cp.addClass("java.io.BufferedReader");
      int ir_index = cp.addClass("java.io.InputStreamReader");
      int system_out = cp.addFieldref("java.lang.System", "out", "Ljava/io/PrintStream;");
      int system_in = cp.addFieldref("java.lang.System", "in", "Ljava/io/InputStream;");
      il.append(new NEW(br_index));
      il.append(InstructionConstants.DUP);
      il.append(new NEW(ir_index));
      il.append(InstructionConstants.DUP);
      il.append(new GETSTATIC(system_in));
      il.append(new INVOKESPECIAL(cp.addMethodref("java.io.InputStreamReader", "<init>", "(Ljava/io/InputStream;)V")));
      il.append(new INVOKESPECIAL(cp.addMethodref("java.io.BufferedReader", "<init>", "(Ljava/io/Reader;)V")));
      LocalVariableGen lg = mg.addLocalVariable("in", new ObjectType("java.io.BufferedReader"), (InstructionHandle)null, (InstructionHandle)null);
      int in = lg.getIndex();
      lg.setStart(il.append(new ASTORE(in)));
      lg = mg.addLocalVariable("name", Type.STRING, (InstructionHandle)null, (InstructionHandle)null);
      int name = lg.getIndex();
      il.append(InstructionConstants.ACONST_NULL);
      lg.setStart(il.append(new ASTORE(name)));
      InstructionHandle try_start = il.append(new GETSTATIC(system_out));
      il.append(new PUSH(cp, "I will be a template compiler!"));
      il.append(new INVOKEVIRTUAL(cp.addMethodref("java.io.PrintStream", "println", "(Ljava/lang/String;)V")));
      GOTO g = new GOTO((InstructionHandle)null);
      InstructionHandle try_end = il.append(g);
      InstructionHandle handler = il.append(InstructionConstants.RETURN);
      mg.addExceptionHandler(try_start, try_end, handler, new ObjectType("java.io.IOException"));
      InstructionHandle ih = il.append(new GETSTATIC(system_out));
      g.setTarget(ih);
      il.append(new NEW(cp.addClass("java.lang.StringBuffer")));
      il.append(InstructionConstants.DUP);
      il.append(new PUSH(cp, " "));
      il.append(new INVOKESPECIAL(cp.addMethodref("java.lang.StringBuffer", "<init>", "(Ljava/lang/String;)V")));
      il.append(new ALOAD(name));
      String sig = Type.getMethodSignature(Type.STRINGBUFFER, new Type[]{Type.STRING});
      il.append(new INVOKEVIRTUAL(cp.addMethodref("java.lang.StringBuffer", "append", sig)));
      il.append(new INVOKEVIRTUAL(cp.addMethodref("java.lang.StringBuffer", "toString", "()Ljava/lang/String;")));
      il.append(InstructionConstants.RETURN);
      mg.setMaxStack(5);
      cg.addMethod(mg.getMethod());
      cg.addEmptyConstructor(1);

      try {
         cg.getJavaClass().dump(template + ".class");
      } catch (IOException var20) {
         System.err.println(var20);
      }

   }
}
