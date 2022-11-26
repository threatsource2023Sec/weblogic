package weblogic.cluster;

import java.util.EventObject;

public class ClusterMembersChangeEvent extends EventObject {
   private static final long serialVersionUID = 1894491991090401626L;
   public static final int ADD = 0;
   public static final int REMOVE = 1;
   public static final int UPDATE = 2;
   public static final int DISCOVER = 3;
   private int action;
   private ClusterMemberInfo member;

   public int getAction() {
      return this.action;
   }

   public ClusterMemberInfo getClusterMemberInfo() {
      return this.member;
   }

   public ClusterMembersChangeEvent(Object source, int action, ClusterMemberInfo member) {
      super(source);
      this.action = action;
      this.member = member;
   }
}
