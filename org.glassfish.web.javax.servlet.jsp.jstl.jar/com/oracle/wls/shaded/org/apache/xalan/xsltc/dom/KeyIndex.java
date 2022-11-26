package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.DOMEnhancedForDTM;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.Hashtable;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.util.IntegerArray;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMAxisIteratorBase;
import java.util.StringTokenizer;

public class KeyIndex extends DTMAxisIteratorBase {
   private Hashtable _index;
   private int _currentDocumentNode = -1;
   private Hashtable _rootToIndexMap = new Hashtable();
   private IntegerArray _nodes = null;
   private DOM _dom;
   private DOMEnhancedForDTM _enhancedDOM;
   private int _markedPosition = 0;
   private static final IntegerArray EMPTY_NODES = new IntegerArray(0);

   public KeyIndex(int dummy) {
   }

   public void setRestartable(boolean flag) {
   }

   public void add(Object value, int node, int rootNode) {
      if (this._currentDocumentNode != rootNode) {
         this._currentDocumentNode = rootNode;
         this._index = new Hashtable();
         this._rootToIndexMap.put(new Integer(rootNode), this._index);
      }

      IntegerArray nodes = (IntegerArray)this._index.get(value);
      if (nodes == null) {
         nodes = new IntegerArray();
         this._index.put(value, nodes);
         nodes.add(node);
      } else if (node != nodes.at(nodes.cardinality() - 1)) {
         nodes.add(node);
      }

   }

   /** @deprecated */
   public void merge(KeyIndex other) {
      if (other != null) {
         if (other._nodes != null) {
            if (this._nodes == null) {
               this._nodes = (IntegerArray)other._nodes.clone();
            } else {
               this._nodes.merge(other._nodes);
            }
         }

      }
   }

   /** @deprecated */
   public void lookupId(Object value) {
      this._nodes = null;
      StringTokenizer values = new StringTokenizer((String)value, " \n\t");

      while(values.hasMoreElements()) {
         String token = (String)values.nextElement();
         IntegerArray nodes = (IntegerArray)this._index.get(token);
         if (nodes == null && this._enhancedDOM != null && this._enhancedDOM.hasDOMSource()) {
            nodes = this.getDOMNodeById(token);
         }

         if (nodes != null) {
            if (this._nodes == null) {
               nodes = (IntegerArray)nodes.clone();
               this._nodes = nodes;
            } else {
               this._nodes.merge(nodes);
            }
         }
      }

   }

   public IntegerArray getDOMNodeById(String id) {
      IntegerArray nodes = null;
      if (this._enhancedDOM != null) {
         int ident = this._enhancedDOM.getElementById(id);
         if (ident != -1) {
            Integer root = new Integer(this._enhancedDOM.getDocument());
            Hashtable index = (Hashtable)this._rootToIndexMap.get(root);
            if (index == null) {
               index = new Hashtable();
               this._rootToIndexMap.put(root, index);
            } else {
               nodes = (IntegerArray)index.get(id);
            }

            if (nodes == null) {
               nodes = new IntegerArray();
               index.put(id, nodes);
            }

            nodes.add(this._enhancedDOM.getNodeHandle(ident));
         }
      }

      return nodes;
   }

   /** @deprecated */
   public void lookupKey(Object value) {
      IntegerArray nodes = (IntegerArray)this._index.get(value);
      this._nodes = nodes != null ? (IntegerArray)nodes.clone() : null;
      this._position = 0;
   }

   /** @deprecated */
   public int next() {
      if (this._nodes == null) {
         return -1;
      } else {
         return this._position < this._nodes.cardinality() ? this._dom.getNodeHandle(this._nodes.at(this._position++)) : -1;
      }
   }

   public int containsID(int node, Object value) {
      String string = (String)value;
      int rootHandle = this._dom.getAxisIterator(19).setStartNode(node).next();
      Hashtable index = (Hashtable)this._rootToIndexMap.get(new Integer(rootHandle));
      StringTokenizer values = new StringTokenizer(string, " \n\t");

      IntegerArray nodes;
      do {
         if (!values.hasMoreElements()) {
            return 0;
         }

         String token = (String)values.nextElement();
         nodes = null;
         if (index != null) {
            nodes = (IntegerArray)index.get(token);
         }

         if (nodes == null && this._enhancedDOM != null && this._enhancedDOM.hasDOMSource()) {
            nodes = this.getDOMNodeById(token);
         }
      } while(nodes == null || nodes.indexOf(node) < 0);

      return 1;
   }

