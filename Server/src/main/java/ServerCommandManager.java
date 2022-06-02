import Messages.Answer;
import Messages.Request;
import collection.CollectionManager;
import commands.Command;
import commands.CommandManager;
import person.ColorE;
import person.Location;
import person.Person;

public class ServerCommandManager implements CommandManager {
    private final CollectionManager collectionManager;

    public ServerCommandManager(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    public Answer execute(Request request, boolean argument) {
        Command command = request.getCommand();
        if (!command.hasArgement()) {
            if (command.getName().equals("help")) {
                return helpCommand();
            } else if (command.getName().equals("info")) {
                return infoCommand();
            } else if (command.getName().equals("show")) {
                return showCommand();
            } else if (command.getName().equals("clear")) {
                return clearCommand();
            }else if (command.getName().equals("remove_head")){
                return removeHeadCommand();
            }else {
                return printFieldAscendingLocationCommand();
            }
        }else{
            if (command.getName().equals("add")){
                return addCommand((Person) request.getObject());
            }else if (command.getName().equals("add_if_max")){
                return addIfMaxCommand((Person) request.getObject());
            }else if (command.getName().equals("remove_greater")){
                return removeGreaterCommand((Person) request.getObject());
            }else if (command.getName().equals("remove_by_id")){
                return removeByIdCommand(request.getId());
            }else if (command.getName().equals("update id")){
                return updateCommand(request.getId(), (Person) request.getObject());
            }else if (command.getName().equals("count_greater_than_location")){
                return countGreaterThanLocationCommand((Location) request.getObject());
            }else {
                return filterLessThanEyeColorCommand((ColorE) request.getObject());
            }
        }
    }

    public Answer helpCommand(){
               return new Answer("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить работу \n" +
                "remove_head : вывести первый элемент коллекции и удалить его\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "count_greater_than_location location : вывести количество элементов, значение поля location которых больше заданного\n" +
                "filter_less_than_eye_color eyeColor : вывести элементы, значение поля eyeColor которых меньше заданного\n" +
                "print_field_ascending_location : вывести значения поля location всех элементов в порядке возрастания\n",true);
    }

    public Answer infoCommand(){
        return  new Answer("Информация о коллекции:\n" +
                "Коллекция типа PriorityQueue, в которой хранятся объекты класса Person\n" +
                "Дата инициализации: " + collectionManager.getInitDate() + "\n" +
                "Количестов элементов: " + collectionManager.getCollection().size() + "\n",true);
    }

    public synchronized Answer showCommand(){
        StringBuilder stringBuilder = new StringBuilder();
        if (collectionManager.getCollection().isEmpty()) {
            stringBuilder.append("Нельзя выполнить команду show: коллекция пустая\n");
        } else {
            stringBuilder.append("Все элементы коллекции: \n");
            collectionManager.getCollection().forEach(person -> stringBuilder.append(person).append("\n"));
        }
        return new Answer(stringBuilder.toString(),true);
    }

    public synchronized Answer clearCommand(){
        if (collectionManager.getCollection().isEmpty()) {
            return new Answer("Коллекция пуста",true);
        }else if (collectionManager.removeAll()) {
            return new Answer("Из коллекции удалены все элементы, принадлежащие вам",false);
        }else return new Answer("В коллекции нет элементов, принадлежащих вам",true);
    }

    public synchronized Answer removeHeadCommand() {
        if (collectionManager.getCollection().isEmpty()) {
            return new Answer("Коллекция пуста",true);
        } else {
            Person person = collectionManager.removeFirstElement();
            if (!(person == null)) {
                return new Answer(person.toString(),false);
            }else return new Answer("Нет элементов, созданных вами",true);
        }
    }

    public synchronized Answer printFieldAscendingLocationCommand(){
        StringBuilder stringBuilder = new StringBuilder();
        if (collectionManager.getCollection().isEmpty()) {
            return new Answer("Коллекция пуста",true);
        } else {collectionManager.sortByLocation().forEach(person -> stringBuilder.append(person.getLocation()).append("\n"));
        } return new Answer(stringBuilder.toString(),true);
    }

    public synchronized Answer addCommand(Person person){
        if (collectionManager.addElement(createPerson(person))) {
            return new Answer("Элемент успешно добавлен",false);
        }else return new Answer("Объект не добавлен",true);
   }

   public synchronized Answer addIfMaxCommand(Person person){
       if (collectionManager.ifMore(person)) {
           return addCommand(person);
       } else {
           return new Answer("Значение элемента не превышает наибольшего элемента коллекции",true);
       }
   }

   public synchronized Answer removeGreaterCommand(Person person){
       if (collectionManager.removeGreater(person)){
           return new Answer("Элементы успешно удалены",false);
       } else {
           return new Answer("В коллекции нет элементов, удовлетворяющих условию, или они не принадлежат вам",true);
       }
   }

   public synchronized Answer removeByIdCommand(int id){
       if (collectionManager.removeElementByID(id)) {
           return new Answer("Элемент успешно удалён.",false);
       }else {return new Answer("Элемента с таким id нет в коллекции или он не принадлежит вам",true);}
   }

   public synchronized Answer updateCommand(int id, Person person){
        if (collectionManager.updateElement(id,person)){
            return new Answer("Элемент успешно обновлен",false);
        }else {
            return new Answer("Элемента с таким id нет в коллекции или он не принадлежит вам",true);}
        }

   public Answer countGreaterThanLocationCommand(Location location){
        return new Answer("Количество элементов, значение поля location которых больше заданного - " + collectionManager.countGreaterLocation(location),true);

   }

   public synchronized Answer filterLessThanEyeColorCommand(ColorE colorE){
        StringBuilder stringBuilder = new StringBuilder();
        collectionManager.filterLessThanEyeColor(colorE).forEach(person -> stringBuilder.append(person.toString()).append("\n"));
       if (stringBuilder.length()==0){
           return new Answer("Таких элементов нет",true);
       }else {return new Answer(stringBuilder.toString(),true);}
   }

   public Person createPerson(Person person){
        return new Person(person.getName(),person.getCoordinates().getX(),person.getCoordinates().getY(),person.getHeight(),person.getEyeColor(),person.getHairColor(),person.getNationality(),person.getLocation().getX(),person.getLocation().getY(),person.getLocation().getZ(),person.getLocation().getName());
   }
}
