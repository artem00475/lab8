package Script;

import person.*;
import exceptions.*;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

/**
 * Класс, реализующий ввод с консоли
 */
public class ConsoleManager {
    /**
     * Возвращает объект класса {@link Person} из консоли
     * @return объект класса {@link Person}
     */
    public Person getPersonFromConsole(Scanner scanner){
            System.out.println("Введите данные элемента:");
        return new Person(getName(scanner), getCoordinatesX(scanner), getCoordinatesY(scanner), getHeight(scanner), getEyeColor(scanner), getHairColor(scanner), getNationality(scanner), getLocationX(scanner), getLocationY(scanner), getLocationZ(scanner), getLocationName(scanner));
    }

    /**
     * Возвращает имя из консоли
     * @return name
     */
    public String getName(Scanner scanner){
        String name;
        while (true) {
            try {
                System.out.print("Введите поле name: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new NameException("У элемента отсутсвует поле name");
                }
                name = scanned;
                break;
            }catch (NameException ignored) {}
        }
        return name;
    }

    /**
     * Возвращает координату Х из консоли
     * @return coordinatesX
     */
    public Integer getCoordinatesX(Scanner scanner){
        int coordinatesX;
        while (true) {
            try {
                System.out.print("Введите поле coordinatesX: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new CoordinatesException("У элемента отсутствует поле coordinatesX");
                }
                try {
                    coordinatesX = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                    throw new CoordinatesException("В поле coordinatesX нечисловое значение");
                }
                if (coordinatesX>408) {throw new CoordinatesException("Значение не должно превышать 408");}
                break;
            }catch (CoordinatesException ignored) {}
        }
        return coordinatesX;
    }

    /**
     * Возвращает координату Y из консоли
     * @return coordinatesY
     */
    public Integer getCoordinatesY(Scanner scanner){
        int coordinatesY;
        while (true) {
            try {
                System.out.print("Введите поле coordinatesY: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new CoordinatesException("У элемента отсутствует поле coordinatesY");
                }
                try {
                    coordinatesY = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                    throw new CoordinatesException("В поле coordinatesY нечисловое значение");
                }
                if (!(coordinatesY>-806)) {throw new CoordinatesException("Значение должно быть больше -806");}
                break;
            }catch (CoordinatesException ignored) {}
        }
        return coordinatesY;
    }

    /**
     * Возвращает рост из консоли
     * @return height
     */
    public Double getHeight(Scanner scanner){
        double height;
        while (true) {
            try {
                System.out.print("Введите поле height: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new HeightException("У элемента отсутствует поле height");
                }
                try {
                    height = Double.parseDouble(scanned);
                } catch (NumberFormatException e) {
                    throw new HeightException("В поле height нечисловое значение");
                }
                if (!(height >0)) {throw new HeightException("Значение должно быть больше 0");}
                break;
            }catch (HeightException ignored) {}
        }
        return height;
    }

