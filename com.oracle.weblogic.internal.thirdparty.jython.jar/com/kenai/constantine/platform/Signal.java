package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;

/** @deprecated */
@Deprecated
public enum Signal implements Constant {
   SIGHUP,
   SIGINT,
   SIGQUIT,
   SIGILL,
   SIGTRAP,
   SIGABRT,
   SIGIOT,
   SIGBUS,
   SIGFPE,
   SIGKILL,
   SIGUSR1,
   SIGSEGV,
   SIGUSR2,
   SIGPIPE,
   SIGALRM,
   SIGTERM,
   SIGSTKFLT,
   SIGCLD,
   SIGCHLD,
   SIGCONT,
   SIGSTOP,
   SIGTSTP,
   SIGTTIN,
   SIGTTOU,
   SIGURG,
   SIGXCPU,
   SIGXFSZ,
   SIGVTALRM,
   SIGPROF,
   SIGWINCH,
   SIGPOLL,
   SIGIO,
   SIGPWR,
   SIGSYS,
   SIGUNUSED,
   SIGRTMIN,
   SIGRTMAX,
   NSIG,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(Signal.class, 20000, 29999);

   public final int value() {
      return resolver.intValue(this);
   }

   public final int intValue() {
      return (int)resolver.longValue(this);
   }

   public final long longValue() {
      return resolver.longValue(this);
   }

   public final String description() {
      return resolver.description(this);
   }

   public final boolean defined() {
      return true;
   }

   public final String toString() {
      return this.description();
   }

   public static final Signal valueOf(int value) {
      return (Signal)resolver.valueOf(value);
   }
}
