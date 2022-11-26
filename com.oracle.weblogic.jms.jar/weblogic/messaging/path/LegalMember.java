package weblogic.messaging.path;

import weblogic.common.CompletionRequest;

public interface LegalMember extends Member {
   void isLegal(Key var1, LegalMember var2, CompletionRequest var3);
}
