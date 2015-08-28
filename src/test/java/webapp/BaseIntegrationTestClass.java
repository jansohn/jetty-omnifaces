package webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration.ClassList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.weld.environment.servlet.Listener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseIntegrationTestClass
{
	private static final Logger log = LoggerFactory.getLogger(BaseIntegrationTestClass.class);

	private static Server jettyServer;

	@BeforeClass
	public static void setupClass() throws Exception
	{
		// Start the embedded Jetty webserver
		log.info("Starting embedded Jetty server...");
		startJettyServer();
		log.info("Embedded Jetty server started!");
	}

	@AfterClass
	public static void teardownClass() throws Exception
	{
		// Stop the embedded Jetty webserver
		log.info("Stopping embedded Jetty server...");
		stopJettyServer();
		log.info("Embedded Tomcat Jetty stopped!");
	}

	@Before
	public void before()
	{
		// Call specific before method from implementing class
		beforeSpecific();
	}    

	@After
	public void after()
	{
		// Call specific after method from implementing class
		afterSpecific();
	}
	
	/**
	 * Start the embedded Jetty webserver and wait till it is completely started
	 * @throws Exception 
	 */
	private static void startJettyServer() throws Exception
	{
		jettyServer = new Server(8080);
		
		//Enable parsing of jndi-related parts of web.xml and jetty-env.xml
        ClassList classlist = ClassList.setServerDefault(jettyServer);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

		WebAppContext context = new WebAppContext();
		
		String webappDir = "target/testwebapp-1.0.0/";
		context.setResourceBase(webappDir);
		context.setDescriptor("src/test/resources/WEB-INF/web.xml");
		context.setContextPath("/webapp");		
		context.setParentLoaderPriority(true);
		context.addEventListener(new Listener()); // add WELD listener
		
		jettyServer.setHandler(context);
		jettyServer.start();
		jettyServer.dump(System.err);

		while (!jettyServer.isStarted())
		{
			try { Thread.sleep(500); } catch (InterruptedException e) { log.error(e.getMessage(), e); }
		}
	}

	/**
	 * Stop the embedded Jetty webserver and wait until it is completely stopped
	 * @throws Exception 
	 */
	private static void stopJettyServer() throws Exception
	{
		if (jettyServer != null)
		{
			jettyServer.stop();
			
			while (!jettyServer.isStopped())
			{
				try { Thread.sleep(500); } catch (InterruptedException e) { log.error(e.getMessage(), e); }
			}
		}
	}
	
	protected abstract void beforeSpecific();
	protected abstract void afterSpecific();
}
