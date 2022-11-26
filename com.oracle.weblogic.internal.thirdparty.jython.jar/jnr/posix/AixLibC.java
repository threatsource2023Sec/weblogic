package jnr.posix;

public interface AixLibC extends UnixLibC {
   int stat64x(CharSequence var1, AixFileStat var2);

   int fstat64x(int var1, AixFileStat var2);

   int lstat64x(CharSequence var1, AixFileStat var2);
}
