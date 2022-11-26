package weblogic.jms.forwarder.dd.internal;

import java.security.AccessControlException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.jms.cache.CacheContextInfo;
import weblogic.jms.cache.CacheEntry;
import weblogic.jms.common.CDS;
import weblogic.jms.common.DDMemberInformation;
import weblogic.jms.common.DDMembershipChangeEventImpl;
import weblogic.jms.common.DDMembershipChangeListener;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.forwarder.DestinationName;
import weblogic.jms.forwarder.SessionRuntimeContext;
import weblogic.jms.forwarder.dd.DDMembersCache;
import weblogic.jms.forwarder.dd.DDMembersCacheChangeListener;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class DDMembersCacheImpl implements DDMembersCache {
   private static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private static final AbstractSubject kernelID = getKernelIdentity();
   private String cacheName;
   private boolean isForLocalCluster = true;
   private DestinationName destinationName;
   private CacheContextInfo cacheContextInfo;
   private Context providerContext;
   private AbstractSubject subject;
   private SessionRuntimeContext sessionRuntimeContext;
   private static DDMembersCacheManagerImpl ddMembersCacheManager;
   private DDMembershipHandler ddMembershipHandler;
   private HashMap ddEventListenersMap = new HashMap();
   private DDMemberInformation[] ddMemberConfigInformation;
   private Map ddMemberRuntimeInformationMap = new HashMap();
   private String loginUrl;

   private static final AbstractSubject getKernelIdentity() {
      try {
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public DDMembersCacheImpl(SessionRuntimeContext sessionRuntimeContext, DestinationName destinationName, boolean inLocalCluster) {
      this.sessionRuntimeContext = sessionRuntimeContext;
      this.isForLocalCluster = inLocalCluster;
      this.loginUrl = sessionRuntimeContext.getLoginUrl();
      this.setProviderContext(sessionRuntimeContext.getProviderContext());
      this.setSubject(sessionRuntimeContext.getSubject());
      this.setName(destinationName);
      this.setDestinationName(destinationName);
      if (this.ddMembershipHandler == null) {
         this.ddMembershipHandler = new DDMembershipHandler();
      }

   }

   private void setName(DestinationName destinationName) {
      this.setName("[ProviderUrl = " + (this.isForLocalCluster ? " Local Cluster" : (this.cacheContextInfo == null ? this.loginUrl : this.cacheContextInfo.getProviderUrl())) + "]" + destinationName);
   }

   public String getName() {
      return this.cacheName;
   }

   public void setName(String cacheName) {
      this.cacheName = cacheName;
   }

   public synchronized CacheEntry[] getCacheEntries() {
      if (this.ddMembershipHandler == null) {
         this.ddMembershipHandler = new DDMembershipHandler();
      }

      return new CacheEntry[0];
   }

   public void setCacheContextInfo(CacheContextInfo cacheContextInfo) {
      this.cacheContextInfo = cacheContextInfo;
   }

   public CacheContextInfo getCacheContextInfo() {
      return this.cacheContextInfo;
   }

   public void setProviderContext(Context context) {
      this.providerContext = context;
   }

   public Context getProviderContext() {
      return this.sessionRuntimeContext != null ? this.sessionRuntimeContext.getProviderContext() : this.providerContext;
   }

   public void setSubject(AbstractSubject subject) {
      this.subject = subject;
   }

   public boolean isForLocalCluster() {
      return this.isForLocalCluster;
   }

   public DDMemberInformation[] getDDMemberConfigInformation() {
      CacheEntry[] cacheEntry = this.getCacheEntries();
      return (DDMemberInformation[])((DDMemberInformation[])cacheEntry);
   }

   public Map getDDMemberRuntimeInformation() {
      return this.ddMemberRuntimeInformationMap;
   }

   public void removeDDMembersCacheChangeListener(DDMembersCacheChangeListener ddMembersCacheChangeListener) {
      synchronized(this) {
         if (this.ddMembershipHandler != null) {
            this.ddMembershipHandler.close();
         }

         this.ddEventListenersMap.remove(ddMembersCacheChangeListener.getId());
      }
   }

   public void addDDMembersCacheChangeListener(DDMembersCacheChangeListener ddMembersCacheChangeListener) {
      DDMembersCacheChangeListener origDDMembersCacheChangeListener = null;
      int numDDInfos = false;
      DDMemberInformation[] ddMemberInformationClone = null;
      int numDDInfos;
      synchronized(this) {
         origDDMembersCacheChangeListener = (DDMembersCacheChangeListener)this.ddEventListenersMap.get(ddMembersCacheChangeListener.getId());
         if (this.ddMemberConfigInformation == null) {
            if (origDDMembersCacheChangeListener == null) {
               this.ddEventListenersMap.put(ddMembersCacheChangeListener.getId(), ddMembersCacheChangeListener);
            }

            return;
         }

         numDDInfos = this.ddMemberConfigInformation.length;
         ddMemberInformationClone = (DDMemberInformation[])((DDMemberInformation[])this.ddMemberConfigInformation.clone());
      }

      for(int i = 0; i < numDDInfos; ++i) {
         ddMembersCacheChangeListener.onCacheEntryAdd(ddMemberInformationClone[i]);
      }

      synchronized(this) {
         if (origDDMembersCacheChangeListener == null) {
            this.ddEventListenersMap.put(ddMembersCacheChangeListener.getId(), ddMembersCacheChangeListener);
         }

      }
   }

   public void setDestinationName(DestinationName destinationName) {
      this.destinationName = destinationName;
   }

   public DestinationName getDestinationName() {
      return this.destinationName;
   }

   static {
      ddMembersCacheManager = DDMembersCacheManagerImpl.ddMembersCacheManager;
   }

   private class DDMembershipHandler implements DDMembershipChangeListener {
      public DDMembershipHandler() {
         synchronized(DDMembersCacheImpl.this) {
            DDMembersCacheImpl.this.ddMemberConfigInformation = CDS.getCDS().getDDMembershipInformation(this);
         }
      }

      void close() {
         CDS.getCDS().unregisterDDMembershipChangeListener(this);
      }

      public void onDDMembershipChange(DDMembershipChangeEventImpl event) {
         Iterator itr = null;
         synchronized(DDMembersCacheImpl.this) {
            itr = ((HashMap)DDMembersCacheImpl.this.ddEventListenersMap.clone()).values().iterator();
         }

         DDMemberInformation[] removedDdMemberInformation = event.getRemovedDDMemberInformation();
         DDMemberInformation[] addedDdMemberInformation = event.getAddedDDMemberInformation();

         while(itr.hasNext()) {
            try {
               DDMembersCacheChangeListener ddMembersCacheChangeListener = (DDMembersCacheChangeListener)itr.next();
               if (removedDdMemberInformation != null) {
                  ddMembersCacheChangeListener.onCacheEntryRemove(removedDdMemberInformation);
               }

               if (addedDdMemberInformation != null) {
                  ddMembersCacheChangeListener.onCacheEntryAdd(addedDdMemberInformation);
               }
            } catch (Throwable var6) {
               var6.printStackTrace();
            }
         }

      }

      public String getDestinationName() {
         return DDMembersCacheImpl.this.destinationName.getJNDIName();
      }

      public String getProviderURL() {
         return DDMembersCacheImpl.this.loginUrl;
      }

      public Context getInitialContext() throws NamingException {
         if (DDMembersCacheImpl.this.sessionRuntimeContext != null && !DDMembersCacheImpl.this.sessionRuntimeContext.getForceResolveDNS()) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug(this + ": CACHED getInitialContext: " + DDMembersCacheImpl.this.sessionRuntimeContext.getProviderContext() + " with subject: " + DDMembersCacheImpl.this.sessionRuntimeContext.getSubject() + " username: " + DDMembersCacheImpl.this.sessionRuntimeContext.getUsername() + " forceResolveDNS: false  URL: " + this.getProviderURL());
            }

            DDMembersCacheImpl.this.providerContext = DDMembersCacheImpl.this.sessionRuntimeContext.getProviderContext();
            if (DDMembersCacheImpl.this.providerContext != null) {
               return DDMembersCacheImpl.this.providerContext;
            }
         }

         String loginURL = this.getProviderURL();
         String username = null;
         String password = null;
         boolean forceResolveDNS = false;
         long jndiTimeout = 0L;
         if (DDMembersCacheImpl.this.sessionRuntimeContext != null) {
            password = DDMembersCacheImpl.this.sessionRuntimeContext.getPassword();
            username = DDMembersCacheImpl.this.sessionRuntimeContext.getUsername();
            forceResolveDNS = DDMembersCacheImpl.this.sessionRuntimeContext.getForceResolveDNS();
            jndiTimeout = DDMembersCacheImpl.this.sessionRuntimeContext.getJndiTimeout();
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug(this + ": NEW PATH getInitialContext URL: " + loginURL + " username: " + username + " pass: XXXX forceResolveDNS: " + forceResolveDNS);
         }

         Hashtable env = new Hashtable();
         env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         if (loginURL != null) {
            env.put("java.naming.provider.url", loginURL);
         }

         if (username != null) {
            env.put("java.naming.security.principal", username);
         }

         if (password != null) {
            env.put("java.naming.security.credentials", password);
         }

         if (forceResolveDNS) {
            System.setProperty("weblogic.jndi.forceResolveDNSName", "true");
         }

         env.put("weblogic.jndi.connectTimeout", jndiTimeout);
         env.put("weblogic.jndi.responseReadTimeout", jndiTimeout);
         DDMembersCacheImpl.this.providerContext = new InitialContext(env);
         DDMembersCacheImpl.this.subject = SubjectManager.getSubjectManager().getCurrentSubject(DDMembersCacheImpl.kernelID);
         if (DDMembersCacheImpl.this.sessionRuntimeContext != null) {
            DDMembersCacheImpl.this.sessionRuntimeContext.setSubject(DDMembersCacheImpl.this.subject);
            DDMembersCacheImpl.this.sessionRuntimeContext.setProviderContext(DDMembersCacheImpl.this.providerContext);
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug(this + ": getInitialcontext , return providerContext " + DDMembersCacheImpl.this.providerContext + " subject " + DDMembersCacheImpl.this.subject);
         }

         return DDMembersCacheImpl.this.providerContext;
      }

      public AbstractSubject getSubject() {
         return DDMembersCacheImpl.this.sessionRuntimeContext != null ? DDMembersCacheImpl.this.sessionRuntimeContext.getSubject() : DDMembersCacheImpl.this.subject;
      }

      public void onFailure(String ddJNDIName, Exception ex) {
      }

      public Context getEnvContext() {
         return null;
      }
   }
}
