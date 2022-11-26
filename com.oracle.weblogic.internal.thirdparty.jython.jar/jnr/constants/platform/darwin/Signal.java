package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum Signal implements Constant {
   SIGHUP(1L),
   SIGINT(2L),
   SIGQUIT(3L),
   SIGILL(4L),
   SIGTRAP(5L),
   SIGABRT(6L),
   SIGIOT(6L),
   SIGBUS(10L),
   SIGFPE(8L),
   SIGKILL(9L),
   SIGUSR1(30L),
   SIGSEGV(11L),
   SIGUSR2(31L),
   SIGPIPE(13L),
   SIGALRM(14L),
   SIGTERM(15L),
   SIGCHLD(20L),
   SIGCONT(19L),
   SIGSTOP(17L),
   SIGTSTP(18L),
   SIGTTIN(21L),
   SIGTTOU(22L),
   SIGURG(16L),
   SIGXCPU(24L),
   SIGXFSZ(25L),
   SIGVTALRM(26L),
   SIGPROF(27L),
   SIGWINCH(28L),
   SIGIO(23L),
   SIGSYS(12L),
   NSIG(32L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 32L;

   private Signal(long value) {
      this.value = value;
   }

   public final int intValue() {
      return (int)this.value;
   }

   public final long longValue() {
      return this.value;
   }

   public final boolean defined() {
      return true;
   }
}
