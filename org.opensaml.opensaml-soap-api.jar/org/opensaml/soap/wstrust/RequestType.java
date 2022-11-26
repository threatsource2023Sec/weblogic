package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;

public interface RequestType extends XSURI, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestType";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestType", "wst");
   String TYPE_LOCAL_NAME = "RequestTypeOpenEnum";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestTypeOpenEnum", "wst");
   String ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue";
   String RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Renew";
   String CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Cancel";
   String STSCANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/STSCancel";
   String VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Validate";
   String KET = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/KET";
   String BATCH_ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchIssue";
   String BATCH_RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchRenew";
   String BATCH_CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchCancel";
   String BATCH_VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchValidate";
}
