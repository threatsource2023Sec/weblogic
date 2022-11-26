package com.bea.wls.redef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.jws.WebMethod;
import javax.jws.WebService;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.BootstrapMethodElement;
import serp.bytecode.BootstrapMethods;
import serp.bytecode.Code;
import serp.bytecode.ConstantValue;
import serp.bytecode.ExceptionHandler;
import serp.bytecode.FieldInstruction;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LocalVariableInstruction;
import serp.bytecode.LookupSwitchInstruction;
import serp.bytecode.MethodInstruction;
import serp.bytecode.NestHost;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.DoubleEntry;
import serp.bytecode.lowlevel.Entry;
import serp.bytecode.lowlevel.FieldEntry;
import serp.bytecode.lowlevel.FloatEntry;
import serp.bytecode.lowlevel.IntEntry;
import serp.bytecode.lowlevel.InterfaceMethodEntry;
import serp.bytecode.lowlevel.InvokeDynamicEntry;
import serp.bytecode.lowlevel.LongEntry;
import serp.bytecode.lowlevel.MethodEntry;
import serp.bytecode.lowlevel.MethodHandleEntry;
import serp.bytecode.lowlevel.MethodTypeEntry;
import serp.bytecode.lowlevel.NameAndTypeEntry;
import serp.bytecode.lowlevel.StringEntry;
import serp.bytecode.lowlevel.UTF8Entry;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.annotation.BeaSynthetic;

public class Enhancer {
   private static final String PRE = "bea";
   private static final String INVOKE = "beaInvoke";
   private static final String INVOKE_PRIVATE = "beaInvokePrivate";
   private static final String INVOKE_SUPER = "beaInvokeSuper";
   private static final String INVOKE_STATIC = "beaInvokeStatic";
   private static final String ADDED_FIELDS = "beaAddedFields";
   private static final String VERSIONED_CONS = "beaAddedConstructor";
   private static final String GENERIC_CONS_ARG = Void.class.getName();
   private static final String PRIVATE_ACCESS = "beaAccess";
   protected static final String FINAL_PROTECTED_ACCESS = "beaAccessSuperFinalProtected";
   private static final Set PRIMITIVE_TYPE_NAMES = new HashSet(Arrays.asList("boolean", "byte", "char", "double", "float", "int", "long", "short"));
   private static final Comparator MEMBER_COMPARATOR = new Comparator() {
      public int compare(MemberMetaData m1, MemberMetaData m2) {
         return m1.getIndex() - m2.getIndex();
      }
   };
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   public static final String NAMING_SCHEME_PROPERTY_NAME = "weblogic.fastswap.namingscheme.original";
   private final BCClass _bc;
   private final MetaDataRepository _repos;
   private final ClassMetaData _meta;
   private final ClassMetaData _supMeta;
   private final ClassLoader _loader;
   private final Code _template;
   private BCClass _vers;
   private SortedSet _nonredefSuperInvocations;
   private static int _versionSequenceNum;
   private boolean _originalNamingScheme;
   private boolean _isWebServiceClass;

   public Enhancer(BCClass bc, MetaDataRepository repos) {
      this(bc, repos.getMetaData(bc.getName()), Thread.currentThread().getContextClassLoader());
   }

   public Enhancer(BCClass bc, ClassMetaData meta, ClassLoader loader) {
      this._template = new Code();
      this._vers = null;
      this._originalNamingScheme = false;
      this._isWebServiceClass = false;
      this._bc = bc;
      this._meta = meta;
      this._repos = meta == null ? null : meta.getRepository();
      this._supMeta = meta == null ? null : meta.getSuperclassMetaData();
      this._loader = loader;
      this._originalNamingScheme = Boolean.parseBoolean(System.getProperty("weblogic.fastswap.namingscheme.original"));
      this._isWebServiceClass = bc.getDeclaredRuntimeAnnotations(true).getAnnotation(WebService.class) != null;
   }

   public BCClass getBytecode() {
      return this._bc;
   }

   public BCClass getVersionBytecode() {
      return this._vers;
   }

   public boolean run() {
      if (this._meta == null) {
         return false;
      } else {
         String[] var1 = this._bc.getDeclaredInterfaceNames();
         int var2 = var1.length;

         int var3;
         for(var3 = 0; var3 < var2; ++var3) {
            String intf = var1[var3];
            if (intf.equals(Redefinable.class.getName())) {
               return false;
            }
         }

         if (this._bc.getMajorVersion() >= 53 && !this._bc.isInterface()) {
            BCField[] bcFields = this._bc.getDeclaredFields();
            BCField[] var7 = bcFields;
            var3 = bcFields.length;

            for(int var8 = 0; var8 < var3; ++var8) {
               BCField field = var7[var8];
               if (!field.getName().equals("serialVersionUID") && field.isFinal()) {
                  field.setAccessFlags(field.getAccessFlags() & -17);
               }
            }
         }

         this._bc.declareInterface(Redefinable.class);
         if (!this._meta.isInterface()) {
            this.fixSerialVersionUID();
            this._bc.setAbstract(false);
            this.createVersionClass();
            this.replaceDirectMemberAccess();
         }

         if (this._bc.getMajorVersion() >= 53) {
            this.addBootstrapAttribute();
         }

         this.moveMethodsToVersion();
         if (!this._meta.isInterface()) {
            this.moveAddedStaticFieldsToVersion();
            this.addFieldAccessCode();
            this.addInvokeMethods();
            this.moveAddedConstructorLogicToVersion();
            this.combineAddedConstructors();
         }

         this.addStaticInitializer();
         this.addOriginalFields();
         this.fixConstructorModifiers();
         this.orderFieldDeclarations();
         this.orderMethodDeclarations();
         return true;
      }
   }

   private void fixSerialVersionUID() {
      long svuid = this._meta.getSerialVersionUID();
      BCField svuidField = this._bc.getDeclaredField("serialVersionUID");
      if (svuidField == null) {
         svuidField = this._bc.declareField("serialVersionUID", Long.TYPE);
         svuidField.makePublic();
         svuidField.setStatic(true);
         svuidField.setFinal(true);
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Assigning SerialVersionUID " + svuid + " to " + this._meta.getName());
      }

      ConstantValue constValue = svuidField.getConstantValue(true);
      constValue.setLongValue(svuid);
   }

   private void createVersionClass() {
      String versionClassName = null;
      if (this._originalNamingScheme) {
         versionClassName = this._meta.getName().replace('$', '_') + "_" + "bea" + "Version" + this._meta.getVersion() + "_" + getVersionSequenceNum();
      } else {
         versionClassName = this._meta.getName() + "$" + "bea" + "Version" + this._meta.getVersion() + "_" + getVersionSequenceNum();
      }

      this._vers = this._bc.getProject().loadClass(versionClassName, this._loader);
      this._vers.setSynthetic(true);
      this._vers.makePackage();
      this._vers.addDefaultConstructor().makePrivate();
      if (this._bc.getMajorVersion() >= 53) {
         this._vers.setMinorVersion(this._bc.getMinorVersion());
         this._vers.setMajorVersion(this._bc.getMajorVersion());
      }

      if (this._bc.getSourceFile(false) != null) {
         this._vers.getSourceFile(true).setFile(this._bc.getSourceFile(false).getFileName());
      }

   }

   private static synchronized int getVersionSequenceNum() {
      return ++_versionSequenceNum;
   }

