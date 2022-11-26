package weblogic.application.naming;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.NamingException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.logging.Loggable;

public class MessageDestinationReference extends EnvReference {
   private static final String MESSAGE_DESTINATION_OBJECT_FACTORY = "weblogic.application.naming.MessageDestinationObjectFactory";
   private static final long serialVersionUID = 8761944172177728069L;
   private final String initialContextFactory;
   private final String providerURL;

   public MessageDestinationReference(Environment env, MessageDestinationRefBean msgDest, String jndiName, String providerURL, String initialContextFactory) throws EnvironmentException {
      super(env, msgDest.getMessageDestinationType(), "weblogic.application.naming.MessageDestinationObjectFactory");
      this.jndiName = jndiName;
      this.providerURL = providerURL;
      this.initialContextFactory = initialContextFactory;
   }

   public Object lookupMessageDestination() throws NamingException {
      InitialContext ic;
      if (this.initialContextFactory == null) {
         ic = new InitialContext();
      } else {
         Hashtable env = new Hashtable();
         env.put("java.naming.factory.initial", this.initialContextFactory);
         if (null != this.providerURL) {
            env.put("java.naming.provider.url", this.providerURL);
         }

         ic = new InitialContext(env);
      }

      return ic.lookup(this.jndiName);
   }

   public static Object getBindable(Environment env, MessageDestinationRefBean msgDest, String jarName) throws EnvironmentException {
      String linkName = msgDest.getMessageDestinationLink() == null ? msgDest.getMessageDestinationRefName() : msgDest.getMessageDestinationLink();
      ApplicationAccess aa = ApplicationAccess.getApplicationAccess();
      ApplicationContextInternal appCtx = aa.getApplicationContext(env.getApplicationName());
      Collection ctxs = getEjbAndWebMCs(appCtx);
      MessageDestinationInfoRegistry.MessageDestinationInfo mdi = null;
      if (linkName.contains("#")) {
         mdi = findByPath(linkName, ctxs, jarName);
         if (mdi == null) {
            Loggable l = J2EELogger.logUnableToResolveMessageDestinationLinkLoggable(msgDest.getMessageDestinationLink(), msgDest.getMessageDestinationRefName(), jarName);
            throw new EnvironmentException(l.getMessage());
         }
      } else if (linkName.contains("/")) {
         mdi = findByModuleName(linkName, ctxs);
      }

      if (mdi == null) {
         ModuleContext currentCtx = appCtx.getModuleContext(env.getModuleId());
         if (currentCtx != null && isEjbOrWeb(currentCtx.getType())) {
            Collection currentCtxs = new HashSet(1);
            currentCtxs.add(currentCtx);
            mdi = findByName(linkName, (Collection)currentCtxs);
         }

         if (mdi == null) {
            mdi = findByName(linkName, ctxs);
         }

         if (mdi == null) {
            mdi = findByName(linkName, appCtx);
         }
      }

      String jndiName = null;
      String initialContextFactory = null;
      String providerURL = null;
      if (mdi != null) {
         MessageDestinationDescriptorBean mdd = mdi.getMessageDestinationDescriptor();
         MessageDestinationBean mdb = mdi.getMessageDestination();
         if (mdd != null) {
            String destinationResourceLink = mdd.getDestinationResourceLink();
            if (destinationResourceLink != null) {
               jndiName = EnvUtils.resolveResourceLink(env.getApplicationName(), destinationResourceLink);
            } else {
               jndiName = EnvUtils.transformJNDIName(mdd.getDestinationJNDIName(), env.getApplicationName());
               initialContextFactory = mdd.getInitialContextFactory();
               providerURL = mdd.getProviderUrl();
            }
         }

         if (jndiName == null && mdb != null) {
            if (mdb.getLookupName() != null) {
               jndiName = mdb.getLookupName();
            } else if (mdb.getMappedName() != null) {
               jndiName = mdb.getMappedName();
            }
         }
      }

      if (jndiName == null) {
         if (msgDest.getMappedName() != null) {
            jndiName = msgDest.getMappedName();
         } else {
            jndiName = msgDest.getMessageDestinationRefName();
         }
      }

      return providerURL == null && initialContextFactory == null ? new LinkRef(jndiName) : new MessageDestinationReference(env, msgDest, jndiName, providerURL, initialContextFactory);
   }

