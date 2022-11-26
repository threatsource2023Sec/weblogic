package weblogic.j2ee.dd.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceProperty;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.javaee.EJBReference;

public class J2eeAnnotationProcessor extends BaseJ2eeAnnotationProcessor {
   public J2eeAnnotationProcessor() {
   }

   public J2eeAnnotationProcessor(Set supportedAnnos) {
      super(supportedAnnos);
   }

   public void processJ2eeAnnotations(Class c, J2eeClientEnvironmentBean eg) {
      Iterator var3 = this.getClassPersistenceContextRefs(c).iterator();

      while(var3.hasNext()) {
         PersistenceContext pc = (PersistenceContext)var3.next();
         this.addPersistenceContextRef(pc.name(), pc, (J2eeEnvironmentBean)eg, false);
      }

      super.processJ2eeAnnotations(c, eg);
   }

   protected void processField(Field f, J2eeClientEnvironmentBean eg) {
      if (f.isAnnotationPresent(PersistenceContext.class)) {
         this.addPersistenceContextRef(f, (J2eeEnvironmentBean)eg);
      } else {
         super.processField(f, eg);
      }

   }

   protected void processMethod(Method m, J2eeClientEnvironmentBean eg) {
      if (m.isAnnotationPresent(PersistenceContext.class)) {
         this.addPersistenceContextRef(m, (J2eeEnvironmentBean)eg);
      } else {
         super.processMethod(m, eg);
      }

   }

   private void addPersistenceContextRef(Field f, J2eeEnvironmentBean eg) {
      PersistenceContext pc = (PersistenceContext)f.getAnnotation(PersistenceContext.class);
      String name = this.getCompEnvJndiName(pc.name(), f);
      if (this.findInjectionTargetFromPersistenceContextRef(f, name, eg) == null) {
         this.addInjectionTarget(f, this.addPersistenceContextRef(name, pc, eg, true));
      }

   }

   private void addPersistenceContextRef(Method m, J2eeEnvironmentBean eg) {
      PersistenceContext pc = (PersistenceContext)m.getAnnotation(PersistenceContext.class);
      String name = this.getCompEnvJndiName(pc.name(), m);
      if (this.findInjectionTargetFromPersistenceContextRef(m, name, eg) == null) {
         this.addInjectionTarget(m, this.addPersistenceContextRef(name, pc, eg, true));
      }

   }

   private InjectionTargetBean addPersistenceContextRef(String name, PersistenceContext pc, J2eeEnvironmentBean eg, boolean injectable) {
      PersistenceContextRefBean pcRef = null;
      PersistenceContextRefBean[] var6 = eg.getPersistenceContextRefs();
      int var7 = var6.length;

      int var8;
      for(var8 = 0; var8 < var7; ++var8) {
         PersistenceContextRefBean ref = var6[var8];
         if (ref.getPersistenceContextRefName().equals(name)) {
            pcRef = ref;
            break;
         }
      }

      if (pcRef == null) {
         pcRef = eg.createPersistenceContextRef();
         pcRef.setPersistenceContextRefName(name);
      }

      if (!this.isSet("PersistenceUnitName", pcRef) && pc.unitName().length() > 0) {
         pcRef.setPersistenceUnitName(pc.unitName());
      }

      if (!this.isSet("PersistenceContextType", pcRef)) {
         pcRef.setPersistenceContextType(pc.type() == PersistenceContextType.TRANSACTION ? "Transaction" : "Extended");
      }

      if (!this.isSet("SynchronizationType", pcRef)) {
         pcRef.setSynchronizationType(pc.synchronization().toString());
      }

      if (!this.isSet("PersistenceProperties", pcRef)) {
         PersistenceProperty[] var11 = pc.properties();
         var7 = var11.length;

         for(var8 = 0; var8 < var7; ++var8) {
            PersistenceProperty prop = var11[var8];
            JavaEEPropertyBean pb = pcRef.createPersistenceProperty();
            pb.setName(prop.name());
            pb.setValue(pb.getValue());
         }
      }

      return injectable ? pcRef.createInjectionTarget() : null;
   }

   protected InjectionTargetBean findInjectionTargetFromEjbRef(String targetClass, String targetName, String name, Class type, J2eeClientEnvironmentBean eg) {
      EjbRefBean eRef = this.findEjbRef(name, eg);
      if (eRef != null) {
         InjectionTargetBean targetBean = this.findInjectionTargetInArray(targetClass, targetName, eRef.getInjectionTargets());
         if (targetBean != null) {
            return targetBean;
         }
      }

      EjbLocalRefBean eLocalRef = this.findEjbLocalRef(name, (J2eeEnvironmentBean)eg);
      if (eLocalRef != null) {
         InjectionTargetBean targetBean = this.findInjectionTargetInArray(targetClass, targetName, eLocalRef.getInjectionTargets());
         if (targetBean != null) {
            return targetBean;
         }
      }

      return null;
   }

