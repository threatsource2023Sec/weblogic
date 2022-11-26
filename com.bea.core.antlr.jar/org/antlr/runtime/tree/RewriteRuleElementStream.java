package org.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class RewriteRuleElementStream {
   protected int cursor;
   protected Object singleElement;
   protected List elements;
   protected boolean dirty;
   protected String elementDescription;
   protected TreeAdaptor adaptor;

   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription) {
      this.cursor = 0;
      this.dirty = false;
      this.elementDescription = elementDescription;
      this.adaptor = adaptor;
   }

   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
      this(adaptor, elementDescription);
      this.add(oneElement);
   }

   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, List elements) {
      this(adaptor, elementDescription);
      this.singleElement = null;
      this.elements = elements;
   }

   public void reset() {
      this.cursor = 0;
      this.dirty = true;
   }

   public void add(Object el) {
      if (el != null) {
         if (this.elements != null) {
            this.elements.add(el);
         } else if (this.singleElement == null) {
            this.singleElement = el;
         } else {
            this.elements = new ArrayList(5);
            this.elements.add(this.singleElement);
            this.singleElement = null;
            this.elements.add(el);
         }
      }
   }

   public Object nextTree() {
      int n = this.size();
      Object el;
      if (!this.dirty && (this.cursor < n || n != 1)) {
         el = this._next();
         return el;
      } else {
         el = this._next();
         return this.dup(el);
      }
   }

   protected Object _next() {
      int n = this.size();
      if (n == 0) {
         throw new RewriteEmptyStreamException(this.elementDescription);
      } else if (this.cursor >= n) {
         if (n == 1) {
            return this.toTree(this.singleElement);
         } else {
            throw new RewriteCardinalityException(this.elementDescription);
         }
      } else if (this.singleElement != null) {
         ++this.cursor;
         return this.toTree(this.singleElement);
      } else {
         Object o = this.toTree(this.elements.get(this.cursor));
         ++this.cursor;
         return o;
      }
   }

   protected abstract Object dup(Object var1);

   protected Object toTree(Object el) {
      return el;
   }

   public boolean hasNext() {
      return this.singleElement != null && this.cursor < 1 || this.elements != null && this.cursor < this.elements.size();
   }

   public int size() {
      int n = 0;
      if (this.singleElement != null) {
         n = 1;
      }

      return this.elements != null ? this.elements.size() : n;
   }

   public String getDescription() {
      return this.elementDescription;
   }
}
