package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement(name = "message") // sending xml

public class Message {

	private int id;
	private String content;
	private int sender_id;
	private int topic_id;
	private String timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSender_id() {
		return sender_id;
	}

	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int i) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = ts;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strDate = formatter.format(date);
		this.timestamp = strDate;
	}

	@Override
	public String toString() {
		return id + "::" + timestamp +"::" + sender_id + "::" + topic_id + "::" + content;
	}

}
