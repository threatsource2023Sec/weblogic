package weblogic.descriptor.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.descriptor.AmbiguousReferenceException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.UnresolvedReferenceException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.Debug;

public class ReferenceManager {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");
   private static BeanScoper beanScoper;
   private final DuplicateChecker duplicateChecker = new DuplicateChecker();
   private final BucketMap unresolved = new BucketMap();
   private final BucketMap referenceable = new BucketMap();
   private final BucketMap resolved = new BucketMap();
   private final Descriptor descriptor;
   private volatile boolean editable;

   public static void registerBeanScoper(BeanScoper scoper) {
      beanScoper = scoper;
   }

   public ReferenceManager(boolean editable, Descriptor descriptor) {
      this.editable = editable;
      this.descriptor = descriptor;
   }

   public void registerUnresolvedReference(String name, Class type, Resolver resolver) {
      Debug.assertion(name != null && name.length() > 0);
      UnresolvedReference ref = new UnresolvedReference(name, type, resolver);
      if (debug.isDebugEnabled()) {
         debug.debug("register ref: " + ref);
      }

      this.unresolved.putInBucket(name, ref);
   }

   public void registerResolvedReference(AbstractDescriptorBean referent, ResolvedReference resolved) {
      if (referent.getDescriptor() != this.descriptor) {
         throw new IllegalArgumentException("Cannot set reference to a bean in different descriptor");
      } else {
         if (referent != null && this.duplicateChecker.registerIfNoDuplicate(resolved) == null) {
            if (debug.isDebugEnabled()) {
               debug.debug("adding resolved reference to : " + referent + " by " + resolved);
            }

            this.resolved.putInBucket(referent, resolved);
         }

      }
   }

   boolean isReference(DescriptorBean bean, int propIndex) {
      Collection referents = this.resolved.allValues();
      Iterator i = referents.iterator();

      ResolvedReference referent;
      do {
         if (!i.hasNext()) {
            return false;
         }

         referent = (ResolvedReference)i.next();
      } while(referent.getBean() != bean || referent.getPropIndex() != propIndex);

      return true;
   }

   public List getResolvedReferences(AbstractDescriptorBean ref) {
      if (!this.editable) {
         return null;
      } else {
         List l = new ArrayList();
         this.findResolvedReferences(ref, l, ref);
         return l.size() == 0 ? null : l;
      }
   }

   private void findResolvedReferences(AbstractDescriptorBean referedBean, List referenceList, AbstractDescriptorBean topLevel) {
      Iterator setIterator = this.resolved.getBucketIterator(referedBean);
      boolean debugEnabled = debug.isDebugEnabled();
      Iterator i;
      if (setIterator != null) {
         if (debugEnabled) {
            debug.debug("finding refs for " + referedBean);
         }

         ResolvedReference _resolved;
         for(; setIterator.hasNext(); referenceList.add(_resolved)) {
            _resolved = (ResolvedReference)setIterator.next();
            if (debugEnabled) {
               debug.debug("adding " + _resolved);
            }
         }

         if (referenceList != null) {
            i = referenceList.iterator();

            label54:
            while(true) {
               ResolvedReference _resolved;
               AbstractDescriptorBean bean;
               do {
                  if (!i.hasNext()) {
                     break label54;
                  }

                  _resolved = (ResolvedReference)i.next();
                  bean = (AbstractDescriptorBean)_resolved.getBean();
                  if (debugEnabled) {
                     debug.debug(bean + " destroyed ? " + bean._isDestroyed() + " isResolvedRefAParent: " + this.isResolvedRefAParent(referedBean, bean, topLevel) + " isResolvedRefNested: " + this.isResolvedRefNested(referedBean, bean, topLevel));
                  }
               } while(!bean._isDestroyed() && _resolved.isValid() && !this.isResolvedRefAParent(referedBean, bean, topLevel) && !this.isResolvedRefNested(referedBean, bean, topLevel));

               if (debugEnabled) {
                  debug.debug("removing old resolved ref " + _resolved + " from BucketMap ");
               }

               this.duplicateChecker.unregister(_resolved);
               this.resolved.removeFromBucket(referedBean, _resolved);
               i.remove();
            }
         }
      }

      i = referedBean._getHelper().getChildren();

      while(i.hasNext()) {
         this.findResolvedReferences((AbstractDescriptorBean)i.next(), referenceList, topLevel);
      }

   }

   private boolean isResolvedRefNested(AbstractDescriptorBean ref, AbstractDescriptorBean bean, AbstractDescriptorBean limit) {
      return this.isResolvedRefAParent(ref, limit, (AbstractDescriptorBean)null) && this.isResolvedRefAParent(bean, limit, (AbstractDescriptorBean)null) && !limit.getDescriptor().getRootBean().equals(limit);
   }

