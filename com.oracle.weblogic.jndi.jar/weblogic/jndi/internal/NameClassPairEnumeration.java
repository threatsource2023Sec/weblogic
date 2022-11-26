package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public class NameClassPairEnumeration implements NamingEnumeration, Externalizable {
   static final long serialVersionUID = 3554395159027162363L;
   protected NameClassPair[] list;
   private transient int index;

   public NameClassPairEnumeration(NameClassPair[] list) {
      this.list = list;
      this.index = 0;
   }

   public boolean hasMore() {
      return this.list != null && this.index < this.list.length;
   }

   public Object next() throws NoSuchElementException {
      try {
         return this.list[this.index++];
      } catch (ArrayIndexOutOfBoundsException var2) {
         throw new NoSuchElementException();
      }
   }

   public boolean hasMoreElements() {
      return this.hasMore();
   }

   public Object nextElement() throws NoSuchElementException {
      return this.next();
   }

   public void close() {
   }

   public NameClassPairEnumeration() {
      this.index = 0;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      ((WLObjectOutput)oo).writeObjectWL(this.list);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.list = (NameClassPair[])((NameClassPair[])((WLObjectInput)oi).readObjectWL());
   }
}
