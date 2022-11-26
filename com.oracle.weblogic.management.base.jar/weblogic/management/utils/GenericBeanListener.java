package weblogic.management.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class GenericBeanListener implements BeanUpdateListener {
   private DescriptorBean listenToMe;
   private HashMap beanMethods;
   private Object managedObject;
   private HashMap managedMethods;
   private HashMap additionMethods;
   private HashMap validationMethods;
   private boolean registered;
   private BeanListenerCustomizer customizer;
   private BeanUpdateEvent currentEvent;
   private ComponentInvocationContext cic;
   private static ComponentInvocationContextManager CICM;
   private static final int START_ADD = 0;
   private static final int FINISH_ADD = 1;
   private static final int START_REM = 2;
   private static final int FINISH_REM = 3;
   private static final int MAX_INDEX = 4;
   private static final String[] adderMethodNames;

   public GenericBeanListener(DescriptorBean paramListenToMe, Object paramManagedObject, Map paramPropertyMap, Map paramAdditionMap, boolean paramRegister) {
      this.beanMethods = new HashMap();
      this.managedMethods = new HashMap();
      this.additionMethods = new HashMap();
      this.validationMethods = new HashMap();
      this.registered = false;
      this.listenToMe = paramListenToMe;
      this.managedObject = paramManagedObject;
      this.cic = CICM.getCurrentComponentInvocationContext();
      Class managedObjectClass = this.managedObject == null ? null : this.managedObject.getClass();
      Class beanObjectClass = this.listenToMe.getClass();
      Class[] parameterTypes = new Class[1];
      Set keySet;
      Iterator it;
      String key;
      if (paramPropertyMap != null) {
         keySet = paramPropertyMap.keySet();

         Method method;
         String key;
         for(it = keySet.iterator(); it.hasNext(); this.beanMethods.put(key, method)) {
            method = null;
            key = (String)it.next();
            parameterTypes[0] = (Class)paramPropertyMap.get(key);
            if (this.managedObject != null) {
               key = "set" + key;
               String validatorName = "val" + key;

               try {
                  method = managedObjectClass.getMethod(key, parameterTypes);
               } catch (NoSuchMethodException var24) {
                  throw new AssertionError("ERROR: A managed object did not have a " + key + " method");
               } catch (SecurityException var25) {
                  throw new AssertionError("ERROR: A managed object could not find the " + key + " method");
               }

               this.managedMethods.put(key, method);

               try {
                  method = managedObjectClass.getMethod(validatorName, parameterTypes);
                  this.validationMethods.put(key, method);
               } catch (NoSuchMethodException var22) {
               } catch (SecurityException var23) {
                  throw new AssertionError("ERROR: A managed object could not find the " + validatorName + " method");
               }
            }

            key = null;
            if (parameterTypes[0] == Boolean.TYPE) {
               key = "is" + key;
            } else {
               key = "get" + key;
            }

            try {
               method = beanObjectClass.getMethod(key, (Class[])null);
            } catch (NoSuchMethodException var20) {
               throw new AssertionError("ERROR: A bean did not have a " + key + " method");
            } catch (SecurityException var21) {
               throw new AssertionError("ERROR: A bean did not have the " + key + " method");
            }
         }
      }

      if (paramAdditionMap != null && managedObjectClass != null) {
         keySet = paramAdditionMap.keySet();
         it = keySet.iterator();
         Class[] finishParameterTypes = new Class[]{null, Boolean.TYPE};

         while(it.hasNext()) {
            Method[] methods = new Method[4];
            key = (String)it.next();

            for(int lcv = 0; lcv < 4; ++lcv) {
               String methodName = adderMethodNames[lcv] + key;
               Class[] useParameterTypes;
               if (lcv % 2 == 0) {
                  parameterTypes[0] = (Class)paramAdditionMap.get(key);
                  useParameterTypes = parameterTypes;
               } else {
                  finishParameterTypes[0] = (Class)paramAdditionMap.get(key);
                  useParameterTypes = finishParameterTypes;
               }

               try {
                  methods[lcv] = managedObjectClass.getMethod(methodName, useParameterTypes);
               } catch (NoSuchMethodException var18) {
                  throw new AssertionError("ERROR: A managed object did not have a " + methodName + " method");
               } catch (SecurityException var19) {
                  throw new AssertionError("ERROR: A managed object could not find the " + methodName + " method");
               }
            }

            this.additionMethods.put(key, methods);
         }
      }

      if (paramRegister) {
         this.listenToMe.addBeanUpdateListener(this);
         this.registered = true;
      } else {
         this.registered = false;
      }

   }

   public GenericBeanListener(DescriptorBean paramListenToMe, Object paramManagedObject, Map paramPropertyMap, boolean paramRegister) {
      this(paramListenToMe, paramManagedObject, paramPropertyMap, (Map)null, paramRegister);
   }

   public GenericBeanListener(DescriptorBean paramListenToMe, Object paramManagedObject, Map paramPropertyMap) {
      this(paramListenToMe, paramManagedObject, paramPropertyMap, (Map)null, true);
   }

   public GenericBeanListener(DescriptorBean paramListenToMe, Object paramManagedObject, Map paramPropertyMap, Map paramAdditionMap) {
      this(paramListenToMe, paramManagedObject, paramPropertyMap, paramAdditionMap, true);
   }

   public synchronized void setCustomizer(BeanListenerCustomizer paramCustomizer) {
      this.customizer = paramCustomizer;
   }

   public synchronized void open() {
      ManagedInvocationContext mic = CICM.setCurrentComponentInvocationContext(this.cic);
      Throwable var2 = null;

      try {
         this.openInternal();
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void openInternal() {
      if (!this.registered) {
         this.listenToMe.addBeanUpdateListener(this);
         this.registered = true;
      }
   }

   public synchronized void close() {
      ManagedInvocationContext mic = CICM.setCurrentComponentInvocationContext(this.cic);
      Throwable var2 = null;

      try {
         this.closeInternal();
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void closeInternal() {
      if (this.registered) {
         this.listenToMe.removeBeanUpdateListener(this);
         this.registered = false;
      }
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      ManagedInvocationContext mic = CICM.setCurrentComponentInvocationContext(this.cic);
      Throwable var3 = null;

      try {
         this.prepareUpdateInternal(event);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void prepareUpdateInternal(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      this.currentEvent = event;
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      boolean success = false;
      int lcv = 0;
      boolean var29 = false;

      try {
         var29 = true;
         if (this.additionMethods.size() > 0 || this.validationMethods.size() > 0) {
            for(lcv = 0; lcv < updates.length; ++lcv) {
               BeanUpdateEvent.PropertyUpdate update = updates[lcv];
               String key = update.getPropertyName();
               Method[] methods = (Method[])((Method[])this.additionMethods.get(key));
               int updateType = update.getUpdateType();
               Object[] arguments;
               Throwable cause;
               if (methods != null && updateType == 2) {
                  arguments = new Object[]{update.getAddedObject()};

                  try {
                     methods[0].invoke(this.managedObject, arguments);
                  } catch (IllegalAccessException var31) {
                     throw new BeanUpdateRejectedException(ManagementLogger.logAddBeanFailed1Loggable(key, var31.toString()).getMessage(), var31);
                  } catch (IllegalArgumentException var32) {
                     throw new BeanUpdateRejectedException(ManagementLogger.logAddBeanFailed2Loggable(key, var32.toString()).getMessage(), var32);
                  } catch (InvocationTargetException var33) {
                     cause = var33.getCause();
                     if (cause instanceof BeanUpdateRejectedException) {
                        throw (BeanUpdateRejectedException)cause;
                     }

                     throw new BeanUpdateRejectedException(ManagementLogger.logAddBeanFailed3Loggable(key, cause.toString()).getMessage(), cause);
                  }
               }

               if (methods != null && updateType == 3) {
                  arguments = new Object[]{update.getRemovedObject()};

                  try {
                     methods[2].invoke(this.managedObject, arguments);
                  } catch (IllegalAccessException var34) {
                     throw new BeanUpdateRejectedException(ManagementLogger.logRemoveBeanFailed1Loggable(key, var34.toString()).getMessage(), var34);
                  } catch (IllegalArgumentException var35) {
                     throw new BeanUpdateRejectedException(ManagementLogger.logRemoveBeanFailed2Loggable(key, var35.toString()).getMessage(), var35);
                  } catch (InvocationTargetException var36) {
                     cause = var36.getCause();
                     if (cause instanceof BeanUpdateRejectedException) {
                        throw (BeanUpdateRejectedException)cause;
                     }

                     throw new BeanUpdateRejectedException(ManagementLogger.logRemoveBeanFailed3Loggable(key, cause.toString()).getMessage(), cause);
                  }
               }

               Method managedValidator = this.managedObject == null ? null : (Method)this.validationMethods.get(key);
               Method beanGetter = this.managedObject == null ? null : (Method)this.beanMethods.get(key);
               if (managedValidator != null && beanGetter != null && updateType == 1) {
                  DescriptorBean proposedBean = event.getProposedBean();

                  try {
                     Object[] value = new Object[]{beanGetter.invoke(proposedBean, (Object[])null)};
                     managedValidator.invoke(this.managedObject, value);
                  } catch (IllegalAccessException var37) {
                     throw new BeanUpdateRejectedException(var37.getMessage(), var37);
                  } catch (IllegalArgumentException var38) {
                     throw new BeanUpdateRejectedException(var38.getMessage(), var38);
                  } catch (InvocationTargetException var39) {
                     Throwable cause = var39.getCause();
                     if (cause instanceof BeanUpdateRejectedException) {
                        throw (BeanUpdateRejectedException)cause;
                     }

                     throw new BeanUpdateRejectedException(cause.getMessage(), cause);
                  }
               }
            }
         }

         success = true;
         var29 = false;
      } finally {
         if (var29) {
            if (!success && lcv > 0) {
               try {
                  this.activateOrRollbackAddition(updates, lcv, false);
               } catch (BeanUpdateFailedException var30) {
                  Throwable cause = var30.getCause();
                  ManagementLogger.logRollbackFailure(var30.getMessage(), cause == null ? "null" : cause.toString());
               }
            }

         }
      }

      if (!success && lcv > 0) {
         try {
            this.activateOrRollbackAddition(updates, lcv, false);
         } catch (BeanUpdateFailedException var40) {
            Throwable cause = var40.getCause();
            ManagementLogger.logRollbackFailure(var40.getMessage(), cause == null ? "null" : cause.toString());
         }
      }

   }

   private void activateOrRollbackAddition(BeanUpdateEvent.PropertyUpdate[] updates, int maximalIndex, boolean isActivate) throws BeanUpdateFailedException {
      BeanUpdateFailedException anException = null;
      if (this.additionMethods.size() > 0) {
         int maximum = maximalIndex < 0 ? updates.length : (maximalIndex < updates.length ? maximalIndex : updates.length);

         for(int lcv = 0; lcv < maximum; ++lcv) {
            BeanUpdateEvent.PropertyUpdate update = updates[lcv];
            String key = update.getPropertyName();
            Method[] methods = (Method[])((Method[])this.additionMethods.get(key));
            if (methods != null) {
               int updateType = update.getUpdateType();
               Object[] arguments;
               Throwable cause;
               if (updateType == 2) {
                  arguments = new Object[]{update.getAddedObject(), new Boolean(isActivate)};

                  try {
                     methods[1].invoke(this.managedObject, arguments);
                  } catch (IllegalAccessException var17) {
                     if (anException == null) {
                        anException = new BeanUpdateFailedException(ManagementLogger.logFinishAddFailed1Loggable(key, var17.toString()).getMessage(), var17);
                     }

                     ManagementLogger.logFinishAddFailed1(key, var17.toString());
                  } catch (IllegalArgumentException var18) {
                     if (anException == null) {
                        anException = new BeanUpdateFailedException(ManagementLogger.logFinishAddFailed2Loggable(key, var18.toString()).getMessage(), var18);
                     }

                     ManagementLogger.logFinishAddFailed2(key, var18.toString());
                  } catch (InvocationTargetException var19) {
                     cause = var19.getCause();
                     if (cause instanceof RuntimeException) {
                        ManagementLogger.logBeanUpdateRuntimeException(cause);
                     }

                     if (anException == null) {
                        if (cause instanceof BeanUpdateFailedException) {
                           anException = (BeanUpdateFailedException)cause;
                        } else {
                           anException = new BeanUpdateFailedException(ManagementLogger.logFinishAddFailed3Loggable(key, cause == null ? "null" : cause.toString()).getMessage(), (Throwable)(cause == null ? var19 : cause));
                        }
                     }

                     ManagementLogger.logFinishAddFailed3(key, cause == null ? "null" : cause.toString());
                  }
               }

               if (updateType == 3) {
                  arguments = new Object[]{update.getRemovedObject(), new Boolean(isActivate)};

                  try {
                     methods[3].invoke(this.managedObject, arguments);
                  } catch (IllegalAccessException var14) {
                     if (anException == null) {
                        anException = new BeanUpdateFailedException(ManagementLogger.logFinishRemoveFailed1Loggable(key, var14.toString()).getMessage(), var14);
                     }

                     ManagementLogger.logFinishRemoveFailed1(key, var14.toString());
                  } catch (IllegalArgumentException var15) {
                     if (anException == null) {
                        anException = new BeanUpdateFailedException(ManagementLogger.logFinishRemoveFailed2Loggable(key, var15.toString()).getMessage(), var15);
                     }

                     ManagementLogger.logFinishRemoveFailed2(key, var15.toString());
                  } catch (InvocationTargetException var16) {
                     cause = var16.getCause();
                     if (cause instanceof RuntimeException) {
                        ManagementLogger.logBeanUpdateRuntimeException(cause);
                     }

                     if (anException == null) {
                        if (cause instanceof BeanUpdateFailedException) {
                           anException = (BeanUpdateFailedException)cause;
                        } else {
                           anException = new BeanUpdateFailedException(ManagementLogger.logFinishRemoveFailed3Loggable(key, var16.getCause().toString()).getMessage(), (Throwable)(cause == null ? var16 : cause));
                        }
                     }

                     ManagementLogger.logFinishRemoveFailed3(key, cause == null ? "null" : cause.toString());
                  }
               }
            }
         }
      }

      if (anException != null) {
         throw anException;
      }
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      ManagedInvocationContext mic = CICM.setCurrentComponentInvocationContext(this.cic);
      Throwable var3 = null;

      try {
         this.activateUpdateInternal(event);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void activateUpdateInternal(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateFailedException anException = null;

      try {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         try {
            this.activateOrRollbackAddition(updates, -1, true);
         } catch (BeanUpdateFailedException var19) {
            anException = var19;
         }

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            if (this.managedObject != null && updates[lcv].isDynamic()) {
               String key = updates[lcv].getPropertyName();
               Method beanGetter = (Method)this.beanMethods.get(key);
               if (beanGetter != null) {
                  Method managedSetter = (Method)this.managedMethods.get(key);
                  if (managedSetter != null) {
                     try {
                        Object[] value = new Object[]{beanGetter.invoke(this.listenToMe, (Object[])null)};
                        managedSetter.invoke(this.managedObject, value);
                     } catch (IllegalAccessException var21) {
                        if (anException == null) {
                           anException = new BeanUpdateFailedException(ManagementLogger.logPropertyChangeFailed1Loggable(key, var21.toString()).getMessage(), var21);
                        }

                        ManagementLogger.logPropertyChangeFailed1(key, var21.toString());
                     } catch (IllegalArgumentException var22) {
                        if (anException == null) {
                           anException = new BeanUpdateFailedException(ManagementLogger.logPropertyChangeFailed2Loggable(key, var22.toString()).getMessage(), var22);
                        }

                        ManagementLogger.logPropertyChangeFailed2(key, var22.toString());
                     } catch (InvocationTargetException var23) {
                        Throwable cause = var23.getCause();
                        if (anException == null) {
                           if (cause instanceof BeanUpdateFailedException) {
                              anException = (BeanUpdateFailedException)cause;
                           } else {
                              anException = new BeanUpdateFailedException(ManagementLogger.logPropertyChangeFailed3Loggable(key, var23.getCause().toString()).getMessage(), (Throwable)(cause == null ? var23 : cause));
                           }
                        }

                        ManagementLogger.logPropertyChangeFailed3(key, cause == null ? "null" : cause.toString());
                     }
                  }
               }
            }
         }
      } finally {
         if (this.customizer != null) {
            try {
               this.customizer.activateFinished();
            } catch (BeanUpdateFailedException var20) {
               if (anException == null) {
                  anException = var20;
               }
            }
         }

         this.currentEvent = null;
      }

      if (anException != null) {
         throw anException;
      }
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      ManagedInvocationContext mic = CICM.setCurrentComponentInvocationContext(this.cic);
      Throwable var3 = null;

      try {
         this.rollbackUpdateInternal(event);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void rollbackUpdateInternal(BeanUpdateEvent event) {
      try {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
         this.activateOrRollbackAddition(updates, -1, false);
      } catch (BeanUpdateFailedException var7) {
         Throwable cause = var7.getCause();
         ManagementLogger.logRollbackFailure(var7.getMessage(), cause == null ? "null" : cause.toString());
      } finally {
         this.currentEvent = null;
      }

   }

   public BeanUpdateEvent getCurrentEvent() {
      return this.currentEvent;
   }

   public void initialize() throws ManagementException {
      this.initialize(true);
   }

   public void initialize(boolean setAll) throws ManagementException {
      ManagedInvocationContext mic = CICM.setCurrentComponentInvocationContext(this.cic);
      Throwable var3 = null;

      try {
         this.initializeInternal(setAll);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void initializeInternal(boolean setAll) throws ManagementException {
      Set keySet = this.beanMethods.keySet();
      Iterator it = keySet.iterator();

      while(true) {
         String key;
         do {
            if (!it.hasNext()) {
               return;
            }

            key = (String)it.next();
         } while(!setAll && !this.listenToMe.isSet(key));

         Method beanGetter = (Method)this.beanMethods.get(key);
         if (beanGetter == null) {
            throw new AssertionError("ERROR: Could not set property " + key + " because the method signature was not specified in intialize");
         }

         Method managedSetter = (Method)this.managedMethods.get(key);
         if (managedSetter == null) {
            throw new AssertionError("ERROR: Could not set property " + key + " because the method signature setter was not specified in initialize");
         }

         try {
            Object[] value = new Object[]{beanGetter.invoke(this.listenToMe, (Object[])null)};
            managedSetter.invoke(this.managedObject, value);
         } catch (IllegalAccessException var9) {
            throw new ManagementException(ManagementLogger.logPropertyInitializationFailed1Loggable(key, var9.toString()).getMessage());
         } catch (IllegalArgumentException var10) {
            throw new ManagementException(ManagementLogger.logPropertyInitializationFailed2Loggable(key, var10.toString()).getMessage());
         } catch (InvocationTargetException var11) {
            Throwable cause = var11.getCause();
            if (cause instanceof ManagementException) {
               throw (ManagementException)cause;
            }

            throw new ManagementException(ManagementLogger.logPropertyInitializationFailed3Loggable(key, cause.toString()).getMessage());
         }
      }
   }

   static {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      CICM = ComponentInvocationContextManager.getInstance(kernelId);
      adderMethodNames = new String[]{"startAdd", "finishAdd", "startRemove", "finishRemove"};
   }
}
