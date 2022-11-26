package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.PersistenceUtils;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

public final class RelationChecker extends BaseComplianceChecker {
   private final EjbRelation rel;
   private final EjbRelationshipRole role1;
   private final EjbRelationshipRole role2;
   private final RoleSource src1;
   private final RoleSource src2;
   private final CmrField field1;
   private final CmrField field2;
   private final DeploymentInfo di;

   public RelationChecker(EjbRelation rel, DeploymentInfo di) {
      this.rel = rel;
      this.di = di;
      Iterator iter = rel.getAllEjbRelationshipRoles().iterator();
      this.role1 = (EjbRelationshipRole)iter.next();
      this.role2 = (EjbRelationshipRole)iter.next();
      this.src1 = this.role1.getRoleSource();
      this.src2 = this.role2.getRoleSource();
      this.field1 = this.role1.getCmrField();
      this.field2 = this.role2.getCmrField();
   }

   public void checkRelation() throws ErrorCollectionException {
      this.checkLocalBeanInRoleExists();
      this.checkLocalBeanInRoleIsEntityBean();
      this.checkLocalBeanInRoleNotBM();
      this.checkLocalBeanInRoleUses20CMP();
      this.checkAtLeastUniDirectional();
      this.checkCmrFieldNotACmpField();
      this.checkManyHasType();
      this.check1ManyNoDupFieldForSameBean();
      this.checkCmrFieldExists();
      this.checkEjbCascadeDelete();
   }

   private void check1ManyNoDupFieldForSameBean() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      int ones = 0;
      int manys = 0;
      if (this.role1.getMultiplicity().equalsIgnoreCase("one")) {
         ++ones;
      } else if (this.role1.getMultiplicity().equalsIgnoreCase("many")) {
         ++manys;
      }

      if (this.role2.getMultiplicity().equalsIgnoreCase("one")) {
         ++ones;
      } else if (this.role2.getMultiplicity().equalsIgnoreCase("many")) {
         ++manys;
      }

