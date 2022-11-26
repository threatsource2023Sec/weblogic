package weblogic.application;

import java.util.Map;

public interface RedeployInfo {
   Map getUriMappings();

   boolean areUrisNew();
}
