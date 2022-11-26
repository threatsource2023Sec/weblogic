package weblogic.ejb.container.cmp.rdbms.compliance;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.cmp.rdbms.FieldGroup;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSRelation;
import weblogic.ejb.container.compliance.BaseComplianceChecker;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.j2ee.validation.ComplianceException;
import weblogic.utils.ErrorCollectionException;

public final class RDBMSRelationChecker extends BaseComplianceChecker {
   private final Map beanMap;
   private final Map relationMap;
   private final Map rdbmsBeanMap;
   private final Map rdbmsRelationMap;
   private EjbRelation testEjbRel = null;
   private RDBMSRelation testRDBMSRel = null;
   private final ErrorCollectionException errors = new ErrorCollectionException();
   private static final short REL_IS_UNKNOWN = 0;
   private static final short REL_IS_1_1 = 1;
   private static final short REL_IS_1_N = 2;
   private static final short REL_IS_M_N = 3;

   RDBMSRelationChecker(Map beanM, Map relationMap, Map rdbmsBeanM, Map relM) {
      this.beanMap = beanM;
      this.relationMap = relationMap;
      this.rdbmsBeanMap = rdbmsBeanM;
      this.rdbmsRelationMap = relM;
   }

   public void runComplianceCheck() throws ErrorCollectionException {
      this.runWLDrivenCheck();
      if (!this.errors.isEmpty()) {
         throw this.errors;
      } else {
         this.runEJBDrivenCheck();
         if (!this.errors.isEmpty()) {
            throw this.errors;
         }
      }
   }

   private void runWLDrivenCheck() {
      Iterator wlIter = this.rdbmsRelationMap.values().iterator();

      while(wlIter.hasNext()) {
         this.testRDBMSRel = (RDBMSRelation)wlIter.next();

         try {
            this.checkWLRelHasEjbRel();
            this.checkWLRelRolesHaveEJBRelRoles();
            if (!this.errors.isEmpty()) {
               return;
            }

            this.checkWLMNHasBothRoles();
            this.checkWLMNRelHasTableName();
            this.checkWLRolesHaveValidGroup();
            this.checkWLRolesHaveValidMap();
            this.checkWLDBCascadeDelete();
         } catch (ErrorCollectionException var3) {
            this.errors.add(var3);
         }
      }

   }

   private void runEJBDrivenCheck() {
      Iterator ejbIter = this.relationMap.values().iterator();

      while(ejbIter.hasNext()) {
         this.testEjbRel = (EjbRelation)ejbIter.next();

         try {
            this.checkEjbRelHasWLRel();
            this.checkEjb1NRoleHasWLRole();
            this.checkEjbNo1MappingOn1N();
            this.checkEjb1To1RelHasWLRoleMap();
            this.checkRelatedBeansSameDataSource();
         } catch (ErrorCollectionException var3) {
            this.errors.add(var3);
         }
      }

   }

   private void checkEjb1NRoleHasWLRole() throws ErrorCollectionException {
      if (this.getRelType(this.testEjbRel) == 2) {
         ErrorCollectionException errs = new ErrorCollectionException();
         EjbRelationshipRole eRole = this.getManyEjbRole(this.testEjbRel);
         if (eRole == null) {
            errs.add(new ComplianceException(this.getFmt().roleNotFound(this.testEjbRel.getEjbRelationName())));
            throw errs;
         } else {
            String eRoleName = eRole.getName();
            RDBMSRelation.RDBMSRole rRole = this.getRDBMSRoleForEjbRole(this.testEjbRel, eRole);
            if (rRole == null) {
               errs.add(new ComplianceException(this.getFmt().NO_MATCHING_WL_RELATIONSHIP_ROLE(this.testEjbRel.getEjbRelationName(), eRoleName), new DescriptorErrorInfo("<weblogic-relationship-role>", this.testEjbRel.getEjbRelationName(), eRoleName)));
               throw errs;
            } else {
               Map colMap = rRole.getColumnMap();
               if (colMap == null) {
                  errs.add(new ComplianceException(this.getFmt().MANY_SIDE_OF_M_1_MUST_HAVE_FOREIGN_KEY_MAP(rRole.getName()), new DescriptorErrorInfo("<column-map>", this.testEjbRel.getEjbRelationName(), rRole.getName())));
               } else if (colMap.size() < 1) {
                  errs.add(new ComplianceException(this.getFmt().MANY_SIDE_OF_M_1_MUST_HAVE_FOREIGN_KEY_MAP(rRole.getName()), new DescriptorErrorInfo("<column-map>", this.testEjbRel.getEjbRelationName(), rRole.getName())));
               }

               if (!errs.isEmpty()) {
                  throw errs;
               }
            }
         }
      }
   }

