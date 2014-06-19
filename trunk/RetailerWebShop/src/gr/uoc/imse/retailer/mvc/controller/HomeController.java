package gr.uoc.imse.retailer.mvc.controller;

import gr.uoc.imse.winepro.ws.client.WineProWsStub;
import gr.uoc.imse.winepro.ws.client.WineProWsStub.ListAllWines;
import gr.uoc.imse.winepro.ws.client.WineProWsStub.ListAllWinesResponse;
import gr.uoc.imse.winepro.ws.client.WineProWsStub.WineTypeSequence;
import java.rmi.RemoteException;
import java.util.Map;
import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping ( "/" )
public class HomeController
{
	private static final int PRODUCTS_PER_PAGE = 20;

	public HomeController ()
	{
	}

	@RequestMapping ( value = { "/", "/home" }, method = RequestMethod.GET )
	public String showHomePage ( Map < String, Object > model )
	{
		WineProWsStub stub = null;
		ListAllWinesResponse listAllWinesResponse = null;
		try
		{
			stub = new WineProWsStub();
			ListAllWines listAllWines = new ListAllWines();
			listAllWinesResponse = stub.listAllWines( listAllWines );
		}
		catch ( AxisFault e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( RemoteException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WineTypeSequence [] wineTypeSequenceArray = listAllWinesResponse.getWineArray().getWineTypeSequence();
		model.put( "pageSize", PRODUCTS_PER_PAGE );
		model.put( "wineList", wineTypeSequenceArray );

		return "home";
	}
}