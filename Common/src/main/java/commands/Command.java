package commands;

import java.io.Serializable;

/**
 * Интерфейс, описывающий поведение команд
 */
public interface Command extends Serializable {
    /**
     * Возвращает имя команды
     * @return имя команды
     */
    String getName();

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    String getDescription();

    /**
     * Устанавливает есть ли у команды аргумент
     * @return {@code true} если аргумент есть, иначе {@code false}
     */
    boolean hasArgement();

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды (если есть)
     */
    void execute(Boolean argument, CommandManager commandManager);
}
