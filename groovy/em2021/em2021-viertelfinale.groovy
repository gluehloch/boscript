// groovy -c UTF-8 wm2018.groovy
// Copy the current JAR to the grape repository:
// cp ./target/betoffice-storage-2.6.0-SNAPSHOT.jar \
//   /cygdrive/c/Users/winkler/.groovy/grapes/de.winkler.betoffice/betoffice-storage/jars/betoffice-storage-2.6.0-SNAPSHOT.jar

@GrabResolver(name='gluehloch', root='http://maven.gluehloch.de/repository')
@Grab(group='org.slf4j', module='slf4j-api', version='1.6.1')

@Grab(group='org.apache.commons', module='commons-pool2', version='2.8.1')
@Grab(group='org.apache.commons', module='commons-dbcp2', version='2.7.0')
@Grab(group='org.mariadb.jdbc', module='mariadb-java-client', version='2.6.2')

@Grab(group='de.winkler.betoffice', module='betoffice-storage', version='2.8.0-SNAP-2021-05-31')

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

    def findRoundGroupTeamUserRelations(season) {
        return seasonService.findRoundGroupTeamUserRelations(season)
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
        }
        /*
        else {
            println "Team '${team.get().getName()}' is there."
        }
        */
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
    println "Mannschaften der Gruppe: $group.groupType.name"
    for (team in group.teams) {
        println team
    }
}

Service service = new Service();

def em2021
def seasonOptional = service.seasonService.findSeasonByName('EM Europe', '2021')
if (seasonOptional.isPresent()) {
    em2021 = seasonOptional.get()
} else {
    em2021 = new Season();
    em2021.name = 'EM Europe'
    em2021.year = 2021
    em2021.mode = SeasonType.EC
    em2021.teamType = TeamType.FIFA
    em2021 = service.createSeason(em2021);
}

println em2021.name + " - " + em2021.year

def newTeam
newTeam = service.findTeam('Finnland')
if (!newTeam.present) {
    def team = new Team()
    team.name = 'Finnland'
    team.longName = 'Finnland'
    team.shortName = 'Finnland'
    team.xshortName = 'FIN'
    team.logo = 'finnland.gif'
    team.teamType = TeamType.FIFA
    service.updateTeam(team)
}

newTeam = service.findTeam('Nordmazedonien')
if (!newTeam.present) {
    def team = new Team()
    team.name = 'Nordmazedonien'
    team.longName = 'Nordmazedonien'
    team.shortName = 'Nordmazedonien'
    team.xshortName = 'MKD'
    team.logo = 'nordmazedonien.gif'
    team.teamType = TeamType.FIFA
    service.updateTeam(team)
}

newTeam = service.findTeam('Schottland')
if (!newTeam.present) {
    def team = new Team()
    team.name = 'Schottland'
    team.longName = 'Schottland'
    team.shortName = 'Schottland'
    team.xshortName = 'SCO'
    team.logo = 'schottland.gif'
    team.teamType = TeamType.FIFA
    service.updateTeam(team)
}

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

def achtelfinale = service.findGroupType('Achtelfinale');
validate achtelfinale
def viertelfinale = service.findGroupType('Viertelfinale');
validate viertelfinale
def halbfinale = service.findGroupType('Halbfinale');
validate halbfinale
def finale = service.findGroupType('Finale');
validate finale
/*
def platz3 = service.findGroupType('Spiel um Platz 3');
validate platz3
*/

// Gruppe A
def italien = service.findTeam('Italien').get()
validate italien

def schweiz = service.findTeam('Schweiz').get();
validate schweiz

def tuerkei = service.findTeam("Türkei").get()
validate tuerkei

def wales = service.findTeam('Wales').get();
validate wales

// Gruppe B
def belgien = service.findTeam('Belgien').get();
validate belgien

def daenemark = service.findTeam('Dänemark').get();
validate daenemark

def finnland = service.findTeam('Finnland').get();
validate finnland

def russland = service.findTeam('Russland').get();
validate russland

// Gruppe C
def niederlande = service.findTeam('Niederlande').get();
validate niederlande

def nordmazedonien = service.findTeam('Nordmazedonien').get();
validate nordmazedonien

def oesterreich = service.findTeam('Österreich').get()
validate oesterreich

def ukraine = service.findTeam('Ukraine').get();
validate ukraine

