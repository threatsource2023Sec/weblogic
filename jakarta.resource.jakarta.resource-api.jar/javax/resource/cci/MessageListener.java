package javax.resource.cci;

import javax.resource.ResourceException;

public interface MessageListener {
   Record onMessage(Record var1) throws ResourceException;
}
