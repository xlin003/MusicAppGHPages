/**
 * 
 */
package es.deusto.spq.client.remote;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import es.deusto.spq.server.IMFServer;

public class RMIServiceLocator {

	private static IMFServer mfservice;

	public RMIServiceLocator() {

	}

	public IMFServer getService() {
		return mfservice;
	}

	public void setService(String ip, String port, String serviceName) {
		String name = "//" + ip + ":" + port + "/" + serviceName;
		try {
			mfservice = (IMFServer) java.rmi.Naming.lookup(name);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