// Gruppe D
def england = service.findTeam('England').get();
validate england

def kroatien = service.findTeam('Kroatien').get();
validate kroatien

def schottland = service.findTeam('Schottland').get();
validate schottland

def tschechien = service.findTeam('Tschechien').get();
validate tschechien

// Gruppe E
def polen = service.findTeam('Polen').get();
validate polen

def schweden = service.findTeam('Schweden').get();
validate schweden

def slowakei = service.findTeam('Slowakei').get();
validate slowakei

def spanien = service.findTeam('Spanien').get();
validate spanien

// Gruppe F
def deutschland = service.findTeam('Deutschland').get();
validate deutschland

def frankreich = service.findTeam('Frankreich').get();
validate frankreich

def portugal = service.findTeam('Portugal').get();
validate portugal

def ungarn = service.findTeam('Ungarn').get()
validate ungarn


// TODO #addGroup liefert nicht die Group zurück, sondern die Saison!!!
def em2021_gruppe_viertelfinale = service.addGroup em2021, viertelfinale
println "EM 2021 - Viertelfinale: $em2021_gruppe_viertelfinale $em2021_gruppe_viertelfinale.class.name"


// Vorrunde
def round_2021_06_11 = service.findRound(em2021, 0)
if (round_2021_06_11.isPresent()) {
    round_2021_06_11 = round_2021_06_11.get()
} else {
    round_2021_06_11 = service.addRound(em2021, '2021-06-11 21:00:00', gruppeA)
}
println "1. Runde - Vorrunde - $round_2021_06_11.dateTime"

def round_2021_06_16 = service.findRound(em2021, 1)
if (round_2021_06_16.isPresent()) {
    round_2021_06_16 = round_2021_06_16.get()
} else {
    round_2021_06_16 = service.addRound(em2021, '2021-06-16 14:00:00', gruppeA)
}
println "2. Runde - Vorrunde - $round_2021_06_16.dateTime"

def round_2021_06_20 = service.findRound(em2021, 2)
if (round_2021_06_20.isPresent()) {
    round_2021_06_20 = round_2021_06_20.get()
} else {
    round_2021_06_20 = service.addRound(em2021, '2021-06-20 18:00:00', gruppeA)
}
println "3. Runde - Vorrunde - $round_2021_06_20.dateTime"


// Achtelfinale
def round_2021_06_26 = service.findRound(em2021, 3)
if (round_2021_06_26.isPresent()) {
    round_2021_06_26 = round_2021_06_26.get()
} else {
    round_2021_06_26 = service.addRound(em2021, '2021-06-26 18:00:00', achtelfinale)
}
println "4. Runde - Achtelfinale - $round_2021_06_26.dateTime"


// FIX: Achtelfinale keine Mannschaften zugeordnet.
def em2021_achtelfinale = service.addTeams(em2021, achtelfinale, [
    wales, daenemark, italien, oesterreich, niederlande, tschechien, belgien, portugal,
    kroatien, spanien, frankreich, schweiz, deutschland, england, schweden, ukraine
])
printTeams(em2021_achtelfinale)


// Viertelfinale
// Optional<GameList>
def round_2021_07_02 = service.findRound(em2021, 4)
if (round_2021_07_02.isPresent()) {
    // GameList
    round_2021_07_02 = round_2021_07_02.get()
} else {
    // GameList
    round_2021_07_02 = service.addRound(em2021, '2021-07-02 18:00:00', viertelfinale)
}
println "5. Runde - Viertelfinale - $round_2021_07_02.dateTime"

def em2021_viertelfinale = service.addTeams(em2021, viertelfinale, [
    daenemark, italien, tschechien, belgien, spanien, schweiz, ukraine, england
])
printTeams(em2021_viertelfinale)

// 5. Spieltag / Viertelfinale
service.addMatch(round_2021_07_02, '2021-07-02 18:00:00', em2021_viertelfinale, schweiz, spanien)
service.addMatch(round_2021_07_02, '2021-07-02 21:00:00', em2021_viertelfinale, belgien, italien)

service.addMatch(round_2021_07_02, '2021-07-03 18:00:00', em2021_viertelfinale, tschechien, daenemark)
service.addMatch(round_2021_07_02, '2021-07-03 21:00:00', em2021_viertelfinale, ukraine, england)

