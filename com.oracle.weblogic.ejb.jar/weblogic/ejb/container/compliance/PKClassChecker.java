package weblogic.ejb.container.compliance;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb20.dd.DescriptorErrorInfo;

public final class PKClassChecker extends BaseComplianceChecker {
   private static final Class[] WRAPPER_CLASSES = new Class[]{Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class, String.class};
   private final EntityBeanInfo ebi;
   private final Class pkClass;
   private final String ejbName;
   private final boolean isCMP;
   private final boolean isCompoundCMPPK;

   public PKClassChecker(EntityBeanInfo ebi) {
      this.ebi = ebi;
      this.pkClass = ebi.getPrimaryKeyClass();
      this.ejbName = ebi.getEJBName();
      this.isCMP = !ebi.getIsBeanManagedPersistence();
      this.isCompoundCMPPK = this.isCMP && !ebi.isUnknownPrimaryKey() && ebi.getCMPInfo().getCMPrimaryKeyFieldName() == null;
   }

   private boolean isWrapperClass(Class c) {
      Class[] var2 = WRAPPER_CLASSES;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class wc = var2[var4];
         if (c.equals(wc)) {
            return true;
         }
      }

      return false;
   }

   public void checkPrimitivePKHasFieldSet() throws ComplianceException {
      if (this.isCMP && this.isWrapperClass(this.pkClass) && this.ebi.getCMPInfo().getCMPrimaryKeyFieldName() == null) {
         throw new ComplianceException(this.getFmt().PRIMARY_KEY_WITHOUT_PRIMKEY_FIELD(this.pkClass.getName(), this.ejbName), new DescriptorErrorInfo("<primkey-field>", this.ejbName, this.pkClass.getName()));
      }
   }

   public void checkPKImplementsHashCode() throws ComplianceException {
      if (!Object.class.equals(this.pkClass)) {
         try {
            Method hashCode = this.pkClass.getMethod("hashCode", (Class[])null);
            if (Object.class.equals(hashCode.getDeclaringClass())) {
               throw new ComplianceException(this.getFmt().PK_MUST_IMPLEMENT_HASHCODE(this.ejbName));
            }
         } catch (NoSuchMethodException var2) {
            throw new AssertionError("hashCode() not found?!");
         }
      }
   }

   public void checkPKImplementsEquals() throws ComplianceException {
      if (!Object.class.equals(this.pkClass)) {
         try {
            Method m = this.pkClass.getMethod("equals", Object.class);
            if (Object.class.equals(m.getDeclaringClass())) {
               throw new ComplianceException(this.getFmt().PK_MUST_IMPLEMENT_EQUALS(this.ejbName));
            }
         } catch (NoSuchMethodException var2) {
            throw new AssertionError("equals(Object) not found?!");
         }
      }
   }

   public void checkPKImplementsSerializable() throws ComplianceException {
      if (!Object.class.equals(this.pkClass)) {
         if (!Serializable.class.isAssignableFrom(this.pkClass)) {
            throw new ComplianceException(this.getFmt().CMP_PK_MUST_IMPLEMENT_SERIALIZABLE(this.ejbName));
         }
      }
   }

   public void checkPKClassNotObject() throws ComplianceException {
      if (this.isCMP && !this.ebi.getCMPInfo().uses20CMP() && Object.class.equals(this.pkClass)) {
         throw new ComplianceException(this.getFmt().CMP_PK_CANNOT_BE_JAVA_LANG_OBJECT(this.ejbName));
      }
   }

   public void checkCMPPublicPK() throws ComplianceException {
      if (this.isCMP && this.isCompoundCMPPK) {
         int modifiers = this.pkClass.getModifiers();
         if (!Modifier.isPublic(modifiers)) {
            throw new ComplianceException(this.getFmt().CMP_PK_MUST_BE_PUBLIC(this.ejbName, this.pkClass.getName()));
         }
      }

   }

   public void checkCMPPKDefaultNoArgConstructor() throws ComplianceException {
      if (this.isCMP && this.isCompoundCMPPK) {
         try {
            Constructor constructor = this.pkClass.getConstructor();
            if (!Modifier.isPublic(constructor.getModifiers())) {
               throw new ComplianceException(this.getFmt().CMP_PK_MUST_HAVE_NOARG_CONSTRUCTOR(this.ejbName, this.pkClass.getName()));
            }
         } catch (NoSuchMethodException var2) {
            throw new ComplianceException(this.getFmt().CMP_PK_MUST_HAVE_NOARG_CONSTRUCTOR(this.ejbName, this.pkClass.getName()));
         }
      }

   }

   public void checkCMPFieldsModifiers() throws ComplianceException {
      if (this.isCMP && this.isCompoundCMPPK) {
         Collection containerManagedFields = this.ebi.getCMPInfo().getAllContainerManagedFieldNames();
         Field[] pkFields = this.pkClass.getFields();
         if (pkFields == null || pkFields.length == 0) {
            throw new ComplianceException(this.getFmt().PK_FIELD_CLASS_MUST_HAVE_ATLEAST_ONE_CMP_FIELD(this.ejbName, this.pkClass.getName()));
         }

         boolean isFieldDefined = false;
         Field[] var4 = pkFields;
         int var5 = pkFields.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (!field.getName().equals("serialVersionUID") && containerManagedFields.contains(field.getName())) {
               isFieldDefined = true;
               int modifiers = field.getModifiers();
               if (!Modifier.isPublic(modifiers)) {
                  throw new ComplianceException(this.getFmt().PK_FIELDS_MUST_BE_PUBLIC(this.ejbName, this.pkClass.getName(), field.getName()));
               }

               if (Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
                  throw new ComplianceException(this.getFmt().PK_FIELDS_MUST_NOT_BE_STATIC(this.ejbName, this.pkClass.getName(), field.getName()));
               }
            }
         }

         if (!isFieldDefined) {
            if (pkFields.length > 0) {
               throw new ComplianceException(this.getFmt().FIELDS_IN_PK_CLASS_SHOULD_BE_CMP_FIELDS(this.ejbName, this.pkClass.getName()));
            }

            throw new ComplianceException(this.getFmt().PK_FIELD_CLASS_MUST_HAVE_ATLEAST_ONE_CMP_FIELD(this.ejbName, this.pkClass.getName()));
         }
      }

   }
}
