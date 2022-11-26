package weblogic.connector.common;

import javax.naming.Reference;
import weblogic.connector.external.AdminObjInfo;

public class AdminObjectMetaInfo {
   Object adminObj;
   String versionId;
   AdminObjInfo adminInfo;
   Reference ref;

   AdminObjectMetaInfo(Object adminObj, String versionId, AdminObjInfo adminInfo) {
      this(adminObj, versionId, adminInfo, (Reference)null);
   }

   AdminObjectMetaInfo(Object adminObj, String versionId, AdminObjInfo adminInfo, Reference ref) {
      this.adminObj = adminObj;
      this.versionId = versionId;
      this.adminInfo = adminInfo;
      this.ref = ref;
   }
}
