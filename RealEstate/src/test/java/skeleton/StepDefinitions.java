package skeleton;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;

import TAIP.Java.DataFetch;
import TAIP.Java.House;
import TAIP.Java.extractDescriptionInformation;
import TAIP.Java.workerThread;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class StepDefinitions {
	 String result,link,description;
	 String path,address,cityZipCode;
	 int nrOfThreads;
	 Boolean timeout=true;
	 
	 @Given("^A path (.*), address (.*) and cityZipCode (.*)$")
	    public void setParameters(String path,String address,String cityZipCode) {
		this.path=path;
		this.address=address;
		this.cityZipCode=cityZipCode;
	 }
	 
	 @Given("^The description (.*)$")
	 public void givenTheDescription(String description) {
	     this.description=description;
	 }
	 
	 @When("^A call to the Zillow API is made with the given parameters$")
	    public String callApi() throws URISyntaxException, IOException {
		 DataFetch dataFetch=DataFetch.getInstance();
		 result=dataFetch.getData(path, address, cityZipCode);
		 return result;
	    }
	 
	 @When("^Trying to obtain the build link$")
	    public String getBuildLink() throws URISyntaxException, IOException {
		 DataFetch dataFetch=DataFetch.getInstance();
		 link=dataFetch.buildLink(path, address, cityZipCode);
		 return link;
	    }
	    
	 @When("^The program extracts information from description$")
	 public extractDescriptionInformation extract() {
	 extractDescriptionInformation extract=new extractDescriptionInformation();
	 extract.getNumberOfRooms(description);
	 extract.getNumberOfFloors(description);
	 extract.getNumberOfSquareFoots(description);
	 extract.getInfo(description);
	 return extract;
	 }
	 
	 @Then("^An xml is returned containg information regarding the house$")
	 public void check() throws URISyntaxException, IOException
	 {
		 Assert.assertNotNull(result);
	 }
	 
	 @Then("^The build link returned is (.*)$")
	 public void verifyBuildLink(String expectedBuildLink)
	 {
		 assertEquals(expectedBuildLink,link);
	 }
	 
	 @Then("^It recieves the response (.*)$")
	 public void theResponseIsCorrect(String response)  {
	     extractDescriptionInformation extract=extract();
	     Assert.assertEquals(response,extract.toString());
	 }
	 
	 @Given("^A number (.*) of threads$")
	 public void a_number_of_threads(int arg1) 
	 {
		 nrOfThreads=arg1;
	 }

	 @When("^The api gets called$")
	 public void the_api_gets_called() {
		 ExecutorService executor = Executors.newFixedThreadPool(nrOfThreads);
	        for (int i = 0; i < nrOfThreads; i++) {
	            Runnable worker = new workerThread();
	            executor.execute(worker);
	          }
	        executor.shutdown();
	        while (!executor.isTerminated()) {
	        }
	        timeout=false;
	        System.out.println("All threads have finished execution");
	     
	 }

	 @Then("^Timeout doesn't occur$")
	 public void timeout_doesn_t_occur(){
	    Assert.assertFalse(timeout);
	 }
	 
}
