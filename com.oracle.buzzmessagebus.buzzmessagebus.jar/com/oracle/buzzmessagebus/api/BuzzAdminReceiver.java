package com.oracle.buzzmessagebus.api;

import com.oracle.common.net.exabus.EndPoint;

public interface BuzzAdminReceiver {
   void open(EndPoint var1);

   void close(EndPoint var1);

   void setBuzzAdmin(BuzzAdmin var1);
}
