package org.glassfish.hk2.xml.internal;

import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TwoPhaseResource;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.utilities.general.ValidatorUtilities;
import org.glassfish.hk2.xml.api.XmlHubCommitMessage;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class DynamicChangeInfo {
   private final JAUtilities jaUtilities;
   private final ReentrantReadWriteLock treeLock = new ReentrantReadWriteLock();
   private final ReentrantReadWriteLock.WriteLock writeTreeLock;
   private final ReentrantReadWriteLock.ReadLock readTreeLock;
   private long changeNumber;
   private final Hub hub;
   private final XmlServiceImpl idGenerator;
   private final boolean advertiseInLocator;
   private final boolean advertiseInHub;
   private final DynamicConfigurationService dynamicService;
   private final ServiceLocator locator;
   private final LinkedHashSet listeners;
   private final LinkedHashSet participants;
   private XmlRootHandleImpl root;
   private XmlDynamicChange dynamicChange;
   private int changeDepth;
   private boolean globalSuccess;
   private Validator validator;

   DynamicChangeInfo(JAUtilities jaUtilities, Hub hub, boolean advertiseInHub, XmlServiceImpl idGenerator, DynamicConfigurationService dynamicService, boolean advertiseInLocator, ServiceLocator locator) {
      this.writeTreeLock = this.treeLock.writeLock();
      this.readTreeLock = this.treeLock.readLock();
      this.changeNumber = 0L;
      this.listeners = new LinkedHashSet();
      this.participants = new LinkedHashSet();
      this.dynamicChange = null;
      this.changeDepth = 0;
      this.globalSuccess = true;
      this.jaUtilities = jaUtilities;
      this.hub = hub;
      this.advertiseInHub = advertiseInHub;
      this.idGenerator = idGenerator;
      this.advertiseInLocator = advertiseInLocator;
      this.dynamicService = dynamicService;
      this.locator = locator;
   }

   void setRoot(XmlRootHandleImpl root) {
      this.root = root;
   }

   public ReentrantReadWriteLock.ReadLock getReadLock() {
      return this.readTreeLock;
   }

   public ReentrantReadWriteLock.WriteLock getWriteLock() {
      return this.writeTreeLock;
   }

   public long getChangeNumber() {
      this.readTreeLock.lock();

      long var1;
      try {
         var1 = this.changeNumber;
      } finally {
         this.readTreeLock.unlock();
      }

      return var1;
   }

   public void incrementChangeNumber() {
      this.writeTreeLock.lock();

      try {
         ++this.changeNumber;
      } finally {
         this.writeTreeLock.unlock();
      }

   }

   public JAUtilities getJAUtilities() {
      return this.jaUtilities;
   }

   public String getGeneratedId() {
      return this.jaUtilities.getUniqueId();
   }

   public XmlServiceImpl getIdGenerator() {
      return this.idGenerator;
   }

   public XmlDynamicChange startOrContinueChange(BaseHK2JAXBBean participant) {
      ++this.changeDepth;
      if (participant != null) {
         this.participants.add(participant);
      }

      if (this.dynamicChange != null) {
         return this.dynamicChange;
      } else {
         this.globalSuccess = true;
         DynamicConfiguration change = null;
         DynamicConfiguration systemChange = null;
         if (this.dynamicService != null) {
            systemChange = this.dynamicService.createDynamicConfiguration();
            if (this.advertiseInLocator) {
               change = systemChange;
            }
         }

         WriteableBeanDatabase wbd = null;
         if (this.hub != null && this.advertiseInHub) {
            wbd = this.hub.getWriteableDatabaseCopy();
            wbd.setCommitMessage(new XmlHubCommitMessage() {
            });
            if (systemChange != null) {
               systemChange.registerTwoPhaseResources(new TwoPhaseResource[]{wbd.getTwoPhaseResource()});
            }
         }

         this.dynamicChange = new XmlDynamicChange(wbd, change, systemChange);
         return this.dynamicChange;
      }
   }

   public void endOrDeferChange(boolean success) throws MultiException {
      --this.changeDepth;
      if (this.changeDepth < 0) {
         this.changeDepth = 0;
      }

      if (!success) {
         this.globalSuccess = false;
      }

      if (this.changeDepth <= 0) {
         List localParticipants = new ArrayList(this.participants);
         this.participants.clear();
         XmlDynamicChange localDynamicChange = this.dynamicChange;
         this.dynamicChange = null;
         if (localDynamicChange != null) {
            ConstraintViolationException validationException = null;
            if (this.globalSuccess && this.validator != null && this.root != null && this.root.getRoot() != null) {
               Set violations = this.validator.validate(this.root.getRoot(), new Class[0]);
               if (violations != null && !violations.isEmpty()) {
                  validationException = new ConstraintViolationException(violations);
               }
            }

            BaseHK2JAXBBean participant;
            Iterator var7;
            if (this.globalSuccess && validationException == null) {
               var7 = localParticipants.iterator();

               while(var7.hasNext()) {
                  participant = (BaseHK2JAXBBean)var7.next();
                  participant.__activateChange();
               }

               DynamicConfiguration systemChange = localDynamicChange.getSystemDynamicConfiguration();
               WriteableBeanDatabase wbd = localDynamicChange.getBeanDatabase();
               if (systemChange == null && wbd != null) {
                  wbd.commit(new XmlHubCommitMessage() {
                  });
               } else if (systemChange != null) {
                  systemChange.commit();
               }
            } else {
               var7 = localParticipants.iterator();

               while(var7.hasNext()) {
                  participant = (BaseHK2JAXBBean)var7.next();
                  participant.__rollbackChange();
               }

               if (validationException != null) {
                  throw new MultiException(validationException);
               }
            }
         }
      }
   }

   public ServiceLocator getServiceLocator() {
      return this.locator;
   }

   public void addChangeListener(VetoableChangeListener... allAdds) {
      if (allAdds != null) {
         this.writeTreeLock.lock();

         try {
            VetoableChangeListener[] var2 = allAdds;
            int var3 = allAdds.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               VetoableChangeListener add = var2[var4];
               this.listeners.add(add);
            }
         } finally {
            this.writeTreeLock.unlock();
         }

      }
   }

   public void removeChangeListener(VetoableChangeListener... allRemoves) {
      if (allRemoves != null) {
         this.writeTreeLock.lock();

         try {
            VetoableChangeListener[] var2 = allRemoves;
            int var3 = allRemoves.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               VetoableChangeListener remove = var2[var4];
               this.listeners.remove(remove);
            }
         } finally {
            this.writeTreeLock.unlock();
         }

      }
   }

   public List getChangeListeners() {
      this.readTreeLock.lock();

      List var1;
      try {
         var1 = Collections.unmodifiableList(new ArrayList(this.listeners));
      } finally {
         this.readTreeLock.unlock();
      }

      return var1;
   }

   public Validator findOrCreateValidator() {
      this.writeTreeLock.lock();

      Validator var1;
      try {
         if (this.validator == null) {
            this.validator = ValidatorUtilities.getValidator();
            var1 = this.validator;
            return var1;
         }

         var1 = this.validator;
      } finally {
         this.writeTreeLock.unlock();
      }

      return var1;
   }

   public void deleteValidator() {
      this.writeTreeLock.lock();

      try {
         this.validator = null;
      } finally {
         this.writeTreeLock.unlock();
      }

   }

   public Validator findValidator() {
      this.readTreeLock.lock();

      Validator var1;
      try {
         var1 = this.validator;
      } finally {
         this.readTreeLock.unlock();
      }

      return var1;
   }

   public String toString() {
      return "DynamicChangeInfo(inLocator=" + this.advertiseInLocator + ",inHub=" + this.advertiseInHub + "," + System.identityHashCode(this) + ")";
   }
}
