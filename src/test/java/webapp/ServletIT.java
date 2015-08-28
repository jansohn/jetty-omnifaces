package webapp;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletIT extends BaseIntegrationTestClass
{
	private static final Logger log = LoggerFactory.getLogger(ServletIT.class);
	
	private WebTarget webTarget;
	
	@Override
	protected void beforeSpecific()
	{
		webTarget = ClientBuilder.newClient().target("http://localhost:8080/webapp");
	}

	@Override
	protected void afterSpecific()
	{
		// Nothing specific to do after test case yet
	}

	@Test
	public void getHelloServletTest() throws InterruptedException, ExecutionException, IOException
	{
		log.info("-------------------- {} ---------------------", Thread.currentThread().getStackTrace()[1].getMethodName());

		Response responseEntity = webTarget.path("HelloServlet").request().get();
		
		String response = responseEntity.readEntity(String.class);
		log.debug("response: {}", response);
		
		assertThat("Returned status should be '" + Status.OK.getStatusCode() + "'!", responseEntity.getStatus(), is(Status.OK.getStatusCode()));
		assertThat("Returned string object should contain 'Hello World'!", response, containsString("Hello World"));
	}

}
