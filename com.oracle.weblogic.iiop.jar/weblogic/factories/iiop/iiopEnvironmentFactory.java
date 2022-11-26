package weblogic.factories.iiop;

import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Remote;
import javax.naming.Context;
import javax.naming.NamingException;
import org.omg.CORBA.Object;
import weblogic.corba.cos.naming.NamingContextImpl;
import weblogic.corba.idl.DelegateFactory;
import weblogic.corba.idl.NamingServiceInitialReferenceDelegateImpl;
import weblogic.corba.j2ee.naming.ContextImpl;
import weblogic.corba.j2ee.naming.EndPointList;
import weblogic.corba.j2ee.naming.EndPointSelector;
import weblogic.corba.j2ee.naming.InitialContextFactoryImpl;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.corba.j2ee.naming.ORBInfo;
import weblogic.jndi.Environment;
import weblogic.jndi.api.ThreadEnvironmentService;
import weblogic.jndi.spi.EnvironmentFactory;
import weblogic.rjvm.ClientServerURL;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.channels.ChannelService;

public class iiopEnvironmentFactory implements EnvironmentFactory {
   public Context getInitialContext(Environment env, String contextName) throws NamingException {
      ThreadEnvironmentContext ignored = new ThreadEnvironmentContext(env);
      Throwable var4 = null;

      Context var6;
      try {
         Context ctx = this.selectInitialContext(env, this.getProviderURL(env));
         if (contextName != null) {
            ctx = (Context)ctx.lookup(contextName);
         }

         var6 = ctx;
      } catch (Throwable var15) {
         var4 = var15;
         throw var15;
      } finally {
         if (ignored != null) {
            if (var4 != null) {
               try {
                  ignored.close();
               } catch (Throwable var14) {
                  var4.addSuppressed(var14);
               }
            } else {
               ignored.close();
            }
         }

      }

      return var6;
   }

   private Context selectInitialContext(Environment env, String providerUrl) throws NamingException {
      if (!this.isLocalUrl(providerUrl)) {
         return InitialContextFactoryImpl.getInitialContext(env.getProperties(), providerUrl);
      } else {
         NamingContextImpl partitionRootNamingContext = this.getPartitionRootNamingContext(providerUrl);
         ContextImpl context = new ContextImpl(env.getProperties(), (ORBInfo)null, partitionRootNamingContext);
         InitialContextFactoryImpl.establishThreadEnvironment(env.getProperties(), context);
         return context;
      }
   }

   private NamingContextImpl getPartitionRootNamingContext(String providerUrl) throws NamingException {
      try {
         NamingContextImpl context = NamingServiceInitialReferenceDelegateImpl.getPartitionRootNamingContext(providerUrl);
         context._set_delegate(DelegateFactory.createDelegate(context.getIOR()));
         return context;
      } catch (IOException var4) {
         NamingException ne = new NamingException("Unable to obtain IOR for naming context");
         ne.setRootCause(var4);
         throw ne;
      }
   }

   protected boolean isLocalUrl(String providerUrl) throws NamingException {
      try {
         return (new ClientServerURL(providerUrl)).isHostedByLocalRJVM();
      } catch (MalformedURLException | UnknownHostException var3) {
         throw new NamingException(var3.toString());
      }
   }

   private String getProviderURL(Environment env) {
      String providerUrl = env.getClusterProviderUrl();
      if (providerUrl == null) {
         providerUrl = env.getProviderUrl();
      }

      return providerUrl;
   }

   public Remote getInitialReference(Environment env, Class implClass) throws NamingException {
      ThreadEnvironmentContext ignored = new ThreadEnvironmentContext(env);
      Throwable var4 = null;

      Remote var8;
      try {
         String providerUrl = this.getProviderURL(env);
         EndPointSelector selector = EndPointList.createEndPointList(providerUrl).getStartingEndPoint();

         try {
            if (ChannelService.isLocalChannel(InetAddress.getByName(selector.getHost()), selector.getPort())) {
               Remote var22 = ServerHelper.getLocalInitialReference(implClass);
               return var22;
            }
         } catch (IOException var19) {
         }

         Object obj = ORBHelper.getORBHelper().getORBInitialReference(providerUrl, env.getProperties(), implClass.getName());
         var8 = (Remote)PortableRemoteObject.narrow(obj, Remote.class);
      } catch (Throwable var20) {
         var4 = var20;
         throw var20;
      } finally {
         if (ignored != null) {
            if (var4 != null) {
               try {
                  ignored.close();
               } catch (Throwable var18) {
                  var4.addSuppressed(var18);
               }
            } else {
               ignored.close();
            }
         }

      }

      return var8;
   }

   public Context getInitialContext(Environment environment, String contextName, HostID hostID) throws NamingException {
      return this.getInitialContext(environment, contextName);
   }

   static class ThreadEnvironmentContext implements Closeable {
      ThreadEnvironmentService service = (ThreadEnvironmentService)GlobalServiceLocator.getServiceLocator().getService(ThreadEnvironmentService.class, new Annotation[0]);

      ThreadEnvironmentContext(Environment env) {
         this.service.push(env);
      }

      public void close() {
         this.service.pop();
      }
   }
}
