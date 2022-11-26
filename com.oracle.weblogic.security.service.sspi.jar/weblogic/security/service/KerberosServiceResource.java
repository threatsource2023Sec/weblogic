package weblogic.security.service;

import weblogic.security.spi.Resource;

public class KerberosServiceResource implements Resource {
   private static final long serialVersionUID = 1210168993042288288L;
   private String serviceName;

   public KerberosServiceResource(String serviceName) {
      this.serviceName = serviceName;
   }

   public long getID() {
      return (long)this.serviceName.hashCode();
   }

   public String[] getKeys() {
      return new String[]{"service-name"};
   }

   public Resource getParentResource() {
      return null;
   }

   public String getType() {
      return "<kerberos service>";
   }

   public String[] getValues() {
      return new String[]{this.serviceName};
   }

   public String toString() {
      return this.serviceName;
   }
}
