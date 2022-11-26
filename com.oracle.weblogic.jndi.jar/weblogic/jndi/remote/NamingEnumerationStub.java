package weblogic.jndi.remote;

import java.io.Externalizable;
import java.util.Enumeration;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.jndi.internal.ExceptionTranslator;
import weblogic.jndi.internal.ThreadEnvironment;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.utils.enumerations.BatchingEnumerationStub;
import weblogic.utils.NestedRuntimeException;

public final class NamingEnumerationStub extends BatchingEnumerationStub implements NamingEnumeration, Externalizable {
   private static final long serialVersionUID = -7038756188207498313L;
   private transient Environment env = null;

   public NamingEnumerationStub(Object delegate) {
      super(delegate);
   }

   public void close() throws NamingException {
   }

   public boolean hasMore() throws NamingException {
      try {
         return this.hasMoreElements();
      } catch (NestedRuntimeException var2) {
         throw ExceptionTranslator.toNamingException(var2.getNestedException());
      }
   }

   public Object next() throws NamingException {
      try {
         return this.nextElement();
      } catch (RemoteRuntimeException var2) {
         throw ExceptionTranslator.toNamingException((Throwable)var2);
      } catch (NestedRuntimeException var3) {
         throw ExceptionTranslator.toNamingException(var3.getNestedException());
      }
   }

   protected Enumeration nextBatch() {
      if (this.env != null) {
         ThreadEnvironment.push(this.env);
      }

      Enumeration var1;
      try {
         var1 = super.nextBatch();
      } finally {
         if (this.env != null) {
            ThreadEnvironment.pop();
         }

      }

      return var1;
   }

   public NamingEnumerationStub() {
      this.env = ThreadEnvironment.get();
   }
}
