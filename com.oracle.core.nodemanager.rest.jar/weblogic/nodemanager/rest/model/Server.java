package weblogic.nodemanager.rest.model;

import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.composite.RestModelImpl;
import org.glassfish.admin.rest.composite.metadata.ReadOnly;

public class Server extends RestModelImpl implements RestModel {
   private String name;
   private String state;
   private boolean registered;

   @ReadOnly
   public String getName() {
      return this.name;
   }

   public void setName(String val) {
      this.name = val;
   }

   @ReadOnly
   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   @ReadOnly
   public boolean getRegistered() {
      return this.registered;
   }

   public void setRegistered(boolean registered) {
      this.registered = registered;
   }
}
