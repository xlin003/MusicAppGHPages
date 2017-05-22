/**
 * 
 */
package es.deusto.spq.testing.junit;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;

import es.deusto.spq.client.controller.MFcontroller;
import es.deusto.spq.server.IMFServer;
import es.deusto.spq.server.MFServer;
import junit.framework.JUnit4TestAdapter;

public class RMITest {

	// Properties are hard-coded because we want the test to be executed without external interaction
	private String[] arg = { "127.0.0.1", "1099", "TestMFserver" };
	private static String cwd = RMITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	MFcontroller app;

	// final static Logger logger = LoggerFactory.getLogger(RMITest.class);
	static Logger logger = Logger.getLogger(RMITest.class);

	// If you use the EmptyReportModule, the report is not generated
	// @Rule public ContiPerfRule rule = new ContiPerfRule(new EmptyReportModule());

	private IMFServer mfservice;
	@Rule
	public ContiPerfRule rule = new ContiPerfRule();

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RMITest.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {
			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					logger.info("RMI registry ready.");
				} catch (Exception e) {
					logger.error("Exception starting RMI registry:");
					e.printStackTrace();
				}
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		class RMIServerRunnable implements Runnable {

			public void run() {
				logger.info("Test RMI simulation");
				logger.info("**************: " + cwd);
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target/classes/security/java.policy");
				// System.setProperty("java.security.policy", "src/main/resources/security/java.policy");
				// System.setProperty("java.security.policy", "target/test-classes/security/java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//127.0.0.1:1099/TestMFserver";
				logger.info(" * TestServer name: " + name);

				try {
					IMFServer myserver = new MFServer();
					Naming.rebind(name, myserver);
				} catch (RemoteException re) {
					logger.error(" # MFserver RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					logger.error(" # MFserver MalformedURLException: " + murle.getMessage());
					murle.printStackTrace();
					System.exit(-1);
				}
			}
		}
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
//	@Before
//	public void setUp() throws Exception {
//		app = new MFcontroller(arg);
//	}

	@Before
	public void setUpClient() {
		System.out.println("nidayede<><><><><><><><><><>><><><><><><><><><><><>");
		try {
			System.setProperty("java.security.policy", "target/test-classes/security/java.policy");

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String name = "//127.0.0.1:1099/TestMFserver";

			logger.info("BeforeTest - Setting the client ready for calling TestServer name: " + name);
			mfservice = (IMFServer) java.rmi.Naming.lookup(name);

		} catch (Exception re) {
			System.err.println(" # Messenger RemoteException: " + re.getMessage());
			// re.printStackTrace();
			System.exit(-1);
		}

	}

	@Test
	@PerfTest(invocations = 100, threads = 10)
	@Required(max = 1000, average = 50)
//	@Ignore
	public void testLoadSong() throws RemoteException {

		List<String> songs = new ArrayList<>();
		// songs = mfservice.loadSongs();
		songs = mfservice.loadSongs();
		for (int i = 0; i < songs.size(); i++) {
			 logger.info(songs.get(i));
			 System.out.println(songs.get(i));
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	// @After
	// public void deleteDatabase() {
	// PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	// PersistenceManager pm = pmf.getPersistenceManager();
	// Transaction tx = pm.currentTransaction();
	// try {
	// tx.begin();
	//
	// System.out.println("Deleting test users from persistence. Cleaning up.");
	// Query<Usuario> q1 = pm.newQuery(Usuario.class);
	// long numberInstancesDeleted = q1.deletePersistentAll();
	// System.out.println("Deleted " + numberInstancesDeleted + " user");
	//
	// tx.commit();
	// } finally {
	// if (tx.isActive()) {
	// tx.rollback();
	// }
	// pm.close();
	// }
	// }

}
