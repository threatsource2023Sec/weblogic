package weblogic.diagnostics.image;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFImageRuntimeMBean;

public class ImageRuntimeMBeanImplBeanInfo extends PartitionImageRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFImageRuntimeMBean.class;

   public ImageRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ImageRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.image.ImageRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.image");
      String description = (new String("<p>This interface controls diagnostic image creation, and provides access to run-time information about past and current diagnostic image capture requests.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFImageRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AvailableCapturedImages")) {
         getterName = "getAvailableCapturedImages";
         setterName = null;
         currentResult = new PropertyDescriptor("AvailableCapturedImages", WLDFImageRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvailableCapturedImages", currentResult);
         currentResult.setValue("description", "<p> Returns a list of captured images that are available on the Server.</p> ");
      }

      if (!descriptors.containsKey("ImageCaptureTasks")) {
         getterName = "getImageCaptureTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("ImageCaptureTasks", WLDFImageRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImageCaptureTasks", currentResult);
         currentResult.setValue("description", "<p>The list of all initiated image capture tasks.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ImageDir")) {
         getterName = "getImageDir";
         setterName = null;
         currentResult = new PropertyDescriptor("ImageDir", WLDFImageRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImageDir", currentResult);
         currentResult.setValue("description", "<p>The default directory where the server stores captured diagnostic images.</p> ");
      }

      if (!descriptors.containsKey("ImageTimeout")) {
         getterName = "getImageTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("ImageTimeout", WLDFImageRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImageTimeout", currentResult);
         currentResult.setValue("description", "<p>The default timeout period, in minutes, that the server uses to delay future diagnostic image-capture requests.</p> ");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFImageRuntimeMBean.class.getMethod("lookupImageCaptureTask", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the image capture task to lookup ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lookup image capture task by name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ImageCaptureTasks");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFImageRuntimeMBean.class.getMethod("captureImage");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a diagnostic image in the configured destination directory.</p>  <p>No additional image capture requests will be accepted until the configured lockout period has expired. </p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WLDFServerDiagnosticMBean#getImageTimeout()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("captureImage", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", "absolute or relative directory path ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a diagnostic image in the specified destination directory, which can be specified either as a relative or absolute pathname. If relative, the path is relative to the server's <code>logs</code> directory.</p>  <p>If the directory does not exist, it is created. If the directory exists, it must be writable in order for image creation to proceed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("captureImage", Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lockoutMinutes", "number of minutes before the next image capture request will be accepted ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a diagnostic image in the configured destination directory.</p>  <p>No additional image capture requests will be accepted until the specified lockout period has expired.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("captureImage", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", "absolute or relative path "), createParameterDescriptor("lockoutMinutes", "number of minutes before next image capture request will be accepted ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a diagnostic image in the specified destination directory, which can be specified either as a relative or absolute pathname. If relative, the path is relative to the server's <code>logs</code> directory.</p>  <p>If the directory does not exist, it is created.  If the directory exists, it must be writable in order for image creation to proceed.</p>  <p>No additional image capture requests will be accepted until the specified lockout period has expired.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("listImageCaptureTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "Please use getImageCaptureTasks ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The list of all initiated image capture tasks.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("clearCompletedImageCaptureTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes all completed image capture tasks.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("resetImageLockout");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Reset the lockout period, thus allowing image capture requests to be accepted.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("purgeCapturedImages", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("age", "Purge diagnostic images older than specified age specified in the format Days:Hours:Minutes. If empty or null all existing image files on disk are purged. The hours and minutes components are optional. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Delete captured images specified by the age criteria.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("openImageDataStream", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("imageName", (String)null), createParameterDescriptor("imageEntry", "The imageEntry from within the given imageName. If the imageEntry is null the returned handle pertains to the entire image zip file. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a data stream to the specified image artifact on the server.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("getNextQueryResultDataChunk")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("getNextImageDataChunk", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("imageStreamHandle", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = WLDFImageRuntimeMBean.class.getMethod("closeImageDataStream", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("imageStreamHandle", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Closes the stream specified by the given name. ");
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
