Feature: API CALL

  Scenario Outline: call API
    Given A path <path>, address <address> and cityZipCode <cityZipCode>
    When A call to the Zillow API is made with the given parameters
    Then An xml is returned containg information regarding the house

    Examples: 
      | path                                      | address          | cityZipCode |
      | /webservice/GetUpdatedPropertyDetails.htm | 2114 Bigelow Ave | Seattle WA  |

  Scenario Outline: fetchBuildLInk
    Given A path <path>, address <address> and cityZipCode <cityZipCode>
    When Trying to obtain the build link
    Then The build link returned is <buildLink>

    Examples: 
      | path                                      | address          | cityZipCode | buildLink                                                                                                                                                          |
      | /webservice/GetUpdatedPropertyDetails.htm | 2114 Bigelow Ave | Seattle WA  | http://www.zillow.com/webservice/GetUpdatedPropertyDetails.htm?zws-id=X1-ZWz19hkt3gwmbv_9hc6e&zpid=48749425&address=2114%20Bigelow%20Ave&citystatezip=Seattle%20WA |

  Scenario Outline: text Analysis
    Given The description <description>
    When The program extracts information from description
    Then It recieves the response <response>

    Examples: 
      | description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | response                                                                                                                   |
      | This 679 square foot condo home has 1 bedrooms and 1.0 bathrooms. It is located at 612 Prospect St Seattle, Washington                                                                                                                                                                                                                                                                                                                                                                                            | hasYard:false hasPool:false hasGarage:false hasFireplace:false numberOfRooms:2.0 numberOfFloors:1 numberOfSquareFoots:679  |
      | Feels like a house! Come home to this delightful 1134 square foot 2 bedroom, 1 bath corner unit with a lovely patio & beautiful back yard! Unwind in your spacious living room with built in window seating & wood-burning fireplace & wood laminate floors. Enjoy cooking & entertaining friends in your updated kitchen. Large master suite + updated master bath. Large 2nd bedroom & 3/4 bath. 1 storage unit + tons of extra storage! 1 garage space + lots of guest parking. Close to Fremont & Lake Union! | hasYard:true hasPool:false hasGarage:false hasFireplace:true numberOfRooms:5.0 numberOfFloors:1 numberOfSquareFoots:1134   |
      | This 4480 square foot single family home has 4 bedrooms and 4.0 bathrooms. It is located at 1008 4th Ave N Seattle, Washington.                                                                                                                                                                                                                                                                                                                                                                                   | hasYard:false hasPool:false hasGarage:false hasFireplace:false numberOfRooms:8.0 numberOfFloors:1 numberOfSquareFoots:4480 |
      
     Scenario Outline: concurency testing
    Given A number <thread> of threads
    When  The api gets called
    Then  Timeout doesn't occur
    
    Examples:
    |thread|
    |30|
