
	function initialize (clientId, clientSecret) {


		this.clientId = clientId;
		this.clientSecret=clientSecret;
}

function initialize(){}


function check(clientId, clientSecret)
{
		
			Clarifai.initialize({
				clientId: clientId,
				clientSecret: clientSecret
			})


	}



var obtainKeys= (function() {
  var keys=getKeys() || {};

  return {
    Keys: function() {
      return keys;
  }

}})();



    function gotKeysConfirmation() {
        console.log("Got client id and client secret");
    }

    function beforeApiCall()
    {
    	console.log("Preparing to make the APi call and assign colored tags");
    }
    

    function afterApiCall()
    {
    	console.log("Color tags have been assigned");
    }

    var LogInfo = AOP.aspect(gotKeysConfirmation).after(getKeys); 
  

    LogInfo();
   


var init = new initialize();
initialize.prototype.clientId=obtainKeys.Keys()["CLARIFAI_CLIENT_ID"];
initialize.prototype.clientSecret=obtainKeys.Keys()["CLARIFAI_CLIENT_SECRET"];



var printer = (function () {

  var printerInstance;

  function create () {

    function print() {
      
      console.log(init.clientSecret, init.clientId)
    }


    return {
     
      print: print
    };
  }

  return {
    getInstance: function() {
      if(!printerInstance) {
        printerInstance = create();
      }
      return printerInstance;
    }
  };

  function Singleton () {
    if(!printerInstance) {
      printerInstance = intialize();
    }
  };

})();

var Printer = printer.getInstance();


var callApi=(function ($, Clarifai) {
	$(document).ready(function () {
		Printer.print();
		check(init.clientId,init.clientSecret)
	})

	var app = $(".app")
	var imageInput = $("#imageUrl");
	var submitButton = $("#submitBtn");
	var image = $("#image");
	var tagsContainer = $(".tags-container");
	var tags = $(".tags");

	submitButton.on("click", function (event) {
		
		var url = imageInput.val()
		tagsContainer.hide()

	
		image.attr("src", url)


		

		 Clarifai.getTagsByUrl(url,function(error,response) {

		 	if(error)
		 	{
		 		displayError(error)
		 	}
		 	else if(response)
		 	{
		 		displayTag(response)
		 	}
		 })

		

	})



	function displayTag (response) {
		console.log("Clarifai Response!")
		console.log(response)

		
		var image 	= 	response.results[0]


		var tag 	= 	image.result.tag

		var conceptsLength = tag.classes.length


		var concepts = tag.classes.map(function (value, index, array) {
			var prob = Number.parseFloat(tag.probs[index]);
			var probPercentage = (prob * 100).toString() + "%";

			
			var progressBarColor = assignColor(prob, index, conceptsLength)

			var progressBar = `<div class="progress">
						<div class="progress-bar progress-bar-${progressBarColor}" role="progressbar" aria-valuenow="${prob}" aria-valuemin="0" aria-valuemax="100" style="width: ${probPercentage};">
							${prob}
						</div>
					</div>`

			
			return `<div class="row">
						<div class="col-sm-12">
							<h3>${value}</h3>
							<h4>Concept ID: ${tag["concept_ids"][index]}</h4>
							${progressBar}
						</div>
					</div>`
		})

		
		tags.html(concepts.join(""))

	
		tagsContainer.show()

	}


    var BeforeAssigningColorTags = AOP.aspect(beforeApiCall).before(assignColor); 
    var AfterAssigningColorTags = AOP.aspect(afterApiCall).after(assignColor); 
    BeforeAssigningColorTags();
    AfterAssigningColorTags();



	function assignColor (prob, index, length) {
		if (prob > 0.9) {
			return "success"
		}
		else if (prob > 0.85 && prob <= 0.9) {
			return "info"
		}
		else {
			return "warning"
		}
	}

	
	function displayError (error) {


		var errorMessage = "<p>" + error.status_msg + "</p>" 
		var errorHtml = "<div class='errorBox'><h1>Error ❌</h1>" + errorMessage + "</div>"
		tags.html(errorHtml)
		tagsContainer.show()
	}




}(jQuery, Clarifai));