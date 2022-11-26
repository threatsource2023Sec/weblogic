package weblogic.entitlement.data;

import java.util.Properties;

public interface EnCursor {
   String RESOURCE_ID = "ResourceId";
   String EXPRESSION = "Expression";
   String ROLE_NAME = "RoleName";
   String AUXILIARY_DATA = "AuxiliaryData";
   String SOURCE_DATA = "SourceData";
   String DEPLOYMENT_SOURCE = "Deployment";
   String COLLECTION_NAME = "CollectionName";

   Properties getCurrentProperties();

   void advance();

   void close();

   boolean haveCurrent();

   String getCursorName();
}
