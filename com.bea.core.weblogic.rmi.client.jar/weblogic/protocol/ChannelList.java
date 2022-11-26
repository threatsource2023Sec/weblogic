package weblogic.protocol;

import java.io.Externalizable;

public interface ChannelList extends Externalizable {
   ServerIdentity getIdentity();
}
