package com.sun.faces.application;

public class ConfigNavigationCase {
   private String fromViewId = null;
   private String fromAction = null;
   private String fromOutcome = null;
   private String toViewId = null;
   private String key = null;
   private boolean redirect;

   public ConfigNavigationCase(String fromViewId, String fromAction, String fromOutcome, String toViewId, boolean redirect) {
      this.fromViewId = fromViewId;
      this.fromAction = fromAction;
      this.fromOutcome = fromOutcome;
      this.toViewId = toViewId;
      this.key = fromViewId + (fromAction == null ? "-" : fromAction) + (fromOutcome == null ? "-" : fromOutcome);
      this.redirect = redirect;
   }

   public String getFromViewId() {
      return this.fromViewId;
   }

   public void setFromViewId(String fromViewId) {
      this.fromViewId = fromViewId;
   }

   public String getFromAction() {
      return this.fromAction;
   }

   public void setFromAction(String fromAction) {
      this.fromAction = fromAction;
   }

   public String getFromOutcome() {
      return this.fromOutcome;
   }

   public void setFromOutcome(String fromOutcome) {
      this.fromOutcome = fromOutcome;
   }

   public String getToViewId() {
      return this.toViewId;
   }

   public void setToViewId(String toViewId) {
      this.toViewId = toViewId;
   }

   public boolean hasRedirect() {
      return this.redirect;
   }

   public void setRedirect(boolean redirect) {
      this.redirect = redirect;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(64);
      sb.append("from-view-id=").append(this.getFromViewId());
      sb.append(" from-action=").append(this.getFromAction());
      sb.append(" from-outcome=").append(this.getFromOutcome());
      sb.append(" to-view-id=").append(this.getToViewId());
      sb.append(" redirect=").append(this.hasRedirect());
      return sb.toString();
   }
}
