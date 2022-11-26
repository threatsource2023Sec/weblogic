package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import weblogic.nodemanager.rest.model.Server;
import weblogic.nodemanager.util.ServerInfo;

public class DomainServerResource extends NMBaseResource {
   private static final String KILL = "kill";
   private static final String START = "start";
   private static final String JOBS = "jobs";
   private static final String SERVER_STD_OUT = "stdout";

   @GET
   @Produces({"application/json"})
   public RestModelResponseBody getServer(@PathParam("domain-name") String domainName, @PathParam("server-name") String serverName) throws Exception {
      ServerInfo serverInfo = this.getServerInfo(domainName, serverName);
      Server server = new Server();
      server.setName(serverName);
      server.setState(serverInfo.getState() != null ? serverInfo.getState() : "UNKNOWN");
      server.setRegistered(serverInfo.isConfigComplete());
      server.allFieldsSet();
      RestModelResponseBody rb = this.restModelResponseBody(Server.class, this.getParentUri(), server);
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      this.addResourceLink(rb, "stdout");
      this.addActionResourceLink(rb, "start");
      this.addActionResourceLink(rb, "kill");
      this.addResourceLink(rb, "jobs");
      return rb;
   }

   @Path("stdout")
   public DomainServerStdOutResource getServerStdOutResource() throws Exception {
      return (DomainServerStdOutResource)this.getSubResource(DomainServerStdOutResource.class);
   }

   @Path("start")
   public NMStartServerResource getServerStartResource() throws Exception {
      return (NMStartServerResource)this.getSubResource(NMStartServerResource.class);
   }

   @Path("kill")
   public NMKillServerResource getServerStopResource() throws Exception {
      return (NMKillServerResource)this.getSubResource(NMKillServerResource.class);
   }

   @Path("jobs")
   public NMServerJobsResource getServerJobsResource() throws Exception {
      return (NMServerJobsResource)this.getSubResource(NMServerJobsResource.class);
   }
}
