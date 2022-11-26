package weblogic.jms.dd;

public interface DDConstants {
   int ROUND_ROBIN = 0;
   int RANDOM = 1;
   int FORWARDING_POLICY_PARTITIONED = 0;
   int FORWARDING_POLICY_REPLICATED = 1;
   int STICKY_RANDOM = 2;
   int TYPE_QUEUE = 0;
   int TYPE_TOPIC = 1;
   short MEMBER_TYPE_CLUSTERED_DYNAMIC = 1;
   short MEMBER_TYPE_CLUSTERED_STATIC = 2;
   short MEMBER_TYPE_NON_CLUSTERED = 3;
   short MEMBER_TYPE_NON_DD = 4;
   int MEMBER_TYPE_MASK = 7;
}
