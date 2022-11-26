package org.glassfish.hk2.xml.internal;

import java.beans.VetoableChangeListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlHubCommitMessage;
import org.glassfish.hk2.xml.api.XmlRootCopy;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;
import org.glassfish.hk2.xml.spi.XmlServiceParser;

public class XmlRootHandleImpl implements XmlRootHandle {
   private final XmlServiceImpl parent;
   private final Hub hub;
   private Object root;
   private final ModelImpl rootNode;
   private URI rootURI;
   private final boolean advertised;
   private final boolean advertisedInHub;
   private final DynamicChangeInfo changeControl;

   XmlRootHandleImpl(XmlServiceImpl parent, Hub hub, Object root, ModelImpl rootNode, URI rootURI, boolean advertised, boolean inHub, DynamicChangeInfo changes) {
      this.parent = parent;
      this.hub = hub;
      this.root = root;
      this.rootNode = rootNode;
      this.rootURI = rootURI;
      this.advertised = advertised;
      this.advertisedInHub = inHub;
      this.changeControl = changes;
   }

   public Object getRoot() {
      return this.root;
   }

   public Class getRootClass() {
      return this.rootNode.getOriginalInterfaceAsClass();
   }

   public URI getURI() {
      return this.rootURI;
   }

   public boolean isAdvertisedInLocator() {
      return this.advertised;
   }

   public boolean isAdvertisedInHub() {
      return this.advertisedInHub;
   }

   public void overlay(XmlRootHandle newRoot) {
      if (!(newRoot instanceof XmlRootHandle)) {
         throw new IllegalArgumentException("newRoot must have been created by the same XmlService as this one");
      } else {
         XmlRootHandleImpl newRootImpl = (XmlRootHandleImpl)newRoot;
         if (newRootImpl.isAdvertisedInHub()) {
            throw new IllegalArgumentException("The newRoot must not be advertised in the Hub");
         } else if (newRootImpl.isAdvertisedInLocator()) {
            throw new IllegalArgumentException("The newRoot must not be advertised as hk2 services");
         } else if (this.root == null) {
            throw new IllegalArgumentException("This XmlRootHandle must have a root to be overlayed");
         } else {
            Object newRootRoot = newRootImpl.getRoot();
            if (newRootRoot == null) {
               throw new IllegalArgumentException("The newRoot must have a root to overlay onto this root");
            } else if (!(newRootRoot instanceof BaseHK2JAXBBean)) {
               throw new IllegalArgumentException("The newRoot has a root of an unknown type: " + newRootRoot.getClass().getName());
            } else if (!newRootRoot.getClass().equals(this.root.getClass())) {
               throw new IllegalArgumentException("The two roots must have the same class as this root, instead it is of type " + newRootRoot.getClass().getName());
            } else if (newRootRoot.equals(this.root)) {
               throw new IllegalArgumentException("Cannot overlay the same bean on top of itself");
            } else {
               BaseHK2JAXBBean newRootBase = (BaseHK2JAXBBean)newRootRoot;
               BaseHK2JAXBBean oldRootBase = (BaseHK2JAXBBean)this.root;
               boolean success = false;
               XmlHandleTransaction handle = this.lockForTransaction();

               try {
                  Differences differences = Utilities.getDiff(oldRootBase, newRootBase);
                  if (!differences.getDifferences().isEmpty()) {
                     Utilities.applyDiff(differences, this.changeControl);
                  }

                  success = true;
               } finally {
                  if (success) {
                     handle.commit();
                  } else {
                     handle.abandon();
                  }

               }

            }
         }
      }
   }

   public XmlRootCopy getXmlRootCopy() {
      DynamicChangeInfo copyController = new DynamicChangeInfo(this.changeControl.getJAUtilities(), (Hub)null, false, this.changeControl.getIdGenerator(), (DynamicConfigurationService)null, false, this.changeControl.getServiceLocator());
      this.changeControl.getReadLock().lock();

      XmlRootCopyImpl var14;
      try {
         BaseHK2JAXBBean bean = (BaseHK2JAXBBean)this.root;
         if (bean == null) {
            XmlRootCopyImpl var13 = new XmlRootCopyImpl(this, this.changeControl.getChangeNumber(), (Object)null);
            return var13;
         }

         BaseHK2JAXBBean copy;
         try {
            Map referenceMap = new HashMap();
            List unresolved = new LinkedList();
            copy = Utilities.doCopy(bean, copyController, (BaseHK2JAXBBean)null, this, referenceMap, unresolved);
            Utilities.fillInUnfinishedReferences(referenceMap, unresolved);
         } catch (RuntimeException var10) {
            throw var10;
         } catch (Throwable var11) {
            throw new RuntimeException(var11);
         }

         var14 = new XmlRootCopyImpl(this, this.changeControl.getChangeNumber(), copy);
      } finally {
         this.changeControl.getReadLock().unlock();
      }

      return var14;
   }

