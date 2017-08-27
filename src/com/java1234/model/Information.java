package com.java1234.model;

import java.util.Date;
import java.util.List;

public class Information {

	private int informationId;
	private String title;
	private String author;
	
	private Date publishDate;
	
	private int channelId=-1;
	private String channelName;
	
	private int type = 0; // 0 normal;1 attachment;2 link
	
	private int clickCount;
	private boolean head;
	private boolean hot;

	private String content;
	private List<String> attachments;
	
	public Information() {
		super();
	}

	public Information(int id, String title) {
		this.informationId = id;
		this.title=title;
	}

	public int getInformationId() {
		return informationId;
	}

	public void setInformationId(int informationId) {
		this.informationId = informationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public boolean isHead() {
		return head;
	}

	public void setHead(boolean head) {
		this.head = head;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
}
