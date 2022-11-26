package weblogic.management.mbeanservers.internal;

import java.io.ObjectInputStream;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Set;
import javax.management.Attribute;
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
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;
import javax.management.remote.MBeanServerForwarder;
import javax.security.auth.Subject;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class JMXConnectorSubjectForwarder implements MBeanServerForwarder {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private MBeanServer delegate;

   public MBeanServer getMBeanServer() {
      return this.delegate;
   }

   public void setMBeanServer(MBeanServer mbs) {
      this.delegate = mbs;
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      ObjectInstance objectInstance = null;
      AuthenticatedSubject as = this.getAuthenticatedSubject();
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         try {
            objectInstance = (ObjectInstance)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String fS = s;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public ObjectInstance run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().createMBean(fS, fObjectName);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof MBeanRegistrationException) {
                        throw (MBeanRegistrationException)e1;
                     } else if (e1 instanceof InstanceAlreadyExistsException) {
                        throw (InstanceAlreadyExistsException)e1;
                     } else if (e1 instanceof NotCompliantMBeanException) {
                        throw (NotCompliantMBeanException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var16) {
            this.rethrowCreateException(objectName, var16);
         }
      } catch (Throwable var17) {
         var6 = var17;
         throw var17;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var6.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectInstance;
   }

   public void rethrowCreateException(ObjectName objectName, PrivilegedActionException ex) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      Exception nestedException = ex.getException();
      if (nestedException instanceof NoAccessRuntimeException) {
         throw (NoAccessRuntimeException)nestedException;
      } else if (nestedException instanceof ReflectionException) {
         throw (ReflectionException)nestedException;
      } else if (nestedException instanceof InstanceAlreadyExistsException) {
         throw (InstanceAlreadyExistsException)nestedException;
      } else if (nestedException instanceof MBeanRegistrationException) {
         throw (MBeanRegistrationException)nestedException;
      } else if (nestedException instanceof MBeanException) {
         throw (MBeanException)nestedException;
      } else if (nestedException instanceof NotCompliantMBeanException) {
         throw (NotCompliantMBeanException)nestedException;
      } else if (nestedException instanceof RuntimeException) {
         throw (RuntimeException)nestedException;
      } else {
         throw new AssertionError(nestedException);
      }
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName, final ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      ObjectInstance objectInstance = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            objectInstance = (ObjectInstance)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String fS = s;
                     final ObjectName fObjectName1 = objectName1;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public ObjectInstance run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().createMBean(fS, fObjectName, fObjectName1);
                        }
                     });
                  } catch (PrivilegedActionException var4) {
                     Exception e1 = var4.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof MBeanRegistrationException) {
                        throw (MBeanRegistrationException)e1;
                     } else if (e1 instanceof NotCompliantMBeanException) {
                        throw (NotCompliantMBeanException)e1;
                     } else if (e1 instanceof InstanceAlreadyExistsException) {
                        throw (InstanceAlreadyExistsException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            Exception nestedException = var17.getException();
            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            this.rethrowCreateException(objectName, var17);
         }
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var6.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectInstance;
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName, final Object[] objects, final String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      ObjectInstance objectInstance = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var7 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            objectInstance = (ObjectInstance)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String fS = s;
                     final Object[] fObjects = objects;
                     final String[] fStrings = strings;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public ObjectInstance run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().createMBean(fS, fObjectName, fObjects, fStrings);
                        }
                     });
                  } catch (PrivilegedActionException var5) {
                     Exception e1 = var5.getException();
                     if (e1 instanceof InstanceAlreadyExistsException) {
                        throw (InstanceAlreadyExistsException)e1;
                     } else if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof NotCompliantMBeanException) {
                        throw (NotCompliantMBeanException)e1;
                     } else if (e1 instanceof MBeanRegistrationException) {
                        throw (MBeanRegistrationException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            this.rethrowCreateException(objectName, var17);
         }
      } catch (Throwable var18) {
         var7 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var7 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var7.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectInstance;
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName, final ObjectName objectName1, final Object[] objects, final String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      ObjectInstance objectInstance = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var8 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            objectInstance = (ObjectInstance)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String fS = s;
                     final ObjectName fObjectName1 = objectName1;
                     final Object[] fObjects = objects;
                     final String[] fStrings = strings;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public ObjectInstance run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().createMBean(fS, fObjectName, fObjectName1, fObjects, fStrings);
                        }
                     });
                  } catch (PrivilegedActionException var6) {
                     Exception e1 = var6.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof MBeanRegistrationException) {
                        throw (MBeanRegistrationException)e1;
                     } else if (e1 instanceof NotCompliantMBeanException) {
                        throw (NotCompliantMBeanException)e1;
                     } else if (e1 instanceof InstanceAlreadyExistsException) {
                        throw (InstanceAlreadyExistsException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var19) {
            Exception nestedException = var19.getException();
            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            this.rethrowCreateException(objectName, var19);
         }
      } catch (Throwable var20) {
         var8 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var8 != null) {
               try {
                  mic.close();
               } catch (Throwable var18) {
                  var8.addSuppressed(var18);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectInstance;
   }

   public Object getAttribute(final ObjectName objectName, final String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      Object attributeValue = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var5 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            attributeValue = as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String fS = s;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().getAttribute(fObjectName, fS);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof AttributeNotFoundException) {
                        throw (AttributeNotFoundException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            Exception nestedException = var17.getException();
            if (nestedException instanceof MBeanException) {
               throw (MBeanException)nestedException;
            }

            if (nestedException instanceof AttributeNotFoundException) {
               throw (AttributeNotFoundException)nestedException;
            }

            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            if (nestedException instanceof ReflectionException) {
               throw (ReflectionException)nestedException;
            }

            if (nestedException instanceof RuntimeException) {
               throw (RuntimeException)nestedException;
            }

            throw new AssertionError(nestedException);
         }
      } catch (Throwable var18) {
         var5 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var5.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

      return attributeValue;
   }

   public AttributeList getAttributes(final ObjectName objectName, final String[] strings) throws InstanceNotFoundException, ReflectionException {
      AttributeList attributeList = null;
      AuthenticatedSubject as = this.getAuthenticatedSubject();
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         try {
            attributeList = (AttributeList)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String[] fStrings = strings;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public AttributeList run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().getAttributes(fObjectName, fStrings);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            Exception nestedException = var17.getException();
            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            if (nestedException instanceof ReflectionException) {
               throw (ReflectionException)nestedException;
            }

            if (nestedException instanceof RuntimeException) {
               throw (RuntimeException)nestedException;
            }

            throw new AssertionError(nestedException);
         }
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var6.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

      return attributeList;
   }

   public void unregisterMBean(final ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().unregisterMBean(fObjectName);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof MBeanRegistrationException) {
                        throw (MBeanRegistrationException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var13) {
            this.rethrowUnregisterException(var13);
         }
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void rethrowUnregisterException(PrivilegedActionException ex) throws InstanceNotFoundException, MBeanRegistrationException {
      Exception nestedException = ex.getException();
      if (nestedException instanceof InstanceNotFoundException) {
         throw (InstanceNotFoundException)nestedException;
      } else if (nestedException instanceof NoAccessRuntimeException) {
         throw (NoAccessRuntimeException)nestedException;
      } else if (nestedException instanceof MBeanRegistrationException) {
         throw (MBeanRegistrationException)nestedException;
      } else if (nestedException instanceof RuntimeException) {
         throw (RuntimeException)nestedException;
      } else {
         throw new AssertionError(nestedException);
      }
   }

   public ObjectInstance registerMBean(final Object object, final ObjectName objectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      ObjectInstance objectInstance = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var5 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            objectInstance = (ObjectInstance)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final Object fobject = object;
                     final ObjectName fObjectName = objectName;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public ObjectInstance run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().registerMBean(fobject, fObjectName);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceAlreadyExistsException) {
                        throw (InstanceAlreadyExistsException)e1;
                     } else if (e1 instanceof MBeanRegistrationException) {
                        throw (MBeanRegistrationException)e1;
                     } else if (e1 instanceof NotCompliantMBeanException) {
                        throw (NotCompliantMBeanException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var15) {
            this.rethrowRegisterException(var15);
         }
      } catch (Throwable var16) {
         var5 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var5.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectInstance;
   }

   public void rethrowRegisterException(PrivilegedActionException ex) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      Exception nestedException = ex.getException();
      if (nestedException instanceof InstanceAlreadyExistsException) {
         throw (InstanceAlreadyExistsException)nestedException;
      } else if (nestedException instanceof NoAccessRuntimeException) {
         throw (NoAccessRuntimeException)nestedException;
      } else if (nestedException instanceof MBeanRegistrationException) {
         throw (MBeanRegistrationException)nestedException;
      } else if (nestedException instanceof NotCompliantMBeanException) {
         throw (NotCompliantMBeanException)nestedException;
      } else if (nestedException instanceof RuntimeException) {
         throw (RuntimeException)nestedException;
      } else {
         throw new AssertionError(nestedException);
      }
   }

   public void setAttribute(final ObjectName objectName, final Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var4 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final Attribute fAttribute = attribute;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().setAttribute(fObjectName, fAttribute);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof InvalidAttributeValueException) {
                        throw (InvalidAttributeValueException)e1;
                     } else if (e1 instanceof AttributeNotFoundException) {
                        throw (AttributeNotFoundException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var15) {
            Exception nestedException = var15.getException();
            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            if (nestedException instanceof AttributeNotFoundException) {
               throw (AttributeNotFoundException)nestedException;
            }

            if (nestedException instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)nestedException;
            }

            if (nestedException instanceof MBeanException) {
               throw (MBeanException)nestedException;
            }

            if (nestedException instanceof ReflectionException) {
               throw (ReflectionException)nestedException;
            }

            if (nestedException instanceof RuntimeException) {
               throw (RuntimeException)nestedException;
            }

            throw new AssertionError(nestedException);
         }
      } catch (Throwable var16) {
         var4 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var4.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public AttributeList setAttributes(final ObjectName objectName, final AttributeList attributeList) throws InstanceNotFoundException, ReflectionException {
      AttributeList returnList = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var5 = null;

      try {
         try {
            AuthenticatedSubject as = this.getAuthenticatedSubject();
            returnList = (AttributeList)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final AttributeList fAttributeList = attributeList;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public AttributeList run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().setAttributes(fObjectName, fAttributeList);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var16) {
            Exception nestedException = var16.getException();
            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            if (nestedException instanceof ReflectionException) {
               throw (ReflectionException)nestedException;
            }

            if (nestedException instanceof RuntimeException) {
               throw (RuntimeException)nestedException;
            }

            throw new AssertionError(nestedException);
         }
      } catch (Throwable var17) {
         var5 = var17;
         throw var17;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var5.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

      return returnList;
   }

   public Object invoke(final ObjectName objectName, final String s, final Object[] objects, final String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException {
      Object returnValue = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var7 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            returnValue = as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final String fS = s;
                     final Object[] fObjects = objects;
                     final String[] fStrings = strings;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().invoke(fObjectName, fS, fObjects, fStrings);
                        }
                     });
                  } catch (PrivilegedActionException var5) {
                     Exception e1 = var5.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof MBeanException) {
                        throw (MBeanException)e1;
                     } else if (e1 instanceof ReflectionException) {
                        throw (ReflectionException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var19) {
            Exception nestedException = var19.getException();
            if (nestedException instanceof MBeanException) {
               throw (MBeanException)nestedException;
            }

            if (nestedException instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)nestedException;
            }

            if (nestedException instanceof ReflectionException) {
               throw (ReflectionException)nestedException;
            }

            if (nestedException instanceof RuntimeException) {
               throw (RuntimeException)nestedException;
            }

            throw new AssertionError(nestedException);
         }
      } catch (Throwable var20) {
         var7 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var7 != null) {
               try {
                  mic.close();
               } catch (Throwable var18) {
                  var7.addSuppressed(var18);
               }
            } else {
               mic.close();
            }
         }

      }

      return returnValue;
   }

   public void addNotificationListener(final ObjectName objectName, final NotificationListener notificationListener, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();
         if (as != null) {
            try {
               as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     try {
                        final ObjectName fObjectName = objectName;
                        final NotificationListener fNotificationListener = notificationListener;
                        final NotificationFilter fNotificationFilter = notificationFilter;
                        final Object fO = o;
                        return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                           public Object run() throws Exception {
                              JMXConnectorSubjectForwarder.this.getMBeanServer().addNotificationListener(fObjectName, fNotificationListener, fNotificationFilter, fO);
                              return null;
                           }
                        });
                     } catch (PrivilegedActionException var5) {
                        Exception e1 = var5.getException();
                        if (e1 instanceof InstanceNotFoundException) {
                           throw (InstanceNotFoundException)e1;
                        } else {
                           throw e1;
                        }
                     }
                  }
               });
            } catch (PrivilegedActionException var18) {
               this.rethrowAddNotificationListenerException(objectName, var18);
            }

            return;
         }

         this.getMBeanServer().addNotificationListener(objectName, notificationListener, notificationFilter, o);
      } catch (Throwable var19) {
         var6 = var19;
         throw var19;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var17) {
                  var6.addSuppressed(var17);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void rethrowAddNotificationListenerException(ObjectName objectName, PrivilegedActionException ex) throws InstanceNotFoundException {
      Exception nestedException = ex.getException();
      if (nestedException instanceof NoAccessRuntimeException) {
         throw (NoAccessRuntimeException)nestedException;
      } else if (nestedException instanceof InstanceNotFoundException) {
         throw (InstanceNotFoundException)nestedException;
      } else if (nestedException instanceof RuntimeException) {
         throw (RuntimeException)nestedException;
      } else {
         throw new AssertionError(nestedException);
      }
   }

   public void addNotificationListener(final ObjectName objectName, final ObjectName objectName1, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final ObjectName fObjectName1 = objectName1;
                     final NotificationFilter fNotificationFilter = notificationFilter;
                     final Object fO = o;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().addNotificationListener(fObjectName, fObjectName1, fNotificationFilter, fO);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var5) {
                     Exception e1 = var5.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            this.rethrowAddNotificationListenerException(objectName, var17);
         }
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var6.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void removeNotificationListener(final ObjectName objectName, final ObjectName objectName1) throws InstanceNotFoundException, ListenerNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var4 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final ObjectName fObjectName1 = objectName1;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().removeNotificationListener(fObjectName, fObjectName1);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof ListenerNotFoundException) {
                        throw (ListenerNotFoundException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var15) {
            this.rethrowRemoveNotificationListenerException(objectName, var15);
         }
      } catch (Throwable var16) {
         var4 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var4.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void rethrowRemoveNotificationListenerException(ObjectName objectName, PrivilegedActionException ex) throws InstanceNotFoundException, ListenerNotFoundException {
      Exception nestedException = ex.getException();
      if (nestedException instanceof NoAccessRuntimeException) {
         throw (NoAccessRuntimeException)nestedException;
      } else if (nestedException instanceof InstanceNotFoundException) {
         throw (InstanceNotFoundException)nestedException;
      } else if (nestedException instanceof ListenerNotFoundException) {
         throw (ListenerNotFoundException)nestedException;
      } else if (nestedException instanceof RuntimeException) {
         throw (RuntimeException)nestedException;
      } else {
         throw new AssertionError(nestedException);
      }
   }

   public void removeNotificationListener(final ObjectName objectName, final ObjectName objectName1, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException, ListenerNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final ObjectName fObjectName1 = objectName1;
                     final NotificationFilter fNotificationFilter = notificationFilter;
                     final Object fObject = o;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().removeNotificationListener(fObjectName, fObjectName1, fNotificationFilter, fObject);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var5) {
                     Exception e1 = var5.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof ListenerNotFoundException) {
                        throw (ListenerNotFoundException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            this.rethrowRemoveNotificationListenerException(objectName, var17);
         }
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var6.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void removeNotificationListener(final ObjectName objectName, final NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var4 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();
         if (as == null) {
            this.getMBeanServer().removeNotificationListener(objectName, notificationListener);
            return;
         }

         try {
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final NotificationListener fNotificationListener = notificationListener;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().removeNotificationListener(fObjectName, fNotificationListener);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof ListenerNotFoundException) {
                        throw (ListenerNotFoundException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var16) {
            this.rethrowRemoveNotificationListenerException(objectName, var16);
         }
      } catch (Throwable var17) {
         var4 = var17;
         throw var17;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var4.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void removeNotificationListener(final ObjectName objectName, final NotificationListener notificationListener, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException, ListenerNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var6 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();
         if (as == null) {
            this.getMBeanServer().removeNotificationListener(objectName, notificationListener, notificationFilter, o);
            return;
         }

         try {
            as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final NotificationListener fNotificationListener = notificationListener;
                     final NotificationFilter fNotificationFilter = notificationFilter;
                     final Object fO = o;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           JMXConnectorSubjectForwarder.this.getMBeanServer().removeNotificationListener(fObjectName, fNotificationListener, fNotificationFilter, fO);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var5) {
                     Exception e1 = var5.getException();
                     if (e1 instanceof InstanceNotFoundException) {
                        throw (InstanceNotFoundException)e1;
                     } else if (e1 instanceof ListenerNotFoundException) {
                        throw (ListenerNotFoundException)e1;
                     } else {
                        throw e1;
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var18) {
            this.rethrowRemoveNotificationListenerException(objectName, var18);
         }
      } catch (Throwable var19) {
         var6 = var19;
         throw var19;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var17) {
                  var6.addSuppressed(var17);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public final ClassLoaderRepository getClassLoaderRepository() {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var2 = null;

      ClassLoaderRepository var3;
      try {
         try {
            var3 = (ClassLoaderRepository)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public ClassLoaderRepository run() throws Exception {
                  return JMXConnectorSubjectForwarder.this.getMBeanServer().getClassLoaderRepository();
               }
            });
         } catch (PrivilegedActionException var13) {
            Exception e1 = var13.getException();
            throw new AssertionError(e1);
         }
      } catch (Throwable var14) {
         var2 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var2.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

      return var3;
   }

   public ClassLoader getClassLoaderFor(final ObjectName oName) throws InstanceNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      ClassLoader var18;
      try {
         try {
            var18 = (ClassLoader)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public ClassLoader run() throws Exception {
                  return JMXConnectorSubjectForwarder.this.getMBeanServer().getClassLoaderFor(oName);
               }
            });
         } catch (PrivilegedActionException var15) {
            Exception e1 = var15.getException();
            if (e1 instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)e1;
            }

            throw new AssertionError(e1);
         }
      } catch (Throwable var16) {
         var3 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var3.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

      return var18;
   }

   public ClassLoader getClassLoader(final ObjectName oName) throws InstanceNotFoundException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      ClassLoader var18;
      try {
         try {
            var18 = (ClassLoader)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public ClassLoader run() throws Exception {
                  return JMXConnectorSubjectForwarder.this.getMBeanServer().getClassLoader(oName);
               }
            });
         } catch (PrivilegedActionException var15) {
            Exception e1 = var15.getException();
            if (e1 instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)e1;
            }

            throw new AssertionError(e1);
         }
      } catch (Throwable var16) {
         var3 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var3.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

      return var18;
   }

   /** @deprecated */
   @Deprecated
   public final ObjectInputStream deserialize(final ObjectName name, final byte[] data) throws OperationsException {
      try {
         return (ObjectInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public ObjectInputStream run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().deserialize(name, data);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof OperationsException) {
            throw (OperationsException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public final ObjectInputStream deserialize(final String className, final byte[] data) throws OperationsException, ReflectionException {
      try {
         return (ObjectInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public ObjectInputStream run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().deserialize(className, data);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof OperationsException) {
            throw (OperationsException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public final ObjectInputStream deserialize(final String className, final ObjectName loader, final byte[] data) throws OperationsException, ReflectionException {
      try {
         return (ObjectInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public ObjectInputStream run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().deserialize(className, loader, data);
            }
         });
      } catch (PrivilegedActionException var7) {
         Exception e1 = var7.getException();
         if (e1 instanceof OperationsException) {
            throw (OperationsException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className) throws ReflectionException, MBeanException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().instantiate(className);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className, final ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().instantiate(className, loaderName);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className, final Object[] params, final String[] signature) throws ReflectionException, MBeanException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().instantiate(className, params, signature);
            }
         });
      } catch (PrivilegedActionException var7) {
         Exception e1 = var7.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className, final ObjectName loaderName, final Object[] params, final String[] signature) throws ReflectionException, MBeanException, InstanceNotFoundException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().instantiate(className, loaderName, params, signature);
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public boolean isInstanceOf(final ObjectName objectName, final String s) throws InstanceNotFoundException {
      try {
         return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Boolean run() throws Exception {
               return new Boolean(JMXConnectorSubjectForwarder.this.getMBeanServer().isInstanceOf(objectName, s));
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public MBeanInfo getMBeanInfo(final ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      MBeanInfo var18;
      try {
         try {
            var18 = (MBeanInfo)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public MBeanInfo run() throws Exception {
                  return JMXConnectorSubjectForwarder.this.getMBeanServer().getMBeanInfo(objectName);
               }
            });
         } catch (PrivilegedActionException var15) {
            Exception e1 = var15.getException();
            if (e1 instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)e1;
            }

            if (e1 instanceof IntrospectionException) {
               throw (IntrospectionException)e1;
            }

            if (e1 instanceof ReflectionException) {
               throw (ReflectionException)e1;
            }

            throw new AssertionError(e1);
         }
      } catch (Throwable var16) {
         var3 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var3.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

      return var18;
   }

   public String[] getDomains() {
      try {
         return (String[])AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public String[] run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().getDomains();
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e1 = var3.getException();
         throw new AssertionError(e1);
      }
   }

   public String getDefaultDomain() {
      try {
         return (String)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public String run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().getDefaultDomain();
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e1 = var3.getException();
         throw new AssertionError(e1);
      }
   }

   public Integer getMBeanCount() {
      try {
         return (Integer)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Integer run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().getMBeanCount();
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e1 = var3.getException();
         throw new AssertionError(e1);
      }
   }

   public boolean isRegistered(final ObjectName objectName) {
      boolean registered = false;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var4 = null;

      try {
         try {
            registered = (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Boolean run() throws Exception {
                  return new Boolean(JMXConnectorSubjectForwarder.this.getMBeanServer().isRegistered(objectName));
               }
            });
         } catch (PrivilegedActionException var15) {
            Exception e1 = var15.getException();
            throw new AssertionError(e1);
         }
      } catch (Throwable var16) {
         var4 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var4.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

      return registered;
   }

   public Set queryNames(final ObjectName objectName, final QueryExp queryExp) {
      Set objectNameList = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var5 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            objectNameList = (Set)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final QueryExp fQueryexp = queryExp;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Set run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().queryNames(fObjectName, fQueryexp);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     throw new AssertionError(e1);
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            Exception nestedException = var17.getException();
            if (nestedException instanceof RuntimeException) {
               throw (RuntimeException)nestedException;
            }

            throw new AssertionError(nestedException);
         }
      } catch (Throwable var18) {
         var5 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var5.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectNameList;
   }

   public Set queryMBeans(final ObjectName objectName, final QueryExp queryExp) {
      Set objectInstanceList = null;
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var5 = null;

      try {
         AuthenticatedSubject as = this.getAuthenticatedSubject();

         try {
            objectInstanceList = (Set)as.doAs(KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  try {
                     final ObjectName fObjectName = objectName;
                     final QueryExp fQueryexp = queryExp;
                     return AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Set run() throws Exception {
                           return JMXConnectorSubjectForwarder.this.getMBeanServer().queryMBeans(fObjectName, fQueryexp);
                        }
                     });
                  } catch (PrivilegedActionException var3) {
                     Exception e1 = var3.getException();
                     throw new AssertionError(e1);
                  }
               }
            });
         } catch (PrivilegedActionException var17) {
            Exception e1 = var17.getException();
            throw new AssertionError(e1);
         }
      } catch (Throwable var18) {
         var5 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var5.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

      return objectInstanceList;
   }

   public ObjectInstance getObjectInstance(final ObjectName objectName) throws InstanceNotFoundException {
      try {
         return (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public ObjectInstance run() throws Exception {
               return JMXConnectorSubjectForwarder.this.getMBeanServer().getObjectInstance(objectName);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   private AuthenticatedSubject getAuthenticatedSubject() {
      final AccessControlContext acc = AccessController.getContext();
      if (acc != null) {
         Subject subject = (Subject)AccessController.doPrivileged(new PrivilegedAction() {
            public Subject run() {
               return Subject.getSubject(acc);
            }
         });
         if (subject != null) {
            AuthenticatedSubject authenticatedSubject = AuthenticatedSubject.getFromSubject(subject);
            if (!AuthenticatedSubject.ANON.equals(authenticatedSubject) && !KERNEL_ID.equals(authenticatedSubject)) {
               return authenticatedSubject;
            } else {
               AuthenticatedSubject asFromJMXContext = getAuthenticatedSubjectFromJMXContext();
               if (asFromJMXContext != null && !AuthenticatedSubject.ANON.equals(asFromJMXContext)) {
                  return asFromJMXContext;
               } else {
                  AuthenticatedSubject wlsStackSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
                  return wlsStackSubject;
               }
            }
         } else {
            return AuthenticatedSubject.ANON;
         }
      } else {
         return AuthenticatedSubject.ANON;
      }
   }

   private static AuthenticatedSubject getAuthenticatedSubjectFromJMXContext() {
      JMXContext jmxContext = JMXContextHelper.getJMXContext(false);
      if (jmxContext != null) {
         Subject subject = jmxContext.getSubject();
         if (subject != null) {
            return AuthenticatedSubject.getFromSubject(subject);
         }
      }

      return null;
   }

   private ManagedInvocationContext setInvocationContext() {
      try {
         JMXContext jmxContext = JMXContextHelper.getJMXContext(false);
         if (jmxContext == null) {
            return null;
         } else {
            String partitionName = jmxContext.getPartitionName();
            if (this.ok(partitionName) && !this.isDomain(partitionName)) {
               ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();
               ComponentInvocationContext newcic = cicm.createComponentInvocationContext(partitionName);
               ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(newcic);
               return mic;
            } else {
               return null;
            }
         }
      } catch (Throwable var6) {
         return null;
      }
   }

   private boolean ok(String s) {
      return s != null && s.length() > 0;
   }

   private boolean isDomain(String partitionName) {
      return "DOMAIN".equalsIgnoreCase(partitionName);
   }
}
