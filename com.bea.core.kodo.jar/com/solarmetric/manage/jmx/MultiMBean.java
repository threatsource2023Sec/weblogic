package com.solarmetric.manage.jmx;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;

public class MultiMBean extends BaseDynamicMBean implements NotificationBroadcaster, Serializable {
   private transient HashMap listenersMap = new HashMap();
   private transient SubMBean[] subs;
   private String description;

   public MultiMBean(String description, SubMBean[] subs) {
      this.subs = subs;
      this.description = description;
   }

   protected Object invoke(String name, Class[] params, Object[] args) throws InvalidAttributeValueException, MBeanException, ReflectionException {
      try {
         String action = name.substring(0, 3);
         String methodname;
         String prefix;
         if (!action.equals("get") && !action.equals("set")) {
            if (name.lastIndexOf(46) == -1) {
               prefix = "";
               methodname = name;
            } else {
               prefix = name.substring(0, name.lastIndexOf(46));
               methodname = name.substring(name.lastIndexOf(46) + 1);
            }
         } else {
            String attrname;
            if (name.lastIndexOf(46) == -1) {
               prefix = "";
               attrname = name.substring(3);
            } else {
               prefix = name.substring(3, name.lastIndexOf(46));
               attrname = name.substring(name.lastIndexOf(46) + 1);
            }

            methodname = action + attrname;
         }

         for(int i = 0; i < this.subs.length; ++i) {
            SubMBean sub = this.subs[i];
            if (prefix.equals(sub.getPrefix())) {
               Method m = sub.getSub().getClass().getMethod(methodname, params);
               return m.invoke(sub.getSub(), args);
            }
         }

         throw new ReflectionException(new Exception("Invalid attribute:  " + name));
      } catch (NoSuchMethodException var11) {
         throw new ReflectionException(var11);
      } catch (IllegalAccessException var12) {
         throw new ReflectionException(var12);
      } catch (IllegalArgumentException var13) {
         throw new InvalidAttributeValueException(var13.toString());
      } catch (InvocationTargetException var14) {
         Throwable t = var14.getTargetException();
         if (t instanceof RuntimeException) {
            throw new RuntimeMBeanException((RuntimeException)t);
         } else if (t instanceof Exception) {
            throw new MBeanException((Exception)t);
         } else {
            throw new RuntimeErrorException((Error)t);
         }
      }
   }

   protected String getMBeanDescription() {
      return this.description;
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      ArrayList attrs = new ArrayList();

      for(int i = 0; i < this.subs.length; ++i) {
         SubMBean sub = this.subs[i];
         MBeanAttributeInfo[] subInfoArray = sub.createMBeanAttributeInfo();

         for(int j = 0; j < subInfoArray.length; ++j) {
            MBeanAttributeInfo subInfo = subInfoArray[j];
            String name;
            if (sub.getPrefix().equals("")) {
               name = subInfo.getName();
            } else {
               name = sub.getPrefix() + "." + subInfo.getName();
            }

            attrs.add(new MBeanAttributeInfo(name, subInfo.getType(), subInfo.getDescription(), subInfo.isReadable(), subInfo.isWritable(), subInfo.isIs()));
         }
      }

      return (MBeanAttributeInfo[])((MBeanAttributeInfo[])attrs.toArray(new MBeanAttributeInfo[attrs.size()]));
   }

   protected MBeanOperationInfo[] createMBeanOperationInfo() {
      ArrayList opers = new ArrayList();

      for(int i = 0; i < this.subs.length; ++i) {
         if (this.subs[i] instanceof SubMBeanOperations) {
            SubMBeanOperations sub = (SubMBeanOperations)this.subs[i];
            MBeanOperationInfo[] subInfoArray = sub.createMBeanOperationInfo();

            for(int j = 0; j < subInfoArray.length; ++j) {
               MBeanOperationInfo subInfo = subInfoArray[j];
               String name;
               if (sub.getPrefix().equals("")) {
                  name = subInfo.getName();
               } else {
                  name = sub.getPrefix() + "." + subInfo.getName();
               }

               opers.add(new MBeanOperationInfo(name, subInfo.getDescription(), subInfo.getSignature(), subInfo.getReturnType(), subInfo.getImpact()));
            }
         }
      }

      return (MBeanOperationInfo[])((MBeanOperationInfo[])opers.toArray(new MBeanOperationInfo[opers.size()]));
   }

   protected MBeanNotificationInfo[] createMBeanNotificationInfo() {
      ArrayList notifs = new ArrayList();

      for(int i = 0; i < this.subs.length; ++i) {
         if (this.subs[i] instanceof SubMBeanNotifier) {
            SubMBeanNotifier sub = (SubMBeanNotifier)this.subs[i];
            MBeanNotificationInfo[] subInfoArray = sub.createMBeanNotificationInfo();

            for(int j = 0; j < subInfoArray.length; ++j) {
               MBeanNotificationInfo subInfo = subInfoArray[j];
               notifs.add(new MBeanNotificationInfo(new String[]{subInfo.getNotifTypes()[0]}, "javax.management.Notification", subInfo.getDescription()));
            }
         }
      }

      return (MBeanNotificationInfo[])((MBeanNotificationInfo[])notifs.toArray(new MBeanNotificationInfo[notifs.size()]));
   }

   public synchronized void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      ArrayList notifList = null;

      for(int i = 0; i < this.subs.length; ++i) {
         if (this.subs[i] instanceof SubMBeanNotifier) {
            SubMBeanNotifier sub = (SubMBeanNotifier)this.subs[i];
            MBeanNotificationInfo[] subInfoArray = sub.createMBeanNotificationInfo();

            for(int j = 0; j < subInfoArray.length; ++j) {
               MBeanNotificationInfo subInfo = subInfoArray[j];
               if (filter == null || filter.isNotificationEnabled(new Notification(subInfo.getNotifTypes()[0] + "." + sub.getPrefix(), this, 0L))) {
                  sub.addNotificationListener(listener, filter, handback);
                  if (notifList == null) {
                     notifList = new ArrayList();
                     this.listenersMap.put(listener, notifList);
                  }

                  notifList.add(sub);
                  break;
               }
            }
         }
      }

   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return this.createMBeanNotificationInfo();
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      ArrayList notifList = (ArrayList)this.listenersMap.remove(listener);
      if (notifList != null) {
         Iterator i = notifList.iterator();

         while(i.hasNext()) {
            SubMBeanNotifier sub = (SubMBeanNotifier)i.next();
            sub.removeNotificationListener(listener);
         }
      }

   }
}
