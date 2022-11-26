package org.apache.openjpa.enhance;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.util.BytecodeWriter;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.lib.util.Services;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.BigDecimalId;
import org.apache.openjpa.util.BigIntegerId;
import org.apache.openjpa.util.ByteId;
import org.apache.openjpa.util.CharId;
import org.apache.openjpa.util.DateId;
import org.apache.openjpa.util.DoubleId;
import org.apache.openjpa.util.FloatId;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.IntId;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.LongId;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.ShortId;
import org.apache.openjpa.util.StringId;
import org.apache.openjpa.util.UserException;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.ClassInstruction;
import serp.bytecode.Code;
import serp.bytecode.ConstantInstruction;
import serp.bytecode.Exceptions;
import serp.bytecode.FieldInstruction;
import serp.bytecode.GetFieldInstruction;
import serp.bytecode.IfInstruction;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LoadInstruction;
import serp.bytecode.MethodInstruction;
import serp.bytecode.Project;
import serp.bytecode.TableSwitchInstruction;
import serp.util.Numbers;
import serp.util.Strings;

public class PCEnhancer {
   public static final int ENHANCER_VERSION = 2;
   public static final int ENHANCE_NONE = 0;
   public static final int ENHANCE_AWARE = 2;
   public static final int ENHANCE_INTERFACE = 4;
   public static final int ENHANCE_PC = 8;
   public static final String PRE = "pc";
   public static final String ISDETACHEDSTATEDEFINITIVE = "pcisDetachedStateDefinitive";
   private static final Class PCTYPE = PersistenceCapable.class;
   private static final String SM = "pcStateManager";
   private static final Class SMTYPE = StateManager.class;
   private static final String INHERIT = "pcInheritedFieldCount";
   private static final String CONTEXTNAME = "GenericContext";
   private static final Class USEREXCEP = UserException.class;
   private static final Class INTERNEXCEP = InternalException.class;
   private static final Class HELPERTYPE = PCRegistry.class;
   private static final String SUPER = "pcPCSuperclass";
   private static final Class OIDFSTYPE = FieldSupplier.class;
   private static final Class OIDFCTYPE = FieldConsumer.class;
   private static final Localizer _loc = Localizer.forPackage(PCEnhancer.class);
   private static final String REDEFINED_ATTRIBUTE = PCEnhancer.class.getName() + "#redefined-type";
   private static final AuxiliaryEnhancer[] _auxEnhancers;
   private BCClass _pc;
   private final BCClass _managedType;
   private final MetaDataRepository _repos;
   private final ClassMetaData _meta;
   private final Log _log;
   private Collection _oids;
   private boolean _defCons;
   private boolean _redefine;
   private boolean _subclass;
   private boolean _fail;
   private Set _violations;
   private File _dir;
   private BytecodeWriter _writer;
   private Map _backingFields;
   private Map _attrsToFields;
   private Map _fieldsToAttrs;
   private boolean _isAlreadyRedefined;
   private boolean _isAlreadySubclassed;
   private boolean _bcsConfigured;

   public PCEnhancer(OpenJPAConfiguration conf, Class type) {
      this(conf, (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(new Project(), type)), (MetaDataRepository)null);
   }

   public PCEnhancer(OpenJPAConfiguration conf, ClassMetaData meta) {
      this(conf, (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(new Project(), meta.getDescribedType())), meta.getRepository());
   }

   /** @deprecated */
   public PCEnhancer(OpenJPAConfiguration conf, BCClass type, MetaDataRepository repos) {
      this(conf, type, repos, (ClassLoader)null);
   }

   public PCEnhancer(OpenJPAConfiguration conf, BCClass type, MetaDataRepository repos, ClassLoader loader) {
      this._oids = null;
      this._defCons = true;
      this._redefine = false;
      this._subclass = false;
      this._fail = false;
      this._violations = null;
      this._dir = null;
      this._writer = null;
      this._backingFields = null;
      this._attrsToFields = null;
      this._fieldsToAttrs = null;
      this._isAlreadyRedefined = false;
      this._isAlreadySubclassed = false;
      this._bcsConfigured = false;
      this._managedType = type;
      this._pc = type;
      this._log = conf.getLog("openjpa.Enhance");
      if (repos == null) {
         this._repos = conf.newMetaDataRepositoryInstance();
         this._repos.setSourceMode(1);
      } else {
         this._repos = repos;
      }

      this._meta = this._repos.getMetaData(type.getType(), loader, false);
   }

   public PCEnhancer(MetaDataRepository repos, BCClass type, ClassMetaData meta) {
      this._oids = null;
      this._defCons = true;
      this._redefine = false;
      this._subclass = false;
      this._fail = false;
      this._violations = null;
      this._dir = null;
      this._writer = null;
      this._backingFields = null;
      this._attrsToFields = null;
      this._fieldsToAttrs = null;
      this._isAlreadyRedefined = false;
      this._isAlreadySubclassed = false;
      this._bcsConfigured = false;
      this._managedType = type;
      this._pc = type;
      this._log = repos.getConfiguration().getLog("openjpa.Enhance");
      this._repos = repos;
      this._meta = meta;
   }

   static String toPCSubclassName(Class cls) {
      return Strings.getPackageName(PCEnhancer.class) + "." + cls.getName().replace('.', '$') + "$pcsubclass";
   }

   public static boolean isPCSubclassName(String className) {
      return className.startsWith(Strings.getPackageName(PCEnhancer.class)) && className.endsWith("$pcsubclass");
   }

   public static String toManagedTypeName(String className) {
      if (isPCSubclassName(className)) {
         className = className.substring(Strings.getPackageName(PCEnhancer.class).length() + 1);
         className = className.substring(0, className.lastIndexOf("$"));
         className = className.replace('$', '.');
      }

      return className;
   }

   public PCEnhancer(OpenJPAConfiguration conf, BCClass type, ClassMetaData meta) {
      this(conf, type, meta.getRepository());
   }

   public BCClass getPCBytecode() {
      return this._pc;
   }

   public BCClass getManagedTypeBytecode() {
      return this._managedType;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public boolean getAddDefaultConstructor() {
      return this._defCons;
   }

   public void setAddDefaultConstructor(boolean addDefaultConstructor) {
      this._defCons = addDefaultConstructor;
   }

   public boolean getRedefine() {
      return this._redefine;
   }

   public void setRedefine(boolean redefine) {
      this._redefine = redefine;
   }

   public boolean isAlreadyRedefined() {
      return this._isAlreadyRedefined;
   }

   public boolean isAlreadySubclassed() {
      return this._isAlreadySubclassed;
   }

   public boolean getCreateSubclass() {
      return this._subclass;
   }

   public void setCreateSubclass(boolean subclass) {
      this._subclass = subclass;
   }

   public boolean getEnforcePropertyRestrictions() {
      return this._fail;
   }

   public void setEnforcePropertyRestrictions(boolean fail) {
      this._fail = fail;
   }

   public File getDirectory() {
      return this._dir;
   }

   public void setDirectory(File dir) {
      this._dir = dir;
   }

   public BytecodeWriter getBytecodeWriter() {
      return this._writer;
   }

   public void setBytecodeWriter(BytecodeWriter writer) {
      this._writer = writer;
   }

   public int run() {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("enhance-start", (Object)this._managedType.getType()));
      }

