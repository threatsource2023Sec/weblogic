package com.solarmetric.manage;

import java.util.EventListener;

public interface StatisticListener extends EventListener {
   void statisticChanged(StatisticEvent var1);
}
