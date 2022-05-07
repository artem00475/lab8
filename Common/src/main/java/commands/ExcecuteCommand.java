package commands;

import Messages.Request;

import java.io.File;
import java.util.Deque;

/**
 * Команда, выполняющая скрипт
 */
public class ExcecuteCommand implements Command {

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new ExcecuteCommand()),argument);
    }
}
