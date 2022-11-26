package weblogic.ejb.container.cmp.rdbms.compliance;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.finders.RDBMSFinder;
import weblogic.ejb.container.compliance.BaseComplianceChecker;
import weblogic.ejb.container.compliance.EJBComplianceChecker;
import weblogic.ejb.container.interfaces.EntityBeanQuery;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.validation.ComplianceException;
import weblogic.utils.ErrorCollectionException;

public final class RDBMSComplianceChecker extends BaseComplianceChecker {
   private final Map beanMap;
   private final Relationships relationships;
   private final Map dependentMap;
   private final Map rdbmsBeanMap;
   private final Map rdbmsRelationMap;
   private final WeblogicRdbmsJarBean cmpDesc;
   private final ErrorCollectionException errors = new ErrorCollectionException();

   public RDBMSComplianceChecker(Map beanM, Relationships r, Map dependentM, Map rdbmsBeanM, Map rdbmsRelationM, WeblogicRdbmsJarBean cmpDesc) {
      this.beanMap = beanM;
      this.relationships = r;
      this.dependentMap = dependentM;
      this.rdbmsBeanMap = rdbmsBeanM;
      this.rdbmsRelationMap = rdbmsRelationM;
      this.cmpDesc = cmpDesc;
   }

   public void checkCompliance() throws ErrorCollectionException {
      if (!EJBComplianceChecker.isNeedCheck) {
         if (this.dependentMap != null) {
            this.errors.add(new ComplianceException(this.getFmt().DEPENDENT_OBJECTS_NOT_SUPPORTED()));
         }

         if (!this.errors.isEmpty()) {
            throw this.errors;
         } else {
            this.checkConnectedSubGraph();
            if (!this.errors.isEmpty()) {
               throw this.errors;
            } else {
               Map ejbBeanMap = new HashMap();
               Map ejbRelationMap = new HashMap();
               this.calculateEjbMaps(ejbBeanMap, ejbRelationMap);
               if (!this.errors.isEmpty()) {
                  throw this.errors;
               } else {
                  RDBMSBeanChecker beanChecker = new RDBMSBeanChecker(ejbBeanMap, ejbRelationMap, this.rdbmsBeanMap, this.rdbmsRelationMap, this.cmpDesc);
                  RDBMSRelationChecker relChecker = new RDBMSRelationChecker(ejbBeanMap, ejbRelationMap, this.rdbmsBeanMap, this.rdbmsRelationMap);

                  try {
                     beanChecker.runComplianceCheck();
                  } catch (ErrorCollectionException var7) {
                     this.errors.addCollection(var7);
                  }

                  try {
                     relChecker.runComplianceCheck();
                  } catch (ErrorCollectionException var6) {
                     this.errors.addCollection(var6);
                  }

                  if (!this.errors.isEmpty()) {
                     throw this.errors;
                  }
               }
            }
         }
      }
   }

   private void calculateEjbMaps(Map ejbBeanMap, Map ejbRelationMap) {
      Iterator var3 = this.rdbmsBeanMap.keySet().iterator();

      String relName;
      while(var3.hasNext()) {
         relName = (String)var3.next();
         CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(relName);
         if (bd == null) {
            this.errors.add(new ComplianceException(this.getFmt().NO_MATCHING_CMP_BEAN(relName), new DescriptorErrorInfo("<entity>", relName, relName)));
         } else {
            ejbBeanMap.put(relName, bd);
            this.checkFinderQuery(bd, (RDBMSBean)this.rdbmsBeanMap.get(relName));
         }
      }

      var3 = this.rdbmsRelationMap.keySet().iterator();

      while(var3.hasNext()) {
         relName = (String)var3.next();
         EjbRelation ejbRel = this.relationships.getEjbRelation(relName);
         if (ejbRel == null) {
            this.errors.add(new ComplianceException(this.getFmt().NO_MATCHING_EJB_RELATION_IN_EJB_DD(relName), new DescriptorErrorInfo("<ejb-relation>", relName, relName)));
         } else {
            ejbRelationMap.put(relName, ejbRel);
         }
      }

   }

   private void checkFinderQuery(CMPBeanDescriptor bd, RDBMSBean rdbmsBean) {
      Collection queries = bd.getAllQueries();
      if (queries != null) {
         Iterator queryIter = queries.iterator();

         while(true) {
            EntityBeanQuery query;
            RDBMSFinder rf;
            do {
               do {
                  do {
                     if (!queryIter.hasNext()) {
                        return;
                     }

                     query = (EntityBeanQuery)queryIter.next();
                  } while(query.getQueryText() != null && query.getQueryText().trim().length() > 0);

                  rf = (RDBMSFinder)rdbmsBean.getRdbmsFinders().get(new RDBMSFinder.FinderKey(query.getMethodName(), query.getMethodParams()));
                  if (rf == null) {
                     this.errors.add(new ComplianceException(this.getFmt().EJBQLCANNOTBENULL(bd.getEJBName(), query.getMethodName())));
                     return;
                  }
               } while(rf.getSqlQueries() != null);
            } while(rf.getEjbQlQuery() != null && rf.getEjbQlQuery().trim().length() != 0);

            this.errors.add(new ComplianceException(this.getFmt().EJBANDWLQLCANNOTBENULL(bd.getEJBName(), query.getMethodName())));
         }
      }
   }

   private void checkConnectedSubGraph() {
      if (this.relationships != null) {
         Set beanSet = new HashSet(this.rdbmsBeanMap.keySet());
         Set relationSet = new HashSet(this.rdbmsRelationMap.keySet());
         Iterator relations = this.relationships.getAllEjbRelations().values().iterator();

         while(relations.hasNext()) {
            EjbRelation ejbRel = (EjbRelation)relations.next();
            Iterator roles = ejbRel.getAllEjbRelationshipRoles().iterator();
            RoleSource src1 = ((EjbRelationshipRole)roles.next()).getRoleSource();
            RoleSource src2 = ((EjbRelationshipRole)roles.next()).getRoleSource();
            this.checkConnection(beanSet, relationSet, ejbRel, src1, src2);
            this.checkConnection(beanSet, relationSet, ejbRel, src2, src1);
         }
      }

   }

   private void checkConnection(Set beanSet, Set relationSet, EjbRelation ejbRel, RoleSource src, RoleSource otherSrc) {
      if (beanSet.contains(src.getEjbName())) {
         if (!relationSet.contains(ejbRel.getEjbRelationName())) {
            this.errors.add(new ComplianceException(this.getFmt().MISSING_RELATION_FOR_BEAN(src.getEjbName(), ejbRel.getEjbRelationName()), new DescriptorErrorInfo("<weblogic-rdbms-relation>", ejbRel.getEjbRelationName(), src.getEjbName())));
         }

         if (!beanSet.contains(otherSrc.getEjbName())) {
            this.errors.add(new ComplianceException(this.getFmt().MISSING_BEAN_FOR_BEAN(src.getEjbName(), otherSrc.getEjbName()), new DescriptorErrorInfo("<weblogic-rdbms-bean>", ejbRel.getEjbRelationName(), otherSrc.getEjbName())));
         }
      }

   }
}