    /**
     * Возвращает цвет глаз из консоли
     * @return eyeColor {@link ColorE}
     */
    public ColorE getEyeColor(Scanner scanner){
        ColorE eyeColor;
        while (true) {
            try {
                System.out.print("Введите поле eyeColor (из списка: green, red, yellow, brown): ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new EyeColorException("У элемента отсутствует поле eyeColor");
                }
                try {
                    eyeColor = ColorE.valueOf(scanned.toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    throw new EyeColorException("Некорректное значение поля eyeColor");
                }
                break;
            }catch (EyeColorException ignored) {}
        }
        return eyeColor;
    }

    /**
     * Возвращает цвет волос из консоли
     * @return hairColor {@link ColorH}
     */
    public ColorH getHairColor(Scanner scanner){
        ColorH hairColor;
        while (true) {
            try {
                System.out.print("Введите поле hairColor (из списка: red, black, orange, brown): ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new HairColorException("У элемента отсутствует поле hairColor");
                }
                try {
                    hairColor = ColorH.valueOf(scanned.toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    throw new HairColorException("Некорректное значение поля hairColor");
                }
                break;
            }catch (HairColorException ignored) {}
        }
        return hairColor;
    }

    /**
     * Возвращает национальность из консоли
     * @return nationality {@link Country}
     */
    public Country getNationality(Scanner scanner){
        Country nationality;
        while (true) {
            try {
                System.out.print("Введите поле nationality (из списка: usa, spain, india): ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new NationalityException("У элемента отсутствует поле nationality");
                }
                try {
                    nationality = Country.valueOf(scanned.toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    throw new NationalityException("Некорректное значение поля nationality");
                }
                break;
            }catch (NationalityException ignored) {}
        }
        return nationality;
    }

    /**
     * Возвращает координату Х местопложения из консоли
     * @return locationX
     */
    public Integer getLocationX(Scanner scanner){
        int locationX;
        while (true) {
            try {
                System.out.print("Введите поле locationX: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new LocationException("У элемента отсутствует поле locationX");
                }
                try {
                    locationX = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                    throw new LocationException("В поле locationX нечисловое значение");
                }
                break;
            }catch (LocationException ignored) {}
        }
        return locationX;
    }

    /**
     * Возвращает координату Y местопложения из консоли
     * @return locationY
     */
    public Double getLocationY(Scanner scanner){
        double locationY;
        while (true) {
            try {
                System.out.print("Введите поле locationY: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new LocationException("У элемента отсутствует поле locationY");
                }
                try {
                    locationY=Double.parseDouble(scanned);
                } catch (NumberFormatException e) {
                    throw new LocationException("В поле locationY нечисловое значение");
                }
                break;
            }catch (LocationException ignored) {}
        }
        return locationY;
    }

    /**
     * Возвращает координату Z местопложения из консоли
     * @return locationZ
     */
    public Long getLocationZ(Scanner scanner){
        long locationZ;
        while (true) {
            try {
                System.out.print("Введите поле locationZ: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new LocationException("У элемента отсутствует поле locationZ");
                }
                try {
                    locationZ = Long.parseLong(scanned);
                } catch (NumberFormatException e) {
                    throw new LocationException("В поле locationZ нечисловое значение");
                }
                break;
            }catch (LocationException ignored) {}
        }
        return locationZ;
    }

    /**
     * Возвращает название местопложения из консоли
     * @return locationName
     */
    public String getLocationName(Scanner scanner){
        String locationName;
        while (true) {
            try {
                System.out.print("Введите поле locationName: ");
                String scanned = scanner.nextLine();
                if (scanned.equals("")) {
                    throw new LocationException("У элемента отсутсвует поле locationName");
                }
                locationName = scanned;
                if (locationName.length()>374) {throw new LocationException("Длина поля locationName не должна превышать 374");}
                break;
            }catch (LocationException ignored) {}
        }
        return locationName;
    }

    public File getFile(Scanner scanner) {
        while (true){
            System.out.println("Введите имя файла: ");
            String path = scanner.nextLine();
            try{
            if (path.isEmpty()){throw new FileException("Ничего не введено");}
            File file = new File(path);
            if (!file.isFile()){throw new FileException("Это не файл");}
            if (!file.canRead()){throw new FileException("Нет доступа к файлу");}
            return file;
            }catch (FileException ignored){}
        }
    }

    /**
     * Возвращает значение id из консоли
     * @return id
     */
    public int getID(Scanner scanner){
        System.out.print("Введите значение id: ");
        int id;
        while (true) {
            try {
                String scanned = scanner.nextLine();
                if (scanned.isEmpty()) {
                    throw new IdException("Ничего не введено");
                }
                try {
                    id = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                    throw new IdException("Введено нечисловое значение");
                }
                //if (!Person.getIdArray().contains(id)){
                    //throw new IdException("Элемента с таким id нет в коллекции");
               // }
                break;
            }catch (IdException e) {
                System.out.println("Введите значение id снова: ");
            }
        }
        return id;
    }

    /**
     * Возвращает объект класса {@link Location} из консоли
     * @return объект класса {@link Location}
     */
    public Location getLocationFromConsole(Scanner scanner){
        return new Location(getLocationX(scanner),getLocationY(scanner),getLocationZ(scanner),getLocationName(scanner));
    }

}
