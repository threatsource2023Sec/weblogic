package com.trilead.ssh2;

public interface ChannelCondition {
   int TIMEOUT = 1;
   int CLOSED = 2;
   int STDOUT_DATA = 4;
   int STDERR_DATA = 8;
   int EOF = 16;
   int EXIT_STATUS = 32;
   int EXIT_SIGNAL = 64;
}
