package com.oracle.buzzmessagebus.impl.internalapi;

import com.oracle.buzzmessagebus.api.BuzzAdmin;
import com.oracle.buzzmessagebus.api.BuzzAdminReceiver;

public interface BuzzAdminInternalApi extends BuzzAdmin {
   void addAdminReceiver(BuzzAdminReceiver var1);
}
