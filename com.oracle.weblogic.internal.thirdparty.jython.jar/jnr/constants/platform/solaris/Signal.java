package jnr.constants.platform.solaris;

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
   SIGUSR1(16L),
   SIGSEGV(11L),
   SIGUSR2(17L),
   SIGPIPE(13L),
   SIGALRM(14L),
   SIGTERM(15L),
   SIGCLD(18L),
   SIGCHLD(18L),
   SIGCONT(25L),
   SIGSTOP(23L),
   SIGTSTP(24L),
   SIGTTIN(26L),
   SIGTTOU(27L),
   SIGURG(21L),
   SIGXCPU(30L),
   SIGXFSZ(31L),
   SIGVTALRM(28L),
   SIGPROF(29L),
   SIGWINCH(20L),
   SIGPOLL(22L),
   SIGIO(22L),
   SIGPWR(19L),
   SIGSYS(12L),
   SIGRTMIN(41L),
   SIGRTMAX(72L),
   NSIG(73L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 73L;

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
