package weblogic.iiop.messages;

public interface LocateStatusType {
   int UNKNOWN_OBJECT = 0;
   int OBJECT_HERE = 1;
   int OBJECT_FORWARD = 2;
   int OBJECT_FORWARD_PERM = 3;
   int LOC_SYSTEM_EXCEPTION = 4;
   int LOC_NEEDS_ADDRESSING_MODE = 5;
}
