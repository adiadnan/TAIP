package TAIP.Java;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class workerThread implements Runnable {
  
    
    private Random random=new Random();
    
    public workerThread()
    {}

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Started.");
        try {
			process();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(Thread.currentThread().getName()+" Ended.");
    }

    private void process() throws IOException, URISyntaxException {
    	
    	DataFetch.getInstance().getData("/webservice/GetUpdatedPropertyDetails.htm", "2114 Bigelow Ave","Seattle WA");
         
        
    }

   
}