   private void checkEjbNo1MappingOn1N() throws ErrorCollectionException {
      if (this.getRelType(this.testEjbRel) == 2) {
         ErrorCollectionException errs = new ErrorCollectionException();
         EjbRelationshipRole eRole = this.getOneEjbRole(this.testEjbRel);
         EjbRelationshipRole manyRole = this.getManyEjbRole(this.testEjbRel);
         if (eRole == null) {
            throw new AssertionError(this.testEjbRel.getEjbRelationName() + ":  checker could not find the EjbRelationShip ONE ROLE in a 1-N Relationship");
         } else if (manyRole == null) {
            throw new AssertionError(this.testEjbRel.getEjbRelationName() + ":  checker could not find the EjbRelationShip MANY ROLE in a 1-N Relationship");
         } else {
            RDBMSRelation.RDBMSRole rRole = this.getRDBMSRoleForEjbRole(this.testEjbRel, eRole);
            if (rRole != null) {
               Map colMap = rRole.getColumnMap();
               if (colMap != null && colMap.size() > 0) {
                  errs.add(new ComplianceException(this.getFmt().ONE_SIDE_OF_M_1_MUST_NOT_HAVE_FOREIGN_KEY_MAP(rRole.getName()), new DescriptorErrorInfo("<column-map>", this.testEjbRel.getEjbRelationName(), rRole.getName())));
               }

               if (!errs.isEmpty()) {
                  throw errs;
               }
            }
         }
      }
   }

   private void checkEjb1To1RelHasWLRoleMap() {
      if (this.getRelType(this.testEjbRel) == 1) {
         Iterator eRoles = this.testEjbRel.getAllEjbRelationshipRoles().iterator();

         while(eRoles.hasNext()) {
            RDBMSRelation.RDBMSRole rRole = this.getRDBMSRoleForEjbRole(this.testEjbRel, (EjbRelationshipRole)eRoles.next());
            if (rRole != null) {
               Map roleColumnMap = rRole.getColumnMap();
               if (roleColumnMap != null && roleColumnMap.size() > 0) {
                  return;
               }
            }
         }

         this.errors.add(new ComplianceException(this.getFmt().missingRelationshipRoleMap(this.testEjbRel.getEjbRelationName())));
      }
   }

