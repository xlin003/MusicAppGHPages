//package es.deusto.spq.testing.junit;
//
//import static org.junit.Assert.assertTrue;
//
//import java.rmi.RemoteException;
//import java.util.logging.Level;
//
//import org.databene.contiperf.PerfTest;
//import org.databene.contiperf.Required;
//import org.databene.contiperf.junit.ContiPerfRule;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import es.deusto.spq.client.controller.*;
//import es.deusto.spq.server.data.Usuario;
//import junit.framework.JUnit4TestAdapter;
//
//public class PerformanceTest {
//
//	private static MFcontroller remote;
//
//	private static Usuario usuario;
//
////	final static Logger logger = LoggerFactory.getLogger(PerformanceTest.class);
//	final static Logger logger = LoggerFactory.getLogger(RMITest.class);
//
//	static int iterationSignUpTest = 0;
//	static int iterationSignInTest = 0;
//
//	@Rule
//	public ContiPerfRule rule = new ContiPerfRule();
//
//	public static junit.framework.Test suite() {
//		return new JUnit4TestAdapter(PerformanceTest.class);
//	}
//
//	@Before
//	public void setUp() throws RemoteException {
//		logger.info("Entering setUp: {}");
//		String[] args = { "127.0.0.1", "1099", "MusicAppServer" };
//		try {
//			remote = new MFcontroller(args);
//		} catch (RemoteException ex) {
//			java.util.logging.Logger.getLogger(PerformanceTest.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		logger.info("Leaving setUp");
//	}
//
//	@Test
//	@PerfTest(invocations = 100, threads = 10)
//	@Required(max = 500, average = 50)
//	public void testSignUp() throws Exception {
//		logger.info("Starting SignUp PerformanceTest", iterationSignUpTest++);
//
//		boolean b = false;
//
//		remote.registerUser("xian", "xian", "xian", "xian");
//		b = remote.loginUser("xian", "xian");
//		assertTrue(b);
//
//		logger.debug("Finishing SignUp PerformanceTest");
//	}
//
//}