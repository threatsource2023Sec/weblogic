package weblogic.jms.common;

import java.io.IOException;

public interface PayloadText extends Payload {
   String readUTF8() throws IOException;

   PayloadText copyPayloadWithoutSharedText() throws JMSException;
}
