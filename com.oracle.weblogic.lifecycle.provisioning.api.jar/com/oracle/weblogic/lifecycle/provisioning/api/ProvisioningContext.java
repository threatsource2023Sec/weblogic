package com.oracle.weblogic.lifecycle.provisioning.api;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;
import org.w3c.dom.Document;

@Contract
public interface ProvisioningContext {
   String getCurrentProvisioningComponentName();

   void publish(Document var1);

   Map getContextData();

   @Contract
   public interface Serializer {
      void serialize(ProvisioningContext var1) throws ProvisioningException;

      ProvisioningContext deserialize(ProvisioningOperation var1) throws ProvisioningException;
   }
}
