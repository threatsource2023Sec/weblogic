package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Signature;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Synthetic;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.BasicType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ClassGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.SignatureUtils;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.WeaverStateInfo;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.bcel.asm.AsmDetector;
import com.bea.core.repackaged.aspectj.weaver.bcel.asm.StackMapAdder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public final class LazyClassGen {
   private static final int ACC_SYNTHETIC = 4096;
   private static final String[] NO_STRINGS = new String[0];
   int highestLineNumber = 0;
   private final SortedMap inlinedFiles = new TreeMap();
   private boolean regenerateGenericSignatureAttribute = false;
   private BcelObjectType myType;
   private ClassGen myGen;
   private final ConstantPool cp;
   private final World world;
   private final String packageName = null;
   private final List fields = new ArrayList();
   private final List methodGens = new ArrayList();
   private final List classGens = new ArrayList();
   private final List annotations = new ArrayList();
   private int childCounter = 0;
   private final InstructionFactory fact;
   private boolean isSerializable = false;
   private boolean hasSerialVersionUIDField = false;
   private boolean serialVersionUIDRequiresInitialization = false;
   private long calculatedSerialVersionUID;
   private boolean hasClinit = false;
   private ResolvedType[] extraSuperInterfaces = null;
   private ResolvedType superclass = null;
   private Map tjpFields = new HashMap();
   Map annotationCachingFieldCache = new HashMap();
   private int tjpFieldsCounter = -1;
   private int annoFieldsCounter = 0;
   public static final ObjectType proceedingTjpType = new ObjectType("com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint");
   public static final ObjectType tjpType = new ObjectType("com.bea.core.repackaged.aspectj.lang.JoinPoint");
   public static final ObjectType staticTjpType = new ObjectType("com.bea.core.repackaged.aspectj.lang.JoinPoint$StaticPart");
   public static final ObjectType typeForAnnotation = new ObjectType("java.lang.annotation.Annotation");
   public static final ObjectType enclosingStaticTjpType = new ObjectType("com.bea.core.repackaged.aspectj.lang.JoinPoint$EnclosingStaticPart");
   private static final ObjectType sigType = new ObjectType("com.bea.core.repackaged.aspectj.lang.Signature");
   private static final ObjectType factoryType = new ObjectType("com.bea.core.repackaged.aspectj.runtime.reflect.Factory");
   private static final ObjectType classType = new ObjectType("java.lang.Class");
   private static final Type[] ARRAY_7STRING_INT;
   private static final Type[] ARRAY_8STRING_INT;

   void addInlinedSourceFileInfo(String fullpath, int highestLineNumber) {
      Object o = this.inlinedFiles.get(fullpath);
      if (o != null) {
         InlinedSourceFileInfo info = (InlinedSourceFileInfo)o;
         if (info.highestLineNumber < highestLineNumber) {
            info.highestLineNumber = highestLineNumber;
         }
      } else {
         this.inlinedFiles.put(fullpath, new InlinedSourceFileInfo(highestLineNumber));
      }

   }

   void calculateSourceDebugExtensionOffsets() {
      int i = roundUpToHundreds(this.highestLineNumber);

      InlinedSourceFileInfo element;
      for(Iterator i$ = this.inlinedFiles.values().iterator(); i$.hasNext(); i = roundUpToHundreds(i + element.highestLineNumber)) {
         element = (InlinedSourceFileInfo)i$.next();
         element.offset = i;
      }

   }

   private static int roundUpToHundreds(int i) {
      return (i / 100 + 1) * 100;
   }

   int getSourceDebugExtensionOffset(String fullpath) {
      return ((InlinedSourceFileInfo)this.inlinedFiles.get(fullpath)).offset;
   }

   public static void disassemble(String path, String name, PrintStream out) throws IOException {
      if (null != out) {
         BcelWorld world = new BcelWorld(path);
         UnresolvedType ut = UnresolvedType.forName(name);
         ut.setNeedsModifiableDelegate(true);
         LazyClassGen clazz = new LazyClassGen(BcelWorld.getBcelObjectType(world.resolve(ut)));
         clazz.print(out);
         out.println();
      }
   }

   public String getNewGeneratedNameTag() {
      return (new Integer(this.childCounter++)).toString();
   }

   public LazyClassGen(String class_name, String super_class_name, String file_name, int access_flags, String[] interfaces, World world) {
      this.myGen = new ClassGen(class_name, super_class_name, file_name, access_flags, interfaces);
      this.cp = this.myGen.getConstantPool();
      this.fact = new InstructionFactory(this.myGen, this.cp);
      this.regenerateGenericSignatureAttribute = true;
      this.world = world;
   }

   public LazyClassGen(BcelObjectType myType) {
      this.myGen = new ClassGen(myType.getJavaClass());
      this.cp = this.myGen.getConstantPool();
      this.fact = new InstructionFactory(this.myGen, this.cp);
      this.myType = myType;
      this.world = myType.getResolvedTypeX().getWorld();
      ResolvedMember[] methods;
      int i;
      if (this.implementsSerializable(this.getType())) {
         this.isSerializable = true;
         this.hasSerialVersionUIDField = hasSerialVersionUIDField(this.getType());
         methods = this.getType().getDeclaredMethods();

         for(i = 0; i < methods.length; ++i) {
            ResolvedMember method = methods[i];
            if (method.getName().equals("<clinit>")) {
               if (method.getKind() != Member.STATIC_INITIALIZATION) {
                  throw new RuntimeException("qui?");
               }

               this.hasClinit = true;
            }
         }

         if (!this.getType().isInterface() && !this.hasSerialVersionUIDField && this.world.isAddSerialVerUID()) {
            this.calculatedSerialVersionUID = this.myGen.getSUID();
            FieldGen fg = new FieldGen(26, BasicType.LONG, "serialVersionUID", this.getConstantPool());
            this.addField(fg);
            this.hasSerialVersionUIDField = true;
            this.serialVersionUIDRequiresInitialization = true;
            if (this.world.getLint().calculatingSerialVersionUID.isEnabled()) {
               this.world.getLint().calculatingSerialVersionUID.signal(new String[]{this.getClassName(), Long.toString(this.calculatedSerialVersionUID) + "L"}, (ISourceLocation)null, (ISourceLocation[])null);
            }
         }
      }

      methods = myType.getDeclaredMethods();

      for(i = 0; i < methods.length; ++i) {
         this.addMethodGen(new LazyMethodGen((BcelMethod)methods[i], this));
      }

      ResolvedMember[] fields = myType.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         this.fields.add((BcelField)fields[i]);
      }

   }

   public static boolean hasSerialVersionUIDField(ResolvedType type) {
      ResolvedMember[] fields = type.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         ResolvedMember field = fields[i];
         if (field.getName().equals("serialVersionUID") && Modifier.isStatic(field.getModifiers()) && field.getType().equals(UnresolvedType.LONG)) {
            return true;
         }
      }

      return false;
   }

   public String getInternalClassName() {
      return this.getConstantPool().getConstantString_CONSTANTClass(this.myGen.getClassNameIndex());
   }

   public String getInternalFileName() {
      String str = this.getInternalClassName();
      int index = str.lastIndexOf(47);
      return index == -1 ? this.getFileName() : str.substring(0, index + 1) + this.getFileName();
   }

   public String getPackageName() {
      if (this.packageName != null) {
         return this.packageName;
      } else {
         String str = this.getInternalClassName();
         int index = str.indexOf("<");
         if (index != -1) {
            str = str.substring(0, index);
         }

         index = str.lastIndexOf("/");
         return index == -1 ? "" : str.substring(0, index).replace('/', '.');
      }
   }

   public void addMethodGen(LazyMethodGen gen) {
      this.methodGens.add(gen);
      if (this.highestLineNumber < gen.highestLineNumber) {
         this.highestLineNumber = gen.highestLineNumber;
      }

   }

   public boolean removeMethodGen(LazyMethodGen gen) {
      return this.methodGens.remove(gen);
   }

   public void addMethodGen(LazyMethodGen gen, ISourceLocation sourceLocation) {
      this.addMethodGen(gen);
      if (!gen.getMethod().isPrivate()) {
         this.warnOnAddedMethod(gen.getMethod(), sourceLocation);
      }

   }

   public void errorOnAddedField(FieldGen field, ISourceLocation sourceLocation) {
      if (this.isSerializable && !this.hasSerialVersionUIDField) {
         this.getWorld().getLint().serialVersionUIDBroken.signal(new String[]{this.myType.getResolvedTypeX().getName(), field.getName()}, sourceLocation, (ISourceLocation[])null);
      }

   }

   public void warnOnAddedInterface(String name, ISourceLocation sourceLocation) {
      this.warnOnModifiedSerialVersionUID(sourceLocation, "added interface " + name);
   }

   public void warnOnAddedMethod(Method method, ISourceLocation sourceLocation) {
      this.warnOnModifiedSerialVersionUID(sourceLocation, "added non-private method " + method.getName());
   }

   public void warnOnAddedStaticInitializer(Shadow shadow, ISourceLocation sourceLocation) {
      if (!this.hasClinit) {
         this.warnOnModifiedSerialVersionUID(sourceLocation, "added static initializer");
      }

   }

   public void warnOnModifiedSerialVersionUID(ISourceLocation sourceLocation, String reason) {
      if (this.isSerializable && !this.hasSerialVersionUIDField) {
         this.getWorld().getLint().needsSerialVersionUIDField.signal(new String[]{this.myType.getResolvedTypeX().getName().toString(), reason}, sourceLocation, (ISourceLocation[])null);
      }

   }

   public World getWorld() {
      return this.world;
   }

   public List getMethodGens() {
      return this.methodGens;
   }

   public List getFieldGens() {
      return this.fields;
   }

   public boolean fieldExists(String name) {
      Iterator i$ = this.fields.iterator();

      BcelField f;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         f = (BcelField)i$.next();
      } while(!f.getName().equals(name));

      return true;
   }

   private void writeBack(BcelWorld world) {
      if (this.getConstantPool().getSize() > 32767) {
         this.reportClassTooBigProblem();
      } else {
         if (this.annotations.size() > 0) {
            Iterator i$ = this.annotations.iterator();

            while(i$.hasNext()) {
               AnnotationGen element = (AnnotationGen)i$.next();
               this.myGen.addAnnotation(element);
            }
         }

         if (!this.myGen.hasAttribute("com.bea.core.repackaged.aspectj.weaver.WeaverVersion")) {
            this.myGen.addAttribute(Utility.bcelAttribute(new AjAttribute.WeaverVersionInfo(), this.getConstantPool()));
         }

         if ((!world.isOverWeaving() || !this.myGen.hasAttribute("com.bea.core.repackaged.aspectj.weaver.WeaverState")) && this.myType != null && this.myType.getWeaverState() != null) {
            this.myGen.addAttribute(Utility.bcelAttribute(new AjAttribute.WeaverState(this.myType.getWeaverState()), this.getConstantPool()));
         }

         this.addAjcInitializers();
         boolean sourceDebugExtensionSupportSwitchedOn = false;
         if (sourceDebugExtensionSupportSwitchedOn) {
            this.calculateSourceDebugExtensionOffsets();
         }

         int len = this.methodGens.size();
         this.myGen.setMethods(Method.NoMethods);
         Iterator i$ = this.methodGens.iterator();

         while(i$.hasNext()) {
            LazyMethodGen gen = (LazyMethodGen)i$.next();
            if (!this.isEmptyClinit(gen)) {
               this.myGen.addMethod(gen.getMethod());
            }
         }

         len = this.fields.size();
         this.myGen.setFields(Field.NoFields);

         for(int i = 0; i < len; ++i) {
            BcelField gen = (BcelField)this.fields.get(i);
            this.myGen.addField(gen.getField(this.cp));
         }

         if (sourceDebugExtensionSupportSwitchedOn && this.inlinedFiles.size() != 0 && hasSourceDebugExtensionAttribute(this.myGen)) {
            world.showMessage(IMessage.WARNING, WeaverMessages.format("overwriteJSR45", this.getFileName()), (ISourceLocation)null, (ISourceLocation)null);
         }

         this.fixupGenericSignatureAttribute();
      }
   }

   private void fixupGenericSignatureAttribute() {
      if (this.getWorld() == null || this.getWorld().isInJava5Mode()) {
         if (this.regenerateGenericSignatureAttribute) {
            Signature sigAttr = null;
            if (this.myType != null) {
               sigAttr = (Signature)this.myGen.getAttribute("Signature");
            }

            boolean needAttribute = false;
            if (sigAttr != null) {
               needAttribute = true;
            }

            if (!needAttribute) {
               if (this.myType != null) {
                  ResolvedType[] interfaceRTXs = this.myType.getDeclaredInterfaces();
                  int i = 0;

                  label122:
                  while(true) {
                     ResolvedType interfaceType;
                     if (i >= interfaceRTXs.length) {
                        if (this.extraSuperInterfaces == null) {
                           break;
                        }

                        i = 0;

                        while(true) {
                           if (i >= this.extraSuperInterfaces.length) {
                              break label122;
                           }

                           interfaceType = this.extraSuperInterfaces[i];
                           if (interfaceType.isGenericType() || interfaceType.isParameterizedType()) {
                              needAttribute = true;
                           }

                           ++i;
                        }
                     }

                     interfaceType = interfaceRTXs[i];
                     if (interfaceType.isGenericType() || interfaceType.isParameterizedType()) {
                        needAttribute = true;
                     }

                     ++i;
                  }
               }

               ResolvedType superclassRTX;
               if (this.myType == null) {
                  superclassRTX = this.superclass;
                  if (superclassRTX != null && (superclassRTX.isGenericType() || superclassRTX.isParameterizedType())) {
                     needAttribute = true;
                  }
               } else {
                  superclassRTX = this.getSuperClass();
                  if (superclassRTX.isGenericType() || superclassRTX.isParameterizedType()) {
                     needAttribute = true;
                  }
               }
            }

            if (needAttribute) {
               StringBuffer signature = new StringBuffer();
               if (this.myType != null) {
                  TypeVariable[] tVars = this.myType.getTypeVariables();
                  if (tVars.length > 0) {
                     signature.append("<");

                     for(int i = 0; i < tVars.length; ++i) {
                        TypeVariable variable = tVars[i];
                        signature.append(variable.getSignatureForAttribute());
                     }

                     signature.append(">");
                  }
               }

               String supersig = this.getSuperClass().getSignatureForAttribute();
               signature.append(supersig);
               if (this.myType != null) {
                  ResolvedType[] interfaceRTXs = this.myType.getDeclaredInterfaces();

                  String s;
                  int i;
                  for(i = 0; i < interfaceRTXs.length; ++i) {
                     s = interfaceRTXs[i].getSignatureForAttribute();
                     signature.append(s);
                  }

                  if (this.extraSuperInterfaces != null) {
                     for(i = 0; i < this.extraSuperInterfaces.length; ++i) {
                        s = this.extraSuperInterfaces[i].getSignatureForAttribute();
                        signature.append(s);
                     }
                  }
               }

               if (sigAttr != null) {
                  this.myGen.removeAttribute(sigAttr);
               }

               this.myGen.addAttribute(this.createSignatureAttribute(signature.toString()));
            }

         }
      }
   }

   private Signature createSignatureAttribute(String signature) {
      int nameIndex = this.cp.addUtf8("Signature");
      int sigIndex = this.cp.addUtf8(signature);
      return new Signature(nameIndex, 2, sigIndex, this.cp);
   }

   private void reportClassTooBigProblem() {
      this.myGen = new ClassGen(this.myGen.getClassName(), this.myGen.getSuperclassName(), this.myGen.getFileName(), this.myGen.getModifiers(), this.myGen.getInterfaceNames());
      this.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("classTooBig", this.getClassName()), new SourceLocation(new File(this.myGen.getFileName()), 0), (ISourceLocation)null);
   }

   private static boolean hasSourceDebugExtensionAttribute(ClassGen gen) {
      return gen.hasAttribute("SourceDebugExtension");
   }

   public JavaClass getJavaClass(BcelWorld world) {
      this.writeBack(world);
      return this.myGen.getJavaClass();
   }

   public byte[] getJavaClassBytesIncludingReweavable(BcelWorld world) {
      this.writeBack(world);
      byte[] wovenClassFileData = this.myGen.getJavaClass().getBytes();
      if (this.myGen.getMajor() == 50 && world.shouldGenerateStackMaps() || this.myGen.getMajor() > 50) {
         if (!AsmDetector.isAsmAround) {
            throw new BCException("Unable to find Asm for stackmap generation (Looking for 'aj.org.objectweb.asm.ClassReader'). Stackmap generation for woven code is required to avoid verify errors on a Java 1.7 or higher runtime");
         }

         wovenClassFileData = StackMapAdder.addStackMaps(world, wovenClassFileData);
      }

      WeaverStateInfo wsi = this.myType.getWeaverState();
      return wsi != null && wsi.isReweavable() ? wsi.replaceKeyWithDiff(wovenClassFileData) : wovenClassFileData;
   }

   public void addGeneratedInner(LazyClassGen newClass) {
      this.classGens.add(newClass);
   }

   public void addInterface(ResolvedType newInterface, ISourceLocation sourceLocation) {
      this.regenerateGenericSignatureAttribute = true;
      if (this.extraSuperInterfaces == null) {
         this.extraSuperInterfaces = new ResolvedType[1];
         this.extraSuperInterfaces[0] = newInterface;
      } else {
         ResolvedType[] x = new ResolvedType[this.extraSuperInterfaces.length + 1];
         System.arraycopy(this.extraSuperInterfaces, 0, x, 1, this.extraSuperInterfaces.length);
         x[0] = newInterface;
         this.extraSuperInterfaces = x;
      }

      this.myGen.addInterface(newInterface.getRawName());
      if (!newInterface.equals(UnresolvedType.SERIALIZABLE)) {
         this.warnOnAddedInterface(newInterface.getName(), sourceLocation);
      }

   }

   public void setSuperClass(ResolvedType newSuperclass) {
      this.regenerateGenericSignatureAttribute = true;
      this.superclass = (ResolvedType)newSuperclass;
      if (((ResolvedType)newSuperclass).getGenericType() != null) {
         newSuperclass = ((ResolvedType)newSuperclass).getGenericType();
      }

      this.myGen.setSuperclassName(((ResolvedType)newSuperclass).getName());
   }

   public ResolvedType getSuperClass() {
      return this.superclass != null ? this.superclass : this.myType.getSuperclass();
   }

   public String[] getInterfaceNames() {
      return this.myGen.getInterfaceNames();
   }

   private List getClassGens() {
      List ret = new ArrayList();
      ret.add(this);
      ret.addAll(this.classGens);
      return ret;
   }

   public List getChildClasses(BcelWorld world) {
      if (this.classGens.isEmpty()) {
         return Collections.emptyList();
      } else {
         List ret = new ArrayList();
         Iterator i$ = this.classGens.iterator();

         while(i$.hasNext()) {
            LazyClassGen clazz = (LazyClassGen)i$.next();
            byte[] bytes = clazz.getJavaClass(world).getBytes();
            String name = clazz.getName();
            int index = name.lastIndexOf(36);
            name = name.substring(index + 1);
            ret.add(new UnwovenClassFile.ChildClass(name, bytes));
         }

         return ret;
      }
   }

   public String toString() {
      return this.toShortString();
   }

   public String toShortString() {
      String s = com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.accessToString(this.myGen.getModifiers(), true);
      if (!s.equals("")) {
         s = s + " ";
      }

      s = s + com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.classOrInterface(this.myGen.getModifiers());
      s = s + " ";
      s = s + this.myGen.getClassName();
      return s;
   }

   public String toLongString() {
      ByteArrayOutputStream s = new ByteArrayOutputStream();
      this.print(new PrintStream(s));
      return new String(s.toByteArray());
   }

   public void print() {
      this.print(System.out);
   }

   public void print(PrintStream out) {
      List classGens = this.getClassGens();
      Iterator iter = classGens.iterator();

      while(iter.hasNext()) {
         LazyClassGen element = (LazyClassGen)iter.next();
         element.printOne(out);
         if (iter.hasNext()) {
            out.println();
         }
      }

   }

   private void printOne(PrintStream out) {
      out.print(this.toShortString());
      out.print(" extends ");
      out.print(com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.compactClassName(this.myGen.getSuperclassName(), false));
      int size = this.myGen.getInterfaces().length;
      if (size > 0) {
         out.print(" implements ");

         for(int i = 0; i < size; ++i) {
            out.print(this.myGen.getInterfaceNames()[i]);
            if (i < size - 1) {
               out.print(", ");
            }
         }
      }

      out.print(":");
      out.println();
      if (this.myType != null) {
         this.myType.printWackyStuff(out);
      }

      Field[] fields = this.myGen.getFields();
      int i = 0;

      for(int len = fields.length; i < len; ++i) {
         out.print("  ");
         out.println(fields[i]);
      }

      List methodGens = this.getMethodGens();
      Iterator iter = methodGens.iterator();

      while(iter.hasNext()) {
         LazyMethodGen gen = (LazyMethodGen)iter.next();
         if (!this.isEmptyClinit(gen)) {
            gen.print(out, this.myType != null ? this.myType.getWeaverVersionAttribute() : AjAttribute.WeaverVersionInfo.UNKNOWN);
            if (iter.hasNext()) {
               out.println();
            }
         }
      }

      out.println("end " + this.toShortString());
   }

   private boolean isEmptyClinit(LazyMethodGen gen) {
      if (!gen.getName().equals("<clinit>")) {
         return false;
      } else {
         for(InstructionHandle start = gen.getBody().getStart(); start != null; start = start.getNext()) {
            if (!Range.isRangeHandle(start) && start.getInstruction().opcode != 177) {
               return false;
            }
         }

         return true;
      }
   }

   public ConstantPool getConstantPool() {
      return this.cp;
   }

   public String getName() {
      return this.myGen.getClassName();
   }

   public boolean isWoven() {
      return this.myType.getWeaverState() != null;
   }

   public boolean isReweavable() {
      return this.myType.getWeaverState() == null ? true : this.myType.getWeaverState().isReweavable();
   }

   public Set getAspectsAffectingType() {
      return this.myType.getWeaverState() == null ? null : this.myType.getWeaverState().getAspectsAffectingType();
   }

   public WeaverStateInfo getOrCreateWeaverStateInfo(boolean inReweavableMode) {
      WeaverStateInfo ret = this.myType.getWeaverState();
      if (ret != null) {
         return ret;
      } else {
         ret = new WeaverStateInfo(inReweavableMode);
         this.myType.setWeaverState(ret);
         return ret;
      }
   }

   public InstructionFactory getFactory() {
      return this.fact;
   }

   public LazyMethodGen getStaticInitializer() {
      Iterator i$ = this.methodGens.iterator();

      LazyMethodGen gen;
      do {
         if (!i$.hasNext()) {
            LazyMethodGen clinit = new LazyMethodGen(8, Type.VOID, "<clinit>", new Type[0], NO_STRINGS, this);
            clinit.getBody().insert(InstructionConstants.RETURN);
            this.methodGens.add(clinit);
            return clinit;
         }

         gen = (LazyMethodGen)i$.next();
      } while(!gen.getName().equals("<clinit>"));

      return gen;
   }

   public LazyMethodGen getAjcPreClinit() {
      if (this.isInterface()) {
         throw new IllegalStateException();
      } else {
         Iterator i$ = this.methodGens.iterator();

         LazyMethodGen methodGen;
         do {
            if (!i$.hasNext()) {
               LazyMethodGen ajcPreClinit = new LazyMethodGen(10, Type.VOID, "ajc$preClinit", Type.NO_ARGS, NO_STRINGS, this);
               ajcPreClinit.getBody().insert(InstructionConstants.RETURN);
               this.methodGens.add(ajcPreClinit);
               this.getStaticInitializer().getBody().insert(Utility.createInvoke(this.fact, ajcPreClinit));
               return ajcPreClinit;
            }

            methodGen = (LazyMethodGen)i$.next();
         } while(!methodGen.getName().equals("ajc$preClinit"));

         return methodGen;
      }
   }

   public LazyMethodGen createExtendedAjcPreClinit(LazyMethodGen previousPreClinit, int i) {
      LazyMethodGen ajcPreClinit = new LazyMethodGen(10, Type.VOID, "ajc$preClinit" + i, Type.NO_ARGS, NO_STRINGS, this);
      ajcPreClinit.getBody().insert(InstructionConstants.RETURN);
      this.methodGens.add(ajcPreClinit);
      previousPreClinit.getBody().insert(Utility.createInvoke(this.fact, ajcPreClinit));
      return ajcPreClinit;
   }

   public Field getTjpField(BcelShadow shadow, boolean isEnclosingJp) {
      Field tjpField = (Field)this.tjpFields.get(shadow);
      if (tjpField != null) {
         return tjpField;
      } else {
         int modifiers = 24;
         LazyMethodGen encMethod = shadow.getEnclosingMethod();
         boolean shadowIsInAroundAdvice = false;
         if (encMethod != null && encMethod.getName().startsWith("ajc$around")) {
            shadowIsInAroundAdvice = true;
         }

         if (!this.getType().isInterface() && !shadowIsInAroundAdvice) {
            modifiers |= 2;
         } else {
            modifiers |= 1;
         }

         ObjectType jpType = null;
         if (this.world.isTargettingAspectJRuntime12()) {
            jpType = staticTjpType;
         } else {
            jpType = isEnclosingJp ? enclosingStaticTjpType : staticTjpType;
         }

         if (this.tjpFieldsCounter == -1) {
            if (!this.world.isOverWeaving()) {
               this.tjpFieldsCounter = 0;
            } else {
               List existingFields = this.getFieldGens();
               if (existingFields == null) {
                  this.tjpFieldsCounter = 0;
               } else {
                  BcelField lastField = null;
                  Iterator i$ = existingFields.iterator();

                  while(i$.hasNext()) {
                     BcelField field = (BcelField)i$.next();
                     if (field.getName().startsWith("ajc$tjp_")) {
                        lastField = field;
                     }
                  }

                  if (lastField == null) {
                     this.tjpFieldsCounter = 0;
                  } else {
                     this.tjpFieldsCounter = Integer.parseInt(lastField.getName().substring(8)) + 1;
                  }
               }
            }
         }

         if (!this.isInterface() && this.world.isTransientTjpFields()) {
            modifiers |= 128;
         }

         FieldGen fGen = new FieldGen(modifiers, jpType, "ajc$tjp_" + this.tjpFieldsCounter++, this.getConstantPool());
         this.addField(fGen);
         tjpField = fGen.getField();
         this.tjpFields.put(shadow, tjpField);
         return tjpField;
      }
   }

   public Field getAnnotationCachingField(BcelShadow shadow, ResolvedType toType, boolean isWithin) {
      CacheKey cacheKey = new CacheKey(shadow, toType, isWithin);
      Field field = (Field)this.annotationCachingFieldCache.get(cacheKey);
      if (field == null) {
         StringBuilder sb = new StringBuilder();
         sb.append("ajc$anno$");
         sb.append(this.annoFieldsCounter++);
         FieldGen annotationCacheField = new FieldGen(10, typeForAnnotation, sb.toString(), this.cp);
         this.addField(annotationCacheField);
         field = annotationCacheField.getField();
         this.annotationCachingFieldCache.put(cacheKey, field);
      }

      return field;
   }

   private void addAjcInitializers() {
      if (this.tjpFields.size() != 0 || this.serialVersionUIDRequiresInitialization) {
         InstructionList[] il = null;
         if (this.tjpFields.size() > 0) {
            il = this.initializeAllTjps();
         }

         if (this.serialVersionUIDRequiresInitialization) {
            InstructionList[] ilSVUID = new InstructionList[]{new InstructionList()};
            ilSVUID[0].append(InstructionFactory.PUSH(this.getConstantPool(), this.calculatedSerialVersionUID));
            ilSVUID[0].append((Instruction)this.getFactory().createFieldAccess(this.getClassName(), "serialVersionUID", BasicType.LONG, (short)179));
            if (il == null) {
               il = ilSVUID;
            } else {
               InstructionList[] newIl = new InstructionList[il.length + ilSVUID.length];
               System.arraycopy(il, 0, newIl, 0, il.length);
               System.arraycopy(ilSVUID, 0, newIl, il.length, ilSVUID.length);
               il = newIl;
            }
         }

         LazyMethodGen nextMethod = null;
         LazyMethodGen prevMethod;
         if (this.isInterface()) {
            prevMethod = this.getStaticInitializer();
         } else {
            prevMethod = this.getAjcPreClinit();
         }

         for(int counter = 1; counter <= il.length; ++counter) {
            if (il.length > counter) {
               nextMethod = this.createExtendedAjcPreClinit(prevMethod, counter);
            }

            prevMethod.getBody().insert(il[counter - 1]);
            prevMethod = nextMethod;
         }

      }
   }

   private InstructionList initInstructionList() {
      InstructionList list = new InstructionList();
      InstructionFactory fact = this.getFactory();
      list.append(fact.createNew(factoryType));
      list.append(InstructionFactory.createDup(1));
      list.append(InstructionFactory.PUSH(this.getConstantPool(), this.getFileName()));
      list.append(fact.PUSHCLASS(this.cp, this.myGen.getClassName()));
      list.append((Instruction)fact.createInvoke(factoryType.getClassName(), "<init>", Type.VOID, new Type[]{Type.STRING, classType}, (short)183));
      list.append((Instruction)InstructionFactory.createStore(factoryType, 0));
      return list;
   }

   private InstructionList[] initializeAllTjps() {
      Vector lists = new Vector();
      InstructionList list = this.initInstructionList();
      lists.add(list);
      List entries = new ArrayList(this.tjpFields.entrySet());
      Collections.sort(entries, new Comparator() {
         public int compare(Map.Entry a, Map.Entry b) {
            return ((Field)a.getValue()).getName().compareTo(((Field)b.getValue()).getName());
         }
      });
      long estimatedSize = 0L;
      Iterator i = entries.iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         if (estimatedSize > 65536L) {
            estimatedSize = 0L;
            list = this.initInstructionList();
            lists.add(list);
         }

         estimatedSize += (long)((Field)entry.getValue()).getSignature().getBytes().length;
         this.initializeTjp(this.fact, list, (Field)entry.getValue(), (BcelShadow)entry.getKey());
      }

      InstructionList[] listArrayModel = new InstructionList[1];
      return (InstructionList[])lists.toArray(listArrayModel);
   }

   private void initializeTjp(InstructionFactory fact, InstructionList list, Field field, BcelShadow shadow) {
      boolean fastSJP = false;
      boolean isFastSJPAvailable = shadow.getWorld().isTargettingRuntime1_6_10() && !enclosingStaticTjpType.equals(field.getType());
      Member sig = shadow.getSignature();
      list.append((Instruction)InstructionFactory.createLoad(factoryType, 0));
      list.append(InstructionFactory.PUSH(this.getConstantPool(), shadow.getKind().getName()));
      if (this.world.isTargettingAspectJRuntime12() || !isFastSJPAvailable || !sig.getKind().equals(Member.METHOD)) {
         list.append((Instruction)InstructionFactory.createLoad(factoryType, 0));
      }

      String signatureMakerName = SignatureUtils.getSignatureMakerName(sig);
      ObjectType signatureType = new ObjectType(SignatureUtils.getSignatureType(sig));
      UnresolvedType[] exceptionTypes = null;
      if (this.world.isTargettingAspectJRuntime12()) {
         list.append(InstructionFactory.PUSH(this.cp, SignatureUtils.getSignatureString(sig, shadow.getWorld())));
         list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY1, (short)182));
      } else {
         BcelWorld w;
         if (sig.getKind().equals(Member.METHOD)) {
            w = shadow.getWorld();
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getModifiers(w))));
            list.append(InstructionFactory.PUSH(this.cp, sig.getName()));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterTypes())));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterNames(w))));
            exceptionTypes = sig.getExceptions(w);
            if (isFastSJPAvailable && exceptionTypes.length == 0) {
               fastSJP = true;
            } else {
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(exceptionTypes)));
            }

            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getReturnType())));
            if (isFastSJPAvailable) {
               fastSJP = true;
            } else {
               list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY7, (short)182));
            }
         } else if (sig.getKind().equals(Member.MONITORENTER)) {
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY1, (short)182));
         } else if (sig.getKind().equals(Member.MONITOREXIT)) {
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY1, (short)182));
         } else if (sig.getKind().equals(Member.HANDLER)) {
            w = shadow.getWorld();
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterTypes())));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterNames(w))));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY3, (short)182));
         } else if (sig.getKind().equals(Member.CONSTRUCTOR)) {
            w = shadow.getWorld();
            if (w.isJoinpointArrayConstructionEnabled() && sig.getDeclaringType().isArray()) {
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(1)));
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterTypes())));
               list.append(InstructionFactory.PUSH(this.cp, ""));
               list.append(InstructionFactory.PUSH(this.cp, ""));
               list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY5, (short)182));
            } else {
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getModifiers(w))));
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterTypes())));
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterNames(w))));
               list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getExceptions(w))));
               list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY5, (short)182));
            }
         } else if (sig.getKind().equals(Member.FIELD)) {
            w = shadow.getWorld();
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getModifiers(w))));
            list.append(InstructionFactory.PUSH(this.cp, sig.getName()));
            UnresolvedType dType = sig.getDeclaringType();
            if (((UnresolvedType)dType).getTypekind() == UnresolvedType.TypeKind.PARAMETERIZED || ((UnresolvedType)dType).getTypekind() == UnresolvedType.TypeKind.GENERIC) {
               dType = sig.getDeclaringType().resolve(this.world).getGenericType();
            }

            list.append(InstructionFactory.PUSH(this.cp, this.makeString((UnresolvedType)dType)));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getReturnType())));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY4, (short)182));
         } else if (sig.getKind().equals(Member.ADVICE)) {
            w = shadow.getWorld();
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getModifiers(w))));
            list.append(InstructionFactory.PUSH(this.cp, sig.getName()));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterTypes())));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getParameterNames(w))));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getExceptions(w))));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getReturnType())));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, new Type[]{Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING}, (short)182));
         } else if (sig.getKind().equals(Member.STATIC_INITIALIZATION)) {
            w = shadow.getWorld();
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getModifiers(w))));
            list.append(InstructionFactory.PUSH(this.cp, this.makeString(sig.getDeclaringType())));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY2, (short)182));
         } else {
            list.append(InstructionFactory.PUSH(this.cp, SignatureUtils.getSignatureString(sig, shadow.getWorld())));
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), signatureMakerName, signatureType, Type.STRINGARRAY1, (short)182));
         }
      }

      list.append(Utility.createConstant(fact, shadow.getSourceLine()));
      if (this.world.isTargettingAspectJRuntime12()) {
         list.append((Instruction)fact.createInvoke(factoryType.getClassName(), "makeSJP", staticTjpType, new Type[]{Type.STRING, sigType, Type.INT}, (short)182));
         list.append((Instruction)fact.createFieldAccess(this.getClassName(), field.getName(), staticTjpType, (short)179));
      } else {
         String factoryMethod;
         if (staticTjpType.equals(field.getType())) {
            factoryMethod = "makeSJP";
         } else {
            if (!enclosingStaticTjpType.equals(field.getType())) {
               throw new Error("should not happen");
            }

            factoryMethod = "makeESJP";
         }

         if (fastSJP) {
            if (exceptionTypes != null && exceptionTypes.length != 0) {
               list.append((Instruction)fact.createInvoke(factoryType.getClassName(), factoryMethod, field.getType(), ARRAY_8STRING_INT, (short)182));
            } else {
               list.append((Instruction)fact.createInvoke(factoryType.getClassName(), factoryMethod, field.getType(), ARRAY_7STRING_INT, (short)182));
            }
         } else {
            list.append((Instruction)fact.createInvoke(factoryType.getClassName(), factoryMethod, field.getType(), new Type[]{Type.STRING, sigType, Type.INT}, (short)182));
         }

         list.append((Instruction)fact.createFieldAccess(this.getClassName(), field.getName(), field.getType(), (short)179));
      }

   }

   protected String makeString(int i) {
      return Integer.toString(i, 16);
   }

   protected String makeString(UnresolvedType t) {
      if (t.isArray()) {
         return t.getSignature().replace('/', '.');
      } else {
         return t.isParameterizedType() ? t.getRawType().getName() : t.getName();
      }
   }

   protected String makeString(UnresolvedType[] types) {
      if (types == null) {
         return "";
      } else {
         StringBuilder buf = new StringBuilder();
         int i = 0;

         for(int len = types.length; i < len; ++i) {
            if (i > 0) {
               buf.append(':');
            }

            buf.append(this.makeString(types[i]));
         }

         return buf.toString();
      }
   }

   protected String makeString(String[] names) {
      if (names == null) {
         return "";
      } else {
         StringBuilder buf = new StringBuilder();
         int i = 0;

         for(int len = names.length; i < len; ++i) {
            if (i > 0) {
               buf.append(':');
            }

            buf.append(names[i]);
         }

         return buf.toString();
      }
   }

   public ResolvedType getType() {
      return this.myType == null ? null : this.myType.getResolvedTypeX();
   }

   public BcelObjectType getBcelObjectType() {
      return this.myType;
   }

   public String getFileName() {
      return this.myGen.getFileName();
   }

   private void addField(FieldGen field) {
      this.makeSyntheticAndTransientIfNeeded(field);
      BcelField bcelField = null;
      if (this.getBcelObjectType() != null) {
         bcelField = new BcelField(this.getBcelObjectType(), field.getField());
      } else {
         bcelField = new BcelField(this.getName(), field.getField(), this.world);
      }

      this.fields.add(bcelField);
   }

   private void makeSyntheticAndTransientIfNeeded(FieldGen field) {
      if (field.getName().startsWith("ajc$") && !field.getName().startsWith("ajc$interField$") && !field.getName().startsWith("ajc$instance$")) {
         if (!field.isStatic()) {
            field.setModifiers(field.getModifiers() | 128);
         }

         if (this.getWorld().isInJava5Mode()) {
            field.setModifiers(field.getModifiers() | 4096);
         }

         if (!this.hasSyntheticAttribute(field.getAttributes())) {
            ConstantPool cpg = this.myGen.getConstantPool();
            int index = cpg.addUtf8("Synthetic");
            Attribute synthetic = new Synthetic(index, 0, new byte[0], cpg);
            field.addAttribute(synthetic);
         }
      }

   }

   private boolean hasSyntheticAttribute(List attributes) {
      for(int i = 0; i < attributes.size(); ++i) {
         if (((Attribute)attributes.get(i)).getName().equals("Synthetic")) {
            return true;
         }
      }

      return false;
   }

   public void addField(FieldGen field, ISourceLocation sourceLocation) {
      this.addField(field);
      if (!field.isPrivate() || !field.isStatic() && !field.isTransient()) {
         this.errorOnAddedField(field, sourceLocation);
      }

   }

   public String getClassName() {
      return this.myGen.getClassName();
   }

   public boolean isInterface() {
      return this.myGen.isInterface();
   }

   public boolean isAbstract() {
      return this.myGen.isAbstract();
   }

   public LazyMethodGen getLazyMethodGen(Member m) {
      return this.getLazyMethodGen(m.getName(), m.getSignature(), false);
   }

   public LazyMethodGen getLazyMethodGen(String name, String signature) {
      return this.getLazyMethodGen(name, signature, false);
   }

   public LazyMethodGen getLazyMethodGen(String name, String signature, boolean allowMissing) {
      Iterator i$ = this.methodGens.iterator();

      LazyMethodGen gen;
      do {
         if (!i$.hasNext()) {
            if (!allowMissing) {
               throw new BCException("Class " + this.getName() + " does not have a method " + name + " with signature " + signature);
            }

            return null;
         }

         gen = (LazyMethodGen)i$.next();
      } while(!gen.getName().equals(name) || !gen.getSignature().equals(signature));

      return gen;
   }

   public void forcePublic() {
      this.myGen.setModifiers(Utility.makePublic(this.myGen.getModifiers()));
   }

   public boolean hasAnnotation(UnresolvedType t) {
      AnnotationGen[] agens = this.myGen.getAnnotations();
      if (agens == null) {
         return false;
      } else {
         for(int i = 0; i < agens.length; ++i) {
            AnnotationGen gen = agens[i];
            if (t.equals(UnresolvedType.forSignature(gen.getTypeSignature()))) {
               return true;
            }
         }

         return false;
      }
   }

   public void addAnnotation(AnnotationGen a) {
      if (!this.hasAnnotation(UnresolvedType.forSignature(a.getTypeSignature()))) {
         this.annotations.add(new AnnotationGen(a, this.getConstantPool(), true));
      }

   }

   public void addAttribute(AjAttribute attribute) {
      this.myGen.addAttribute(Utility.bcelAttribute(attribute, this.getConstantPool()));
   }

   public void addAttribute(Attribute attribute) {
      this.myGen.addAttribute(attribute);
   }

   public Collection getAttributes() {
      return this.myGen.getAttributes();
   }

   private boolean implementsSerializable(ResolvedType aType) {
      if (aType.getSignature().equals(UnresolvedType.SERIALIZABLE.getSignature())) {
         return true;
      } else {
         ResolvedType[] interfaces = aType.getDeclaredInterfaces();

         for(int i = 0; i < interfaces.length; ++i) {
            if (!interfaces[i].isMissing() && this.implementsSerializable(interfaces[i])) {
               return true;
            }
         }

         ResolvedType superType = aType.getSuperclass();
         if (superType != null && !superType.isMissing()) {
            return this.implementsSerializable(superType);
         } else {
            return false;
         }
      }
   }

   public boolean isAtLeastJava5() {
      return this.myGen.getMajor() >= 49;
   }

   public String allocateField(String prefix) {
      int highestAllocated = -1;
      List fs = this.getFieldGens();
      Iterator i$ = fs.iterator();

      while(i$.hasNext()) {
         BcelField field = (BcelField)i$.next();
         if (field.getName().startsWith(prefix)) {
            try {
               int num = Integer.parseInt(field.getName().substring(prefix.length()));
               if (num > highestAllocated) {
                  highestAllocated = num;
               }
            } catch (NumberFormatException var7) {
            }
         }
      }

      return prefix + Integer.toString(highestAllocated + 1);
   }

   static {
      ARRAY_7STRING_INT = new Type[]{Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.INT};
      ARRAY_8STRING_INT = new Type[]{Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.STRING, Type.INT};
   }

   static class CacheKey {
      private Object key;
      private ResolvedType annotationType;

      CacheKey(BcelShadow shadow, ResolvedType annotationType, boolean isWithin) {
         this.key = isWithin ? shadow : shadow.toString();
         this.annotationType = annotationType;
      }

      public int hashCode() {
         return this.key.hashCode() * 37 + this.annotationType.hashCode();
      }

      public boolean equals(Object other) {
         if (!(other instanceof CacheKey)) {
            return false;
         } else {
            CacheKey oCacheKey = (CacheKey)other;
            return this.key.equals(oCacheKey.key) && this.annotationType.equals(oCacheKey.annotationType);
         }
      }
   }

   static class InlinedSourceFileInfo {
      int highestLineNumber;
      int offset;

      InlinedSourceFileInfo(int highestLineNumber) {
         this.highestLineNumber = highestLineNumber;
      }
   }
}
