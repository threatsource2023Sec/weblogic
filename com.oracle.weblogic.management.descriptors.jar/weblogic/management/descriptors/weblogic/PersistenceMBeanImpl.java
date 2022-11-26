package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PersistenceMBeanImpl extends XMLElementMBeanDelegate implements PersistenceMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_persistenceUse = false;
   private PersistenceUseMBean persistenceUse;
   private boolean isSet_delayUpdatesUntilEndOfTx = false;
   private boolean delayUpdatesUntilEndOfTx = true;
   private boolean isSet_findersLoadBean = false;
   private boolean findersLoadBean = true;
   private boolean isSet_isModifiedMethodName = false;
   private String isModifiedMethodName;

   public PersistenceUseMBean getPersistenceUse() {
      return this.persistenceUse;
   }

   public void setPersistenceUse(PersistenceUseMBean value) {
      PersistenceUseMBean old = this.persistenceUse;
      this.persistenceUse = value;
      this.isSet_persistenceUse = value != null;
      this.checkChange("persistenceUse", old, this.persistenceUse);
   }

   public boolean getDelayUpdatesUntilEndOfTx() {
      return this.delayUpdatesUntilEndOfTx;
   }

   public void setDelayUpdatesUntilEndOfTx(boolean value) {
      boolean old = this.delayUpdatesUntilEndOfTx;
      this.delayUpdatesUntilEndOfTx = value;
      this.isSet_delayUpdatesUntilEndOfTx = true;
      this.checkChange("delayUpdatesUntilEndOfTx", old, this.delayUpdatesUntilEndOfTx);
   }

   public boolean getFindersLoadBean() {
      return this.findersLoadBean;
   }

   public void setFindersLoadBean(boolean value) {
      boolean old = this.findersLoadBean;
      this.findersLoadBean = value;
      this.isSet_findersLoadBean = true;
      this.checkChange("findersLoadBean", old, this.findersLoadBean);
   }

   public String getIsModifiedMethodName() {
      return this.isModifiedMethodName;
   }

   public void setIsModifiedMethodName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.isModifiedMethodName;
      this.isModifiedMethodName = value;
      this.isSet_isModifiedMethodName = value != null;
      this.checkChange("isModifiedMethodName", old, this.isModifiedMethodName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<persistence");
      result.append(">\n");
      if (null != this.getIsModifiedMethodName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<is-modified-method-name>").append(this.getIsModifiedMethodName()).append("</is-modified-method-name>\n");
      }

      if (this.isSet_delayUpdatesUntilEndOfTx || !this.getDelayUpdatesUntilEndOfTx()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<delay-updates-until-end-of-tx>").append(ToXML.capitalize(Boolean.valueOf(this.getDelayUpdatesUntilEndOfTx()).toString())).append("</delay-updates-until-end-of-tx>\n");
      }

      if (this.isSet_findersLoadBean || !this.getFindersLoadBean()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<finders-load-bean>").append(ToXML.capitalize(Boolean.valueOf(this.getFindersLoadBean()).toString())).append("</finders-load-bean>\n");
      }

      if (null != this.getPersistenceUse()) {
         result.append(this.getPersistenceUse().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</persistence>\n");
      return result.toString();
   }
}
