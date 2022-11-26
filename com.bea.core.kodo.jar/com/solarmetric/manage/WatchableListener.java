package com.solarmetric.manage;

import java.util.EventListener;

public interface WatchableListener extends EventListener {
   void watchableChanged(WatchableEvent var1);
}
