// groovy -c UTF-8 wm2018.groovy
// Copy the current JAR to the grape repository:
// cp ./target/betoffice-storage-2.6.0-SNAPSHOT.jar \
//   /cygdrive/c/Users/winkler/.groovy/grapes/de.winkler.betoffice/betoffice-storage/jars/betoffice-storage-2.6.0-SNAPSHOT.jar

@GrabResolver(name='gluehloch', root='http://maven.gluehloch.de/repository')
@GrabResolver(name='MavenCentral', root='http://maven.gluehloch.de/repository')
@Grab(group='org.slf4j', module='slf4j-api', version='1.6.1')

@Grab(group='org.apache.commons', module='commons-pool2', version='2.8.1')
@Grab(group='org.apache.commons', module='commons-dbcp2', version='2.7.0')
@Grab(group='org.mariadb.jdbc', module='mariadb-java-client', version='2.6.2')

@Grab(group='de.winkler.betoffice', module='betoffice-storage', version='3.0.1-SNAPSHOT')
@Grab(group='de.betoffice.wrapper', module='betoffice-wrapper', version='0.3.0-SNAPSHOT')

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import java.time.*;
import java.time.format.*;

import de.winkler.betoffice.storage.*
import de.winkler.betoffice.storage.enums.*

import de.betoffice.wrapper.cli.*
import de.betoffice.wrapper.api.*

//def args = new String[] {"--test"};
//BetofficeApiMain.main(args);

BetofficeApplicationContext bac = new BetofficeApplicationContext();
ApplicationContext context = bac.createApplicationContext();
BetofficeApi betofficeApi = getBean(BetofficeApi.class, context);

var seasonRef = betofficeApi.createSeason("Bundesliga", "2023/2024", SeasonType.LEAGUE, TeamType.DFB).result();

GroupTypeRef bundesliga = GroupTypeRef.of("1. Liga");
betofficeApi.addGroup(seasonRef, bundesliga);
