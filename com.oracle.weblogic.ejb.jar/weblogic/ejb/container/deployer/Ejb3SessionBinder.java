package weblogic.ejb.container.deployer;

import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.LinkRef;
import javax.naming.NamingException;
import weblogic.application.naming.Environment;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.Ejb3SessionHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.AggregatableOpaqueReference;
import weblogic.ejb.container.internal.SingletonEJBHomeImpl;
import weblogic.ejb.container.internal.StatelessEJBHomeImpl;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;

class Ejb3SessionBinder extends Ejb2JndiBinder {
   Ejb3SessionBinder(SessionBeanInfo sbi, Environment env) throws NamingException {
      super(sbi, env);
   }

   private void bindRemoteProxy(SessionBeanInfo sbi) throws NamingException, WLDeploymentException {
      assert sbi.isStateless() || sbi.isSingleton();

      Map jndiMap = sbi.getBusinessJNDINames();
      Iterator var3 = sbi.getBusinessRemotes().iterator();

      while(var3.hasNext()) {
         Class iface = (Class)var3.next();
         if (!Remote.class.isAssignableFrom(iface)) {
            Object obj = ((Ejb3SessionHome)sbi.getRemoteHome()).getBindableImpl(iface.getName());
            Object obj = new AggregatableOpaqueReference(sbi.getFullyQualifiedName(), iface.getName(), obj);
            boolean clusterable = sbi.getClusteringDescriptor().getBeanIsClusterable();
            String jndiName = sbi.getCustomJndiName(iface);
            if (jndiName != null) {
               this.jndiService.bind(jndiName, obj, clusterable, true);
               if (clusterable) {
                  this.bindPortableNames(obj, (Set)jndiMap.get(iface), true);
               } else {
                  this.nonRepBindToNameSpaces((Set)jndiMap.get(iface), new LinkRef(jndiName), true);
               }
            } else if (clusterable) {
               this.bindPortableNames(obj, (Set)jndiMap.get(iface), true);
            } else {
               this.nonRepBindPortableNames(obj, (Set)jndiMap.get(iface), true);
            }
         }
      }

   }

   private void bindClusterableObject(SessionBeanInfo sbi) throws NamingException, WLDeploymentException {
      assert sbi.isStateless() || sbi.isSingleton();

      Map jndiMap = sbi.getBusinessJNDINames();
      Iterator var3 = sbi.getBusinessRemotes().iterator();

      while(var3.hasNext()) {
         Class iface = (Class)var3.next();
         boolean clusterable = sbi.getClusteringDescriptor().getBeanIsClusterable();
         Object obj;
         if (Remote.class.isAssignableFrom(iface)) {
            obj = ((Ejb3SessionHome)sbi.getRemoteHome()).getBindableImpl(iface.getName());
            String jndiName = sbi.getCustomJndiName(iface);
            if (jndiName != null) {
               this.jndiService.bind(jndiName, obj, clusterable, true);
               if (clusterable) {
                  this.bindPortableNames(obj, (Set)jndiMap.get(iface), true);
               } else {
                  this.nonRepBindToNameSpaces((Set)jndiMap.get(iface), new LinkRef(jndiName), true);
               }
            } else if (clusterable) {
               this.bindPortableNames(obj, (Set)jndiMap.get(iface), true);
            } else {
               this.nonRepBindPortableNames(obj, (Set)jndiMap.get(iface), true);
            }
         } else {
            obj = null;
            Remote obj;
            if (sbi.isStateless()) {
               obj = ((StatelessEJBHomeImpl)sbi.getRemoteHome()).getBindableEO(iface);
            } else {
               obj = ((SingletonEJBHomeImpl)sbi.getRemoteHome()).getBindableEO(iface);
            }

            this.jndiService.bind(sbi.getGeneratedJndiNameFor(iface.getSimpleName()), obj, clusterable, false);
         }
      }

   }

   private void bindOpaqueRefImpl(SessionBeanInfo sbi) throws NamingException, WLDeploymentException {
      assert sbi.isStateful();

      Map jndiMap = sbi.getBusinessJNDINames();

      Class iface;
      AggregatableOpaqueReference obj;
      for(Iterator var3 = sbi.getBusinessRemotes().iterator(); var3.hasNext(); this.bindPortableNames(obj, (Set)jndiMap.get(iface), true)) {
         iface = (Class)var3.next();
         Object obj = ((Ejb3SessionHome)sbi.getRemoteHome()).getBindableImpl(iface.getName());
         obj = new AggregatableOpaqueReference(sbi.getFullyQualifiedName(), iface.getName(), obj);
         String jndiName = sbi.getCustomJndiName(iface);
         if (jndiName != null) {
            this.jndiService.bind(jndiName, obj, true, true);
         }
      }

   }

   private void bindLocalBusinessIntf(SessionBeanInfo sbi) throws NamingException, WLDeploymentException {
      Set localViews = new HashSet(sbi.getBusinessLocals());
      if (sbi.hasNoIntfView()) {
         localViews.add(sbi.getBeanClass());
      }

      Map jndiMap = sbi.getBusinessJNDINames();

      Class iface;
      Object obj;
      for(Iterator var4 = localViews.iterator(); var4.hasNext(); this.nonRepBindToNameSpaces((Set)jndiMap.get(iface), obj, false)) {
         iface = (Class)var4.next();
         if (sbi.isStateful()) {
            obj = sbi.getBindable(iface.getName());
         } else {
            obj = new Ejb2JndiBinder.LocalViewRef(sbi.getBindable(iface.getName()), iface);
         }

         String jndiName = sbi.getCustomJndiName(iface);
         if (jndiName != null) {
            this.jndiService.bind(jndiName, obj, false, false);
         }
      }

   }

   public void bindToJNDI() throws WLDeploymentException {
      super.bindToJNDI();
      SessionBeanInfo sbi = (SessionBeanInfo)this.cdbi;

      try {
         if (sbi.hasBusinessRemotes()) {
            String name = sbi.getGeneratedJndiNameForHome();
            if (!sbi.hasDeclaredRemoteHome() || name.equals(sbi.getJNDINameAsString())) {
               this.jndiService.bind(name, sbi.getRemoteHome(), true, false);
            }

            if (sbi.isStateful()) {
               this.bindOpaqueRefImpl(sbi);
            } else {
               this.bindClusterableObject(sbi);
               this.bindRemoteProxy(sbi);
            }
         }

         if (sbi.hasBusinessLocals() || sbi.hasNoIntfView()) {
            this.bindLocalBusinessIntf(sbi);
         }

      } catch (NamingException var4) {
         Loggable l = EJBLogger.logBusinessJNDIRebindFailedLoggable(sbi.getJNDINameAsString(), var4.toString());
         this.unbindFromJNDI();
         throw new WLDeploymentException(l.getMessageText(), var4);
      }
   }

   private void bindPortableNames(Object obj, Set portableNames, boolean isRemote) throws WLDeploymentException {
      Set names = new HashSet(portableNames);
      String pickedName = this.pickAndBindInGlobalNS(names, obj, true, isRemote);
      this.bindToNameSpaces(names, new LinkRef(pickedName), isRemote);
   }

   private void nonRepBindPortableNames(Object obj, Set portableNames, boolean isRemote) throws WLDeploymentException {
      Set names = new HashSet(portableNames);
      String pickedName = this.pickAndBindInGlobalNS(names, obj, false, isRemote);
      this.nonRepBindToNameSpaces(names, new LinkRef(pickedName), isRemote);
   }
}
