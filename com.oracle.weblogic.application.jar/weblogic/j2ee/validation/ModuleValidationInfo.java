package weblogic.j2ee.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.ejb.spi.EJBValidationInfo;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JMSBean;

public final class ModuleValidationInfo {
   private final String moduleURI;
   private Map ejbs = new HashMap();
   private Collection ejbRefs = new ArrayList();
   private Collection dataSourceRefs = new ArrayList();
   private Map cacheRefs = new HashMap();
   private Collection jmsLinkRefs = new ArrayList();
   private Collection jdbcLinkRefs = new ArrayList();
   private String moduleName = null;
   private JMSBean jmsBean = null;
   private JDBCDataSourceBean jdbcDataSourceBean = null;

   public ModuleValidationInfo(String moduleURI) {
      this.moduleURI = moduleURI;
   }

   public String getURI() {
      return this.moduleURI;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public JMSBean getJMSBean() {
      return this.jmsBean;
   }

   public void setJMSBean(JMSBean jmsBean) {
      this.jmsBean = jmsBean;
   }

   public JDBCDataSourceBean getJDBCDataSourceBean() {
      return this.jdbcDataSourceBean;
   }

   public void setJDBCDataSourceBean(JDBCDataSourceBean jdbcDataSourceBean) {
      this.jdbcDataSourceBean = jdbcDataSourceBean;
   }

   public void addEJBValidationInfo(String ejbName, EJBValidationInfo bi) {
      this.ejbs.put(ejbName, bi);
   }

   public Map getEJBMap() {
      return this.ejbs;
   }

   public void addEJBRef(String ejbName, String ejbRefName, boolean local, String componentInterfaceName, String homeInterfaceName, String refType, String link, boolean isProvisional) {
      this.ejbRefs.add(new EJBRef(ejbName, ejbRefName, local, componentInterfaceName, homeInterfaceName, refType, link, isProvisional));
   }

   public void addEJBRef(String ejbRefName, boolean local, String componentInterfaceName, String homeInterfaceName, String refType, String link, boolean isProvisional) {
      this.addEJBRef((String)null, ejbRefName, local, componentInterfaceName, homeInterfaceName, refType, link, isProvisional);
   }

   public Collection getEJBRefs() {
      return this.ejbRefs;
   }

   public boolean containsEJB(String ejbName) {
      return this.ejbs != null ? this.ejbs.containsKey(ejbName) : false;
   }

   public EJBValidationInfo getEJBValidationInfo(String ejbName) {
      return (EJBValidationInfo)this.ejbs.get(ejbName);
   }

   public void addAppScopedCacheReference(String ejbName, String cacheName) {
      this.cacheRefs.put(cacheName, ejbName);
   }

   public Map getAppScopedCacheReferences() {
      return this.cacheRefs;
   }

   public void addAppScopedDataSourceReference(String dataSourceName) {
      this.dataSourceRefs.add(dataSourceName);
   }

   public void addJMSLinkRefs(String appComponentName, String appComponentType, String resRefName, String resLinkName, String resRefType, boolean isEnv) {
      this.jmsLinkRefs.add(new JLinkRef(appComponentName, appComponentType, resRefName, resLinkName, resRefType, isEnv));
   }

   public JLinkRef findJMSLinkRef(String componentName, String componentType, String refName, boolean isEnv) {
      JLinkRef rtnRef = null;
      synchronized(this.jmsLinkRefs) {
         Iterator refs = this.jmsLinkRefs.iterator();

         while(refs.hasNext()) {
            JLinkRef ref = (JLinkRef)refs.next();
            if (ref.getResRefName().equals(refName) && ref.getAppComponentName().equals(componentName) && ref.getAppComponentType().equals(componentType) && ref.isEnv() == isEnv) {
               rtnRef = ref;
            }
         }

         return rtnRef;
      }
   }

   public Collection getJMSLinkRefs() {
      return this.jmsLinkRefs;
   }

   public void addJDBCLinkRefs(String appComponentName, String appComponentType, String resRefName, String resLinkName, String resRefType, boolean isEnv) {
      this.jdbcLinkRefs.add(new JLinkRef(appComponentName, appComponentType, resRefName, resLinkName, resRefType, isEnv));
   }

   public JLinkRef findJDBCLinkRef(String componentName, String componentType, String refName, boolean isEnv) {
      JLinkRef rtnRef = null;
      synchronized(this.jdbcLinkRefs) {
         Iterator refs = this.jdbcLinkRefs.iterator();

         while(refs.hasNext()) {
            JLinkRef ref = (JLinkRef)refs.next();
            if (ref.getResRefName().equals(refName) && ref.getAppComponentName().equals(componentName) && ref.getAppComponentType().equals(componentType) && ref.isEnv() == isEnv) {
               rtnRef = ref;
            }
         }

         return rtnRef;
      }
   }

   public Collection getJDBCLinkRefs() {
      return this.jdbcLinkRefs;
   }

   public class JLinkRef {
      private String appComponentName;
      private String appComponentType;
      private String resRefName;
      private String resLinkName;
      private String resRefType;
      private boolean isEnv;

      public JLinkRef(String appComponentName, String appComponentType, String resRefName, String resLinkName, String resRefType, boolean isEnv) {
         this.appComponentName = appComponentName;
         this.appComponentType = appComponentType;
         this.resRefName = resRefName;
         this.resLinkName = resLinkName;
         this.resRefType = resRefType;
         this.isEnv = isEnv;
      }

      public String getAppComponentName() {
         return this.appComponentName;
      }

      public String getAppComponentType() {
         return this.appComponentType;
      }

      public String getResRefName() {
         return this.resRefName;
      }

      public String getResLinkName() {
         return this.resLinkName;
      }

      public String getResRefType() {
         return this.resRefType;
      }

      public boolean isEnv() {
         return this.isEnv;
      }
   }

   public class EJBRef {
      private String ejbName;
      private String ejbRefName;
      private boolean local;
      private String componentInterfaceName;
      private String homeInterfaceName;
      private String refType;
      private String link;
      private boolean isProvisional;

      public EJBRef(String ejbName, String ejbRefName, boolean local, String componentInterfaceName, String homeInterfaceName, String refType, String link, boolean isProvisional) {
         this.ejbName = ejbName;
         this.ejbRefName = ejbRefName;
         this.local = local;
         this.componentInterfaceName = componentInterfaceName;
         this.homeInterfaceName = homeInterfaceName;
         this.refType = refType;
         this.link = link;
         this.isProvisional = isProvisional;
      }

      public String getEJBName() {
         return this.ejbName;
      }

      public String getEJBRefName() {
         return this.ejbRefName;
      }

      public String getComponentInterfaceName() {
         return this.componentInterfaceName;
      }

      public String getHomeInterfaceName() {
         return this.homeInterfaceName;
      }

      public String getRefType() {
         return this.refType;
      }

      public boolean isLocal() {
         return this.local;
      }

      public boolean isProvisional() {
         return this.isProvisional;
      }

      public String getEJBLink() {
         return this.link;
      }
   }
}
