package com.oracle.weblogic.lifecycle.provisioning.core.handlers;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;

@Service
@Component("RCU")
@Handler
@ProvisioningOperationScoped
public class RcuXmlHandler {
   public void handledMethod(@SubscribeTo @RcuXml ProvisioningEvent provisioningEvent) {
      Document doc = provisioningEvent.getDocument();
   }
}
