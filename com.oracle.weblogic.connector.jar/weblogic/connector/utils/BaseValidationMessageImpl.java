package weblogic.connector.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseValidationMessageImpl implements ValidationMessage {
   private final List warnings;
   private final List errors;
   private final ValidationMessage baseMessage;
   private final String[] criticalSubComponents;
   private final Map child2ParentMap;

   public BaseValidationMessageImpl(ValidationMessage baseMessage, String[] criticalSubComponents, Map child2ParentMap) {
      this.warnings = new ArrayList();
      this.errors = new ArrayList();
      this.baseMessage = baseMessage;
      this.criticalSubComponents = criticalSubComponents;
      this.child2ParentMap = child2ParentMap;
   }

   public BaseValidationMessageImpl(ValidationMessage baseMessage) {
      this(baseMessage, (String[])null, (Map)null);
   }

   protected void warning(String message, int order) {
      assert message != null : "Should not add an empty warning message";

      this.warnings.add(new MessageEntry("", "", order, message));
   }

   public void error(String subComponent, String key, String message, int order) {
      assert message != null : "Should not add an empty error message";

      this.errors.add(new MessageEntry(subComponent, key, order, message));
   }

   public List getWarnings() {
      this.sort(this.warnings);
      return this.merge(this.toList(this.warnings), this.baseMessage.getWarnings());
   }

   public List getErrors() {
      this.sort(this.errors);
      return this.merge(this.toList(this.errors), this.baseMessage.getErrors());
   }

   public List getCriticalErrors() {
      return this.merge(this.getErrorsOfSubComponents(this.criticalSubComponents), this.baseMessage.getCriticalErrors());
   }

   public boolean hasCriticalError() {
      return this.baseMessage.hasCriticalError() || this.hasErrorsOfCriticalSubComponents();
   }

   public List getNonCriticalErrors() {
      return this.getErrorsOutOfSubComponents(this.criticalSubComponents);
   }

   protected List getErrorsOfSubComponents(String[] subComponents) {
      if (subComponents == null) {
         return this.getErrors();
      } else {
         List theErrors = new ArrayList();
         List list = Arrays.asList(subComponents);
         Iterator var4 = this.errors.iterator();

         while(var4.hasNext()) {
            MessageEntry entry = (MessageEntry)var4.next();
            if (list.contains(entry.getSubComponent())) {
               theErrors.add(entry);
            }
         }

         return this.toList(theErrors);
      }
   }

   private boolean hasErrorsOfCriticalSubComponents() {
      if (this.criticalSubComponents == null) {
         return !this.errors.isEmpty();
      } else {
         List list = Arrays.asList(this.criticalSubComponents);
         Iterator var2 = this.errors.iterator();

         MessageEntry entry;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            entry = (MessageEntry)var2.next();
         } while(!list.contains(entry.getSubComponent()));

         return true;
      }
   }

   protected List getErrorsOutOfSubComponents(String[] subComponents) {
      if (subComponents == null) {
         return Collections.emptyList();
      } else {
         List theErrors = new ArrayList();
         List list = Arrays.asList(subComponents);
         Iterator var4 = this.errors.iterator();

         while(var4.hasNext()) {
            MessageEntry entry = (MessageEntry)var4.next();
            if (!list.contains(entry.getSubComponent())) {
               theErrors.add(entry);
            }
         }

         return this.toList(theErrors);
      }
   }

   public List getErrorsOfMessageKey(ValidationMessage.SubComponentAndKey msgKey) {
      if (msgKey == null) {
         return Collections.emptyList();
      } else {
         String[] compositeSubSystems = this.child2ParentMap == null ? null : (String[])this.child2ParentMap.get(msgKey.subComponent);
         List theErrors = new ArrayList();
         Iterator var4 = this.errors.iterator();

         while(true) {
            while(var4.hasNext()) {
               MessageEntry entry = (MessageEntry)var4.next();
               if (entry.applyTo(msgKey.subComponent, msgKey.key)) {
                  theErrors.add(entry);
               } else if (compositeSubSystems != null) {
                  String[] var6 = compositeSubSystems;
                  int var7 = compositeSubSystems.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     String compositeSubSystem = var6[var8];
                     if (entry.applyTo(compositeSubSystem, msgKey.key)) {
                        theErrors.add(entry);
                        break;
                     }
                  }
               }
            }

            this.sort(theErrors);
            return this.merge(this.toList(theErrors), this.baseMessage.getErrorsOfMessageKey(msgKey));
         }
      }
   }

   public void clearErrorsOfMessageKey(ValidationMessage.SubComponentAndKey msgKey) {
      if (msgKey != null) {
         List theErrors = new ArrayList();
         Iterator var3 = this.errors.iterator();

         while(var3.hasNext()) {
            MessageEntry entry = (MessageEntry)var3.next();
            if (msgKey.equals(entry.getSubComponent(), entry.getKey())) {
               theErrors.add(entry);
            }
         }

         this.errors.removeAll(theErrors);
      }
   }

   private void sort(List msgs) {
      Collections.sort(msgs);
   }

   private List toList(List msgs) {
      ArrayList result = new ArrayList(msgs.size());
      Iterator var3 = msgs.iterator();

      while(var3.hasNext()) {
         MessageEntry msg = (MessageEntry)var3.next();
         result.add(msg.getMessage());
      }

      return result;
   }

   private List merge(List msgs, List base) {
      ArrayList result = new ArrayList(msgs.size() + base.size());
      result.addAll(base);
      result.addAll(msgs);
      return result;
   }

   public String dumpMeta() {
      return "Errors :(" + this.errors.size() + ":" + this.errors.toString() + ") ; Warnings :(" + this.warnings.size() + ":" + this.warnings.toString() + ") ";
   }

   public String toString() {
      return this.dumpMeta();
   }

   private static class MessageEntry implements Comparable {
      String subComponent;
      String key;
      int order;
      String message;

      public MessageEntry(String subComponent, String key, int order, String message) {
         this.subComponent = subComponent == null ? "UNKNOWN" : subComponent;
         this.key = key == null ? "UNKNOWN" : key;
         this.order = order;
         this.message = message;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + this.subComponent.hashCode();
         result = 31 * result + this.key.hashCode();
         result = 31 * result + (this.message == null ? 0 : this.message.hashCode());
         result = 31 * result + this.order;
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            MessageEntry other = (MessageEntry)obj;
            if (!this.subComponent.equals(other.subComponent)) {
               return false;
            } else if (!this.key.equals(other.key)) {
               return false;
            } else {
               if (this.message == null) {
                  if (other.message != null) {
                     return false;
                  }
               } else if (!this.message.equals(other.message)) {
                  return false;
               }

               return this.order == other.order;
            }
         }
      }

      public boolean applyTo(String theSubComponent, String theKey) {
         if (!this.subComponent.equals(theSubComponent)) {
            return false;
         } else {
            return this.key.indexOf(theKey + ";;;;") >= 0 || this.key.equals(theKey);
         }
      }

      public String getSubComponent() {
         return this.subComponent;
      }

      public String getMessage() {
         return this.message;
      }

      public String getKey() {
         return this.key;
      }

      public int compareTo(MessageEntry otherEntry) {
         if (!this.subComponent.equals(otherEntry.subComponent)) {
            return this.subComponent.compareTo(otherEntry.subComponent);
         } else if (!this.key.equals(otherEntry.key)) {
            return this.key.compareTo(otherEntry.key);
         } else {
            int compare = this.order - otherEntry.order;
            return compare != 0 ? compare : this.message.compareTo(otherEntry.getMessage());
         }
      }

      public String toString() {
         return "(" + this.subComponent + "-" + this.key + "-" + this.order + "-" + this.message + ")";
      }
   }
}
