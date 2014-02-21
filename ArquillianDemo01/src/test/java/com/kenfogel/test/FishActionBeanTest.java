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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.kenfogel.beans.FishActionBean;
import com.kenfogel.beans.FishData;

@RunWith(Arquillian.class)
public class FishActionBeanTest {

	@Inject
	FishActionBean fab;

	@Deployment
	public static WebArchive deploy() {
		
		/*
		 SEVERE: CDI Beans module deployment failed
         java.lang.TypeNotPresentException: Type com.kenfogel.beans.FishData not present
         
         Does not see the non-managed FishData class
		 */
/*        return ShrinkWrap.create(WebArchive.class).
                addClasses(FishActionBean.class).
                addAsWebInfResource(EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"));
*/        
		/*
		 Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 10.766 sec <<< FAILURE! - in com.kenfogel.test.FishActionBeanTest
         allTest(com.kenfogel.test.FishActionBeanTest)  Time elapsed: 0.632 sec  <<< ERROR!
         java.sql.SQLSyntaxErrorException: user lacks privilege or object not found: FISH
         
          Does not see context.xml so defaults to hsql
		 */
/*        return ShrinkWrap.create(WebArchive.class)
                .addClasses(FishActionBean.class)
                .addPackage("com.kenfogel.beans")
                .addAsWebInfResource(EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"));
*/        
		/*
		 *SEVERE: Unable to deploy collapsed ear in war StandardEngine[Catalina].StandardHost[localhost].StandardContext[/ArquillianDemo01]
          org.apache.xbean.propertyeditor.PropertyEditorException: Unable to resolve class com.mysql.jdbc.Driver
          
          Can't find the mysql library
          
          Test with embedded TomEE works for all ShrinkWarps from this point on  
		 */
		/*
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(FishActionBean.class)
                .addPackage("com.kenfogel.beans")
                .addAsWebInfResource(EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"))
                				.addAsManifestResource(
						new FileAsset(new File(
								"src/main/webapp/META-INF/context.xml")),
						"context.xml");
         */
		/*
		 Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 13.839 sec <<< FAILURE! - in com.kenfogel.test.FishActionBeanTest
         allTest(com.kenfogel.test.FishActionBeanTest)  Time elapsed: 1.045 sec  <<< ERROR!
         java.lang.IllegalStateException: Error launching test com.kenfogel.test.FishActionBeanTest public void com.kenfogel.test.FishActionBeanTest.allTest() throws java.lang.Exception
         
         Here is where I have run out of ideas 
		 */
		
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(FishActionBean.class)
                .addPackage("com.kenfogel.beans")
                .addAsWebInfResource(EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"))
                				.addAsManifestResource(
						new FileAsset(new File(
								"src/main/webapp/META-INF/context.xml")),
						"context.xml")
								.addAsLibraries(
						Maven.resolver().loadPomFromFile(new File("pom.xml"))
								.importRuntimeDependencies()
								.resolve("mysql:mysql-connector-java")
								.withoutTransitivity().asFile());
        
	}

	/**
	 * Simple test
	 * 
	 * @throws Exception
	 */
	@Test
	public void allTest() throws Exception {

		assertNotNull(fab);
		List<FishData> lfd;
		lfd = fab.getAll();
		assertEquals("Expected number of fish not found.", 200, lfd.size());

	}
}
