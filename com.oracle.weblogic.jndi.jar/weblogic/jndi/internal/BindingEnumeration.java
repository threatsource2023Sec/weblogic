package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.naming.Binding;

public final class BindingEnumeration extends NameClassPairEnumeration implements Externalizable {
   static final long serialVersionUID = -1874230391047383407L;

   public BindingEnumeration(Binding[] list) {
      super(list);
   }

   public BindingEnumeration() {
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeInt(this.list.length);

      for(int i = 0; i < this.list.length; ++i) {
         Binding b = (Binding)this.list[i];
         oo.writeObject(b.getName());
         oo.writeObject(b.getClassName());

         try {
            if (b instanceof LazyBinding) {
               oo.writeObject(((LazyBinding)b).getRawObject());
            } else {
               oo.writeObject(b.getObject());
            }
         } catch (IOException var5) {
            oo.writeObject("non-serializable");
         }
      }

   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.list = new Binding[oi.readInt()];
      int j = 0;

      for(int i = 0; i < this.list.length; ++i) {
         String name = (String)oi.readObject();
         String className = (String)oi.readObject();

         try {
            Object o = oi.readObject();
            this.list[j++] = new Binding(name, className, o);
         } catch (ClassNotFoundException var7) {
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("Skipping over incompatible object at name=" + name + ", className=" + className);
            }
         }
      }

      if (j < this.list.length) {
         Object[] t = this.list;
         this.list = new Binding[j];
         System.arraycopy(t, 0, this.list, 0, j);
      }

   }
}
