package com.java1234.util;

public class NavUtil {

	public static String genNewsManageNavigation(String modName,String actionName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("��ǰλ�ã�&nbsp;&nbsp;");
		navCode.append("��ҳ&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(modName+"&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(actionName+"&nbsp;&nbsp;");
		return navCode.toString();
	}
	
	public static String genNewsListNavigation(String channelName,String channelId){
		StringBuffer navCode=new StringBuffer();
		navCode.append("��ǰλ�ã�&nbsp;&nbsp;");
		navCode.append("<a href='goIndex'>��ҳ</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='information?action=list&channelId="+channelId+"'>"+channelName+"</a>");
		return navCode.toString();
	}
	
	public static String genNewsNavigation(String channelName,String channelId,String informationName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("��ǰλ�ã�&nbsp;&nbsp;");
		navCode.append("<a href='goIndex'>��ҳ</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='information?action=list&channelId="+channelId+"'>"+channelName+"</a>&nbsp;&nbsp;>&nbsp;&nbsp;"+informationName);
		return navCode.toString();
	}
}
