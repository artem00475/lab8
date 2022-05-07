package commands;

import Messages.Request;

/**
 * Команда, выводящая элементы, значение которых меньше заданного
 */
public class FilterLessThanEyeColorCommand implements Command {

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "filter_less_than_eye_color";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля eyeColor которых меньше заданного";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new FilterLessThanEyeColorCommand()),argument);
    }
}
