package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.utils.collections.CollectionUtils;
import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.management.Descriptor;
import javax.management.JMException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanInfo;

public final class MBeanHarvesterImpl extends BaseHarvesterImpl {
   private static final String MODEL_MBEAN_TYPE = ModelMBean.class.getName();
   private static final String WLS_MODEL_MBEAN_TYPE = "weblogic.management.jmx.modelmbean.WLSModelMBean";
   private static final int MAX_GETATTRIBUTE_RETRIES = 3;
   static final long serialVersionUID = 1L;
   static final String MBEAN_HARVESTABLE_TYPE_NAME = "DiagnosticTypeName";
   private MBeanServerConnection mBeanServer = null;
   private String categorizerClassNames = "com.bea.adaptive.mbean.typing.MBeanCategorizerPlugins.WLSPlugin,com.bea.adaptive.mbean.typing.MBeanCategorizerPlugins.CustomPlugin";

   public static Harvester getMBeanHarvesterImpl(String potentialName, String namespace, MBeanServerConnection mbeanServerConn, MBeanCategorizer categorizer, String[] priorityMBeanPatterns, Executor executor) throws IOException, JMException {
      MBeanHarvesterImpl hvst = (MBeanHarvesterImpl)getHarvester(potentialName);
      if (hvst != null && hvst.mBeanServer != mbeanServerConn) {
         throw new RuntimeException(mtf_base.getAlreadyRegisteredStr(potentialName));
      } else {
         hvst = createHarvesterInstance(potentialName, namespace, mbeanServerConn, categorizer, priorityMBeanPatterns, executor);
         return hvst;
      }
   }

   private static MBeanHarvesterImpl createHarvesterInstance(String name, String namespace, MBeanServerConnection mBeanServer, MBeanCategorizer categorizer, String[] priorityMBeanPatterns, Executor executor) throws IOException, JMException {
      MBeanHarvesterImpl harv = new MBeanHarvesterImpl();

      try {
         initHarvesterInstance(harv, name, namespace, mBeanServer, categorizer, priorityMBeanPatterns, executor);
         return harv;
      } catch (IOException var8) {
         harv.deallocate(true);
         throw var8;
      } catch (JMException var9) {
         harv.deallocate(true);
         throw var9;
      } catch (RuntimeException var10) {
         harv.deallocate();
         throw var10;
      } catch (Throwable var11) {
         harv.deallocate();
         throw new RuntimeException(var11);
      }
   }

   protected static void initHarvesterInstance(MBeanHarvesterImpl harv, String name, String namespace, MBeanServerConnection mBeanServer, MBeanCategorizer categorizer, String[] priorityMBeanPatterns, Executor executor) throws IOException, JMException {
      harv.setUpDebugFlags("DebugMBeanHarvester");
      harv.mBeanServer = mBeanServer;
      MBeanRegistrationManager mbeanRegistrationManager = new MBeanRegistrationManager(name, mBeanServer, categorizer, harv, harv.getDebugLogger(), priorityMBeanPatterns, executor);
      MetricInfoManager metricInfoManager = new MetricInfoManager(harv);
      BaseHarvesterImpl.initializeHarvesterInstance(harv, name, namespace, mbeanRegistrationManager, metricInfoManager);
   }

   public String getCategorizers() {
      return this.categorizerClassNames;
   }

   public void setCategorizers(String categorizerClassNames) {
      this.categorizerClassNames = categorizerClassNames;
      if (this.dC()) {
         this.dbgC("Harvester categorizer class names set to: " + CollectionUtils.aggregateToString(categorizerClassNames));
      }

   }

   public MBeanServerConnection getMBeanServer() {
      return this.mBeanServer;
   }

   public void deallocate(boolean force) {
      super.deallocate(force);
      this.mBeanServer = null;
   }

   public String findTypeName(String instanceName) throws IOException {
      String var13;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("findTypeName", this, "   instanceName=" + instanceName);
         }

         synchronized(this) {
            this.afterSync("findTypeName");
            if (!(this.state instanceof BaseHarvesterImpl.States.Active)) {
               throw new RuntimeException(mtf_base.getInvalidStateStr(this.getName(), "" + this.state));
            }

            if (instanceName == null) {
               throw new RuntimeException(mtf_base.getNullInstNameErr());
            }

            String typeName = null;

            try {
               ObjectName instanceObjectName = new ObjectName(instanceName);
               typeName = this.getTypeNameFromMBeanInfo(instanceObjectName, (MBeanInfo)null);
            } catch (JMException var10) {
               throw new RuntimeException(var10);
            }

            var13 = typeName;
         }
      } finally {
         this.unsynced("findTypeName");
      }

      return var13;
   }

   protected boolean instanceNameIsValid(String instName) {
      try {
         new ObjectName(instName);
         return true;
      } catch (MalformedObjectNameException var3) {
         return false;
      }
   }

   private String getTypeNameFromMBeanInfo(ObjectName instanceObjectName, MBeanInfo mbi) throws JMException, IOException {
      if (mbi == null) {
         mbi = this.mBeanServer.getMBeanInfo(instanceObjectName);
      }

      String typeName = null;
      if (!(mbi instanceof ModelMBeanInfo)) {
         typeName = mbi.getClassName();
      } else {
         ModelMBeanInfo mmbi = (ModelMBeanInfo)mbi;
         Descriptor descr = mmbi.getMBeanDescriptor();
         typeName = (String)descr.getFieldValue("DiagnosticTypeName");
      }

      return typeName;
   }

   protected Object getAttribute(Object bean, String attrName) throws Exception {
      ObjectName instanceName = (ObjectName)bean;
      Object attrVal = null;
      int retries = 0;
      if (retries < 3) {
         attrVal = this.mBeanServer.getAttribute(instanceName, attrName);
      }

      return attrVal;
   }

   protected void validateInstanceName(String instName) throws IllegalArgumentException {
      try {
         new ObjectName(instName);
      } catch (MalformedObjectNameException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   protected Object getInstance(String inst) throws Exception {
      return new ObjectName(inst);
   }

   protected boolean isNonWLSModelMBean(String instance) {
      try {
         ObjectName objectName = new ObjectName(instance);
         return this.mBeanServer.isInstanceOf(objectName, MODEL_MBEAN_TYPE) && !this.mBeanServer.isInstanceOf(objectName, "weblogic.management.jmx.modelmbean.WLSModelMBean");
      } catch (Exception var3) {
         if (this.dH()) {
            this.dbgH("Unexpected exception: " + var3.getMessage());
            var3.printStackTrace();
         }

         return false;
      }
   }
}
