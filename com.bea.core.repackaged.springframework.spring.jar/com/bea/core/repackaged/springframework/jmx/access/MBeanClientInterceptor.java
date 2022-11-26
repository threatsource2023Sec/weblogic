package com.bea.core.repackaged.springframework.jmx.access;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.jmx.support.JmxUtils;
import com.bea.core.repackaged.springframework.jmx.support.ObjectNameManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.management.Attribute;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.JMException;
import javax.management.JMX;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXServiceURL;

public class MBeanClientInterceptor implements MethodInterceptor, BeanClassLoaderAware, InitializingBean, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private MBeanServerConnection server;
   @Nullable
   private JMXServiceURL serviceUrl;
   @Nullable
   private Map environment;
   @Nullable
   private String agentId;
   private boolean connectOnStartup = true;
   private boolean refreshOnConnectFailure = false;
   @Nullable
   private ObjectName objectName;
   private boolean useStrictCasing = true;
   @Nullable
   private Class managementInterface;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   private final ConnectorDelegate connector = new ConnectorDelegate();
   @Nullable
   private MBeanServerConnection serverToUse;
   @Nullable
   private MBeanServerInvocationHandler invocationHandler;
   private Map allowedAttributes = Collections.emptyMap();
   private Map allowedOperations = Collections.emptyMap();
   private final Map signatureCache = new HashMap();
   private final Object preparationMonitor = new Object();

   public void setServer(MBeanServerConnection server) {
      this.server = server;
   }

   public void setServiceUrl(String url) throws MalformedURLException {
      this.serviceUrl = new JMXServiceURL(url);
   }

   public void setEnvironment(@Nullable Map environment) {
      this.environment = environment;
   }

   @Nullable
   public Map getEnvironment() {
      return this.environment;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
   }

   public void setConnectOnStartup(boolean connectOnStartup) {
      this.connectOnStartup = connectOnStartup;
   }

   public void setRefreshOnConnectFailure(boolean refreshOnConnectFailure) {
      this.refreshOnConnectFailure = refreshOnConnectFailure;
   }

   public void setObjectName(Object objectName) throws MalformedObjectNameException {
      this.objectName = ObjectNameManager.getInstance(objectName);
   }

   public void setUseStrictCasing(boolean useStrictCasing) {
      this.useStrictCasing = useStrictCasing;
   }

   public void setManagementInterface(@Nullable Class managementInterface) {
      this.managementInterface = managementInterface;
   }

   @Nullable
   protected final Class getManagementInterface() {
      return this.managementInterface;
   }

   public void setBeanClassLoader(ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   public void afterPropertiesSet() {
      if (this.server != null && this.refreshOnConnectFailure) {
         throw new IllegalArgumentException("'refreshOnConnectFailure' does not work when setting a 'server' reference. Prefer 'serviceUrl' etc instead.");
      } else {
         if (this.connectOnStartup) {
            this.prepare();
         }

      }
   }

   public void prepare() {
      synchronized(this.preparationMonitor) {
         if (this.server != null) {
            this.serverToUse = this.server;
         } else {
            this.serverToUse = null;
            this.serverToUse = this.connector.connect(this.serviceUrl, this.environment, this.agentId);
         }

         this.invocationHandler = null;
         if (this.useStrictCasing) {
            Assert.state(this.objectName != null, "No ObjectName set");
            this.invocationHandler = new MBeanServerInvocationHandler(this.serverToUse, this.objectName, this.managementInterface != null && JMX.isMXBeanInterface(this.managementInterface));
         } else {
            this.retrieveMBeanInfo(this.serverToUse);
         }

      }
   }

   private void retrieveMBeanInfo(MBeanServerConnection server) throws MBeanInfoRetrievalException {
      try {
         MBeanInfo info = server.getMBeanInfo(this.objectName);
         MBeanAttributeInfo[] attributeInfo = info.getAttributes();
         this.allowedAttributes = new HashMap(attributeInfo.length);
         MBeanAttributeInfo[] var4 = attributeInfo;
         int var5 = attributeInfo.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            MBeanAttributeInfo infoEle = var4[var6];
            this.allowedAttributes.put(infoEle.getName(), infoEle);
         }

         MBeanOperationInfo[] operationInfo = info.getOperations();
         this.allowedOperations = new HashMap(operationInfo.length);
         MBeanOperationInfo[] var16 = operationInfo;
         var6 = operationInfo.length;

         for(int var17 = 0; var17 < var6; ++var17) {
            MBeanOperationInfo infoEle = var16[var17];
            Class[] paramTypes = JmxUtils.parameterInfoToTypes(infoEle.getSignature(), this.beanClassLoader);
            this.allowedOperations.put(new MethodCacheKey(infoEle.getName(), paramTypes), infoEle);
         }

      } catch (ClassNotFoundException var10) {
         throw new MBeanInfoRetrievalException("Unable to locate class specified in method signature", var10);
      } catch (IntrospectionException var11) {
         throw new MBeanInfoRetrievalException("Unable to obtain MBean info for bean [" + this.objectName + "]", var11);
      } catch (InstanceNotFoundException var12) {
         throw new MBeanInfoRetrievalException("Unable to obtain MBean info for bean [" + this.objectName + "]: it is likely that this bean was unregistered during the proxy creation process", var12);
      } catch (ReflectionException var13) {
         throw new MBeanInfoRetrievalException("Unable to read MBean info for bean [ " + this.objectName + "]", var13);
      } catch (IOException var14) {
         throw new MBeanInfoRetrievalException("An IOException occurred when communicating with the MBeanServer. It is likely that you are communicating with a remote MBeanServer. Check the inner exception for exact details.", var14);
      }
   }

   protected boolean isPrepared() {
      synchronized(this.preparationMonitor) {
         return this.serverToUse != null;
      }
   }

   @Nullable
   public Object invoke(MethodInvocation invocation) throws Throwable {
      synchronized(this.preparationMonitor) {
         if (!this.isPrepared()) {
            this.prepare();
         }
      }

      try {
         return this.doInvoke(invocation);
      } catch (IOException | MBeanConnectFailureException var4) {
         return this.handleConnectFailure(invocation, var4);
      }
   }

   @Nullable
   protected Object handleConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
      if (this.refreshOnConnectFailure) {
         String msg = "Could not connect to JMX server - retrying";
         if (this.logger.isDebugEnabled()) {
            this.logger.warn(msg, ex);
         } else if (this.logger.isWarnEnabled()) {
            this.logger.warn(msg);
         }

         this.prepare();
         return this.doInvoke(invocation);
      } else {
         throw ex;
      }
   }

   @Nullable
   protected Object doInvoke(MethodInvocation invocation) throws Throwable {
      Method method = invocation.getMethod();

      try {
         Object result;
         if (this.invocationHandler != null) {
            result = this.invocationHandler.invoke(invocation.getThis(), method, invocation.getArguments());
         } else {
            PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
            if (pd != null) {
               result = this.invokeAttribute(pd, invocation);
            } else {
               result = this.invokeOperation(method, invocation.getArguments());
            }
         }

         return this.convertResultValueIfNecessary(result, new MethodParameter(method, -1));
      } catch (MBeanException var5) {
         throw var5.getTargetException();
      } catch (RuntimeMBeanException var6) {
         throw var6.getTargetException();
      } catch (RuntimeErrorException var7) {
         throw var7.getTargetError();
      } catch (RuntimeOperationsException var8) {
         RuntimeException rex = var8.getTargetException();
         if (rex instanceof RuntimeMBeanException) {
            throw ((RuntimeMBeanException)rex).getTargetException();
         } else if (rex instanceof RuntimeErrorException) {
            throw ((RuntimeErrorException)rex).getTargetError();
         } else {
            throw rex;
         }
      } catch (OperationsException var9) {
         if (ReflectionUtils.declaresException(method, var9.getClass())) {
            throw var9;
         } else {
            throw new InvalidInvocationException(var9.getMessage());
         }
      } catch (JMException var10) {
         if (ReflectionUtils.declaresException(method, var10.getClass())) {
            throw var10;
         } else {
            throw new InvocationFailureException("JMX access failed", var10);
         }
      } catch (IOException var11) {
         if (ReflectionUtils.declaresException(method, var11.getClass())) {
            throw var11;
         } else {
            throw new MBeanConnectFailureException("I/O failure during JMX access", var11);
         }
      }
   }

   @Nullable
   private Object invokeAttribute(PropertyDescriptor pd, MethodInvocation invocation) throws JMException, IOException {
      Assert.state(this.serverToUse != null, "No MBeanServerConnection available");
      String attributeName = JmxUtils.getAttributeName(pd, this.useStrictCasing);
      MBeanAttributeInfo inf = (MBeanAttributeInfo)this.allowedAttributes.get(attributeName);
      if (inf == null) {
         throw new InvalidInvocationException("Attribute '" + pd.getName() + "' is not exposed on the management interface");
      } else if (invocation.getMethod().equals(pd.getReadMethod())) {
         if (inf.isReadable()) {
            return this.serverToUse.getAttribute(this.objectName, attributeName);
         } else {
            throw new InvalidInvocationException("Attribute '" + attributeName + "' is not readable");
         }
      } else if (invocation.getMethod().equals(pd.getWriteMethod())) {
         if (inf.isWritable()) {
            this.serverToUse.setAttribute(this.objectName, new Attribute(attributeName, invocation.getArguments()[0]));
            return null;
         } else {
            throw new InvalidInvocationException("Attribute '" + attributeName + "' is not writable");
         }
      } else {
         throw new IllegalStateException("Method [" + invocation.getMethod() + "] is neither a bean property getter nor a setter");
      }
   }

   private Object invokeOperation(Method method, Object[] args) throws JMException, IOException {
      Assert.state(this.serverToUse != null, "No MBeanServerConnection available");
      MethodCacheKey key = new MethodCacheKey(method.getName(), method.getParameterTypes());
      MBeanOperationInfo info = (MBeanOperationInfo)this.allowedOperations.get(key);
      if (info == null) {
         throw new InvalidInvocationException("Operation '" + method.getName() + "' is not exposed on the management interface");
      } else {
         String[] signature;
         synchronized(this.signatureCache) {
            signature = (String[])this.signatureCache.get(method);
            if (signature == null) {
               signature = JmxUtils.getMethodSignature(method);
               this.signatureCache.put(method, signature);
            }
         }

         return this.serverToUse.invoke(this.objectName, method.getName(), args, signature);
      }
   }

   @Nullable
   protected Object convertResultValueIfNecessary(@Nullable Object result, MethodParameter parameter) {
      Class targetClass = parameter.getParameterType();

      try {
         if (result == null) {
            return null;
         } else if (ClassUtils.isAssignableValue(targetClass, result)) {
            return result;
         } else {
            Method fromMethod;
            if (result instanceof CompositeData) {
               fromMethod = targetClass.getMethod("from", CompositeData.class);
               return ReflectionUtils.invokeMethod(fromMethod, (Object)null, result);
            } else {
               Class elementType;
               if (result instanceof CompositeData[]) {
                  CompositeData[] array = (CompositeData[])((CompositeData[])result);
                  if (targetClass.isArray()) {
                     return this.convertDataArrayToTargetArray(array, targetClass);
                  }

                  if (Collection.class.isAssignableFrom(targetClass)) {
                     elementType = ResolvableType.forMethodParameter(parameter).asCollection().resolveGeneric();
                     if (elementType != null) {
                        return this.convertDataArrayToTargetCollection(array, targetClass, elementType);
                     }
                  }
               } else {
                  if (result instanceof TabularData) {
                     fromMethod = targetClass.getMethod("from", TabularData.class);
                     return ReflectionUtils.invokeMethod(fromMethod, (Object)null, result);
                  }

                  if (result instanceof TabularData[]) {
                     TabularData[] array = (TabularData[])((TabularData[])result);
                     if (targetClass.isArray()) {
                        return this.convertDataArrayToTargetArray(array, targetClass);
                     }

                     if (Collection.class.isAssignableFrom(targetClass)) {
                        elementType = ResolvableType.forMethodParameter(parameter).asCollection().resolveGeneric();
                        if (elementType != null) {
                           return this.convertDataArrayToTargetCollection(array, targetClass, elementType);
                        }
                     }
                  }
               }

               throw new InvocationFailureException("Incompatible result value [" + result + "] for target type [" + targetClass.getName() + "]");
            }
         }
      } catch (NoSuchMethodException var6) {
         throw new InvocationFailureException("Could not obtain 'from(CompositeData)' / 'from(TabularData)' method on target type [" + targetClass.getName() + "] for conversion of MXBean data structure [" + result + "]");
      }
   }

   private Object convertDataArrayToTargetArray(Object[] array, Class targetClass) throws NoSuchMethodException {
      Class targetType = targetClass.getComponentType();
      Method fromMethod = targetType.getMethod("from", array.getClass().getComponentType());
      Object resultArray = Array.newInstance(targetType, array.length);

      for(int i = 0; i < array.length; ++i) {
         Array.set(resultArray, i, ReflectionUtils.invokeMethod(fromMethod, (Object)null, array[i]));
      }

      return resultArray;
   }

   private Collection convertDataArrayToTargetCollection(Object[] array, Class collectionType, Class elementType) throws NoSuchMethodException {
      Method fromMethod = elementType.getMethod("from", array.getClass().getComponentType());
      Collection resultColl = CollectionFactory.createCollection(collectionType, Array.getLength(array));

      for(int i = 0; i < array.length; ++i) {
         resultColl.add(ReflectionUtils.invokeMethod(fromMethod, (Object)null, array[i]));
      }

      return resultColl;
   }

   public void destroy() {
      this.connector.close();
   }

   private static final class MethodCacheKey implements Comparable {
      private final String name;
      private final Class[] parameterTypes;

      public MethodCacheKey(String name, @Nullable Class[] parameterTypes) {
         this.name = name;
         this.parameterTypes = parameterTypes != null ? parameterTypes : new Class[0];
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof MethodCacheKey)) {
            return false;
         } else {
            MethodCacheKey otherKey = (MethodCacheKey)other;
            return this.name.equals(otherKey.name) && Arrays.equals(this.parameterTypes, otherKey.parameterTypes);
         }
      }

      public int hashCode() {
         return this.name.hashCode();
      }

      public String toString() {
         return this.name + "(" + StringUtils.arrayToCommaDelimitedString(this.parameterTypes) + ")";
      }

      public int compareTo(MethodCacheKey other) {
         int result = this.name.compareTo(other.name);
         if (result != 0) {
            return result;
         } else if (this.parameterTypes.length < other.parameterTypes.length) {
            return -1;
         } else if (this.parameterTypes.length > other.parameterTypes.length) {
            return 1;
         } else {
            for(int i = 0; i < this.parameterTypes.length; ++i) {
               result = this.parameterTypes[i].getName().compareTo(other.parameterTypes[i].getName());
               if (result != 0) {
                  return result;
               }
            }

            return 0;
         }
      }
   }
}
