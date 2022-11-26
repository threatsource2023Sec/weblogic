package weblogic.rjvm;

import weblogic.rmi.provider.BasicServiceContext;
import weblogic.utils.io.Immutable;

class ImmutableServiceContext extends BasicServiceContext implements Immutable {
   public ImmutableServiceContext() {
   }

   public ImmutableServiceContext(int id, Object data, boolean user) {
      super(id, data, user);
   }

   public ImmutableServiceContext(int id, Object data) {
      super(id, data);
   }

   public void setContextData(Object data) {
      this.data = data;
   }

   public int hashCode() {
      return this.data.hashCode();
   }

   public boolean equals(Object other) {
      return other instanceof ImmutableServiceContext ? this.data.equals(((ImmutableServiceContext)other).data) : false;
   }

   public String toString() {
      return "Immutable " + super.toString();
   }
}
