package weblogic.descriptor.internal;

import com.bea.staxb.runtime.ObjectFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import weblogic.descriptor.BasicDescriptorManager;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorCreationListener;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorPreNotifyProcessor;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.LateDescriptorUpdateListener;
import weblogic.descriptor.SecurityService;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.collections.ConcurrentHashMap;

public class DescriptorImpl implements Descriptor, ObjectFactory {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");
   private static final DescriptorPreNotifyProcessor NO_OP_PRENOTIFY_PROCESSOR = new DescriptorPreNotifyProcessor() {
      public void process(DescriptorDiff diff) {
      }
   };
   BasicDescriptorManager dm;
   static final ThreadLocal THREAD_LOCAL = new ThreadLocal() {
      protected Stack initialValue() {
         return new Stack();
      }
   };
   private final List beanListeners = Collections.synchronizedList(new LinkedList());
   private final List descriptorListeners = Collections.synchronizedList(new LinkedList());
   private final ReferenceManager refManager;
   private BeanCreationInterceptor beanCreationInterceptor;
   private final ClassLoader classLoader;
   private boolean modified = false;
   private Map context;
   private DescriptorBean rootBean;
   private DescriptorUpdateEvent descriptorUpdateEvent;
   private final List beanUpdates = Collections.synchronizedList(new LinkedList());
   private final List preparedDescriptorListeners = Collections.synchronizedList(new LinkedList());
   private int updateID;
   private boolean underConstruction;
   private boolean productionMode = false;
   private boolean secureMode = false;
   private String characterEncoding = "UTF-8";
   private List xmlComments;
   private String preferredRoot;
   private volatile boolean editable;
   private Map schemaLocations = null;
   private String versionInfo;

   static DescriptorImpl getThreadLocal() {
      Stack descStack = (Stack)THREAD_LOCAL.get();
      return descStack.empty() ? null : (DescriptorImpl)descStack.peek();
   }

   static void pushThreadLocal(Descriptor descriptor) {
      ((Stack)THREAD_LOCAL.get()).push(descriptor);
   }

   static void popThreadLocal() {
      ((Stack)THREAD_LOCAL.get()).pop();
   }

   public static DescriptorImpl beginConstruction(boolean editable, BasicDescriptorManager bdm) {
      return beginConstruction(editable, bdm, (DescriptorCreationListener)null, (BeanCreationInterceptor)null);
   }

   public static DescriptorImpl beginConstruction(boolean editable, BasicDescriptorManager bdm, DescriptorCreationListener descListener, BeanCreationInterceptor beanInterceptor) {
      Stack descStack = (Stack)THREAD_LOCAL.get();
      DescriptorImpl desc = new DescriptorImpl(editable, beanInterceptor, Thread.currentThread().getContextClassLoader());
      if (descListener != null) {
         descListener.descriptorCreated(desc);
      }

      descStack.push(desc);
      desc.dm = bdm;
      return desc;
   }

   public static Descriptor endConstruction(DescriptorBean root) {
      Stack descStack = (Stack)THREAD_LOCAL.get();
      DescriptorImpl desc = (DescriptorImpl)descStack.pop();
      desc.setModified(false);
      desc.initializeRootBean(root);
      desc.constructionComplete();
      return desc;
   }

   private DescriptorImpl(boolean editable, BeanCreationInterceptor listener, ClassLoader cl) {
      this.editable = editable;
      this.beanCreationInterceptor = listener;
      this.classLoader = cl;
      this.refManager = new ReferenceManager(editable, this);
      this.underConstruction = true;
   }

   public DescriptorBean getRootBean() {
      if (this.rootBean == null) {
         throw new AssertionError("RootBean must be initialized before calling getRootBean().  Failure  of this assertion indicates that no bean is annotated as @root.");
      } else {
         return this.rootBean;
      }
   }

   SecurityService getSecurityService() {
      return this.dm.getSecurityService();
   }

   void initializeRootBean(DescriptorBean rootBean) {
      if (this.rootBean == null || this.rootBean == rootBean) {
         this.rootBean = rootBean;
      }
   }

   public boolean isUnderConstruction() {
      return this.underConstruction;
   }

   private void constructionComplete() {
      this.underConstruction = false;
      this.beanCreationInterceptor = null;
   }

   public void addUpdateListener(DescriptorUpdateListener listener) {
      if (listener instanceof LateDescriptorUpdateListener) {
         this.descriptorListeners.add(listener);
      } else {
         this.descriptorListeners.add(0, listener);
      }

   }

