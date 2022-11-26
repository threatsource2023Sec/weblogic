package weblogic.jms.common;

public interface PayloadStream extends Payload {
   PayloadStream copyPayloadWithoutSharedStream() throws JMSException;
}
