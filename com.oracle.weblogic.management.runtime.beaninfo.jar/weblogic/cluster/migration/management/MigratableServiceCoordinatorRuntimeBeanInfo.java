package weblogic.cluster.migration.management;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;

public class MigratableServiceCoordinatorRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MigratableServiceCoordinatorRuntimeMBean.class;

   public MigratableServiceCoordinatorRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MigratableServiceCoordinatorRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.migration.management.MigratableServiceCoordinatorRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.cluster.migration.management");
      String description = (new String("This class is used for monitoring the Migratable Service Coordinator. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("migrate", MigratableTargetMBean.class, ServerMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("migratableTarget", "- all services targeted to this target are to be migrated to the destination server. THIS MUST BE A CONFIG MBEAN "), createParameterDescriptor("destination", "- the new server where the services deployed to migratableTarget shall be activated ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Migrates all services deployed to the migratableTarget to the destination server. This method assumes that the source and the destination server are up and running. Precondition: The migratableTarget must contain at least one server. The destination server must be a member of the migratableTarget's list of candidate servers. If automatic migration mode is disabled, the destination server must not be the currently hosting server (i.e. head of candidate list of the migratableTarget). Postcondition: If automatic migration mode is disabled and if the migration succeeded, the head of the candidate server list in the migratableTarget will be the destination server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("migrateSingleton", SingletonServiceMBean.class, ServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("singletonService", "- SingletonService to be migrated to the destination server. THIS MUST BE A CONFIG MBEAN "), createParameterDescriptor("destination", "- the new server where the singleton service shall be activated ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Migrates the singleton service specified by the SingletonServiceMBean to the destination server. This method assumes that the source and the destination server are up and running. Precondition: The SingletonServiceMBean must contain at least one server. The destination server must be a member of the SingletonServiceMBean's list of candidate servers. If automatic migration mode is disabled, the destination server must not be the currently hosting server (i.e. head of candidate list of the migratableTarget). Postcondition: If automatic migration mode is disabled and if the migration succeeded, the head of the candidate server list in the migratableTarget will be the destination server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("migrate", MigratableTargetMBean.class, ServerMBean.class, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("migratableTarget", "- all services targeted to this target are to be                            migrated to the destination server.                            THIS MUST BE A CONFIG MBEAN "), createParameterDescriptor("destination", "- the new server where the services deployed to                       migratableTarget shall be activated "), createParameterDescriptor("sourceUp", "- the currently active server is up and running. If false,                    the administrator must ensure that the services deployed                    to migratableTarget are NOT active. "), createParameterDescriptor("destinationUp", "- the destination server is up and running. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Migrates all services deployed to the migratableTarget to the destination server. Use this method if either the source or the destination or both are not running. Precondition: The migratableTarget must contain at least one server. The destination server must be a member of the migratableTarget's list of candidate servers. If automatic migration mode is disabled, the destination server must not be the currently hosting server (i.e. head of candidate list of the migratableTarget). Postcondition: If automatic migration mode is disabled and if the migration succeeded, the head of the candidate server list in the migratableTarget will be the destination server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("migrateJTA", MigratableTargetMBean.class, ServerMBean.class, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("migratableTarget", (String)null), createParameterDescriptor("destination", (String)null), createParameterDescriptor("sourceUp", (String)null), createParameterDescriptor("destinationUp", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Migrates the JTARecoveryManager deployed to a migratableTarget to the destination server.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("startMigrateTask", MigratableTargetMBean.class, ServerMBean.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("migratableTarget", (String)null), createParameterDescriptor("destination", (String)null), createParameterDescriptor("jta", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts the migration from the targeted server to the destination.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("startMigrateTask", MigratableTargetMBean.class, ServerMBean.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("migratableTarget", (String)null), createParameterDescriptor("destination", (String)null), createParameterDescriptor("jta", (String)null), createParameterDescriptor("sourceDown", (String)null), createParameterDescriptor("destinationDown", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts the migration from the targeted server to the destination. If the targeted server is down, sourceDown should be set to true. If the destination server is down, destinationDown should be set to true.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("deactivateJTATarget", MigratableTargetMBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("migratableTarget", (String)null), createParameterDescriptor("host", "Server that should host the service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("MigrationException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Each server in a cluster has a JTAMigratableTarget and this target is associated with transaction log. When the server hosting this target fails, this target can be migrated to another server in the cluster so that the all the pending transactions can be recovered. When the dead server becomes alive, the tlog has to be remigrated to the original server and it has to be guaranteed that only one server has the target active at an given point of time. Automatic JTAMigratable Target recovery steps:  We have two managed servers in the cluster s1 and s2 and an admin server AS. 1. All three servers are running. 2. MS1 crashes 3. Administrator migrates JTAMigratableTarget to MS2 4. AS records this information in its file store. 5. MS1 is restarted. 6. During boot process, MS1 checks on the AS if JTAMT has been migrated. 7. If the JTAMT is not migrated, AS just returns 8. If the JTAMT is migrated, then AS will deactivate the traget on the that is currently hosting the service. 9. AS records this information in its file store and returns and MS1 activates the JTAMT locally 10.If it fails to deactivate, then MS1 will not reboot. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = MigratableServiceCoordinatorRuntimeMBean.class.getMethod("clearOldMigrationTaskRuntimes");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes all MigrationTaskRuntimeMBeans that have completed and been around for over 30 minutes. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
