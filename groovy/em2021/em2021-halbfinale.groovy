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

import Service;

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
def em2021_gruppe_halbfinale = service.addGroup em2021, halbfinale
println "EM 2021 - Halbfinale: $em2021_gruppe_halbfinale $em2021_gruppe_halbfinale.class.name"


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


// Viertelfinale
def round_2021_07_02 = service.findRound(em2021, 4)
if (round_2021_07_02.isPresent()) {
    round_2021_07_02 = round_2021_07_02.get()
} else {
    round_2021_07_02 = service.addRound(em2021, '2021-07-02 18:00:00', viertelfinale)
}
println "5. Runde - Viertelfinale - $round_2021_07_02.dateTime"

def em2021_viertelfinale = service.addTeams(em2021, viertelfinale, [
    daenemark, italien, tschechien, belgien, spanien, schweiz, ukraine, england
])
printTeams(em2021_viertelfinale)


// 6. Spieltag / Halbfinale
def round_2021_07_06 = service.findRound(em2021, 5)
if (round_2021_07_06.isPresent()) {
    round_2021_07_06 = round_2021_07_06.get()
} else {
    round_2021_07_06 = service.addRound(em2021, '2021-07-06 21:00:00', halbfinale)
}
println "5. Runde - Halbfinale - $round_2021_07_06.dateTime"

def em2021_halbfinale = service.addTeams(em2021, halbfinale, [
    daenemark, italien, spanien, england
])
printTeams(em2021_halbfinale)

// 6. Spieltag / Halbfinale
service.addMatch(round_2021_07_06, '2021-07-06 21:00:00', em2021_halbfinale, italien, spanien)
service.addMatch(round_2021_07_06, '2021-07-07 21:00:00', em2021_halbfinale, england, daenemark)