   protected InjectionTargetBean findInjectionTargetFromPersistenceContextRef(Method m, String name, J2eeEnvironmentBean eg) {
      return this.findInjectionTargetFromPersistenceContextRef(m.getDeclaringClass().getName(), this.getPropertyName(m), name, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromPersistenceContextRef(Field f, String name, J2eeEnvironmentBean eg) {
      return this.findInjectionTargetFromPersistenceContextRef(f.getDeclaringClass().getName(), f.getName(), name, eg);
   }

   protected InjectionTargetBean findInjectionTargetFromPersistenceContextRef(String targetClass, String targetName, String name, J2eeEnvironmentBean eg) {
      PersistenceContextRefBean pcRef = null;
      PersistenceContextRefBean[] var6 = eg.getPersistenceContextRefs();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PersistenceContextRefBean ref = var6[var8];
         if (ref.getPersistenceContextRefName().equals(name)) {
            pcRef = ref;
            break;
         }
      }

      return pcRef == null ? null : this.findInjectionTargetInArray(targetClass, targetName, pcRef.getInjectionTargets());
   }

   protected InjectionTargetBean addEjbRef(String name, Class iface, EJBReference ref, J2eeClientEnvironmentBean ceb, boolean injectable) {
      J2eeEnvironmentBean eg = (J2eeEnvironmentBean)ceb;
      EjbRefBean eRef = this.findEjbRef(name, eg);
      if (eRef != null) {
         return this.addEJBRemoteRef(name, iface, ref, eg, eRef, injectable);
      } else {
         EjbLocalRefBean eLRef = this.findEjbLocalRef(name, eg);
         if (eLRef != null) {
            return this.addEJBLocalRef(name, iface, ref, eg, eLRef, injectable);
         } else if (iface == Object.class) {
            this.addBeanInterfaceNotSetError(ceb);
            return null;
         } else {
            return this.addEJBLocalRef(name, iface, (EJBReference)ref, eg, (EjbLocalRefBean)null, injectable);
         }
      }
   }

   protected InjectionTargetBean addEjbRef(String name, Class iface, EJB ejb, J2eeClientEnvironmentBean ceb, boolean injectable) {
      J2eeEnvironmentBean eg = (J2eeEnvironmentBean)ceb;
      EjbRefBean eRef = this.findEjbRef(name, eg);
      if (eRef != null) {
         return this.addEJBRemoteRef(name, iface, ejb, eg, eRef, injectable);
      } else {
         EjbLocalRefBean eLRef = this.findEjbLocalRef(name, eg);
         if (eLRef != null) {
            return this.addEJBLocalRef(name, iface, ejb, eg, eLRef, injectable);
         } else if (!EJBHome.class.isAssignableFrom(iface) && !iface.isAnnotationPresent(Remote.class)) {
            return !EJBLocalHome.class.isAssignableFrom(iface) && !iface.isAnnotationPresent(Local.class) ? this.addEJBLocalRef(name, iface, (EJB)ejb, eg, (EjbLocalRefBean)null, injectable) : this.addEJBLocalRef(name, iface, (EJB)ejb, eg, (EjbLocalRefBean)null, injectable);
         } else {
            return this.addEJBRemoteRef(name, iface, ejb, eg, (EjbRefBean)null, injectable);
         }
      }
   }

   private InjectionTargetBean addEJBLocalRef(String name, Class iface, EJBReference ejbRef, J2eeEnvironmentBean eg, EjbLocalRefBean eRef, boolean injectable) {
      if (eRef == null) {
         eRef = eg.createEjbLocalRef();
         eRef.setEjbRefName(name);
      }

      if (iface != Object.class) {
         if (EJBLocalHome.class.isAssignableFrom(iface)) {
            if (!this.isSet("LocalHome", eRef)) {
               eRef.setLocalHome(iface.getName());
            }
         } else if (!this.isSet("Local", eRef)) {
            eRef.setLocal(iface.getName());
         }
      }

      if (!this.isSet("MappedName", eRef) && ejbRef.jndiName().length() > 0) {
         eRef.setMappedName("weblogic-jndi:" + ejbRef.jndiName());
      }

      return injectable ? eRef.createInjectionTarget() : null;
   }

   private InjectionTargetBean addEJBLocalRef(String name, Class iface, EJB ejbRef, J2eeEnvironmentBean eg, EjbLocalRefBean eRef, boolean injectable) {
      if (eRef == null) {
         eRef = eg.createEjbLocalRef();
         eRef.setEjbRefName(name);
      }

      if (iface != Object.class) {
         if (EJBLocalHome.class.isAssignableFrom(iface)) {
            if (!this.isSet("LocalHome", eRef)) {
               eRef.setLocalHome(iface.getName());
            }
         } else if (!this.isSet("Local", eRef)) {
            eRef.setLocal(iface.getName());
         }
      }

      if (!this.isSet("EjbLink", eRef) && ejbRef.beanName().length() > 0) {
         eRef.setEjbLink(ejbRef.beanName());
      }

      if (!this.isSet("MappedName", eRef) && ejbRef.mappedName().length() > 0) {
         eRef.setMappedName(ejbRef.mappedName());
      }

      this.setUnsetAttribute(eRef, false, "LookupName", ejbRef.lookup());
      return injectable ? eRef.createInjectionTarget() : null;
   }

   private EjbLocalRefBean findEjbLocalRef(String name, J2eeEnvironmentBean eg) {
      EjbLocalRefBean[] var3 = eg.getEjbLocalRefs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EjbLocalRefBean eRef = var3[var5];
         if (name.equals(eRef.getEjbRefName())) {
            return eRef;
         }
      }

      return null;
   }

   protected void processRunAs(Class c, DescriptorBean identityBean) {
      if (c.isAnnotationPresent(RunAs.class)) {
         this.perhapsDeclareRunAs(identityBean, ((RunAs)c.getAnnotation(RunAs.class)).value());
      }

   }

   protected void perhapsDeclareRunAs(DescriptorBean identityBean, String roleName) {
   }

   protected void processDeclareRoles(Class c, DescriptorBean identityBean) {
      if (c.isAnnotationPresent(DeclareRoles.class)) {
         this.perhapsDeclareRoles(identityBean, ((DeclareRoles)c.getAnnotation(DeclareRoles.class)).value());
      }

   }

   protected void perhapsDeclareRoles(DescriptorBean identityBean, String[] roles) {
   }
}
