package weblogic.connector.external;

import java.util.List;

public interface InboundInfo {
   String getRADescription();

   String getDisplayName();

   String getVendorName();

   String getEisType();

   List getActivationSpecProps();

   String getMsgType();

   ActivationSpecInfo getActivationSpec();
}
