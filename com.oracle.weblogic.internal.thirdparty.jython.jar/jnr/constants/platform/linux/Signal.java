package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum Signal implements Constant {
   SIGHUP(1),
   SIGINT(2),
   SIGQUIT(3),
   SIGILL(4),
   SIGTRAP(5),
   SIGABRT(6),
   SIGIOT(6),
   SIGBUS(7),
   SIGFPE(8),
   SIGKILL(9),
   SIGUSR1(10),
   SIGSEGV(11),
   SIGUSR2(12),
   SIGPIPE(13),
   SIGALRM(14),
   SIGTERM(15),
   SIGSTKFLT(16),
   SIGCLD(17),
   SIGCHLD(17),
   SIGCONT(18),
   SIGSTOP(19),
   SIGTSTP(20),
   SIGTTIN(21),
   SIGTTOU(22),
   SIGURG(23),
   SIGXCPU(24),
   SIGXFSZ(25),
   SIGVTALRM(26),
   SIGPROF(27),
   SIGWINCH(28),
   SIGPOLL(29),
   SIGIO(29),
   SIGPWR(30),
   SIGSYS(31),
   SIGUNUSED(31),
   SIGRTMIN(34),
   SIGRTMAX(64),
   NSIG(65);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 65L;

   private Signal(int value) {
      this.value = value;
   }

   public final int value() {
      return this.value;
   }

   public final int intValue() {
      return this.value;
   }

   public final long longValue() {
      return (long)this.value;
   }

   public final boolean defined() {
      return true;
   }
}