   long getRevision() {
      return this.changeControl.getChangeNumber();
   }

   public void addRoot(Object newRoot) {
      this.changeControl.getWriteLock().lock();

      try {
         if (this.root != null) {
            throw new IllegalStateException("An attempt was made to add a root to a handle that already has a root " + this);
         }

         if (!(newRoot instanceof BaseHK2JAXBBean)) {
            throw new IllegalArgumentException("The added bean must be from XmlService.createBean");
         }

         WriteableBeanDatabase wbd = null;
         if (this.advertisedInHub) {
            wbd = this.hub.getWriteableDatabaseCopy();
         }

         DynamicConfiguration config = null;
         if (this.advertised) {
            config = this.parent.getDynamicConfigurationService().createDynamicConfiguration();
         }

         List addedServices = new LinkedList();
         BaseHK2JAXBBean copiedRoot = Utilities._addRoot(this.rootNode, newRoot, this.changeControl, this.parent.getClassReflectionHelper(), wbd, config, addedServices, this);
         if (config != null) {
            config.commit();
         }

         if (wbd != null) {
            wbd.commit(new XmlHubCommitMessage() {
            });
         }

         this.root = copiedRoot;
         ServiceLocator locator = this.parent.getServiceLocator();
         Iterator var7 = addedServices.iterator();

         while(var7.hasNext()) {
            ActiveDescriptor added = (ActiveDescriptor)var7.next();
            locator.getServiceHandle(added).getService();
         }
      } finally {
         this.changeControl.getWriteLock().unlock();
      }

   }

   public void addRoot() {
      this.addRoot(this.parent.createBean(this.rootNode.getOriginalInterfaceAsClass()));
   }

   public Object removeRoot() {
      throw new AssertionError("removeRoot not implemented");
   }

   public Object getReadOnlyRoot(boolean representDefaults) {
      throw new AssertionError("getReadOnlyRoot not implemented");
   }

   DynamicChangeInfo getChangeInfo() {
      return this.changeControl;
   }

   public void addChangeListener(VetoableChangeListener... listeners) {
      this.changeControl.addChangeListener(listeners);
   }

   public void removeChangeListener(VetoableChangeListener... listeners) {
      this.changeControl.removeChangeListener(listeners);
   }

   public List getChangeListeners() {
      return this.changeControl.getChangeListeners();
   }

   public XmlHandleTransaction lockForTransaction() throws IllegalStateException {
      if (this.changeControl == null) {
         throw new IllegalStateException();
      } else {
         return new XmlHandleTransactionImpl(this, this.changeControl);
      }
   }

   public void startValidating() {
      if (this.changeControl == null) {
         throw new IllegalStateException();
      } else {
         Validator validator = this.changeControl.findOrCreateValidator();
         if (this.root != null) {
            Set violations = validator.validate(this.root, new Class[0]);
            if (violations != null && !violations.isEmpty()) {
               throw new ConstraintViolationException(violations);
            }
         }
      }
   }

   public void stopValidating() {
      if (this.changeControl == null) {
         throw new IllegalStateException();
      } else {
         this.changeControl.deleteValidator();
      }
   }

   public boolean isValidating() {
      if (this.changeControl == null) {
         throw new IllegalStateException();
      } else {
         return this.changeControl.findValidator() != null;
      }
   }

   public void marshal(OutputStream outputStream) throws IOException {
      this.marshal(outputStream, (Map)null);
   }

   public void marshal(OutputStream outputStream, Map options) throws IOException {
      if (this.changeControl == null) {
         throw new IllegalStateException("marshall May only be called on a fully initialized root handle " + this);
      } else {
         this.changeControl.getReadLock().lock();

         try {
            XmlServiceParser parser = this.parent.getParser();
            if (parser == null) {
               XmlStreamImpl.marshall(outputStream, this);
               return;
            }

            parser.marshal(outputStream, this, options);
         } finally {
            this.changeControl.getReadLock().unlock();
         }

      }
   }

   public Map getPackageNamespace(Class clazz) {
      return this.parent.getPackageNamespace(clazz);
   }

   public String toString() {
      return "XmlRootHandleImpl(" + this.root + "," + this.rootNode + "," + this.rootURI + "," + System.identityHashCode(this) + ")";
   }
}
