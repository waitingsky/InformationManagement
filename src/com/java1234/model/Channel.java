package com.java1234.model;

public class Channel {

	private int channelId;
	private String channelName;

	public Channel() {
		super();
	}

	public Channel(String typeName) {
		super();
		this.channelName = typeName;
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

}
