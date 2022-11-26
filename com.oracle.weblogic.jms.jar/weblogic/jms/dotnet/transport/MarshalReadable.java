package weblogic.jms.dotnet.transport;

public interface MarshalReadable {
   int getMarshalTypeCode();

   void unmarshal(MarshalReader var1);
}
