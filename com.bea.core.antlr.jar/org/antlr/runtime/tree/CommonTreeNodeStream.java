package org.antlr.runtime.tree;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.misc.IntArray;
import org.antlr.runtime.misc.LookaheadStream;

public class CommonTreeNodeStream extends LookaheadStream implements TreeNodeStream, PositionTrackingStream {
   public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
   public static final int INITIAL_CALL_STACK_SIZE = 10;
   protected Object root;
   protected TokenStream tokens;
   TreeAdaptor adaptor;
   protected TreeIterator it;
   protected IntArray calls;
   protected boolean hasNilRoot;
   protected int level;
   protected Object previousLocationElement;

   public CommonTreeNodeStream(Object tree) {
      this(new CommonTreeAdaptor(), tree);
   }

   public CommonTreeNodeStream(TreeAdaptor adaptor, Object tree) {
      this.hasNilRoot = false;
      this.level = 0;
      this.root = tree;
      this.adaptor = adaptor;
      this.it = new TreeIterator(adaptor, this.root);
   }

   public void reset() {
      super.reset();
      this.it.reset();
      this.hasNilRoot = false;
      this.level = 0;
      this.previousLocationElement = null;
      if (this.calls != null) {
         this.calls.clear();
      }

   }

   public Object nextElement() {
      Object t = this.it.next();
      if (t == this.it.up) {
         --this.level;
         if (this.level == 0 && this.hasNilRoot) {
            return this.it.next();
         }
      } else if (t == this.it.down) {
         ++this.level;
      }

      if (this.level == 0 && this.adaptor.isNil(t)) {
         this.hasNilRoot = true;
         t = this.it.next();
         ++this.level;
         t = this.it.next();
      }

      return t;
   }

   public Object remove() {
      Object result = super.remove();
      if (this.p == 0 && this.hasPositionInformation(this.prevElement)) {
         this.previousLocationElement = this.prevElement;
      }

      return result;
   }

   public boolean isEOF(Object o) {
      return this.adaptor.getType(o) == -1;
   }

   public void setUniqueNavigationNodes(boolean uniqueNavigationNodes) {
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

   public Object get(int i) {
      throw new UnsupportedOperationException("Absolute node indexes are meaningless in an unbuffered stream");
   }

   public int LA(int i) {
      return this.adaptor.getType(this.LT(i));
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

   public Object getKnownPositionElement(boolean allowApproximateLocation) {
      Object node = this.data.get(this.p);
      if (this.hasPositionInformation(node)) {
         return node;
      } else if (!allowApproximateLocation) {
         return null;
      } else {
         for(int index = this.p - 1; index >= 0; --index) {
            node = this.data.get(index);
            if (this.hasPositionInformation(node)) {
               return node;
            }
         }

         return this.previousLocationElement;
      }
   }

   public boolean hasPositionInformation(Object node) {
      Token token = this.adaptor.getToken(node);
      if (token == null) {
         return false;
      } else {
         return token.getLine() > 0;
      }
   }

   public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
      if (parent != null) {
         this.adaptor.replaceChildren(parent, startChildIndex, stopChildIndex, t);
      }

   }

   public String toString(Object start, Object stop) {
      return "n/a";
   }

   public String toTokenTypeString() {
      this.reset();
      StringBuilder buf = new StringBuilder();
      Object o = this.LT(1);

      for(int type = this.adaptor.getType(o); type != -1; type = this.adaptor.getType(o)) {
         buf.append(" ");
         buf.append(type);
         this.consume();
         o = this.LT(1);
      }

      return buf.toString();
   }
}