   public void removeUpdateListener(DescriptorUpdateListener listener) {
      this.descriptorListeners.remove(listener);
   }

   public void addBeanUpdateListener(DescriptorBean bean, BeanUpdateListener listener) {
      if (listener == null) {
         throw new AssertionError("Bean Update Listener may not be null");
      } else if (bean == null) {
         throw new AssertionError("Bean may not be null");
      } else {
         BeanListenerRegistration beanReg = new BeanListenerRegistration(bean, listener);
         boolean alreadyRegistered = false;

         try {
            alreadyRegistered = this.beanListeners.contains(beanReg);
         } catch (Exception var6) {
         }

         if (!alreadyRegistered) {
            this.beanListeners.add(beanReg);
         }

      }
   }

   public void removeBeanUpdateListener(DescriptorBean bean, BeanUpdateListener listener) {
      if (listener == null) {
         throw new AssertionError("Bean Update Listener may not be null");
      } else if (bean == null) {
         throw new AssertionError("Bean may not be null");
      } else {
         this.beanListeners.remove(new BeanListenerRegistration(bean, listener));
      }
   }

   public void prepareUpdate(Descriptor proposed) throws DescriptorUpdateRejectedException {
      this.prepareUpdate(proposed, true);
   }

   public void prepareUpdate(Descriptor proposed, boolean failOnNonDynamicChanges) throws DescriptorUpdateRejectedException {
      this.prepareUpdateDiff(proposed, failOnNonDynamicChanges);
   }

   public DescriptorDiff prepareUpdateDiff(Descriptor proposed, boolean failOnNonDynamicChanges) throws DescriptorUpdateRejectedException {
      if (this.descriptorUpdateEvent != null) {
         this.rollbackUpdate();
      }

      DescriptorDiffImpl newDiff = (DescriptorDiffImpl)this.computeDiff(proposed);
      if (failOnNonDynamicChanges && newDiff.hasNonDynamicUpdates()) {
         throw new DescriptorUpdateRejectedException(newDiff.getNonDynamicUpdateMessage());
      } else {
         DescriptorUpdateEvent newDescriptorUpdateEvent = new DescriptorUpdateEvent(this, proposed, this.updateID, newDiff);
         if (newDiff.size() > 0) {
            List newUpdates = new LinkedList();
            List newListeners = new LinkedList();
            boolean success = false;
            boolean var21 = false;

            Iterator var8;
            DescriptorUpdateListener listener;
            try {
               var21 = true;
               var8 = (new LinkedList(this.beanListeners)).iterator();

               while(true) {
                  if (!var8.hasNext()) {
                     var8 = (new LinkedList(this.descriptorListeners)).iterator();

                     while(var8.hasNext()) {
                        listener = (DescriptorUpdateListener)var8.next();
                        listener.prepareUpdate(newDescriptorUpdateEvent);
                        newListeners.add(listener);
                     }

                     success = true;
                     var21 = false;
                     break;
                  }

                  BeanListenerRegistration reg = (BeanListenerRegistration)var8.next();
                  if (reg != null) {
                     BeanUpdateEvent event = newDiff.getBeanUpdateEvent(reg.bean);
                     if (event != null) {
                        Update update = new Update(event, reg.listener);
                        update.prepare();
                        newUpdates.add(update);
                     }
                  }
               }
            } finally {
               if (var21) {
                  if (!success) {
                     Iterator var13 = newUpdates.iterator();

                     while(var13.hasNext()) {
                        Update update = (Update)var13.next();

                        try {
                           update.rollback();
                        } catch (Throwable var23) {
                           if (debug.isDebugEnabled()) {
                              debug.debug("BeanUpdateListener failed in rollback", var23);
                           }
                        }
                     }

                     var13 = newListeners.iterator();

                     while(var13.hasNext()) {
                        DescriptorUpdateListener listener = (DescriptorUpdateListener)var13.next();

                        try {
                           listener.rollbackUpdate(newDescriptorUpdateEvent);
                        } catch (Throwable var22) {
                           if (debug.isDebugEnabled()) {
                              debug.debug("DescriptorListener failed in rollback", var22);
                           }
                        }
                     }
                  }

               }
            }

            if (!success) {
               var8 = newUpdates.iterator();

               while(var8.hasNext()) {
                  Update update = (Update)var8.next();

                  try {
                     update.rollback();
                  } catch (Throwable var25) {
                     if (debug.isDebugEnabled()) {
                        debug.debug("BeanUpdateListener failed in rollback", var25);
                     }
                  }
               }

               var8 = newListeners.iterator();

               while(var8.hasNext()) {
                  listener = (DescriptorUpdateListener)var8.next();

                  try {
                     listener.rollbackUpdate(newDescriptorUpdateEvent);
                  } catch (Throwable var24) {
                     if (debug.isDebugEnabled()) {
                        debug.debug("DescriptorListener failed in rollback", var24);
                     }
                  }
               }
            }

            this.beanUpdates.addAll(newUpdates);
            this.preparedDescriptorListeners.addAll(newListeners);
            ++this.updateID;
         }

         this.descriptorUpdateEvent = newDescriptorUpdateEvent;
         return newDiff;
      }
   }

