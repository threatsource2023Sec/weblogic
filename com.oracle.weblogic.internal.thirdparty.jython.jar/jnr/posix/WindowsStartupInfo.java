package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class WindowsStartupInfo extends Struct {
   public final Struct.Unsigned32 cb = new Struct.Unsigned32();
   public final Struct.Pointer lpReserved = new Struct.Pointer();
   public final Struct.Pointer lpDesktop = new Struct.Pointer();
   public final Struct.Pointer lpTitle = new Struct.Pointer();
   public final Struct.Unsigned32 dwX = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwY = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwXSize = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwYSize = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwXCountChars = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwYCountChars = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwFillAttribute = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwFlags = new Struct.Unsigned32();
   public final Struct.Unsigned16 wShowWindow = new Struct.Unsigned16();
   public final Struct.Unsigned16 cbReserved2 = new Struct.Unsigned16();
   public final Struct.Pointer lpReserved2 = new Struct.Pointer();
   public final Struct.Pointer standardInput = new Struct.Pointer();
   public final Struct.Pointer standardOutput = new Struct.Pointer();
   public final Struct.Pointer standardError = new Struct.Pointer();

   public WindowsStartupInfo(Runtime runtime) {
      super(runtime);
   }

   public void setFlags(int value) {
      this.dwFlags.set((long)value);
   }

   public void setStandardInput(HANDLE standardInput) {
      this.standardInput.set(standardInput.toPointer());
   }

   public void setStandardOutput(HANDLE standardOutput) {
      this.standardOutput.set(standardOutput.toPointer());
   }

   public void setStandardError(HANDLE standardError) {
      this.standardError.set(standardError.toPointer());
   }
}
