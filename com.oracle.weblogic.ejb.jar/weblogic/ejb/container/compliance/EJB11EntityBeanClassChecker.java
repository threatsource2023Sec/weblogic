package weblogic.ejb.container.compliance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

public final class EJB11EntityBeanClassChecker extends EntityBeanClassChecker {
   public EJB11EntityBeanClassChecker(ClientDrivenBeanInfo cbi) {
      super(cbi);
   }

   public void checkCreatesMatchPostCreates() throws ErrorCollectionException {
      super.doCheckCreatesMatchPostCreates("9.1.5.1");
   }

   public void checkBeanClassImplementsEntityBean() throws ComplianceException {
      super.doCheckBeanClassImplementsEntityBean("9.2.2");
   }

   protected void validateCreateReturnType(Method createMethod) throws ComplianceException {
      super.validateCreateReturnType(createMethod, "9.2.3");
   }

   public void checkPostCreates() throws ErrorCollectionException {
      super.doCheckPostCreates("9.2.4");
   }

   public void checkCMPBeanDoesntDefineFinders() throws ErrorCollectionException {
      super.doCheckCMPBeanDoesntDefineFinders("9.4.6", false);
   }

   public void checkBeanClassIsNotAbstract() throws ComplianceException {
      if (Modifier.isAbstract(this.beanClassMod)) {
         throw new ComplianceException(this.getFmt().ABSTRACT_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkIsModifiedMethod() throws ComplianceException {
      String isModifiedMethodName = this.ebi.getIsModifiedMethodName();
      if (isModifiedMethodName != null) {
         Method m = null;

         try {
            m = this.beanClass.getMethod(isModifiedMethodName, (Class[])null);
         } catch (NoSuchMethodException var4) {
            throw new ComplianceException(this.getFmt().ISMODIFIED_NOT_EXIST(this.ejbName, isModifiedMethodName));
         }

         if (!Boolean.TYPE.isAssignableFrom(m.getReturnType())) {
            throw new ComplianceException(this.getFmt().ISMODIFIED_RETURNS_BOOL(this.ejbName, this.methodSig(m)));
         }
      }

   }

   public void checkCMPFields() throws ComplianceException {
      if (!this.isBMP) {
         Class beanClass = this.ebi.getBeanClass();
         CMPInfo cmpi = this.ebi.getCMPInfo();
         Collection fieldNameSet = cmpi.getAllContainerManagedFieldNames();
         Iterator fieldNames = fieldNameSet.iterator();

         String primaryKeyFieldName;
         Field field;
         while(fieldNames.hasNext()) {
            primaryKeyFieldName = (String)fieldNames.next();
            field = null;

            try {
               field = beanClass.getField(primaryKeyFieldName);
            } catch (NoSuchFieldException var9) {
               throw new ComplianceException(this.getFmt().CMP_FIELDS_MUST_BE_BEAN_FIELDS(this.ejbName, primaryKeyFieldName));
            }

            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers)) {
               throw new ComplianceException(this.getFmt().CMP_FIELDS_MUST_BE_PUBLIC(this.ejbName, field.getName()));
            }

            if (Modifier.isStatic(modifiers)) {
               throw new ComplianceException(this.getFmt().CMP_FIELDS_MUST_NOT_BE_STATIC(this.ejbName, field.getName()));
            }
         }

         primaryKeyFieldName = cmpi.getCMPrimaryKeyFieldName();
         if (primaryKeyFieldName == null) {
            this.doCheckPrimaryKeyClassFieldsAreCMPFields("9.4.7.2");
         } else {
            this.doCheckPrimkeyFieldIsCMPField("9.4.7.1");
            field = null;

            try {
               field = beanClass.getField(primaryKeyFieldName);
            } catch (NoSuchFieldException var8) {
               throw new ComplianceException(this.getFmt().PK_FIELD_MUST_EXIST(this.ejbName, primaryKeyFieldName), new DescriptorErrorInfo("<primkey-field>", this.ejbName, primaryKeyFieldName));
            }

            Class pkFieldType = field.getType();
            if (!pkFieldType.getName().equals(this.pkClass.getName())) {
               throw new ComplianceException(this.getFmt().PK_FIELD_WRONG_TYPE(this.ejbName, primaryKeyFieldName, pkFieldType.getName()), new DescriptorErrorInfo("<primkey-field>", this.ejbName, pkFieldType.getName()));
            }
         }
      }

   }

   public void checkCMP11UsingOptimisticConcurrency() throws ComplianceException {
      if (this.ebi.getConcurrencyStrategy() == 6) {
         throw new ComplianceException(this.getFmt().CMP11_CANNOT_USE_OPTIMISTIC_CONCURRENCY(this.ejbName));
      }
   }
}
