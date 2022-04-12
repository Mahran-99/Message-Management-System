package app;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Message;
import model.Response;
import model.User;
import model.Topics;

@Path("/service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicesImpl implements Services {

	private static Map<String, ArrayList<User>> Sub_Users = new HashMap<String, ArrayList<User>>();
	private static Map<Integer, Topics> Topic = new HashMap<Integer, Topics>();
	private static Map<String, ArrayList<Message>> Messages = new HashMap<String, ArrayList<Message>>();

	@Override
	@POST
	@Path("/{name}/subUser")
	public Response sub_User(@PathParam("name") String topicName, User new_User) {
		Response response = new Response();
		ArrayList<User> userDetails = new ArrayList<User>();
		for (int j = 1; j <= Topic.size(); j++) {
			Topics topic = Topic.get(j);
			if (topic.getName().equalsIgnoreCase(topicName) == true) {
				if (Sub_Users.get(topicName) == null) {
					userDetails.add(new_User);
					Sub_Users.put(topicName, userDetails);
				} else {
					userDetails = Sub_Users.get(topicName);
					for (int i = 0; i < userDetails.size(); i++) {
						User old_User = userDetails.get(i);
						if (old_User.getId() == new_User.getId()) {
							response.setStatus(false);
							response.setMessage("ID Already Taken....Change ID");
							return response;
						} else {
							continue;
						}
					}
					userDetails.add(new_User);
					Sub_Users.put(topicName, userDetails);
				}
				response.setStatus(true);
				response.setMessage("User Subscribed to " + topicName + " Topic Successfully");
				return response;
			} else {
				continue;
			}
		}
		response.setStatus(false);
		response.setMessage("Topic Doesn't Exist...Can't Add User");
		return response;

	}

	@Override
	@GET
	@Path("/{id}/unsubUser")
	public Response unsub_User(@PathParam("id") int id, Topics topics) {
		Response response = new Response();
		ArrayList<User> userDetails = new ArrayList<User>();
		for (int j = 1; j <= Topic.size(); j++) {
			Topics topic = Topic.get(j);
			if (topic.getId() == topics.getId()) {
				if (Sub_Users.get(topics.getName()) == null) {
					response.setStatus(false);
					response.setMessage("No Subscribers in Topic");
					return response;
				} else {
					userDetails = Sub_Users.get(topics.getName());
					for (int i = 0; i < userDetails.size(); i++) {
						User old_User = userDetails.get(i);
						if (old_User.getId() == id) {
							userDetails.remove(i);
							Sub_Users.put(topics.getName(), userDetails);
							response.setStatus(true);
							response.setMessage("User Un-Subscribed From " + topics.getName() + " Topic Successfully");
							return response;
						} else {
							continue;
						}
					}
					response.setStatus(false);
					response.setMessage("Can't Find User with Specified ID");
					return response;
				}
			} else {
				continue;
			}
		}
		response.setStatus(false);
		response.setMessage("Topic Doesn't Exist");
		return response;
	}

	@Override
	@GET
	@Path("/listSubs")
	public ArrayList<User> getAllsubs(Topics topic) {
		ArrayList<User> userDetails = new ArrayList<User>();
		for (int i = 1; i <= Topic.size(); i++) {
			Topics topics = Topic.get(i);
			if (topics.getId() == topic.getId()) {
				if (Sub_Users.get(topic.getName()) == null) {
					return null;
				} else {
					userDetails = Sub_Users.get(topic.getName());
					return userDetails;
				}
			}

		}
		return null;

	}

	@Override
	@POST
	@Path("/addtopic")
	public Response addTopic(Topics topic) {

		Response response = new Response();
		if (Topic.get(topic.getId()) != null) {
			response.setStatus(false);
			response.setMessage("Topic ID Taken....Try Another ID");
			return response;
		}
		Topic.put(topic.getId(), topic);
		response.setStatus(true);
		response.setMessage("Topic created successfully");
		return response;
	}

	@Override
	@GET
	@Path("/listTopics")
	public Topics[] ListTopics() {
		Set<Integer> ids = Topic.keySet();
		Topics[] topics = new Topics[ids.size()];
		int i = 0;
		for (Integer id : ids) {
			topics[i] = Topic.get(id);
			i++;
		}
		return topics;

	}

	@Override
	@GET
	@Path("/listMessages")
	public ArrayList<Message> ListMessages(Topics topics) {
		ArrayList<Message> messageDetails = new ArrayList<Message>();
		if (Topic.containsKey(topics.getId())) {
			messageDetails = Messages.get(topics.getName());
			return messageDetails;
		} else {
			return null;
		}
	}

	@Override
	@POST
	@Path("/message")
	public Response Message(Message message) {
		Response response = new Response();
		ArrayList<User> userDetails = new ArrayList<User>();
		ArrayList<Message> messageDetails = new ArrayList<Message>();
		if (Topic.containsKey(message.getTopic_id())) {
			Topics topicName = Topic.get(message.getTopic_id());
			String Name = topicName.getName();
			userDetails = Sub_Users.get(Name);
			for (int j = 0; j < userDetails.size(); j++) {
				User user = userDetails.get(j);
				if (user.getId() == message.getSender_id()) {
					if (Messages.get(Name) == null) {
						messageDetails.add(message);
						Messages.put(Name, messageDetails);
					} else {
						messageDetails = Messages.get(Name);
						for (int i = 0; i < messageDetails.size(); i++) {
							Message old_Message = messageDetails.get(i);
							if (old_Message.getId() == message.getId()) {
								response.setStatus(false);
								response.setMessage("ID Taken....Change ID");
								return response;
							} else {
								continue;
							}
						}
						messageDetails.add(message);
						Messages.put(Name, messageDetails);
					}

				} else {
					response.setStatus(false);
					response.setMessage("User isn't Subbed to this Topic");
					return response;
				}
			}
			response.setStatus(true);
			response.setMessage("Content Added");
			return response;
		} else {
			response.setStatus(false);
			response.setMessage("Couldn't Add Content....Make Sure Topic ID is Correct");
			return response;
		}

	}

	@Override
	@GET
	@Path("/{id}/retrive")
	public Message retriveMessage(@PathParam("id") int id, Topics topics) {
		ArrayList<Message> messageDetails = new ArrayList<Message>();
		messageDetails = Messages.get(topics.getName());
		for (int i = 0; i < messageDetails.size(); i++) {
			if (messageDetails.get(i).getId() == id) {
				return messageDetails.get(i);
			} else {
				continue;
			}
		}
		return null;
	}

}
