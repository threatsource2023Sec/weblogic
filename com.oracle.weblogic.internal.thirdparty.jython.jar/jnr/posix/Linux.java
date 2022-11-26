package jnr.posix;

public interface Linux extends POSIX {
   int ioprio_get(int var1, int var2);

   int ioprio_set(int var1, int var2, int var3);
}
