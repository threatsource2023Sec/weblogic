package jnr.posix;

import java.nio.ByteBuffer;
import jnr.ffi.Pointer;
import jnr.ffi.Variable;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.annotations.StdCall;
import jnr.ffi.annotations.Transient;
import jnr.ffi.byref.IntByReference;
import jnr.posix.windows.SystemTime;
import jnr.posix.windows.WindowsByHandleFileInformation;
import jnr.posix.windows.WindowsFileInformation;
import jnr.posix.windows.WindowsFindData;

public interface WindowsLibC extends LibC {
   int STD_INPUT_HANDLE = -10;
   int STD_OUTPUT_HANDLE = -11;
   int STD_ERROR_HANDLE = -12;
   int NORMAL_PRIORITY_CLASS = 32;
   int CREATE_UNICODE_ENVIRONMENT = 1024;
   int INFINITE = -1;
   int FILE_TYPE_DISK = 1;
   int FILE_TYPE_CHAR = 2;
   int FILE_TYPE_PIPE = 3;
   int FILE_TYPE_REMOTE = 32768;
   int FILE_TYPE_UNKNOWN = 0;

   int _open_osfhandle(HANDLE var1, int var2);

   HANDLE _get_osfhandle(int var1);

   int _close(int var1);

   int _getpid();

   int _stat64(CharSequence var1, @Out @Transient FileStat var2);

   int _umask(int var1);

   int _wmkdir(@In WString var1);

   boolean RemoveDirectoryW(@In WString var1);

   int _wchmod(@In WString var1, int var2);

   int _wchdir(@In WString var1);

   int _wstat64(@In WString var1, @Out @Transient FileStat var2);

   int _wstat64(@In byte[] var1, @Out @Transient FileStat var2);

   int _pipe(int[] var1, int var2, int var3);

   @StdCall
   boolean CreateProcessW(byte[] var1, @In @Out ByteBuffer var2, WindowsSecurityAttributes var3, WindowsSecurityAttributes var4, int var5, int var6, @In Pointer var7, @In byte[] var8, WindowsStartupInfo var9, WindowsProcessInformation var10);

   int FileTimeToSystemTime(@In FileTime var1, @Out @Transient SystemTime var2);

   int GetFileAttributesW(@In WString var1);

   int GetFileAttributesExW(@In WString var1, @In int var2, @Out @Transient WindowsFileInformation var3);

   int GetFileAttributesExW(@In byte[] var1, @In int var2, @Out @Transient WindowsFileInformation var3);

   int SetFileAttributesW(@In WString var1, int var2);

   int GetFileInformationByHandle(@In HANDLE var1, @Out @Transient WindowsByHandleFileInformation var2);

   int FindClose(HANDLE var1);

   HANDLE FindFirstFileW(@In WString var1, @Out WindowsFindData var2);

   HANDLE FindFirstFileW(@In byte[] var1, @Out WindowsFindData var2);

   @StdCall
   boolean GetExitCodeProcess(HANDLE var1, @Out Pointer var2);

   @StdCall
   boolean GetExitCodeProcess(HANDLE var1, @Out IntByReference var2);

   @StdCall
   int GetFileType(HANDLE var1);

   @StdCall
   int GetFileSize(HANDLE var1, @Out IntByReference var2);

   @StdCall
   HANDLE GetStdHandle(int var1);

   @StdCall
   boolean CreateHardLinkW(@In WString var1, @In WString var2, @In WString var3);

   @StdCall
   HANDLE CreateFileW(byte[] var1, int var2, int var3, Pointer var4, int var5, int var6, int var7);

   @StdCall
   boolean SetEnvironmentVariableW(@In WString var1, @In WString var2);

   @StdCall
   boolean SetFileTime(HANDLE var1, FileTime var2, FileTime var3, FileTime var4);

   @StdCall
   boolean CloseHandle(HANDLE var1);

   @StdCall
   int WaitForSingleObject(HANDLE var1, int var2);

   Variable _environ();
}
