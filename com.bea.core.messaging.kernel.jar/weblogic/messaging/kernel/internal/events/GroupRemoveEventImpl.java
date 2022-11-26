package weblogic.messaging.kernel.internal.events;

import weblogic.messaging.kernel.Group;
import weblogic.messaging.kernel.GroupRemoveEvent;

public class GroupRemoveEventImpl extends GroupEventImpl implements GroupRemoveEvent {
   public GroupRemoveEventImpl(String subjectName, Group group) {
      super(subjectName, group);
   }
}