      try {
         if (this._pc.isInterface()) {
            return 4;
         } else {
            Class[] interfaces = this._managedType.getDeclaredInterfaceTypes();

            for(int i = 0; i < interfaces.length; ++i) {
               if (interfaces[i].getName().equals(PCTYPE.getName())) {
                  if (this._log.isTraceEnabled()) {
                     this._log.trace(_loc.get("pc-type", (Object)this._managedType.getType()));
                  }

                  return 0;
               }
            }

            this.configureBCs();
            if (this._meta != null && this._meta.getAccessType() == 4) {
               this.validateProperties();
               if (this.getCreateSubclass()) {
                  this.addAttributeTranslation();
               }
            }

            this.replaceAndValidateFieldAccess();
            this.processViolations();
            if (this._meta != null) {
               this.enhanceClass();
               this.addFields();
               this.addStaticInitializer();
               this.addPCMethods();
               this.addAccessors();
               this.addAttachDetachCode();
               this.addSerializationCode();
               this.addCloningCode();
               this.runAuxiliaryEnhancers();
               return 8;
            } else {
               if (this._log.isWarnEnabled()) {
                  this._log.warn(_loc.get("pers-aware", (Object)this._managedType.getType()));
               }

               return 2;
            }
         }
      } catch (OpenJPAException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new GeneralException(_loc.get("enhance-error", this._managedType.getType().getName(), var4.getMessage()), var4);
      }
   }

   private void configureBCs() {
      if (!this._bcsConfigured) {
         if (this.getRedefine()) {
            if (this._managedType.getAttribute(REDEFINED_ATTRIBUTE) == null) {
               this._managedType.addAttribute(REDEFINED_ATTRIBUTE);
            } else {
               this._isAlreadyRedefined = true;
            }
         }

         if (this.getCreateSubclass()) {
            PCSubclassValidator val = new PCSubclassValidator(this._meta, this._managedType, this._log, this._fail);
            val.assertCanSubclass();
            this._pc = this._managedType.getProject().loadClass(toPCSubclassName(this._managedType.getType()));
            if (this._pc.getSuperclassBC() != this._managedType) {
               this._pc.setSuperclass(this._managedType);
               this._pc.setAbstract(this._managedType.isAbstract());
               this._pc.declareInterface(DynamicPersistenceCapable.class);
            } else {
               this._isAlreadySubclassed = true;
            }
         }

         this._bcsConfigured = true;
      }

   }

   public void record() throws IOException {
      if (this._managedType != this._pc && this.getRedefine()) {
         this.record(this._managedType);
      }

      this.record(this._pc);
      if (this._oids != null) {
         Iterator itr = this._oids.iterator();

         while(itr.hasNext()) {
            this.record((BCClass)itr.next());
         }
      }

   }

   private void record(BCClass bc) throws IOException {
      if (this._writer != null) {
         this._writer.write(bc);
      } else if (this._dir == null) {
         bc.write();
      } else {
         File dir = Files.getPackageFile(this._dir, bc.getPackageName(), true);
         bc.write(new File(dir, bc.getClassName() + ".class"));
      }

   }

   private void validateProperties() {
      FieldMetaData[] fmds;
      if (this.getCreateSubclass()) {
         fmds = this._meta.getFields();
      } else {
         fmds = this._meta.getDeclaredFields();
      }

      BCField assigned = null;

      for(int i = 0; i < fmds.length; ++i) {
         if (!(fmds[i].getBackingMember() instanceof Method)) {
            this.addViolation("property-bad-member", new Object[]{fmds[i], fmds[i].getBackingMember()}, true);
         } else {
            Method meth = (Method)fmds[i].getBackingMember();
            BCClass declaringType = this._managedType.getProject().loadClass(fmds[i].getDeclaringType());
            BCMethod getter = declaringType.getDeclaredMethod(meth.getName(), meth.getParameterTypes());
            if (getter == null) {
               this.addViolation("property-no-getter", new Object[]{fmds[i]}, true);
            } else {
               BCField returned = getReturnedField(getter);
               if (returned != null) {
                  this.registerBackingFieldInfo(fmds[i], getter, returned);
               }

               BCMethod setter = declaringType.getDeclaredMethod(getSetterName(fmds[i]), new Class[]{fmds[i].getDeclaredType()});
               if (setter == null) {
                  if (returned == null) {
                     this.addViolation("property-no-setter", new Object[]{fmds[i]}, true);
                     continue;
                  }

                  if (!this.getRedefine()) {
                     setter = this._managedType.declareMethod(getSetterName(fmds[i]), Void.TYPE, new Class[]{fmds[i].getDeclaredType()});
                     setter.makePrivate();
                     Code code = setter.getCode(true);
                     code.aload().setThis();
                     code.xload().setParam(0);
                     code.putfield().setField(returned);
                     code.vreturn();
                     code.calculateMaxStack();
                     code.calculateMaxLocals();
                  }
               }

               if (setter != null) {
                  assigned = getAssignedField(setter);
               }

               if (assigned != null) {
                  if (setter != null) {
                     this.registerBackingFieldInfo(fmds[i], setter, assigned);
                  }

                  if (assigned != returned) {
                     this.addViolation("property-setter-getter-mismatch", new Object[]{fmds[i], assigned.getName(), returned == null ? null : returned.getName()}, false);
                  }
               }
            }
         }
      }

   }

   private void registerBackingFieldInfo(FieldMetaData fmd, BCMethod method, BCField field) {
      if (this._backingFields == null) {
         this._backingFields = new HashMap();
      }

      this._backingFields.put(method.getName(), field.getName());
      if (this._attrsToFields == null) {
         this._attrsToFields = new HashMap();
      }

      this._attrsToFields.put(fmd.getName(), field.getName());
      if (this._fieldsToAttrs == null) {
         this._fieldsToAttrs = new HashMap();
      }

      this._fieldsToAttrs.put(field.getName(), fmd.getName());
   }

   private void addAttributeTranslation() {
      this._pc.declareInterface(AttributeTranslator.class);
      BCMethod method = this._pc.declareMethod("pcAttributeIndexToFieldName", String.class, new Class[]{Integer.TYPE});
      method.makePublic();
      Code code = method.getCode(true);
      FieldMetaData[] fmds = this._meta.getFields();
      code.iload().setParam(0);
      TableSwitchInstruction tabins = code.tableswitch();
      tabins.setLow(0);
      tabins.setHigh(fmds.length - 1);

      for(int i = 0; i < fmds.length; ++i) {
         tabins.addTarget(code.constant().setValue(this._attrsToFields.get(fmds[i].getName())));
         code.areturn();
      }

      tabins.setDefaultTarget(this.throwException(code, IllegalArgumentException.class));
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private static String getSetterName(FieldMetaData fmd) {
      return "set" + StringUtils.capitalize(fmd.getName());
   }

   static BCField getReturnedField(BCMethod meth) {
      return findField(meth, ((Code)AccessController.doPrivileged(J2DoPrivHelper.newCodeAction())).xreturn().setType(meth.getReturnType()), false);
   }

   static BCField getAssignedField(BCMethod meth) {
      return findField(meth, ((Code)AccessController.doPrivileged(J2DoPrivHelper.newCodeAction())).putfield(), true);
   }

   private static BCField findField(BCMethod meth, Instruction template, boolean findAccessed) {
      if (meth.isStatic()) {
         return null;
      } else {
         Code code = meth.getCode(false);
         if (code == null) {
            return null;
         } else {
            code.beforeFirst();
            BCField field = null;

            label76:
            while(code.searchForward(template)) {
               int backupCount = 3;
               Instruction templateIns = code.previous();
               if (!code.hasPrevious()) {
                  return null;
               }

               Instruction prevIns = code.previous();
               if (prevIns instanceof ClassInstruction && code.hasPrevious()) {
                  prevIns = code.previous();
                  ++backupCount;
               }

               if (!code.hasPrevious()) {
                  return null;
               }

               Instruction earlierIns = code.previous();
               if (earlierIns instanceof LoadInstruction && ((LoadInstruction)earlierIns).isThis()) {
                  BCField cur;
                  FieldInstruction fTemplateIns;
                  if (!findAccessed && prevIns instanceof GetFieldInstruction) {
                     fTemplateIns = (FieldInstruction)prevIns;
                     cur = (BCField)AccessController.doPrivileged(J2DoPrivHelper.getFieldInstructionFieldAction(fTemplateIns));
                  } else {
                     if (!findAccessed || !(prevIns instanceof LoadInstruction) || ((LoadInstruction)prevIns).getParam() != 0) {
                        return null;
                     }

                     fTemplateIns = (FieldInstruction)templateIns;
                     cur = (BCField)AccessController.doPrivileged(J2DoPrivHelper.getFieldInstructionFieldAction(fTemplateIns));
                  }

                  if (field != null && cur != field) {
                     return null;
                  }

                  field = cur;

                  while(true) {
                     if (backupCount <= 0) {
                        continue label76;
                     }

                     code.next();
                     --backupCount;
                  }
               }

               return null;
            }

            return field;
         }
      }
   }

   private void addViolation(String key, Object[] args, boolean fatal) {
      if (this._violations == null) {
         this._violations = new HashSet();
      }

      this._violations.add(_loc.get(key, args));
      this._fail |= fatal;
   }

   private void processViolations() {
      if (this._violations != null) {
         String sep = J2DoPrivHelper.getLineSeparator();
         StringBuffer buf = new StringBuffer();
         Iterator itr = this._violations.iterator();

         while(itr.hasNext()) {
            buf.append(itr.next());
            if (itr.hasNext()) {
               buf.append(sep);
            }
         }

         Localizer.Message msg = _loc.get("property-violations", (Object)buf);
         if (this._fail) {
            throw new UserException(msg);
         } else {
            if (this._log.isWarnEnabled()) {
               this._log.warn(msg);
            }

         }
      }
   }

   private void replaceAndValidateFieldAccess() throws NoSuchMethodException {
      Code template = (Code)AccessController.doPrivileged(J2DoPrivHelper.newCodeAction());
      Instruction put = template.putfield();
      Instruction get = template.getfield();
      Instruction stat = template.invokestatic();
      BCMethod[] methods = this._managedType.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         Code code = methods[i].getCode(false);
         if (code != null && !this.skipEnhance(methods[i])) {
            this.replaceAndValidateFieldAccess(code, get, true, stat);
            this.replaceAndValidateFieldAccess(code, put, false, stat);
         }
      }

   }

   private void replaceAndValidateFieldAccess(Code code, Instruction ins, boolean get, Instruction stat) throws NoSuchMethodException {
      code.beforeFirst();

      while(true) {
         while(code.searchForward(ins)) {
            FieldInstruction fi = (FieldInstruction)code.previous();
            String name = fi.getFieldName();
            String typeName = fi.getFieldTypeName();
            ClassMetaData owner = this.getPersistenceCapableOwner(name, fi.getFieldDeclarerType());
            if (owner != null && owner.getAccessType() == 4) {
               if (owner != this._meta && owner.getDeclaredField(name) != null && this._meta != null && !owner.getDescribedType().isAssignableFrom(this._meta.getDescribedType())) {
                  throw new UserException(_loc.get("property-field-access", new Object[]{this._meta, owner, name, code.getMethod().getName()}));
               }

               if (this.isBackingFieldOfAnotherProperty(name, code)) {
                  this.addViolation("property-field-access", new Object[]{this._meta, owner, name, code.getMethod().getName()}, false);
               }
            }

            if (owner != null && owner.getDeclaredField(this.fromBackingFieldName(name)) != null) {
               if (!this.getRedefine() && !this.getCreateSubclass() && owner.getAccessType() == 2) {
                  MethodInstruction mi = (MethodInstruction)code.set(stat);
                  String prefix = get ? "pcGet" : "pcSet";
                  String methodName = prefix + name;
                  if (get) {
                     mi.setMethod(this.getType(owner).getName(), methodName, typeName, new String[]{this.getType(owner).getName()});
                  } else {
                     mi.setMethod(this.getType(owner).getName(), methodName, "void", new String[]{this.getType(owner).getName(), typeName});
                  }

                  code.next();
               } else if (this.getRedefine()) {
                  name = this.fromBackingFieldName(name);
                  if (get) {
                     this.addNotifyAccess(code, owner.getField(name));
                     code.next();
                  } else {
                     this.loadManagedInstance(code, false);
                     code.getfield().setField((BCField)AccessController.doPrivileged(J2DoPrivHelper.getFieldInstructionFieldAction(fi)));
                     int val = code.getNextLocalsIndex();
                     code.xstore().setLocal(val).setType(fi.getFieldType());
                     code.next();
                     this.addNotifyMutation(code, owner.getField(name), val, -1);
                  }
               } else {
                  code.next();
               }

               code.calculateMaxLocals();
               code.calculateMaxStack();
            } else {
               code.next();
            }
         }

         return;
      }
   }

   private void addNotifyAccess(Code code, FieldMetaData fmd) {
      code.aload().setThis();
      code.constant().setValue(fmd.getIndex());
      code.invokestatic().setMethod(RedefinitionHelper.class, "accessingField", Void.TYPE, new Class[]{Object.class, Integer.TYPE});
   }

   private void addNotifyMutation(Code code, FieldMetaData fmd, int val, int param) throws NoSuchMethodException {
      code.aload().setThis();
      code.constant().setValue(fmd.getIndex());
      Class type = fmd.getDeclaredType();
      if (!type.isPrimitive() && type != String.class) {
         type = Object.class;
      }

      code.xload().setLocal(val).setType(type);
      if (param == -1) {
         this.loadManagedInstance(code, false);
         this.addGetManagedValueCode(code, fmd);
      } else {
         code.xload().setParam(param).setType(type);
      }

      code.invokestatic().setMethod(RedefinitionHelper.class, "settingField", Void.TYPE, new Class[]{Object.class, Integer.TYPE, type, type});
   }

   private boolean isBackingFieldOfAnotherProperty(String name, Code code) {
      String methName = code.getMethod().getName();
      return !"<init>".equals(methName) && this._backingFields != null && !name.equals(this._backingFields.get(methName)) && this._backingFields.containsValue(name);
   }

   private ClassMetaData getPersistenceCapableOwner(String fieldName, Class owner) {
      Field f = Reflection.findField(owner, fieldName, false);
      if (f == null) {
         return null;
      } else {
         return this._meta != null && this._meta.getDescribedType().isInterface() ? this._meta : this._repos.getMetaData((Class)f.getDeclaringClass(), (ClassLoader)null, false);
      }
   }

   private void addPCMethods() throws NoSuchMethodException {
      this.addClearFieldsMethod();
      this.addNewInstanceMethod(true);
      this.addNewInstanceMethod(false);
      this.addManagedFieldCountMethod();
      this.addReplaceFieldsMethods();
      this.addProvideFieldsMethods();
      this.addCopyFieldsMethod();
      if (this._meta.getPCSuperclass() == null || this.getCreateSubclass()) {
         this.addStockMethods();
         this.addGetVersionMethod();
         this.addReplaceStateManagerMethod();
         if (this._meta.getIdentityType() != 2) {
            this.addNoOpApplicationIdentityMethods();
         }
      }

      if (this._meta.getIdentityType() == 2 && (this._meta.getPCSuperclass() == null || this.getCreateSubclass() || this._meta.getObjectIdType() != this._meta.getPCSuperclassMetaData().getObjectIdType())) {
         this.addCopyKeyFieldsToObjectIdMethod(true);
         this.addCopyKeyFieldsToObjectIdMethod(false);
         this.addCopyKeyFieldsFromObjectIdMethod(true);
         this.addCopyKeyFieldsFromObjectIdMethod(false);
         this.addNewObjectIdInstanceMethod(true);
         this.addNewObjectIdInstanceMethod(false);
      }

   }

   private void addClearFieldsMethod() throws NoSuchMethodException {
      BCMethod method = this._pc.declareMethod("pcClearFields", Void.TYPE, (Class[])null);
      method.makeProtected();
      Code code = method.getCode(true);
      if (this._meta.getPCSuperclass() != null && !this.getCreateSubclass()) {
         code.aload().setThis();
         code.invokespecial().setMethod(this.getType(this._meta.getPCSuperclassMetaData()), "pcClearFields", Void.TYPE, (Class[])null);
      }

      FieldMetaData[] fmds = this._meta.getDeclaredFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getManagement() == 3) {
            this.loadManagedInstance(code, false);
            switch (fmds[i].getDeclaredTypeCode()) {
               case 0:
               case 1:
               case 2:
               case 5:
               case 7:
                  code.constant().setValue(0);
                  break;
               case 3:
                  code.constant().setValue(0.0);
                  break;
               case 4:
                  code.constant().setValue(0.0F);
                  break;
               case 6:
                  code.constant().setValue(0L);
                  break;
               default:
                  code.constant().setNull();
            }

            this.addSetManagedValueCode(code, fmds[i]);
         }
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addNewInstanceMethod(boolean oid) {
      Class[] args = oid ? new Class[]{SMTYPE, Object.class, Boolean.TYPE} : new Class[]{SMTYPE, Boolean.TYPE};
      BCMethod method = this._pc.declareMethod("pcNewInstance", PCTYPE, args);
      Code code = method.getCode(true);
      if (this._pc.isAbstract()) {
         this.throwException(code, USEREXCEP);
         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      } else {
         code.anew().setType(this._pc);
         code.dup();
         code.invokespecial().setMethod("<init>", Void.TYPE, (Class[])null);
         int inst = code.getNextLocalsIndex();
         code.astore().setLocal(inst);
         code.iload().setParam(oid ? 2 : 1);
         JumpInstruction noclear = code.ifeq();
         code.aload().setLocal(inst);
         code.invokevirtual().setMethod("pcClearFields", Void.TYPE, (Class[])null);
         noclear.setTarget(code.aload().setLocal(inst));
         code.aload().setParam(0);
         code.putfield().setField("pcStateManager", SMTYPE);
         if (oid) {
            code.aload().setLocal(inst);
            code.aload().setParam(1);
            code.invokevirtual().setMethod("pcCopyKeyFieldsFromObjectId", Void.TYPE, new Class[]{Object.class});
         }

         code.aload().setLocal(inst);
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void addManagedFieldCountMethod() {
      BCMethod method = this._pc.declareMethod("pcGetManagedFieldCount", Integer.TYPE, (Class[])null);
      method.setStatic(true);
      method.makeProtected();
      Code code = method.getCode(true);
      code.constant().setValue(this._meta.getDeclaredFields().length);
      if (this._meta.getPCSuperclass() != null) {
         Class superClass = this.getType(this._meta.getPCSuperclassMetaData());
         String superName = this.getCreateSubclass() ? toPCSubclassName(superClass) : superClass.getName();
         code.invokestatic().setMethod(superName, "pcGetManagedFieldCount", Integer.TYPE.getName(), (String[])null);
         code.iadd();
      }

      code.ireturn();
      code.calculateMaxStack();
   }

   private void addProvideFieldsMethods() throws NoSuchMethodException {
      BCMethod method = this._pc.declareMethod("pcProvideField", Void.TYPE, new Class[]{Integer.TYPE});
      Code code = method.getCode(true);
      int relLocal = this.beginSwitchMethod("pcProvideField", code);
      FieldMetaData[] fmds = this.getCreateSubclass() ? this._meta.getFields() : this._meta.getDeclaredFields();
      if (fmds.length == 0) {
         this.throwException(code, IllegalArgumentException.class);
      } else {
         code.iload().setLocal(relLocal);
         TableSwitchInstruction tabins = code.tableswitch();
         tabins.setLow(0);
         tabins.setHigh(fmds.length - 1);

         for(int i = 0; i < fmds.length; ++i) {
            tabins.addTarget(this.loadManagedInstance(code, false));
            code.getfield().setField("pcStateManager", SMTYPE);
            this.loadManagedInstance(code, false);
            code.iload().setParam(0);
            this.loadManagedInstance(code, false);
            this.addGetManagedValueCode(code, fmds[i]);
            code.invokeinterface().setMethod(this.getStateManagerMethod(fmds[i].getDeclaredType(), "provided", false, false));
            code.vreturn();
         }

         tabins.setDefaultTarget(this.throwException(code, IllegalArgumentException.class));
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
      this.addMultipleFieldsMethodVersion(method);
   }

   private void addReplaceFieldsMethods() throws NoSuchMethodException {
      BCMethod method = this._pc.declareMethod("pcReplaceField", Void.TYPE, new Class[]{Integer.TYPE});
      Code code = method.getCode(true);
      int relLocal = this.beginSwitchMethod("pcReplaceField", code);
      FieldMetaData[] fmds = this.getCreateSubclass() ? this._meta.getFields() : this._meta.getDeclaredFields();
      if (fmds.length == 0) {
         this.throwException(code, IllegalArgumentException.class);
      } else {
         code.iload().setLocal(relLocal);
         TableSwitchInstruction tabins = code.tableswitch();
         tabins.setLow(0);
         tabins.setHigh(fmds.length - 1);

         for(int i = 0; i < fmds.length; ++i) {
            tabins.addTarget(this.loadManagedInstance(code, false));
            this.loadManagedInstance(code, false);
            code.getfield().setField("pcStateManager", SMTYPE);
            this.loadManagedInstance(code, false);
            code.iload().setParam(0);
            code.invokeinterface().setMethod(this.getStateManagerMethod(fmds[i].getDeclaredType(), "replace", true, false));
            if (!fmds[i].getDeclaredType().isPrimitive()) {
               code.checkcast().setType(fmds[i].getDeclaredType());
            }

            this.addSetManagedValueCode(code, fmds[i]);
            code.vreturn();
         }

         tabins.setDefaultTarget(this.throwException(code, IllegalArgumentException.class));
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
      this.addMultipleFieldsMethodVersion(method);
   }

   private void addCopyFieldsMethod() throws NoSuchMethodException {
      BCMethod method = this._pc.declareMethod("pcCopyField", Void.TYPE.getName(), new String[]{this._managedType.getName(), Integer.TYPE.getName()});
      method.makeProtected();
      Code code = method.getCode(true);
      int relLocal = this.beginSwitchMethod("pcCopyField", code);
      FieldMetaData[] fmds = this.getCreateSubclass() ? this._meta.getFields() : this._meta.getDeclaredFields();
      if (fmds.length == 0) {
         this.throwException(code, IllegalArgumentException.class);
      } else {
         code.iload().setLocal(relLocal);
         TableSwitchInstruction tabins = code.tableswitch();
         tabins.setLow(0);
         tabins.setHigh(fmds.length - 1);

         for(int i = 0; i < fmds.length; ++i) {
            tabins.addTarget(this.loadManagedInstance(code, false));
            code.aload().setParam(0);
            this.addGetManagedValueCode(code, fmds[i], false);
            this.addSetManagedValueCode(code, fmds[i]);
            code.vreturn();
         }

         tabins.setDefaultTarget(this.throwException(code, IllegalArgumentException.class));
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
      this.addMultipleFieldsMethodVersion(method);
   }

   private int beginSwitchMethod(String name, Code code) {
      boolean copy = "pcCopyField".equals(name);
      int fieldNumber = copy ? 1 : 0;
      int relLocal = code.getNextLocalsIndex();
      if (this.getCreateSubclass()) {
         code.iload().setParam(fieldNumber);
         code.istore().setLocal(relLocal);
         return relLocal;
      } else {
         code.iload().setParam(fieldNumber);
         code.getstatic().setField("pcInheritedFieldCount", Integer.TYPE);
         code.isub();
         code.istore().setLocal(relLocal);
         code.iload().setLocal(relLocal);
         JumpInstruction ifins = code.ifge();
         if (this._meta.getPCSuperclass() != null) {
            this.loadManagedInstance(code, false);
            String[] args;
            if (copy) {
               args = new String[]{this.getType(this._meta.getPCSuperclassMetaData()).getName(), Integer.TYPE.getName()};
               code.aload().setParam(0);
            } else {
               args = new String[]{Integer.TYPE.getName()};
            }

            code.iload().setParam(fieldNumber);
            code.invokespecial().setMethod(this.getType(this._meta.getPCSuperclassMetaData()).getName(), name, Void.TYPE.getName(), args);
            code.vreturn();
         } else {
            this.throwException(code, IllegalArgumentException.class);
         }

         ifins.setTarget(code.nop());
         return relLocal;
      }
   }

   private void addMultipleFieldsMethodVersion(BCMethod single) {
      boolean copy = "pcCopyField".equals(single.getName());
      Class[] args = copy ? new Class[]{Object.class, int[].class} : new Class[]{int[].class};
      BCMethod method = this._pc.declareMethod(single.getName() + "s", Void.TYPE, args);
      Code code = method.getCode(true);
      int fieldNumbers = 0;
      int inst = 0;
      if (copy) {
         fieldNumbers = 1;
         if (this.getCreateSubclass()) {
            code.aload().setParam(0);
            code.invokestatic().setMethod(ImplHelper.class, "getManagedInstance", Object.class, new Class[]{Object.class});
            code.checkcast().setType(this._managedType);
            inst = code.getNextLocalsIndex();
            code.astore().setLocal(inst);
            code.aload().setParam(0);
            code.aload().setThis();
            code.getfield().setField("pcStateManager", SMTYPE);
            code.invokestatic().setMethod(ImplHelper.class, "toPersistenceCapable", PersistenceCapable.class, new Class[]{Object.class, Object.class});
            code.invokeinterface().setMethod(PersistenceCapable.class, "pcGetStateManager", StateManager.class, (Class[])null);
         } else {
            code.aload().setParam(0);
            code.checkcast().setType(this._pc);
            inst = code.getNextLocalsIndex();
            code.astore().setLocal(inst);
            code.aload().setLocal(inst);
            code.getfield().setField("pcStateManager", SMTYPE);
         }

         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", SMTYPE);
         JumpInstruction ifins = code.ifacmpeq();
         this.throwException(code, IllegalArgumentException.class);
         ifins.setTarget(code.nop());
         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", SMTYPE);
         ifins = code.ifnonnull();
         this.throwException(code, IllegalStateException.class);
         ifins.setTarget(code.nop());
      }

      code.constant().setValue(0);
      int idx = code.getNextLocalsIndex();
      code.istore().setLocal(idx);
      JumpInstruction testins = code.go2();
      Instruction bodyins = this.loadManagedInstance(code, false);
      if (copy) {
         code.aload().setLocal(inst);
      }

      code.aload().setParam(fieldNumbers);
      code.iload().setLocal(idx);
      code.iaload();
      code.invokevirtual().setMethod(single);
      code.iinc().setIncrement(1).setLocal(idx);
      testins.setTarget(code.iload().setLocal(idx));
      code.aload().setParam(fieldNumbers);
      code.arraylength();
      code.ificmplt().setTarget(bodyins);
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addStockMethods() throws NoSuchMethodException {
      try {
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "getGenericContext", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "fetchObjectId", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "isDeleted", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "isDirty", (Class[])null)), true);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "isNew", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "isPersistent", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "isTransactional", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "serializing", (Class[])null)), false);
         this.translateFromStateManagerMethod((Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(SMTYPE, "dirty", new Class[]{String.class})), false);
         BCMethod meth = this._pc.declareMethod("pcGetStateManager", StateManager.class, (Class[])null);
         Code code = meth.getCode(true);
         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", StateManager.class);
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      } catch (PrivilegedActionException var3) {
         throw (NoSuchMethodException)var3.getException();
      }
   }

   private void translateFromStateManagerMethod(Method m, boolean isDirtyCheckMethod) {
      String name = "pc" + StringUtils.capitalize(m.getName());
      Class[] params = m.getParameterTypes();
      Class returnType = m.getReturnType();
      BCMethod method = this._pc.declareMethod(name, returnType, params);
      Code code = method.getCode(true);
      this.loadManagedInstance(code, false);
      code.getfield().setField("pcStateManager", SMTYPE);
      JumpInstruction ifins = code.ifnonnull();
      if (returnType.equals(Boolean.TYPE)) {
         code.constant().setValue(false);
      } else if (!returnType.equals(Void.TYPE)) {
         code.constant().setNull();
      }

      code.xreturn().setType(returnType);
      if (isDirtyCheckMethod && !this.getRedefine()) {
         ifins.setTarget(this.loadManagedInstance(code, false));
         code.getfield().setField("pcStateManager", SMTYPE);
         code.dup();
         code.invokestatic().setMethod(RedefinitionHelper.class, "dirtyCheck", Void.TYPE, new Class[]{SMTYPE});
      } else {
         ifins.setTarget(this.loadManagedInstance(code, false));
         code.getfield().setField("pcStateManager", SMTYPE);
      }

      for(int i = 0; i < params.length; ++i) {
         code.xload().setParam(i);
      }

      code.invokeinterface().setMethod(m);
      code.xreturn().setType(returnType);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addGetVersionMethod() throws NoSuchMethodException {
      BCMethod method = this._pc.declareMethod("pcGetVersion", Object.class, (Class[])null);
      Code code = method.getCode(true);
      this.loadManagedInstance(code, false);
      code.getfield().setField("pcStateManager", SMTYPE);
      JumpInstruction ifins = code.ifnonnull();
      FieldMetaData versionField = this._meta.getVersionField();
      if (versionField == null) {
         code.constant().setNull();
      } else {
         Class wrapper = this.toPrimitiveWrapper(versionField);
         if (wrapper != versionField.getDeclaredType()) {
            code.anew().setType(wrapper);
            code.dup();
         }

         this.loadManagedInstance(code, false);
         this.addGetManagedValueCode(code, versionField);
         if (wrapper != versionField.getDeclaredType()) {
            code.invokespecial().setMethod(wrapper, "<init>", Void.TYPE, new Class[]{versionField.getDeclaredType()});
         }
      }

      code.areturn();
      ifins.setTarget(this.loadManagedInstance(code, false));
      code.getfield().setField("pcStateManager", SMTYPE);
      code.invokeinterface().setMethod(SMTYPE, "getVersion", Object.class, (Class[])null);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private Class toPrimitiveWrapper(FieldMetaData fmd) {
      switch (fmd.getDeclaredTypeCode()) {
         case 0:
            return Boolean.class;
         case 1:
            return Byte.class;
         case 2:
            return Character.class;
         case 3:
            return Double.class;
         case 4:
            return Float.class;
         case 5:
            return Integer.class;
         case 6:
            return Long.class;
         case 7:
            return Short.class;
         default:
            return fmd.getDeclaredType();
      }
   }

   private void addReplaceStateManagerMethod() {
      BCMethod method = this._pc.declareMethod("pcReplaceStateManager", Void.TYPE, new Class[]{SMTYPE});
      method.setSynchronized(true);
      method.getExceptions(true).addException(SecurityException.class);
      Code code = method.getCode(true);
      this.loadManagedInstance(code, false);
      code.getfield().setField("pcStateManager", SMTYPE);
      JumpInstruction ifins = code.ifnull();
      this.loadManagedInstance(code, false);
      this.loadManagedInstance(code, false);
      code.getfield().setField("pcStateManager", SMTYPE);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(SMTYPE, "replaceStateManager", SMTYPE, new Class[]{SMTYPE});
      code.putfield().setField("pcStateManager", SMTYPE);
      code.vreturn();
      ifins.setTarget(code.invokestatic().setMethod(System.class, "getSecurityManager", SecurityManager.class, (Class[])null));
      ifins.setTarget(this.loadManagedInstance(code, false));
      code.aload().setParam(0);
      code.putfield().setField("pcStateManager", SMTYPE);
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addNoOpApplicationIdentityMethods() {
      BCMethod method = this._pc.declareMethod("pcCopyKeyFieldsToObjectId", Void.TYPE, new Class[]{OIDFSTYPE, Object.class});
      Code code = method.getCode(true);
      code.vreturn();
      code.calculateMaxLocals();
      method = this._pc.declareMethod("pcCopyKeyFieldsToObjectId", Void.TYPE, new Class[]{Object.class});
      code = method.getCode(true);
      code.vreturn();
      code.calculateMaxLocals();
      method = this._pc.declareMethod("pcCopyKeyFieldsFromObjectId", Void.TYPE, new Class[]{OIDFCTYPE, Object.class});
      code = method.getCode(true);
      code.vreturn();
      code.calculateMaxLocals();
      method = this._pc.declareMethod("pcCopyKeyFieldsFromObjectId", Void.TYPE, new Class[]{Object.class});
      code = method.getCode(true);
      code.vreturn();
      code.calculateMaxLocals();
      method = this._pc.declareMethod("pcNewObjectIdInstance", Object.class, (Class[])null);
      code = method.getCode(true);
      code.constant().setNull();
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      method = this._pc.declareMethod("pcNewObjectIdInstance", Object.class, new Class[]{Object.class});
      code = method.getCode(true);
      code.constant().setNull();
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addCopyKeyFieldsToObjectIdMethod(boolean fieldManager) throws NoSuchMethodException {
      String[] args = fieldManager ? new String[]{OIDFSTYPE.getName(), Object.class.getName()} : new String[]{Object.class.getName()};
      BCMethod method = this._pc.declareMethod("pcCopyKeyFieldsToObjectId", Void.TYPE.getName(), args);
      Code code = method.getCode(true);
      if (this._meta.isOpenJPAIdentity()) {
         this.throwException(code, INTERNEXCEP);
         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      } else {
         int id;
         if (this._meta.getPCSuperclass() != null && !this.getCreateSubclass()) {
            this.loadManagedInstance(code, false);

            for(id = 0; id < args.length; ++id) {
               code.aload().setParam(id);
            }

            code.invokespecial().setMethod(this.getType(this._meta.getPCSuperclassMetaData()).getName(), "pcCopyKeyFieldsToObjectId", Void.TYPE.getName(), args);
         }

         if (fieldManager) {
            code.aload().setParam(1);
         } else {
            code.aload().setParam(0);
         }

         if (this._meta.isObjectIdTypeShared()) {
            code.checkcast().setType(ObjectId.class);
            code.invokevirtual().setMethod(ObjectId.class, "getId", Object.class, (Class[])null);
         }

         id = code.getNextLocalsIndex();
         Class oidType = this._meta.getObjectIdType();
         code.checkcast().setType(oidType);
         code.astore().setLocal(id);
         int inherited = 0;
         if (fieldManager) {
            code.getstatic().setField("pcInheritedFieldCount", Integer.TYPE);
            inherited = code.getNextLocalsIndex();
            code.istore().setLocal(inherited);
         }

         FieldMetaData[] fmds = this.getCreateSubclass() ? this._meta.getFields() : this._meta.getDeclaredFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (fmds[i].isPrimaryKey()) {
               code.aload().setLocal(id);
               String name = fmds[i].getName();
               Class type = fmds[i].getObjectIdFieldType();
               Field field;
               Method setter;
               boolean reflect;
               if (this._meta.getAccessType() == 2) {
                  setter = null;
                  field = Reflection.findField(oidType, name, true);
                  reflect = !Modifier.isPublic(field.getModifiers());
                  if (reflect) {
                     code.classconstant().setClass(oidType);
                     code.constant().setValue(name);
                     code.constant().setValue(true);
                     code.invokestatic().setMethod(Reflection.class, "findField", Field.class, new Class[]{Class.class, String.class, Boolean.TYPE});
                  }
               } else {
                  field = null;
                  setter = Reflection.findSetter(oidType, name, type, true);
                  reflect = !Modifier.isPublic(setter.getModifiers());
                  if (reflect) {
                     code.classconstant().setClass(oidType);
                     code.constant().setValue(name);
                     code.classconstant().setClass(type);
                     code.constant().setValue(true);
                     code.invokestatic().setMethod(Reflection.class, "findSetter", Method.class, new Class[]{Class.class, String.class, Class.class, Boolean.TYPE});
                  }
               }

               if (fieldManager) {
                  code.aload().setParam(0);
                  code.constant().setValue(i);
                  code.iload().setLocal(inherited);
                  code.iadd();
                  code.invokeinterface().setMethod(this.getFieldSupplierMethod(type));
                  if (!reflect && !type.isPrimitive() && !type.getName().equals(String.class.getName())) {
                     code.checkcast().setType(type);
                  }
               } else {
                  this.loadManagedInstance(code, false);
                  this.addGetManagedValueCode(code, fmds[i]);
                  if (fmds[i].getDeclaredTypeCode() == 15) {
                     this.addExtractObjectIdFieldValueCode(code, fmds[i]);
                  }
               }

               if (reflect && field != null) {
                  code.invokestatic().setMethod(Reflection.class, "set", Void.TYPE, new Class[]{Object.class, Field.class, type.isPrimitive() ? type : Object.class});
               } else if (reflect) {
                  code.invokestatic().setMethod(Reflection.class, "set", Void.TYPE, new Class[]{Object.class, Method.class, type.isPrimitive() ? type : Object.class});
               } else if (field != null) {
                  code.putfield().setField(field);
               } else {
                  code.invokevirtual().setMethod(setter);
               }
            }
         }

         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void addExtractObjectIdFieldValueCode(Code code, FieldMetaData pk) {
      int pc = code.getNextLocalsIndex();
      code.astore().setLocal(pc);
      code.aload().setLocal(pc);
      JumpInstruction ifnull1 = code.ifnull();
      code.aload().setLocal(pc);
      code.checkcast().setType(PersistenceCapable.class);
      code.invokeinterface().setMethod(PersistenceCapable.class, "pcFetchObjectId", Object.class, (Class[])null);
      int oid = code.getNextLocalsIndex();
      code.astore().setLocal(oid);
      code.aload().setLocal(oid);
      JumpInstruction ifnull2 = code.ifnull();
      ClassMetaData pkmeta = pk.getDeclaredTypeMetaData();
      int pkcode = pk.getObjectIdFieldTypeCode();
      Class pktype = pk.getObjectIdFieldType();
      if (pkmeta.getIdentityType() == 1 && pkcode == 6) {
         code.aload().setLocal(oid);
         code.checkcast().setType(Id.class);
         code.invokevirtual().setMethod(Id.class, "getId", Long.TYPE, (Class[])null);
      } else if (pkmeta.getIdentityType() == 1) {
         code.aload().setLocal(oid);
      } else if (pkmeta.isOpenJPAIdentity()) {
         switch (pkcode) {
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            default:
               code.aload().setLocal(oid);
               code.checkcast().setType(ObjectId.class);
               code.invokevirtual().setMethod(ObjectId.class, "getId", Object.class, (Class[])null);
               break;
            case 9:
               code.aload().setLocal(oid);
               code.checkcast().setType(StringId.class);
               code.invokevirtual().setMethod(StringId.class, "getId", String.class, (Class[])null);
               break;
            case 14:
               code.aload().setLocal(oid);
               code.checkcast().setType(DateId.class);
               code.invokevirtual().setMethod(DateId.class, "getId", Date.class, (Class[])null);
               break;
            case 17:
               code.anew().setType(Byte.class);
               code.dup();
            case 1:
               code.aload().setLocal(oid);
               code.checkcast().setType(ByteId.class);
               code.invokevirtual().setMethod(ByteId.class, "getId", Byte.TYPE, (Class[])null);
               if (pkcode == 17) {
                  code.invokespecial().setMethod(Byte.class, "<init>", Void.TYPE, new Class[]{Byte.TYPE});
               }
               break;
            case 18:
               code.anew().setType(Character.class);
               code.dup();
            case 2:
               code.aload().setLocal(oid);
               code.checkcast().setType(CharId.class);
               code.invokevirtual().setMethod(CharId.class, "getId", Character.TYPE, (Class[])null);
               if (pkcode == 18) {
                  code.invokespecial().setMethod(Character.class, "<init>", Void.TYPE, new Class[]{Character.TYPE});
               }
               break;
            case 19:
               code.anew().setType(Double.class);
               code.dup();
            case 3:
               code.aload().setLocal(oid);
               code.checkcast().setType(DoubleId.class);
               code.invokevirtual().setMethod(DoubleId.class, "getId", Double.TYPE, (Class[])null);
               if (pkcode == 19) {
                  code.invokespecial().setMethod(Double.class, "<init>", Void.TYPE, new Class[]{Double.TYPE});
               }
               break;
            case 20:
               code.anew().setType(Float.class);
               code.dup();
            case 4:
               code.aload().setLocal(oid);
               code.checkcast().setType(FloatId.class);
               code.invokevirtual().setMethod(FloatId.class, "getId", Float.TYPE, (Class[])null);
               if (pkcode == 20) {
                  code.invokespecial().setMethod(Float.class, "<init>", Void.TYPE, new Class[]{Float.TYPE});
               }
               break;
            case 21:
               code.anew().setType(Integer.class);
               code.dup();
            case 5:
               code.aload().setLocal(oid);
               code.checkcast().setType(IntId.class);
               code.invokevirtual().setMethod(IntId.class, "getId", Integer.TYPE, (Class[])null);
               if (pkcode == 21) {
                  code.invokespecial().setMethod(Integer.class, "<init>", Void.TYPE, new Class[]{Integer.TYPE});
               }
               break;
            case 22:
               code.anew().setType(Long.class);
               code.dup();
            case 6:
               code.aload().setLocal(oid);
               code.checkcast().setType(LongId.class);
               code.invokevirtual().setMethod(LongId.class, "getId", Long.TYPE, (Class[])null);
               if (pkcode == 22) {
                  code.invokespecial().setMethod(Long.class, "<init>", Void.TYPE, new Class[]{Long.TYPE});
               }
               break;
            case 23:
               code.anew().setType(Short.class);
               code.dup();
            case 7:
               code.aload().setLocal(oid);
               code.checkcast().setType(ShortId.class);
               code.invokevirtual().setMethod(ShortId.class, "getId", Short.TYPE, (Class[])null);
               if (pkcode == 23) {
                  code.invokespecial().setMethod(Short.class, "<init>", Void.TYPE, new Class[]{Short.TYPE});
               }
               break;
            case 24:
               code.aload().setLocal(oid);
               code.checkcast().setType(BigDecimalId.class);
               code.invokevirtual().setMethod(BigDecimalId.class, "getId", BigDecimalId.class, (Class[])null);
               break;
            case 25:
               code.aload().setLocal(oid);
               code.checkcast().setType(BigIntegerId.class);
               code.invokevirtual().setMethod(BigIntegerId.class, "getId", BigIntegerId.class, (Class[])null);
         }
      } else if (pkmeta.getObjectIdType() != null) {
         code.aload().setLocal(oid);
         code.checkcast().setType(pktype);
      } else {
         code.aload().setLocal(oid);
      }

      JumpInstruction go2 = code.go2();
      ConstantInstruction def;
      switch (pkcode) {
         case 0:
            def = code.constant().setValue(false);
            break;
         case 1:
            def = code.constant().setValue((short)0);
            break;
         case 2:
            def = code.constant().setValue('\u0000');
            break;
         case 3:
            def = code.constant().setValue(0.0);
            break;
         case 4:
            def = code.constant().setValue(0.0F);
            break;
         case 5:
            def = code.constant().setValue(0);
            break;
         case 6:
            def = code.constant().setValue(0L);
            break;
         case 7:
            def = code.constant().setValue((short)0);
            break;
         default:
            def = code.constant().setNull();
      }

      ifnull1.setTarget(def);
      ifnull2.setTarget(def);
      go2.setTarget(code.nop());
   }

   private void addCopyKeyFieldsFromObjectIdMethod(boolean fieldManager) throws NoSuchMethodException {
      String[] args = fieldManager ? new String[]{OIDFCTYPE.getName(), Object.class.getName()} : new String[]{Object.class.getName()};
      BCMethod method = this._pc.declareMethod("pcCopyKeyFieldsFromObjectId", Void.TYPE.getName(), args);
      Code code = method.getCode(true);
      int id;
      if (this._meta.getPCSuperclass() != null && !this.getCreateSubclass()) {
         this.loadManagedInstance(code, false);

         for(id = 0; id < args.length; ++id) {
            code.aload().setParam(id);
         }

         code.invokespecial().setMethod(this.getType(this._meta.getPCSuperclassMetaData()).getName(), "pcCopyKeyFieldsFromObjectId", Void.TYPE.getName(), args);
      }

      if (fieldManager) {
         code.aload().setParam(1);
      } else {
         code.aload().setParam(0);
      }

      if (!this._meta.isOpenJPAIdentity() && this._meta.isObjectIdTypeShared()) {
         code.checkcast().setType(ObjectId.class);
         code.invokevirtual().setMethod(ObjectId.class, "getId", Object.class, (Class[])null);
      }

      id = code.getNextLocalsIndex();
      Class oidType = this._meta.getObjectIdType();
      code.checkcast().setType(oidType);
      code.astore().setLocal(id);
      FieldMetaData[] fmds = this.getCreateSubclass() ? this._meta.getFields() : this._meta.getDeclaredFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].isPrimaryKey()) {
            String name = fmds[i].getName();
            Class type = fmds[i].getObjectIdFieldType();
            if (!fieldManager && fmds[i].getDeclaredTypeCode() == 15) {
               this.loadManagedInstance(code, false);
               code.dup();
               code.getfield().setField("pcStateManager", SMTYPE);
               code.aload().setLocal(id);
               code.constant().setValue(i);
               code.getstatic().setField("pcInheritedFieldCount", Integer.TYPE);
               code.iadd();
               code.invokeinterface().setMethod(StateManager.class, "getPCPrimaryKey", Object.class, new Class[]{Object.class, Integer.TYPE});
               code.checkcast().setType(fmds[i].getDeclaredType());
            } else {
               Class unwrapped = fmds[i].getDeclaredTypeCode() == 15 ? type : this.unwrapSingleFieldIdentity(fmds[i]);
               if (fieldManager) {
                  code.aload().setParam(0);
                  code.constant().setValue(i);
                  code.getstatic().setField("pcInheritedFieldCount", Integer.TYPE);
                  code.iadd();
               } else {
                  this.loadManagedInstance(code, false);
               }

               if (unwrapped != type) {
                  code.anew().setType(type);
                  code.dup();
               }

               code.aload().setLocal(id);
               if (this._meta.isOpenJPAIdentity()) {
                  if (oidType == ObjectId.class) {
                     code.invokevirtual().setMethod(oidType, "getId", Object.class, (Class[])null);
                     if (!fieldManager && type != Object.class) {
                        code.checkcast().setType(fmds[i].getDeclaredType());
                     }
                  } else if (oidType == DateId.class) {
                     code.invokevirtual().setMethod(oidType, "getId", Date.class, (Class[])null);
                     if (!fieldManager && type != Date.class) {
                        code.checkcast().setType(fmds[i].getDeclaredType());
                     }
                  } else {
                     code.invokevirtual().setMethod(oidType, "getId", unwrapped, (Class[])null);
                     if (unwrapped != type) {
                        code.invokespecial().setMethod(type, "<init>", Void.TYPE, new Class[]{unwrapped});
                     }
                  }
               } else if (this._meta.getAccessType() == 2) {
                  Field field = Reflection.findField(oidType, name, true);
                  if (Modifier.isPublic(field.getModifiers())) {
                     code.getfield().setField(field);
                  } else {
                     code.classconstant().setClass(oidType);
                     code.constant().setValue(name);
                     code.constant().setValue(true);
                     code.invokestatic().setMethod(Reflection.class, "findField", Field.class, new Class[]{Class.class, String.class, Boolean.TYPE});
                     code.invokestatic().setMethod(this.getReflectionGetterMethod(type, Field.class));
                     if (!type.isPrimitive() && type != Object.class) {
                        code.checkcast().setType(type);
                     }
                  }
               } else {
                  Method getter = Reflection.findGetter(oidType, name, true);
                  if (Modifier.isPublic(getter.getModifiers())) {
                     code.invokevirtual().setMethod(getter);
                  } else {
                     code.classconstant().setClass(oidType);
                     code.constant().setValue(name);
                     code.constant().setValue(true);
                     code.invokestatic().setMethod(Reflection.class, "findGetter", Method.class, new Class[]{Class.class, String.class, Boolean.TYPE});
                     code.invokestatic().setMethod(this.getReflectionGetterMethod(type, Method.class));
                     if (!type.isPrimitive() && type != Object.class) {
                        code.checkcast().setType(type);
                     }
                  }
               }
            }

            if (fieldManager) {
               code.invokeinterface().setMethod(this.getFieldConsumerMethod(type));
            } else {
               this.addSetManagedValueCode(code, fmds[i]);
            }
         }
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private Boolean usesClassStringIdConstructor() {
      if (this._meta.getIdentityType() != 2) {
         return Boolean.FALSE;
      } else if (this._meta.isOpenJPAIdentity()) {
         return this._meta.getObjectIdType() == ObjectId.class ? null : Boolean.TRUE;
      } else {
         Class oidType = this._meta.getObjectIdType();

         try {
            oidType.getConstructor(Class.class, String.class);
            return Boolean.TRUE;
         } catch (Throwable var4) {
            try {
               oidType.getConstructor(String.class);
               return Boolean.FALSE;
            } catch (Throwable var3) {
               return null;
            }
         }
      }
   }

   private Class unwrapSingleFieldIdentity(FieldMetaData fmd) {
      if (!fmd.getDefiningMetaData().isOpenJPAIdentity()) {
         return fmd.getDeclaredType();
      } else {
         switch (fmd.getDeclaredTypeCode()) {
            case 17:
               return Byte.TYPE;
            case 18:
               return Character.TYPE;
            case 19:
               return Double.TYPE;
            case 20:
               return Float.TYPE;
            case 21:
               return Integer.TYPE;
            case 22:
               return Long.TYPE;
            case 23:
               return Short.TYPE;
            default:
               return fmd.getDeclaredType();
         }
      }
   }

   private Method getReflectionGetterMethod(Class type, Class argType) throws NoSuchMethodException {
      String name = "get";
      if (type.isPrimitive()) {
         name = name + StringUtils.capitalize(type.getName());
      }

      return Reflection.class.getMethod(name, Object.class, argType);
   }

   private Method getFieldSupplierMethod(Class type) throws NoSuchMethodException {
      return this.getMethod(OIDFSTYPE, type, "fetch", true, false, false);
   }

   private Method getFieldConsumerMethod(Class type) throws NoSuchMethodException {
      return this.getMethod(OIDFCTYPE, type, "store", false, false, false);
   }

   private void addNewObjectIdInstanceMethod(boolean obj) throws NoSuchMethodException {
      Class[] args = obj ? new Class[]{Object.class} : null;
      BCMethod method = this._pc.declareMethod("pcNewObjectIdInstance", Object.class, args);
      Code code = method.getCode(true);
      Boolean usesClsString = this.usesClassStringIdConstructor();
      Class oidType = this._meta.getObjectIdType();
      if (obj && usesClsString == null) {
         String msg = _loc.get("str-cons", oidType, this._meta.getDescribedType()).getMessage();
         code.anew().setType(IllegalArgumentException.class);
         code.dup();
         code.constant().setValue(msg);
         code.invokespecial().setMethod(IllegalArgumentException.class, "<init>", Void.TYPE, new Class[]{String.class});
         code.athrow();
         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      } else {
         if (!this._meta.isOpenJPAIdentity() && this._meta.isObjectIdTypeShared()) {
            code.anew().setType(ObjectId.class);
            code.dup();
            code.classconstant().setClass(this.getType(this._meta));
         }

         code.anew().setType(oidType);
         code.dup();
         if (this._meta.isOpenJPAIdentity() || obj && usesClsString == Boolean.TRUE) {
            code.classconstant().setClass(this.getType(this._meta));
         }

         if (obj) {
            code.aload().setParam(0);
            code.checkcast().setType(String.class);
            if (usesClsString == Boolean.TRUE) {
               args = new Class[]{Class.class, String.class};
            } else if (usesClsString == Boolean.FALSE) {
               args = new Class[]{String.class};
            }
         } else if (this._meta.isOpenJPAIdentity()) {
            this.loadManagedInstance(code, false);
            FieldMetaData pk = this._meta.getPrimaryKeyFields()[0];
            this.addGetManagedValueCode(code, pk);
            if (pk.getDeclaredTypeCode() == 15) {
               this.addExtractObjectIdFieldValueCode(code, pk);
            }

            if (this._meta.getObjectIdType() == ObjectId.class) {
               args = new Class[]{Class.class, Object.class};
            } else if (this._meta.getObjectIdType() == Date.class) {
               args = new Class[]{Class.class, Date.class};
            } else {
               args = new Class[]{Class.class, pk.getObjectIdFieldType()};
            }
         }

         code.invokespecial().setMethod(oidType, "<init>", Void.TYPE, args);
         if (!this._meta.isOpenJPAIdentity() && this._meta.isObjectIdTypeShared()) {
            code.invokespecial().setMethod(ObjectId.class, "<init>", Void.TYPE, new Class[]{Class.class, Object.class});
         }

         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private Method getStateManagerMethod(Class type, String prefix, boolean get, boolean curValue) throws NoSuchMethodException {
      return this.getMethod(SMTYPE, type, prefix, get, true, curValue);
   }

   private Method getMethod(Class owner, Class type, String prefix, boolean get, boolean haspc, boolean curValue) throws NoSuchMethodException {
      String typeName = type.getName();
      if (type.isPrimitive()) {
         typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
      } else if (type.equals(String.class)) {
         typeName = "String";
      } else {
         typeName = "Object";
         type = Object.class;
      }

      List plist = new ArrayList(4);
      if (haspc) {
         plist.add(PCTYPE);
      }

      plist.add(Integer.TYPE);
      if (!get || curValue) {
         plist.add(type);
      }

      if (!get && curValue) {
         plist.add(type);
         plist.add(Integer.TYPE);
      }

      String name = prefix + typeName + "Field";
      Class[] params = (Class[])((Class[])plist.toArray(new Class[plist.size()]));

      try {
         return (Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(owner, name, params));
      } catch (PrivilegedActionException var12) {
         throw (NoSuchMethodException)var12.getException();
      }
   }

   private Instruction throwException(Code code, Class type) {
      Instruction ins = code.anew().setType(type);
      code.dup();
      code.invokespecial().setMethod(type, "<init>", Void.TYPE, (Class[])null);
      code.athrow();
      return ins;
   }

   private void enhanceClass() {
      this._pc.declareInterface(PCTYPE);
      this.addGetEnhancementContractVersionMethod();
      BCMethod method = this._pc.getDeclaredMethod("<init>", (String[])null);
      if (method == null) {
         String name = this._pc.getName();
         if (!this._defCons) {
            throw new UserException(_loc.get("enhance-defaultconst", (Object)name));
         }

         method = this._pc.addDefaultConstructor();
         String access;
         if (this._meta.isDetachable()) {
            method.makePublic();
            access = "public";
         } else if (this._pc.isFinal()) {
            method.makePrivate();
            access = "private";
         } else {
            method.makeProtected();
            access = "protected";
         }

         if (!this._meta.getDescribedType().isInterface() && !this.getCreateSubclass() && this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("enhance-adddefaultconst", name, access));
         }
      }

   }

   private void addFields() {
      this._pc.declareField("pcInheritedFieldCount", Integer.TYPE).setStatic(true);
      this._pc.declareField("pcFieldNames", String[].class).setStatic(true);
      this._pc.declareField("pcFieldTypes", Class[].class).setStatic(true);
      this._pc.declareField("pcFieldFlags", byte[].class).setStatic(true);
      this._pc.declareField("pcPCSuperclass", Class.class).setStatic(true);
      if (this._meta.getPCSuperclass() == null || this.getCreateSubclass()) {
         BCField field = this._pc.declareField("pcStateManager", SMTYPE);
         field.makeProtected();
         field.setTransient(true);
      }

   }

   private void addStaticInitializer() {
      Code code = this.getOrCreateClassInitCode(true);
      if (this._meta.getPCSuperclass() != null) {
         if (this.getCreateSubclass()) {
            code.constant().setValue(0);
            code.putstatic().setField("pcInheritedFieldCount", Integer.TYPE);
         } else {
            code.invokestatic().setMethod(this.getType(this._meta.getPCSuperclassMetaData()).getName(), "pcGetManagedFieldCount", Integer.TYPE.getName(), (String[])null);
            code.putstatic().setField("pcInheritedFieldCount", Integer.TYPE);
         }

         code.classconstant().setClass(this._meta.getPCSuperclassMetaData().getDescribedType());
         code.putstatic().setField("pcPCSuperclass", Class.class);
      }

      FieldMetaData[] fmds = this._meta.getDeclaredFields();
      code.constant().setValue(fmds.length);
      code.anewarray().setType(String.class);

      int i;
      for(i = 0; i < fmds.length; ++i) {
         code.dup();
         code.constant().setValue(i);
         code.constant().setValue(fmds[i].getName());
         code.aastore();
      }

      code.putstatic().setField("pcFieldNames", String[].class);
      code.constant().setValue(fmds.length);
      code.anewarray().setType(Class.class);

      for(i = 0; i < fmds.length; ++i) {
         code.dup();
         code.constant().setValue(i);
         code.classconstant().setClass(fmds[i].getDeclaredType());
         code.aastore();
      }

      code.putstatic().setField("pcFieldTypes", Class[].class);
      code.constant().setValue(fmds.length);
      code.newarray().setType(Byte.TYPE);

      for(i = 0; i < fmds.length; ++i) {
         code.dup();
         code.constant().setValue(i);
         code.constant().setValue((short)getFieldFlag(fmds[i]));
         code.bastore();
      }

      code.putstatic().setField("pcFieldFlags", byte[].class);
      code.classconstant().setClass(this._meta.getDescribedType());
      code.getstatic().setField("pcFieldNames", String[].class);
      code.getstatic().setField("pcFieldTypes", Class[].class);
      code.getstatic().setField("pcFieldFlags", byte[].class);
      code.getstatic().setField("pcPCSuperclass", Class.class);
      if (this._meta.isMapped()) {
         code.constant().setValue(this._meta.getTypeAlias());
      } else {
         code.constant().setNull();
      }

      if (this._pc.isAbstract()) {
         code.constant().setNull();
      } else {
         code.anew().setType(this._pc);
         code.dup();
         code.invokespecial().setMethod("<init>", Void.TYPE, (Class[])null);
      }

      code.invokestatic().setMethod(HELPERTYPE, "register", Void.TYPE, new Class[]{Class.class, String[].class, Class[].class, byte[].class, Class.class, String.class, PCTYPE});
      code.vreturn();
      code.calculateMaxStack();
   }

   private static byte getFieldFlag(FieldMetaData fmd) {
      if (fmd.getManagement() == 0) {
         return -1;
      } else {
         byte flags = 0;
         if (fmd.getDeclaredType().isPrimitive() || Serializable.class.isAssignableFrom(fmd.getDeclaredType())) {
            flags = 16;
         }

         byte flags;
         if (fmd.getManagement() == 1) {
            flags = (byte)(flags | 4);
         } else if (!fmd.isPrimaryKey() && !fmd.isInDefaultFetchGroup()) {
            flags = (byte)(flags | 5);
         } else {
            flags = (byte)(flags | 10);
         }

         return flags;
      }
   }

   private void addSerializationCode() {
      if (!this.externalizeDetached() && Serializable.class.isAssignableFrom(this._meta.getDescribedType())) {
         if (this.getCreateSubclass()) {
            if (!Externalizable.class.isAssignableFrom(this._meta.getDescribedType())) {
               this.addSubclassSerializationCode();
            }

         } else {
            BCField field = this._pc.getDeclaredField("serialVersionUID");
            if (field == null) {
               Long uid = null;

               try {
                  uid = Numbers.valueOf(ObjectStreamClass.lookup(this._meta.getDescribedType()).getSerialVersionUID());
               } catch (Throwable var5) {
                  if (this._log.isTraceEnabled()) {
                     this._log.warn(_loc.get("enhance-uid-access", (Object)this._meta), var5);
                  } else {
                     this._log.warn(_loc.get("enhance-uid-access", (Object)this._meta));
                  }
               }

               if (uid != null) {
                  field = this._pc.declareField("serialVersionUID", Long.TYPE);
                  field.makePrivate();
                  field.setStatic(true);
                  field.setFinal(true);
                  Code code = this.getOrCreateClassInitCode(false);
                  code.beforeFirst();
                  code.constant().setValue(uid);
                  code.putstatic().setField(field);
                  code.calculateMaxStack();
               }
            }

            BCMethod write = this._pc.getDeclaredMethod("writeObject", new Class[]{ObjectOutputStream.class});
            boolean full = write == null;
            if (full) {
               write = this._pc.declareMethod("writeObject", Void.TYPE, new Class[]{ObjectOutputStream.class});
               write.getExceptions(true).addException(IOException.class);
               write.makePrivate();
            }

            this.modifyWriteObjectMethod(write, full);
            BCMethod read = this._pc.getDeclaredMethod("readObject", new Class[]{ObjectInputStream.class});
            full = read == null;
            if (full) {
               read = this._pc.declareMethod("readObject", Void.TYPE, new Class[]{ObjectInputStream.class});
               read.getExceptions(true).addException(IOException.class);
               read.getExceptions(true).addException(ClassNotFoundException.class);
               read.makePrivate();
            }

            this.modifyReadObjectMethod(read, full);
         }
      }
   }

   private void addSubclassSerializationCode() {
      BCMethod method = this._pc.declareMethod("writeReplace", Object.class, (Class[])null);
      method.getExceptions(true).addException(ObjectStreamException.class);
      Code code = method.getCode(true);
      code.anew().setType(this._managedType);
      code.dup();
      code.dup();
      code.invokespecial().setMethod(this._managedType.getType(), "<init>", Void.TYPE, (Class[])null);
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (!fmds[i].isTransient()) {
            code.dup();
            code.aload().setThis();
            this.getfield(code, this._managedType, fmds[i].getName());
            this.putfield(code, this._managedType, fmds[i].getName(), fmds[i].getDeclaredType());
         }
      }

      code.areturn().setType(Object.class);
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private boolean externalizeDetached() {
      return "`syn".equals(this._meta.getDetachedState()) && Serializable.class.isAssignableFrom(this._meta.getDescribedType()) && !this._repos.getConfiguration().getDetachStateInstance().isDetachedStateTransient();
   }

   private void modifyWriteObjectMethod(BCMethod method, boolean full) {
      Code code = method.getCode(true);
      code.beforeFirst();
      this.loadManagedInstance(code, false);
      code.invokevirtual().setMethod("pcSerializing", Boolean.TYPE, (Class[])null);
      int clear = code.getNextLocalsIndex();
      code.istore().setLocal(clear);
      if (full) {
         code.aload().setParam(0);
         code.invokevirtual().setMethod(ObjectOutputStream.class, "defaultWriteObject", Void.TYPE, (Class[])null);
         code.vreturn();
      }

      Instruction tmplate = ((Code)AccessController.doPrivileged(J2DoPrivHelper.newCodeAction())).vreturn();
      code.beforeFirst();

      while(code.searchForward(tmplate)) {
         Instruction ret = code.previous();
         code.iload().setLocal(clear);
         JumpInstruction toret = code.ifeq();
         this.loadManagedInstance(code, false);
         code.constant().setNull();
         code.invokevirtual().setMethod("pcSetDetachedState", Void.TYPE, new Class[]{Object.class});
         toret.setTarget(ret);
         code.next();
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void modifyReadObjectMethod(BCMethod method, boolean full) {
      Code code = method.getCode(true);
      code.beforeFirst();
      if ("`syn".equals(this._meta.getDetachedState())) {
         this.loadManagedInstance(code, false);
         code.getstatic().setField(PersistenceCapable.class, "DESERIALIZED", Object.class);
         code.invokevirtual().setMethod("pcSetDetachedState", Void.TYPE, new Class[]{Object.class});
      }

      if (full) {
         code.aload().setParam(0);
         code.invokevirtual().setMethod(ObjectInputStream.class, "defaultReadObject", Void.TYPE, (Class[])null);
         code.vreturn();
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addIsDetachedMethod() throws NoSuchMethodException {
      BCMethod method = this._pc.declareMethod("pcIsDetached", Boolean.class, (Class[])null);
      method.makePublic();
      Code code = method.getCode(true);
      boolean needsDefinitiveMethod = this.writeIsDetachedMethod(code);
      code.calculateMaxStack();
      code.calculateMaxLocals();
      if (needsDefinitiveMethod) {
         method = this._pc.declareMethod("pcisDetachedStateDefinitive", Boolean.TYPE, (Class[])null);
         method.makePrivate();
         code = method.getCode(true);
         code.constant().setValue(false);
         code.ireturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private boolean writeIsDetachedMethod(Code code) throws NoSuchMethodException {
      if (!this._meta.isDetachable()) {
         code.getstatic().setField(Boolean.class, "FALSE", Boolean.class);
         code.areturn();
         return false;
      } else {
         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", SMTYPE);
         JumpInstruction ifins = code.ifnull();
         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", SMTYPE);
         code.invokeinterface().setMethod(SMTYPE, "isDetached", Boolean.TYPE, (Class[])null);
         JumpInstruction iffalse = code.ifeq();
         code.getstatic().setField(Boolean.class, "TRUE", Boolean.class);
         code.areturn();
         iffalse.setTarget(code.getstatic().setField(Boolean.class, "FALSE", Boolean.class));
         code.areturn();
         Boolean state = this._meta.usesDetachedState();
         JumpInstruction notdeser = null;
         if (state != Boolean.FALSE) {
            ifins.setTarget(this.loadManagedInstance(code, false));
            code.invokevirtual().setMethod("pcGetDetachedState", Object.class, (Class[])null);
            ifins = code.ifnull();
            this.loadManagedInstance(code, false);
            code.invokevirtual().setMethod("pcGetDetachedState", Object.class, (Class[])null);
            code.getstatic().setField(PersistenceCapable.class, "DESERIALIZED", Object.class);
            notdeser = code.ifacmpeq();
            code.getstatic().setField(Boolean.class, "TRUE", Boolean.class);
            code.areturn();
            if (state == Boolean.TRUE) {
               Instruction target = code.getstatic().setField(Boolean.class, "FALSE", Boolean.class);
               ifins.setTarget(target);
               notdeser.setTarget(target);
               code.areturn();
               return false;
            }
         }

         Instruction target = code.nop();
         ifins.setTarget(target);
         if (notdeser != null) {
            notdeser.setTarget(target);
         }

         FieldMetaData version = this._meta.getVersionField();
         JumpInstruction ifins;
         if (state != Boolean.TRUE && version != null) {
            this.loadManagedInstance(code, false);
            this.addGetManagedValueCode(code, version);
            ifins = ifDefaultValue(code, version);
            code.getstatic().setField(Boolean.class, "TRUE", Boolean.class);
            code.areturn();
            ifins.setTarget(code.getstatic().setField(Boolean.class, "FALSE", Boolean.class));
            code.areturn();
            return false;
         } else {
            ifins = null;
            JumpInstruction ifins2 = null;
            boolean hasAutoAssignedPK = false;
            if (state != Boolean.TRUE && this._meta.getIdentityType() == 2) {
               FieldMetaData[] pks = this._meta.getPrimaryKeyFields();

               for(int i = 0; i < pks.length; ++i) {
                  if (pks[i].getValueStrategy() != 0) {
                     target = this.loadManagedInstance(code, false);
                     if (ifins != null) {
                        ifins.setTarget(target);
                     }

                     if (ifins2 != null) {
                        ifins2.setTarget(target);
                     }

                     ifins2 = null;
                     this.addGetManagedValueCode(code, pks[i]);
                     ifins = ifDefaultValue(code, pks[i]);
                     if (pks[i].getDeclaredTypeCode() == 9) {
                        code.constant().setValue("");
                        this.loadManagedInstance(code, false);
                        this.addGetManagedValueCode(code, pks[i]);
                        code.invokevirtual().setMethod(String.class, "equals", Boolean.TYPE, new Class[]{Object.class});
                        ifins2 = code.ifne();
                     }

                     code.getstatic().setField(Boolean.class, "TRUE", Boolean.class);
                     code.areturn();
                  }
               }
            }

            target = code.nop();
            if (ifins != null) {
               ifins.setTarget(target);
            }

            if (ifins2 != null) {
               ifins2.setTarget(target);
            }

            if (hasAutoAssignedPK) {
               code.getstatic().setField(Boolean.class, "FALSE", Boolean.class);
               code.areturn();
               return false;
            } else {
               code.aload().setThis();
               code.invokespecial().setMethod("pcisDetachedStateDefinitive", Boolean.TYPE, (Class[])null);
               ifins = code.ifne();
               code.constant().setNull();
               code.areturn();
               ifins.setTarget(code.nop());
               if (state != null || "`syn".equals(this._meta.getDetachedState()) && Serializable.class.isAssignableFrom(this._meta.getDescribedType()) && this._repos.getConfiguration().getDetachStateInstance().isDetachedStateTransient()) {
                  if (state == null) {
                     this.loadManagedInstance(code, false);
                     code.invokevirtual().setMethod("pcGetDetachedState", Object.class, (Class[])null);
                     ifins = code.ifnonnull();
                     code.getstatic().setField(Boolean.class, "FALSE", Boolean.class);
                     code.areturn();
                     ifins.setTarget(code.nop());
                  }

                  code.constant().setNull();
                  code.areturn();
                  return true;
               } else {
                  code.getstatic().setField(Boolean.class, "FALSE", Boolean.class);
                  code.areturn();
                  return true;
               }
            }
         }
      }
   }

   private static JumpInstruction ifDefaultValue(Code code, FieldMetaData fmd) {
      switch (fmd.getDeclaredTypeCode()) {
         case 0:
         case 1:
         case 2:
         case 5:
         case 7:
            return code.ifeq();
         case 3:
            code.constant().setValue(0.0);
            code.dcmpl();
            return code.ifeq();
         case 4:
            code.constant().setValue(0.0F);
            code.fcmpl();
            return code.ifeq();
         case 6:
            code.constant().setValue(0L);
            code.lcmp();
            return code.ifeq();
         default:
            return code.ifnull();
      }
   }

   private Code getOrCreateClassInitCode(boolean replaceLast) {
      BCMethod clinit = this._pc.getDeclaredMethod("<clinit>");
      Code code;
      if (clinit != null) {
         code = clinit.getCode(true);
         if (replaceLast) {
            Code template = (Code)AccessController.doPrivileged(J2DoPrivHelper.newCodeAction());
            code.searchForward(template.vreturn());
            code.previous();
            code.set(template.nop());
            code.next();
         }

         return code;
      } else {
         clinit = this._pc.declareMethod("<clinit>", Void.TYPE, (Class[])null);
         clinit.makePackage();
         clinit.setStatic(true);
         clinit.setFinal(true);
         code = clinit.getCode(true);
         if (!replaceLast) {
            code.vreturn();
            code.previous();
         }

         return code;
      }
   }

   private void addCloningCode() {
      if (this._meta.getPCSuperclass() == null || this.getCreateSubclass()) {
         BCMethod clone = this._pc.getDeclaredMethod("clone", (String[])null);
         String superName = this._managedType.getSuperclassName();
         Code code = null;
         if (clone != null) {
            code = clone.getCode(false);
            if (code == null) {
               return;
            }
         } else {
            boolean isCloneable = Cloneable.class.isAssignableFrom(this._managedType.getType());
            boolean extendsObject = superName.equals(Object.class.getName());
            if (!isCloneable || !extendsObject && !this.getCreateSubclass()) {
               return;
            }

            if (!this.getCreateSubclass() && this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("enhance-cloneable", (Object)this._managedType.getName()));
            }

            clone = this._pc.declareMethod("clone", Object.class, (Class[])null);
            if (!this.setVisibilityToSuperMethod(clone)) {
               clone.makeProtected();
            }

            clone.getExceptions(true).addException(CloneNotSupportedException.class);
            code = clone.getCode(true);
            this.loadManagedInstance(code, false);
            code.invokespecial().setMethod(superName, "clone", Object.class.getName(), (String[])null);
            code.areturn();
         }

         Instruction template = ((Code)AccessController.doPrivileged(J2DoPrivHelper.newCodeAction())).invokespecial().setMethod(superName, "clone", Object.class.getName(), (String[])null);
         code.beforeFirst();
         if (code.searchForward(template)) {
            code.dup();
            code.checkcast().setType(this._pc);
            code.constant().setNull();
            code.putfield().setField("pcStateManager", SMTYPE);
            code.calculateMaxStack();
            code.calculateMaxLocals();
         }

      }
   }

   public AuxiliaryEnhancer[] getAuxiliaryEnhancers() {
      return _auxEnhancers;
   }

   private void runAuxiliaryEnhancers() {
      for(int i = 0; i < _auxEnhancers.length; ++i) {
         _auxEnhancers[i].run(this._pc, this._meta);
      }

   }

   private boolean skipEnhance(BCMethod method) {
      if ("<init>".equals(method.getName())) {
         return true;
      } else {
         for(int i = 0; i < _auxEnhancers.length; ++i) {
            if (_auxEnhancers[i].skipEnhance(method)) {
               return true;
            }
         }

         return false;
      }
   }

   private void addAccessors() throws NoSuchMethodException {
      FieldMetaData[] fmds = this.getCreateSubclass() ? this._meta.getFields() : this._meta.getDeclaredFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (this.getCreateSubclass()) {
            if (!this.getRedefine() && this._meta.getAccessType() != 2) {
               this.addSubclassSetMethod(fmds[i]);
               this.addSubclassGetMethod(fmds[i]);
            }
         } else {
            this.addGetMethod(i, fmds[i]);
            this.addSetMethod(i, fmds[i]);
         }
      }

   }

   private void addSubclassSetMethod(FieldMetaData fmd) throws NoSuchMethodException {
      Class propType = fmd.getDeclaredType();
      String setterName = getSetterName(fmd);
      BCMethod setter = this._pc.declareMethod(setterName, Void.TYPE, new Class[]{propType});
      this.setVisibilityToSuperMethod(setter);
      Code code = setter.getCode(true);
      if (!this.getRedefine()) {
         code.aload().setThis();
         this.addGetManagedValueCode(code, fmd);
         int val = code.getNextLocalsIndex();
         code.xstore().setLocal(val).setType(fmd.getDeclaredType());
         this.addNotifyMutation(code, fmd, val, 0);
      }

      code.aload().setThis();
      code.xload().setParam(0).setType(propType);
      code.invokespecial().setMethod(this._managedType.getType(), setterName, Void.TYPE, new Class[]{propType});
      code.vreturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private boolean setVisibilityToSuperMethod(BCMethod method) {
      BCMethod[] methods = this._managedType.getMethods(method.getName(), method.getParamTypes());
      if (methods.length == 0) {
         throw new UserException(_loc.get("no-accessor", this._managedType.getName(), method.getName()));
      } else {
         BCMethod superMeth = methods[0];
         if (superMeth.isPrivate()) {
            method.makePrivate();
            return true;
         } else if (superMeth.isPackage()) {
            method.makePackage();
            return true;
         } else if (superMeth.isProtected()) {
            method.makeProtected();
            return true;
         } else if (superMeth.isPublic()) {
            method.makePublic();
            return true;
         } else {
            return false;
         }
      }
   }

   private void addSubclassGetMethod(FieldMetaData fmd) {
      String methName = "get" + StringUtils.capitalize(fmd.getName());
      if (this._managedType.getMethods(methName, new Class[0]).length == 0) {
         methName = "is" + StringUtils.capitalize(fmd.getName());
      }

      BCMethod getter = this._pc.declareMethod(methName, fmd.getDeclaredType(), (Class[])null);
      this.setVisibilityToSuperMethod(getter);
      getter.makePublic();
      Code code = getter.getCode(true);
      if (!this.getRedefine()) {
         this.addNotifyAccess(code, fmd);
      }

      code.aload().setThis();
      code.invokespecial().setMethod(this._managedType.getType(), methName, fmd.getDeclaredType(), (Class[])null);
      code.xreturn().setType(fmd.getDeclaredType());
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addGetMethod(int index, FieldMetaData fmd) throws NoSuchMethodException {
      BCMethod method = this.createGetMethod(fmd);
      Code code = method.getCode(true);
      byte fieldFlag = getFieldFlag(fmd);
      if ((fieldFlag & 1) == 0 && (fieldFlag & 2) == 0) {
         this.loadManagedInstance(code, true);
         this.addGetManagedValueCode(code, fmd);
         code.xreturn().setType(fmd.getDeclaredType());
         code.calculateMaxStack();
         code.calculateMaxLocals();
      } else {
         this.loadManagedInstance(code, true);
         code.getfield().setField("pcStateManager", SMTYPE);
         JumpInstruction ifins = code.ifnonnull();
         this.loadManagedInstance(code, true);
         this.addGetManagedValueCode(code, fmd);
         code.xreturn().setType(fmd.getDeclaredType());
         int fieldLocal = code.getNextLocalsIndex();
         ifins.setTarget(code.getstatic().setField("pcInheritedFieldCount", Integer.TYPE));
         code.constant().setValue(index);
         code.iadd();
         code.istore().setLocal(fieldLocal);
         this.loadManagedInstance(code, true);
         code.getfield().setField("pcStateManager", SMTYPE);
         code.iload().setLocal(fieldLocal);
         code.invokeinterface().setMethod(SMTYPE, "accessingField", Void.TYPE, new Class[]{Integer.TYPE});
         this.loadManagedInstance(code, true);
         this.addGetManagedValueCode(code, fmd);
         code.xreturn().setType(fmd.getDeclaredType());
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void addSetMethod(int index, FieldMetaData fmd) throws NoSuchMethodException {
      BCMethod method = this.createSetMethod(fmd);
      Code code = method.getCode(true);
      int firstParamOffset = this.getAccessorParameterOffset();
      this.loadManagedInstance(code, true);
      code.getfield().setField("pcStateManager", SMTYPE);
      JumpInstruction ifins = code.ifnonnull();
      this.loadManagedInstance(code, true);
      code.xload().setParam(firstParamOffset);
      this.addSetManagedValueCode(code, fmd);
      code.vreturn();
      ifins.setTarget(this.loadManagedInstance(code, true));
      code.getfield().setField("pcStateManager", SMTYPE);
      this.loadManagedInstance(code, true);
      code.getstatic().setField("pcInheritedFieldCount", Integer.TYPE);
      code.constant().setValue(index);
      code.iadd();
      this.loadManagedInstance(code, true);
      this.addGetManagedValueCode(code, fmd);
      code.xload().setParam(firstParamOffset);
      code.constant().setValue(0);
      code.invokeinterface().setMethod(this.getStateManagerMethod(fmd.getDeclaredType(), "setting", false, true));
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addAttachDetachCode() throws NoSuchMethodException {
      boolean parentDetachable = false;

      for(ClassMetaData parent = this._meta.getPCSuperclassMetaData(); parent != null; parent = parent.getPCSuperclassMetaData()) {
         if (parent.isDetachable()) {
            parentDetachable = true;
            break;
         }
      }

      if (this._meta.getPCSuperclass() == null || this.getCreateSubclass() || parentDetachable != this._meta.isDetachable()) {
         this.addIsDetachedMethod();
         this.addDetachedStateMethods(this._meta.usesDetachedState() != Boolean.FALSE);
      }

      if (this.externalizeDetached()) {
         try {
            this.addDetachExternalize(parentDetachable, this._meta.usesDetachedState() != Boolean.FALSE);
         } catch (NoSuchMethodException var3) {
            throw new GeneralException(var3);
         }
      }

   }

   private void addDetachedStateMethods(boolean impl) {
      Field detachField = this._meta.getDetachedStateField();
      String name = null;
      String declarer = null;
      if (impl && detachField == null) {
         name = "pcDetachedState";
         declarer = this._pc.getName();
         BCField field = this._pc.declareField(name, Object.class);
         field.makePrivate();
         field.setTransient(true);
      } else if (impl) {
         name = detachField.getName();
         declarer = detachField.getDeclaringClass().getName();
      }

      BCMethod method = this._pc.declareMethod("pcGetDetachedState", Object.class, (Class[])null);
      method.setStatic(false);
      method.makePublic();
      int access = method.getAccessFlags();
      Code code = method.getCode(true);
      if (impl) {
         this.loadManagedInstance(code, false);
         this.getfield(code, this._managedType.getProject().loadClass(declarer), name);
      } else {
         code.constant().setNull();
      }

      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
      method = this._pc.declareMethod("pcSetDetachedState", Void.TYPE, new Class[]{Object.class});
      method.setAccessFlags(access);
      code = method.getCode(true);
      if (impl) {
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         this.putfield(code, this._managedType.getProject().loadClass(declarer), name, Object.class);
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void getfield(Code code, BCClass declarer, String attrName) {
      if (declarer == null) {
         declarer = this._managedType;
      }

      String fieldName = this.toBackingFieldName(attrName);
      BCField field = null;

      label56:
      for(BCClass bc = this._pc; bc != null; bc = bc.getSuperclassBC()) {
         BCField[] fields = (BCField[])((BCField[])AccessController.doPrivileged(J2DoPrivHelper.getBCClassFieldsAction(bc, fieldName)));

         for(int i = 0; i < fields.length; ++i) {
            field = fields[i];
            if (fields[i].getDeclarer() == declarer) {
               break label56;
            }
         }
      }

      if (!this.getCreateSubclass() || code.getMethod().getDeclarer() != this._pc || field != null && field.isPublic()) {
         code.getfield().setField(declarer.getName(), fieldName, field.getType().getName());
      } else {
         code.classconstant().setClass(declarer);
         code.constant().setValue(fieldName);
         code.constant().setValue(true);
         code.invokestatic().setMethod(Reflection.class, "findField", Field.class, new Class[]{Class.class, String.class, Boolean.TYPE});
         Class type = this._meta.getField(attrName).getDeclaredType();

         try {
            code.invokestatic().setMethod(this.getReflectionGetterMethod(type, Field.class));
         } catch (NoSuchMethodException var9) {
            throw new InternalException(var9);
         }

         if (!type.isPrimitive() && type != Object.class) {
            code.checkcast().setType(type);
         }
      }

   }

   private void putfield(Code code, BCClass declarer, String attrName, Class fieldType) {
      if (declarer == null) {
         declarer = this._managedType;
      }

      String fieldName = this.toBackingFieldName(attrName);
      if (!this.getRedefine() && !this.getCreateSubclass()) {
         code.putfield().setField(declarer.getName(), fieldName, fieldType.getName());
      } else {
         code.classconstant().setClass(declarer);
         code.constant().setValue(fieldName);
         code.constant().setValue(true);
         code.invokestatic().setMethod(Reflection.class, "findField", Field.class, new Class[]{Class.class, String.class, Boolean.TYPE});
         code.invokestatic().setMethod(Reflection.class, "set", Void.TYPE, new Class[]{Object.class, fieldType.isPrimitive() ? fieldType : Object.class, Field.class});
      }

   }

   private String toBackingFieldName(String name) {
      if (this._meta.getAccessType() == 4 && this._attrsToFields != null && this._attrsToFields.containsKey(name)) {
         name = (String)this._attrsToFields.get(name);
      }

      return name;
   }

   private String fromBackingFieldName(String name) {
      return this._meta != null && this._meta.getAccessType() == 4 && this._fieldsToAttrs != null && this._fieldsToAttrs.containsKey(name) ? (String)this._fieldsToAttrs.get(name) : name;
   }

   private void addDetachExternalize(boolean parentDetachable, boolean detachedState) throws NoSuchMethodException {
      BCMethod meth = this._pc.getDeclaredMethod("<init>", (String[])null);
      if (!meth.isPublic()) {
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("enhance-defcons-extern", (Object)this._meta.getDescribedType()));
         }

         meth.makePublic();
      }

      if (!Externalizable.class.isAssignableFrom(this._meta.getDescribedType())) {
         this._pc.declareInterface(Externalizable.class);
      }

      Class[] input = new Class[]{ObjectInputStream.class};
      Class[] output = new Class[]{ObjectOutputStream.class};
      if (this._managedType.getDeclaredMethod("readObject", input) == null && this._managedType.getDeclaredMethod("writeObject", output) == null) {
         input[0] = ObjectInput.class;
         output[0] = ObjectOutput.class;
         if (this._managedType.getDeclaredMethod("readExternal", input) == null && this._managedType.getDeclaredMethod("writeExternal", output) == null) {
            BCField[] fields = this._managedType.getDeclaredFields();
            Collection unmgd = new ArrayList(fields.length);

            for(int i = 0; i < fields.length; ++i) {
               if (!fields[i].isTransient() && !fields[i].isStatic() && !fields[i].isFinal() && !fields[i].getName().startsWith("pc") && this._meta.getDeclaredField(fields[i].getName()) == null) {
                  unmgd.add(fields[i]);
               }
            }

            this.addReadExternal(parentDetachable, detachedState);
            this.addReadUnmanaged(unmgd, parentDetachable);
            this.addWriteExternal(parentDetachable, detachedState);
            this.addWriteUnmanaged(unmgd, parentDetachable);
         } else {
            throw new UserException(_loc.get("detach-custom-extern", (Object)this._meta));
         }
      } else {
         throw new UserException(_loc.get("detach-custom-ser", (Object)this._meta));
      }
   }

   private void addReadExternal(boolean parentDetachable, boolean detachedState) throws NoSuchMethodException {
      Class[] inargs = new Class[]{ObjectInput.class};
      BCMethod meth = this._pc.declareMethod("readExternal", Void.TYPE, inargs);
      Exceptions exceps = meth.getExceptions(true);
      exceps.addException(IOException.class);
      exceps.addException(ClassNotFoundException.class);
      Code code = meth.getCode(true);
      Class sup = this._meta.getDescribedType().getSuperclass();
      if (!parentDetachable && Externalizable.class.isAssignableFrom(sup)) {
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         code.invokespecial().setMethod(sup, "readExternal", Void.TYPE, inargs);
      }

      this.loadManagedInstance(code, false);
      code.aload().setParam(0);
      code.invokevirtual().setMethod(this.getType(this._meta), "pcReadUnmanaged", Void.TYPE, inargs);
      if (detachedState) {
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         code.invokeinterface().setMethod(ObjectInput.class, "readObject", Object.class, (Class[])null);
         code.invokevirtual().setMethod("pcSetDetachedState", Void.TYPE, new Class[]{Object.class});
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         code.invokeinterface().setMethod(ObjectInput.class, "readObject", Object.class, (Class[])null);
         code.checkcast().setType(StateManager.class);
         code.invokevirtual().setMethod("pcReplaceStateManager", Void.TYPE, new Class[]{StateManager.class});
      }

      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (!fmds[i].isTransient()) {
            this.readExternal(code, fmds[i].getName(), fmds[i].getDeclaredType(), fmds[i]);
         }
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addReadUnmanaged(Collection unmgd, boolean parentDetachable) throws NoSuchMethodException {
      Class[] inargs = new Class[]{ObjectInput.class};
      BCMethod meth = this._pc.declareMethod("pcReadUnmanaged", Void.TYPE, inargs);
      meth.makeProtected();
      Exceptions exceps = meth.getExceptions(true);
      exceps.addException(IOException.class);
      exceps.addException(ClassNotFoundException.class);
      Code code = meth.getCode(true);
      if (parentDetachable) {
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         code.invokespecial().setMethod(this.getType(this._meta.getPCSuperclassMetaData()), "pcReadUnmanaged", Void.TYPE, inargs);
      }

      Iterator itr = unmgd.iterator();

      while(itr.hasNext()) {
         BCField field = (BCField)itr.next();
         this.readExternal(code, field.getName(), field.getType(), (FieldMetaData)null);
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void readExternal(Code code, String fieldName, Class type, FieldMetaData fmd) throws NoSuchMethodException {
      String methName;
      if (type.isPrimitive()) {
         methName = type.getName();
         methName = Character.toUpperCase(methName.charAt(0)) + methName.substring(1);
         methName = "read" + methName;
      } else {
         methName = "readObject";
      }

      this.loadManagedInstance(code, false);
      code.aload().setParam(0);
      Class ret = type.isPrimitive() ? type : Object.class;
      code.invokeinterface().setMethod(ObjectInput.class, methName, ret, (Class[])null);
      if (!type.isPrimitive() && type != Object.class) {
         code.checkcast().setType(type);
      }

      if (fmd == null) {
         this.putfield(code, (BCClass)null, fieldName, type);
      } else {
         this.addSetManagedValueCode(code, fmd);
         switch (fmd.getDeclaredTypeCode()) {
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
            case 28:
               this.loadManagedInstance(code, false);
               code.getfield().setField("pcStateManager", SMTYPE);
               IfInstruction ifins = code.ifnull();
               this.loadManagedInstance(code, false);
               code.getfield().setField("pcStateManager", SMTYPE);
               code.constant().setValue(fmd.getIndex());
               code.invokeinterface().setMethod(SMTYPE, "proxyDetachedDeserialized", Void.TYPE, new Class[]{Integer.TYPE});
               ifins.setTarget(code.nop());
         }
      }

   }

   private void addWriteExternal(boolean parentDetachable, boolean detachedState) throws NoSuchMethodException {
      Class[] outargs = new Class[]{ObjectOutput.class};
      BCMethod meth = this._pc.declareMethod("writeExternal", Void.TYPE, outargs);
      Exceptions exceps = meth.getExceptions(true);
      exceps.addException(IOException.class);
      Code code = meth.getCode(true);
      Class sup = this.getType(this._meta).getSuperclass();
      if (!parentDetachable && Externalizable.class.isAssignableFrom(sup)) {
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         code.invokespecial().setMethod(sup, "writeExternal", Void.TYPE, outargs);
      }

      this.loadManagedInstance(code, false);
      code.aload().setParam(0);
      code.invokevirtual().setMethod(this.getType(this._meta), "pcWriteUnmanaged", Void.TYPE, outargs);
      JumpInstruction go2 = null;
      if (detachedState) {
         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", SMTYPE);
         IfInstruction ifnull = code.ifnull();
         this.loadManagedInstance(code, false);
         code.getfield().setField("pcStateManager", SMTYPE);
         code.aload().setParam(0);
         code.invokeinterface().setMethod(SMTYPE, "writeDetached", Boolean.TYPE, outargs);
         go2 = code.ifeq();
         code.vreturn();
         Class[] objargs = new Class[]{Object.class};
         ifnull.setTarget(code.aload().setParam(0));
         this.loadManagedInstance(code, false);
         code.invokevirtual().setMethod("pcGetDetachedState", Object.class, (Class[])null);
         code.invokeinterface().setMethod(ObjectOutput.class, "writeObject", Void.TYPE, objargs);
         code.aload().setParam(0);
         code.constant().setValue((Object)null);
         code.invokeinterface().setMethod(ObjectOutput.class, "writeObject", Void.TYPE, objargs);
      }

      if (go2 != null) {
         go2.setTarget(code.nop());
      }

      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (!fmds[i].isTransient()) {
            this.writeExternal(code, fmds[i].getName(), fmds[i].getDeclaredType(), fmds[i]);
         }
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addWriteUnmanaged(Collection unmgd, boolean parentDetachable) throws NoSuchMethodException {
      Class[] outargs = new Class[]{ObjectOutput.class};
      BCMethod meth = this._pc.declareMethod("pcWriteUnmanaged", Void.TYPE, outargs);
      meth.makeProtected();
      Exceptions exceps = meth.getExceptions(true);
      exceps.addException(IOException.class);
      Code code = meth.getCode(true);
      if (parentDetachable) {
         this.loadManagedInstance(code, false);
         code.aload().setParam(0);
         code.invokespecial().setMethod(this.getType(this._meta.getPCSuperclassMetaData()), "pcWriteUnmanaged", Void.TYPE, outargs);
      }

      Iterator itr = unmgd.iterator();

      while(itr.hasNext()) {
         BCField field = (BCField)itr.next();
         this.writeExternal(code, field.getName(), field.getType(), (FieldMetaData)null);
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void writeExternal(Code code, String fieldName, Class type, FieldMetaData fmd) throws NoSuchMethodException {
      String methName;
      if (type.isPrimitive()) {
         methName = type.getName();
         methName = Character.toUpperCase(methName.charAt(0)) + methName.substring(1);
         methName = "write" + methName;
      } else {
         methName = "writeObject";
      }

      code.aload().setParam(0);
      this.loadManagedInstance(code, false);
      if (fmd == null) {
         this.getfield(code, (BCClass)null, fieldName);
      } else {
         this.addGetManagedValueCode(code, fmd);
      }

      Class[] args = new Class[]{type};
      if (type != Byte.TYPE && type != Character.TYPE && type != Short.TYPE) {
         if (!type.isPrimitive()) {
            args[0] = Object.class;
         }
      } else {
         args[0] = Integer.TYPE;
      }

      code.invokeinterface().setMethod(ObjectOutput.class, methName, Void.TYPE, args);
   }

   private void addGetManagedValueCode(Code code, FieldMetaData fmd) throws NoSuchMethodException {
      this.addGetManagedValueCode(code, fmd, true);
   }

   private void addGetManagedValueCode(Code code, FieldMetaData fmd, boolean fromSameClass) throws NoSuchMethodException {
      if (!this.getRedefine() && this._meta.getAccessType() != 2) {
         Method meth;
         if (this.getCreateSubclass()) {
            if (fromSameClass) {
               meth = (Method)fmd.getBackingMember();
               code.invokespecial().setMethod(meth);
            } else {
               this.getfield(code, (BCClass)null, fmd.getName());
            }
         } else {
            meth = (Method)fmd.getBackingMember();
            code.invokevirtual().setMethod("pc" + meth.getName(), meth.getReturnType(), meth.getParameterTypes());
         }
      } else {
         this.getfield(code, (BCClass)null, fmd.getName());
      }

   }

   private void addSetManagedValueCode(Code code, FieldMetaData fmd) throws NoSuchMethodException {
      if (!this.getRedefine() && this._meta.getAccessType() != 2) {
         if (this.getCreateSubclass()) {
            code.invokespecial().setMethod(this._managedType.getType(), getSetterName(fmd), Void.TYPE, new Class[]{fmd.getDeclaredType()});
         } else {
            code.invokevirtual().setMethod("pc" + getSetterName(fmd), Void.TYPE, new Class[]{fmd.getDeclaredType()});
         }
      } else {
         this.putfield(code, (BCClass)null, fmd.getName(), fmd.getDeclaredType());
      }

   }

   private int getAccessorParameterOffset() {
      return this._meta.getAccessType() == 2 ? 1 : 0;
   }

   private Instruction loadManagedInstance(Code code, boolean forStatic) {
      return (Instruction)(this._meta.getAccessType() == 2 && forStatic ? code.aload().setParam(0) : code.aload().setThis());
   }

   private BCMethod createGetMethod(FieldMetaData fmd) {
      BCMethod getter;
      if (this._meta.getAccessType() == 2) {
         BCField field = this._pc.getDeclaredField(fmd.getName());
         getter = this._pc.declareMethod("pcGet" + fmd.getName(), fmd.getDeclaredType().getName(), new String[]{this._pc.getName()});
         getter.setAccessFlags(field.getAccessFlags() & -129 & -65);
         getter.setStatic(true);
         getter.setFinal(true);
         return getter;
      } else {
         Method meth = (Method)fmd.getBackingMember();
         getter = this._pc.getDeclaredMethod(meth.getName(), meth.getParameterTypes());
         BCMethod newgetter = this._pc.declareMethod("pc" + meth.getName(), meth.getReturnType(), meth.getParameterTypes());
         newgetter.setAccessFlags(getter.getAccessFlags());
         newgetter.makeProtected();
         transferCodeAttributes(getter, newgetter);
         return getter;
      }
   }

   private BCMethod createSetMethod(FieldMetaData fmd) {
      BCMethod setter;
      if (this._meta.getAccessType() == 2) {
         BCField field = this._pc.getDeclaredField(fmd.getName());
         setter = this._pc.declareMethod("pcSet" + fmd.getName(), Void.TYPE, new Class[]{this.getType(this._meta), fmd.getDeclaredType()});
         setter.setAccessFlags(field.getAccessFlags() & -129 & -65);
         setter.setStatic(true);
         setter.setFinal(true);
         return setter;
      } else {
         setter = this._pc.getDeclaredMethod(getSetterName(fmd), new Class[]{fmd.getDeclaredType()});
         BCMethod newsetter = this._pc.declareMethod("pc" + setter.getName(), setter.getReturnName(), setter.getParamNames());
         newsetter.setAccessFlags(setter.getAccessFlags());
         newsetter.makeProtected();
         transferCodeAttributes(setter, newsetter);
         return setter;
      }
   }

   private void addGetEnhancementContractVersionMethod() {
      BCMethod method = this._pc.declareMethod("pcGetEnhancementContractVersion", Integer.TYPE, (Class[])null);
      method.makePublic();
      Code code = method.getCode(true);
      code.constant().setValue(2);
      code.ireturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   public Class getType(ClassMetaData meta) {
      return meta.getInterfaceImpl() != null ? meta.getInterfaceImpl() : meta.getDescribedType();
   }

   private static void transferCodeAttributes(BCMethod from, BCMethod to) {
      Code code = from.getCode(false);
      if (code != null) {
         to.addAttribute(code);
         from.removeCode();
      }

      Exceptions exceps = from.getExceptions(false);
      if (exceps != null) {
         to.addAttribute(exceps);
      }

   }

   public static void main(String[] args) {
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      if (!run(args, opts)) {
         System.err.println(_loc.get("enhance-usage"));
      }

   }

   public static boolean run(final String[] args, Options opts) {
      return Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws IOException {
            OpenJPAConfiguration conf = new OpenJPAConfigurationImpl();

            boolean var3;
            try {
               var3 = PCEnhancer.run(conf, args, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
   }

   public static boolean run(OpenJPAConfiguration conf, String[] args, Options opts) throws IOException {
      Flags flags = new Flags();
      flags.directory = Files.getFile(opts.removeProperty("directory", "d", (String)null), (ClassLoader)null);
      flags.addDefaultConstructor = opts.removeBooleanProperty("addDefaultConstructor", "adc", flags.addDefaultConstructor);
      flags.tmpClassLoader = opts.removeBooleanProperty("tmpClassLoader", "tcl", flags.tmpClassLoader);
      flags.enforcePropertyRestrictions = opts.removeBooleanProperty("enforcePropertyRestrictions", "epr", flags.enforcePropertyRestrictions);
      BytecodeWriter writer = (BytecodeWriter)opts.get(PCEnhancer.class.getName() + "#bytecodeWriter");
      Configurations.populateConfiguration(conf, opts);
      return run(conf, args, flags, (MetaDataRepository)null, writer, (ClassLoader)null);
   }

   public static boolean run(OpenJPAConfiguration conf, String[] args, Flags flags, MetaDataRepository repos, BytecodeWriter writer, ClassLoader loader) throws IOException {
      if (loader == null) {
         loader = conf.getClassResolverInstance().getClassLoader(PCEnhancer.class, (ClassLoader)null);
      }

      if (flags.tmpClassLoader) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newTemporaryClassLoaderAction(loader));
      }

      if (repos == null) {
         repos = conf.newMetaDataRepositoryInstance();
         repos.setSourceMode(1);
      }

      Log log = conf.getLog("openjpa.Tool");
      Object classes;
      if (args != null && args.length != 0) {
         ClassArgParser cap = conf.getMetaDataRepositoryInstance().getMetaDataFactory().newClassArgParser();
         cap.setClassLoader(loader);
         classes = new HashSet();

         for(int i = 0; i < args.length; ++i) {
            ((Collection)classes).addAll(Arrays.asList(cap.parseTypes(args[i])));
         }
      } else {
         log.info(_loc.get("running-all-classes"));
         classes = repos.getPersistentTypeNames(true, loader);
         if (classes == null) {
            log.warn(_loc.get("no-class-to-enhance"));
            return false;
         }
      }

      Project project = new Project();

      for(Iterator itr = ((Collection)classes).iterator(); itr.hasNext(); project.clear()) {
         Object o = itr.next();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("enhance-running", o));
         }

         BCClass bc;
         if (o instanceof String) {
            bc = project.loadClass((String)o, loader);
         } else {
            bc = project.loadClass((Class)o);
         }

         PCEnhancer enhancer = new PCEnhancer(conf, bc, repos, loader);
         if (writer != null) {
            enhancer.setBytecodeWriter(writer);
         }

         enhancer.setDirectory(flags.directory);
         enhancer.setAddDefaultConstructor(flags.addDefaultConstructor);
         int status = enhancer.run();
         if (status == 0) {
            if (log.isTraceEnabled()) {
               log.trace(_loc.get("enhance-norun"));
            }
         } else if (status == 4) {
            if (log.isTraceEnabled()) {
               log.trace(_loc.get("enhance-interface"));
            }
         } else if (status == 2) {
            if (log.isTraceEnabled()) {
               log.trace(_loc.get("enhance-aware"));
            }

            enhancer.record();
         } else {
            enhancer.record();
         }
      }

      return true;
   }

   static {
      Class[] classes = Services.getImplementorClasses(AuxiliaryEnhancer.class, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(AuxiliaryEnhancer.class)));
      List auxEnhancers = new ArrayList(classes.length);

      for(int i = 0; i < classes.length; ++i) {
         try {
            auxEnhancers.add(AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(classes[i])));
         } catch (Throwable var4) {
         }
      }

      _auxEnhancers = (AuxiliaryEnhancer[])((AuxiliaryEnhancer[])auxEnhancers.toArray(new AuxiliaryEnhancer[auxEnhancers.size()]));
   }

   public interface AuxiliaryEnhancer {
      void run(BCClass var1, ClassMetaData var2);

      boolean skipEnhance(BCMethod var1);
   }

   public static class Flags {
      public File directory = null;
      public boolean addDefaultConstructor = true;
      public boolean tmpClassLoader = true;
      public boolean enforcePropertyRestrictions = false;
   }
}
