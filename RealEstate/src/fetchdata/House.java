package fetchdata;

public class House {
	private final String street;
	private final String zipcode;
	private final String city;
	private final String state;
	private final String lotsizeSqFt;
	private final String bathrooms;
	private final String bedrooms;
	private final String rooms;
	private final String useCode;
	private final String imageUrl;
	private String price;
	
	public static class Builder{
		private final String street;
		private final String zipcode;
		private final String city;
		private final String state;
		private String lotsizeSqFt;
		private String bathrooms;
		private String bedrooms;
		private String rooms;
		private String useCode;
		private String imageUrl;
		
		public Builder(String street, String zipcode, String city, String state){
			this.street = street;
			this.zipcode = zipcode;
			this.city = city;
			this.state = state;
		}
		
		public Builder lotsizeSqFt(String lotsizeSqFt){
			this.lotsizeSqFt = lotsizeSqFt;
			return this;
		}
		
		public Builder bathrooms(String bathrooms){
			this.bathrooms = bathrooms;
			return this;
		}
		
		public Builder bedrooms(String bedrooms){
			this.bedrooms = bedrooms;
			return this;
		}
		public Builder rooms(String rooms){
			this.rooms = rooms;
			return this;
		}
		public Builder useCode(String useCode){
			this.useCode = useCode;
			return this;
		}
		public Builder imageUrl(String imageUrl){
			this.imageUrl = imageUrl;
			return this;
		}
		public House build(){
			return new House(this);
		}
	}
	
	private House(Builder builder){
		street = builder.street;
		zipcode = builder.zipcode;
		city = builder.city;
		state = builder.state;
		lotsizeSqFt = builder.lotsizeSqFt;
		bathrooms = builder.bathrooms;
		bedrooms = builder.bedrooms;
		rooms = builder.rooms;
		useCode = builder.useCode;
		imageUrl = builder.imageUrl;
	}

	
	public void setPrice(String price){
		this.price = price;
	}
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("House object: \n");
		output.append(String.format("street : %s \n", street));
		output.append(String.format("zipcode : %s \n", zipcode));
		output.append(String.format("city : %s \n", city));
		output.append(String.format("state : %s \n", state));
		output.append(String.format("lotsizeSqFt : %s \n", lotsizeSqFt));
		output.append(String.format("bathrooms : %s \n", bathrooms));
		output.append(String.format("bedrooms : %s \n", bedrooms));
		output.append(String.format("rooms : %s \n", rooms));
		output.append(String.format("useCode : %s \n", useCode));
		output.append(String.format("price : %s $\n", price));
		output.append(String.format("imageUrl : %s\n", imageUrl));
		return output.toString();
	}
	
	

}