      if (ones == 1 && manys == 1 && this.src1.getEjbName().compareTo(this.src2.getEjbName()) == 0 && this.field1 != null && this.field2 != null && this.field1.getName().compareTo(this.field2.getName()) == 0) {
         errs.add(new ComplianceException(this.getFmt().N1_RELATION_HAS_DUP_FIELD_FOR_SAME_BEAN(this.rel.getEjbRelationName() + " <cmr-field>: " + this.field1.getName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), this.rel.getEjbRelationName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkLocalBeanInRoleExists() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      if (this.di.getBeanInfo(this.src1.getEjbName()) == null) {
         errs.add(new ComplianceException(this.getFmt().NON_EXISTENT_BEAN_IN_ROLE(this.src1.getEjbName(), this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src1.getEjbName())));
      }

      if (this.di.getBeanInfo(this.src2.getEjbName()) == null) {
         errs.add(new ComplianceException(this.getFmt().NON_EXISTENT_BEAN_IN_ROLE(this.src2.getEjbName(), this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src2.getEjbName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkLocalBeanInRoleIsEntityBean() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      BeanInfo bi = this.di.getBeanInfo(this.src1.getEjbName());
      if (!(bi instanceof EntityBeanInfo)) {
         errs.add(new ComplianceException(this.getFmt().NON_ENTITY_BEAN_IN_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src1.getEjbName())));
      }

      bi = this.di.getBeanInfo(this.src2.getEjbName());
      if (!(bi instanceof EntityBeanInfo)) {
         errs.add(new ComplianceException(this.getFmt().NON_ENTITY_BEAN_IN_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src2.getEjbName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkLocalBeanInRoleNotBM() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      EntityBeanInfo ebi = (EntityBeanInfo)this.di.getBeanInfo(this.src1.getEjbName());
      if (ebi.getIsBeanManagedPersistence()) {
         errs.add(new ComplianceException(this.getFmt().BM_BEAN_IN_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src1.getEjbName())));
      }

      ebi = (EntityBeanInfo)this.di.getBeanInfo(this.src2.getEjbName());
      if (ebi.getIsBeanManagedPersistence()) {
         errs.add(new ComplianceException(this.getFmt().BM_BEAN_IN_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src2.getEjbName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkLocalBeanInRoleUses20CMP() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      EntityBeanInfo ebi = (EntityBeanInfo)this.di.getBeanInfo(this.src1.getEjbName());
      if (!ebi.getCMPInfo().uses20CMP()) {
         errs.add(new ComplianceException(this.getFmt().CMP11_BEAN_IN_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src1.getEjbName())));
      }

      ebi = (EntityBeanInfo)this.di.getBeanInfo(this.src2.getEjbName());
      if (!ebi.getCMPInfo().uses20CMP()) {
         errs.add(new ComplianceException(this.getFmt().CMP11_BEAN_IN_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<relationship-role-source>", this.rel.getEjbRelationName(), this.src2.getEjbName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkAtLeastUniDirectional() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      if (this.field1 == null && this.field2 == null) {
         errs.add(new ComplianceException(this.getFmt().FIELD_NOT_DEFINED_FOR_ROLE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), this.rel.getEjbRelationName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkManyRoleHasType(EjbRelationshipRole role, EjbRelationshipRole otherRole, ErrorCollectionException errs) {
      CmrField field = role.getCmrField();
      if (otherRole.getMultiplicity().equalsIgnoreCase("many") && field != null && field.getType() == null) {
         errs.add(new ComplianceException(this.getFmt().COLLECTION_FIELD_HAS_NO_TYPE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), this.rel.getEjbRelationName())));
      }

   }

   private void checkOneRoleHasNoType(EjbRelationshipRole role, EjbRelationshipRole otherRole, ErrorCollectionException errs) {
      CmrField field = role.getCmrField();
      if (otherRole.getMultiplicity().equalsIgnoreCase("one") && field != null && field.getType() != null) {
         errs.add(new ComplianceException(this.getFmt().SINGLETON_FIELD_HAS_TYPE(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), this.rel.getEjbRelationName())));
      }

   }

   private void checkManyHasType() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      this.checkManyRoleHasType(this.role1, this.role2, errs);
      this.checkManyRoleHasType(this.role2, this.role1, errs);
      this.checkOneRoleHasNoType(this.role1, this.role2, errs);
      this.checkOneRoleHasNoType(this.role2, this.role1, errs);
      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkThatRoleFieldExistsOnBean(CmrField field, RoleSource src, RoleSource otherSrc, ErrorCollectionException errs) {
      if (field != null) {
         EntityBeanInfo ebi = (EntityBeanInfo)this.di.getBeanInfo(src.getEjbName());
         Class beanClass = ebi.getBeanClass();
         String ejbName = ebi.getEJBName();
         String fieldName = field.getName();
         String methodName = MethodUtils.getMethodName(fieldName);
         boolean beanClassIsAbstract = false;
         CMPInfo cmpInfo = ebi.getCMPInfo();
         if (cmpInfo != null) {
            beanClassIsAbstract = !ebi.isEJB30();
         }

         Class fieldClass = null;
         if (field.getType() == null) {
            EntityBeanInfo otherEbi = (EntityBeanInfo)this.di.getBeanInfo(otherSrc.getEjbName());
            if (otherEbi.hasLocalClientView()) {
               fieldClass = otherEbi.getLocalInterfaceClass();
            } else if (otherEbi.hasRemoteClientView()) {
               fieldClass = otherEbi.getRemoteInterfaceClass();
            } else {
               fieldClass = otherEbi.getBeanClass();
            }
         } else if (field.getType().equals("java.util.Collection")) {
            fieldClass = Collection.class;
         } else {
            fieldClass = Set.class;
         }

         Method method = PersistenceUtils.getMethodIncludeSuper(beanClass, methodName, new Class[0]);
         if (method == null) {
            errs.add(new ComplianceException(this.getFmt().GET_METHOD_NOT_DEFINED_FOR_ROLE(ejbName, methodName, this.rel.getEjbRelationName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), methodName)));
         }

         if (method != null && !method.getReturnType().equals(fieldClass)) {
            errs.add(new ComplianceException(this.getFmt().GET_METHOD_HAS_WRONG_RETURN_TYPE(ejbName, methodName, this.rel.getEjbRelationName())));
         }

         int modifier;
         if (method != null) {
            modifier = method.getModifiers();
            if (!Modifier.isAbstract(modifier) && beanClassIsAbstract) {
               errs.add(new ComplianceException(this.getFmt().GET_METHOD_IS_NOT_ABSTRACT(ejbName, methodName, this.rel.getEjbRelationName())));
            }
         }

         if (method != null) {
            modifier = method.getModifiers();
            if (!Modifier.isPublic(modifier)) {
               errs.add(new ComplianceException(this.getFmt().GET_METHOD_IS_NOT_PUBLIC(ejbName, methodName, this.rel.getEjbRelationName())));
            }
         }

         methodName = MethodUtils.setMethodName(fieldName);
         method = PersistenceUtils.getMethodIncludeSuper(beanClass, methodName, new Class[]{fieldClass});
         if (method == null) {
            errs.add(new ComplianceException(this.getFmt().SET_METHOD_NOT_DEFINED_FOR_ROLE(ejbName, methodName, this.rel.getEjbRelationName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), methodName)));
         }

         if (method != null && !method.getReturnType().equals(Void.TYPE)) {
            errs.add(new ComplianceException(this.getFmt().SET_METHOD_HAS_WRONG_RETURN_TYPE(ejbName, methodName, this.rel.getEjbRelationName())));
         }

         if (method != null && !Modifier.isAbstract(method.getModifiers()) && beanClassIsAbstract) {
            errs.add(new ComplianceException(this.getFmt().SET_METHOD_IS_NOT_ABSTRACT(ejbName, methodName, this.rel.getEjbRelationName())));
         }

         if (method != null && !Modifier.isPublic(method.getModifiers())) {
            errs.add(new ComplianceException(this.getFmt().SET_METHOD_IS_NOT_PUBLIC(ejbName, methodName, this.rel.getEjbRelationName())));
         }
      }

   }

   private void checkCmrFieldExists() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      this.checkThatRoleFieldExistsOnBean(this.field1, this.src1, this.src2, errs);
      this.checkThatRoleFieldExistsOnBean(this.field2, this.src2, this.src1, errs);
      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkCmrFieldNotCmp(CmrField field, RoleSource src, ErrorCollectionException errs) {
      if (field != null) {
         EntityBeanInfo ebi = (EntityBeanInfo)this.di.getBeanInfo(src.getEjbName());
         if (ebi.getCMPInfo().getAllContainerManagedFieldNames().contains(field.getName())) {
            errs.add(new ComplianceException(this.getFmt().CMR_FIELD_SAME_AS_CMP_FIELD(this.rel.getEjbRelationName()), new DescriptorErrorInfo("<cmr-field>", this.rel.getEjbRelationName(), this.rel.getEjbRelationName())));
         }
      }

   }

   private void checkCmrFieldNotACmpField() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      this.checkCmrFieldNotCmp(this.field1, this.src1, errs);
      this.checkCmrFieldNotCmp(this.field2, this.src2, errs);
      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkEjbCascadeDelete() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      if (this.role1.getMultiplicity().equalsIgnoreCase("many") && this.role2.getCascadeDelete()) {
         errs.add(new ComplianceException(this.getFmt().CASCADE_DELETE_CANNOT_BE_SPECIFIED(this.role2.getName()), new DescriptorErrorInfo("<ejb-relationship-role>", this.rel.getEjbRelationName(), this.role2.getName())));
      }

      if (this.role2.getMultiplicity().equalsIgnoreCase("many") && this.role1.getCascadeDelete()) {
         errs.add(new ComplianceException(this.getFmt().CASCADE_DELETE_CANNOT_BE_SPECIFIED(this.role1.getName()), new DescriptorErrorInfo("<ejb-relationship-role>", this.rel.getEjbRelationName(), this.role1.getName())));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }
}
