package com.solarmetric.profile;

public class EventInfoImpl implements EventInfo {
   private static final long serialVersionUID = 1L;
   private String _name;
   private String _description;
   private String _category;

   public EventInfoImpl(String name, String description) {
      this._name = name;
      this._description = description;
   }

   public String getName() {
      return this._name;
   }

   public String getDescription() {
      return this._description;
   }

   public String getCategory() {
      return this._category;
   }

   public void setCategory(String catName) {
      this._category = catName;
   }

   public String toString() {
      return this._name;
   }

   public boolean equals(Object o) {
      if (!(o instanceof EventInfoImpl)) {
         return false;
      } else {
         EventInfoImpl m = (EventInfoImpl)o;
         return m.getName().equals(this.getName()) && m.getDescription().equals(this.getDescription());
      }
   }

   public int hashCode() {
      return this._name.hashCode() ^ this._description.hashCode();
   }

   public String getViewerClassName() {
      return null;
   }
}
