package jnr.posix;

public class WindowsChildRecord {
   private final HANDLE process;
   private final int pid;

   public WindowsChildRecord(HANDLE process, int pid) {
      this.process = process;
      this.pid = pid;
   }

   public HANDLE getProcess() {
      return this.process;
   }

   public int getPid() {
      return this.pid;
   }
}
