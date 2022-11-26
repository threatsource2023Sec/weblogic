package com.bea.common.ldap.notification;

import com.bea.common.ldap.DistinguishedNameId;
import com.bea.common.ldap.ObjectIdConverter;
import com.bea.common.ldap.ParserFactory;
import com.bea.common.store.service.RemoteCommitEvent;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.RemoteCommitProvider;
import com.bea.common.store.service.config.StoreServicePropertiesConfigurator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import javax.jdo.PersistenceManagerFactory;
import kodo.jdo.KodoPersistenceManagerFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.meta.ClassMetaData;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.ldap.EmbeddedLDAPChange;
import weblogic.ldap.EmbeddedLDAPChangeListener;

public class LDAPRemoteCommitProvider implements RemoteCommitProvider {
   private static final String HEADER = "LDAPRemoteCommitProvider: ";
   private String baseDN;
   private boolean initialized = false;
   private ArrayList ldapListeners;

   public void initialize(Properties storeProperties, Properties notificationProperties, StoreServicePropertiesConfigurator sspc) {
      StringBuilder dn = new StringBuilder();
      if (notificationProperties != null) {
         String realmName = notificationProperties.getProperty("realmName");
         if (realmName != null) {
            dn.append("ou=");
            dn.append(realmName);
         }

         String bdn = notificationProperties.getProperty("baseDN");
         if (bdn != null) {
            if (dn.length() > 0) {
               dn.append(',');
            }

            dn.append(bdn);
         }
      }

      this.baseDN = dn.toString();
      this.ldapListeners = new ArrayList();
      this.initialized = true;
   }

   public void shutdown() {
      if (this.ldapListeners != null) {
         EmbeddedLDAP ldap = EmbeddedLDAP.getEmbeddedLDAP();

         for(int i = 0; i < this.ldapListeners.size(); ++i) {
            LDAPChangeListener ldapListener = (LDAPChangeListener)this.ldapListeners.get(i);
            if (ldapListener != null) {
               ldapListener.enabled = false;
               ldap.unregisterChangeListener(this.baseDN, ldapListener);
            }
         }

         this.ldapListeners.clear();
      }

   }

   public void addRemoteCommitListener(PersistenceManagerFactory pmf, Class pc, RemoteCommitListener listener) {
      if (!this.initialized) {
         throw new IllegalStateException();
      } else {
         OpenJPAConfiguration config = ((KodoPersistenceManagerFactory)pmf).getConfiguration();
         ClassMetaData cmd = config.getMetaDataRepositoryInstance().getMetaData(pc, pc.getClassLoader(), true);
         Log log = config.getLog("openjpa.Runtime");
         if (log.isTraceEnabled()) {
            log.trace("LDAPRemoteCommitProvider: initiating notification for class type: " + cmd.getDescribedType().getSimpleName() + " at base DN: " + this.baseDN);
         }

         Class oidc = cmd.getObjectIdType();
         ParserFactory fac = new ParserFactory(log);
         ObjectIdConverter oc = fac.getObjectIdConverter(cmd);
         LDAPChangeListener ldapListener = new LDAPChangeListener(listener, cmd, log, oidc, oc);
         this.ldapListeners.add(ldapListener);
         EmbeddedLDAP ldap = EmbeddedLDAP.getEmbeddedLDAP();
         ldap.registerChangeListener(this.baseDN, ldapListener);
      }
   }

   private static class SimpleRemoteCommitEvent implements RemoteCommitEvent {
      private Collection deletedObjectIds;
      private Collection addedObjectIds;
      private Collection updatedObjectIds;

      public SimpleRemoteCommitEvent(Collection deletedObjectIds, Collection addedObjectIds, Collection updatedObjectIds) {
         this.deletedObjectIds = deletedObjectIds;
         this.addedObjectIds = addedObjectIds;
         this.updatedObjectIds = updatedObjectIds;
      }

      public Collection getDeletedObjectIds() {
         return this.deletedObjectIds;
      }

      public Collection getAddedObjectIds() {
         return this.addedObjectIds;
      }

      public Collection getUpdatedObjectIds() {
         return this.updatedObjectIds;
      }
   }

   private static class LDAPChangeListener implements EmbeddedLDAPChangeListener {
      boolean enabled = true;
      private RemoteCommitListener listener;
      private ClassMetaData cmd;
      private Log log;
      private Class oidc;
      private ObjectIdConverter oc;

      public LDAPChangeListener(RemoteCommitListener listener, ClassMetaData cmd, Log log, Class oidc, ObjectIdConverter oc) {
         this.listener = listener;
         this.cmd = cmd;
         this.log = log;
         this.oidc = oidc;
         this.oc = oc;
      }

      public void entryChanged(EmbeddedLDAPChange change) {
         if (this.enabled) {
            String dn = change.getEntryName();
            DistinguishedNameId dni = new DistinguishedNameId(this.cmd, dn);
            Object oid = this.oc.convertDistinguishedNameId(dni);
            if (this.log.isTraceEnabled()) {
               if (oid != null) {
                  this.log.trace("LDAPRemoteCommitProvider: notification for: " + oid);
               } else {
                  this.log.trace("LDAPRemoteCommitProvider: suppressing notification because dn (" + dn + ") is not instance of class type: " + this.cmd.getDescribedType().getSimpleName());
               }
            }

            if (oid != null && this.oidc.isAssignableFrom(oid.getClass())) {
               switch (change.getChangeType()) {
                  case 1:
                     this.listener.afterCommit(new SimpleRemoteCommitEvent((Collection)null, Collections.singleton(oid), (Collection)null));
                     break;
                  case 2:
                  case 4:
                     this.listener.afterCommit(new SimpleRemoteCommitEvent((Collection)null, (Collection)null, Collections.singleton(oid)));
                     break;
                  case 3:
                     this.listener.afterCommit(new SimpleRemoteCommitEvent(Collections.singleton(oid), (Collection)null, (Collection)null));
               }
            }

         }
      }
   }
}
