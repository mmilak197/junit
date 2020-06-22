package com.devmmilak.junit.logic;


import com.devmmilak.junit.domain.*;
import com.devmmilak.junit.mock.HoldingMockGenerator;
import com.devmmilak.junit.mock.UserMockGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.*;


public class WorkShop {
    /**
     * Lista holdingów wczytana z mocka.
     */
    private final List<Holding> holdings;

    private final Predicate<User> isWoman = user -> user.getSex().equals(Sex.WOMAN);
    private Predicate<User> isMan = m -> m.getSex() == Sex.MAN;

    public WorkShop() {
        final HoldingMockGenerator holdingMockGenerator = new HoldingMockGenerator();
        holdings = holdingMockGenerator.generate();
    }

    /**
     * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma.
     */
    public long getHoldingsWhereAreCompanies() {
        return holdings.stream()
                .filter(it -> it.getCompanies().size() > 0)
                .count();
    }

    /**
     * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy.
     */
    public List<String> getHoldingNames() {
        return holdings.stream()
                .map(it -> it.getName().toLowerCase())
                .collect(Collectors.toList());
    }

    /**
     * Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane.
     * String ma postać: (Coca-Cola, Nestle, Pepsico)
     */
    public String getHoldingNamesAsString() {
        return holdings.stream()
                .map(Holding::getName)
                .sorted()
                .collect(joining(", ", "(", ")"));
    }

    /**
     * Zwraca liczbę firm we wszystkich holdingach.
     */
    public long getCompaniesAmount() {
        return holdings.stream()
                .mapToInt(it -> it.getCompanies().size())
                .sum();
    }

