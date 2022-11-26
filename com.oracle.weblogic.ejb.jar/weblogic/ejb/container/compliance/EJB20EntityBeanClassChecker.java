package weblogic.ejb.container.compliance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EntityBean;
import javax.ejb.FinderException;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.persistence.PersistenceUtils;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

public final class EJB20EntityBeanClassChecker extends EntityBeanClassChecker {
   private final Map methodMap;

   public EJB20EntityBeanClassChecker(ClientDrivenBeanInfo cbi) {
      super(cbi);
      this.methodMap = PersistenceUtils.getAccessorMethodMap(this.beanClass);
   }

   private Method[] getEjbSelectMethods() {
      List methods = new ArrayList();
      Method[] var2 = this.beanClass.getDeclaredMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbSelect")) {
            int modifiers = m.getModifiers();
            if (Modifier.isAbstract(modifiers) && (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers))) {
               methods.add(m);
            }
         }
      }

      return (Method[])methods.toArray(new Method[methods.size()]);
   }

   public void checkCreatesMatchPostCreates() throws ErrorCollectionException {
      super.doCheckCreatesMatchPostCreates("9.6.2");
   }

   public void checkBeanClassImplementsEntityBean() throws ComplianceException {
      if (!this.ebi.isEJB30()) {
         super.doCheckBeanClassImplementsEntityBean("9.7.2");
      }
   }

   protected void validateCreateReturnType(Method createMethod) throws ComplianceException {
      super.validateCreateReturnType(createMethod, "9.7.5");
   }

   public void checkPostCreates() throws ErrorCollectionException {
      super.doCheckPostCreates("9.7.6");
   }

   public void checkCMPBeanDoesntDefineFinders() throws ErrorCollectionException {
      super.doCheckCMPBeanDoesntDefineFinders("9.7.2", true);
   }

   public void checkIsModifiedMethod() throws ComplianceException {
      if (this.isBMP) {
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

   }

   public void checkCMPFields() throws ComplianceException {
      if (!this.ebi.isEJB30()) {
         if (!this.isBMP) {
            Class beanClass = this.ebi.getBeanClass();
            CMPInfo cmpi = this.ebi.getCMPInfo();
            Collection fieldNameColl = cmpi.getAllContainerManagedFieldNames();
            Iterator fieldNames = fieldNameColl.iterator();

            while(fieldNames.hasNext()) {
               String fieldName = (String)fieldNames.next();
               if (Character.isUpperCase(fieldName.charAt(0)) || !Character.isLetter(fieldName.charAt(0))) {
                  throw new ComplianceException(this.getFmt().CM_FIELD_MUST_START_WITH_LOWERCASE(this.ejbName, fieldName), new DescriptorErrorInfo("<cmp-field>", this.ejbName, fieldName));
               }

               String getter = RDBMSUtils.getterMethodName(fieldName);
               if (!this.methodMap.containsKey(getter)) {
                  throw new ComplianceException(this.getFmt().DEFINE_CMP_ACCESSOR_METHOD_20(this.ejbName, getter));
               }

               String setter = RDBMSUtils.setterMethodName(fieldName);
               if (!this.methodMap.containsKey(setter)) {
                  throw new ComplianceException(this.getFmt().DEFINE_CMP_ACCESSOR_METHOD_20(this.ejbName, setter));
               }

               Method setMethod = (Method)this.methodMap.get(setter);
               if (!setMethod.getReturnType().getName().equals("void")) {
                  throw new ComplianceException(this.getFmt().SETTER_DOES_NOT_RETURN_VOID(this.ejbName));
               }

               if (!Modifier.isPublic(setMethod.getModifiers())) {
                  throw new ComplianceException(this.getFmt().CMP_ACCESSOR_NOT_PUBLIC(this.ejbName, setter));
               }

               Method getMethod = (Method)this.methodMap.get(getter);
               Class getReturn = getMethod.getReturnType();
               Class[] setParams = setMethod.getParameterTypes();
               if (setParams.length != 1) {
                  throw new ComplianceException(this.getFmt().SETTER_DOES_NOT_HAVE_SINGLE_PARAM(this.ejbName, setter));
               }

               if (!getReturn.equals(setParams[0])) {
                  throw new ComplianceException(this.getFmt().SETTER_PARAM_DOES_NOT_MATCH_GETTER_RETURN(this.ejbName, setter));
               }

               if (!Modifier.isPublic(getMethod.getModifiers())) {
                  throw new ComplianceException(this.getFmt().CMP_ACCESSOR_NOT_PUBLIC(this.ejbName, setter));
               }

               boolean beanHasCMField = true;

               try {
                  beanClass.getField(fieldName);
               } catch (NoSuchFieldException var14) {
                  beanHasCMField = false;
               }

               if (beanHasCMField) {
                  throw new ComplianceException(this.getFmt().DO_NOT_DEFINE_CMFIELD_20(this.ejbName));
               }
            }
         }

      }
   }

   public void checkBeanClassIsNotAbstract() throws ComplianceException {
      if (this.isBMP && Modifier.isAbstract(this.beanClassMod)) {
         throw new ComplianceException(this.getFmt().ABSTRACT_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkEjbSelectReturnType() throws ComplianceException {
      if (!this.isBMP) {
         Method[] var1 = this.getEjbSelectMethods();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method m = var1[var3];
            if (m.getReturnType().getName().equals("java.util.Enumeration")) {
               throw new ComplianceException(this.getFmt().EJB_SELECT_CANNOT_RETURN_ENUMERATION(this.ejbName, m.getName()));
            }
         }
      }

   }

   public void checkEjbSelectThrowsClause() throws ComplianceException {
      if (!this.isBMP) {
         Method[] var1 = this.getEjbSelectMethods();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method m = var1[var3];
            boolean found = false;
            Class[] var6 = m.getExceptionTypes();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Class exception = var6[var8];
               if (exception.equals(FinderException.class)) {
                  found = true;
               }
            }

            if (!found) {
               throw new ComplianceException(this.getFmt().EJB_SELECT_MUST_THROW(this.ejbName, m.getName()));
            }
         }
      }

   }

   public void checkPrimaryKeyFieldClass() throws ComplianceException {
      if (!this.isBMP) {
         CMPInfo cmpi = this.ebi.getCMPInfo();
         String PKFieldName = cmpi.getCMPrimaryKeyFieldName();
         if (PKFieldName != null && PKFieldName.length() > 0) {
            Class PKClass = this.ebi.getPrimaryKeyClass();
            String PKClassString = PKClass.getName();
            String getterName = RDBMSUtils.getterMethodName(PKFieldName);
            Method getMethod = (Method)this.methodMap.get(getterName);
            if (getMethod != null) {
               Class retType = getMethod.getReturnType();
               if (retType != null && !retType.equals(PKClass)) {
                  throw new ComplianceException(this.getFmt().PRIMKEY_CLASS_DOES_NOT_MATCH_ACCESSOR_FOR_GETTER(this.ejbName, PKFieldName, PKClassString), new DescriptorErrorInfo("<prim-key-class>", this.ejbName, PKClassString));
               }
            }

            String setterName = RDBMSUtils.setterMethodName(PKFieldName);
            Method setMethod = (Method)this.methodMap.get(setterName);
            if (setMethod != null) {
               Class[] parmTypes = setMethod.getParameterTypes();
               if (parmTypes != null && parmTypes.length > 0 && parmTypes[0] != null && !parmTypes[0].equals(PKClass)) {
                  throw new ComplianceException(this.getFmt().PRIMKEY_CLASS_DOES_NOT_MATCH_ACCESSOR_FOR_SETTER(this.ejbName, PKFieldName, PKClassString), new DescriptorErrorInfo("<prim-key-class>", this.ejbName, PKClassString));
               }
            }
         }
      }

   }

   public void checkBeanAndPKClassDeclareSamePKFieldType() throws ErrorCollectionException {
      if (!this.isBMP) {
         ErrorCollectionException errors = new ErrorCollectionException();
         boolean isCompoundCMPPK = this.ebi.getCMPInfo().getCMPrimaryKeyFieldName() == null;
         if (isCompoundCMPPK) {
            Field[] var3 = this.ebi.getPrimaryKeyClass().getFields();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Field field = var3[var5];
               if (!field.getName().equals("serialVersionUID")) {
                  String getterName = RDBMSUtils.getterMethodName(field.getName());
                  Method getMethod = (Method)this.methodMap.get(getterName);
                  if (getMethod != null) {
                     Class retType = getMethod.getReturnType();
                     if (retType != null && !retType.equals(field.getType())) {
                        errors.add(new ComplianceException(this.getFmt().BEAN_PK_CLASS_DOES_NOT_MATCH_PKFIELD_FOR_GETTER(this.ejbName, field.getName(), retType.getName(), field.getType().getName())));
                     }
                  }

                  String setterName = RDBMSUtils.setterMethodName(field.getName());
                  Method setMethod = (Method)this.methodMap.get(setterName);
                  if (setMethod != null) {
                     Class[] parmTypes = setMethod.getParameterTypes();
                     if (parmTypes != null && parmTypes[0] != null && !parmTypes[0].equals(field.getType())) {
                        errors.add(new ComplianceException(this.getFmt().BEAN_PK_CLASS_DOES_NOT_MATCH_PKFIELD_FOR_SETTER(this.ejbName, field.getName(), parmTypes[0].getName(), field.getType().getName())));
                     }
                  }
               }
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   public void checkPrimaryKeyFieldIsCMPField() throws ComplianceException {
      super.doCheckPrimkeyFieldIsCMPField("9.10.1.1");
   }

   public void checkPrimaryKeyClassFieldsAreCMPFields() throws ComplianceException {
      super.doCheckPrimaryKeyClassFieldsAreCMPFields("9.10.1.2");
   }

   public void checkAbstractBeanClassImplementsEntityBean() throws ErrorCollectionException {
      if (!this.ebi.isEJB30()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Method[] var2 = EntityBean.class.getMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];

            try {
               Method bm = this.beanClass.getMethod(m.getName(), m.getParameterTypes());
               if (Modifier.isAbstract(bm.getModifiers())) {
                  errors.add(new ComplianceException(this.getFmt().MISSING_ENTITY_BEAN_METHOD(this.ejbName, this.methodSig(m))));
               }
            } catch (NoSuchMethodException var7) {
               errors.add(new ComplianceException(this.getFmt().MISSING_ENTITY_BEAN_METHOD(this.ejbName, this.methodSig(m))));
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   public void checkBMPUsingOptimisticConcurrency() throws ComplianceException {
      if (this.isBMP && this.ebi.getConcurrencyStrategy() == 6) {
         throw new ComplianceException(this.getFmt().BMP_CANNOT_USE_OPTIMISTIC_CONCURRENCY(this.ejbName));
      }
   }

   public void checkOptimisticReadTimeoutSecondsNoCacheBetweenTransactions() {
      if (this.ebi.getConcurrencyStrategy() == 6 && this.ebi.getCachingDescriptor().getReadTimeoutSeconds() > 0 && !this.ebi.getCacheBetweenTransactions()) {
         Log.getInstance().logWarning(this.getFmt().OptimisticWithReadTimeoutSecondsNoCacheBetweenTx(this.ejbName));
      }

   }
}
