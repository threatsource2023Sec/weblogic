package jnr.posix;

public interface NanosecondFileStat extends FileStat {
   long aTimeNanoSecs();

   long cTimeNanoSecs();

   long mTimeNanoSecs();
}