   public int containsKey(int node, Object value) {
      int rootHandle = this._dom.getAxisIterator(19).setStartNode(node).next();
      Hashtable index = (Hashtable)this._rootToIndexMap.get(new Integer(rootHandle));
      if (index == null) {
         return 0;
      } else {
         IntegerArray nodes = (IntegerArray)index.get(value);
         return nodes != null && nodes.indexOf(node) >= 0 ? 1 : 0;
      }
   }

   /** @deprecated */
   public DTMAxisIterator reset() {
      this._position = 0;
      return this;
   }

   /** @deprecated */
   public int getLast() {
      return this._nodes == null ? 0 : this._nodes.cardinality();
   }

   /** @deprecated */
   public int getPosition() {
      return this._position;
   }

   /** @deprecated */
   public void setMark() {
      this._markedPosition = this._position;
   }

   /** @deprecated */
   public void gotoMark() {
      this._position = this._markedPosition;
   }

   /** @deprecated */
   public DTMAxisIterator setStartNode(int start) {
      if (start == -1) {
         this._nodes = null;
      } else if (this._nodes != null) {
         this._position = 0;
      }

      return this;
   }

   /** @deprecated */
   public int getStartNode() {
      return 0;
   }

   /** @deprecated */
   public boolean isReverse() {
      return false;
   }

   /** @deprecated */
   public DTMAxisIterator cloneIterator() {
      KeyIndex other = new KeyIndex(0);
      other._index = this._index;
      other._rootToIndexMap = this._rootToIndexMap;
      other._nodes = this._nodes;
      other._position = this._position;
      return other;
   }

   public void setDom(DOM dom) {
      this._dom = dom;
      if (dom instanceof DOMEnhancedForDTM) {
         this._enhancedDOM = (DOMEnhancedForDTM)dom;
      } else if (dom instanceof DOMAdapter) {
         DOM idom = ((DOMAdapter)dom).getDOMImpl();
         if (idom instanceof DOMEnhancedForDTM) {
            this._enhancedDOM = (DOMEnhancedForDTM)idom;
         }
      }

   }

   public KeyIndexIterator getKeyIndexIterator(Object keyValue, boolean isKeyCall) {
      return keyValue instanceof DTMAxisIterator ? this.getKeyIndexIterator((DTMAxisIterator)keyValue, isKeyCall) : this.getKeyIndexIterator(BasisLibrary.stringF(keyValue, this._dom), isKeyCall);
   }

   public KeyIndexIterator getKeyIndexIterator(String keyValue, boolean isKeyCall) {
      return new KeyIndexIterator(keyValue, isKeyCall);
   }

   public KeyIndexIterator getKeyIndexIterator(DTMAxisIterator keyValue, boolean isKeyCall) {
      return new KeyIndexIterator(keyValue, isKeyCall);
   }

   public class KeyIndexIterator extends MultiValuedNodeHeapIterator {
      private IntegerArray _nodes;
      private DTMAxisIterator _keyValueIterator;
      private String _keyValue;
      private boolean _isKeyIterator;

      KeyIndexIterator(String keyValue, boolean isKeyIterator) {
         this._isKeyIterator = isKeyIterator;
         this._keyValue = keyValue;
      }

      KeyIndexIterator(DTMAxisIterator keyValues, boolean isKeyIterator) {
         this._keyValueIterator = keyValues;
         this._isKeyIterator = isKeyIterator;
      }

      protected IntegerArray lookupNodes(int root, String keyValue) {
         IntegerArray result = null;
         Hashtable index = (Hashtable)KeyIndex.this._rootToIndexMap.get(new Integer(root));
         if (!this._isKeyIterator) {
            StringTokenizer values = new StringTokenizer(keyValue, " \n\t");

            while(values.hasMoreElements()) {
               String token = (String)values.nextElement();
               IntegerArray nodes = null;
               if (index != null) {
                  nodes = (IntegerArray)index.get(token);
               }

               if (nodes == null && KeyIndex.this._enhancedDOM != null && KeyIndex.this._enhancedDOM.hasDOMSource()) {
                  nodes = KeyIndex.this.getDOMNodeById(token);
               }

               if (nodes != null) {
                  if (result == null) {
                     result = (IntegerArray)nodes.clone();
                  } else {
                     result.merge(nodes);
                  }
               }
            }
         } else if (index != null) {
            result = (IntegerArray)index.get(keyValue);
         }

         return result;
      }

