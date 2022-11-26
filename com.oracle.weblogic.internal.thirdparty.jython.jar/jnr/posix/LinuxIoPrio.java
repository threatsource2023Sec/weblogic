package jnr.posix;

public abstract class LinuxIoPrio {
   public static int IOPRIO_WHO_PROCESS = 1;
   public static int IOPRIO_WHO_PGRP = 2;
   public static int IOPRIO_WHO_USER = 3;
   public static int IOPRIO_CLASS_NONE = 0;
   public static int IOPRIO_CLASS_RT = 1;
   public static int IOPRIO_CLASS_BE = 2;
   public static int IOPRIO_CLASS_IDLE = 3;

   public static int IOPRIO_PRIO_VALUE(int _class, int data) {
      return _class << 13 | data;
   }

   public static int IOPRIO_PRIO_CLASS(int mask) {
      return mask >> 13;
   }

   public static int IOPRIO_PRIO_DATA(int mask) {
      return mask & 15;
   }
}
