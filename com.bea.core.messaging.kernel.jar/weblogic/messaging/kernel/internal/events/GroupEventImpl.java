package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Group;
import weblogic.messaging.kernel.GroupEvent;

public class GroupEventImpl extends DestinationEventImpl implements GroupEvent {
   private Group group;

   public GroupEventImpl(String subjectName, Group group) {
      super(subjectName, group.getQueue(), (Xid)null);
      this.group = group;
   }

   public Group getGroup() {
      return this.group;
   }
}
