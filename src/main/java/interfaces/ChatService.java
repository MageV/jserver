package interfaces;

import tools.ChatWebSocket;

public interface ChatService {
    void sendMessage(String data);

    void remove(ChatWebSocket chatWebSocket);

    void add(ChatWebSocket chatWebSocket);
}
