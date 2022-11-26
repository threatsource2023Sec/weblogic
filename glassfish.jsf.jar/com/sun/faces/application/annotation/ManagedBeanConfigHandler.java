package com.sun.faces.application.annotation;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.mgbean.ManagedBeanInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

public class ManagedBeanConfigHandler implements ConfigAnnotationHandler {
   private static final Class[] SCOPES = new Class[]{RequestScoped.class, ViewScoped.class, SessionScoped.class, ApplicationScoped.class, NoneScoped.class, CustomScoped.class};
   private static final Collection HANDLES;
   private Map managedBeans;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.managedBeans == null) {
         this.managedBeans = new HashMap();
      }

      this.managedBeans.put(target, annotation);
   }

   public void push(FacesContext ctx) {
      if (this.managedBeans != null) {
         ApplicationAssociate associate = ApplicationAssociate.getInstance(ctx.getExternalContext());
         if (associate != null) {
            BeanManager manager = associate.getBeanManager();
            Iterator var4 = this.managedBeans.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               this.process(manager, (Class)entry.getKey(), (Annotation)entry.getValue());
            }
         }
      }

   }

   private void process(BeanManager manager, Class annotatedClass, Annotation annotation) {
      manager.register(this.getBeanInfo(annotatedClass, (ManagedBean)annotation));
   }

   private ManagedBeanInfo getBeanInfo(Class annotatedClass, ManagedBean metadata) {
      String name = this.getName(metadata, annotatedClass);
      String scope = this.getScope(annotatedClass);
      boolean eager = metadata.eager();
      Map annotatedFields = new LinkedHashMap();
      this.collectAnnotatedFields(annotatedClass, annotatedFields);
      List properties = null;
      if (!annotatedFields.isEmpty()) {
         properties = new ArrayList(annotatedFields.size());
         Iterator var8 = annotatedFields.entrySet().iterator();

         while(var8.hasNext()) {
            Map.Entry entry = (Map.Entry)var8.next();
            Field f = (Field)entry.getValue();
            ManagedProperty property = (ManagedProperty)f.getAnnotation(ManagedProperty.class);
            ManagedBeanInfo.ManagedProperty propertyInfo = new ManagedBeanInfo.ManagedProperty((String)entry.getKey(), f.getType().getName(), property.value(), (ManagedBeanInfo.MapEntry)null, (ManagedBeanInfo.ListEntry)null);
            properties.add(propertyInfo);
         }
      }

      return new ManagedBeanInfo(name, annotatedClass.getName(), scope, eager, (ManagedBeanInfo.MapEntry)null, (ManagedBeanInfo.ListEntry)null, properties, (Map)null);
   }

   private void collectAnnotatedFields(Class baseClass, Map annotatedFields) {
      Field[] fields = baseClass.getDeclaredFields();
      Field[] var4 = fields;
      int var5 = fields.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Field field = var4[var6];
         ManagedProperty property = (ManagedProperty)field.getAnnotation(ManagedProperty.class);
         if (property != null) {
            String propName = property.name();
            if (propName == null || propName.length() == 0) {
               propName = field.getName();
            }

            if (!annotatedFields.containsKey(propName)) {
               annotatedFields.put(propName, field);
            }
         }
      }

      Class superClass = baseClass.getSuperclass();
      if (!Object.class.equals(superClass)) {
         this.collectAnnotatedFields(superClass, annotatedFields);
      }

   }

   private String getScope(Class annotatedClass) {
      Class[] var2 = SCOPES;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class scope = var2[var4];
         Annotation a = annotatedClass.getAnnotation(scope);
         if (a != null) {
            if (a instanceof RequestScoped) {
               return "request";
            }

            if (a instanceof ViewScoped) {
               return "view";
            }

            if (a instanceof SessionScoped) {
               return "session";
            }

            if (a instanceof ApplicationScoped) {
               return "application";
            }

            if (a instanceof NoneScoped) {
               return "none";
            }

            if (a instanceof CustomScoped) {
               return ((CustomScoped)a).value();
            }
         }
      }

      return "request";
   }

   private String getName(ManagedBean managedBean, Class annotatedClass) {
      String name = managedBean.name();
      if (name.length() == 0) {
         String t = annotatedClass.getName();
         name = t.substring(t.lastIndexOf(46) + 1);
         char[] nameChars = name.toCharArray();
         nameChars[0] = Character.toLowerCase(nameChars[0]);
         name = new String(nameChars);
      }

      return name;
   }

   static {
      Collection handles = new ArrayList(2);
      handles.add(ManagedBean.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }
}