   private void checkEjbRelHasWLRel() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      String relName = this.testEjbRel.getEjbRelationName();
      if (!this.rdbmsRelationMap.containsKey(relName)) {
         errs.add(new ComplianceException(this.getFmt().NO_MATCHING_WL_RELATION(relName), new DescriptorErrorInfo("<weblogic-rdbms-relation>", this.testEjbRel.getEjbRelationName(), relName)));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkWLRelHasEjbRel() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      String relName = this.testRDBMSRel.getName();
      if (!this.relationMap.containsKey(relName)) {
         errs.add(new ComplianceException(this.getFmt().NO_MATCHING_EJB_RELATION_IN_EJB_DD(relName)));
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkWLRelRolesHaveEJBRelRoles() {
      String relName = this.testRDBMSRel.getName();
      EjbRelation ejbr = (EjbRelation)this.relationMap.get(relName);
      RDBMSRelation.RDBMSRole rRole = this.testRDBMSRel.getRole1();
      if (rRole != null) {
         this.doWLRoleCheck(rRole, ejbr);
      }

      rRole = this.testRDBMSRel.getRole2();
      if (rRole != null) {
         this.doWLRoleCheck(rRole, ejbr);
      }

   }

   private void checkWLMNHasBothRoles() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      String relName = this.testRDBMSRel.getName();
      EjbRelation ejbr = (EjbRelation)this.relationMap.get(relName);
      if (this.getRelType(ejbr) == 3) {
         RDBMSRelation.RDBMSRole rRole = this.testRDBMSRel.getRole1();
         if (rRole == null) {
            errs.add(new ComplianceException(this.getFmt().MANY_TO_MANY_RELATIONSHIP_MUST_HAVE_BOTH_WL_ROLES(relName), new DescriptorErrorInfo("<weblogic-relationship-role>", ejbr.getEjbRelationName(), relName)));
            throw errs;
         }

         rRole = this.testRDBMSRel.getRole2();
         if (rRole == null) {
            errs.add(new ComplianceException(this.getFmt().MANY_TO_MANY_RELATIONSHIP_MUST_HAVE_BOTH_WL_ROLES(relName), new DescriptorErrorInfo("<weblogic-relationship-role>", ejbr.getEjbRelationName(), relName)));
            throw errs;
         }
      }

   }

   private void checkWLMNRelHasTableName() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      String relName = this.testRDBMSRel.getName();
      EjbRelation ejbr = (EjbRelation)this.relationMap.get(relName);
      if (this.getRelType(ejbr) == 3) {
         String tableName = this.testRDBMSRel.getTableName();
         if (tableName == null) {
            errs.add(new ComplianceException(this.getFmt().MANY_TO_MANY_RELATIONSHIP_MISSING_TABLE_NAME(relName), new DescriptorErrorInfo("<table-name>", ejbr.getEjbRelationName(), relName)));
         }
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void checkWLRolesHaveValidGroup() {
      String relName = this.testRDBMSRel.getName();
      EjbRelation ejbRelation = (EjbRelation)this.relationMap.get(relName);
      Iterator ejbRoles = ejbRelation.getAllEjbRelationshipRoles().iterator();

      while(ejbRoles.hasNext()) {
         EjbRelationshipRole ejbRole = (EjbRelationshipRole)ejbRoles.next();
         RDBMSRelation.RDBMSRole rdbmsRole = this.getRDBMSRoleForEjbRole(ejbRelation, ejbRole);
         if (rdbmsRole != null) {
            String groupName = rdbmsRole.getGroupName();
            if (groupName != null) {
               RoleSource roleSource = ejbRole.getRoleSource();
               FieldGroup group = null;
               RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(roleSource.getEjbName());
               group = rb.getFieldGroup(groupName);
               if (group == null) {
                  this.errors.add(new ComplianceException(this.getFmt().RELATIONSHIP_ROLE_HAS_INVALID_GROUP(relName, groupName), new DescriptorErrorInfo("<weblogic-relationship-role>", ejbRelation.getEjbRelationName(), relName)));
               }
            }
         }
      }

   }

   private void checkWLRolesHaveValidMap() throws ErrorCollectionException {
      String relName = this.testRDBMSRel.getName();
      EjbRelation ejbRelation = (EjbRelation)this.relationMap.get(relName);
      Iterator ejbRoles = ejbRelation.getAllEjbRelationshipRoles().iterator();
      EjbRelationshipRole role1 = (EjbRelationshipRole)ejbRoles.next();
      EjbRelationshipRole role2 = (EjbRelationshipRole)ejbRoles.next();
      RDBMSRelation.RDBMSRole rdbmsRole1 = this.getRDBMSRoleForEjbRole(ejbRelation, role1);
      RDBMSRelation.RDBMSRole rdbmsRole2 = this.getRDBMSRoleForEjbRole(ejbRelation, role2);
      this.checkWLRoleHasValidTableName(ejbRelation, role1, role2, rdbmsRole1);
      this.checkWLRoleHasValidTableName(ejbRelation, role2, role1, rdbmsRole2);
      this.checkWLRolesHaveMap(ejbRelation, role1, role2, rdbmsRole1, rdbmsRole2);
      if (this.errors.isEmpty()) {
         this.checkWLRoleHasValidMap(ejbRelation, role1, role2, rdbmsRole1);
         this.checkWLRoleHasValidMap(ejbRelation, role2, role1, rdbmsRole2);
         this.checkWLRoleHasValidFkColumns(ejbRelation, role1, rdbmsRole1, role2);
         this.checkWLRoleHasValidFkColumns(ejbRelation, role2, rdbmsRole2, role1);
         this.checkForeignKeyTableSpecified(ejbRelation, role1, rdbmsRole1);
         this.checkForeignKeyTableSpecified(ejbRelation, role2, rdbmsRole2);
      }
   }

   private void checkWLDBCascadeDelete() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      String relName = this.testRDBMSRel.getName();
      EjbRelation ejbRelation = (EjbRelation)this.relationMap.get(relName);
      int relType = this.getRelType(ejbRelation);
      Iterator ejbRoles = ejbRelation.getAllEjbRelationshipRoles().iterator();
      if (relType != 3) {
         while(ejbRoles.hasNext()) {
            EjbRelationshipRole ejbRole = (EjbRelationshipRole)ejbRoles.next();
            RDBMSRelation.RDBMSRole rdbmsRole = this.getRDBMSRoleForEjbRole(ejbRelation, ejbRole);
            if (rdbmsRole != null) {
               if (rdbmsRole.getDBCascadeDelete() && !ejbRole.getCascadeDelete()) {
                  errs.add(new ComplianceException(this.getFmt().CASCADE_DELETE_MUST_SPECIFIED_IF_DB_CASCADE_DELETE_SPECIFIED(relName), new DescriptorErrorInfo("<cascade-delete>", ejbRelation.getEjbRelationName(), relName)));
               }

               if (rdbmsRole.getDBCascadeDelete()) {
                  Map colMap = rdbmsRole.getColumnMap();
                  if (colMap == null) {
                     errs.add(new ComplianceException(this.getFmt().CASCADE_DELETE_MUST_HAVE_FOREIGN_KEY_MAP(rdbmsRole.getName()), new DescriptorErrorInfo("<foreign-key-column>", rdbmsRole.getName(), rdbmsRole.getName())));
                  }

                  if (colMap != null && colMap.size() < 1) {
                     errs.add(new ComplianceException(this.getFmt().CASCADE_DELETE_MUST_HAVE_FOREIGN_KEY_MAP(rdbmsRole.getName()), new DescriptorErrorInfo("<foreign-key-column>", rdbmsRole.getName(), rdbmsRole.getName())));
                  }
               }
            }
         }

         if (!errs.isEmpty()) {
            throw errs;
         }
      }
   }

   private void checkWLRolesHaveMap(EjbRelation ejbRelation, EjbRelationshipRole role1, EjbRelationshipRole role2, RDBMSRelation.RDBMSRole rdbmsRole1, RDBMSRelation.RDBMSRole rdbmsRole2) {
      String relName = ejbRelation.getEjbRelationName();
      short relType = this.getRelType(ejbRelation);
      int mapCount;
      if (relType == 1) {
         mapCount = 0;
         if (rdbmsRole1 != null && rdbmsRole1.getColumnMap().size() > 0) {
            ++mapCount;
         }

         if (rdbmsRole2 != null && rdbmsRole2.getColumnMap().size() > 0) {
            ++mapCount;
         }

         if (mapCount == 0) {
            this.errors.add(new ComplianceException(this.getFmt().ONE_ONE_MISSING_COLUMN_MAP(relName)));
         }
      } else if (relType == 3) {
         mapCount = 0;
         if (rdbmsRole1 != null && rdbmsRole1.getColumnMap().size() > 0) {
            ++mapCount;
         }

         if (rdbmsRole2 != null && rdbmsRole2.getColumnMap().size() > 0) {
            ++mapCount;
         }

         if (mapCount < 2) {
            this.errors.add(new ComplianceException(this.getFmt().MANY_MANY_MISSING_COLUMN_MAP(relName)));
         }
      }

   }

   private void checkWLRoleHasValidTableName(EjbRelation ejbRelation, EjbRelationshipRole role, EjbRelationshipRole otherRole, RDBMSRelation.RDBMSRole rdbmsRole) throws ErrorCollectionException {
      if (rdbmsRole != null) {
         boolean foundError = false;
         short relType = this.getRelType(ejbRelation);
         String relName = ejbRelation.getEjbRelationName();
         String relRoleName = role.getName();
         RoleSource src = role.getRoleSource();
         String srcEjbName = src.getEjbName();
         RDBMSBean srcRDBMSBean = (RDBMSBean)this.rdbmsBeanMap.get(srcEjbName);
         RoleSource otherSrc = otherRole.getRoleSource();
         String otherSrcEjbName = otherSrc.getEjbName();
         RDBMSBean otherSrcRDBMSBean = (RDBMSBean)this.rdbmsBeanMap.get(otherSrcEjbName);
         String pkTableName = null;
         String fkTableName = null;
         pkTableName = rdbmsRole.getPrimaryKeyTableName();
         fkTableName = rdbmsRole.getForeignKeyTableName();
         RDBMSBean manyRoleRDBMSBean;
         EjbRelationshipRole manyRole;
         String manyRoleName;
         if (pkTableName != null) {
            if (relType == 1) {
               if (!srcRDBMSBean.hasTable(pkTableName) && !otherSrcRDBMSBean.hasTable(pkTableName)) {
                  this.errors.add(new ComplianceException(this.getFmt().ROLE_11_SPECIFIES_INVALID_PK_TABLE_NAME(relName, relRoleName, pkTableName, srcEjbName, otherSrcEjbName), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
                  foundError = true;
               }
            } else if (relType == 2) {
               manyRoleRDBMSBean = null;
               manyRole = this.getOneEjbRole(ejbRelation);
               manyRoleName = manyRole.getName();
               if (relRoleName.equals(manyRoleName)) {
                  manyRoleRDBMSBean = srcRDBMSBean;
               } else {
                  manyRoleRDBMSBean = otherSrcRDBMSBean;
               }

               if (!manyRoleRDBMSBean.hasTable(pkTableName)) {
                  this.errors.add(new ComplianceException(this.getFmt().ROLE_1N_SPECIFIES_INVALID_PK_TABLE_NAME(relName, manyRoleName, pkTableName, manyRoleRDBMSBean.getEjbName()), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
                  foundError = true;
               }
            } else if (relType == 3 && !srcRDBMSBean.hasTable(pkTableName)) {
               this.errors.add(new ComplianceException(this.getFmt().ROLE_MN_SPECIFIES_INVALID_PK_TABLE_NAME(relName, relRoleName, pkTableName, srcEjbName), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
               foundError = true;
            }
         }

         if (fkTableName != null) {
            if (relType == 1) {
               if (!srcRDBMSBean.hasTable(fkTableName) && !otherSrcRDBMSBean.hasTable(fkTableName)) {
                  this.errors.add(new ComplianceException(this.getFmt().ROLE_11_SPECIFIES_INVALID_FK_TABLE_NAME(relName, relRoleName, fkTableName, srcEjbName, otherSrcEjbName), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
                  foundError = true;
               }
            } else if (relType == 2) {
               manyRoleRDBMSBean = null;
               manyRole = this.getManyEjbRole(ejbRelation);
               manyRoleName = manyRole.getName();
               if (relRoleName.equals(manyRoleName)) {
                  manyRoleRDBMSBean = srcRDBMSBean;
               } else {
                  manyRoleRDBMSBean = otherSrcRDBMSBean;
               }

               if (!manyRoleRDBMSBean.hasTable(fkTableName)) {
                  this.errors.add(new ComplianceException(this.getFmt().ROLE_1N_SPECIFIES_INVALID_FK_TABLE_NAME(relName, manyRoleName, fkTableName, manyRoleRDBMSBean.getEjbName()), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
                  foundError = true;
               }
            } else if (relType == 3) {
               this.errors.add(new ComplianceException(this.getFmt().ROLE_MN_SPECIFIES_INVALID_FK_TABLE_NAME(relName, relRoleName, fkTableName), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
               foundError = true;
            }
         }

         if (foundError) {
            throw this.errors;
         } else {
            boolean found11Error = false;
            if (relType == 1 && fkTableName != null && pkTableName != null) {
               found11Error = true;
               if (srcRDBMSBean.hasTable(fkTableName)) {
                  if (otherSrcRDBMSBean.hasTable(pkTableName)) {
                     found11Error = false;
                  }
               } else if (otherSrcRDBMSBean.hasTable(fkTableName) && srcRDBMSBean.hasTable(pkTableName)) {
                  found11Error = false;
               }
            }

            if (found11Error) {
               this.errors.add(new ComplianceException(this.getFmt().ROLE_11_SPECIFIES_FK_AND_PK_TABLE_NAMES_NOT_IN_DIFFERENT_ROLES(relName, relRoleName, pkTableName, fkTableName), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, relRoleName)));
               throw this.errors;
            }
         }
      }
   }

   private void checkWLRoleHasValidMap(EjbRelation ejbRelation, EjbRelationshipRole role, EjbRelationshipRole otherRole, RDBMSRelation.RDBMSRole rdbmsRole) {
      String relName = ejbRelation.getEjbRelationName();
      short relType = this.getRelType(ejbRelation);
      RoleSource src = role.getRoleSource();
      RoleSource otherSrc = otherRole.getRoleSource();
      Set pkColumns = null;
      String pkTableName = null;
      Map roleColumnMap = null;
      if (rdbmsRole != null) {
         roleColumnMap = rdbmsRole.getColumnMap();
         pkTableName = rdbmsRole.getPrimaryKeyTableName();
      }

      if (relType == 3) {
         pkColumns = this.getPrimaryKeyColumns(src.getEjbName(), pkTableName);
      } else if ((relType == 2 || relType == 1) && rdbmsRole != null && rdbmsRole.getColumnMap().size() > 0) {
         pkColumns = this.getPrimaryKeyColumns(otherSrc.getEjbName(), pkTableName);
      }

      if (pkColumns != null) {
         if (roleColumnMap == null) {
            this.errors.add(new ComplianceException(this.getFmt().ROLE_HAS_NO_COLUMN_MAP(relName, role.getName()), new DescriptorErrorInfo("<column-map>", relName, role.getName())));
            return;
         }

         if (pkColumns.size() != roleColumnMap.size()) {
            this.errors.add(new ComplianceException(this.getFmt().ROLE_HAS_WRONG_NUMBER_OF_COLUMNS_IN_MAP(relName, role.getName()), new DescriptorErrorInfo("<weblogic-relationship-role>", ejbRelation.getEjbRelationName(), role.getName())));
         } else {
            Iterator keyColumns = roleColumnMap.values().iterator();

            while(keyColumns.hasNext()) {
               String keyColumn = (String)keyColumns.next();
               if (!pkColumns.contains(keyColumn)) {
                  this.errors.add(new ComplianceException(this.getFmt().ROLE_HAS_INVALID_KEY_COLUMN_IN_MAP2(relName, role.getName(), keyColumn, otherSrc.getEjbName()), new DescriptorErrorInfo("<column-map>", relName, role.getName())));
               }
            }
         }
      }

   }

   private void checkWLRoleHasValidFkColumns(EjbRelation ejbRelation, EjbRelationshipRole role, RDBMSRelation.RDBMSRole rdbmsRole, EjbRelationshipRole otherRole) {
      String relName = ejbRelation.getEjbRelationName();
      short relType = this.getRelType(ejbRelation);
      RoleSource src = role.getRoleSource();
      if ((relType == 2 || relType == 1) && rdbmsRole != null && rdbmsRole.getColumnMap().size() > 0) {
         String fkTableName = null;
         RDBMSBean bean = (RDBMSBean)this.rdbmsBeanMap.get(src.getEjbName());
         Set pkColumns;
         Set cmpColumns;
         if (bean.hasMultipleTables()) {
            fkTableName = rdbmsRole.getForeignKeyTableName();
            if (fkTableName == null) {
               this.errors.add(new ComplianceException(this.getFmt().MISSING_FK_TABLE_NAME_FOR_MULTITABLE_BEAN(relName, role.getName(), src.getEjbName()), new DescriptorErrorInfo("<foreign-key-column>", relName, role.getName())));
               return;
            }

            pkColumns = this.getPrimaryKeyColumns(src.getEjbName(), fkTableName);
            cmpColumns = this.getCmpColumns(src.getEjbName(), fkTableName);
         } else {
            pkColumns = this.getPrimaryKeyColumns(src.getEjbName());
            cmpColumns = this.getCmpColumns(src.getEjbName());
         }

         Iterator fkColumns = rdbmsRole.getColumnMap().keySet().iterator();
         int pkCount = 0;
         int cmpCount = 0;

         while(true) {
            RDBMSBean otherBean;
            String fieldName;
            Class fieldClass;
            String otherField;
            Class otherClass;
            do {
               do {
                  CMPBeanDescriptor otherDescriptor;
                  do {
                     String pkTableName;
                     String pkColumn;
                     do {
                        String fkColumn;
                        do {
                           if (!fkColumns.hasNext()) {
                              if (pkCount != 0 && cmpCount != 0) {
                                 this.errors.add(new ComplianceException(this.getFmt().FKCOLUMNS_MIX_OF_PK_NONPK_COLUMNS(relName, role.getName()), new DescriptorErrorInfo("<foreign-key-column>", relName, role.getName())));
                              }

                              return;
                           }

                           fkColumn = (String)fkColumns.next();
                           if (pkColumns.contains(fkColumn)) {
                              ++pkCount;
                           }

                           if (!pkColumns.contains(fkColumn) && cmpColumns.contains(fkColumn)) {
                              ++cmpCount;
                           }
                        } while(!cmpColumns.contains(fkColumn));

                        otherBean = (RDBMSBean)this.rdbmsBeanMap.get(otherRole.getRoleSource().getEjbName());
                        otherDescriptor = (CMPBeanDescriptor)this.beanMap.get(otherBean.getEjbName());
                        CMPBeanDescriptor descriptor = (CMPBeanDescriptor)this.beanMap.get(bean.getEjbName());
                        if (fkTableName == null) {
                           fkTableName = bean.getTableName();
                        }

                        fieldName = bean.getCmpField(fkTableName, fkColumn);
                        fieldClass = descriptor.getFieldClass(fieldName);
                        pkTableName = rdbmsRole.getPrimaryKeyTableName();
                        if (pkTableName == null) {
                           pkTableName = otherBean.getTableName();
                        }

                        pkColumn = (String)rdbmsRole.getColumnMap().get(fkColumn);
                     } while(pkColumn == null);

                     otherField = otherBean.getCmpField(pkTableName, pkColumn);
                  } while(otherField == null);

                  otherClass = otherDescriptor.getFieldClass(otherField);
               } while(otherClass.equals(fieldClass));

               if (otherClass.isPrimitive()) {
                  otherClass = ClassUtils.getObjectClass(otherClass);
               }

               if (fieldClass.isPrimitive()) {
                  fieldClass = ClassUtils.getObjectClass(fieldClass);
               }
            } while(fieldClass.isAssignableFrom(otherClass) && otherClass.isAssignableFrom(fieldClass));

            this.errors.add(new ComplianceException(this.getFmt().INVALID_FOREIGN_KEY_FIELD_CLASS(bean.getEjbName(), fieldName, otherBean.getEjbName(), otherField), new DescriptorErrorInfo("<foreign-key-column>", relName, role.getName())));
         }
      }
   }

   private void checkForeignKeyTableSpecified(EjbRelation ejbRelation, EjbRelationshipRole role, RDBMSRelation.RDBMSRole rdbmsRole) {
      String relName = ejbRelation.getEjbRelationName();
      short relType = this.getRelType(ejbRelation);
      if ((relType == 2 || relType == 1) && rdbmsRole != null && rdbmsRole.getColumnMap().size() > 0) {
         RoleSource src = role.getRoleSource();
         RDBMSBean bean = (RDBMSBean)this.rdbmsBeanMap.get(src.getEjbName());
         String fkTableName = null;
         if (bean.hasMultipleTables()) {
            fkTableName = rdbmsRole.getForeignKeyTableName();
            if (fkTableName == null) {
               this.errors.add(new ComplianceException(this.getFmt().MISSING_FK_TABLE_NAME_FOR_MULTITABLE_BEAN(relName, role.getName(), src.getEjbName()), new DescriptorErrorInfo("<foreign-key-column>", relName, role.getName())));
               return;
            }
         }
      }

   }

   private void checkRelatedBeansSameDataSource() {
      String relName = this.testEjbRel.getEjbRelationName();
      EjbRelation ejbr = (EjbRelation)this.relationMap.get(relName);
      Iterator ejbRoles = ejbr.getAllEjbRelationshipRoles().iterator();
      EjbRelationshipRole role1 = (EjbRelationshipRole)ejbRoles.next();
      if (ejbRoles.hasNext()) {
         EjbRelationshipRole role2 = (EjbRelationshipRole)ejbRoles.next();
         RoleSource src1 = role1.getRoleSource();
         RoleSource src2 = role2.getRoleSource();
         String srcEjbName1 = src1.getEjbName();
         String srcEjbName2 = src2.getEjbName();
         RDBMSBean srcRDBMSBean1 = (RDBMSBean)this.rdbmsBeanMap.get(srcEjbName1);
         RDBMSBean srcRDBMSBean2 = (RDBMSBean)this.rdbmsBeanMap.get(srcEjbName2);
         String dataSourceName1 = srcRDBMSBean1.getDataSourceName();
         String dataSourceName2 = srcRDBMSBean2.getDataSourceName();
         if (dataSourceName1 != null && dataSourceName2 != null) {
            if (!dataSourceName1.equals(dataSourceName2)) {
               this.errors.add(new ComplianceException(this.getFmt().RELATED_BEANS_MUST_SHARE_DATASOURCE(relName, role1.getName(), srcEjbName1, dataSourceName1, role2.getName(), srcEjbName2, dataSourceName2), new DescriptorErrorInfo("<weblogic-relationship-role>", relName, role1.getName())));
            }
         }
      }
   }

   private Set getPrimaryKeyColumns(String ejbName) {
      CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(ejbName);
      RDBMSBean bean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Set pkColumns = new HashSet();
      Iterator pkFields = bd.getPrimaryKeyFieldNames().iterator();

      while(pkFields.hasNext()) {
         String pkField = (String)pkFields.next();
         String pkColumn = bean.getCmpColumnForField(pkField);
         pkColumns.add(pkColumn);
      }

      return pkColumns;
   }

   private Set getPrimaryKeyColumns(String ejbName, String tableName) {
      if (tableName == null) {
         return this.getPrimaryKeyColumns(ejbName);
      } else {
         CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(ejbName);
         RDBMSBean bean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
         Set pkColumns = new HashSet();
         Iterator pkFields = bd.getPrimaryKeyFieldNames().iterator();

         while(pkFields.hasNext()) {
            String pkField = (String)pkFields.next();
            String pkColumn = bean.getColumnForCmpFieldAndTable(pkField, tableName);
            if (pkColumn != null) {
               pkColumns.add(pkColumn);
            }
         }

         return pkColumns;
      }
   }

   private Set getCmpColumns(String ejbName) {
      return new HashSet(((RDBMSBean)this.rdbmsBeanMap.get(ejbName)).getCmpFieldToColumnMap().values());
   }

   private Set getCmpColumns(String ejbName, String tableName) {
      if (tableName == null) {
         return this.getCmpColumns(ejbName);
      } else {
         Iterator it = ((RDBMSBean)this.rdbmsBeanMap.get(ejbName)).getCmpField2ColumnMap(tableName).values().iterator();
         Set s = new HashSet();

         while(it.hasNext()) {
            s.add(it.next());
         }

         return s;
      }
   }

   private void doWLRoleCheck(RDBMSRelation.RDBMSRole rRole, EjbRelation ejbr) {
      EjbRelationshipRole eRole = ejbr.getEjbRelationshipRole(rRole.getName());
      if (eRole == null) {
         this.errors.add(new ComplianceException(this.getFmt().NO_MATCHING_EJB_RELATIONSHIP_ROLE(ejbr.getEjbRelationName(), rRole.getName()), new DescriptorErrorInfo("<ejb-relationship-role>", ejbr.getEjbRelationName(), rRole.getName())));
      }

   }

   private EjbRelationshipRole getManyEjbRole(EjbRelation eRel) {
      Iterator eRoles = eRel.getAllEjbRelationshipRoles().iterator();

      EjbRelationshipRole eRole;
      do {
         if (!eRoles.hasNext()) {
            return null;
         }

         eRole = (EjbRelationshipRole)eRoles.next();
      } while(!eRole.getMultiplicity().equalsIgnoreCase("many"));

      return eRole;
   }

   private EjbRelationshipRole getOneEjbRole(EjbRelation eRel) {
      Iterator eRoles = eRel.getAllEjbRelationshipRoles().iterator();

      EjbRelationshipRole eRole;
      do {
         if (!eRoles.hasNext()) {
            return null;
         }

         eRole = (EjbRelationshipRole)eRoles.next();
      } while(!eRole.getMultiplicity().equalsIgnoreCase("one"));

      return eRole;
   }

   private RDBMSRelation.RDBMSRole getRDBMSRoleForEjbRole(EjbRelation relation, EjbRelationshipRole role) {
      RDBMSRelation rRel = (RDBMSRelation)this.rdbmsRelationMap.get(relation.getEjbRelationName());
      if (rRel == null) {
         return null;
      } else {
         String roleName = role.getName();
         RDBMSRelation.RDBMSRole rRole = rRel.getRole1();
         if (rRole != null && rRole.getName().compareTo(roleName) == 0) {
            return rRole;
         } else {
            rRole = rRel.getRole2();
            return rRole != null && rRole.getName().compareTo(roleName) == 0 ? rRole : null;
         }
      }
   }

   private short getRelType(EjbRelation eRel) {
      Collection eRoleC = eRel.getAllEjbRelationshipRoles();
      if (eRoleC.size() == 2) {
         short ones = 0;
         short manys = 0;
         Iterator eRoles = eRoleC.iterator();

         while(eRoles.hasNext()) {
            EjbRelationshipRole eRole = (EjbRelationshipRole)eRoles.next();
            if (eRole.getMultiplicity().equalsIgnoreCase("one")) {
               ++ones;
            } else if (eRole.getMultiplicity().equalsIgnoreCase("many")) {
               ++manys;
            }
         }

         if (ones == 2) {
            return 1;
         }

         if (manys == 2) {
            return 3;
         }

         if (ones == 1 && manys == 1) {
            return 2;
         }
      }

      return 0;
   }
}
