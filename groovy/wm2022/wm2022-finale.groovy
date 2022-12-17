// groovy -c UTF-8 wn2922.groovy
// Copy the current JAR to the grape repository:
// cp ./target/betoffice-storage-2.6.0-SNAPSHOT.jar \
//   /cygdrive/c/Users/winkler/.groovy/grapes/de.winkler.betoffice/betoffice-storage/jars/betoffice-storage-2.6.0-SNAPSHOT.jar

@GrabResolver(name='gluehloch', root='http://maven.gluehloch.de/repository')
@Grab(group='org.slf4j', module='slf4j-api', version='1.6.1')

@Grab(group='org.apache.commons', module='commons-pool2', version='2.8.1')
@Grab(group='org.apache.commons', module='commons-dbcp2', version='2.7.0')
@Grab(group='org.mariadb.jdbc', module='mariadb-java-client', version='2.6.2')

//@Grab(group='de.winkler.betoffice', module='betoffice-storage', version='2.8.0-SNAP-2021-05-31')
@Grab(group='de.winkler.betoffice', module='betoffice-storage', version='2.8.2')

import org.springframework.context.support.ClassPathXmlApplicationContext

import java.time.*;
import java.time.format.*;

import de.winkler.betoffice.storage.*
import de.winkler.betoffice.storage.enums.*

class Service {
    private ClassPathXmlApplicationContext context

    def maintenanceService
    def masterService
    def seasonService

    public Service() {
        context = new ClassPathXmlApplicationContext(
            ['classpath:/betoffice-persistence.xml', 'classpath:/betoffice-datasource.xml', 'file:hibernate.xml'] as String[])
        maintenanceService = context.getBean('databaseMaintenanceService')
        masterService = context.getBean('masterDataManagerService')
        seasonService = context.getBean('seasonManagerService')
    }

    def toDate(dateTimeAsString) {
        ZoneId zone = ZoneId.of("Europe/Berlin");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeAsString, formatter).atZone(zone);

        /*
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
        DateTime dateTime = formatter.parseDateTime(dateTimeAsString)
        return dateTime
        */
    }

    def createSeason(season) {
        seasonService.createSeason(season);
    }

    def findGroupType(groupType) {
        return masterService.findGroupType(groupType).get()
    }

    def findRound(season, index) {
        return seasonService.findRound(season, index)
    }

    def findGroup(season, group) {
        try {
            return seasonService.findGroup(season, group)
        } catch (javax.persistence.NoResultException ex) {
            return null
        }
    }

    def addGroup(season, groupType) {
        def group = findGroup season, groupType
        if (group == null) {
            group = seasonService.addGroupType season, groupType
        }
        return group
    }

    def findTeam(teamName) {
        def team = masterService.findTeam(teamName)
        if (!team.isPresent()) {
            println "team '${teamName}' not found"
        } else {
            println "Team '${team.get().getName()}' is there."
        }
        return team
    }

    def findTeams(season, groupType) {
        return seasonService.findTeams(season, groupType)
    }

    def updateTeam(team) {
        masterService.updateTeam(team)
    }

    def addTeam(season, groupType, team) {
        seasonService.addTeam(season, groupType, team)
    }

    def addTeams(season, groupType, teams) {
        seasonService.addTeams(season, groupType, teams)
    }

    def addRound(season, date, groupType) {
        return seasonService.addRound(season, toDate(date), groupType)
    }

    def addMatch(round, date, group, homeTeam, guestTeam) {
      return seasonService.addMatch(round, toDate(date), group, homeTeam, guestTeam)
    }
}

def validate(object) {
    if (object == null) {
        throw new NullPointerException();
    } else {
        println "OK: " + object
    }
}

def printTeams(group) {
    println "Teams $group"
    for (team in group.teams) {
        println team
    }
}

Service service = new Service();

def wm2022
def seasonOptional = service.seasonService.findSeasonByName('WM Katar', '2022')
if (seasonOptional.isPresent()) {
    wm2022 = seasonOptional.get()
} else {
    wm2022 = new Season();
    wm2022.name = 'WM Katar'
    wm2022.year = 2022
    wm2022.mode = SeasonType.WC
    wm2022.teamType = TeamType.FIFA
    wm2022 = service.createSeason(wm2022);
}

println wm2022.name + " - " + wm2022.year

 // def bundesliga = master.findGroupType('1. Bundesliga');
def gruppeA = service.findGroupType('Gruppe A');
validate gruppeA
def gruppeB = service.findGroupType('Gruppe B');
validate gruppeB
def gruppeC = service.findGroupType('Gruppe C');
validate gruppeC
def gruppeD = service.findGroupType('Gruppe D');
validate gruppeD
def gruppeE = service.findGroupType('Gruppe E');
validate gruppeE
def gruppeF = service.findGroupType('Gruppe F');
validate gruppeF
def gruppeG = service.findGroupType('Gruppe G');
validate gruppeG
def gruppeH = service.findGroupType('Gruppe H');
validate gruppeH

