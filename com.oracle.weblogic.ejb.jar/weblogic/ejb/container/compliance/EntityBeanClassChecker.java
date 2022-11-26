package weblogic.ejb.container.compliance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EntityBean;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

abstract class EntityBeanClassChecker extends BeanClassChecker {
   protected final Class pkClass;
   protected final boolean isBMP;
   protected final EntityBeanInfo ebi;

   EntityBeanClassChecker(ClientDrivenBeanInfo cbi) {
      super(cbi);
      this.ebi = (EntityBeanInfo)cbi;
      this.pkClass = this.ebi.getPrimaryKeyClass();
      this.isBMP = this.ebi.getIsBeanManagedPersistence();
   }

   protected List getPostCreateMethods() {
      List postCreates = new ArrayList();
      Method[] var2 = this.beanClass.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbPostCreate")) {
            postCreates.add(m);
         }
      }

      return postCreates;
   }

   protected List getFinderMethods() {
      List finders = new ArrayList();
      Method[] var2 = this.beanClass.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbFind")) {
            finders.add(m);
         }
      }

      return finders;
   }

   public void doCheckCreatesMatchPostCreates(String specNumber) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Set postCreateMethods = new HashSet(this.getPostCreateMethods());
      Iterator var4 = this.getCreateMethods().iterator();

      Method m;
      while(var4.hasNext()) {
         m = (Method)var4.next();

         try {
            String postCreateName = "ejbPostC" + m.getName().substring(4);
            Method pcm = this.beanClass.getMethod(postCreateName, m.getParameterTypes());
            postCreateMethods.remove(pcm);
         } catch (NoSuchMethodException var8) {
            errors.add(new ComplianceException(this.getFmt().CREATES_MATCH_POSTCREATE(this.ejbName, this.methodSig(m))));
         }
      }

      var4 = postCreateMethods.iterator();

      while(var4.hasNext()) {
         m = (Method)var4.next();
         errors.add(new ComplianceException(this.getFmt().EXTRA_POSTCREATE(this.ejbName, this.methodSig(m))));
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkEjbCreateMethods() throws ErrorCollectionException {
      List cm = this.getCreateMethods();
      if (cm.size() != 0) {
         this.validateEjbCreates(cm);
      }

   }

   public void doCheckBeanClassImplementsEntityBean(String specNumber) throws ComplianceException {
      if (!EntityBean.class.isAssignableFrom(this.beanClass)) {
         throw new ComplianceException(this.getFmt().MUST_IMPLEMENT_ENTITYBEAN(this.ejbName));
      }
   }

   protected void validateCreateReturnType(Method createMethod, String specNumber) throws ComplianceException {
      if (!createMethod.getReturnType().isAssignableFrom(this.pkClass)) {
         throw new ComplianceException(this.getFmt().EJBCREATE_RETURNS_PK(this.ejbName, this.methodSig(createMethod)));
      }
   }

   public void doCheckPostCreates(String specNumber) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Method[] var3 = this.beanClass.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (m.getName().startsWith("ejbPostCreate")) {
            int mod = m.getModifiers();
            if (!Modifier.isPublic(mod)) {
               errors.add(new ComplianceException(this.getFmt().EJBPOSTCREATE_MUST_BE_PUBLIC(this.ejbName, this.methodSig(m))));
            }

            if (Modifier.isFinal(mod)) {
               errors.add(new ComplianceException(this.getFmt().EJBPOSTCREATE_MUST_NOT_BE_FINAL(this.ejbName, this.methodSig(m))));
            }

            if (Modifier.isStatic(mod)) {
               errors.add(new ComplianceException(this.getFmt().EJBPOSTCREATE_MUST_NOT_BE_STATIC(this.ejbName, this.methodSig(m))));
            }

            if (!Void.TYPE.isAssignableFrom(m.getReturnType())) {
               errors.add(new ComplianceException(this.getFmt().EJBPOSTCREATE_MUST_RETURN_VOID(this.ejbName, this.methodSig(m))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void doCheckCMPBeanDoesntDefineFinders(String specNumber, boolean allowAbstract) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      if (!this.isBMP) {
         Iterator var4 = this.getFinderMethods().iterator();

         while(true) {
            Method fm;
            do {
               if (!var4.hasNext()) {
                  if (!errors.isEmpty()) {
                     throw errors;
                  }

                  return;
               }

               fm = (Method)var4.next();
            } while(allowAbstract && Modifier.isAbstract(fm.getModifiers()));

            errors.add(new ComplianceException(this.getFmt().FINDER_IN_CMP_BEAN(this.ejbName, this.methodSig(fm))));
         }
      }
   }

   public void doCheckCMPBeanHasPersistenceUse() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      if (!this.isBMP) {
         if (this.ebi.getCMPInfo().getPersistenceUseIdentifier() == null) {
            errors.add(new ComplianceException(this.getFmt().BEAN_MISSING_PERSISTENCE_USE(this.ejbName)));
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   public void doCheckPrimkeyFieldIsCMPField(String specNumber) throws ComplianceException {
      if (!this.isBMP) {
         CMPInfo cmpi = this.ebi.getCMPInfo();
         String primkeyField = cmpi.getCMPrimaryKeyFieldName();
         if (primkeyField != null && !cmpi.getAllContainerManagedFieldNames().contains(primkeyField)) {
            throw new ComplianceException(this.getFmt().PRIMKEY_FIELD_MUST_BE_CMP_FIELD(this.ejbName), new DescriptorErrorInfo("<cmp-field>", this.ejbName, primkeyField));
         }
      }

   }

   public void doCheckPrimaryKeyClassFieldsAreCMPFields(String specNumber) throws ComplianceException {
      if (!this.isBMP) {
         CMPInfo cmpi = this.ebi.getCMPInfo();
         if (cmpi.getCMPrimaryKeyFieldName() == null) {
            Collection cmpFields = cmpi.getAllContainerManagedFieldNames();
            Field[] var4 = this.pkClass.getFields();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Field field = var4[var6];
               String name = field.getName();
               if (!name.equals("serialVersionUID") && !cmpFields.contains(name)) {
                  throw new ComplianceException(this.getFmt().PK_FIELDS_MUST_BE_CMP_FIELDS(this.ejbName, field.getName()), new DescriptorErrorInfo("<cmp-field>", this.ejbName, field.getName()));
               }
            }
         }
      }

   }

   public void checkConcurrencyDatabaseAndCacheBetweenTransactionsMatch() {
      if (this.ebi.getConcurrencyStrategy() == 2 && this.ebi.getCacheBetweenTransactions()) {
         Log.getInstance().logWarning(this.getFmt().CACHE_BETWEEN_TRANS_MUST_BE_FALSE_FOR_CONCURRENCY_DB(this.ejbName));
      }

   }

   public void checkInvalidationTarget() throws ComplianceException {
      String targetEJBName = this.ebi.getInvalidationTargetEJBName();
      if (targetEJBName != null) {
         BeanInfo targetBeanInfo = this.ebi.getDeploymentInfo().getBeanInfo(targetEJBName);
         if (targetBeanInfo == null) {
            throw new ComplianceException(this.getFmt().INVALIDATION_TARGET_DOES_NOT_EXIST(this.ejbName, targetEJBName), new DescriptorErrorInfo("<invalidation-target>", this.ejbName, targetEJBName));
         } else if (!(targetBeanInfo instanceof EntityBeanInfo)) {
            throw new ComplianceException(this.getFmt().INVALIDATION_TARGET_MUST_BE_RO_ENTITY(this.ejbName, targetEJBName), new DescriptorErrorInfo("<invalidation-target>", this.ejbName, targetEJBName));
         } else if (((EntityBeanInfo)targetBeanInfo).getConcurrencyStrategy() != 5) {
            throw new ComplianceException(this.getFmt().INVALIDATION_TARGET_MUST_BE_RO_ENTITY(this.ejbName, targetEJBName), new DescriptorErrorInfo("<invalidation-target>", this.ejbName, targetEJBName));
         } else if (this.ebi.getConcurrencyStrategy() == 5) {
            throw new ComplianceException(this.getFmt().INVALIDATION_TARGET_CANNOT_BE_SET_FOR_RO_ENTITY(this.ejbName), new DescriptorErrorInfo("<invalidation-target>", this.ejbName, targetEJBName));
         } else if (this.ebi.getIsBeanManagedPersistence() || !this.ebi.getCMPInfo().uses20CMP()) {
            throw new ComplianceException(this.getFmt().INVALIDATION_TARGET_MUST_BE_SET_ON_CMP20(this.ejbName), new DescriptorErrorInfo("<invalidation-target>", this.ejbName, targetEJBName));
         }
      }
   }
}
