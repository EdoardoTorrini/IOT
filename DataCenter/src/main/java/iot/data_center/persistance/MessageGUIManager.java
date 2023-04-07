package iot.data_center.persistance;

import iot.data_center.models.MessageGUIModel;

public class MessageGUIManager {
    
    private MessageGUIModel msg;

    public MessageGUIManager() { this.msg = new MessageGUIModel(); }

    public MessageGUIModel getMsgGUIModel() { return this.msg; }
}