   public void applyDiff(DescriptorDiff diff) throws DescriptorUpdateFailedException, IllegalStateException {
      this.applyUpdate(diff);
      this.setModified(true);
   }

   public void activateUpdate() throws IllegalStateException, DescriptorUpdateFailedException {
      this.activateUpdate(NO_OP_PRENOTIFY_PROCESSOR);
   }

   public void activateUpdate(DescriptorPreNotifyProcessor processor) throws IllegalStateException, DescriptorUpdateFailedException {
      List localPreparedBeanListeners = new LinkedList(this.beanUpdates);
      this.beanUpdates.clear();
      List localPreparedDescriptorListeners = new LinkedList(this.preparedDescriptorListeners);
      this.preparedDescriptorListeners.clear();
      if (this.descriptorUpdateEvent == null) {
         throw new IllegalStateException("activateUpdate() called without first calling prepareUpdate()");
      } else {
         LinkedList updateExceptions = null;
         boolean var15 = false;

         Exception exception;
         DescriptorUpdateFailedException updateException;
         Iterator var21;
         Exception[] errs;
         int i;
         label341: {
            try {
               var15 = true;
               if (this.descriptorUpdateEvent.getDiff().size() <= 0) {
                  var15 = false;
                  break label341;
               }

               this.applyUpdate(processor);
               Iterator var5 = localPreparedBeanListeners.iterator();

               while(var5.hasNext()) {
                  Update update = (Update)var5.next();

                  try {
                     update.activate();
                  } catch (Throwable var16) {
                     if (updateExceptions == null) {
                        updateExceptions = new LinkedList();
                     }

                     if (var16 instanceof BeanUpdateFailedException) {
                        updateExceptions.add((BeanUpdateFailedException)var16);
                     } else {
                        updateExceptions.add(new BeanUpdateFailedException(var16.getMessage(), var16));
                     }
                  }
               }

               var5 = localPreparedDescriptorListeners.iterator();

               while(var5.hasNext()) {
                  DescriptorUpdateListener listener = (DescriptorUpdateListener)var5.next();

                  try {
                     listener.activateUpdate(this.descriptorUpdateEvent);
                  } catch (Throwable var17) {
                     if (updateExceptions == null) {
                        updateExceptions = new LinkedList();
                     }

                     if (var17 instanceof DescriptorUpdateFailedException) {
                        updateExceptions.add((DescriptorUpdateFailedException)var17);
                     } else {
                        updateExceptions.add(new DescriptorUpdateFailedException(var17.getMessage(), var17));
                     }
                  }
               }

               var15 = false;
            } finally {
               if (var15) {
                  this.descriptorUpdateEvent = null;
                  this.setModified(false);
                  if (updateExceptions != null) {
                     DescriptorUpdateFailedException updateException = new DescriptorUpdateFailedException("one or more registered update listeners reported activation problems.");
                     Iterator var10 = updateExceptions.iterator();

                     while(var10.hasNext()) {
                        Exception exception = (Exception)var10.next();
                        updateException.addException(exception);
                     }

                     if (debug.isDebugEnabled()) {
                        debug.debug("Update listeners reported activation problems.");
                        Exception[] errs = updateException.getExceptionList();

                        for(int i = 0; errs != null && i < errs.length; ++i) {
                           if (errs[i] != null) {
                              errs[i].printStackTrace();
                           }
                        }
                     }

                     throw updateException;
                  }

               }
            }

            this.descriptorUpdateEvent = null;
            this.setModified(false);
            if (updateExceptions == null) {
               return;
            }

            updateException = new DescriptorUpdateFailedException("one or more registered update listeners reported activation problems.");
            var21 = updateExceptions.iterator();

            while(var21.hasNext()) {
               exception = (Exception)var21.next();
               updateException.addException(exception);
            }

            if (debug.isDebugEnabled()) {
               debug.debug("Update listeners reported activation problems.");
               errs = updateException.getExceptionList();

               for(i = 0; errs != null && i < errs.length; ++i) {
                  if (errs[i] != null) {
                     errs[i].printStackTrace();
                  }
               }
            }

            throw updateException;
         }

         this.descriptorUpdateEvent = null;
         this.setModified(false);
         if (updateExceptions != null) {
            updateException = new DescriptorUpdateFailedException("one or more registered update listeners reported activation problems.");
            var21 = updateExceptions.iterator();

            while(var21.hasNext()) {
               exception = (Exception)var21.next();
               updateException.addException(exception);
            }

            if (debug.isDebugEnabled()) {
               debug.debug("Update listeners reported activation problems.");
               errs = updateException.getExceptionList();

               for(i = 0; errs != null && i < errs.length; ++i) {
                  if (errs[i] != null) {
                     errs[i].printStackTrace();
                  }
               }
            }

            throw updateException;
         }
      }
   }

