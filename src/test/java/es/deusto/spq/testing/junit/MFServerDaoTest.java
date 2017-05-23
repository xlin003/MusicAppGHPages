/**
 * 
 */
package es.deusto.spq.testing.junit;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.deusto.spq.client.controller.MFcontroller;
import es.deusto.spq.server.IMFServer;
import es.deusto.spq.server.MFServer;
import es.deusto.spq.server.dao.IMFdao;
import es.deusto.spq.server.dao.MFdao;
import es.deusto.spq.server.data.Cancion;
import es.deusto.spq.server.data.Usuario;
import junit.framework.JUnit4TestAdapter;

//import org.junit.Rule;
//import org.databene.contiperf.Required;
//import org.databene.contiperf.PerfTest;
//import org.databene.contiperf.junit.ContiPerfRule;
//import org.databene.contiperf.report.EmptyReportModule;
//import org.databene.contiperf.junit.ContiPerfRule;
import org.apache.log4j.Logger;

public class MFServerDaoTest {

	static IMFdao dao;
	static IMFServer server;
	static Logger logger = Logger.getLogger("Server side test MFdao & MFserver");

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(MFServerDaoTest.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.info("TESTING STARTED");

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// logger.info("TESTING STARTED");
		logger.info("Entering setUp");
		dao = new MFdao();
		server = new MFServer();
	}

	/**
	 * 
	 */
	@Test
	public void testUserCreation() {
		logger.info("Testing MFdao unit storeUser and checkUser");
		Usuario user = new Usuario("us", "us", "us", "us");
		if (!dao.checkUser(user)) {
			dao.storeUser(user);
			logger.info("User created");
		} else {
			logger.info("User already exist in the databse");
		}
	}

	/**
	 * 
	 */
	@Test
	public void testLoginUser() {
		logger.info("Testing MFdao unit loginUser");
		assertEquals("Result", true, dao.loginUser("xian", "xian"));
	}

	/**
	 * @throws RemoteException
	 * 
	 */
	@Test
	public void testSearchSong() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		logger.info("Testing MFserver unit searchSong invoking MFdao");
		list = server.searchSong("audio01");
		for (int i = 0; i < list.size(); i++) {
			String[] s = list.get(i).split("#");
			assertEquals("Result", "audio01", s[1]);
			assertEquals("Result", "audio01", s[2]);
		}
	}

	/**
	 * 
	 */
	@Test
	public void testloadFavoriteSong() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		logger.info("Testing MFserver unit loadFavoriteSong invoking MFdao");
		logger.info(dao.loadFavoriteSongs());
	}

	/**
	 * @throws RemoteException 
	 * 
	 */
	@Test
	private void testUploadSong() {
		// TODO Auto-generated method stub
		String s = "Test";
		logger.info("Testing MFserver unit UploadSong invoking MFdao");
		server.uploadSong(s, s, s, s, s);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void deleteDatabase() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			System.out.println("Deleting test users from persistence. Cleaning up.");
			Query<Cancion> q1 = pm.newQuery("delete from CANCION where nombre = Test");
			long numberInstancesDeleted = q1.deletePersistentAll();
			System.out.println("Deleted " + numberInstancesDeleted + " user");

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
