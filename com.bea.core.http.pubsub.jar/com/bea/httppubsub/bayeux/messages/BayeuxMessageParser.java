package com.bea.httppubsub.bayeux.messages;

import java.util.List;

public interface BayeuxMessageParser {
   List parse(String var1) throws BayeuxParseException;
}
