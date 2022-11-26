package weblogic.iiop.contexts;

import weblogic.iiop.ior.ITTConstants;

public interface CSIConstants extends ITTConstants {
   short MTEstablishContext = 0;
   short MTCompleteEstablishContext = 1;
   short MTContextError = 4;
   short MTMessageInContext = 5;
   int CEMinor = 1;
   int CEMajorInvalidEvidence = 1;
   int CEMajorInvalidMechanism = 2;
   int CEMajorConflictingEvidence = 3;
   int CEMajorNoContext = 4;
}
