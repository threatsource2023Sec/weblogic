package weblogic.ejb.container.cmp.rdbms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.persistence.spi.JarDeployment;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;

public final class RDBMSDeployment implements JarDeployment {
   private final Map beans = new HashMap();
   private final Map relations = new HashMap();
   private final Set fileNames = new HashSet();
   private final Set cmpDescriptors = new HashSet();

   public boolean needToReadFile(String fileName) {
      return !this.fileNames.contains(fileName);
   }

   public void addFileName(String fileName) {
      this.fileNames.add(fileName);
   }

   public RDBMSBean getRDBMSBean(String ejbName) {
      assert this.beans.get(ejbName) != null;

      return (RDBMSBean)this.beans.get(ejbName);
   }

   public void addRdbmsBeans(Map beanMap) throws RDBMSException {
      Iterator var2 = beanMap.values().iterator();

      while(var2.hasNext()) {
         RDBMSBean rb = (RDBMSBean)var2.next();
         if (this.beans.containsKey(rb.getEjbName())) {
            String errMsg = EJBLogger.logDuplicateBeanOrRelationLoggable("weblogic-rdbms-bean", rb.getEjbName()).getMessageText();
            Iterator files = this.fileNames.iterator();

            while(files.hasNext()) {
               errMsg = errMsg + (String)files.next();
               if (files.hasNext()) {
                  errMsg = errMsg + ", ";
               }
            }

            errMsg = errMsg + ".";
            throw new RDBMSException(errMsg);
         }

         this.beans.put(rb.getEjbName(), rb);
      }

   }

   public void addRdbmsRelations(Map relationMap) throws RDBMSException {
      Iterator var2 = relationMap.values().iterator();

      while(var2.hasNext()) {
         RDBMSRelation rel = (RDBMSRelation)var2.next();
         if (this.relations.containsKey(rel.getName())) {
            String errMsg = EJBLogger.logDuplicateBeanOrRelationLoggable("weblogic-rdbms-relation", rel.getName()).getMessageText();
            Iterator files = this.fileNames.iterator();

            while(files.hasNext()) {
               errMsg = errMsg + (String)files.next();
               if (files.hasNext()) {
                  errMsg = errMsg + ", ";
               }
            }

            errMsg = errMsg + ".";
            throw new RDBMSException(errMsg);
         }

         this.relations.put(rel.getName(), rel);
      }

   }

   public Map getRDBMSBeanMap() {
      return this.beans;
   }

   public Map getRDBMSRelationMap() {
      return this.relations;
   }

   public void addDescriptorBean(WeblogicRdbmsJarBean cmpDesc) {
      this.cmpDescriptors.add(cmpDesc);
   }
}
