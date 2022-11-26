package org.python.antlr.runtime.tree;

import org.python.antlr.runtime.TokenStream;
import org.python.antlr.runtime.misc.IntArray;
import org.python.antlr.runtime.misc.LookaheadStream;

public class CommonTreeNodeStream extends LookaheadStream implements TreeNodeStream {
   public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
   public static final int INITIAL_CALL_STACK_SIZE = 10;
   protected Object root;
   protected TokenStream tokens;
   TreeAdaptor adaptor;
   protected TreeIterator it;
   protected IntArray calls;
   protected boolean hasNilRoot;
   protected int level;

   public CommonTreeNodeStream(Object tree) {
      this(new CommonTreeAdaptor(), tree);
   }

   public CommonTreeNodeStream(TreeAdaptor adaptor, Object tree) {
      super(adaptor.create(-1, (String)"EOF"));
      this.hasNilRoot = false;
      this.level = 0;
      this.root = tree;
      this.adaptor = adaptor;
      this.it = new TreeIterator(this.root);
      this.it.eof = this.eof;
   }

   public void reset() {
      super.reset();
      this.it.reset();
      this.hasNilRoot = false;
      this.level = 0;
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
      StringBuffer buf = new StringBuffer();
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
