package TAIP.Java;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.chart.PieChart.Data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.*;

public class DataFetch {

	private static final String baseGeneralSearchLink = "www.zillow.com";
	private static final String updatedDetailsPath = "/webservice/GetUpdatedPropertyDetails.htm";
	private static final String deepSearchPath = "/webservice/GetDeepSearchResults.htm";
	private static final String API_KEY = "X1-ZWz19hkt3gwmbv_9hc6e";

	private static DataFetch dataFetch = null;

	private DataFetch() {
	}

	public static DataFetch getInstance() {
		if (dataFetch == null) {
			dataFetch = new DataFetch();
		}
		return dataFetch;
	}

	public Document parseXML(String xml) throws ParserConfigurationException,
			SAXException, IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringBuilder xmlStringBuilder = new StringBuilder();
			xmlStringBuilder.append(xml);
			ByteArrayInputStream input = new ByteArrayInputStream(
					xmlStringBuilder.toString().getBytes("UTF-8"));
			Document document = builder.parse(input);
			return document;

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	private House buildHouse(Document document) {
		document.getDocumentElement().normalize();
		NodeList resultList = document.getDocumentElement().getChildNodes()
				.item(2).getChildNodes();
		Node nNode = document.getDocumentElement().getChildNodes().item(2);
		Element eElement = (Element) nNode;
		String street = isList(eElement, "street");
		String zipcode = isList(eElement, "zipcode");
		String city = isList(eElement, "city");
		String state = isList(eElement, "state");
		String lotsizeSqFt = isList(eElement, "lotSizeSqFt");
		String bathrooms = isList(eElement, "bathrooms");
		String bedrooms = isList(eElement, "bedrooms");
		String rooms = isList(eElement, "rooms");
		String useCode = isList(eElement, "useCode");
		String imageUrl = isList(eElement, "url");
		// NEEDS REFACTORING
		// String price = isList(eElement, "price");
		House house = new House.Builder(street, zipcode, city, state)
				.lotsizeSqFt(lotsizeSqFt).bathrooms(bathrooms)
				.bedrooms(bedrooms).rooms(rooms).useCode(useCode).imageUrl(imageUrl).build();
		return house;
		// }
	}

	private String isList(Element element, String data){
		try{
		return element.getElementsByTagName(data) == null ? "N/A" : element
				.getElementsByTagName(data).item(0).getTextContent();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return "N/A";
	}

	public String buildLink(String path, String address, String cityzipcode)
			throws URISyntaxException {
		URI uri = new URI("http", baseGeneralSearchLink, path, "zws-id="
				+ API_KEY + "&zpid=48749425" + "&address=" + address
				+ "&citystatezip=" + cityzipcode, null);
		//System.out.println("URL " + uri.toString());
		return uri.toString();
	}

	public String getData(String path, String address, String cityzipcode)
			throws IOException, URISyntaxException {
		String link = buildLink(path, address, cityzipcode);
		URL url = new URL(link);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/xml");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		StringBuilder result = new StringBuilder();
		String output;
		//System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
			result.append(output);
		}

		conn.disconnect();
		return result.toString();
	}

	public void parseWebsite(String address, String cityzipcode) {
		try {
			String result = getData(updatedDetailsPath, address, cityzipcode);
			Document document = parseXML(result);
			House house = buildHouse(document);
			result = getData(deepSearchPath, address, cityzipcode);
			document = parseXML(result);
			Node nNode = document.getDocumentElement().getChildNodes().item(2);
			Element eElement = (Element) nNode;
			house.setPrice(isList(eElement, "amount"));
			System.out.println(house.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			DataFetch dt = DataFetch.getInstance();
			System.out.println(dt.buildLink("/webservice/GetUpdatedPropertyDetails.htm", "2114 Bigelow Ave", "Seattle WA"));
//			dt.getData("/webservice/GetUpdatedPropertyDetails.htm", "2114 Bigelow Ave", "Seattle WA");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
