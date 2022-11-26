package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassParser;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Unknown;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ArrayElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.SimpleElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ArrayType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.BasicType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionByte;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionCP;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionSelect;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionShort;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionTargeter;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LineNumberTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ReferenceType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.SwitchBuilder;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.TargetLostException;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ConstantPoolReader;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Lint;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.Utils;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class Utility {
   private static String[] argNames = new String[]{"arg0", "arg1", "arg2", "arg3", "arg4"};
   private static Hashtable validBoxing = new Hashtable();
   public static int testingParseCounter;

   public static List readAjAttributes(String classname, Attribute[] as, ISourceContext context, World w, AjAttribute.WeaverVersionInfo version, ConstantPoolReader dataDecompressor) {
      List attributes = new ArrayList();
      List forSecondPass = new ArrayList();

      int i;
      for(i = as.length - 1; i >= 0; --i) {
         Attribute a = as[i];
         if (a instanceof Unknown) {
            Unknown u = (Unknown)a;
            String name = u.getName();
            if (name.charAt(0) == 'o' && name.startsWith("com.bea.core.repackaged.aspectj.weaver")) {
               if (name.endsWith("com.bea.core.repackaged.aspectj.weaver.WeaverVersion")) {
                  version = (AjAttribute.WeaverVersionInfo)AjAttribute.read(version, name, u.getBytes(), context, w, dataDecompressor);
                  if (version.getMajorVersion() > AjAttribute.WeaverVersionInfo.getCurrentWeaverMajorVersion()) {
                     throw new BCException("Unable to continue, this version of AspectJ supports classes built with weaver version " + AjAttribute.WeaverVersionInfo.toCurrentVersionString() + " but the class " + classname + " is version " + version.toString() + ".  Please update your AspectJ.");
                  }
               }

               forSecondPass.add(u);
            }
         }
      }

      for(i = forSecondPass.size() - 1; i >= 0; --i) {
         Unknown a = (Unknown)forSecondPass.get(i);
         String name = a.getName();
         AjAttribute attr = AjAttribute.read(version, name, a.getBytes(), context, w, dataDecompressor);
         if (attr != null) {
            attributes.add(attr);
         }
      }

      return attributes;
   }

   public static String beautifyLocation(ISourceLocation isl) {
      StringBuffer nice = new StringBuffer();
      if (isl != null && isl.getSourceFile() != null && isl.getSourceFile().getName().indexOf("no debug info available") == -1) {
         int takeFrom = isl.getSourceFile().getPath().lastIndexOf(47);
         if (takeFrom == -1) {
            takeFrom = isl.getSourceFile().getPath().lastIndexOf(92);
         }

         nice.append(isl.getSourceFile().getPath().substring(takeFrom + 1));
         if (isl.getLine() != 0) {
            nice.append(":").append(isl.getLine());
         }
      } else {
         nice.append("no debug info available");
      }

      return nice.toString();
   }

   public static Instruction createSuperInvoke(InstructionFactory fact, BcelWorld world, Member signature) {
      if (Modifier.isInterface(signature.getModifiers())) {
         throw new RuntimeException("bad");
      } else if (!Modifier.isPrivate(signature.getModifiers()) && !signature.getName().equals("<init>")) {
         if (Modifier.isStatic(signature.getModifiers())) {
            throw new RuntimeException("bad");
         } else {
            short kind = 183;
            return fact.createInvoke(signature.getDeclaringType().getName(), signature.getName(), BcelWorld.makeBcelType(signature.getReturnType()), BcelWorld.makeBcelTypes(signature.getParameterTypes()), kind);
         }
      } else {
         throw new RuntimeException("unimplemented, possibly bad");
      }
   }

   public static Instruction createInvoke(InstructionFactory fact, BcelWorld world, Member signature) {
      int signatureModifiers = signature.getModifiers();
      short kind;
      if (Modifier.isInterface(signatureModifiers)) {
         kind = 185;
      } else if (Modifier.isStatic(signatureModifiers)) {
         kind = 184;
      } else if (!Modifier.isPrivate(signatureModifiers) && !signature.getName().equals("<init>")) {
         kind = 182;
      } else {
         kind = 183;
      }

      UnresolvedType targetType = signature.getDeclaringType();
      if (((UnresolvedType)targetType).isParameterizedType()) {
         targetType = ((UnresolvedType)targetType).resolve(world).getGenericType();
      }

      return fact.createInvoke(((UnresolvedType)targetType).getName(), signature.getName(), BcelWorld.makeBcelType(signature.getReturnType()), BcelWorld.makeBcelTypes(signature.getParameterTypes()), kind);
   }

   public static Instruction createGet(InstructionFactory fact, Member signature) {
      short kind;
      if (Modifier.isStatic(signature.getModifiers())) {
         kind = 178;
      } else {
         kind = 180;
      }

      return fact.createFieldAccess(signature.getDeclaringType().getName(), signature.getName(), BcelWorld.makeBcelType(signature.getReturnType()), kind);
   }

   public static Instruction createSet(InstructionFactory fact, Member signature) {
      short kind;
      if (Modifier.isStatic(signature.getModifiers())) {
         kind = 179;
      } else {
         kind = 181;
      }

      return fact.createFieldAccess(signature.getDeclaringType().getName(), signature.getName(), BcelWorld.makeBcelType(signature.getReturnType()), kind);
   }

   public static Instruction createInstanceof(InstructionFactory fact, ReferenceType t) {
      int cpoolEntry = t instanceof ArrayType ? fact.getConstantPool().addArrayClass((ArrayType)t) : fact.getConstantPool().addClass((ObjectType)t);
      return new InstructionCP((short)193, cpoolEntry);
   }

   public static Instruction createInvoke(InstructionFactory fact, LazyMethodGen m) {
      short kind;
      if (m.getEnclosingClass().isInterface()) {
         if (m.isStatic()) {
            kind = 184;
         } else {
            kind = 185;
         }
      } else if (m.isStatic()) {
         kind = 184;
      } else if (!m.isPrivate() && !m.getName().equals("<init>")) {
         kind = 182;
      } else {
         kind = 183;
      }

      return fact.createInvoke(m.getClassName(), m.getName(), m.getReturnType(), m.getArgumentTypes(), kind);
   }

   public static Instruction createInvoke(InstructionFactory fact, short kind, Member member) {
      return fact.createInvoke(member.getDeclaringType().getName(), member.getName(), BcelWorld.makeBcelType(member.getReturnType()), BcelWorld.makeBcelTypes(member.getParameterTypes()), kind);
   }

   public static String[] makeArgNames(int n) {
      String[] ret = new String[n];

      for(int i = 0; i < n; ++i) {
         if (i < 5) {
            ret[i] = argNames[i];
         } else {
            ret[i] = "arg" + i;
         }
      }

      return ret;
   }

   public static void appendConversion(InstructionList il, InstructionFactory fact, ResolvedType fromType, ResolvedType toType) {
      if (!toType.isConvertableFrom(fromType) && !fromType.isConvertableFrom(toType)) {
         throw new BCException("can't convert from " + fromType + " to " + toType);
      } else {
         World w = toType.getWorld();
         if (w == null) {
            throw new IllegalStateException("Debug349636: Unexpectedly found world null for type " + toType.getName());
         } else {
            if (!w.isInJava5Mode()) {
               if (toType.needsNoConversionFrom(fromType)) {
                  return;
               }
            } else if (toType.needsNoConversionFrom(fromType) && !(toType.isPrimitiveType() ^ fromType.isPrimitiveType())) {
               return;
            }

            if (toType.equals(UnresolvedType.VOID)) {
               il.append(InstructionFactory.createPop(fromType.getSize()));
            } else {
               if (fromType.equals(UnresolvedType.VOID)) {
                  il.append(InstructionFactory.createNull(Type.OBJECT));
                  return;
               }

               Type from;
               String name;
               if (fromType.equals(UnresolvedType.OBJECT)) {
                  from = BcelWorld.makeBcelType((UnresolvedType)toType);
                  if (toType.isPrimitiveType()) {
                     name = toType.toString() + "Value";
                     il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, from, new Type[]{Type.OBJECT}, (short)184));
                  } else {
                     il.append(fact.createCheckCast((ReferenceType)from));
                  }
               } else if (toType.equals(UnresolvedType.OBJECT)) {
                  from = BcelWorld.makeBcelType((UnresolvedType)fromType);
                  name = fromType.toString() + "Object";
                  il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, Type.OBJECT, new Type[]{from}, (short)184));
               } else {
                  Type to;
                  if (toType.getWorld().isInJava5Mode() && validBoxing.get(toType.getSignature() + fromType.getSignature()) != null) {
                     from = BcelWorld.makeBcelType((UnresolvedType)fromType);
                     to = BcelWorld.makeBcelType((UnresolvedType)toType);
                     String name = (String)validBoxing.get(toType.getSignature() + fromType.getSignature());
                     if (toType.isPrimitiveType()) {
                        il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, to, new Type[]{Type.OBJECT}, (short)184));
                     } else {
                        il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, Type.OBJECT, new Type[]{from}, (short)184));
                        il.append(fact.createCheckCast((ReferenceType)to));
                     }
                  } else if (fromType.isPrimitiveType()) {
                     from = BcelWorld.makeBcelType((UnresolvedType)fromType);
                     to = BcelWorld.makeBcelType((UnresolvedType)toType);

                     try {
                        Instruction i = fact.createCast(from, to);
                        if (i != null) {
                           il.append(i);
                        } else {
                           il.append(fact.createCast(from, Type.INT));
                           il.append(fact.createCast(Type.INT, to));
                        }
                     } catch (RuntimeException var8) {
                        il.append(fact.createCast(from, Type.INT));
                        il.append(fact.createCast(Type.INT, to));
                     }
                  } else {
                     from = BcelWorld.makeBcelType((UnresolvedType)toType);
                     il.append(fact.createCheckCast((ReferenceType)from));
                  }
               }
            }

         }
      }
   }

   public static InstructionList createConversion(InstructionFactory factory, Type fromType, Type toType) {
      return createConversion(factory, fromType, toType, false);
   }

   public static InstructionList createConversion(InstructionFactory fact, Type fromType, Type toType, boolean allowAutoboxing) {
      InstructionList il = new InstructionList();
      if ((fromType.equals(Type.BYTE) || fromType.equals(Type.CHAR) || fromType.equals(Type.SHORT)) && toType.equals(Type.INT)) {
         return il;
      } else if (fromType.equals(toType)) {
         return il;
      } else if (toType.equals(Type.VOID)) {
         il.append(InstructionFactory.createPop(fromType.getSize()));
         return il;
      } else if (fromType.equals(Type.VOID)) {
         if (toType instanceof BasicType) {
            throw new BCException("attempting to cast from void to basic type");
         } else {
            il.append(InstructionFactory.createNull(Type.OBJECT));
            return il;
         }
      } else {
         String name;
         if (fromType.equals(Type.OBJECT) && toType instanceof BasicType) {
            name = toType.toString() + "Value";
            il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, toType, new Type[]{Type.OBJECT}, (short)184));
            return il;
         } else if (toType.equals(Type.OBJECT)) {
            if (fromType instanceof BasicType) {
               name = fromType.toString() + "Object";
               il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, Type.OBJECT, new Type[]{fromType}, (short)184));
               return il;
            } else if (fromType instanceof ReferenceType) {
               return il;
            } else {
               throw new RuntimeException();
            }
         } else if (fromType instanceof ReferenceType && ((ReferenceType)fromType).isAssignmentCompatibleWith(toType)) {
            return il;
         } else {
            if (allowAutoboxing) {
               if (toType instanceof BasicType && fromType instanceof ReferenceType) {
                  name = toType.toString() + "Value";
                  il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, toType, new Type[]{Type.OBJECT}, (short)184));
                  return il;
               }

               if (fromType instanceof BasicType && toType instanceof ReferenceType) {
                  name = fromType.toString() + "Object";
                  il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.Conversions", name, Type.OBJECT, new Type[]{fromType}, (short)184));
                  il.append(fact.createCast(Type.OBJECT, toType));
                  return il;
               }
            }

            il.append(fact.createCast(fromType, toType));
            return il;
         }
      }
   }

   public static Instruction createConstant(InstructionFactory fact, int value) {
      Object inst;
      switch (value) {
         case -1:
            inst = InstructionConstants.ICONST_M1;
            break;
         case 0:
            inst = InstructionConstants.ICONST_0;
            break;
         case 1:
            inst = InstructionConstants.ICONST_1;
            break;
         case 2:
            inst = InstructionConstants.ICONST_2;
            break;
         case 3:
            inst = InstructionConstants.ICONST_3;
            break;
         case 4:
            inst = InstructionConstants.ICONST_4;
            break;
         case 5:
            inst = InstructionConstants.ICONST_5;
            break;
         default:
            if (value <= 127 && value >= -128) {
               inst = new InstructionByte((short)16, (byte)value);
            } else if (value <= 32767 && value >= -32768) {
               inst = new InstructionShort((short)17, (short)value);
            } else {
               int ii = fact.getClassGen().getConstantPool().addInteger(value);
               inst = new InstructionCP((short)(value <= 255 ? 18 : 19), ii);
            }
      }

      return (Instruction)inst;
   }

   public static JavaClass makeJavaClass(String filename, byte[] bytes) {
      try {
         ++testingParseCounter;
         ClassParser parser = new ClassParser(new ByteArrayInputStream(bytes), filename);
         return parser.parse();
      } catch (IOException var3) {
         throw new BCException("malformed class file");
      }
   }

   public static void replaceInstruction(InstructionHandle ih, InstructionList replacementInstructions, LazyMethodGen enclosingMethod) {
      InstructionList il = enclosingMethod.getBody();
      InstructionHandle fresh = il.append(ih, replacementInstructions);
      deleteInstruction(ih, fresh, enclosingMethod);
   }

   public static void deleteInstruction(InstructionHandle ih, LazyMethodGen enclosingMethod) {
      deleteInstruction(ih, ih.getNext(), enclosingMethod);
   }

   public static void deleteInstruction(InstructionHandle ih, InstructionHandle retargetTo, LazyMethodGen enclosingMethod) {
      InstructionList il = enclosingMethod.getBody();
      Iterator i$ = ih.getTargetersCopy().iterator();

      while(i$.hasNext()) {
         InstructionTargeter targeter = (InstructionTargeter)i$.next();
         targeter.updateTarget(ih, retargetTo);
      }

      ih.removeAllTargeters();

      try {
         il.delete(ih);
      } catch (TargetLostException var6) {
         throw new BCException("this really can't happen");
      }
   }

   public static Instruction copyInstruction(Instruction i) {
      if (!(i instanceof InstructionSelect)) {
         return i.copy();
      } else {
         InstructionSelect freshSelect = (InstructionSelect)i;
         InstructionHandle[] targets = new InstructionHandle[freshSelect.getTargets().length];

         for(int ii = 0; ii < targets.length; ++ii) {
            targets[ii] = freshSelect.getTargets()[ii];
         }

         return (new SwitchBuilder(freshSelect.getMatchs(), targets, freshSelect.getTarget())).getInstruction();
      }
   }

   public static int getSourceLine(InstructionHandle ih) {
      for(int lookahead = 0; lookahead++ < 100; ih = ih.getPrev()) {
         if (ih == null) {
            return -1;
         }

         Iterator tIter = ih.getTargeters().iterator();

         while(tIter.hasNext()) {
            InstructionTargeter t = (InstructionTargeter)tIter.next();
            if (t instanceof LineNumberTag) {
               return ((LineNumberTag)t).getLineNumber();
            }
         }
      }

      return -1;
   }

   public static void setSourceLine(InstructionHandle ih, int lineNumber) {
      ih.addTargeter(new LineNumberTag(lineNumber));
   }

   public static int makePublic(int i) {
      return i & -7 | 1;
   }

   public static BcelVar[] pushAndReturnArrayOfVars(ResolvedType[] proceedParamTypes, InstructionList il, InstructionFactory fact, LazyMethodGen enclosingMethod) {
      int len = proceedParamTypes.length;
      BcelVar[] ret = new BcelVar[len];

      for(int i = len - 1; i >= 0; --i) {
         ResolvedType typeX = proceedParamTypes[i];
         Type type = BcelWorld.makeBcelType((UnresolvedType)typeX);
         int local = enclosingMethod.allocateLocal(type);
         il.append((Instruction)InstructionFactory.createStore(type, local));
         ret[i] = new BcelVar(typeX, local);
      }

      return ret;
   }

   public static boolean isConstantPushInstruction(Instruction i) {
      long ii = Constants.instFlags[i.opcode];
      return (ii & 1L) != 0L && (ii & 2L) != 0L;
   }

   public static boolean isSuppressing(Member member, String lintkey) {
      boolean isSuppressing = Utils.isSuppressing(member.getAnnotations(), lintkey);
      if (isSuppressing) {
         return true;
      } else {
         UnresolvedType type = member.getDeclaringType();
         return type instanceof ResolvedType ? Utils.isSuppressing(((ResolvedType)type).getAnnotations(), lintkey) : false;
      }
   }

   public static List getSuppressedWarnings(AnnotationAJ[] anns, Lint lint) {
      if (anns == null) {
         return Collections.emptyList();
      } else {
         List suppressedWarnings = new ArrayList();
         boolean found = false;

         for(int i = 0; !found && i < anns.length; ++i) {
            if (UnresolvedType.SUPPRESS_AJ_WARNINGS.getSignature().equals(((BcelAnnotation)anns[i]).getBcelAnnotation().getTypeSignature())) {
               found = true;
               List vals = ((BcelAnnotation)anns[i]).getBcelAnnotation().getValues();
               if (vals != null && !vals.isEmpty()) {
                  ArrayElementValue array = (ArrayElementValue)((NameValuePair)vals.get(0)).getValue();
                  ElementValue[] values = array.getElementValuesArray();

                  for(int j = 0; j < values.length; ++j) {
                     SimpleElementValue value = (SimpleElementValue)values[j];
                     Lint.Kind lintKind = lint.getLintKind(value.getValueString());
                     if (lintKind != null) {
                        suppressedWarnings.add(lintKind);
                     }
                  }
               } else {
                  suppressedWarnings.addAll(lint.allKinds());
               }
            }
         }

         return suppressedWarnings;
      }
   }

   public static Attribute bcelAttribute(AjAttribute a, ConstantPool pool) {
      int nameIndex = pool.addUtf8(a.getNameString());
      byte[] bytes = a.getBytes(new BcelConstantPoolWriter(pool));
      int length = bytes.length;
      return new Unknown(nameIndex, length, bytes, pool);
   }

   static {
      validBoxing.put("Ljava/lang/Byte;B", "byteObject");
      validBoxing.put("Ljava/lang/Character;C", "charObject");
      validBoxing.put("Ljava/lang/Double;D", "doubleObject");
      validBoxing.put("Ljava/lang/Float;F", "floatObject");
      validBoxing.put("Ljava/lang/Integer;I", "intObject");
      validBoxing.put("Ljava/lang/Long;J", "longObject");
      validBoxing.put("Ljava/lang/Short;S", "shortObject");
      validBoxing.put("Ljava/lang/Boolean;Z", "booleanObject");
      validBoxing.put("BLjava/lang/Byte;", "byteValue");
      validBoxing.put("CLjava/lang/Character;", "charValue");
      validBoxing.put("DLjava/lang/Double;", "doubleValue");
      validBoxing.put("FLjava/lang/Float;", "floatValue");
      validBoxing.put("ILjava/lang/Integer;", "intValue");
      validBoxing.put("JLjava/lang/Long;", "longValue");
      validBoxing.put("SLjava/lang/Short;", "shortValue");
      validBoxing.put("ZLjava/lang/Boolean;", "booleanValue");
      testingParseCounter = 0;
   }
}
