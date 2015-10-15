# jbehave-jbel
JBel is an expression language interpretor for JBehave's story file

# Getting started
Initially you should add **JBelStoryParser** in yur jbehave configuration
```java
configuration.useStoryParser(new JBelStoryParser(new RegexStoryParser()))
```
**here it is !**  you can verify jbel doesn't break your test by running then as usally

# Using
JBel story parser is a preprocessor.
JBel use javascript to evaluate expression.

The syntax is 

| type | syntax | converted to| 
| --- | --- | --- |
|Assignment| Given user want @value=1 chocolate | Given user want 1 chocolate |
|Assignment| Given user want @value={1 + 3 } chocolates | Given user want 4 chocolates |
|Assignment| Given user want @value="3 chocolats" | Given user want 3 chocolates |
|Evaluation| Then user will @{value > 30?"":"not"} continue eating | Then user will not continue eating |


# Samples

```
Given I want to buy a new computer for @value=5 Euros
When I give @money={5*5 + 4*2 + 2*1} Euros to the seller
Then I will have @{money - value} Euros back
```
is evaluated as 
```
Given I want to buy a new computer for 5.0 Euros
When I give 35.0 Euros to the seller
Then I will have 30.0 Euros back
```

***

```
Given you take version @version="v2"
When You don't want to do that : @{ (function(){return {test:4}.test+2})()*5} but maybe that : @{ {v1:"Good version",v2:"Bad version"}[version]}
```
is evaluated as 
```
Given you take version v2
When You don't want to do that : 30.0 but maybe that : Bad version
```

***

```
Given a user who has @amount=1000 on his account
When he makes a trade of type @type="buy" for quantity @quantity=10 and unitPrice @unitPrice=2
Then he has on his account @{amount + ((type=="buy")?-1:1) * quantity * unitPrice}
```
is evaluated as 

```
Given a user who has 1000.0 on his account
When he makes a trade of type buy for quantity 10.0 and unitPrice 2.0
Then he has on his account 980.0
```


