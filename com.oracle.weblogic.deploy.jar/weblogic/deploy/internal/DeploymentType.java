package weblogic.deploy.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.CacheMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CustomResourceMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.StartupClassMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.deploy.internal.DeploymentManagerLogger;

public class DeploymentType implements Comparator {
   private final String name;
   private final Class cls;
   private final boolean isDefaultParallelPrepare;
   private final boolean isDefaultParallelActivate;
   private final boolean usesDeploymentOrderPrepare;
   private final boolean usesDeploymentOrderActivate;
   public static final DeploymentType CUSTOM_SYS_RES = new DeploymentType("Custom", CustomResourceMBean.class);
   public static final DeploymentType JDBC_SYS_RES = new DeploymentType("JDBC", JDBCSystemResourceMBean.class) {
      public int compare(Object o1, Object o2) {
         this.ensureType(o1, o2);
         JDBCSystemResourceMBean jsr1 = (JDBCSystemResourceMBean)o1;
         JDBCSystemResourceMBean jsr2 = (JDBCSystemResourceMBean)o2;
         boolean ismds1 = this.isMultiDS(jsr1);
         boolean ismds2 = this.isMultiDS(jsr2);
         boolean issharedpool1 = this.isSharedPoolDS(jsr1);
         boolean issharedpool2 = this.isSharedPoolDS(jsr2);
         if (!ismds1 && ismds2) {
            return -1;
         } else if (ismds1 && !ismds2) {
            return 1;
         } else if (issharedpool1 && !issharedpool2) {
            return -1;
         } else {
            return !issharedpool1 && issharedpool2 ? 1 : this.compare(jsr1, jsr2);
         }
      }

      private boolean isMultiDS(JDBCSystemResourceMBean mbean) {
         try {
            return JDBCUtil.getLegacyType(mbean.getJDBCResource()) == 0 && mbean.getJDBCResource().getJDBCDataSourceParams().getDataSourceList() != null;
         } catch (NullPointerException var3) {
            return false;
         }
      }

      private boolean isSharedPoolDS(JDBCSystemResourceMBean mbean) {
         try {
            return JDBCUtil.getLegacyType(mbean.getJDBCResource()) == 0 && JDBCUtil.isSharedPool(mbean.getJDBCResource());
         } catch (NullPointerException var3) {
            return false;
         }
      }
   };
   public static final DeploymentType JMS_SYS_RES = new DeploymentType("JMS", JMSSystemResourceMBean.class);
   public static final DeploymentType WLDF_SYS_RES = new DeploymentType("WLDF", WLDFSystemResourceMBean.class);
   public static final DeploymentType COHERENCE_CLUSTER_SYS_RES = new DeploymentType("CoherenceCluster", CoherenceClusterSystemResourceMBean.class);
   public static final DeploymentType LIBRARY = new DeploymentType("Library", LibraryMBean.class, true, true, true, true);
   public static final DeploymentType INTERNAL_APP = new DeploymentType("Internal", AppDeploymentMBean.class, true, true, false, false) {
      public boolean isInstance(Object obj) {
         return super.isInstance(obj) && ((AppDeploymentMBean)obj).isInternalApp();
      }
   };
   public static final DeploymentType DEFAULT_APP = new DeploymentType("Default", AppDeploymentMBean.class, true, true, true, true) {
      public boolean isInstance(Object obj) {
         return super.isInstance(obj) && !((AppDeploymentMBean)obj).isInternalApp() && !(obj instanceof LibraryMBean);
      }
   };
   public static final Object PSEUDO_DEPLOYMENT_HANDLER_MBEAN = new String("PseudoDeploymentHandlerMBean");
   public static final DeploymentType DEPLOYMENT_HANDLER = new DeploymentType("DeploymentHandler", DeploymentMBean.class) {
      public boolean isInstance(Object obj) {
         return obj == PSEUDO_DEPLOYMENT_HANDLER_MBEAN ? true : super.isInstance(obj);
      }

      public int compare(Object o1, Object o2) {
         this.ensureType(o1, o2);
         DeploymentMBean dep1 = (DeploymentMBean)o1;
         DeploymentMBean dep2 = (DeploymentMBean)o2;
         int order1 = dep1.getDeploymentOrder();
         int order2 = dep2.getDeploymentOrder();
         if (order1 < order2) {
            return -1;
         } else {
            return order2 < order1 ? 1 : this.defaultCompare(dep1, dep2);
         }
      }
   };
   public static final Object PSEUDO_RESOURCE_DEPENDENT_DEP_HANDLER_MBEAN = new String("PseudoResourceDependentDeploymentHandlerMBean");
   public static final DeploymentType RESOURCE_DEPENDENT_DEPLOYMENT_HANDLER = new DeploymentType("ResourceDependentDeploymentHandler", DeploymentMBean.class) {
      public boolean isInstance(Object obj) {
         return obj == PSEUDO_RESOURCE_DEPENDENT_DEP_HANDLER_MBEAN;
      }
   };
   public static final Object PSEUDO_STARTUP_CLASS_MBEAN = new String("PseudoStartupClassMBean");
   public static final DeploymentType STARTUP_CLASS = new DeploymentType("StartupClass", StartupClassMBean.class, false, false, true, true) {
      public boolean isInstance(Object obj) {
         return obj == PSEUDO_STARTUP_CLASS_MBEAN ? true : super.isInstance(obj);
      }

      public int compare(Object o1, Object o2) {
         return DEPLOYMENT_HANDLER.compare(o1, o2);
      }
   };
   public static final DeploymentType CACHE = new DeploymentType("Cache", CacheMBean.class);
   private static final List ALL_TYPES = new ArrayList();

