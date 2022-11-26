package weblogic.jms.dotnet.transport;

public interface MarshalWritable {
   int getMarshalTypeCode();

   void marshal(MarshalWriter var1);
}
