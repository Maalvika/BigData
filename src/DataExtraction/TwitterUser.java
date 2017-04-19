package DataExtraction;

public class TwitterUser {
	
	private int id;
	private String name;
	private String description;
	private int followers_count;
	private int friends_count;
	private String language;
	
	public TwitterUser() {
		
		this.id = -1;
		this.name = null;
		this.description = null;
		this.followers_count = -1;
		this.friends_count = -1;
		this.language = null;
	}
	
	public TwitterUser(int id, String name, String description, int followers_count, int friends_count,
			String language) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.followers_count = followers_count;
		this.friends_count = friends_count;
		this.language = language;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	public int getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getId()+"\t");
		sb.append(getName()+"\t");
		sb.append(getDescription()+"\t");
		sb.append(getFollowers_count()+"t");
		sb.append(getFriends_count()+"\t");
		sb.append(getLanguage()+"\n");
	
		return sb.toString();
	}
	

}
