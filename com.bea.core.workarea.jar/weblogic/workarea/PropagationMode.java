package weblogic.workarea;

public interface PropagationMode {
   int LOCAL = 1;
   int WORK = 2;
   int RMI = 4;
   int TRANSACTION = 8;
   int JMS_QUEUE = 16;
   int JMS_TOPIC = 32;
   int SOAP = 64;
   int MIME_HEADER = 128;
   int ONEWAY = 256;
   int GLOBAL = 212;
   int DEFAULT = 212;
}