   private void applyUpdate(DescriptorPreNotifyProcessor processor) {
      this.applyUpdate(this.descriptorUpdateEvent.getDiff(), processor);
   }

   private void applyUpdate(DescriptorDiff diff) {
      this.applyUpdate(diff, NO_OP_PRENOTIFY_PROCESSOR);
   }

   private void applyUpdate(DescriptorDiff diff, DescriptorPreNotifyProcessor processor) {
      Stack descStack = (Stack)THREAD_LOCAL.get();
      descStack.push(this);

      try {
         Iterator var4 = diff.iterator();

         while(var4.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)var4.next();
            ((AbstractDescriptorBean)event.getSourceBean())._getHelper().applyUpdate(event);
         }

         this.refManager.resolveReferences();
         this.fixupClones(processor, diff);
      } finally {
         descStack.pop();
         this.setModified(false);
      }
   }

   public void rollbackUpdate() throws IllegalStateException {
      List localPreparedBeanListeners = new LinkedList(this.beanUpdates);
      this.beanUpdates.clear();
      List localPreparedDescriptorListeners = new LinkedList(this.preparedDescriptorListeners);
      this.preparedDescriptorListeners.clear();
      if (this.descriptorUpdateEvent == null) {
         throw new IllegalStateException("rollbackUpdate() called without first calling prepareUpdate()");
      } else {
         try {
            if (this.descriptorUpdateEvent.getDiff().size() <= 0) {
               return;
            }

            Iterator var3 = localPreparedBeanListeners.iterator();

            while(var3.hasNext()) {
               Update update = (Update)var3.next();

               try {
                  update.rollback();
               } catch (Throwable var10) {
                  if (debug.isDebugEnabled()) {
                     debug.debug("BeanUpdateListener rollback threw an unexpected exception", var10);
                  }
               }
            }

            var3 = localPreparedDescriptorListeners.iterator();

            while(var3.hasNext()) {
               DescriptorUpdateListener listener = (DescriptorUpdateListener)var3.next();

               try {
                  listener.rollbackUpdate(this.descriptorUpdateEvent);
               } catch (Throwable var11) {
                  if (debug.isDebugEnabled()) {
                     debug.debug("DescriptorUpdateListener rollback threw an unexpected exception", var11);
                  }
               }
            }
         } finally {
            this.descriptorUpdateEvent = null;
         }

      }
   }

   public DescriptorDiff computeDiff(Descriptor proposed) {
      return new DescriptorDiffImpl(this, proposed, this.updateID);
   }

   public void validate() throws DescriptorValidateException {
      this.refManager.resolveReferences();
      ((AbstractDescriptorBean)this.getRootBean())._getHelper().validateSubTree();
   }

   public void resolveReferences() throws DescriptorValidateException {
      this.refManager.resolveReferences();
   }

   public boolean isEditable() {
      return this.editable;
   }

   public boolean isModified() {
      return this.modified;
   }

   public void setModified(boolean modified) {
      this.modified = modified;
   }

   public Object clone() {
      beginConstruction(this.isEditable(), this.dm);
      DescriptorBean rootBean = null;

      try {
         rootBean = ((AbstractDescriptorBean)this.getRootBean())._createCopy(false, (List)null);
         ((DescriptorImpl)rootBean.getDescriptor()).getReferenceManager().resolveReferences();
         rootBean.getDescriptor().setOriginalVersionInfo(this.versionInfo);
      } finally {
         endConstruction(rootBean);
      }

      return rootBean.getDescriptor();
   }

   public Map getContext() {
      if (this.context == null) {
         this.context = new ConcurrentHashMap();
      }

      return this.context;
   }

   public List getResolvedReferences(DescriptorBean ref) {
      return this.refManager.getResolvedReferences((AbstractDescriptorBean)ref);
   }

   /** @deprecated */
   @Deprecated
   public void toXML(OutputStream out) throws IOException {
      this.dm.writeDescriptorAsXML(this, out);
   }

   public DescriptorBean createRootBean(Class ifc) {
      try {
         String className = DescriptorBeanClassName.toImpl(ifc.getName(), this.isEditable(), this.classLoader);
         return (DescriptorBean)this.createObject(Class.forName(className, true, this.classLoader));
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      }
   }

   public Object createObject(Class implClass) {
      try {
         this.rootBean = (DescriptorBean)implClass.newInstance();
         this.rootBean = this.callBeanCreationInterceptor(this.rootBean, (DescriptorBean)null);
         return this.rootBean;
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      } catch (InstantiationException var4) {
         throw new AssertionError(var4);
      }
   }

   ReferenceManager getReferenceManager() {
      return this.refManager;
   }

   DescriptorBean callBeanCreationInterceptor(DescriptorBean bean, DescriptorBean parent) {
      return this.beanCreationInterceptor != null ? this.beanCreationInterceptor.beanCreated(bean, parent) : bean;
   }

   public void setProductionMode(boolean mode) {
      this.productionMode = mode;
   }

   public boolean getProductionMode() {
      return this.productionMode;
   }

   public void setSecureMode(boolean mode) {
      this.secureMode = mode;
   }

   public boolean getSecureMode() {
      return this.secureMode;
   }

   void addSchemaLocation(String namespace, String location) {
      if (location != null && namespace != null) {
         if (this.schemaLocations == null) {
            this.schemaLocations = new HashMap();
         }

         if (!this.schemaLocations.keySet().contains(namespace)) {
            this.schemaLocations.put(namespace, location);
         }

      }
   }

   public Map getSchemaLocations() {
      return this.schemaLocations;
   }

   private void fixupClones(DescriptorPreNotifyProcessor proc, DescriptorDiff diff) {
      try {
         proc.process(diff);
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public void setCharacterEncoding(String enc) {
      this.characterEncoding = enc;
   }

   public String getCharacterEncoding() {
      return this.characterEncoding;
   }

   public void addXMLComments(List comments) {
      if (this.xmlComments == null) {
         this.xmlComments = new LinkedList();
      }

      this.xmlComments.addAll(comments);
   }

   public String[] getXMLComments() {
      return this.xmlComments == null ? null : (String[])((String[])this.xmlComments.toArray(new String[0]));
   }

   public String getOriginalVersionInfo() {
      return this.versionInfo;
   }

   public void setOriginalVersionInfo(String versionInfo) {
      this.versionInfo = versionInfo;
   }

   public String toString() {
      return "DescriptorImpl(" + System.identityHashCode(this) + ")";
   }

   public String getPreferredRoot() {
      return this.preferredRoot == null && this.rootBean != null && this.rootBean instanceof DescriptorImpl && (DescriptorImpl)this.rootBean != this ? ((DescriptorImpl)this.rootBean).getPreferredRoot() : this.preferredRoot;
   }

   public void setPreferredRoot(String preferredRoot) {
      this.preferredRoot = preferredRoot;
   }

   public void setEditable(boolean editable) {
      this.editable = editable;
      this.refManager.setEditable(editable);
   }

   private static class Update {
      private BeanUpdateEvent event;
      private BeanUpdateListener listener;

      public Update(BeanUpdateEvent event, BeanUpdateListener listener) {
         this.event = event;
         this.listener = listener;
      }

      public void prepare() throws BeanUpdateRejectedException {
         this.listener.prepareUpdate(this.event);
      }

      public void activate() throws BeanUpdateFailedException {
         this.listener.activateUpdate(this.event);
      }

      public void rollback() {
         this.listener.rollbackUpdate(this.event);
      }

      public String toString() {
         return this.event.getProposedBean() + " to " + this.listener;
      }
   }

   private static class BeanListenerRegistration {
      final DescriptorBean bean;
      final BeanUpdateListener listener;

      BeanListenerRegistration(DescriptorBean bean, BeanUpdateListener listener) {
         this.bean = bean;
         this.listener = listener;
      }

      public int hashCode() {
         return this.listener.hashCode() ^ this.bean.hashCode();
      }

      public boolean equals(Object compareMe) {
         if (!(compareMe instanceof BeanListenerRegistration)) {
            return false;
         } else {
            BeanListenerRegistration co = (BeanListenerRegistration)compareMe;
            return this.listener.equals(co.listener) && this.bean.equals(co.bean);
         }
      }
   }
}