   public static DeploymentType findType(BasicDeploymentMBean mBean) {
      Iterator var1 = ALL_TYPES.iterator();

      DeploymentType type;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         type = (DeploymentType)var1.next();
      } while(!type.isInstance(mBean));

      return type;
   }

   private DeploymentType(String name, Class cls) {
      this(name, cls, false, false, true, true);
   }

   private DeploymentType(String name, Class cls, boolean isDefaultParallelPrepare, boolean isDefaultParallelActivate, boolean usesDeploymentOrderPrepare, boolean usesDeploymentOrderActivate) {
      this.name = name;
      this.cls = cls;
      this.isDefaultParallelPrepare = isDefaultParallelPrepare;
      this.isDefaultParallelActivate = isDefaultParallelActivate;
      this.usesDeploymentOrderPrepare = usesDeploymentOrderPrepare;
      this.usesDeploymentOrderActivate = usesDeploymentOrderActivate;
   }

   public boolean isDefaultParallelPrepare() {
      return this.isDefaultParallelPrepare;
   }

   public boolean isDefaultParallelActivate() {
      return this.isDefaultParallelActivate;
   }

   public boolean usesDeploymentOrderPrepare() {
      return this.usesDeploymentOrderPrepare;
   }

   public boolean usesDeploymentOrderActivate() {
      return this.usesDeploymentOrderActivate;
   }

   public String toString() {
      return this.name;
   }

   public boolean isInstance(Object obj) {
      return this.cls.isInstance(obj);
   }

   public Comparator getComparator() {
      return this;
   }

   public int compare(Object o1, Object o2) {
      this.ensureType(o1, o2);
      return o1 instanceof BasicDeploymentMBean ? this.compare((BasicDeploymentMBean)o1, (BasicDeploymentMBean)o2) : this.defaultCompare(o1, o2);
   }

   protected void ensureType(Object o1, Object o2) {
      String msg;
      if (!this.isInstance(o1)) {
         msg = DeploymentManagerLogger.unrecognizedType(o1.getClass().getName());
         throw new ClassCastException(msg);
      } else if (!this.isInstance(o2)) {
         msg = DeploymentManagerLogger.unrecognizedType(o2.getClass().getName());
         throw new ClassCastException(msg);
      }
   }

   protected int defaultCompare(Object o1, Object o2) {
      return o1 instanceof WebLogicMBean && o2 instanceof WebLogicMBean ? ((WebLogicMBean)o1).getName().compareTo(((WebLogicMBean)o2).getName()) : 0;
   }

   protected int compare(BasicDeploymentMBean d1, BasicDeploymentMBean d2) {
      int order1 = DeploymentOrder.getCachedDeploymentOrder(d1);
      int order2 = DeploymentOrder.getCachedDeploymentOrder(d2);
      if (order1 < order2) {
         return -1;
      } else {
         return order2 < order1 ? 1 : this.defaultCompare(d1, d2);
      }
   }

   // $FF: synthetic method
   DeploymentType(String x0, Class x1, Object x2) {
      this(x0, x1);
   }

   // $FF: synthetic method
   DeploymentType(String x0, Class x1, boolean x2, boolean x3, boolean x4, boolean x5, Object x6) {
      this(x0, x1, x2, x3, x4, x5);
   }

   static {
      ALL_TYPES.add(CACHE);
      ALL_TYPES.add(COHERENCE_CLUSTER_SYS_RES);
      ALL_TYPES.add(CUSTOM_SYS_RES);
      ALL_TYPES.add(DEFAULT_APP);
      ALL_TYPES.add(DEPLOYMENT_HANDLER);
      ALL_TYPES.add(INTERNAL_APP);
      ALL_TYPES.add(JDBC_SYS_RES);
      ALL_TYPES.add(JMS_SYS_RES);
      ALL_TYPES.add(LIBRARY);
      ALL_TYPES.add(RESOURCE_DEPENDENT_DEPLOYMENT_HANDLER);
      ALL_TYPES.add(STARTUP_CLASS);
      ALL_TYPES.add(WLDF_SYS_RES);
   }
}
