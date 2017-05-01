package com.sky.commonutil.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankEntity{

	@SerializedName("createdAt")
	@Expose
	private String createdAt;

	@SerializedName("images")
	@Expose
	private List<String> images;

	@SerializedName("publishedAt")
	@Expose
	private String publishedAt;

	@SerializedName("_id")
	@Expose
	private String id;

	@SerializedName("source")
	@Expose
	private String source;

	@SerializedName("used")
	@Expose
	private boolean used;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("url")
	@Expose
	private String url;

	@SerializedName("desc")
	@Expose
	private String desc;

	@SerializedName("who")
	@Expose
	private String who;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setImages(List<String> images){
		this.images = images;
	}

	public List<String> getImages(){
		return images;
	}

	public void setPublishedAt(String publishedAt){
		this.publishedAt = publishedAt;
	}

	public String getPublishedAt(){
		return publishedAt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setUsed(boolean used){
		this.used = used;
	}

	public boolean isUsed(){
		return used;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	public void setWho(String who){
		this.who = who;
	}

	public String getWho(){
		return who;
	}

	@Override
 	public String toString(){
		return 
			"GankEntity{" + 
			"createdAt = '" + createdAt + '\'' + 
			",images = '" + images + '\'' + 
			",publishedAt = '" + publishedAt + '\'' + 
			",_id = '" + id + '\'' + 
			",source = '" + source + '\'' + 
			",used = '" + used + '\'' + 
			",type = '" + type + '\'' + 
			",url = '" + url + '\'' + 
			",desc = '" + desc + '\'' + 
			",who = '" + who + '\'' + 
			"}";
		}
}