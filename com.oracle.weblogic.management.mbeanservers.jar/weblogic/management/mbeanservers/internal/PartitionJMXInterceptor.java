package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerNotification;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import org.glassfish.hk2.utilities.cache.CacheUtilities;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.ComputationErrorException;
import org.glassfish.hk2.utilities.cache.WeakCARCache;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.Loggable;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.mbeanserver.WLSCoherenceOrVTMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSCoherenceOrVTMBeanNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.mbeanservers.JMXContextUtil;
import weblogic.management.mbeanservers.MBeanInfoBuilder;
import weblogic.management.mbeanservers.MBeanPartitionUtil;
import weblogic.management.mbeanservers.MBeanServerType;
import weblogic.management.mbeanservers.PartitionDecorator;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.MBeanVisibilityResult;
import weblogic.management.visibility.WLSMBeanVisibility;
import weblogic.management.visibility.utils.MBeanNameUtil;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.cmm.Scrubber;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class PartitionJMXInterceptor extends WLSMBeanServerInterceptorBase {
   private static final int MAX_CACHE_SIZE = 2000;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugPartitionJMX");
   private Map listenerToListenerMap = new ConcurrentWeakHashMap();
   private Map objectNameToListnerMap = new ConcurrentWeakHashMap();
   private final WeakCARCache mbeanVisibilityMap = CacheUtilities.createWeakCARCache(new VisibilityComputable(), 2000, false);
   private final WeakCARCache mbeanAttrVisibilityMap = CacheUtilities.createWeakCARCache(new MBeanAttributeVisibilityComputable(this), 2000, false);
   private final WeakCARCache mbeanInfoMap = CacheUtilities.createWeakCARCache(new MBeanInfoMapComputable(this), 2000, false);
   private final WeakCARCache coherenceMBeanMap = CacheUtilities.createWeakCARCache(new CoherenceComputable(), 2000, false);
   private final WeakCARCache vtMBeanMap = CacheUtilities.createWeakCARCache(new VTMBeanComputable(), 2000, false);
   private final String name;

   public PartitionJMXInterceptor(MBeanServerType type) {
      this.name = type.toString();
      if (type != MBeanServerType.EDIT) {
         ((PartitionScrubber)LocatorUtilities.getService(PartitionScrubber.class)).registerPartitionInterceptor(this);
      }

   }

   public String findPartitionName() {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
      return cic.getPartitionName();
   }

   public ObjectInstance createMBean(String s, ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      ObjectInstance instanceObj = null;

      try {
         ObjectName objectNameInMT = this.getObjectNameForRegistration(objectName);
         if (!super.isRegistered(objectNameInMT)) {
            ObjectInstance mbeanInstance = super.createMBean(s, objectNameInMT);
            instanceObj = PartitionDecorator.removePartitionFromObjectInstance(mbeanInstance);
         }

         return instanceObj;
      } catch (InstanceNotFoundException var6) {
         throw new MBeanException(var6);
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameForRegistration(objectName);
      ObjectName objectNameInMT1 = this.getObjectNameForRegistration(objectName1);
      ObjectInstance instanceObj = null;
      if (!super.isRegistered(objectNameInMT)) {
         ObjectInstance mbeanInstance = super.createMBean(s, objectNameInMT, objectNameInMT1);
         instanceObj = PartitionDecorator.removePartitionFromObjectInstance(mbeanInstance);
      }

      return instanceObj;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      ObjectInstance instanceObj = null;

      try {
         ObjectName objectNameInMT = this.getObjectNameForRegistration(objectName);
         if (!super.isRegistered(objectNameInMT)) {
            ObjectInstance mbeanInstance = super.createMBean(s, objectNameInMT, objects, strings);
            instanceObj = PartitionDecorator.removePartitionFromObjectInstance(mbeanInstance);
         }

         return instanceObj;
      } catch (InstanceNotFoundException var8) {
         throw new MBeanException(var8);
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameForRegistration(objectName);
      ObjectName objectNameInMT1 = this.getObjectNameForRegistration(objectName1);
      ObjectInstance instanceObj = null;
      if (!super.isRegistered(objectNameInMT)) {
         ObjectInstance mbeanInstance = super.createMBean(s, objectNameInMT, objectNameInMT1, objects, strings);
         instanceObj = PartitionDecorator.removePartitionFromObjectInstance(mbeanInstance);
      }

      return instanceObj;
   }

   public void unregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      ObjectName objectNameInMT = this.getObjectNameForRegistration(objectName);
      if (super.isRegistered(objectNameInMT)) {
         if (debug.isDebugEnabled()) {
            debug.debug("Calling unregister MBean " + objectNameInMT + " in partition " + this.findPartitionName());
         }

         MBeanInfo info = (MBeanInfo)this.mbeanInfoMap.compute(objectNameInMT);
         this.mbeanVisibilityMap.compute(new ObjectNameMBeanInfo(objectNameInMT, info));
         super.unregisterMBean(objectNameInMT);
      }

   }

   public ObjectInstance registerMBean(Object object, ObjectName objectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      ObjectInstance theObject = null;

      try {
         ObjectName objectNameInMT = this.getObjectNameForRegistration(objectName);
         if (!super.isRegistered(objectNameInMT)) {
            if (debug.isDebugEnabled()) {
               debug.debug("Calling register MBean " + objectNameInMT + " in partition " + this.findPartitionName());
            }

            ObjectInstance instance = super.registerMBean(object, objectNameInMT);
            theObject = PartitionDecorator.removePartitionFromObjectInstance(instance);
         } else {
            theObject = PartitionDecorator.removePartitionFromObjectInstance(super.getObjectInstance(objectNameInMT));
         }

         return theObject;
      } catch (IOException var6) {
         throw new AssertionError(var6);
      } catch (InstanceNotFoundException var7) {
         throw new MBeanRegistrationException(var7);
      }
   }

   public Object getAttribute(ObjectName objectName, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      if (!this.visibleToPartitionsEnabledOnAttribute(objectNameInMT, s)) {
         Loggable loggable = JMXLogger.logMBeanAnnotatedGetAttributeFailedLoggable(s, this.findPartitionName(), objectName.toString());
         throw new AttributeNotFoundException(loggable.getMessage());
      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("Calling getAttribute(): attr: " + s + ", MBean " + objectNameInMT + " in partition " + this.findPartitionName());
         }

         Object attrReturned = super.getAttribute(objectNameInMT, s);
         Object attrVisibleReturned = this.getVisibleReturned(attrReturned);
         Object attrInCurrentPartReturned = MBeanPartitionUtil.getObjectInPartitionContext(this.findPartitionName(), attrVisibleReturned);
         return PartitionDecorator.removePartitionFromResult(attrInCurrentPartReturned);
      }
   }

   public AttributeList getAttributes(ObjectName objectName, String[] strings) throws InstanceNotFoundException, ReflectionException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      ArrayList newStrings = new ArrayList();

      for(int i = 0; i < strings.length; ++i) {
         if (!this.visibleToPartitionsEnabledOnAttribute(objectNameInMT, strings[i])) {
            if (debug.isDebugEnabled()) {
               debug.debug("Cannot get attribute " + strings[i] + " from partition " + this.findPartitionName() + " for MBean " + objectNameInMT + " because it's not annotated with @VisibleToPartitions ALWAYS.");
            }
         } else {
            newStrings.add(strings[i]);
         }
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Calling getAttributes(): attrs: " + newStrings.toString() + ", MBean " + objectNameInMT + " in partition " + this.findPartitionName());
      }

      AttributeList attrList = super.getAttributes(objectNameInMT, (String[])newStrings.toArray(new String[newStrings.size()]));
      AttributeList attrVisibleList = this.getMBeansVisibleResultList(attrList);
      AttributeList newAttrList = MBeanPartitionUtil.rebuildReturnedAttribuuteListForPartition(this.findPartitionName(), attrVisibleList);
      return (AttributeList)PartitionDecorator.removePartitionFromAttributeList(newAttrList);
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      if (!this.visibleToPartitionsEnabledOnAttribute(objectNameInMT, attribute.getName())) {
         Loggable loggable = JMXLogger.logMBeanAnnotatedSetAttributeFailedLoggable(attribute.getName(), this.findPartitionName(), objectNameInMT.toString());
         throw new AttributeNotFoundException(loggable.getMessage());
      } else {
         attribute = (Attribute)this.addPartitionToParam(attribute, this.findPartitionName());
         super.setAttribute(objectNameInMT, attribute);
      }
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      AttributeList newAttributeList = new AttributeList();
      Iterator iter = attributeList.iterator();

      while(iter.hasNext()) {
         Attribute attr = (Attribute)iter.next();
         if (!this.visibleToPartitionsEnabledOnAttribute(objectNameInMT, attr.getName())) {
            if (debug.isDebugEnabled()) {
               debug.debug("Cannot set attribute " + attr.getName() + " from partition " + this.findPartitionName() + " for MBean " + objectNameInMT + " because it's not annotated with @VisibleToPartitions.");
            }
         } else {
            newAttributeList.add(attr);
         }
      }

      attributeList = (AttributeList)this.addPartitionToAttributeList(newAttributeList, this.findPartitionName());
      return super.setAttributes(objectNameInMT, attributeList);
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      if (!this.visibleToPartitionsEnabledOnOperation(objectNameInMT, s, strings)) {
         Loggable loggable = JMXLogger.logMBeanAnnotatedOperationFailedLoggable(s, this.findPartitionName(), objectNameInMT.toString());
         throw new ReflectionException(new NoSuchMethodException(loggable.getMessage()));
      } else {
         Object[] partitionObjects = (Object[])((Object[])this.addPartitionToObjectArray(objects, this.findPartitionName()));
         Object[] objectsNotVisible = this.paramsNotVisibleToPartitions(partitionObjects);
         if (objectsNotVisible != null && objectsNotVisible.length > 0) {
            Loggable loggable = JMXLogger.logParamsNotVisibleToPartitionLoggable(s, this.findPartitionName(), objectNameInMT.toString(), Arrays.toString(objectsNotVisible));
            throw new ReflectionException(new NoSuchMethodException(loggable.getMessage()));
         } else {
            if (debug.isDebugEnabled()) {
               debug.debug("Calling invoke(): method: " + s + ", MBean " + objectNameInMT + " in partition " + this.findPartitionName());
            }

            Object invokeReturned = super.invoke(objectNameInMT, s, partitionObjects, strings);
            Object invokeReturnedVisible = this.getVisibleReturned(invokeReturned);
            return PartitionDecorator.removePartitionFromResult(MBeanPartitionUtil.getObjectInPartitionContext(this.findPartitionName(), invokeReturnedVisible));
         }
      }
   }

   public void addNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      IntermediateNotificationListener intermediateNotificationListener = (IntermediateNotificationListener)this.listenerToListenerMap.get(notificationListener);
      if (intermediateNotificationListener == null) {
         intermediateNotificationListener = new IntermediateNotificationListener(notificationListener);
         this.listenerToListenerMap.put(notificationListener, intermediateNotificationListener);
      }

      super.addNotificationListener(objectNameInMT, intermediateNotificationListener, notificationFilter, o);
      if (debug.isDebugEnabled()) {
         debug.debug("Notification: Type = Add, ListenerSource: " + notificationListener + " ListenedObject: " + objectName);
      }

   }

   public void addNotificationListener(ObjectName sourceObjectName, ObjectName listenerObjectName, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, IOException {
      ObjectName mtSourceObjectName = this.getObjectNameInMTContext(sourceObjectName);
      ObjectName mtListenerObjectName = this.getObjectNameInMTContext(listenerObjectName);

      try {
         this.invokeSuperClassMethod(mtListenerObjectName, (Notification)null, (Object)null);
         IntermediateNotificationListener intermediateNotificationListener = (IntermediateNotificationListener)this.objectNameToListnerMap.get(mtListenerObjectName);
         if (intermediateNotificationListener == null) {
            intermediateNotificationListener = new IntermediateNotificationListener(mtListenerObjectName);
            this.objectNameToListnerMap.put(mtListenerObjectName, intermediateNotificationListener);
         }

         super.addNotificationListener(mtSourceObjectName, intermediateNotificationListener, notificationFilter, o);
         if (debug.isDebugEnabled()) {
            debug.debug("Notification:ListenerSource: " + mtListenerObjectName + " ListenedObject: " + mtSourceObjectName);
         }
      } catch (UnsupportedOperationException var8) {
         throw new RuntimeOperationsException(new RuntimeException("The MBean " + listenerObjectName + " does not implement the NotificationListener interface"));
      } catch (ReflectionException var9) {
      } catch (MBeanException var10) {
         throw new RuntimeException(var10);
      }

   }

   public void removeNotificationListener(ObjectName sourceObjectName, ObjectName listenerObjectName) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      ObjectName mtSourceObjectName = this.getObjectNameInMTContext(sourceObjectName);
      ObjectName mtListenerObjectName = this.getObjectNameInMTContext(listenerObjectName);
      IntermediateNotificationListener listener = (IntermediateNotificationListener)this.objectNameToListnerMap.remove(mtListenerObjectName);
      if (listener != null) {
         super.removeNotificationListener(mtSourceObjectName, listener);
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Notification: Type = Remove, ListenerSource: " + mtListenerObjectName + " ListenedObject: " + mtSourceObjectName);
      }

   }

   public void removeNotificationListener(ObjectName sourceObjectName, ObjectName listenerObjectName, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      ObjectName mtSourceObjectName = this.getObjectNameInMTContext(sourceObjectName);
      ObjectName mtListenerObjectName = this.getObjectNameInMTContext(listenerObjectName);
      IntermediateNotificationListener listener = (IntermediateNotificationListener)this.objectNameToListnerMap.remove(mtListenerObjectName);
      if (listener != null) {
         super.removeNotificationListener(mtSourceObjectName, listener);
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Notification: Type = Remove, ListenerSource: " + mtListenerObjectName + " ListenedObject: " + mtSourceObjectName);
      }

   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      IntermediateNotificationListener listener = (IntermediateNotificationListener)this.listenerToListenerMap.remove(notificationListener);
      if (listener != null) {
         super.removeNotificationListener(objectNameInMT, listener);
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Notification: Type = Remove, ListenerSource: " + notificationListener + " ListenedObject: " + objectName);
      }

   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      IntermediateNotificationListener listener = (IntermediateNotificationListener)this.listenerToListenerMap.remove(notificationListener);
      if (listener != null) {
         super.removeNotificationListener(objectNameInMT, listener);
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Notification: Type = Remove, ListenerSource: " + notificationListener + " ListenedObject: " + objectName);
      }

   }

   public ClassLoader getClassLoaderFor(ObjectName objectName) throws InstanceNotFoundException {
      try {
         ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
         return super.getClassLoaderFor(objectNameInMT);
      } catch (IOException var3) {
         throw new AssertionError(var3);
      }
   }

   public Object instantiate(String className, ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      try {
         ObjectName objectNameInMT = this.getObjectNameInMTContext(loaderName);
         return super.instantiate(className, objectNameInMT);
      } catch (IOException var4) {
         throw new AssertionError(var4);
      }
   }

   public Object instantiate(String className, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, MBeanException, InstanceNotFoundException {
      try {
         ObjectName objectNameInMT = this.getObjectNameInMTContext(loaderName);
         return super.instantiate(className, objectNameInMT, params, signature);
      } catch (IOException var6) {
         throw new AssertionError(var6);
      }
   }

   public boolean isInstanceOf(ObjectName objectName, String s) throws InstanceNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      return super.isInstanceOf(objectNameInMT, s);
   }

   public MBeanInfo getMBeanInfo(ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      if (!MBeanPartitionUtil.isGlobalRuntime()) {
         this.checkVisibility(this.findPartitionName(), objectNameInMT, objectName);
      }

      return MBeanInfoBuilder.buildMBeanInfoWithAnno(objectNameInMT, super.getMBeanInfo(objectNameInMT), this.findPartitionName());
   }

   public boolean isRegistered(ObjectName objectName) throws IOException {
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         return super.isRegistered(objectName);
      } else {
         ObjectName objectNameInMT;
         try {
            objectNameInMT = PartitionDecorator.addPartitionToObjectName(objectName, this.findPartitionName());
         } catch (InstanceNotFoundException var5) {
            if (debug.isDebugEnabled()) {
               debug.debug("Calling isRegistered(): The MBean contains key decorator with different partition name. Return false. " + objectName);
            }

            return false;
         }

         try {
            this.checkVisibility(this.findPartitionName(), objectNameInMT, objectName);
         } catch (InstanceNotFoundException var4) {
            return false;
         }

         boolean registered;
         if (super.isRegistered(objectNameInMT)) {
            if (JMXContextUtil.getMBeanCIC(objectNameInMT) != null && JMXContextUtil.getMBeanCIC(objectNameInMT).getApplicationId() != null) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Calling isRegistered(): This is an app MBean with the current key decorator, returns true : " + objectNameInMT);
               }

               return true;
            } else if (this.visibleToPartitionsEnabledForGlobalMBeans(objectNameInMT)) {
               registered = MBeanPartitionUtil.isNonAppMBeanRegistered(objectNameInMT, this.findPartitionName());
               if (debug.isDebugEnabled()) {
                  debug.debug("Calling isRegistered(): The decorated non-app MBean is visible to partitions, " + objectNameInMT + " is in current partition: " + registered);
               }

               return registered;
            } else {
               if (debug.isDebugEnabled()) {
                  debug.debug("Calling isRegistered(): The decorated non-app MBean is not visible to partitions, return false: " + objectNameInMT);
               }

               return false;
            }
         } else if (super.isRegistered(objectName)) {
            if (JMXContextUtil.getMBeanCIC(objectName) != null && JMXContextUtil.getMBeanCIC(objectName).getApplicationId() != null) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Calling isRegistered() on the app MBean " + objectNameInMT + " should return false: " + super.isRegistered(objectNameInMT));
               }

               return super.isRegistered(objectNameInMT);
            } else if (this.visibleToPartitionsEnabledForGlobalMBeans(objectName)) {
               registered = MBeanPartitionUtil.isNonAppMBeanRegistered(objectName, this.findPartitionName());
               if (debug.isDebugEnabled()) {
                  debug.debug("Calling isRegistered(): The original non-app MBean is visible to partitions, " + objectName + " is in current partition: " + registered);
               }

               return registered;
            } else {
               if (debug.isDebugEnabled()) {
                  debug.debug("Calling isRegistered(): The original non-app MBean is not visible to partitions, return false: " + objectName);
               }

               return false;
            }
         } else {
            if (debug.isDebugEnabled()) {
               debug.debug("Calling isRegistered(): Neither the decorated nor original MBean is registered, return false: " + objectName);
            }

            return false;
         }
      }
   }

   public Set queryNames(ObjectName objectName, QueryExp queryExp) throws IOException {
      String partitionName = this.findPartitionName();
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         if (debug.isDebugEnabled()) {
            debug.debug("queryNames on MBean " + objectName + " in partition " + partitionName);
         }

         return super.queryNames(objectName, queryExp);
      } else {
         Set finalReturnSet = new HashSet();

         try {
            ObjectName objectNameInMT = PartitionDecorator.addPartitionToObjectName(objectName, partitionName);
            Set returned = super.queryNames(objectNameInMT, queryExp);
            if (debug.isDebugEnabled()) {
               debug.debug("queryNames on MBean " + objectNameInMT + " in partition " + partitionName);
            }

            finalReturnSet = this.filterPartitionMBeans(partitionName, returned);
            returned = super.queryNames(objectName, queryExp);
            ((Set)finalReturnSet).addAll(this.filterPartitionMBeans(partitionName, returned));
         } catch (InstanceNotFoundException var7) {
            if (debug.isDebugEnabled()) {
               debug.debug("Calling queryMBean(): The MBean contains key decorator with different partition name. Return empty result. " + objectName);
            }
         }

         return (Set)finalReturnSet;
      }
   }

   public Set queryMBeans(ObjectName objectName, QueryExp queryExp) throws IOException {
      String partitionName = this.findPartitionName();
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         if (debug.isDebugEnabled()) {
            debug.debug("queryMBeans on MBean " + objectName + " in partition " + partitionName);
         }

         return super.queryMBeans(objectName, queryExp);
      } else {
         Set finalReturnSet = new HashSet();

         try {
            ObjectName objectNameInMT = PartitionDecorator.addPartitionToObjectName(objectName, partitionName);
            if (debug.isDebugEnabled()) {
               debug.debug("queryMBeans on the decorated MBean " + objectNameInMT + " in partition " + partitionName);
            }

            Set returned = super.queryMBeans(objectNameInMT, queryExp);
            finalReturnSet = this.filterPartitionMBeans(partitionName, returned);
            if (debug.isDebugEnabled()) {
               debug.debug("queryMBeans on the original MBean " + objectName + " in partition " + partitionName);
            }

            returned = super.queryMBeans(objectName, queryExp);
            ((Set)finalReturnSet).addAll(this.filterPartitionMBeans(partitionName, returned));
         } catch (InstanceNotFoundException var7) {
            if (debug.isDebugEnabled()) {
               debug.debug("Calling queryMBeans(): The MBean contains key decorator with different partition name. Return empty result. " + objectName);
            }
         }

         return (Set)finalReturnSet;
      }
   }

   public ObjectInstance getObjectInstance(ObjectName objectName) throws InstanceNotFoundException, IOException {
      ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
      return PartitionDecorator.removePartitionFromObjectInstance(super.getObjectInstance(objectNameInMT));
   }

   public ClassLoader getClassLoader(ObjectName objectName) throws InstanceNotFoundException {
      try {
         ObjectName objectNameInMT = this.getObjectNameInMTContext(objectName);
         return super.getClassLoader(objectNameInMT);
      } catch (IOException var3) {
         throw new AssertionError(var3);
      }
   }

   private ObjectName getObjectNameInMTContext(ObjectName objectName) throws InstanceNotFoundException, IOException {
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         return objectName;
      } else {
         String partitionName = this.findPartitionName();
         ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName(objectName, partitionName);
         return super.isRegistered(partitionObjectName) ? this.checkGlobalMBeanWithConditions(partitionName, partitionObjectName) : this.checkGlobalMBeanWithConditions(partitionName, objectName);
      }
   }

   private ObjectName checkGlobalMBeanWithConditions(String partitionName, ObjectName objectName) throws InstanceNotFoundException {
      if (this.visibleToPartitionsEnabledForGlobalMBeans(objectName)) {
         return objectName;
      } else {
         Loggable loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
         throw new InstanceNotFoundException(loggable.getMessage());
      }
   }

   private boolean visibleToPartitionsEnabledForGlobalMBeans(ObjectName objectName) {
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         return true;
      } else {
         ComponentInvocationContext mbeanCIC = MBeanCICInterceptor.getCICForMBean(objectName);
         if (mbeanCIC != null && (mbeanCIC.getPartitionName() == null || mbeanCIC.getPartitionName().equals("DOMAIN")) && mbeanCIC.getApplicationId() == null) {
            if (MBeanPartitionUtil.showCoherenceMBeanInPartitionContext(this.findPartitionName(), objectName)) {
               return true;
            } else {
               try {
                  return MBeanInfoBuilder.sureMBeanVisibleToPartitions(objectName, super.getMBeanInfo(objectName));
               } catch (Exception var4) {
                  return MBeanInfoBuilder.globalMBeansVisibleToPartitions;
               }
            }
         } else {
            return MBeanNameUtil.isCoherenceMBean(objectName) ? MBeanPartitionUtil.shouldShowCoherenceMBeanToPartitionUser(this.findPartitionName(), objectName) : true;
         }
      }
   }

   private boolean visibleToPartitionsEnabledOnAttribute(ObjectName objectName, String s) {
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         return true;
      } else {
         ComponentInvocationContext mbeanCIC = MBeanCICInterceptor.getCICForMBean(objectName);
         if (mbeanCIC != null && (mbeanCIC.getPartitionName() == null || mbeanCIC.getPartitionName().equals("DOMAIN")) && mbeanCIC.getApplicationId() == null) {
            if (MBeanPartitionUtil.showCoherenceMBeanInPartitionContext(this.findPartitionName(), objectName)) {
               return true;
            } else {
               try {
                  return MBeanInfoBuilder.sureAttributeVisibleToPartitions(objectName, super.getMBeanInfo(objectName), s);
               } catch (Exception var5) {
                  return true;
               }
            }
         } else {
            return MBeanNameUtil.isCoherenceMBean(objectName) ? MBeanPartitionUtil.shouldShowCoherenceMBeanToPartitionUser(this.findPartitionName(), objectName) : true;
         }
      }
   }

   private boolean visibleToPartitionsEnabledOnOperation(ObjectName objectName, String operation, String[] paramTypes) {
      if (MBeanPartitionUtil.isGlobalRuntime()) {
         return true;
      } else {
         ComponentInvocationContext mbeanCIC = MBeanCICInterceptor.getCICForMBean(objectName);
         if (mbeanCIC != null && (mbeanCIC.getPartitionName() == null || mbeanCIC.getPartitionName().equals("DOMAIN")) && mbeanCIC.getApplicationId() == null) {
            if (MBeanPartitionUtil.showCoherenceMBeanInPartitionContext(this.findPartitionName(), objectName)) {
               return true;
            } else {
               try {
                  return MBeanInfoBuilder.sureOperationVisibleToPartitions(objectName, super.getMBeanInfo(objectName), operation, paramTypes);
               } catch (Exception var6) {
                  return true;
               }
            }
         } else {
            return MBeanNameUtil.isCoherenceMBean(objectName) ? MBeanPartitionUtil.shouldShowCoherenceMBeanToPartitionUser(this.findPartitionName(), objectName) : true;
         }
      }
   }

   private Object getVisibleReturned(Object result) {
      if (result == null) {
         return result;
      } else if (MBeanPartitionUtil.isGlobalRuntime()) {
         return result;
      } else if (result instanceof ObjectName) {
         return this.visibleToPartitionsEnabledForGlobalMBeans((ObjectName)result) ? result : null;
      } else if (result instanceof ObjectInstance) {
         return this.visibleToPartitionsEnabledForGlobalMBeans(((ObjectInstance)result).getObjectName()) ? result : null;
      } else {
         ArrayList resultList;
         int i;
         if (result instanceof ObjectName[]) {
            ObjectName[] resultArray = (ObjectName[])((ObjectName[])result);
            resultList = new ArrayList();

            for(i = 0; i < resultArray.length; ++i) {
               if (this.visibleToPartitionsEnabledForGlobalMBeans(resultArray[i])) {
                  resultList.add(resultArray[i]);
               }
            }

            return resultList.toArray(new ObjectName[resultList.size()]);
         } else if (result instanceof ObjectInstance[]) {
            ObjectInstance[] resultArray = (ObjectInstance[])((ObjectInstance[])result);
            resultList = new ArrayList();

            for(i = 0; i < resultArray.length; ++i) {
               if (this.visibleToPartitionsEnabledForGlobalMBeans(resultArray[i].getObjectName())) {
                  resultList.add(resultArray[i]);
               }
            }

            return resultList.toArray(new ObjectInstance[resultList.size()]);
         } else {
            return result;
         }
      }
   }

   private AttributeList getMBeansVisibleResultList(AttributeList attrListResult) {
      if (attrListResult == null) {
         return attrListResult;
      } else if (MBeanPartitionUtil.isGlobalRuntime()) {
         return attrListResult;
      } else {
         AttributeList newAttrList = new AttributeList();

         for(int i = 0; i < attrListResult.size(); ++i) {
            Attribute attr = (Attribute)attrListResult.get(i);
            Object oldAttrValue = attr.getValue();
            if (oldAttrValue instanceof ObjectName) {
               if (this.visibleToPartitionsEnabledForGlobalMBeans((ObjectName)oldAttrValue)) {
                  newAttrList.add(attr);
               }
            } else if (oldAttrValue instanceof ObjectInstance) {
               if (this.visibleToPartitionsEnabledForGlobalMBeans(((ObjectInstance)oldAttrValue).getObjectName())) {
                  newAttrList.add(attr);
               }
            } else {
               ArrayList visibleAttrList;
               int j;
               Attribute newAttr;
               if (oldAttrValue instanceof ObjectName[]) {
                  ObjectName[] oldAttrArray = (ObjectName[])((ObjectName[])oldAttrValue);
                  visibleAttrList = new ArrayList();

                  for(j = 0; j < oldAttrArray.length; ++j) {
                     if (this.visibleToPartitionsEnabledForGlobalMBeans(oldAttrArray[j])) {
                        visibleAttrList.add(oldAttrArray[j]);
                     }
                  }

                  newAttr = new Attribute(attr.getName(), visibleAttrList.toArray(new ObjectName[visibleAttrList.size()]));
                  newAttrList.add(newAttr);
               } else if (!(oldAttrValue instanceof ObjectInstance[])) {
                  newAttrList.add(attr);
               } else {
                  ObjectInstance[] oldAttrArray = (ObjectInstance[])((ObjectInstance[])oldAttrValue);
                  visibleAttrList = new ArrayList();

                  for(j = 0; j < oldAttrArray.length; ++j) {
                     if (this.visibleToPartitionsEnabledForGlobalMBeans(oldAttrArray[j].getObjectName())) {
                        visibleAttrList.add(oldAttrArray[j]);
                     }
                  }

                  newAttr = new Attribute(attr.getName(), visibleAttrList.toArray(new ObjectInstance[visibleAttrList.size()]));
                  newAttrList.add(newAttr);
               }
            }
         }

         return newAttrList;
      }
   }

   private Object[] paramsNotVisibleToPartitions(Object[] params) {
      if (params == null) {
         return null;
      } else if (MBeanPartitionUtil.isGlobalRuntime()) {
         return null;
      } else {
         List paramsNotVisible = new ArrayList();

         for(int i = 0; i < params.length; ++i) {
            if (params[i] instanceof ObjectName) {
               if (!this.visibleToPartitionsEnabledForGlobalMBeans((ObjectName)params[i])) {
                  paramsNotVisible.add(params[i]);
               }
            } else if (params[i] instanceof ObjectInstance) {
               if (!this.visibleToPartitionsEnabledForGlobalMBeans(((ObjectInstance)params[i]).getObjectName())) {
                  paramsNotVisible.add(params[i]);
               }
            } else {
               ArrayList elementsNotVisible;
               int j;
               if (params[i] instanceof ObjectName[]) {
                  ObjectName[] paramArray = (ObjectName[])((ObjectName[])params[i]);
                  elementsNotVisible = new ArrayList();

                  for(j = 0; j < paramArray.length; ++j) {
                     if (!this.visibleToPartitionsEnabledForGlobalMBeans(paramArray[j])) {
                        elementsNotVisible.add(paramArray[j]);
                     }
                  }

                  if (!elementsNotVisible.isEmpty()) {
                     paramsNotVisible.add(elementsNotVisible.toArray(new ObjectName[elementsNotVisible.size()]));
                  }
               } else if (params[i] instanceof ObjectInstance[]) {
                  ObjectInstance[] paramArray = (ObjectInstance[])((ObjectInstance[])params[i]);
                  elementsNotVisible = new ArrayList();

                  for(j = 0; j < paramArray.length; ++j) {
                     if (!this.visibleToPartitionsEnabledForGlobalMBeans(paramArray[j].getObjectName())) {
                        elementsNotVisible.add(paramArray[j]);
                     }
                  }

                  if (!elementsNotVisible.isEmpty()) {
                     paramsNotVisible.add(elementsNotVisible.toArray(new ObjectInstance[elementsNotVisible.size()]));
                  }
               }
            }
         }

         return paramsNotVisible.toArray(new Object[paramsNotVisible.size()]);
      }
   }

   private ObjectName getObjectNameForRegistration(ObjectName objectName) throws IOException, InstanceNotFoundException {
      String partitionName = this.findPartitionName();
      if (MBeanInfoBuilder.globalMBeansVisibleToPartitions && MBeanPartitionUtil.isReservedMBean(objectName)) {
         return objectName;
      } else {
         return !MBeanInfoBuilder.globalMBeansVisibleToPartitions && MBeanPartitionUtil.isReservedMBeanInRevisedModel(objectName) ? objectName : PartitionDecorator.addPartitionToObjectNameForRegistration(objectName, partitionName);
      }
   }

   private void checkVisibility(String partitionName, ObjectName mtObjectName, ObjectName objectName) throws InstanceNotFoundException {
      Set set = new HashSet();
      set.add(mtObjectName);
      Set result = this.filterPartitionMBeans(partitionName, set);
      if (result == null || result.size() == 0) {
         throw new InstanceNotFoundException(objectName + " is not found in " + partitionName);
      }
   }

   private Set filterPartitionMBeans(String partitionName, Set originalSet) {
      Set filteredSet = Collections.synchronizedSet(new HashSet());
      Iterator iter = originalSet.iterator();

      while(iter.hasNext()) {
         Object object = iter.next();
         ObjectName oname;
         if (object instanceof ObjectInstance) {
            oname = ((ObjectInstance)object).getObjectName();
            if (this.visibleToPartitionsEnabledForGlobalMBeans(oname)) {
               MBeanPartitionUtil.buildQueryResultSetForPartition(filteredSet, partitionName, object, oname);
            }
         } else if (object instanceof ObjectName) {
            oname = (ObjectName)object;
            if (this.visibleToPartitionsEnabledForGlobalMBeans(oname)) {
               MBeanPartitionUtil.buildQueryResultSetForPartition(filteredSet, partitionName, object, oname);
            }
         }
      }

      return filteredSet;
   }

   private QueryExp processQueryExp(QueryExp queryexp) {
      return queryexp;
   }

   private Object addPartitionToParam(Object param, String partitionName) throws IOException, InstanceNotFoundException {
      if (param == null) {
         return param;
      } else if (partitionName != null && !partitionName.equals("DOMAIN")) {
         if (param instanceof ObjectName) {
            ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName((ObjectName)param, partitionName);
            return super.isRegistered(partitionObjectName) ? partitionObjectName : param;
         } else {
            if (param instanceof Attribute) {
               Attribute attribute = (Attribute)param;
               Object value = attribute.getValue();
               if (value instanceof ObjectName) {
                  ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName((ObjectName)value, partitionName);
                  if (super.isRegistered(partitionObjectName)) {
                     return new Attribute(attribute.getName(), partitionObjectName);
                  }

                  return new Attribute(attribute.getName(), value);
               }

               if (value instanceof ObjectName[]) {
                  ObjectName[] paramArray = (ObjectName[])((ObjectName[])value);

                  for(int i = 0; i < paramArray.length; ++i) {
                     ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName(paramArray[i], partitionName);
                     if (super.isRegistered(partitionObjectName)) {
                        paramArray[i] = partitionObjectName;
                     } else {
                        paramArray[i] = paramArray[i];
                     }
                  }

                  return new Attribute(attribute.getName(), paramArray);
               }
            }

            return param;
         }
      } else {
         return param;
      }
   }

   private Object addPartitionToAttributeList(AttributeList object, String partitionName) throws IOException, InstanceNotFoundException {
      AttributeList newList = new AttributeList();
      if (object == null) {
         return object;
      } else if (partitionName != null && !partitionName.equals("DOMAIN")) {
         List attrs = object.asList();
         Iterator var5 = attrs.iterator();

         while(var5.hasNext()) {
            Attribute attr = (Attribute)var5.next();
            if (attr.getValue() instanceof ObjectName) {
               ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName((ObjectName)attr.getValue(), partitionName);
               if (super.isRegistered(partitionObjectName)) {
                  newList.add(new Attribute(attr.getName(), partitionObjectName));
               } else {
                  newList.add(new Attribute(attr.getName(), attr.getValue()));
               }
            } else {
               newList.add(attr);
            }
         }

         return newList;
      } else {
         return object;
      }
   }

   private Object addPartitionToObjectArray(Object[] object, String partitionName) throws IOException, InstanceNotFoundException {
      if (object == null) {
         return object;
      } else if (partitionName != null && !partitionName.equals("DOMAIN")) {
         Object[] newObject = new Object[object.length];

         for(int i = 0; i < object.length; ++i) {
            if (object[i] instanceof ObjectName) {
               ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName((ObjectName)object[i], partitionName);
               if (super.isRegistered(partitionObjectName)) {
                  newObject[i] = partitionObjectName;
               } else {
                  newObject[i] = object[i];
               }
            } else if (object[i] instanceof ObjectName[]) {
               ObjectName[] onames = (ObjectName[])((ObjectName[])object[i]);
               newObject[i] = new ObjectName[onames.length];

               for(int y = 0; y < onames.length; ++y) {
                  ObjectName partitionObjectName = PartitionDecorator.addPartitionToObjectName(onames[y], partitionName);
                  if (super.isRegistered(partitionObjectName)) {
                     ((Object[])((Object[])newObject[i]))[y] = partitionObjectName;
                  } else {
                     ((Object[])((Object[])newObject[i]))[y] = onames[y];
                  }
               }
            } else {
               newObject[i] = object[i];
            }
         }

         return newObject;
      } else {
         return object;
      }
   }

   private synchronized MBeanInfo getMBeanInfoForCache(ObjectName name) throws IntrospectionException, ReflectionException, InstanceNotFoundException, IOException {
      return super.getMBeanInfo(name);
   }

   private void invokeSuperClassMethod(ObjectName objectName, Notification notification, Object handback) throws UnsupportedOperationException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      super.invoke(objectName, "handleNotification", new Object[]{notification, handback}, new String[]{Notification.class.getName(), Object.class.getName()});
   }

   public String toString() {
      return "PartitionJMXInterceptor(" + this.name + "," + System.identityHashCode(this) + ")";
   }

   protected static String formatTrace(StackTraceElement[] stackTraceElements) {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] var2 = stackTraceElements;
      int var3 = stackTraceElements.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement st = var2[var4];
         sb.append("\t").append(st).append(System.lineSeparator());
      }

      return sb.toString();
   }

   @Service
   private static final class PartitionScrubber implements Scrubber {
      private final LinkedList clients = new LinkedList();

      private void registerPartitionInterceptor(PartitionJMXInterceptor registerMe) {
         this.clients.add(registerMe);
      }

      public void scrubADubDub() {
         if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
            PartitionJMXInterceptor.debug.debug("Notification:" + this + " Fired. Cache is starting to be cleared");
         }

         Iterator var1 = this.clients.iterator();

         while(var1.hasNext()) {
            PartitionJMXInterceptor cleanMe = (PartitionJMXInterceptor)var1.next();
            if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
               PartitionJMXInterceptor.debug.debug("Cleaning " + cleanMe);
               PartitionJMXInterceptor.debug.debug("mbeanInfoMap=" + cleanMe.mbeanInfoMap);
               PartitionJMXInterceptor.debug.debug("mbeanVisibilityMap=" + cleanMe.mbeanInfoMap);
               PartitionJMXInterceptor.debug.debug("mbeanAttrVisibilityMap=" + cleanMe.mbeanAttrVisibilityMap);
               PartitionJMXInterceptor.debug.debug("vtMBeanMap=" + cleanMe.vtMBeanMap);
               PartitionJMXInterceptor.debug.debug("coherenceMBeanMap=" + cleanMe.coherenceMBeanMap);
            }

            cleanMe.mbeanInfoMap.clear();
            cleanMe.mbeanVisibilityMap.clear();
            cleanMe.mbeanAttrVisibilityMap.clear();
            cleanMe.vtMBeanMap.clear();
            cleanMe.coherenceMBeanMap.clear();
         }

         if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
            PartitionJMXInterceptor.debug.debug("Notification:" + this + " Fired. Cache cleared");
         }

      }

      public String toString() {
         return "PartitionScrubber(" + System.identityHashCode(this) + ")";
      }
   }

   private static final class ObjectNameMBeanInfo {
      private final ObjectName objectName;
      private final MBeanInfo info;

      private ObjectNameMBeanInfo(ObjectName objectName, MBeanInfo info) {
         this.objectName = objectName;
         this.info = info;
      }

      public int hashCode() {
         return this.objectName.hashCode();
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            return !(o instanceof ObjectNameMBeanInfo) ? false : this.objectName.equals(((ObjectNameMBeanInfo)o).objectName);
         }
      }

      // $FF: synthetic method
      ObjectNameMBeanInfo(ObjectName x0, MBeanInfo x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class CoherenceComputable implements Computable {
      private CoherenceComputable() {
      }

      public Set compute(ObjectName key) throws ComputationErrorException {
         return MBeanPartitionUtil.getCoherenceMBeanPartitions(key);
      }

      // $FF: synthetic method
      CoherenceComputable(Object x0) {
         this();
      }
   }

   private static final class VTMBeanComputable implements Computable {
      private VTMBeanComputable() {
      }

      public Set compute(ObjectName key) throws ComputationErrorException {
         return MBeanPartitionUtil.getVTMBeanPartitions(key);
      }

      // $FF: synthetic method
      VTMBeanComputable(Object x0) {
         this();
      }
   }

   private static final class VisibilityComputable implements Computable {
      private VisibilityComputable() {
      }

      public MBeanVisibilityResult compute(ObjectNameMBeanInfo key) throws ComputationErrorException {
         return MBeanPartitionUtil.getMBeanVisibility(key.objectName, key.info);
      }

      // $FF: synthetic method
      VisibilityComputable(Object x0) {
         this();
      }
   }

   private static final class MBeanAttributeVisibilityComputable implements Computable {
      private final PartitionJMXInterceptor parent;

      private MBeanAttributeVisibilityComputable(PartitionJMXInterceptor parent) {
         this.parent = parent;
      }

      public Boolean compute(AttributeKey key) throws ComputationErrorException {
         try {
            return MBeanInfoBuilder.sureAttributeVisibleToPartitions(key.objectName, this.parent.getMBeanInfoForCache(key.objectName), key.attribute);
         } catch (Throwable var3) {
            if (var3 instanceof RuntimeException) {
               throw (RuntimeException)var3;
            } else {
               throw new RuntimeException(var3);
            }
         }
      }

      // $FF: synthetic method
      MBeanAttributeVisibilityComputable(PartitionJMXInterceptor x0, Object x1) {
         this(x0);
      }
   }

   private static final class MBeanInfoMapComputable implements Computable {
      private final PartitionJMXInterceptor parent;

      private MBeanInfoMapComputable(PartitionJMXInterceptor parent) {
         this.parent = parent;
      }

      public MBeanInfo compute(ObjectName key) throws ComputationErrorException {
         try {
            return this.parent.getMBeanInfoForCache(key);
         } catch (Throwable var3) {
            if (var3 instanceof RuntimeException) {
               throw (RuntimeException)var3;
            } else {
               throw new RuntimeException(var3);
            }
         }
      }

      // $FF: synthetic method
      MBeanInfoMapComputable(PartitionJMXInterceptor x0, Object x1) {
         this(x0);
      }
   }

   private class IntermediateNotificationListener implements NotificationListener {
      private NotificationListener listener = null;
      private String partition;
      private boolean isRemoteClient = false;
      private ObjectName listenerObjectName = null;

      public IntermediateNotificationListener(NotificationListener aListener) {
         this.listener = aListener;
         ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(PartitionJMXInterceptor.kernelId);
         ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
         if (BasicServerRef.isRemote()) {
            this.partition = "DOMAIN";
            this.isRemoteClient = true;
         } else {
            this.partition = cic != null ? cic.getPartitionName() : "DOMAIN";
         }

         if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
            PartitionJMXInterceptor.debug.debug("Notification: Listener registering in partition: " + this.partition);
         }

      }

      public IntermediateNotificationListener(ObjectName objectName) {
         this.listenerObjectName = objectName;
         this.isRemoteClient = false;
         ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(PartitionJMXInterceptor.kernelId);
         ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
         this.partition = cic != null ? cic.getPartitionName() : "DOMAIN";
         if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
            PartitionJMXInterceptor.debug.debug("Notification: Listener registering in partition: " + this.partition);
         }

      }

      private void fireEvent(Notification notification, Object handback) throws Exception {
         if (this.listener != null) {
            this.listener.handleNotification(notification, handback);
         }

         if (this.listenerObjectName != null) {
            PartitionJMXInterceptor.this.invokeSuperClassMethod(this.listenerObjectName, notification, handback);
         }

      }

      private void handleMBeanNotification(Notification notification, Object handback) throws Exception {
         MBeanServerNotification mBeanServerNotification = (MBeanServerNotification)notification;
         ObjectName objectName = mBeanServerNotification.getMBeanName();
         ObjectName name = MBeanNameUtil.removeLocation(objectName);
         if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
            PartitionJMXInterceptor.debug.debug("Notification:MBean Before = " + objectName + ", After = " + name);
         }

         MBeanInfo info = (MBeanInfo)PartitionJMXInterceptor.this.mbeanInfoMap.compute(name);
         if (!this.isRemoteClient && !this.partition.equals("DOMAIN")) {
            boolean isVisible = MBeanPartitionUtil.isMBeanVisibleToPartition(name, this.partition, info);
            if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
               PartitionJMXInterceptor.debug.debug("Notification: Listener = " + this.partition + ", ObjectName = " + name + ", Visible = " + isVisible);
            }

            if (isVisible) {
               this.fireEvent(notification, handback);
            }
         } else {
            MBeanVisibilityResult result = (MBeanVisibilityResult)PartitionJMXInterceptor.this.mbeanVisibilityMap.compute(new ObjectNameMBeanInfo(name, info));
            Set partitions;
            WLSCoherenceOrVTMBeanNotification wlsmBeanServerNotification;
            if (result.getMbeanType().equals(MBeanType.WLS_COHERENCE)) {
               partitions = (Set)PartitionJMXInterceptor.this.coherenceMBeanMap.compute(name);
               wlsmBeanServerNotification = new WLSCoherenceOrVTMBeanNotification(mBeanServerNotification.getType(), mBeanServerNotification.getSource(), mBeanServerNotification.getSequenceNumber(), mBeanServerNotification.getMBeanName(), result.getVisibility(), result.getMbeanType(), info, partitions);
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Listener = " + (this.isRemoteClient ? "Remote" : "DOMAIN") + ", Type = Coherence, ObjectName = " + name + ", Partitions = " + partitions);
               }

               this.fireEvent(wlsmBeanServerNotification, handback);
            } else if (result.getMbeanType().equals(MBeanType.WLS_VIRTUAL_TARGET)) {
               partitions = (Set)PartitionJMXInterceptor.this.vtMBeanMap.compute(name);
               wlsmBeanServerNotification = new WLSCoherenceOrVTMBeanNotification(mBeanServerNotification.getType(), mBeanServerNotification.getSource(), mBeanServerNotification.getSequenceNumber(), mBeanServerNotification.getMBeanName(), result.getVisibility(), result.getMbeanType(), info, partitions);
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Listener = " + (this.isRemoteClient ? "Remote" : "DOMAIN") + ", Type = VirtualTarget, ObjectName = " + name + ", Partitions = " + partitions);
               }

               this.fireEvent(wlsmBeanServerNotification, handback);
            } else {
               WLSMBeanNotification wlsmBeanNotification = new WLSMBeanNotification(mBeanServerNotification.getType(), mBeanServerNotification.getSource(), mBeanServerNotification.getSequenceNumber(), mBeanServerNotification.getMBeanName(), result.getVisibility(), result.getMbeanType(), info);
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Listener = " + (this.isRemoteClient ? "Remote" : "DOMAIN") + ", Type = " + result.getMbeanType() + ", ObjectName = " + name + ", Visibility = " + result.getVisibility());
               }

               this.fireEvent(wlsmBeanNotification, handback);
            }
         }

      }

      private void handleAttributeChangeNotification(Notification notification, Object handback) throws Exception {
         AttributeChangeNotification attrChangeNotification = (AttributeChangeNotification)notification;
         ObjectName objectName = (ObjectName)attrChangeNotification.getSource();
         ObjectName name = MBeanNameUtil.removeLocation(objectName);
         if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
            PartitionJMXInterceptor.debug.debug("Notification:AttributeChange: Before = " + objectName + ", After = " + name);
         }

         String attributeName = attrChangeNotification.getAttributeName();
         MBeanInfo info = (MBeanInfo)PartitionJMXInterceptor.this.mbeanInfoMap.compute(name);
         boolean isAttrVisibleToPartition;
         if (!this.isRemoteClient && !this.partition.equals("DOMAIN")) {
            boolean isVisible = MBeanPartitionUtil.isMBeanVisibleToPartition(name, this.partition, info);
            isAttrVisibleToPartition = false;
            if (isVisible) {
               isAttrVisibleToPartition = (Boolean)PartitionJMXInterceptor.this.mbeanAttrVisibilityMap.compute(PartitionJMXInterceptor.this.new AttributeKey(name, attributeName));
               if (isAttrVisibleToPartition) {
                  this.fireEvent(notification, handback);
               }
            }

            if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
               PartitionJMXInterceptor.debug.debug("Notification: Listener = " + this.partition + ", ObjectName = " + name + ", MBeanVisibility = " + isVisible + ", Attribute: " + attributeName + ", AttributeVisibility = " + isAttrVisibleToPartition);
            }
         } else {
            MBeanVisibilityResult visibility = (MBeanVisibilityResult)PartitionJMXInterceptor.this.mbeanVisibilityMap.compute(new ObjectNameMBeanInfo(name, info));
            isAttrVisibleToPartition = false;
            if (!visibility.getVisibility().equals(WLSMBeanVisibility.NONE)) {
               isAttrVisibleToPartition = (Boolean)PartitionJMXInterceptor.this.mbeanAttrVisibilityMap.compute(PartitionJMXInterceptor.this.new AttributeKey(name, attributeName));
            }

            Set partitions;
            WLSCoherenceOrVTMBeanAttributeChangeNotification wlsAttributeChangeNotification;
            if (visibility.getMbeanType().equals(MBeanType.WLS_COHERENCE)) {
               partitions = (Set)PartitionJMXInterceptor.this.coherenceMBeanMap.compute(name);
               wlsAttributeChangeNotification = new WLSCoherenceOrVTMBeanAttributeChangeNotification(attrChangeNotification.getSource(), attrChangeNotification.getSequenceNumber(), attrChangeNotification.getTimeStamp(), attrChangeNotification.getMessage(), attrChangeNotification.getAttributeName(), attrChangeNotification.getAttributeType(), attrChangeNotification.getOldValue(), attrChangeNotification.getNewValue(), visibility.getVisibility(), visibility.getMbeanType(), isAttrVisibleToPartition, info, partitions);
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Listener = " + (this.isRemoteClient ? "Remote" : "DOMAIN") + ", Type = Coherence, ObjectName = " + name + ", Partitions = " + partitions + ", Attribute: " + attributeName + ", AttributeVisibility = " + isAttrVisibleToPartition);
               }

               this.fireEvent(wlsAttributeChangeNotification, handback);
            } else if (visibility.getMbeanType().equals(MBeanType.WLS_VIRTUAL_TARGET)) {
               partitions = (Set)PartitionJMXInterceptor.this.vtMBeanMap.compute(name);
               wlsAttributeChangeNotification = new WLSCoherenceOrVTMBeanAttributeChangeNotification(attrChangeNotification.getSource(), attrChangeNotification.getSequenceNumber(), attrChangeNotification.getTimeStamp(), attrChangeNotification.getMessage(), attrChangeNotification.getAttributeName(), attrChangeNotification.getAttributeType(), attrChangeNotification.getOldValue(), attrChangeNotification.getNewValue(), visibility.getVisibility(), visibility.getMbeanType(), isAttrVisibleToPartition, info, partitions);
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Listener = " + (this.isRemoteClient ? "Remote" : "DOMAIN") + ", Type = VT, ObjectName = " + name + ", Partitions = " + partitions + ", Attribute: " + attributeName + ", AttributeVisibility = " + isAttrVisibleToPartition);
               }

               this.fireEvent(wlsAttributeChangeNotification, handback);
            } else {
               WLSMBeanAttributeChangeNotification wlsAttributeChangeNotificationx = new WLSMBeanAttributeChangeNotification(attrChangeNotification.getSource(), attrChangeNotification.getSequenceNumber(), attrChangeNotification.getTimeStamp(), attrChangeNotification.getMessage(), attrChangeNotification.getAttributeName(), attrChangeNotification.getAttributeType(), attrChangeNotification.getOldValue(), attrChangeNotification.getNewValue(), visibility.getVisibility(), visibility.getMbeanType(), info, isAttrVisibleToPartition);
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Listener = " + (this.isRemoteClient ? "Remote" : "DOMAIN") + ", Type = " + visibility.getMbeanType() + ", ObjectName = " + name + ", Visibility = " + visibility.getVisibility() + ", Attribute: " + attributeName + ", AttributeVisibility = " + isAttrVisibleToPartition);
               }

               this.fireEvent(wlsAttributeChangeNotificationx, handback);
            }
         }

      }

      public void handleNotification(Notification notification, Object handback) {
         try {
            if (notification instanceof WLSMBeanNotification) {
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Skipped MBean Processing. " + notification);
               }

               this.fireEvent(notification, handback);
            } else if (notification instanceof MBeanServerNotification) {
               this.handleMBeanNotification(notification, handback);
            } else if (notification instanceof WLSMBeanAttributeChangeNotification) {
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification: Skipped Attribute Processing. " + notification);
               }

               this.fireEvent(notification, handback);
            } else if (notification instanceof AttributeChangeNotification) {
               this.handleAttributeChangeNotification(notification, handback);
            } else if (!this.isRemoteClient && !this.partition.equals("DOMAIN")) {
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification:Skipped: Reason = Unknown type, Type = " + notification.getClass() + ". " + notification);
               }
            } else {
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification:Skipped: Reason = Unknown type, Type = " + notification.getClass() + ". Firing the event to remote clients and Domain listeners Anyway. " + notification);
               }

               this.fireEvent(notification, handback);
            }
         } catch (Exception var6) {
            if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
               PartitionJMXInterceptor.debug.debug("Notification:Exception: Occurred during handling of " + notification + "\n" + PartitionJMXInterceptor.formatTrace(var6.getStackTrace()));
            }

            if (this.isRemoteClient || this.partition.equals("DOMAIN")) {
               if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                  PartitionJMXInterceptor.debug.debug("Notification:Exception: Still firing the event to remote client and Domain Context listener. " + notification);
               }

               try {
                  this.fireEvent(notification, handback);
               } catch (Exception var5) {
                  if (PartitionJMXInterceptor.debug.isDebugEnabled()) {
                     PartitionJMXInterceptor.debug.debug("Notification:Exception: Occurred on the second try during handling of " + notification + "\n" + PartitionJMXInterceptor.formatTrace(var6.getStackTrace()));
                  }
               }
            }
         }

      }
   }

   private class AttributeKey {
      private ObjectName objectName;
      private String attribute;

      public AttributeKey(ObjectName name, String attribute) {
         this.objectName = name;
         this.attribute = attribute;
      }

      public int hashCode() {
         int result = this.objectName != null ? this.objectName.hashCode() : 0;
         result = 31 * result + (this.attribute != null ? this.attribute.hashCode() : 0);
         return result;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof AttributeKey)) {
            return false;
         } else {
            AttributeKey that = (AttributeKey)o;
            if (this.attribute != null) {
               if (!this.attribute.equals(that.attribute)) {
                  return false;
               }
            } else if (that.attribute != null) {
               return false;
            }

            if (this.objectName != null) {
               if (!this.objectName.equals(that.objectName)) {
                  return false;
               }
            } else if (that.objectName != null) {
               return false;
            }

            return true;
         }
      }
   }
}
