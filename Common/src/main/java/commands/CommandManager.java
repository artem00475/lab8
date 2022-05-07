package commands;

import Messages.Message;
import Messages.Request;

public interface CommandManager {
    Message execute(Request request, boolean argument);
}