      public DTMAxisIterator setStartNode(int node) {
         this._startNode = node;
         if (this._keyValueIterator != null) {
            this._keyValueIterator = this._keyValueIterator.setStartNode(node);
         }

         this.init();
         return super.setStartNode(node);
      }

      public int next() {
         int nodeHandle;
         if (this._nodes != null) {
            if (this._position < this._nodes.cardinality()) {
               nodeHandle = this.returnNode(this._nodes.at(this._position));
            } else {
               nodeHandle = -1;
            }
         } else {
            nodeHandle = super.next();
         }

         return nodeHandle;
      }

      public DTMAxisIterator reset() {
         if (this._nodes == null) {
            this.init();
         } else {
            super.reset();
         }

         return this.resetPosition();
      }

      protected void init() {
         super.init();
         this._position = 0;
         int rootHandle = KeyIndex.this._dom.getAxisIterator(19).setStartNode(this._startNode).next();
         if (this._keyValueIterator == null) {
            this._nodes = this.lookupNodes(rootHandle, this._keyValue);
            if (this._nodes == null) {
               this._nodes = KeyIndex.EMPTY_NODES;
            }
         } else {
            DTMAxisIterator keyValues = this._keyValueIterator.reset();
            int retrievedKeyValueIdx = false;
            boolean foundNodes = false;
            this._nodes = null;

            for(int keyValueNode = keyValues.next(); keyValueNode != -1; keyValueNode = keyValues.next()) {
               String keyValue = BasisLibrary.stringF(keyValueNode, KeyIndex.this._dom);
               IntegerArray nodes = this.lookupNodes(rootHandle, keyValue);
               if (nodes != null) {
                  if (!foundNodes) {
                     this._nodes = nodes;
                     foundNodes = true;
                  } else {
                     if (this._nodes != null) {
                        this.addHeapNode(new KeyIndexHeapNode(this._nodes));
                        this._nodes = null;
                     }

                     this.addHeapNode(new KeyIndexHeapNode(nodes));
                  }
               }
            }

            if (!foundNodes) {
               this._nodes = KeyIndex.EMPTY_NODES;
            }
         }

      }

      public int getLast() {
         return this._nodes != null ? this._nodes.cardinality() : super.getLast();
      }

      public int getNodeByPosition(int position) {
         int node = -1;
         if (this._nodes != null) {
            if (position > 0) {
               if (position <= this._nodes.cardinality()) {
                  this._position = position;
                  node = this._nodes.at(position - 1);
               } else {
                  this._position = this._nodes.cardinality();
               }
            }
         } else {
            node = super.getNodeByPosition(position);
         }

         return node;
      }

      protected class KeyIndexHeapNode extends MultiValuedNodeHeapIterator.HeapNode {
         private IntegerArray _nodes;
         private int _position = 0;
         private int _markPosition = -1;

         KeyIndexHeapNode(IntegerArray nodes) {
            super();
            this._nodes = nodes;
         }

         public int step() {
            if (this._position < this._nodes.cardinality()) {
               this._node = this._nodes.at(this._position);
               ++this._position;
            } else {
               this._node = -1;
            }

            return this._node;
         }

         public MultiValuedNodeHeapIterator.HeapNode cloneHeapNode() {
            KeyIndexHeapNode clone = (KeyIndexHeapNode)super.cloneHeapNode();
            clone._nodes = this._nodes;
            clone._position = this._position;
            clone._markPosition = this._markPosition;
            return clone;
         }

         public void setMark() {
            this._markPosition = this._position;
         }

         public void gotoMark() {
            this._position = this._markPosition;
         }

         public boolean isLessThan(MultiValuedNodeHeapIterator.HeapNode heapNode) {
            return this._node < heapNode._node;
         }

         public MultiValuedNodeHeapIterator.HeapNode setStartNode(int node) {
            return this;
         }

         public MultiValuedNodeHeapIterator.HeapNode reset() {
            this._position = 0;
            return this;
         }
      }
   }
}
