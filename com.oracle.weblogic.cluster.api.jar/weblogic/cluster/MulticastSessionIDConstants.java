package weblogic.cluster;

public interface MulticastSessionIDConstants {
   String NO_RESOURCE_GROUP = "NO_RESOURCE_GROUP";
   MulticastSessionId HEARTBEAT_SENDER_ID = new MulticastSessionId("0", "NO_RESOURCE_GROUP", "HeartBeats");
   MulticastSessionId ATTRIBUTE_MANAGER_ID = new MulticastSessionId("0", "NO_RESOURCE_GROUP", "ClusterMemberAttributes");
   MulticastSessionId ANNOUNCEMENT_MANAGER_ID = new MulticastSessionId("0", "NO_RESOURCE_GROUP", "JNDI tree");
   MulticastSessionId MEMBER_MANAGER_ID = new MulticastSessionId("0", "NO_RESOURCE_GROUP", "MembershipData");
   MulticastSessionId CUSTOM_MULTICAST_SESSION_ID = new MulticastSessionId("0", "NO_RESOURCE_GROUP", "Other info");
}