def achtelfinale = service.findGroupType('Achtelfinale');
validate achtelfinale
def viertelfinale = service.findGroupType('Viertelfinale');
validate viertelfinale
def halbfinale = service.findGroupType('Halbfinale');
validate halbfinale
def finale = service.findGroupType('Finale');
validate finale
def platz3 = service.findGroupType('Spiel um Platz 3');
validate platz3

// Gruppe A

def katar = service.findTeam('Katar').get();
validate katar

def ecuador = service.findTeam('Ecuador').get();
validate ecuador

def senegal = service.findTeam('Senegal').get();
validate senegal

def niederlande = service.findTeam('Niederlande').get();
validate niederlande

// Gruppe B

def england = service.findTeam('England').get();
validate england

def iran = service.findTeam('Iran').get();
validate iran

def usa = service.findTeam('USA').get();
validate usa

def wales = service.findTeam('Wales').get();
validate wales

// --- Gruppe C

def argentinien = service.findTeam('Argentinien').get();
validate argentinien

def saudiArabien = service.findTeam('Saudi Arabien').get();
validate saudiArabien

def mexiko = service.findTeam('Mexiko').get();
validate mexiko

def polen = service.findTeam('Polen').get();
validate polen

// Gruppe D

def daenemark = service.findTeam('Dänemark').get();
validate daenemark

def tunesien = service.findTeam('Tunesien').get();
validate tunesien

def frankreich = service.findTeam('Frankreich').get();
validate frankreich

def australien = service.findTeam('Australien').get();
validate australien

// Gruppe E

def costaRica = service.findTeam('Costa Rica').get();
validate costaRica

def deutschland = service.findTeam('Deutschland').get();
validate deutschland

def spanien = service.findTeam('Spanien').get();
validate spanien

def japan = service.findTeam('Japan').get();
validate japan

// Gruppe F

def belgien = service.findTeam('Belgien').get();
validate belgien

def kanada = service.findTeam('Kanada').get();
validate kanada

def marokko = service.findTeam('Marokko').get();
validate marokko

def kroatien = service.findTeam('Kroatien').get();
validate kroatien

// Gruppe G

def brasilien = service.findTeam('Brasilien').get();
validate brasilien

def serbien = service.findTeam('Serbien').get();
validate serbien

def schweiz = service.findTeam('Schweiz').get();
validate schweiz

def kamerun = service.findTeam('Kamerun').get()
validate kamerun

// Gruppe H

def portugal = service.findTeam('Portugal').get();
validate portugal

def ghana = service.findTeam('Ghana').get();
validate ghana

def uruguay = service.findTeam('Uruguay').get();
validate uruguay

def suedkorea = service.findTeam('Rep.Korea').get();
validate suedkorea

def wm2022_gruppe_A = service.findGroup(wm2022, gruppeA);
def wm2022_gruppe_B = service.findGroup(wm2022, gruppeB);
def wm2022_gruppe_C = service.findGroup(wm2022, gruppeC);
def wm2022_gruppe_D = service.findGroup(wm2022, gruppeD);
def wm2022_gruppe_E = service.findGroup(wm2022, gruppeE);
def wm2022_gruppe_F = service.findGroup(wm2022, gruppeF);
def wm2022_gruppe_G = service.findGroup(wm2022, gruppeG);
def wm2022_gruppe_H = service.findGroup(wm2022, gruppeH);
def wm2022_achtelfinale = service.findGroup(wm2022, achtelfinale);
def wm2022_viertelfinale = service.findGroup(wm2022, viertelfinale);
def wm2022_halbfinale = service.findGroup(wm2022, halbfinale);

def wm2022_platz3 = service.addGroup wm2022, platz3
wm2022_platz3 = service.findGroup(wm2022, platz3);

def wm2022_finale = service.addGroup wm2022, finale
wm2022_finale = service.findGroup(wm2022, finale);

def round_2022_12_17 = service.findRound(wm2022, 6)
if (round_2022_12_17.isPresent()) {
    round_2022_12_17 = round_2022_12_17.get()
} else {
    round_2022_12_17 = service.addRound(wm2022, '2022-12-17 16:00:00', platz3)
}

def round_2022_12_18 = service.findRound(wm2022, 7)
if (round_2022_12_18.isPresent()) {
    round_2022_12_18 = round_2022_12_18.get()
} else {
    round_2022_12_18 = service.addRound(wm2022, '2022-12-18 16:00:00', finale)
}

wm2022_platz3 = service.addTeams(wm2022, platz3, [kroatien, marokko])
printTeams(wm2022_platz3)

wm2022_finale = service.addTeams(wm2022, finale, [argentinien, frankreich])
printTeams(wm2022_finale)


service.addMatch(round_2022_12_17, '2022-12-17 16:00:00', wm2022_platz3, kroatien, marokko)
service.addMatch(round_2022_12_18, '2022-12-18 16:00:00', wm2022_finale, argentinien, frankreich)

