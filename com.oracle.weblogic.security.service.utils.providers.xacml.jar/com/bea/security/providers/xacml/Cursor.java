package com.bea.security.providers.xacml;

import java.util.Properties;
import weblogic.management.utils.InvalidCursorException;

public interface Cursor {
   String RESOURCE_ID = "ResourceId";
   String EXPRESSION = "Expression";
   String ROLE_NAME = "RoleName";
   String AUXILIARY_DATA = "AuxiliaryData";
   String SOURCE_DATA = "SourceData";
   String DEPLOYMENT_SOURCE = "Deployment";
   String COLLECTION_NAME = "CollectionName";

   Properties getCurrentProperties();

   boolean advance() throws InvalidCursorException;

   boolean haveCurrent();

   void close();

   String getCursorName();
}
