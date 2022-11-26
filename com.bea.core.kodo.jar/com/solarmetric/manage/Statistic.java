package com.solarmetric.manage;

public interface Statistic {
   int DRAW_LINEAR = 0;
   int DRAW_DISCRETE = 1;
   int DRAW_SAMPLEHOLD = 2;
   double UNSET = Double.NaN;

   String getName();

   String getDescription();

   String getOrdinateDescription();

   int getDrawStyle();

   double getValue();

   boolean addListener(StatisticListener var1);

   boolean removeListener(StatisticListener var1);

   void clearListeners();

   boolean hasListeners();
}
