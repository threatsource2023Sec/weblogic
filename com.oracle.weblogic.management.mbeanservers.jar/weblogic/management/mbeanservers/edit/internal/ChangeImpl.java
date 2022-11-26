package weblogic.management.mbeanservers.edit.internal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.mbeanservers.edit.Change;

public class ChangeImpl implements Change {
   private final Object bean;
   private final String attributeName;
   private final String operation;
   private final Object oldValue;
   private final Object newValue;
   private final boolean restartRequired;
   private Object affectedBean = null;
   private final String entityToRestart;
   private static final Set supportedEntities = new HashSet();
   private static final DebugLogger changLogger = DebugLogger.getDebugLogger("ChangeLogger");

   public ChangeImpl(Object bean, String attributeName, String operation, Object oldValue, Object newValue, boolean restartRequired, Object restartRequiredMBeans, String entity) {
      this.checkEntityType(entity);
      this.bean = bean;
      this.attributeName = attributeName;
      this.operation = operation;
      this.oldValue = oldValue;
      this.newValue = newValue;
      this.restartRequired = restartRequired;
      this.affectedBean = restartRequiredMBeans;
      this.entityToRestart = entity;
   }

   public ChangeImpl(Object bean, String attributeName, String operation, Object oldValue, Object newValue, boolean restartRequired, String entity) {
      this.checkEntityType(entity);
      this.bean = bean;
      this.attributeName = attributeName;
      this.operation = operation;
      this.oldValue = oldValue;
      this.newValue = newValue;
      this.restartRequired = restartRequired;
      this.entityToRestart = entity;
   }

   private void checkEntityType(String entity) {
      if (changLogger.isDebugEnabled()) {
         changLogger.debug("Input entity type to be verified: " + entity);
      }

      if (!supportedEntities.contains(entity)) {
         StringBuffer sb = new StringBuffer(20);
         sb.append("Only these entities are supported: ");
         Iterator var3 = supportedEntities.iterator();

         while(var3.hasNext()) {
            String supportedEntity = (String)var3.next();
            sb.append(supportedEntity).append(",");
         }

         sb.deleteCharAt(sb.lastIndexOf(",")).append(".");
         throw new IllegalArgumentException(sb.toString());
      }
   }

   public Object getBean() {
      return this.bean;
   }

   public String getAttributeName() {
      return this.attributeName;
   }

   public Object getAffectedBean() {
      return this.affectedBean;
   }

   public String getOperation() {
      return this.operation;
   }

   public Object getOldValue() {
      return this.oldValue;
   }

   public Object getNewValue() {
      return this.newValue;
   }

   public boolean isRestartRequired() {
      return this.restartRequired;
   }

   public String getEntityToRestart() {
      return this.entityToRestart;
   }

   static {
      supportedEntities.add("server");
      supportedEntities.add("partition");
      supportedEntities.add("none");
   }
}