    /**
     * Zwraca liczbę wszystkich pracowników we wszystkich firmach.
     */
    public long getAllUserAmount() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .mapToInt(it -> it.getUsers().size())
                .sum();
    }

    /**
     * Zwraca listę wszystkich nazw firm w formie listy. Tworzenie strumienia firm umieść w osobnej metodzie którą
     * później będziesz wykorzystywać.
     */
    public List<String> getAllCompaniesNames() {
//        return holdings.stream()
//                .flatMap(it -> it.getCompanies().stream())
//                .map(it -> it.getName())
//                .collect(Collectors.toList());

        return getCompanyStream().map(it -> it.getName())
                .collect(Collectors.toList());
    }


    private Stream<Company> getCompanyStream() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream());
    }

    /**
     * Zwraca listę firm jako String gdzie poszczególne firmy są oddzielone od siebie znakiem "+"
     */
    public String getAllCompaniesNamesAsString() {
        return getCompanyStream()
                .map(Company::getName)
                .collect(joining("+"));
    }

    public String getAllCompaniesNamesAsSortedString() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .map(it -> it.getName())
                .sorted()
                .collect(joining("+"));
    }

    /**
     * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+".
     * Używamy collect i StringBuilder.
     * <p>
     * UWAGA: Zadanie z gwiazdką. Nie używamy zmiennych.
     */
    public String getAllCompaniesNamesAsStringUsingStringBuilder() {
        return null;
    }


    /**
     * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach.
     */
    public long getAllUserAccountsAmount() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .mapToInt((it -> it.getAccounts().size()))
                .sum();
    }

    /**
     * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList. Obiektów nie przepisujemy
     * po zakończeniu działania strumienia.
     */
    public LinkedList<String> getAllCompaniesNamesAsLinkedList() {
        return getCompanyStream()
                .map(it -> it.getName())
                .collect(Collectors.toCollection(
                        LinkedList::new
                ));
    }

    public HashSet<String> getAllCompaniesNamesAsHashSet() {
        return getCompanyStream()
                .map(it -> it.getName())
                .collect(Collectors.toCollection(
                        HashSet::new
                ));
    }


    /**
     * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości
     * występują bez powtórzeń i są posortowane.
     */
    public String getAllCurrencies() {

        var currency = getAllCurrenciesToListAsString();

        return currency.stream()
                .sorted()
                .distinct()
                .collect(joining(", "));
    }

    private List<String> getAllCurrenciesToListAsString() {
        return holdings.stream()
                    .flatMap(it -> it.getCompanies().stream())
                    .flatMap(it -> it.getUsers().stream())
                    .flatMap(it -> it.getAccounts().stream())
                    .map(it -> it.getCurrency())
                    .map(Object::toString)
                    .collect(Collectors.toList());
    }

    /**
     * Metoda zwraca analogiczne dane jak getAllCurrencies, jednak na utworzonym zbiorze nie uruchamiaj metody
     * stream, tylko skorzystaj z  Stream.generate. Wspólny kod wynieś do osobnej metody.
     *
     * @see #getAllCurrencies()
     */
    public String getAllCurrenciesUsingGenerate() {
        var currency = getAllCurrenciesToListAsString();

        return Stream.generate(currency.iterator()::next)
                .limit(currency.size())
                .sorted()
                .distinct()
                .collect(joining(", "));
    }

    /**
     * Zwraca liczbę kobiet we wszystkich firmach. Powtarzający się fragment kodu tworzący strumień użytkowników umieść
     * w osobnej metodzie. Predicate określający czy mamy do czynienia z kobietą niech będzie polem statycznym w klasie.
     */
    public long getWomanAmount() {

        // without define Predicate
//        return holdings.stream()
//                .flatMap((it -> it.getCompanies().stream()))
//                .flatMap((it -> it.getUsers().stream()))
//                .filter(it -> it.getSex().equals(Sex.WOMAN)) // without
//                .count();

        // with define Predicate please see isWoman
        // Predicate<User> isWoman = user -> user.getSex().equals(Sex.WOMAN);
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .filter(isWoman) // with predicate
                .count();
    }

    /**
     * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency.
     */
    public BigDecimal getAccountAmountInPLN(Account accountWithOneZloty) {
        return accountWithOneZloty.getAmount()
                .multiply(BigDecimal.valueOf(accountWithOneZloty.getCurrency().rate))
                .round(new MathContext(4, RoundingMode.HALF_UP));
    }

    /**
     * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency i sumuje ją.
     */
    public Number getTotalCashInPLN(List<Account> accounts) {
        return accounts.stream()
                .mapToInt(it -> {
                    return it.getAmount().multiply(BigDecimal.valueOf(it.getCurrency().rate)).intValue();
                })
                .sum();
    }

    /**
     * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn
     * i zwraca ich imiona w formie listy.
     */
    public List<String> getOldWoman(int age) {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .filter(it -> it.getAge() > age)
                .peek(System.out::println) // print stream
                .filter(isMan)
                .map(User::getFirstName)
                .collect(Collectors.toList());
    }

    /**
     * Wyszukuje najbogatsza kobietę i zwraca ją. Metoda musi uzwględniać to że rachunki są w różnych walutach.
     */
    //pomoc w rozwiązaniu problemu w zadaniu: https://stackoverflow.com/a/55052733/9360524
    public Optional<User> getRichestWoman() {

        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .filter(isWoman)
                .max(Comparator.comparing(this::getUserAmountInPLN));

//        return getUserStream().stream()
//                .filter(isWoman)
//                .max(Comparator.comparing(this::getUserAmountInPLN));
    }

    private Double getUserAmountInPLN(final User user) {
        return user.getAccounts()
                .stream()
                .mapToDouble(it -> (it.getAmount().multiply(BigDecimal.valueOf(it.getCurrency().rate)).doubleValue()))
                .sum();
    }

    private List<User> getUserList() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .collect(Collectors.toList());
    }

    /**
     * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia.
     */
    public Set<String> getFirstNCompany(int n) {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .limit(n)
                .map(Company::getName)
                .collect(Collectors.toCollection(
                        HashSet::new
                ));
    }

    /**
     * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metodę getAccountStream.
     * Jeżeli nie udało się znaleźć najpopularniejszego rachunku metoda ma wyrzucić wyjątek IllegalStateException.
     * Pierwsza instrukcja metody to return.
     */
    public AccountType getMostPopularAccountType() {

//        getAccountList().stream()
//                .map(Account::getType)
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))

        var counter = getAccountList().stream()
                .collect(counting());

        return getAccountList().stream()
                .map(Account::getType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalStateException::new);
    }

    private List<Account> getAccountList() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .flatMap(it -> it.getAccounts().stream())
                .collect(Collectors.toList());
    }

    /**
     * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca
     * wyjątek IllegalArgumentException.
     */
    public User getUser(final Predicate<User> predicate) {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .filter(predicate)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników.
     */
    public Map<String, List<User>> getUserPerCompany() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .collect(Collectors.toMap(it -> it.getName(), it -> it.getUsers()));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty
     * typu T, tworzonych za pomocą przekazanej funkcji.
     */
    //pomoc w rozwiązaniu problemu w zadaniu: https://stackoverflow.com/a/54969615/9360524
    public Map<String, List<String>> getUserPerCompany(final Function<User, String> text) {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .collect(Collectors.toMap(Company::getName, it -> it.getUsers().stream()
                                                            .map(text)
                                                            .collect(Collectors.toList())));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako String
     * składający się z imienia i nazwiska. Podpowiedź:  Możesz skorzystać z metody entrySet.
     */
    public Map<String, List<String>> getUserPerCompanyAsString() {

//        Z użyciem BiFunction
        BiFunction<String, String, String> joinNameAndLastName =
                (x, y) -> x + " " + y;

        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .collect(Collectors.toMap(Company::getName,
                        it -> it.getUsers().stream()
                            .map(user -> joinNameAndLastName.apply(user.getFirstName(), user.getLastName()))
                            .collect(Collectors.toList()
                            )));

//            Bez użycia BiFunction
//        return holdings.stream()
//                .flatMap(it -> it.getCompanies().stream())
//                .collect(Collectors.toMap(Company::getName, this::getUsersBelongCompanySecond));
    }



    private List<String> getUsersBelongCompany(final Company company) {
        return company.getUsers().stream()
                .map(it -> it.getFirstName() + " " + it.getLastName())
                .collect(Collectors.toList());
    }

    private List<String> getUsersBelongCompanySecond(final Company company) {
        BiFunction<String, String, String> joinNameAndLastName =
                (x, y) -> x + " " + y;
        return company.getUsers().stream()
                .map(it -> joinNameAndLastName.apply(it.getFirstName(), it.getLastName()))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą.
     * Osoby "innej" płci mają zostać zignorowane. Wartością jest natomiast zbiór nazwisk tych osób.
     */
    public Map<Boolean, Set<String>> getUserBySex() {

        Predicate<User> isManOrWoman = it -> it.getSex().equals(Sex.WOMAN)
                || it.getSex().equals(Sex.MAN);

       return holdings.stream()
               .flatMap(it -> it.getCompanies().stream())
               .flatMap(it -> it.getUsers().stream())
               .filter(isManOrWoman)
               .collect(Collectors.partitioningBy(it -> it.getSex().equals(Sex.MAN), mapping(User::getLastName, toSet())));
    }

//    public void checkStatistics() {
//        holdings.stream()
//                .flatMap(it -> it.getCompanies().stream())
//                .flatMap(it -> it.getUsers().stream())
//                .flatMap(it -> it.getAccounts().stream())
//                .flatMap()
//    }

    /**
     * Zwraca mapę rachunków, gdzie kluczem jest numer rachunku, a wartością ten rachunek.
     */
    public Map<String, Account> createAccountsMap() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .flatMap(it -> it.getAccounts().stream())
                .collect(toMap(Account::getNumber, Function.identity()));
    }

    /**
     * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń.
     */
    public String getUserNames() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .distinct()
                .map(User::getFirstName)
                .sorted()
                .collect(joining(" "));
    }

    /**
     * Zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10.
     */
    public Set<User> getUsers() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .limit(10)
                .collect(toSet());
    }

    /**
     * Zapisuje listę numerów rachunków w pliku na dysku, gdzie w każda linijka wygląda następująco:
     * NUMER_RACHUNKU|KWOTA|WALUTA
     * <p>
     * Skorzystaj z strumieni i try-resources.
     */
    public void saveAccountsInFile(String s) {
        try (var lines = Files.lines(Paths.get(s))) {
            Files.write(Paths.get(String.valueOf(lines)),
                    (Iterable<String>)
                    getAccountStream()
                    .map(it -> it.getNumber() + "|" + it.getAmount() + "|" + it.getCurrency())
            ::iterator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stream<Account> getAccountStream() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .flatMap(it -> it.getAccounts().stream());
    }

    /**
     * Metoda wypisuje na ekranie wszystkich użytkowników (imię, nazwisko) posortowanych od z do a.
     * Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred Pasibrzuch, Adam Wojcik
     */
    public void showAllUser() {
        holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .sorted(Comparator.comparing(User::getFirstName).reversed())
                .forEach(it -> System.out.println(it.getFirstName() + " " + it.getLastName()));
    }

    /**
     * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu
     * przeliczona na złotówki.
     */
    //TODO: fix
    // java.lang.AssertionError:
    // Expected :87461.4992
    // Actual   :87461.3999
    public Map<AccountType, BigDecimal> getMoneyOnAccounts() {
        return
                holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .flatMap(it -> it.getAccounts().stream())
                .collect(Collectors.toMap(it -> it.getType(), it -> it.getAmount()
                .multiply(BigDecimal.valueOf(it.getCurrency().rate))
                .round(new MathContext(3, RoundingMode.DOWN)), BigDecimal::add));
//                .collect(Collectors.toMap(Account::getType, it ->
//                     it.getAmount().multiply(BigDecimal.valueOf(it.getCurrency().rate))
//                             .round(new MathContext(4, RoundingMode.HALF_UP))
//                ));
    }

    public Map<AccountType, BigDecimal> getMoneyOnAccountsSecond() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .flatMap(it -> it.getAccounts().stream())
                .collect(Collectors.toMap(Account::getType, account -> account.getAmount()
                        .multiply(BigDecimal.valueOf(account.getCurrency().rate))
                        .round(new MathContext(6, RoundingMode.DOWN)), BigDecimal::add));
    }

    public Map<AccountType, Long> getMoneyOnAccountsLong() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .flatMap(it -> it.getAccounts().stream())
                .collect(Collectors.groupingBy(Account::getType,Collectors.counting()));
    }

    /**
     * Zwraca sumę kwadratów wieków wszystkich użytkowników.
     */
    public int getAgeSquaresSum() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .mapToInt(it -> it.getAge() * it.getAge())
                .sum();
    }

    /**
     * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się
     * powtarzać, wszystkie zmienną muszą być final. Jeżeli podano liczbę większą niż liczba użytkowników należy
     * wyrzucić wyjątek (bez zmiany sygnatury metody).
     */
    public List<User> getRandomUsers(int i) {
        final var userMockGenerator = new UserMockGenerator();

//        return Optional.of(userMockGenerator.generate().stream()
//                .collect(Collectors.collectingAndThen(Collectors.toList(),
//                        it -> {
//                    Collections.shuffle(it);
//                    return it.stream();
//                        }))
//                .limit(i)
//                .distinct()
//                .collect(Collectors.toList()))
//                .orElseThrow(ArrayIndexOutOfBoundsException::new);

        return Optional.of(getUserStream()
                            .limit(i)
                            .collect(Collectors.toList()))
                .orElseThrow(ArrayIndexOutOfBoundsException::new);

//        return getUserStream()
//                .limit(4)
//                .collect(Collectors.toList());
    }

    private Stream<User> getUserStream() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream());
    }

    /**
     * 38.
     * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem
     * jest obiekt User a wartością suma pieniędzy na rachunku danego typu przeliczona na złotkówki.
     */
    public Map<Stream<AccountType>, Map<User, BigDecimal>> getMapWithAccountTypeKeyAndSumMoneyForManInPLN() {

        return getCompanyStream()
                .collect(Collectors.toMap(
                        it -> it.getUsers()
                        .stream()
                        .flatMap(itt -> itt.getAccounts()
                        .stream()
                        .map(Account::getType)), this::manWithSumMoneyOnAccounts

                ));

        //        return getUserStream()
//                .flatMap(it -> it.getAccounts().stream())
//                .collect(Collectors.toMap(Account::getType, getUserStream()
//                                                            .filter(it -> Objects.equals(it.getSex(), Sex.MAN))))
    }

    private Map<User, BigDecimal> manWithSumMoneyOnAccounts(final Company company) {
        return company
                .getUsers()
                .stream()
                .filter(it -> it.getSex().equals(Sex.MAN))
                .collect(Collectors.toMap(Function.identity(), this::getSumUserAmountInPLN));
    }

    private BigDecimal getSumUserAmountInPLN(final User user) {
        return BigDecimal.valueOf(user.getAccounts()
                .stream()
                .mapToLong(it -> it.getAmount().multiply(BigDecimal.valueOf(it.getCurrency().rate)).longValue())
                .sum());
    }

    /**
     * 39. Policz ile pieniędzy w złotówkach jest na kontach osób które nie są ani kobietą ani mężczyzną.
     */
    public BigDecimal getSumMoneyOnAccountsForPeopleOtherInPLN() {
//        return BigDecimal.valueOf(holdings.stream()
//                .flatMap(it -> it.getCompanies().stream())
//                .flatMap(it -> it.getUsers().stream())
//                .filter(it -> it.getSex().equals(Sex.OTHER))
//                .mapToLong(this::calculateAmountsOnAccounts)
//                .sum()).round(MathContext.DECIMAL32);

        return getUserStream()
                .filter(it -> it.getSex().equals(Sex.OTHER))
                .map(this::calculateAmountsOnAccounts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

        private BigDecimal calculateAmountsOnAccounts(final User user) {

        return user.getAccounts().stream()
                .map(it -> it.getAmount().multiply(BigDecimal.valueOf(it.getCurrency().rate)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    /**
     * 40.
     * Policz ile osób pełnoletnich posiada rachunek oraz ile osób niepełnoletnich posiada rachunek. Zwróć mapę
     * przyjmując klucz True dla osób pełnoletnich i klucz False dla osób niepełnoletnich. Osoba pełnoletnia to osoba
     * która ma więcej lub równo 18 lat
     */
    public Map<Boolean, Long> divideIntoAdultsAndNonAdults() {
        return holdings.stream()
                .flatMap(it -> it.getCompanies().stream())
                .flatMap(it -> it.getUsers().stream())
                .collect(Collectors.partitioningBy(it -> it.getAge() >= 18, counting()));
    }
}
