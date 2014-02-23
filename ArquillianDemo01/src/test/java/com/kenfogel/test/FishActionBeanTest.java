/*
 In the folder webapp/resources/sql you will find the createFishMySQL.sql file
 Eclipse has a nasty habit of making the context.xml file disappear so a copy
 of that file is in this directory too. 
 
 All testing is run through maven using the goal: clean compile war:war test
 
 pom.xml refers to richfaces that are not being used, Removing all refernces to
 richfaces and its helpers jas no effect
 
 Problem occurs on systems running Windows 8.1 and Apple OSX 10.7
 Tests run with both Eclipse and NetBeams.
 
 TomEE server is version 1.6.0, Java 1.7.0_51
 
 */

package com.kenfogel.test;

import static org.assertj.core.api.Assertions.*;

import com.kenfogel.beans.FishActionBean;
import com.kenfogel.beans.FishData;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@RunWith(Arquillian.class)
public class FishActionBeanTest {

    @Deployment
    public static WebArchive deploy() {

        final File[] dependencies = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java", "org.assertj:assertj-core")
                .withoutTransitivity().asFile();

        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(FishActionBean.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("resources-mysql-ds.xml", "resources.xml")
                .addAsResource("createFishMySQL.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Inject
    private FishActionBean fab;

    @Resource(name = "jdbc/fish")
    private DataSource ds;

    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("createFishMySQL.sql");
        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    @Test
    public void should_find_all_fish() throws Exception {
        List<FishData> lfd = fab.getAll();
        assertThat(lfd).hasSize(200);
    }

    // Utility methods

    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader, String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement =  new StringBuilder();
        final List<String> statements = new LinkedList<String>();
        try {
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
    }

}