   private boolean isResolvedRefAParent(AbstractDescriptorBean ref, AbstractDescriptorBean bean, AbstractDescriptorBean limit) {
      AbstractDescriptorBean parent = ref;

      assert bean != null;

      while(parent != null) {
         if (bean.equals(parent)) {
            return true;
         }

         if (parent.equals(limit)) {
            break;
         }

         parent = (AbstractDescriptorBean)parent.getParentBean();
      }

      return false;
   }

   public void registerBean(AbstractDescriptorBean bean, boolean referenceable) throws BeanAlreadyExistsException {
      DescriptorBean orig = this.duplicateChecker.registerIfNoDuplicate((DescriptorBean)bean);
      if (orig != null && orig != bean) {
         throw new BeanAlreadyExistsException(orig);
      } else {
         if (referenceable) {
            Object localKey = bean._getKey();
            if (localKey != null && localKey != "") {
               if (debug.isDebugEnabled()) {
                  debug.debug("register " + bean);
               }

               this.referenceable.putInBucket(localKey, bean);
            }
         }

      }
   }

   public boolean unregisterBean(AbstractDescriptorBean bean) {
      return this.unregisterBean(bean, false);
   }

   public boolean unregisterBean(AbstractDescriptorBean bean, boolean checkRefs) {
      this.duplicateChecker.unregister(bean);
      Object localKey = bean._getKey();
      if (localKey != null && localKey != "") {
         List refs = checkRefs ? this.getResolvedReferences(bean) : null;

         assert checkRefs || this.getResolvedReferences(bean) == null;

         if (refs != null && refs.size() != 0) {
            return false;
         } else {
            this.referenceable.removeFromBucket(localKey, bean);
            this.releaseResolvedReferencesFromBean(bean);
            Iterator i = bean._getHelper().getChildren();

            while(i.hasNext()) {
               this.unregisterBean((AbstractDescriptorBean)i.next(), checkRefs);
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public void resolveReferences() throws DescriptorValidateException {
      if (debug.isDebugEnabled()) {
         debug.debug("begin resolving with the following defined: " + this.referenceable.allValues());
      }

      DescriptorValidateException dve = null;
      List keys = this.unresolved.getOrderedKeys();
      Iterator it = keys.iterator();

      label83:
      while(it.hasNext()) {
         String key = (String)it.next();
         Iterator referenceIt = this.unresolved.getBucketIterator(key);

         while(true) {
            while(true) {
               if (!referenceIt.hasNext()) {
                  continue label83;
               }

               UnresolvedReference reference = (UnresolvedReference)referenceIt.next();
               Iterator beanIt = this.referenceable.getBucketIterator(key);
               DescriptorBean[] candidates = this.resolveReference(reference, beanIt);
               if (candidates.length == 1) {
                  reference.resolve(candidates[0]);
                  this.unresolved.removeFromBucket(key, reference);
               } else if (candidates.length > 1) {
                  String unresolvedScope = beanScoper.findScope(reference.getParent());
                  List scopedCandidates = new ArrayList();
                  DescriptorBean[] var11 = candidates;
                  int var12 = candidates.length;

                  for(int var13 = 0; var13 < var12; ++var13) {
                     DescriptorBean candidate = var11[var13];
                     String candidateScope = beanScoper.findScope(candidate);
                     if (unresolvedScope == null && candidateScope == null || unresolvedScope != null && unresolvedScope.equals(candidateScope)) {
                        scopedCandidates.add(candidate);
                     }
                  }

                  if (scopedCandidates.size() == 1) {
                     reference.resolve(scopedCandidates.get(0));
                     this.unresolved.removeFromBucket(key, reference);
                  } else if (scopedCandidates.size() > 1) {
                     if (dve == null) {
                        dve = new DescriptorValidateException();
                     }

                     dve.addException(reference.newAmbiguousException(candidates));
                     this.unresolved.removeFromBucket(key, reference);
                  }
               }
            }
         }
      }

      if (this.unresolved.size() > 0) {
         if (dve == null) {
            dve = new DescriptorValidateException();
         }

         it = this.unresolved.allValues().iterator();

         while(it.hasNext()) {
            UnresolvedReference ref = (UnresolvedReference)it.next();
            dve.addException(ref.newUnresolvedException());
         }
      }

      if (dve != null) {
         throw dve;
      }
   }

   private DescriptorBean[] resolveReference(UnresolvedReference ref, Iterator beanIt) {
      boolean debugEnabled = debug.isDebugEnabled();
      if (debugEnabled) {
         debug.debug("resolving " + ref);
      }

      List candidates = new ArrayList();
      int distance = Integer.MAX_VALUE;
      DescriptorBean refParent = ref.getParent();

      while(beanIt.hasNext()) {
         DescriptorBean bean = (DescriptorBean)beanIt.next();
         if (debugEnabled) {
            debug.debug("checking: " + bean);
         }

         Class beanType = bean.getClass();
         if (ref.getType().isAssignableFrom(beanType)) {
            int d = this.computeGenerationalDistance(refParent, bean.getParentBean());
            if (d == distance) {
               if (debugEnabled) {
                  debug.debug("match for " + ref + ": " + bean);
               }

               candidates.add(bean);
            } else if (d < distance) {
               if (debugEnabled) {
                  debug.debug("match for " + ref + ": " + bean);
               }

               distance = d;
               candidates = new ArrayList();
               candidates.add(bean);
            }
         }
      }

      return (DescriptorBean[])((DescriptorBean[])candidates.toArray(new DescriptorBean[0]));
   }

   private int computeGenerationalDistance(DescriptorBean b1, DescriptorBean b2) {
      int distance = 0;

      DescriptorBean cur;
      for(cur = b1; cur != b2; ++distance) {
         cur = cur.getParentBean();
         if (cur == null && b2 != null) {
            break;
         }
      }

      return cur == null ? Integer.MAX_VALUE : distance;
   }

   private void releaseResolvedReferencesFromBean(AbstractDescriptorBean ref) {
      Iterator resolvedIterator = this.resolved.getOrderedKeys().iterator();
      AbstractDescriptorBean reskey = null;
      ResolvedReference resolvedRef = null;
      HashMap map = new HashMap();

      while(true) {
         Iterator setIterator;
         do {
            if (!resolvedIterator.hasNext()) {
               setIterator = map.entrySet().iterator();

               while(setIterator.hasNext()) {
                  Map.Entry entry = (Map.Entry)setIterator.next();
                  Iterator resRefs = ((List)entry.getValue()).iterator();

                  while(resRefs.hasNext()) {
                     ResolvedReference resRef = (ResolvedReference)resRefs.next();
                     this.duplicateChecker.unregister(resRef);
                     this.resolved.removeFromBucket(entry.getKey(), resRef);
                  }
               }

               return;
            }

            reskey = (AbstractDescriptorBean)resolvedIterator.next();
            setIterator = this.resolved.getBucketIterator(reskey);
         } while(setIterator == null);

         List arrayList = new ArrayList();

         while(setIterator.hasNext()) {
            resolvedRef = (ResolvedReference)setIterator.next();
            if (resolvedRef.getBean() == ref) {
               arrayList.add(resolvedRef);
            }
         }

         if (arrayList.size() > 0) {
            map.put(reskey, arrayList);
         }
      }
   }

   void setEditable(boolean editable) {
      this.editable = editable;
   }

   private static class UnresolvedReference {
      private String name;
      private Resolver resolver;
      private Class type;

      UnresolvedReference(String name, Class type, Resolver resolver) {
         this.name = name;
         this.type = type;
         this.resolver = resolver;
      }

      Class getType() {
         return this.type;
      }

      DescriptorBean getParent() {
         return this.resolver.getParent();
      }

      void resolve(Object referent) {
         this.resolver.resolveReference(referent);
      }

      UnresolvedReferenceException newUnresolvedException() {
         return new UnresolvedReferenceException(this.name, this.resolver.toString());
      }

      AmbiguousReferenceException newAmbiguousException(DescriptorBean[] candidates) {
         return new AmbiguousReferenceException(this.name, this.resolver.toString(), candidates);
      }

      public String toString() {
         return this.name + " by " + this.resolver;
      }

      public int hashCode() {
         return this.resolver.hashCode();
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            return !(o instanceof UnresolvedReference) ? false : this.resolver.equals(((UnresolvedReference)o).resolver);
         }
      }
   }

   public abstract static class Resolver {
      private AbstractDescriptorBean parent;
      private int property;
      public Object m_handback;

      protected Resolver(AbstractDescriptorBean parent, int property) {
         this.parent = parent;
         this.property = property;
      }

      protected Resolver(AbstractDescriptorBean parent, int property, Object handback) {
         this.parent = parent;
         this.property = property;
         this.m_handback = handback;
      }

      public abstract void resolveReference(Object var1);

      public String toString() {
         return this.parent._getQualifiedName(this.property);
      }

      public final int hashCode() {
         return System.identityHashCode(this.parent) ^ this.property;
      }

      public final boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Resolver)) {
            return false;
         } else {
            Resolver other = (Resolver)o;
            if (this.parent != other.parent) {
               return false;
            } else {
               return this.property == other.property;
            }
         }
      }

      AbstractDescriptorBean getParent() {
         return this.parent;
      }

      int getProperty() {
         return this.property;
      }

      public Object getHandbackObject() {
         return this.m_handback;
      }
   }
}
