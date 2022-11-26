package com.oracle.wls.shaded.org.apache.xpath.axes;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMManager;
import com.oracle.wls.shaded.org.apache.xml.utils.NodeVector;
import com.oracle.wls.shaded.org.apache.xpath.NodeSetDTM;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.Vector;

public class NodeSequence extends XObject implements DTMIterator, Cloneable, PathComponent {
   static final long serialVersionUID = 3866261934726581044L;
   protected int m_last = -1;
   protected int m_next = 0;
   private IteratorCache m_cache;
   protected DTMIterator m_iter;
   protected DTMManager m_dtmMgr;

   protected NodeVector getVector() {
      NodeVector nv = this.m_cache != null ? this.m_cache.getVector() : null;
      return nv;
   }

   private IteratorCache getCache() {
      return this.m_cache;
   }

   protected void SetVector(NodeVector v) {
      this.setObject(v);
   }

   public boolean hasCache() {
      NodeVector nv = this.getVector();
      return nv != null;
   }

   private boolean cacheComplete() {
      boolean complete;
      if (this.m_cache != null) {
         complete = this.m_cache.isComplete();
      } else {
         complete = false;
      }

      return complete;
   }

   private void markCacheComplete() {
      NodeVector nv = this.getVector();
      if (nv != null) {
         this.m_cache.setCacheComplete(true);
      }

   }

   public final void setIter(DTMIterator iter) {
      this.m_iter = iter;
   }

   public final DTMIterator getContainedIter() {
      return this.m_iter;
   }

   private NodeSequence(DTMIterator iter, int context, XPathContext xctxt, boolean shouldCacheNodes) {
      this.setIter(iter);
      this.setRoot(context, xctxt);
      this.setShouldCacheNodes(shouldCacheNodes);
   }

   public NodeSequence(Object nodeVector) {
      super(nodeVector);
      if (nodeVector instanceof NodeVector) {
         this.SetVector((NodeVector)nodeVector);
      }

      if (null != nodeVector) {
         this.assertion(nodeVector instanceof NodeVector, "Must have a NodeVector as the object for NodeSequence!");
         if (nodeVector instanceof DTMIterator) {
            this.setIter((DTMIterator)nodeVector);
            this.m_last = ((DTMIterator)nodeVector).getLength();
         }
      }

   }

   private NodeSequence(DTMManager dtmMgr) {
      super(new NodeVector());
      this.m_last = 0;
      this.m_dtmMgr = dtmMgr;
   }

   public NodeSequence() {
   }

   public DTM getDTM(int nodeHandle) {
      DTMManager mgr = this.getDTMManager();
      if (null != mgr) {
         return this.getDTMManager().getDTM(nodeHandle);
      } else {
         this.assertion(false, "Can not get a DTM Unless a DTMManager has been set!");
         return null;
      }
   }

   public DTMManager getDTMManager() {
      return this.m_dtmMgr;
   }

   public int getRoot() {
      return null != this.m_iter ? this.m_iter.getRoot() : -1;
   }

   public void setRoot(int nodeHandle, Object environment) {
      if (null != this.m_iter) {
         XPathContext xctxt = (XPathContext)environment;
         this.m_dtmMgr = xctxt.getDTMManager();
         this.m_iter.setRoot(nodeHandle, environment);
         if (!this.m_iter.isDocOrdered()) {
            if (!this.hasCache()) {
               this.setShouldCacheNodes(true);
            }

            this.runTo(-1);
            this.m_next = 0;
         }
      } else {
         this.assertion(false, "Can not setRoot on a non-iterated NodeSequence!");
      }

   }

   public void reset() {
      this.m_next = 0;
   }

   public int getWhatToShow() {
      return this.hasCache() ? -17 : this.m_iter.getWhatToShow();
   }

   public boolean getExpandEntityReferences() {
      return null != this.m_iter ? this.m_iter.getExpandEntityReferences() : true;
   }

