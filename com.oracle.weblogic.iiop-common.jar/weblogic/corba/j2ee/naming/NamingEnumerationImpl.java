package weblogic.corba.j2ee.naming;

import java.util.NoSuchElementException;
import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.omg.CORBA.SystemException;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingIterator;
import org.omg.CosNaming.NamingContext;
import weblogic.corba.cos.naming.NamingContextAny;

public class NamingEnumerationImpl implements NamingEnumeration {
   private BindingIterator biter;
   private NamingContext ctx;
   private ContextImpl rootCtx;
   private Binding next_one;

   NamingEnumerationImpl(BindingIterator biter, NamingContext ctx, ContextImpl rootCtx) {
      this.biter = biter;
      this.ctx = ctx;
      this.rootCtx = rootCtx;
      this.next_one = null;
   }

   public void close() throws NamingException {
      this.biter.destroy();
   }

   public Object next() throws NamingException {
      if (!this.hasMore()) {
         throw new NoSuchElementException("No more elements");
      } else {
         Object ret = this.next_one;
         this.next_one = null;
         return ret;
      }
   }

   public boolean hasMore() throws NamingException {
      if (this.biter == null) {
         return false;
      } else {
         if (this.next_one == null) {
            BindingHolder bh = new BindingHolder();

            try {
               if (this.biter.next_one(bh)) {
                  if (this.ctx instanceof NamingContextAny) {
                     this.next_one = new Binding(bh.value.binding_name[0].id, this.rootCtx.lookup((NamingContextAny)this.ctx, Utils.nameToWName(bh.value.binding_name)));
                  } else {
                     this.next_one = new Binding(bh.value.binding_name[0].id, this.rootCtx.lookup(this.ctx, bh.value.binding_name));
                  }
               }
            } catch (SystemException var4) {
               NamingException ne = new NamingException("Unhandled error in hasMore()");
               ne.setRootCause(var4);
               throw ne;
            }
         }

         return this.next_one != null;
      }
   }

   public Object nextElement() {
      try {
         return this.next();
      } catch (NamingException var2) {
         throw new NoSuchElementException(var2.getMessage());
      }
   }

   public boolean hasMoreElements() {
      try {
         return this.hasMore();
      } catch (NamingException var2) {
         throw new NoSuchElementException(var2.getMessage());
      }
   }
}
