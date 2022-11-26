package com.oracle.weblogic.lifecycle.provisioning.api;

import org.w3c.dom.Document;

public class ProvisioningEvent {
   private final Document document;

   public ProvisioningEvent(Document document) {
      this.document = document;
   }

   public final Document getDocument() {
      return this.document;
   }

   public String toString() {
      return String.valueOf(this.getDocument());
   }
}
