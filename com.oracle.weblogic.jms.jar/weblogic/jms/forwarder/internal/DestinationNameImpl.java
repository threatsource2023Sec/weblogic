package weblogic.jms.forwarder.internal;

import weblogic.jms.forwarder.DestinationName;

public class DestinationNameImpl implements DestinationName {
   private String configName;
   private String jndiName;

   public DestinationNameImpl(String ddConfigName, String ddJNDIName) {
      this.configName = ddConfigName;
      this.jndiName = ddJNDIName;
   }

   public String getConfigName() {
      return this.configName;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DestinationNameImpl)) {
         return false;
      } else {
         DestinationNameImpl ddName = (DestinationNameImpl)o;
         if (this.configName != null) {
            if (!this.configName.equals(ddName.configName)) {
               return false;
            }
         } else if (ddName.configName != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.configName != null ? this.configName.hashCode() : 0;
      return result;
   }

   public String toString() {
      return "<JNDIName = " + this.jndiName + " : ConfigName = " + this.configName + ">";
   }
}
