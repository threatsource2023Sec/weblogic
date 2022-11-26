package weblogic.xml.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import weblogic.utils.collections.CombinedIterator;

public class CompositeNamespaceContext implements NamespaceContext {
   private final ArrayList contexts;

   public CompositeNamespaceContext(Collection contexts) {
      this.contexts = new ArrayList(contexts.size());
      Iterator itr = contexts.iterator();

      while(itr.hasNext()) {
         NamespaceContext namespaceContext = (NamespaceContext)itr.next();
         if (namespaceContext == null) {
            throw new IllegalArgumentException("null contexts not allowed");
         }

         contexts.add(namespaceContext);
      }

   }

   public CompositeNamespaceContext(NamespaceContext ctx1, NamespaceContext ctx2) {
      if (ctx1 != null && ctx2 != null) {
         this.contexts = new ArrayList(2);
         this.contexts.add(ctx1);
         this.contexts.add(ctx2);
      } else {
         throw new IllegalArgumentException("null contexts not allowed");
      }
   }

   public String getNamespaceURI(String prefix) {
      ArrayList ctx_list = this.contexts;
      int i = 0;

      for(int len = ctx_list.size(); i < len; ++i) {
         NamespaceContext nsctx = (NamespaceContext)ctx_list.get(i);
         String ns = nsctx.getNamespaceURI(prefix);
         if (ns != null) {
            return ns;
         }
      }

      return null;
   }

   public String getPrefix(String namespaceURI) {
      ArrayList ctx_list = this.contexts;
      int i = 0;

      for(int len = ctx_list.size(); i < len; ++i) {
         NamespaceContext nsctx = (NamespaceContext)ctx_list.get(i);
         String pfx = nsctx.getPrefix(namespaceURI);
         if (pfx != null) {
            return pfx;
         }
      }

      return null;
   }

   public Iterator getPrefixes(String namespaceURI) {
      ArrayList ctx_list = this.contexts;
      int len = ctx_list.size();
      Iterator[] itrs = new Iterator[len];

      for(int i = 0; i < len; ++i) {
         NamespaceContext nsctx = (NamespaceContext)ctx_list.get(i);
         itrs[i] = nsctx.getPrefixes(namespaceURI);
      }

      return new CombinedIterator(itrs);
   }
}
