package com.solarmetric.manage;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class SimpleStatistic implements Statistic, Serializable {
   private String name;
   private String description;
   private String ordDesc;
   private int drawStyle;
   private transient Collection listeners;
   private double value;
   private boolean ignoreDuplicates;

   public SimpleStatistic(String name, String description, String ordinateDescription) {
      this.drawStyle = 0;
      this.listeners = new CopyOnWriteArraySet();
      this.value = Double.NaN;
      this.ignoreDuplicates = true;
      this.name = name;
      this.description = description;
      this.ordDesc = ordinateDescription;
   }

   public SimpleStatistic(String name, String description, String ordinateDescription, int drawStyle, boolean ignoreDuplicates) {
      this(name, description, ordinateDescription);
      this.ignoreDuplicates = ignoreDuplicates;
      this.drawStyle = drawStyle;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public String getOrdinateDescription() {
      return this.ordDesc;
   }

   public int getDrawStyle() {
      return this.drawStyle;
   }

   public double getValue() {
      return this.value;
   }

   public void setValue(long time, double value) {
      if (this.listeners.isEmpty()) {
         this.value = value;
      } else if (!this.ignoreDuplicates || value != this.value) {
         this.value = value;
         StatisticEvent event = new StatisticEvent(this, time, value);
         Iterator i = this.listeners.iterator();

         while(i.hasNext()) {
            StatisticListener listener = (StatisticListener)i.next();
            listener.statisticChanged(event);
         }

      }
   }

   public void setValue(double value) {
      if (this.listeners.isEmpty()) {
         this.value = value;
      } else {
         long curtime = System.currentTimeMillis();
         this.setValue(curtime, value);
      }
   }

   public void increment(int byVal) {
      this.setValue(this.getValue() + (double)byVal);
   }

   public void decrement(int byVal) {
      this.setValue(this.getValue() - (double)byVal);
   }

   public boolean addListener(StatisticListener listener) {
      return this.listeners.add(listener);
   }

   public boolean removeListener(StatisticListener listener) {
      return this.listeners.remove(listener);
   }

   public void clearListeners() {
      this.listeners.clear();
   }

   public boolean hasListeners() {
      return !this.listeners.isEmpty();
   }
}
