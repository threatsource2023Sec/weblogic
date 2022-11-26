package weblogic.messaging.kernel.internal;

import weblogic.utils.collections.AbstractEmbeddedListElement;

final class GroupReference extends AbstractEmbeddedListElement {
   private MessageReference msgRef;
   private GroupImpl group;

   GroupReference(MessageReference msgRef, GroupImpl group) {
      this.msgRef = msgRef;
      this.group = group;
   }

   MessageReference getMessageReference() {
      return this.msgRef;
   }

   GroupImpl getGroup() {
      return this.group;
   }
}
