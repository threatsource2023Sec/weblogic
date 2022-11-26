package weblogic.management.jmx.modelmbean;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeChangeNotificationFilter;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.ServiceNotFoundException;
import javax.management.loading.ClassLoaderRepository;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.openmbean.OpenType;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.PrimitiveMapper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.LocatorUtilities;

public class WLSModelMBean implements ModelMBean, MBeanRegistration, NotificationEmitter, NotificationGenerator {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   private static final Class[] CHANGELISTENER_PARAMS = new Class[]{PropertyChangeListener.class};
   private static final Object[] EMPTY_ARGS = new Object[0];
   ModelMBeanInfoWrapper modelMBeanInfoWrapper;
   private NotificationBroadcasterSupport generalBroadcaster = null;
   private NotificationBroadcasterSupport attributeBroadcaster = null;
   private long notificationSequenceNumber = 0L;
   protected Object managedResource = null;
   private transient MBeanServer server = null;
   private ObjectName objectName = null;
   private WLSModelMBeanContext context;
   private int listenerCounter = 0;
   private final ArrayList changeListeners = new ArrayList();

   public WLSModelMBean(Object wlsBean, WLSModelMBeanContext context) throws OperationsException {
      if (!context.isReadOnly()) {
         MutabilityManager mutabilityManager = (MutabilityManager)LocatorUtilities.getService(MutabilityManager.class);
         if (mutabilityManager != null && mutabilityManager.isImmutableSubtreeRoot(wlsBean)) {
            context = context.clone();
            context.setReadOnly(true);
         }
      }

      this.context = context;
      this.managedResource = wlsBean;
      this.modelMBeanInfoWrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInstance(wlsBean, context.isReadOnly(), context.getVersion(), context.getNameManager());
   }

   private void bindNotifications() {
      PropertyChangeListener[] initialPCLs = this.listPropertyChangeListeners();
      NotificationTranslatorBuilder.instantiateNotificationTranslator(this.context, this.managedResource, this);
      BeanDescriptor beanDescriptor = this.modelMBeanInfoWrapper.getBeanInfo().getBeanDescriptor();
      String notificationTranslator = (String)beanDescriptor.getValue("notificationTranslator");
      if (notificationTranslator != null) {
         NotificationTranslatorBuilder.instantiateNotificationTranslator(notificationTranslator, this.context, this.managedResource, this);
      }

      PropertyChangeListener[] afterSpiPCLs = this.listPropertyChangeListeners();
      ArrayUtils.computeDiff(initialPCLs, afterSpiPCLs, new ArrayUtils.DiffHandler() {
         public void addObject(PropertyChangeListener added) {
            WLSModelMBean.this.changeListeners.add(added);
         }

         public void removeObject(PropertyChangeListener removed) {
         }
      }, new Comparator() {
         public int compare(PropertyChangeListener o1, PropertyChangeListener o2) {
            return o1 == o2 ? 0 : 1;
         }
      });

      try {
         Method addChangeListener = this.managedResource.getClass().getMethod("addPropertyChangeListener", CHANGELISTENER_PARAMS);
         PropertyChangeListener pChangeListener = this.createPropertyChangeListener();
         addChangeListener.invoke(this.managedResource, pChangeListener);
         this.changeListeners.add(pChangeListener);
      } catch (NoSuchMethodException var7) {
      } catch (IllegalAccessException var8) {
         throw new Error(var8);
      } catch (InvocationTargetException var9) {
         throw new Error(var9.getTargetException());
      }

   }