   private void replaceDirectMemberAccess() {
      Instruction invokev = this._template.invokevirtual();
      Instruction invokes = this._template.invokespecial();
      Instruction invokestat = this._template.invokestatic();
      BCMethod[] var4 = this._bc.getDeclaredMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BCMethod method = var4[var6];
         Code code = method.getCode(false);
         if (code != null) {
            boolean altered = false;
            String methodName = method.getName();
            boolean isConstructor = "<init>".equals(method.getName());
            boolean foundSuperCall = false;
            int arr = code.getMaxLocals();
            int tmp = arr + 1;

            while(true) {
               while(code.hasNext()) {
                  Instruction ins = code.next();
                  int startByteIndex = ins.getByteIndex();
                  this.getNextByteIndex(code);
                  switch (ins.getOpcode()) {
                     case 178:
                        altered |= this.replaceDirectFieldAccess(code, invokestat, true, true);
                        break;
                     case 179:
                        altered |= this.replaceDirectFieldAccess(code, invokestat, false, true);
                        break;
                     case 180:
                        if (!isConstructor || foundSuperCall) {
                           altered |= this.replaceDirectFieldAccess(code, invokev, true, false);
                        }
                        break;
                     case 181:
                        if (!isConstructor || foundSuperCall) {
                           altered |= this.replaceDirectFieldAccess(code, invokev, false, false);
                        }
                        break;
                     case 182:
                     case 185:
                        altered |= this.replaceDirectMethodAccess(code, "beaInvoke", invokestat, arr, tmp);
                        break;
                     case 183:
                        MethodInstruction mInsn = (MethodInstruction)ins;
                        if ("<init>".equals(mInsn.getMethodName())) {
                           if (isConstructor && this.isSuperOrThisCall(mInsn)) {
                              foundSuperCall = true;
                           }

                           altered |= this.replaceDirectConstruction(code, arr, tmp);
                        } else {
                           altered |= this.replaceDirectSpecialMethodAccess(code, invokestat, arr, tmp);
                        }
                        break;
                     case 184:
                        altered |= this.replaceDirectMethodAccess(code, "beaInvokeStatic", invokestat, arr, tmp);
                  }
               }

               if (altered) {
                  this.removeUnsupportedAttributes(code);
                  code.calculateMaxStack();
                  code.calculateMaxLocals();
               }
               break;
            }
         }
      }

   }

   private void addBootstrapAttribute() {
      BootstrapMethods bootstrapMethods = (BootstrapMethods)this._bc.getAttribute("BootstrapMethods");
      if (bootstrapMethods != null) {
         this._vers.addAttribute("BootstrapMethods");
         BootstrapMethods verbootstrapMethods = (BootstrapMethods)this._vers.getAttribute("BootstrapMethods");
         ConstantPool constantPool = this._vers.getPool();
         BootstrapMethodElement[] bootstrapMethodElement = bootstrapMethods.getBootstrapMethods();
         BootstrapMethodElement[] verBootstrapMethodElements = new BootstrapMethodElement[bootstrapMethods.getNumberBootstrapMethods()];

         int i;
         BootstrapMethodElement bme;
         for(i = 0; i < bootstrapMethods.getNumberBootstrapMethods(); ++i) {
            bme = new BootstrapMethodElement();
            verBootstrapMethodElements[i] = bme;
         }

         for(i = 0; i < bootstrapMethods.getNumberBootstrapMethods(); ++i) {
            bme = bootstrapMethodElement[i];
            int[] arguments = new int[bme.getNumBootstrapArguments()];
            MethodHandleEntry bcmhe = bme.getBootstrapMethod();
            Entry[] bcentries = bme.getBootstrapArguments();
            int bcreferencekind = bcmhe.getReferenceKind();
            ClassEntry bcclassentry = null;
            NameAndTypeEntry bcnameandtypeentry = null;
            int indexvermethodentry = 0;
            if (bcreferencekind == 6) {
               MethodEntry bcmheReference = (MethodEntry)bcmhe.getReference();
               bcclassentry = bcmheReference.getClassEntry();
               bcnameandtypeentry = bcmheReference.getNameAndTypeEntry();
               indexvermethodentry = constantPool.findMethodEntry(bcclassentry.getNameEntry().getValue(), bcnameandtypeentry.getNameEntry().getValue(), bcnameandtypeentry.getDescriptorEntry().getValue(), true);
            } else if (bcreferencekind == 8) {
               InterfaceMethodEntry bcmheReference = (InterfaceMethodEntry)bcmhe.getReference();
               bcclassentry = bcmheReference.getClassEntry();
               bcnameandtypeentry = bcmheReference.getNameAndTypeEntry();
               indexvermethodentry = constantPool.findInterfaceMethodEntry(bcclassentry.getNameEntry().getValue(), bcnameandtypeentry.getNameEntry().getValue(), bcnameandtypeentry.getDescriptorEntry().getValue(), true);
            }

            MethodHandleEntry vermhe = new MethodHandleEntry(bcreferencekind, indexvermethodentry);
            int indexvermhe = constantPool.indexOf(vermhe);
            if (indexvermhe == 0) {
               constantPool.addEntry(vermhe);
            }

            for(int j = 0; j < bme.getNumBootstrapArguments(); ++j) {
               int type = bcentries[j].getType();
               switch (type) {
                  case 1:
                     UTF8Entry bcutf8 = (UTF8Entry)bcentries[j];
                     int indexutf8 = constantPool.findUTF8Entry(bcutf8.getValue(), true);
                     arguments[j] = indexutf8;
                     break;
                  case 2:
                  case 13:
                  case 14:
                  case 17:
                  default:
                     throw new IllegalArgumentException("type = " + type);
                  case 3:
                     IntEntry bcseint = (IntEntry)bcentries[j];
                     int indexint = constantPool.findIntEntry(bcseint.getValue(), true);
                     arguments[j] = indexint;
                     break;
                  case 4:
                     FloatEntry bcsefloat = (FloatEntry)bcentries[j];
                     int indexfloat = constantPool.findFloatEntry(bcsefloat.getValue(), true);
                     arguments[j] = indexfloat;
                     break;
                  case 5:
                     LongEntry bcselong = (LongEntry)bcentries[j];
                     int indexlong = constantPool.findLongEntry(bcselong.getValue(), true);
                     arguments[j] = indexlong;
                     break;
                  case 6:
                     DoubleEntry bcsedouble = (DoubleEntry)bcentries[j];
                     int indexdouble = constantPool.findDoubleEntry(bcsedouble.getValue(), true);
                     arguments[j] = indexdouble;
                     break;
                  case 7:
                     ClassEntry bcseclass = (ClassEntry)bcentries[j];
                     int indexclass = constantPool.findClassEntry(bcseclass.getNameEntry().getValue(), true);
                     arguments[j] = indexclass;
                     break;
                  case 8:
                     StringEntry bcsestring = (StringEntry)bcentries[j];
                     int indexstring = constantPool.findStringEntry(bcsestring.getStringEntry().getValue(), true);
                     arguments[j] = indexstring;
                     break;
                  case 9:
                     FieldEntry bcsefiled = (FieldEntry)bcentries[j];
                     int indexfield = constantPool.findFieldEntry(bcsefiled.getClassEntry().getNameEntry().getValue(), bcsefiled.getNameAndTypeEntry().getNameEntry().getValue(), bcsefiled.getNameAndTypeEntry().getDescriptorEntry().getValue(), true);
                     arguments[j] = indexfield;
                     break;
                  case 10:
                     MethodEntry bcsemethod = (MethodEntry)bcentries[j];
                     int indexmethod = constantPool.findMethodEntry(bcsemethod.getClassEntry().getNameEntry().getValue(), bcsemethod.getNameAndTypeEntry().getNameEntry().getValue(), bcsemethod.getNameAndTypeEntry().getDescriptorEntry().getValue(), true);
                     arguments[j] = indexmethod;
                     break;
                  case 11:
                     InterfaceMethodEntry interfaceMethodEntry = (InterfaceMethodEntry)bcentries[j];
                     int indexinterface = constantPool.findInterfaceMethodEntry(interfaceMethodEntry.getClassEntry().getNameEntry().getValue(), interfaceMethodEntry.getNameAndTypeEntry().getNameEntry().getValue(), interfaceMethodEntry.getNameAndTypeEntry().getDescriptorEntry().getValue(), true);
                     arguments[j] = indexinterface;
                  case 12:
                     NameAndTypeEntry nameAndTypeEntry = (NameAndTypeEntry)bcentries[j];
                     int indexnameandtype = constantPool.findNameAndTypeEntry(nameAndTypeEntry.getNameEntry().getValue(), nameAndTypeEntry.getDescriptorEntry().getValue(), true);
                     arguments[j] = indexnameandtype;
                  case 15:
                     MethodHandleEntry methodHandleEntry = (MethodHandleEntry)bcentries[j];
                     int kind = methodHandleEntry.getReferenceKind();
                     MethodEntry reference = (MethodEntry)methodHandleEntry.getReference();
                     int indexreference = constantPool.findMethodEntry(reference.getClassEntry().getNameEntry().getValue(), reference.getNameAndTypeEntry().getNameEntry().getValue(), reference.getNameAndTypeEntry().getDescriptorEntry().getValue(), true);
                     MethodHandleEntry handleEntry = new MethodHandleEntry(kind, indexreference);
                     int indexhandle = constantPool.indexOf(handleEntry);
                     if (indexhandle != 0) {
                        arguments[j] = indexhandle;
                     } else {
                        constantPool.addEntry(handleEntry);
                        arguments[j] = constantPool.indexOf(handleEntry);
                     }
                     break;
                  case 16:
                     MethodTypeEntry methodTypeEntry = (MethodTypeEntry)bcentries[j];
                     int utf8 = constantPool.findUTF8Entry(methodTypeEntry.getMethodDescriptorEntry().getValue(), true);
                     MethodTypeEntry newtype = new MethodTypeEntry(utf8);
                     int indextype = constantPool.indexOf(newtype);
                     if (indextype != 0) {
                        arguments[j] = indextype;
                     } else {
                        constantPool.addEntry(newtype);
                        arguments[j] = constantPool.indexOf(newtype);
                     }
                     break;
                  case 18:
                     InvokeDynamicEntry invokeDynamicEntry = (InvokeDynamicEntry)bcentries[j];
                     int indexdynamic = constantPool.findInvokeDynamicEntry(invokeDynamicEntry.getBootstrapMethodAttrIndex(), invokeDynamicEntry.getNameAndTypeEntry().getNameEntry().getValue(), invokeDynamicEntry.getNameAndTypeEntry().getDescriptorEntry().getValue(), true);
                     arguments[j] = indexdynamic;
               }
            }

            verBootstrapMethodElements[i].setBootstrapMethodRef(constantPool.indexOf(vermhe));
            verBootstrapMethodElements[i].setBootstrapMethodAttribute(verbootstrapMethods);
            verBootstrapMethodElements[i].setBootstrapArgumentIndices(arguments);
            verBootstrapMethodElements[i].setBootstrapMethod(vermhe);
         }

         verbootstrapMethods.setBootstrapMethods(verBootstrapMethodElements);
      }
   }

   private int getNextByteIndex(Code code) {
      int next = code.size();
      if (code.hasNext()) {
         next = code.next().getByteIndex();
         code.previous();
      }

      return next;
   }

   private boolean isSuperOrThisCall(MethodInstruction mi) {
      String declarerName = mi.getMethodDeclarerName();
      return declarerName.equals(this._meta.getSuperclass()) || declarerName.equals(this._meta.getName());
   }

   private boolean replaceDirectFieldAccess(Code code, Instruction replace, boolean get, boolean stat) {
      FieldInstruction fi = (FieldInstruction)code.previous();
      code.next();
      String clsName = fi.getFieldDeclarerName();
      String name = fi.getFieldName();
      String type = fi.getFieldTypeName();
      if (!stat && name.equals("this$0")) {
         return false;
      } else {
         ClassMetaData declarer = this._repos.getMetaData(clsName);
         FieldMetaData field;
         if (declarer == null) {
            declarer = this.findRedefinableSubclass(clsName);
            if (declarer == null) {
               return false;
            }

            field = declarer.getNonredefinableSuperclassField(clsName, name, type, stat);
         } else {
            field = declarer.getField(name, type, stat);
         }

         if (field == null) {
            return false;
         } else if (declarer.isInterface()) {
            return false;
         } else {
            Instruction placeholder = code.set(this._template.constant().setValue(field.getIndex()));
            MethodInstruction mi = (MethodInstruction)code.add(replace);
            String accessorType = toFieldAccessorTypeName(type);
            if (get) {
               mi.setMethod(declarer.getName(), toFieldGetterName(accessorType, stat), accessorType, new String[]{"int"});
               if (!accessorType.equals(type)) {
                  code.checkcast().setType(type);
               }
            } else {
               mi.setMethod(declarer.getName(), toFieldSetterName(accessorType, stat), "void", new String[]{accessorType, "int"});
            }

            this.adjustExceptionHandlerTryEnd(code, placeholder, mi);
            return true;
         }
      }
   }

   private ClassMetaData findRedefinableSubclass(String clsName) {
      for(ClassMetaData meta = this._meta; meta != null; meta = meta.getSuperclassMetaData()) {
         if (meta.hasNonredefinableSuperclass(clsName)) {
            return meta;
         }
      }

      return null;
   }

   private static String toFieldGetterName(String accessorType, boolean stat) {
      accessorType = accessorType.substring(accessorType.lastIndexOf(46) + 1);
      StringBuffer buf = new StringBuffer();
      buf.append("bea").append("Fetch");
      if (stat) {
         buf.append("Static");
      }

      buf.append(capitalize(accessorType)).append("Field");
      return buf.toString();
   }

   private static String toFieldSetterName(String accessorType, boolean stat) {
      accessorType = accessorType.substring(accessorType.lastIndexOf(46) + 1);
      StringBuffer buf = new StringBuffer();
      buf.append("bea").append("Store");
      if (stat) {
         buf.append("Static");
      }

      buf.append(capitalize(accessorType)).append("Field");
      return buf.toString();
   }

   private static String toFieldAccessorTypeName(String typeName) {
      return PRIMITIVE_TYPE_NAMES.contains(typeName) ? typeName : Object.class.getName();
   }

   private static String toAddedFieldsGetterName(String accessorType) {
      accessorType = accessorType.substring(accessorType.lastIndexOf(46) + 1);
      return "fetch" + capitalize(accessorType) + "Field";
   }

   private static String toAddedFieldsSetterName(String accessorType) {
      accessorType = accessorType.substring(accessorType.lastIndexOf(46) + 1);
      return "store" + capitalize(accessorType) + "Field";
   }

   private boolean replaceDirectMethodAccess(Code code, String invokeMeth, Instruction invokestat, int arr, int tmp) {
      MethodInstruction mi = (MethodInstruction)code.previous();
      code.next();
      String declarerName = mi.getMethodDeclarerName();
      ClassMetaData declarer = this._repos.getMetaData(declarerName);
      if (declarer == null) {
         return false;
      } else {
         String returnType = mi.getMethodReturnName();
         String[] params = mi.getMethodParamNames();
         AccessType accessType = "beaInvokeStatic".equals(invokeMeth) ? AccessType.STATIC : AccessType.NONPRIVATE;
         MethodMetaData priMethod = declarer.getMethod(mi.getMethodName(), returnType, params, AccessType.PRIVATE);
         MethodMetaData method = declarer.getMethod(mi.getMethodName(), returnType, params, accessType);
         if (priMethod == null || method != null && !priMethod.isAdded()) {
            if (method == null) {
               String newMethodName = "beaAccessSuperFinalProtected" + mi.getMethodName();
               method = declarer.getMethod(newMethodName, returnType, params, accessType);
               if (method == null) {
                  return false;
               } else {
                  mi = (MethodInstruction)code.set(this._template.invokevirtual());
                  mi.setMethod(newMethodName, returnType, params);
                  return false;
               }
            } else if (!method.isAdded()) {
               return false;
            } else {
               mi = (MethodInstruction)this._template.add(mi);
               Instruction placeholder = code.set(this._template.nop());
               if (params.length != 0) {
                  this.newParamArray(code, params, arr, tmp);
               } else {
                  code.constant().setNull();
               }

               String targetName = declarerName;
               if (declarer.isInterface()) {
                  code.astore().setLocal(arr);
                  code.checkcast().setType(Redefinable.class);
                  code.aload().setLocal(arr);
                  targetName = Redefinable.class.getName();
               }

               code.constant().setValue(method.getIndex());
               mi.setMethod(targetName, invokeMeth, Object.class.getName(), new String[]{Object[].class.getName(), "int"});
               Instruction newInsn = code.add(mi);
               this.adjustExceptionHandlerTryEnd(code, placeholder, newInsn);
               if ("void".equals(returnType)) {
                  code.pop();
               } else if (!this.unpack(code, returnType) && !Object.class.getName().equals(returnType)) {
                  code.checkcast().setType(returnType);
               }

               return true;
            }
         } else {
            return this.replacePrivateMethodAccess(code, priMethod, invokestat, mi, declarer, arr, tmp);
         }
      }
   }

   private boolean replacePrivateMethodAccess(Code code, MethodMetaData method, Instruction invokestat, MethodInstruction mi, ClassMetaData declarer, int arr, int tmp) {
      boolean sup = declarer != this._meta;
      boolean hasNestHost = false;
      String declarerName = declarer.getName();
      NestHost nestHost = this._bc.getNestHost(false);
      if (nestHost != null) {
         hasNestHost = declarerName.equals(nestHost.getNestHostName().replaceAll("/", "\\."));
      }

      if (!sup && !hasNestHost && !method.isAdded()) {
         mi = (MethodInstruction)code.set(invokestat);
         mi.setMethod("beaAccess" + method.getName(), method.getReturnType(), this.toStaticMethodParameters(method.getParameterTypes()));
         return false;
      } else if (hasNestHost && !method.isAdded()) {
         mi = (MethodInstruction)code.set(invokestat);
         mi.setMethod(declarerName, "beaAccess" + method.getName(), method.getReturnType(), this.toStaticMethodParameters(method.getParameterTypes(), declarerName));
         return false;
      } else {
         this.changePrivateMethodInvocation(code, mi, method, invokestat, sup, arr, tmp);
         return true;
      }
   }

   private void adjustExceptionHandlerTryEnd(Code code, Instruction oldEnd, Instruction newEnd) {
      ExceptionHandler[] var4 = code.getExceptionHandlers();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ExceptionHandler handler = var4[var6];
         if (handler.getTryEnd() == oldEnd) {
            handler.setTryEnd(newEnd);
         }
      }

   }

   private boolean replaceDirectSpecialMethodAccess(Code code, Instruction invokestat, int arr, int tmp) {
      MethodInstruction mi = (MethodInstruction)code.previous();
      code.next();
      ClassMetaData declarer = this._repos.getMetaData(mi.getMethodDeclarerName());
      boolean sup = declarer != this._meta;
      String name = mi.getMethodName();
      String returnType = mi.getMethodReturnName();
      String[] params = mi.getMethodParamNames();
      MethodMetaData method;
      if (declarer == null) {
         method = this.newMethodMetaData(name, returnType, params);
         if (this._nonredefSuperInvocations == null) {
            this._nonredefSuperInvocations = new TreeSet(MEMBER_COMPARATOR);
         }

         this._nonredefSuperInvocations.add(method);
      } else {
         method = declarer.getMethod(name, returnType, params, sup ? AccessType.NONPRIVATE : AccessType.PRIVATE);
         if (method == null) {
            return false;
         }
      }

      if (!sup && !method.isAdded()) {
         mi = (MethodInstruction)code.set(invokestat);
         mi.setMethod("beaAccess" + method.getName(), method.getReturnType(), this.toStaticMethodParameters(method.getParameterTypes()));
         return false;
      } else {
         this.changePrivateMethodInvocation(code, mi, method, invokestat, sup, arr, tmp);
         return true;
      }
   }

   private void changePrivateMethodInvocation(Code code, MethodInstruction mi, MethodMetaData method, Instruction invokestat, boolean sup, int arr, int tmp) {
      String returnType = mi.getMethodReturnName();
      String[] params = mi.getMethodParamNames();
      Instruction placeholder = code.set(this._template.nop());
      if (params.length != 0) {
         this.newParamArray(code, params, arr, tmp);
      } else {
         code.constant().setNull();
      }

      code.constant().setValue(method.getIndex());
      String invokeMeth = sup ? "beaInvokeSuper" : "beaInvokePrivate";
      mi = (MethodInstruction)code.add(invokestat);
      mi.setMethod(invokeMeth, Object.class.getName(), new String[]{this._meta.getName(), Object[].class.getName(), "int"});
      this.adjustExceptionHandlerTryEnd(code, placeholder, mi);
      if ("void".equals(returnType)) {
         code.pop();
      } else if (!this.unpack(code, returnType) && !Object.class.getName().equals(returnType)) {
         code.checkcast().setType(returnType);
      }

   }

   private MethodMetaData newMethodMetaData(String name, String ret, String[] params) {
      String desc = this._bc.getProject().getNameCache().getDescriptor(ret, params);
      MethodMetaData meta = new MethodMetaData((ClassMetaData)null, name, ret, params, desc, 1);
      meta.setIndex(this._repos.getMethodIndex(meta));
      return meta;
   }

   private boolean replaceDirectConstruction(Code code, int arr, int tmp) {
      MethodInstruction mi = (MethodInstruction)code.previous();
      code.next();
      String declarerName = mi.getMethodDeclarerName();
      ClassMetaData declarer = this._repos.getMetaData(declarerName);
      if (declarer == null) {
         return false;
      } else {
         String[] params = mi.getMethodParamNames();
         ConstructorMetaData cons = declarer.getConstructor(params);
         if (cons != null && cons.isAdded()) {
            mi = (MethodInstruction)this._template.add(mi);
            code.set(this._template.nop());
            if (params.length != 0) {
               this.newParamArray(code, params, arr, tmp);
            } else {
               code.constant().setNull();
            }

            code.constant().setValue(cons.getIndex());
            code.constant().setNull();
            mi.setMethod(declarer.getName(), "<init>", "void", new String[]{Object[].class.getName(), "int", GENERIC_CONS_ARG});
            code.add(mi);
            return true;
         } else {
            return false;
         }
      }
   }

   private void newParamArray(Code code, String[] params, int arr, int tmp) {
      for(int i = params.length - 1; i >= 0; --i) {
         this.pack(code, params[i]);
         code.astore().setLocal(tmp);
         if (i == params.length - 1) {
            code.constant().setValue(params.length);
            code.anewarray().setType(Object.class);
            code.astore().setLocal(arr);
         }

         code.aload().setLocal(arr);
         code.constant().setValue(i);
         code.aload().setLocal(tmp);
         code.aastore();
      }

      code.aload().setLocal(arr);
   }

   private boolean pack(Code code, String type) {
      if ("boolean".equals(type)) {
         code.invokestatic().setMethod(Boolean.class, "valueOf", Boolean.class, new Class[]{Boolean.TYPE});
      } else if ("byte".equals(type)) {
         code.invokestatic().setMethod(Byte.class, "valueOf", Byte.class, new Class[]{Byte.TYPE});
      } else if ("char".equals(type)) {
         code.invokestatic().setMethod(Character.class, "valueOf", Character.class, new Class[]{Character.TYPE});
      } else if ("double".equals(type)) {
         code.invokestatic().setMethod(Double.class, "valueOf", Double.class, new Class[]{Double.TYPE});
      } else if ("float".equals(type)) {
         code.invokestatic().setMethod(Float.class, "valueOf", Float.class, new Class[]{Float.TYPE});
      } else if ("int".equals(type)) {
         code.invokestatic().setMethod(Integer.class, "valueOf", Integer.class, new Class[]{Integer.TYPE});
      } else if ("long".equals(type)) {
         code.invokestatic().setMethod(Long.class, "valueOf", Long.class, new Class[]{Long.TYPE});
      } else {
         if (!"short".equals(type)) {
            return false;
         }

         code.invokestatic().setMethod(Short.class, "valueOf", Short.class, new Class[]{Short.TYPE});
      }

      return true;
   }

   private boolean unpack(Code code, String type) {
      if ("boolean".equals(type)) {
         code.checkcast().setType(Boolean.class);
         code.invokevirtual().setMethod(Boolean.class, "booleanValue", Boolean.TYPE, (Class[])null);
      } else if ("byte".equals(type)) {
         code.checkcast().setType(Byte.class);
         code.invokevirtual().setMethod(Byte.class, "byteValue", Byte.TYPE, (Class[])null);
      } else if ("char".equals(type)) {
         code.checkcast().setType(Character.class);
         code.invokevirtual().setMethod(Character.class, "charValue", Character.TYPE, (Class[])null);
      } else if ("double".equals(type)) {
         code.checkcast().setType(Double.class);
         code.invokevirtual().setMethod(Double.class, "doubleValue", Double.TYPE, (Class[])null);
      } else if ("float".equals(type)) {
         code.checkcast().setType(Float.class);
         code.invokevirtual().setMethod(Float.class, "floatValue", Float.TYPE, (Class[])null);
      } else if ("int".equals(type)) {
         code.checkcast().setType(Integer.class);
         code.invokevirtual().setMethod(Integer.class, "intValue", Integer.TYPE, (Class[])null);
      } else if ("long".equals(type)) {
         code.checkcast().setType(Long.class);
         code.invokevirtual().setMethod(Long.class, "longValue", Long.TYPE, (Class[])null);
      } else {
         if (!"short".equals(type)) {
            return false;
         }

         code.checkcast().setType(Short.class);
         code.invokevirtual().setMethod(Short.class, "shortValue", Short.TYPE, (Class[])null);
      }

      return true;
   }

   private void removeUnsupportedAttributes(Code code) {
      code.removeAttribute("StackMapTable");
   }

   private void addOriginalFields() {
      FieldMetaData[] var1 = this._meta.getDeclaredFields();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         FieldMetaData meta = var1[var3];
         if (!meta.isAdded() && !meta.isCurrent()) {
            this._bc.declareField(meta.getName(), meta.getType()).setAccessFlags(meta.getAccess());
         }
      }

   }

   private void fixFieldModifiers() {
      if (!this._meta.isInterface()) {
         FieldMetaData[] var1 = this._meta.getDeclaredFields();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            FieldMetaData meta = var1[var3];
            if (!meta.isAdded()) {
               String name = meta.getName();
               BCField field = this._bc.getDeclaredField(name);
               if (field != null) {
                  if (!name.equals("serialVersionUID")) {
                     field.setFinal(false);
                  }

                  field.makePublic();
               }
            }
         }

      }
   }

   private void fixConstructorModifiers() {
      BCMethod[] var1 = this._bc.getDeclaredMethods("<init>");
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         BCMethod constructor = var1[var3];
         constructor.makePublic();
      }

   }

   private void moveMethodsToVersion() {
      MethodMetaData[] var1 = this._meta.getDeclaredMethods();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         MethodMetaData meta = var1[var3];
         BCMethod method = this._bc.getDeclaredMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
         if (method != null) {
            if (meta.isStatic() != method.isStatic()) {
               method = null;
            } else if (!meta.isStatic() && meta.isPrivate() != method.isPrivate()) {
               method = null;
            }
         }

         if (method == null) {
            if (!meta.isAdded()) {
               if (this._meta.isInterface()) {
                  this.addDeletedInterfaceMethod(meta);
               } else if (meta.getOverride() == OverrideType.FINALPROTECTED_NONREDEFINABLE) {
                  this.addDelegateToSuperclassFinalProtectedMethod(meta);
               } else if (meta.getOverride() != OverrideType.NONE) {
                  this.addDelegateToSuperclassMethod(meta);
               } else if (!meta.isPrivate() && this._supMeta != null) {
                  this.addDelegateToSuperclassInvokeMethod(meta);
               } else {
                  this.addNoSuchMethodErrorMethod(meta);
                  if (!meta.isStatic() && meta.isPrivate()) {
                     this.addPrivateAccessMethod(meta);
                  }
               }
            }
         } else if (this._meta.isInterface()) {
            if (meta.isAdded()) {
               this._bc.removeDeclaredMethod(method);
            }
         } else {
            BCMethod vmethod = this._vers.declareMethod(method);
            if (meta.isStatic()) {
               vmethod.setParams(meta.getParameterTypes());
            } else {
               vmethod.setParams(this.toStaticMethodParameters(meta.getParameterTypes()));
            }

            vmethod.setStatic(true);
            vmethod.makePublic();
            if (vmethod.isAbstract()) {
               this.addAbstractMethodHandler(vmethod, meta);
            }

            if (!meta.isStatic() && meta.isSynchronized()) {
               this.synchronizeInstanceMethod(vmethod);
            }

            if (meta.isAdded()) {
               this._bc.removeDeclaredMethod(method);
            } else {
               this.invokeVersionMethod(meta, method);
               if (meta.isPrivate() && !meta.isStatic()) {
                  this.addPrivateAccessMethod(meta);
               } else {
                  method.makePublic();
               }

               method.setFinal(false);
            }
         }
      }

   }

   private void synchronizeInstanceMethod(BCMethod vmethod) {
      String origName = vmethod.getName();
      String methodName = origName + "$sync";
      vmethod.setSynchronized(false);
      vmethod.setName(methodName);
      vmethod.setSynthetic(true);
      BCMethod method = this._vers.declareMethod(vmethod);
      String[] paramTypes = method.getParamNames();
      String returnType = method.getReturnName();
      int paramCount = paramTypes != null ? paramTypes.length : 0;
      method.setName(origName);
      method.removeCode();
      method.setFinal(false);
      method.setAbstract(false);
      method.setStatic(true);
      method.setSynchronized(false);
      Code code = method.getCode(true);
      int monitor = code.getMaxLocals();
      int exception = monitor + 1;
      code.aload().setParam(0);
      code.dup();
      code.astore().setLocal(monitor);
      code.monitorenter();
      Instruction tryStart = code.nop();

      for(int i = 0; i < paramCount; ++i) {
         code.xload().setParam(i);
      }

      code.invokestatic().setMethod(this._vers.getName(), methodName, returnType, paramTypes);
      code.aload().setLocal(monitor);
      code.monitorexit();
      Instruction tryEnd = code.xreturn().setType(returnType);
      Instruction handlerStart = code.astore().setLocal(exception);
      code.aload().setLocal(monitor);
      code.monitorexit();
      Instruction tryErr = code.aload().setLocal(exception);
      code.athrow();
      String excType = null;
      code.addExceptionHandler(tryStart, tryEnd, handlerStart, (String)excType);
      code.addExceptionHandler(handlerStart, tryErr, handlerStart, (String)excType);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addDeletedInterfaceMethod(MethodMetaData meta) {
      BCMethod method = this._bc.declareMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      method.setAccessFlags(meta.getAccess());
      method.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         method.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

   }

   private void addNoSuchMethodErrorMethod(MethodMetaData meta) {
      BCMethod method = this._bc.declareMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      method.setAccessFlags(meta.getAccess());
      method.setFinal(false);
      method.setAbstract(false);
      method.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         method.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      if (meta.isStatic()) {
         method.makePublic();
      }

      Code code = method.getCode(true);
      throwError(code, NoSuchMethodError.class, getMethodDescriptor(meta));
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addDelegateToSuperclassFinalProtectedMethod(MethodMetaData meta) {
      BCMethod method = this._bc.declareMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      String realMethodName = meta.getName().substring("beaAccessSuperFinalProtected".length());
      method.makeProtected();
      method.setFinal(false);
      method.setSynthetic(true);
      Code code = method.getCode(true);
      code.aload().setThis();

      for(int i = 0; i < meta.getParameterTypes().length; ++i) {
         code.xload().setParam(i);
      }

      code.invokespecial().setMethod(this._meta.getSuperclass(), realMethodName, meta.getReturnType(), meta.getParameterTypes());
      code.xreturn().setType(meta.getReturnType());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addDelegateToSuperclassMethod(MethodMetaData meta) {
      BCMethod method = this._bc.declareMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      method.setAccessFlags(meta.getAccess());
      method.setAbstract(false);
      method.setFinal(false);
      method.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         method.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = method.getCode(true);
      if (!meta.isStatic()) {
         code.aload().setThis();
      }

      for(int i = 0; i < meta.getParameterTypes().length; ++i) {
         code.xload().setParam(i);
      }

      if (meta.isStatic()) {
         code.invokestatic().setMethod(this._meta.getSuperclass(), meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      } else {
         code.invokespecial().setMethod(this._meta.getSuperclass(), meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      }

      code.xreturn().setType(meta.getReturnType());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addDelegateToSuperclassInvokeMethod(MethodMetaData meta) {
      BCMethod method = this._bc.declareMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      method.setAccessFlags(meta.getAccess());
      method.setAbstract(false);
      method.setFinal(false);
      method.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         method.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = method.getCode(true);
      if (!meta.isStatic()) {
         code.aload().setThis();
      }

      String[] params = meta.getParameterTypes();

      int arr;
      for(arr = 0; arr < params.length; ++arr) {
         code.xload().setParam(arr);
      }

      if (params.length != 0) {
         arr = code.getNextLocalsIndex();
         this.newParamArray(code, params, arr, arr + 1);
      } else {
         code.constant().setNull();
      }

      code.constant().setValue(meta.getIndex());
      if (meta.isStatic()) {
         code.invokestatic().setMethod(this._supMeta.getName(), "beaInvokeStatic", Object.class.getName(), new String[]{Object[].class.getName(), "int"});
      } else {
         code.invokespecial().setMethod(this._supMeta.getName(), "beaInvoke", Object.class.getName(), new String[]{Object[].class.getName(), "int"});
      }

      String ret = meta.getReturnType();
      if ("void".equals(ret)) {
         code.pop();
      } else if (!this.unpack(code, ret) && !Object.class.getName().equals(ret)) {
         code.checkcast().setType(ret);
      }

      code.xreturn().setType(ret);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addPrivateAccessMethod(MethodMetaData meta) {
      BCMethod method = this._bc.declareMethod("beaAccess" + meta.getName(), meta.getReturnType(), this.toStaticMethodParameters(meta.getParameterTypes()));
      method.makePackage();
      method.setStatic(true);
      method.setSynthetic(true);
      method.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         method.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = method.getCode(true);

      for(int i = 0; i < meta.getParameterTypes().length + 1; ++i) {
         code.xload().setParam(i);
      }

      code.invokespecial().setMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes());
      code.xreturn().setType(meta.getReturnType());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addAbstractMethodHandler(BCMethod vmethod, MethodMetaData meta) {
      vmethod.removeCode();
      Code code = vmethod.getCode(true);
      vmethod.setAbstract(false);
      throwError(code, AbstractMethodError.class, getMethodDescriptor(meta));
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private static String getMethodDescriptor(MethodMetaData meta) {
      String pstr = getParameterDescriptor(meta.getParameterTypes());
      return meta.getReturnType() + " " + meta.getName() + "(" + pstr + ")";
   }

   private static String getParameterDescriptor(String[] params) {
      if (params.length == 0) {
         return "";
      } else if (params.length == 1) {
         return params[0];
      } else {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < params.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append(params[i]);
         }

         return buf.toString();
      }
   }

   private static String getFieldDescriptor(FieldMetaData meta) {
      return meta.getType() + " " + meta.getName();
   }

   private static Instruction throwError(Code code, Class err, String msg) {
      Instruction ret = code.anew().setType(err);
      code.dup();
      Class[] args = null;
      if (msg != null) {
         code.constant().setValue(msg);
         args = new Class[]{String.class};
      }

      code.invokespecial().setMethod(err, "<init>", Void.TYPE, args);
      code.athrow();
      return ret;
   }

   private void invokeVersionMethod(MethodMetaData meta, BCMethod method) {
      method.removeCode();
      Code code = method.getCode(true);
      method.setAbstract(false);
      method.setFinal(false);
      if (!meta.isStatic()) {
         code.aload().setThis();
      }

      String[] params = meta.getParameterTypes();

      for(int i = 0; i < params.length; ++i) {
         code.xload().setParam(i);
      }

      if (!meta.isStatic()) {
         params = this.toStaticMethodParameters(params);
      }

      code.invokestatic().setMethod(this._vers.getName(), meta.getName(), meta.getReturnType(), params);
      code.xreturn().setType(meta.getReturnType());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private String[] toStaticMethodParameters(String[] params) {
      return this.toStaticMethodParameters(params, this._meta.getName());
   }

   private String[] toStaticMethodParameters(String[] params, String metaName) {
      String[] vparams = new String[params.length + 1];
      vparams[0] = metaName;
      System.arraycopy(params, 0, vparams, 1, params.length);
      return vparams;
   }

   private void moveAddedStaticFieldsToVersion() {
      FieldMetaData[] var1 = this._meta.getDeclaredFields();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         FieldMetaData meta = var1[var3];
         if (meta.isCurrent() && meta.isStatic() && meta.isAdded()) {
            BCField copy = this._vers.declareField(meta.getName(), meta.getType());
            copy.setAccessFlags(meta.getAccess());
            copy.makePublic();
            this._bc.removeDeclaredField(meta.getName());
         }
      }

   }

   private void addFieldAccessCode() {
      BCField addedFields = this._bc.declareField("beaAddedFields", AddedFields.class);
      addedFields.setFinal(true);
      addedFields.setTransient(true);
      Instruction constructSup = this._template.invokespecial().setMethodDeclarer(this._meta.getSuperclass()).setMethodName("<init>");
      BCMethod[] var3 = this._bc.getDeclaredMethods("<init>");
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BCMethod constructor = var3[var5];
         Code code = constructor.getCode(false);
         if (code.searchForward(constructSup)) {
            code.aload().setThis();
            code.anew().setType(AddedFields.class);
            code.dup();
            code.constant().setValue(this._meta.getFirstDeclaredAddedFieldIndex());
            code.invokespecial().setMethod(AddedFields.class, "<init>", Void.TYPE, new Class[]{Integer.TYPE});
            code.putfield().setField(addedFields);
            code.setMaxStack(Math.max(code.getMaxStack(), 4));
         }
      }

      FieldMetaData[] fields = sort(this._meta.getDeclaredFields(), this._meta.getNonredefinableSuperclassFields());
      Iterator var9 = PRIMITIVE_TYPE_NAMES.iterator();

      while(var9.hasNext()) {
         String type = (String)var9.next();
         this.addMemberFieldAccessMethod(type, fields, addedFields, true);
         this.addMemberFieldAccessMethod(type, fields, addedFields, false);
         this.addStaticFieldAccessMethod(type, fields, true);
         this.addStaticFieldAccessMethod(type, fields, false);
      }

      this.addMemberFieldAccessMethod(Object.class.getName(), fields, addedFields, true);
      this.addMemberFieldAccessMethod(Object.class.getName(), fields, addedFields, false);
      this.addStaticFieldAccessMethod(Object.class.getName(), fields, true);
      this.addStaticFieldAccessMethod(Object.class.getName(), fields, false);
   }

   private void addMemberFieldAccessMethod(String type, FieldMetaData[] fields, BCField addedFields, boolean getter) {
      String name;
      String addedFieldsName;
      String ret;
      String[] args;
      byte param;
      if (getter) {
         name = toFieldGetterName(type, false);
         addedFieldsName = toAddedFieldsGetterName(type);
         ret = type;
         args = new String[]{"int"};
         param = 0;
      } else {
         name = toFieldSetterName(type, false);
         addedFieldsName = toAddedFieldsSetterName(type);
         ret = "void";
         args = new String[]{type, "int"};
         param = 1;
      }

      BCMethod accessor = this._bc.declareMethod(name, ret, args);
      accessor.makePublic();
      accessor.setSynthetic(true);
      accessor.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         accessor.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = accessor.getCode(true);
      code.iload().setParam(param);
      LookupSwitchInstruction lookup = code.lookupswitch();
      FieldMetaData[] var13 = fields;
      int i = fields.length;

      for(int var15 = 0; var15 < i; ++var15) {
         FieldMetaData meta = var13[var15];
         if (meta.isCurrent() && !meta.isStatic() && type.equals(toFieldAccessorTypeName(meta.getType()))) {
            lookup.addCase(meta.getIndex(), code.aload().setThis());
            if (meta.isAdded()) {
               code.getfield().setField(addedFields);
               if (!getter) {
                  code.xload().setParam(0);
               }

               code.constant().setValue(meta.getIndex());
               code.invokevirtual().setMethod(AddedFields.class.getName(), addedFieldsName, ret, args);
               this._bc.removeDeclaredField(meta.getName());
            } else if (!getter) {
               code.xload().setParam(0);
               if (!type.equals(meta.getType())) {
                  code.checkcast().setType(meta.getType());
               }

               code.putfield().setField(meta.getName(), meta.getType());
            } else {
               code.getfield().setField(meta.getName(), meta.getType());
            }

            code.xreturn().setType(ret);
         }
      }

      Object def;
      if (this._supMeta != null) {
         def = code.aload().setThis();

         for(i = 0; i < args.length; ++i) {
            code.xload().setParam(i);
         }

         code.invokespecial().setMethod(this._supMeta.getName(), name, ret, args);
         code.xreturn().setType(ret);
      } else {
         def = throwError(code, NoSuchFieldError.class, (String)null);
      }

      lookup.setDefaultTarget((Instruction)def);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addStaticFieldAccessMethod(String type, FieldMetaData[] fields, boolean getter) {
      String name;
      String ret;
      String[] args;
      byte param;
      if (getter) {
         name = toFieldGetterName(type, true);
         ret = type;
         args = new String[]{"int"};
         param = 0;
      } else {
         name = toFieldSetterName(type, true);
         ret = "void";
         args = new String[]{type, "int"};
         param = 1;
      }

      BCMethod accessor = this._bc.declareMethod(name, ret, args);
      accessor.makePublic();
      accessor.setStatic(true);
      accessor.setSynthetic(true);
      accessor.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         accessor.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = accessor.getCode(true);
      code.iload().setParam(param);
      LookupSwitchInstruction lookup = code.lookupswitch();
      FieldMetaData[] var11 = fields;
      int i = fields.length;

      for(int var13 = 0; var13 < i; ++var13) {
         FieldMetaData meta = var11[var13];
         if (meta.isCurrent() && meta.isStatic() && type.equals(toFieldAccessorTypeName(meta.getType()))) {
            String dec = meta.isAdded() ? this._vers.getName() : meta.getDeclarer();
            Object ins;
            if (!getter) {
               ins = code.xload().setParam(0);
               if (!type.equals(meta.getType())) {
                  code.checkcast().setType(meta.getType());
               }

               code.putstatic().setField(dec, meta.getName(), meta.getType());
            } else {
               ins = code.getstatic().setField(dec, meta.getName(), meta.getType());
            }

            code.xreturn().setType(ret);
            lookup.addCase(meta.getIndex(), (Instruction)ins);
         }
      }

      Instruction def;
      if (this._supMeta != null) {
         def = code.nop();

         for(i = 0; i < args.length; ++i) {
            code.xload().setParam(i);
         }

         code.invokestatic().setMethod(this._supMeta.getName(), name, ret, args);
         code.xreturn().setType(ret);
      } else {
         def = throwError(code, NoSuchFieldError.class, (String)null);
      }

      lookup.setDefaultTarget(def);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private static FieldMetaData[] sort(FieldMetaData[] f1, FieldMetaData[] f2) {
      if (f2.length == 0) {
         return f1;
      } else if (f1.length == 0) {
         return f2;
      } else {
         FieldMetaData[] copy = new FieldMetaData[f1.length + f2.length];
         System.arraycopy(f1, 0, copy, 0, f1.length);
         System.arraycopy(f2, 0, copy, f1.length, f2.length);
         Arrays.sort(copy, MEMBER_COMPARATOR);
         return copy;
      }
   }

   private void addInvokeMethods() {
      this.addInvokeSuper();
      MethodMetaData[] methods = sort(this._meta.getDeclaredMethods());
      this.addInvoke(methods, false);
      this.addInvoke(methods, true);
      this.addInvokeStatic(methods);
   }

   private void addInvokeSuper() {
      BCMethod invokesuper = this._bc.declareMethod("beaInvokeSuper", Object.class.getName(), new String[]{this._meta.getName(), Object[].class.getName(), "int"});
      invokesuper.makePackage();
      invokesuper.setStatic(true);
      invokesuper.setSynthetic(true);
      invokesuper.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      Code code = invokesuper.getCode(true);
      code.iload().setParam(2);
      LookupSwitchInstruction lookup = code.lookupswitch();
      if (this._nonredefSuperInvocations != null) {
         for(Iterator var4 = this._nonredefSuperInvocations.iterator(); var4.hasNext(); code.areturn()) {
            MethodMetaData meta = (MethodMetaData)var4.next();
            lookup.addCase(meta.getIndex(), code.aload().setParam(0));
            String[] params = meta.getParameterTypes();

            for(int i = 0; i < params.length; ++i) {
               code.aload().setParam(1);
               code.constant().setValue(i);
               code.aaload();
               if (!this.unpack(code, params[i]) && !Object.class.getName().equals(params[i])) {
                  code.checkcast().setType(params[i]);
               }
            }

            code.invokespecial().setMethod(this._meta.getSuperclass(), meta.getName(), meta.getReturnType(), params);
            if ("void".equals(meta.getReturnType())) {
               code.constant().setNull();
            } else {
               this.pack(code, meta.getReturnType());
            }
         }
      }

      Object def;
      if (this._supMeta != null) {
         def = code.aload().setParam(0);
         code.aload().setParam(1);
         code.iload().setParam(2);
         code.invokespecial().setMethod(this._supMeta.getName(), "beaInvoke", Object.class.getName(), new String[]{Object[].class.getName(), "int"});
         code.areturn();
      } else {
         def = throwError(code, NoSuchMethodError.class, (String)null);
      }

      lookup.setDefaultTarget((Instruction)def);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addInvoke(MethodMetaData[] methods, boolean priv) {
      BCMethod invoke;
      String[] args;
      byte arr;
      byte idx;
      if (priv) {
         args = new String[]{this._meta.getName(), Object[].class.getName(), "int"};
         invoke = this._bc.declareMethod("beaInvokePrivate", Object.class.getName(), args);
         invoke.setStatic(true);
         arr = 1;
         idx = 2;
      } else {
         args = new String[]{Object[].class.getName(), "int"};
         invoke = this._bc.declareMethod("beaInvoke", Object.class.getName(), args);
         arr = 0;
         idx = 1;
      }

      invoke.setSynthetic(true);
      invoke.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         invoke.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = invoke.getCode(true);
      code.iload().setParam(idx);
      LookupSwitchInstruction lookup = code.lookupswitch();
      MethodMetaData[] var9 = methods;
      int var10 = methods.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         MethodMetaData meta = var9[var11];
         if (priv == meta.isPrivate() && !meta.isStatic() && (meta.isCurrent() || meta.getOverride() == OverrideType.NONREDEFINABLE)) {
            lookup.addCase(meta.getIndex(), code.aload().setThis());
            String[] params = meta.getParameterTypes();

            for(int i = 0; i < params.length; ++i) {
               code.aload().setParam(arr);
               code.constant().setValue(i);
               code.aaload();
               if (!this.unpack(code, params[i]) && !Object.class.getName().equals(params[i])) {
                  code.checkcast().setType(params[i]);
               }
            }

            if (meta.isCurrent()) {
               code.invokestatic().setMethod(this._vers.getName(), meta.getName(), meta.getReturnType(), this.toStaticMethodParameters(params));
            } else {
               code.invokespecial().setMethod(meta.getName(), meta.getReturnType(), params);
            }

            if ("void".equals(meta.getReturnType())) {
               code.constant().setNull();
            } else {
               this.pack(code, meta.getReturnType());
            }

            code.areturn();
         }
      }

      Object def;
      if (!priv && this._supMeta != null) {
         def = code.aload().setThis();
         code.aload().setParam(arr);
         code.iload().setParam(idx);
         code.invokespecial().setMethod(this._supMeta.getName(), "beaInvoke", Object.class.getName(), args);
         code.areturn();
      } else {
         def = throwError(code, NoSuchMethodError.class, (String)null);
      }

      lookup.setDefaultTarget((Instruction)def);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addInvokeStatic(MethodMetaData[] methods) {
      String[] args = new String[]{Object[].class.getName(), "int"};
      BCMethod invoke = this._bc.declareMethod("beaInvokeStatic", Object.class.getName(), args);
      invoke.makePublic();
      invoke.setStatic(true);
      invoke.setSynthetic(true);
      invoke.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         invoke.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = invoke.getCode(true);
      code.iload().setParam(1);
      LookupSwitchInstruction lookup = code.lookupswitch();
      MethodMetaData[] var6 = methods;
      int var7 = methods.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         MethodMetaData meta = var6[var8];
         if (meta.isStatic() && (meta.isCurrent() || meta.getOverride() == OverrideType.STATIC_NONREDEFINABLE)) {
            lookup.addCase(meta.getIndex(), code.nop());
            String[] params = meta.getParameterTypes();

            for(int i = 0; i < params.length; ++i) {
               code.aload().setParam(0);
               code.constant().setValue(i);
               code.aaload();
               if (!this.unpack(code, params[i]) && !Object.class.getName().equals(params[i])) {
                  code.checkcast().setType(params[i]);
               }
            }

            String cls = meta.isCurrent() ? this._vers.getName() : this._meta.getName();
            code.invokestatic().setMethod(cls, meta.getName(), meta.getReturnType(), params);
            if ("void".equals(meta.getReturnType())) {
               code.constant().setNull();
            } else {
               this.pack(code, meta.getReturnType());
            }

            code.areturn();
         }
      }

      Object def;
      if (this._supMeta != null) {
         def = code.aload().setParam(0);
         code.iload().setParam(1);
         code.invokestatic().setMethod(this._supMeta.getName(), "beaInvokeStatic", Object.class.getName(), args);
         code.areturn();
      } else {
         def = throwError(code, NoSuchMethodError.class, (String)null);
      }

      lookup.setDefaultTarget((Instruction)def);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private static MethodMetaData[] sort(MethodMetaData[] a) {
      boolean ordered = true;

      for(int i = 1; i < a.length && ordered; ++i) {
         ordered = a[i].getIndex() > a[i - 1].getIndex();
      }

      if (ordered) {
         return a;
      } else {
         MethodMetaData[] copy = new MethodMetaData[a.length];
         System.arraycopy(a, 0, copy, 0, a.length);
         Arrays.sort(copy, MEMBER_COMPARATOR);
         return copy;
      }
   }

   private void moveAddedConstructorLogicToVersion() {
      ConstructorMetaData[] var1 = this._meta.getDeclaredConstructors();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ConstructorMetaData meta = var1[var3];
         String[] params = meta.getParameterTypes();
         BCMethod method = this._bc.getDeclaredMethod(meta.getName(), params);
         if (method == null) {
            if (!meta.isAdded()) {
               this.addNoSuchMethodErrorConstructor(meta);
            }
         } else if (meta.isAdded()) {
            BCMethod vmethod = this._vers.declareMethod(method);
            vmethod.setName("beaAddedConstructor");
            vmethod.setParams(this.toStaticMethodParameters(params));
            vmethod.setStatic(true);
            Code code = method.getCode(false);
            this.removeUnsupportedAttributes(code);
            Instruction nop = this._template.nop();
            Code vcode = vmethod.getCode(false);

            while(code.hasNext()) {
               boolean end = this.isRequiredConstructorCodeEnd(code.next());
               vcode.next();
               vcode.set(nop);
               if (end) {
                  code.aload().setThis();

                  for(int i = 0; i < params.length; ++i) {
                     code.xload().setParam(i);
                  }

                  code.invokestatic().setMethod(vmethod);
                  code.vreturn();
                  break;
               }
            }
         }
      }

   }

   private boolean isRequiredConstructorCodeEnd(Instruction ins) {
      if (ins.getOpcode() == 181) {
         return "beaAddedFields".equals(((FieldInstruction)ins).getFieldName());
      } else if (ins.getOpcode() != 183) {
         return false;
      } else {
         MethodInstruction mi = (MethodInstruction)ins;
         return this._meta.getName().equals(mi.getMethodDeclarerName()) && "<init>".equals(mi.getMethodName());
      }
   }

   private void combineAddedConstructors() {
      BCMethod cons = this._bc.declareMethod("<init>", "void", new String[]{Object[].class.getName(), "int", GENERIC_CONS_ARG});
      cons.makePublic();
      cons.setSynthetic(true);
      cons.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      Code code = cons.getCode(true);
      code.iload().setParam(1);
      LookupSwitchInstruction lookup = code.lookupswitch();
      int addLocals = 3;
      ConstructorMetaData[] var5 = this._meta.getDeclaredConstructors();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ConstructorMetaData meta = var5[var7];
         if (meta.isCurrent() && meta.isAdded()) {
            String[] params = meta.getParameterTypes();
            BCMethod method = this._bc.getDeclaredMethod(meta.getName(), params);
            lookup.addCase(meta.getIndex(), code.nop());

            for(int i = 0; i < params.length; ++i) {
               code.aload().setParam(0);
               code.constant().setValue(i);
               code.aaload();
               if (!this.unpack(code, params[i]) && !Object.class.getName().equals(params[i])) {
                  code.checkcast().setType(params[i]);
               }

               code.xstore().setLocal(i + 1 + addLocals).setType(params[i]);
            }

            this.insertConstructor(code, method, addLocals);
            this._bc.removeDeclaredMethod(method);
         }
      }

      lookup.setDefaultTarget(throwError(code, NoSuchMethodError.class, (String)null));
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void insertConstructor(Code code, BCMethod method, int addLocals) {
      Code origcode = method.getCode(false);
      List ins = new ArrayList();
      List origjumps = null;
      List jumps = null;

      int i;
      for(i = 0; origcode.hasNext(); ++i) {
         Instruction orig = origcode.next();
         Instruction in = code.add(orig);
         ins.add(in);
         if (in.getOpcode() == 177) {
            break;
         }

         if (orig instanceof JumpInstruction) {
            if (origjumps == null) {
               origjumps = new ArrayList(3);
               jumps = new ArrayList(3);
            }

            origjumps.add((JumpInstruction)orig);
            jumps.add((JumpInstruction)in);
         }

         if (in instanceof LocalVariableInstruction) {
            LocalVariableInstruction loc = (LocalVariableInstruction)in;
            int local = loc.getLocal();
            if (local != 0) {
               loc.setLocal(local + addLocals);
            }
         }
      }

      if (origjumps != null) {
         for(i = 0; i < origjumps.size(); ++i) {
            ((JumpInstruction)jumps.get(i)).setTarget((Instruction)ins.get(indexOf(origcode, ((JumpInstruction)origjumps.get(i)).getTarget())));
         }
      }

   }

   private static int indexOf(Code code, Instruction in) {
      code.beforeFirst();

      for(int i = 0; code.hasNext(); ++i) {
         if (code.next() == in) {
            return i;
         }
      }

      return -1;
   }

   private void addNoSuchMethodErrorConstructor(ConstructorMetaData meta) {
      BCMethod method = this._bc.declareMethod("<init>", "void", meta.getParameterTypes());
      method.setAccessFlags(meta.getAccess());
      method.getDeclaredRuntimeAnnotations(true).addAnnotation(BeaSynthetic.class);
      if (this._isWebServiceClass) {
         method.getDeclaredRuntimeAnnotations(true).addAnnotation(WebMethod.class).addProperty("exclude").setValue(true);
      }

      Code code = method.getCode(true);
      throwError(code, NoSuchMethodError.class, "void <init>(" + getParameterDescriptor(meta.getParameterTypes()) + ")");
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addStaticInitializer() {
      BCMethod clinit = this._bc.getDeclaredMethod("<clinit>", (String[])null);
      if (clinit == null) {
         clinit = this._bc.declareMethod("<clinit>", Void.TYPE, (Class[])null);
         clinit.setStatic(true);
         clinit.makePackage();
         Code code = clinit.getCode(true);
         code.vreturn();
      }
   }

   private void orderFieldDeclarations() {
      if (this._meta.getVersion() != 0) {
         List fields = new ArrayList(Arrays.asList(this._bc.getDeclaredFields()));
         int origIdx = -1;
         FieldMetaData[] var3 = this._meta.getDeclaredFields();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FieldMetaData meta = var3[var5];
            if (meta.isAdded()) {
               break;
            }

            int idx = fields.indexOf(this._bc.getDeclaredField(meta.getName()));
            ++origIdx;
            if (idx != origIdx) {
               this._bc.moveDeclaredField(idx, origIdx);
               Object o = fields.remove(idx);
               fields.add(origIdx, o);
            }
         }

      }
   }

   private void orderMethodDeclarations() {
      List methods = new ArrayList(Arrays.asList(this._bc.getDeclaredMethods()));
      int origIdx = -1;
      int idx = methods.indexOf(this._bc.getDeclaredMethod("<clinit>"));
      ++origIdx;
      if (idx != origIdx) {
         this._bc.moveDeclaredMethod(idx, origIdx);
         Object o = methods.remove(idx);
         methods.add(origIdx, o);
      }

      ConstructorMetaData[] var9 = this._meta.getDeclaredConstructors();
      int var5 = var9.length;

      int var6;
      Object o;
      for(var6 = 0; var6 < var5; ++var6) {
         ConstructorMetaData meta = var9[var6];
         if (meta.isAdded()) {
            break;
         }

         idx = methods.indexOf(this._bc.getDeclaredMethod("<init>", meta.getParameterTypes()));
         ++origIdx;
         if (idx != origIdx) {
            this._bc.moveDeclaredMethod(idx, origIdx);
            o = methods.remove(idx);
            methods.add(origIdx, o);
         }
      }

      MethodMetaData[] var10 = this._meta.getDeclaredMethods();
      var5 = var10.length;

      for(var6 = 0; var6 < var5; ++var6) {
         MethodMetaData meta = var10[var6];
         if (meta.isAdded()) {
            break;
         }

         idx = methods.indexOf(this._bc.getDeclaredMethod(meta.getName(), meta.getReturnType(), meta.getParameterTypes()));
         ++origIdx;
         if (idx != origIdx) {
            this._bc.moveDeclaredMethod(idx, origIdx);
            o = methods.remove(idx);
            methods.add(origIdx, o);
         }
      }

   }

   private static String capitalize(String str) {
      int strLen;
      return str != null && (strLen = str.length()) != 0 ? (new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString() : str;
   }

   private static class TryBlock {
      private Instruction _tryStart;
      private Instruction _tryEnd;

      TryBlock(Instruction tryStart, Instruction tryEnd) {
         this._tryStart = tryStart;
         this._tryEnd = tryEnd;
      }

      Instruction getTryStart() {
         return this._tryStart;
      }

      Instruction getTryEnd() {
         return this._tryEnd;
      }
   }
}
