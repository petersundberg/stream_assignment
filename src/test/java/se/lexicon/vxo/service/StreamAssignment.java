package se.lexicon.vxo.service;

import org.junit.jupiter.api.Test;
import se.lexicon.vxo.model.Gender;
import se.lexicon.vxo.model.Person;
import se.lexicon.vxo.model.PersonDto;
import sun.invoke.empty.Empty;
import sun.reflect.generics.tree.Tree;

import javax.print.attribute.IntegerSyntax;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Your task is not make all tests pass (except task1 because its non testable).
 * You have to solve each task by using a java.util.Stream or any of it's variance.
 * You also need to use lambda expressions as implementation to functional interfaces.
 * (No Anonymous Inner Classes or Class implementation of functional interfaces)
 *
 */
public class StreamAssignment {

    private static List<Person> people = People.INSTANCE.getPeople();

    /**
     * Turn integers into a stream then use forEach as a terminal operation to print out the numbers
     */
    @Test
    public void task1(){
        List<Integer> integers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        //Write code here
        integers.stream()
                .forEach(System.out::println);
    }

    /**
     * Turning people into a Stream count all members
     */
    @Test
    public void task2(){
        long amount = 0;
        //Write code here
//Först gjorde jag denna kod, men varför måste jag först skapa en personList?
//        List<Person> personList = people.stream()
//                .collect(Collectors.toList());
//        amount= personList.stream().count();

//Denna verkar också fungera ...:
        people.stream()
                .collect(Collectors.toList());

        amount= people.stream().count();


        System.out.println("Number of people: " + amount);

        assertEquals(10000, amount);
    }

    /**
     * Count all people that has Andersson as lastName.
     */
    @Test
    public void task3(){
        long amount = 0;
        int expected = 90;

        //Write code here
        List<Person> allAnderssons = null;
        allAnderssons = people.stream()
                .filter(person -> person.getLastName().equalsIgnoreCase("Andersson"))
                .collect(Collectors.toList());
        amount = allAnderssons.stream().count();


        System.out.println("Expected number of people with last name 'Andersson': " + expected);
        System.out.println("Number of people with last name 'Andersson': " + amount);

        assertEquals(expected, amount);

    }

    /**
     * Extract a list of all female
     */
    @Test
    public void task4(){
        int expectedSize = 4988;
        List<Person> females = null;

        //Write code here
        females = people.stream()
                .filter(person -> person.getGender().equals(Gender.FEMALE))
                .collect(Collectors.toList());

        System.out.println("Expected number of Females: " + expectedSize);
        System.out.println("Actual number of Females: " + females.size()+"\n");

        for(int i=0;i<females.size(); i++){
            System.out.println(females.get(i).getFirstName() + " " + females.get(i).getLastName());
        }

        assertNotNull(females);
        assertEquals(expectedSize, females.size());
    }

    /**
     * Extract a TreeSet with all birthDates
     */
    @Test
    public void task5(){
        int expectedSize = 8882;
        Set<LocalDate> dates = null;

        //Write code here
        dates = people.stream()
                .map(person -> person.getDateOfBirth())
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Expected number of birthDates: " + expectedSize);
        System.out.println("Actual number of birthDates: " + dates.size());

        assertNotNull(dates);
        assertEquals(expectedSize, dates.size());
    }

    /**
     * Extract an array of all people named "Erik"
     */
    @Test
    public void task6(){
        int expectedLength = 3;

        Person[] result = null;

        //Write code here
        result = people.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase("Erik"))
                .toArray(Person[]::new);

        System.out.println("Expected length of array: " + expectedLength);
        System.out.println("Length of 'result' array: " + result.length +"\n");

        for(int i=0;i<result.length;i++){
            System.out.println(result[i]);
        }

