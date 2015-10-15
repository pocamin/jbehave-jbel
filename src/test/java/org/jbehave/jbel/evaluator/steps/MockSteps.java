package org.jbehave.jbel.evaluator.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * (c) Swissquote 10/15/15
 *
 * @author bleroux
 */
public class MockSteps {

	@Given("I want to buy $what for $price Euros")
	public void buy(String what, Double price) {
		// Mock
	}

	@When("I give $amount Euros to the seller")
	public void give(Double amount) {
		// Mock
	}

	@Then("I will have $amount Euros back")
	public void rest(Double amount) {
		// Mock
	}

	@Given("you take version $version")
	public void takeVersion(String version) {
		// Mock
	}

	@When("You don't want to do that : $doubleValue but maybe that : $versionStatus")
	public void wantToDo(Double doubleValue, String versionStatus) {
		// Mock
	}

	@Given("a user who has 1000.0 on his account")
	@When("he makes a trade of type buy for quantity 10.0 and unitPrice 2.0")
	@Then("he has on his account 980.0")
	public void notManagedSteps() {
		// mock
	}

	@Given("I have $amount in my pocket")
	public void myPocket(Double amount) {
		// Mock
	}

	@When("I buy $quantity for $unitPrice with extra taxes $taxes %")
	public void buy(Double quantity, Double unitPrice, Double taxes) {
		// Mock
	}

	@When("I buy $quantity for $unitPrice")
	public void buyWithoutTaxes(Double quantity, Double unitPrice) {
		// Mock
	}

	@Then("I have $amount in my pocket")
	public void verifyMyPocket(Double amount) {

	}

	@Given("the user says $talk")
	public void userSays(String talk) {
		// Mock
	}

	@When("user is invited to talk")
	public void invitedToTalk() {
		// Mock
	}

	@Then("the user says $talk")
	public void thenUserSays(String talk) {
		// Mock
	}

}
