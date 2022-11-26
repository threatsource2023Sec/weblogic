package org.python.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.antlr.runtime.TokenStream;
import org.python.antlr.runtime.misc.IntArray;

public class BufferedTreeNodeStream implements TreeNodeStream {
   public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
   public static final int INITIAL_CALL_STACK_SIZE = 10;
   protected Object down;
   protected Object up;
   protected Object eof;
   protected List nodes;
   protected Object root;
   protected TokenStream tokens;
   TreeAdaptor adaptor;
   protected boolean uniqueNavigationNodes;
   protected int p;
   protected int lastMarker;
   protected IntArray calls;

   public BufferedTreeNodeStream(Object tree) {
      this(new CommonTreeAdaptor(), tree);
   }

   public BufferedTreeNodeStream(TreeAdaptor adaptor, Object tree) {
      this(adaptor, tree, 100);
   }

   public BufferedTreeNodeStream(TreeAdaptor adaptor, Object tree, int initialBufferSize) {
      this.uniqueNavigationNodes = false;
      this.p = -1;
      this.root = tree;
      this.adaptor = adaptor;
      this.nodes = new ArrayList(initialBufferSize);
      this.down = adaptor.create(2, (String)"DOWN");
      this.up = adaptor.create(3, (String)"UP");
      this.eof = adaptor.create(-1, (String)"EOF");
   }

   protected void fillBuffer() {
      this.fillBuffer(this.root);
      this.p = 0;
   }

   public void fillBuffer(Object t) {
      boolean nil = this.adaptor.isNil(t);
      if (!nil) {
         this.nodes.add(t);
      }

      int n = this.adaptor.getChildCount(t);
      if (!nil && n > 0) {
         this.addNavigationNode(2);
      }

      for(int c = 0; c < n; ++c) {
         Object child = this.adaptor.getChild(t, c);
         this.fillBuffer(child);
      }

      if (!nil && n > 0) {
         this.addNavigationNode(3);
      }

   }

   protected int getNodeIndex(Object node) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      for(int i = 0; i < this.nodes.size(); ++i) {
         Object t = this.nodes.get(i);
         if (t == node) {
            return i;
         }
      }

