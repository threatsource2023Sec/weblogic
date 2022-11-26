package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Synthetic;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.BranchHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ClassGenException;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.CodeExceptionGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionBranch;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionSelect;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionTargeter;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LineNumberTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LocalVariableTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.MethodGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Tag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.TargetLostException;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.MemberImpl;
import com.bea.core.repackaged.aspectj.weaver.NameMangler;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.tools.Traceable;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public final class LazyMethodGen implements Traceable {
   private static final AnnotationAJ[] NO_ANNOTATIONAJ = new AnnotationAJ[0];
   private int modifiers;
   private Type returnType;
   private final String name;
   private Type[] argumentTypes;
   private String[] declaredExceptions;
   private InstructionList body;
   private List attributes;
   private List newAnnotations;
   private List annotationsForRemoval;
   private AnnotationAJ[][] newParameterAnnotations;
   private final LazyClassGen enclosingClass;
   private BcelMethod memberView;
   private AjAttribute.EffectiveSignatureAttribute effectiveSignature;
   int highestLineNumber = 0;
   boolean wasPackedOptimally = false;
   private Method savedMethod = null;
   private final boolean originalMethodHasLocalVariableTable;
   String fromFilename = null;
   private int maxLocals;
   private boolean canInline = true;
   private boolean isSynthetic = false;
   List matchedShadows;
   public ResolvedType definingType = null;

   public LazyMethodGen(int modifiers, Type returnType, String name, Type[] paramTypes, String[] declaredExceptions, LazyClassGen enclosingClass) {
      this.memberView = null;
      this.modifiers = modifiers;
      this.returnType = returnType;
      this.name = name;
      this.argumentTypes = paramTypes;
      this.declaredExceptions = declaredExceptions;
      if (!Modifier.isAbstract(modifiers)) {
         this.body = new InstructionList();
         this.setMaxLocals(this.calculateMaxLocals());
      } else {
         this.body = null;
      }

      this.attributes = new ArrayList();
      this.enclosingClass = enclosingClass;
      this.assertGoodBody();
      this.originalMethodHasLocalVariableTable = true;
      if (this.memberView != null && this.isAdviceMethod() && enclosingClass.getType().isAnnotationStyleAspect()) {
         this.canInline = false;
      }

   }

   private int calculateMaxLocals() {
      int ret = Modifier.isStatic(this.modifiers) ? 0 : 1;
      Type[] arr$ = this.argumentTypes;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Type type = arr$[i$];
         ret += type.getSize();
      }

      return ret;
   }

   public LazyMethodGen(Method m, LazyClassGen enclosingClass) {
      this.savedMethod = m;
      this.enclosingClass = enclosingClass;
      if (!m.isAbstract() && !m.isNative() && m.getCode() == null) {
         throw new RuntimeException("bad non-abstract method with no code: " + m + " on " + enclosingClass);
      } else if ((m.isAbstract() || m.isNative()) && m.getCode() != null) {
         throw new RuntimeException("bad abstract method with code: " + m + " on " + enclosingClass);
      } else {
         this.memberView = new BcelMethod(enclosingClass.getBcelObjectType(), m);
         this.originalMethodHasLocalVariableTable = this.savedMethod.getLocalVariableTable() != null;
         this.modifiers = m.getModifiers();
         this.name = m.getName();
         if (this.memberView != null && this.isAdviceMethod() && enclosingClass.getType().isAnnotationStyleAspect()) {
            this.canInline = false;
         }

      }
   }

   private boolean isAbstractOrNative(int modifiers) {
      return Modifier.isAbstract(modifiers) || Modifier.isNative(modifiers);
   }

   public LazyMethodGen(BcelMethod m, LazyClassGen enclosingClass) {
      this.savedMethod = m.getMethod();
      this.enclosingClass = enclosingClass;
      if (!this.isAbstractOrNative(m.getModifiers()) && this.savedMethod.getCode() == null) {
         throw new RuntimeException("bad non-abstract method with no code: " + m + " on " + enclosingClass);
      } else if (this.isAbstractOrNative(m.getModifiers()) && this.savedMethod.getCode() != null) {
         throw new RuntimeException("bad abstract method with code: " + m + " on " + enclosingClass);
      } else {
         this.memberView = m;
         this.modifiers = this.savedMethod.getModifiers();
         this.name = m.getName();
         this.originalMethodHasLocalVariableTable = this.savedMethod.getLocalVariableTable() != null;
         if (this.memberView != null && this.isAdviceMethod() && enclosingClass.getType().isAnnotationStyleAspect()) {
            this.canInline = false;
         }

      }
   }

   public boolean hasDeclaredLineNumberInfo() {
      return this.memberView != null && this.memberView.hasDeclarationLineNumberInfo();
   }

   public int getDeclarationLineNumber() {
      return this.hasDeclaredLineNumberInfo() ? this.memberView.getDeclarationLineNumber() : -1;
   }

   public int getDeclarationOffset() {
      return this.hasDeclaredLineNumberInfo() ? this.memberView.getDeclarationOffset() : 0;
   }

   public void addAnnotation(AnnotationAJ ax) {
      this.initialize();
      if (this.memberView == null) {
         if (this.newAnnotations == null) {
            this.newAnnotations = new ArrayList();
         }

         this.newAnnotations.add(ax);
      } else {
         this.memberView.addAnnotation(ax);
      }

   }

   public void removeAnnotation(ResolvedType annotationType) {
      this.initialize();
      if (this.memberView == null) {
         if (this.annotationsForRemoval == null) {
            this.annotationsForRemoval = new ArrayList();
         }

         this.annotationsForRemoval.add(annotationType);
      } else {
         this.memberView.removeAnnotation(annotationType);
      }

   }

   public void addParameterAnnotation(int parameterNumber, AnnotationAJ anno) {
      this.initialize();
      if (this.memberView == null) {
         if (this.newParameterAnnotations == null) {
            int pcount = this.getArgumentTypes().length;
            this.newParameterAnnotations = new AnnotationAJ[pcount][];

            for(int i = 0; i < pcount; ++i) {
               if (i == parameterNumber) {
                  this.newParameterAnnotations[i] = new AnnotationAJ[1];
                  this.newParameterAnnotations[i][0] = anno;
               } else {
                  this.newParameterAnnotations[i] = NO_ANNOTATIONAJ;
               }
            }
         } else {
            AnnotationAJ[] currentAnnoArray = this.newParameterAnnotations[parameterNumber];
            AnnotationAJ[] newAnnoArray = new AnnotationAJ[currentAnnoArray.length + 1];
            System.arraycopy(currentAnnoArray, 0, newAnnoArray, 0, currentAnnoArray.length);
            newAnnoArray[currentAnnoArray.length] = anno;
            this.newParameterAnnotations[parameterNumber] = newAnnoArray;
         }
      } else {
         this.memberView.addParameterAnnotation(parameterNumber, anno);
      }

   }

   public ResolvedType[] getAnnotationTypes() {
      this.initialize();
      if (this.memberView == null && this.newAnnotations != null && this.newAnnotations.size() != 0) {
         ResolvedType[] annotationTypes = new ResolvedType[this.newAnnotations.size()];
         int a = 0;

         for(int len = this.newAnnotations.size(); a < len; ++a) {
            annotationTypes[a] = ((AnnotationAJ)this.newAnnotations.get(a)).getType();
         }

         return annotationTypes;
      } else {
         return null;
      }
   }

   public AnnotationAJ[] getAnnotations() {
      this.initialize();
      return this.memberView == null && this.newAnnotations != null && this.newAnnotations.size() != 0 ? (AnnotationAJ[])this.newAnnotations.toArray(new AnnotationAJ[this.newAnnotations.size()]) : null;
   }

   public boolean hasAnnotation(UnresolvedType annotationType) {
      this.initialize();
      if (this.memberView != null) {
         return this.memberView.hasAnnotation(annotationType);
      } else {
         Iterator i$;
         if (this.annotationsForRemoval != null) {
            i$ = this.annotationsForRemoval.iterator();

            while(i$.hasNext()) {
               ResolvedType at = (ResolvedType)i$.next();
               if (at.equals(annotationType)) {
                  return false;
               }
            }
         }

         if (this.newAnnotations != null) {
            i$ = this.newAnnotations.iterator();

            while(i$.hasNext()) {
               AnnotationAJ annotation = (AnnotationAJ)i$.next();
               if (annotation.getTypeSignature().equals(annotationType.getSignature())) {
                  return true;
               }
            }
         }

         this.memberView = new BcelMethod(this.getEnclosingClass().getBcelObjectType(), this.getMethod());
         return this.memberView.hasAnnotation(annotationType);
      }
   }

   private void initialize() {
      if (this.returnType == null) {
         MethodGen gen = new MethodGen(this.savedMethod, this.enclosingClass.getName(), this.enclosingClass.getConstantPool(), true);
         this.returnType = gen.getReturnType();
         this.argumentTypes = gen.getArgumentTypes();
         this.declaredExceptions = gen.getExceptions();
         this.attributes = gen.getAttributes();
         this.maxLocals = gen.getMaxLocals();
         if (!gen.isAbstract() && !gen.isNative()) {
            this.body = gen.getInstructionList();
            this.unpackHandlers(gen);
            this.ensureAllLineNumberSetup();
            this.highestLineNumber = gen.getHighestlinenumber();
         } else {
            this.body = null;
         }

         this.assertGoodBody();
      }
   }

   private void unpackHandlers(MethodGen gen) {
      CodeExceptionGen[] exns = gen.getExceptionHandlers();
      if (exns != null) {
         int len = exns.length;
         int priority = len - 1;

         for(int i = 0; i < len; --priority) {
            CodeExceptionGen exn = exns[i];
            InstructionHandle start = Range.genStart(this.body, this.getOutermostExceptionStart(exn.getStartPC()));
            InstructionHandle end = Range.genEnd(this.body, this.getOutermostExceptionEnd(exn.getEndPC()));
            ExceptionRange er = new ExceptionRange(this.body, exn.getCatchType() == null ? null : BcelWorld.fromBcel((Type)exn.getCatchType()), priority);
            er.associateWithTargets(start, end, exn.getHandlerPC());
            exn.setStartPC((InstructionHandle)null);
            exn.setEndPC((InstructionHandle)null);
            exn.setHandlerPC((InstructionHandle)null);
            ++i;
         }

         gen.removeExceptionHandlers();
      }

   }

   private InstructionHandle getOutermostExceptionStart(InstructionHandle ih) {
      while(ExceptionRange.isExceptionStart(ih.getPrev())) {
         ih = ih.getPrev();
      }

      return ih;
   }

   private InstructionHandle getOutermostExceptionEnd(InstructionHandle ih) {
      while(ExceptionRange.isExceptionEnd(ih.getNext())) {
         ih = ih.getNext();
      }

      return ih;
   }

   public void ensureAllLineNumberSetup() {
      LineNumberTag lastKnownLineNumberTag = null;
      boolean skip = false;

      for(InstructionHandle ih = this.body.getStart(); ih != null; ih = ih.getNext()) {
         skip = false;
         Iterator i$ = ih.getTargeters().iterator();

         while(i$.hasNext()) {
            InstructionTargeter targeter = (InstructionTargeter)i$.next();
            if (targeter instanceof LineNumberTag) {
               lastKnownLineNumberTag = (LineNumberTag)targeter;
               skip = true;
            }
         }

         if (lastKnownLineNumberTag != null && !skip) {
            ih.addTargeter(lastKnownLineNumberTag);
         }
      }

   }

   public int allocateLocal(Type type) {
      return this.allocateLocal(type.getSize());
   }

   public int allocateLocal(int slots) {
      int max = this.getMaxLocals();
      this.setMaxLocals(max + slots);
      return max;
   }

   public Method getMethod() {
      if (this.savedMethod != null) {
         return this.savedMethod;
      } else {
         MethodGen gen;
         try {
            MethodGen gen = this.pack();
            this.savedMethod = gen.getMethod();
            return this.savedMethod;
         } catch (ClassGenException var3) {
            this.enclosingClass.getBcelObjectType().getResolvedTypeX().getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("problemGeneratingMethod", this.getClassName(), this.getName(), var3.getMessage()), this.getMemberView() == null ? null : this.getMemberView().getSourceLocation(), (ISourceLocation)null);
            this.body = null;
            gen = this.pack();
            return gen.getMethod();
         } catch (RuntimeException var4) {
            if (var4.getCause() instanceof ClassGenException) {
               this.enclosingClass.getBcelObjectType().getResolvedTypeX().getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("problemGeneratingMethod", this.getClassName(), this.getName(), var4.getCause().getMessage()), this.getMemberView() == null ? null : this.getMemberView().getSourceLocation(), (ISourceLocation)null);
               this.body = null;
               gen = this.pack();
               return gen.getMethod();
            } else {
               throw var4;
            }
         }
      }
   }

   public void markAsChanged() {
      if (this.wasPackedOptimally) {
         throw new RuntimeException("Already packed method is being re-modified: " + this.getClassName() + " " + this.toShortString());
      } else {
         this.initialize();
         this.savedMethod = null;
      }
   }

   public String toString() {
      BcelObjectType bot = this.enclosingClass.getBcelObjectType();
      AjAttribute.WeaverVersionInfo weaverVersion = bot == null ? AjAttribute.WeaverVersionInfo.CURRENT : bot.getWeaverVersionAttribute();
      return this.toLongString(weaverVersion);
   }

   public String toShortString() {
      String access = com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.accessToString(this.getAccessFlags());
      StringBuffer buf = new StringBuffer();
      if (!access.equals("")) {
         buf.append(access);
         buf.append(" ");
      }

      buf.append(com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.signatureToString(this.getReturnType().getSignature(), true));
      buf.append(" ");
      buf.append(this.getName());
      buf.append("(");
      int len = this.argumentTypes.length;
      int i;
      if (len > 0) {
         buf.append(com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.signatureToString(this.argumentTypes[0].getSignature(), true));

         for(i = 1; i < this.argumentTypes.length; ++i) {
            buf.append(", ");
            buf.append(com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.signatureToString(this.argumentTypes[i].getSignature(), true));
         }
      }

      buf.append(")");
      len = this.declaredExceptions != null ? this.declaredExceptions.length : 0;
      if (len > 0) {
         buf.append(" throws ");
         buf.append(this.declaredExceptions[0]);

         for(i = 1; i < this.declaredExceptions.length; ++i) {
            buf.append(", ");
            buf.append(this.declaredExceptions[i]);
         }
      }

      return buf.toString();
   }

   public String toLongString(AjAttribute.WeaverVersionInfo weaverVersion) {
      ByteArrayOutputStream s = new ByteArrayOutputStream();
      this.print(new PrintStream(s), weaverVersion);
      return new String(s.toByteArray());
   }

   public void print(AjAttribute.WeaverVersionInfo weaverVersion) {
      this.print(System.out, weaverVersion);
   }

   public void print(PrintStream out, AjAttribute.WeaverVersionInfo weaverVersion) {
      out.print("  " + this.toShortString());
      this.printAspectAttributes(out, weaverVersion);
      InstructionList body = this.getBody();
      if (body == null) {
         out.println(";");
      } else {
         out.println(":");
         (new BodyPrinter(out)).run();
         out.println("  end " + this.toShortString());
      }
   }

   private void printAspectAttributes(PrintStream out, AjAttribute.WeaverVersionInfo weaverVersion) {
      ISourceContext context = null;
      if (this.enclosingClass != null && this.enclosingClass.getType() != null) {
         context = this.enclosingClass.getType().getSourceContext();
      }

      List as = Utility.readAjAttributes(this.getClassName(), (Attribute[])this.attributes.toArray(new Attribute[0]), context, (World)null, weaverVersion, new BcelConstantPoolReader(this.enclosingClass.getConstantPool()));
      if (!as.isEmpty()) {
         out.println("    " + as.get(0));
      }

   }

   static LocalVariableTag getLocalVariableTag(InstructionHandle ih, int index) {
      Iterator i$ = ih.getTargeters().iterator();

      while(i$.hasNext()) {
         InstructionTargeter t = (InstructionTargeter)i$.next();
         if (t instanceof LocalVariableTag) {
            LocalVariableTag lvt = (LocalVariableTag)t;
            if (lvt.getSlot() == index) {
               return lvt;
            }
         }
      }

      return null;
   }

   static int getLineNumber(InstructionHandle ih, int prevLine) {
      Iterator i$ = ih.getTargeters().iterator();

      InstructionTargeter t;
      do {
         if (!i$.hasNext()) {
            return prevLine;
         }

         t = (InstructionTargeter)i$.next();
      } while(!(t instanceof LineNumberTag));

      return ((LineNumberTag)t).getLineNumber();
   }

   public boolean isStatic() {
      return Modifier.isStatic(this.getAccessFlags());
   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.getAccessFlags());
   }

   public boolean isBridgeMethod() {
      return (this.getAccessFlags() & 64) != 0;
   }

   public void addExceptionHandler(InstructionHandle start, InstructionHandle end, InstructionHandle handlerStart, ObjectType catchType, boolean highPriority) {
      InstructionHandle start1 = Range.genStart(this.body, start);
      InstructionHandle end1 = Range.genEnd(this.body, end);
      ExceptionRange er = new ExceptionRange(this.body, catchType == null ? null : BcelWorld.fromBcel((Type)catchType), highPriority);
      er.associateWithTargets(start1, end1, handlerStart);
   }

   public int getAccessFlags() {
      return this.modifiers;
   }

   public int getAccessFlagsWithoutSynchronized() {
      return this.isSynchronized() ? this.modifiers - 32 : this.modifiers;
   }

   public boolean isSynchronized() {
      return (this.modifiers & 32) != 0;
   }

   public void setAccessFlags(int newFlags) {
      this.modifiers = newFlags;
   }

   public Type[] getArgumentTypes() {
      this.initialize();
      return this.argumentTypes;
   }

   public LazyClassGen getEnclosingClass() {
      return this.enclosingClass;
   }

   public int getMaxLocals() {
      return this.maxLocals;
   }

   public String getName() {
      return this.name;
   }

   public String getGenericReturnTypeSignature() {
      return this.memberView == null ? this.getReturnType().getSignature() : this.memberView.getGenericReturnType().getSignature();
   }

   public Type getReturnType() {
      this.initialize();
      return this.returnType;
   }

   public void setMaxLocals(int maxLocals) {
      this.maxLocals = maxLocals;
   }

   public InstructionList getBody() {
      this.markAsChanged();
      return this.body;
   }

   public InstructionList getBodyForPrint() {
      return this.body;
   }

   public boolean hasBody() {
      if (this.savedMethod != null) {
         return this.savedMethod.getCode() != null;
      } else {
         return this.body != null;
      }
   }

   public List getAttributes() {
      return this.attributes;
   }

   public String[] getDeclaredExceptions() {
      return this.declaredExceptions;
   }

   public String getClassName() {
      return this.enclosingClass.getName();
   }

   public MethodGen pack() {
      this.forceSyntheticForAjcMagicMembers();
      int flags = this.getAccessFlags();
      if (this.enclosingClass.getWorld().isJoinpointSynchronizationEnabled() && this.enclosingClass.getWorld().areSynchronizationPointcutsInUse()) {
         flags = this.getAccessFlagsWithoutSynchronized();
      }

      MethodGen gen = new MethodGen(flags, this.getReturnType(), this.getArgumentTypes(), (String[])null, this.getName(), this.getEnclosingClass().getName(), new InstructionList(), this.getEnclosingClass().getConstantPool());
      int i = 0;

      int index;
      for(index = this.declaredExceptions.length; i < index; ++i) {
         gen.addException(this.declaredExceptions[i]);
      }

      Iterator i$ = this.attributes.iterator();

      while(i$.hasNext()) {
         Attribute attr = (Attribute)i$.next();
         gen.addAttribute(attr);
      }

      if (this.newAnnotations != null) {
         i$ = this.newAnnotations.iterator();

         while(i$.hasNext()) {
            AnnotationAJ element = (AnnotationAJ)i$.next();
            gen.addAnnotation(new AnnotationGen(((BcelAnnotation)element).getBcelAnnotation(), gen.getConstantPool(), true));
         }
      }

      int j;
      if (this.newParameterAnnotations != null) {
         for(i = 0; i < this.newParameterAnnotations.length; ++i) {
            AnnotationAJ[] annos = this.newParameterAnnotations[i];

            for(j = 0; j < annos.length; ++j) {
               gen.addParameterAnnotation(i, new AnnotationGen(((BcelAnnotation)annos[j]).getBcelAnnotation(), gen.getConstantPool(), true));
            }
         }
      }

      if (this.memberView != null && this.memberView.getAnnotations() != null && this.memberView.getAnnotations().length != 0) {
         AnnotationAJ[] ans = this.memberView.getAnnotations();
         index = 0;

         for(j = ans.length; index < j; ++index) {
            AnnotationGen a = ((BcelAnnotation)ans[index]).getBcelAnnotation();
            gen.addAnnotation(new AnnotationGen(a, gen.getConstantPool(), true));
         }
      }

      if (this.isSynthetic) {
         if (this.enclosingClass.getWorld().isInJava5Mode()) {
            gen.setModifiers(gen.getModifiers() | 4096);
         }

         if (!this.hasAttribute("Synthetic")) {
            ConstantPool cpg = gen.getConstantPool();
            index = cpg.addUtf8("Synthetic");
            gen.addAttribute(new Synthetic(index, 0, new byte[0], cpg));
         }
      }

      if (this.hasBody()) {
         if (this.enclosingClass.getWorld().shouldFastPackMethods()) {
            if (!this.isAdviceMethod() && !this.getName().equals("<clinit>")) {
               this.optimizedPackBody(gen);
            } else {
               this.packBody(gen);
            }
         } else {
            this.packBody(gen);
         }

         gen.setMaxLocals();
         gen.setMaxStack();
      } else {
         gen.setInstructionList((InstructionList)null);
      }

      return gen;
   }

   private boolean hasAttribute(String attributeName) {
      Iterator i$ = this.attributes.iterator();

      Attribute attr;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         attr = (Attribute)i$.next();
      } while(!attr.getName().equals(attributeName));

      return true;
   }

   private void forceSyntheticForAjcMagicMembers() {
      if (NameMangler.isSyntheticMethod(this.getName(), this.inAspect())) {
         this.makeSynthetic();
      }

   }

   private boolean inAspect() {
      BcelObjectType objectType = this.enclosingClass.getBcelObjectType();
      return objectType == null ? false : objectType.isAspect();
   }

   public void makeSynthetic() {
      this.isSynthetic = true;
   }

   public void packBody(MethodGen gen) {
      InstructionList fresh = gen.getInstructionList();
      Map map = this.copyAllInstructionsExceptRangeInstructionsInto(fresh);
      InstructionHandle oldInstructionHandle = this.getBody().getStart();
      InstructionHandle newInstructionHandle = fresh.getStart();
      LinkedList exceptionList = new LinkedList();
      Map localVariables = new HashMap();
      int currLine = -1;
      int lineNumberOffset = this.fromFilename == null ? 0 : this.getEnclosingClass().getSourceDebugExtensionOffset(this.fromFilename);

      while(true) {
         while(oldInstructionHandle != null) {
            if (map.get(oldInstructionHandle) == null) {
               this.handleRangeInstruction(oldInstructionHandle, exceptionList);
               oldInstructionHandle = oldInstructionHandle.getNext();
            } else {
               Instruction oldInstruction = oldInstructionHandle.getInstruction();
               Instruction newInstruction = newInstructionHandle.getInstruction();
               if (oldInstruction instanceof InstructionBranch) {
                  this.handleBranchInstruction(map, oldInstruction, newInstruction);
               }

               Iterator i$ = oldInstructionHandle.getTargeters().iterator();

               while(i$.hasNext()) {
                  InstructionTargeter targeter = (InstructionTargeter)i$.next();
                  if (targeter instanceof LineNumberTag) {
                     int line = ((LineNumberTag)targeter).getLineNumber();
                     if (line != currLine) {
                        gen.addLineNumber(newInstructionHandle, line + lineNumberOffset);
                        currLine = line;
                     }
                  } else if (targeter instanceof LocalVariableTag) {
                     LocalVariableTag lvt = (LocalVariableTag)targeter;
                     LVPosition p = (LVPosition)localVariables.get(lvt);
                     if (p == null) {
                        LVPosition newp = new LVPosition();
                        newp.start = newp.end = newInstructionHandle;
                        localVariables.put(lvt, newp);
                     } else {
                        p.end = newInstructionHandle;
                     }
                  }
               }

               oldInstructionHandle = oldInstructionHandle.getNext();
               newInstructionHandle = newInstructionHandle.getNext();
            }
         }

         this.addExceptionHandlers(gen, map, exceptionList);
         if (this.originalMethodHasLocalVariableTable || this.enclosingClass.getBcelObjectType().getResolvedTypeX().getWorld().generateNewLvts) {
            if (localVariables.size() == 0) {
               this.createNewLocalVariables(gen);
            } else {
               this.addLocalVariables(gen, localVariables);
            }
         }

         if (gen.getLineNumbers().length == 0) {
            gen.addLineNumber(gen.getInstructionList().getStart(), 1);
         }

         return;
      }
   }

   private void createNewLocalVariables(MethodGen gen) {
      gen.removeLocalVariables();
      if (!this.getName().startsWith("<")) {
         int slot = 0;
         InstructionHandle start = gen.getInstructionList().getStart();
         InstructionHandle end = gen.getInstructionList().getEnd();
         if (!this.isStatic()) {
            String cname = this.enclosingClass.getClassName();
            if (cname == null) {
               return;
            }

            Type enclosingType = BcelWorld.makeBcelType(UnresolvedType.forName(cname));
            gen.addLocalVariable("this", enclosingType, slot++, start, end);
         }

         String[] paramNames = this.memberView == null ? null : this.memberView.getParameterNames();
         if (paramNames != null) {
            for(int i = 0; i < this.argumentTypes.length; ++i) {
               String pname = paramNames[i];
               if (pname == null) {
                  pname = "arg" + i;
               }

               gen.addLocalVariable(pname, this.argumentTypes[i], slot, start, end);
               slot += this.argumentTypes[i].getSize();
            }
         }
      }

   }

   private World getWorld() {
      return this.enclosingClass.getBcelObjectType().getResolvedTypeX().getWorld();
   }

   public void optimizedPackBody(MethodGen gen) {
      InstructionList theBody = this.getBody();
      InstructionHandle iHandle = theBody.getStart();
      int currLine = -1;
      int lineNumberOffset = this.fromFilename == null ? 0 : this.getEnclosingClass().getSourceDebugExtensionOffset(this.fromFilename);
      Map localVariables = new HashMap();
      LinkedList exceptionList = new LinkedList();
      Set forDeletion = new HashSet();

      HashSet branchInstructions;
      for(branchInstructions = new HashSet(); iHandle != null; iHandle = iHandle.getNext()) {
         Instruction inst = iHandle.getInstruction();
         if (inst == Range.RANGEINSTRUCTION) {
            Range r = Range.getRange(iHandle);
            if (r instanceof ExceptionRange) {
               ExceptionRange er = (ExceptionRange)r;
               if (er.getStart() == iHandle && !er.isEmpty()) {
                  insertHandler(er, exceptionList);
               }
            }

            forDeletion.add(iHandle);
         } else {
            if (inst instanceof InstructionBranch) {
               branchInstructions.add((BranchHandle)iHandle);
            }

            Iterator i$ = iHandle.getTargetersCopy().iterator();

            while(i$.hasNext()) {
               InstructionTargeter targeter = (InstructionTargeter)i$.next();
               if (targeter instanceof LineNumberTag) {
                  int line = ((LineNumberTag)targeter).getLineNumber();
                  if (line != currLine) {
                     gen.addLineNumber(iHandle, line + lineNumberOffset);
                     currLine = line;
                  }
               } else if (targeter instanceof LocalVariableTag) {
                  LocalVariableTag lvt = (LocalVariableTag)targeter;
                  LVPosition p = (LVPosition)localVariables.get(lvt);
                  if (p == null) {
                     LVPosition newp = new LVPosition();
                     newp.start = newp.end = iHandle;
                     localVariables.put(lvt, newp);
                  } else {
                     p.end = iHandle;
                  }
               }
            }
         }
      }

      Iterator i$ = branchInstructions.iterator();

      while(i$.hasNext()) {
         BranchHandle branchHandle = (BranchHandle)i$.next();
         this.handleBranchInstruction(branchHandle, forDeletion);
      }

      i$ = exceptionList.iterator();

      while(i$.hasNext()) {
         ExceptionRange r = (ExceptionRange)i$.next();
         if (!r.isEmpty()) {
            gen.addExceptionHandler(this.jumpForward(r.getRealStart(), forDeletion), this.jumpForward(r.getRealEnd(), forDeletion), this.jumpForward(r.getHandler(), forDeletion), r.getCatchType() == null ? null : (ObjectType)BcelWorld.makeBcelType(r.getCatchType()));
         }
      }

      i$ = forDeletion.iterator();

      while(i$.hasNext()) {
         InstructionHandle handle = (InstructionHandle)i$.next();

         try {
            theBody.delete(handle);
         } catch (TargetLostException var16) {
            var16.printStackTrace();
         }
      }

      gen.setInstructionList(theBody);
      if (this.originalMethodHasLocalVariableTable || this.getWorld().generateNewLvts) {
         if (localVariables.size() == 0) {
            this.createNewLocalVariables(gen);
         } else {
            this.addLocalVariables(gen, localVariables);
         }
      }

      if (gen.getLineNumbers().length == 0) {
         gen.addLineNumber(gen.getInstructionList().getStart(), 1);
      }

      this.wasPackedOptimally = true;
   }

   private void addLocalVariables(MethodGen gen, Map localVariables) {
      gen.removeLocalVariables();
      InstructionHandle methodStart = gen.getInstructionList().getStart();
      InstructionHandle methodEnd = gen.getInstructionList().getEnd();
      int paramSlots = gen.isStatic() ? 0 : 1;
      Type[] argTypes = gen.getArgumentTypes();
      if (argTypes != null) {
         for(int i = 0; i < argTypes.length; ++i) {
            if (argTypes[i].getSize() == 2) {
               paramSlots += 2;
            } else {
               ++paramSlots;
            }
         }
      }

      if (!this.enclosingClass.getWorld().generateNewLvts) {
         paramSlots = -1;
      }

      Map duplicatedLocalMap = new HashMap();
      Iterator i$ = localVariables.keySet().iterator();

      while(true) {
         LocalVariableTag tag;
         InstructionHandle start;
         InstructionHandle end;
         Object slots;
         do {
            if (!i$.hasNext()) {
               return;
            }

            tag = (LocalVariableTag)i$.next();
            LVPosition lvpos = (LVPosition)localVariables.get(tag);
            start = tag.getSlot() < paramSlots ? methodStart : lvpos.start;
            end = tag.getSlot() < paramSlots ? methodEnd : lvpos.end;
            slots = (Set)duplicatedLocalMap.get(start);
            if (slots == null) {
               slots = new HashSet();
               duplicatedLocalMap.put(start, slots);
               break;
            }
         } while(((Set)slots).contains(new Integer(tag.getSlot())));

         ((Set)slots).add(tag.getSlot());
         Type t = tag.getRealType();
         if (t == null) {
            t = BcelWorld.makeBcelType(UnresolvedType.forSignature(tag.getType()));
         }

         gen.addLocalVariable(tag.getName(), t, tag.getSlot(), start, end);
      }
   }

   private void addExceptionHandlers(MethodGen gen, Map map, LinkedList exnList) {
      Iterator i$ = exnList.iterator();

      while(i$.hasNext()) {
         ExceptionRange r = (ExceptionRange)i$.next();
         if (!r.isEmpty()) {
            InstructionHandle rMappedStart = remap(r.getRealStart(), map);
            InstructionHandle rMappedEnd = remap(r.getRealEnd(), map);
            InstructionHandle rMappedHandler = remap(r.getHandler(), map);
            gen.addExceptionHandler(rMappedStart, rMappedEnd, rMappedHandler, r.getCatchType() == null ? null : (ObjectType)BcelWorld.makeBcelType(r.getCatchType()));
         }
      }

   }

   private void handleBranchInstruction(Map map, Instruction oldInstruction, Instruction newInstruction) {
      InstructionBranch oldBranchInstruction = (InstructionBranch)oldInstruction;
      InstructionBranch newBranchInstruction = (InstructionBranch)newInstruction;
      InstructionHandle oldTarget = oldBranchInstruction.getTarget();
      newBranchInstruction.setTarget(remap(oldTarget, map));
      if (oldBranchInstruction instanceof InstructionSelect) {
         InstructionHandle[] oldTargets = ((InstructionSelect)oldBranchInstruction).getTargets();
         InstructionHandle[] newTargets = ((InstructionSelect)newBranchInstruction).getTargets();

         for(int k = oldTargets.length - 1; k >= 0; --k) {
            newTargets[k] = remap(oldTargets[k], map);
            newTargets[k].addTargeter(newBranchInstruction);
         }
      }

   }

   private InstructionHandle jumpForward(InstructionHandle t, Set handlesForDeletion) {
      InstructionHandle target = t;
      if (handlesForDeletion.contains(t)) {
         do {
            target = target.getNext();
         } while(handlesForDeletion.contains(target));
      }

      return target;
   }

   private void handleBranchInstruction(BranchHandle branchHandle, Set handlesForDeletion) {
      InstructionBranch branchInstruction = (InstructionBranch)branchHandle.getInstruction();
      InstructionHandle target = branchInstruction.getTarget();
      if (handlesForDeletion.contains(target)) {
         do {
            target = target.getNext();
         } while(handlesForDeletion.contains(target));

         branchInstruction.setTarget(target);
      }

      if (branchInstruction instanceof InstructionSelect) {
         InstructionSelect iSelect = (InstructionSelect)branchInstruction;
         InstructionHandle[] targets = iSelect.getTargets();

         for(int k = targets.length - 1; k >= 0; --k) {
            InstructionHandle oneTarget = targets[k];
            if (handlesForDeletion.contains(oneTarget)) {
               do {
                  oneTarget = oneTarget.getNext();
               } while(handlesForDeletion.contains(oneTarget));

               iSelect.setTarget(k, oneTarget);
               oneTarget.addTargeter(branchInstruction);
            }
         }
      }

   }

   private void handleRangeInstruction(InstructionHandle ih, LinkedList exnList) {
      Range r = Range.getRange(ih);
      if (r instanceof ExceptionRange) {
         ExceptionRange er = (ExceptionRange)r;
         if (er.getStart() == ih && !er.isEmpty()) {
            insertHandler(er, exnList);
         }
      }

   }

   private Map copyAllInstructionsExceptRangeInstructionsInto(InstructionList intoList) {
      Map map = new HashMap();

      for(InstructionHandle ih = this.getBody().getStart(); ih != null; ih = ih.getNext()) {
         if (!Range.isRangeHandle(ih)) {
            Instruction inst = ih.getInstruction();
            Instruction copy = Utility.copyInstruction(inst);
            if (copy instanceof InstructionBranch) {
               map.put(ih, intoList.append((InstructionBranch)copy));
            } else {
               map.put(ih, intoList.append(copy));
            }
         }
      }

      return map;
   }

   private static InstructionHandle remap(InstructionHandle handle, Map map) {
      while(true) {
         InstructionHandle ret = (InstructionHandle)map.get(handle);
         if (ret != null) {
            return ret;
         }

         handle = handle.getNext();
      }
   }

   static void insertHandler(ExceptionRange fresh, LinkedList l) {
      ListIterator iter = l.listIterator();

      ExceptionRange r;
      do {
         if (!iter.hasNext()) {
            l.add(fresh);
            return;
         }

         r = (ExceptionRange)iter.next();
      } while(fresh.getPriority() < r.getPriority());

      iter.previous();
      iter.add(fresh);
   }

   public boolean isPrivate() {
      return Modifier.isPrivate(this.getAccessFlags());
   }

   public boolean isProtected() {
      return Modifier.isProtected(this.getAccessFlags());
   }

   public boolean isDefault() {
      return !this.isProtected() && !this.isPrivate() && !this.isPublic();
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.getAccessFlags());
   }

   public void assertGoodBody() {
   }

   public static void assertGoodBody(InstructionList il, String from) {
   }

   private static void assertTargetedBy(InstructionHandle target, InstructionTargeter targeter, String from) {
      Iterator tIter = target.getTargeters().iterator();

      do {
         if (!tIter.hasNext()) {
            throw new RuntimeException("bad targeting relationship in " + from);
         }
      } while((InstructionTargeter)tIter.next() != targeter);

   }

   private static void assertTargets(InstructionTargeter targeter, InstructionHandle target, String from) {
      if (targeter instanceof Range) {
         Range r = (Range)targeter;
         if (r.getStart() == target || r.getEnd() == target) {
            return;
         }

         if (r instanceof ExceptionRange && ((ExceptionRange)r).getHandler() == target) {
            return;
         }
      } else if (targeter instanceof InstructionBranch) {
         InstructionBranch bi = (InstructionBranch)targeter;
         if (bi.getTarget() == target) {
            return;
         }

         if (targeter instanceof InstructionSelect) {
            InstructionSelect sel = (InstructionSelect)targeter;
            InstructionHandle[] itargets = sel.getTargets();

            for(int k = itargets.length - 1; k >= 0; --k) {
               if (itargets[k] == target) {
                  return;
               }
            }
         }
      } else if (targeter instanceof Tag) {
         return;
      }

      throw new BCException(targeter + " doesn't target " + target + " in " + from);
   }

   private static Range getRangeAndAssertExactlyOne(InstructionHandle ih, String from) {
      Range ret = null;
      Iterator tIter = ih.getTargeters().iterator();
      if (!tIter.hasNext()) {
         throw new BCException("range handle with no range in " + from);
      } else {
         while(tIter.hasNext()) {
            InstructionTargeter ts = (InstructionTargeter)tIter.next();
            if (ts instanceof Range) {
               if (ret != null) {
                  throw new BCException("range handle with multiple ranges in " + from);
               }

               ret = (Range)ts;
            }
         }

         if (ret == null) {
            throw new BCException("range handle with no range in " + from);
         } else {
            return ret;
         }
      }
   }

   boolean isAdviceMethod() {
      if (this.memberView == null) {
         return false;
      } else {
         return this.memberView.getAssociatedShadowMunger() != null;
      }
   }

   boolean isAjSynthetic() {
      return this.memberView == null ? true : this.memberView.isAjSynthetic();
   }

   boolean isSynthetic() {
      return this.memberView == null ? false : this.memberView.isSynthetic();
   }

   public ISourceLocation getSourceLocation() {
      return this.memberView != null ? this.memberView.getSourceLocation() : null;
   }

   public AjAttribute.EffectiveSignatureAttribute getEffectiveSignature() {
      return this.effectiveSignature != null ? this.effectiveSignature : this.memberView.getEffectiveSignature();
   }

   public void setEffectiveSignature(ResolvedMember member, Shadow.Kind kind, boolean shouldWeave) {
      this.effectiveSignature = new AjAttribute.EffectiveSignatureAttribute(member, kind, shouldWeave);
   }

   public String getSignature() {
      return this.memberView != null ? this.memberView.getSignature() : MemberImpl.typesToSignature(BcelWorld.fromBcel(this.getReturnType()), BcelWorld.fromBcel(this.getArgumentTypes()), false);
   }

   public String getParameterSignature() {
      return this.memberView != null ? this.memberView.getParameterSignature() : MemberImpl.typesToSignature(BcelWorld.fromBcel(this.getArgumentTypes()));
   }

   public BcelMethod getMemberView() {
      return this.memberView;
   }

   public void forcePublic() {
      this.markAsChanged();
      this.modifiers = Utility.makePublic(this.modifiers);
   }

   public boolean getCanInline() {
      return this.canInline;
   }

   public void setCanInline(boolean canInline) {
      this.canInline = canInline;
   }

   public void addAttribute(Attribute attribute) {
      this.attributes.add(attribute);
   }

   public String toTraceString() {
      return this.toShortString();
   }

   public ConstantPool getConstantPool() {
      return this.enclosingClass.getConstantPool();
   }

   public static boolean isConstructor(LazyMethodGen aMethod) {
      return aMethod.getName().equals("<init>");
   }

   private static class LVPosition {
      InstructionHandle start;
      InstructionHandle end;

      private LVPosition() {
         this.start = null;
         this.end = null;
      }

      // $FF: synthetic method
      LVPosition(Object x0) {
         this();
      }
   }

   private class BodyPrinter {
      Map labelMap = new HashMap();
      InstructionList body;
      PrintStream out;
      ConstantPool pool;
      static final int BODY_INDENT = 4;
      static final int CODE_INDENT = 16;

      BodyPrinter(PrintStream out) {
         this.pool = LazyMethodGen.this.enclosingClass.getConstantPool();
         this.body = LazyMethodGen.this.getBodyForPrint();
         this.out = out;
      }

      BodyPrinter(PrintStream out, InstructionList il) {
         this.pool = LazyMethodGen.this.enclosingClass.getConstantPool();
         this.body = il;
         this.out = out;
      }

      void run() {
         this.assignLabels();
         this.print();
      }

      void assignLabels() {
         LinkedList exnTable = new LinkedList();
         String pendingLabel = null;
         int lcounter = 0;

         Iterator i;
         for(InstructionHandle ih = this.body.getStart(); ih != null; ih = ih.getNext()) {
            i = ih.getTargeters().iterator();

            while(i.hasNext()) {
               InstructionTargeter t = (InstructionTargeter)i.next();
               if (t instanceof ExceptionRange) {
                  ExceptionRange r = (ExceptionRange)t;
                  if (r.getStart() == ih) {
                     LazyMethodGen.insertHandler(r, exnTable);
                  }
               } else if (t instanceof InstructionBranch && pendingLabel == null) {
                  pendingLabel = "L" + lcounter++;
               }
            }

            if (pendingLabel != null) {
               this.labelMap.put(ih, pendingLabel);
               if (!Range.isRangeHandle(ih)) {
                  pendingLabel = null;
               }
            }
         }

         int ecounter = 0;
         i = exnTable.iterator();

         while(i.hasNext()) {
            ExceptionRange er = (ExceptionRange)i.next();
            String exceptionLabel = "E" + ecounter++;
            this.labelMap.put(Range.getRealStart(er.getHandler()), exceptionLabel);
            this.labelMap.put(er.getHandler(), exceptionLabel);
         }

      }

      void print() {
         int depth = 0;
         int currLine = -1;

         label39:
         for(InstructionHandle ih = this.body.getStart(); ih != null; ih = ih.getNext()) {
            if (Range.isRangeHandle(ih)) {
               Range r = Range.getRange(ih);

               for(InstructionHandle xx = r.getStart(); Range.isRangeHandle(xx); xx = xx.getNext()) {
                  if (xx == r.getEnd()) {
                     continue label39;
                  }
               }

               if (r.getStart() == ih) {
                  this.printRangeString(r, depth++);
               } else {
                  if (r.getEnd() != ih) {
                     throw new RuntimeException("bad");
                  }

                  --depth;
                  this.printRangeString(r, depth);
               }
            } else {
               this.printInstruction(ih, depth);
               int line = LazyMethodGen.getLineNumber(ih, currLine);
               if (line != currLine) {
                  currLine = line;
                  this.out.println("   (line " + line + ")");
               } else {
                  this.out.println();
               }
            }
         }

      }

      void printRangeString(Range r, int depth) {
         this.printDepth(depth);
         this.out.println(this.getRangeString(r, this.labelMap));
      }

      String getRangeString(Range r, Map labelMap) {
         if (r instanceof ExceptionRange) {
            ExceptionRange er = (ExceptionRange)r;
            return er.toString() + " -> " + (String)labelMap.get(er.getHandler());
         } else {
            return r.toString();
         }
      }

      void printDepth(int depth) {
         this.pad(4);

         while(depth > 0) {
            this.out.print("| ");
            --depth;
         }

      }

      void printLabel(String s, int depth) {
         int space = Math.max(16 - depth * 2, 0);
         if (s == null) {
            this.pad(space);
         } else {
            space = Math.max(space - (s.length() + 2), 0);
            this.pad(space);
            this.out.print(s);
            this.out.print(": ");
         }

      }

      void printInstruction(InstructionHandle h, int depth) {
         this.printDepth(depth);
         this.printLabel((String)this.labelMap.get(h), depth);
         Instruction inst = h.getInstruction();
         if (inst.isConstantPoolInstruction()) {
            this.out.print(Constants.OPCODE_NAMES[inst.opcode].toUpperCase());
            this.out.print(" ");
            this.out.print(this.pool.constantToString(this.pool.getConstant(inst.getIndex())));
         } else if (inst instanceof InstructionSelect) {
            InstructionSelect sinst = (InstructionSelect)inst;
            this.out.println(Constants.OPCODE_NAMES[sinst.opcode].toUpperCase());
            int[] matches = sinst.getMatchs();
            InstructionHandle[] targets = sinst.getTargets();
            InstructionHandle defaultTarget = sinst.getTarget();
            int i = 0;

            for(int len = matches.length; i < len; ++i) {
               this.printDepth(depth);
               this.printLabel((String)null, depth);
               this.out.print("  ");
               this.out.print(matches[i]);
               this.out.print(": \t");
               this.out.println((String)this.labelMap.get(targets[i]));
            }

            this.printDepth(depth);
            this.printLabel((String)null, depth);
            this.out.print("  ");
            this.out.print("default: \t");
            this.out.print((String)this.labelMap.get(defaultTarget));
         } else if (inst instanceof InstructionBranch) {
            InstructionBranch brinst = (InstructionBranch)inst;
            this.out.print(Constants.OPCODE_NAMES[brinst.getOpcode()].toUpperCase());
            this.out.print(" ");
            this.out.print((String)this.labelMap.get(brinst.getTarget()));
         } else if (inst.isLocalVariableInstruction()) {
            this.out.print(inst.toString(false).toUpperCase());
            int index = inst.getIndex();
            LocalVariableTag tag = LazyMethodGen.getLocalVariableTag(h, index);
            if (tag != null) {
               this.out.print("     // ");
               this.out.print(tag.getType());
               this.out.print(" ");
               this.out.print(tag.getName());
            }
         } else {
            this.out.print(inst.toString(false).toUpperCase());
         }

      }

      void pad(int size) {
         for(int i = 0; i < size; ++i) {
            this.out.print(" ");
         }

      }
   }

   static class LightweightBcelMethod extends BcelMethod {
      LightweightBcelMethod(BcelObjectType declaringType, Method method) {
         super(declaringType, method);
      }
   }
}
