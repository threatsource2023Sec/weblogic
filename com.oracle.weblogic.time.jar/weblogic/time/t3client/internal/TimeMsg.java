package weblogic.time.t3client.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.time.t3client.ScheduledTrigger;

public final class TimeMsg implements Externalizable {
   private static final long serialVersionUID = 5819526650654284539L;
   public static final byte CMD_TRIGGER_SCHEDULE_T = 1;
   public static final byte CMD_TRIGGER_SCHEDULE_F = 2;
   public static final byte CMD_TRIGGER_CANCEL = 3;
   public static final byte CMD_TRIGGER_SET_DAEMON_T = 4;
   public static final byte CMD_TRIGGER_SET_DAEMON_F = 5;
   public static final byte CMD_CLOCK_PING = 6;
   public static final boolean verbose = false;
   static final String[] cmd_names = new String[]{"UNKNOWN", "Schedule Daemon Trigger", "Schedule Trigger", "Cancel Trigger", "Set Daemon True", "Set Daemon False", "Clock Ping"};
   public static final String PROXYCLASS = "weblogic.time.t3client.internal.TimeProxy";
   protected byte cmd;
   protected ScheduledTrigger sch;
   protected int key;
   protected byte tindex;
   protected long t1 = 0L;
   protected long t2 = 0L;
   protected long t3 = 0L;
   protected long t4 = 0L;

   public byte cmd() {
      return this.cmd;
   }

   public String toString() {
      String stuff = "(unknown)";
      switch (this.cmd) {
         case 1:
         case 2:
            stuff = " sch = " + this.sch.toString();
            break;
         case 3:
            stuff = " key = " + this.key;
         case 4:
         case 5:
         default:
            break;
         case 6:
            stuff = " ts  = \n\tt1=" + (this.t1 != 0L ? (new Date(this.t1)).toString() : "-") + "/" + this.t1 + "\n\tt2=" + (this.t2 != 0L ? (new Date(this.t2)).toString() : "-") + "/" + this.t2 + "\n\tt3=" + (this.t3 != 0L ? (new Date(this.t3)).toString() : "-") + "/" + this.t3 + "\n\tt4=" + (this.t4 != 0L ? (new Date(this.t4)).toString() : "-") + "/" + this.t4;
      }

      return "TimeMsg: " + cmd_names[this.cmd] + stuff;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      WLObjectInput sis = (WLObjectInput)in;
      this.cmd = sis.readByte();
      switch (this.cmd) {
         case 1:
         case 2:
            this.sch = (ScheduledTrigger)sis.readObjectWL();
            break;
         case 3:
            this.key = sis.readInt();
            break;
         case 4:
            this.key = sis.readInt();
            break;
         case 5:
            this.key = sis.readInt();
            break;
         case 6:
            long now = System.currentTimeMillis();
            this.tindex = sis.readByte();
            switch (this.tindex) {
               case 4:
                  this.t4 = sis.readLong();
               case 3:
                  this.t3 = sis.readLong();
               case 2:
                  this.t2 = sis.readLong();
               case 1:
                  this.t1 = sis.readLong();
               default:
                  ++this.tindex;
                  switch (this.tindex) {
                     case 1:
                        this.t1 = now;
                        break;
                     case 2:
                        this.t2 = now;
                        break;
                     case 3:
                        this.t3 = now;
                        break;
                     case 4:
                        this.t4 = now;
                  }
            }
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      WLObjectOutput sos = (WLObjectOutput)out;
      sos.writeByte(this.cmd);
      switch (this.cmd) {
         case 1:
         case 2:
            sos.writeObjectWL(this.sch);
            break;
         case 3:
            sos.writeInt(this.key);
            break;
         case 4:
            sos.writeInt(this.key);
            break;
         case 5:
            sos.writeInt(this.key);
            break;
         case 6:
            long now = System.currentTimeMillis();
            ++this.tindex;
            switch (this.tindex) {
               case 1:
                  this.t1 = now;
                  break;
               case 2:
                  this.t2 = now;
                  break;
               case 3:
                  this.t3 = now;
                  break;
               case 4:
                  this.t4 = now;
            }

            sos.writeByte(this.tindex);
            switch (this.tindex) {
               case 4:
                  sos.writeLong(this.t4);
               case 3:
                  sos.writeLong(this.t3);
               case 2:
                  sos.writeLong(this.t2);
               case 1:
                  sos.writeLong(this.t1);
            }
      }

   }

   public TimeMsg doSchedule(ScheduledTrigger sch, boolean isDaemon) {
      this.cmd = (byte)(isDaemon ? 1 : 2);
      this.sch = sch;
      return this;
   }

   public TimeMsg doSetDaemon(boolean isDaemon, int key) {
      this.cmd = (byte)(isDaemon ? 4 : 5);
      this.key = key;
      return this;
   }

   public TimeMsg doCancel(int key) {
      this.cmd = 3;
      this.key = key;
      return this;
   }

   public TimeMsg doPing() {
      this.cmd = 6;
      return this;
   }
}
