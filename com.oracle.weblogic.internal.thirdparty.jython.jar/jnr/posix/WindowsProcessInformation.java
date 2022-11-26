package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class WindowsProcessInformation extends Struct {
   final Struct.Pointer hProcess = new Struct.Pointer();
   final Struct.Pointer hThread = new Struct.Pointer();
   final Struct.Unsigned32 dwProcessId = new Struct.Unsigned32();
   final Struct.Unsigned32 dwThreadId = new Struct.Unsigned32();

   public WindowsProcessInformation(Runtime runtime) {
      super(runtime);
   }

   public HANDLE getThread() {
      return new HANDLE(this.hThread.get());
   }

   public HANDLE getProcess() {
      return new HANDLE(this.hProcess.get());
   }

   public int getPid() {
      return this.dwProcessId.intValue();
   }
}