        assertNotNull(result);
        assertEquals(expectedLength, result.length);
    }

    /**
     * Find a person that has id of 5436
     */
    @Test
    public void task7(){
        Person expected = new Person(5436, "Tea", "Håkansson", LocalDate.parse("1968-01-25"), Gender.FEMALE);

        Optional<Person> optional = null;

        //Write code here
        optional = people.stream()
                .filter(person -> person.getPersonId() == 5436)
                .findFirst();

        System.out.println(optional.get());

        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(expected, optional.get());
    }

    /**
     * Using min() define a comparator that extracts the oldest person i the list as an Optional
     */
    @Test
    public void task8(){
        LocalDate expectedBirthDate = LocalDate.parse("1910-01-02");

        Optional<Person> optional = null;

        //Write code here
        optional = people.stream()
                .min(Comparator.comparing(Person::getDateOfBirth));

        System.out.println("Result of optional: " + optional.get().getFirstName() +" " + optional.get().getLastName()
                + ", born " + optional.get().getDateOfBirth());

        assertNotNull(optional);
        assertEquals(expectedBirthDate, optional.get().getDateOfBirth());
    }

    /**
     * Map each person born before 1920-01-01 into a PersonDto object then extract to a List
     */
    @Test
    public void task9(){
        int expectedSize = 892;
        LocalDate date = LocalDate.parse("1920-01-01");

        List<PersonDto> dtoList = null;

        //Write code here
        dtoList = people.stream()
                .filter(person -> person.getDateOfBirth().isBefore(date))
                .map(person -> new PersonDto(person.getPersonId(), person.getFirstName() + " " + person.getLastName()  + " " + person.getDateOfBirth()))
                .collect(Collectors.toList());

        System.out.println("Expected size of list: " + " " + expectedSize);
        System.out.println("Actual size of list: " + dtoList.size());

        assertNotNull(dtoList);
        assertEquals(expectedSize, dtoList.size());
    }

    /**
     * In a Stream Filter out one person with id 5914 from people and take the birthdate and build a string from data that the date contains then
     * return the string.
     */
    @Test
    public void task10(){
        String expected = "WEDNESDAY 19 DECEMBER 2012";
        int personId = 5914;

        Optional<String> optional = null;

        //Write code here
        optional = people.stream()
                .filter(person -> person.getPersonId() == personId)
                .map(person -> person.getDateOfBirth().format((DateTimeFormatter.ofPattern("eeee dd MMMM YYYY"))).toUpperCase())
                .findFirst();

        System.out.println("Expected String: " + " " + expected);
        System.out.println("Actual String: " + optional.get() + " (det blir 'ONSDAG' ist f 'WEDNESDAY'?!)");

        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(expected, optional.get());
    }

    /**
     * Get average age of all People by turning people into a stream and use defined ToIntFunction personToAge
     * changing type of stream to an IntStream.
     */
    @Test
    public void task11(){
        ToIntFunction<Person> personToAge =
                person -> Period.between(person.getDateOfBirth(), LocalDate.parse("2019-12-20")).getYears();
        double expected = 54.42;
        double averageAge = 0;

        //Write code here
        averageAge = people.stream()
                .mapToInt(person -> personToAge.applyAsInt(person))
                .average().getAsDouble();

        System.out.println("Expected average age: " + expected);
        System.out.println("Actual average age: " + averageAge);

        assertTrue(averageAge > 0);
        assertEquals(expected, averageAge, .01);
    }

    /**
     * Extract from people a sorted string array of all firstNames that are palindromes. No duplicates
     */
    @Test
    public void task12(){
        String[] expected = {"Ada", "Ana", "Anna", "Ava", "Aya", "Bob", "Ebbe", "Efe", "Eje", "Elle", "Hannah", "Maram", "Natan", "Otto"};

        String[] result = null;

        //Write code here
        result = people.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(new StringBuilder(person.getFirstName()).reverse().toString()))
                .map(Person::getFirstName)
                .distinct()
                .sorted()
                .toArray(String[]::new);

        //Expected names
        for(int i = 0;i<result.length; i++){
            if(i==0){
                System.out.print("Expected names: " + result[i]);
            }
            System.out.print(", " + result[i]);
        }
        System.out.println("\nExpected length: " + expected.length);

        //Actual names
        for(int i = 0;i<result.length; i++){
            if(i==0){
                System.out.print("\nActual names: " + expected[i]);
            }
            System.out.print(", " + expected[i]);
        }
        System.out.println("\nActual length: " + expected.length);


        assertNotNull(result);
        assertArrayEquals(expected, result);
    }

    /**
     * Extract from people a map where each key is a last name with a value containing a list of all that has that lastName
     */
    @Test
    public void task13(){
        int expectedSize = 107;
        Map<String, List<Person>> personMap = null;

        //Write code here
        personMap = people.stream()
                .collect(Collectors.groupingBy(Person::getLastName));

        System.out.println("Expected size: " + expectedSize);
        System.out.println("Actual size: " + personMap.size());

        assertNotNull(personMap);
        assertEquals(expectedSize, personMap.size());
    }

    /**
     * Create a calendar using Stream.iterate of year 2020. Extract to a LocalDate array
     */
    @Test
    public void task14(){
        LocalDate[] _2020_dates = null;

        //Write code here
                _2020_dates = Stream.iterate(LocalDate.of(2020,1,1),localDate ->localDate.plusDays(1))
                .limit(366)
                .toArray(LocalDate[]::new);

//Java v.11?:
//        _2020_dates = Stream.iterate(
//                LocalDate.of(2020,1,1)
//                ,localDate -> localDate.isBefore(LocalDate.of(2021,1,1))
//                ,localDate -> localDate.plusDays(1))
//                .toArray(LocalDate[]::new);

            System.out.println("Expected length: " + 366);
            System.out.println("Actual length: " + _2020_dates.length);


        assertNotNull(_2020_dates);
        assertEquals(366, _2020_dates.length);
        assertEquals(LocalDate.parse("2020-01-01"), _2020_dates[0]);
        assertEquals(LocalDate.parse("2020-12-31"), _2020_dates[_2020_dates.length-1]);
    }


}
