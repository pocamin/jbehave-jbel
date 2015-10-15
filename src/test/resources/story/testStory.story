Story: a simple story

Lifecycle:
Before:
Given I have 102.0 in my pocket

Scenario: normal case
When I buy 7.0 for 2.0 with extra taxes 50.0 %
Then I have 81.0 in my pocket

Scenario: Free Taxe payment  !
When I buy 10.0 for 7.0
Then I have 32.0 in my pocket

Scenario: Overriding a header attribute !
Given I have 10.0 in my pocket
Then I have 10.0 in my pocket

Scenario: ...doesn't impact the next scenario
Then I have 102.0 in my pocket