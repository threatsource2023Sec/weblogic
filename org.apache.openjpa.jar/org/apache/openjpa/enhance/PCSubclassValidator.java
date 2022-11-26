package org.apache.openjpa.enhance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;

public class PCSubclassValidator {
   private static final Localizer loc = Localizer.forPackage(PCSubclassValidator.class);
   private final ClassMetaData meta;
   private final BCClass pc;
   private final Log log;
   private final boolean failOnContractViolations;
   private Collection errors;
   private Collection contractViolations;

   public PCSubclassValidator(ClassMetaData meta, BCClass bc, Log log, boolean enforceContractViolations) {
      this.meta = meta;
      this.pc = bc;
      this.log = log;
      this.failOnContractViolations = enforceContractViolations;
   }

   public void assertCanSubclass() {
      Class superclass = this.meta.getDescribedType();
      String name = superclass.getName();
      if (superclass.isInterface()) {
         this.addError(loc.get("subclasser-no-ifaces", (Object)name), this.meta);
      }

      if (Modifier.isFinal(superclass.getModifiers())) {
         this.addError(loc.get("subclasser-no-final-classes", (Object)name), this.meta);
      }

      if (Modifier.isPrivate(superclass.getModifiers())) {
         this.addError(loc.get("subclasser-no-private-classes", (Object)name), this.meta);
      }

      if (PersistenceCapable.class.isAssignableFrom(superclass)) {
         this.addError(loc.get("subclasser-super-already-pc", (Object)name), this.meta);
      }

      try {
         Constructor c = superclass.getDeclaredConstructor();
         if (!Modifier.isProtected(c.getModifiers()) && !Modifier.isPublic(c.getModifiers())) {
            this.addError(loc.get("subclasser-private-ctor", (Object)name), this.meta);
         }
      } catch (NoSuchMethodException var4) {
         this.addError(loc.get("subclasser-no-void-ctor", (Object)name), this.meta);
      }

      if (this.pc.isInstanceOf(PersistenceCapable.class) && !PersistenceCapable.class.isAssignableFrom(superclass)) {
         throw new InternalException(loc.get("subclasser-class-already-pc", (Object)name));
      } else {
         if (this.meta.getAccessType() == 4) {
            this.checkPropertiesAreInterceptable();
         }

         if (this.errors != null && !this.errors.isEmpty()) {
            throw new UserException(this.errors.toString());
         } else {
            if (this.contractViolations != null && !this.contractViolations.isEmpty() && this.log.isWarnEnabled()) {
               this.log.warn(this.contractViolations.toString());
            }

         }
      }
   }

   private void checkPropertiesAreInterceptable() {
      FieldMetaData[] fmds = this.meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         Method getter = (Method)fmds[i].getBackingMember();
         if (getter == null) {
            this.addError(loc.get("subclasser-no-getter", (Object)fmds[i].getName()), fmds[i]);
         } else {
            BCField returnedField = this.checkGetterIsSubclassable(getter, fmds[i]);
            Method setter = this.setterForField(fmds[i]);
            if (setter == null) {
               this.addError(loc.get("subclasser-no-setter", (Object)fmds[i].getName()), fmds[i]);
            } else {
               BCField assignedField = this.checkSetterIsSubclassable(setter, fmds[i]);
               if (assignedField != null && assignedField != returnedField) {
                  this.addContractViolation(loc.get("subclasser-setter-getter-field-mismatch", fmds[i].getName(), returnedField, assignedField), fmds[i]);
               }
            }
         }
      }

   }

   private Method setterForField(FieldMetaData fmd) {
      try {
         return fmd.getDeclaringType().getDeclaredMethod("set" + StringUtils.capitalize(fmd.getName()), fmd.getDeclaredType());
      } catch (NoSuchMethodException var3) {
         return null;
      }
   }

   private BCField checkGetterIsSubclassable(Method meth, FieldMetaData fmd) {
      this.checkMethodIsSubclassable(meth, fmd);
      BCField field = PCEnhancer.getReturnedField(this.getBCMethod(meth));
      if (field == null) {
         this.addContractViolation(loc.get("subclasser-invalid-getter", (Object)fmd.getName()), fmd);
         return null;
      } else {
         return field;
      }
   }

   private BCField checkSetterIsSubclassable(Method meth, FieldMetaData fmd) {
      this.checkMethodIsSubclassable(meth, fmd);
      BCField field = PCEnhancer.getAssignedField(this.getBCMethod(meth));
      if (field == null) {
         this.addContractViolation(loc.get("subclasser-invalid-setter", (Object)fmd.getName()), fmd);
         return null;
      } else {
         return field;
      }
   }

   private BCMethod getBCMethod(Method meth) {
      BCClass bc = this.pc.getProject().loadClass(meth.getDeclaringClass());
      return bc.getDeclaredMethod(meth.getName(), meth.getParameterTypes());
   }

   private void checkMethodIsSubclassable(Method meth, FieldMetaData fmd) {
      String className = fmd.getDefiningMetaData().getDescribedType().getName();
      if (!Modifier.isProtected(meth.getModifiers()) && !Modifier.isPublic(meth.getModifiers())) {
         this.addError(loc.get("subclasser-private-accessors-unsupported", className, meth.getName()), fmd);
      }

      if (Modifier.isFinal(meth.getModifiers())) {
         this.addError(loc.get("subclasser-final-methods-not-allowed", className, meth.getName()), fmd);
      }

      if (Modifier.isNative(meth.getModifiers())) {
         this.addContractViolation(loc.get("subclasser-native-methods-not-allowed", className, meth.getName()), fmd);
      }

      if (Modifier.isStatic(meth.getModifiers())) {
         this.addError(loc.get("subclasser-static-methods-not-supported", className, meth.getName()), fmd);
      }

   }

   private void addError(Localizer.Message s, ClassMetaData cls) {
      if (this.errors == null) {
         this.errors = new ArrayList();
      }

      this.errors.add(loc.get("subclasser-error-meta", s, cls.getDescribedType().getName(), cls.getSourceFile()));
   }

   private void addError(Localizer.Message s, FieldMetaData fmd) {
      if (this.errors == null) {
         this.errors = new ArrayList();
      }

      this.errors.add(loc.get("subclasser-error-field", s, fmd.getFullName(), fmd.getDeclaringMetaData().getSourceFile()));
   }

   private void addContractViolation(Localizer.Message m, FieldMetaData fmd) {
      if (this.failOnContractViolations) {
         this.addError(m, fmd);
      }

      if (this.contractViolations == null) {
         this.contractViolations = new ArrayList();
      }

      this.contractViolations.add(loc.get("subclasser-contract-violation-field", m.getMessage(), fmd.getFullName(), fmd.getDeclaringMetaData().getSourceFile()));
   }
}