   private PropertyChangeListener[] listPropertyChangeListeners() {
      try {
         Method listChangeListener = this.managedResource.getClass().getMethod("listPropertyChangeListeners");
         PropertyChangeListener[] result = (PropertyChangeListener[])((PropertyChangeListener[])listChangeListener.invoke(this.managedResource));
         return result == null ? new PropertyChangeListener[0] : result;
      } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var3) {
         return new PropertyChangeListener[0];
      }
   }

   private PropertyChangeListener createPropertyChangeListener() {
      return new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            Object oldValue = evt.getOldValue();
            Object newValue = evt.getNewValue();
            String propertyName = evt.getPropertyName();
            PropertyDescriptor descriptor = WLSModelMBean.this.modelMBeanInfoWrapper.getPropertyDescriptor(propertyName);
            if (descriptor == null) {
               if (WLSModelMBean.debug.isDebugEnabled()) {
                  WLSModelMBean.debug.debug("WLSModelMBean: Unable to process property change event for the property: " + propertyName);
               }

            } else {
               Class propertyClass = descriptor.getPropertyType();
               if (WLSModelMBean.this.context.isContainedBean(descriptor)) {
                  if (propertyClass.isArray() && oldValue instanceof Object[] && newValue instanceof Object[]) {
                     ArrayUtils.computeDiff((Object[])((Object[])oldValue), (Object[])((Object[])newValue), new ArrayUtils.DiffHandler() {
                        public void addObject(Object added) {
                           WLSModelMBeanFactory.registerWLSModelMBean(added, WLSModelMBean.this.context);
                        }

                        public void removeObject(Object removed) {
                           WLSModelMBeanFactory.unregisterWLSModelMBean(removed, WLSModelMBean.this.context);
                        }
                     });
                  } else {
                     if (oldValue == null && newValue != null) {
                        WLSModelMBeanFactory.registerWLSModelMBean(newValue, WLSModelMBean.this.context);
                     }

                     if (oldValue != null && newValue == null) {
                        WLSModelMBeanFactory.unregisterWLSModelMBean(oldValue, WLSModelMBean.this.context);
                     }
                  }
               }

            }
         }
      };
   }

   private void unbindNotifications() {
      try {
         Method removeChangeListener = this.managedResource.getClass().getMethod("removePropertyChangeListener", CHANGELISTENER_PARAMS);
         Iterator var2 = this.changeListeners.iterator();

         while(var2.hasNext()) {
            PropertyChangeListener changeListener = (PropertyChangeListener)var2.next();
            removeChangeListener.invoke(this.managedResource, changeListener);
         }

         this.changeListeners.clear();
      } catch (NoSuchMethodException var4) {
      } catch (IllegalAccessException var5) {
         throw new Error(var5);
      } catch (InvocationTargetException var6) {
         throw new Error(var6.getTargetException());
      }

   }

   public ObjectName getObjectName() {
      return this.objectName;
   }

   public boolean isSubscribed() {
      return this.listenerCounter != 0 && this.objectName != null;
   }

   public synchronized long incrementSequenceNumber() {
      return (long)(this.notificationSequenceNumber++);
   }

   public void setModelMBeanInfo(ModelMBeanInfo mbi) throws MBeanException, RuntimeOperationsException {
      throw new MBeanException(new Exception("NOT SUPPORTED"));
   }

   public void setManagedResource(Object mr, String mr_type) throws MBeanException, RuntimeOperationsException, InstanceNotFoundException, InvalidTargetObjectTypeException {
      throw new MBeanException(new Exception("NOT SUPPORTED"));
   }

   public void load() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException {
   }

   public void store() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException {
      ServiceNotFoundException x = new ServiceNotFoundException("Persistence not supported for this MBean");
      throw new MBeanException(x, x.getMessage());
   }

   public MBeanInfo getMBeanInfo() {
      return (MBeanInfo)this.modelMBeanInfoWrapper.getModelMBeanInfo().clone();
   }

   public Object invoke(String opName, Object[] opArgs, String[] sig) throws MBeanException, ReflectionException {
      if (opName == null) {
         RuntimeException x = new IllegalArgumentException("Method name must not be null");
         throw new RuntimeOperationsException(x, "An exception occured while trying to invoke a method on a ModelMBean");
      } else {
         Object response = null;
         String opMethodName = this.getMethodName(opName);
         if (debug.isDebugEnabled()) {
            debug.debug("Invoking method " + opMethodName + " with " + this.signatureToString(sig));
         }

         ModelMBeanInfoWrapper.OperationData operationData = this.modelMBeanInfoWrapper.getOperationData(opMethodName, sig);
         if (operationData == null) {
            boolean retry = false;

            for(int i = 0; sig != null && i < sig.length; ++i) {
               Class wrapperClass = PrimitiveMapper.lookupWrapperClass(sig[i]);
               if (wrapperClass != null) {
                  retry = true;
                  sig[i] = wrapperClass.getName();
               }
            }

            if (retry) {
               operationData = this.modelMBeanInfoWrapper.getOperationData(opMethodName, sig);
            }
         }

         if (operationData == null) {
            throw new ReflectionException(new NoSuchMethodException("Cannot find the method " + opMethodName + this.signatureToString(sig) + " for " + this.objectName));
         } else {
            MethodDescriptor methodDescriptor = operationData.descriptor;
            if (methodDescriptor == null) {
               throw new ReflectionException(new NoSuchMethodException("Cannot find the method " + opMethodName + this.signatureToString(sig) + " for " + this.objectName));
            } else {
               Method beanMethod = methodDescriptor.getMethod();
               Class[] beanParams = beanMethod.getParameterTypes();
               Object[] beanArgs;
               if (opArgs != null && opArgs.length != 0) {
                  beanArgs = new Object[opArgs.length];

                  try {
                     for(int i = 0; i < opArgs.length; ++i) {
                        if (this.context.getNameManager() == null) {
                           beanArgs[i] = opArgs[i];
                        } else {
                           String jmxArgumentType = sig[i];
                           Class signatureClass = this.loadClass(jmxArgumentType);
                           Class beanArgumentClass = beanParams[i];
                           beanArgs[i] = this.context.mapFromJMX(signatureClass, beanArgumentClass, opArgs[i]);
                        }
                     }
                  } catch (ClassNotFoundException var19) {
                     throw new ReflectionException(var19, "The parameter class could not be found");
                  }
               } else {
                  beanArgs = EMPTY_ARGS;
               }

               try {
                  response = beanMethod.invoke(this.managedResource, beanArgs);
               } catch (IllegalAccessException var16) {
                  throw new MBeanException(var16, "MBean invoke failed: " + var16);
               } catch (IllegalArgumentException var17) {
                  throw var17;
               } catch (InvocationTargetException var18) {
                  Throwable mmbTargEx = var18.getTargetException();
                  if (mmbTargEx instanceof RuntimeException) {
                     throw (RuntimeException)mmbTargEx;
                  }

                  if (mmbTargEx instanceof MBeanException) {
                     throw (MBeanException)mmbTargEx;
                  }

                  if (mmbTargEx instanceof Exception) {
                     throw new MBeanException((Exception)mmbTargEx, "MBean invoke failed: " + mmbTargEx);
                  }

                  throw (Error)mmbTargEx;
               }

               Class responseClass = null;
               ModelMBeanOperationInfo operInfo = operationData.info;
               OpenType openType = (OpenType)operInfo.getDescriptor().getFieldValue("openType");

               try {
                  String operReturnType;
                  if (openType == null) {
                     operReturnType = operInfo.getReturnType();
                  } else {
                     operReturnType = openType.getTypeName();
                  }

                  responseClass = this.loadClass(operReturnType);
               } catch (ClassNotFoundException var15) {
               }

               if (responseClass == null) {
                  responseClass = beanMethod.getReturnType();
               }

               response = this.context.mapToJMX(responseClass, response, openType);
               return response;
            }
         }
      }
   }

   public Object getAttribute(String attrName) throws AttributeNotFoundException, MBeanException, ReflectionException {
      if (attrName == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("attributeName must not be null or \"\""), "Exception occured trying to get attribute of a ModelMBean");
      } else {
         PropertyDescriptor propertyDescriptor = this.modelMBeanInfoWrapper.getPropertyDescriptor(attrName);
         ModelMBeanAttributeInfo attrInfo = this.modelMBeanInfoWrapper.getModelMBeanInfo().getAttribute(attrName);
         if (propertyDescriptor == null) {
            throw new AttributeNotFoundException(this.objectName + ":" + attrName);
         } else {
            Method attrGetMethod = propertyDescriptor.getReadMethod();
            Object response = null;

            try {
               response = attrGetMethod.invoke(this.managedResource, EMPTY_ARGS);
               OpenType openType = (OpenType)attrInfo.getDescriptor().getFieldValue("openType");
               Class responseClass = null;

               try {
                  String attributeReturnType;
                  if (openType != null) {
                     attributeReturnType = openType.getTypeName();
                  } else {
                     attributeReturnType = attrInfo.getType();
                  }

                  responseClass = this.loadClass(attributeReturnType);
               } catch (ClassNotFoundException var9) {
               }

               if (responseClass == null) {
                  responseClass = attrGetMethod.getReturnType();
               }

               response = this.context.mapToJMX(responseClass, response, openType);
               return response;
            } catch (IllegalAccessException var10) {
               throw new MBeanException(var10, "MBean getAttribute failed: " + var10);
            } catch (IllegalArgumentException var11) {
               throw new RuntimeMBeanException(var11, "MBean getAttribute failed: " + var11);
            } catch (InvocationTargetException var12) {
               Throwable mmbTargEx = var12.getTargetException();
               if (mmbTargEx instanceof RuntimeException) {
                  throw new RuntimeMBeanException((RuntimeException)mmbTargEx, "MBean getAttribute failed: " + mmbTargEx);
               } else if (mmbTargEx instanceof Exception) {
                  throw new MBeanException((Exception)mmbTargEx, "MBean getAttribute failed: " + mmbTargEx);
               } else {
                  throw new RuntimeErrorException((Error)mmbTargEx, "MBean getAttribute failed: " + mmbTargEx);
               }
            }
         }
      }
   }

   public AttributeList getAttributes(String[] attrNames) {
      AttributeList responseList = null;
      if (attrNames == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames must not be null"), "Exception occured trying to get attributes of a ModelMBean");
      } else {
         responseList = new AttributeList();

         for(int i = 0; i < attrNames.length; ++i) {
            try {
               responseList.add(new Attribute(attrNames[i], this.getAttribute(attrNames[i])));
            } catch (Exception var5) {
               if (debug.isDebugEnabled()) {
                  JMXLogger.logErrorDuringGetAttributes(this.getObjectName().toString(), attrNames[i]);
                  debug.debug("getAttribute exception is:", var5);
               }
            }
         }

         return responseList;
      }
   }

   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      if (attribute == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("attribute must not be null"), "Exception occured trying to set an attribute of a ModelMBean");
      } else {
         String attrName = attribute.getName();
         Object attrValue = attribute.getValue();
         this.getAttribute(attrName);

         try {
            PropertyDescriptor propertyDescriptor = this.modelMBeanInfoWrapper.getPropertyDescriptor(attrName);
            if (propertyDescriptor == null) {
               throw new AttributeNotFoundException(this.objectName + ":" + attrName);
            } else {
               Class propertyClass = propertyDescriptor.getPropertyType();
               Object beanAttribute = null;
               if (attrValue != null) {
                  beanAttribute = this.context.mapFromJMX(attrValue.getClass(), propertyClass, attrValue);
               }

               Method attrSetMethod = propertyDescriptor.getWriteMethod();
               if (attrSetMethod == null) {
                  throw new AttributeNotFoundException("Attribute is readonly. : " + this.objectName + ":" + attrName);
               } else {
                  attrSetMethod.invoke(this.managedResource, beanAttribute);
               }
            }
         } catch (IllegalAccessException var8) {
            throw new MBeanException(var8);
         } catch (IllegalArgumentException var9) {
            InvalidAttributeValueException toThrow = new InvalidAttributeValueException(var9.getMessage());
            toThrow.initCause(var9);
            throw toThrow;
         } catch (InvocationTargetException var10) {
            Throwable mmbTargEx = var10.getTargetException();
            if (mmbTargEx instanceof IllegalArgumentException) {
               InvalidAttributeValueException toThrow = new InvalidAttributeValueException(mmbTargEx.getMessage());
               toThrow.initCause(mmbTargEx);
               throw toThrow;
            } else if (mmbTargEx instanceof RuntimeException) {
               throw new RuntimeMBeanException((RuntimeException)mmbTargEx);
            } else if (mmbTargEx instanceof Exception) {
               throw new MBeanException((Exception)mmbTargEx);
            } else {
               throw new RuntimeErrorException((Error)mmbTargEx);
            }
         }
      }
   }

   public AttributeList setAttributes(AttributeList attributes) {
      if (attributes == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("attributes must not be null"), "Exception occured trying to set attributes of a ModelMBean");
      } else {
         AttributeList responseList = new AttributeList();
         Iterator i = attributes.iterator();

         while(i.hasNext()) {
            Attribute attr = (Attribute)i.next();

            try {
               this.setAttribute(attr);
               responseList.add(attr);
            } catch (Exception var6) {
               responseList.remove(attr);
               JMXLogger.logErrorDuringSetAttributes(this.getObjectName().toString(), attr.getName());
            }
         }

         return responseList;
      }
   }

   public synchronized void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
      if (listener == null) {
         throw new IllegalArgumentException("notification listener must not be null");
      } else {
         if (this.generalBroadcaster == null) {
            this.generalBroadcaster = new NotificationBroadcasterSupport();
         }

         this.generalBroadcaster.addNotificationListener(listener, filter, handback);
         ++this.listenerCounter;
      }
   }

   public synchronized void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      if (listener == null) {
         throw new ListenerNotFoundException("Notification listener is null");
      } else if (this.generalBroadcaster == null) {
         throw new ListenerNotFoundException("No notification listeners registered");
      } else {
         this.generalBroadcaster.removeNotificationListener(listener);
         --this.listenerCounter;
      }
   }

   public synchronized void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {
      if (listener == null) {
         throw new ListenerNotFoundException("Notification listener is null");
      } else if (this.generalBroadcaster == null) {
         throw new ListenerNotFoundException("No notification listeners registered");
      } else {
         this.generalBroadcaster.removeNotificationListener(listener, filter, handback);
         --this.listenerCounter;
      }
   }

   public void sendNotification(Notification ntfyObj) throws MBeanException, RuntimeOperationsException {
      if (this.isSubscribed()) {
         if (ntfyObj == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("notification object must not be null"), "Exception occured trying to send a notification from a ModelMBean");
         } else {
            if (ntfyObj instanceof AttributeChangeNotification && this.attributeBroadcaster != null) {
               this.attributeBroadcaster.sendNotification(ntfyObj);
            }

            if (this.generalBroadcaster != null) {
               this.generalBroadcaster.sendNotification(ntfyObj);
            }

         }
      }
   }

   public void sendNotification(String ntfyText) throws MBeanException, RuntimeOperationsException {
      if (ntfyText == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("notification message must not be null"), "Exception occured trying to send a text notification from a ModelMBean");
      } else {
         Notification myNtfyObj = new Notification("jmx.modelmbean.generic", this.getObjectName(), this.incrementSequenceNumber(), ntfyText);
         this.sendNotification(myNtfyObj);
      }
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      boolean hasGeneric = this.hasNotification("GENERIC");
      boolean hasAttributeChange = this.hasNotification("ATTRIBUTE_CHANGE");
      ModelMBeanNotificationInfo[] currInfo = (ModelMBeanNotificationInfo[])((ModelMBeanNotificationInfo[])this.modelMBeanInfoWrapper.getModelMBeanInfo().getNotifications());
      int len = (currInfo == null ? 0 : currInfo.length) + (hasGeneric ? 0 : 1) + (hasAttributeChange ? 0 : 1);
      ModelMBeanNotificationInfo[] respInfo = new ModelMBeanNotificationInfo[len];
      int inserted = 0;
      if (!hasGeneric) {
         respInfo[inserted++] = makeGenericNotificationInfo();
      }

      if (!hasAttributeChange) {
         respInfo[inserted++] = this.makeAttributeChangeInfo();
      }

      int count = currInfo == null ? 0 : currInfo.length;
      int offset = inserted;

      for(int j = 0; j < count; ++j) {
         respInfo[offset + j] = currInfo[j];
      }

      return respInfo;
   }

   public synchronized void addAttributeChangeNotificationListener(NotificationListener inListener, String inAttributeName, Object inHandback) throws MBeanException, RuntimeOperationsException, IllegalArgumentException {
      if (inListener == null) {
         throw new IllegalArgumentException("Listener to be registered must not be null");
      } else {
         if (this.attributeBroadcaster == null) {
            this.attributeBroadcaster = new NotificationBroadcasterSupport();
         }

         AttributeChangeNotificationFilter currFilter = new AttributeChangeNotificationFilter();
         MBeanAttributeInfo[] attrInfo = this.modelMBeanInfoWrapper.getModelMBeanInfo().getAttributes();

         int index;
         for(index = 0; index < attrInfo.length; ++index) {
            if (inAttributeName == null) {
               currFilter.enableAttribute(attrInfo[index].getName());
            } else if (inAttributeName.equals(attrInfo[index].getName())) {
               currFilter.enableAttribute(inAttributeName);
               break;
            }
         }

         if (inAttributeName != null && index == attrInfo.length) {
            throw new RuntimeOperationsException(new IllegalArgumentException("The attribute name does not exist: " + inAttributeName), "Exception occured trying to add an AttributeChangeNotification listener");
         } else {
            if (inAttributeName != null) {
               try {
                  BeanDescriptor beanDescriptor = this.modelMBeanInfoWrapper.getBeanInfo().getBeanDescriptor();
                  if (this.context.getSecurityManager() != null) {
                     this.context.getSecurityManager().isAccessAllowed(this.getObjectName(), inAttributeName, "addAttributeChangeNotificationListener", beanDescriptor, this.getPropertyDescriptorForAttribute(inAttributeName));
                  }
               } catch (RuntimeException var8) {
                  throw new RuntimeOperationsException(var8);
               } catch (AttributeNotFoundException var9) {
                  throw new RuntimeOperationsException(new IllegalArgumentException(var9));
               }
            }

            this.attributeBroadcaster.addNotificationListener(inListener, currFilter, inHandback);
            ++this.listenerCounter;
         }
      }
   }

   public synchronized void removeAttributeChangeNotificationListener(NotificationListener inlistener, String inAttributeName) throws MBeanException, RuntimeOperationsException, ListenerNotFoundException {
      if (inlistener == null) {
         throw new ListenerNotFoundException("Notification listener is null");
      } else if (this.attributeBroadcaster == null) {
         throw new ListenerNotFoundException("No attribute change notification listeners registered");
      } else {
         if (inAttributeName != null) {
            MBeanAttributeInfo[] attrInfo = this.modelMBeanInfoWrapper.getModelMBeanInfo().getAttributes();
            int index = 0;
            if (attrInfo != null) {
               while(index < attrInfo.length && !attrInfo[index].getName().equals(inAttributeName)) {
                  ++index;
               }

               if (index == attrInfo.length) {
                  throw new RuntimeOperationsException(new IllegalArgumentException("Invalid attribute name"), "Exception occured trying to remove attribute change notification listener");
               }
            }
         }

         this.attributeBroadcaster.removeNotificationListener(inlistener);
         --this.listenerCounter;
      }
   }

   public void sendAttributeChangeNotification(AttributeChangeNotification ntfyObj) throws MBeanException, RuntimeOperationsException {
      this.sendNotification((Notification)ntfyObj);
   }

   public void sendAttributeChangeNotification(Attribute inOldVal, Attribute inNewVal) throws MBeanException, RuntimeOperationsException {
      if (inOldVal != null && inNewVal != null) {
         String newName = inNewVal.getName();
         String oldName = inOldVal.getName();
         Object newVal = inNewVal.getValue();
         Object oldVal = inOldVal.getValue();
         if (!oldName.equals(newName)) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute names are not the same"), "Exception occured trying to send attribute change notification of a ModelMBean");
         } else {
            String className = "unknown";
            if (newVal != null) {
               className = newVal.getClass().getName();
            }

            if (oldVal != null) {
               className = oldVal.getClass().getName();
            }

            AttributeChangeNotification myNtfyObj = new AttributeChangeNotification(this.getObjectName(), this.incrementSequenceNumber(), (new Date()).getTime(), "AttributeChangeDetected", oldName, className, oldVal, newVal);
            this.sendNotification((Notification)myNtfyObj);
         }
      } else {
         throw new RuntimeOperationsException(new IllegalArgumentException("Attribute object must not be null"), "Exception occured trying to send attribute change notification of a ModelMBean");
      }
   }

   public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
      if (name == null) {
         throw new NullPointerException("name of ModelMBean to registered is null");
      } else {
         this.server = server;
         this.objectName = name;
         this.bindNotifications();
         return name;
      }
   }

   public void postRegister(Boolean registrationDone) {
   }

   public void preDeregister() throws Exception {
      this.unbindNotifications();
   }

   public void postDeregister() {
      this.server = null;
      this.objectName = null;
      this.generalBroadcaster = null;
      this.attributeBroadcaster = null;
   }

   private static final ModelMBeanNotificationInfo makeGenericNotificationInfo() {
      Descriptor genericDescriptor = new DescriptorSupport(new String[]{"name=GENERIC", "descriptorType=notification", "log=F", "severity=5", "displayName=jmx.modelmbean.generic"});
      return new ModelMBeanNotificationInfo(new String[]{"jmx.modelmbean.generic"}, "GENERIC", "A text notification has been issued by the managed resource", genericDescriptor);
   }

   ModelMBeanNotificationInfo makeAttributeChangeInfo() {
      Descriptor attributeDescriptor = new DescriptorSupport(new String[]{"name=ATTRIBUTE_CHANGE", "descriptorType=notification", "log=F", "severity=5", "displayName=jmx.attribute.change"});
      return new ModelMBeanNotificationInfo(new String[]{"jmx.attribute.change"}, "ATTRIBUTE_CHANGE", "Signifies that an observed MBean attribute value has changed", attributeDescriptor);
   }

   private final boolean hasNotification(String notifName) {
      try {
         if (this.modelMBeanInfoWrapper.getModelMBeanInfo() == null) {
            return false;
         } else {
            return this.modelMBeanInfoWrapper.getModelMBeanInfo().getNotification(notifName) != null;
         }
      } catch (MBeanException var3) {
         return false;
      } catch (RuntimeOperationsException var4) {
         return false;
      }
   }

   private Class loadClass(String className) throws ClassNotFoundException {
      try {
         Class result = PrimitiveMapper.lookupClass(className);
         return result != null ? result : Class.forName(className);
      } catch (ClassNotFoundException var4) {
         ClassLoaderRepository clr = MBeanServerFactory.getClassLoaderRepository(this.server);
         if (clr == null) {
            throw new ClassNotFoundException(className);
         } else {
            return clr.loadClass(className);
         }
      }
   }

   public BeanInfo getBeanInfo() {
      return this.modelMBeanInfoWrapper.getBeanInfo();
   }

   private String signatureToString(String[] sig) {
      if (sig != null && sig.length != 0) {
         StringBuffer result = new StringBuffer();
         result.append("(");

         for(int i = 0; i < sig.length; ++i) {
            String s = sig[i];
            result.append(s);
            if (i < sig.length) {
               result.append(",");
            }
         }

         result.append(")");
         return result.toString();
      } else {
         return "()";
      }
   }

   private String getMethodName(String opName) {
      String opMethodName = opName;
      int opSplitter = opName.lastIndexOf(".");
      if (opSplitter > 0) {
         opMethodName = opName.substring(opSplitter + 1);
      }

      opSplitter = opMethodName.indexOf("(");
      if (opSplitter > 0) {
         opMethodName = opMethodName.substring(0, opSplitter);
      }

      return opMethodName;
   }

   public String getRole(String opName, Object[] opArgs, String[] sig) {
      String opMethodName = this.getMethodName(opName);
      MethodDescriptor methodDescriptor = this.modelMBeanInfoWrapper.getMethodDescriptor(opMethodName, sig);
      return methodDescriptor == null ? null : (String)methodDescriptor.getValue("role");
   }

   public Method getMethod(String opName, String[] sig) {
      String opMethodName = this.getMethodName(opName);
      MethodDescriptor methodDescriptor = this.modelMBeanInfoWrapper.getMethodDescriptor(opMethodName, sig);
      return methodDescriptor == null ? null : methodDescriptor.getMethod();
   }

   public String getImpact(String opName, Object[] opArgs, String[] sig) {
      String opMethodName = this.getMethodName(opName);
      MethodDescriptor methodDescriptor = this.modelMBeanInfoWrapper.getMethodDescriptor(opMethodName, sig);
      return methodDescriptor == null ? null : (String)methodDescriptor.getValue("impact");
   }

   public Class getManagedResourceClass() {
      return this.managedResource.getClass();
   }

   public PropertyDescriptor getPropertyDescriptorForAttribute(String attrName) throws AttributeNotFoundException {
      if (attrName == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("attributeName must not be null or \"\""), "Exception occured trying to get attribute of a ModelMBean");
      } else {
         PropertyDescriptor propertyDescriptor = this.modelMBeanInfoWrapper.getPropertyDescriptor(attrName);
         if (propertyDescriptor == null) {
            throw new AttributeNotFoundException(this.objectName + ":" + attrName);
         } else {
            return propertyDescriptor;
         }
      }
   }

   public Method getSetterMethod(String name) {
      PropertyDescriptor descriptor = this.modelMBeanInfoWrapper.getPropertyDescriptor(name);
      return descriptor != null && descriptor.getWriteMethod() != null ? descriptor.getWriteMethod() : null;
   }

   public MethodDescriptor getMethodDescriptor(String method, String[] sig) {
      return this.modelMBeanInfoWrapper.getMethodDescriptor(method, sig);
   }

   ModelMBeanInfoWrapper getModelMBeanInfoWrapper() {
      return this.modelMBeanInfoWrapper;
   }

   WLSModelMBeanContext getContext() {
      return this.context;
   }

   private static class NotificationListenerKey {
      private NotificationListener listener;
      private NotificationFilter filter;
      private Object handback;

      public NotificationListenerKey(NotificationListener listener, NotificationFilter filter, Object handback) {
         this.listener = listener;
         this.filter = filter;
         this.handback = handback;
      }

      public boolean equals(Object o) {
         if (!(o instanceof NotificationListenerKey)) {
            return false;
         } else {
            NotificationListenerKey inKey = (NotificationListenerKey)o;
            return this.listener == inKey.listener && this.filter == inKey.filter && this.handback == inKey.handback;
         }
      }

      public int hashCode() {
         return System.identityHashCode(this.listener) ^ System.identityHashCode(this.filter) ^ System.identityHashCode(this.handback);
      }
   }
}
