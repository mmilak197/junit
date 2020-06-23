package com.devmmilak.junit;

import com.devmmilak.junit.domain.*;
import com.devmmilak.junit.logic.WorkShop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JUnitTest {

	private WorkShop sut;

	@BeforeEach
	public void setUp() {
		sut = new WorkShop();
	}

	@Test
	public void shouldGetHoldingWhereAreCompanies() {
		var actual = sut.getHoldingsWhereAreCompanies();
		var expected = 3;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetHoldingNames() {
		var actual = sut.getHoldingNames();
		var expected = 3;

		assertThat(expected).isEqualTo(actual.size());
		assertThat("[nestle, coca-cola, pepsico]").isEqualTo(actual.toString());
	}

	@Test
	public void shouldGetHoldingNamesAsString() {
		var actual = sut.getHoldingNamesAsString();
		var expected = "(Coca-Cola, Nestle, Pepsico)";

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetCompaniesAmount() {
		var actual = sut.getCompaniesAmount();
		var expected = 8;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllUserAmount() {
		var actual = sut.getAllUserAmount();
		var expected = 20;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllCompaniesNames() {
		var actual = sut.getAllCompaniesNames();
		var expected = "[Nescafe, Gerber, Nestea, Fanta, Sprite, Lays, Pepsi, Mirinda]";

		assertNotNull(actual);
		assertThat(expected).isEqualTo(actual.toString());
	}

	@Test
	public void shouldGetAllCompaniesNamesAsString() {
		var actual = sut.getAllCompaniesNamesAsString();
		var expected = "Nescafe+Gerber+Nestea+Fanta+Sprite+Lays+Pepsi+Mirinda";

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllCompaniesNamesAsSortedString() {
		var actual = sut.getAllCompaniesNamesAsSortedString();
		var expected = "Fanta+Gerber+Lays+Mirinda+Nescafe+Nestea+Pepsi+Sprite";

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllCompaniesNamesAsStringUsingStringBuilder() {
		var actual = sut.getAllCompaniesNamesAsStringUsingStringBuilder();
		var expected = "Nescafe+Gerber+Nestea+Fanta+Sprite+Lays+Pepsi+Mirinda";

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllUserAccountsAmount() {
		var actual = sut.getAllUserAccountsAmount();
		var expected = 35;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllCompaniesNamesAsLinkedList() {
		var actual = sut.getAllCompaniesNamesAsLinkedList();
		var expected = "[Nescafe, Gerber, Nestea, Fanta, Sprite, Lays, Pepsi, Mirinda]";

		assertThat(expected).isEqualTo(actual.toString());
	}

	@Test
	public void shouldGetAllCompaniesNamesAsHashSet() {
		var actual = sut.getAllCompaniesNamesAsHashSet();
		var expected = "[Sprite, Gerber, Pepsi, Fanta, Nescafe, Nestea, Lays, Mirinda]";

		assertThat(expected).isEqualTo(actual.toString());
	}

	@Test
	public void shouldGetAllCurrencies() {
		var actual = sut.getAllCurrencies();
		var expected = "CHF, EUR, PLN, USD";

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetAllCurrenciesToListAsString() {
		var actual = sut.getAllCurrenciesUsingGenerate();
		var expected = "CHF, EUR, PLN, USD";

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldGetWomanAmount() {
		var actual = sut.getWomanAmount();
		var expected = 4;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldReturnTotalCashInPLN() {
		var accounts = Arrays.asList(
				Account.builder().amount(new BigDecimal(12)).currency(Currency.PLN).build(),
				Account.builder().amount(new BigDecimal(234)).currency(Currency.EUR).build()
		);

		var actual = sut.getTotalCashInPLN(accounts);
		var expected = 1001;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldReturnWomanWhichAreOlderThan50() {
		var actual = sut.getOldMan(50);
		var expected = "[Karol]";

		assertNotNull(actual);
		assertThat(expected).isEqualTo(actual.toString());
	}

	@Test
	public void shouldReturnRichestWoman() {
		var actual = sut.getRichestWoman().get();
		var expected = "Zosia";

		assertThat(expected).isEqualTo(actual.getFirstName());
		assertThat("Psikuta").isEqualTo(actual.getLastName());
	}

	@Test
	public void shouldReturnUserAmountInPLN() {
		var actual = sut.getFirstNCompany(10);
		var expected = "[Sprite, Gerber, Pepsi, Fanta, Nescafe, Nestea, Lays, Mirinda]";
//		var user = User.builder().age(12).firstName("test").build();

		assertThat(expected).isEqualTo(actual.toString());
	}

	@Test
	public void shouldReturnMostPopularAccountType() {
		var actual = sut.getMostPopularAccountType();
		var expected = AccountType.ROR1;

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void shouldReturnAccountList() {
		Consumer<Company> consumer = company -> System.out.println(company.getName());
		sut.executeForEachCompany(consumer);
	}
}
