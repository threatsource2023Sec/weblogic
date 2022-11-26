package com.bea.security.providers.xacml.entitlement;

import com.bea.common.security.SecurityLogger;
import com.bea.security.providers.xacml.Cursor;
import java.util.Properties;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.NotFoundException;

public abstract class CursorImpl implements Cursor {
   private String name;
   protected CursorElement next;

   public CursorImpl(String name) throws NotFoundException {
      this.name = name;
      if (!this.hasNext()) {
         throw new NotFoundException(SecurityLogger.getDefAuthImplNoSearchResults());
      }
   }

   public abstract boolean hasNext();

   public abstract CursorElement next() throws InvalidCursorException;

   public Properties getCurrentProperties() {
      Properties p = new Properties();
      String resourceId = this.next.getResourceId();
      if (resourceId != null) {
         p.put("ResourceId", resourceId);
      }

      String roleName = this.next.getRoleName();
      if (roleName != null) {
         p.put("RoleName", roleName);
      }

      String expression = this.next.getExpression();
      if (expression != null) {
         p.put("Expression", expression);
      }

      String auxiliary = this.next.getAuxiliary();
      if (auxiliary != null) {
         p.put("AuxiliaryData", auxiliary);
      }

      if (this.next.isDeployment()) {
         p.put("SourceData", "Deployment");
      }

      String collectionName = this.next.getCollectionName();
      if (collectionName != null) {
         p.put("CollectionName", collectionName);
      }

      return p;
   }

   public boolean advance() throws InvalidCursorException {
      this.next();
      return !this.hasNext();
   }

   public boolean haveCurrent() {
      return this.hasNext();
   }

   public void close() {
   }

   public String getCursorName() {
      return this.name;
   }
}
