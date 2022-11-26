package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.PersistenceUtils;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.utils.ErrorCollectionException;

public class EntityObjectClassChecker extends EJBObjectClassChecker {
   private final CMPInfo cmpInfo;
   private final boolean uses20CMP;
   private final Relationships relationships;

   public EntityObjectClassChecker(Class eoClass, ClientDrivenBeanInfo bi, Class ejbObjectInterface) {
      super(eoClass, bi, ejbObjectInterface);
      this.cmpInfo = ((EntityBeanInfo)bi).getCMPInfo();
      if (this.cmpInfo != null && this.cmpInfo.uses20CMP()) {
         this.uses20CMP = true;
         this.relationships = this.cmpInfo.getRelationships();
         if (this.checkingRemoteClientView()) {
            this.remoteInterfaceClass = bi.getRemoteInterfaceClass();
         }
      } else {
         this.uses20CMP = false;
         this.relationships = null;
      }

   }

   public void checkRelationExposedThroughRemote() throws ErrorCollectionException {
      if (this.checkingRemoteClientView()) {
         if (this.uses20CMP) {
            if (this.relationships != null) {
               Map allEjbRelations = this.relationships.getAllEjbRelations();
               if (!allEjbRelations.isEmpty()) {
                  Map cmrAccessorMap = new HashMap();
                  Iterator i = allEjbRelations.entrySet().iterator();

                  String type;
                  while(i.hasNext()) {
                     EjbRelation er = (EjbRelation)((Map.Entry)i.next()).getValue();
                     Iterator j = er.getAllEjbRelationshipRoles().iterator();

                     while(j.hasNext()) {
                        EjbRelationshipRole rr = (EjbRelationshipRole)j.next();
                        RoleSource rs = rr.getRoleSource();
                        if (rs.getEjbName().equals(this.ejbName)) {
                           CmrField cmrf = rr.getCmrField();
                           if (cmrf != null) {
                              String cmrName = cmrf.getName();
                              type = cmrf.getType();
                              cmrAccessorMap.put(RDBMSUtils.getterMethodName(cmrName), type);
                              cmrAccessorMap.put(RDBMSUtils.setterMethodName(cmrName), type);
                           }
                        }
                     }
                  }

                  ErrorCollectionException errors = new ErrorCollectionException();
                  Map methodMapAbstract = PersistenceUtils.getAccessorMethodMap(this.beanClass);
                  Map methodMapRemoteEO = PersistenceUtils.getAccessorMethodMap(this.remoteInterfaceClass);
                  Iterator i = methodMapRemoteEO.entrySet().iterator();

                  while(i.hasNext()) {
                     Map.Entry entry = (Map.Entry)i.next();
                     String methodName = (String)entry.getKey();
                     if (cmrAccessorMap.get(methodName) != null) {
                        Method abstractBeanClassMethod = (Method)methodMapAbstract.get(methodName);
                        type = DDUtils.getMethodSignature(abstractBeanClassMethod);
                        Method remoteMethod = (Method)entry.getValue();
                        String remoteMSig = DDUtils.getMethodSignature(remoteMethod);
                        if (remoteMSig.equals(type)) {
                           errors.add(new ComplianceException(this.getFmt().CANNOT_EXPOSE_RELATIONSHIP_ACCESSOR_IN_REMOTE(this.ejbName, this.remoteInterfaceClass.getName(), methodName)));
                        }
                     }
                  }

                  if (!errors.isEmpty()) {
                     throw errors;
                  }
               }
            }
         }
      }
   }
}
