package com.sun.faces.renderkit;

public class Attribute implements Comparable {
   private final String name;
   private final String[] events;

   public Attribute(String name, String[] events) {
      this.name = name;
      this.events = events;
   }

   public static Attribute attr(String name) {
      return new Attribute(name, (String[])null);
   }

   public static Attribute attr(String name, String... events) {
      return new Attribute(name, events);
   }

   public String getName() {
      return this.name;
   }

   public String[] getEvents() {
      return this.events;
   }

   public int compareTo(Attribute o) {
      return this.getName().compareTo(o.getName());
   }
}