   public int nextNode() {
      NodeVector vec = this.getVector();
      int next;
      if (null != vec) {
         if (this.m_next < vec.size()) {
            next = vec.elementAt(this.m_next);
            ++this.m_next;
            return next;
         }

         if (this.cacheComplete() || -1 != this.m_last || null == this.m_iter) {
            ++this.m_next;
            return -1;
         }
      }

      if (null == this.m_iter) {
         return -1;
      } else {
         next = this.m_iter.nextNode();
         if (-1 != next) {
            if (this.hasCache()) {
               if (this.m_iter.isDocOrdered()) {
                  this.getVector().addElement(next);
                  ++this.m_next;
               } else {
                  int insertIndex = this.addNodeInDocOrder(next);
                  if (insertIndex >= 0) {
                     ++this.m_next;
                  }
               }
            } else {
               ++this.m_next;
            }
         } else {
            this.markCacheComplete();
            this.m_last = this.m_next++;
         }

         return next;
      }
   }

   public int previousNode() {
      if (this.hasCache()) {
         if (this.m_next <= 0) {
            return -1;
         } else {
            --this.m_next;
            return this.item(this.m_next);
         }
      } else {
         int n = this.m_iter.previousNode();
         this.m_next = this.m_iter.getCurrentPos();
         return this.m_next;
      }
   }

   public void detach() {
      if (null != this.m_iter) {
         this.m_iter.detach();
      }

      super.detach();
   }

   public void allowDetachToRelease(boolean allowRelease) {
      if (!allowRelease && !this.hasCache()) {
         this.setShouldCacheNodes(true);
      }

      if (null != this.m_iter) {
         this.m_iter.allowDetachToRelease(allowRelease);
      }

      super.allowDetachToRelease(allowRelease);
   }

   public int getCurrentNode() {
      if (this.hasCache()) {
         int currentIndex = this.m_next - 1;
         NodeVector vec = this.getVector();
         return currentIndex >= 0 && currentIndex < vec.size() ? vec.elementAt(currentIndex) : -1;
      } else {
         return null != this.m_iter ? this.m_iter.getCurrentNode() : -1;
      }
   }

   public boolean isFresh() {
      return 0 == this.m_next;
   }

   public void setShouldCacheNodes(boolean b) {
      if (b) {
         if (!this.hasCache()) {
            this.SetVector(new NodeVector());
         }
      } else {
         this.SetVector((NodeVector)null);
      }

   }

   public boolean isMutable() {
      return this.hasCache();
   }

   public int getCurrentPos() {
      return this.m_next;
   }

   public void runTo(int index) {
      if (-1 == index) {
         int pos = this.m_next;

         while(true) {
            if (-1 == this.nextNode()) {
               this.m_next = pos;
               break;
            }
         }
      } else {
         if (this.m_next == index) {
            return;
         }

         if (this.hasCache() && this.m_next < this.getVector().size()) {
            this.m_next = index;
         } else if (null == this.getVector() && index < this.m_next) {
            while(this.m_next >= index && -1 != this.previousNode()) {
            }
         } else {
            while(this.m_next < index && -1 != this.nextNode()) {
            }
         }
      }

   }

   public void setCurrentPos(int i) {
      this.runTo(i);
   }

   public int item(int index) {
      this.setCurrentPos(index);
      int n = this.nextNode();
      this.m_next = index;
      return n;
   }

   public void setItem(int node, int index) {
      NodeVector vec = this.getVector();
      if (null != vec) {
         int oldNode = vec.elementAt(index);
         if (oldNode != node && this.m_cache.useCount() > 1) {
            IteratorCache newCache = new IteratorCache();

            NodeVector nv;
            try {
               nv = (NodeVector)vec.clone();
            } catch (CloneNotSupportedException var9) {
               var9.printStackTrace();
               RuntimeException rte = new RuntimeException(var9.getMessage());
               throw rte;
            }

            newCache.setVector(nv);
            newCache.setCacheComplete(true);
            this.m_cache = newCache;
            vec = nv;
            super.setObject(nv);
         }

         vec.setElementAt(node, index);
         this.m_last = vec.size();
      } else {
         this.m_iter.setItem(node, index);
      }

   }

