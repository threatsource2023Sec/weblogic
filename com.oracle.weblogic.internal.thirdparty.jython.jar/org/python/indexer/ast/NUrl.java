package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NUrl extends NNode {
   static final long serialVersionUID = -3488021036061979551L;
   private String url;
   private static int count = 0;

   public NUrl(String url) {
      this.url = url == null ? "" : url;
      this.setStart(count);
      this.setEnd(count++);
   }

   public NUrl(String url, int start, int end) {
      super(start, end);
      this.url = url == null ? "" : url;
   }

   public String getURL() {
      return this.url;
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(Indexer.idx.builtins.BaseStr);
   }

   public String toString() {
      return "<Url:\"" + this.url + "\">";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
