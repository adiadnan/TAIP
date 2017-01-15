package TAIP.Java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;


public class GetDataTest {

	private static DataFetch dataFetch=DataFetch.getInstance();
	
	@Test
	public void fetchDataTest() throws IOException, URISyntaxException
		{
		String path="/webservice/GetUpdatedPropertyDetails.htm";
		String address="2114 Bigelow Ave";
		String cityzipcode="Seattle WA";
		String expectedResultDisregardingTime="<?xml version=\"1.0\" encoding=\"utf-8\"?><UpdatedPropertyDetails:updatedPropertyDetails xmlns:UpdatedPropertyDetails=\"http://www.zillow.com/static/xsd/UpdatedPropertyDetails.xsd\" xsi:schemaLocation=\"http://www.zillow.com/static/xsd/UpdatedPropertyDetails.xsd http://www.zillowstatic.com/vstatic/5fa4733/static/xsd/UpdatedPropertyDetails.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><request><zpid>48749425</zpid></request><message><text>Request successfully processed</text><code>0</code></message><response><zpid>48749425</zpid><pageViewCount><currentMonth>22271</currentMonth><total>22271</total></pageViewCount><address><street>2114 Bigelow Ave N</street><zipcode>98109</zipcode><city>Seattle</city><state>WA</state><latitude>47.637933</latitude><longitude>-122.347938</longitude></address><links><homeDetails>http://www.zillow.com/homedetails/2114-Bigelow-Ave-N-Seattle-WA-98109/48749425_zpid/</homeDetails><photoGallery>http://www.zillow.com/homedetails/2114-Bigelow-Ave-N-Seattle-WA-98109/48749425_zpid/#image=lightbox%3Dtrue</photoGallery><homeInfo>http://www.zillow.com/homedetails/2114-Bigelow-Ave-N-Seattle-WA-98109/48749425_zpid/</homeInfo></links><images><count>1</count><image><url>http://photos.zillowstatic.com/p_d/ISxb3qa8s1cwx01000000000.jpg</url></image></images><editedFacts><useCode>SingleFamily</useCode><bedrooms>4</bedrooms><bathrooms>3.0</bathrooms><finishedSqFt>3470</finishedSqFt><lotSizeSqFt>4680</lotSizeSqFt><yearBuilt>1924</yearBuilt><yearUpdated>2003</yearUpdated><numFloors>2</numFloors><basement>Finished</basement><roof>Composition</roof><view>Water, City, Mountain</view><parkingType>Off-street</parkingType><heatingSources>Gas</heatingSources><heatingSystem>Forced air</heatingSystem><rooms>Laundry room, Walk-in closet, Master bath, Office, Dining room, Family room, Breakfast nook</rooms></editedFacts><neighborhood>Queen Anne</neighborhood><schoolDistrict>Seattle</schoolDistrict><elementarySchool>John Hay</elementarySchool><middleSchool>McClure</middleSchool></response></UpdatedPropertyDetails:updatedPropertyDetails>";
		String actualResult=dataFetch.getData(path, address, cityzipcode);
		assertTrue(actualResult.contains(expectedResultDisregardingTime));

		}
	
	
	@Test
	public void fetchBuildLink() throws URISyntaxException{
		String expectedResult = "http://www.zillow.com/webservice/GetUpdatedPropertyDetails.htm?zws-id=X1-ZWz19hkt3gwmbv_9hc6e&zpid=48749425&address=2114%20Bigelow%20Ave&citystatezip=Seattle%20WA";
		String path="/webservice/GetUpdatedPropertyDetails.htm";
		String address="2114 Bigelow Ave";
		String cityzipcode="Seattle WA";
		String actualResult = dataFetch.buildLink(path, address, cityzipcode);
		assertEquals(actualResult, expectedResult);
	}
	
}
