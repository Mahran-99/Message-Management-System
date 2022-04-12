package app;

import model.User;

import java.util.ArrayList;

import javax.ws.rs.PathParam;

import model.Message;
import model.Response;
import model.Topics;

public interface Services {

	public Response sub_User(@PathParam("name") String topicName,User new_User);

	public Response unsub_User(@PathParam("id") int id, Topics topics);

	public ArrayList<User> getAllsubs(Topics topic);

	public Response addTopic(Topics topic);

	public Topics[] ListTopics();

	public ArrayList<Message> ListMessages(Topics topics);

	public Response Message(Message message);

	public Message retriveMessage(@PathParam("id") int id, Topics topics);

}
