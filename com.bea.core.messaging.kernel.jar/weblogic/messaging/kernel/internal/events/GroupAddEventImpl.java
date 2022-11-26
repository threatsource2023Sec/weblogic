package weblogic.messaging.kernel.internal.events;

import weblogic.messaging.kernel.Group;
import weblogic.messaging.kernel.GroupAddEvent;

public class GroupAddEventImpl extends GroupEventImpl implements GroupAddEvent {
   public GroupAddEventImpl(String subjectName, Group group) {
      super(subjectName, group);
   }
}
