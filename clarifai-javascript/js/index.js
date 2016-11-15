

 	// function to initialize the keys
	function initialize (clientId, clientSecret) {
		// getting the credential through calling getKeys()

		this.clientId = clientId;
		this.clientSecret=clientSecret;

}

function initialize(){}


function check(clientId, clientSecret)
{
		
			// Initialize the Clarifai api here			
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


(function ($, Clarifai) {
	$(document).ready(function () {
		Printer.print();
		check(init.clientId,init.clientSecret)
	})

	// Finding a bunch of elements in the DOM
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

		// Preparing the error message
		var errorMessage = "<p>" + error.status_msg + "</p>" 
		var errorHtml = "<div class='errorBox'><h1>Error ❌</h1>" + errorMessage + "</div>"

		// throwing the errorHtml in .tags
		tags.html(errorHtml)
		tagsContainer.show()
	}




}(jQuery, Clarifai));
