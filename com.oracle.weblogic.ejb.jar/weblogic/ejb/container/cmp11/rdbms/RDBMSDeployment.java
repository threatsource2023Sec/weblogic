package weblogic.ejb.container.cmp11.rdbms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.persistence.spi.JarDeployment;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;

public final class RDBMSDeployment implements JarDeployment {
   private final Set cmpDescriptors = new HashSet();
   private final Set fileNames = new HashSet();
   private final Map m_beans = new HashMap();

   public void addDescriptorMBean(WeblogicRdbmsJarBean cmpDesc) {
      this.cmpDescriptors.add(cmpDesc);
   }

   public WeblogicRdbmsJarBean getDescriptorMBean(String ejbName) {
      Iterator it = this.cmpDescriptors.iterator();

      while(it.hasNext()) {
         WeblogicRdbmsJarBean cmpDesc = (WeblogicRdbmsJarBean)it.next();
         WeblogicRdbmsBeanBean[] beans = cmpDesc.getWeblogicRdbmsBeans();

         for(int i = 0; i < beans.length; ++i) {
            if (beans[i].getEjbName().equals(ejbName)) {
               return cmpDesc;
            }
         }
      }

      return null;
   }

   public WeblogicRdbmsJarBean[] getDescriptorMBeans() {
      return (WeblogicRdbmsJarBean[])this.cmpDescriptors.toArray(new WeblogicRdbmsJarBean[this.cmpDescriptors.size()]);
   }

   public boolean needToReadFile(String fileName) {
      return !this.fileNames.contains(fileName);
   }

   public void addFileName(String fileName) {
      this.fileNames.add(fileName);
   }

   public RDBMSBean getRDBMSBean(String ejbName) {
      assert this.m_beans.get(ejbName) != null;

      return (RDBMSBean)this.m_beans.get(ejbName);
   }

   public void addRdbmsBeans(Map beanMap) throws RDBMSException {
      Iterator iter = beanMap.values().iterator();

      while(iter.hasNext()) {
         RDBMSBean rb = (RDBMSBean)iter.next();
         if (this.m_beans.containsKey(rb.getEjbName())) {
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

         this.m_beans.put(rb.getEjbName(), rb);
      }

   }
}