      return -1;
   }

   protected void addNavigationNode(int ttype) {
      Object navNode = null;
      if (ttype == 2) {
         if (this.hasUniqueNavigationNodes()) {
            navNode = this.adaptor.create(2, (String)"DOWN");
         } else {
            navNode = this.down;
         }
      } else if (this.hasUniqueNavigationNodes()) {
         navNode = this.adaptor.create(3, (String)"UP");
      } else {
         navNode = this.up;
      }

      this.nodes.add(navNode);
   }

   public Object get(int i) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      return this.nodes.get(i);
   }

   public Object LT(int k) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      if (k == 0) {
         return null;
      } else if (k < 0) {
         return this.LB(-k);
      } else {
         return this.p + k - 1 >= this.nodes.size() ? this.eof : this.nodes.get(this.p + k - 1);
      }
   }

   public Object getCurrentSymbol() {
      return this.LT(1);
   }

   protected Object LB(int k) {
      if (k == 0) {
         return null;
      } else {
         return this.p - k < 0 ? null : this.nodes.get(this.p - k);
      }
   }

   public Object getTreeSource() {
      return this.root;
   }

   public String getSourceName() {
      return this.getTokenStream().getSourceName();
   }

   public TokenStream getTokenStream() {
      return this.tokens;
   }

   public void setTokenStream(TokenStream tokens) {
      this.tokens = tokens;
   }

   public TreeAdaptor getTreeAdaptor() {
      return this.adaptor;
   }

   public void setTreeAdaptor(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public boolean hasUniqueNavigationNodes() {
      return this.uniqueNavigationNodes;
   }

   public void setUniqueNavigationNodes(boolean uniqueNavigationNodes) {
      this.uniqueNavigationNodes = uniqueNavigationNodes;
   }

   public void consume() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      ++this.p;
   }

   public int LA(int i) {
      return this.adaptor.getType(this.LT(i));
   }

   public int mark() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      this.lastMarker = this.index();
      return this.lastMarker;
   }

   public void release(int marker) {
   }

   public int index() {
      return this.p;
   }

   public void rewind(int marker) {
      this.seek(marker);
   }

   public void rewind() {
      this.seek(this.lastMarker);
   }

   public void seek(int index) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      this.p = index;
   }

   public void push(int index) {
      if (this.calls == null) {
         this.calls = new IntArray();
      }

      this.calls.push(this.p);
      this.seek(index);
   }

   public int pop() {
      int ret = this.calls.pop();
      this.seek(ret);
      return ret;
   }

   public void reset() {
      this.p = 0;
      this.lastMarker = 0;
      if (this.calls != null) {
         this.calls.clear();
      }

   }

   public int size() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      return this.nodes.size();
   }

   public Iterator iterator() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      return new StreamIterator();
   }

   public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
      if (parent != null) {
         this.adaptor.replaceChildren(parent, startChildIndex, stopChildIndex, t);
      }

   }

   public String toTokenTypeString() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < this.nodes.size(); ++i) {
         Object t = this.nodes.get(i);
         buf.append(" ");
         buf.append(this.adaptor.getType(t));
      }

      return buf.toString();
   }

   public String toTokenString(int start, int stop) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      StringBuffer buf = new StringBuffer();

      for(int i = start; i < this.nodes.size() && i <= stop; ++i) {
         Object t = this.nodes.get(i);
         buf.append(" ");
         buf.append(this.adaptor.getToken(t));
      }

      return buf.toString();
   }

   public String toString(Object start, Object stop) {
      System.out.println("toString");
      if (start != null && stop != null) {
         if (this.p == -1) {
            this.fillBuffer();
         }

         if (start instanceof CommonTree) {
            System.out.print("toString: " + ((CommonTree)start).getToken() + ", ");
         } else {
            System.out.println(start);
         }

         if (stop instanceof CommonTree) {
            System.out.println(((CommonTree)stop).getToken());
         } else {
            System.out.println(stop);
         }

         int i;
         if (this.tokens != null) {
            int beginTokenIndex = this.adaptor.getTokenStartIndex(start);
            i = this.adaptor.getTokenStopIndex(stop);
            if (this.adaptor.getType(stop) == 3) {
               i = this.adaptor.getTokenStopIndex(start);
            } else if (this.adaptor.getType(stop) == -1) {
               i = this.size() - 2;
            }

            return this.tokens.toString(beginTokenIndex, i);
         } else {
            Object t = null;

            for(i = 0; i < this.nodes.size(); ++i) {
               t = this.nodes.get(i);
               if (t == start) {
                  break;
               }
            }

            StringBuffer buf = new StringBuffer();

            String text;
            for(t = this.nodes.get(i); t != stop; t = this.nodes.get(i)) {
               text = this.adaptor.getText(t);
               if (text == null) {
                  text = " " + String.valueOf(this.adaptor.getType(t));
               }

               buf.append(text);
               ++i;
            }

            text = this.adaptor.getText(stop);
            if (text == null) {
               text = " " + String.valueOf(this.adaptor.getType(stop));
            }

            buf.append(text);
            return buf.toString();
         }
      } else {
         return null;
      }
   }

   protected class StreamIterator implements Iterator {
      int i = 0;

      public boolean hasNext() {
         return this.i < BufferedTreeNodeStream.this.nodes.size();
      }

      public Object next() {
         int current = this.i++;
         return current < BufferedTreeNodeStream.this.nodes.size() ? BufferedTreeNodeStream.this.nodes.get(current) : BufferedTreeNodeStream.this.eof;
      }

      public void remove() {
         throw new RuntimeException("cannot remove nodes from stream");
      }
   }
}
