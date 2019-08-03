package cn.yhcao.itchat4j.demo1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.controller.LoginController;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;


public class itchat implements IMsgHandlerFace {
	Logger logger = Logger.getLogger(itchat.class);
	String dataPath;
	public itchat(String strDataPath) {
		this.dataPath = strDataPath;
	}
	@Override
	public String textMsgHandle(BaseMsg msg) {
		logger.info("textMsgHandle:"+msg.getText());
		// String docFilePath = "D:/itchat4j/pic/1.jpg"; // 这里是需要发送的文件的路径
//		if (!msg.isGroupMsg()) { // 群消息不处理
//			// String userId = msg.getString("FromUserName");
//			// MessageTools.sendFileMsgByUserId(userId, docFilePath); // 发送文件
//			// MessageTools.sendPicMsgByUserId(userId, docFilePath);
//			String text = msg.getText(); // 发送文本消息，也可调用MessageTools.sendFileMsgByUserId(userId,text);
//			logger.info(text);
//			if (text.equals("111")) {
//				WechatTools.logout();
//			}
//			if (text.equals("222")) {
//				WechatTools.remarkNameByNickName("yaphone", "Hello");
//			}
//			if (text.equals("333")) { // 测试群列表
//				System.out.print(WechatTools.getGroupNickNameList());
//				System.out.print(WechatTools.getGroupIdList());
//				System.out.print(Core.getInstance().getGroupMemeberMap());
//			}
//			return text;
//		}
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		logger.info("picMsgHandle:"+msg.getFileName());
//		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
//		String picPath = System.getProperty("user.dir") + File.separator+"file"+ File.separator + fileName + ".jpg"; // 调用此方法来保存图片
//		DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
		return "图片保存成功";
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		logger.info("voiceMsgHandle:"+msg.getFileName());
//		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
//		String voicePath = System.getProperty("user.dir") + File.separator+"file"+ File.separator + fileName + ".mp3";
//		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
		return "声音保存成功";
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		logger.info("viedoMsgHandle:"+msg.getFileName());
//		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
//		String viedoPath = System.getProperty("user.dir") + File.separator+"file"+ File.separator + fileName + ".mp4";
//		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
		return "视频保存成功";
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		logger.info("nameCardMsgHandle");
		return "收到名片消息";
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
		logger.info("sysMsgHandle:"+msg.getContent());
	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		logger.info("verifyAddFriendMsgHandle");
		MessageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
		RecommendInfo recommendInfo = msg.getRecommendInfo();
		String nickName = recommendInfo.getNickName();
		String province = recommendInfo.getProvince();
		String city = recommendInfo.getCity();
		String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
		return text;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		logger.info("mediaMsgHandle");
		String fileName = msg.getFileName();

		logger.info(fileName);
		String strUrl = msg.getUrl();
		if(!strUrl.isEmpty()&&strUrl.startsWith("http://mp.weixin.qq.com/")&&strUrl.endsWith("#rd"))
		{
			logger.info("strUrl:\t" + strUrl);
			ItchatWriteFile(strUrl);
			String content = msg.getContent();

			int nStart = 0;
			int nEnd = 0;
			String strStartHttp = "http://mp.weixin.qq.com/";
			String strEnd = "#rd";
			nStart = content.indexOf(strStartHttp);
			while (nStart >= 0) {
				nEnd = content.indexOf(strEnd, nStart + strStartHttp.length());
				if(nEnd<0)
				{
					break;
				}
				int n = content.lastIndexOf(strStartHttp, nEnd);
				if(n>nStart)
				{
					nStart = n;
				}
				String strUrlx = content.substring(nStart, nEnd + strEnd.length());
				logger.info("strUrl:\t" + strUrlx);
				ItchatWriteFile(strUrlx);
				nStart = content.indexOf(strStartHttp, nEnd + strEnd.length());
			}
			
//			int nStart = 0;
//			int nEnd = 0;
//			String strStart = "![CDATA[";
//			String strStartHttp = "![CDATA[http";
//			String strEnd = "#rd]]";
//			String strEndrd = "#rd";
//			nStart = content.indexOf(strStartHttp);
//			while (nStart >= 0) {
//				nEnd = content.indexOf(strEnd, nStart + strStartHttp.length());
//				if(nEnd<0)
//				{
//					break;
//				}
//				int n = content.lastIndexOf(strStartHttp, nEnd);
//				if(n>nStart)
//				{
//					nStart = n;
//				}
//				String strUrlx = content.substring(nStart + strStart.length(), nEnd + strEndrd.length());
//				logger.info("strUrl:\t" + strUrlx);
//				nStart = content.indexOf(strStartHttp, nEnd + strEndrd.length());
//			}
		}
		
		return "文件" + fileName + "保存成功";
	}

	private void ItchatWriteFile(String strUrl) {
		try{
			String txtFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String txtPath = this.dataPath + File.separator+"file"+ File.separator + txtFileName;
		    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(txtPath),true));
		    writer.write(strUrl.toString()+"\n");
		    writer.close();
		}catch(Exception e){
		    e.printStackTrace();
		}
	}

}
