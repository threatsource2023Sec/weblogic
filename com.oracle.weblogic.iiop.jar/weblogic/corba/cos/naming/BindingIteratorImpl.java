package weblogic.corba.cos.naming;

import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NameComponent;

public final class BindingIteratorImpl extends _BindingIteratorImplBase {
   private NamingEnumeration nenum;

   BindingIteratorImpl(NamingEnumeration nenum) {
      this.nenum = nenum;
   }

   public boolean next_one(BindingHolder holder) {
      holder.value = new Binding(new NameComponent[0], BindingType.nobject);

      try {
         if (!this.nenum.hasMore()) {
            return false;
         } else {
            javax.naming.Binding binding = (javax.naming.Binding)this.nenum.next();
            binding.setRelative(true);
            if (binding.getObject() instanceof Context) {
               holder.value.binding_type = BindingType.ncontext;
            } else {
               holder.value.binding_type = BindingType.nobject;
            }

            holder.value.binding_name = new NameComponent[]{new NameComponent(binding.getName(), "")};
            return true;
         }
      } catch (NamingException var3) {
         return false;
      }
   }

   public boolean next_n(int how_many, BindingListHolder holder) {
      try {
         return getBindings(this.nenum, how_many, holder);
      } catch (NamingException var4) {
         return false;
      }
   }

   static boolean getBindings(NamingEnumeration nenum, int how_many, BindingListHolder holder) throws NamingException {
      if (!nenum.hasMore()) {
         holder.value = new Binding[0];
         return false;
      } else {
         ArrayList bindings = new ArrayList(how_many);

         int i;
         for(i = 0; i < how_many && nenum.hasMore(); ++i) {
            javax.naming.Binding binding = (javax.naming.Binding)nenum.next();
            binding.setRelative(true);
            Binding cosbinding = new Binding();
            if (binding.getObject() instanceof Context) {
               cosbinding.binding_type = BindingType.ncontext;
            } else {
               cosbinding.binding_type = BindingType.nobject;
            }

            cosbinding.binding_name = new NameComponent[]{new NameComponent(binding.getName(), "")};
            bindings.add(cosbinding);
         }

         holder.value = (Binding[])((Binding[])bindings.toArray(new Binding[i]));
         return true;
      }
   }

   public void destroy() {
      try {
         this.nenum.close();
      } catch (NamingException var2) {
      }

   }
}