   public int getLength() {
      IteratorCache cache = this.getCache();
      if (cache != null) {
         if (cache.isComplete()) {
            NodeVector nv = cache.getVector();
            return nv.size();
         } else if (this.m_iter instanceof NodeSetDTM) {
            return this.m_iter.getLength();
         } else {
            if (-1 == this.m_last) {
               int pos = this.m_next;
               this.runTo(-1);
               this.m_next = pos;
            }

            return this.m_last;
         }
      } else {
         return -1 == this.m_last ? (this.m_last = this.m_iter.getLength()) : this.m_last;
      }
   }

   public DTMIterator cloneWithReset() throws CloneNotSupportedException {
      NodeSequence seq = (NodeSequence)super.clone();
      seq.m_next = 0;
      if (this.m_cache != null) {
         this.m_cache.increaseUseCount();
      }

      return seq;
   }

   public Object clone() throws CloneNotSupportedException {
      NodeSequence clone = (NodeSequence)super.clone();
      if (null != this.m_iter) {
         clone.m_iter = (DTMIterator)this.m_iter.clone();
      }

      if (this.m_cache != null) {
         this.m_cache.increaseUseCount();
      }

      return clone;
   }

   public boolean isDocOrdered() {
      return null != this.m_iter ? this.m_iter.isDocOrdered() : true;
   }

   public int getAxis() {
      if (null != this.m_iter) {
         return this.m_iter.getAxis();
      } else {
         this.assertion(false, "Can not getAxis from a non-iterated node sequence!");
         return 0;
      }
   }

   public int getAnalysisBits() {
      return null != this.m_iter && this.m_iter instanceof PathComponent ? ((PathComponent)this.m_iter).getAnalysisBits() : 0;
   }

   public void fixupVariables(Vector vars, int globalsSize) {
      super.fixupVariables(vars, globalsSize);
   }

   protected int addNodeInDocOrder(int node) {
      this.assertion(this.hasCache(), "addNodeInDocOrder must be done on a mutable sequence!");
      int insertIndex = -1;
      NodeVector vec = this.getVector();
      int size = vec.size();

      int i;
      for(i = size - 1; i >= 0; --i) {
         int child = vec.elementAt(i);
         if (child == node) {
            i = -2;
            break;
         }

         DTM dtm = this.m_dtmMgr.getDTM(node);
         if (!dtm.isNodeAfter(node, child)) {
            break;
         }
      }

      if (i != -2) {
         insertIndex = i + 1;
         vec.insertElementAt(node, insertIndex);
      }

      return insertIndex;
   }

   protected void setObject(Object obj) {
      if (obj instanceof NodeVector) {
         super.setObject(obj);
         NodeVector v = (NodeVector)obj;
         if (this.m_cache != null) {
            this.m_cache.setVector(v);
         } else if (v != null) {
            this.m_cache = new IteratorCache();
            this.m_cache.setVector(v);
         }
      } else if (obj instanceof IteratorCache) {
         IteratorCache cache = (IteratorCache)obj;
         this.m_cache = cache;
         this.m_cache.increaseUseCount();
         super.setObject(cache.getVector());
      } else {
         super.setObject(obj);
      }

   }

   protected IteratorCache getIteratorCache() {
      return this.m_cache;
   }

   private static final class IteratorCache {
      private NodeVector m_vec2 = null;
      private boolean m_isComplete2 = false;
      private int m_useCount2 = 1;

      IteratorCache() {
      }

      private int useCount() {
         return this.m_useCount2;
      }

      private void increaseUseCount() {
         if (this.m_vec2 != null) {
            ++this.m_useCount2;
         }

      }

      private void setVector(NodeVector nv) {
         this.m_vec2 = nv;
         this.m_useCount2 = 1;
      }

      private NodeVector getVector() {
         return this.m_vec2;
      }

      private void setCacheComplete(boolean b) {
         this.m_isComplete2 = b;
      }

      private boolean isComplete() {
         return this.m_isComplete2;
      }
   }
}