   public static MessageDestinationInfoRegistry.MessageDestinationInfo findByPath(String link, Collection ctxs, String jarName) {
      String absoluteLink = link;
      if (link.startsWith("../")) {
         absoluteLink = EnvUtils.makePathAbsolute(link, jarName);
      }

      String targetModuleURI = absoluteLink.substring(0, absoluteLink.lastIndexOf(35));
      String targetName = link.substring(link.lastIndexOf(35) + 1);
      ModuleContext mc = findContextByURI(targetModuleURI, ctxs);
      if (mc != null) {
         MessageDestinationInfoRegistry reg = getRegistry(mc);
         if (reg != null) {
            return reg.get(targetName);
         }
      }

      return null;
   }

   public static MessageDestinationInfoRegistry.MessageDestinationInfo findByModuleName(String link, Collection ctxs) {
      String targetModuleName = link.substring(0, link.indexOf(47));
      String targetName = link.substring(link.indexOf(47) + 1);
      ModuleContext mc = findContextByName(targetModuleName, ctxs);
      if (mc != null) {
         MessageDestinationInfoRegistry reg = getRegistry(mc);
         if (reg != null) {
            return reg.get(targetName);
         }
      }

      return null;
   }

   public static MessageDestinationInfoRegistry.MessageDestinationInfo findByName(String link, Collection ctxs) {
      Iterator var2 = ctxs.iterator();

      while(var2.hasNext()) {
         ModuleContext ctx = (ModuleContext)var2.next();
         MessageDestinationInfoRegistry reg = getRegistry(ctx);
         if (reg != null) {
            MessageDestinationInfoRegistry.MessageDestinationInfo mddb = reg.get(link);
            if (mddb != null) {
               return mddb;
            }
         }
      }

      return null;
   }

   public static MessageDestinationInfoRegistry.MessageDestinationInfo findByName(String link, ApplicationContextInternal appCtx) {
      Map registry = (Map)appCtx.getUserObject(ModuleRegistry.class.getName());
      if (registry == null) {
         return null;
      } else {
         MessageDestinationInfoRegistry reg = (MessageDestinationInfoRegistry)registry.get(MessageDestinationInfoRegistry.class.getName());
         return reg != null ? reg.get(link) : null;
      }
   }

   private static ModuleContext findContextByName(String name, Collection ctxs) {
      Iterator var2 = ctxs.iterator();

      ModuleContext ctx;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ctx = (ModuleContext)var2.next();
      } while(!name.equals(ctx.getName()));

      return ctx;
   }

   private static ModuleContext findContextByURI(String uri, Collection ctxs) {
      Iterator var2 = ctxs.iterator();

      ModuleContext ctx;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ctx = (ModuleContext)var2.next();
      } while(!uri.equals(ctx.getURI()));

      return ctx;
   }

   private static MessageDestinationInfoRegistry getRegistry(ModuleContext ctx) {
      return (MessageDestinationInfoRegistry)ctx.getRegistry().get(MessageDestinationInfoRegistry.class.getName());
   }

   private static Collection getEjbAndWebMCs(ApplicationContextInternal appCtx) {
      Collection ctxs = new HashSet();
      Module[] var2 = appCtx.getApplicationModules();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module m = var2[var4];
         if (isEjbOrWeb(m.getType())) {
            ctxs.add(appCtx.getModuleContext(m.getId()));
         }
      }

      return ctxs;
   }

   private static boolean isEjbOrWeb(String type) {
      return ModuleType.EJB.toString().equals(type) || ModuleType.WAR.toString().equals(type);
   }
}
