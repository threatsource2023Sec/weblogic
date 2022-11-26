package weblogic.management.deploy.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.DeployerRuntimeMBean;

public class DeployerRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeployerRuntimeMBean.class;

   public DeployerRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeployerRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.internal.DeployerRuntimeImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.deploy.api.spi.WebLogicDeploymentManager} ");
      beanDescriptor.setValue("package", "weblogic.management.deploy.internal");
      String description = (new String("<p>This MBean is the user API for initiating deployment requests and exists only on an Administration Server. To access this MBean, use {@link weblogic.management.deploy.DeployerRuntime#getDeployerRuntime()}.</p>  <p>The deployment methods in this MBean provide access to the two-phase deployment protocol. This protocol is only supported on WebLogic Server 7.x servers and later. If no target servers in an activate request are known to be pre-release 7 servers, then the two-phase protocol is used. Otherwise the deployment will use the WebLogic Server 6.x deployment protocol, even if some target servers are at release 7.</p>  <p>The deployment process involves a number of state changes: start&lt;--&gt;staged&lt;--&gt;prepared&lt;--&gt;active. The methods in this MBean provide the means for changing the state of an application, as follows:</p>  <p>activate: places application in active state from any other state</p>  <p>deactivate: places application in prepared state from active state</p>  <p>unprepare: places application in staged state from active and prepared state</p>  <p>remove: places application in start state from any other state</p>  <p>Activating An Application</p>  <p>The basic process of deploying an application is shown in the following example:</p>  <p><code> DeployerRuntimeMBean deployer = getDeployerRuntime(userName, password, adminURL);<br> try {<br> DeploymentTaskRuntimeMBean task = deployer.activate(sourceFile,appName,taging,info,id);<br> } catch (ManagementException me) {<br> System.out.println(\"Deployment failed: \"+me.getMessage());<br> } </code></p>  <p>In this example, <code>sourceFile</code> is the path to the application. If the application is an EAR, then <code>sourceFile</code> would name the EAR archive or the root directory if it is not archived. Similarly, if the application is not an EAR, but a standalone module (web application or EJB), the <code>sourceFile</code> argument would be the path to the module archive or directory.</p>  <p>The <code>sourceFile</code> argument can be null, indicating that this is a redeployment and that the source is unchanged from a previous deployment.</p>  <p>The <code>appName</code> argument is the name to be given to the application. If this is a new deployment, an {@link weblogic.management.configuration.ApplicationMBean} is created. For redeployments, an existing ApplicationMBean with <code>appName</code> is used as the application's configured state.</p>  <p>The <code>info</code> argument is a {@link weblogic.management.deploy.DeploymentData} object which is used to qualify the deployment in terms of targets of the deployment and an optional list of files which are to be refreshed during a redeploy.</p>  <p>The staging argument is used to specify whether the application is to be staged to the target servers. This argument may be null (use {@link weblogic.management.configuration.ServerMBean#getStagingMode}), \"stage\", or \"nostage\". Staging is the process of uploading the application file to the target servers' staging area, defined in {@link weblogic.management.configuration.ServerMBean#getStagingDirectoryName}.</p>  <p>The <code>id</code> argument allows the caller to name the deployment task. Care should be taken here as the tag must be unique. The recommendation is to generally use null for this argument, allowing the system to generate a unique tag.</p>  <p>The deployment process runs asynchronously to the invoker; it will initiate the task then return the {@link weblogic.management.runtime.DeploymentTaskRuntimeMBean} object representing the task to the client. This object may be used to track status of the task. If the client wants to wait until the task completes then the following is a basic method for doing this.</p>  <p><code> while (task.isRunning()) {<br> try {<br> Thread.sleep(oneSecond);<br> } catch (InterruptedException ie) {}<br> } </code></p>  <p>Cancelling A Deployment</p>  <p>Note that a task will not complete until all targets have either completed the deployment or failed. If one of the targets is inactive, the task will remain active until the server starts or the task is cancelled. Cancelling a deployment task is accomplished as follows:</p>  <p><code> if (task.isRunning()) {<br> try {<br> task.cancel();<br> } catch (Exception e) {}<br> } </code></p>  <p>Targeting Specific Servers</p>  <p>The following examples show how to be more specific when targeting a deployment.</p>  <p><code> DeploymentData info = new DeploymentData();<br> <br> // adds server1 as target for all modules in app<br> <br> info.addTarget(server1,null); <br> String[] mods = { \"web-module\",\"ejb\" };<br> <br> // adds server2 as target for modules web-module and ejb<br> <br> info.addTarget(server2,mods);<br> deployer.activate(sourceFile, appName, info, null, null);<br> <br> // refreshes the hello.jsp file on all currently targeted servers.<br> // The \"jsps\" directory is relative to the root of the application.<br> <br> String[] files = { \"jsps/hello.jsp\" };<br> DeploymentData info = new DeploymentData(files);<br> deployer.activate(null, appName, null, info, null);<br> </code></p>  <p>Deactivating An Application</p>  <p>To deactivate an application is to suspend it. The application files remain staged on the target servers and can be reactivated without restaging. It should be noted that deactivating an application does not unload any of its classes. To do so requires an <code>unprepare</code> operation (see below). The following example show appName being deactivated, then subsequently reactivated on all configured servers.</p>  <p><code> deployer.deactivate(appName, null, null);<br> . . .<br> deployer.activate(null, appName, null, null, null);<br> </code></p>  <p>Unpreparing An Application</p>  <p>To unprepare an application is to suspend and unload it. The application files remain staged on the target servers and any relevant classes are unloaded. If the application is to be reactivated with new class files, unprepare is the correct approach, rather than deactivate. The following example shows appName being unprepared, then subsequently reactivated on all configured servers.</p>  <p><code> <code>deployer.unprepare(appName, null, null);<br> . . .<br> deployer.activate(sourceFile, appName, null, null, null); </code></p>  <p>Removing An Application</p>  <p>Removing an application involves deactivation, unstaging and possible removal of the application. After removing an application from a Managed Server, it is deconfigured from that server. If no servers remain targeted by the application, the entire configuration of the application is removed. Removal does not touch the application source, but will remove staged copies of the application.</p>  <p><code> // this completely removes an application from the domain configuration<br> <br> deployer.remove(appName, null, null); </code></p>  <p>Tracking Deployment Status</p>  <p>Once initiated, a deployment task can be monitored via notifications or polling. Use of notifications relies on JMX Notifications on the relevant ApplicationMBean and is accomplished as follows:</p>  <p><code> package examples.deploy;<br> import java.io.Serializable;<br> import javax.management.MBeanServer;<br> import javax.management.Notification;<br> import javax.management.NotificationFilter;<br> import weblogic.management.DeploymentNotification;<br> import weblogic.management.Helper;<br> import weblogic.management.MBeanHome;<br> import weblogic.management.RemoteNotificationListener;<br> import weblogic.management.configuration.ApplicationMBean;<br> import weblogic.management.deploy.DeploymentData;<br> import weblogic.management.deploy.DeployerRuntime;<br> import weblogic.management.runtime.DeployerRuntimeMBean;<br> import weblogic.management.runtime.DeploymentTaskRuntimeMBean;<br> <br> //This example activates an application and prints the resulting<br> // notifications generated during the processing of the deployment.<br> // The args passed to this program are:<br> // arg1: userid<br> // arg2: password<br> // arg3: admin URL<br> // arg4: app name<br> // arg5: app source<br> // arg6: target server<br> <br> public class Activater implements Serializable {<br> <br> private static String userid;<br> private static String password;<br> private static String url;<br> private static String name;<br> private static String source;<br> private static String server;<br> <br> void deploy() {<br> try {<br> <br> // Get access to MBeanHome<br> <br> MBeanHome home = Helper.getAdminMBeanHome(userid, password, url);<br> <br> // Get the deployer<br> <br> DeployerRuntimeMBean deployer = DeployerRuntime.getDeployerRuntime(home);<br> <br> // Build the DeploymentData object<br> <br> DeploymentData info = new DeploymentData();<br> info.addTarget(server, null);<br> <br> // Create the deployment task. Last arg indicates to just<br> // create the task, but not initiate it<br> <br> DeploymentTaskRuntimeMBean task = deployer.activate(source,name,null,info,null,false);<br> <br> // Register for notifications<br> <br> ApplicationMBean app = task.getDeploymentObject();<br> MBeanServer mBeanServer = home.getMBeanServer();<br> mBeanServer.addNotificationListener( app.getObjectName(),new DeployListener(),new DeployFilter(),null );<br> <br> // Start the task<br> <br> task.start();<br> System.out.println(task.getDescription());<br> <br> // wait until finished<br> <br> while (task.isRunning()) {<br> try {<br> Thread.sleep(1000);<br> } catch (InterruptedException ie) {<br> System.out.println(task.getStatus());<br> }<br> }<br> } catch (Exception e) {<br> System.out.println(e.getMessage());<br> }<br> }<br> <br> public static void main(String[] argv) throws Exception {<br> if (argv.length == 6) {<br> userid = argv[0];<br> password = argv[1];<br> url = argv[2];<br> name = argv[3];<br> source = argv[4];<br> server = argv[5];<br> Activater activater = new Activater();<br> activater.deploy();<br> System.exit(0);<br> }<br> }<br> <br> // Inner classes for handling notifications<br> <br> class DeployListener implements RemoteNotificationListener {<br> <br> public void handleNotification(Notification notification,java.lang.Object handback)<br> {<br> System.out.println( notification.getMessage() );<br> }<br> };<br> <br> // Inner class for filtering notifications<br> <br> class DeployFilter implements NotificationFilter, Serializable {<br> <br> public boolean isNotificationEnabled( Notification n )<br> {<br> return ( n instanceof DeploymentNotification );<br> }<br> <br> }<br> } </code></p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.DeployerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("DeploymentTaskRuntimes")) {
         String getterName = "getDeploymentTaskRuntimes";
         String setterName = null;
         currentResult = new PropertyDescriptor("DeploymentTaskRuntimes", DeployerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentTaskRuntimes", currentResult);
         currentResult.setValue("description", "Return the deployment task runtime MBeans. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeployerRuntimeMBean.class.getMethod("activate", String.class, String.class, String.class, DeploymentData.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", "is the path to the application. If null, the configured path is used. "), createParameterDescriptor("name", "is the configured name of the application. "), createParameterDescriptor("stagingMode", "the value that will be set on the ApplicationMBean for this deployment &quot;stage&quot;, &quot;external_stage&quot;,&quot;nostage&quot;, or null which implies use the servers stagingMode value. "), createParameterDescriptor("info", "describes the details of the deployment. "), createParameterDescriptor("id", "to use for tracking. Use null to allow system to generate. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #deploy} or {@link #redeploy} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Activate a deployment. The deployment is extended to the relevant Java EE containers and subsystems. An ApplicationMBean is created if necessary and initialized prior to initiating the deployment for new deployments. If the application is already configured, it is expected to be correctly defined. Activate covers new deployments, redeployments and refreshes.</p>  <p>If the source argument is a valid path to an EAR or module, the application files will be distributed as necessary to the target servers. If the source argument is null, then the application must already be configured with a valid path.</p>  <p>The name argument must always be specified (not null). If there is already an application configured with this name, then the deployment will be based on that application. Otherwise, this is a new deployment and an ApplicationMBean will be created and fully configured based on the application descriptors found in the archive or directory named by the source argument. If this is a new deployment, the source argument cannot be null.</p>  <p>The stagingMode argument can be used to override the staging attribute of the targeted servers. If this argument is null, the application will be staged to each server if that server is configured to be staged. If stagingMode is \"stage\" or \"nostage\", then the application will be staged or not staged, respectively, to each server, regardless of the server's configuration. If the staging mode is \"external_stage\", the application files are not staged by the server, rather the user is expected to place them in the staging area.</p>  <p>The info argument is used to qualify the deployment. If null, the deployment will apply to the application's configured target servers. If info is not null, then it names a list of servers, each of which can be further qualified by module names. If a named target is not already configured for this application, it will be added as a target to the appropriate components.</p>  <p>The info argument can also specify a list of files and directories. This supports application refreshes. When a file list is defined in the info object, the deployment will cause those files to be redistributed to the target servers. The file paths must be relative to the application source. If the application is an archive, the entire archive is redistributed, otherwise only the named files are distributed. Note that if the application targets release 6.x servers, there is no guarantee that only the files listed are redeployed.</p>  <p>The ID argument is used to specify the identifier for the resulting task. If null, the system will generate the ID. If not null, then the value must be unique across all existing deployment tasks.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("activate", String.class, String.class, String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", (String)null), createParameterDescriptor("name", (String)null), createParameterDescriptor("stagingMode", (String)null), createParameterDescriptor("info", (String)null), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #deploy} or {@link #redeploy} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #activate(String, String, String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must then invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the activation process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("deactivate", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application. "), createParameterDescriptor("info", "describes the details of the deployment. Null interpreted               to deactivate the application on all servers, retaining               targets. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #stop} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deactivate a deployment. This suspends the services offered by the deployment and removes it from the relevant Java EE containers. The deployment will remain in the staging area and prepared following deactivation.</p>  <p>The info parameter is used to define the specific targets the deactivation applies to. If any targets are specified in the info object, they will be removed from the application configuration. If info object does not specify any targets, then the deactivation will apply to all targets configured for the application. In this scenario, the configured targets are not removed from the configuration. Rather, the application is configured as undeployed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("deactivate", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("info", (String)null), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #stop} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #deactivate(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the deactivation process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("remove", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to remove "), createParameterDescriptor("info", "describes the details of the deployment. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #undeploy} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a deployment. This results in the application being deactivated and deconfigured from the target servers. Staged files are removed from the target server if they were staged when first deployed. If no targets are specified in the info object, or if the info object is null, the application is removed entirely from the domain.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("remove", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("info", (String)null), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #undeploy} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #remove(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the remove process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("unprepare", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to unprepare "), createParameterDescriptor("info", "describes the details of the deployment. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #stop} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deactivate and unload a deployment. This results in the application being deactivated and classes are unloaded from the target servers. Staged files are not removed from the target server if they were staged when first deployed. If no targets are specified in the info object, or if the info object is null, the application is removed entirely from the domain.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("unprepare", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("info", (String)null), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link #stop} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #unprepare(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("distribute", String.class, String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", "is the path to the application files "), createParameterDescriptor("name", "is the configured name of the application to distribute "), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Distributes application files on targets. This results in the application being copied to the staging area of a target.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("distribute", String.class, String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", (String)null), createParameterDescriptor("name", (String)null), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #distribute(String source, String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DeployerRuntimeMBean.class.getMethod("appendToExtensionLoader", String.class, DeploymentData.class, String.class, Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", (String)null), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Distributes code source identified by source to the WebLogic extension loader directory of each targeted server and appends the code source to the running WebLogic extension class loader. Control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = DeployerRuntimeMBean.class.getMethod("start", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to start "), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts an already distributed application on its target(s). This results in the application being prepared and activated on its target(s).</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DeployerRuntimeMBean.class.getMethod("start", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #start(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DeployerRuntimeMBean.class.getMethod("stop", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to start "), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Stops an application on its target(s). This results in an application becoming unavailable for the clients.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DeployerRuntimeMBean.class.getMethod("stop", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #stop(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DeployerRuntimeMBean.class.getMethod("deploy", String.class, String.class, String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", "is the path to the application files "), createParameterDescriptor("name", "is the configured name of the application to distribute "), createParameterDescriptor("info", "contains target information. It names a list of target servers, each of which can be further  qualified by module names. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. "), createParameterDescriptor("stagingMode", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deploys an application on its target(s). This results in the application being distributed, prepared and activated on specified target(s). The name argument must always be specified (not null). If there is already an application configured with this name, then the deployment will be based on that application. Otherwise, this is a new deployment and an ApplicationMBean will be created and fully configured based on the application descriptors found in the archive or directory named by the source argument. If this is a new deployment, the source argument cannot be null.</p>  <p>The stagingMode argument can be used to override the staging attribute of the targeted servers. If this argument is null, the application will be staged to each server if that server is configured to be staged. If stagingMode is \"stage\" or \"nostage\" then the application will be staged or not staged, respectively, to each server, regardless of the server's configuration. If the staging mode is \"external_stage\", the application files are not staged by the server, rather the user is expected to place them in the staging area.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("deploy", String.class, String.class, String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("source", (String)null), createParameterDescriptor("name", (String)null), createParameterDescriptor("stagingMode", (String)null), createParameterDescriptor("info", (String)null), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #deploy(java.lang.String, java.lang.String, java.lang.String, weblogic.management.deploy.DeploymentData, java.lang.String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("redeploy", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to distribute "), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Redeploys an application on its target(s). This results in the redeploy of an entire application on its target(s). Redeploy results in redistribution of the application if staging mode is \"stage\". Redeploy can also be used for partial redeployment by specifying a list of files.</p>  <p>The info argument is used to qualify the deployment. If null, the deployment will apply to the application's configured target servers. If info is not null, then it names a list of servers, each of which can be further qualified by module names. If a named target is not already configured for this application, it will be added as a target to the appropriate components.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("redeploy", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("info", (String)null), createParameterDescriptor("id", (String)null), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Same functionality as {@link #redeploy(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("redeploy", String.class, String.class, DeploymentData.class, String.class, Boolean.TYPE);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Redeploys a new version of an application. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("update", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the name of the deployed application "), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. "), createParameterDescriptor("startTask", "indicates whether to automatically start the operation ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates an application's configuration. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("undeploy", String.class, DeploymentData.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to distribute "), createParameterDescriptor("info", "contains target information. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undeploys an application on its target(s). This results in deactivating and removing an application on its target(s). This is the exact reverse of deploy().</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("undeploy", String.class, DeploymentData.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "is the configured name of the application to distribute "), createParameterDescriptor("info", "describes the details of the deployment. "), createParameterDescriptor("id", "to use for tracking. If null, a system generated id is used. "), createParameterDescriptor("startTask", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException if the request is rejected.")};
         currentResult.setValue("throws", roleObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Same functionality as {@link #undeploy(String, DeploymentData, String)} except that control is given back to the caller without actually initiating the task, when startTask is false. The client must invoke the {@link DeploymentTaskRuntimeMBean#start} method to complete the process. This is most useful when the client is interested in receiving notifications of the task's progress. ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("query", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("id", "is the id to query ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Locates a deployment task based on the deployment ID.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DeployerRuntimeMBean.class.getMethod("list");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Return array of all known deployment tasks.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("removeTask", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Return array of all known deployment tasks.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("getDeployments", TargetMBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get all of the DeploymentMBeans that are associated with a specific target. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DeployerRuntimeMBean.class.getMethod("purgeRetiredTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Flush out all retired tasks after completion and returns their IDs back so that the DeployerTool reports a message. </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("8.1.4.0", (String)null, this.targetVersion)) {
         mth = DeployerRuntimeMBean.class.getMethod("getAvailabilityStatusForApplication", String.class, Boolean.TYPE);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "8.1.4.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.AppRuntimeStateRuntimeMBean} ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Provides a map consisting of component names of the application and a map of availability status for each target of that component including any virtual hosts. The map corresponding to each component name consists of the component target name and the weblogic.management.TargetAvailabilityStatus object corresponding to that component target. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "8.1.4.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("8.1.4.0", (String)null, this.targetVersion)) {
         mth = DeployerRuntimeMBean.class.getMethod("getAvailabilityStatusForComponent", ComponentMBean.class, Boolean.TYPE);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "8.1.4.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.AppRuntimeStateRuntimeMBean} ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Provides a map of availability status for each target of that component, including any virtual hosts. The map consist of the component target name and the TargetAvailabilityStatus object corresponding to that component target. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "8.1.4.0");
         }
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
