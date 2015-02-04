# ArquillianDemo01
Version 1: Small sample program to demonstrate Arquillian in a basic JSF and JDBC program. Embedded tests work, remote tests do not. See FishActionBeanTest.java for more info.

This problem was resolved with the kind assitance of Bartosz Majsak.

Version 2: Program is now called AquillianDemo01JPA Moved to GlassFish 4.1 and using jee 7 and se 1.8. The test is of a single method that uses JPA to retieve all the records. The site displays the records when testing is turned off (skiptests = true). In remote testing the database seeding routine that uses an injected DataSource and plain JDBC works fine. The method being tested used JPA and it gives an error message that says it cannot retrieve the EntityManagerFactory.

A similar version of this program that only uses an injected DataSource and JDBC for the method being tested does pass the test.

In all cases it takes as much as 48 seconds for the test to run, success or failure. Placing print statements in the code revealed that the test is run almost immediately and then there is the delay that NetBeans shows as the project still Building. Just running the test directly takes just as long and NetBeans tells me it is Testing.

Can't imagine what will happen if I try embedded. 
