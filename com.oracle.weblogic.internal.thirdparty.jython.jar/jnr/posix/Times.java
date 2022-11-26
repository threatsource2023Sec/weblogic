package jnr.posix;

public interface Times {
   long utime();

   long stime();

   long cutime();

   long cstime();
}
