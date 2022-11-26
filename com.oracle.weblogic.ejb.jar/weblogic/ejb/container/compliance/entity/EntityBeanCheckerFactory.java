package weblogic.ejb.container.compliance.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import weblogic.ejb.container.compliance.BaseEJBCheckerFactory;
import weblogic.ejb.container.compliance.ClientJarChecker;
import weblogic.ejb.container.compliance.CmpJarChecker;
import weblogic.ejb.container.compliance.ContainerTransactionChecker;
import weblogic.ejb.container.compliance.EJB11EntityBeanClassChecker;
import weblogic.ejb.container.compliance.EJB20EntityBeanClassChecker;
import weblogic.ejb.container.compliance.EntityHomeInterfaceChecker;
import weblogic.ejb.container.compliance.EntityObjectClassChecker;
import weblogic.ejb.container.compliance.EnvironmentValuesChecker;
import weblogic.ejb.container.compliance.GlobalRelationsChecker;
import weblogic.ejb.container.compliance.PKClassChecker;
import weblogic.ejb.container.compliance.RelationChecker;
import weblogic.ejb.container.compliance.SecurityRoleChecker;
import weblogic.ejb.container.compliance.WeblogicJarChecker;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.Relationships;

public class EntityBeanCheckerFactory extends BaseEJBCheckerFactory {
   public EntityBeanCheckerFactory(DeploymentInfo di, BeanInfo bi) {
      super(di, bi);
   }

   public Object[] getRelationShipCheckers() {
      Set checkers = new HashSet();
      Relationships relationships = this.di.getRelationships();
      if (relationships != null) {
         checkers.add(new GlobalRelationsChecker(this.di));
         Iterator it = relationships.getAllEjbRelations().values().iterator();

         while(it.hasNext()) {
            EjbRelation rel = (EjbRelation)it.next();
            checkers.add(new RelationChecker(rel, this.di));
         }
      }

      return checkers.toArray();
   }

   public Object[] getBeanInfoCheckers() {
      ClientDrivenBeanInfo cbi = (ClientDrivenBeanInfo)this.bi;
      EntityBeanInfo ebi = (EntityBeanInfo)this.bi;
      Collection col = new ArrayList();
      if (cbi.hasRemoteClientView()) {
         col.add(new EntityHomeInterfaceChecker(cbi.getHomeInterfaceClass(), cbi.getRemoteInterfaceClass(), cbi.getBeanClass(), cbi, EJBHome.class));
         col.add(new EntityObjectClassChecker(cbi.getRemoteInterfaceClass(), cbi, EJBObject.class));
      }

      if (cbi.hasLocalClientView()) {
         col.add(new EntityHomeInterfaceChecker(cbi.getLocalHomeInterfaceClass(), cbi.getLocalInterfaceClass(), cbi.getBeanClass(), cbi, EJBLocalHome.class));
         col.add(new EntityObjectClassChecker(cbi.getLocalInterfaceClass(), cbi, EJBLocalObject.class));
      }

      col.add(new PKClassChecker((EntityBeanInfo)this.bi));
      if (!ebi.getIsBeanManagedPersistence() && !ebi.getCMPInfo().uses20CMP()) {
         col.add(new EJB11EntityBeanClassChecker(ebi));
      } else {
         col.add(new EJB20EntityBeanClassChecker(ebi));
      }

      return col.toArray();
   }

   public Class[] getDeploymentInfoCheckerClasses() {
      return new Class[]{CmpJarChecker.class, WeblogicJarChecker.class, ClientJarChecker.class, SecurityRoleChecker.class, EnvironmentValuesChecker.class, ContainerTransactionChecker.class};
   }

   public Object getInterceptorChecker() {
      return null;
   }
}
